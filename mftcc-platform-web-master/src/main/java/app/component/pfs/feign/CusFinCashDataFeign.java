package app.component.pfs.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.pfs.entity.CusFinCashData;
import app.util.toolkit.Ipage;

/**
 * Title: CusFinCashDataBoImplImpl.java Description:现金流量表业务操作
 * 
 * @author:@LJW
 * @Mon May 09 05:30:24 GMT 2016
 **/

@FeignClient("mftcc-platform-factor")
public interface CusFinCashDataFeign {

	@RequestMapping(value = "/cusFinCashData/insert")
	public void insert(@RequestBody CusFinCashData cusFinCashData) throws Exception ;

	@RequestMapping(value = "/cusFinCashData/delete")
	public void delete(@RequestBody CusFinCashData cusFinCashData) throws Exception ;

	@RequestMapping(value = "/cusFinCashData/update")
	public void update(@RequestBody CusFinCashData cusFinCashData) throws Exception ;

	@RequestMapping(value = "/cusFinCashData/getById")
	public CusFinCashData getById(@RequestBody CusFinCashData cusFinCashData) throws Exception ;

	@RequestMapping(value = "/cusFinCashData/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("cusFinCashData") CusFinCashData cusFinCashData) throws Exception ;

	@RequestMapping(value = "/cusFinCashData/insertOrUpdate")
	public void insertOrUpdate(@RequestBody Map<String, String> map,@RequestParam("cusFinCashData") CusFinCashData cusFinCashData) throws Exception ;

	@RequestMapping(value = "/cusFinCashData/insertOrUpdate1")
	public void insertOrUpdate1(@RequestBody Map<String, Object> parmMap)
			throws Exception ;

	@RequestMapping(value = "/cusFinCashData/getByCusNoDateTypeForList")
	public List<CusFinCashData> getByCusNoDateTypeForList(@RequestBody CusFinCashData cusFinCashData) throws Exception ;

	@RequestMapping(value = "/cusFinCashData/updateBegin")
	public int updateBegin(@RequestBody CusFinCashData cusFinCashData) throws Exception ;

}
