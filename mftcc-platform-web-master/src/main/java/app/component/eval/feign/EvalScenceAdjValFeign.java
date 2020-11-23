package  app.component.eval.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.eval.entity.EvalScenceAdjVal;
import app.util.toolkit.Ipage;
import net.sf.json.JSONObject;

/**
* Title: EvalScenceAdjValBo.java
* Description:
* @author:@dhcc.com.cn
* @Tue Apr 05 06:46:34 GMT 2016
**/

@FeignClient("mftcc-platform-factor")
public interface EvalScenceAdjValFeign {
	
	@RequestMapping(value = "/evalScenceAdjVal/insert")
	public void insert(@RequestBody EvalScenceAdjVal evalScenceAdjVal) throws ServiceException;
	
	@RequestMapping(value = "/evalScenceAdjVal/delete")
	public void delete(@RequestBody EvalScenceAdjVal evalScenceAdjVal) throws ServiceException;
	
	@RequestMapping(value = "/evalScenceAdjVal/update")
	public void update(@RequestBody EvalScenceAdjVal evalScenceAdjVal) throws ServiceException;
	/**
	 * 
	 * 方法描述： 更新调整评分信息
	 * @param evalScenceAdjVal
	 * @throws ServiceException
	 * void
	 * @author 沈浩兵
	 * @date 2016-12-23 上午11:11:44
	 */
	@RequestMapping(value = "/evalScenceAdjVal/updateEvalScenceAdjVal")
	public void updateEvalScenceAdjVal(@RequestBody EvalScenceAdjVal evalScenceAdjVal,@RequestParam("jb") JSONObject jb) throws ServiceException;
	
	@RequestMapping(value = "/evalScenceAdjVal/getById")
	public EvalScenceAdjVal getById(@RequestBody EvalScenceAdjVal evalScenceAdjVal) throws ServiceException;
	
	@RequestMapping(value = "/evalScenceAdjVal/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("evalScenceAdjVal") EvalScenceAdjVal evalScenceAdjVal) throws ServiceException;
	
}
