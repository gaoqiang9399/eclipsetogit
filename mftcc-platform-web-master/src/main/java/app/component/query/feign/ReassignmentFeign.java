/**
 * Copyright (C) DXHM 版权所有
 * 文件名： ReassignmentFeign.java
 * 包名： app.component.query.feign
 * 说明： 
 * @author 仇招
 * @date 2018年4月16日 上午9:59:27
 * @version V1.0
 */ 
package app.component.query.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.util.toolkit.Ipage;

/**
 * 类名： ReassignmentFeign
 * 描述：
 * @author 仇招
 * @date 2018年4月16日 上午9:59:27
 */
@FeignClient("mftcc-platform-factor")
public interface ReassignmentFeign {
	/**
	 * 改派分页查询
	 * 
	 * @param ipage
	 * @param reassignmentBean
	 * @return
	 * @throws Exception
	 * @author WangChao
	 * @date 2018-2-8 下午4:05:58
	 */
	@RequestMapping("/reassignment/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;

	/**
	 * 改派
	 * 
	 * @param userId
	 * @throws Exception
	 * @author WangChao
	 * @date 2018-2-8 下午4:05:49
	 */
	@RequestMapping("/reassignment/updateTaskChangeUser")
	public void updateTaskChangeUser(@RequestParam("pasNo") String pasNo, @RequestParam("wkfTaskNo") String wkfTaskNo, @RequestParam("bizPkNo") String bizPkNo,@RequestParam("userId") String userId) throws Exception;
}
