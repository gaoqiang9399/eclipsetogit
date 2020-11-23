package  app.component.query.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.query.entity.MfQueryItem;
import app.util.toolkit.Ipage;

/**
* Title: MfQueryItemBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Sat Jun 10 18:03:08 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface MfQueryItemFeign {
	
	@RequestMapping(value = "/mfQueryItem/insert")
	public List<MfQueryItem> insert(@RequestBody MfQueryItem mfQueryItem) throws Exception;
	
	@RequestMapping(value = "/mfQueryItem/delete")
	public void delete(@RequestBody MfQueryItem mfQueryItem) throws Exception;
	
	@RequestMapping(value = "/mfQueryItem/update")
	public void update(@RequestBody MfQueryItem mfQueryItem) throws Exception;
	
	@RequestMapping(value = "/mfQueryItem/getById")
	public MfQueryItem getById(@RequestBody MfQueryItem mfQueryItem) throws Exception;
	
	@RequestMapping(value = "/mfQueryItem/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("mfQueryItem") MfQueryItem mfQueryItem) throws Exception;

	@RequestMapping(value = "/mfQueryItem/getMyAttentionList")
	public List<MfQueryItem> getMyAttentionList(@RequestBody MfQueryItem mfQueryItem) throws Exception;
	/**
	 * 
	 * @Title: insertAndGetOAMyAttentionList
	 * @Description: 根据OA入口界面优化需求,在用户未设置的情况下,显示全部oa入口,然后用户可以设置增减个性化oa界面,用户是否进入过oa界面通过mf_query_item表中的is_base等于3的一条数据进行标识
	 * @param mfQueryItem
	 * @param 
	 * @param Exception    参数
	 * @return List<MfQueryItem>    返回类型
	 * @throws
	 * @date 2017-7-27
	 */
	@RequestMapping(value = "/mfQueryItem/insertAndGetOAMyAttentionList")
	public List<MfQueryItem> insertAndGetOAMyAttentionList(@RequestBody MfQueryItem mfQueryItem)throws Exception;
	
	/**
	 * 
	 * 方法描述：获取关注的基础项  
	 * @param mfQueryItem
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author zhs
	 * @date 2017-6-26 下午2:43:31
	 */
	@RequestMapping(value = "/mfQueryItem/getBaseItemList")
	public List<Map<String, Object>> getBaseItemList(@RequestBody MfQueryItem mfQueryItem) throws Exception;
	
	@RequestMapping(value = "/mfQueryItem/getReportItem")
	public Map<String, Object> getReportItem(@RequestBody MfQueryItem mfQueryItem) throws Exception;
	
	@RequestMapping(value = "/mfQueryItem/getReportItemAll")
	public Map<String, Object> getReportItemAll(@RequestBody MfQueryItem mfQueryItem) throws Exception;
	
	@RequestMapping(value = "/mfQueryItem/updateReport")
	public void updateReport(@RequestBody MfQueryItem mfQueryItem) throws Exception;

	/**
	 * 
	 * 方法描述：获取关注的办公基础项 
	 * @param mfQueryItem
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author zhs
	 * @date 2017-6-27 上午10:53:56
	 */
	@RequestMapping(value = "/mfQueryItem/getBaseOaItemList")
	public Map<String, Object> getBaseOaItemList(@RequestBody MfQueryItem mfQueryItem) throws Exception;
	
	/**
	 * 
	 * 方法描述： 获取台账和统计查询的报表基础数据
	 * @return
	 * @throws Exception
	 * List<MfQueryItem>
	 * @author lwq
	 * @date 2017-7-25 下午4:52:47
	 */
	@RequestMapping(value = "/mfQueryItem/getQueryItemList")
	public Map<String, Object> getQueryItemList()throws Exception;

	/**
	 * 
	 * 方法描述： 获取查询的不同模块下的数据（客户、业务、押品、办公）
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author zhs
	 * @date 2017-11-3 下午4:15:07
	 */
	@RequestMapping(value = "/mfQueryItem/getQueryBaseItemList")
	public Map<String, Object> getQueryBaseItemList() throws Exception;

	@RequestMapping(value = "/mfQueryItem/getQueryItemListByMenuNo")
	Map<String, Map<String, List<MfQueryItem>>> getQueryItemListByMenuNo(@RequestBody Ipage ipage)throws Exception;

	@RequestMapping(value = "/mfQueryItem/getQueryItemListAllByMenuNo")
	Map<String, Object> getQueryItemListAllByMenuNo(@RequestBody MfQueryItem mfQueryItem) throws Exception;

}
