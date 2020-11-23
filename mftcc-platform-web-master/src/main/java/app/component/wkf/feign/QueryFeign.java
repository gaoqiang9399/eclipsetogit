/**
 * Copyright(c)2013
 * DHC Software Co., Ltd.
 *
 * All Rights Reserved
 *
 * Revision History:
 *                       Modification        Tracking
 * Author (Email ID)     Date                Number              Description
 * liping               2013-03-11          BugId				 
 *
 */
package app.component.wkf.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.wkf.entity.WorkflowActivity;
import app.component.wkf.entity.WorkflowInstance;
import app.component.wkf.entity.WorkflowTask;
import app.util.toolkit.Ipage;
@FeignClient("mftcc-platform-factor")
public interface QueryFeign {
	@RequestMapping(value = "/query/listActivity")
	public Ipage listActivity(@RequestBody Ipage ipage) throws Exception;
	@RequestMapping(value = "/query/listInstance")
	public Ipage listInstance(@RequestBody Ipage ipage) throws Exception;
	@RequestMapping(value = "/query/listTask")
	public Ipage listTask(@RequestBody Ipage ipage) throws Exception;
}
