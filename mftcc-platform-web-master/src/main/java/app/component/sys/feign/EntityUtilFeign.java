package app.component.sys.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import net.sf.json.JSONArray;
@FeignClient("mftcc-platform-factor")
public interface EntityUtilFeign {
	@RequestMapping(value = "/entityUtil/prodAutoMenu")
	public JSONArray prodAutoMenu(@RequestParam("entityJson") String entityJson, @RequestParam("ajaxData")String ajaxData, @RequestParam("query")String query, @RequestParam("parms")String parms, @RequestParam("methodName")String methodName,@RequestBody Class clazz) ;

}
