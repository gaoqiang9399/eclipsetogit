package app.component.oa.consumable.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.base.ServiceException;
import app.component.oa.consumable.entity.MfOaCons;
import app.util.toolkit.Ipage;

/**
 * Title: MfOaConsBo.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Sat Dec 24 11:56:30 CST 2016
 **/
@FeignClient("mftcc-platform-factor")
public interface MfOaConsFeign {
	@RequestMapping(value = "/mfOaCons/insert")
	public void insert(@RequestBody Map<String ,Object> paramMap ) throws ServiceException;

	@RequestMapping(value = "/mfOaCons/delete")
	public void delete(@RequestBody MfOaCons mfOaCons) throws ServiceException;

	@RequestMapping(value = "/mfOaCons/update")
	public void update(@RequestBody MfOaCons mfOaCons) throws ServiceException;

	@RequestMapping(value = "/mfOaCons/getById")
	public MfOaCons getById(@RequestBody MfOaCons mfOaCons) throws ServiceException;

	@RequestMapping(value = "/mfOaCons/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws ServiceException;

	@RequestMapping(value = "/mfOaCons/getByClass")
	public List<MfOaCons> getByClass(@RequestBody MfOaCons mfOaCons) throws ServiceException;

	@RequestMapping(value = "/mfOaCons/update1")
	public void update(@RequestBody Map<String ,Object> paramMap) throws ServiceException;

	@RequestMapping(value = "/mfOaCons/findByPageAndClass")
	public Ipage findByPageAndClass(@RequestBody Ipage ipage) throws ServiceException;

	@RequestMapping(value = "/mfOaCons/getAppNumByOpNo")
	public String getAppNumByOpNo(@RequestBody String opNo) throws ServiceException;

	@RequestMapping(value = "/mfOaCons/getOaConsCount")
	public Integer getOaConsCount(@RequestBody MfOaCons mfOaCons) throws ServiceException;
}
