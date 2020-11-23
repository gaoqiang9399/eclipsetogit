package  app.component.doc.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.doc.entity.DocBizSceConfig;
import app.component.doc.entity.DocDimm;
import app.component.doc.entity.SceDocTypeRel;
import app.util.toolkit.Ipage;

/**
* Title: DocBizSceConfigBo.java
* Description:
* @author:@dhcc.com.cn
* @Sat Jan 16 06:27:40 GMT 2016
**/

@FeignClient("mftcc-platform-factor")
public interface DocBizSceConfigFeign {
	
	@RequestMapping(value = "/docBizSceConfig/insert")
	public void insert(@RequestBody DocBizSceConfig docBizSceConfig) throws ServiceException;
	
	@RequestMapping(value = "/docBizSceConfig/delete")
	public void delete(@RequestBody DocBizSceConfig docBizSceConfig) throws ServiceException;
	
	@RequestMapping(value = "/docBizSceConfig/update")
	public void update(@RequestBody DocBizSceConfig docBizSceConfig) throws ServiceException;
	
	@RequestMapping(value = "/docBizSceConfig/getById")
	public DocBizSceConfig getById(@RequestBody DocBizSceConfig docBizSceConfig) throws ServiceException;
	
	@RequestMapping(value = "/docBizSceConfig/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("docBizSceConfig") DocBizSceConfig docBizSceConfig) throws ServiceException;
	
	@RequestMapping(value = "/docBizSceConfig/getDocDimms")
	public List<DocDimm> getDocDimms(@RequestBody DocDimm docDimm) throws ServiceException;
	
	@RequestMapping(value = "/docBizSceConfig/getSplitDocs")
	public List<DocBizSceConfig> getSplitDocs(@RequestBody DocBizSceConfig docBizSceConfig) throws ServiceException;
	
	
	@RequestMapping(value = "/docBizSceConfig/getSplitDocsForDisplay")
	public List<DocBizSceConfig> getSplitDocsForDisplay(@RequestBody DocBizSceConfig docBizSceConfig) throws ServiceException;

	
	@RequestMapping(value = "/docBizSceConfig/insertSceDocTypeRel")
	public void insertSceDocTypeRel(@RequestBody SceDocTypeRel sceDocTypeRel) throws ServiceException;
	
	@RequestMapping(value = "/docBizSceConfig/deleteSceDocTypeRel")
	public void deleteSceDocTypeRel(@RequestBody SceDocTypeRel sceDocTypeRel) throws ServiceException;
	
	@RequestMapping(value = "/docBizSceConfig/deleteBysplitNo")
	public void deleteBysplitNo(@RequestBody DocBizSceConfig docBizSceConfig) throws ServiceException;
	
	/**
	 * @author czk
	 * @Description: 根据Dime字段的值查询得到列表
	 * date 2016-12-26
	 */
	@RequestMapping(value = "/docBizSceConfig/getByDime")
	public List<DocBizSceConfig> getByDime(@RequestBody DocBizSceConfig docBizSceConfig) throws Exception;
	
	/**
	 * @author czk
	 * @Description: 使用IN查询
	 * date 2016-12-26
	 */
	@RequestMapping(value = "/docBizSceConfig/getByDimesAndScNo")
	public List<DocBizSceConfig> getByDimesAndScNo(@RequestBody Map map) throws Exception;

	/**
	 * 
	 * 方法描述： 根据Dime字段的值查询得到列表(后台产品设置-要件配置使用)
	 * @param docBizSceConfig
	 * @return
	 * @throws Exception
	 * List<Map<String, Object>>
	 * @author zhs
	 * @date 2017-6-26 上午11:39:36
	 */
	@RequestMapping(value = "/docBizSceConfig/getDocBizSecConfigInfo")
	public List<Map<String, Object>> getDocBizSecConfigInfo(@RequestBody DocBizSceConfig docBizSceConfig)throws Exception;

	/**
	 * 
	 * 方法描述： 批量插入要件的配置
	 * @param docBizSceConfigList
	 * @throws Exception
	 * void
	 * @author zhs
	 * @date 2017-6-28 下午3:56:52
	 */
	@RequestMapping(value = "/docBizSceConfig/insertDocs")
	public Map<String, Object> insertDocs(@RequestBody List<DocBizSceConfig> docBizSceConfigList) throws Exception;

	
	
	
	


	
}
