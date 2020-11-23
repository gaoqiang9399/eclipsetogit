package  app.component.calc.core.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.calc.core.entity.MfBusFeePlan;
import app.util.toolkit.Ipage;

/**
* Title: MfBusFeePlanBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Tue Jun 20 15:41:03 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface MfBusFeePlanFeign {
	
	@RequestMapping(value = "/mfBusFeePlan/insert")
	public void insert(@RequestBody MfBusFeePlan mfBusFeePlan) throws Exception;
	
	@RequestMapping(value = "/mfBusFeePlan/delete")
	public void delete(@RequestBody MfBusFeePlan mfBusFeePlan) throws Exception;
	/**
	 * 
	 * 方法描述：通过借据号和期号更新费用计划
	 * @param mfBusFeePlan
	 * @throws Exception 
	 * void
	 * @author WD
	 * @date 2017-6-21 下午10:07:01
	 */
	@RequestMapping(value = "/mfBusFeePlan/update")
	public void update(@RequestBody MfBusFeePlan mfBusFeePlan) throws Exception;
	
	@RequestMapping(value = "/mfBusFeePlan/getById")
	public MfBusFeePlan getById(@RequestBody MfBusFeePlan mfBusFeePlan) throws Exception;
	
	@RequestMapping(value = "/mfBusFeePlan/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("mfBusFeePlan") MfBusFeePlan mfBusFeePlan) throws Exception;
	
	/**
	 * 方法描述：获取费用计划列表 
	 * @param mfBusFeePlan
	 * @return
	 * @throws Exception
	 * List<MfBusFeePlan>
	 * @author Javelin
	 * @date 2017-6-28 下午4:59:02
	 */
	@RequestMapping(value = "/mfBusFeePlan/getBusFeePlanList")
	public List<MfBusFeePlan> getBusFeePlanList(@RequestBody MfBusFeePlan mfBusFeePlan) throws Exception;
	@RequestMapping(value = "/mfBusFeePlan/getFeePlanList")
	public List<MfBusFeePlan> getFeePlanList(@RequestBody MfBusFeePlan mfBusFeePlan) throws Exception;
	/**
	 * 获取费用计划
	 * @param ipage
	 * @param mfBusFeePlan
	 * @return
	 */
	@RequestMapping(value = "/mfBusFeePlan/getReturnFeePlanList")
	public Ipage getReturnFeePlanList(@RequestBody Ipage ipage,@RequestParam("mfBusFeePlan")  MfBusFeePlan mfBusFeePlan) throws Exception;
    /**
     * 
     * 方法描述：通过费用计划信息 获取费用相关
     * @param mfBusFeePlan
     * @return
     * @throws Exception 
     * MfBusFeePlan
     * @author WD
     * @date 2017-8-5 上午10:03:07
     */
	@RequestMapping(value = "/mfBusFeePlan/getMfBusFeePlanByInfo")
	public List<MfBusFeePlan> getMfBusFeePlanByInfo(@RequestBody MfBusFeePlan mfBusFeePlan)throws Exception;
    /**
     * 方法描述：通过费用计划信息 获取费用相关信息
     * @param mfBusFeePlan
     * @return
     * @throws Exception
     */
	@RequestMapping(value = "/mfBusFeePlan/getMfBusFeePlanList")
	public List<MfBusFeePlan> getMfBusFeePlanList(@RequestBody MfBusFeePlan mfBusFeePlan)throws Exception;
    /**
     * 
     * 方法描述：通过 状态和日期 获取 逾期费用计划
     * @param busFeePlan
     * @return
     * @throws Exception 
     * List<MfBusFeePlan>
     * @author WD
     * @date 2017-8-24 下午5:53:47
     */
	@RequestMapping(value = "/mfBusFeePlan/getMfBusFeePlanListByOverBatch")
	public List<MfBusFeePlan> getMfBusFeePlanListByOverBatch(@RequestBody MfBusFeePlan busFeePlan)throws Exception;
    /**
     * 
     * 方法描述：获取最后一期的费用计划
     * @param lastMfBusFeePlan
     * @return
     * @throws Exception 
     * MfBusFeePlan
     * @author WD
     * @date 2017-9-23 下午5:36:07
     */
	@RequestMapping(value = "/mfBusFeePlan/getLastMfBusFeePlanByBean")
	public MfBusFeePlan getLastMfBusFeePlanByBean(@RequestBody MfBusFeePlan lastMfBusFeePlan)throws Exception;
    
	@RequestMapping(value = "/mfBusFeePlan/deleteMfBusFeePlanByInfo")
	public void deleteMfBusFeePlanByInfo(@RequestBody MfBusFeePlan mfBusFeePlanDel)throws Exception;
    /**
     * 
     * 方法描述：通过借据号 费用计划状态 费用计划类型  费用收取时间 期号 通过期号分组获取费用计划
     * @param mfBusFeePlan
     * @return 
     * List<MfBusFeePlan>
     * @author WD
     * @date 2018-1-3 下午2:46:44
     */
	@RequestMapping(value = "/mfBusFeePlan/getMfBusFeePlanByfeeBeanGroupByTerm")
	public List<MfBusFeePlan> getMfBusFeePlanByfeeBeanGroupByTerm(
			MfBusFeePlan mfBusFeePlan)throws Exception;
    /**
     * 
     * 方法描述：过借据号 和 费用收取时间 整体更新相关费用计划 或者 通过费用计划id 更新
     * @param busFeePlan
     * @throws Exception 
     * void
     * @author WD
     * @date 2018-1-5 下午3:34:48
     */
	@RequestMapping(value = "/mfBusFeePlan/updateMfBusFeePlanByFeeCollectInfo")
	public void updateMfBusFeePlanByFeeCollectInfo(@RequestBody MfBusFeePlan busFeePlan)throws Exception;
    /**
     * 
     * 方法描述：通过借据号 fincId 和  outFlag 期号 和 费用项编号 和 收取时间  获取该借据该费用项的费用计划
     * @param feeMapParm
     * @return
     * @throws Exception 
     * MfBusFeePlan
     * @author WD
     * @date 2018-1-5 下午7:42:07
     */
	@RequestMapping(value = "/mfBusFeePlan/getMfBusFeePlanByFeeMap")
	public MfBusFeePlan getMfBusFeePlanByFeeMap(@RequestBody Map<String, String> feeMapParm)throws Exception;
    /**
     * 
     * 方法描述 通过 AppId 或者fincId 获取费用总和实体
     * @param mfBusFeePlan
     * @return
     * @throws Exception 
     * MfBusFeePlan
     * @author WD
     * @date 2018-1-8 下午2:29:45
     */
	@RequestMapping(value = "/mfBusFeePlan/getSumBusFeePlan")
	public MfBusFeePlan getSumBusFeePlan(@RequestBody MfBusFeePlan mfBusFeePlan)throws Exception;
	
}
