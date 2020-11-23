package app.component.frontview.util;

import app.base.SpringUtil;
import app.component.frontview.feign.VwUploadFeign;
import app.tech.upload.FeignSpringFormEncoder;
import cn.mftcc.util.StringUtil;
import com.google.gson.Gson;
import config.YmlConfig;
import feign.Feign;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UploadUtils {

	/**
	 * 获取文件服务器地址
	 */
	public static String getFileServiceUrl(){
		DiscoveryClient discoveryClient=(DiscoveryClient)SpringUtil.getBean("discoveryClient");
		List<ServiceInstance> list = discoveryClient.getInstances("mftcc-platform-fileService");
		String httpurl = null;
		if (list != null && list.size() > 0) {
			ServiceInstance instance = list.get(0);
			URI uri=instance.getUri();
			String scheme=uri.getScheme();
			String url = instance.getHost();
			int port = instance.getPort();
			httpurl = scheme+"://"+url + ":" + port;
		}
		return httpurl;
	}

	/**
	 * 获取mftcc-platform-factor-web地址
	 * @return
	 */
	public static String getFactorWebUrl(){
		YmlConfig ymlConfig=(YmlConfig)SpringUtil.getBean("ymlConfig");
		Map<String, String> webservice= ymlConfig.getWebservice();
		String frontWebPath=webservice.get("frontWebPath");
		if(StringUtil.isEmpty(frontWebPath)){
			return "";
		}
		return frontWebPath;
	}


	public static Map<String,Object> uploadVwImg(MultipartFile upload, String folder, String filename) {
		Map<String ,Object> result=new HashMap<>();
		try {
			VwUploadFeign vwUploadFeign = Feign.builder().encoder(new FeignSpringFormEncoder()).target(VwUploadFeign.class, UploadUtils.getFileServiceUrl());
			String str=vwUploadFeign.uploadFile(upload,folder,filename);
			Gson gson=new Gson();
			Map<String,Object> map=gson.fromJson(str,Map.class);
			String path=(String)map.get("path");
			String originalFilename=upload.getOriginalFilename();
			String fileType = originalFilename.substring(originalFilename.lastIndexOf(".") );
			result.put("name",filename);
			result.put("originalName",upload.getOriginalFilename());
			result.put("size",upload.getSize());
			result.put("state","SUCCESS");
			result.put("type",fileType);
			result.put("url",path);
		}catch (Exception e){
			throw e;
		}
		return result;
	}
}
