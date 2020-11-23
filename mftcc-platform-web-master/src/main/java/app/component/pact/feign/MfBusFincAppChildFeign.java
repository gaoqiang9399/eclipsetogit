package app.component.pact.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.pact.entity.MfBusFincAppChild;
import app.util.toolkit.Ipage;

/**
 * Title: MfBusFincAppChildBoImplImpl.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Wed Jun 07 16:33:20 CST 2017
 **/

@FeignClient("mftcc-platform-factor")
public interface MfBusFincAppChildFeign {

	@RequestMapping(value = "/mfBusFincAppChild/insert")
	public void insert(@RequestBody MfBusFincAppChild mfBusFincAppChild) throws Exception;

	@RequestMapping(value = "/mfBusFincAppChild/delete")
	public void delete(@RequestBody MfBusFincAppChild mfBusFincAppChild) throws Exception;

	@RequestMapping(value = "/mfBusFincAppChild/update")
	public void update(@RequestBody MfBusFincAppChild mfBusFincAppChild) throws Exception;

	@RequestMapping(value = "/mfBusFincAppChild/getById")
	public MfBusFincAppChild getById(@RequestBody MfBusFincAppChild mfBusFincAppChild) throws Exception;

	@RequestMapping(value = "/mfBusFincAppChild/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage, @RequestParam("mfBusFincAppChild") MfBusFincAppChild mfBusFincAppChild)
			throws Exception;

	@RequestMapping(value = "/mfBusFincAppChild/getMfBusFincAppChildByBean")
	public MfBusFincAppChild getMfBusFincAppChildByBean(@RequestBody MfBusFincAppChild mfBusFincAppChild)
			throws Exception;

	@RequestMapping(value = "/mfBusFincAppChild/getMfBusFincAppChildByInfo")
	public MfBusFincAppChild getMfBusFincAppChildByInfo(@RequestBody MfBusFincAppChild mfBusFincAppChild)
			throws Exception;

	@RequestMapping(value = "/mfBusFincAppChild/getByBean")
	public List<MfBusFincAppChild> getByBean(@RequestBody MfBusFincAppChild mc) throws Exception;

	@RequestMapping(value = "/mfBusFincAppChild/getMfBusFincAppChild")
	public MfBusFincAppChild getMfBusFincAppChild(@RequestBody MfBusFincAppChild mc) throws Exception;

	@RequestMapping(value = "/mfBusFincAppChild/getMfBusFincAppChildByAppId")
	public MfBusFincAppChild getMfBusFincAppChildByAppId(@RequestBody MfBusFincAppChild mfBusFincAppChild)
			throws Exception;

	@RequestMapping(value = "/mfBusFincAppChild/getTaskList")
	public List<MfBusFincAppChild> getTaskList(@RequestParam("opNo") String opNo) throws Exception;

}
