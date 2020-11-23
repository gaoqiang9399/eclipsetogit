package  app.component.collateral.feign;
import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.collateral.entity.CertiInfo;
import app.component.wkf.entity.Result;
import app.util.toolkit.Ipage;

/**
* Title: CertiInfoBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Wed Mar 08 11:15:48 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface CertiInfoFeign {
	
	@RequestMapping(value = "/certiInfo/insert")
	public void insert(@RequestBody CertiInfo certiInfo) throws ServiceException;
	/**
	 * 
	 * 方法描述： 业务中担保证明登记保存
	 * @param certiInfo
	 * @throws ServiceException
	 * void
	 * @author 沈浩兵
	 * @date 2017-7-31 上午11:49:05
	 */
	@RequestMapping(value = "/certiInfo/insertBus")
	public void insertBus(@RequestBody CertiInfo certiInfo,@RequestParam("appId") String appId) throws ServiceException;
	
	@RequestMapping(value = "/certiInfo/insertAlone")
	public Map<String,Object> insertAlone(@RequestBody CertiInfo certiInfo,@RequestParam("appId") String appId) throws Exception;

	@RequestMapping(value = "/certiInfo/updateAlone")
	public Map<String,Object> updateAlone(@RequestBody CertiInfo certiInfo,@RequestParam("appId") String appId) throws Exception;

	@RequestMapping(value = "/certiInfo/delete")
	public void delete(@RequestBody CertiInfo certiInfo) throws ServiceException;
	
	@RequestMapping(value = "/certiInfo/update")
	public void update(@RequestBody CertiInfo certiInfo) throws ServiceException;
	
	@RequestMapping(value = "/certiInfo/getById")
	public CertiInfo getById(@RequestBody CertiInfo certiInfo) throws ServiceException;
	
	@RequestMapping(value = "/certiInfo/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("certiInfo") CertiInfo certiInfo) throws ServiceException;

	@RequestMapping(value = "/certiInfo/getListByPage")
	public Ipage getListByPage(@RequestBody Ipage ipage) throws Exception;

	@RequestMapping(value = "/certiInfo/getAll")
	public List<CertiInfo> getAll(@RequestBody CertiInfo certiInfo) throws ServiceException;
	
	@RequestMapping(value = "/certiInfo/getByCollateralId")
	public CertiInfo getByCollateralId(@RequestBody CertiInfo certiInfo) throws ServiceException;

	/**
	 * 方法描述： 插入权证数据并提交流程
	 * @param certiInfo
	 * @return
	 * @throws ServiceException
	 * Result
	 * @author YuShuai
	 * @date 2017-6-3 下午2:28:22
	 */
	@RequestMapping(value = "/certiInfo/insertAndDocommit")
	public Result insertAndDocommit(@RequestBody CertiInfo certiInfo)throws ServiceException;
	
	@RequestMapping(value = "/certiInfo/validateDupCertiNoAjax")
	public String validateDupCertiNoAjax(@RequestBody String certiNo) throws ServiceException;
	/**
	 * 
	 * 方法描述： 如果业务流程中跳过担保证明登记，则直接提交到下一个业务节点
	 * @param appId
	 * @throws ServiceException
	 * void
	 * @author 沈浩兵
	 * @date 2017-7-31 下午12:57:21
	 */
	@RequestMapping(value = "/certiInfo/submitBussProcess")
	public void submitBussProcess(@RequestParam("appId") String appId,@RequestParam("opNo") String opNo) throws ServiceException;
	/**
	 * 
	 * 方法描述： 根据业务编号的关联押品的权证信息，暂时默认取第一个押品的权证信息
	 * @param appId
	 * @return
	 * @throws ServiceException
	 * CertiInfo
	 * @author 沈浩兵
	 * @date 2017-11-28 下午8:31:01
	 */
	@RequestMapping(value = "/certiInfo/getByAppId")
	public CertiInfo getByAppId(@RequestBody String appId) throws Exception;
	/**
	 *
	 * 方法描述： 业务流程中更新
	 * @param certiInfo
	 * @throws ServiceException
	 * void
	 * @author 沈浩兵
	 * @date 2017-11-28 下午8:47:07
	 */
	@RequestMapping(value = "/certiInfo/updateBuss")
	public void updateBuss(@RequestBody CertiInfo certiInfo,@RequestParam("appId") String appId,@RequestParam("opNo") String opNo) throws ServiceException;

	@RequestMapping(value = "/certiInfo/submitProcess")
	public Map<String,Object> submitProcess(@RequestBody CertiInfo certiInfo, @RequestParam("appId") String appId) throws Exception;

	@RequestMapping(value = "/certiInfo/doCommit")
	public Result doCommit(@RequestParam("taskId") String taskId,@RequestParam("appId") String appId,@RequestParam("certiId")  String certiId,@RequestParam("opinionType")  String opinionType,@RequestParam("approvalOpinion") String approvalOpinion,@RequestParam("transition")  String transition,@RequestParam("opNo")  String opNo,@RequestParam("nextUser") String nextUser,@RequestBody  Map<String, Object> dataMap) throws Exception;

	@RequestMapping(value = "/certiInfo/getListByAppId")
	public List<CertiInfo> getListByAppId(@RequestBody CertiInfo certiInfo) throws Exception;

	@RequestMapping(value = "/certiInfo/getCertiInfoListAll")
	public List<CertiInfo> getCertiInfoListAll(@RequestBody CertiInfo certiInfo);
	/**
	 * 方法描述： 审批提交
	 *
	 * @param taskId
	 * @param appNo
	 * @param transition
	 * @param nextUser
	 * @param dataMap
	 * @return
	 * @throws Exception
	 *             Result
	 * @author Javelin
	 * @date 2017-6-13 上午9:01:29
	 */
	@RequestMapping(value = "/certiInfo/commitProcess")
	public Result commitProcess(@RequestParam("taskId") String taskId, @RequestParam("appNo") String appNo, @RequestParam("appId") String appId,
								@RequestParam("transition") String transition, @RequestParam("nextUser") String nextUser,
								@RequestBody Map<String, Object> dataMap) throws Exception;
	/**
	 * 方法描述： 审批启动
	 * @return
	 * @throws Exception
	 *             Result
	 * @author Javelin
	 * @date 2017-6-13 上午9:01:29
	 */
	@RequestMapping(value = "/certiInfo/startProcess",produces = {"text/html;charset=utf-8"})
	public String startProcess(@RequestParam("appNo")String appNo,@RequestParam("opNo")String opNo,
							   @RequestParam("opName")String opName,@RequestParam("brNo")String brNo,@RequestParam("appId")String appId) throws Exception;
}
