/**
 * Copyright (C) 仇招 版权所有
 * 文件名： ResolveHisProcessUtilFeign.java
 * 包名： app.tech.wkf.feign
 * 说明： 
 * @author 仇招
 * @date 2018年3月23日 下午5:07:00
 * @version V1.0
 */ 
package app.tech.wkf.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import net.sf.json.JSONArray;

import java.util.List;
import java.util.Map;

/**
 * @类名： ResolveHisProcessUtilFeign
 * @描述：
 * @author 仇招
 * @date 2018年3月23日 下午5:07:00
 */
@FeignClient("mftcc-platform-factor")
public interface ResolveHisProcessUtilFeign {
	/**
	 * 通过业务编号 获取审批流程图所需要的数据
	 * @param appNo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/resolveHisProcessUtil/resolveProcess")
	public  JSONArray resolveProcess(@RequestParam("appNo") String appNo) throws Exception;

	/**
	 * 通过业务编号 获取审批历史列表数据
	 * @param appNo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/resolveHisProcessUtil/getWfHistTaskList")
	public  List<Map<String,String>> getWfHistTaskList(@RequestParam("appNo") String appNo,@RequestParam("formType") String formType,@RequestParam("approveType") String approveType) throws Exception;

	/**
	 * 根据dbId获取审批历史中的业务信息（app_value_中的值）
	 * @param taskId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/resolveHisProcessUtil/getWfHistTaskInfoById")
	public Map<String,String> getWfHistTaskInfoById(@RequestParam("taskId") String taskId,@RequestParam("approveType") String approveType) throws Exception;
}
