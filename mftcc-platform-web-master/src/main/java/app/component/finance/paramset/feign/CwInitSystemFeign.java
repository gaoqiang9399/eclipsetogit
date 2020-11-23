package app.component.finance.paramset.feign;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.finance.util.R;

/**
 * 类名： CwInitSystemBo 描述：
 * 
 * @author Javelin
 * @date 2017-2-6 上午9:44:58
 */
@FeignClient("mftcc-platform-fiscal")
public interface CwInitSystemFeign {

	/**
	 * 方法描述： 财务重新初始化操作(@RequestBody 清理)
	 * 
	 * @param formMap
	 * @return
	 * @throws Exception
	 *             String
	 * @author Javelin
	 * @date 2017-2-6 上午9:55:35
	 */
	@RequestMapping(value = "/cwInitSystem/doReInitSystem", method = RequestMethod.POST)
	public R doReInitSystem(@RequestBody Map<String, Object> formMap,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 方法描述： 财务初始化操作
	 * 
	 * @param formMap
	 * @return
	 * @throws Exception
	 *             String
	 * @author Javelin
	 * @date 2017-2-7 上午11:39:02
	 */
	@RequestMapping(value = "/cwInitSystem/doCwSystemInit", method = RequestMethod.POST)
	public R doCwSystemInit(@RequestBody Map<String, String> formMap,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 方法描述： 财务分步骤初始化 initType： week：启用期间初始化 comtype：科目编码规则初始化 comitem：科目
	 * initFlag：初始化完成
	 * 
	 * @param formMap
	 * @return
	 * @throws Exception
	 *             String
	 * @author Javelin
	 * @date 2017-2-20 下午2:17:48
	 */
	@RequestMapping(value = "/cwInitSystem/doCwSetInitPram", method = RequestMethod.POST)
	public R doCwSetInitPram(@RequestBody Map<String, String> formMap,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 方法描述： 获取系统初始化标志
	 * 
	 * @return
	 * @throws Exception
	 *             Map<String,String>
	 * @author Javelin
	 * @date 2017-2-8 上午11:02:07
	 */
	@RequestMapping(value = "/cwInitSystem/getSysInitFlag", method = RequestMethod.POST)
	public R getSysInitFlag(@RequestParam("finBooks") String finBooks) throws Exception;
}
