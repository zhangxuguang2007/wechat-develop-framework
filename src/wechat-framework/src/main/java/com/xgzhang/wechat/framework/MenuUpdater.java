package com.xgzhang.wechat.framework;

import java.io.StringWriter;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.json.JSONObject;

import com.xgzhang.wechat.framework.domain.menu.ButtonWrap;

public class MenuUpdater {
	private String _appId;
	private String _secret;
	private MenuFactory _menuFactory;
	
	public MenuUpdater(String appId, String secret, MenuFactory menuFactory){
		this._appId = appId;
		this._secret = secret;
		this._menuFactory = menuFactory;
	}
	
	public int update() throws Exception {
		// Access token
		HttpGet tokenHttpGet = new HttpGet(String.format("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s", this._appId, this._secret));
		HttpClient httpClient = new DefaultHttpClient();
		HttpResponse tokenResponse = httpClient.execute(tokenHttpGet);
		byte[] tokenBytes = EntityUtils.toByteArray(tokenResponse.getEntity());
		String token = (new JSONObject(new String(tokenBytes, "utf8"))).getString("access_token");

		// Delete old menus
		HttpGet deleteHttpGet = new HttpGet("https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=" + token);
		HttpResponse deleteResponse = httpClient.execute(deleteHttpGet);
		byte[] deleteBytes = EntityUtils.toByteArray(deleteResponse.getEntity());
		int errorCode = (new JSONObject(new String(deleteBytes, "utf8"))).getInt("errcode");
		if(errorCode != 0)
			return errorCode;

		// Create new menus
		HttpPost createPost = new HttpPost("https://api.weixin.qq.com/cgi-bin/menu/create?access_token=" + token);
		StringEntity contentEntity = new StringEntity(createMenuJsonString());
		createPost.setEntity(contentEntity);
		HttpResponse createResponse = httpClient.execute(createPost);
		byte[] createBytes = EntityUtils.toByteArray(createResponse.getEntity());
		errorCode = (new JSONObject(new String(createBytes, "utf8"))).getInt("errcode");
		return errorCode;
	}
	
	private String createMenuJsonString() throws Exception {
		String jsonStr = null;
		ObjectMapper mapper = new ObjectMapper();
		mapper.disable(SerializationConfig.Feature.WRITE_EMPTY_JSON_ARRAYS);
		mapper.disable(SerializationConfig.Feature.WRITE_NULL_PROPERTIES);
		StringWriter sw = new StringWriter();
		JsonGenerator gen = new JsonFactory().createJsonGenerator(sw);
		mapper.writeValue(gen, new ButtonWrap(this._menuFactory.generateMenu()));
		gen.close();
		jsonStr = sw.toString();
		return jsonStr;
	}
}
