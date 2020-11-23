package app.component.pss.fund.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.pss.fund.entity.PssOtherPayDetailBill;
import app.util.toolkit.Ipage;

/**
 * Title: PssOtherPayDetailBillBo.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Wed Nov 15 15:02:32 CST 2017
 **/
@FeignClient("mftcc-platform-factor")
public interface PssOtherPayDetailBillFeign {

	@RequestMapping(value = "/pssOtherPayDetailBill/insert")
	public void insert(@RequestBody PssOtherPayDetailBill pssOtherPayDetailBill) throws Exception ;

	@RequestMapping(value = "/pssOtherPayDetailBill/delete")
	public void delete(@RequestBody PssOtherPayDetailBill pssOtherPayDetailBill) throws Exception ;

	@RequestMapping(value = "/pssOtherPayDetailBill/update")
	public void update(@RequestBody PssOtherPayDetailBill pssOtherPayDetailBill) throws Exception ;

	@RequestMapping(value = "/pssOtherPayDetailBill/getById")
	public PssOtherPayDetailBill getById(@RequestBody PssOtherPayDetailBill pssOtherPayDetailBill) throws Exception ;

	@RequestMapping(value = "/pssOtherPayDetailBill/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,
			@RequestParam("pssOtherPayDetailBill") PssOtherPayDetailBill pssOtherPayDetailBill) throws Exception ;

	@RequestMapping(value = "/pssOtherPayDetailBill/getAll")
	public List<PssOtherPayDetailBill> getAll(@RequestBody PssOtherPayDetailBill pssOtherPayDetailBill)
			throws Exception ;

	@RequestMapping(value = "/pssOtherPayDetailBill/findByPage1")
	public Ipage findByPage(@RequestBody Ipage ipage, @RequestParam Map<String, String> formMap)
			throws Exception ;
}
