package app.component.calccoreinterface;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.calc.core.entity.MfRepayHistory;
import app.component.calc.core.entity.MfRepayHistoryDetail;
/**
 * 提前还款 接口
 * @author WD
 *
 */
@FeignClient("mftcc-platform-factor")
public interface CalcPrepaymentInterfaceFeign {
	/**
	 * 
	 * 方法描述：获取提前还款页面信息
	 * @param fincId
	 * @return 
	 * Map<String,Object>
	 * @author WD
	 * @date 2017-7-1 下午4:19:46
	 */
	@RequestMapping(value = "/calcPrepaymentInterface/doPrepaymentJsp")
	public Map<String, Object> doPrepaymentJsp(@RequestBody String fincId) throws Exception;
	/**
	 * 
	 * 方法描述： 提前还款操作业务处理
	 * @param parmMap
	 * 
	 * @return
	 * @throws Exception 
	 * Map<String,Object>
	 * @author WD
	 * @date 2017-7-7 上午9:29:36
	 */
	@RequestMapping(value = "/calcPrepaymentInterface/doPrepaymentOperate")
	public Map<String, Object> doPrepaymentOperate(@RequestBody Map<String, Object> parmMap) throws Exception;
	
	/**
	 * 
	 * 方法描述：提前还款时 判断该笔借据是否允许提前还款
	 * @param parmMap
	 * @return 
	 * Map<String,Object>
	 * @author WD
	 * @date 2017-7-8 下午5:31:40
	 */
	@RequestMapping(value = "/calcPrepaymentInterface/doCheckTiQianHuanKuan")
	public Map<String, Object> doCheckTiQianHuanKuan(@RequestBody Map<String, String> parmMap)throws Exception;
	/**
	 * 
	 * 方法描述：获取提前还款 利息  和 违约金  
	 * @param parmMap.put("fincId",fincId);//借据号
@RequestParam("parmMap.put("repayDate"") 			  parmMap.put("repayDate",@RequestParam("repayDate")  repayDate);//还款日期
@RequestParam("parmMap.put("tiQianHuanBen"") 			  parmMap.put("tiQianHuanBen",@RequestParam("tiQianHuanBen")  tiQianHuanBen);//提前还本 金额
	 * @return 
	 * Map<String,Object>
	 * @author WD
	 * @date 2017-7-12 下午2:54:57
	 */
	@RequestMapping(value = "/calcPrepaymentInterface/doCalcLiXiTiQianHuanKuan")
	public Map<String, Object> doCalcLiXiTiQianHuanKuan(
			Map<String, String> parmMap)throws Exception;
	
	
	/**
	 * 
	 * 方法描述：判断该笔借据是否能够提前还款且提前还款相关金额是否正确
	 * @param parmMap
	 * @return 
	 * Map<String,Object>
	 * @author WD
	 * @date 2017-7-14 上午9:51:27
	 */
	@RequestMapping(value = "/calcPrepaymentInterface/doCheckPrepaymentOperate")
	public Map<String, Object> doCheckPrepaymentOperate(
			Map<String, Object> parmMap)throws Exception;
	/**
	 * 方法描述 插入还款历史总表
	 * @param mfRepayHistory
	 * @throws Exception
	 */
	@RequestMapping(value = "/calcPrepaymentInterface/insertReapyHistory")
	public void insertReapyHistory(@RequestBody MfRepayHistory mfRepayHistory)throws Exception;
	/**
	 * 方法描述 插入还款历史明细表
	 * @param mfRepayHistory
	 * @throws Exception
	 */
	@RequestMapping(value = "/calcPrepaymentInterface/insertMfRepayHistoryDetail")
	public void insertMfRepayHistoryDetail(@RequestBody MfRepayHistoryDetail mfRepayHistoryDetail)throws Exception;
	
	
    /**
     * 
     * 方法描述：通过借据号获取提前还款利息  本金和 违约金 （利息按照剩余本金计算且计算到当天 返回本金为借据未还本金）
	 * @param parmMap.put("fincId",fincId);//借据号
	 * 
	 * @return Map 中的key  shiShouBenJin                   提前还款本金</br>
	 *                     shiShouBenJinFormat             提前还款本金格式化</br>
	 *                     shiShouLiXi                     提前还款利息</br>
	 *                     shiShouLiXiFormat               提前还款利息格式化</br>
	 *                     tiQianHuanKuanWeiYueJin         提前还款违约金</br>
	 *                     tiQianHuanKuanWeiYueJinFormat   提前还款违约金格式化</br>
     * @throws Exception 
     * Map<String,Object>
     * @author WD
     * @date 2018-1-26 上午11:46:40
     */
	@RequestMapping(value = "/calcPrepaymentInterface/doCalcLiXiTiQianHuanKuanByFincInfo")
	public Map<String, Object> doCalcLiXiTiQianHuanKuanByFincInfo(
			Map<String, String> parmMap)throws Exception;
	
	/**
	 * 
	 * 方法描述：通过借据号,提前还款本金,提前还款利息，提前还款违约金,还款日期 进行提前还款         
	 * @param parmMap 	   fincId                          借据号</br>
	 *                     shiShouBenJin                   提前还款本金</br>
	 *                     shiShouLiXi                     提前还款利息</br>
	 *                     tiQianHuanKuanWeiYueJin         提前还款违约金</br>
	 *                     repayDate                       提前还款日期<br>
	 *                     
	 * @return  resultMap.put("flag", "0");//返回结果  0 为提前还款成功  其他失败
	 * @throws Exception 
	 * Map<String,Object>
	 * @author WD
	 * @date 2018-1-26 下午2:40:25
	 */
	@RequestMapping(value = "/calcPrepaymentInterface/doPrepaymentOperateByFincInfo")
	public Map<String, Object> doPrepaymentOperateByFincInfo(@RequestBody Map<String, Object> parmMap)
			throws Exception;
}
