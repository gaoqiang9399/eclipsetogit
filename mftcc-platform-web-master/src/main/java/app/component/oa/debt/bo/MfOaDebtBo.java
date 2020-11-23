package  app.component.oa.debt.bo;

import java.math.BigDecimal;
import java.util.Map;

import app.base.ServiceException;
import app.component.oa.debt.entity.MfOaDebt;
import app.component.wkf.entity.Result;
import app.util.toolkit.Ipage;

/**
* Title: MfOaDebtBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Sat Dec 17 14:00:23 CST 2016
**/

public interface MfOaDebtBo {
	
	public void insert(MfOaDebt mfOaDebt) throws ServiceException;
	public Map<String,String> insertForApp(MfOaDebt mfOaDebt) throws ServiceException;
	
	public void delete(MfOaDebt mfOaDebt) throws ServiceException;
	
	public void update(MfOaDebt mfOaDebt) throws ServiceException;
	
	public MfOaDebt getById(MfOaDebt mfOaDebt) throws ServiceException;
	
	public Ipage findByPage(Ipage ipage,MfOaDebt mfOaDebt) throws ServiceException;

	public Map<String, String> insertForSubmit(MfOaDebt mfOaDebt) throws Exception;
	public Map<String, String> insertForSubmitForApp(MfOaDebt mfOaDebt) throws Exception;

	public Result updateForSubmit1(String taskId, String debtId,
			String opinionType, String approvalOpinion, String transition,
			String regNo, String nextUser, MfOaDebt mfOaDebt);

	public BigDecimal[]  sumAmt(String opNo) throws ServiceException;
	/**
	 * 
	 * 方法描述：获取当前登录人员借款总额
	 * @param opNo
	 * @return
	 * @throws ServiceException
	 * Map<String,Object>
	 * @author zhs
	 * @date 2017-3-8 下午5:31:32
	 */
	public Map<String, BigDecimal> getDebtSumAmt(String opNo) throws ServiceException;
}
