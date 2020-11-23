package app.component.pfs.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.pfs.entity.CusFinModel;

/**
 * @author lenovo
 *
 */

@FeignClient("mftcc-platform-factor")
public interface CusFinModelFeign {

	@RequestMapping(value = "/cusFinModel/list")
	public List<CusFinModel> list(@RequestBody CusFinModel cusFinModel) throws Exception ;

	@RequestMapping(value = "/cusFinModel/saveOrUpdate")
	public void saveOrUpdate(@RequestBody CusFinModel ciffinmodel) throws Exception ;

	@RequestMapping(value = "/cusFinModel/updateDragFinModel")
	public void updateDragFinModel(@RequestBody CusFinModel cusFinModel) throws Exception ;

	@RequestMapping(value = "/cusFinModel/getListForAccRule")
	public List<CusFinModel> getListForAccRule(@RequestBody String accRule) throws Exception ;

}