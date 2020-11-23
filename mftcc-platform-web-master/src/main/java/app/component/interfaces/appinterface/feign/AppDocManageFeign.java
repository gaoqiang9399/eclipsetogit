package app.component.interfaces.appinterface.feign;

import java.io.File;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.doc.entity.DocManage;

/**
 * 要件信息管理的Bo类
 * @author zhang_dlei
 */
@FeignClient("mftcc-platform-factor")
public interface AppDocManageFeign{
	@RequestMapping(value = "/appDocManage/getDocInfoList")
	public Map<String, Object> getDocInfoList(@RequestBody  String ajaxData) throws Exception;

	@RequestMapping(value = "/appDocManage/insertDocManage")
	public Map<String, Object> insertDocManage(@RequestParam("realpath")  String realpath,@RequestBody File upload,@RequestParam("docManage") DocManage docManage,@RequestParam("httpServletRequest")
			HttpServletRequest httpServletRequest) throws Exception;
	
}
