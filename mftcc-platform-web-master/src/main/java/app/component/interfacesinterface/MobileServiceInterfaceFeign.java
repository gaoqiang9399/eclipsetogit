/**
 * Copyright (C) DXHM 版权所有
 * 文件名： MobileServiceInterface.java
 * 包名： app.component.interfacesinterface
 * 说明：
 * @author 沈浩兵
 * @date 2017-10-12 上午9:30:17
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

import app.component.calc.core.entity.MfBusOverBaseRecord;
import app.component.frontview.entity.MfFrontAppSetting;
import app.component.frontview.entity.VwBannerManage;
import app.component.interfaces.mobileinterface.entity.MfAccessInfo;
import app.component.oa.attendance.entity.MfOaAttendance;
import app.component.oa.debt.entity.MfOaDebt;
import app.component.oa.expense.entity.MfOaExpense;
import app.component.oa.leave.entity.MfOaLeave;
import app.component.sys.entity.SysTaskInfo;
import app.util.toolkit.Ipage;
import net.sf.json.JSONArray;

/**
 * 类名： MobileServiceInterface 描述：
 * 
 * @author 沈浩兵
 * @date 2017-10-12 上午9:30:17
 *
 *
 */
@FeignClient("mftcc-platform-factor")
public interface MobileServiceInterfaceFeign {

	/**
	 * 
	 * 方法描述： 根据产品编号获得移动端产品配置信息
	 * 
	 * @param kindNo
	 *            产品编号
	 * @return
	 * @throws Exception
	 *             Map<String, Object>
	 * @author 沈浩兵
	 * @date 2017-10-12 上午9:33:08
	 */
	@RequestMapping(value = "/mobileServiceInterface/getMfSysKindByKindNo")
	public Map<String, Object> getMfSysKindByKindNo(@RequestBody String kindNo) throws Exception;

	/**
	 * 
	 * 方法描述： 根据产品编号获得pad端产品配置信息
	 * 
	 * @param kindNo
	 *            产品编号
	 * @return
	 * @throws Exception
	 *             Map<String, Object>
	 * @author 沈浩兵
	 * @date 2017-10-12 上午9:33:08
	 */
	@RequestMapping(value = "/mobileServiceInterface/getMfSysKindByKindNoForPad")
	public Map<String, Object> getMfSysKindByKindNoForPad(@RequestBody String kindNo) throws Exception;

	/**
	 * 
	 * 方法描述： 获得可用的产品列表
	 * 
	 * @return
	 * @throws Exception
	 *             List<Map<String, Object>>
	 * @author 沈浩兵
	 * @date 2017-10-12 上午9:33:00
	 */
	@RequestMapping(value = "/mobileServiceInterface/getMfSysKindList")
	public List<Map<String, Object>> getMfSysKindList() throws Exception;

	/**
	 * 
	 * 方法描述： 上传要件接口
	 * 
	 * @param cusNo
	 *            客户号
	 * @param relNo
	 *            要件关联编号.客户号cusNo、授信申请号creditAppId、融资申请号appId等
	 * @param uploadFileName
	 *            上传文件名
	 * @param type
	 *            身份证信息CARD_DOC 授信申请附件CREDIT_DOC 融资申请附件APPLY_DOC
	 * @param uploadContentType
	 *            文件的内容类型
	 * @param upload
	 *            要件
	 * @param docSplitNo
	 *            要件类型
	 * @throws Exception
	 *             void
	 * @author 沈浩兵
	 * @date 2017-10-12 上午10:15:17
	 */
	@RequestMapping(value = "/mobileServiceInterface/insertDocManage")
	public Map<String, Object> insertDocManage(@RequestBody String cusNo, @RequestParam("relNo") String relNo,
			@RequestParam("uploadFileName") String uploadFileName, @RequestParam("type") String type,
			@RequestParam("uploadContentType") String uploadContentType, @RequestParam("upload") File upload,
			@RequestParam("opNo") String opNo, @RequestParam("docSplitNo") String docSplitNo) throws Exception;

