package app.component.doc.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.multipart.MultipartFile;

import app.component.doc.entity.DocManage;
import feign.Headers;
import feign.Param;
import feign.RequestLine;
@FeignClient("mftcc-platform-fileService")
public interface FileUtilFeign {
	
	@RequestLine("POST /docUpLoad/upLoadFile")
	@Headers("Content-Type: multipart/form-data")
	String uploadFileToService(@Param("relNo") String relNo, @Param("docType")String docType,@Param("upload") MultipartFile file,@Param("docBizNo") String docBizNo,
			@Param("scNo")String scNo, @Param("docSplitNo")String docSplitNo,@Param("cusNo") String cusNo,@Param("fileName") String fileName,@Param("docManage") DocManage docManage);
	
	

}
