/**
 * Copyright (C) DXHM 版权所有
 * 文件名： mobileservicecusinterface.java
 * 包名： app.component.interfacesinterface
 * 说明：移动服务客户模块相关接口
 * @author 沈浩兵
 * @date 2017-10-11 上午10:10:17
 * @version V1.0
 */ 
package app.component.interfacesinterface;
import java.io.File;
import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.cus.custracing.entity.MfCusTrack;
import app.component.cus.entity.MfCusApplyPersonSurvey;
import app.component.cus.entity.MfCusApplySpouseSurvey;
import app.component.cus.entity.MfCusAssets;
import app.component.cus.entity.MfCusBankAccManage;
import app.component.cus.entity.MfCusCorpBaseInfo;
import app.component.cus.entity.MfCusCustomer;
import app.component.cus.entity.MfCusEquityInfo;
import app.component.cus.entity.MfCusFamilyInfo;
import app.component.cus.entity.MfCusFarmerEconoInfo;
import app.component.cus.entity.MfCusFarmerIncExpe;
import app.component.cus.entity.MfCusGuaranteeOuter;
import app.component.cus.entity.MfCusHighInfo;
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
import app.component.cus.entity.MfCusTable;
import app.component.interfaces.mobileinterface.entity.MfAppCusIntegrity;
import app.util.toolkit.Ipage;
import net.sf.json.JSONArray;

/**
 * 类名： mobileservicecusinterface
 * 描述：移动服务客户模块相关接口
 * @author 沈浩兵
 * @date 2017-10-11 上午10:10:17
 *
 *
 */
@FeignClient("mftcc-platform-factor")
public interface MobileServiceCusInterfaceFeign {
	/**
	 * 
	 * 方法描述： 插入客户登记信息
	 * @param mfCusCustomer
	 * @throws Exception
	 * void
	 * @author 沈浩兵
	 * @date 2017-10-11 下午2:44:27
	 */
	@RequestMapping(value = "/mobileServiceCusInterface/insertMfCusCustomer")
	public MfCusCustomer insertMfCusCustomer(@RequestBody MfCusCustomer mfCusCustomer) throws Exception;
	
