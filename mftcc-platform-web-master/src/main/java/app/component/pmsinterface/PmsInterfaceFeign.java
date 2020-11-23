package app.component.pmsinterface;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.pms.entity.PmsDataRangRole;
import app.component.pms.entity.PmsEntranceRole;
@FeignClient("mftcc-platform-factor")
public interface PmsInterfaceFeign {
	/**
	 * 根据角色获取入口
	 * 
	 * @param roleNo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pmsInterface/getEntorByRole")
	public String getEntorByRole(@RequestParam("roleNoArr") String[] roleNoArr) throws Exception;

	/**
	 * 通过角色获取视角对象
	 * 
	 * @param roleNoArr
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pmsInterface/getPmsViewpointByRole")
	public String getPmsViewpointByRole(@RequestParam("roleNoArr") String[] roleNoArr) throws Exception;

	/**
	 * 通过角色获取角色跟数据权限关联信息 @Title: getPmsDataRangRoleByRole @param @param
	 * roleNoArr @param @throws Exception @return List<PmsDataRangRole> @throws
	 */
	@RequestMapping(value = "/pmsInterface/getPmsDataRangRoleByRole")
	public List<PmsDataRangRole> getPmsDataRangRoleByRole(@RequestParam("roleNoArr") String[] roleNoArr) throws Exception;

	/**
	 * 
	 * 方法描述： 获得当前操作员的功能权限，对角色时，所有开启的功能权限合并。
	 * 
	 * @param opNo
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2017-9-5 下午2:49:29
	 */
	@RequestMapping(value = "/pmsInterface/getUserPmsBizs")
	public String getUserPmsBizs(@RequestParam("opNo") String opNo) throws Exception;

	/**
	 * 
	 * 方法描述： 获得当前操作员的功能权限Map，对角色时，所有开启的功能权限合并。
	 * 
	 * @param opNo
	 * @return
	 * @throws Exception
	 *             String
	 * @author zhs
	 * @date 2017-12-12 上午11:45:37
	 */
	@RequestMapping(value = "/pmsInterface/getUserPmsBizsMap")
	public Map<String, String> getUserPmsBizsMap(@RequestParam("opNo") String opNo) throws Exception;

	/**
	 * 方法描述：根据角色获取所有入口权限
	 * 
	 * @param sysDate
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pmsInterface/findByRoleNo")
	public List<PmsEntranceRole> findByRoleNo(@RequestParam("roleNo") String roleNo) throws Exception;

	/**
	 * 
	 * 方法描述： 添加获得当前操作员的功能权限Map。 多角色时，所有开启的功能权限合并。不从session获得操作员的角色信息。
	 * 
	 * @param opNo
	 * @return
	 * @throws Exception
	 *             Map<String,String>
	 * @author 沈浩兵
	 * @date 2017-12-27 下午5:30:13
	 */
	@RequestMapping(value = "/pmsInterface/getPmsBizsMapNotBySession")
	public Map<String, String> getPmsBizsMapNotBySession(@RequestParam("opNo") String opNo) throws Exception;
}
