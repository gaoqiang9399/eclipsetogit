package app.component.pfs.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.pfs.entity.CusFinFormula;
import app.util.toolkit.Ipage;

/**
 * Title: CusFinFormulaBoImplImpl.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Tue Nov 15 14:49:36 CST 2016
 **/

@FeignClient("mftcc-platform-factor")
public interface CusFinFormulaFeign {

	@RequestMapping(value = "/cusFinFormula/insert")
	public void insert(@RequestBody CusFinFormula cusFinFormula) throws Exception ;

	@RequestMapping(value = "/cusFinFormula/delete")
	public void delete(@RequestBody CusFinFormula cusFinFormula) throws Exception ;

	@RequestMapping(value = "/cusFinFormula/update")
	public void update(@RequestBody CusFinFormula cusFinFormula) throws Exception ;

	@RequestMapping(value = "/cusFinFormula/getById")
	public CusFinFormula getById(@RequestBody CusFinFormula cusFinFormula) throws Exception ;

	@RequestMapping(value = "/cusFinFormula/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("cusFinFormula") CusFinFormula cusFinFormula) throws Exception ;

	@RequestMapping(value = "/cusFinFormula/getByCodeColumn")
	public List<CusFinFormula> getByCodeColumn(@RequestBody CusFinFormula cusFinFormula) throws Exception ;

	@RequestMapping(value = "/cusFinFormula/getByFormulaColumn")
	public List<CusFinFormula> getByFormulaColumn(@RequestBody CusFinFormula cusFinFormula) throws Exception ;

}