	/**
	 * 
	 * 方法描述： 插入客户基本信息
	 * @param mfCusPersBaseInfo
	 * @throws Exception
	 * void
	 * @author 沈浩兵
	 * @date 2017-10-11 下午2:34:31
	 */
	@RequestMapping(value = "/mobileServiceCusInterface/insertPersBaseInfo")
	public MfCusPersBaseInfo insertPersBaseInfo(@RequestBody MfCusPersBaseInfo mfCusPersBaseInfo) throws Exception;
	/**
	 * 
	 * 方法描述： 职业信息登记
	 * @param mfCusPersonJob
	 * @throws Exception
	 * void
	 * @author 沈浩兵
	 * @date 2017-10-11 下午3:43:41
	 */
	@RequestMapping(value = "/mobileServiceCusInterface/insertMfCusPersonJob")
	public MfCusPersonJob insertMfCusPersonJob(@RequestBody MfCusPersonJob mfCusPersonJob) throws Exception;
	/**
	 * 
	 * 方法描述： 收支情况登记
	 * @param mfCusPersonIncExpe
	 * @throws Exception
	 * void
	 * @author 沈浩兵
	 * @date 2017-10-11 下午3:45:59
	 */
	@RequestMapping(value = "/mobileServiceCusInterface/insertMfCusPersonIncExpe")
	public MfCusPersonIncExpe insertMfCusPersonIncExpe(@RequestBody MfCusPersonIncExpe mfCusPersonIncExpe) throws Exception;
	/**
	 * 
	 * 方法描述： 社会关系登记
	 * @param mfCusFamilyInfo
	 * @throws Exception
	 * void
	 * @author 沈浩兵
	 * @date 2017-10-11 下午3:47:31
	 */
	@RequestMapping(value = "/mobileServiceCusInterface/insertMfCusFamilyInfo")
	public MfCusFamilyInfo insertMfCusFamilyInfo(@RequestBody MfCusFamilyInfo mfCusFamilyInfo) throws Exception;
	/**
	 * 
	 * 方法描述： 账号管理登记
	 * @param mfCusBankAccManage
	 * @throws Exception
	 * void
	 * @author 沈浩兵
	 * @date 2017-10-11 下午3:48:20
	 */
	@RequestMapping(value = "/mobileServiceCusInterface/insertMfCusBankAccManage")
	public MfCusBankAccManage insertMfCusBankAccManage(@RequestBody MfCusBankAccManage mfCusBankAccManage) throws Exception;
	/**
	 * 
	 * 方法描述： Pad账号管理登记
	 * @param mfCusBankAccManage
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author YaoWenHao
	 * @date 2017-12-15 上午11:02:16
	 */
	@RequestMapping(value = "/mobileServiceCusInterface/insertMfCusBankAccManageForPad")
	public Map<String , Object> insertMfCusBankAccManageForPad(@RequestBody MfCusBankAccManage mfCusBankAccManage) throws Exception;
	/**
	 * 
	 * 方法描述： 信用情况登记
	 * @param mfCusPersonCreditInfo
	 * @throws Exception
	 * void
	 * @author 沈浩兵
	 * @date 2017-10-11 下午3:49:11
	 */
	@RequestMapping(value = "/mobileServiceCusInterface/insertMfCusPersonCreditInfo")
	public MfCusPersonCreditInfo insertMfCusPersonCreditInfo(@RequestBody MfCusPersonCreditInfo mfCusPersonCreditInfo) throws Exception;
	/**
	 * 
	 * 方法描述： 资产信息登记
	 * @param mfCusPersonAssetsInfo
	 * @throws Exception
	 * void
	 * @author 沈浩兵
	 * @date 2017-10-11 下午3:51:16
	 */
	@RequestMapping(value = "/mobileServiceCusInterface/insertMfCusPersonAssetsInfo")
	public MfCusPersonAssetsInfo insertMfCusPersonAssetsInfo(@RequestBody MfCusPersonAssetsInfo mfCusPersonAssetsInfo) throws Exception;
	/**
	 * 
	 * 方法描述： 负债信息登记
	 * @param mfCusPersonDebtInfo
	 * @throws Exception
	 * void
	 * @author 沈浩兵
	 * @date 2017-10-11 下午3:52:38
	 */
	@RequestMapping(value = "/mobileServiceCusInterface/insertMfCusPersonDebtInfo")
	public MfCusPersonDebtInfo insertMfCusPersonDebtInfo(@RequestBody MfCusPersonDebtInfo mfCusPersonDebtInfo) throws Exception;
	/**
	 * 
	 * 方法描述： 对外担保情况登记
	 * @param mfCusGuaranteeOuter
	 * @throws Exception
	 * void
	 * @author 沈浩兵
	 * @date 2017-10-11 下午3:59:10
	 */
	@RequestMapping(value = "/mobileServiceCusInterface/insertMfCusGuaranteeOuter")
	public MfCusGuaranteeOuter insertMfCusGuaranteeOuter(@RequestBody MfCusGuaranteeOuter mfCusGuaranteeOuter) throws Exception;
	/**
	 * 
	 * 方法描述： 更新客户登记信息
	 * @param mfCusCustomer
	 * @throws Exception
	 * void
	 * @author 沈浩兵
	 * @date 2017-10-11 下午2:45:06
	 */
	@RequestMapping(value = "/mobileServiceCusInterface/updateMfCusCustomer")
	public Map<String, Object> updateMfCusCustomer(@RequestBody MfCusCustomer mfCusCustomer) throws Exception;
	/**
	 * 
	 * 方法描述： 更新客户基本信息
	 * @param mfCusPersBaseInfo
	 * @throws Exception
	 * void
	 * @author 沈浩兵
	 * @date 2017-10-11 下午2:34:31
	 */
	@RequestMapping(value = "/mobileServiceCusInterface/updatePersBaseInfo")
	public Map<String, Object> updatePersBaseInfo(@RequestBody MfCusPersBaseInfo mfCusPersBaseInfo) throws Exception;
	/**
	 * 
	 * 方法描述： 更新职业信息
	 * @param mfCusPersonJob
	 * @throws Exception
	 * void
	 * @author 沈浩兵
	 * @date 2017-10-11 下午3:43:41
	 */
	@RequestMapping(value = "/mobileServiceCusInterface/updateMfCusPersonJob")
	public Map<String, Object> updateMfCusPersonJob(@RequestBody MfCusPersonJob mfCusPersonJob) throws Exception;
	/**
	 * 
	 * 方法描述： 更新收支情况
	 * @param mfCusPersonIncExpe
	 * @throws Exception
	 * void
	 * @author 沈浩兵
	 * @date 2017-10-11 下午3:45:59
	 */
	@RequestMapping(value = "/mobileServiceCusInterface/updateMfCusPersonIncExpe")
	public Map<String, Object> updateMfCusPersonIncExpe(@RequestBody MfCusPersonIncExpe mfCusPersonIncExpe) throws Exception;
	/**
	 * 
	 * 方法描述  更新社会关系
	 * @param mfCusFamilyInfo
	 * @throws Exception
	 * void
	 * @author 沈浩兵
	 * @date 2017-10-11 下午3:47:31
	 */
	@RequestMapping(value = "/mobileServiceCusInterface/updateMfCusFamilyInfo")
	public Map<String, Object> updateMfCusFamilyInfo(@RequestBody MfCusFamilyInfo mfCusFamilyInfo) throws Exception;
	/**
	 * 
	 * 方法描述： 更新账号管理
	 * @param mfCusBankAccManage
	 * @throws Exception
	 * void
	 * @author 沈浩兵
	 * @date 2017-10-11 下午3:48:20
	 */
	@RequestMapping(value = "/mobileServiceCusInterface/updateMfCusBankAccManage")
	public Map<String, Object> updateMfCusBankAccManage(@RequestBody MfCusBankAccManage mfCusBankAccManage) throws Exception;
	/**
	 * 
	 * 方法描述： 更新信用情况
	 * @param mfCusPersonCreditInfo
	 * @throws Exception
	 * void
	 * @author 沈浩兵
	 * @date 2017-10-11 下午3:49:11
	 */
	@RequestMapping(value = "/mobileServiceCusInterface/updateMfCusPersonCreditInfo")
	public Map<String, Object> updateMfCusPersonCreditInfo(@RequestBody MfCusPersonCreditInfo mfCusPersonCreditInfo) throws Exception;
	/**
	 * 
	 * 方法描述： 更新资产信息
	 * @param mfCusPersonAssetsInfo
	 * @throws Exception
	 * void
	 * @author 沈浩兵
	 * @date 2017-10-11 下午3:51:16
	 */
	@RequestMapping(value = "/mobileServiceCusInterface/updateMfCusPersonAssetsInfo")
	public Map<String, Object> updateMfCusPersonAssetsInfo(@RequestBody MfCusPersonAssetsInfo mfCusPersonAssetsInfo) throws Exception;
	/**
	 * 
	 * 方法描述： 更新负债信息
	 * @param mfCusPersonDebtInfo
	 * @throws Exception
	 * void
	 * @author 沈浩兵
	 * @date 2017-10-11 下午3:52:38
	 */
	@RequestMapping(value = "/mobileServiceCusInterface/updateMfCusPersonDebtInfo")
	public Map<String, Object> updateMfCusPersonDebtInfo(@RequestBody MfCusPersonDebtInfo mfCusPersonDebtInfo) throws Exception;
	/**
	 * 
	 * 方法描述： 更新对外担保情况
	 * @param mfCusGuaranteeOuter
	 * @throws Exception
	 * void
	 * @author 沈浩兵
	 * @date 2017-10-11 下午3:59:10
	 */
	@RequestMapping(value = "/mobileServiceCusInterface/updateMfCusGuaranteeOuter")
	public Map<String, Object> updateMfCusGuaranteeOuter(@RequestBody MfCusGuaranteeOuter mfCusGuaranteeOuter) throws Exception;
	/**
	 * 
	 * 方法描述： 根据客户号获得客户登记信息
	 * @param cusNo
	 * @return
	 * @throws Exception
	 * MfCusCustomer
	 * @author 沈浩兵
	 * @date 2017-10-11 下午5:25:34
	 */
	@RequestMapping(value = "/mobileServiceCusInterface/getMfCusCustomer")
	public MfCusCustomer getMfCusCustomer(@RequestBody String cusNo) throws Exception;
	@RequestMapping(value = "/mobileServiceCusInterface/getMfCusCustomerByIdNum")
	public Map<String, Object> getMfCusCustomerByIdNum(@RequestBody String IdNum);
	@RequestMapping(value = "/mobileServiceCusInterface/getMfCusCustomerByOpenid")
	public Map<String, Object> getMfCusCustomerByOpenid(@RequestBody String openid);
	/**
	 * 
	 * 方法描述： 根据客户号获得客户基本信息
	 * @param mfCusPersBaseInfo
	 * @throws Exception
	 * void
	 * @author 沈浩兵
	 * @date 2017-10-11 下午2:34:31
	 */
	@RequestMapping(value = "/mobileServiceCusInterface/getPersBaseInfoByCusNo")
	public MfCusPersBaseInfo getPersBaseInfoByCusNo(@RequestBody String cusNo) throws Exception;
	/**
	 * 
	 * 方法描述： 根据客户号获得职业信息，一对一
	 * @param mfCusPersonJob
	 * @throws Exception
	 * void
	 * @author 沈浩兵
	 * @date 2017-10-11 下午3:43:41
	 */
	@RequestMapping(value = "/mobileServiceCusInterface/getMfCusPersonJob")
	public MfCusPersonJob getMfCusPersonJob(@RequestBody String cusNo) throws Exception;
	/**
	 * 
	 * 方法描述： 根据客户号根据客户号获得收支情况
	 * @param mfCusPersonIncExpe
	 * @throws Exception
	 * void
	 * @author 沈浩兵
	 * @date 2017-10-11 下午3:45:59
	 */
	@RequestMapping(value = "/mobileServiceCusInterface/getMfCusPersonIncExpe")
	public MfCusPersonIncExpe getMfCusPersonIncExpe(@RequestBody String cusNo) throws Exception;
	/**
	 * 
	 * 方法描述： 根据客户号获得社会关系
	 * @param cusNo
	 * @throws Exception
	 * void
	 * @author 沈浩兵
	 * @date 2017-10-11 下午3:47:31
	 */
	@RequestMapping(value = "/mobileServiceCusInterface/getMfCusFamilyInfoList")
	public List<MfCusFamilyInfo> getMfCusFamilyInfoList(@RequestBody String cusNo) throws Exception;
	/**
	 * 
	 * 方法描述： 根据客户号获得账号管理列表
	 * @param mfCusBankAccManage
	 * @throws Exception
	 * void
	 * @author 沈浩兵
	 * @date 2017-10-11 下午3:48:20
	 */
	@RequestMapping(value = "/mobileServiceCusInterface/getMfCusBankAccManage")
	public List<MfCusBankAccManage> getMfCusBankAccManage(@RequestBody String cusNo) throws Exception;
	/**
	 * 
	 * 方法描述： 根据客户号获得账号管理列表(合同阶段调用)
	 * @param mfCusBankAccManage
	 * @throws Exception
	 * void
	 * @author 沈浩兵
	 * @date 2017-10-11 下午3:48:20
	 */
	@RequestMapping(value = "/mobileServiceCusInterface/getMfCusBankAccManageForPact")
	public List<MfCusBankAccManage> getMfCusBankAccManageForPact(@RequestBody String cusNo) throws Exception;
	/**
	 * 
	 * 方法描述： 根据账号获取获得账号管理
	 * @param cusNo
	 * @param accountNo
	 * @return
	 * @throws Exception
	 * MfCusBankAccManage
	 * @author YaoWenHao
	 * @date 2017-12-14 上午9:15:34
	 */
	@RequestMapping(value = "/mobileServiceCusInterface/getMfCusBankAccManageByAccountNo")
	public MfCusBankAccManage getMfCusBankAccManageByAccountNo(@RequestBody String cusNo,@RequestParam("accountNo") String accountNo,@RequestParam("bankId") String bankId) throws Exception;
	/**
	 * 
	 * 方法描述： 根据客户号信用情况登记列表
	 * @param mfCusPersonCreditInfo
	 * @throws Exception
	 * void
	 * @author 沈浩兵
	 * @date 2017-10-11 下午3:49:11
	 */
	@RequestMapping(value = "/mobileServiceCusInterface/getMfCusPersonCreditInfoList")
	public List<MfCusPersonCreditInfo> getMfCusPersonCreditInfoList(@RequestBody String cusNo) throws Exception;
	/**
	 * 
	 * 方法描述： 根据客户号和资产类型获得资产信息
	 * @param cusNo
	 * @param assetsType
	 * @throws Exception
	 * void
	 * @author 沈浩兵
	 * @date 2017-10-11 下午3:51:16
	 */
	@RequestMapping(value = "/mobileServiceCusInterface/getMfCusPersonAssetsInfoList")
	public List<MfCusPersonAssetsInfo> getMfCusPersonAssetsInfoList(@RequestBody String cusNo,@RequestParam("assetsType") String assetsType) throws Exception;
	/**
	 * 
	 * 方法描述： 根据客户号获得负债信息
	 * @param mfCusPersonDebtInfo
	 * @throws Exception
	 * void
	 * @author 沈浩兵
	 * @date 2017-10-11 下午3:52:38
	 */
	@RequestMapping(value = "/mobileServiceCusInterface/getMfCusPersonDebtInfo")
	public List<MfCusPersonDebtInfo> getMfCusPersonDebtInfo(@RequestBody String cusNo) throws Exception;
	/**
	 * 
	 * 方法描述： 根据客户号获得对外担保情况
	 * @param mfCusGuaranteeOuter
	 * @throws Exception
	 * void
	 * @author 沈浩兵
	 * @date 2017-10-11 下午3:59:10
	 */
	@RequestMapping(value = "/mobileServiceCusInterface/getMfCusGuaranteeOuter")
	public List<MfCusGuaranteeOuter> getMfCusGuaranteeOuter(@RequestBody String cusNo) throws Exception;
	
