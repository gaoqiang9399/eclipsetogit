package app.component.frontview.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.frontview.entity.MfAppProdItem;
import app.util.toolkit.Ipage;

/**
 * Title: MfAppProdItemBo.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Tue Oct 24 20:40:37 CST 2017
 **/

@FeignClient("mftcc-platform-factor")
public interface MfAppProdItemFeign {

	@RequestMapping("/mfAppProdItem/insert")
	public void insert(@RequestBody MfAppProdItem mfAppProdItem) throws Exception;

	@RequestMapping("/MfExamIndex/delete")
	public void delete(@RequestBody MfAppProdItem mfAppProdItem) throws Exception;

	@RequestMapping("/MfExamIndex/update")
	public void update(@RequestBody MfAppProdItem mfAppProdItem) throws Exception;

	@RequestMapping("/MfExamIndex/getById")
	public MfAppProdItem getById(@RequestBody MfAppProdItem mfAppProdItem) throws Exception;

	@RequestMapping("/MfExamIndex/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage, @RequestParam("mfAppProdItem") MfAppProdItem mfAppProdItem) throws Exception;

	/**
	 * 根据认证信息项插入产品需要采集的信息
	 * 
	 * @param KindNo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/MfExamIndex/insertProdItem")
	public Map<String, Object> insertProdItem(@RequestBody String KindNo, @RequestParam("KindName") String KindName) throws Exception;

	/**
	 * 根据kindNo查询是否存在该产品
	 * 
	 * @param mfAppProdItem
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/MfExamIndex/getAppProdItemByKindNo")
	public MfAppProdItem getAppProdItemByKindNo(@RequestBody MfAppProdItem mfAppProdItem) throws Exception;

	/**
	 * 查询新增的产品列表
	 * 
	 * @param mfAppProdItem
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/MfExamIndex/getAppProdItemList")
	public List<MfAppProdItem> getAppProdItemList(@RequestBody MfAppProdItem mfAppProdItem) throws Exception;

	/**
	 * 根据产品号删除产品的信息
	 * 
	 * @param mfAppProdItem
	 * @throws Exception
	 */
	@RequestMapping("/MfExamIndex/deleteByKindNo")
	public void deleteByKindNo(@RequestBody MfAppProdItem mfAppProdItem) throws Exception;
}
