package app.component.oa.expense.feign;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.oa.expense.entity.MfOaExpense;
import app.component.wkf.entity.Result;
import app.util.toolkit.Ipage;

/**
 * Title: MfOaExpenseBo.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Mon Dec 19 09:18:12 CST 2016
 **/
@FeignClient("mftcc-platform-factor")
public interface MfOaExpenseFeign {
	@RequestMapping(value = "/mfOaExpense/insert")
	public void insert(@RequestBody MfOaExpense mfOaExpense) throws ServiceException;

	@RequestMapping(value = "/mfOaExpense/insertForApp")
	public Map<String, String> insertForApp(@RequestBody MfOaExpense mfOaExpense) throws Exception;

	/*
	 * 根据id是否存在判断插入还是更新
	 * 
	 * @see app.component.oa.expense.bo.MfOaExpenseBo#IUById(app.component.oa.
	 * expense. entity.MfOaExpense)
	 */
	@RequestMapping(value = "/mfOaExpense/IUById",produces="text/html;charset=UTF-8")
	public String IUById(@RequestBody MfOaExpense mfOaExpense) throws ServiceException;

	@RequestMapping(value = "/mfOaExpense/delete")
	public void delete(@RequestBody MfOaExpense mfOaExpense) throws ServiceException;

	@RequestMapping(value = "/mfOaExpense/update")
	public void update(@RequestBody MfOaExpense mfOaExpense) throws ServiceException;

	@RequestMapping(value = "/mfOaExpense/queryById")
	public MfOaExpense queryById(@RequestBody MfOaExpense mfOaExpense) throws Exception;

	@RequestMapping(value = "/mfOaExpense/getById")
	public MfOaExpense getById(@RequestBody MfOaExpense mfOaExpense) throws ServiceException;

	// TODO
	@RequestMapping(value = "/mfOaExpense/updateForSubmit")
	public Result updateForSubmit(@RequestParam("taskId") String taskId, @RequestParam("expenseId") String expenseId,
			@RequestParam("opinionType") String opinionType, @RequestParam("approvalOpinion") String approvalOpinion,
			@RequestParam("transition") String transition, @RequestParam("regNo") String regNo,
			@RequestParam("nextUser") String nextUser, @RequestBody MfOaExpense mfOaExpense) throws ServiceException;

	/* public BigDecimal amtByExpenseSts() throws ServiceException; */
	@RequestMapping(value = "/mfOaExpense/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws ServiceException;

	@RequestMapping(value = "/mfOaExpense/insertForSubmit")
	public Map<String, Object> insertForSubmit(@RequestBody MfOaExpense mfOaExpense) throws ServiceException;

	@RequestMapping(value = "/mfOaExpense/getExpenseSumAmt")
	public Map<String, BigDecimal> getExpenseSumAmt(@RequestBody String opNo) throws Exception;

	// TODO
	@RequestMapping(value = "/mfOaExpense/updateForApplySubmit")
	public Result updateForApplySubmit(@RequestParam("taskId") String taskId,
			@RequestParam("expenseId") String expenseId, @RequestParam("opinionType") String opinionType,
			@RequestParam("approvalOpinion") String approvalOpinion, @RequestParam("transition") String transition,
			@RequestParam("regNo") String regNo, @RequestParam("nextUser") String nextUser,
			@RequestBody MfOaExpense mfOaExpense, @RequestParam("dataMap") Map<String, Object> dataMap)
			throws ServiceException;

	/**
	 * 方法描述： 中汇鑫德存入报销明细
	 * 
	 * @param mfOaBudgetMst
	 * @param dataMap
	 * @throws ServiceException
	 *             Map<String, Object>
	 * @author lcl
	 * @date 2017-6-15 下午5:33:01
	 */
	// TODO
	@RequestMapping(value = "/mfOaExpense/insertMap")
	public Map<String, Object> insert(@RequestBody MfOaExpense mfOaExpense,
			@RequestParam("dataMap") Map<String, Object> dataMap) throws ServiceException;

	/**
	 * 方法描述： 中汇鑫德查询报销实体和报销明细
	 * 
	 * @param expenseId
	 * @return
	 * @throws Exception
	 *             Map<String,Object>
	 * @author lcl
	 * @date 2017-6-15 下午6:54:32
	 */
	@RequestMapping(value = "/mfOaExpense/getExpesionById")
	public Map<String, Object> getExpesionById(@RequestBody Map<String, Object> paramMap) throws Exception;

	@RequestMapping(value = "/mfOaExpense/getCount")
	public int getCount() throws Exception;
}
