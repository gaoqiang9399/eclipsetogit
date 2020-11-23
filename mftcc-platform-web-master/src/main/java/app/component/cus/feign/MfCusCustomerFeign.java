package  app.component.cus.feign;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.component.assure.entity.MfAssureInfo;
import app.component.cus.entity.*;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import app.util.toolkit.Ipage;

/**
* Title: MfCusCustomerBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Fri May 20 09:13:03 CST 2016
**/
@FeignClient("mftcc-platform-factor")
public interface MfCusCustomerFeign {
	
	@RequestMapping("/mfCusCustomer/insert1")
	public MfCusCustomer insert1(@RequestBody MfCusCustomer mfCusCustomer) throws Exception;
	/**
	 * 
	 * 方法描述： 更新客户登记信息
	 * @param mfCusCustomer
	 * @return
	 * @throws Exception
	 * MfCusCustomer
	 * @author 沈浩兵
	 * @date 2016-12-2 下午2:59:02
	 */
	@RequestMapping("/mfCusCustomer/updateCustomter")
	public MfCusCustomer updateCustomter(@RequestBody Map<String,Object> parmMap) throws Exception;
	
	@RequestMapping("/mfCusCustomer/delete")
	public void delete(@RequestBody MfCusCustomer mfCusCustomer) throws Exception;
	
	@RequestMapping("/mfCusCustomer/update")
	public void update(@RequestBody MfCusCustomer mfCusCustomer) throws Exception;
	
	@RequestMapping("/mfCusCustomer/getById")
	public MfCusCustomer getById(@RequestBody MfCusCustomer mfCusCustomer) throws Exception;
	
