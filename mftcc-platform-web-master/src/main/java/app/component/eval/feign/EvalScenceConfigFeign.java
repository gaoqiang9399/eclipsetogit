package  app.component.eval.feign;
import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.eval.entity.EvalScenceConfig;
import app.util.toolkit.Ipage;

/**
* Title: EvalScenceConfigBo.java
* Description:
* @author:@dhcc.com.cn
* @Thu Mar 17 02:46:38 GMT 2016
**/

@FeignClient("mftcc-platform-factor")
public interface EvalScenceConfigFeign {
	
	@RequestMapping(value = "/evalScenceConfig/insert")
	public EvalScenceConfig insert(@RequestBody EvalScenceConfig evalScenceConfig) throws ServiceException;
	
	@RequestMapping(value = "/evalScenceConfig/delete")
	public void delete(@RequestBody EvalScenceConfig evalScenceConfig) throws ServiceException;
	
	@RequestMapping(value = "/evalScenceConfig/update")
	public void update(@RequestBody EvalScenceConfig evalScenceConfig) throws ServiceException;
	
	@RequestMapping(value = "/evalScenceConfig/getById")
	public EvalScenceConfig getById(@RequestBody EvalScenceConfig evalScenceConfig) throws ServiceException;
	
	@RequestMapping(value = "/evalScenceConfig/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("evalScenceConfig") EvalScenceConfig evalScenceConfig) throws ServiceException;

	@RequestMapping(value = "/evalScenceConfig/getAll")
	public List<EvalScenceConfig> getAll(@RequestBody EvalScenceConfig evalScenceConfig) throws ServiceException;
	/**
	 * 
	 * 方法描述： 查询可用的评级场景
	 * @param evalScenceConfig
	 * @return
	 * @throws ServiceException
	 * List<EvalScenceConfig>
	 * @author 沈浩兵
	 * @date 2016-11-3 上午9:04:53
	 */
	@RequestMapping(value = "/evalScenceConfig/getAllUseAble")
	public List<EvalScenceConfig> getAllUseAble(@RequestBody EvalScenceConfig evalScenceConfig) throws ServiceException;
	/**
	 * 
	 * 方法描述： 获得评分卡配置信息
	 * @param evalScenceNo
	 * @return
	 * @throws ServiceException
	 * Map<String,Object>
	 * @author 沈浩兵
	 * @date 2017-6-1 上午9:59:14
	 */
	@RequestMapping(value = "/evalScenceConfig/getListData")
	public Map<String,Object> getListData(@RequestBody String evalScenceNo) throws ServiceException;
	/**
	 * 
	 * 方法描述： 获得可用的评级场景信息
	 * @param map
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author 沈浩兵
	 * @date 2017-7-11 下午5:45:47
	 */
	@RequestMapping(value = "/evalScenceConfig/getEvalScenceConfigDataMap")
	public Map<String,Object> getEvalScenceConfigDataMap(@RequestBody Map<String,String> map) throws Exception;
	/**
	 * 
	 * 方法描述：保存评分卡评级指标关系 
	 * @param map
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author 沈浩兵
	 * @date 2018年5月23日 下午9:59:36
	 */
	@RequestMapping(value = "/evalScenceConfig/saveEvalIndecRel")
	public Map<String,Object> saveEvalIndecRel(@RequestBody Map<String,Object> map) throws Exception;
	/**
	 * 
	 * 方法描述： 获得评级模型配置的评分卡及指标信息
	 * @param evalScenceNo
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author 沈浩兵
	 * @date 2018年5月29日 下午8:11:04
	 */
	@RequestMapping(value = "/evalScenceConfig/getIndexRelListData")
	public Map<String,Object> getIndexRelListData(@RequestBody String evalScenceNo) throws Exception;
}