	/**
	 * 
	 * 方法描述： 根据客户手机号码检查是否可进行融资申请：是否已完成基本信息完善，
	 * 1代表需完善基本信息，2代表可以申请。
	 * @param cusTel
	 * @return
	 * @throws Exception
	 * String
	 * @author 沈浩兵
	 * @date 2017-10-12 下午2:28:50
	 */
	@RequestMapping(value = "/mobileServiceCusInterface/getCheckApplyByCusTelFlag")
	public String getCheckApplyByCusTelFlag(@RequestBody String cusTel) throws Exception;
	/**
	 * 
	 * 方法描述： 插入手机通讯录
	 * @param ajaxData 通讯录的字符串
	 * @return
	 * @throws Exception
	 * String
	 * @author ywh
	 * @date 2017-10-13 11:33:50
	 */
	@RequestMapping(value = "/mobileServiceCusInterface/insertPhoneBookList")
	public  Map<String, Object> insertPhoneBookList(@RequestBody String ajaxData) throws Exception;
	/**
	 * 
	 * 方法描述：  根据客户类型、操作员编号、部门编号、数据权限类型获得客户列表,不分页
	 * @param cusBaseType
	 * @param opNo
	 * @param brNo
	 * @param funRoleType
	 * @return
	 * @throws Exception
	 * List<MfCusCustomer>
	 * @author 沈浩兵
	 * @date 2017-10-16 下午8:18:01
	 */
	@RequestMapping(value = "/mobileServiceCusInterface/getMfCusCustomerList")
	public List<MfCusCustomer> getMfCusCustomerList(@RequestBody String cusBaseType,@RequestParam("opNo") String opNo,@RequestParam("brNo") String brNo,@RequestParam("funRoleType") String funRoleType) throws Exception;
	
