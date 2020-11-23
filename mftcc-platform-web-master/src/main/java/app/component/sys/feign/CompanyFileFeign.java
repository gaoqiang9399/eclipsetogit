package  app.component.sys.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import app.base.ServiceException;
import app.component.sys.entity.MfSysCompanyMst;
import app.util.toolkit.Ipage;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

/**
* Title: MfSysCompanyMstBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Wed May 03 15:16:02 CST 2017
**/
@FeignClient("mftcc-platform-factor")
public interface CompanyFileFeign {
	/**
	 * 保存上传图片并返回base64字符
	 * @param upload
	 * @param fileType
	 * @param flag
	 * @return
	 * @throws Exception
	 */
//	@RequestMapping(value = "/mfSysCompanyMst/updateImg",method = RequestMethod.POST,
//			 produces = {MediaType.APPLICATION_JSON_UTF8_VALUE},
//			   consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@RequestLine("POST /mfSysCompanyMst/updateImg")
	@Headers("Content-Type: multipart/form-data")
	public String updateImg(@Param("upload") MultipartFile upload, @Param("fileType") String fileType, @Param("flag")String flag, @Param("regNo")String regNo, @Param("comNo")String comNo) throws Exception;
	
}