	@RequestMapping("/mfCusCustomer/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;

	@RequestMapping("/mfCusCustomer/findByBusPage")
	public Ipage findByBusPage(@RequestBody Ipage ipage) throws Exception;

	@RequestMapping("/mfCusCustomer/findByPageTmp")
	public List<MfCusCustomer> findByPageTmp(@RequestBody MfCusCustomer mfCusCustomer) throws Exception;
	
	@RequestMapping("/mfCusCustomer/findByPageIdNumNotNull")
	public List<MfCusCustomer> findByPageIdNumNotNull(@RequestBody MfCusCustomer mfCusCustomer) throws Exception;
	
	@RequestMapping("/mfCusCustomer/getByUserName")
	public MfCusCustomer getByUserName(@RequestBody MfCusCustomer mfCusCustomer)throws Exception;
	
	@RequestMapping("/mfCusCustomer/doCusRegister")
	public MfCusCustomer doCusRegister(@RequestBody MfCusCustomer mfCusCustomer)throws Exception;
	
	@RequestMapping("/mfCusCustomer/findCusByPact")
	public Ipage findCusByPact(@RequestBody Ipage ipage) throws Exception;
	/**
	 * 查询合作机构
	 * @param ipage
	 * @param mfBusPact
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/mfCusCustomer/findCoAgencyByPage")
	public Ipage findCoAgencyByPage(@RequestBody Ipage ipage) throws Exception;
	
	//(优质/黑名单)客户分类列表查询
	@RequestMapping("/mfCusCustomer/getCusAndClassifyPage")
	public Ipage getCusAndClassifyPage(@RequestBody Ipage ipage) throws Exception;

	@RequestMapping("/mfCusCustomer/getCusAndClassifyById")
	public CusAndClassify getCusAndClassifyById(@RequestBody CusAndClassify cusAndClassify) throws Exception;
	
	@RequestMapping("/mfCusCustomer/getCusAndClassifyByCusId")
	public List<CusAndClassify> getCusAndClassifyByCusId(@RequestBody CusAndClassify cusAndClassify) throws Exception;
	/**
	 * 发起授信业务申请时调用规则引擎检查
	 * @author LJW
	 * date 2017-3-13
	 */
	@RequestMapping("/mfCusCustomer/getCreditRules")
	public Map<String, Object> getCreditRules(@RequestBody Map<String, Object> dataMap) throws Exception;
	
	@RequestMapping("/mfCusCustomer/getByContactsTel")
	public MfCusCustomer getByContactsTel(@RequestBody MfCusCustomer mfCusCustomer);
	
	@RequestMapping("/mfCusCustomer/getByCusMngNo")
	public Ipage getByCusMngNo(@RequestBody Ipage ipage) throws Exception;

	@RequestMapping("/mfCusCustomer/findCusNumByMngNo")
	public int findCusNumByMngNo(@RequestBody MfCusCustomer mfCusCustomer) throws Exception;

	
	/**
	 * 
	 * @param mfCusCustomer
	 * @throws Exception
	 * @author mahao
	 * @date 2017-05-06
	 */
	@RequestMapping("/mfCusCustomer/getByIdNum")
	public List<MfCusCustomer> getByIdNum(@RequestBody MfCusCustomer mfCusCustomer)  throws Exception;
	
	/**
	 * 方法描述： 更新客户信息完整度 
	 * @param cusNo
	 * @return
	 * @throws Exception
	 * String
	 * @author lcl
	 * @date 2017-5-16 下午9:42:02
	 */
	@RequestMapping("/mfCusCustomer/updateInfIntegrity")
	public String  updateInfIntegrity(@RequestParam("cusNo") String  cusNo,@RequestParam("relNo") String relNo) throws Exception;
	/**
	 * 方法描述： 生成客户号
	 * @param cusType
	 * @return
	 * @throws Exception
	 * String
	 * @author YuShuai
	 * @date 2017-5-23 下午2:03:47
	 */
	@RequestMapping("/mfCusCustomer/getFormCusNo")
	public String getFormCusNo(@RequestParam("cusType") String cusType)throws Exception;
	/**
	 * 方法描述： 通过客户类型获取所有客户列表
	 * @param string
	 * @return
	 * @throws Exception
	 * List<Map<String,String>>
	 * @author YuShuai
	 * @date 2017-5-24 上午11:32:52
	 */
	@RequestMapping("/mfCusCustomer/getCusListByCusType")
	public List<Map<String, String>> getCusListByCusType(@RequestParam("string") String string)throws Exception;
	/**
	 * 方法描述： 查询客户（押品选择用）
	 * @return
	 * @throws Exception
	 */

	@RequestMapping("/mfCusCustomer/getAllCus")
	public List<MfCusCustomer> getAllCus(@RequestParam("cusNo") String cusNo) throws Exception;
	
	/**
	 * 方法描述： 获取共同借款人数据源（所有客户和社会关系）
	 * @param ipage
	 * @param cusNo
	 * @return
	 * @throws Exception
	 * Ipage
	 * @author YuShuai
	 * @date 2017-7-21 下午4:24:30
	 */
	@RequestMapping("/mfCusCustomer/getCobBoowerAjax")
	public Ipage getCobBoowerAjax(@RequestBody Ipage ipage)throws Exception;
	/**
	 * 查询企业客户
	 * @param ipage
	 * @param mfCusCustomer
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/mfCusCustomer/findCorpCustomerByPage")
	public Ipage findCorpCustomerByPage(@RequestBody Ipage ipage) throws Exception;
	
	/**
	 * 方法描述：获取社会关系数据 
	 * @param mfCusCustomer
	 * @return
	 * @throws Exception
	 * Page<MfCusCustomer>
	 * @author YuShuai
	 * @date 2017-8-21 下午7:37:18
	 */
	@RequestMapping("/mfCusCustomer/findOnlyCobByPage")
	public Ipage findOnlyCobByPage(@RequestBody MfCusCustomer mfCusCustomer)throws Exception;
	/**
	 * 方法描述： 获取社会关系和客户数据 
	 * @param mfCusCustomer
	 * @return
	 * @throws Exception
	 * Page<MfCusCustomer>
	 * @author YuShuai
	 * @date 2017-8-21 下午7:39:47
	 */
	@RequestMapping("/mfCusCustomer/findCobByPage")
	public Ipage findCobByPage(@RequestBody MfCusCustomer mfCusCustomer)throws Exception;
	/**
	 * 方法描述：  获取排除自己的所有客户（共同借款人所用）
	 * @param mfCusCustomer
	 * @return
	 * List<MfCusCustomer>
	 * @author YuShuai
	 * @date 2017-8-22 下午5:20:42
	 */
	@RequestMapping("/mfCusCustomer/getCusListForCob")
	public List<MfCusCustomer> getCusListForCob(@RequestBody MfCusCustomer mfCusCustomer)throws Exception;
	/**
	 * 删除列表中信息到为空时，用于更新客户表单配置和资料完整度。并返回新的资料完整度。
	 * @param cusNo
	 * @param relNo
	 * @param tableName 
	 * @return
	 */
	@RequestMapping("/mfCusCustomer/updateCusTableAndIntegrity")
	public String updateCusTableAndIntegrity(@RequestParam("cusNo") String cusNo,@RequestParam("relNo") String relNo,@RequestParam("tableName") String tableName);
	/**
	 * @Description:修改客户经理 
	 * @param mfCusCustomer
	 * @return
	 * @author: 李伟
	 * @date: 2017-9-21 下午5:29:06
	 */
	@RequestMapping("/mfCusCustomer/updateCusManage")
	public void updateCusManage(@RequestBody MfCusCustomer mfCusCustomer)  throws Exception;
	/**
	 * 
	 * 方法描述：获取客户反欺诈报告信息 
	 * @param parmMap
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author zhs
	 * @date 2017-9-29 下午3:54:00
	 */
	@RequestMapping("/mfCusCustomer/getCusAntiFraudReport")
	public Map<String, Object> getCusAntiFraudReport(@RequestBody Map<String, String> parmMap)throws Exception;
	/**
	 * 
	 * 方法描述： 
	 * @param parmMap
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author zhs
	 * @date 2017-9-29 下午8:33:48
	 */
	@RequestMapping("/mfCusCustomer/getReportHistory")
	public Map<String, Object> getReportHistory(@RequestBody Map<String, String> parmMap)throws Exception;
	/**
	 * 
	 * 方法描述： 获取反欺诈报告查询参数
	 * @param cusNo
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author zhs
	 * @date 2017-9-30 上午9:51:33
	 */
	@RequestMapping("/mfCusCustomer/getAntiFraudParam")
	public Map<String, Object> getAntiFraudParam(@RequestParam("cusNo") String cusNo)throws Exception;
	/**
	 * 
	 * 方法描述： 获取授权码
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author zhs
	 * @date 2017-9-30 上午11:30:19
	 */
	@RequestMapping("/mfCusCustomer/getEntityAuthCode")
	public Map<String, Object> getEntityAuthCode(@RequestBody Map<String, String> parmMap) throws Exception;
	/**
	 * 
	 * 根据客户微信账户标示查询客户信息
	 * @return
	 * @throws Exception
	 * MfCusCustomer
	 * @author ywh
	 * @date 2017-10-11 上午11:30:19
	 */
	@RequestMapping("/mfCusCustomer/getByOpenid")
	public MfCusCustomer getByOpenid(@RequestBody MfCusCustomer mfCusCustomer) throws Exception;
	/**
	 * 
	 * 根据客户手机号查询客户信息
	 * @return
	 * @throws Exception
	 * MfCusCustomer
	 * @author ywh
	 * @date 2017-10-12上午11:30:19
	 */
	@RequestMapping("/mfCusCustomer/getByCusTel")
	public MfCusCustomer getByCusTel(@RequestBody MfCusCustomer mfCusCustomer) throws Exception;
	/**
	 * 根据AppWXOpenid查询客户信息
	 * @param AppWXOpenid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/mfCusCustomer/getByAppWXOpenid")
	public MfCusCustomer getByAppWXOpenid(@RequestBody MfCusCustomer mfCusCustomer) throws Exception;
	/**
	 * 根据AppQQOpenid查询客户信息
	 * @param AppQQOpenid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/mfCusCustomer/getByAppQQOpenid")
	public MfCusCustomer getByAppQQOpenid(@RequestBody MfCusCustomer mfCusCustomer) throws Exception;
	/**
	 * app端插入客户
	 * @param mfCusCustomer
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/mfCusCustomer/insertForApp")
	public MfCusCustomer insertForApp(@RequestBody MfCusCustomer mfCusCustomer) throws Exception;
	/**
	 * 获取系统操作员名下当天客户数
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/mfCusCustomer/getOpNoList")
	public List<Map<String, String>> getOpNoList()throws Exception;
	/**
	 * 去客户号最大的那条数据
	 * @param cusCustomer
	 * @return
	 */
	@RequestMapping("/mfCusCustomer/getMfCusCustomerByCusNoMax")
	public String getMfCusCustomerByCusNoMax(@RequestBody MfCusCustomer cusCustomer)throws Exception;
	/**
	 * 刷新资料完整度
	 * @param cusCustomer
	 * @return
	 */
	@RequestMapping("/mfCusCustomer/getRefreshDataIntegrity")
	public String getRefreshDataIntegrity()throws Exception;
	/**
	 * app:更新客户表和客户基本信息表
	 * @param cusCustomer
	 * @return
	 */
	@RequestMapping("/mfCusCustomer/updateCustomerInfo")
	public void updateCustomerInfo(@RequestBody Map<String,Object> parmMap)throws Exception;
	/**
	 * 根据条件查询一个
	 * @param cusCustomer
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/mfCusCustomer/getOneByConditions")
	public MfCusCustomer getOneByConditions(@RequestBody MfCusCustomer cusCustomer)throws Exception;
	/**
	 * 
	 * 方法描述： 判断客户的表单是否允许编辑 
	 * @param cusNo
	 * @return
	 * @throws Exception
	 * String
	 * @author zhs
	 * @param busEntrance 
	 * @param appId 
	 * @date 2017-11-9 下午3:19:35
	 */
	@RequestMapping("/mfCusCustomer/validateCusFormModify")
	public String validateCusFormModify(@RequestParam("cusNo") String cusNo,@RequestParam("appId") String appId,@RequestParam("busEntrance") String busEntrance,@RequestParam("regNo") String regNo) throws Exception;

	/**
	 * 查询担保公司
	 * 
	 * @param mfCusCustomer
	 * @return
	 * @author WangChao
	 * @date 2017-11-9 下午3:36:51
	 */
	@RequestMapping("/mfCusCustomer/getAssureCompany")
	public Ipage getAssureCompany(@RequestBody Ipage ipage) throws Exception;
	
	/**
	 * 根据操作员编号获取今日此操作员下的潜在客户数量
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/mfCusCustomer/getCntByOpNo")
	public int getCntByOpNo(@RequestBody MfCusCustomer mfCusCustomer)throws Exception;
	
	/**
	 * 方法描述： 获取校验的表单
	 * @param list
	 * @param cusTableList
	 * @return
	 * @throws Exception
	 * List<MfCusTable>
	 * @author YuShuai
	 * @date 2017-11-25 下午5:41:09
	 */
	@RequestMapping("/mfCusCustomer/getCusKindTableList")
	public List<MfCusTable> getCusKindTableList(@RequestBody Map<String,Object> parmMap)throws Exception;
	
	/**
	 * 方法描述： 校验业务中配置的客户表单信息是否完整
	 * @throws Exception
	 * Map<String,Object>
	 * @author YuShuai
	 * @date 2017-11-26 下午4:48:21
	 */
	@RequestMapping("/mfCusCustomer/validateCusInfo")
	public Map<String, Object> validateCusInfo(@RequestBody Map<String,Object> parmMap)throws Exception;
	/**
	 * 
	 * 方法描述： 查询客户列表，主要是手机号，这个带有自定义筛选
	 * @param ipage
	 * @param mfCusCustomer
	 * @return
	 * @throws Exception
	 * Ipage
	 * @author lzshuai
	 * @date 2017-11-27 下午8:24:17
	 */
	@RequestMapping("/mfCusCustomer/mfCusCustomer")
	public Ipage findCusTelList(@RequestBody Ipage ipage) throws Exception;
	
	/**
   	 * 
   	 * 方法描述： B端插入客户登记信息
   	 * @param mfCusCustomer
   	 * @throws Exception
   	 * void
   	 * @author ywh
   	 * @date 2017-12-04 下午2:44:27
   	 */
	@RequestMapping("/mfCusCustomer/addCustomer")
   	public MfCusCustomer addCustomer(@RequestBody Map<String,Object> parmMap)  throws Exception;
   	/**
   	 * 
   	* @Title: findPadByPage  
   	* @Description: pad端获取客户数据（只获取没有审批通过的  分别显示进件中， 和 审批中）
   	* @param @param ipage
   	* @param @param mfCusCustomer
   	* @param @return
   	* @param @throws Exception    参数  
   	* @return Ipage    返回类型  
   	* @throws
   	 */
	@RequestMapping("/mfCusCustomer/findPadByPage")
   	Ipage findPadByPage(@RequestBody Ipage ipage) throws Exception;
   	/**
   	 * 
   	 * 方法描述： 获取客户列表
   	 * @param ipage
   	 * @param mfCusCustomer
   	 * @return
   	 * Ipage
   	 * @author lzshuai
   	 * @date 2017-12-28 下午7:22:35
   	 */
	@RequestMapping("/mfCusCustomer/getCusList")
	public Ipage getCusList(@RequestBody Ipage ipage) throws Exception; 

	/**
	 * 方法描述： 根据渠道相关信息客户列表
	 * 
	 * @param ipage
	 * @param mfCusCustomer
	 * @return
	 * @throws Exception Ipage
	 * @author 沈浩兵
	 * @date 2018-3-8 上午11:38:54
	 */
	@RequestMapping("/mfCusCustomer/findCusBytrenchPage")
	public Ipage findCusBytrenchPage(@RequestBody Ipage ipage) throws Exception;

	/**
	 * 方法描述： 判断客户的表单是否允许编辑
	 * 
	 * @param cusNo
	 * @return
	 * @throws Exception String
	 * @author zhs
	 * @param formEditFlag BizPubParm.FORM_EDIT_FLAG
	 * @param appId
	 * @date 2017-11-9 下午3:19:35
	 */
	@RequestMapping("/mfCusCustomer/validateCusFormModify")
	public String validateCusFormModify(@RequestParam("cusNo") String cusNo, @RequestParam("appId") String appId, @RequestParam("formEditFlag") String formEditFlag) throws Exception;
	
	@RequestMapping("/mfCusCustomer/findEntByPage")
	public Ipage findEntByPage(@RequestBody Ipage ipage) throws Exception;
	@RequestMapping("/mfCusCustomer/getcusInfoByTrenchPage")
	public Ipage getcusInfoByTrenchPage(@RequestBody Ipage ipage) throws Exception;
	/**
	 * 
	 * 方法描述： 查询共享客户列表
	 * @param ipage
	 * @return
	 * @throws Exception
	 * Ipage
	 * @author lwq
	 * @date 2018年6月6日 下午8:43:51
	 */
	@RequestMapping("/mfCusCustomer/findShareCusByPage")
	public Ipage findShareCusByPage(@RequestBody Ipage ipage) throws Exception;
	@RequestMapping("/mfCusCustomer/getCusNoByIdNum")
	public String getCusNoByIdNum(String idNum)throws Exception;
	/**
	 * 方法描述： 根据渠道编号查找有效客户列表
	 * @param ipage
	 * @return
	 * @throws Exception
	 * Ipage
	 * @author 仇招
	 * @date 2018年9月7日 上午9:41:46
	 */
	@RequestMapping("/mfCusCustomer/findByTrench")
	public Ipage findByTrench(@RequestBody Ipage ipage)throws Exception;
	/**
	 * 方法描述： 根据资金机构查找有效客户列表
	 * @param ipage
	 * @return
	 * @throws Exception
	 * Ipage
	 * @author 仇招
	 * @date 2018年9月7日 下午4:39:17
	 */
	@RequestMapping("/mfCusCustomer/findByAgencies")
	public Ipage findByAgencies(@RequestBody Ipage ipage)throws Exception;
	/**
	 * 方法描述： 查找所有个人和企业客户分页
	 * @param ipage
	 * @return
	 * @throws Exception
	 * Ipage
	 * @author 仇招
	 * @date 2018年9月8日 下午6:22:33
	 */
	@RequestMapping("/mfCusCustomer/findPerAndCoreByPage")
	public Ipage findPerAndCoreByPage(@RequestBody Ipage ipage)throws Exception;
	/**
	 * 方法描述： 查找所有个人和企业客户分页
	 * @param ipage
	 * @return
	 * @throws Exception
	 * Ipage
	 * @author 仇招
	 * @date 2018年9月8日 下午6:22:33
	 */
	@RequestMapping("/mfCusCustomer/findPerAndCoreHaveLoanByPage")
	public Ipage findPerAndCoreHaveLoanByPage(@RequestBody Ipage ipage)throws Exception;
	/**
	 * 方法描述： 查找所有逾期个人和企业客户分页
	 * @param ipage
	 * @return
	 * @throws Exception
	 * Ipage
	 * @author 仇招
	 * @date 2018年9月8日 下午6:22:33
	 */
	@RequestMapping("/mfCusCustomer/findOverPerAndCoreByPage")
	public Ipage findOverPerAndCoreByPage(@RequestBody Ipage ipage)throws Exception;

	/**
	*@desc 校验授信模块的客户资料编辑权限
	*@author lwq        
	*@date 2018/9/30 10:42
	*@parm [cusNo, appId, busEntrance, regNo]
	*@parm type 1申请 2合同
	*@return java.lang.String
	**/
	@RequestMapping("/mfCusCustomer/validateCreditCusFormModify")
	public String validateCreditCusFormModify(@RequestParam("cusNo") String cusNo,@RequestParam("creditAppId") String creditAppId,@RequestParam("busEntrance") String busEntrance,@RequestParam("regNo") String regNo,@RequestParam("type") String type) throws Exception;

	@RequestMapping("/mfCusCustomer/GetNotCusInfo")
	public Ipage GetNotCusInfo(@RequestBody Ipage ipage) throws Exception;

	/**
	 * 根据多客户号查询
	 * @param ipage
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/mfCusCustomer/getCusListByCusNos")
	public Ipage getCusListByCusNos(@RequestBody Ipage ipage) throws Exception;

	@RequestMapping("/mfCusCustomer/getNotCussilpsList")
	public List<Map<String,Object>> getNotCussilpsList(@RequestBody MfCusCustomer mfCusCustomer) throws Exception;

	@RequestMapping("/mfCusCustomer/getAssureListByAppid")
	public List<MfAssureInfo> getAssureListByAppid(@RequestBody String appId) throws Exception;

    @RequestMapping(value="/mfCusCustomer/checkSave")
    public HashMap<String,Object> checkSave(@RequestBody Map<String,Object> parmMap) throws Exception;
	@RequestMapping(value="/mfCusCustomer/insetCustomer_new")
    Map<String,Object> insetCustomer_new(@RequestBody Map<String,Object> parmMap) throws Exception;

	@RequestMapping(value="/mfCusCustomer/getMfCusNetworkConfig")
	MfCusNetworkConfig getMfCusNetworkConfig(@RequestBody MfCusNetworkConfig mfCusNetworkConfig)throws Exception;

	@RequestMapping(value="/mfCusCustomer/checkCusNetwork")
	Map<String,Object> checkCusNetwork(@RequestBody Map<String,Object> parmMap)throws Exception;
}

