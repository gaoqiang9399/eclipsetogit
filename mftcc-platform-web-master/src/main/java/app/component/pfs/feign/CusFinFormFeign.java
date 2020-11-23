package app.component.pfs.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.pfs.entity.CusFinForm;
import app.component.pfs.entity.CusFinModel;
import app.util.toolkit.Ipage;

/**
 * Title: CusFinFormBoImplImpl.java Description:
 * 
 * @author:@dhcc.com.cn
 * @Wed Jun 01 07:24:53 GMT 2016
 **/

@FeignClient("mftcc-platform-factor")
public interface CusFinFormFeign {

	@RequestMapping(value = "/cusFinForm/insert")
	public void insert(@RequestBody CusFinForm cusFinForm) throws Exception;

	@RequestMapping(value = "/cusFinForm/delete")
	public void delete(@RequestBody CusFinForm cusFinForm) throws Exception;

	@RequestMapping(value = "/cusFinForm/update")
	public void update(@RequestBody CusFinForm cusFinForm) throws Exception;

	@RequestMapping(value = "/cusFinForm/getById")
	public CusFinForm getById(@RequestBody CusFinForm cusFinForm) throws Exception;

	@RequestMapping(value = "/cusFinForm/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;

	@RequestMapping(value = "/cusFinForm/getForGradefindByPage")
	public Ipage getForGradefindByPage(@RequestBody Ipage ipage) throws Exception;

	@RequestMapping(value = "/cusFinForm/getCusFinModel")
	public List<CusFinModel> getCusFinModel(@RequestBody String reportType, @RequestParam("accRule") String accRule)
			throws Exception;

	@RequestMapping(value = "/cusFinForm/getCusFinModelList")
	public List<CusFinForm> getCusFinModelList(@RequestBody CusFinForm cusFinForm) throws Exception;

}
