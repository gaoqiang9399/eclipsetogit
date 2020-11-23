package  app.component.eval.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.eval.entity.MfEvalIndexSub;
import app.util.toolkit.Ipage;

/**
* Title: MfEvalIndexSubBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Tue Mar 07 16:03:02 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface MfEvalIndexSubFeign {
	
	@RequestMapping(value = "/mfEvalIndexSub/insert")
	public MfEvalIndexSub insert(@RequestBody MfEvalIndexSub mfEvalIndexSub) throws ServiceException;
	
	@RequestMapping(value = "/mfEvalIndexSub/delete")
	public void delete(@RequestBody MfEvalIndexSub mfEvalIndexSub) throws ServiceException;
	
	@RequestMapping(value = "/mfEvalIndexSub/update")
	public void update(@RequestBody MfEvalIndexSub mfEvalIndexSub) throws ServiceException;
	
	@RequestMapping(value = "/mfEvalIndexSub/getById")
	public MfEvalIndexSub getById(@RequestBody MfEvalIndexSub mfEvalIndexSub) throws ServiceException;
	
	@RequestMapping(value = "/mfEvalIndexSub/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("mfEvalIndexSub") MfEvalIndexSub mfEvalIndexSub) throws ServiceException;
	/**
	 * 
	 * 方法描述： 获得评级指标子项
	 * @param mfEvalIndexSub
	 * @return
	 * @throws ServiceException
	 * List<MfEvalIndexSub>
	 * @author 沈浩兵
	 * @date 2017-3-7 下午4:19:34
	 */
	@RequestMapping(value = "/mfEvalIndexSub/getMfEvalIndexSubList")
	public List<MfEvalIndexSub> getMfEvalIndexSubList(@RequestBody MfEvalIndexSub mfEvalIndexSub) throws ServiceException;
	/**
	 * 
	 * 方法描述： 插入多个子项
	 * @param mfEvalIndexSub
	 * @return
	 * @throws ServiceException
	 * MfEvalIndexSub
	 * @author 沈浩兵
	 * @date 2018年5月31日 下午11:14:51
	 */
	@RequestMapping(value = "/mfEvalIndexSub/insertIndexSubs")
	public List<MfEvalIndexSub> insertIndexSubs(@RequestBody MfEvalIndexSub mfEvalIndexSub) throws Exception;
	
	
}
