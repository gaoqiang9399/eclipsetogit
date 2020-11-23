package  app.component.doc.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.doc.entity.DocBizSceConfig;
import app.component.doc.entity.DocModel;
import app.util.toolkit.Ipage;

/**
* Title: DocModelBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Wed Dec 21 15:43:44 CST 2016
**/

@FeignClient("mftcc-platform-factor")
public interface DocModelFeign {
	
	@RequestMapping(value = "/docModel/insert")
	public void insert(@RequestBody DocModel docModel) throws ServiceException;
	
	@RequestMapping(value = "/docModel/delete")
	public void delete(@RequestBody DocModel docModel) throws ServiceException;
	
	@RequestMapping(value = "/docModel/update")
	public void update(@RequestBody DocModel docModel) throws ServiceException;
	
	@RequestMapping(value = "/docModel/getById")
	public DocModel getById(@RequestBody DocModel docModel) throws ServiceException;
	
	@RequestMapping(value = "/docModel/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("docModel") DocModel docModel) throws ServiceException;
	
	@RequestMapping(value = "/docModel/getList")
	public List<DocModel> getList(@RequestBody DocModel docModel) throws Exception;
	
	@RequestMapping(value = "/docModel/insert")
	public void insert(@RequestBody DocModel docModel,@RequestParam("docBizSceConfigList")List<DocBizSceConfig> docBizSceConfigList) throws ServiceException;
	@RequestMapping(value = "/docModel/update1")
	public void update(@RequestBody DocModel docModel,@RequestParam("docBizSceConfigList")List<DocBizSceConfig> docBizSceConfigList) throws ServiceException;

	/**
	 * @author czk
	 * @Description: 删除文档模板，并删除此模板下关联的文档。
	 * date 2016-12-29
	 */
	@RequestMapping(value = "/docModel/deleteAndBizSce")
	public void deleteAndBizSce(@RequestBody String docModelNo) throws ServiceException;

	@RequestMapping(value = "/docModel/findByCusType")
	public DocModel findByCusType(@RequestBody DocModel docModel);
}
