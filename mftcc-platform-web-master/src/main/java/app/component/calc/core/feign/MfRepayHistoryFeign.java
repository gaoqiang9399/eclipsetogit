package  app.component.calc.core.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.calc.core.entity.MfRepayHistory;
import app.component.calc.core.entity.MfRepayRecheck;
import app.component.pact.entity.MfBusFincApp;
import app.component.wkf.entity.Result;
import app.util.toolkit.Ipage;

/**
* Title: MfRepayHistoryBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Sun Jun 12 15:33:55 CST 2016
**/

@FeignClient("mftcc-platform-factor")
public interface MfRepayHistoryFeign {
	
	@RequestMapping(value = "/mfRepayHistory/insert")
	public Result insert(@RequestBody MfRepayHistory mfRepayHistory) throws ServiceException;
	
	@RequestMapping(value = "/mfRepayHistory/delete")
	public void delete(@RequestBody MfRepayHistory mfRepayHistory) throws ServiceException;
	
	@RequestMapping(value = "/mfRepayHistory/update")
	public void update(@RequestBody MfRepayHistory mfRepayHistory) throws ServiceException;
	/**
	 * 
	 * 方法描述： 还款复核
	 * @param mfRepayHistory
	 * @throws ServiceException
	 * void
	 * @author 沈浩兵
	 * @date 2016-8-9 下午5:48:46
	 */
	@RequestMapping(value = "/mfRepayHistory/doRepayReview")
	public Result doRepayReview(@RequestBody MfRepayHistory mfRepayHistory) throws ServiceException;
	
	@RequestMapping(value = "/mfRepayHistory/getById")
	public MfRepayHistory getById(@RequestBody MfRepayHistory mfRepayHistory) throws ServiceException;
	/**
	 * 
	 * 方法描述： 初始化 MfRepayHistory
	 * @param appId
	 * @return
	 * @throws ServiceException
	 * MfRepayHistory
	 * @author 沈浩兵
	 * @date 2016-8-8 上午10:38:23
	 */
	@RequestMapping(value = "/mfRepayHistory/setMfRepayHistoryInfo")
	public MfRepayHistory setMfRepayHistoryInfo(@RequestBody String fincId) throws ServiceException;
	/**
	 * 
	 * 方法描述： 获得还款信息list
	 * @return
	 * @throws ServiceException
	 * List<MfRepayHistory>
	 * @author 沈浩兵
	 * @date 2016-8-9 下午5:58:47
	 */
	@RequestMapping(value = "/mfRepayHistory/getList")
	public List<MfRepayHistory> getList(@RequestBody MfRepayHistory mfRepayHistory) throws ServiceException;

	@RequestMapping(value = "/mfRepayHistory/getListInvoice")
	public List<MfRepayHistory> getListInvoice(@RequestBody MfRepayHistory mfRepayHistory) throws ServiceException;
	
	@RequestMapping(value = "/mfRepayHistory/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws ServiceException;

	@RequestMapping(value = "/mfRepayHistory/findRepayHisByPage")
	public Ipage findRepayHisByPage(@RequestBody Ipage ipage) throws ServiceException;
	/**
	 * 获取今日还款列表
	 */
	@RequestMapping(value = "/mfRepayHistory/findByPageForCash")
	public Ipage findByPageForCash(@RequestBody Ipage ipage) throws ServiceException;

	
	/**
	 * 
	 * 方法描述： 还款方法（保存还款历史数据，更新收款计划状态）
	 * @param mfRepayHistory
	 * @param planList
	 * @return
	 * @throws ServiceException
	 * Result
	 * @author zhs
	 * @date 2016-8-17 下午3:27:31
	 */
	@RequestMapping(value = "/mfRepayHistory/repayment")
	public Result repayment(@RequestBody MfRepayHistory mfRepayHistory)throws ServiceException;

	@RequestMapping(value = "/mfRepayHistory/repaymentJsp")
	public Map<String, Object> repaymentJsp(@RequestBody String fincId)throws ServiceException;

	/**
	 * 
	 * 方法描述：根据借据号获取复核信息 
	 * @param mfRepayHistory
	 * @return
	 * @throws ServiceException
	 * MfRepayHistory
	 * @author zhs
	 * @date 2017-3-7 下午5:52:11
	 */
	@RequestMapping(value = "/mfRepayHistory/getReviewInfo")
	public MfRepayHistory getReviewInfo(@RequestBody MfRepayHistory mfRepayHistory)throws ServiceException;
    /**
     * 方法描述：还款时保存数据到还款历史总表
     * @param mfRepayHistory
     * @throws ServiceException
     * @author WD
     */
	@RequestMapping(value = "/mfRepayHistory/insertMfRepayHistory")
	public void insertMfRepayHistory(@RequestBody MfRepayHistory mfRepayHistory) throws Exception;
    /**
     *方法描述：查找还款的历史(目的是： 判断是否为第一次还款) 获取还款历史中还款次数
     * @param mfRepayObj
     * @return
     * @throws Exception
     */
	@RequestMapping(value = "/mfRepayHistory/getHisContById")
	public int getHisContById(@RequestBody MfRepayHistory mfRepayObj)throws Exception;
    /**
     * 判断 还款操作按钮 是否能够操作
     * @param mfBusFincApp
     * @return 0 能操作  1 不能操作
     * @throws Exception
     */
	@RequestMapping(value = "/mfRepayHistory/getRepaymentOperateFlag")
	public String getRepaymentOperateFlag(@RequestBody MfBusFincApp mfBusFincApp)throws Exception;
    /**
     * 
     * 方法描述：通过借据号和还款状态查看该笔借据的还款条数
     * @param mfRepayHistory
     * @return
     * @throws Exception 
     * String
     * @author WD
     * @date 2017-12-7 下午6:54:05
     */
	@RequestMapping(value = "/mfRepayHistory/getMfRepayHistoryListByrepayInfo")
	public int getMfRepayHistoryListByrepayInfo(@RequestBody MfRepayHistory mfRepayHistory)throws Exception;
	/**
	 * 获取还款信息列表关联借据表查出的数据
	 * @param ipage
	 * @returnmfRepayHistory
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/mfRepayHistory/getRepayRecheckList")
	public Ipage getRepayRecheckList(@RequestBody Ipage ipage) throws ServiceException;
	/**
	 * 批量打印还款凭证获取数据源
	 * @param ipage
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/mfRepayHistory/getRepayRecheckByRepayId")
	public MfRepayRecheck getRepayRecheckByRepayId(@RequestBody MfRepayRecheck mfRepayRecheck) throws ServiceException;

    /**
     * @return
     * @throws Exception
     * @desc 处理还款历史排序的老数据
     * @author zkq
     * @date 20190619
     */
    @RequestMapping(value = "/mfRepayHistory/doRepayHistoryRepaymentNumber")
    public Map<String, Object> doRepayHistoryRepaymentNumber() throws Exception;


    @RequestMapping(value = "/mfRepayHistory/getRepayHistoryListByCusNo")
    public List<MfRepayHistory> getRepayHistoryListByCusNo(@RequestBody MfRepayHistory mfRepayHistory) throws Exception;


}
