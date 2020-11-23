package  app.component.calc.core.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.calc.core.entity.MfBusOverBaseRecord;

/**
* Title: MfBusOverBaseRecordBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Mon Jun 12 15:48:59 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface MfBusOverBaseRecordFeign {
	
	@RequestMapping(value = "/mfBusOverBaseRecord/insert")
	public void insert(@RequestBody MfBusOverBaseRecord mfBusOverBaseRecord) throws Exception;
	
	@RequestMapping(value = "/mfBusOverBaseRecord/delete")
	public void delete(@RequestBody MfBusOverBaseRecord mfBusOverBaseRecord) throws Exception;
	
	@RequestMapping(value = "/mfBusOverBaseRecord/update")
	public void update(@RequestBody MfBusOverBaseRecord mfBusOverBaseRecord) throws Exception;
	
	@RequestMapping(value = "/mfBusOverBaseRecord/getById")
	public MfBusOverBaseRecord getById(@RequestBody MfBusOverBaseRecord mfBusOverBaseRecord) throws Exception;
	
    /**
     * 查看 逾期基数情况记录表 中信息 查看该期还款  再逾期基数情况表中 最近一次的数据记录 一条
     * @param busOverBaseRecord
     * @return
     */
	@RequestMapping(value = "/mfBusOverBaseRecord/getMfBusOverBaseRecordByBean")
	public MfBusOverBaseRecord getMfBusOverBaseRecordByBean(
			MfBusOverBaseRecord busOverBaseRecord)throws Exception;
	
}
