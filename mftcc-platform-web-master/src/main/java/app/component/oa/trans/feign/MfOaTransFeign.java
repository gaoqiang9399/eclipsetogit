package app.component.oa.trans.feign;

import java.util.List;
import java.util.Map;

import app.component.app.entity.MfBusApply;
import app.component.app.entity.MfCusAndApply;
import app.component.cus.entity.MfCusCustomer;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.oa.trans.entity.MfOaTrans;
import app.component.wkf.entity.Result;
import app.util.toolkit.Ipage;

/**
 * Title: MfOaTransBo.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Tue Apr 25 15:20:00 CST 2017
 **/
@FeignClient("mftcc-platform-factor")
public interface MfOaTransFeign {
	@RequestMapping(value = "/mfOaTrans/insert")
	public MfOaTrans insert(@RequestBody Map<String,Object> paramMap)
			throws ServiceException;

	@RequestMapping(value = "/mfOaTrans/cusinsert")
	public MfOaTrans cusinsert(@RequestBody Map<String,Object> paramMap)
			throws ServiceException;

	@RequestMapping(value = "/mfOaTrans/delete")
	public void delete(@RequestBody MfOaTrans mfOaTrans) throws ServiceException;

	@RequestMapping(value = "/mfOaTrans/update")
	public void update(@RequestBody MfOaTrans mfOaTrans) throws ServiceException;

	@RequestMapping(value = "/mfOaTrans/getById")
	public MfOaTrans getById(@RequestBody MfOaTrans mfOaTrans) throws ServiceException;

	@RequestMapping(value = "/mfOaTrans/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws ServiceException;

	@RequestMapping(value = "/mfOaTrans/getByTransId")
	public List<MfOaTrans> getByTransId(@RequestBody MfOaTrans mfOaTrans) throws Exception;

	@RequestMapping(value = "/mfOaTrans/doCommit")
	public Result doCommit(@RequestParam("taskId") String taskId, @RequestParam("id") String id,
			@RequestParam("opinionType") String opinionType, @RequestParam("approvalOpinion") String approvalOpinion,
			@RequestParam("transition") String transition, @RequestParam("opNo") String opNo,
			@RequestParam("nextUser") String nextUser, 
			@RequestBody Map<String, Object> dataMap) throws Exception;

	@RequestMapping(value = "/mfOaTrans/findByPageHis")
	public Ipage findByPageHis(@RequestBody Ipage ipage) throws ServiceException;
	@RequestMapping(value = "/mfOaTrans/findMfOaTransDetailListByPageAjax")
    Ipage findMfOaTransDetailListByPageAjax(Ipage ipage) throws Exception;

	/**
	 * @Description 获取客户维度工作移交页面回显展示列表数据
	 * @Author zhaomingguang
	 * @DateTime 2019/10/31 10:23
	 * @Param 
	 * @return 
	 */
	@RequestMapping(value = "/mfOaTrans/getCusOaTransListData")
    List<MfCusCustomer> getCusOaTransListData(Map<String,String> paraMap) throws Exception;

	/**
	 * @Description 获取项目维度工作移交页面回显展示列表数据
	 * @Author zhaomingguang
	 * @DateTime 2019/10/31 17:50
	 * @Param 
	 * @return 
	 */
	@RequestMapping(value = "/mfOaTrans/getBusOaTransListData")
	List<MfCusAndApply> getBusOaTransListData(Map<String,String> paraMap) throws Exception;
}
