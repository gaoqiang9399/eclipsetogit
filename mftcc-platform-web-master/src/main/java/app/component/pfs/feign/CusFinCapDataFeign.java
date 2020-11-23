package app.component.pfs.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.pfs.entity.CusFinCapData;
import app.util.toolkit.Ipage;

/**
 * Title: CusFinCapDataBoImplImpl.java Description:资产负债表业务控制操作
 * 
 * @author:LJW
 * @Mon May 09 05:27:05 GMT 2016
 **/

@FeignClient("mftcc-platform-factor")
public interface CusFinCapDataFeign {

	@RequestMapping(value = "/cusFinCapData/insert")
	public void insert(@RequestBody CusFinCapData cusFinCapData) throws Exception;

	@RequestMapping(value = "/cusFinCapData/delete")
	public void delete(@RequestBody CusFinCapData cusFinCapData) throws Exception;

	@RequestMapping(value = "/cusFinCapData/update")
	public void update(@RequestBody CusFinCapData cusFinCapData) throws Exception;

	@RequestMapping(value = "/cusFinCapData/getById")
	public CusFinCapData getById(@RequestBody CusFinCapData cusFinCapData) throws Exception;

	@RequestMapping(value = "/cusFinCapData/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage, @RequestParam("cusFinCapData") CusFinCapData cusFinCapData) throws Exception;

	@RequestMapping(value = "/cusFinCapData/insertOrUpdate")
	public void insertOrUpdate(@RequestBody Map<String, String> map, @RequestParam("cusFinCapData") CusFinCapData cusFinCapData)
			throws Exception;

	@RequestMapping(value = "/cusFinCapData/insertOrUpdate1")
	public void insertOrUpdate1(@RequestBody Map<String, Object> parmMap) throws Exception;

	@RequestMapping(value = "/cusFinCapData/getByCusNoDateTypeForList")
	public List<CusFinCapData> getByCusNoDateTypeForList(@RequestBody CusFinCapData cusFinCapData) throws Exception;

	@RequestMapping(value = "/cusFinCapData/updateBegin")
	public int updateBegin(@RequestBody CusFinCapData cusFinCapData) throws Exception;

	@RequestMapping(value = "/cusFinCapData/getFinCapDataMaps")
	public List<Map<String, Object>> getFinCapDataMaps(@RequestBody Map<String, String> parmMap) throws Exception;

}
