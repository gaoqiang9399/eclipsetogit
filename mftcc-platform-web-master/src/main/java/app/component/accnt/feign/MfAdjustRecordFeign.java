package app.component.accnt.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.accnt.entity.MfAdjustRecord;
import app.util.toolkit.Ipage;

/**
 * Title: MfAdjustRecordBo.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Sat Jun 04 17:14:31 CST 2016
 **/
@FeignClient("mftcc-platform-factor")
public interface MfAdjustRecordFeign {
	@RequestMapping(value = "/mfAdjustRecord/insert")
	public void insert(@RequestBody MfAdjustRecord mfAdjustRecord) throws ServiceException;

	@RequestMapping(value = "/mfAdjustRecord/delete")
	public void delete(@RequestBody MfAdjustRecord mfAdjustRecord) throws ServiceException;

	@RequestMapping(value = "/mfAdjustRecord/update")
	public void update(@RequestBody MfAdjustRecord mfAdjustRecord) throws ServiceException;

	@RequestMapping(value = "/mfAdjustRecord/getById")
	public MfAdjustRecord getById(@RequestBody MfAdjustRecord mfAdjustRecord) throws ServiceException;

	@RequestMapping(value = "/mfAdjustRecord/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("mfAdjustRecord") MfAdjustRecord mfAdjustRecord) throws ServiceException;

	@RequestMapping(value = "/mfAdjustRecord/getList")
	public List<MfAdjustRecord> getList(@RequestBody MfAdjustRecord mfAdjustRecord) throws ServiceException;

}
