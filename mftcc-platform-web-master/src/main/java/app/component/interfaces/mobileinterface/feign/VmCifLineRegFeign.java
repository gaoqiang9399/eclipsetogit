package  app.component.interfaces.mobileinterface.feign;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.interfaces.mobileinterface.entity.VmCifCustomerDTO;
import app.component.interfaces.mobileinterface.entity.VmCifLineReg;
import app.util.toolkit.Ipage;

/**
* Title: VmCifLineRegBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Fri May 05 10:14:21 CST 2017
**/
@FeignClient("mftcc-platform-factor")
public interface VmCifLineRegFeign{
	
	@RequestMapping(value = "/vmCifLineReg/sendMessage")
	public Map<String, Object> sendMessage(@RequestBody  VmCifLineReg vmCifLineReg) throws Exception;

	@RequestMapping(value = "/vmCifLineReg/login")
	public Map<String, Object> login(@RequestBody  VmCifLineReg vmCifLineReg,@RequestParam("type") String type,@RequestParam("verifyNum") String verifyNum) throws Exception;

	@RequestMapping(value = "/vmCifLineReg/insert")
	public void insert(@RequestBody  VmCifLineReg vmCifLineReg) throws Exception;

	@RequestMapping(value = "/vmCifLineReg/delete")
	public void delete(@RequestBody  VmCifLineReg vmCifLineReg) throws Exception;

	@RequestMapping(value = "/vmCifLineReg/update")
	public void update(@RequestBody  VmCifLineReg vmCifLineReg) throws Exception;

	@RequestMapping(value = "/vmCifLineReg/getById")
	public VmCifLineReg getById(@RequestBody  VmCifLineReg vmCifLineReg) throws Exception;

	@RequestMapping(value = "/vmCifLineReg/findByPage")
	public Ipage findByPage(@RequestBody  Ipage ipage,@RequestParam("vmCifLineReg") VmCifLineReg vmCifLineReg) throws Exception;

	@RequestMapping(value = "/vmCifLineReg/getByOpenid")
	public VmCifLineReg getByOpenid(@RequestBody  VmCifLineReg vmCifLineReg) throws Exception;

	@RequestMapping(value = "/vmCifLineReg/completeCustomerInfo")
	public Map<String, Object> completeCustomerInfo(@RequestBody  VmCifCustomerDTO cifcus,@RequestParam("httpServletRequest") HttpServletRequest httpServletRequest)
			throws Exception;

	@RequestMapping(value = "/vmCifLineReg/registerCustomer")
	public Map<String, Object> registerCustomer(@RequestBody  VmCifLineReg vmCifLineReg,@RequestParam("verifyNum") String verifyNum,@RequestParam("type") String type)
			throws Exception;

	@RequestMapping(value = "/vmCifLineReg/validateVerifyCode")
	public Map<String, Object> validateVerifyCode(@RequestBody  VmCifLineReg vmCifLineReg,@RequestParam("verifyNum") String verifyNum);

	@RequestMapping(value = "/vmCifLineReg/insert")
	public Map<String, Object> PClogin(@RequestBody  Map<String, String> paramMap) throws Exception;
	
	
}
