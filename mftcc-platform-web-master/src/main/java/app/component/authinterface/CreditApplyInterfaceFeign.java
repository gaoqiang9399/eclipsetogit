package app.component.authinterface;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.app.entity.MfBusApply;
import app.component.auth.entity.MfCusCreditAdjustApply;
import app.component.auth.entity.MfCusCreditApply;
import app.component.auth.entity.MfCusCreditModel;
import app.component.auth.entity.MfCusPorductCredit;

/**
 * <p>Title:CreditApplyInterface.java</p>
 * <p>Description:授信申请对外接口</p>
 * @author LJW
 * @date 2017-3-3 下午5:29:00
 */
@FeignClient("mftcc-platform-factor")
public interface CreditApplyInterfaceFeign {

	/**
	 * 根据客户编号和最后修改时间降序排序查询授信申请最新数据
	 * @author LJW
	 * date 2017-3-3
	 */
	@RequestMapping(value = "/creditApplyInterface/getByCusNoAndOrederFirst")
	public MfCusCreditApply getByCusNoAndOrederFirst(@RequestBody MfCusCreditApply mfCusCreditApply)throws Exception;

	/**
	 * 根据客户编号和产品编号查询客户最新授信信息和产品授信信息
	 * @author LCL
	 * date 2017-6-18
	 */
	@RequestMapping(value = "/creditApplyInterface/getByCusNoAndKindNo")
	public Map<String, Object> getByCusNoAndKindNo(@RequestParam("cusNo") String cusNo, @RequestParam("kindNo") String kindNo) throws Exception;

	/**
	 * 根据授信申请id更新授信可用额度
	 * @author LJW
	 * date 2017-3-14
	 */
	@RequestMapping(value = "/creditApplyInterface/updateByCreditAppId")
	public void updateByCreditAppId(@RequestBody MfCusCreditApply mfCusCreditApply)throws Exception;
	/**
	 * 
	 * 方法描述： 按条件获得授信模型数据
	 * @return
	 * @throws Exception
	 * List<MfCusCreditModel>
	 * @author 沈浩兵
	 * @date 2017-3-22 上午9:43:26
	 */
	@RequestMapping(value = "/creditApplyInterface/getMfCusCreditModelList")
	public List<MfCusCreditModel> getMfCusCreditModelList(@RequestBody MfCusCreditModel mfCusCreditModel) throws Exception;
	
	/**
	 * 根据客户编号查询授信模型
	 * @author LJW
	 * date 2017-3-31
	 */
	@RequestMapping(value = "/creditApplyInterface/getByCusTypeNo")
	public MfCusCreditModel getByCusTypeNo(@RequestBody MfCusCreditModel mfCusCreditModel) throws Exception;
	/**
	 * 
	 * 方法描述： 获得授信申请信息
	 * @param cusCreditApply
	 * @return
	 * @throws Exception
	 * MfCusCreditApply
	 * @author 沈浩兵
	 * @date 2017-4-18 下午3:33:27
	 */
	@RequestMapping(value = "/creditApplyInterface/getCusCreditApply")
	public MfCusCreditApply getCusCreditApply(@RequestBody MfCusCreditApply cusCreditApply) throws Exception;
	/**
	 * 
	 * 方法描述： 获得授信调整历史
	 * @param mfCusCreditAdjustApply
	 * @return
	 * @throws Exception
	 * List<MfCusCreditAdjustApply>
	 * @author 沈浩兵
	 * @date 2017-6-25 上午10:26:03
	 */
	@RequestMapping(value = "/creditApplyInterface/getCreditAdjustApplyList")
	public List<MfCusCreditAdjustApply> getCreditAdjustApplyList(@RequestBody MfCusCreditAdjustApply mfCusCreditAdjustApply) throws Exception;
	/**
	 * 
	 * 方法描述： 获得授信相关参数
	 * @param cusCreditApply
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author 沈浩兵
	 * @date 2017-6-25 上午10:41:10
	 */
	@RequestMapping(value = "/creditApplyInterface/getCreditParmMap")
	public Map<String,Object> getCreditParmMap(@RequestBody MfCusCreditApply cusCreditApply) throws Exception;
	/**
	 * 插入申请授信信息并开启流程
	 * @param mfCusCreditApply
	 * @param dataMap
	 * @return
	 * @throws ServiceException
	 * @author MaHao
	 */
	@RequestMapping(value = "/creditApplyInterface/insertStartProcess")
	public MfCusCreditApply insertStartProcess(@RequestBody MfCusCreditApply mfCusCreditApply,
			@RequestParam("dataMap")Map<String, Object> dataMap) throws ServiceException;
	/**
	 * 根据id删除授信
	 * @param mfCusCreditApply
	 * @throws ServiceException
	 * @author MaHao
	 */
	@RequestMapping(value = "/creditApplyInterface/delete")
	public void delete(@RequestBody MfCusCreditApply mfCusCreditApply) throws ServiceException;
	/**
	 * 初始化发起授信数据， 验证客户是否授信过,如果有过授信，当前日期是否在授信期限内，如果在授信期限内，返回使用的授信调整表单 如果从未授信过，返回授信新增页面。
	 * @param mfCusCreditApply
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/creditApplyInterface/initCusCreditedInput")
	public Map<String, Object> initCusCreditedInput(@RequestBody MfCusCreditApply mfCusCreditApply) throws ServiceException;
	/**
	 * 
	 * 方法描述： 根据wkfAppId获得授信审批流程编号
	 * @param wkfAppId
	 * @return
	 * @throws Exception
	 * String
	 * @author 沈浩兵
	 * @date 2017-8-29 下午6:45:57
	 */
	@RequestMapping(value = "/creditApplyInterface/getCreditProcessId")
	public String getCreditProcessId(@RequestBody String wkfAppId) throws Exception;
	/**
	 * 
	 * 方法描述： 根据业务编号获得项目授信信息和客户授信信息
	 * @param appId
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author 沈浩兵
	 * @date 2018年4月15日 上午10:39:54
	 */
	@RequestMapping(value = "/creditApplyInterface/getCreditDataByAppId")
	public Map<String, Object> getCreditDataByAppId(@RequestParam("appId") String appId) throws Exception;

