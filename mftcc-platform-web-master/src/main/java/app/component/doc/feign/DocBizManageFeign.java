package  app.component.doc.feign;

import java.util.List;

import app.component.doc.entity.DocTypeConfig;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.doc.entity.DocBizManage;
import app.util.toolkit.Ipage;

/**
* Title: DocBizManageBo.java
* Description:
* @author:@dhcc.com.cn
* @Tue Jan 26 11:15:50 GMT 2016
**/

@FeignClient("mftcc-platform-factor")
public interface DocBizManageFeign {
	
	@RequestMapping(value = "/docBizManage/insert")
	public void insert(@RequestBody DocBizManage docBizManage) throws ServiceException;
	
	@RequestMapping(value = "/docBizManage/delete")
	public void delete(@RequestBody DocBizManage docBizManage) throws ServiceException;
	
	@RequestMapping(value = "/docBizManage/update")
	public void update(@RequestBody DocBizManage docBizManage) throws ServiceException;
	
	@RequestMapping(value = "/docBizManage/getById")
	public DocBizManage getById(@RequestBody DocBizManage docBizManage) throws ServiceException;
	
	@RequestMapping(value = "/docBizManage/getByIdForDocDown")
	public DocBizManage getByIdForDocDown(@RequestBody DocBizManage docBizManage) throws ServiceException;

	
	@RequestMapping(value = "/docBizManage/getByRelNo")
	public List<DocBizManage> getByRelNo(@RequestBody DocBizManage docBizManage) throws ServiceException;
	
	@RequestMapping(value = "/docBizManage/getListByDocBizNo")
	public List<DocBizManage> getListByDocBizNo(@RequestBody String docBizNo) throws ServiceException;
	
	@RequestMapping(value = "/docBizManage/getFstListForPackDown")
	public List<DocBizManage> getFstListForPackDown(@RequestBody DocBizManage docBizManage) throws ServiceException;
	
	@RequestMapping(value = "/docBizManage/valiWkfDocIsUp")
	public List<String> valiWkfDocIsUp(@RequestParam("relNo")   String relNo,@RequestParam("scNo")  String scNo) throws ServiceException;
	
	@RequestMapping(value = "/docBizManage/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("docBizManage") DocBizManage docBizManage) throws ServiceException;

	@RequestMapping(value = "/docBizManage/getCount")
	public Integer getCount(@RequestBody DocBizManage docBizManage)throws ServiceException;

	@RequestMapping(value = "/docBizManage/getAll")
	public List<DocBizManage> getAll(@RequestBody DocBizManage docBizManage)throws ServiceException;
	
	@RequestMapping(value = "/docBizManage/getDocCount")
	public int getDocCount(@RequestBody DocBizManage docBizManage) throws Exception;

	@RequestMapping(value = "/docBizManage/getListDocSpiltName")
	public List<String> getListDocSpiltName(@RequestBody DocTypeConfig docTypeConfig) throws Exception;
	
}
