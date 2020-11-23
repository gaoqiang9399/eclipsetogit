package  app.component.thirdservice.lmxd.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.thirdservice.lmxd.entity.MfThirdServiceRecord;
import app.component.thirdservice.lmxd.entity.MfThirdServiceRecordParm;
import app.util.toolkit.Ipage;

/**
* Title: MfThirdServiceRecordBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Tue Dec 19 15:46:55 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface MfThirdServiceRecordFeign {
	
	@RequestMapping(value = "/mfThirdServiceRecord/insert")
	public void insert(@RequestBody MfThirdServiceRecord mfThirdServiceRecord) throws Exception;

	@RequestMapping(value = "/mfThirdServiceRecord/delete")
	public void delete(@RequestBody MfThirdServiceRecord mfThirdServiceRecord) throws Exception;
	
	@RequestMapping(value = "/mfThirdServiceRecord/update")
	public void update(@RequestBody MfThirdServiceRecord mfThirdServiceRecord) throws Exception;
	
	@RequestMapping(value = "/mfThirdServiceRecord/getById")
	public MfThirdServiceRecord getById(@RequestBody MfThirdServiceRecord mfThirdServiceRecord) throws Exception;
	
	@RequestMapping(value = "/mfThirdServiceRecord/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;
	
	@RequestMapping(value = "/mfThirdServiceRecord/findByPages")
	public List<MfThirdServiceRecord> findByPages(@RequestBody MfThirdServiceRecord mfThirdServiceRecord) throws Exception;
	/**
	 * 
	 * 方法描述：通过id查询报告内容
	 * @author 王俊杰
	 * @date 2017-12-4 下午2:51:50
	 */
	@RequestMapping(value = "/mfThirdServiceRecord/getHtmlContent")
	public Map<String,Object> getHtmlContent(@RequestBody String queryId) throws Exception;//通过id查询报告内容

	/**
	 * 通过客户号查询所有报告
	 */
	@RequestMapping(value = "/mfThirdServiceRecord/getListByCusNoAjax")
	public List<MfThirdServiceRecord> getListByCusNoAjax(@RequestBody MfThirdServiceRecord mfThirdServiceRecord) throws Exception;

	/**
	 * 方法描述： 身份认证
	 * @param mfThirdServiceRecordParm
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author 吕春杰
	 * @date 2018-8-24 下午14:00:01
	 */
	@RequestMapping(value = "/mfThirdServiceRecord/idAttestation")
	public Map<String, Object> idAttestation(@RequestBody MfThirdServiceRecordParm mfThirdServiceRecordParm)throws Exception;
	/**
	 * 方法描述： 短信服务
	 * @param mfThirdServiceRecordParm
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author 吕春杰
	 * @date 2018-8-24 下午14:00:01
	 */
	@RequestMapping(value = "/mfThirdServiceRecord/messageService")
	public Map<String, Object> messageService(@RequestBody MfThirdServiceRecordParm mfThirdServiceRecordParm)throws Exception;
	/**
	 * 方法描述： 户籍信息查询
	 * @param mfThirdServiceRecordParm
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author 吕春杰
	 * @date 2018-8-24 下午14:00:01
	 */
	@RequestMapping(value = "/mfThirdServiceRecord/censusDataQuery")
	public Map<String, Object> censusDataQuery(@RequestBody MfThirdServiceRecordParm mfThirdServiceRecordParm)throws Exception;
	/**
	 * 方法描述： 不良信息查询
	 * @param mfThirdServiceRecordParm
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author 吕春杰
	 * @date 2018-8-24 下午14:00:01
	 */
	@RequestMapping(value = "/mfThirdServiceRecord/badInfoQuery")
	public Map<String, Object> badInfoQuery(@RequestBody MfThirdServiceRecordParm mfThirdServiceRecordParm)throws Exception;
	/**
	 * 方法描述： 个人名下企业查询
	 * @param mfThirdServiceRecordParm
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author 吕春杰
	 * @date 2018-8-24 下午14:00:01
	 */
	@RequestMapping(value = "/mfThirdServiceRecord/personalCorpQuery")
	public Map<String, Object> personalCorpQuery(@RequestBody MfThirdServiceRecordParm mfThirdServiceRecordParm)throws Exception;
	/**
	 * 方法描述： 企业涉诉高法全类
	 * @param mfThirdServiceRecordParm
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author 吕春杰
	 * @date 2018-8-24 下午14:00:01
	 */
	@RequestMapping(value = "/mfThirdServiceRecord/corpLawsuitsAllClass")
	public Map<String, Object> corpLawsuitsAllClass(@RequestBody MfThirdServiceRecordParm mfThirdServiceRecordParm)throws Exception;
	/**
	 * 方法描述： 多重借贷查询
	 * @param mfThirdServiceRecordParm
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author 吕春杰
	 * @date 2018-8-24 下午14:00:01
	 */
	@RequestMapping(value = "/mfThirdServiceRecord/multipleLoansQuery")
	public Map<String, Object> multipleLoansQuery(@RequestBody MfThirdServiceRecordParm mfThirdServiceRecordParm)throws Exception;
	/**
	 * 方法描述： 企业工商数据查询
	 * @param mfThirdServiceRecordParm
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author 吕春杰
	 * @date 2018-8-24 下午14:00:01
	 */
	@RequestMapping(value = "/mfThirdServiceRecord/corpICData")
	public Map<String, Object> corpICData(@RequestBody MfThirdServiceRecordParm mfThirdServiceRecordParm)throws Exception;
	/**
	 * 方法描述： 失信黑名单查询
	 * @param mfThirdServiceRecordParm
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author 吕春杰
	 * @date 2018-8-24 下午14:00:01
	 */
	@RequestMapping(value = "/mfThirdServiceRecord/loseCreditBlacklist")
	public Map<String, Object> loseCreditBlacklist(@RequestBody MfThirdServiceRecordParm mfThirdServiceRecordParm)throws Exception;
	/**
	 * 方法描述： 失信人信息查询
	 * @param mfThirdServiceRecordParm
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author 吕春杰
	 * @date 2018-8-24 下午14:00:01
	 */
	@RequestMapping(value = "/mfThirdServiceRecord/loseCerditPersonInfo")
	public Map<String, Object> loseCerditPersonInfo(@RequestBody MfThirdServiceRecordParm mfThirdServiceRecordParm)throws Exception;
	/**
	 * 方法描述： 网贷黑名单信息
	 * @param mfThirdServiceRecordParm
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author 吕春杰
	 * @date 2018-8-24 下午14:00:01
	 */
	@RequestMapping(value = "/mfThirdServiceRecord/netLoanBlacklistInfo")
	public Map<String, Object> netLoanBlacklistInfo(@RequestBody MfThirdServiceRecordParm mfThirdServiceRecordParm)throws Exception;
	/**
	 * 方法描述： 网贷多重借贷查询
	 * @param mfThirdServiceRecordParm
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author 吕春杰
	 * @date 2018-8-24 下午14:00:01
	 */
	@RequestMapping(value = "/mfThirdServiceRecord/netLoanMultipleLoans")
	public Map<String, Object> netLoanMultipleLoans(@RequestBody MfThirdServiceRecordParm mfThirdServiceRecordParm)throws Exception;
	/**
	 * 方法描述： 行业多重借贷查询
	 * @param mfThirdServiceRecordParm
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author 吕春杰
	 * @date 2018-8-24 下午14:00:01
	 */
	@RequestMapping(value = "/mfThirdServiceRecord/indusMultipleLoans")
	public Map<String, Object> indusMultipleLoans(@RequestBody MfThirdServiceRecordParm mfThirdServiceRecordParm)throws Exception;
	/**
	 * 方法描述： 失信被执行人信息查询
	 * @param mfThirdServiceRecordParm
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author 吕春杰
	 * @date 2018-8-24 下午14:00:01
	 */
	@RequestMapping(value = "/mfThirdServiceRecord/loseCerditBZXRInfo")
	public Map<String, Object> loseCerditBZXRInfo(@RequestBody MfThirdServiceRecordParm mfThirdServiceRecordParm)throws Exception;
	/**
	 * 方法描述： 民间借贷行为验证
	 * @param mfThirdServiceRecordParm
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author 吕春杰
	 * @date 2018-8-24 下午14:00:01
	 */
	@RequestMapping(value = "/mfThirdServiceRecord/folkLoansBehavVerify")
	public Map<String, Object> folkLoansBehavVerify(@RequestBody MfThirdServiceRecordParm mfThirdServiceRecordParm)throws Exception;
	/**
	 * 方法描述： 非银体系逾期记录
	 * @param mfThirdServiceRecordParm
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author 吕春杰
	 * @date 2018-8-24 下午14:00:01
	 */
	@RequestMapping(value = "/mfThirdServiceRecord/notSilverSysOverdue")
	public Map<String, Object> notSilverSysOverdue(@RequestBody MfThirdServiceRecordParm mfThirdServiceRecordParm)throws Exception;
	/**
	 * 方法描述： 反欺诈信息查询
	 * @param mfThirdServiceRecordParm
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author 吕春杰
	 * @date 2018-8-24 下午14:00:01
	 */
	@RequestMapping(value = "/mfThirdServiceRecord/antiFraudInfoQuery")
	public Map<String, Object> antiFraudInfoQuery(@RequestBody MfThirdServiceRecordParm mfThirdServiceRecordParm)throws Exception;
	/**
	 * 方法描述： 信贷申请详情
	 * @param mfThirdServiceRecordParm
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author 吕春杰
	 * @date 2018-8-24 下午14:00:01
	 */
	@RequestMapping(value = "/mfThirdServiceRecord/creditLoanApply")
	public Map<String, Object> creditLoanApply(@RequestBody MfThirdServiceRecordParm mfThirdServiceRecordParm)throws Exception;
	/**
	 * 方法描述： 逾期欠款查询
	 * @param mfThirdServiceRecordParm
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author 吕春杰
	 * @date 2018-8-24 下午14:00:01
	 */
	@RequestMapping(value = "/mfThirdServiceRecord/overdueDebtQuery")
	public Map<String, Object> overdueDebtQuery(@RequestBody MfThirdServiceRecordParm mfThirdServiceRecordParm)throws Exception;

	@RequestMapping(value = "/mfThirdServiceRecord/findThirdServiceRecordList")
	public Ipage findThirdServiceRecordList(@RequestBody Ipage ipage) throws Exception;

	@RequestMapping(value = "/mfThirdServiceRecord/getThirdRecodLastOneByCusNo")
	public MfThirdServiceRecord getThirdRecodLastOneByCusNo(@RequestBody MfThirdServiceRecord mfThirdServiceRecord) throws Exception;


}
