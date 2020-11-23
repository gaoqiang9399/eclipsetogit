package  app.component.clean.feign;
import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.base.ServiceException;
import app.component.clean.entity.MfDataClean;
import app.util.toolkit.Ipage;

/**
* Title: MfDataCleanBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Fri Apr 07 11:53:20 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface MfDataCleanFeign {

	/**
	 *
	 * 方法描述： 清理客户数据（单条）
	 * @param paramMap
	 * @throws ServiceException
	 * void
	 * @author zhs
	 * @date 2017-4-7 下午3:19:04
	 */
	@RequestMapping(value = "/mfDataClean/deleteCus")
	public void deleteCus(@RequestBody Map<String, String> paramMap) throws Exception;

	/**
	 *
	 * 方法描述：一键清理客户所有数据（包括客户、业务、押品及归档信息）
	 * @throws ServiceException
	 * void
	 * @author zhs
	 * @date 2017-4-24 下午3:15:57
	 */
	@RequestMapping(value = "/mfDataClean/deleteBatch")
	public void deleteBatch() throws Exception;
	/**
	 *
	 * 方法描述： 清理业务数据（单条）
	 * @param paramMap
	 * @throws ServiceException
	 * void
	 * @author zhs
	 * @date 2017-4-8 上午11:42:18
	 */
	@RequestMapping(value = "/mfDataClean/deleteBus")
	public void deleteBus(@RequestBody Map<String, String> paramMap) throws Exception;

	/**
	 *
	 * 方法描述： 批量清理业务数据
	 * @param paramList
	 * @throws ServiceException
	 * void
	 * @author zhs
	 * @date 2017-4-8 上午11:41:51
	 */
	@RequestMapping(value = "/mfDataClean/deleteBusBatch")
	public void deleteBusBatch(@RequestBody List<Map<String, String>> paramList) throws Exception;

	/**
	 *
	 * 方法描述：获取带分页的列表数据
	 * @param ipage
	 * @param mfDataClean
	 * @return
	 * @throws ServiceException
	 * Ipage
	 * @author zhs
	 * @date 2017-4-24 下午12:10:04
	 */
	@RequestMapping(value = "/mfDataClean/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;

	/**
	 *
	 * 方法描述： 获取清理详情页面信息
	 * @param mfDataClean
	 * @return
	 * @throws ServiceException
	 * Map<String,Object>
	 * @author zhs
	 * @date 2017-4-26 上午10:52:10
	 */
	@RequestMapping(value = "/mfDataClean/getDetailPage")
	public Map<String, Object> getDetailPage(@RequestBody MfDataClean mfDataClean) throws Exception;

	/**
	 *
	 * 方法描述： 验证客户信息是否能够被删除（判断该客户是否是其他业务的关联企业或其押品是否被其他项目引用）
	 * @param mfDataClean
	 * @throws ServiceException
	 * void
	 * @author zhs
	 * @date 2017-4-28 下午4:54:44
	 */
	@RequestMapping(value = "/mfDataClean/verifyCusCleanFlag")
	public Map<String, Object> verifyCusCleanFlag(@RequestBody MfDataClean mfDataClean) throws Exception;


	/**
	 *
	 * 方法描述： 根据部门选择操作员
	 * @param mfDataClean
	 * @return
	 * @throws ServiceException
	 * Map<String,Object>
	 * @author cd
	 * @date 2019-3-13 下午19:00:30
	 */
	@RequestMapping(value = "/mfDataClean/selectOpByBr")
	public Ipage selectOpByBr(@RequestBody Ipage ipage) throws Exception;

	/**
	 *
	 * 方法描述： 展示客户业务列表信息
	 * @param mfDataClean
	 * @return
	 * @throws ServiceException
	 * Map<String,Object>
	 * @author cd
	 * @date 2019-3-13 下午19:00:30
	 */
	@RequestMapping(value = "/mfDataClean/cusBusinessInfo")
	public List<MfDataClean> cusBusinessInfo(@RequestBody MfDataClean mfDataClean) throws Exception;

	/**
	 *
	 * 方法描述： 部门客户清理
	 * @param mfDataClean
	 * @return
	 * @throws ServiceException
	 * Map<String,Object>
	 * @author cd
	 * @date 2019-3-13 下午19:00:30
	 */
	@RequestMapping(value = "/mfDataClean/dataBrClear")
	public Map<String, Object> dataBrClear(@RequestBody Map<String, String> paramMap) throws Exception;
}


