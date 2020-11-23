package  app.component.collateral.feign;
import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.collateral.entity.KeepInfo;
import app.component.wkf.entity.Result;
import app.util.toolkit.Ipage;

/**
* Title: KeepInfoBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Wed Mar 15 13:23:37 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface KeepInfoFeign {
	
	@RequestMapping(value = "/keepInfo/insert")
	public void insert(@RequestBody KeepInfo keepInfo) throws ServiceException;
	
	@RequestMapping(value = "/keepInfo/delete")
	public void delete(@RequestBody KeepInfo keepInfo) throws ServiceException;
	
	@RequestMapping(value = "/keepInfo/update")
	public void update(@RequestBody KeepInfo keepInfo) throws ServiceException;

	@RequestMapping(value = "/keepInfo/updateByCollateralId")
	public void updateByCollateralId(@RequestBody KeepInfo keepInfo) throws ServiceException;

	@RequestMapping(value = "/keepInfo/getById")
	public KeepInfo getById(@RequestBody KeepInfo keepInfo) throws ServiceException;
	
	@RequestMapping(value = "/keepInfo/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("keepInfo") KeepInfo keepInfo) throws ServiceException;

	@RequestMapping(value = "/keepInfo/getAll")
	public List<KeepInfo> getAll(@RequestBody KeepInfo keepInfo) throws ServiceException;
	
	@RequestMapping(value = "/keepInfo/getByCollateralId")
	public List<KeepInfo> getByCollateralId(@RequestBody KeepInfo keepInfo) throws ServiceException;
	
	@RequestMapping(value = "/keepInfo/isDataExists")
	public String isDataExists(@RequestBody KeepInfo keepInfo) throws ServiceException;
	
	@RequestMapping(value = "/keepInfo/getByColIdKPT")
	public KeepInfo getByColIdKPT(@RequestBody KeepInfo keepInfo) throws ServiceException;
	
	@RequestMapping(value = "/keepInfo/getLatestOne")
	public KeepInfo getLatestOne(@RequestBody KeepInfo keepQuery) throws ServiceException;
	
	@RequestMapping(value = "/keepInfo/insertForInOutStock")
	public KeepInfo insertForInOutStock(@RequestBody KeepInfo keepInfo,@RequestParam("keepType") String keepType) throws ServiceException;
	/**
	 * 
	 * 方法描述： 保管申请审批意见提交
	 * @param taskId
	 * @param transition
	 * @param nextUser
	 * @param dataMap
	 * @return
	 * @throws Exception
	 * Result
	 * @author 沈浩兵
	 * @date 2017-9-26 下午6:23:25
	 */
	@RequestMapping(value = "/keepInfo/doCommit")
	public Result doCommit(@RequestParam("taskId") String taskId,@RequestParam("transition") String transition,@RequestParam("nextUser") String nextUser,@RequestBody Map<String, Object> dataMap) throws Exception;
	/**
	 * 
	 * 方法描述： 插入其他押品保管信息.
	 * 更新押品状态为已入库、已出库，进行抵质押出入库记账。
	 * 如果审批否决，修改保管信息状态为否决
	 * @throws Exception
	 * void
	 * @author 沈浩兵
	 * @date 2017-9-26 下午6:55:46
	 */
	@RequestMapping(value = "/keepInfo/updatePledgeSts")
	public void updatePledgeSts(@RequestBody KeepInfo keepInfo,@RequestParam("appId") String appId,@RequestParam("flag") String flag) throws Exception;
}
