package  app.component.app.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.app.entity.MfBusAppKind;
import app.component.app.entity.MfBusApply;
import app.util.toolkit.Ipage;

/**
* Title: MfBusAppKindBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Fri Dec 02 18:04:47 CST 2016
**/

@FeignClient("mftcc-platform-factor")
public interface MfBusAppKindFeign {
	
	@RequestMapping(value = "/mfBusAppKind/insert")
	public void insert(@RequestBody MfBusAppKind mfBusAppKind) throws ServiceException;
	
	@RequestMapping(value = "/mfBusAppKind/delete")
	public void delete(@RequestBody MfBusAppKind mfBusAppKind) throws ServiceException;
	
	@RequestMapping(value = "/mfBusAppKind/update")
	public void update(@RequestBody MfBusAppKind mfBusAppKind) throws ServiceException;
	
	@RequestMapping(value = "/mfBusAppKind/getById")
	public MfBusAppKind getById(@RequestBody MfBusAppKind mfBusAppKind) throws ServiceException;
	
	@RequestMapping(value = "/mfBusAppKind/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws ServiceException;

	/**
	 * 
	 * 方法描述：业务保存时，将产品信息与业务关联 ，插入表中
	 * @param appId
	 * @param mfSysKind
	 * @throws ServiceException
	 * MfBusAppKind
	 * @author zhs
	 * @return 
	 * @date 2016-12-2 下午7:32:03
	 */
	@RequestMapping(value = "/mfBusAppKind/insertBusAppKind")
	public MfBusAppKind insertBusAppKind(@RequestBody MfBusApply mfBusApply)throws Exception;
    /**
     * 
     * 方法描述：通过appId 更新数据
     * @param upBusAppKind 
     * void
     * @author WD
     * @date 2017-7-25 下午8:18:21
     */
	@RequestMapping(value = "/mfBusAppKind/updateMfBusAppKind")
	public void updateMfBusAppKind(@RequestBody MfBusAppKind upBusAppKind)throws Exception;;
	
}
