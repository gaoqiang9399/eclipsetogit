package  app.component.wkf.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.wkf.entity.WkfApprovalOpinion;
import app.util.toolkit.Ipage;

/**
* Title: WkfApprovalOpinionBo
* Description:
* @author:zhanglei@dhcc.com.cn
* @Fri Feb 22 10:03:00 CST 2013
**/
@FeignClient("mftcc-platform-factor")
public interface WkfApprovalOpinionFeign {
	
	@RequestMapping(value = "/wkfApprovalOpinion/insert")
	public Ipage findByPage(@RequestBody Ipage ipg,@RequestParam("wkfApprovalOpinion") WkfApprovalOpinion wkfApprovalOpinion) throws ServiceException;
	@RequestMapping(value = "/wkfApprovalOpinion/findAllForLoan")
	public List<WkfApprovalOpinion> findAllForLoan(@RequestBody WkfApprovalOpinion wkfApprovalOpinion) throws ServiceException;
	@RequestMapping(value = "/wkfApprovalOpinion/findAllForApply")
	public List<WkfApprovalOpinion> findAllForApply(@RequestBody WkfApprovalOpinion wkfApprovalOpinion) throws ServiceException;
	/**
	 * @see 根据业务主键获取审批意见历史
	 * @param appNo 业务主键
	 */
	@RequestMapping(value = "/wkfApprovalOpinion/getWkfTaskHisList")
	public List<WkfApprovalOpinion> getWkfTaskHisList(@RequestBody WkfApprovalOpinion wkfApprovalOpinion) throws ServiceException;
	@RequestMapping(value = "/wkfApprovalOpinion/findAll")
	public List<WkfApprovalOpinion> findAll(@RequestBody WkfApprovalOpinion wkfApprovalOpinion) throws ServiceException;
	@RequestMapping(value = "/wkfApprovalOpinion/getLastOpinion")
	public WkfApprovalOpinion getLastOpinion(@RequestBody WkfApprovalOpinion wkfApprovalOpinion) throws ServiceException;
}