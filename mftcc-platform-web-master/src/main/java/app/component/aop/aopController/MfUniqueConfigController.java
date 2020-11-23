package  app.component.aop.aopController;

import java.util.Map;

import app.component.aop.entity.MfUniqueConfig;
import app.util.toolkit.Ipage;

/**
* Title: MfUniqueConfigBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Tue Jun 13 17:54:49 CST 2017
**/

public interface MfUniqueConfigController {
	
	
	
	public void insert(MfUniqueConfig mfUniqueConfig) throws Exception;
	
	public void delete(MfUniqueConfig mfUniqueConfig) throws Exception;
	
	public void update(MfUniqueConfig mfUniqueConfig) throws Exception;
	
	public MfUniqueConfig getById(MfUniqueConfig mfUniqueConfig) throws Exception;
	/**
	 * 初始化唯一表配置map
	 * @throws Exception
	 */
	public   Map<String,MfUniqueConfig > initUniqueConfigMap()throws Exception;
	
	public Ipage findByPage(Ipage ipage,MfUniqueConfig mfUniqueConfig) throws Exception;
	/**
	 * 操作唯一表信息
	 * @param exeId 执行类
	 * @param parm 操作实体
	 * @param opType 操作类型 insert-新增 update-修改 deltete-删除
	 * @throws Exception
	 */
	public  void  insertUniqualInfo(String exeId,Object parm)throws Exception;
	
	
	
}
