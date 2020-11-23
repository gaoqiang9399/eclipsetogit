package app.component.tools.charge.feign;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.tools.charge.entity.AllCloudService;
import app.component.tools.charge.entity.BasicCifAccount;
import app.component.tools.charge.entity.DxUsedView;
import app.component.tools.charge.entity.ServiceCloudOpened;
import app.component.tools.charge.entity.ServiceCloudUsed;
import app.component.tools.charge.entity.SfUsedView;
import app.util.toolkit.Ipage;

@FeignClient("mftcc-platform-factor")
public interface AccountPayFeign {
		/**
		 * 
		 * 方法描述：获取公司基本信息
		 * 
		 * @return Map<String,Object>
		 * @author 张耀忠
		 * @param cifAccount 
		 * @throws Exception 
		 * @date 2016-12-16 下午3:41:15
		 */
	@RequestMapping(value = "/accountPay/getManageCloudJson")
		public Map<String, Object> getManageCloudJson(@RequestBody String cifAccount) throws Exception;
		/**
		 * 
		 * 方法描述：查询公司已开通服务
		 * 
		 * @return String
		 * @author 张耀忠
		 * @throws Exception 
		 * @date 2016-12-21 下午4:05:17
		 */
	@RequestMapping(value = "/accountPay/getServiceCloudJson")
		public Ipage getServiceCloudJson(@RequestBody Ipage ipg,@RequestParam("cifAccount") String cifAccount) throws Exception;
		/**
		 * 
		 * 方法描述： 使用一览查询
		 * 
		 * @param param
		 * @return String
		 * @author 张耀忠
		 * @throws Exception 
		 * @date 2016-12-21 下午6:42:42
		 */
	@RequestMapping(value = "/accountPay/getUseCloudJson")
		public Ipage getUseCloudJson(@RequestBody Ipage ipg,@RequestParam("cifAccount") String cifAccount,@RequestParam("licenseNo") String licenseNo) throws Exception;
		/**
		 * 
		 * 方法描述： checkbox数据写入
		 * @param traceNo
		 * @param passWord
		 * @param cifAccount
		 * @return
		 * String
		 * @author 张耀忠
		 * @throws Exception 
		 * @date 2016-12-29 下午2:46:45
		 */
	@RequestMapping(value = "/accountPay/getServiceCheck")
		public String getServiceCheck(@RequestBody String cifAccount) throws Exception;
		/**
		 * 
		 * 方法描述：连接云平台方法
		 * 
		 * @param parmMap
		 * @param url
		 * @return
		 * @throws Exception
		 *             String
		 * @author 张耀忠
		 * @date 2016-12-16 下午3:41:50
		 */
//	@RequestMapping(value = "/accountPay/getCloudJson")
//		private static String getCloudJson(@RequestBody String parmMap,@RequestParam("url")  String url) throws Exception;
		/**
		 * 
		 * 方法描述：发票信息修改
		 * 
		 * @param param
		 * @return
		 * @throws Exception
		 *             Map<String,Object>
		 * @author 张耀忠
		 * @date 2016-12-16 下午3:45:58
		 */
	@RequestMapping(value = "/accountPay/sendBillInfo")
		public Map<String, Object> sendBillInfo(@RequestBody String param) throws Exception;

		/**
		 * 
		 * 方法描述： 充值保存功能
		 * 
		 * @param param
		 * @return Map<String,Object>
		 * @author 张耀忠
		 * @throws Exception 
		 * @date 2016-12-19 上午9:49:50
		 */
	@RequestMapping(value = "/accountPay/sendPayInfo")
		public Map<String, Object> sendPayInfo(@RequestBody String param) throws Exception;

		/**
		 * 
		 * 方法描述：开通服务
		 * 
		 * @param param
		 * @return Map<String,Object>
		 * @author 张耀忠
		 * @throws Exception 
		 * @date 2016-12-19 上午10:44:21
		 */
	@RequestMapping(value = "/accountPay/openService")
		public Map<String, String> openService(@RequestBody String cifAccount,@RequestParam("itemNo") String itemNo,@RequestParam("cifSts") String cifSts,@RequestParam("smsSuffix") String smsSuffix) throws Exception;

