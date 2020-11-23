package app.component.finance.assets.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.finance.assets.entity.CwAssets;
import app.component.finance.assets.entity.CwAssetsClass;
import app.component.finance.util.R;
import app.util.toolkit.Ipage;

/**
 * Title: CwAssetsBo.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Mon Apr 10 16:28:21 CST 2017
 **/
@FeignClient("mftcc-platform-fiscal")
public interface CwAssetsFeign {

	@RequestMapping(value = "/cwAssets/insert", method = RequestMethod.POST)
	public R insert(@RequestBody CwAssets cwAssets,@RequestParam("finBooks") String finBooks) throws ServiceException;

	@RequestMapping(value = "/cwAssets/delete", method = RequestMethod.POST)
	public R delete(@RequestBody CwAssets cwAssets,@RequestParam("finBooks") String finBooks) throws ServiceException;

	@RequestMapping(value = "/cwAssets/update", method = RequestMethod.POST)
	public R update(@RequestBody CwAssets cwAssets,@RequestParam("finBooks") String finBooks) throws ServiceException;

	@RequestMapping(value = "/cwAssets/getById", method = RequestMethod.POST)
	public CwAssets getById(@RequestBody CwAssets cwAssets,@RequestParam("finBooks") String finBooks) throws ServiceException;

	@RequestMapping(value = "/cwAssets/findByPage", method = RequestMethod.POST)
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("finBooks") String finBooks) throws ServiceException;

	/**
	 * 方法描述： 获取资产详情
	 * 
	 * @param cwAssets
	 * @return
	 * @throws ServiceException
	 *             Map<String,Object>
	 * @author Javelin
	 * @date 2017-5-27 下午2:17:18
	 */
	@RequestMapping(value = "/cwAssets/getCwAssetsInfo", method = RequestMethod.POST)
	public Map<String, Object> getCwAssetsInfo(@RequestBody CwAssets cwAssets,@RequestParam("finBooks") String finBooks) throws ServiceException;

	/**
	 * 方法描述： 批量删除
	 * 
	 * @param formMap
	 * @return
	 * @throws Exception
	 *             Map<String,String>
	 * @author Javelin
	 * @date 2017-5-11 下午2:39:10
	 */
	@RequestMapping(value = "/cwAssets/deleteBatchs", method = RequestMethod.POST)
	public R deleteBatchs(@RequestBody Map<String, String> formMap,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 方法描述： 处理固定资产折旧
	 * 
	 * @param vchMstMap
	 * @return
	 * @throws Exception
	 *             Map<String,String>
	 * @author Javelin
	 * @date 2017-5-11 下午2:39:25
	 */
	@RequestMapping(value = "/cwAssets/doDepreciation", method = RequestMethod.POST)
	public Map<String, String> doDepreciation(@RequestBody Map<String, String> vchMstMap,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 方法描述： 获取资产类别列表
	 * 
	 * @param cwAssetsClass
	 * @return
	 * @throws Exception
	 *             List<CwAssetsClass>
	 * @author Javelin
	 * @date 2017-6-27 下午3:21:26
	 */
	@RequestMapping(value = "/cwAssets/getAssetsClassList", method = RequestMethod.POST)
	public List<CwAssetsClass> getAssetsClassList(@RequestBody CwAssetsClass cwAssetsClass,@RequestParam("finBooks")String finBooks) throws Exception;
}
