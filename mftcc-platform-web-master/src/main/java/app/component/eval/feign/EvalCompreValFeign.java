package  app.component.eval.feign;
import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.eval.entity.EvalCompreVal;
import app.util.toolkit.Ipage;
import net.sf.json.JSONObject;

/**
* Title: EvalCompreValBo.java
* Description:
* @author:@dhcc.com.cn
* @Thu Mar 31 06:43:39 GMT 2016
**/

@FeignClient("mftcc-platform-factor")
public interface EvalCompreValFeign {
	
	@RequestMapping(value = "/evalCompreVal/insert")
	public void insert(@RequestBody EvalCompreVal evalCompreVal) throws ServiceException;
	
	@RequestMapping(value = "/evalCompreVal/delete")
	public void delete(@RequestBody EvalCompreVal evalCompreVal) throws ServiceException;
	
	@RequestMapping(value = "/evalCompreVal/update")
	public void update(@RequestBody EvalCompreVal evalCompreVal,@RequestParam("jb") JSONObject jb) throws ServiceException;
	
	@RequestMapping(value = "/evalCompreVal/getById")
	public EvalCompreVal getById(@RequestBody EvalCompreVal evalCompreVal) throws ServiceException;
	
	@RequestMapping(value = "/evalCompreVal/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("evalCompreVal") EvalCompreVal evalCompreVal) throws ServiceException;

	@RequestMapping(value = "/evalCompreVal/getAll")
	public List<EvalCompreVal> getAll(@RequestBody EvalCompreVal evalCompreVal) throws ServiceException;
}
