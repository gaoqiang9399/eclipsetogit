package  app.component.oa.changemoney.bo;

import java.util.Map;

import app.component.oa.changemoney.entity.MfOaCounttrans;
import app.component.wkf.entity.Result;
import app.util.toolkit.Ipage;

/**
* Title: MfOaCounttransBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Tue Jun 13 15:21:27 CST 2017
**/

public interface MfOaCounttransBo {
	
	public MfOaCounttrans insert(MfOaCounttrans mfOaCounttrans) throws Exception;
	
	public void delete(MfOaCounttrans mfOaCounttrans) throws Exception;
	
	public void update(MfOaCounttrans mfOaCounttrans) throws Exception;
	
	public MfOaCounttrans getById(MfOaCounttrans mfOaCounttrans) throws Exception;
	
	public Ipage findByPage(Ipage ipage,MfOaCounttrans mfOaCounttrans) throws Exception;

	public Result updateSubmit(MfOaCounttrans mfOaCounttrans, Map thisObjMap);

	public void updateOutChangeMoney(MfOaCounttrans mfOaCounttrans) throws Exception;
	
	public int getCount() throws Exception;
	
}