	/**
	 * 根据客户编号、产品编号和基地类型查询客户最新授信信息和产品授信信息
	 * 
	 * @author HGX date 2018-6-19
	 */
	@RequestMapping(value = "/creditApplyInterface/getByCusNoKindNoAndBaseType")
	public MfCusPorductCredit getByCusNoKindNoAndBaseType(@RequestBody Map<String, Object> parmMap) throws Exception;
	/**
	 * 方法描述： 
	 * @param appNo
	 * @return
	 * @throws Exception
	 * String
	 * @author YuShuai
	 * @date 2018年6月28日 下午3:25:39
	 */
	@RequestMapping(value = "/creditApplyInterface/getCreditPactProcessId")
	public String getCreditPactProcessId(String appNo)throws Exception;
	@RequestMapping(value = "/creditApplyInterface/getCreditApplyId")
	public String getCreditApplyId(String appNo)throws Exception;
	@RequestMapping(value = "/creditApplyInterface/getIfRepeatPact")
	public String getIfRepeatPact(String appNo)throws Exception;
	/**
	 * 方法描述：
	 * @param appNo
	 * @return
	 * @throws Exception
	 * String
	 * @author YuShuai
	 * @date 2018年6月28日 下午3:25:39
	 */
	@RequestMapping(value = "/creditApplyInterface/getCreditPrimaryProcessId")
	public String getCreditPrimaryProcessId(@RequestParam(name="wkfAppId") String appNo)throws Exception;
	
	/**
	 * 方法描述： 授信检查
	 * @param mfBusApply
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author 仇招
	 * @param baseType 
	 * @param cusNoFund 
	 * @date 2018年8月6日 上午10:04:36
	 */
	@RequestMapping(value = "/creditApplyInterface/checkCredit")
	public Map<String, Object> checkCredit(@RequestBody MfBusApply mfBusApply,@RequestParam("baseType") String baseType,@RequestParam("cusNo") String cusNo)throws Exception;

	/**
	 * 方法描述： 授信额度占用
	 * @param mfBusApply
	 * @param cusBaseType
	 * @param cusNo
	 * @param nodeNo
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author 仇招
	 * @date 2018年8月10日 下午7:53:07
	 */
	@RequestMapping(value = "/creditApplyInterface/creditTakedUp")
	public Map<String, Object> creditTakedUp(@RequestBody MfBusApply mfBusApply,@RequestParam("cusBaseType") String cusBaseType,@RequestParam("cusNo") String cusNo,@RequestParam("nodeNo") String nodeNo)throws Exception;
    @RequestMapping(value = "/creditApplyInterface/getCreditPactPrimaryProcessId")
    public String getCreditPactPrimaryProcessId(@RequestParam("appNo") String appNo)throws Exception;

    /**
     * @Description 根据登记人编号获取其下面授信数据条数
     * @Author zhaomingguang
     * @DateTime 2019/10/28 14:15
     * @Param 
     * @return 
     */
	@RequestMapping(value = "/creditApplyInterface/getCreditCountByCusMngNo")
	int getCreditCountByCusMngNo(MfCusCreditApply mfCusCreditApply)throws Exception;

	/**
	 * @Description 查询客户企业内部授信总额
	 * @Param
	 * @return
	 */
	@RequestMapping(value = "/creditApplyInterface/getCreditFincAmtSum")
	public Double getCreditFincAmtSum(@RequestParam("cusNo") String cusNo)throws Exception;
}
