package app.component.rec.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.rec.entity.MfCollectionTable;
import app.util.toolkit.Ipage;

/**
 * Title: MfCollectionTableBo.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Sun Mar 19 15:03:41 CST 2017
 **/
@FeignClient("mftcc-platform-factor")
public interface MfCollectionTableFeign {
	@RequestMapping(value = "/MfCollectionTable/insert")
	public void insert(@RequestBody MfCollectionTable mfCollectionTable) throws ServiceException;

	@RequestMapping(value = "/MfCollectionTable/delete")
	public void delete(@RequestBody MfCollectionTable mfCollectionTable) throws ServiceException;

	@RequestMapping(value = "/MfCollectionTable/update")
	public void update(@RequestBody MfCollectionTable mfCollectionTable) throws ServiceException;

	@RequestMapping(value = "/MfCollectionTable/getById")
	public MfCollectionTable getById(@RequestBody MfCollectionTable mfCollectionTable) throws ServiceException;

	@RequestMapping(value = "/MfCollectionTable/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("mfCollectionTable") MfCollectionTable mfCollectionTable) throws ServiceException;

	@RequestMapping(value = "/MfCollectionTable/getAll")
	public List<MfCollectionTable> getAll(@RequestBody MfCollectionTable mfCollectionTable) throws ServiceException;

	@RequestMapping(value = "/MfCollectionTable/getList")
	public List<MfCollectionTable> getList(@RequestBody MfCollectionTable mfCollectionTable) throws ServiceException;

}
