package app.component.interfaces.appinterface.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.interfaces.appinterface.entity.DingBusApproval;
import app.component.sys.entity.SysTaskInfo;
import app.component.sys.entity.SysUser;
import app.util.toolkit.Ipage;
@FeignClient("mftcc-platform-factor")
public interface DingBusApprovalFeign{
	
	@RequestMapping(value = "/dingBusApproval/insert")
	public void insert(@RequestBody  DingBusApproval sysTaskInfo) throws Exception;

	@RequestMapping(value = "/dingBusApproval/delete")
	public void delete(@RequestBody  DingBusApproval sysTaskInfo) throws Exception;

	@RequestMapping(value = "/dingBusApproval/update")
	public void update(@RequestBody  DingBusApproval sysTaskInfo) throws Exception;

	@RequestMapping(value = "/dingBusApproval/getById")
	public DingBusApproval getById(@RequestBody  DingBusApproval dingBusApproval) throws Exception;

	@RequestMapping(value = "/dingBusApproval/findByPage")
	public Ipage findByPage(@RequestBody  Ipage ipage,@RequestParam("dingBusApproval") DingBusApproval dingBusApproval) throws Exception;

	@RequestMapping(value = "/dingBusApproval/findByPagePact")
	public Ipage findByPagePact(@RequestBody  Ipage ipage,@RequestParam("dingBusApproval") DingBusApproval dingBusApproval) throws Exception;

	@RequestMapping(value = "/dingBusApproval/getAllForWechat")
	public List<SysTaskInfo> getAllForWechat(@RequestBody  String pasMaxNo,@RequestParam("pasMinNo") String pasMinNo,@RequestParam("userNo") String userNo,@RequestParam("pasSts") String pasSts);

	@RequestMapping(value = "/dingBusApproval/findByPage1")
	public Ipage findByPage1(@RequestBody  Ipage ipage,@RequestParam("sysTaskInfo") SysTaskInfo sysTaskInfo) throws Exception;

	@RequestMapping(value = "/dingBusApproval/getByMobile")
	public SysUser getByMobile(@RequestBody  SysUser sysUser) throws Exception;
	
	

}
