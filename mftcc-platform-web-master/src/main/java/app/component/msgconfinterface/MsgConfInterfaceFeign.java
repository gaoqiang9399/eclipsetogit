package app.component.msgconfinterface;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.calc.core.entity.MfRepayPlan;
import app.component.msgconf.entity.CuslendWarning;
import app.component.msgconf.entity.MatchAfterLoan;
import app.component.msgconf.entity.MfMsgPledge;
import app.component.msgconf.entity.MfMsgVar;
import app.component.pact.entity.MfBusFincApp;

/**
 * 
 * 类名： ModelInterface 描述：消息模块管理
 * 
 * @author 张冬磊
 * @date 2016-9-8 下午5:11:23
 * 
 */
@FeignClient("mftcc-platform-factor")
public interface MsgConfInterfaceFeign {

	/**
	 * 通过id和flag获取预警信息
	 * 
	 * @param CuslendWarning
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/msgConfInterface/getByIdAndFlag")
	public CuslendWarning getByIdAndFlag(@RequestBody CuslendWarning cuslendWarning) throws Exception;

	/**
	 * 通过id获取押品预警信息
	 * 
	 * @param mfMsgPledge
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/msgConfInterface/getById")
	public MfMsgPledge getById(@RequestBody MfMsgPledge mfMsgPledge) throws Exception;

	/**
	 * 方法描述： 获取押品检查列表
	 * 
	 * @param mfMsgPledge
	 * @return
	 * @throws Exception
	 *             List<MfMsgPledge>
	 * @author YuShuai
	 * @date 2017-12-9 下午4:35:46
	 */
	@RequestMapping("/msgConfInterface/getMsgPledgeList")
	public List<MfMsgPledge> getMsgPledgeList(@RequestBody MfMsgPledge mfMsgPledge) throws Exception;

	/**
	 * 通过借据信息、还款计划信息获取借据匹配的预警模型及下一次预警日期
	 * 
	 * @param mfBusFincApp
	 *            mfRepayPlan
	 * @return matchAfterLoan
	 * @throws Exception
	 */
	@RequestMapping("/msgConfInterface/getPliWarningByLoan")
	public MatchAfterLoan getPliWarningByLoan(@RequestBody MfBusFincApp mfBusFincApp,@RequestParam("mfRepayPlan") MfRepayPlan mfRepayPlan) throws Exception;

	/**
	 * 
	 * 方法描述： 获取短信模板
	 * 
	 * @param cuslendWarning
	 * @return
	 * @throws Exception
	 *             List<CuslendWarning>
	 * @author lzshuai
	 * @date 2017-11-28 下午1:48:35
	 */
	@RequestMapping("/msgConfInterface/geMsgPlateList")
	public List<CuslendWarning> geMsgPlateList(@RequestBody CuslendWarning cuslendWarning) throws Exception;

	/**
	 * 
	 * 方法描述： 获取短信参数
	 * 
	 * @param bean
	 * @return
	 * @throws Exception
	 *             MfMsgVar
	 * @author lzshuai
	 * @date 2017-11-30 上午11:30:41
	 */
	@RequestMapping("/msgConfInterface/getMfMsgVarBean")
	public MfMsgVar getMfMsgVarBean(@RequestBody MfMsgVar bean) throws Exception;

}