		/**
		 * 
		 * 方法描述：获取所有服务项
		 * 
		 * @param param
		 * @return Map<String,Object>
		 * @author 张耀忠
		 * @throws Exception 
		 * @date 2016-12-19 上午10:49:16
		 */
	@RequestMapping(value = "/accountPay/getAllService")
		public Ipage getAllService(@RequestBody Ipage ipage,@RequestParam("used") AllCloudService used,@RequestParam("cifAccount") String cifAccount) throws Exception ;

		/**
		 * 
		 * 方法描述： 短信使用记录查询
		 * 
		 * @param param
		 * @return Map<String,Object>
		 * @author 张耀忠
		 * @param itemNo 
		 * @throws Exception 
		 * @date 2016-12-19 上午10:50:13
		 */
	@RequestMapping(value = "/accountPay/getDxUseRecordList")
		public Ipage getDxUseRecordList(@RequestBody Ipage ipage,@RequestParam("used") DxUsedView used,@RequestParam("cifAccount") String cifAccount,@RequestParam("itemNo")  String itemNo) throws Exception;
		/**
		 * 
		 * 方法描述： 已开通服务查询
		 * 
		 * @param param
		 * @return Map<String,Object>
		 * @author 张耀忠
		 * @throws Exception 
		 * @date 2016-12-19 上午10:58:43
		 */
	@RequestMapping(value = "/accountPay/getCifServiceItems")
		public Map<String, Object> getCifServiceItems(@RequestBody String param) throws Exception;
		/**
		 * 
		 * 方法描述： 从tomcat读取信息
		 * @param property 要读取文件名
		 * @param target   要读取文件属性
		 * @return
		 * @throws Exception
		 * String
		 * @author 张耀忠
		 * @date 2017-1-4 上午9:08:55
		 */
	@RequestMapping(value = "/accountPay/getTomcatPro")
		public String getTomcatPro(@RequestBody String property,@RequestParam("target") String target) throws Exception;
		/**
		 * 方法描述： 用户身份认证服务的记录查询
		 * @param ipage
		 * @param used
		 * @param cifAccount
		 * @param itemNo
		 * @return
		 */
	@RequestMapping(value = "/accountPay/getSfUseRecordList")
		public Ipage getSfUseRecordList(@RequestBody Ipage ipage, @RequestParam("used") SfUsedView used,
@RequestParam("cifAccount") 				String cifAccount,@RequestParam("itemNo")  String itemNo) throws Exception;
		/**
		 * 方法描述：判断此公司在云平台用户表中的状态
		 * @return
		 * @throws Exception
		 */
	@RequestMapping(value = "/accountPay/judgeCifByLicenseNo")
		public Map<String,String> judgeCifByLicenseNo() throws Exception;
		/**
		 * 方法描述：获得此公司的账户申请的审核信息
		 * @return
		 * @throws Exception
		 */
	@RequestMapping(value = "/accountPay/getAuditInfo")
		public BasicCifAccount getAuditInfo() throws Exception;
		/**
		 * 保存用户申请信息
		 * @param bc
		 * @param file1
		 * @param file1FileName
		 * @return
		 */
	@RequestMapping(value = "/accountPay/insertCifAccount")
		public String insertCifAccount(@RequestParam("bc")BasicCifAccount bc, @RequestParam("file1")File file1,
				@RequestParam("file1FileName")String file1FileName) throws Exception;
		/**
		 * 获取公司的营业执照图片base64格式
		 * @return
		 * @throws Exception
		 */
	@RequestMapping(value = "/accountPay/getBase64Img")
		public String getBase64Img() throws Exception;
		/**
		 * 获取安装码
		 * @return
		 */
	@RequestMapping(value = "/accountPay/getCodeInit")
		public String getCodeInit() throws Exception;
		/**
		 * 获取使用一览
		 * @param cifAccount
		 * @param licenseNo
		 * @return
		 * @throws Exception
		 */
	@RequestMapping(value = "/accountPay/getUseCloudJson")
		public List<ServiceCloudUsed> getUseCloudJson(@RequestParam("cifAccount")String cifAccount,
				@RequestParam("licenseNo")String licenseNo) throws Exception;
		/**
		 * 已开通服务
		 * @param cifAccount
		 * @return
		 * @throws Exception
		 */
	@RequestMapping(value = "/accountPay/getServiceCloudJson")
		public List<ServiceCloudOpened> getServiceCloudJson(@RequestBody String cifAccount) throws Exception;
}
