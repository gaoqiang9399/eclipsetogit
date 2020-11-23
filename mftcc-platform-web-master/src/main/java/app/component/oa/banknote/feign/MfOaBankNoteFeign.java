package app.component.oa.banknote.feign;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.oa.banknote.entity.MfOaBankNote;
import app.component.wkf.entity.Result;
import app.util.toolkit.Ipage;

/**
 * Title: MfOaBankNoteBo.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Tue Jun 13 10:05:32 CST 2017
 **/
@FeignClient("mftcc-platform-factor")
public interface MfOaBankNoteFeign {
	@RequestMapping(value = "/mfOaBankNote/insert")
	public void insert(@RequestBody MfOaBankNote mfOaBankNote) throws ServiceException;

	@RequestMapping(value = "/mfOaBankNote/delete")
	public void delete(@RequestBody MfOaBankNote mfOaBankNote) throws ServiceException;

	@RequestMapping(value = "/mfOaBankNote/update")
	public void update(@RequestBody MfOaBankNote mfOaBankNote) throws ServiceException;

	@RequestMapping(value = "/mfOaBankNote/getById")
	public MfOaBankNote getById(@RequestBody MfOaBankNote mfOaBankNote) throws ServiceException;

	@RequestMapping(value = "/mfOaBankNote/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws ServiceException;

	@RequestMapping(value = "/mfOaBankNote/insertForSubmit")
	public Map<String, Object> insertForSubmit(@RequestBody MfOaBankNote mfOaBankNote) throws ServiceException;

	@RequestMapping(value = "/mfOaBankNote/doSubmit")
	public Result doSubmit(@RequestParam("taskId") String taskId, @RequestParam("billId") String billId,
			@RequestParam("opinionType") String opinionType, @RequestParam("approvalOpinion") String approvalOpinion,
			@RequestParam("transition") String transition, @RequestParam("regNo") String regNo,
			@RequestParam("nextUser") String nextUser, @RequestBody MfOaBankNote mfOaBankNote)
			throws ServiceException;

	@RequestMapping(value = "/mfOaBankNote/getCount")
	public int getCount() throws ServiceException;
}
