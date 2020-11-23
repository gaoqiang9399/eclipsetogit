package  app.component.oa.opencount.bo;

import app.component.oa.opencount.entity.MfOaOpening;
import app.component.wkf.entity.Result;
import app.util.toolkit.Ipage;

/**
* Title: MfOaOpeningBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Sat Jun 17 15:31:41 CST 2017
**/

public interface MfOaOpeningBo {
	
	public MfOaOpening insert(MfOaOpening mfOaOpening) throws Exception;
	
	public void delete(MfOaOpening mfOaOpening) throws Exception;
	
	public void update(MfOaOpening mfOaOpening) throws Exception;
	
	public MfOaOpening getById(MfOaOpening mfOaOpening) throws Exception;
	
	public Ipage findByPage(Ipage ipage,MfOaOpening mfOaOpening) throws Exception;
	
	public Result updateForSubmit(String taskId, String badgeno,
			String opinionType, String approvalOpinion, String transition,
			String regNo, String nextUser, MfOaOpening mfOaBadge,String badgeNodeType);

	public void updateOpen(MfOaOpening mfOaOpening) throws Exception;
	
	public int getCount() throws Exception;
}
