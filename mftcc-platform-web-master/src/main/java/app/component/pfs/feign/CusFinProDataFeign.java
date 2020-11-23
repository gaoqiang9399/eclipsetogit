package app.component.pfs.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.pfs.entity.CusFinProData;
import app.util.toolkit.Ipage;

/**
 * Title: CusFinProDataBoImplImpl.java Description:利润分配表业务控制
 * 
 * @author:LJW
 * @Mon May 09 05:31:09 GMT 2016
 **/

@FeignClient("mftcc-platform-factor")
public interface CusFinProDataFeign {

	@RequestMapping(value = "/cusFinProData/insert")
	public void insert(@RequestBody CusFinProData cusFinProData) throws Exception ;

	@RequestMapping(value = "/cusFinProData/delete")
	public void delete(@RequestBody CusFinProData cusFinProData) throws Exception ;

	@RequestMapping(value = "/cusFinProData/update")
	public void update(@RequestBody CusFinProData cusFinProData) throws Exception ;

	@RequestMapping(value = "/cusFinProData/getById")
	public CusFinProData getById(@RequestBody CusFinProData cusFinProData) throws Exception ;

	@RequestMapping(value = "/cusFinProData/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("cusFinProData") CusFinProData cusFinProData) throws Exception ;

	@RequestMapping(value = "/cusFinProData/insertOrUpdate")
	public void insertOrUpdate(@RequestBody Map<String, String> map,@RequestParam("cusFinProData") CusFinProData cusFinProData) throws Exception ;

	@RequestMapping(value = "/cusFinProData/insertOrUpdate1")
	public void insertOrUpdate1(@RequestBody Map<String, Object> parmMap)
			throws Exception ;

	@RequestMapping(value = "/cusFinProData/getByCusNoDateTypeForList")
	public List<CusFinProData> getByCusNoDateTypeForList(@RequestBody CusFinProData cusFinProData) throws Exception ;

	@RequestMapping(value = "/cusFinProData/updateBegin")
	public int updateBegin(@RequestBody CusFinProData cusFinProData) throws Exception ;

	@RequestMapping(value = "/cusFinProData/getCwProftListMap")
	public List<Map<String, Object>> getCwProftListMap(@RequestBody Map<String, String> paraMap) throws Exception ;

}
