package app.component.oa.budget.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.oa.budget.entity.MfOaBudgetDetail;
import app.component.oa.budget.entity.MfOaBudgetMst;
import app.component.wkf.entity.Result;
import app.util.toolkit.Ipage;

/**
 * Title: MfOaBudgetMstBo.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Fri Jun 09 10:50:44 CST 2017
 **/
@FeignClient("mftcc-platform-factor")
public interface MfOaBudgetMstFeign {
	@RequestMapping(value = "/mfOaBudgetMst/insert")
	public String insert(@RequestBody MfOaBudgetMst mfOaBudgetMst) throws ServiceException;

	/**
	 * 方法描述： 插入预算数据
	 * 
	 * @param mfOaBudgetMst
	 * @param dataMap
	 * @throws ServiceException
	 *             void
	 * @author Javelin
	 * @return
	 * @date 2017-6-9 下午2:23:57
	 */
	// MfOaBudgetMst mfOaBudgetMst, Map<String, Object> dataMap
	@RequestMapping(value = "/mfOaBudgetMst/insertMap",produces = "text/html;charset=UTF-8")
	public String insert(@RequestBody Map<String, Object> paramMap) throws ServiceException;

	@RequestMapping(value = "/mfOaBudgetMst/delete")
	public void delete(@RequestBody MfOaBudgetMst mfOaBudgetMst) throws ServiceException;

	@RequestMapping(value = "/mfOaBudgetMst/update")
	public void update(@RequestBody MfOaBudgetMst mfOaBudgetMst) throws ServiceException;

	@RequestMapping(value = "/mfOaBudgetMst/getById")
	public MfOaBudgetMst getById(@RequestBody MfOaBudgetMst mfOaBudgetMst) throws ServiceException;

	/**
	 * 方法描述： 获取预算主列表
	 * 
	 * @param ipage
	 * @param mfOaBudgetMst
	 * @return
	 * @throws ServiceException
	 *             Ipage
	 * @author Javelin
	 * @date 2017-6-9 下午2:22:32
	 */
	@RequestMapping(value = "/mfOaBudgetMst/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws ServiceException;

	/**
	 * 方法描述： 获取预算详情列表
	 * 
	 * @param dataMap
	 * @return
	 * @throws Exception
	 *             List<MfOaBudgetDetail>
	 * @author Javelin
	 * @date 2017-6-9 下午2:26:27
	 */
	@RequestMapping(value = "/mfOaBudgetMst/getMfOaBudgerDetailList")
	public List<MfOaBudgetDetail> getMfOaBudgerDetailList(@RequestBody Map<String, String> dataMap) throws Exception;

	@RequestMapping(value = "/mfOaBudgetMst/getAll")
	public List<MfOaBudgetMst> getAll(@RequestBody MfOaBudgetMst mfOaBudgetMst) throws ServiceException;

	/**
	 * 方法描述： 流程启用
	 * 
	 * @param budgetId
	 * @return
	 * @throws ServiceException
	 *             String
	 * @author Javelin
	 * @date 2017-6-9 下午4:35:09
	 */
	@RequestMapping(value = "/mfOaBudgetMst/startProcess")
	public String startProcess(@RequestBody String budgetId) throws ServiceException;

	/**
	 * 方法描述： 流程提交
	 * 
	 * @param taskId
	 * @param budgetId
	 * @param transition
	 * @param nextUser
	 * @param dataMap
	 * @return
	 * @throws ServiceException
	 *             Result
	 * @author Javelin
	 * @throws Exception
	 * @date 2017-6-9 下午4:44:06
	 */
	// TODO
	@RequestMapping(value = "/mfOaBudgetMst/commitProcess")
	public Result commitProcess(@RequestParam("taskId") String taskId, @RequestParam("budgetId") String budgetId,
			@RequestParam("transition") String transition, @RequestParam("nextUser") String nextUser,
			@RequestParam("dataMap") Map<String, Object> dataMap) throws Exception, Exception;

	/**
	 * 方法描述： 获取数据数量
	 * 
	 * @param mfOaBudgetMst
	 * @return
	 * @throws ServiceException
	 *             int
	 * @author Javelin
	 * @date 2017-6-16 下午5:45:29
	 */
	@RequestMapping(value = "/mfOaBudgetMst/getDataCount")
	public int getDataCount(@RequestBody MfOaBudgetMst mfOaBudgetMst) throws ServiceException;
}
