package  app.component.oa.trans.bo;

import java.util.List;
import java.util.Map;

import app.base.ServiceException;
import app.component.oa.trans.entity.MfOaTrans;
import app.component.wkf.entity.Result;
import app.util.toolkit.Ipage;

/**
* Title: MfOaTransBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Tue Apr 25 15:20:00 CST 2017
**/

public interface MfOaTransBo {
	
	public MfOaTrans insert(MfOaTrans mfOaTrans,String cusNos,String appNos,String pactNos) throws ServiceException;
	
	public void delete(MfOaTrans mfOaTrans) throws ServiceException;
	
	public void update(MfOaTrans mfOaTrans) throws ServiceException;
	
	public MfOaTrans getById(MfOaTrans mfOaTrans) throws ServiceException;
	
	public Ipage findByPage(Ipage ipage,MfOaTrans mfOaTrans) throws ServiceException;
	
	public List<MfOaTrans> getByTransId(MfOaTrans mfOaTrans) throws Exception;

	public Result doCommit(String taskId, String id, String opinionType,String approvalOpinion, String transition,String opNo,String nextUser, MfOaTrans mfOaTrans,Map<String, Object> dataMap) throws Exception;
	
}