	/**
	 * 
	 * 方法描述： 业务阶段上传要件接口
	 * 
	 * @param cusNo
	 *            客户号
	 * @param relNo
	 *            要件关联编号.客户号cusNo、授信申请号creditAppId、融资申请号appId等
	 * @param uploadFileName
	 *            上传文件名
	 * @param uploadContentType
	 *            文件的内容类型
	 * @param upload
	 *            要件
	 * @param docSplitNo
	 *            要件类型
	 * @param scNo
	 *            业务节点
	 * @throws Exception
	 *             void
	 * @author 沈浩兵
	 * @date 2017-10-12 上午10:15:17
	 */
	@RequestMapping(value = "/mobileServiceInterface/insertDocManageForBusiness")
	public Map<String, Object> insertDocManageForBusiness(@RequestBody String cusNo,
			@RequestParam("relNo") String relNo, @RequestParam("scNo") String scNo,
			@RequestParam("uploadFileName") String uploadFileName,
			@RequestParam("uploadContentType") String uploadContentType, @RequestParam("upload") File upload,
			@RequestParam("opNo") String opNo, @RequestParam("docSplitNo") String docSplitNo);

	/**
	 * 
	 * 方法描述： 根据手机号获得和banner的类型获得主页广告列表 暂时手机号码没有用
	 * 
	 * @param phoneNum
	 *            客户手机号
	 * @param type
	 *            banner的类型 0:pc,1:手机
	 * @return
	 * @throws Exception
	 *             List<VwBannerManage>
	 * @author 沈浩兵
	 * @date 2017-10-12 上午11:57:41
	 */
	@RequestMapping(value = "/mobileServiceInterface/getVwBannerManageList")
	public List<VwBannerManage> getVwBannerManageList(@RequestBody String phoneNum, @RequestParam("type") String type)
			throws Exception;

	/**
	 * 方法描述： 根据数据字段想获取数据字典列表
	 * 
	 * @param dicName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/mobileServiceInterface/getParmDicistByDicName")
	public JSONArray getParmDicistByDicName(@RequestBody String dicName) throws Exception;

	/**
	 * 方法描述： 插入访问信息
	 * 
	 * @param mfAccessInfo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/mobileServiceInterface/insertMfAccessInfo")
	public MfAccessInfo insertMfAccessInfo(@RequestBody MfAccessInfo mfAccessInfo) throws Exception;

	/**
	 * 方法描述： 根据手机号查询访问信息
	 * 
	 * @param mfAccessInfo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/mobileServiceInterface/getMfAccessInfoListByPhone")
	public List<MfAccessInfo> getMfAccessInfoListByPhone(@RequestBody MfAccessInfo mfAccessInfo) throws Exception;

	/**
	 * 
	 * @param tlrNo
	 *            登陆账号
	 * @param messageType
	 *            消息类型 0 消息 6通知公告1 审批任务2 催收任务 3检查任务5预警消息9 业务工作流 不传查全部
	 * @param pageNo
	 *            页码
	 * @param pagesize
	 *            分页大小
	 * @param search
	 *            搜索内容
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/mobileServiceInterface/findTaskInfoPage")
	public Ipage findTaskInfoPage(@RequestBody String tlrNo, @RequestParam("pageNo") int pageNo,
			@RequestParam("pageSize") int pageSize, @RequestParam("search") String search,
			@RequestParam("messageType") String messageType) throws Exception;

	/**
	 * 获取消息详情
	 * 
	 * @param messageId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/mobileServiceInterface/getTaskInfoById")
	public SysTaskInfo getTaskInfoById(@RequestBody String messageId) throws Exception;

	/**
	 * 修改信息为已读
	 * 
	 * @param messageId
	 * @throws Exception
	 */
	@RequestMapping(value = "/mobileServiceInterface/updateTaskByFilter")
	public Map<String, Object> updateTaskByFilter(@RequestBody String messageId) throws Exception;

	/**
	 * 删除消息
	 * 
	 * @param messageId
	 * @throws Exception
	 */
	@RequestMapping(value = "/mobileServiceInterface/deleteSysTaskInfo")
	public Map<String, Object> deleteSysTaskInfo(@RequestBody String messageId) throws Exception;

	/**
	 * 
	 * 方法描述： 获得前端交易_移动端设置信息（客服信息）
	 * 
	 * @return
	 * @throws Exception
	 *             MfFrontAppSetting
	 * @author 沈浩兵
	 * @date 2017-11-16 上午10:16:16
	 */
	@RequestMapping(value = "/mobileServiceInterface/getMfFrontAppSetting")
	public MfFrontAppSetting getMfFrontAppSetting() throws Exception;

