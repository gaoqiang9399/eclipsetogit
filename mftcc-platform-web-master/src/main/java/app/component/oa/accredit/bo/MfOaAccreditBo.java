package  app.component.oa.accredit.bo;
import java.util.List;
import java.util.Map;

import app.base.ServiceException;
import app.component.oa.accredit.entity.MfOaAccredit;
import app.util.toolkit.Ipage;

/**
* Title: MfOaAccreditBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Tue Apr 25 11:16:07 CST 2017
**/

public interface MfOaAccreditBo {
	
	public void insert(MfOaAccredit mfOaAccredit) throws ServiceException;
	
	public void delete(MfOaAccredit mfOaAccredit) throws ServiceException;
	
	public int[] update(MfOaAccredit mfOaAccredit) throws ServiceException;
	
	public MfOaAccredit getById(MfOaAccredit mfOaAccredit) throws ServiceException;
	
	public Ipage findByPage(Ipage ipage,MfOaAccredit mfOaAccredit) throws ServiceException;

	public List<MfOaAccredit> getListForLogin(String authorizerNo) throws ServiceException;
	
	public void updateAll(MfOaAccredit mfOaAccredit) throws ServiceException;
	
	public Map<String , String > getSum(String authorizerNo) throws ServiceException;
	
	public String  getCount(String opNo) throws ServiceException;
	/**
	 * 将参与托管的任务记录到托管辅助表里（记录来自哪个托管人，来自流程还是生效时）
	 * @param parmMap
	 * @throws ServiceException
	 */
	public void insertAccreditTask(Map<String, String> parmMap) throws ServiceException;
	/**
	 * 检查托管是否有冲突
	 * 多次托管情况禁止
	 * @param mfOaAccredit
	 * @return
	 */
	public Map<String, Object> checkAccredit(MfOaAccredit mfOaAccredit) throws ServiceException;
	/**
	 * 仅替换流程中的用户集合
	 */
	public String[] updateUserArryAccredited(String[] userArr,String pasMinNo);
}
