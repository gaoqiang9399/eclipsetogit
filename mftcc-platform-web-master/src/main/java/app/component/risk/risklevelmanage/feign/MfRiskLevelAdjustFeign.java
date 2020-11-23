/**
 * Copyright (C) DXHM 版权所有
 * 文件名： MfRiskLevelAdjustFeign.java
 * 包名： app.component.risk.risklevelmanage.feign
 * 说明： 
 * @author 仇招
 * @date 2018年5月19日 下午8:29:43
 * @version V1.0
 */
package app.component.risk.risklevelmanage.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.risk.risklevelmanage.entity.MfRiskLevelAdjust;
import app.component.wkf.entity.Result;
import app.util.toolkit.Ipage;

/**
 * 类名： MfRiskLevelAdjustFeign 描述：风险等级调整
 * 
 * @author 仇招
 * @date 2018年5月19日 下午8:29:43
 */
@FeignClient("mftcc-platform-factor")
public interface MfRiskLevelAdjustFeign {

	@RequestMapping("/mfRiskLevelAdjust/insert")
	public Map<String, Object> insert(@RequestBody MfRiskLevelAdjust mfRiskLevelAdjust) throws Exception;

	@RequestMapping("/mfRiskLevelAdjust/delete")
	public void delete(@RequestBody MfRiskLevelAdjust mfRiskLevelAdjust) throws Exception;

	@RequestMapping("/mfRiskLevelAdjust/update")
	public void update(@RequestBody MfRiskLevelAdjust mfRiskLevelAdjust) throws Exception;

	@RequestMapping("/mfRiskLevelAdjust/getById")
	public MfRiskLevelAdjust getById(@RequestBody MfRiskLevelAdjust mfRiskLevelAdjust) throws Exception;

	@RequestMapping("/mfRiskLevelAdjust/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;

	@RequestMapping("/mfRiskLevelAdjust/submitProcess1")
	public MfRiskLevelAdjust submitProcess1(@RequestBody MfRiskLevelAdjust mfRiskLevelAdjust) throws Exception;

	@RequestMapping("/mfRiskLevelAdjust/submitProcess2")
	public MfRiskLevelAdjust submitProcess2(@RequestBody MfRiskLevelAdjust mfRiskLevelAdjust) throws Exception;

	@RequestMapping("/mfRiskLevelAdjust/submitProcess3")
	public MfRiskLevelAdjust submitProcess3(@RequestBody MfRiskLevelAdjust mfRiskLevelAdjust) throws Exception;
	
	@RequestMapping("/mfRiskLevelAdjust/submitProcess4")
	public MfRiskLevelAdjust submitProcess4(@RequestBody MfRiskLevelAdjust mfRiskLevelAdjust) throws Exception;
	
	@RequestMapping("/mfRiskLevelAdjust/doCommit")
	public Result doCommit(@RequestParam("taskId") String taskId, @RequestParam("transition") String transition,
			@RequestParam("nextUser") String nextUser, @RequestBody Map<String, Object> dataMap) throws Exception;
	
	@RequestMapping("/mfRiskLevelAdjust/getByRiskId")
	public List<MfRiskLevelAdjust> getByRiskId(@RequestBody MfRiskLevelAdjust mfRiskLevelAdjust) throws Exception;
	/**
	 * 方法描述： 获取处置方案
	 * @return
	 * @throws Exception
	 * MfRiskLevelAdjust
	 * @author 仇招
	 * @date 2018年6月29日 上午11:32:16
	 */
	@RequestMapping("/mfRiskLevelAdjust/getManageTypeByRiskId")
	public MfRiskLevelAdjust getManageTypeByRiskId(@RequestBody MfRiskLevelAdjust mfRiskLevelAdjust) throws Exception;

	
}
