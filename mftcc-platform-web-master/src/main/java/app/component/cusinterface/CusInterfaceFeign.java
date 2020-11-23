package app.component.cusinterface;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.app.entity.MfBusAssureDetail;
import app.component.cus.chainrelation.entity.MfCusChainRelation;
import app.component.cus.creditquery.entity.MfCreditQueryRecordInfo;
import app.component.cus.cuslevel.entity.MfCusClassify;
import app.component.cus.custracing.entity.MfCusTrack;
import app.component.cus.entity.BankIdentify;
import app.component.cus.entity.MfBusAgencies;
import app.component.cus.entity.MfBusTrench;
import app.component.cus.entity.MfCusApplyPersonSurvey;
import app.component.cus.entity.MfCusApplySpouseSurvey;
import app.component.cus.entity.MfCusAssets;
import app.component.cus.entity.MfCusAssureCompany;
import app.component.cus.entity.MfCusBankAccManage;
import app.component.cus.entity.MfCusCorpBaseInfo;
import app.component.cus.entity.MfCusCustomer;
import app.component.cus.entity.MfCusEquityInfo;
import app.component.cus.entity.MfCusFamilyInfo;
import app.component.cus.entity.MfCusFarmerEconoInfo;
import app.component.cus.entity.MfCusFarmerIncExpe;
import app.component.cus.entity.MfCusFormConfig;
import app.component.cus.entity.MfCusGuaranteeOuter;
import app.component.cus.entity.MfCusHighInfo;
import app.component.cus.entity.MfCusLegalEmployInfo;
import app.component.cus.entity.MfCusLegalEquityInfo;
import app.component.cus.entity.MfCusPersBaseInfo;
import app.component.cus.entity.MfCusPersonAssetsInfo;
import app.component.cus.entity.MfCusPersonCorp;
import app.component.cus.entity.MfCusPersonCreditInfo;
import app.component.cus.entity.MfCusPersonDebtInfo;
import app.component.cus.entity.MfCusPersonFlowAssetsInfo;
import app.component.cus.entity.MfCusPersonIncExpe;
import app.component.cus.entity.MfCusPersonIncomeSurvey;
import app.component.cus.entity.MfCusPersonJob;
import app.component.cus.entity.MfCusShareholder;
import app.component.cus.entity.MfCusStaff;
import app.component.cus.entity.MfCusTable;
import app.component.cus.entity.MfCusType;
import app.component.cus.entity.MfUniqueVal;
import app.component.cus.identitycheck.entity.MfIdentityCheckRecordInfo;
import app.component.cus.relation.entity.Child;
import app.component.interfaces.mobileinterface.entity.WebCusLineReg;
import app.util.toolkit.Ipage;
import net.sf.json.JSONArray;



/**
 * Title: cusInterface.java Description:
 * 
 * @author:LiuYF@dhcc.com.cn
 * @Mon May 16 20:45:38 CST 2016
 **/
@FeignClient("mftcc-platform-factor")
public interface CusInterfaceFeign {
	/**
	 * 根据客户编号查询客户信息
	 * 
	 * @param mfCusCustomer
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/cusInterface/getMfCusCustomerById")
	public MfCusCustomer getMfCusCustomerById(@RequestBody MfCusCustomer mfCusCustomer) throws Exception;

	/**
	 * 根据客户编号查询客户信息
	 * 
	 * @param mfCusCustomer
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/cusInterface/getByContactsTel")
	public MfCusCustomer getByContactsTel(@RequestParam("contactsTel") String mfCusCustomer) throws Exception;

	/**
	 * 根据客户证件号码查询客户信息 可以用来判断证件是否重复
	 * 
	 * @param idNum
	 * @throws Exception
	 * @author MaHao
	 */
	@RequestMapping(value = "/cusInterface/getByIdNum")
	public List<MfCusCustomer> getByIdNum(@RequestParam("idNum") String idNum) throws Exception;

	@RequestMapping(value = "/cusInterface/getCusByCusNo")
	public MfCusCustomer getCusByCusNo(@RequestParam("cusNo") String cusNo) throws Exception;
	
	/**
	 * 获取客户信息和客户所属区域信息
	 * @param cusNo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/cusInterface/getCusAndCareaCityByCusNo")
	public MfCusCustomer getCusAndCareaCityByCusNo(@RequestParam("cusNo") String cusNo) throws Exception;
	/**
	 * 
	 * 方法描述： 获得对公客户基本信息
	 * 
	 * @return
	 * @throws Exception
	 *             MfCusCorpBaseInfo
	 * @author 沈浩兵
	 * @date 2016-11-3 上午11:25:03
	 */
	@RequestMapping(value = "/cusInterface/getCusCorpByCusNo")
	public MfCusCorpBaseInfo getCusCorpByCusNo(@RequestParam("cusNo") String cusNo) throws Exception;

	/**
	 * 
	 * 方法描述： 获得对公客户基本信息
	 * 
	 * @param cusNo
	 * @return
	 * @throws Exception
	 *             MfCusCorpBaseInfo
	 * @author 沈浩兵
	 * @date 2016-12-30 下午4:37:46
	 */
	@RequestMapping(value = "/cusInterface/getMfCusCorpBaseInfoByCusNo")
	public MfCusCorpBaseInfo getMfCusCorpBaseInfoByCusNo(@RequestParam("cusNo") String cusNo) throws Exception;



	/**
	 * 
	 * 方法描述：获得公司员工信息
	 * 
	 * @param cusNo
	 * @return
	 * @throws Exception
	 *             MfCusCorpBaseInfo
	 * @author 沈浩兵
	 * @date 2016-12-30 下午4:41:07
	 */
	@RequestMapping(value = "/cusInterface/getMfCusStaffByCusNo")
	public MfCusStaff getMfCusStaffByCusNo(@RequestParam("cusNo") String cusNo) throws Exception;

	/**
	 * 
	 * 方法描述： 查询客户中是否使用某个评级场景
	 * 
	 * @param mfCusCorpBaseInfo
	 * @return
	 * @throws Exception
	 *             List<MfCusCorpBaseInfo>
	 * @author 沈浩兵
	 * @date 2016-11-3 下午2:27:03
	 */
	@RequestMapping(value = "/cusInterface/getScenceIsUsed")
	public String getScenceIsUsed(@RequestParam("scenceNo") String scenceNo) throws Exception;

	/**
	 * 
	 * 方法描述： 根据客户编号查询客户信息
	 * 
	 * @param cusNo
	 * @return
	 * @throws Exception
	 *             MfCusCustomer
	 * @author 沈浩兵
	 * @date 2016-10-25 下午2:58:35
	 */
	@RequestMapping(value = "/cusInterface/getMapCusByCusNo")
	public Map<String, Object> getMapCusByCusNo(@RequestParam("cusNo") String cusNo) throws Exception;

	/**
	 * 
	 * 方法描述：插入客户登记信息
	 * 
	 * @param mfCusCustomer
	 * @throws Exception
	 *             void
	 * @author zhs
	 * @date 2016-5-23 下午5:50:29
	 */
	@RequestMapping(value = "/cusInterface/insert")
	public MfCusCustomer insert(@RequestBody MfCusCustomer mfCusCustomer) throws Exception;

	/**
	 * 
	 * 方法描述：更新客户登记信息
	 * 
	 * @param mfCusCustomer
	 * @throws Exception
	 *
	 * @author zhs
	 * @date 2016-5-23 下午5:50:29
	 */
	@RequestMapping(value = "/cusInterface/updateCusCustomer")
	public void updateCusCustomer(@RequestBody MfCusCustomer mfCusCustomer) throws Exception;

	/**
	 * @author czk
	 * @Description: 先根据idType和idNum验证客户是否存在，如果不存在则新增。返回客户号 date 2016-7-28
	 * @param mfCusCustomer
	 * @throws Exception
	 */
	@RequestMapping(value = "/cusInterface/doCheckAndInsert")
	public String doCheckAndInsert(@RequestBody MfCusCustomer mfCusCustomer) throws Exception;

	/**
	 * 
	 * 方法描述：更新客户的评分信息
	 * 
	 * @param cusNo
	 * @param grade
	 * @throws Exception
	 *             void
	 * @author zhs
	 * @date 2016-9-20 上午10:46:15
	 */
	@RequestMapping(value = "/cusInterface/updateCusGradeInfo")
	public void updateCusGradeInfo(@RequestBody MfCusCustomer mfCusCustomer) throws Exception;

	/**
	 * 
	 * 方法描述：查询个人客户基本信息
	 * 
	 * @param cusNo
	 * @throws Exception
	 *             void
	 * @author 张冬磊
	 * @date 2017-5-6 上午10:46:15
	 */
	@RequestMapping(value = "/cusInterface/getByCusNo")
	public MfCusPersBaseInfo getByCusNo(@RequestBody MfCusPersBaseInfo mfCusPersBaseInfo) throws Exception;

	/**
	 * 
	 * 方法描述：插入个人客户基本信息
	 * 
	 * @throws Exception
	 *             void
	 * @author 张冬磊
	 * @date 2017-5-6 上午10:46:15
	 */
	@RequestMapping(value = "/cusInterface/insertMfCusPersBaseInfo")
	public MfCusPersBaseInfo insertMfCusPersBaseInfo(@RequestBody MfCusPersBaseInfo mfCusPersBaseInfo)
			throws Exception;

	/**
	 * 方法描述：完善其他信息
	 * 
	 * @param tableName
	 *            操作表名称
	 * @param entity
	 *            操作实体对象 void
	 * @throws Exception
	 */
	@RequestMapping(value = "/cusInterface/insertCusOtherInfo")
	public void insertCusOtherInfo(@RequestParam("tableName") String tableName, @RequestBody Object entity) throws Exception;

	/**
	 * 方法描述：更新其他信息
	 * 
	 * @param tableName
	 *            操作表名称
	 * @param entity
	 *            操作实体对象 void
	 * @throws Exception
	 */
	@RequestMapping(value = "/cusInterface/updateCusOtherInfo")
	public void updateCusOtherInfo(@RequestParam("tableName") String tableName,@RequestBody Object entity) throws Exception;

	/**
	 * 方法描述：获取其他信息
	 * 
	 * @param tableName
	 *            操作表名称
	 * @param cusNo
	 *            客户号 void
	 * @throws Exception
	 */
	@RequestMapping(value = "/cusInterface/getCusOtherInfo")
	public Object getCusOtherInfo(@RequestParam("tableName") String tableName,@RequestParam("cusNo") String cusNo) throws Exception;

	/**
	 * 
	 * 方法描述：更新个人客户基本信息
	 * 
	 * @param cusNo
	 * @throws Exception
	 *             void
	 * @author 张冬磊
	 * @date 2017-5-6 上午10:46:15
	 */
	@RequestMapping(value = "/cusInterface/updateMfCusPersBaseInfo")
	public void updateMfCusPersBaseInfo(@RequestBody MfCusPersBaseInfo mfCusPersBaseInfo) throws Exception;

