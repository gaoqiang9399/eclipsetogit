package app.component.platforminterface;

import java.util.Map;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * Title: platforminterface.java Description:
 * 
 * @author:LiuYF@mftcc.cn
 * @Thu Feb 09 10:31:48 CST 2017
 **/
@FeignClient("mftcc-platform-factor")
public interface PlatformInterfaceFeign {

	@RequestMapping(value = "/platformInterface/cusRegister")
	public boolean cusRegister(@RequestBody Map<String, String> paramMap) ;

	@RequestMapping(value = "/platformInterface/getAllCores",method=RequestMethod.POST)
	public String getAllCores() ;

	@RequestMapping(value = "/platformInterface/cusLogin")
	public boolean cusLogin(@RequestParam String userName,@RequestParam String pwd) ;

	@RequestMapping(value = "/platformInterface/getAllSysKindList",method=RequestMethod.POST)
	public String getAllSysKindList() ;

	@RequestMapping(value = "/platformInterface/insertForApply")
	public boolean insertForApply(@RequestBody Map<String, String> paramMap) ;

	@RequestMapping(value = "/platformInterface/getCusInfo")
	public String getCusInfo(@RequestBody String userName) ;

	@RequestMapping(value = "/platformInterface/getRelations")
	public String getRelations(@RequestParam String cusNo,@RequestParam String cusType) ;

	@RequestMapping(value = "/platformInterface/getCusApplyList")
	public String getCusApplyList(@RequestBody String userName) ;

}
