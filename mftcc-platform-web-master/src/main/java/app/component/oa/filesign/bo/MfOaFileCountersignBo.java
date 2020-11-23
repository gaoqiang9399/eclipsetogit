package  app.component.oa.filesign.bo;

import java.util.Map;

import app.base.ServiceException;
import app.component.oa.filesign.entity.MfOaFileCountersign;
import app.component.wkf.entity.Result;
import app.util.toolkit.Ipage;

/**
* Title: MfOaFileCountersignBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Mon Jun 12 16:40:24 CST 2017
**/

public interface MfOaFileCountersignBo {
	
	public String insert(MfOaFileCountersign mfOaFileCountersign) throws ServiceException;
	
	public void delete(MfOaFileCountersign mfOaFileCountersign) throws ServiceException;
	
	public void update(MfOaFileCountersign mfOaFileCountersign) throws ServiceException;
	
	public MfOaFileCountersign getById(MfOaFileCountersign mfOaFileCountersign) throws ServiceException;
	
	public Ipage findByPage(Ipage ipage,MfOaFileCountersign mfOaFileCountersign) throws ServiceException;
	
	/**
	 * 方法描述： 审批提交
	 * @param taskId
	 * @param appNo
	 * @param transition
	 * @param nextUser
	 * @param dataMap
	 * @return
	 * @throws Exception
	 * Result
	 * @author Javelin
	 * @date 2017-6-13 上午9:01:29
	 */
	public Result commitProcess(String taskId, String appNo, String transition, String nextUser, Map<String, Object> dataMap) throws Exception;

	/**
	 * 方法描述： 获取数据数量
	 * @param mfOaFileCountersign
	 * @return
	 * @throws ServiceException
	 * int
	 * @author Javelin
	 * @date 2017-6-16 下午5:46:53
	 */
	public int getDataCount(MfOaFileCountersign mfOaFileCountersign) throws ServiceException;
}
