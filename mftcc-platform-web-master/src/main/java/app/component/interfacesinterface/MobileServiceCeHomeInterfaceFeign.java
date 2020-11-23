package app.component.interfacesinterface;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 类名： CeHomeServiceInterface 描述：铁甲网接口
 * 
 * @author YuShuai
 * @date 2017-12-12 下午3:03:10
 * 
 *
 */
@FeignClient("mftcc-platform-factor")
public interface MobileServiceCeHomeInterfaceFeign {

	/**
	 * 方法描述： 借款人及配偶信息
	 * 
	 * @param inputdata
	 * @return Map<String,Object>
	 * @author YuShuai
	 * @date 2017-12-12 下午3:03:19
	 */
	@RequestMapping(value = "/mobileServiceCeHomeInterface/delCusInfo")
	public Map<String, Object> delCusInfo(@RequestBody String inputdata, @RequestParam("flag") String flag)
			throws Exception;

	/**
	 * 方法描述： 请求时插入记录
	 * 
	 * @param map
	 * @throws Exception
	 *             void
	 * @author YuShuai
	 * @date 2017-12-19 上午11:44:51
	 */
	@RequestMapping(value = "/mobileServiceCeHomeInterface/insertBusRecord")
	public void insertBusRecord(@RequestBody Map<String, Object> inputMap) throws Exception;

	/**
	 * 方法描述：返回插入记录
	 * 
	 * @param map
	 * @throws Exception
	 *             void
	 * @author YuShuai
	 * @date 2017-12-19 上午11:44:51
	 */
	@RequestMapping(value = "/mobileServiceCeHomeInterface/insertReturnRecord")
	public void insertReturnRecord(@RequestBody Map<String, Object> inputMap) throws Exception;

	/**
	 * 方法描述： 交易设备及融资信息
	 * 
	 * @param inputdata
	 * @param flag
	 * @return
	 * @throws Exception
	 *             Map<String,Object>
	 * @author YuShuai
	 * @date 2017-12-19 下午4:00:38
	 */
	@RequestMapping(value = "/mobileServiceCeHomeInterface/delApplyInfo")
	public Map<String, Object> delApplyInfo(@RequestBody String inputdata, @RequestParam("flag") String flag)
			throws Exception;

	/**
	 * 方法描述： 保证人信息
	 * 
	 * @param inputdata
	 * @param flag
	 * @return
	 * @throws Exception
	 *             Map<String,Object>
	 * @author YuShuai
	 * @date 2017-12-20 上午9:43:59
	 */
	@RequestMapping(value = "/mobileServiceCeHomeInterface/delAssureInfo")
	public Map<String, Object> delAssureInfo(@RequestBody String inputdata, @RequestParam("flag") String flag)
			throws Exception;

	/**
	 * 方法描述： 联系人信息
	 * 
	 * @param inputdata
	 * @param flag
	 * @return
	 * @throws Exception
	 *             Map<String,Object>
	 * @author YuShuai
	 * @date 2017-12-20 下午3:20:33
	 */
	@RequestMapping(value = "/mobileServiceCeHomeInterface/delCusFamilyInfo")
	public Map<String, Object> delCusFamilyInfo(@RequestBody String inputdata, @RequestParam("flag") String flag)
			throws Exception;

	/**
	 * 方法描述： 获取产品信息
	 * 
	 * @param inputdata
	 * @return
	 * @throws Exception
	 *             List<Map<String,Object>>
	 * @author YuShuai
	 * @date 2017-12-20 下午3:53:58
	 */
	@RequestMapping(value = "/mobileServiceCeHomeInterface/getMfKindList")
	public Map<String, Object> getMfKindList(@RequestBody String inputdata) throws Exception;

	/**
	 * 方法描述： 提交数据
	 * 
	 * @param inputdata
	 * @return
	 * @throws Exception
	 *             Map<String,Object>
	 * @author YuShuai
	 * @date 2017-12-20 下午4:46:26
	 */
	@RequestMapping(value = "/mobileServiceCeHomeInterface/submitAppInfo")
	public Map<String, Object> submitAppInfo(@RequestBody String inputdata,@RequestParam("regNo") String regNo, @RequestParam("regName") String regName, @RequestParam("orgNo") String orgNo) throws Exception;

	/**
	 * 方法描述： 查询数据状态
	 * 
	 * @param inputdata
	 * @return
	 * @throws Exception
	 *             Map<String,Object>
	 * @author YuShuai
	 * @date 2017-12-21 上午9:23:35
	 */
	@RequestMapping(value = "/mobileServiceCeHomeInterface/delQuerySts")
	public Map<String, Object> delQuerySts(@RequestBody String inputdata) throws Exception;

	/**
	 * 方法描述： APP、公众号等客户申请新增、修改
	 * 
	 * @param inputdata
	 * @param flag
	 * @return
	 * @throws Exception
	 *             Map<String,Object>
	 * @author YuShuai
	 * @date 2017-12-21 下午1:53:36
	 */
	@RequestMapping(value = "/mobileServiceCeHomeInterface/delAppReg")
	public Map<String, Object> delAppReg(@RequestBody String inputdata, @RequestParam("flag") String flag)
			throws Exception;

	/**
	 * 方法描述： 上传要件
	 * 
	 * @param inputdata
	 * @param upload
	 * @return
	 * @throws Exception
	 *             Map<String,Object>
	 * @author YuShuai
	 * @date 2017-12-21 下午4:14:21
	 */
	@RequestMapping(value = "/mobileServiceCeHomeInterface/uploadFile")
	public Map<String, Object> uploadFile(@RequestBody String inputdata,
			@RequestParam("upload") List<Map<String, Object>> upload) throws Exception;

	public Map<String, Object> insertDocManageForBusiness(@RequestBody String cusNo,
			@RequestParam("relNo") String relNo, @RequestParam("scNo") String scNo,
			@RequestParam("uploadFileName") String uploadFileName,
			@RequestParam("uploadContentType") String uploadContentType, @RequestParam("upload") File upload,
			@RequestParam("opNo") String opNo, @RequestParam("docSplitNo") String docSplitNo) throws Exception;

	/**
	 * 方法描述： 否决app 公众号客户信息
	 * 
	 * @param inputdata
	 * @return
	 * @throws Exception
	 *             Map<String,Object>
	 * @author YuShuai
	 * @date 2018-1-17 下午8:01:27
	 */
	@RequestMapping(value = "/mobileServiceCeHomeInterface/delAppRegRefuse")
	public Map<String, Object> delAppRegRefuse(@RequestBody String inputdata) throws Exception;

}