	/**
	 * 
	 * 方法描述：  根据客户类型、操作员编号、部门编号、数据权限类型获得客户列表.分页
	 * @param ipage
	 * @param cusBaseType
	 * @param opNo
	 * @param brNo
	 * @param funRoleType
	 * @return
	 * @throws Exception
	 * Ipage
	 * @author 沈浩兵
	 * @date 2017-10-16 下午8:17:29
	 */
	@RequestMapping(value = "/mobileServiceCusInterface/findMfCusCustomerByPage")
	public Ipage findMfCusCustomerByPage(@RequestBody Ipage ipage,@RequestParam("cusBaseType")  String cusBaseType,@RequestParam("opNo") String opNo,@RequestParam("brNo") String brNo,@RequestParam("funRoleType") String funRoleType) throws Exception;
	/**
	 * 根据客户号获取客户跟踪列表
	 * @param mfCusTrack
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/mobileServiceCusInterface/getMfCusTraclListByCusNo")
	public List<MfCusTrack> getMfCusTraclListByCusNo(@RequestBody String  cusNo)throws Exception;
	/**
	 * 根据跟踪ID获取评论列表
	 * @param mfCusTrack
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/mobileServiceCusInterface/getMfCusTraclListByTarctId")
	public List<MfCusTrack> getMfCusTraclListByTarctId(@RequestBody String  trackId)throws Exception;
	/**
	 * 插入客户跟踪
	 * @param trackType 跟踪类型
	 * @param trackContent 跟踪内容
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/mobileServiceCusInterface/insertMfCusTrack")
	public MfCusTrack insertMfCusTrack(@RequestBody String trackType,@RequestParam("trackContent") String trackContent,@RequestParam("cusNo") String cusNo,@RequestParam("cusName") String cusName)throws Exception;
	/**
	 * 插入客户跟踪评论
	 * @param trackContent 评论内容
	 * @param trackId 跟踪ID
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/mobileServiceCusInterface/insertMfCusTrackComment")
	public MfCusTrack insertMfCusTrackComment(@RequestBody String trackContent,@RequestParam("trackId") String trackId,@RequestParam("cusNo") String cusNo,@RequestParam("cusName") String cusName)throws Exception;
	/**
	 * 
	 * 方法描述： 插入企业客户基本信息
	 * @param mfCusCorpBaseInfo
	 * @throws Exception
	 * void
	 * @author ywh
	 * @date 2017-10-11 下午2:34:31
	 */
	@RequestMapping(value = "/mobileServiceCusInterface/insertCorpBaseInfo")
	public MfCusCorpBaseInfo insertCorpBaseInfo(@RequestBody MfCusCorpBaseInfo mfCusCorpBaseInfo) throws Exception;
	/**
	 * 
	 * 方法描述： 根据客户号获得企业客户基本信息
	 * @param cusNo
	 * @throws Exception
	 * void
	 * @author ywh
	 * @date 2017-10-11 下午2:34:31
	 */
	@RequestMapping(value = "/mobileServiceCusInterface/getCorpBaseInfoByCusNo")
	public MfCusCorpBaseInfo getCorpBaseInfoByCusNo(@RequestBody String cusNo) throws Exception;
	/**
	 * 
	 * 方法描述： 更新企业客户基本信息
	 * @param mfCusPersBaseInfo
	 * @throws Exception
	 * void
	 * @author ywh
	 * @date 2017-10-11 下午2:34:31
	 */
	@RequestMapping(value = "/mobileServiceCusInterface/updateCorpBaseInfo")
	public Map<String, Object> updateCorpBaseInfo(@RequestBody MfCusCorpBaseInfo mfCusCorpBaseInfo) throws Exception;
	/**
	 * 
	 * 方法描述： 插入企业客户高管信息
	 * @param mfCusCorpBaseInfo
	 * @throws Exception
	 * void
	 * @author ywh
	 * @date 2017-10-11 下午2:34:31
	 */
	@RequestMapping(value = "/mobileServiceCusInterface/insertHighInfo")
	public MfCusHighInfo insertHighInfo(@RequestBody MfCusHighInfo mfCusHighInfo) throws Exception;
	/**
	 * 
	 * 方法描述： 根据客户号获得企业客户高管信息
	 * @param cusNo
	 * @throws Exception
	 * void
	 * @author ywh
	 * @date 2017-10-11 下午2:34:31
	 */
	@RequestMapping(value = "/mobileServiceCusInterface/getHighInfoByCusNo")
	public List<MfCusHighInfo> getHighInfoByCusNo(@RequestBody String cusNo) throws Exception;
	/**
	 * 
	 * 方法描述： 更新企业企业客户高管信息
	 * @param mfCusPersBaseInfo
	 * @throws Exception
	 * void
	 * @author ywh
	 * @date 2017-10-11 下午2:34:31
	 */
	@RequestMapping(value = "/mobileServiceCusInterface/updateHighInfo")
	public Map<String, Object> updateHighInfo(@RequestBody MfCusHighInfo mfCusHighInfo) throws Exception;
	/**
	 * 
	 * 方法描述： 插入企业客户股东信息
	 * @param mfCusCorpBaseInfo
	 * @throws Exception
	 * void
	 * @author ywh
	 * @date 2017-10-11 下午2:34:31
	 */
	@RequestMapping(value = "/mobileServiceCusInterface/insertMfCusShareholder")
	public MfCusShareholder insertMfCusShareholder(@RequestBody MfCusShareholder mfCusShareholder) throws Exception;
	/**
	 * 
	 * 方法描述： 根据客户号获得企业客户股东信息
	 * @param cusNo
	 * @throws Exception
	 * void
	 * @author ywh
	 * @date 2017-10-11 下午2:34:31
	 */
	@RequestMapping(value = "/mobileServiceCusInterface/getMfCusShareholderById")
	public List<MfCusShareholder> getMfCusShareholderById(@RequestBody String cusNo)throws Exception;
	/**
	 * 
	 * 方法描述： 更新企业企业客户股东信息
	 * @param mfCusPersBaseInfo
	 * @throws Exception
	 * void
	 * @author ywh
	 * @date 2017-10-11 下午2:34:31
	 */
	@RequestMapping(value = "/mobileServiceCusInterface/updateMfCusShareholder")
	public Map<String, Object> updateMfCusShareholder(@RequestBody MfCusShareholder mfCusShareholder) throws Exception;
	/**
	 * 
	 * 方法描述： 插入企业客户固定资产
	 * @param mfCusCorpBaseInfo
	 * @throws Exception
	 * void
	 * @author ywh
	 * @date 2017-10-11 下午2:34:31
	 */
	@RequestMapping(value = "/mobileServiceCusInterface/insertMfCusAssets")
	public MfCusAssets insertMfCusAssets(@RequestBody MfCusAssets mfCusAssets) throws Exception;
	/**
	 * 
	 * 方法描述： 根据客户号获得企业客户固定资产
	 * @param cusNo
	 * @throws Exception
	 * void
	 * @author ywh
	 * @date 2017-10-11 下午2:34:31
	 */
	@RequestMapping(value = "/mobileServiceCusInterface/getMfCusAssetsById")
	public MfCusAssets getMfCusAssetsById(@RequestBody String cusNo)throws Exception;
	/**
	 * 
	 * 方法描述： 更新企业企业客户固定资产
	 * @param mfCusPersBaseInfo
	 * @throws Exception
	 * void
	 * @author ywh
	 * @date 2017-10-11 下午2:34:31
	 */
	@RequestMapping(value = "/mobileServiceCusInterface/updateMfCusAssets")
	public Map<String, Object> updateMfCusAssets(@RequestBody MfCusAssets mfCusAssets) throws Exception;
	/**
	 * app:更新客户表和客户基本信息表
	 * @param cusCustomer
	 * @return
	 */
	@RequestMapping(value = "/mobileServiceCusInterface/updateCustomerInfo")
	public Map<String, Object> updateCustomerInfo(@RequestBody MfCusCustomer mfCusCustomer,@RequestParam("mfCusPersBaseInfo") MfCusPersBaseInfo mfCusPersBaseInfo)throws Exception;
	/**
	 * app:2.2.4	获取身份证资料接口
	 * @param cusNo 客户号
	 * @return
	 */
	@RequestMapping(value = "/mobileServiceCusInterface/getIdCardPicture")
	public Map<String, Object> getIdCardPicture(@RequestBody String cusNo)throws Exception;
	/**
	 * 移动端我的推荐列表。根据手机号码获得我的分享信息。包括分页推荐人列表信息 
	 * 包括推荐人数量  personSum  
	 * 推荐人中借款的人数 putoutCount
	 * 推荐人借款总额 putoutAmtSum
	 * 推荐人列表 mfCusRecommendList
	 * @param rdCusTel 手机号
	 * @param pageSize 分页数据条数
	 * @param pageNo 分页页数
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/mobileServiceCusInterface/getCusRecommendListByPage")
	public Map<String, Object> getCusRecommendListByPage(@RequestBody String rdCusTel,@RequestParam("pageSize") int pageSize,@RequestParam("pageNo") int pageNo) throws Exception;
	/**
	 * 
	 * 方法描述： 更新客户资料完整度
	 * @param mfAppCusIntegrity
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author YaoWenHao
	 * @date 2017-11-13 下午5:49:09
	 */
	@RequestMapping(value = "/mobileServiceCusInterface/updateMfAppCusIntegrity")
	public  Map<String, Object> updateMfAppCusIntegrity(@RequestBody MfAppCusIntegrity mfAppCusIntegrity) throws Exception;
	/**
	 * 
	 * 方法描述： 根据客户号获取客户信息的完整信息 （管理端paid使用）
	 * @param cusNo
	 * @param cheackItems
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author YaoWenHao
	 * @date 2017-11-13 下午5:53:23
	 */
	@RequestMapping(value = "/mobileServiceCusInterface/doCheckIntegrity")
	public  Map<String, Object>  doCheckIntegrity(@RequestBody String cusNo,@RequestParam("cheackItems")  String cheackItems ) throws Exception;
	
