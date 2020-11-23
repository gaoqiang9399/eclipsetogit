package app.component.finance.voucher.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.finance.util.R;
import app.component.finance.voucher.entity.CwReviewBusiness;
import app.component.prdct.entity.MfSysKind;
import app.util.toolkit.Ipage;

/**
 * Title: CwReviewBusinessBo.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Fri Apr 21 11:46:05 CST 2017
 **/
@FeignClient("mftcc-platform-fiscal")
public interface CwReviewBusinessFeign {

	/**
	 * 方法描述： 批量业务记账复核
	 * 
	 * @param paramMap
	 * @return
	 * @throws Exception
	 *             String
	 * @author Javelin
	 * @date 2017-5-3 下午5:24:30
	 */
	@RequestMapping(value = "/cwReviewBusiness/doBusBatchReview", method = RequestMethod.POST)
	public R doBusBatchReview(@RequestBody Map<String, String> paramMap,@RequestParam("finBooks") String finBooks) throws Exception;

	@RequestMapping(value = "/cwReviewBusiness/insert", method = RequestMethod.POST)
	public void insert(@RequestBody CwReviewBusiness cwReviewBusiness) throws ServiceException;

	@RequestMapping(value = "/cwReviewBusiness/delete", method = RequestMethod.POST)
	public void delete(@RequestBody CwReviewBusiness cwReviewBusiness,@RequestParam("finBooks") String finBooks) throws ServiceException;

	@RequestMapping(value = "/cwReviewBusiness/update", method = RequestMethod.POST)
	public void update(@RequestBody CwReviewBusiness cwReviewBusiness,@RequestParam("finBooks") String finBooks) throws ServiceException;

	@RequestMapping(value = "/cwReviewBusiness/getById", method = RequestMethod.POST)
	public CwReviewBusiness getById(@RequestBody CwReviewBusiness cwReviewBusiness) throws ServiceException;

	@RequestMapping(value = "/cwReviewBusiness/findByPage", method = RequestMethod.POST)
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("finBooks") String finBooks) throws ServiceException;

	/**
	 * 
	 * 方法描述： 插入一条复核数据
	 * 
	 * @param reviewbean
	 * @return
	 * @throws ServiceException
	 *             String
	 * @author lzshuai
	 * @date 2017-8-11 下午3:57:31
	 */
	@RequestMapping(value = "/cwReviewBusiness/insertReviewBean", method = RequestMethod.POST)
	public String insertReviewBean(@RequestBody CwReviewBusiness reviewbean) throws ServiceException;

	/**
	 * 
	 * 方法描述： 批量插入复核数据
	 * 
	 * @param reviewList
	 * @return
	 * @throws ServiceException
	 *             String
	 * @author lzshuai
	 * @date 2017-8-11 下午4:05:09
	 */
	@RequestMapping(value = "/cwReviewBusiness/insertBatchRevicwBuinessList", method = RequestMethod.POST)
	public String insertBatchRevicwBuinessList(@RequestBody List<CwReviewBusiness> reviewList) throws ServiceException;

	/**
	 * 
	 * 方法描述： 查看复核数据在业务记账规则中有没有配置
	 * 
	 * @param cwReviewBusiness
	 * @return
	 * @throws ServiceException
	 *             String
	 * @author lzshuai
	 * @date 2017-9-2 上午11:32:36
	 */
	@RequestMapping(value = "/cwReviewBusiness/doCheckHaveRules", method = RequestMethod.POST)
	public R doCheckHaveRules(@RequestBody CwReviewBusiness cwReviewBusiness,@RequestParam("finBooks") String finBooks) throws ServiceException;

	/**
	 * 
	 * 方法描述： 获取产品列表
	 * @return
	 * @throws Exception
	 * List<MfSysKind>
	 * @author lzshuai
	 * @date 2017-12-28 下午2:18:39
	 */
	@RequestMapping(value = "/cwReviewBusiness/getKindList", method = RequestMethod.POST) 
	public List<MfSysKind> getKindList() throws Exception;

}
