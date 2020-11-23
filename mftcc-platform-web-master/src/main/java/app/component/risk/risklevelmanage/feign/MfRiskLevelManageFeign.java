/**
 * Copyright (C) DXHM 版权所有
 * 文件名： MfRiskLevelManageFeign.java
 * 包名： app.component.risk.risklevelmanage.feign
 * 说明： 
 * @author 仇招
 * @date 2018年5月19日 下午6:05:37
 * @version V1.0
 */
package app.component.risk.risklevelmanage.feign;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.risk.risklevelmanage.entity.MfRiskLevelManage;
import app.component.wkf.entity.Result;
import app.util.toolkit.Ipage;

/**
 * 类名： MfRiskLevelManageFeign 
 * 描述：风险管理
 * @author 仇招
 * @date 2018年5月19日 下午6:05:37
 */
@FeignClient("mftcc-platform-factor")
public interface MfRiskLevelManageFeign {
	@RequestMapping("/mfRiskLevelManage/insert")
	public void insert(@RequestBody MfRiskLevelManage mfRiskLevelManage) throws Exception;

	@RequestMapping("/mfRiskLevelManage/delete")
	public void delete(@RequestBody MfRiskLevelManage mfRiskLevelManage) throws Exception;

	@RequestMapping("/mfRiskLevelManage/update")
	public void update(@RequestBody MfRiskLevelManage mfRiskLevelManage) throws Exception;

	@RequestMapping("/mfRiskLevelManage/getById")
	public MfRiskLevelManage getById(@RequestBody MfRiskLevelManage mfRiskLevelManage) throws Exception;

	@RequestMapping("/mfRiskLevelManage/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;
	
	@RequestMapping("/mfRiskLevelManage/submitProcess")
	public MfRiskLevelManage submitProcess(@RequestBody MfRiskLevelManage mfRiskLevelManage) throws Exception;
	
	@RequestMapping("/mfRiskLevelManage/doCommit")
	public Result doCommit(@RequestParam("taskId") String taskId,@RequestParam("transition") String transition,@RequestParam("nextUser") String nextUser, @RequestBody Map<String, Object> formDataMap)throws Exception;
	
	@RequestMapping("/mfRiskLevelManage/findRiskRgtByPage")
	public Ipage findRiskRgtByPage(@RequestBody Ipage ipage)throws Exception;

}
