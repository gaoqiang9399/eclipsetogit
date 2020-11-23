package  app.component.oa.budget.bo;
import java.util.List;
import java.util.Map;

import app.base.ServiceException;
import app.component.oa.budget.entity.MfOaBudgetDetail;
import app.component.oa.budget.entity.MfOaBudgetMst;
import app.component.wkf.entity.Result;
import app.util.toolkit.Ipage;

/**
* Title: MfOaBudgetMstBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Fri Jun 09 10:50:44 CST 2017
**/

public interface MfOaBudgetMstBo {
	
	public String insert(MfOaBudgetMst mfOaBudgetMst) throws ServiceException;
	
	/**
	 * 方法描述： 插入预算数据
	 * @param mfOaBudgetMst
	 * @param dataMap
	 * @throws ServiceException
	 * void
	 * @author Javelin
	 * @return 
	 * @date 2017-6-9 下午2:23:57
	 */
	public String insert(MfOaBudgetMst mfOaBudgetMst, Map<String, Object> dataMap) throws ServiceException;
	
	public void delete(MfOaBudgetMst mfOaBudgetMst) throws ServiceException;
	
	public void update(MfOaBudgetMst mfOaBudgetMst) throws ServiceException;
	
	public MfOaBudgetMst getById(MfOaBudgetMst mfOaBudgetMst) throws ServiceException;
	
	/**
	 * 方法描述： 获取预算主列表
	 * @param ipage
	 * @param mfOaBudgetMst
	 * @return
	 * @throws ServiceException
	 * Ipage
	 * @author Javelin
	 * @date 2017-6-9 下午2:22:32
	 */
	public Ipage findByPage(Ipage ipage,MfOaBudgetMst mfOaBudgetMst) throws ServiceException;
	
	/**
	 * 方法描述： 获取预算详情列表
	 * @param dataMap
	 * @return
	 * @throws Exception
	 * List<MfOaBudgetDetail>
	 * @author Javelin
	 * @date 2017-6-9 下午2:26:27
	 */
	public List<MfOaBudgetDetail> getMfOaBudgerDetailList(Map<String, String> dataMap) throws Exception;

	public List<MfOaBudgetMst> getAll(MfOaBudgetMst mfOaBudgetMst) throws ServiceException;
	
	/**
	 * 方法描述： 流程启用
	 * @param budgetId
	 * @return
	 * @throws ServiceException
	 * String
	 * @author Javelin
	 * @date 2017-6-9 下午4:35:09
	 */
	public String startProcess(String budgetId) throws ServiceException;
	
	/**
	 * 方法描述： 流程提交
	 * @param taskId
	 * @param budgetId
	 * @param transition
	 * @param nextUser
	 * @param dataMap
	 * @return
	 * @throws ServiceException
	 * Result
	 * @author Javelin
	 * @throws Exception 
	 * @date 2017-6-9 下午4:44:06
	 */
	public Result commitProcess(String taskId, String budgetId, String transition, String nextUser, Map<String, Object> dataMap) throws Exception, Exception;

	/**
	 * 方法描述： 获取数据数量
	 * @param mfOaBudgetMst
	 * @return
	 * @throws ServiceException
	 * int
	 * @author Javelin
	 * @date 2017-6-16 下午5:45:29
	 */
	public int getDataCount(MfOaBudgetMst mfOaBudgetMst) throws ServiceException;
}
