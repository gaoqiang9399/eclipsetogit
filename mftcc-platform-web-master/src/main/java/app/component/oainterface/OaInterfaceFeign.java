package app.component.oainterface;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.oa.archive.entity.MfOaArchivesBase;
import app.component.oa.attendance.entity.MfOaAttendance;
import app.component.oa.debt.entity.MfOaDebt;
import app.component.oa.expense.entity.MfOaExpense;
import app.component.oa.leave.entity.MfOaLeave;
import app.component.oa.notice.entity.MfOaNotice;
import app.util.toolkit.Ipage;




@FeignClient("mftcc-platform-factor")
public interface OaInterfaceFeign {
	/**
	 * 
	 * 方法描述： 获取个人中心页面oa办公模块的统计数据
	 * @param opNo
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author zhs
	 * @date 2017-3-8 下午4:31:09
	 */
	@RequestMapping(value = "/oaInterface/getPersonCenterCount")
	public Map<String, Object> getPersonCenterCount(@RequestBody String opNo) throws Exception;
	
	@RequestMapping(value = "/oaInterface/getAccrditInfo")
	public boolean getAccrditInfo(@RequestBody String opNo) throws Exception;
	/**
	 * 
	 * 方法描述： 插入借款申请
	 * @throws Exception
	 * void
	 * @author YaoWenHao
	 * @date 2018-1-9 上午10:07:13
	 */
	@RequestMapping(value = "/oaInterface/insertMfOaDebt")
	public Map<String, String>  insertMfOaDebt(@RequestBody MfOaDebt mfOaDebt ) throws Exception;
	/**
	 * 
	 * 方法描述： 获取借款和报销列表
	 * @param ipage
	 * @param mfOaDebt
	 * @return
	 * @throws Exception
	 * Ipage
	 * @author YaoWenHao
	 * @date 2018-1-9 上午10:10:02
	 */
	@RequestMapping(value = "/oaInterface/findMfOaDebtexpenseByPage")
	public Ipage findMfOaDebtexpenseByPage(@RequestBody Ipage ipage) throws Exception;
	/**
	 * 
	 * 方法描述：  非立即提交的接口提交
	 * @param mfOaDebt
	 * @return
	 * @throws Exception
	 * Map<String,String>
	 * @author YaoWenHao
	 * @date 2018-1-9 下午2:17:12
	 */
	@RequestMapping(value = "/oaInterface/insertForSubmitForApp")
	public Map<String, String> insertForSubmitForApp(@RequestBody MfOaDebt mfOaDebt) throws Exception;
	/**
	 * 
	 * 方法描述：获取借款或申请请详情 
	 * @param debtexpenseId mf_oa_debtexpense的debtexpense_id
	 * @return
	 * Map<String,Object>
	 * @author YaoWenHao
	 * @date 2018-1-9 下午2:55:24
	 */
	@RequestMapping(value = "/oaInterface/getDebtExpenseDetail")
	public Map<String, Object> getDebtExpenseDetail(@RequestBody String debtexpenseId)  throws Exception;
	/**
	 * 
	 * 方法描述： app端报销申请
	 * @param mfOaExpense
	 * @return
	 * @throws Exception
	 * Map<String,String>
	 * @author YaoWenHao
	 * @date 2018-1-9 下午3:40:45
	 */
	@RequestMapping(value = "/oaInterface/insertMfOaExpenseForApp")
	public Map<String,String> insertMfOaExpenseForApp(@RequestBody MfOaExpense mfOaExpense) throws Exception;
	/**
	 * 
	 * 方法描述： app端请假申请
	 * @param mfOaLeave
	 * @return
	 * @throws Exception
	 * Map<String,String>
	 * @author YaoWenHao
	 * @date 2018-1-10 下午1:54:27
	 */
	@RequestMapping(value = "/oaInterface/insertMfOaLeaveForApp")
	public MfOaLeave insertMfOaLeaveForApp(@RequestBody MfOaLeave mfOaLeave) throws Exception;
	/**
	 * 
	 * 方法描述： 获取请假列表
	 * @param mfOaLeave
	 * @return
	 * @throws Exception
	 * List<MfOaLeave>
	 * @author YaoWenHao
	 * @date 2018-1-10 下午2:36:17
	 */
	@RequestMapping(value = "/oaInterface/findMfOaLeaveList")
	public List<MfOaLeave> findMfOaLeaveList(@RequestBody MfOaLeave mfOaLeave) throws Exception;
	/**
	 * 
	 * 方法描述： 获取请假详情
	 * @param mfOaLeave
	 * @return
	 * @throws Exception
	 * MfOaLeave
	 * @author YaoWenHao
	 * @date 2018-1-10 下午3:28:39
	 */
	@RequestMapping(value = "/oaInterface/getMfOaLeave")
	public MfOaLeave getMfOaLeave(@RequestBody MfOaLeave mfOaLeave) throws Exception;
	/**
	 * 
	 * 方法描述：获取通知公告 
	 * @param ipage
	 * @param mfOaNotice
	 * @return
	 * @throws Exception
	 * Ipage
	 * @author YaoWenHao
	 * @date 2018-1-11 下午5:32:47
	 */
	@RequestMapping(value = "/oaInterface/findMfOaNoticeByOpNo")
	public Ipage findMfOaNoticeByOpNo(@RequestBody Ipage ipage) throws Exception;
	/**
	 * 
	 * 方法描述：通知公告详情
	 * @param mfOaNotice
	 * @return
	 * @throws Exception
	 * MfOaNotice
	 * @author YaoWenHao
	 * @date 2018-1-11 下午5:48:11
	 */
	@RequestMapping(value = "/oaInterface/getMfOaNoticeDetail")
	public MfOaNotice getMfOaNoticeDetail(@RequestBody MfOaNotice mfOaNotice) throws Exception;
	/**
	 * 根据id获取基本信息
	 * @param mfOaArchivesBase
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/oaInterface/getMfOaArchivesBaseById")
	public MfOaArchivesBase getMfOaArchivesBaseById(@RequestBody MfOaArchivesBase mfOaArchivesBase) throws Exception;


	/**
	 * 
	 * 方法描述： 插入签到数据
	 * @param mfOaNotice
	 * @return
	 * @throws Exception
	 * MfOaAttendance
	 * @author YaoWenHao
	 * @date 2018-1-15 下午3:36:08
	 */
	@RequestMapping(value = "/oaInterface/insertMfOaAttendance")
	public MfOaAttendance insertMfOaAttendance(@RequestBody MfOaAttendance mfOaAttendance) throws Exception;
	/**
	 * 
	 * 方法描述： 获取签到详情
	 * @param mfOaAttendance
	 * @return
	 * @throws Exception
	 * MfOaAttendance
	 * @author YaoWenHao
	 * @date 2018-1-15 下午3:54:15
	 */
	@RequestMapping(value = "/oaInterface/getMfOaAttendanceDetail")
	public MfOaAttendance getMfOaAttendanceDetail(@RequestBody MfOaAttendance mfOaAttendance) throws Exception;
	/**
	 * 
	 * 方法描述： 签到统计
	 * @param mfOaAttendance
	 * @return
	 * @throws Exception
	 * List<MfOaAttendance>
	 * @author YaoWenHao
	 * @date 2018-1-15 下午3:54:51
	 */
	@RequestMapping(value = "/oaInterface/getMfOaAttendanceList")
	public List<MfOaAttendance> getMfOaAttendanceList(@RequestBody MfOaAttendance mfOaAttendance) throws Exception;
	
}
