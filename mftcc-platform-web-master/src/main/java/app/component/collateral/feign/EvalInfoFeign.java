package  app.component.collateral.feign;
import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.collateral.entity.EvalInfo;
import app.util.toolkit.Ipage;

/**
* Title: EvalInfoBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Wed Mar 08 11:20:33 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface EvalInfoFeign {
	
	@RequestMapping(value = "/evalInfo/insert")
	public void insert(@RequestBody EvalInfo evalInfo) throws ServiceException;
	
	@RequestMapping(value = "/evalInfo/delete")
	public void delete(@RequestBody EvalInfo evalInfo) throws ServiceException;
	
	@RequestMapping(value = "/evalInfo/update")
	public void update(@RequestBody EvalInfo evalInfo) throws ServiceException;
	
	@RequestMapping(value = "/evalInfo/getById")
	public EvalInfo getById(@RequestBody EvalInfo evalInfo) throws ServiceException;
	
	@RequestMapping(value = "/evalInfo/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws ServiceException;

	@RequestMapping(value = "/evalInfo/getAll")
	public List<EvalInfo> getAll(@RequestBody EvalInfo evalInfo) throws ServiceException;
	/**
	 * 根据申请号查询 押品评估
	 * @param appId
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/evalInfo/getEvalInfosByAppId")
	public List<EvalInfo> getEvalInfosByAppId(@RequestBody String appId) throws ServiceException;
	
	@RequestMapping(value = "/evalInfo/getLatest")
	public EvalInfo getLatest(@RequestBody EvalInfo evalInfo) throws ServiceException;

	/**
	 * <P>
	 * 仅用于网信:
	 * </P>
	 * 系统规则授信环节，系统计算出的授信额度未显示给 审核经理； <br>
	 * 
	 * @param appId
	 * @return
	 * @throws ServiceException
	 * @author WangChao
	 * @date 2017-11-8 上午10:04:08
	 */
	@RequestMapping(value = "/evalInfo/getCreditQuota")
	public Double getCreditQuota(@RequestBody String appId) throws ServiceException;
	/**
	 * 
	 * 方法描述： 业务流程中保存押品评估信息
	 * @param evalInfo
	 * @param appId
	 * @throws Exception
	 * void
	 * @author 沈浩兵
	 * @date 2017-12-15 下午12:00:18
	 */
	@RequestMapping(value = "/evalInfo/insertBuss")
	public void insertBuss(@RequestBody EvalInfo evalInfo,@RequestParam("appId") String appId) throws Exception;

	@RequestMapping(value = "/evalInfo/insertArtificialApply")
	public void insertArtificialApply(@RequestBody EvalInfo evalInfo)throws Exception;
}
