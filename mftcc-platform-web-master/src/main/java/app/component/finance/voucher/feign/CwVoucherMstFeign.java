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
import app.component.finance.voucher.entity.CwCashFlowAnalysis;
import app.component.finance.voucher.entity.CwVoucherMst;
import app.util.toolkit.Ipage;
import net.sf.json.JSONObject;

/**
 * Title: CwVoucherMstBo.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Thu Dec 29 15:49:43 CST 2016
 **/
@FeignClient("mftcc-platform-fiscal")
public interface CwVoucherMstFeign {

	/**
	 * 方法描述： 删除单个凭证(@RequestBody flag为0不删除出纳和资产相关联的数据)
	 * 
	 * @param cwVoucherMst
	 * @throws ServiceException
	 *             String
	 * @author Javelin
	 * @date 2017-1-6 上午11:59:08
	 */
	@RequestMapping(value = "/cwVoucherMst/delete", method = RequestMethod.POST)
	public R delete(@RequestBody CwVoucherMst cwVoucherMst, @RequestParam("flag") String flag,@RequestParam ("finBooks") String finBooks) throws Exception;

	/**
	 * 方法描述： 获取凭证主表实体
	 * 
	 * @param cwVoucherMst
	 * @return
	 * @throws Exception
	 *             CwVoucherMst
	 * @author Javelin
	 * @date 2017-2-18 上午9:55:11
	 */
	@RequestMapping(value = "/cwVoucherMst/getById", method = RequestMethod.POST)
	public CwVoucherMst getById(@RequestBody CwVoucherMst cwVoucherMst,@RequestParam ("finBooks") String finBooks) throws Exception;

	/**
	 * 方法描述： 查询凭证查询列表数据
	 * 
	 * @param ipage
	 * @param formMap
	 * @return
	 * @throws ServiceException
	 *             Ipage
	 * @author Javelin
	 * @date 2017-1-7 下午3:30:36
	 */
	@RequestMapping(value = "/cwVoucherMst/getVoucherList", method = RequestMethod.POST)
	public Ipage getVoucherList(@RequestBody Ipage ipage, @RequestParam ("finBooks") String finBooks) throws ServiceException;

	/**
	 * 方法描述： 页面新增凭证
	 * 
	 * @param formMap
	 * @return
	 * @throws ServiceException
	 *             String
	 * @author Javelin
	 * @date 2016-12-30 上午11:02:17
	 */
	@RequestMapping(value = "/cwVoucherMst/addVoucher", method = RequestMethod.POST)
	public R addVoucher(@RequestBody Map<String, Object> formMap,@RequestParam ("finBooks") String finBooks) throws Exception;

	/**
	 * 方法描述： 获取凭证详情信息
	 * 
	 * @param voucherNo
	 *            凭证编号
	 * @param which
	 *            界面类型 add：新增，edit：可编辑， view：展示-不可编辑
	 * @return
	 * @throws Exception
	 *             Map<String,Object>
	 * @author Javelin
	 * @date 2017-2-13 下午4:34:58
	 */
	@RequestMapping(value = "/cwVoucherMst/getVoucherByNo", method = RequestMethod.POST)

	public Map<String, Object> getVoucherByNo(@RequestParam("voucherNo") String voucherNo, @RequestParam("which") String which,@RequestParam ("finBooks") String finBooks,@RequestParam("regName") String regName) throws Exception;


	/**
	 * 方法描述： 获取凭证打印数据
	 * 
	 * @param voucherNo
	 *            以 ，隔开
	 * @return
	 * @throws Exception
	 *             List<Map<String, Object>>
	 * @author Javelin
	 * @date 2017-3-14 下午4:17:15
	 */
	@RequestMapping(value = "/cwVoucherMst/getVchPrintData", method = RequestMethod.POST)

	public List<Map<String, Object>> getVchPrintData(@RequestBody String voucherNo,@RequestParam ("finBooks") String finBooks,@RequestParam("orgName") String orgName) throws Exception;


