package app.component.pss.information.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.pss.information.entity.PssFreightSpace;
import app.component.pss.information.entity.PssStorehouse;
import app.util.toolkit.Ipage;
import net.sf.json.JSONArray;

/**
 * Title: PssStorehouseBo.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Wed Aug 16 18:28:15 CST 2017
 **/
@FeignClient("mftcc-platform-factor")
public interface PssStorehouseFeign {

	@RequestMapping("/pssStorehouse/insert")
	public void insert(@RequestBody PssStorehouse pssStorehouse, @RequestParam("pssFreightSpaceList")List<PssFreightSpace> pssFreightSpaceList)
			throws Exception ;

	@RequestMapping("/pssStorehouse/delete")
	public void delete(@RequestBody PssStorehouse pssStorehouse) throws Exception ;

	@RequestMapping("/pssStorehouse/update")
	public void update(@RequestBody PssStorehouse pssStorehouse,  @RequestParam("pssFreightSpaceList")List<PssFreightSpace> pssFreightSpaceList)
			throws Exception ;

	@RequestMapping("/pssStorehouse/updateFlag")
	public int updateFlag(@RequestBody PssStorehouse pssStorehouse) throws Exception ;

	@RequestMapping("/pssStorehouse/updateDisplayFlag")
	public int updateDisplayFlag(@RequestBody PssStorehouse pssStorehouse) throws Exception ;

	@RequestMapping("/pssStorehouse/getById")
	public PssStorehouse getById(@RequestBody PssStorehouse pssStorehouse) throws Exception ;

	@RequestMapping("/pssStorehouse/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception ;

	@RequestMapping("/pssStorehouse/getAll")
	public List<PssStorehouse> getAll(@RequestBody PssStorehouse pssStorehouse) throws Exception ;

	@RequestMapping("/pssStorehouse/getIdAndNameMap")
	public List<Map<String, String>> getIdAndNameMap(@RequestBody PssStorehouse pssStorehouse) throws Exception ;

	@RequestMapping("/pssStorehouse/getPssStorehouseJSONArray")
	public JSONArray getPssStorehouseJSONArray(@RequestBody PssStorehouse pssStorehouse) throws Exception ;
}