	/**
	 * 
	 * 方法描述： 根据客户号获取客户信息的完整信息 （）
	 * @param cusNo
	 * @param cheackItems
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author YaoWenHao
	 * @date 2017-11-13 下午5:53:23
	 */
	@RequestMapping(value = "/mobileServiceCusInterface/doCheckIntegrityForPaid")
	public  Map<String, Object>  doCheckIntegrityForPaid(@RequestBody String cusNo,@RequestParam("cheackItems")  String cheackItems ) throws Exception;
	
	/**
	 * 
	 * 方法描述： 根据手机号获取分享总人数 personSum、借款人数 putoutCount、借款总金额 putoutAmtSum 
	 *  
	 * @param rdCusTel
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author 沈浩兵
	 * @date 2017-11-20 上午11:25:21
	 */
	@RequestMapping(value = "/mobileServiceCusInterface/getPerCountAndLoanAmtByCusTel")
	public Map<String, Object> getPerCountAndLoanAmtByCusTel(@RequestBody String rdCusTel) throws Exception;
	/**
	 * 
	 * 方法描述： 根据银行卡卡号获得开户行信息
	 * 主要返回参数：
	 * bankId 银行机构号(03010000)
	 * bankName 银行名称(交通银行)
	 * cardName 卡类型名称(太平洋互连卡)
	 * cardType 卡类型(借记卡)
	 * @param accountNo
	 * @return
	 * @throws Exception
	 * Map<String, Object>
	 * @author 沈浩兵
	 * @date 2017-11-20 下午2:34:21
	 */
	@RequestMapping(value = "/mobileServiceCusInterface/getBankInfoByAccountNo")
	public Map<String, Object> getBankInfoByAccountNo(@RequestBody String accountNo) throws Exception;
	/**
	 * 
	 * 方法描述： 获取资料完整度
	 * @param cusNo
	 * @return
	 * @throws Exception
	 * List<MfCusTable>
	 * @author YaoWenHao
	 * @date 2017-11-22 下午4:54:32
	 */
	@RequestMapping(value = "/mobileServiceCusInterface/getMfCusTableList")
	public List<MfCusTable> getMfCusTableList(@RequestBody String cusNo) throws Exception;
	
