package app.component.pact.feign;

import java.util.List;

import app.component.pact.entity.MfBusPactHis;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.util.toolkit.Ipage;

/**
 * Title: MfBusPactHisBoImplImpl.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Wed Jun 22 10:21:41 CST 2016
 **/
@FeignClient("mftcc-platform-factor")
public interface MfBusPactHisFeign {

	@RequestMapping(value = "/mfBusPactHis/insert")
	public void insert(@RequestBody MfBusPactHis mfBusPactHis) throws Exception;

	@RequestMapping(value = "/mfBusPactHis/delete")
	public void delete(@RequestBody MfBusPactHis mfBusPactHis) throws Exception;

	@RequestMapping(value = "/mfBusPactHis/update")
	public void update(@RequestBody MfBusPactHis mfBusPactHis) throws Exception;

	@RequestMapping(value = "/mfBusPactHis/getById")
	public MfBusPactHis getById(@RequestBody MfBusPactHis mfBusPactHis) throws Exception;

	@RequestMapping(value = "/mfBusPactHis/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage, @RequestParam("mfBusPactHis") MfBusPactHis mfBusPactHis) throws Exception;

	@RequestMapping(value = "/mfBusPactHis/getListByPactId")
	public List<MfBusPactHis> getListByPactId(@RequestBody MfBusPactHis mfBusPactHis) throws Exception;

	@RequestMapping(value = "/mfBusPactHis/getListByDESC")
	public List<MfBusPactHis> getListByDESC(@RequestBody MfBusPactHis mfBusPactHis) throws Exception;

}
