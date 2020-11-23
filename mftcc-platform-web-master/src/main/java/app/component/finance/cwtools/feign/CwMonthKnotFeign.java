package app.component.finance.cwtools.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.finance.cwtools.entity.CwMonthKnot;
import app.util.toolkit.Ipage;

/**
 * Title: CwMonthKnotBo.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Thu Jan 05 17:01:51 CST 2017
 **/
@FeignClient("mftcc-platform-fiscal")
public interface CwMonthKnotFeign {

	@RequestMapping(value = "/cwMonthKnot/insert", method = RequestMethod.POST)
	public void insert(@RequestBody CwMonthKnot cwMonthKnot,@RequestParam("finBooks") String finBooks) throws ServiceException;

	@RequestMapping(value = "/cwMonthKnot/delete", method = RequestMethod.POST)
	public void delete(@RequestBody CwMonthKnot cwMonthKnot,@RequestParam("finBooks") String finBooks) throws ServiceException;

	@RequestMapping(value = "/cwMonthKnot/update", method = RequestMethod.POST)
	public void update(@RequestBody CwMonthKnot cwMonthKnot,@RequestParam("finBooks") String finBooks) throws ServiceException;

	@RequestMapping(value = "/cwMonthKnot/getById", method = RequestMethod.POST)
	public CwMonthKnot getById(@RequestBody CwMonthKnot cwMonthKnot,@RequestParam("finBooks") String finBooks) throws ServiceException;

	@RequestMapping(value = "/cwMonthKnot/findByPage", method = RequestMethod.POST)
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("finBooks") String finBooks) throws ServiceException;

	@RequestMapping(value = "/cwMonthKnot/getAll", method = RequestMethod.POST)
	public List<CwMonthKnot> getAll(@RequestBody CwMonthKnot cwMonthKnot,@RequestParam("finBooks") String finBooks) throws ServiceException;

	/**
	 * 方法描述： 批量插入结账记录数据
	 * 
	 * @param list
	 * @throws ServiceException
	 *             void
	 * @author Javelin
	 * @date 2017-1-5 下午5:12:21
	 */
	@RequestMapping(value = "/cwMonthKnot/dobatchInsert", method = RequestMethod.POST)
	public void dobatchInsert(@RequestBody List<CwMonthKnot> list) throws ServiceException;

	/**
	 * 方法描述：查询当前是否已结账，true：已结账
	 * 
	 * @param week
	 * @return
	 * @throws ServiceException
	 *             boolean
	 * @author Javelin
	 * @date 2017-1-5 下午5:15:41
	 */
	@RequestMapping(value = "/cwMonthKnot/doisMonthedCloseByWeek", method = RequestMethod.POST)
	public boolean doisMonthedCloseByWeek(@RequestBody String week,@RequestParam("finBooks") String finBooks) throws ServiceException;

	/**
	 * 方法描述： 获取最大结账周期
	 * 
	 * @return
	 * @throws ServiceException
	 *             String
	 * @author Javelin
	 * @date 2017-1-11 上午10:04:20
	 */
	@RequestMapping(value = "/cwMonthKnot/getMaxCloseWeek", method = RequestMethod.POST)
	public String getMaxCloseWeek(@RequestParam("finBooks") String finBooks) throws ServiceException;

	/**
	 * 方法描述： 获取最小未结账周期
	 * 
	 * @return
	 * @throws ServiceException
	 *             String
	 * @author Javelin
	 * @date 2017-1-11 上午10:05:27
	 */
	@RequestMapping(value = "/cwMonthKnot/getMinNoCloseWeek", method = RequestMethod.POST)
	public String getMinNoCloseWeek(@RequestParam("finBooks") String finBooks) throws ServiceException;
}
