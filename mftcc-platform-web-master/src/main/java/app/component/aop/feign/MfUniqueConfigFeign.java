package  app.component.aop.feign;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.aop.entity.MfUniqueConfig;
import app.util.toolkit.Ipage;

/**
* Title: MfUniqueConfigBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Tue Jun 13 17:54:49 CST 2017
**/
@FeignClient("mftcc-platform-factor")
public interface MfUniqueConfigFeign{
	
	
	@RequestMapping(value = "/mfUniqueConfig/insert")
	public void insert(@RequestBody MfUniqueConfig mfUniqueConfig) throws Exception;
	@RequestMapping(value = "/mfUniqueConfig/delete")
	public void delete(@RequestBody MfUniqueConfig mfUniqueConfig) throws Exception;
	@RequestMapping(value = "/mfUniqueConfig/update")
	public void update(@RequestBody MfUniqueConfig mfUniqueConfig) throws Exception;
	@RequestMapping(value = "/mfUniqueConfig/getById")
	public MfUniqueConfig getById(@RequestBody MfUniqueConfig mfUniqueConfig) throws Exception;
	/**
	 * 初始化唯一表配置map
	 * @throws Exception
	 */
	@RequestMapping(value = "/mfUniqueConfig/initUniqueConfigMap")
	public   Map<String,MfUniqueConfig > initUniqueConfigMap()throws Exception;
	@RequestMapping(value = "/mfUniqueConfig/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("mfUniqueConfig") MfUniqueConfig mfUniqueConfig) throws Exception;
	/**
	 * 操作唯一表信息
	 * @param exeId 执行类
	 * @param parm 操作实体
	 * @param opType 操作类型 insert-新增 update-修改 deltete-删除
	 * @throws Exception
	 */
	@RequestMapping(value = "/mfUniqueConfig/insertUniqualInfo")
	public  void  insertUniqualInfo(@RequestParam("exeId") String exeId,@RequestBody Object parm)throws Exception;
	
	
	
}
