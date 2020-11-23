package app.component.model.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.model.entity.MfTemplateShow;
import app.util.toolkit.Ipage;

/**
 * Title: MfTemplateShowBo.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Mon Jul 31 15:32:34 CST 2017
 **/

@FeignClient("mftcc-platform-factor")
public interface MfTemplateShowFeign {

	@RequestMapping(value = "/mfTemplateShow/insert")
	public void insert(@RequestBody MfTemplateShow mfTemplateShow) throws ServiceException;

	@RequestMapping(value = "/mfTemplateShow/delete")
	public void delete(@RequestBody MfTemplateShow mfTemplateShow) throws ServiceException;

	@RequestMapping(value = "/mfTemplateShow/update")
	public void update(@RequestBody MfTemplateShow mfTemplateShow) throws ServiceException;

	@RequestMapping(value = "/mfTemplateShow/getById")
	public MfTemplateShow getById(@RequestBody MfTemplateShow mfTemplateShow) throws ServiceException;

	@RequestMapping(value = "/mfTemplateShow/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("mfTemplateShow")  MfTemplateShow mfTemplateShow) throws ServiceException;

	@RequestMapping(value = "/mfTemplateShow/getList")
	public List<MfTemplateShow> getList(@RequestBody MfTemplateShow templateShow) throws Exception;
}
