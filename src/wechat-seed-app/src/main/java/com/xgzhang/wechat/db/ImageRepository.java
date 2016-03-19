package com.xgzhang.wechat.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ImageRepository {
	private final int MAX_COUNT = 10;
	
	private Map<String, byte[]> _imageMap;
	
	public ImageRepository(){
		this._imageMap = new HashMap<String, byte[]>();
	}
	
	public String saveImage(byte[] imageBuffer){
		String newImagekey = UUID.randomUUID().toString();
		if(this._imageMap.keySet().size() >= MAX_COUNT)
		{
			Iterator<String> iterator = this._imageMap.keySet().iterator();
			String clearedImageKey = iterator.next();
			this._imageMap.remove(clearedImageKey);
		}
		this._imageMap.put(newImagekey, imageBuffer);
		return newImagekey;
	}
	
	public byte[] getImage(String key){
		return this._imageMap.get(key);
	}
	
	public List<String> getAllImageKeys(){
		List<String> keys = new ArrayList<String>();
		Iterator<String> iterator = this._imageMap.keySet().iterator();
		while(iterator.hasNext())
			keys.add(iterator.next());
		return keys;
	}
}