	/**
	 * 
	 * 方法描述：删除个人客户基本信息
	 * 
	 * @param cusNo
	 * @throws Exception
	 *             void
	 * @author 张冬磊
	 * @date 2017-5-6 上午10:46:15
	 */
	@RequestMapping(value = "/cusInterface/deleteMfCusPersBaseInfo")
	public void deleteMfCusPersBaseInfo(@RequestBody MfCusPersBaseInfo mfCusPersBaseInfo) throws Exception;

	/**
	 * 
	 * 方法描述： 插入银行卡信息
	 * 
	 * @param dataMap
	 * @throws Exception
	 *             void
	 * @author 沈浩兵
	 * @date 2016-9-30 上午11:47:40
	 */
	@RequestMapping(value = "/cusInterface/insertBnakAccManage")
	public void insertBnakAccManage(@RequestBody Map<String, Object> dataMap) throws Exception;

	/**
	 * 
	 * 方法描述： 根据客户号查询银行列表
	 * 
	 * @param mfCusBankAccManage
	 * @throws Exception
	 * @return
	 * @author 张冬磊
	 * @date 2017-07-23 上午11:47:40
	 */

	@RequestMapping(value = "/cusInterface/getMfCusBankAccListByCusNo")
	public List<MfCusBankAccManage> getMfCusBankAccListByCusNo(@RequestBody MfCusBankAccManage mfCusBankAccManage)
			throws Exception;

	/**
	 * @author czk
	 * @Description: 获得和该客户拥有相同法人的所有客户；返回的list中包含该客户本身 date 2016-12-5
	 */
	@RequestMapping(value = "/cusInterface/getSameLegalManCus")
	public List<MfCusCorpBaseInfo> getSameLegalManCus(@RequestParam("cusNo") String cusNo) throws Exception;

	@RequestMapping(value = "/cusInterface/getMfCusTableList")
	public List<MfCusTable> getMfCusTableList(@RequestParam("cusNo") String cusNo) throws Exception;

	/**
	 * 方法描述：根据客户号获得 客户信息完善情况信息(@RequestBody 移动端接口)
	 * 
	 * @param cusNo
	 * @return List<MfCusTable>
	 * @author 张冬磊
	 * @date 2017-06-14 下午8:56:54
	 */
	@RequestMapping(value = "/cusInterface/getMfCusTableList1")
	public List<MfCusTable> getMfCusTableList1(@RequestParam("cusNo") String cusNo) throws Exception;

	/**
	 * 
	 * 方法描述： 检查买卖双方是否为关联企业（子母公司，同子公司等）
	 * 
	 * @param sellCusNo
	 *            卖方客户号
	 * @param buyCusNo
	 *            买方客户号
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2017-1-5 下午4:19:46
	 */
	@RequestMapping(value = "/cusInterface/checkRelstionBySellAndBuyNo")
	public String checkRelstionBySellAndBuyNo(@RequestParam("cusNo") String CusNo,@RequestParam("buyCusNo") String buyCusNo) throws Exception;

	/**
	 * 提供所有核心企业供选择。
	 * 
	 * @return List<MfCusCustomer>
	 * @throws Exception
	 */
	@RequestMapping(value = "/cusInterface/getAllCoresForSelect")
	public List<MfCusCustomer> getAllCoresForSelect() throws Exception;

	/**
	 * @param cusNo
	 *            根据客户号（链属企业/核心企业）获取到该企业的关联企业。
	 * @param cusType
	 *            链属企业-7/核心企业-8
	 * @return List<MfCusChainRelation>
	 * @author LiuYF
	 * @throws Exception
	 */
	@RequestMapping(value = "/cusInterface/getCusChainRelations")
	public List<MfCusChainRelation> getCusChainRelations(@RequestParam("cusNo") String cusNo,@RequestParam("cusType") String cusType) throws Exception;

	/**
	 * 方法描述：根据线上用户明获取客户注册信息
	 * 
	 * @param mfCusCustomer
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getByUserName")
	public MfCusCustomer getByUserName(@RequestBody MfCusCustomer mfCusCustomer) throws Exception;

	/**
	 * 方法描述：线上客户注册
	 * 
	 * @param mfCusCustomer
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/cusInterface/doCusRegister")
	public boolean doCusRegister(@RequestBody Map<String, String> paramMap) throws Exception;

	/**
	 * 方法描述：线上客户 登录
	 * 
	 * @param mfCusCustomer
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/cusInterface/cusLogin")
	public boolean cusLogin(@RequestParam("userName") String userName,@RequestParam("pwd") String pwd) throws Exception;

	/**
	 * 
	 * 方法描述： 获取系统中所有的客户
	 * 
	 * @return
	 * @throws Exception
	 *             List<MfCusCustomer>
	 * @author zhs
	 * @date 2017-4-24 下午3:20:10
	 */
	@RequestMapping(value = "/cusInterface/getAllCusList")
	public List<MfCusCustomer> getAllCusList(@RequestBody MfCusCustomer mfCusCustomer) throws Exception;

	/**
	 * @Description: 获取系统中所有证件编号不为空的客户
	 * @param mfCusCustomer
	 * @return
	 * @throws Exception
	 * @author: 李伟
	 * @date: 2017-11-8 下午6:10:40
	 */
	@RequestMapping(value = "/cusInterface/getAllCusListIdNumNotNull")
	public List<MfCusCustomer> getAllCusListIdNumNotNull(@RequestBody MfCusCustomer mfCusCustomer) throws Exception;

	@RequestMapping(value = "/cusInterface/getByCusMngNo")
	public Ipage getByCusMngNo(@RequestBody Ipage ipg) throws Exception;

	@RequestMapping(value = "/cusInterface/findCusNumByMngNo")
	public int findCusNumByMngNo(@RequestBody MfCusCustomer mfCusCustomer) throws ServiceException;

	/**
	 * 插入一条基本信息
	 * 
	 * @param mfCusPersBaseInfo
	 * @author MaHao
	 * @throws Exception
	 * @date 2017-5-6 下午3:40:10
	 */

	/**
	 * 方法描述： 更新客户信息完整度
	 * 
	 * @param cusNo
	 * @throws ServiceException
	 *             String
	 * @author lcl
	 * @date 2017-5-16 下午3:39:58
	 */
	@RequestMapping(value = "/cusInterface/updateInfIntegrity")
	public String updateInfIntegrity(@RequestParam("cusNo") String cusNo) throws ServiceException;

	@RequestMapping(value = "/cusInterface/updateCustomter")
	public MfCusCustomer updateCustomter(@RequestBody MfCusCustomer mfCusCustomerTmp,@RequestParam("mfCusCustomer") MfCusCustomer mfCusCustomer)
			throws ServiceException;

	@RequestMapping(value = "/cusInterface/insertMfCusCorpBaseInfo")
	public void insertMfCusCorpBaseInfo(@RequestBody MfCusCorpBaseInfo mfCusCorpBaseInfo) throws Exception;

	@RequestMapping(value = "/cusInterface/insertMfCusCorpBaseInfoForApp")
	public MfCusCorpBaseInfo insertMfCusCorpBaseInfoForApp(@RequestBody MfCusCorpBaseInfo mfCusCorpBaseInfo)
			throws Exception;

	/**
	 * 跟新企业客户基本信息
	 * 
	 * @param mfCusCorpBaseInfo
	 * @throws Exception
	 */
	@RequestMapping(value = "/cusInterface/updateMfCusCorpBaseInfo")
	public void updateMfCusCorpBaseInfo(@RequestBody MfCusCorpBaseInfo mfCusCorpBaseInfo) throws Exception;

	@RequestMapping(value = "/cusInterface/updateCusTable")
	public void updateCusTable(@RequestBody MfCusTable mfCusTable) throws Exception;

	/**
	 * 方法描述： 根据客户类型
	 * 
	 * @param cusType
	 * @return
	 * @throws Exception
	 *             List<Map<String,String>>
	 * @author YuShuai
	 * @date 2017-6-2 下午7:16:41
	 */
	@RequestMapping(value = "/cusInterface/getCusListByCusType")
	public List<Map<String, String>> getCusListByCusType(@RequestParam("cusType") String cusType) throws Exception;

	/**
	 * 方法描述： 根据客户类型获取客户号
	 * 
	 * @param cusType
	 * @return
	 * @throws Exception
	 *             String
	 * @author YuShuai
	 * @date 2017-6-6 上午11:01:24
	 */
	@RequestMapping(value = "/cusInterface/getCusNo")
	public String getCusNo(@RequestParam("cusType") String cusType) throws Exception;

	/**
	 * 方法描述：获取客户资产信息
	 * 
	 * @param MfCusPersonAssetsInfo
	 * @return
	 * @throws Exception
	 *             List<MfCusPersonAssetsInfo>
	 * @author YuShuai
	 * @date 2017-6-13 下午4:21:11
	 */
	@RequestMapping(value = "/cusInterface/getCusPerAssetList")
	public List<MfCusPersonAssetsInfo> getCusPerAssetList(@RequestBody MfCusPersonAssetsInfo MfCusPersonAssetsInfo)
			throws Exception;

	/**
	 * 方法描述： 根据客户号获取社会关系列表
	 * 
	 * @param cusNo
	 * @return
	 * @throws Exception
	 *             List<MfCusFamilyInfo>
	 * @author YuShuai
	 * @date 2017-6-14 上午9:31:48
	 */
	@RequestMapping(value = "/cusInterface/getFamilyList")
	public List<MfCusFamilyInfo> getFamilyList(@RequestBody MfCusFamilyInfo mfCusFamilyInfo) throws Exception;

	/**
	 * 方法描述： 根据社会关系id获取联系人信息
	 * 
	 * @param mfCusFamilyInfo
	 * @return
	 * @throws Exception
	 * @author Jiasl
	 */
	@RequestMapping(value = "/cusInterface/getCusFamilyInfo")
	public MfCusFamilyInfo getCusFamilyInfo(@RequestBody MfCusFamilyInfo mfCusFamilyInfo) throws Exception;
	/**
	 * 方法描述： 获取录入的银行卡信息
	 *
	 * @param mfCusBankAccManage
	 * @return
	 * @throws ServiceException
	 *             MfCusBankAccManage
	 * @author YuShuai
	 * @date 2017-6-16 下午5:29:43
	 */
	@RequestMapping(value = "/cusInterface/getMfCusBankAccManageById")
	public MfCusBankAccManage getMfCusBankAccManageById(@RequestBody MfCusBankAccManage mfCusBankAccManage) throws ServiceException;

	/**
	 *
	 * 方法描述： 查询客户的职业信息列表
	 *
	 * @param mfCusPersonJob
	 * @return
	 * @throws Exception
	 *             List<MfCusPersonJob>
	 * @author lwq
	 * @date 2017-6-28 下午7:49:34
	 */
	@RequestMapping(value = "/cusInterface/mfCusPersonJobList")
	public List<MfCusPersonJob> mfCusPersonJobList(@RequestBody MfCusPersonJob mfCusPersonJob) throws Exception;