	/**
	 * 方法描述： 根据日期与凭证字获取最大凭证字号
	 * 
	 * @param formMap
	 * @return
	 * @throws Exception
	 *             Map<String,Object>
	 * @author Javelin
	 * @date 2017-2-17 下午1:43:18
	 */
	@RequestMapping(value = "/cwVoucherMst/getVchMaxNoteNo", method = RequestMethod.POST)
	public R getVchMaxNoteNo(@RequestBody Map<String, String> formMap,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 方法描述： 修改凭证信息
	 * 
	 * @param formMap
	 * @return
	 * @throws Exception
	 *             String
	 * @author Javelin
	 * @date 2017-1-6 下午2:55:58
	 */
	@RequestMapping(value = "/cwVoucherMst/updateVoucher", method = RequestMethod.POST)
	public String updateVoucher(@RequestBody Map<String, Object> formMap,@RequestParam ("finBooks") String finBooks) throws Exception;

	/**
	 * 方法描述： 凭证批量删除
	 * 
	 * @param formMap
	 * @return
	 * @throws Exception
	 *             Map<String,String>
	 * @author Javelin
	 * @date 2017-1-6 下午4:36:04
	 */
	@RequestMapping(value = "/cwVoucherMst/deleteBatch", method = RequestMethod.POST)
	public Map<String, String> deleteBatch(@RequestBody Map<String, String> formMap,@RequestParam ("finBooks") String finBooks) throws Exception;

	/**
	 * 方法描述： 凭证单个审核
	 * 
	 * @param cwVoucherMst
	 * @return
	 * @throws ServiceException
	 *             String
	 * @author Javelin
	 * @date 2017-1-6 下午4:33:44
	 */
	@RequestMapping(value = "/cwVoucherMst/doVoucherAudit", method = RequestMethod.POST)
	public String doVoucherAudit(@RequestBody CwVoucherMst cwVoucherMst,@RequestParam ("finBooks") String finBooks) throws ServiceException;

	/**
	 * 方法描述： 凭证批量审核
	 * 
	 * @param formMap
	 * @return
	 * @throws Exception
	 *             Map<String,String>
	 * @author Javelin
	 * @date 2017-1-6 下午4:35:31
	 */
	@RequestMapping(value = "/cwVoucherMst/doVoucherBatchAudit", method = RequestMethod.POST)
	public Map<String, String> doVoucherBatchAudit(@RequestBody Map<String, String> formMap,@RequestParam ("finBooks") String finBooks) throws Exception;

	/**
	 * 方法描述： 凭证单个反审核
	 * 
	 * @param cwVoucherMst
	 * @return
	 * @throws ServiceException
	 *             String
	 * @author Javelin
	 * @date 2017-1-6 下午4:33:44
	 */
	@RequestMapping(value = "/cwVoucherMst/doVoucherRevAudit", method = RequestMethod.POST)
	public R doVoucherRevAudit(@RequestBody CwVoucherMst cwVoucherMst,@RequestParam ("finBooks") String finBooks) throws Exception;

	/**
	 * 方法描述： 凭证批量反审核
	 * 
	 * @param formMap
	 * @return
	 * @throws Exception
	 *             Map<String,String>
	 * @author Javelin
	 * @date 2017-1-6 下午4:35:31
	 */
	@RequestMapping(value = "/cwVoucherMst/doVoucherBatchRevAudit", method = RequestMethod.POST)
	public R doVoucherBatchRevAudit(@RequestBody Map<String, String> formMap,@RequestParam ("finBooks") String finBooks) throws Exception;

	/**
	 * 
	 * 方法描述： 凭证整理：根据凭证周期获取凭证字号
	 * 
	 * @param parmMap
	 * @return
	 * @throws Exception
	 *             List<Map<String,String>>
	 * @author 刘争帅
	 * @date 2017-1-6 下午2:23:35
	 */
	@RequestMapping(value = "/cwVoucherMst/getNoteNoInfoByMap", method = RequestMethod.POST)
	public List<Map<String, String>> getNoteNoInfoByMap(@RequestBody Map<String, String> parmMap,@RequestParam ("finBooks") String finBooks) throws Exception;
	

	/**
	 * 
	 * 方法描述： 凭证整理：开始凭证整理
	 * 
	 * @param parmMap
	 * @return
	 * @throws Exception
	 *             List<Map<String,String>>
	 * @author 刘争帅
	 * @date 2017-1-7 下午2:14:59
	 */
	@RequestMapping(value = "/cwVoucherMst/doDealPingzheng", method = RequestMethod.POST)
	public String doDealPingzheng(@RequestBody Map<String, String> parmMap,@RequestParam ("finBooks") String finBooks) throws Exception;

	/**
	 * 
	 * 方法描述： 获取凭证字信息
	 * @return
	 * @throws Exception
	 * List<Map<String,String>>
	 * @author 刘争帅
	 * @date 2017-1-7 下午5:18:10
	 */
	@RequestMapping(value = "/cwVoucherMst/getpzzInfo", method = RequestMethod.POST) 
	public List<Map<String, String>> getpzzInfo(@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 方法描述： 获取凭证初始化所需数据 凭证字 日期 周期
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author Javelin
	 * @date 2017-1-24 上午9:29:16
	 */
	@RequestMapping(value = "/cwVoucherMst/getInitVchData", method = RequestMethod.POST) 
	public Map<String, Object> getInitVchData(@RequestParam("finBooks") String finBooks,@RequestParam("regName") String regName) throws Exception;

	/**
	 * 冲销凭证
	 * 
	 * @param voucherNo
	 * @throws Exception
	 */
	@RequestMapping(value = "/cwVoucherMst/chongXiaoPz", method = RequestMethod.POST)

	public void chongXiaoPz(@RequestBody String voucherNo,@RequestParam ("finBooks") String finBooks,@RequestParam("regNo") String regNo, @RequestParam("regName") String regName) throws Exception;


	/**
	 * 获得现金流量分析的基础数据
	 * 
	 * @param voucherNo
	 *            凭证号
	 * @return
	 */
	@RequestMapping(value = "/cwVoucherMst/getCashFlowData", method = RequestMethod.POST)
	public List<CwCashFlowAnalysis> getCashFlowData(@RequestBody String voucherNo,@RequestParam ("finBooks") String finBooks) throws Exception;

	/**
	 * 现金流量分析之后的数据保存
	 * 
	 * @param jsonObject
	 * @throws Exception
	 */
	@RequestMapping(value = "/cwVoucherMst/doSaveCashFlowAnalysis", method = RequestMethod.POST)
	public void doSaveCashFlowAnalysis(@RequestBody JSONObject jsonObject,@RequestParam ("finBooks") String finBooks) throws Exception;

	/**
	 * 方法描述： 获取不同类型凭证数据
	 * 
	 * @param paramMap
	 * @return
	 * @throws Exception
	 *             Map<String,Object>
	 * @author Javelin
	 * @date 2017-3-31 上午9:51:27
	 */
	@RequestMapping(value = "/cwVoucherMst/getAllVchDataByType", method = RequestMethod.POST)
	public R getAllVchDataByType(@RequestBody Map<String, String> paramMap,@RequestParam("finBooks") String finBooks) throws Exception;
}