	/**
	 * 身份证ocr识别
	 * @param cusNo file(照片) type(正/反)
	 * @return 
	 * @throws Exception
	 * @author yjk
	 */
	@RequestMapping(value = "/mobileServiceCusInterface/getIdCardInfoByOCR")
    public Map<String,Object> getIdCardInfoByOCR(@RequestBody String cusNo,@RequestParam("file") File file,@RequestParam("type") String type) throws Exception;
    /**
   	 * 
   	 * 方法描述： 插入客户登记信息
   	 * @param mfCusCustomer
   	 * @throws Exception
   	 * void
   	 * @author ywh
   	 * @date 2017-12-04 下午2:44:27
   	 */
	@RequestMapping(value = "/mobileServiceCusInterface/addCustomer")
   	public MfCusCustomer addCustomer(@RequestBody MfCusCustomer mfCusCustomer,@RequestParam("mfCusPersBaseInfo") MfCusPersBaseInfo mfCusPersBaseInfo);
   	/**
   	 * 
   	 * 方法描述： 分页获取客户列表
   	 * @param opNo
   	 * @param cusBaseType 客户类型（不传标示全部）
   	 * @param pageNo
   	 * @param pageSize
   	 * @return
   	 * Map<String,Object>
   	 * @author YaoWenHao
   	 * @date 2017-12-5 下午5:12:58
   	 */
	@RequestMapping(value = "/mobileServiceCusInterface/getMfCusCustomerListForB")
   	public Map<String,Object> getMfCusCustomerListForB(@RequestBody String opNo,@RequestParam("cusBaseType") String cusBaseType ,@RequestParam("pageNo") String pageNo,@RequestParam("pageSize") String pageSize);
   	/**
   	 * 
   	* @Title: getPadMfCusCustomerListForB  
   	* @Description: pad分页获取客户
   	* @param @param opNo
   	* @param @param cusBaseType
   	* @param @param pageNo
   	* @param @param pageSize
   	* @param @param search
   	* @param @param dataRang 0 全部1当前操作员
   	* @param @return    参数  
   	* @return Map<String,Object>    返回类型  
   	* @throws
   	 */
	@RequestMapping(value = "/mobileServiceCusInterface/getMfCusCustomerListForPad")
   	public Map<String,Object> getMfCusCustomerListForPad(@RequestBody Map<String, String> parmMap);
   	/**
   	 * 
   	 * 方法描述： 
   	 * @param opNo分页查询获取客户列表
   	 * @param cusBaseType
   	 * @param pageNo
   	 * @param pageSize
   	 * @param search
   	 * @return
   	 * Map<String,Object>
   	 * @author YaoWenHao
   	 * @date 2017-12-5 下午5:14:05
   	 */
	@RequestMapping(value = "/mobileServiceCusInterface/getMfCusCustomerListSearchForB")
	public Map<String,Object> getMfCusCustomerListSearchForB(@RequestBody String opNo,@RequestParam("cusBaseType") String cusBaseType ,@RequestParam("pageNo") String pageNo,@RequestParam("pageSize") String pageSize,@RequestParam("search") String search);
	/**
   	 * 
   	 * 方法描述：新增农户经济状况
   	 * @param mfCusFarmerEconoInfo
   	 * @return
   	 * MfCusFarmerEconoInfo
   	 * @author YaoWenHao
   	 * @date 2017-12-5 下午7:15:44
   	 */
	@RequestMapping(value = "/mobileServiceCusInterface/addMfCusFarmerEconoInfo")
   	public MfCusFarmerEconoInfo addMfCusFarmerEconoInfo(@RequestBody MfCusFarmerEconoInfo mfCusFarmerEconoInfo);
   	/**
   	 * 
   	 * 方法描述： 根据客户号获取农户经济状况
   	 * @param mfCusFarmerEconoInfo
   	 * @return
   	 * MfCusFarmerEconoInfo
   	 * @author YaoWenHao
   	 * @date 2017-12-5 下午7:21:07
   	 */
	@RequestMapping(value = "/mobileServiceCusInterface/getMfCusFarmerEconoInfo")
   	public MfCusFarmerEconoInfo getMfCusFarmerEconoInfo(@RequestBody String cusNo);
   	/**
   	 * 
   	 * 方法描述： 更新农户经济状况
   	 * @param mfCusFarmerEconoInfo
   	 * void
   	 * @author YaoWenHao
   	 * @date 2017-12-5 下午7:22:41
   	 */
	@RequestMapping(value = "/mobileServiceCusInterface/updateMfCusFarmerEconoInfo")
   	public void updateMfCusFarmerEconoInfo(@RequestBody MfCusFarmerEconoInfo mfCusFarmerEconoInfo);
   	/**
   	 * 
   	 * 方法描述： 新增农户收支情况
   	 * @param mfCusFarmerIncExpe
   	 * @return
   	 * MfCusFarmerIncExpe
   	 * @author YaoWenHao
   	 * @date 2017-12-5 下午7:25:09
   	 */
	@RequestMapping(value = "/mobileServiceCusInterface/addMfCusFarmerIncExpe")
   	public MfCusFarmerIncExpe addMfCusFarmerIncExpe(@RequestBody MfCusFarmerIncExpe mfCusFarmerIncExpe);
   	/**
   	 * 
   	 * 方法描述：  根据客户号获取农户收支情况
   	 * @param mfCusFarmerIncExpe
   	 * @return
   	 * MfCusFarmerIncExpe
   	 * @author YaoWenHao
   	 * @date 2017-12-5 下午7:30:54
   	 */
	@RequestMapping(value = "/mobileServiceCusInterface/getMfCusFarmerIncExpe")
   	public MfCusFarmerIncExpe getMfCusFarmerIncExpe(@RequestBody String cusNo);
   	/**
   	 * 
   	 * 方法描述： 更新客户号获取农户收支情况
   	 * @param mfCusFarmerIncExpe
   	 * void
   	 * @author YaoWenHao
   	 * @date 2017-12-5 下午7:31:23
   	 */
	@RequestMapping(value = "/mobileServiceCusInterface/updateMfCusFarmerIncExpe")
   	public void updateMfCusFarmerIncExpe(@RequestBody MfCusFarmerIncExpe mfCusFarmerIncExpe);
   	/**
   	 * 
   	 * 方法描述： app端新增个人流动资产
   	 * @param mfCusPersonFlowAssetsInfo
   	 * @return
   	 * @throws Exception
   	 * MfCusPersonFlowAssetsInfo
   	 * @author YaoWenHao
   	 * @date 2017-12-6 下午3:35:41
   	 */
	@RequestMapping(value = "/mobileServiceCusInterface/insertMfCusPersonFlowAssetsInfoForApp")
	public Map<String,Object> insertMfCusPersonFlowAssetsInfoForApp(@RequestBody MfCusPersonFlowAssetsInfo mfCusPersonFlowAssetsInfo);
	/**
   	 * 
   	 * 方法描述： app端更新个人流动资产
   	 * @param mfCusPersonFlowAssetsInfo
   	 * @return
   	 * @throws Exception
   	 * MfCusPersonFlowAssetsInfo
   	 * @author YaoWenHao
   	 * @date 2017-12-6 下午3:35:41
   	 */
	@RequestMapping(value = "/mobileServiceCusInterface/updateMfCusPersonFlowAssetsInfoForApp")
	public Map<String,Object> updateMfCusPersonFlowAssetsInfoForApp(@RequestBody MfCusPersonFlowAssetsInfo mfCusPersonFlowAssetsInfo);
	/**
	 * 
	 * 方法描述： 根据客户号获取个人流动资产
	 * @param mfCusPersonFlowAssetsInfo
	 * void
	 * @author YaoWenHao
	 * @date 2017-12-6 下午3:39:33
	 */
	@RequestMapping(value = "/mobileServiceCusInterface/getMfCusPersonFlowAssetsInfoByCusNo")
	public Map<String,Object> getMfCusPersonFlowAssetsInfoByCusNo(@RequestBody String cusNo);
	/**
	 * 
	 * 方法描述： app端增加个人名下企业
	 * @param mfCusPersonCorp
	 * @return
	 * @throws Exception
	 * MfCusPersonCorp
	 * @author YaoWenHao
	 * @date 2017-12-6 下午3:52:31
	 */
	@RequestMapping(value = "/mobileServiceCusInterface/insertMfCusPersonCorpForApp")
	public Map<String,Object> insertMfCusPersonCorpForApp(@RequestBody MfCusPersonCorp mfCusPersonCorp);
	/**
	 * 
	 * 方法描述：  app端更新个人名下企业
	 * @param mfCusPersonCorp
	 * @throws Exception
	 * void
	 * @author YaoWenHao
	 * @date 2017-12-6 下午4:00:46
	 */
	@RequestMapping(value = "/mobileServiceCusInterface/updateMfCusPersonCorpForApp")
	public Map<String,Object> updateMfCusPersonCorpForApp(@RequestBody MfCusPersonCorp mfCusPersonCorp);
	/**
	 * 
	 * 方法描述： 获取个人名下企业
	 * @param cusNo
	 * @return
	 * Map<String,Object>
	 * @author YaoWenHao
	 * @date 2017-12-6 下午4:03:50
	 */
	@RequestMapping(value = "/mobileServiceCusInterface/getAllPersonCorpList")
	public Map<String,Object> getAllPersonCorpList(@RequestBody String cusNo);
	/**
	 * 
	 * 方法描述： 根据客户号获取共同借款人
	 * @param cusNo
	 * @return
	 * @throws Exception
	 * JSONArray
	 * @author YaoWenHao
	 * @date 2017-12-14 上午9:34:32
	 */
	@RequestMapping(value = "/mobileServiceCusInterface/getCobBorrower")
	public JSONArray getCobBorrower(@RequestBody String cusNo)throws Exception;
	/**
	 * 
	 * 方法描述： pad端获取所有操作员分页
	 * @return
	 * Map<String,Object>
	 * @author YaoWenHao
	 * @date 2017-12-15 上午11:28:12
	 */
	@RequestMapping(value = "/mobileServiceCusInterface/getAllUserByPage")
	public Map<String,Object> getAllUserByPage(@RequestBody String pageNo,@RequestParam("pageSize") String pageSize,@RequestParam("opName") String opName);
	/**
	 * 
	 * 方法描述： pad端获取开户行客户列表
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * Map<String,Object>
	 * @author YaoWenHao
	 * @date 2017-12-15 下午2:17:47
	 */
	@RequestMapping(value = "/mobileServiceCusInterface/getCnapsList")
	public Map<String,Object> getCnapsList(@RequestBody String pageNo,@RequestParam("pageSize") String pageSize,@RequestParam("search") String search);
	/**
	 * 
	 * 方法描述： 
	 * @param formId 表单
	 * @param element 表单字段
	 * @param cusType 客户类型
	 * @param cusBaseType个人/企业客户
	 * @param pageNo
	 * @param pageSize
	 * @param cusNo 取社会关系是使用
	 * @param search 搜索内容 支持证件号，客户名，客户号，电话模糊查询
	 * @return
	 * Map<String,Object>
	 * @author YaoWenHao
	 * @date 2017-12-20 上午11:45:36
	 */
	@RequestMapping(value = "/mobileServiceCusInterface/getUserOrCustomer")
	public Map<String,Object>  getUserOrCustomer(@RequestBody String formId,@RequestParam("element") String element,@RequestParam("cusType") String cusType,@RequestParam("cusBaseType") String cusBaseType,@RequestParam("pageNo") String pageNo,@RequestParam("pageSize") String pageSize,@RequestParam("cusNo") String cusNo,@RequestParam("search") String search);
	/**
	 * 
	 * 方法描述： 根据客户号、业务编号获取借款客户信息、业务关联保证人信息、借款客户配偶信息。
	 * @param cusNo
	 * @param appId
	 * @return
	 * Map<String,Object>
	 * cusNo 借款客户号
	 * relId 查询客户关联编号 借款客户：借款客户号；担保人：担保人客户号；配偶：配偶信息主键
	 * cusName 查询客户名称
	 * idType 证件类型
	 * idNum 证件号码
	 * relationType 查询客户类型 1借款客户2担保人3配偶
	 * @author YaoWenHao
	 * @date 2017-12-26 下午3:36:22
	 */
	@RequestMapping(value = "/mobileServiceCusInterface/getAssureAndFamily")
	public Map<String,Object>  getAssureAndFamily(@RequestBody String cusNo,@RequestParam("appId") String appId);
	/**
	 * 
	 * 方法描述： 判断操作员是否具有查询人行征信权限的接口
	 * @param opNo
	 * @return
	 * boolean
	 * @author YaoWenHao
	 * @date 2017-12-26 下午4:12:24
	 */
	@RequestMapping(value = "/mobileServiceCusInterface/isQueryCredit")
	public boolean isQueryCredit(@RequestBody String opNo);
	/**
	 * 
	 * 方法描述： 征信查询
	 * @param cusNo 借款人客户号
	 * @param realtionType 关系类型 1借款客户2担保人3配偶
	 * @param opNo 操作员
	 * @param reason查询原因 01-贷后管理02-贷款审批03-信用卡审批05-异议核查08-担保资格审查16-公积金提取复核19-特约商户实名审查22-法人代表、负责人、高管等资信审查23-客户准入资格审查
	 * @param relId 关联编号。查询借款人：借款人客户号;查询担保人 ：担保人客户号;查询配偶 ：配偶信息主键
	 * @param docMangeNoStr 选择的征询查询所需要的征信要件资料。包括身份证、授权书
	 * @return
	 * Map<String,Object>
	 * @author YaoWenHao
	 * @date 2017-12-26 下午4:47:40
	 */
	@RequestMapping(value = "/mobileServiceCusInterface/doCreditQuery")
	public Map<String,Object> doCreditQuery(@RequestBody Map<String,Object> parmMap);
	/**
	 * 
	 * 方法描述： 获得征信查询结果
	 * @param cusNo
	 * @return
	 * Map<String,Object>
	 * @author YaoWenHao
	 * @date 2017-12-26 下午5:06:20
	 */
	@RequestMapping(value = "/mobileServiceCusInterface/getNewestCreditHtml")
	public Map<String,Object> getNewestCreditHtml(@RequestBody Map<String,Object> parmMap);
	
