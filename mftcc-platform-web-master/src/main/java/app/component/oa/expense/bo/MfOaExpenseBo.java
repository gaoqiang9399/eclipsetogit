package  app.component.oa.expense.bo;

import java.math.BigDecimal;
import java.util.Map;

import app.base.ServiceException;
import app.component.oa.expense.entity.MfOaExpense;
import app.component.wkf.entity.Result;
import app.util.toolkit.Ipage;

/**
* Title: MfOaExpenseBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Mon Dec 19 09:18:12 CST 2016
**/

public interface MfOaExpenseBo {
	
	public void insert(MfOaExpense mfOaExpense) throws ServiceException;
	public Map<String,String> insertForApp(MfOaExpense mfOaExpense) throws Exception;
	/*
	 * 根据id是否存在判断插入还是更新
	 * @see app.component.oa.expense.bo.MfOaExpenseBo#IUById(app.component.oa.expense.entity.MfOaExpense)
	 */
	public String IUById(MfOaExpense mfOaExpense) throws ServiceException;
	public void delete(MfOaExpense mfOaExpense) throws ServiceException;
	public void update(MfOaExpense mfOaExpense) throws ServiceException;
	public MfOaExpense queryById(MfOaExpense mfOaExpense) throws Exception;
	public MfOaExpense getById(MfOaExpense mfOaExpense) throws ServiceException;
	public Result updateForSubmit(String taskId, String expenseId,
			String opinionType, String approvalOpinion, String transition,
			String regNo, String nextUser, MfOaExpense mfOaExpense)throws ServiceException;
	/*public BigDecimal amtByExpenseSts() throws ServiceException;*/
	public Ipage findByPage(Ipage ipage,MfOaExpense mfOaExpense) throws ServiceException;
	public Map<String, Object> insertForSubmit(MfOaExpense mfOaExpense) throws ServiceException;
	public Map<String, BigDecimal> getExpenseSumAmt(String opNo) throws Exception;
	public Result updateForApplySubmit(String taskId, String expenseId,
			String opinionType, String approvalOpinion, String transition,
			String regNo, String nextUser, MfOaExpense mfOaExpense, Map<String, Object> dataMap)throws ServiceException;

	/**
	 * 方法描述： 中汇鑫德存入报销明细
	 * @param mfOaBudgetMst
	 * @param dataMap
	 * @throws ServiceException
	 * Map<String, Object>
	 * @author lcl
	 * @date 2017-6-15 下午5:33:01
	 */
	public Map<String, Object> insert(MfOaExpense mfOaExpense, Map<String, Object> dataMap) throws ServiceException;
	/**
	 * 方法描述： 中汇鑫德查询报销实体和报销明细
	 * @param expenseId
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author lcl
	 * @date 2017-6-15 下午6:54:32
	 */
	public Map<String, Object> getExpesionById(String expenseId) throws Exception;
	
	public int getCount() throws Exception;
}
