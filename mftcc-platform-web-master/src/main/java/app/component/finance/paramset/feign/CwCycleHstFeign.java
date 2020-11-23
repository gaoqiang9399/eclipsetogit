package app.component.finance.paramset.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.finance.paramset.entity.CwCycleHst;

/**
 * Title: CwCycleHstBo.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Sat Jan 07 15:57:56 CST 2017
 **/
@FeignClient("mftcc-platform-fiscal")
public interface CwCycleHstFeign {
	/**
	 * 
	 * 方法描述： 保存会计期间
	 * 
	 * @param years
	 * @param paramMap
	 * @return
	 * @throws Exception
	 *             String
	 * @author 张丽
	 * @date 2017-1-10 下午2:47:23
	 */
	@RequestMapping(value = "/cwCycleHst/insertCwcycle", method = RequestMethod.POST)
	public String insertCwcycle(@RequestParam("years") String years,@RequestBody Map<String, String> paramMap,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 
	 * 方法描述： 获取自然年的会计期间
	 * 
	 * @param year
	 * @return
	 * @throws Exception
	 *             List<List<String>>
	 * @author 张丽
	 * @date 2017-1-16 上午11:46:31
	 */
	@RequestMapping(value = "/cwCycleHst/getPeriod", method = RequestMethod.POST)
	public List<List<String>> getPeriod(@RequestBody String year) throws Exception;

	/**
	 * 
	 * 方法描述： 获取非自然年的会计期间
	 * 
	 * @param year
	 * @return
	 * @throws Exception
	 *             List<List<String>>
	 * @author 张丽
	 * @date 2017-1-17 下午4:43:20
	 */
	@RequestMapping(value = "/cwCycleHst/getPeriodByYear", method = RequestMethod.POST)
	public List<List<String>> getPeriodByYear(@RequestBody String year,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 获得显示会计区间页面的显示的年份和不能修改的最大周期
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/cwCycleHst/getShowParam", method = RequestMethod.POST) 
	public Map<String, String> getShowParam(@RequestParam("finBooks") String finBooks) throws Exception;

	@RequestMapping(value = "/cwCycleHst/delete", method = RequestMethod.POST)
	public void delete(@RequestBody CwCycleHst cwCycleHst) throws Exception;

	@RequestMapping(value = "/cwCycleHst/update", method = RequestMethod.POST)
	public void update(@RequestBody CwCycleHst cwCycleHst) throws Exception;

	@RequestMapping(value = "/cwCycleHst/getById", method = RequestMethod.POST)
	public CwCycleHst getById(@RequestBody CwCycleHst cwCycleHst) throws Exception;

	/**
	 * 插入20年的自然年度自然期间
	 * @throws Exception
	 */
	@RequestMapping(value = "/cwCycleHst/insertZiRanCwcycle", method = RequestMethod.POST) 
	public void insertZiRanCwcycle(@RequestParam("finBooks") String finBooks) throws Exception;


	// @RequestMapping(value = "/cwCycleHst/insert", method = RequestMethod.POST) public String[] isExistInitDate(@RequestBody );

}
