package  app.component.doc.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.doc.entity.DocFormConfig;
import app.util.toolkit.Ipage;

/**
* Title: DocFormConfigBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Mon May 15 15:37:13 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface DocFormConfigFeign {
	
	@RequestMapping(value = "/docFormConfig/insert")
	public void insert(@RequestBody DocFormConfig docFormConfig) throws Exception;
	
	@RequestMapping(value = "/docFormConfig/delete")
	public void delete(@RequestBody DocFormConfig docFormConfig) throws Exception;
	
	@RequestMapping(value = "/docFormConfig/update")
	public void update(@RequestBody DocFormConfig docFormConfig) throws Exception;
	
	@RequestMapping(value = "/docFormConfig/getById")
	public DocFormConfig getById(@RequestBody DocFormConfig docFormConfig) throws Exception;
	
	@RequestMapping(value = "/docFormConfig/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("docFormConfig") DocFormConfig docFormConfig) throws Exception;
	
}
