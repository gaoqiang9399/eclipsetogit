package  app.component.calc.core.feign;

import java.util.List;
import java.util.Map;

import app.util.toolkit.Ipage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.calc.core.entity.MfRepayPlan;

/**
* Title: MfRepayPlanBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Tue May 16 21:02:41 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface MfRepayPlanFeign {
	
	@RequestMapping(value = "/mfRepayPlan/insert")
	public void insert(@RequestBody MfRepayPlan mfRepayPlan) throws Exception;
	
	@RequestMapping(value = "/mfRepayPlan/delete")
	public void delete(@RequestBody MfRepayPlan mfRepayPlan) throws Exception;
	
	@RequestMapping(value = "/mfRepayPlan/update")
	public void update(@RequestBody MfRepayPlan mfRepayPlan) throws Exception;
	/**
	 * 批量插入
	 * @param mfRepayPlans
	 * @throws Exception
	 */
	@RequestMapping(value = "/mfRepayPlan/insert")
	public void insert(@RequestBody List<MfRepayPlan> mfRepayPlans)  throws Exception;
    /**
     * 方法描述：获取还款计划列表信息
     * @param mfRepayPlan
     * @return
     * @throws Exception
     */
	@RequestMapping(value = "/mfRepayPlan/getMfBusRepayPlanList")
	public List<MfRepayPlan> getMfBusRepayPlanList(@RequestBody MfRepayPlan mfRepayPlan)throws Exception;
    /**
     * 获取已还款信息
     * @param mfRepayPlan
     * @return
     * @throws Exception
     */
	@RequestMapping(value = "/mfRepayPlan/findByPage")
	public List<MfRepayPlan> findByPage(@RequestBody MfRepayPlan mfRepayPlan)throws Exception;
    /**
     * 获取还款相关信息 获取最新一期应还信息
     * @param mfRepayPlan
     * @return
     * @throws Exception
     */
	@RequestMapping(value = "/mfRepayPlan/getMfBusRepayPlanListOnlyOne")
	public List<MfRepayPlan> getMfBusRepayPlanListOnlyOne(
			MfRepayPlan mfRepayPlan)throws Exception;
    /**
     * 获取一期还款计划信息
     * @param mfRepayPlan
     * @return
     * @throws Exception
     */
	@RequestMapping(value = "/mfRepayPlan/getMfRepayPlanById")
	public MfRepayPlan getMfRepayPlanById(@RequestBody MfRepayPlan mfRepayPlan)throws Exception;
    /**
     * 通过id获取还款计划列表信息
     * @param bean
     * @return
     * @throws Exception
     */
	@RequestMapping(value = "/mfRepayPlan/getMfBusRepayPlanListById")
	public List<MfRepayPlan> getMfBusRepayPlanListById(@RequestBody MfRepayPlan bean)throws Exception;
    /**
     * 通过id获取还款计划列表信息
     * @param ipage
     * @return
     * @throws Exception
     */
	@RequestMapping(value = "/mfRepayPlan/findPageById")
	public Ipage findPageById(@RequestBody Ipage ipage)throws Exception;
    /**
     * 逾期批量 获取要逾期的还款计划列表
     * @param mfRepayPlan
     * @return
     * @throws Exception
     */
	@RequestMapping(value = "/mfRepayPlan/getMfBusRepayPlanListByOverBatch")
	public List<MfRepayPlan> getMfBusRepayPlanListByOverBatch(
			MfRepayPlan mfRepayPlan)throws Exception;
    /**
     * 通过借据号和还款状态 获取还款计划条数
     * @param mfRepayPlan
     * @return
     * @throws Exception
     */
	@RequestMapping(value = "/mfRepayPlan/getMfRepayPlanCount")
	public int getMfRepayPlanCount(@RequestBody MfRepayPlan mfRepayPlan)throws Exception;
	/**
	 * 合并还款计划时通过借据号更新还款计划
	 * @param mfRepayPlan
	 * @throws Exception
	 * @author Jiasl
	 */
	@RequestMapping(value = "/mfRepayPlan/updatePlanList")
	public void updatePlanList(@RequestBody MfRepayPlan mfRepayPlan) throws Exception;
    /**
     * 
     * 方法描述：通过借据号和期号更新还款计划
     * @param upMfRepayPlan
     * @throws Exception 
     * void
     * @author WD
     * @date 2017-7-8 上午10:45:07
     */
	@RequestMapping(value = "/mfRepayPlan/updateMfRepayPlan")
	public void updateMfRepayPlan(@RequestBody MfRepayPlan upMfRepayPlan)throws Exception;
	
	/**
	 * 
	 * 方法描述：通过借据号 和 期号 更新  大于改期号的还款计划
	 * @param upMfRepayPlan
	 * @throws Exception 
	 * void
	 * @author WD
	 * @date 2017-8-3 下午3:33:25
	 */
	@RequestMapping(value = "/mfRepayPlan/updateMfRepayPlanByInfo")
	public void updateMfRepayPlanByInfo(@RequestBody MfRepayPlan upMfRepayPlan) throws Exception;
    /**
     * 
     * 方法描述：提前还款时 删除借据当期后的所有还款计划
     * @param curMfRepayPlan
     * @throws Exception 
     * void
     * @author WD
     * @date 2017-7-12 上午8:57:52
     */
	@RequestMapping(value = "/mfRepayPlan/deleteCurMfRepayPlanList")
	public void deleteCurMfRepayPlanList(@RequestBody MfRepayPlan curMfRepayPlan)throws Exception;
    /**
     * 方法描述：通过 借据号 获取该笔借据的 最后一期的还款计划 
     * @param curMfRepayPlan
     * @return 
     * MfRepayPlan
     * @author WD
     * @date 2017-7-12 上午11:18:58
     */
	@RequestMapping(value = "/mfRepayPlan/getLastRepayPlanList")
	public MfRepayPlan getLastRepayPlanList(@RequestBody MfRepayPlan curMfRepayPlan)throws Exception;
	/**
	 * 
	* @Title: getMfRepayPlanByDateAndOutFlag  
	* @Description:  根据  借据号，当前日期，还款状态（过滤掉已换状态） 获取为结清的还款计划
	* @param @param mfRepayPlan
	* @param @return
	* @param @throws Exception    参数  
	* @return List<MfRepayPlan>    返回类型  
	* @throws
	 */
	@RequestMapping(value = "/mfRepayPlan/getMfRepayPlanByDateAndOutFlag")
	public List<MfRepayPlan> getMfRepayPlanByDateAndOutFlag(@RequestBody MfRepayPlan mfRepayPlan)throws Exception;
    /**
     * 
     * 方法描述：通过借据号 从还款计划表中 获取 未还利息
     * @param mfRepayPlan
     * @return
     * @throws Exception 
     * String
     * @author WD
     * @date 2017-8-3 上午9:10:54
     */
	@RequestMapping(value = "/mfRepayPlan/getRepayIntstFromRepayPlan")
	public String getRepayIntstFromRepayPlan(@RequestBody MfRepayPlan mfRepayPlan)throws Exception;
    /**
     * 
     * 方法描述：通过借据号 期号 或者 还款计划id 获取一期还款计划
     * @param mfRepayPlan
     * @return
     * @throws Exception 
     * MfRepayPlan
     * @author WD
     * @date 2017-8-9 下午6:04:35
     */
	@RequestMapping(value = "/mfRepayPlan/getRepayPlanByBean")
	public MfRepayPlan getRepayPlanByBean(@RequestBody MfRepayPlan mfRepayPlan)throws Exception;
    /**
     * 
     * 方法描述：通过合同id和还款计划状态 获取还款计划列表
     * @param mfRepayPlanPact
     * @return
     * @throws Exception 
     * List<MfRepayPlan>
     * @author WD
     * @date 2017-9-5 下午6:57:31
     */
	@RequestMapping(value = "/mfRepayPlan/getMfBusRepayPlanListByPactId")
	public List<MfRepayPlan> getMfBusRepayPlanListByPactId(
			MfRepayPlan mfRepayPlanPact)throws Exception;
    /**
     * 
     * 方法描述：通过借据号和期号 获取该笔借据的未还款利息之和
     * @param mfRepayPlan
     * @return  repayIntst  feeSum
     * @throws Exception 
     * String
     * @author WD
     * @date 2017-10-10 下午5:01:45
     */
	@RequestMapping(value = "/mfRepayPlan/getShengYuQuBuLiXiBYMfRepayPlan")
	public Map<String,Object> getShengYuQuBuLiXiBYMfRepayPlan(@RequestBody MfRepayPlan mfRepayPlan)throws Exception;

	/**
	 * @Description 根据客户号和还款计划状态查询还款计划数据
	 * @Author zhaomingguang
	 * @DateTime 2019/9/20 15:02
	 * @Param 
	 * @return 
	 */
	@RequestMapping(value = "/mfRepayPlan/getMfBusRepayPlanListByCus")
	List<MfRepayPlan> getMfBusRepayPlanListByCus(MfRepayPlan mfRepayPlan);
}
