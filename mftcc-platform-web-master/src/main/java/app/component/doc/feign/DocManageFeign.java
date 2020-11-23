package  app.component.doc.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.doc.entity.DocManage;
import app.util.toolkit.Ipage;

/**
* Title: DocManageBo.java
* Description:
* @author:@dhcc.com.cn
* @Tue Jan 26 11:18:01 GMT 2016
**/

@FeignClient("mftcc-platform-factor")
public interface DocManageFeign {
	
	@RequestMapping(value = "/docManage/insert")
	public void insert(@RequestBody DocManage docManage) throws ServiceException;
	@RequestMapping(value = "/docManage/insertForApp")
	public void insertForApp(@RequestBody DocManage docManage) throws ServiceException;
	
	@RequestMapping(value = "/docManage/delete")
	public void delete(@RequestBody DocManage docManage) throws ServiceException;
	
	@RequestMapping(value = "/docManage/update")
	public void update(@RequestBody DocManage docManage) throws ServiceException;
	
	@RequestMapping(value = "/docManage/getById")
	public DocManage getById(@RequestBody DocManage docManage) throws ServiceException;
	
	@RequestMapping(value = "/docManage/getListForPackDown")
	public List<DocManage> getListForPackDown(@RequestBody DocManage docManage) throws ServiceException;

	@RequestMapping(value = "/docManage/getList")
	public List<DocManage> getList(@RequestBody DocManage docManage) throws ServiceException;
	
	@RequestMapping(value = "/docManage/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("docManage") DocManage docManage) throws ServiceException;
	/**
	 * 
	 * 方法描述： 根据业务编号删除上传的要件
	 * @param docManage
	 * @throws ServiceException
	 * void
	 * @author 沈浩兵
	 * @date 2017-10-10 下午3:40:42
	 */
	@RequestMapping(value = "/docManage/deleteByBizNo")
	public void deleteByBizNo(@RequestBody DocManage docManage) throws ServiceException;
	
}
