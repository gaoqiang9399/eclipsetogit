package  app.component.interfaces.mobileinterface.feign;

import java.io.File;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.doc.entity.DocManage;
import app.component.interfaces.mobileinterface.entity.AppDocManageDTO;


/**
* Title: DocManageBo.java
* Description:
* @author:@dhcc.com.cn
* @Tue Jan 26 11:18:01 GMT 2016
**/
@FeignClient("mftcc-platform-factor")
public interface AppDocUploadFeign{
	
	@RequestMapping(value = "/appDocUpload/insert")
	public Map<String, Object> upload(@RequestParam("uploadFileName") String uploadFileName,@RequestBody  File upload,@RequestParam("appDocManageDTO") AppDocManageDTO appDocManageDTO,@RequestParam("httpServletRequest")
			HttpServletRequest httpServletRequest) throws Exception;

	@RequestMapping(value = "/appDocUpload/insertDocManage")
	public Map<String, Object> insertDocManage(@RequestParam("realpath")  String realpath,@RequestBody File upload,@RequestParam("docManage") DocManage docManage,@RequestParam("uploadContentType")
			String uploadContentType) throws Exception;

	@RequestMapping(value = "/appDocUpload/updateDocManage")
	public Map<String, Object> updateDocManage(@RequestBody  File upload,@RequestParam("docManage") DocManage docManage,@RequestParam("uploadContentType") String uploadContentType)
			throws Exception;

	@RequestMapping(value = "/appDocUpload/insertOrUpdateDocManage")
	public Map<String, Object> insertOrUpdateDocManage(@RequestParam("cusNo")  String cusNo,@RequestParam("fileName") String fileName,@RequestBody File upload,@RequestParam("relNo") String relNo,@RequestParam("docBizNo")
			String docBizNo,@RequestParam("docSplitNo") String docSplitNo,@RequestParam("docType") String docType,@RequestParam("uploadContentType") String uploadContentType) throws Exception;

	@RequestMapping(value = "/appDocUpload/getDocFileByNames")
	public Map<String, Object> getDocFileByNames(@RequestBody  String dicNames,@RequestParam("relsNo") String relsNo,@RequestParam("docType") String docType,@RequestParam("scNo") String scNo)
			throws Exception;

	@RequestMapping(value = "/appDocUpload/getThumbnailByNames")
	public Map<String, Object> getThumbnailByNames(@RequestBody  String dicNames,@RequestParam("relsNo") String relsNo,@RequestParam("docType") String docType,@RequestParam("scNo") String scNo)
			throws Exception;

	@RequestMapping(value = "/appDocUpload/checkIsUpload")
	public Map<String, Object> checkIsUpload(@RequestBody  String dicNames,@RequestParam("cusNo") String cusNo) throws Exception;

	@RequestMapping(value = "/appDocUpload/getDocManageByName")
	public DocManage getDocManageByName(@RequestBody  String fileName,@RequestParam("docBizNo") String docBizNo,@RequestParam("docSplitNo") String docSplitNo) throws Exception;

}
