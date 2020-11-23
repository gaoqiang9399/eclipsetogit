package app.component.pledge.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.pledge.entity.MfPledgeDynamicForm;
import app.util.toolkit.Ipage;

/**
 * Title: MfPledgeDynamicFormBo.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Thu Nov 17 15:37:26 CST 2016
 **/
@FeignClient("mftcc-platform-factor")
public interface MfPledgeDynamicFormFeign {

	@RequestMapping(value = "/mfPledgeDynamicForm/insert")
	public void insert(@RequestBody MfPledgeDynamicForm mfPledgeDynamicForm) throws ServiceException;

	@RequestMapping(value = "/mfPledgeDynamicForm/delete")
	public void delete(@RequestBody MfPledgeDynamicForm mfPledgeDynamicForm) throws ServiceException;

	@RequestMapping(value = "/mfPledgeDynamicForm/update")
	public void update(@RequestBody MfPledgeDynamicForm mfPledgeDynamicForm) throws ServiceException;

	/**
	 * 
	 * 方法描述： 修改禁用启用标识
	 * 
	 * @param mfPledgeDynamicForm
	 * @throws ServiceException
	 *             void
	 * @author 沈浩兵
	 * @date 2016-11-17 下午5:23:57
	 */
	@RequestMapping(value = "/mfPledgeDynamicForm/updateSts")
	public void updateSts(@RequestBody MfPledgeDynamicForm mfPledgeDynamicForm) throws ServiceException;

	@RequestMapping(value = "/mfPledgeDynamicForm/getById")
	public MfPledgeDynamicForm getById(@RequestBody MfPledgeDynamicForm mfPledgeDynamicForm) throws ServiceException;

	/**
	 * 
	 * 方法描述： 根据申请编号获得该业务对应的押品动态表单信息
	 * 
	 * @param mfPledgeDynamicForm
	 * @return
	 * @throws ServiceException
	 *             MfPledgeDynamicForm
	 * @author 沈浩兵
	 * @date 2016-11-18 上午11:57:32
	 */
	@RequestMapping(value = "/mfPledgeDynamicForm/getByBusAppId")
	public MfPledgeDynamicForm getByBusAppId(@RequestBody String appId, @RequestParam("formType") String formType)
			throws ServiceException;

	@RequestMapping(value = "/mfPledgeDynamicForm/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage, @RequestParam("mfPledgeDynamicForm") MfPledgeDynamicForm mfPledgeDynamicForm)
			throws ServiceException;

	/**
	 * 
	 * 方法描述： 获得可用的押品表单信息
	 * 
	 * @param mfPledgeDynamicForm
	 * @return
	 * @throws ServiceException
	 *             List<MfPledgeDynamicForm>
	 * @author 沈浩兵
	 * @date 2016-11-17 下午9:04:45
	 */
	@RequestMapping(value = "/mfPledgeDynamicForm/getUseableList")
	public List<MfPledgeDynamicForm> getUseableList(@RequestBody MfPledgeDynamicForm mfPledgeDynamicForm)
			throws ServiceException;

}
