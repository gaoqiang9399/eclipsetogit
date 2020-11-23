package app.component.rec.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.rec.entity.MfCollectionFormConfig;
import app.util.toolkit.Ipage;

/**
 * Title: MfCollectionFormConfigBo.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Sun Mar 19 15:13:37 CST 2017
 **/
@FeignClient("mftcc-platform-factor")
public interface MfCollectionFormConfigFeign {
	@RequestMapping(value = "/MfCollectionFormConfig/insert")
	public void insert(@RequestBody MfCollectionFormConfig mfCollectionFormConfig) throws ServiceException;

	@RequestMapping(value = "/MfCollectionFormConfig/delete")
	public void delete(@RequestBody MfCollectionFormConfig mfCollectionFormConfig) throws ServiceException;

	@RequestMapping(value = "/MfCollectionFormConfig/update")
	public void update(@RequestBody MfCollectionFormConfig mfCollectionFormConfig) throws ServiceException;

	@RequestMapping(value = "/MfCollectionFormConfig/getById")
	public MfCollectionFormConfig getById(@RequestBody MfCollectionFormConfig mfCollectionFormConfig) throws ServiceException;

	@RequestMapping(value = "/MfCollectionFormConfig/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("mfCollectionFormConfig") MfCollectionFormConfig mfCollectionFormConfig) throws ServiceException;

	@RequestMapping(value = "/MfCollectionFormConfig/getAll")
	public List<MfCollectionFormConfig> getAll(@RequestBody MfCollectionFormConfig mfCollectionFormConfig) throws ServiceException;

	@RequestMapping(value = "/MfCollectionFormConfig/getByCollectionTypeAndAction")
	public MfCollectionFormConfig getByCollectionTypeAndAction(@RequestParam("collectionType") String collectionType,@RequestParam("action") String action)
			throws ServiceException;
}
