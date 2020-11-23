package  app.component.cus.feign;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.cus.entity.MfCusPersBaseInfo;
import app.util.toolkit.Ipage;

/**
* Title: MfCusPersBaseInfoBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Mon May 30 08:58:15 CST 2016
**/
@FeignClient("mftcc-platform-factor")
public interface MfCusPersBaseInfoFeign {
	
	@RequestMapping("/mfCusPersBaseInfo/insert")
	public void insert(@RequestBody MfCusPersBaseInfo mfCusPersBaseInfo) throws Exception;
	
	@RequestMapping("/mfCusPersBaseInfo/insertForApp")
	public MfCusPersBaseInfo insertForApp(@RequestBody MfCusPersBaseInfo mfCusPersBaseInfo) throws Exception;
	
	@RequestMapping("/mfCusPersBaseInfo/delete")
	public void delete(@RequestBody MfCusPersBaseInfo mfCusPersBaseInfo) throws Exception;
	
	@RequestMapping("/mfCusPersBaseInfo/update")
	public void update(@RequestBody MfCusPersBaseInfo mfCusPersBaseInfo) throws Exception;
	
	@RequestMapping("/mfCusPersBaseInfo/getById")
	public MfCusPersBaseInfo getById(@RequestBody MfCusPersBaseInfo mfCusPersBaseInfo) throws Exception;
	
	@RequestMapping("/mfCusPersBaseInfo/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;

	@RequestMapping("/mfCusPersBaseInfo/checkCusFamilyInfo")
	public boolean checkCusFamilyInfo(@RequestParam("cusNo") String cusNo) throws Exception;
	/**
	 * 
	 * 方法描述： 根据客户号获取客户基本的信息
	 * @param cusNo
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author zhs
	 * @date 2017-12-5 上午10:08:02
	 */
	@RequestMapping("/mfCusPersBaseInfo/getCusPersInfo")
	public Map<String, Object> getCusPersInfo(@RequestParam("cusNo") String cusNo) throws  Exception;
	
	/**
	 * @param cusNo 客户号
	 * @param cusName 客户名称
	 * @return
	 * @throws Exception
	 */	
	@RequestMapping("/mfCusPersBaseInfo/identityCheck")
	public Map<String, Object> identityCheck(@RequestParam("idNum") String idNum,@RequestParam("cusName") String cusName,@RequestParam("thirdFlag") String thirdFlag,@RequestParam("cusNo") String cusNo)throws Exception;
	
}
