/**
 * Copyright (C) 仇招 版权所有
 * 文件名： ResolveProcessUtilFeign.java
 * 包名： app.tech.wkf.feign
 * 说明： 
 * @author 仇招
 * @date 2018年3月23日 下午5:16:58
 * @version V1.0
 */ 
package app.tech.wkf.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import net.sf.json.JSONArray;

/**
 * @类名： ResolveProcessUtilFeign
 * @描述：
 * @author 仇招
 * @date 2018年3月23日 下午5:16:58
 */
@FeignClient("mftcc-platform-factor")
public interface ResolveProcessUtilFeign {
	/**
	 * 根据流程标识 获取 解析成JSON的数据
	 * @param key 流程标识
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/resolveProcessUtil/getDeploymentByKey")
	public  JSONArray getDeploymentByKey(@RequestParam("key") String key) throws Exception ;
	
	/**
	 * 通过业务编号 获取审批流程图所需要的数据
	 * @param appNo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/resolveProcessUtil/resolveProcess")
	public  JSONArray resolveProcess(@RequestParam("appNo") String appNo) throws Exception;
	/**
	 * 根据流程定义号返回解析成JSON结构的数据格式
	 * @param deploymentId 流程定义编号
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/resolveProcessUtil/getDeploymentById")
	public  JSONArray getDeploymentById(@RequestParam("deploymentId") String deploymentId) throws Exception;
}
