package  app.component.coreservice.feign;

import app.base.ServiceException;
import app.component.coreservice.entity.MfHxBusKind;
import app.util.toolkit.Ipage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
* Title: MfHxBusKindBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Thu Aug 31 15:17:24 CST 2017
**/

@FeignClient("mftcc-platform-dhcc")
public interface MfHxBusKindFeign {
	
	@RequestMapping(value = "/mfHxBusKind/insert")
	public void insert(@RequestBody MfHxBusKind mfHxBusKind) throws ServiceException;
	
	@RequestMapping(value = "/mfHxBusKind/delete")
	public void delete(@RequestBody MfHxBusKind mfHxBusKind) throws ServiceException;
	
	@RequestMapping(value = "/mfHxBusKind/update")
	public void update(@RequestBody MfHxBusKind mfHxBusKind) throws ServiceException;
	
	@RequestMapping(value = "/mfHxBusKind/getById")
	public MfHxBusKind getById(@RequestBody MfHxBusKind mfHxBusKind) throws ServiceException;
	
	@RequestMapping(value = "/mfHxBusKind/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("mfHxBusKind") MfHxBusKind mfHxBusKind) throws ServiceException;
	
	/**
	 * 方法描述： 查询列表
	 * @param mfHxBusKind
	 * @return
	 * @throws ServiceException
	 * List<MfHxBusKind>
	 * @author Javelin
	 * @date 2017-8-31 下午7:35:15
	 */
	@RequestMapping(value = "/mfHxBusKind/getMfHxBusKindList")
	public List<MfHxBusKind> getMfHxBusKindList(@RequestBody MfHxBusKind mfHxBusKind) throws ServiceException;
	
}