	/**
	 * 
	 * 方法描述： 获得征信查询提交时选择上传ftp要件信息
	 * @param cusNo
	 * @param appId
	 * @return
	 * @throws Exception
	 * Map<String,Object> docManageList
	 * @author 沈浩兵
	 * @date 2017-12-28 下午1:36:51
	 */
	@RequestMapping(value = "/mobileServiceCusInterface/getSelectCreditDocDataByAppId")
	public Map<String,Object> getSelectCreditDocDataByAppId(@RequestBody String cusNo,@RequestParam("appId") String appId)throws Exception;
	/**
	 * 
	 * 方法描述： 插入对外投资信息
	 * @param mfCusEquityInfo
	 * @return
	 * @throws Exception
	 * @author yjk
	 */
	@RequestMapping(value = "/mobileServiceCusInterface/insertMfCusEquityInfo")
	public Map<String,Object> insertMfCusEquityInfo(@RequestBody MfCusEquityInfo mfCusEquityInfo) throws Exception;
	/**
	 * 获取对外投资信息
	 * @param cusNo
	 * @return
	 * @throws Exception
	 * @author yjk
	 */
	@RequestMapping(value = "/mobileServiceCusInterface/getMfCusEquityInfoByCusNo")
	public  Map<String,Object> getMfCusEquityInfoByCusNo(@RequestBody String cusNo) throws Exception;

