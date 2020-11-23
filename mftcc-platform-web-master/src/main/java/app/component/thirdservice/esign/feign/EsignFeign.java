package app.component.thirdservice.esign.feign;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/** 
 * e签宝签章接口
 * @author 徐明伟
 * @datetime 2017-11-25 下午3:48:44
 * @editnote 
 *	
 */
@FeignClient("mftcc-platform-factor")
public interface EsignFeign {
	/**
	 * 个人签章手绘记录
	 *
	 * @param paramMap cus_no(客户编号),fileNameOrig(待签章文件名称 ),sealData(印章数据base64字符串)
	 * @return
	 * @throws Exception
	 * @author 徐明伟
	 * @datetime 2017-11-29 下午6:22:47
	 * @editnote 
	 *
	 */
	@RequestMapping(value = "/esign/doCusSignSealDrawRecord")
	Map<String, Object> doCusSignSealDrawRecord(@RequestBody Map<String, Object> paramMap) throws Exception; 
	
	/**
	 * 电子签章
	 * 
	 * @param paramMap srcPdfFile(待签pdf文件的路径),signedFileName(待签署的PDF文件名称),platformKeyWord(平台关键字),list集合：存放的是personMap对应的是签署人的信息---cusKeyWord(个人关键字),cusNo(客户账号编号),cusName(客户姓名),idNum(身份证/护照)
	 * @return Map<String Object> flag成功(success)或失败(error)的标识，signedFolder签章成功存放的路径,msg提示信息
	 * @throws Exception
	 * @author 徐明伟
	 * @datetime 2017-11-27 下午3:40:03
	 * @editnote 
	 *
	 */
	@RequestMapping(value = "/esign/doSignWithSealByStream")
	Map<String, Object> doSignWithSealByStream(@RequestBody Map<String, Object> paramMap) throws Exception;
	/**
	 * 电子手绘签章
	 *
	 * @param paramMap srcPdfFile(待签pdf文件的路径),signedFileName(待签署的PDF文件名称),platformKeyWord(平台关键字),list集合：存放的是personMap对应的是签署人的信息---cusKeyWord(个人关键字),cusNo(客户账号编号),cusName(客户姓名),idNum(身份证/护照)
	 * @return
	 * @throws Exception
	 * @author 徐明伟
	 * @datetime 2017-12-21 下午3:40:18
	 * @editnote 
	 *
	 */
	@RequestMapping(value = "/esign/doSignWithImageSealByStream")
	Map<String,Object> doSignWithImageSealByStream(@RequestBody Map<String, Object> paramMap) throws Exception;
}
