package  app.component.calc.penalty.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.calc.penalty.entity.MfBusAppPenaltyChild;
import app.util.toolkit.Ipage;

/**
* Title: MfBusAppPenaltyChildBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Sat Jul 01 17:33:08 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface MfBusAppPenaltyChildFeign {
	
	@RequestMapping(value = "/mfBusAppPenaltyChild/insert")
	public void insert(@RequestBody MfBusAppPenaltyChild mfBusAppPenaltyChild) throws Exception;
	
	@RequestMapping(value = "/mfBusAppPenaltyChild/delete")
	public void delete(@RequestBody MfBusAppPenaltyChild mfBusAppPenaltyChild) throws Exception;
	
	@RequestMapping(value = "/mfBusAppPenaltyChild/update")
	public void update(@RequestBody MfBusAppPenaltyChild mfBusAppPenaltyChild) throws Exception;
	
	@RequestMapping(value = "/mfBusAppPenaltyChild/getById")
	public MfBusAppPenaltyChild getById(@RequestBody MfBusAppPenaltyChild mfBusAppPenaltyChild) throws Exception;
	
	@RequestMapping(value = "/mfBusAppPenaltyChild/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("mfBusAppPenaltyChild") MfBusAppPenaltyChild mfBusAppPenaltyChild) throws Exception;
    /**
     * 
     * 方法描述：通过申请号 和借据剩余日期获取违约金计算比例
     * @param appPenaltyChildQuery
     * @return 
     * MfBusAppPenaltyChild
     * @author WD
     * @date 2017-7-21 上午10:16:12
     */
	@RequestMapping(value = "/mfBusAppPenaltyChild/getMfBusAppPenaltyChildByBean")
	public MfBusAppPenaltyChild getMfBusAppPenaltyChildByBean(
			MfBusAppPenaltyChild appPenaltyChildQuery)throws Exception;
	
}