	/**
	 * 
	 * 方法描述： 获取数据字典项
	 * 
	 * @param keyName
	 *            数据字典项
	 * @return
	 * @throws Exception
	 *             List<Map<String,String>>
	 * @author YaoWenHao
	 * @date 2017-11-16 下午2:11:30
	 */
	@RequestMapping(value = "/mobileServiceInterface/getParmDic")
	public Map<String, String> getParmDic(@RequestBody String keyName) throws Exception;

	/**
	 * 
	 * 方法描述： 根据操作员获取PC端的借据产品列表
	 * 
	 * @param opNo
	 * @return Map<String,String>
	 * @author YaoWenHao
	 * @date 2017-12-4 上午11:21:38
	 */
	@RequestMapping(value = "/mobileServiceInterface/getMfSysKindListForOpNo")
	public Map<String, Object> getMfSysKindListForOpNo(@RequestBody String opNo);

	/**
	 * 
	 * 方法描述： 获取要件列表
	 * 
	 * @param cusNo
	 * @return Map<String,Object>
	 * @author YaoWenHao
	 * @date 2017-12-5 上午10:57:50
	 */
	@RequestMapping(value = "/mobileServiceInterface/getDocListByCusNo")
	public Map<String, Object> getDocListByCusNo(@RequestBody String cusNo, @RequestParam("appId") String appId);

	/**
	 * 
	 * 方法描述： 根据docNo和docBizNo查询要件
	 * 
	 * @param docNo
	 * @param docBizNo
	 * @param type
	 *            1压缩图2正常图片
	 * @return Map<String,Object>
	 * @author YaoWenHao
	 * @date 2017-12-5 上午11:55:23
	 */
	@RequestMapping(value = "/mobileServiceInterface/getDocManager")
	public Map<String, Object> getDocManager(@RequestBody String docNo, @RequestParam("docBizNo") String docBizNo,
			@RequestParam("type") String type);

	/**
	 * 
	 * 方法描述：文件删除
	 * 
	 * @param docNo
	 * @param docBizNo
	 * @param docSplitNo
	 * @return Map<String,Object>
	 * @author YaoWenHao
	 * @date 2017-12-19 上午11:06:32
	 */
	@RequestMapping(value = "/mobileServiceInterface/delDocManager")
	public Map<String, Object> delDocManager(@RequestBody String docNo, @RequestParam("docBizNo") String docBizNo,
			@RequestParam("docSplitNo") String docSplitNo);

	/**
	 * 
	 * 方法描述： 根据操作员编号获取用户
	 * 
	 * @param opNo
	 * @return Map<String,Object>
	 * @author YaoWenHao
	 * @date 2017-12-26 下午1:30:35
	 */
	@RequestMapping(value = "/mobileServiceInterface/getSysUserById")
	public Map<String, Object> getSysUserById(@RequestBody String opNo);

	/**
	 * 
	 * 方法描述： 借款与报销列表接口
	 * 
	 * @param opNo
	 * @return Map<String,Object>
	 * @author YaoWenHao
	 * @date 2018-1-9 上午9:55:19
	 */
	@RequestMapping(value = "/mobileServiceInterface/getMfOaDebtexpenseList")
	public Map<String, Object> getMfOaDebtexpenseList(@RequestBody String opNo, @RequestParam("pageNo") String pageNo,
			@RequestParam("pageSize") String pageSize);

	/**
	 * 
	 * 方法描述： 获取个人中心页面oa办公模块的统计数据
	 * 
	 * @param opNo
	 * @return
	 * @throws Exception
	 *             Map<String,Object>
	 * @author zhs
	 * @date 2017-3-8 下午4:31:09
	 */
	@RequestMapping(value = "/mobileServiceInterface/getPersonCenterCount")
	public Map<String, Object> getPersonCenterCount(@RequestBody String opNo) throws Exception;

	/**
	 * 
	 * 方法描述： 借款申请
	 * 
	 * @param opNo
	 * @return
	 * @throws Exception
	 *             Map<String,Object>
	 * @author YaoWenHao
	 * @date 2018-1-9 上午11:06:17
	 */
	@RequestMapping(value = "/mobileServiceInterface/insertMfOaDebt")
	public Map<String, Object> insertMfOaDebt(@RequestBody MfOaDebt mfOaDebt);

