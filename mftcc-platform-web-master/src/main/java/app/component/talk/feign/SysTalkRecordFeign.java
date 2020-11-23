package  app.component.talk.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.talk.entity.SysTalkRecord;
import app.util.toolkit.Ipage;

/**
* Title: SysTalkRecordBo.java
* Description:
* @author:@dhcc.com.cn
* @Mon Jan 14 07:02:01 GMT 2013
**/
@FeignClient("mftcc-platform-factor")
public interface SysTalkRecordFeign {
	@RequestMapping(value = "/sysTalkRecord/getById")
	public SysTalkRecord getById(@RequestBody SysTalkRecord sysTalkRecord) throws ServiceException;
	@RequestMapping(value = "/sysTalkRecord/del")
	public void del(@RequestBody SysTalkRecord sysTalkRecord) throws ServiceException;
	@RequestMapping(value = "/sysTalkRecord/insert")
	public void insert(@RequestBody SysTalkRecord sysTalkRecord) throws ServiceException;
	@RequestMapping(value = "/sysTalkRecord/update")
	public void update(@RequestBody SysTalkRecord sysTalkRecord) throws ServiceException;
	@RequestMapping(value = "/sysTalkRecord/updateStatus")
	public void updateStatus(@RequestBody SysTalkRecord sysTalkRecord) throws ServiceException;
	@RequestMapping(value = "/sysTalkRecord/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipg,@RequestParam("sysTalkRecord") SysTalkRecord sysTalkRecord) throws ServiceException;
	@RequestMapping(value = "/sysTalkRecord/findMyMsg")
	public Ipage findMyMsg(@RequestBody Ipage ipg) throws ServiceException;
	@RequestMapping(value = "/sysTalkRecord/getUnReadRecords")
	public List<SysTalkRecord> getUnReadRecords(@RequestBody SysTalkRecord sysTalkRecord) throws ServiceException;
	@RequestMapping(value = "/sysTalkRecord/getUnReadSource")
	public List<SysTalkRecord> getUnReadSource(@RequestBody SysTalkRecord sysTalkRecord) throws ServiceException;
}