	/**
	 * 方法描述：查询系统中所有启用的客户类型
	 *
	 * @return
	 * @throws Exception
	 *             List<MfCusType>
	 * @author lcl
	 * @date 2017-6-30 上午10:56:44
	 */
	@RequestMapping(value = "/cusInterface/getAllList")
	public List<MfCusType> getAllList() throws Exception;

	/**
	 * 获取客户号下的银行卡信息
	 *
	 * @param ipage
	 * @param mfCusBankAccManage
	 * @author MaHao
	 * @return
	 */
	@RequestMapping(value = "/cusInterface/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage);

	/**
	 * 根据主键更新银行卡信息
	 *
	 * @param mfCusBankAccManage
	 * @author MaHao
	 */
	@RequestMapping(value = "/cusInterface/updateMfCusBankAccManage")
	public void updateMfCusBankAccManage(@RequestBody MfCusBankAccManage mfCusBankAccManage);

	/**
	 * 根据银行卡号获取银行卡信息
	 *
	 * @param bankIdentify
	 * @return
	 * @author MaHao
	 */
	@RequestMapping(value = "/cusInterface/getDataMapById")
	public Map<String, Object> getDataMapById(@RequestBody BankIdentify bankIdentify);

	/**
	 * 方法描述： 选择组件获取系统中所有客户和公共借款人 id:证件号码 name:名称
	 *
	 * @return
	 * @throws Exception
	 *             JSONArray
	 * @author YuShuai
	 * @param cusNo
	 * @date 2017-7-21 下午2:50:54
	 */
	@RequestMapping(value = "/cusInterface/getCobBorrower")

	public JSONArray getCobBorrower(@RequestParam("cusNo") String cusNo) throws Exception;

	/**
	 * 方法描述： 选择组件获取系统中所有客户和公共借款人 id:证件号码 name:名称
	 *
	 * @return
	 * @throws Exception
	 *             JSONArray
	 * @author YuShuai
	 * @param cusNo
	 * @date 2017-7-21 下午2:50:54
	 */
	@RequestMapping(value = "/cusInterface/getCobBorrowerForShow")
	public JSONArray getCobBorrowerForShow(@RequestParam("id") String id) throws Exception;
	/**
	 *
	 * 方法描述： 获取账号信息
	 *
	 * @param mfCusBankAccManage
	 * @return
	 * @throws Exception
	 *             MfCusBankAccManage
	 * @author zhs
	 * @date 2017-7-31 下午3:09:13
	 */
	@RequestMapping(value = "/cusInterface/getCusBankAccInfo")
	public MfCusBankAccManage getCusBankAccInfo(@RequestBody MfCusBankAccManage mfCusBankAccManage) throws Exception;

	/**
	 *
	 * 方法描述： 负债信息
	 *
	 * @param mfCusPersonDebtInfo
	 * @return
	 * @throws Exception
	 *             List<MfCusPersonDebtInfo>
	 * @author lwq
	 * @date 2017-8-1 下午5:58:13
	 */
	@RequestMapping(value = "/cusInterface/findDebetList")
	public List<MfCusPersonDebtInfo> findDebetList(@RequestBody MfCusPersonDebtInfo mfCusPersonDebtInfo)
			throws Exception;

	/**
	 *
	 * 方法描述： 个人名下企业
	 *
	 * @param parmMap
	 * @return
	 * @throws Exception
	 *             List<MfCusPersonCorp>
	 * @author lwq
	 * @date 2017-8-4 下午2:34:26
	 */
	@RequestMapping(value = "/cusInterface/getPersonCorpList")
	public List<MfCusPersonCorp> getPersonCorpList(@RequestBody MfCusPersonCorp mfCusPersonCorp) throws Exception;

	/**
	 * lzs 获取渠道信息中的银行账户信息
	 *
	 * @param busTrence
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/cusInterface/getMfBusTrench")
	public MfBusTrench getMfBusTrench(@RequestBody MfBusTrench busTrence) throws Exception;

	/**
	 *
	 * 方法描述： 获取所有渠道信息
	 *
	 * @param busTrence
	 * @return
	 * @throws Exception
	 *             List<MfBusTrench>
	 * @author lwq
	 * @date 2018-1-19 下午4:18:30
	 */
	@RequestMapping(value = "/cusInterface/getMfBusTrenchList")
	public List<MfBusTrench> getMfBusTrenchList() throws Exception;

	/**
	 * 钉钉校验手机号证件号唯一 获取唯一值的list数据
	 *
	 * @param mfUniqueVal
	 * @param saveType
	 * @return
	 * @throws Exception
	 * @author MaHao
	 * @date 2017-8-16 下午4:07:19
	 */
	@RequestMapping(value = "/cusInterface/doCheckUniqueAjax")
	public String doCheckUniqueAjax(@RequestBody MfUniqueVal mfUniqueVal,@RequestParam("saveType") String saveType) throws Exception;

	/**
	 * 方法描述： 获取社会关系数据
	 *
	 * @param mfCusCustomer
	 * @return
	 * @throws Exception
	 *             Page<MfCusCustomer>
	 * @author YuShuai
	 * @date 2017-8-21 下午7:36:16
	 */
	@RequestMapping(value = "/cusInterface/findOnlyCobByPage")
	public Page<MfCusCustomer> findOnlyCobByPage(@RequestBody MfCusCustomer mfCusCustomer) throws Exception;

	/**
	 * 方法描述： 获取客户和社会关系
	 *
	 * @param mfCusCustomer
	 * @return
	 * @throws Exception
	 *             Page<MfCusCustomer>
	 * @author YuShuai
	 * @date 2017-8-21 下午7:39:00
	 */
	@RequestMapping(value = "/cusInterface/findCobByPage")
	public Page<MfCusCustomer> findCobByPage(@RequestBody MfCusCustomer mfCusCustomer) throws Exception;

	/**
	 * 方法描述： 获取排除自己的所有客户（共同借款人所用）
	 *
	 * @param mfCusCustomer
	 * @return
	 * @throws Exception
	 *             List<MfCusCustomer>
	 * @author YuShuai
	 * @date 2017-8-22 下午5:18:00
	 */
	@RequestMapping(value = "/cusInterface/getCusListForCob")
	public List<MfCusCustomer> getCusListForCob(@RequestBody MfCusCustomer mfCusCustomer) throws Exception;

	@RequestMapping(value = "/cusInterface/getCoborrNumList")
	public Ipage getCoborrNumList(@RequestBody Ipage ipage) throws Exception;

	/**
	 * 钉钉使用 根据客户类型和action，获得该模块的表单信息；如果该客户类型的信息找不到，则查询模板表单信息
	 *
	 * @param cusType
	 * @param action
	 * @return
	 * @throws Exception
	 * @author MaHao
	 * @date 2017-8-28 下午4:52:14
	 */
	@RequestMapping(value = "/cusInterface/getByCusType")
	public MfCusFormConfig getByCusType(@RequestParam("cusType") String cusType,@RequestParam("action") String action) throws Exception;

	/**
	 * 钉钉 根据id获取资产信息
	 *
	 * @param mfCusPersonAssetsInfo
	 * @return
	 * @throws Exception
	 * @author MaHao
	 * @date 2017-8-28 下午5:46:29
	 */
	@RequestMapping(value = "/cusInterface/getMfCusPersonAssetsInfoById")
	public MfCusPersonAssetsInfo getMfCusPersonAssetsInfoById(@RequestBody MfCusPersonAssetsInfo mfCusPersonAssetsInfo)
			throws Exception;

	/**
	 * 钉钉 获取股东信息
	 *
	 * @param mfCusShareholder
	 * @return
	 * @throws Exception
	 * @author MaHao
	 * @date 2017-8-29 上午9:46:50
	 */
	@RequestMapping(value = "/cusInterface/getMfCusShareholderById")
	public MfCusShareholder getMfCusShareholderById(@RequestBody MfCusShareholder mfCusShareholder) throws Exception;

	/**
	 *
	 * 方法描述： 根据客户号获取股东信息
	 *
	 * @param mfCusShareholder
	 * @return
	 * @throws Exception
	 *             MfCusShareholder
	 * @author YaoWenHao
	 * @date 2017-12-6 上午10:04:45
	 */
	@RequestMapping(value = "/cusInterface/getMfCusShareholderByCusNo")
	public List<MfCusShareholder> getMfCusShareholderByCusNo(@RequestBody MfCusShareholder mfCusShareholder)
			throws Exception;

	/**
	 * 钉钉插入股东信息
	 *
	 * @param mfCusShareholder
	 * @return
	 * @throws Exception
	 * @author MaHao
	 * @date 2017-8-29 下午5:54:11
	 */
	@RequestMapping(value = "/cusInterface/insertMfCusShareholder")
	public MfCusShareholder insertMfCusShareholder(@RequestBody MfCusShareholder mfCusShareholder) throws Exception;

	/**
	 * App插入股东信息
	 *
	 * @param mfCusShareholder
	 * @return
	 * @throws Exception
	 * @author ywh
	 * @date 2017-10-19 下午5:54:11
	 */
	@RequestMapping(value = "/cusInterface/insertMfCusShareholderForApp")
	public MfCusShareholder insertMfCusShareholderForApp(@RequestBody MfCusShareholder mfCusShareholder)
			throws Exception;

	/**
	 * 钉钉获取高管信息
	 *
	 * @param mfCusHighInfo
	 * @return
	 * @throws Exception
	 * @author MaHao
	 * @date 2017-8-29 下午6:03:41
	 */
	@RequestMapping(value = "/cusInterface/getMfCusHighInfoById")
	public MfCusHighInfo getMfCusHighInfoById(@RequestBody MfCusHighInfo mfCusHighInfo) throws Exception;

	/**
	 * 钉钉插入对外投资信息
	 *
	 * @param mfCusEquityInfo
	 * @throws Exception
	 * @author MaHao
	 * @date 2017-8-29 下午6:07:27
	 */
	@RequestMapping(value = "/cusInterface/insertMfCusEquityInfo")
	public void insertMfCusEquityInfo(@RequestBody MfCusEquityInfo mfCusEquityInfo) throws Exception;

	/**
	 * 获取对外投资信息
	 *
	 * @param mfCusEquityInfo
	 * @return
	 * @throws Exception
	 * @author MaHao
	 * @date 2017-8-29 下午6:10:54
	 */
	@RequestMapping(value = "/cusInterface/getMfCusEquityInfoById")
	public MfCusEquityInfo getMfCusEquityInfoById(@RequestBody MfCusEquityInfo mfCusEquityInfo) throws Exception;

	/**
	 * 外投资信息
	 *
	 * @param mfCusEquityInfo
	 * @throws Exception
	 * @author MaHao
	 * @date 2017-8-29 下午6:12:37
	 */
	@RequestMapping(value = "/cusInterface/updateMfCusEquityInfo")
	public void updateMfCusEquityInfo(@RequestBody MfCusEquityInfo mfCusEquityInfo) throws Exception;

	/**
	 * 根据客户号获取对外投资信息
	 *
	 * @param mfCusEquityInfo
	 * @throws Exception
	 * @author MaHao
	 * @date 2017-8-29 下午6:12:37
	 */
	@RequestMapping(value = "/cusInterface/getMfCusEquityInfoByCusNo")
	public List<MfCusEquityInfo> getMfCusEquityInfoByCusNo(@RequestBody MfCusEquityInfo mfCusEquityInfo)
			throws Exception;

	/**
	 * 带返回信息插入
	 *
	 * @param mfCusBankAccManage
	 * @return
	 * @throws Exception
	 * @author MaHao
	 * @date 2017-8-29 下午6:17:56
	 */
	@RequestMapping(value = "/cusInterface/insertMfCusBankAccManage")
	public MfCusBankAccManage insertMfCusBankAccManage(@RequestBody MfCusBankAccManage mfCusBankAccManage)
			throws Exception;

	/**
	 * App带返回信息插入
	 *
	 * @param mfCusBankAccManage
	 * @return
	 * @throws Exception
	 * @author MaHao
	 * @date 2017-8-29 下午6:17:56
	 */
	@RequestMapping(value = "/cusInterface/insertMfCusBankAccManageForApp")
	public MfCusBankAccManage insertMfCusBankAccManageForApp(@RequestBody MfCusBankAccManage mfCusBankAccManage)
			throws Exception;

	/**
	 * 插入高管信息
	 *
	 * @param mfCusHighInfo
	 * @throws Exception
	 * @author MaHao
	 * @date 2017-8-29 下午6:21:48
	 */
	@RequestMapping(value = "/cusInterface/insertMfCusHighInfo")
	public void insertMfCusHighInfo(@RequestBody MfCusHighInfo mfCusHighInfo) throws Exception;

	/**
	 * App插入高管信息
	 *
	 * @param mfCusHighInfo
	 * @throws Exception
	 * @author MaHao
	 * @date 2017-8-29 下午6:21:48
	 */
	@RequestMapping(value = "/cusInterface/insertMfCusHighInfoForApp")
	public MfCusHighInfo insertMfCusHighInfoForApp(@RequestBody MfCusHighInfo mfCusHighInfo) throws Exception;

	/**
	 * 高管信息
	 *
	 * @param mfCusHighInfo
	 * @throws Exception
	 * @author MaHao
	 * @date 2017-8-29 下午6:23:39
	 */
	@RequestMapping(value = "/cusInterface/updateMfCusHighInfo")
	public void updateMfCusHighInfo(@RequestBody MfCusHighInfo mfCusHighInfo) throws Exception;

	/**
	 * 钉钉更新股东信息
	 *
	 * @param mfCusShareholder
	 * @throws Exception
	 * @author MaHao
	 * @date 2017-8-29 上午9:49:18
	 */
	@RequestMapping(value = "/cusInterface/updateMfCusShareholder")
	public void updateMfCusShareholder(@RequestBody MfCusShareholder mfCusShareholder) throws Exception;

	/**
	 * @Description:获取客户分类分页数据
	 * @param mfCusType
	 * @return
	 * @throws Exception
	 * @author: 李伟
	 * @date: 2017-8-28 下午10:03:30
	 */
	@RequestMapping(value = "/cusInterface/getMfCusTypePageList")
	public Ipage getMfCusTypePageList(@RequestBody Ipage ipage) throws Exception;

	/**
	 * 钉钉个人详情中 获取行业分类
	 *
	 * @param mfCusCorpBaseInfo
	 * @return
	 * @throws Exception
	 * @author MaHao
	 * @date 2017-8-31 下午6:35:59
	 */
	@RequestMapping(value = "/cusInterface/getLoanUseById")
	public Child getLoanUseById(@RequestBody MfCusCorpBaseInfo mfCusCorpBaseInfo) throws Exception;

	/**
	 * 获取该客户号下的股东信息，不分页
	 *
	 * @param mfCusShareholder
	 * @return
	 * @throws Exception
	 * @author MaHao
	 * @date 2017-8-31 下午6:50:05
	 */
	@RequestMapping(value = "/cusInterface/findMfCusShareholderListByCusNo")
	public List<MfCusShareholder> findMfCusShareholderListByCusNo(@RequestBody MfCusShareholder mfCusShareholder)
			throws Exception;

	/**
	 * 高管信息不分页
	 *
	 * @param mfCusHighInfo
	 * @return
	 * @throws Exception
	 * @author MaHao
	 * @date 2017-8-31 下午6:54:03
	 */
	@RequestMapping(value = "/cusInterface/findMfCusHighInfoListByCusNo")
	public List<MfCusHighInfo> findMfCusHighInfoListByCusNo(@RequestBody MfCusHighInfo mfCusHighInfo) throws Exception;

	/**
	 * 获取对外投资信息不分页
	 *
	 * @param mfCusEquityInfo
	 * @return
	 * @throws Exception
	 * @author MaHao
	 * @date 2017-8-31 下午7:16:34
	 */
	@RequestMapping(value = "/cusInterface/findMfCusEquityInfoListByCusNo")
	public List<MfCusEquityInfo> findMfCusEquityInfoListByCusNo(@RequestBody MfCusEquityInfo mfCusEquityInfo)
			throws Exception;

	/**
	 * 资产信息分页 资产类型转换<strong>汽车、房屋字符串</strong>类型
	 *
	 * @param ipg
	 * @param mfCusPersonAssetsInfo
	 * @return
	 * @throws Exception
	 * @author MaHao
	 * @date 2017-9-1 上午9:02:30
	 */
	@RequestMapping(value = "/cusInterface/findMfCusPersonAssetsInfoByPage")
	public Ipage findMfCusPersonAssetsInfoByPage(@RequestBody Ipage ipg)
			throws Exception;

	/**
	 * 根据客户类型编号获取客户类型实体
	 *
	 * @param mfCusType
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/cusInterface/getMfCusTypeByTypeNo")
	public MfCusType getMfCusTypeByTypeNo(@RequestBody MfCusType mfCusType) throws Exception;

	/**
	 * 根据机构号获取机构名称
	 *
	 * @param mfBusAgencies
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/cusInterface/geMfBusAgenciestById")
	public MfBusAgencies getMfBusAgenciesById(@RequestBody MfBusAgencies mfBusAgencies) throws Exception;

	/**
	 * 方法描述： 获取该客户的所有信用情况
	 *
	 * @param cusNo
	 * @return List<MfCusPersonCreditInfo>
	 * @author YuShuai
	 * @date 2017-9-22 下午5:37:54
	 */
	@RequestMapping(value = "/cusInterface/getCusPerCreditList")
	public List<MfCusPersonCreditInfo> getCusPerCreditList(@RequestParam("cusNo") String cusNo) throws Exception;

	/**
	 * 方法描述： 获取客户总负债额度
	 *
	 * @param cusNo
	 * @return
	 * @throws Exception
	 *             String
	 * @author YuShuai
	 * @date 2017-9-23 上午10:09:31
	 */
	@RequestMapping(value = "/cusInterface/getCusLiabilities")
	public String getCusLiabilities(@RequestParam("cusNo") String cusNo) throws Exception;

	/**
	 * 方法描述： 获取客户的净资产
	 *
	 * @param cusNo
	 * @return
	 * @throws Exception
	 *             String
	 * @author YuShuai
	 * @date 2017-9-23 上午10:12:08
	 */
	@RequestMapping(value = "/cusInterface/getCusAssValue")
	public String getCusAssValue(@RequestParam("cusNo") String cusNo) throws Exception;

	/**
	 * 方法描述： 获取客户净利润
	 *
	 * @param cusNo
	 * @return
	 * @throws Exception
	 *             String
	 * @author YuShuai
	 * @date 2017-9-23 上午10:14:53
	 */
	@RequestMapping(value = "/cusInterface/getCusNetProfitValue")
	public String getCusNetProfitValue(@RequestParam("cusNo") String cusNo) throws Exception;

	/**
	 * 方法描述： 获取客户日均流水
	 *
	 * @param cusNo
	 * @return
	 * @throws Exception
	 *             String
	 * @author YuShuai
	 * @date 2017-9-23 上午10:20:39
	 */
	@RequestMapping(value = "/cusInterface/getCusCashFlowValue")
	public String getCusCashFlowValue(@RequestParam("cusNo") String cusNo) throws Exception;

	/**
	 *
	 * 方法描述：插入与业务相关的客户表单配置信息
	 *
	 * @param kindNo
	 * @param appId
	 * @throws Exception
	 *             void
	 * @author zhs
	 * @param cusNo
	 * @date 2017-9-22 下午2:39:37
	 */
	@RequestMapping(value = "/cusInterface/insertBizCusTable")
	public void insertBizCusTable(@RequestParam("kindNo") String kindNo,@RequestParam("appId") String appId,@RequestParam("cusNo") String cusNo) throws Exception;

	/**
	 *
	 * 方法描述： 客户流动资产信息
	 *
	 * @param mfCusPersonFlowAssetsInfo
	 * @return
	 * @throws Exception
	 *             List<MfCusPersonFlowAssetsInfo>
	 * @author lwq
	 * @date 2017-9-26 下午4:15:17
	 */
	@RequestMapping(value = "/cusInterface/getMfCusPersonFlowAssetsInfoList")
	public List<MfCusPersonFlowAssetsInfo> getMfCusPersonFlowAssetsInfoList(
			@RequestBody MfCusPersonFlowAssetsInfo mfCusPersonFlowAssetsInfo) throws Exception;

	/**
	 *
	 * 方法描述： 新增职业信息
	 *
	 * @param mfCusPersonJob
	 * @throws Exception
	 *             void
	 * @author 沈浩兵
	 * @date 2017-10-11 下午5:29:10
	 */
	@RequestMapping(value = "/cusInterface/insertMfCusPersonJob")
	public void insertMfCusPersonJob(@RequestBody MfCusPersonJob mfCusPersonJob) throws Exception;

	/**
	 *
	 * 方法描述： APP新增职业信息
	 *
	 * @param mfCusPersonJob
	 * @throws Exception
	 *             void
	 * @author ywh
	 * @date 2017-10-19下午4:29:10
	 */
	@RequestMapping(value = "/cusInterface/insertMfCusPersonJobForApp")
	public MfCusPersonJob insertMfCusPersonJobForApp(@RequestBody MfCusPersonJob mfCusPersonJob) throws Exception;

	/**
	 *
	 * 方法描述： 收支情况登记
	 *
	 * @param mfCusPersonIncExpe
	 * @throws Exception
	 *             void
	 * @author 沈浩兵
	 * @date 2017-10-11 下午5:31:56
	 */
	@RequestMapping(value = "/cusInterface/insertMfCusPersonIncExpe")
	public void insertMfCusPersonIncExpe(@RequestBody MfCusPersonIncExpe mfCusPersonIncExpe) throws Exception;

	/**
	 *
	 * 方法描述： 收支情况登记
	 *
	 * @param mfCusPersonIncExpe
	 * @throws Exception
	 *             void
	 * @author ywh
	 * @date 2017-10-19 下午4:31:56
	 */
	@RequestMapping(value = "/cusInterface/insertMfCusPersonIncExpeForApp")
	public MfCusPersonIncExpe insertMfCusPersonIncExpeForApp(@RequestBody MfCusPersonIncExpe mfCusPersonIncExpe)
			throws Exception;

	/**
	 *
	 * 方法描述： 新增社会关系
	 *
	 * @param mfCusFamilyInfo
	 * @throws Exception
	 *             void
	 * @author 沈浩兵
	 * @date 2017-10-11 下午5:34:24
	 */
	@RequestMapping(value = "/cusInterface/insertMfCusFamilyInfo")
	public MfCusFamilyInfo insertMfCusFamilyInfo(@RequestBody MfCusFamilyInfo mfCusFamilyInfo) throws Exception;

	/**
	 *
	 * 方法描述： 新增信用信息
	 *
	 * @param mfCusPersonCreditInfo
	 * @throws Exception
	 *             void
	 * @author 沈浩兵
	 * @date 2017-10-11 下午5:35:50
	 */
	@RequestMapping(value = "/cusInterface/insertMfCusPersonCreditInfo")
	public void insertMfCusPersonCreditInfo(@RequestBody MfCusPersonCreditInfo mfCusPersonCreditInfo) throws Exception;

	/**
	 *
	 * 方法描述：App 新增信用信息
	 *
	 * @param mfCusPersonCreditInfo
	 * @throws Exception
	 *             void
	 * @author 沈浩兵
	 * @date 2017-10-11 下午5:35:50
	 */
	@RequestMapping(value = "/cusInterface/insertMfCusPersonCreditInfoForApp")
	public MfCusPersonCreditInfo insertMfCusPersonCreditInfoForApp(
			@RequestBody MfCusPersonCreditInfo mfCusPersonCreditInfo) throws Exception;

	/**
	 *
	 * 方法描述： 资产信息登记
	 *
	 * @param mfCusPersonAssetsInfo
	 * @throws Exception
	 *             void
	 * @author 沈浩兵
	 * @date 2017-10-11 下午5:36:52
	 */
	@RequestMapping(value = "/cusInterface/insertMfCusPersonAssetsInfo")
	public void insertMfCusPersonAssetsInfo(@RequestBody MfCusPersonAssetsInfo mfCusPersonAssetsInfo) throws Exception;

	/**
	 *
	 * 方法描述： App资产信息登记
	 *
	 * @param mfCusPersonAssetsInfo
	 * @throws Exception
	 *             void
	 * @author 沈浩兵
	 * @date 2017-10-11 下午5:36:52
	 */
	@RequestMapping(value = "/cusInterface/insertMfCusPersonAssetsInfoForApp")
	public MfCusPersonAssetsInfo insertMfCusPersonAssetsInfoForApp(
			@RequestBody MfCusPersonAssetsInfo mfCusPersonAssetsInfo) throws Exception;

	/**
	 *
	 * 方法描述： 负债信息登记
	 *
	 * @param mfCusPersonDebtInfo
	 * @throws Exception
	 *             void
	 * @author 沈浩兵
	 * @date 2017-10-11 下午5:38:03
	 */
	@RequestMapping(value = "/cusInterface/insertMfCusPersonDebtInfo")
	public void insertMfCusPersonDebtInfo(@RequestBody MfCusPersonDebtInfo mfCusPersonDebtInfo) throws Exception;

	/**
	 *
	 * 方法描述： 负债信息登记
	 *
	 * @param mfCusPersonDebtInfo
	 * @throws Exception
	 *             void
	 * @author ywh
	 * @date 2017-10-19 下午5:38:03
	 */
	@RequestMapping(value = "/cusInterface/insertMfCusPersonDebtInfoForApp")
	public MfCusPersonDebtInfo insertMfCusPersonDebtInfoForApp(@RequestBody MfCusPersonDebtInfo mfCusPersonDebtInfo)
			throws Exception;

	/**
	 *
	 * 方法描述： 更新职业信息
	 *
	 * @param mfCusPersonJob
	 * @throws Exception
	 *             void
	 * @author 沈浩兵
	 * @date 2017-10-11 下午6:26:53
	 */
	@RequestMapping(value = "/cusInterface/updateMfCusPersonJob")
	public void updateMfCusPersonJob(@RequestBody MfCusPersonJob mfCusPersonJob) throws Exception;

	/**
	 *
	 * 方法描述： 更新社会关系
	 *
	 * @param mfCusFamilyInfo
	 * @throws Exception
	 *             void
	 * @author 沈浩兵
	 * @date 2017-10-11 下午6:27:51
	 */
	@RequestMapping(value = "/cusInterface/updateMfCusFamilyInfo")
	public void updateMfCusFamilyInfo(@RequestBody MfCusFamilyInfo mfCusFamilyInfo) throws Exception;

	/**
	 *
	 * 方法描述： 更新资产信息
	 *
	 * @param mfCusPersonAssetsInfo
	 * @throws Exception
	 *             void
	 * @author 沈浩兵
	 * @date 2017-10-11 下午6:30:17
	 */
	@RequestMapping(value = "/cusInterface/updateMfCusPersonAssetsInfo")
	public void updateMfCusPersonAssetsInfo(@RequestBody MfCusPersonAssetsInfo mfCusPersonAssetsInfo) throws Exception;

	/**
	 *
	 * 方法描述： 新增对外担保信息
	 *
	 * @param mfCusGuaranteeOuter
	 * @throws Exception
	 *             void
	 * @author 沈浩兵
	 * @date 2017-10-11 下午6:31:31
	 */
	@RequestMapping(value = "/cusInterface/insertMfCusGuaranteeOuter")
	public void insertMfCusGuaranteeOuter(@RequestBody MfCusGuaranteeOuter mfCusGuaranteeOuter) throws Exception;

	/**
	 *
	 * 方法描述： App新增对外担保信息
	 *
	 * @param mfCusGuaranteeOuter
	 * @throws Exception
	 *             void
	 * @author 沈浩兵
	 * @date 2017-10-11 下午6:31:31
	 */
	@RequestMapping(value = "/cusInterface/insertMfCusGuaranteeOuterForApp")
	public MfCusGuaranteeOuter insertMfCusGuaranteeOuterForApp(@RequestBody MfCusGuaranteeOuter mfCusGuaranteeOuter)
			throws Exception;

	/**
	 *
	 * 方法描述： 获得收支情况
	 *
	 * @param cusNo
	 * @return
	 * @throws Exception
	 *             MfCusPersonIncExpe
	 * @author 沈浩兵
	 * @date 2017-10-11 下午6:33:39
	 */
	@RequestMapping(value = "/cusInterface/getMfCusPersonIncExpe")
	public MfCusPersonIncExpe getMfCusPersonIncExpe(@RequestParam("cusNo") String cusNo) throws Exception;

	/**
	 *
	 * 方法描述： 获得对外担保信息
	 *
	 * @param cusNo
	 * @return
	 * @throws Exception
	 *             List<MfCusGuaranteeOuter>
	 * @author 沈浩兵
	 * @date 2017-10-11 下午6:36:28
	 */
	@RequestMapping(value = "/cusInterface/getMfCusGuaranteeOuter")
	public List<MfCusGuaranteeOuter> getMfCusGuaranteeOuter(@RequestParam("cusNo") String cusNo) throws Exception;

	/**
	 *
	 * 方法描述： 更新收支情况
	 *
	 * @param mfCusPersonIncExpe
	 * @throws Exception
	 *             void
	 * @author 沈浩兵
	 * @date 2017-10-11 下午6:39:13
	 */
	@RequestMapping(value = "/cusInterface/updateMfCusPersonIncExpe")
	public void updateMfCusPersonIncExpe(@RequestBody MfCusPersonIncExpe mfCusPersonIncExpe) throws Exception;

	/**
	 *
	 * 方法描述： 更新信用情况
	 *
	 * @param mfCusPersonCreditInfo
	 * @throws Exception
	 *             void
	 * @author 沈浩兵
	 * @date 2017-10-11 下午6:40:28
	 */
	@RequestMapping(value = "/cusInterface/updateMfCusPersonCreditInfo")
	public void updateMfCusPersonCreditInfo(@RequestBody MfCusPersonCreditInfo mfCusPersonCreditInfo) throws Exception;

	/**
	 *
	 * 方法描述： 更新负债信息
	 *
	 * @param mfCusPersonDebtInfo
	 * @throws Exception
	 *             void
	 * @author 沈浩兵
	 * @date 2017-10-11 下午6:41:32
	 */
	@RequestMapping(value = "/cusInterface/updateMfCusPersonDebtInfo")
	public void updateMfCusPersonDebtInfo(@RequestBody MfCusPersonDebtInfo mfCusPersonDebtInfo) throws Exception;

	/**
	 *
	 * 方法描述： 更新对外担保情况信息
	 *
	 * @param mfCusGuaranteeOuter
	 * @throws Exception
	 *             void
	 * @author 沈浩兵
	 * @date 2017-10-11 下午6:42:33
	 */
	@RequestMapping(value = "/cusInterface/updateMfCusGuaranteeOuter")
	public void updateMfCusGuaranteeOuter(@RequestBody MfCusGuaranteeOuter mfCusGuaranteeOuter) throws Exception;

	/**
	 * 根据客户微信账户标示查询客户信息
	 *
	 * @param openid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/cusInterface/getByOpenid")
	public MfCusCustomer getByOpenid(@RequestBody MfCusCustomer mfCusCustomer) throws Exception;

	/**
	 * 方法描述： 获取客户销售收入
	 *
	 * @param cusNo
	 * @return
	 * @throws Exception
	 *             String
	 * @author YuShuai
	 * @date 2017-10-11 上午10:31:08
	 */
	@RequestMapping(value = "/cusInterface/getCusSaleProfitValue")
	public String getCusSaleProfitValue(@RequestParam("cusNo") String cusNo) throws Exception;

	/**
	 *
	 * 方法描述： 对外担保列表
	 *
	 * @param mfCusGuaranteeOuter
	 * @return
	 * @throws Exception
	 *             List<MfCusGuaranteeOuter>
	 * @author lwq
	 * @date 2017-10-11 下午6:37:46
	 */
	@RequestMapping(value = "/cusInterface/getMfCusGuaranteeOuterList")
	public List<MfCusGuaranteeOuter> getMfCusGuaranteeOuterList(@RequestBody MfCusGuaranteeOuter mfCusGuaranteeOuter)
			throws Exception;

	/**
	 * 方法描述： 获取股东列表
	 *
	 * @param mfCusShareholder
	 * @return
	 * @throws Exception
	 *             List<MfCusShareholder>
	 * @author YuShuai
	 * @date 2017-10-13 下午5:38:51
	 */
	@RequestMapping(value = "/cusInterface/getMfCusShareholderList")
	public List<MfCusShareholder> getMfCusShareholderList(@RequestBody MfCusShareholder mfCusShareholder)
			throws Exception;

	/**
	 * 根据客户手机号查询客户信息
	 *
	 * @param openid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/cusInterface/getByCusTel")
	public MfCusCustomer getByCusTel(@RequestBody MfCusCustomer mfCusCustomer) throws Exception;

	/**
	 * 根据AppWXOpenid查询客户信息
	 *
	 * @param AppWXOpenid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/cusInterface/getByAppWXOpenid")
	public MfCusCustomer getByAppWXOpenid(@RequestBody MfCusCustomer mfCusCustomer) throws Exception;

	/**
	 * 根据AppQQOpenid查询客户信息
	 *
	 * @param AppQQOpenid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/cusInterface/getByAppQQOpenid")
	public MfCusCustomer getByAppQQOpenid(@RequestBody MfCusCustomer mfCusCustomer) throws Exception;

	/**
	 *
	 * 方法描述： 获取客户分类
	 *
	 * @param mfCusClassify
	 * @return
	 * @throws Exception
	 *             List<MfCusClassify>
	 * @author lwq
	 * @date 2017-10-12 上午10:45:51
	 */
	@RequestMapping(value = "/cusInterface/getNewByCusNo")
	public List<MfCusClassify> getNewByCusNo(@RequestBody MfCusClassify mfCusClassify) throws Exception;

	/**
	 *
	 * 方法描述： 获得客户分页列表
	 *
	 * @param ipage
	 * @param mfCusCustomer
	 * @return
	 * @throws Exception
	 *             Ipage
	 * @author 沈浩兵
	 * @date 2017-10-16 上午11:43:02
	 */
	@RequestMapping(value = "/cusInterface/findMfCusCustomerByPage")
	public Ipage findMfCusCustomerByPage(@RequestBody Ipage ipage) throws Exception;

	/**
	 *
	 * @Title: findPadMfCusCustomerByPage @Description:pad端获取客户信息，分进件中 和审批中。
	 *         审批通过的不获取 @param @param ipage @param @param
	 *         mfCusCustomer @param @return @param @throws Exception 参数 @return
	 *         Ipage 返回类型 @throws
	 */
	@RequestMapping(value = "/cusInterface/findPadMfCusCustomerByPage")
	public Ipage findPadMfCusCustomerByPage(@RequestBody Ipage ipage) throws Exception;

	/**
	 * 根据客户号获取客户跟踪列表
	 *
	 * @param mfCusTrack
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/cusInterface/getMfCusTraclListByCusNo")
	public List<MfCusTrack> getMfCusTraclListByCusNo(@RequestBody MfCusTrack mfCusTrack) throws Exception;

	/**
	 * 根据跟踪ID获取评论列表
	 *
	 * @param mfCusTrack
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/cusInterface/getMfCusTraclListByTarctId")
	public List<MfCusTrack> getMfCusTraclListByTarctId(@RequestBody MfCusTrack mfCusTrack) throws Exception;

	/**
	 * 插入客户跟踪
	 *
	 * @param mfCusTrack
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/cusInterface/insertMfCusTrack")
	public MfCusTrack insertMfCusTrack(@RequestBody MfCusTrack mfCusTrack) throws Exception;

	/**
	 * App插入客户跟踪
	 *
	 * @param mfCusTrack
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/cusInterface/insertMfCusTrackForApp")
	public MfCusTrack insertMfCusTrackForApp(@RequestBody MfCusTrack mfCusTrack) throws Exception;

	/**
	 *
	 * 方法描述： 插入企业客户固定资产
	 *
	 * @param mfCusCorpBaseInfo
	 * @throws Exception
	 *             void
	 * @author ywh
	 * @date 2017-10-11 下午2:34:31
	 */
	@RequestMapping(value = "/cusInterface/insertMfCusAssets")
	public void insertMfCusAssets(@RequestBody MfCusAssets mfCusAssets) throws Exception;

	/**
	 *
	 * 方法描述： App插入企业客户固定资产
	 *
	 * @param mfCusCorpBaseInfo
	 * @throws Exception
	 *             void
	 * @author ywh
	 * @date 2017-10-11 下午2:34:31
	 */
	@RequestMapping(value = "/cusInterface/insertMfCusAssetsForApp")
	public MfCusAssets insertMfCusAssetsForApp(@RequestBody MfCusAssets mfCusAssets) throws Exception;

	/**
	 *
	 * 方法描述： 根据客户号获得企业客户固定资产
	 *
	 * @param cusNo
	 * @throws Exception
	 *             void
	 * @author ywh
	 * @date 2017-10-11 下午2:34:31
	 */
	@RequestMapping(value = "/cusInterface/getMfCusAssetsById")
	public MfCusAssets getMfCusAssetsById(@RequestParam("cusNo") String cusNo) throws Exception;

	/**
	 *
	 * 方法描述： 更新企业企业客户固定资产
	 *
	 * @param mfCusPersBaseInfo
	 * @throws Exception
	 *             void
	 * @author ywh
	 * @date 2017-10-11 下午2:34:31
	 */
	@RequestMapping(value = "/cusInterface/updateMfCusAssets")
	public void updateMfCusAssets(@RequestBody MfCusAssets mfCusAssets) throws Exception;

	/**
	 * app端插入客户
	 *
	 * @param mfCusCustomer
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/cusInterface/insertForApp")
	public MfCusCustomer insertForApp(@RequestBody MfCusCustomer mfCusCustomer) throws ServiceException;

	/**
	 * 获取系统操作员名下当天客户数
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/cusInterface/getOpNoList")
	public List<Map<String, String>> getOpNoList() throws Exception;

	/**
	 * 取客户号最大的那条数据
	 *
	 * @param cusCustomer
	 * @return
	 */
	@RequestMapping(value = "/cusInterface/getMfCusCustomerByCusNoMax")
	public String getMfCusCustomerByCusNoMax(@RequestBody MfCusCustomer cusCustomer) throws Exception;

	/**
	 * 检查客户配偶信息
	 *
	 * @param cusNo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/cusInterface/checkCusFamilyInfo")
	public boolean checkCusFamilyInfo(@RequestParam("cusNo") String cusNo) throws Exception;

	/**
	 * app:更新客户表和客户基本信息表
	 *
	 * @param cusCustomer
	 * @return
	 */
	@RequestMapping(value = "/cusInterface/updateCustomerInfo")
	public void updateCustomerInfo(@RequestBody Map<String,Object> pramMap) throws Exception;

	/**
	 * 根据担保额度占用明细更新担保公司额度
	 *
	 * @param mfBusAssureDetail
	 * @throws Exception
	 * @author WangChao
	 * @date 2017-10-25 下午2:05:36
	 */
	@RequestMapping(value = "/cusInterface/occupyUsableAmt")
	public void occupyUsableAmt(@RequestBody MfBusAssureDetail mfBusAssureDetail) throws Exception;

	/**
	 * 审批否决、还款结清等恢复额度
	 *
	 * @param mfBusAssureDetail
	 * @param recoveryAmt
	 *            恢复金额
	 * @throws Exception
	 * @author WangChao
	 * @date 2017-10-25 下午3:26:24
	 */
	@RequestMapping(value = "/cusInterface/recoveryUsableAmt")
	public void recoveryUsableAmt(@RequestBody MfBusAssureDetail mfBusAssureDetail,@RequestParam("recoveryAmt") Double recoveryAmt)
			throws Exception;

	/**
	 * 获取所有渠道商
	 *
	 * @param mfBusTrench
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/cusInterface/getChannelListPage")
	public Ipage getChannelListPage(@RequestBody Ipage ipage) throws Exception;

	/**
	 *
	 * 方法描述： 根据客户基础类型获得客户类型列表
	 *
	 * @param baseType
	 * @return
	 * @throws Exception
	 *             List<MfCusType>
	 * @author 沈浩兵
	 * @date 2017-10-28 下午12:06:45
	 */
	@RequestMapping(value = "/cusInterface/getAllByBaseTypeList")
	public List<MfCusType> getAllByBaseTypeList(@RequestParam("baseType") String baseType) throws Exception;

	/**
	 *
	 * 方法描述： 获取所有的名下企业信息（包含手动录入的）
	 *
	 * @param mfCusPersonCorp
	 * @return
	 * @throws Exception
	 *             List<MfCusPersonCorp>
	 * @author lwq
	 * @date 2017-10-28 下午12:01:37
	 */
	@RequestMapping(value = "/cusInterface/getAllPersonCorpList")
	public List<MfCusPersonCorp> getAllPersonCorpList(@RequestBody MfCusPersonCorp mfCusPersonCorp) throws Exception;

	/**
	 * 根据条件查询一个
	 *
	 * @param cusCustomer
	 * @return
	 * @throws Exception
	 * @author 张冬磊
	 * @date 2017-11-3 上午11:37:32
	 */
	@RequestMapping(value = "/cusInterface/getOneByConditions")
	public MfCusCustomer getOneByConditions(@RequestBody MfCusCustomer cusCustomer) throws Exception;

	/**
	 *
	 * 方法描述： 获得保证人信息
	 *
	 * @param assureCompanyId
	 * @return
	 * @throws Exception
	 *             MfCusAssureCompany
	 * @author 沈浩兵
	 * @date 2017-11-8 下午10:33:45
	 */
	@RequestMapping(value = "/cusInterface/getCusAssureCompanyById")
	public MfCusAssureCompany getCusAssureCompanyById(@RequestParam("assureCompanyId") String assureCompanyId) throws Exception;

	/**
	 * 根据操作员编号获取今日此操作员下的潜在客户数量
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/cusInterface/getCntByOpNo")
	public int getCntByOpNo(@RequestBody MfCusCustomer mfCusCustomer) throws Exception;

	/**
	 *
	 * 方法描述：客户信用
	 *
	 * @param cusNo
	 * @return
	 * @throws Exception
	 *             MfCusPersonCreditInfo
	 * @author lwq
	 * @date 2017-11-20 下午4:00:08
	 */
	@RequestMapping(value = "/cusInterface/getCusPerCreditInfo")
	public MfCusPersonCreditInfo getCusPerCreditInfo(@RequestParam("cusNo") String cusNo) throws Exception;

	/**
	 *
	 * 方法描述： 更新担保公司可使用金额
	 *
	 * @param mfCusAssureCompany
	 * @return
	 * @throws Exception
	 *             MfCusAssureCompany
	 * @author 沈浩兵
	 * @date 2017-11-25 上午11:30:22
	 */
	@RequestMapping(value = "/cusInterface/updateCusAssureCompany")
	public MfCusAssureCompany updateCusAssureCompany(@RequestBody MfCusAssureCompany mfCusAssureCompany)
			throws Exception;

	/**
	 *
	 * 方法描述： B端插入客户登记信息
	 *
	 * @param mfCusCustomer
	 * @throws Exception
	 *             void
	 * @author ywh
	 * @date 2017-12-04 下午2:44:27
	 */
	@RequestMapping(value = "/cusInterface/addCustomer")
	public MfCusCustomer addCustomer(@RequestBody Map<String,Object> pramMap) throws Exception;

	/**
	 *
	 * 方法描述： 获取最新的职业信息
	 *
	 * @param mfCusPersonJob
	 * @return
	 * @throws Exception
	 *             MfCusPersonJob
	 * @author zhs
	 * @date 2017-12-5 下午2:47:33
	 */
	@RequestMapping(value = "/cusInterface/getNewById")
	public MfCusPersonJob getNewById(@RequestBody MfCusPersonJob mfCusPersonJob) throws Exception;

	/**
	 *
	 * 方法描述： 获取个人客户信息（包括基本信息和其他信息）
	 *
	 * @param cusNo
	 * @return
	 * @throws Exception
	 *             Map<String,Object>
	 * @author zhs
	 * @date 2017-12-5 下午4:39:24
	 */
	@RequestMapping(value = "/cusInterface/getCusPersInfo")
	public Map<String, Object> getCusPersInfo(@RequestParam("cusNo") String cusNo) throws Exception;

	/**
	 *
	 * 方法描述：新增农户经济状况
	 *
	 * @param mfCusFarmerEconoInfo
	 * @return MfCusFarmerEconoInfo
	 * @author YaoWenHao
	 * @date 2017-12-5 下午7:15:44
	 */
	@RequestMapping(value = "/cusInterface/addMfCusFarmerEconoInfo")
	public MfCusFarmerEconoInfo addMfCusFarmerEconoInfo(@RequestBody MfCusFarmerEconoInfo mfCusFarmerEconoInfo);

	/**
	 *
	 * 方法描述： 根据客户号获取农户经济状况
	 *
	 * @param mfCusFarmerEconoInfo
	 * @return MfCusFarmerEconoInfo
	 * @author YaoWenHao
	 * @date 2017-12-5 下午7:21:07
	 */
	@RequestMapping(value = "/cusInterface/getMfCusFarmerEconoInfo")
	public MfCusFarmerEconoInfo getMfCusFarmerEconoInfo(@RequestBody MfCusFarmerEconoInfo mfCusFarmerEconoInfo);

	/**
	 *
	 * 方法描述： 更新农户经济状况
	 *
	 * @param mfCusFarmerEconoInfo
	 *            void
	 * @author YaoWenHao
	 * @date 2017-12-5 下午7:22:41
	 */
	@RequestMapping(value = "/cusInterface/updateMfCusFarmerEconoInfo")
	public void updateMfCusFarmerEconoInfo(@RequestBody MfCusFarmerEconoInfo mfCusFarmerEconoInfo);

	/**
	 *
	 * 方法描述： 新增农户收支情况
	 *
	 * @param mfCusFarmerIncExpe
	 * @return MfCusFarmerIncExpe
	 * @author YaoWenHao
	 * @date 2017-12-5 下午7:25:09
	 */
	@RequestMapping(value = "/cusInterface/addMfCusFarmerIncExpe")
	public MfCusFarmerIncExpe addMfCusFarmerIncExpe(@RequestBody MfCusFarmerIncExpe mfCusFarmerIncExpe);

	/**
	 *
	 * 方法描述： 根据客户号获取农户收支情况
	 *
	 * @param mfCusFarmerIncExpe
	 * @return MfCusFarmerIncExpe
	 * @author YaoWenHao
	 * @date 2017-12-5 下午7:30:54
	 */
	@RequestMapping(value = "/cusInterface/getMfCusFarmerIncExpe")
	public MfCusFarmerIncExpe getMfCusFarmerIncExpe(@RequestBody MfCusFarmerIncExpe mfCusFarmerIncExpe);

	/**
	 *
	 * 方法描述： 更新客户号获取农户收支情况
	 *
	 * @param mfCusFarmerIncExpe
	 *            void
	 * @author YaoWenHao
	 * @date 2017-12-5 下午7:31:23
	 */
	@RequestMapping(value = "/cusInterface/updateMfCusFarmerIncExpe")
	public void updateMfCusFarmerIncExpe(@RequestBody MfCusFarmerIncExpe mfCusFarmerIncExpe);

	/**
	 *
	 * 方法描述： app端新增个人流动资产
	 *
	 * @param mfCusPersonFlowAssetsInfo
	 * @return
	 * @throws Exception
	 *             MfCusPersonFlowAssetsInfo
	 * @author YaoWenHao
	 * @date 2017-12-6 下午3:35:41
	 */
	@RequestMapping(value = "/cusInterface/insertMfCusPersonFlowAssetsInfoForApp")
	public MfCusPersonFlowAssetsInfo insertMfCusPersonFlowAssetsInfoForApp(
			@RequestBody MfCusPersonFlowAssetsInfo mfCusPersonFlowAssetsInfo) throws Exception;

	/**
	 *
	 * 方法描述： app端更新个人流动资产
	 *
	 * @param mfCusPersonFlowAssetsInfo
	 * @return
	 * @throws Exception
	 *             MfCusPersonFlowAssetsInfo
	 * @author YaoWenHao
	 * @date 2017-12-6 下午3:35:41
	 */
	@RequestMapping(value = "/cusInterface/updateMfCusPersonFlowAssetsInfoForApp")
	public void updateMfCusPersonFlowAssetsInfoForApp(@RequestBody MfCusPersonFlowAssetsInfo mfCusPersonFlowAssetsInfo)
			throws Exception;

	/**
	 *
	 * 方法描述： app端增加个人名下企业
	 *
	 * @param mfCusPersonCorp
	 * @return
	 * @throws Exception
	 *             MfCusPersonCorp
	 * @author YaoWenHao
	 * @date 2017-12-6 下午3:52:31
	 */
	@RequestMapping(value = "/cusInterface/insertMfCusPersonCorpForApp")
	public MfCusPersonCorp insertMfCusPersonCorpForApp(@RequestBody MfCusPersonCorp mfCusPersonCorp) throws Exception;

	/**
	 *
	 * 方法描述： app端更新个人名下企业
	 *
	 * @param mfCusPersonCorp
	 * @throws Exception
	 *             void
	 * @author YaoWenHao
	 * @date 2017-12-6 下午4:00:46
	 */
	@RequestMapping(value = "/cusInterface/updateMfCusPersonCorpForApp")
	public void updateMfCusPersonCorpForApp(@RequestBody MfCusPersonCorp mfCusPersonCorp) throws Exception;

	/**
	 * 方法描述： 更新客户信息
	 *
	 * @return
	 * @throws Exception
	 *             MfCusCustomer
	 * @author YuShuai
	 * @date 2017-12-12 下午6:06:11
	 */
	@RequestMapping(value = "/cusInterface/updateMfCusCustomer")
	public void updateMfCusCustomer(@RequestBody MfCusCustomer mfCusCustomer) throws Exception;

	/**
	 * 方法描述： 根据客户号删除客户
	 *
	 * @param cusNo
	 * @throws Exception
	 *             void
	 * @author YuShuai
	 * @date 2017-12-12 下午6:30:51
	 */
	@RequestMapping(value = "deleMfCuscustomer")
	public void deleMfCuscustomer(@RequestParam("cusNo") String cusNo) throws Exception;
	/**
	 *
	 * 方法描述： 获取核心开户行列表
	 *
	 * @param ipage
	 * @param mfCnapsBankInfo
	 * @return
	 * @throws Exception
	 *             Ipage
	 * @author YaoWenHao
	 * @date 2017-12-15 下午2:22:09
	 */
	@RequestMapping(value = "/cusInterface/findMfCnapsBankInfoByPage")
	public Ipage findMfCnapsBankInfoByPage(@RequestBody Ipage ipage) throws Exception;

	/**
	 * @Description:获取申请人调查信息
	 * @param mfCusApplyPersonSurvey
	 * @return
	 * @throws Exception
	 * @author: 李伟
	 * @date: 2017-12-20 下午5:53:08
	 */
	@RequestMapping(value = "/cusInterface/getMfCusApplyPersonSurvey")
	public MfCusApplyPersonSurvey getMfCusApplyPersonSurvey(@RequestBody MfCusApplyPersonSurvey mfCusApplyPersonSurvey)
			throws Exception;

	/**
	 * @Description:插入申请人调查信息
	 * @param mfCusApplyPersonSurvey
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/cusInterface/insertMfCusApplyPersonSurvey")
	public void insertMfCusApplyPersonSurvey(@RequestBody MfCusApplyPersonSurvey mfCusApplyPersonSurvey)
			throws Exception;

	/**
	 * @Description:获取申请人调查信息list
	 * @param mfCusApplyPersonSurvey
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/cusInterface/getMfCusApplyPersonSurveyList")
	public List<MfCusApplyPersonSurvey> getMfCusApplyPersonSurveyList(
			@RequestBody MfCusApplyPersonSurvey mfCusApplyPersonSurvey) throws Exception;

	/**
	 * 方法描述： 根据bankId删除银行卡信息
	 *
	 * @param mfCusBankAccManage
	 * @throws Exception
	 *             void
	 * @author YuShuai
	 * @date 2017-12-19 下午7:20:12
	 */
	@RequestMapping(value = "/cusInterface/deleteMfCusBankAccManage")
	public void deleteMfCusBankAccManage(@RequestBody MfCusBankAccManage mfCusBankAccManage) throws Exception;

	/**
	 * 方法描述： 删除客户表单
	 *
	 * @param mfCusTable
	 * @throws Exception
	 *             void
	 * @author YuShuai
	 * @date 2017-12-20 下午2:30:14
	 */
	@RequestMapping(value = "/cusInterface/deleteCusTable")
	public void deleteCusTable(@RequestBody MfCusTable mfCusTable) throws Exception;

	/**
	 * 方法描述： 获取客户类型表单列表
	 *
	 * @param mfCusFormConfig
	 * @return
	 * @throws Exception
	 *             List<MfCusFormConfig>
	 * @author YuShuai
	 * @date 2017-12-20 下午3:03:50
	 */
	@RequestMapping(value = "/cusInterface/getMfCusFormConfig")
	public List<MfCusFormConfig> getMfCusFormConfig(@RequestBody MfCusFormConfig mfCusFormConfig) throws Exception;

	/**
	 * 方法描述： 插入客户表单
	 *
	 * @param mfCusTable
	 * @throws Exception
	 *             void
	 * @author YuShuai
	 * @date 2017-12-20 下午3:05:44
	 */
	@RequestMapping(value = "/cusInterface/insertMfCusTable")
	public void insertMfCusTable(@RequestBody MfCusTable mfCusTable) throws Exception;

	/**
	 * 方法描述： 删除社会关系
	 *
	 * @param mfCusFamilyInfo
	 * @throws Exception
	 *             void
	 * @author YuShuai
	 * @date 2017-12-20 下午3:37:40
	 */
	@RequestMapping(value = "/cusInterface/deleteMfCusFamily")
	public void deleteMfCusFamily(@RequestBody MfCusFamilyInfo mfCusFamilyInfo) throws Exception;

	/**
	 * 方法描述：铁甲网---根据证件号码删除信息
	 *
	 * @param idNum
	 * @throws Exception
	 *             void
	 * @author YuShuai
	 * @date 2017-12-21 下午2:56:19
	 */
	@RequestMapping(value = "/cusInterface/deleteMfWeChatCusInfo")
	public void deleteMfWeChatCusInfo(@RequestParam("idNum") String idNum) throws Exception;

	/**
	 *
	 * 方法描述： 判断客户的表单是否允许编辑
	 *
	 * @param cusNo
	 * @param appId
	 * @param busType
	 * @return String
	 * @author zhs
	 * @date 2017-12-23 下午6:23:20
	 */
	@RequestMapping(value = "/cusInterface/validateCusFormModify")
	public String validateCusFormModify(@RequestParam("cusNo") String cusNo,@RequestParam("appId") String appId,@RequestParam("busType") String busType,@RequestParam("regNo") String regNo) throws Exception;

	@RequestMapping(value = "/cusInterface/validateCreditCusFormModify")
	public String validateCreditCusFormModify(@RequestParam("cusNo") String cusNo,@RequestParam("appId") String appId,@RequestParam("busType") String busType,@RequestParam("regNo") String regNo) throws Exception;

	/**
	 *
	 * 方法描述： 获得征信查询客户数据源。包括借款客户、配偶、业务关联担保人
	 *
	 * @param cusNo
	 * @return
	 * @throws Exception
	 *             JSONArray
	 * @author 沈浩兵
	 * @date 2017-12-26 下午5:53:52
	 */
	@RequestMapping(value = "/cusInterface/getCreditCusJSONArray")
	public JSONArray getCreditCusJSONArray(@RequestParam("cusNo") String cusNo,@RequestParam("appId") String appId) throws Exception;

	/**
	 *
	 * 方法描述： 征信查询
	 *
	 * @param mfCreditQueryRecordInfo
	 * @return
	 * @throws Exception
	 *             Map<String,Object>
	 * @author 沈浩兵
	 * @date 2017-11-30 上午9:49:42
	 */
	@RequestMapping(value = "/cusInterface/doCreditQuery")
	public Map<String, Object> doCreditQuery(@RequestBody MfCreditQueryRecordInfo mfCreditQueryRecordInfo)
			throws Exception;

	/**
	 *
	 * 方法描述：根据客户号、查询客户类型、查询客户关联编号获得最新的征信结果内容
	 *
	 * @param mfCreditQueryRecordInfo
	 *            目前包含
	 * @param cusNo
	 *            借款人客户号
	 * @param queryCusType
	 *            查询客户类型
	 * @param relId
	 *            查询客户关联编号 （查询借款人：借款人客户号，查询担保人：担保人客户号，查询配偶：配偶信息主键）
	 * @param uuid
	 *            存放安卓的IMEI或是苹果的uuid
	 * @param equipmentType
	 *            查询设置类型:mobile 移动端，pc pc端
	 * @return
	 * @throws Exception
	 *             Map<String,Object>
	 * @author YaoWenHao
	 * @date 2017-12-26 下午5:15:05
	 */
	@RequestMapping(value = "/cusInterface/getNewestCreditHtml")
	public Map<String, Object> getNewestCreditHtml(@RequestBody MfCreditQueryRecordInfo mfCreditQueryRecordInfo)
			throws Exception;

	/**
	 * @Description:获取配偶信息
	 * @param mfCusApplySpouseSurvey
	 * @return
	 * @throws Exception
	 * @author: 李伟
	 * @date: 2017-12-27 下午5:27:19
	 */
	@RequestMapping(value = "/cusInterface/getMfCusApplySpouseSurvey")
	public MfCusApplySpouseSurvey getMfCusApplySpouseSurvey(@RequestBody MfCusApplySpouseSurvey mfCusApplySpouseSurvey)
			throws Exception;

	/**
	 * @Description:插入配偶信息
	 * @param mfCusApplySpouseSurvey
	 * @return
	 * @throws Exception
	 * @author: 李伟
	 * @date: 2017-12-27 下午5:27:19
	 */
	@RequestMapping(value = "/cusInterface/insertMfCusApplySpouseSurvey")
	public void insertMfCusApplySpouseSurvey(@RequestBody MfCusApplySpouseSurvey mfCusApplySpouseSurvey)
			throws Exception;

	/**
	 *
	 * 方法描述： 获得征信查询提交时选择上传ftp要件信息
	 *
	 * @param cusNo
	 * @param appId
	 * @return
	 * @throws Exception
	 *             Map<String,Object>
	 * @author 沈浩兵
	 * @date 2017-12-28 下午1:36:51
	 */
	@RequestMapping(value = "/cusInterface/getSelectCreditDocDataByAppId")
	public Map<String, Object> getSelectCreditDocDataByAppId(@RequestParam("cusNo") String cusNo,@RequestParam("appId") String appId) throws Exception;

	/**
	 * @Description:获取收入流水信息
	 * @param mfCusTuneOutside
	 * @return
	 * @throws Exception
	 * @author: 李伟
	 * @date: 2017-12-23 上午10:11:12
	 */
	@RequestMapping(value = "/cusInterface/getMfCusPersonIncomeSurvey")
	public MfCusPersonIncomeSurvey getMfCusPersonIncomeSurvey(
			@RequestBody MfCusPersonIncomeSurvey mfCusPersonIncomeSurvey) throws Exception;

	/**
	 * @Description:插入收入流水信息
	 * @param MfCusPersonIncomeSurvey
	 * @return
	 * @throws Exception
	 * @author: yjk
	 */
	@RequestMapping(value = "/cusInterface/insertMfCusPersonIncomeSurvey")
	public void insertMfCusPersonIncomeSurvey(@RequestBody MfCusPersonIncomeSurvey mfCusPersonIncomeSurvey)
			throws Exception;

	/**
	 * @Description:获取收入流水信息
	 * @param MfCusPersonIncomeSurvey
	 * @return
	 * @throws Exception
	 * @author: yjk
	 */
	@RequestMapping(value = "/cusInterface/getMfCusPersonIncomeSurveyList")
	public List<MfCusPersonIncomeSurvey> getMfCusPersonIncomeSurveyList(
			@RequestBody MfCusPersonIncomeSurvey mfCusPersonIncomeSurvey) throws Exception;

	/**
	 * @Description:企业法人对外任职信息
	 * @param MfCusLegalEmployInfo
	 * @return
	 * @throws Exception
	 * @author: yjk
	 */
	@RequestMapping(value = "/cusInterface/insertMfCusLegalEmployInfo")
	public void insertMfCusLegalEmployInfo(@RequestBody MfCusLegalEmployInfo mfCusLegalEmployInfo) throws Exception;

	/**
	 * @Description:获取企业法人对外任职信息
	 * @param MfCusLegalEmployInfo
	 * @return
	 * @throws Exception
	 * @author: yjk
	 */
	@RequestMapping(value = "/cusInterface/findMfCusLegalEmployInfoList")
	public List<MfCusLegalEmployInfo> findMfCusLegalEmployInfoList(
			@RequestBody MfCusLegalEmployInfo mfCusLegalEmployInfo) throws Exception;

	/**
	 * @Description:企业法人对外投资信息
	 * @param MfCusLegalEmployInfo
	 * @return
	 * @throws Exception
	 * @author: yjk
	 */
	@RequestMapping(value = "/cusInterface/insertMfCusLegalEquityInfo")
	public void insertMfCusLegalEquityInfo(@RequestBody MfCusLegalEquityInfo mfCusLegalEquityInfo) throws Exception;

	/**
	 * @Description:获取企业法人对外投资信息
	 * @param MfCusLegalEquityInfo
	 * @return
	 * @throws Exception
	 * @author: yjk
	 */
	@RequestMapping(value = "/cusInterface/findMfCusLegalEquityInfoList")
	public List<MfCusLegalEquityInfo> findMfCusLegalEquityInfoList(
			@RequestBody MfCusLegalEquityInfo mfCusLegalEquityInfo) throws Exception;

	/**
	 *
	 * 方法描述： 身份核查 mfIdentityCheckRecordInfo
	 *
	 * @return
	 * @throws Exception
	 *             Map<String,Object>
	 * @author 沈浩兵
	 * @date 2018-1-29 上午10:06:00
	 */
	@RequestMapping(value = "/cusInterface/doIdentityCheck")
	public Map<String, Object> doIdentityCheck(@RequestBody MfIdentityCheckRecordInfo mfIdentityCheckRecordInfo)
			throws Exception;

	/**
	 * 方法描述： 获得渠道及其子渠道编号字符串
	 *
	 * @param mfBusTrench
	 * @return
	 * @throws Exception String
	 * @author 沈浩兵
	 * @date 2018-3-8 下午2:43:25
	 */
	@RequestMapping(value = "/cusInterface/getTrenchChildStr")
	public String getTrenchChildStr(@RequestBody MfBusTrench mfBusTrench) throws Exception;

	/**
	 * 查询渠道操作员
	 *
	 * @param webCusLineReg
	 * @return
	 * @throws Exception
	 * @author wangchao
	 * @date 2018年4月16日 上午11:03:30
	 */
	@RequestMapping(value = "/cusInterface/getWebCusLineReg")
	public WebCusLineReg getWebCusLineReg(@RequestBody WebCusLineReg webCusLineReg) throws Exception;

	/**
	*@desc 查询客户的社会关系，实体那个字段有值就作为查询条件
	*@author lwq
	*@date 2018/10/9 18:10
	*@parm [mfCusFamilyInfo]
	*@return app.component.cus.entity.MfCusFamilyInfo
	**/
	@RequestMapping(value = "/cusInterface/getMfCusFamilyInfo")
	public MfCusFamilyInfo getMfCusFamilyInfo(@RequestBody MfCusFamilyInfo mfCusFamilyInfo) throws Exception;

	/**
	 * 根据客户类型获取客户列表
	 * @param mfCusCustomer
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/cusInterface/getMfCusCustomerListByCusType")
	public List <MfCusCustomer> getMfCusCustomerListByCusType(@RequestBody MfCusCustomer mfCusCustomer) throws Exception;

	@RequestMapping(value = "/cusInterface/getByCusName")
	public List <MfCusCustomer> getByCusName(@RequestParam("cusName") String cusName) throws Exception;

	/**
	 * @Description 获取授信移交列表数据
	 * @Author zhaomingguang
	 * @DateTime 2019/11/1 17:44
	 * @Param [pageNo, pageSize, tableId, tableType, ajaxData, cusMngNo]
	 * @return java.util.Map<java.lang.String,java.lang.Object>
	 */
	@RequestMapping(value = "/cusInterface/getCreditCusByCusMngNo")
    Ipage getCreditCusByCusMngNo(Ipage ipage)throws Exception;

	@RequestMapping(value = "/cusInterface/getCusNewWorkMap")
    Map<String,String> getCusNewWorkMap(@RequestParam("cusNo") String cusNo)throws Exception;
	@RequestMapping(value = "/cusInterface/getTongDunByCusNo")
    String  getTongDunByCusNo(@RequestParam("cusNo") String cusNo,@RequestParam("appId")String appId)throws Exception;
}