	/**
	 * 
	 * 方法描述： 非立即提交的接口提交
	 * 
	 * @param mfOaDebt
	 * @return Map<String,Object>
	 * @author YaoWenHao
	 * @date 2018-1-9 下午2:16:03
	 */
	@RequestMapping(value = "/mobileServiceInterface/updateMfOaDebtForSubmit")
	public Map<String, Object> updateMfOaDebtForSubmit(@RequestBody MfOaDebt mfOaDebt);

	/**
	 * 
	 * 方法描述： 获取借款报销审批历史
	 * 
	 * @param relId
	 *            对应mf_oa_debtexpense的rel_id
	 * @return Map<String,Object>
	 * @author YaoWenHao
	 * @date 2018-1-9 下午2:21:20
	 */
	@RequestMapping(value = "/mobileServiceInterface/getDebtApprovalOpinionList")
	public Map<String, Object> getDebtApprovalOpinionList(@RequestBody String relId);

	/**
	 * 
	 * 方法描述：获取借款或申请请详情
	 * 
	 * @param debtexpenseId
	 *            mf_oa_debtexpense的debtexpense_id
	 * @return Map<String,Object>
	 * @author YaoWenHao
	 * @date 2018-1-9 下午2:55:24
	 */
	@RequestMapping(value = "/mobileServiceInterface/getDebtExpenseDetail")
	public Map<String, Object> getDebtExpenseDetail(@RequestBody String debtexpenseId);

	/**
	 * 
	 * 方法描述：报销申请申请
	 * 
	 * @param opNo
	 * @return
	 * @throws Exception
	 *             Map<String,Object>
	 * @author YaoWenHao
	 * @date 2018-1-9 上午11:06:17
	 */
	@RequestMapping(value = "/mobileServiceInterface/insertMfOaExpense")
	public Map<String, Object> insertMfOaExpense(@RequestBody MfOaExpense mfOaExpense);

	/**
	 * 
	 * 方法描述： 请假申请
	 * 
	 * @param mfOaLeave
	 * @return Map<String,Object>
	 * @author YaoWenHao
	 * @date 2018-1-10 上午10:55:14
	 */
	@RequestMapping(value = "/mobileServiceInterface/insertMfOaLeave")
	public Map<String, Object> insertMfOaLeave(@RequestBody MfOaLeave mfOaLeave);

	/**
	 * 
	 * 方法描述：获取请假列表
	 * 
	 * @param opNo
	 * @return Map<String,Object>
	 * @author YaoWenHao
	 * @date 2018-1-10 下午2:44:13
	 */
	@RequestMapping(value = "/mobileServiceInterface/findMfOaLeaveList")
	public Map<String, Object> findMfOaLeaveList(@RequestBody String opNo);

	/**
	 * 
	 * 方法描述：获取请假详情
	 * 
	 * @param leaveNo
	 * @return Map<String,Object>
	 * @author YaoWenHao
	 * @date 2018-1-10 下午3:27:48
	 */
	@RequestMapping(value = "/mobileServiceInterface/getMfOaLeave")
	public Map<String, Object> getMfOaLeave(@RequestBody String leaveNo);

	/**
	 * 
	 * 方法描述： 获取通知公告
	 * 
	 * @param opNo
	 * @return Map<String,Object>
	 * @author YaoWenHao
	 * @date 2018-1-11 下午5:13:10
	 */
	@RequestMapping(value = "/mobileServiceInterface/getMfOaNoticeList")
	public Map<String, Object> getMfOaNoticeList(@RequestBody String opNo, @RequestParam("pageNo") String pageNo,
			@RequestParam("pageSize") String pageSize);

	/**
	 * 
	 * 方法描述： 获取通知公告详情
	 * 
	 * @param noticeId
	 * @return Map<String,Object>
	 * @author YaoWenHao
	 * @date 2018-1-11 下午5:44:50
	 */
	@RequestMapping(value = "/mobileServiceInterface/getMfOaNoticeDetail")
	public Map<String, Object> getMfOaNoticeDetail(@RequestBody String noticeId);