	/**
	 * 获取非现场信息
	 * @param cusNo
	 * @return
	 * @throws Exception
	 * @author yjk
	 */
	@RequestMapping(value = "/mobileServiceCusInterface/getMfCusSurveyRelate")
	public  Map<String,Object> getMfCusSurveyRelate(@RequestBody String cusNo) throws Exception;
	/**
	 * 获取非现场信息
	 * @param cusNo
	 * @return
	 * @throws Exception
	 * @author yjk
	 */
	@RequestMapping(value = "/mobileServiceCusInterface/getMfCusSurveySpotList")
	public  Map<String,Object> getMfCusSurveySpotList(@RequestBody String cusNo) throws Exception;
	/**
	 * 登记收入流水信息
	 * @param MfCusPersonIncomeSurvey
	 * @return
	 * @throws Exception
	 * @author yjk
	 */
	@RequestMapping(value = "/mobileServiceCusInterface/insertMfCusPersonIncomeSurvey")
	public  Map<String,Object> insertMfCusPersonIncomeSurvey(@RequestBody MfCusPersonIncomeSurvey mfCusPersonIncomeSurvey) throws Exception;
	/**
	 * 获取收入流水信息
	 * @param cusNo
	 * @return
	 * @throws Exception
	 * @author yjk
	 */
	@RequestMapping(value = "/mobileServiceCusInterface/getMfCusPersonIncomeSurveyList")
	public  Map<String,Object> getMfCusPersonIncomeSurveyList(@RequestBody String cusNo) throws Exception;
	/**
	 * 获取检测表信息
	 * @param cusNo
	 * @return
	 * @throws Exception
	 * @author yjk
	 */
	@RequestMapping(value = "/mobileServiceCusInterface/getMfCusEquipmentEvalCheckList")
	public  Map<String,Object> getMfCusEquipmentEvalCheckList(@RequestBody String cusNo) throws Exception;
	/**
	 * 插入申请人调查信息
	 * @param MfCusEquipmentEvalCheck
	 * @return
	 * @throws Exception
	 * @author yjk
	 */
	@RequestMapping(value = "/mobileServiceCusInterface/insertMfCusApplyPersonSurvey")
	public  Map<String,Object> insertMfCusApplyPersonSurvey(@RequestBody MfCusApplyPersonSurvey mfCusApplyPersonSurvey) throws Exception;
	/**
	 * 获取申请人调查信息
	 * @param cusNo
	 * @return
	 * @throws Exception
	 * @author yjk
	 */
	@RequestMapping(value = "/mobileServiceCusInterface/getMfCusApplyPersonSurveyList")
	public  Map<String,Object> getMfCusApplyPersonSurveyList(@RequestBody String cusNo) throws Exception;
	/**
	 * 插入申请人调查配偶信息
	 * @param mfCusApplyPersonSurvey
	 * @return
	 * @throws Exception
	 * @author yjk
	 */
	@RequestMapping(value = "/mobileServiceCusInterface/insertMfCusApplySpouseSurvey")
	public  Map<String,Object> insertMfCusApplySpouseSurvey(@RequestBody MfCusApplySpouseSurvey mfCusApplySpouseSurvey) throws Exception;
	/**
	 * 获取申请人调查配偶信息
	 * @param cusNo
	 * @return
	 * @throws Exception
	 * @author yjk
	 */
	@RequestMapping(value = "/mobileServiceCusInterface/getMfCusApplySpouseSurvey")
	public  Map<String,Object> getMfCusApplySpouseSurvey(@RequestBody String cusNo) throws Exception;
	/**
	 * 获取担保人调查信息
	 * @param cusNo
	 * @return
	 * @throws Exception
	 * @author yjk
	 */
	@RequestMapping(value = "/mobileServiceCusInterface/getMfCusGuarantorSurveyList")
	public  Map<String,Object> getMfCusGuarantorSurveyList(@RequestBody String cusNo) throws Exception;
	/**
	 * 
	 * 方法描述：获取客户的配置表单 
	 * @param cusNo
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author YaoWenHao
	 * @date 2018-1-18 下午3:25:25
	 */
	@RequestMapping(value = "/mobileServiceCusInterface/getCusFormConfig")
	public  Map<String,Object> getCusFormConfig(@RequestBody String cusNo) throws Exception;
	
	
	
 
}
