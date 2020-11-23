package app.component.tour.feign;


import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.cus.custracing.entity.MfCusTrack;
import app.component.tour.entity.MfBusTour;
import app.util.toolkit.Ipage;
import net.sf.json.JSONArray;

@FeignClient("mftcc-platform-factor")
public interface MfBusTourFeign {
	@RequestMapping("/mfBusTour/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;

	@RequestMapping("/mfBusTour/queryTourContext")
	public Ipage queryTourContext(@RequestBody Ipage ipage) throws Exception;
	
	
	@RequestMapping("/mfBusTour/insert")
	public void insert(@RequestBody MfBusTour mfBusTour) throws Exception;


	@RequestMapping("/mfBusTour/getById")
	public MfBusTour getById(@RequestBody MfBusTour mfBusTour) throws Exception;
	
	@RequestMapping("/mfBusTour/queyAllBase")
	public MfBusTour queyAllBase(@RequestBody MfBusTour mfBusTour) throws Exception;

	@RequestMapping("/mfBusTour/delete")
	public void delete(@RequestBody MfBusTour mfBusTour) throws Exception;

	@RequestMapping("/mfBusTour/update")
	public void update(@RequestBody MfBusTour mfBusTour) throws Exception;

	@RequestMapping(value = "/mfBusTour/getTrackTypeArray")
	public JSONArray getTrackTypeArray()throws ServiceException;
	
	@RequestMapping(value = "/mfBusTour/getList")
	public List<MfBusTour> getList(@RequestBody MfBusTour mfBusTour) throws ServiceException;
	
	

	
}
