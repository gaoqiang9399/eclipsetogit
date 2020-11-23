package app.component.thirdserviceinterface;

import java.io.File;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by dark on 2017/9/5.
 */
@FeignClient("mftcc-platform-factor")
public interface LinkFaceInterfaceFeign {
	
    /**
     *活体识别并识别是否是本人
     * @param CusParamMap
     *      cusNo 客户号
     *      cusName 客户姓名
     *      idCard 客户身份证
     * @param uploadFile
     * @param fileName
     * @param fileType
     * @return
     * @throws Exception
     */
	@RequestMapping(value = "/linkFaceInterface/isRealInPerson")
    Map<String,String > isRealInPerson(@RequestBody Map<String, String> CusParamMap,@RequestParam("uploadFile") File uploadFile,@RequestParam("fileName") String fileName,@RequestParam("fileType") String fileType) throws Exception;
}
