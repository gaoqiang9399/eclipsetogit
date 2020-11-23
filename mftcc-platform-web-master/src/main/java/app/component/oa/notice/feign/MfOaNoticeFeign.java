package  app.component.oa.notice.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.base.ServiceException;
import app.component.oa.notice.entity.MfOaNotice;
import app.util.toolkit.Ipage;

/**
* Title: MfOaNoticeBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Fri Dec 09 16:16:55 CST 2016
**/
@FeignClient("mftcc-platform-factor")
public interface MfOaNoticeFeign {
	@RequestMapping(value = "/mfOaNotice/insert")
	public void insert(@RequestBody MfOaNotice mfOaNotice) throws ServiceException;
	
	@RequestMapping(value = "/mfOaNotice/delete")
	public void delete(@RequestBody MfOaNotice mfOaNotice) throws ServiceException;
	
	@RequestMapping(value = "/mfOaNotice/update")
	public void update(@RequestBody MfOaNotice mfOaNotice) throws ServiceException;
	
	@RequestMapping(value = "/mfOaNotice/updateisTop")
	public void updateisTop(@RequestBody MfOaNotice mfOaNotice) throws ServiceException;
	
	@RequestMapping(value = "/mfOaNotice/updateNoticeSts")
	public void updateNoticeSts(@RequestBody MfOaNotice mfOaNotice) throws Exception;
	
	@RequestMapping(value = "/mfOaNotice/getById")
	public MfOaNotice getById(@RequestBody MfOaNotice mfOaNotice) throws ServiceException;
	
	@RequestMapping(value = "/mfOaNotice/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws ServiceException;
	
	@RequestMapping(value = "/mfOaNotice/getByStartDate")
	public List<MfOaNotice> getByStartDate(@RequestBody MfOaNotice mfOaNotice) throws ServiceException;
	
	@RequestMapping(value = "/mfOaNotice/getByEndDate")
	public List<MfOaNotice> getByEndDate(@RequestBody MfOaNotice mfOaNotice) throws ServiceException;
	
	@RequestMapping(value = "/mfOaNotice/getNoticeCount")
	public int getNoticeCount(@RequestBody MfOaNotice mfOaNotice) throws ServiceException;
	
	@RequestMapping(value = "/mfOaNotice/getAllList")
	public List<MfOaNotice> getAllList(@RequestBody MfOaNotice mfOaNotice) throws ServiceException;
	
	@RequestMapping(value = "/mfOaNotice/getLooking")
	public Ipage getLooking(@RequestBody Ipage ipage) throws Exception;

	@RequestMapping(value = "/mfOaNotice/findMfOaNoticeByOpNo")
	public Ipage findMfOaNoticeByOpNo(@RequestBody Ipage ipage) throws Exception;
	
}
