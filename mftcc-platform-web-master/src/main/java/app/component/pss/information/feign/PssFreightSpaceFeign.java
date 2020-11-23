package app.component.pss.information.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.pss.information.entity.PssFreightSpace;
import app.util.toolkit.Ipage;

/**
 * Title: PssFreightSpaceBo.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Wed Jan 10 15:43:42 CST 2018
 **/
@FeignClient("mftcc-platform-factor")
public interface PssFreightSpaceFeign {

	@RequestMapping("/PssFreightSpace/insert")
	public void insert(@RequestBody PssFreightSpace pssFreightSpace) throws Exception ;

	@RequestMapping("/PssFreightSpace/delete")
	public void delete(@RequestBody PssFreightSpace pssFreightSpace) throws Exception ;

	@RequestMapping("/PssFreightSpace/update")
	public void update(@RequestBody PssFreightSpace pssFreightSpace) throws Exception ;

	@RequestMapping("/PssFreightSpace/getById")
	public PssFreightSpace getById(@RequestBody PssFreightSpace pssFreightSpace) throws Exception ;

	@RequestMapping("/PssFreightSpace/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception ;

	@RequestMapping("/PssFreightSpace/getAll")
	public List<PssFreightSpace> getAll(@RequestBody PssFreightSpace pssFreightSpace) throws Exception ;

	/**
	 * 关闭仓位
	 * 
	 * @param pssFreightSpace
	 *            仓位
	 * @return 结果
	 * @throws Exception
	 */
	@RequestMapping("/PssFreightSpace/doCloseFreightSpace")
	public boolean doCloseFreightSpace(@RequestBody PssFreightSpace pssFreightSpace) throws Exception ;

	/**
	 * 启用仓位
	 * 
	 * @param pssFreightSpace
	 *            仓位
	 * @return 结果
	 * @throws Exception
	 */
	@RequestMapping("/PssFreightSpace/doEnableFreightSpace")
	public boolean doEnableFreightSpace(@RequestBody PssFreightSpace pssFreightSpace) throws Exception ;
}
