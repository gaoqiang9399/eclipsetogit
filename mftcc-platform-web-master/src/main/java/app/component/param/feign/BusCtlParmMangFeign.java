package app.component.param.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.param.entity.BusCtlParmMang;
import app.util.toolkit.Ipage;

/**
 * Title: BusCtlParmMangBoImplImpl.java Description:
 * 
 * @author:@dhcc.com.cn
 * @Fri Mar 11 01:44:29 GMT 2016
 **/
@FeignClient("mftcc-platform-factor")
public interface BusCtlParmMangFeign {

	@RequestMapping(value = "/busCtlParmMang/insert")
	public void insert(@RequestBody BusCtlParmMang busCtlParmMang) throws Exception;

	@RequestMapping(value = "/busCtlParmMang/delete")
	public void delete(@RequestBody BusCtlParmMang busCtlParmMang) throws Exception;

	@RequestMapping(value = "/busCtlParmMang/update")
	public void update(@RequestBody BusCtlParmMang busCtlParmMang) throws Exception;

	@RequestMapping(value = "/busCtlParmMang/getById")
	public BusCtlParmMang getById(@RequestBody BusCtlParmMang busCtlParmMang) throws Exception;

	@RequestMapping(value = "/busCtlParmMang/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("busCtlParmMang") BusCtlParmMang busCtlParmMang) throws Exception;

	@RequestMapping(value = "/busCtlParmMang/getAll")
	public List<BusCtlParmMang> getAll(@RequestBody BusCtlParmMang busCtlParmMang) throws Exception;

}