	/**
	 * 
	 * 方法描述： 获取部门的子部门及员工
	 * 
	 * @param brNo
	 * @return Map<String,Object>
	 * @author YaoWenHao
	 * @date 2018-1-12 上午10:18:27
	 */
	@RequestMapping(value = "/mobileServiceInterface/getOrgAndUserList")
	public Map<String, Object> getOrgAndUserList(@RequestBody String brNo);

	/**
	 * 
	 * 方法描述： 查询员工
	 * 
	 * @param search
	 * @return Map<String,Object>
	 * @author YaoWenHao
	 * @date 2018-1-18 上午11:05:25
	 */
	@RequestMapping(value = "/mobileServiceInterface/getUserListForSearch")
	public Map<String, Object> getUserListForSearch(@RequestBody String search);

	/**
	 * 
	 * 方法描述： 获取员工详情
	 * 
	 * @param opNo
	 * @return Map<String,Object>
	 * @author YaoWenHao
	 * @date 2018-1-12 上午11:07:17
	 */
	@RequestMapping(value = "/mobileServiceInterface/getUserDetail")
	public Map<String, Object> getUserDetail(@RequestBody String opNo);

	/**
	 * 
	 * 方法描述： 签到
	 * 
	 * @param mfOaAttendance
	 * @return Map<String,Object>
	 * @author YaoWenHao
	 * @date 2018-1-15 下午3:38:21
	 */
	@RequestMapping(value = "/mobileServiceInterface/insertMfOaAttendance")
	public Map<String, Object> insertMfOaAttendance(@RequestBody MfOaAttendance mfOaAttendance);

	/***
	 * 
	 * 方法描述： 获取当前日期的打卡记录
	 * 
	 * @param date
	 *            按日统计20180101 按月统计201801
	 * @return Map<String,Object>
	 * @author YaoWenHao
	 * @date 2018-1-15 下午3:51:20
	 */
	@RequestMapping(value = "/mobileServiceInterface/getMfOaAttendanceByDate")
	public Map<String, Object> getMfOaAttendanceByDate(@RequestBody String date);

	/***
	 * 
	 * 方法描述： 获取用户当前日期的打卡记录
	 * 
	 * @param date
	 *            按日统计20180101
	 * @return Map<String,Object>
	 * @author YaoWenHao
	 * @date 2018-1-15 下午3:51:20
	 */
	@RequestMapping(value = "/mobileServiceInterface/getMfOaAttendanceByDateAndUser")
	public Map<String, Object> getMfOaAttendanceByDateAndUser(@RequestBody String date,
			@RequestParam("userPhone") String userPhone);

	/**
	 * 
	 * @Title: getMfOaAttendanceByDateAndUserCount @Description:
	 * 打卡统计 @param @param date @param @param userPhone @param @return 参数 @return
	 * Map<String,Object> 返回类型 @throws
	 */
	@RequestMapping(value = "/mobileServiceInterface/getMfOaAttendanceByDateAndUserCount")
	public Map<String, Object> getMfOaAttendanceByDateAndUserCount(@RequestBody String date,
			@RequestParam("userPhone") String userPhone);

	/**
	 * 
	 * 方法描述： 获取月份下的节假日
	 * 
	 * @param date
	 *            201701
	 * @return Map<String,Object>
	 * @author YaoWenHao
	 * @date 2018-1-18 上午11:23:08
	 */
	@RequestMapping(value = "/mobileServiceInterface/getHolidayDayByDate")
	public Map<String, Object> getHolidayDayByDate(@RequestBody String date);

	/**
	 * 上传要件，如果该要件存在，则覆盖更新它
	 * 
	 * @param cusNo
	 * @param relNo
	 * @param uploadFileName
	 * @param type
	 * @param uploadContentType
	 * @param upload
	 * @param opNo
	 * @param docSplitNo
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> insertDocManageOverwrite(String cusNo, @RequestParam("relNo") String relNo,
			@RequestParam("uploadFileName") String uploadFileName, @RequestParam("type") String type,
			@RequestParam("uploadContentType") String uploadContentType, @RequestParam("upload") File upload,
			@RequestParam("opNo") String opNo, @RequestParam("docSplitNo") String docSplitNo) throws Exception;

	MfBusOverBaseRecord getMfBusOverBaseRecord(MfBusOverBaseRecord busOverBaseRecord) throws Exception;

}
