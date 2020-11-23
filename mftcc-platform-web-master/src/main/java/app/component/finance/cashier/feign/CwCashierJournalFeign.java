package app.component.finance.cashier.feign;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.finance.cashier.entity.CwCashierJournal;
import app.util.toolkit.Ipage;

/**
 * Title: CwCashierJournalBo.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Mon Mar 27 16:43:21 CST 2017
 **/

@FeignClient("mftcc-platform-fiscal")
public interface CwCashierJournalFeign {

	/**
	 * 方法描述：获取出纳列表数据
	 * 
	 * @param ipage
	 * @param paramMap
	 * @return
	 * @throws ServiceException
	 *             Ipage
	 * @author Javelin
	 * @date 2017-3-29 下午2:02:31
	 */
	@RequestMapping(value = "/cwCashierJournal/getCashierJournalPage", method = RequestMethod.POST)
	public Ipage getCashierJournalPage(@RequestBody Ipage ipage, @RequestParam("finBooks") String finBooks) throws ServiceException;

	/**
	 * 方法描述： 获取出纳核对总账数据
	 * 
	 * @param ipage
	 * @param paramMap
	 * @return
	 * @throws ServiceException
	 *             Ipage
	 * @author Javelin
	 * @date 2017-4-1 下午2:14:58
	 */
	@RequestMapping(value = "/cwCashierJournal/getCashierReportData", method = RequestMethod.POST)
	public Ipage getCashierReportData(@RequestBody Ipage ipage, @RequestParam("finBooks") String finBooks) throws ServiceException;

	@RequestMapping(value = "/cwCashierJournal/insert", method = RequestMethod.POST)
	public void insert(@RequestBody CwCashierJournal cwCashierJournal, @RequestParam("finBooks") String finBooks) throws ServiceException;

	@RequestMapping(value = "/cwCashierJournal/delete", method = RequestMethod.POST)
	public void delete(@RequestBody CwCashierJournal cwCashierJournal, @RequestParam("finBooks") String finBooks) throws ServiceException;

	@RequestMapping(value = "/cwCashierJournal/update", method = RequestMethod.POST)
	public void update(@RequestBody CwCashierJournal cwCashierJournal, @RequestParam("finBooks") String finBooks) throws ServiceException;

	@RequestMapping(value = "/cwCashierJournal/getById", method = RequestMethod.POST)
	public CwCashierJournal getById(@RequestBody CwCashierJournal cwCashierJournal, @RequestParam("finBooks") String finBooks) throws ServiceException;

	@RequestMapping(value = "/cwCashierJournal/findByPage", method = RequestMethod.POST)
	public Ipage findByPage(@RequestBody Ipage ipage, @RequestParam("finBooks") String finBooks) throws ServiceException;

}
