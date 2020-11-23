package app.component.finance.menthed.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.finance.menthed.entity.CwLedgerMst;
import app.component.finance.util.R;
import app.util.toolkit.Ipage;

/**
 * Title: CwLedgerMstBo.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Mon Jan 09 09:38:45 CST 2017
 **/
@FeignClient("mftcc-platform-fiscal")
public interface CwLedgerMstFeign {
	/**
	 * 方法描述： 获取月末结账初始数据
	 * 
	 * @return
	 * @throws ServiceException
	 *             Map<String,Object>
	 * @author Javelin
	 * @date 2017-1-11 上午10:17:06
	 */
	@RequestMapping(value = "/cwLedgerMst/getInitCwMonthedData", method = RequestMethod.POST)
	public Map<String, Object> getInitCwMonthedData(@RequestParam("finBooks") String finBooks) throws ServiceException;

	/**
	 * 方法描述： 获取结账下一期的满足条件
	 * 
	 * @return
	 * @throws ServiceException
	 *             Map<String,Object>
	 * @author Javelin
	 * @date 2017-2-24 下午3:40:36
	 */
	@RequestMapping(value = "/cwLedgerMst/getCwCloseNextFlag", method = RequestMethod.POST)
	public Map<String, Object> getCwCloseNextFlag(@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 方法描述： 生成月结凭证
	 * 
	 * @param parmMap
	 * @return
	 * @throws ServiceException
	 *             String
	 * @author Javelin
	 * @date 2017-1-12 上午11:40:57
	 */
	@RequestMapping(value = "/cwLedgerMst/doCreateMonthVch", method = RequestMethod.POST)
	public R doCreateMonthVch(@RequestBody Map<String, String> parmMap,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 方法描述： 月末结账到下期
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Javelin
	 * @date 2017-1-17 上午11:24:12
	 */
	@RequestMapping(value = "/cwLedgerMst/doCloseToNextMonth", method = RequestMethod.POST)
	public R doCloseToNextMonth(@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 方法描述： 反结账到上一期
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Javelin
	 * @date 2017-2-9 上午9:14:28
	 */
	@RequestMapping(value = "/cwLedgerMst/doReCloseToPrevMonth", method = RequestMethod.POST)
	public R doReCloseToPrevMonth(@RequestParam("finBooks") String finBooks) throws Exception;

	@RequestMapping(value = "/cwLedgerMst/insert", method = RequestMethod.POST)
	public void insert(@RequestBody CwLedgerMst cwLedgerMst) throws ServiceException;

	@RequestMapping(value = "/cwLedgerMst/delete", method = RequestMethod.POST)
	public void delete(@RequestBody CwLedgerMst cwLedgerMst,@RequestParam("finBooks") String finBooks) throws ServiceException;

	@RequestMapping(value = "/cwLedgerMst/update", method = RequestMethod.POST)
	public void update(@RequestBody CwLedgerMst cwLedgerMst,@RequestParam("finBooks") String finBooks) throws ServiceException;

	@RequestMapping(value = "/cwLedgerMst/getById", method = RequestMethod.POST)
	public CwLedgerMst getById(@RequestBody CwLedgerMst cwLedgerMst,@RequestParam("finBooks") String finBooks) throws ServiceException;

	@RequestMapping(value = "/cwLedgerMst/findByPage", method = RequestMethod.POST)
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("finBooks") String finBooks) throws ServiceException;

	@RequestMapping(value = "/cwLedgerMst/getAll", method = RequestMethod.POST)
	public List<CwLedgerMst> getAll(@RequestBody CwLedgerMst cwLedgerMst,@RequestParam("finBooks") String finBooks) throws ServiceException;

	/**
	 * 
	 * 方法描述： 获取年结数据信息
	 * 
	 * @param formMap
	 * @return
	 * @throws Exception
	 *             String
	 * @author lzshuai
	 * @date 2017-10-11 上午10:35:38
	 */
	@RequestMapping(value = "/cwLedgerMst/getYearPz", method = RequestMethod.POST)
	public R getYearPz(@RequestBody Map<String, String> formMap,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 
	 * 方法描述： 年结获取科目相关余额
	 * 
	 * @return
	 * @throws Exception
	 *             Map<String,String>
	 * @author lzshuai
	 * @date 2017-10-12 下午4:34:08
	 */
	@RequestMapping(value = "/cwLedgerMst/getYearKemuBal", method = RequestMethod.POST)
	public R getYearKemuBal(@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 
	 * 方法描述： 计算利润数据
	 * 
	 * @param wfplrBal
	 * @param fptq1
	 * @param fptq4
	 * @param fptq3
	 * @param fptq42
	 * @return
	 * @throws Exception
	 *             Map<String,String>
	 * @author lzshuai
	 * @date 2017-10-12 下午7:50:26
	 */
	@RequestMapping(value = "/cwLedgerMst/getfptqAmt", method = RequestMethod.POST)
	public R getfptqAmt(@RequestParam("wfplrBal")  String wfplrBal, @RequestParam("fptq1") String fptq1, @RequestParam("fptq4") String fptq4, @RequestParam("fptq3") String fptq3,
			@RequestParam("fptq42") String fptq42) throws Exception;

	/**
	 * 
	 * 方法描述： 声称年结利润分配凭证
	 * 
	 * @param fptq1
	 * @param fptq2
	 * @param fptq3
	 * @param fptq4
	 * @return
	 * @throws Exception
	 *             Map<String,String>
	 * @author lzshuai
	 * @date 2017-10-12 下午7:50:44
	 */
	@RequestMapping(value = "/cwLedgerMst/doCreatefptqPz", method = RequestMethod.POST)
	public R doCreatefptqPz(@RequestBody Map<String, String> tmap,@RequestParam("finBooks") String finBooks) throws Exception;

}
