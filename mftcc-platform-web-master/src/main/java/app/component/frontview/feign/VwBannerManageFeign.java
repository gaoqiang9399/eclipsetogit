package app.component.frontview.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.base.ServiceException;
import app.component.frontview.entity.VwBannerManage;
import app.util.toolkit.Ipage;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Title: VwBannerManageBo.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Wed Apr 26 11:06:06 CST 2017
 **/
@FeignClient("mftcc-platform-factor")
public interface VwBannerManageFeign {
	/**
	 * 新增banner图
	 * 
	 * @param vwBannerManage
	 * @throws ServiceException
	 * @throws Exception
	 */
	@RequestMapping("/vwBannerManage/insert")
	public VwBannerManage insert(@RequestBody VwBannerManage vwBannerManage) throws ServiceException, Exception;

	/**
	 * 删除banner图
	 * 
	 * @param vwBannerManage
	 * @throws ServiceException
	 * @throws Exception
	 */
	@RequestMapping("/vwBannerManage/delete")
	public void delete(@RequestBody VwBannerManage vwBannerManage) throws ServiceException, Exception;

	/**
	 * 更新banner图
	 * 
	 * @param vwBannerManage
	 * @throws ServiceException
	 * @throws Exception
	 */
	@RequestMapping("/vwBannerManage/update")
	public VwBannerManage update(@RequestBody VwBannerManage vwBannerManage) throws ServiceException, Exception;

	/**
	 * 根据id查询banner图
	 * 
	 * @param vwBannerManage
	 * @return
	 * @throws ServiceException
	 * @throws Exception
	 */
	@RequestMapping("/vwBannerManage/getById")
	public VwBannerManage getById(@RequestBody VwBannerManage vwBannerManage) throws ServiceException, Exception;

	/**
	 * 获取列表
	 * 
	 * @param ipage
	 * @param vwBannerManage
	 * @return
	 * @throws ServiceException
	 * @throws Exception
	 */
	@RequestMapping("/vwBannerManage/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws ServiceException, Exception;

	/**
	 * 获取移动端banner图列表
	 * 
	 * @param ipage
	 * @param vwBannerManage
	 * @return
	 * @throws ServiceException
	 * @throws Exception
	 */
	@RequestMapping("/vwBannerManage/findAppByPage")
	public Ipage findAppByPage(@RequestBody Ipage ipage) throws ServiceException, Exception;

	/**
	 * 获取banner的最大编号
	 * 
	 * @param type
	 * @return
	 * @throws ServiceException
	 * @throws Exception
	 */
	@RequestMapping("/vwBannerManage/getMaxSort")
	public String getMaxSort(@RequestParam("type") String type) throws ServiceException, Exception;

	/**
	 * 获取图片的base64字符
	 * 
	 * @param id
	 * @return
	 * @throws ServiceException
	 * @throws Exception
	 */
	@RequestMapping("/vwBannerManage/getImgBase64")
	public String getImgBase64(@RequestParam("id") String id) throws ServiceException, Exception;

	/**
	 * 获取所有的banner图
	 * 
	 * @param paramMap
	 * @return
	 * @throws ServiceException
	 * @throws Exception
	 */
	@RequestMapping("/vwBannerManage/getBannerList")
	public List<VwBannerManage> getBannerList(@RequestBody Map<String, String> paramMap) throws ServiceException, Exception;

	/**
	 * 
	 * @Title: getBannerBasicList @Description: 获取banner信息
	 * 不包括base64数据 @param @param paramMap @param @return @param @throws
	 * ServiceException @param @throws Exception 参数 @return List<VwBannerManage>
	 * 返回类型 @throws
	 */
	@RequestMapping("/vwBannerManage/getBannerBasicList")
	List<VwBannerManage> getBannerBasicList(@RequestBody Map<String, String> paramMap) throws Exception;

	/**
	 * 
	 * @Title: getBannerListForId @Description: 获取banner信息 根据id @param @param
	 * paramMap @param @return @param @throws Exception 参数 @return
	 * List<VwBannerManage> 返回类型 @throws
	 */
	@RequestMapping("/vwBannerManage/getBannerListForId")
	List<VwBannerManage> getBannerListForId(@RequestBody Map<String, String> paramMap) throws Exception;

	/**
	 * 验证bannerIds是否和库里要展示的banner图一致，不一致 把新查询出来的结果发送回去； <br/>
	 * 不一致 返回map中含有“flag”键 并且
	 * flag=2,其余格式类型同{@link VwBannerManageFeign#getBannerList(@RequestBody Map)}
	 * 
	 * @param paramMap
	 *            参数中有bannerIds以，分隔
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/vwBannerManage/validateBannerList")
	public Map<String, Object> validateBannerList(@RequestBody Map<String, String> paramMap) throws Exception;

}
