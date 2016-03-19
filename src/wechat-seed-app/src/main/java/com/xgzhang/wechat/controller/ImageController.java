package com.xgzhang.wechat.controller;

import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.xgzhang.wechat.db.ImageRepository;
import com.xgzhang.wechat.framework.tools.IOUtility;

@Controller
@RequestMapping(value="image")
public class ImageController {
	private Logger _logger = LoggerFactory.getLogger(this.getClass());
	private ImageRepository _imageRepository;
	
	@Autowired
	public ImageController(ImageRepository imageRepository){
		this._imageRepository = imageRepository;
	}
	
	@RequestMapping(value="/{key}",method=RequestMethod.GET)
	public void getImage(@PathVariable String key,
			HttpServletRequest request,
			HttpServletResponse response){
		byte[] imageBuffer = this._imageRepository.getImage(key);
		if(imageBuffer != null && imageBuffer.length != 0){
			response.setContentType("image/jpg");
			try
			{
				OutputStream outputStream = response.getOutputStream();
				outputStream.write(imageBuffer);
			}
			catch(Exception e)
			{
				this._logger.error(e.getMessage(), e);
			}
		}else{
			try{
				String defaultImagePath = request.getSession().getServletContext().getRealPath("/resources/images") + "/image.png";
				OutputStream outputStream = response.getOutputStream();
				outputStream.write(IOUtility.readImageFromDisc(defaultImagePath));
			}catch(Exception e){
				this._logger.error(e.getMessage(), e);
			}
		}
	}
}
