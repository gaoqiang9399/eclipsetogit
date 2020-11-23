package app.component.frontviewinterface;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.frontview.entity.MfAppVersion;
import app.component.frontview.entity.MfFrontAppSetting;
import app.component.frontview.entity.MfFrontAppform;
import app.component.frontview.entity.MfFrontKind;
import app.component.frontview.entity.VwBannerManage;
import app.component.frontview.entity.VwContManage;
import app.component.frontview.entity.VwFeatureManage;
import app.component.frontview.entity.VwLinkManage;
import app.component.frontview.entity.VwMenuManage;
import app.component.frontview.entity.VwSetManage;
import app.util.toolkit.Ipage;

/**
 * Title: frontviewinterface.java Description:
 * 
 * @author:yht@dhcc.com.cn
 * @Sat May 27 15:11:14 CST 2017
 **/

@FeignClient("mftcc-platform-factor")
public interface FrontviewinterfaceFeign {
	/**
	 * 验证bannerIds是否和库里要展示的banner图一致，不一致 把新查询出来的结果发送回去
	 * 
	 * @return
	 */
	@RequestMapping("/frontviewinterface/validateBannerList")
	public Map<String, Object> validateBannerList(@RequestBody Map<String, String> paramMap) throws Exception;

	/**
	 * 获取banner图
	 */
	@RequestMapping("/frontviewinterface/getBannerList")
	public List<VwBannerManage> getBannerList(@RequestBody Map<String, String> paramMap) throws Exception;

	/**
	 * 
	 * @Title: getBannerBasicList @Description: 获取banner图
	 *         不包括base64 @param @param paramMap @param @return @param @throws
	 *         Exception 参数 @return List<VwBannerManage> 返回类型 @throws
	 */
	@RequestMapping("/frontviewinterface/getBannerBasicList")
	List<VwBannerManage> getBannerBasicList(@RequestBody Map<String, String> paramMap) throws Exception;

	/**
	 * 
	 * @Title: getBannerListForId @Description: 获取banner图 根据id @param @param
	 *         paramMap @param @return @param @throws Exception 参数 @return
	 *         List<VwBannerManage> 返回类型 @throws
	 */
	@RequestMapping("/frontviewinterface/getBannerListForId")
	List<VwBannerManage> getBannerListForId(@RequestBody Map<String, String> paramMap) throws Exception;

	/**
	 * 获取网站基础设置信息
	 */
	@RequestMapping("/frontviewinterface/getVwSetBean")
	public VwSetManage getVwSetBean() throws Exception;

	/**
	 * 获取合作伙伴和友情链接
	 */
	@RequestMapping("/frontviewinterface/getLinkGroup")
	public List<VwLinkManage> getLinkGroup(@RequestBody Map<String, String> paramMap) throws Exception;

	/**
	 * 获取新闻菜单和内容
	 */
	@RequestMapping("/frontviewinterface/getAllMenuCont")
	public List<VwMenuManage> getAllMenuCont() throws Exception;

	/**
	 * 获取新闻菜单
	 */
	@RequestMapping("/frontviewinterface/getAllMenu")
	public List<VwMenuManage> getAllMenu() throws Exception;

	/**
	 * 获取新闻菜单内容
	 */
	@RequestMapping("/frontviewinterface/getContByBlock")
	public List<VwContManage> getContByBlock(@RequestBody Map<String, String> paramMap) throws Exception;

	/**
	 * 获取新闻菜单内容
	 */
	@RequestMapping("/frontviewinterface/findContByBlockByPage")
	public Ipage findContByBlockByPage(@RequestBody Ipage ipage, @RequestParam("paramMap") Map<String, String> paramMap) throws Exception;

	/**
	 * 根据内容id查询内容
	 */
	@RequestMapping("/frontviewinterface/getContentById")
	public VwContManage getContentById(@RequestBody Map<String, String> paramMap) throws Exception;

	/**
	 * 获取所有平台特点
	 * 
	 * @return
	 */
	@RequestMapping("/frontviewinterface/getAllFeature")
	public List<VwFeatureManage> getAllFeature() throws Exception;

	/**
	 * 获取所有前端交易产品，不分页
	 * 
	 * @param ipage
	 * @param mfFrontKind
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/frontviewinterface/getAllFrondKindList")
	public Ipage getAllFrondKindList(@RequestBody Ipage ipage, @RequestParam("mfFrontKind") MfFrontKind mfFrontKind) throws Exception;

	/**
	 * 根据产品字段名获取到配置信息
	 * 
	 * @param mfFrontAppform
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/frontviewinterface/getByKindNoAndFieldName")
	public MfFrontAppform getByKindNoAndFieldName(@RequestBody String kindNo, @RequestParam("fieldName") String fieldName) throws Exception;

	/**
	 * 获取移动端产品配置信息
	 * 
	 * @author MaHao
	 * @param mfFrontAppform
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/frontviewinterface/getBy")
	public List<MfFrontAppform> getBy(@RequestBody MfFrontAppform mfFrontAppform) throws Exception;

	/**
	 * 获取移动端基本配置信息
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/frontviewinterface/get")
	public MfFrontAppSetting get() throws Exception;

	/**
	 * 根据产品编号获取产品配置信息(@RequestBody 放款次数控制，放款金额控制)
	 * 
	 * @param mfFrontKind
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/frontviewinterface/getFrontKindByKindNo")
	public MfFrontKind getFrontKindByKindNo(@RequestBody MfFrontKind mfFrontKind) throws Exception;

	/**
	 * 获取最新一条app版本信息
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/frontviewinterface/getLatestOneVersion")
	public MfAppVersion getLatestOneVersion() throws Exception;

	/**
	 * 根据版本号获取版本信息
	 * 
	 * @param mfAppVersion
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/frontviewinterface/getAppVersionByVer")
	public MfAppVersion getAppVersionByVer(@RequestBody MfAppVersion mfAppVersion) throws Exception;

	/**
	 * 获取大于当前时间的版本列表
	 * 
	 * @param mfAppVersion
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/frontviewinterface/getAppVersionListByTime")
	public List<MfAppVersion> getAppVersionListByTime(@RequestBody MfAppVersion mfAppVersion) throws Exception;

	/**
	 * 获取列表
	 * 
	 * @param vwMenuManage
	 * @returnz
	 * @throws ServiceException
	 */
	@RequestMapping("/frontviewinterface/getVwMenuManageList")
	public List<VwMenuManage> getVwMenuManageList(@RequestBody VwMenuManage vwMenuManage) throws ServiceException, Exception;
}
