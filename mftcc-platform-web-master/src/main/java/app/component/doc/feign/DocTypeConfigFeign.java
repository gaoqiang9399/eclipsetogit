package  app.component.doc.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.doc.entity.DocTypeConfig;
import app.util.toolkit.Ipage;

/**
* Title: DocTypeConfigBo.java
* Description:
* @author:@dhcc.com.cn
* @Sat Jan 16 09:13:29 GMT 2016
**/

@FeignClient("mftcc-platform-factor")
public interface DocTypeConfigFeign {
	
	@RequestMapping(value = "/docTypeConfig/insert")
	public void insert(@RequestBody DocTypeConfig docTypeConfig) throws ServiceException;
	
	@RequestMapping(value = "/docTypeConfig/delete")
	public void delete(@RequestBody DocTypeConfig docTypeConfig) throws ServiceException;
	
	@RequestMapping(value = "/docTypeConfig/update")
	public void update(@RequestBody DocTypeConfig docTypeConfig) throws ServiceException;
	
	@RequestMapping(value = "/docTypeConfig/getById")
	public DocTypeConfig getById(@RequestBody DocTypeConfig docTypeConfig) throws ServiceException;
	
	@RequestMapping(value = "/docTypeConfig/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws ServiceException;
	
	
	@RequestMapping(value = "/docTypeConfig/getListByDocType")
	public List<DocTypeConfig> getListByDocType(@RequestBody String docType) throws ServiceException;
	
	@RequestMapping(value = "/docTypeConfig/getList")
	public List<DocTypeConfig> getList(@RequestBody DocTypeConfig docTypeConfig) throws Exception;
	
	/**
	 * @author czk
	 * @Description: 删除文件类型，并删除文档模型下和此关联的记录。
	 * date 2016-12-28
	 */
	@RequestMapping(value = "/docTypeConfig/deleteAndBizSce")
	public void deleteAndBizSce(@RequestBody String docSplitNo) throws Exception;

	/**
	 * 
	 * 方法描述： 配置要件预览表单
	 * @param docTypeConfig
	 * @throws Exception
	 * void
	 * @author zhs
	 * @return 
	 * @date 2017-5-15 下午4:38:31
	 */
	@RequestMapping(value = "/docTypeConfig/checkAndCreateForm")
	public Map<String, Object> checkAndCreateForm(@RequestBody DocTypeConfig docTypeConfig) throws Exception;


}
