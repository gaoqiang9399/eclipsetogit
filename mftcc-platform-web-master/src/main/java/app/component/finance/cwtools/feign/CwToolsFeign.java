/**
 * Copyright (@RequestBody C) DXHM 版权所有
 * 文件名： CwToolsBo.java
 * 包名： app.component.finance.cwtools.bo
 * 说明：
 * @author Javelin
 * @date 2017-1-3 下午2:47:30
 * @version V1.0
 */
package app.component.finance.cwtools.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.finance.account.entity.CwAssistData;
import app.component.finance.account.entity.CwComItem;
import app.component.finance.account.entity.CwFication;
import app.component.finance.account.entity.CwFicationData;
import app.component.finance.account.entity.CwRelation;
import app.component.finance.assets.entity.CwAssets;
import app.component.finance.assets.entity.CwAssetsClass;
import app.component.finance.assets.entity.CwAssetsHst;
import app.component.finance.cashier.entity.CwCashierAccount;
import app.component.finance.cashier.entity.CwCashierJournal;
import app.component.finance.cwtools.entity.CwMonthKnot;
import app.component.finance.finreport.entity.CwReportItem;
import app.component.finance.menthed.entity.CwLedgerHst;
import app.component.finance.menthed.entity.CwLedgerMst;
import app.component.finance.paramset.entity.CwCycle;
import app.component.finance.paramset.entity.CwCycleHst;
import app.component.finance.paramset.entity.CwInitBal;
import app.component.finance.paramset.entity.CwJiti;
import app.component.finance.paramset.entity.CwJitiItem;
import app.component.finance.paramset.entity.CwPriceTaxSep;
import app.component.finance.paramset.entity.CwPriceTaxSepItems;
import app.component.finance.paramset.entity.CwPrintTmpl;
import app.component.finance.paramset.entity.CwProofWords;
import app.component.finance.paramset.entity.CwSysParam;
import app.component.finance.paramset.entity.CwVchRuleDetail;
import app.component.finance.paramset.entity.CwVchRuleDetailPlate;
import app.component.finance.paramset.entity.CwVchRuleMst;
import app.component.finance.paramset.entity.CwVchRuleMstPlate;
import app.component.finance.vchout.entity.CwVchOutSoftver;
import app.component.finance.vchout.entity.ExportVchBean;
import app.component.finance.voucher.entity.CwReviewBusiness;
import app.component.finance.voucher.entity.CwVoucherAssist;
import app.component.finance.voucher.entity.CwVoucherDetial;
import app.component.finance.voucher.entity.CwVoucherMst;
import app.component.finance.voucher.entity.CwVoucherRemarks;

/**
 * 类名： CwToolsBo 描述：
 * 
 * @author Javelin
 * @date 2017-1-3 下午2:47:30
 *
 *
 */
@FeignClient("mftcc-platform-fiscal")
public interface CwToolsFeign {

	/**
	 * 方法描述： 根据科目编码 查找科目名称
	 * 
	 * @param accno
	 * @return
	 * @throws Exception
	 *             String
	 * @author Javelin
	 * @date 2017-1-4 上午10:19:09
	 */
	@RequestMapping(value = "/cwTools/getAccNameByAccNo", method = RequestMethod.POST)
	public String getAccNameByAccNo(@RequestParam("accno") String accno,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 方法描述： 根据科目号取得科目控制字
	 * 
	 * @param accno
	 * @return
	 * @throws Exception
	 *             String
	 * @author Javelin
	 * @date 2017-1-4 上午10:19:19
	 */
	@RequestMapping(value = "/cwTools/getAccHrtByAccNo", method = RequestMethod.POST)
	public String getAccHrtByAccNo(@RequestBody String accno,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 方法描述： 根据科目控制字 查找科目名称
	 * 
	 * @param acchrt
	 * @return
	 * @throws Exception
	 *             String
	 * @author Javelin
	 * @date 2017-1-4 上午10:19:21
	 */
	@RequestMapping(value = "/cwTools/getAccNameByAccHrt", method = RequestMethod.POST)
	public String getAccNameByAccHrt(@RequestBody String acchrt,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 方法描述： 根据科目控制字取得科目号
	 * 
	 * @param acchrt
	 * @return
	 * @throws Exception
	 *             String
	 * @author Javelin
	 * @date 2017-1-4 上午10:19:23
	 */
	@RequestMapping(value = "/cwTools/getAccNoByAccHrt", method = RequestMethod.POST)
	public String getAccNoByAccHrt(@RequestBody String acchrt,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 方法描述： 根据科目号获取科目显示名称
	 * 
	 * @param accno
	 * @return
	 * @throws Exception
	 *             String
	 * @author Javelin
	 * @date 2017-1-4 上午10:19:25
	 */
	@RequestMapping(value = "/cwTools/getShowNameByAccNo", method = RequestMethod.POST)
	public String getShowNameByAccNo(@RequestBody String accno,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 方法描述： 根据科目控制字获取科目显示名称
	 * 
	 * @param acchrt
	 * @return
	 * @throws Exception
	 *             String
	 * @author Javelin
	 * @date 2017-1-4 上午10:19:26
	 */
	@RequestMapping(value = "/cwTools/getShowNameByAccHrt", method = RequestMethod.POST)
	public String getShowNameByAccHrt(@RequestBody String acchrt,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 方法描述： 获取科目信息对象，通过uid查询
	 * 
	 * @param cwComItem
	 * @return
	 * @throws Exception
	 *             CwComItem
	 * @author Javelin
	 * @date 2017-1-4 下午5:01:32
	 */
	@RequestMapping(value = "/cwTools/getCwComItemBean", method = RequestMethod.POST)
	public CwComItem getCwComItemBean(@RequestBody CwComItem cwComItem,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 方法描述： 获取科目信息列表
	 * 
	 * @param cwComItem
	 * @return
	 * @throws Exception
	 *             List<CwComItem>
	 * @author Javelin
	 * @date 2017-1-4 下午6:26:17
	 */
	@RequestMapping(value = "/cwTools/getCwComItemList", method = RequestMethod.POST)
	public List<CwComItem> getCwComItemList(@RequestBody CwComItem cwComItem,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 方法描述： 获取凭证字对象
	 * 
	 * @param cwProofWords
	 * @return
	 * @throws Exception
	 *             CwProofWords
	 * @author Javelin
	 * @date 2017-1-4 下午2:45:26
	 */
	@RequestMapping(value = "/cwTools/getCwProofWordBean", method = RequestMethod.POST)
	public CwProofWords getCwProofWordBean(@RequestBody CwProofWords cwProofWords,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 方法描述： 获取凭证字列表
	 * 
	 * @param cwProofWords
	 * @return
	 * @throws Exception
	 *             List<CwProofWords>
	 * @author Javelin
	 * @date 2017-1-4 下午2:46:13
	 */
	@RequestMapping(value = "/cwTools/getCwProofWordsList", method = RequestMethod.POST)
	public List<CwProofWords> getCwProofWordsList(@RequestBody CwProofWords cwProofWords,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 方法描述： 根据编号获取系统参数实体信息
	 * 
	 * @param pcode
	 * @return
	 * @throws Exception
	 *             CwSysParam
	 * @author Javelin
	 * @date 2017-1-11 下午2:09:18
	 */
	@RequestMapping(value = "/cwTools/getCwSysParamBeanByCode", method = RequestMethod.POST)
	public CwSysParam getCwSysParamBeanByCode(@RequestBody String pcode,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 方法描述： 获取系统参数实体信息
	 * 
	 * @param cwSysParam
	 * @return
	 * @throws Exception
	 *             CwSysParam
	 * @author Javelin
	 * @date 2017-1-4 下午6:09:36
	 */
	@RequestMapping(value = "/cwTools/getCwSysParamBean", method = RequestMethod.POST)
	public CwSysParam getCwSysParamBean(@RequestBody CwSysParam cwSysParam,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 方法描述： 修改系统参数信息
	 * 
	 * @param cwSysParam
	 * @return
	 * @throws Exception
	 *             String
	 * @author Javelin
	 * @date 2017-1-4 下午6:30:31
	 */
	@RequestMapping(value = "/cwTools/updateCwSysParam", method = RequestMethod.POST)
	public String updateCwSysParam(@RequestBody CwSysParam cwSysParam,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 方法描述： 根据编号修改系统参数
	 * 
	 * @param pcode
	 * @param pvalue
	 * @return
	 * @throws Exception
	 *             String
	 * @author Javelin
	 * @date 2017-3-9 下午4:49:37
	 */
	@RequestMapping(value = "/cwTools/updateCwSysParamByCode", method = RequestMethod.POST)
	public String updateCwSysParamByCode(@RequestBody String pcode, @RequestParam("pvalue")String pvalue,@RequestParam ("finBooks") String finBooks) throws Exception;

	/**
	 * 方法描述： 获取科目缓存数据
	 * 
	 * @return
	 * @throws Exception
	 *             List<CwComItem>
	 * @author Javelin
	 * @date 2017-2-10 下午4:31:39
	 */
	@RequestMapping(value = "/cwTools/getCwComItemCache", method = RequestMethod.POST)
	public List<CwComItem> getCwComItemCache(@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 方法描述： 获取科目缓存数据
	 * 
	 * @param paramMap
	 * @return
	 * @throws Exception
	 *             CwComItem
	 * @author Javelin
	 * @date 2017-3-1 上午11:53:45
	 */
	@RequestMapping(value = "/cwTools/getCwComItemCacheBean", method = RequestMethod.POST)
	public CwComItem getCwComItemCache(@RequestBody Map<String, String> paramMap,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 方法描述： 清理科目缓存数据
	 * 
	 * @throws Exception
	 *             void
	 * @author Javelin
	 * @date 2017-3-2 下午1:48:05
	 */
	@RequestMapping(value = "/cwTools/removeCwComItemCache", method = RequestMethod.POST)
	public void removeCwComItemCache(@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 方法描述： 根据属性条件查询出科目集合
	 * 
	 * @param CwComItem
	 * @return
	 * @throws Exception
	 *             List<CwComItem>
	 * @author yht
	 * @date 2017-1-5上午10:30:31
	 */
	@RequestMapping(value = "/cwTools/getCwComListByProperty", method = RequestMethod.POST)
	public List<CwComItem> getCwComListByProperty(@RequestBody CwComItem cwComItem,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 方法描述： 根据属性条件查询出科目集合
	 * 
	 * @param paramMap
	 * @return
	 * @throws Exception
	 *             List<CwComItem>
	 * @author Javelin
	 * @date 2017-2-22 下午3:10:14
	 */
	@RequestMapping(value = "/cwTools/getCwComItemListByMap", method = RequestMethod.POST)
	public List<CwComItem> getCwComItemListByMap(@RequestBody Map<String, String> paramMap,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 方法描述：根据日期 查询会计期间明细数据
	 * 
	 * @param txdate
	 * @return
	 * @throws Exception
	 *             CwCycleHst
	 * @author Javelin
	 * @date 2017-1-5 上午10:31:29
	 */
	@RequestMapping(value = "/cwTools/getCwCycleHstByDate", method = RequestMethod.POST)
	public CwCycleHst getCwCycleHstByDate(@RequestBody String txdate,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 方法描述：根据年，期数 查询会计期间明细数据
	 * 
	 * @param year
	 * @param num
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/cwTools/getCwCycleHstByYearNum", method = RequestMethod.POST)
	public CwCycleHst getCwCycleHstByYearNum(@RequestBody String year, @RequestParam("num")String num,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 方法描述：获取科目列表存放在map中
	 * 
	 * @param cwComItem
	 * @return
	 * @throws Exception
	 *             List<Map<String, String>>
	 */
	@RequestMapping(value = "/cwTools/doselectCwComItemToMap", method = RequestMethod.POST)
	public List<Map<String, String>> doselectCwComItemToMap(@RequestBody CwComItem cwComItem,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 描述:获取区间列表
	 * 
	 * @param cwCycleHst
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/cwTools/getInterval", method = RequestMethod.POST)
	public List<CwCycleHst> getInterval(@RequestBody CwCycleHst cwCycleHst,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 方法描述： 获取凭证主表数据列表数据
	 * 
	 * @param cwVoucherMst
	 * @return
	 * @throws Exception
	 *             List<CwVoucherMst>
	 * @author Javelin
	 * @date 2017-1-11 上午11:48:26
	 */
	@RequestMapping(value = "/cwTools/getCwVoucherMstList", method = RequestMethod.POST)
	public List<CwVoucherMst> getCwVoucherMstList(@RequestBody CwVoucherMst cwVoucherMst,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 方法描述： 判断凭证是否存在断号
	 * 
	 * @param parmMap
	 *            weeks 必传，pzProofNo 非必传
	 * @return
	 * @throws Exception
	 *             boolean
	 * @author Javelin
	 * @date 2017-1-11 下午2:51:19
	 */
	@RequestMapping(value = "/cwTools/doisExistBrokenVch", method = RequestMethod.POST)
	public boolean doisExistBrokenVch(@RequestBody Map<String, String> parmMap,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 方法描述： 删除凭证
	 * 
	 * @param cwVoucherMst
	 * @return
	 * @throws Exception
	 *             String
	 * @author Javelin
	 * @date 2017-1-16 下午4:56:53
	 */
	@RequestMapping(value = "/cwTools/delCwVoucher", method = RequestMethod.POST)
	public String delCwVoucher(@RequestBody CwVoucherMst cwVoucherMst,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 方法描述： 针对月末结账的凭证录入
	 * 
	 * @param parmList
	 * @param parmMap
	 * @return
	 * @throws Exception
	 *             String
	 * @author Javelin
	 * @date 2017-1-16 下午5:19:04
	 */
	@RequestMapping(value = "/cwTools/insertVchForMonthed", method = RequestMethod.POST)
	public String insertVchForMonthed(@RequestBody List<List<String>> parmList, @RequestParam("parmMap") Map<String, String> parmMap,@RequestParam("finBooks") String finBooks)
			throws Exception;

	/**
	 * 方法描述：获取总账历史表里的数据
	 * 
	 * @param parmMap
	 *            startDate/endDate
	 */
	@RequestMapping(value = "/cwTools/getLedgetHstData", method = RequestMethod.POST)
	public List<Map<String, String>> getLedgetHstData(@RequestBody Map<String, String> paramp,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 获取总账历史表里的最大或最小会计周期
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/cwTools/getLedgerHstMinMaxWeek", method = RequestMethod.POST)
	public Map<String, String> getLedgerHstMinMaxWeek(@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 条件查询获取凭证分录表的总发生额
	 * 
	 * @param condmap
	 *            startWeek:必需，endWeek必需， includeNotJz:勾选包含未记账凭证，includeWrong:包含错误凭证
	 * @return Map:accHrt,upAccHrt,mdrAmt,mcrAmt,accLvl
	 * @throws Exception
	 */
	@RequestMapping(value = "/cwTools/getVoucherAmtList", method = RequestMethod.POST)
	public List<Map<String, String>> getVoucherAmtList(@RequestBody Map<String, String> condmap,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 获取一级科目下的一段日期内的凭证的发生额
	 * 
	 * @param vouConMp
	 *            {startDate：开始日期，endDate：结束日期（不包括），accNo：一级科目号}
	 * @return {mdrAmt:借，mcrAmt：贷}
	 * @throws Exception
	 */
	@RequestMapping(value = "/cwTools/getMAmtByDateAccNo", method = RequestMethod.POST)
	public Map<String, String> getMAmtByDateAccNo(@RequestBody Map<String, String> vouConMp,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 查询某天某一级科目下所有明细科目的发生额，通过凭证编号分组求和
	 * 
	 * @param gaConMp
	 *            {accNo:一级科目，date:查询日期}
	 * @return {voucherNo:凭证编号，voucherDate：日期，pwNo：凭证字号，remark：摘要，dcInd：借或贷，drAmt:借方发生额，crAmt:贷方发生额}
	 * @throws Exception
	 */
	@RequestMapping(value = "/cwTools/getAmtGroupByVoucherNo", method = RequestMethod.POST)
	public List<Map<String, String>> getAmtGroupByVoucherNo(@RequestBody Map<String, String> gaConMp,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 资金日记账获取对方科目
	 * 
	 * @param
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/cwTools/getOtherComitemByDate", method = RequestMethod.POST)
	public Map<String, String> getOtherComitemByDate(@RequestBody Map<String, String> otherConMp,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 
	 * 方法描述： 获取财务总账bean
	 * 
	 * @return
	 * @throws Exception
	 *             List<CwLedgerMst>
	 * @author 刘争帅
	 * @date 2017-1-18 上午11:31:32
	 */
	@RequestMapping(value = "/cwTools/getCwLedgerMstBybean", method = RequestMethod.POST)
	public List<CwLedgerMst> getCwLedgerMstBybean(@RequestBody CwLedgerMst cwLedgerMst,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 
	 * 方法描述： 科目添加批量添加CwledgerMst
	 * 
	 * @param listcwledgerMst
	 * @throws Exception
	 *             void
	 * @author 刘争帅
	 * @date 2017-1-18 下午2:44:36
	 */
	@RequestMapping(value = "/cwTools/dobatchInsertCwledgerMst", method = RequestMethod.POST)
	public void dobatchInsertCwledgerMst(@RequestBody List<CwLedgerMst> listcwledgerMst) throws Exception;

	/**
	 * 
	 * 方法描述： 科目新增：获取cw_ledger_hst （财务总账历史表）bean
	 * 
	 * @param cwLedgerHst
	 * @return
	 * @throws Exception
	 *             List<CwLedgerHst>
	 * @author 刘争帅
	 * @date 2017-1-18 下午3:08:12
	 */
	@RequestMapping(value = "/cwTools/getcwLedgerHstBybean", method = RequestMethod.POST)
	public List<CwLedgerHst> getcwLedgerHstBybean(@RequestBody CwLedgerHst cwLedgerHst,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 
	 * 方法描述： 科目新增，批量添加CwLedgerHst
	 * 
	 * @param listCwLedgerHst
	 * @throws Exception
	 *             void
	 * @author 刘争帅
	 * @date 2017-1-18 下午3:23:19
	 */
	@RequestMapping(value = "/cwTools/insertCwLedgerHstlist", method = RequestMethod.POST)
	public void insertCwLedgerHstlist(@RequestBody List<CwLedgerHst> listCwLedgerHst) throws Exception;

	/**
	 * 
	 * 方法描述： 修改科目控制字
	 * 
	 * @param temMap
	 * @throws Exception
	 *             void
	 * @author 刘争帅
	 * @date 2017-1-18 下午4:19:27
	 */
	@RequestMapping(value = "/cwTools/updateAccHrtbyHrt", method = RequestMethod.POST)
	public void updateAccHrtbyHrt(@RequestBody Map<String, String> temMap,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 获取资金日报表数据
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping(value = "/cwTools/getCapDayReportDate", method = RequestMethod.POST)
	public List<Map<String, Object>> getCapDayReportDate(@RequestBody Map<String, String> parmMap,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 方法描述： 获取周期内最大凭证字号 + 1
	 * 
	 * @param weeks
	 * @param pzProofNo
	 *            凭证字， 可以为空
	 * @return
	 * @throws Exception
	 *             String
	 * @author Javelin
	 * @date 2017-1-24 上午9:46:44
	 */
	@RequestMapping(value = "/cwTools/getMaxVchNoteNoByWeek", method = RequestMethod.POST)
	public String getMaxVchNoteNoByWeek(@RequestBody String weeks, @RequestParam("pzProofNo") String pzProofNo,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 获得初始化余额表的列表数据
	 * 
	 * @param cwInitBal
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/cwTools/getInitBalList", method = RequestMethod.POST)
	public List<CwInitBal> getInitBalList(@RequestBody CwInitBal cwInitBal,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 
	 * 方法描述： 根据条件获取科目CwComItem（bean）对象，通过对象中的任意属性查询
	 * 
	 * @param cwComItem
	 * @return
	 * @throws Exception
	 *             CwComItem
	 * @author 刘争帅
	 * @date 2017-1-25 上午10:05:05
	 */
	@RequestMapping(value = "/cwTools/getCwComItemBeanByBean", method = RequestMethod.POST)
	public CwComItem getCwComItemBeanByBean(@RequestBody CwComItem cwComItem,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 
	 * 方法描述： 保存科目与辅助核算的关系
	 * 
	 * @param cwRelation
	 * @return
	 * @throws Exception
	 *             String
	 * @author 刘争帅
	 * @date 2017-2-3 下午2:01:25
	 */
	@RequestMapping(value = "/cwTools/insertCwRelationBean", method = RequestMethod.POST)
	public String insertCwRelationBean(@RequestBody CwRelation cwRelation,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 
	 * 方法描述： 根据条件获取科目与辅助核算关系表的数据
	 * 
	 * @param cwRelation
	 * @return
	 * @throws Exception
	 *             List<CwRelation>
	 * @author 刘争帅
	 * @date 2017-2-3 下午3:34:59
	 */
	@RequestMapping(value = "/cwTools/getCwRelationData", method = RequestMethod.POST)
	public List<CwRelation> getCwRelationData(@RequestBody CwRelation cwRelation,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 方法描述： 查询所有辅助核算数据
	 * 
	 * @param cwRelation
	 * @return
	 * @throws Exception
	 *             List<CwRelation>
	 * @author Javelin
	 * @date 2017-3-16 下午5:57:17
	 */
	@RequestMapping(value = "/cwTools/getCwRelationList", method = RequestMethod.POST)
	public List<CwRelation> getCwRelationList(@RequestBody CwRelation cwRelation,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 方法描述： 获取辅助核算列表数据
	 * 
	 * @param cwFication
	 * @return
	 * @throws Exception
	 *             List<CwFication>
	 * @author Javelin
	 * @date 2017-2-10 下午2:39:16
	 */
	@RequestMapping(value = "/cwTools/getCwFicationList", method = RequestMethod.POST)
	public List<CwFication> getCwFicationList(@RequestBody CwFication cwFication,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 
	 * 方法描述： 根据条件获取辅助核算项
	 * 
	 * @param cwFication
	 * @return
	 * @throws Exception
	 *             CwFication
	 * @author 刘争帅
	 * @date 2017-2-3 下午4:16:09
	 */
	@RequestMapping(value = "/cwTools/getCwFicationBean", method = RequestMethod.POST)
	public CwFication getCwFicationBean(@RequestBody CwFication cwFication,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 
	 * 方法描述：科目检查： 获取凭证信息
	 * 
	 * @param parmMap
	 * @return
	 * @throws Exception
	 *             List<Map<String,String>>
	 * @author 刘争帅
	 * @date 2017-2-4 下午4:12:45
	 */
	@RequestMapping(value = "/cwTools/getPzInfo", method = RequestMethod.POST)
	public List<Map<String, String>> getPzInfo(@RequestBody Map<String, String> parmMap,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 方法描述： 根据科目号 获取指定时间内 借贷发生额汇总
	 * 
	 * @param parmMap
	 * @return
	 * @throws Exception
	 *             Map<String,String>
	 * @author Javelin
	 * @date 2017-2-3 下午4:32:23
	 */
	@RequestMapping(value = "/cwTools/getVchSumAmtByAccNo", method = RequestMethod.POST)
	public Map<String, Object> getVchSumAmtByAccNo(@RequestBody Map<String, String> parmMap,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 方法描述： 获取凭证中的现金流量金额
	 * 
	 * @param reportItemId
	 * @param staWeek
	 * @param endWeek
	 * @return
	 * @throws Exception
	 *             String
	 * @author Javelin
	 * @date 2017-2-4 上午10:11:09
	 */
	@RequestMapping(value = "/cwTools/getCashFlowAmtByVch", method = RequestMethod.POST)
	public String getCashFlowAmtByVch(@RequestBody String reportItemId,  @RequestParam("staWeek")  String staWeek,  @RequestParam("endWeek")  String endWeek,
			@RequestParam("finBooks") String finBooks)
			throws Exception;

	/**
	 * 方法描述： 获取总账历史表中的现金流量金额
	 * 
	 * @param weeks
	 * @param state
	 *            0:期初，1:期末
	 * @return
	 * @throws Exception
	 *             String
	 * @author Javelin
	 * @date 2017-2-4 上午10:11:36
	 */
	@RequestMapping(value = "/cwTools/getCashFlowAmtByHst", method = RequestMethod.POST)
	public String getCashFlowAmtByHst(@RequestBody String weeks,  @RequestParam("state") String state,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 
	 * 方法描述： 根据日期获取查询会计周期数据
	 * 
	 * @param txDate
	 * @return
	 * @throws Exception
	 *             Map<String,String>
	 * @author 刘争帅
	 * @date 2017-2-9 上午9:49:27
	 */
	@RequestMapping(value = "/cwTools/getCwcycleHstDateByTxdate", method = RequestMethod.POST)
	public List<Map<String, String>> getCwcycleHstDateByTxdate(@RequestBody String txDate,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 
	 * 方法描述： 根据辅助项查询该辅助项的上期期末余额
	 * 
	 * @param tempmap
	 * @return
	 * @throws Exception
	 *             String
	 * @author 刘争帅
	 * @date 2017-2-9 上午11:41:32
	 */
	@RequestMapping(value = "/cwTools/getYesterByItem", method = RequestMethod.POST)
	public String getYesterByItem(@RequestBody Map<String, String> tempmap,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 方法描述： 清理指定周期报表数据
	 * 
	 * @param weeks
	 * @return
	 * @throws Exception
	 *             String
	 * @author Javelin
	 * @date 2017-2-9 上午10:49:26
	 */
	@RequestMapping(value = "/cwTools/doclearReportData", method = RequestMethod.POST)
	public String doclearReportData(@RequestBody String weeks,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 方法描述： 获取摘要列表
	 * 
	 * @param cwVoucherRemarks
	 * @return
	 * @throws Exception
	 *             List<CwVoucherRemarks>
	 * @author Javelin
	 * @date 2017-2-14 下午3:15:57
	 */
	@RequestMapping(value = "/cwTools/getCwVoucherRemarksList", method = RequestMethod.POST)
	public List<CwVoucherRemarks> getCwVoucherRemarksList(@RequestBody CwVoucherRemarks cwVoucherRemarks,@RequestParam("finBooks") String finBooks)
			throws Exception;

	/**
	 * 
	 * 方法描述： 根据辅助项查询该辅助项的本年累计
	 * 
	 * @param tempmaps
	 * @return
	 * @throws Exception
	 *             List<Map<String,String>>
	 * @author lzshuai
	 * @date 2017-2-9 下午3:04:17
	 */
	@RequestMapping(value = "/cwTools/getCumBalanceByItem", method = RequestMethod.POST)
	public List<Map<String, String>> getCumBalanceByItem(@RequestBody Map<String, String> tempmap,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 
	 * 方法描述： 获取对方科目，获得会计期间内所有凭证
	 * 
	 * @param tparmMap
	 * @return
	 * @throws Exception
	 *             Map<String,String>
	 * @author lzshuai
	 * @date 2017-2-9 下午6:03:30
	 */
	@RequestMapping(value = "/cwTools/getOtherAccNo", method = RequestMethod.POST)
	public List<Map<String, String>> getOtherAccNo(@RequestBody Map<String, String> tparmMap,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 
	 * 方法描述： 获取昨日余额
	 * 
	 * @param ymMap
	 * @return
	 * @throws Exception
	 *             String
	 * @author lzshuai
	 * @date 2017-2-10 上午11:52:56
	 */
	@RequestMapping(value = "/cwTools/getYesterAmt", method = RequestMethod.POST)
	public String getYesterAmt(@RequestBody Map<String, String> ymMap,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 
	 * 方法描述： 获得所以科目的初始化余额
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 *             List<Map<String,String>>
	 * @author lzshuai
	 * @date 2017-2-10 下午4:07:54
	 */
	@RequestMapping(value = "/cwTools/getAllBalance", method = RequestMethod.POST)
	public Map<String, String> getAllBalance(@RequestBody Map<String, String> map,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 
	 * 方法描述： 获得上期期末余额以及本年累计金额
	 * 
	 * @param balMap
	 * @return
	 * @throws Exception
	 *             List<Map<String,String>>
	 * @author lzshuai
	 * @date 2017-2-10 下午5:10:53
	 */
	@RequestMapping(value = "/cwTools/getCumBalance", method = RequestMethod.POST)
	public List<Map<String, String>> getCumBalance(@RequestBody Map<String, String> balMap,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 获取报表项实体
	 * 
	 * @param ci
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/cwTools/getCwReportItem", method = RequestMethod.POST)
	public CwReportItem getCwReportItem(@RequestBody CwReportItem ci,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 
	 * 方法描述： 获取总账历史表的最小日期
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author lzshuai
	 * @date 2017-2-14 下午4:28:56
	 */
	@RequestMapping(value = "/cwTools/getminDateFromCwLedgerhst", method = RequestMethod.POST)
	public String getminDateFromCwLedgerhst(@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 
	 * 方法描述： 获得初始化辅助项余额
	 * 
	 * @param string
	 * @return
	 * @throws Exception
	 *             List<CwInitBal>
	 * @author lzshuai
	 * @date 2017-2-14 下午5:16:34
	 */
	@RequestMapping(value = "/cwTools/getInitPactBalance", method = RequestMethod.POST)
	public List<CwInitBal> getInitPactBalance(@RequestBody String string,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 方法描述：判断凭证的现金流量分析状态
	 * 
	 * @param cdList
	 *            凭证分录list
	 * @param cvm
	 *            凭证主表bean
	 * @throws Exception
	 */
	@RequestMapping(value = "/cwTools/doSelePzCashSts", method = RequestMethod.POST)
	public void doSelePzCashSts(@RequestBody List<CwVoucherDetial> cdList, @RequestParam("cvm") CwVoucherMst cvm,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 方法描述： 获取指定条件凭证数量
	 * 
	 * @param parmMap
	 * @return
	 * @throws Exception
	 *             String
	 * @author Javelin
	 * @date 2017-2-18 下午2:38:19
	 */
	@RequestMapping(value = "/cwTools/getVchCount", method = RequestMethod.POST)
	public String getVchCount(@RequestBody Map<String, String> parmMap,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 方法描述： 修改科目数据的 编码长度替换
	 * 
	 * @param accType
	 * @return
	 * @throws Exception
	 *             String
	 * @author Javelin
	 * @date 2017-2-20 下午3:53:03
	 */
	@RequestMapping(value = "/cwTools/doupComItemAccNoLength", method = RequestMethod.POST)
	public String doupComItemAccNoLength(@RequestBody String accType,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 方法描述： 获取所有期初余额
	 * 
	 * @param parmMap
	 *            weeks 周期（必填） accHrt 科目控制字
	 * @return
	 * @throws Exception
	 *             Map<String,String> key：accHrt
	 * @author Javelin
	 * @date 2017-2-22 下午5:10:38
	 */
	@RequestMapping(value = "/cwTools/getInitialBal", method = RequestMethod.POST)
	public Map<String, String> getInitialBal(@RequestBody Map<String, String> parmMap,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 方法描述：获取总账历史总所有发生额， 本年累计
	 * 
	 * @param parmMap
	 *            opnWeek 开始周期（必填） endWeek 结束周期 accHrt 科目控制字
	 * @return
	 * @throws Exception
	 *             Map<String,String> key：Mdr/Mcr/Ydr/Ycr + accHrt + weeks
	 * @author Javelin
	 * @date 2017-2-22 下午5:14:54
	 */
	@RequestMapping(value = "/cwTools/getCwLedgerHstAmt", method = RequestMethod.POST)
	public Map<String, String> getCwLedgerHstAmt(@RequestBody Map<String, String> parmMap,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 方法描述： 获取指定周期内的发生额
	 * 
	 * @param parmMap
	 *            opnWeek 开始周期（必填） endWeek 结束周期 accHrt 科目控制字 accNo 科目号（所有下级）
	 * @return
	 * @throws Exception
	 *             Map<String,String> key：Mdr/Mcr + accHrt + weeks
	 * @author Javelin
	 * @date 2017-2-23 上午9:02:38
	 */
	@RequestMapping(value = "/cwTools/getVchDetailWeekAmt", method = RequestMethod.POST)
	public Map<String, String> getVchDetailWeekAmt(@RequestBody Map<String, String> parmMap,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 方法描述： 获取指定周期前的本年累计
	 * 
	 * @param parmMap
	 *            weeks 指定周期 accHrt 科目控制字（所有下级） accNo 科目号（所有下级）
	 * @return
	 * @throws Exception
	 *             Map<String,String>
	 * @author Javelin
	 * @date 2017-3-23 上午10:30:38
	 */
	@RequestMapping(value = "/cwTools/getVchDetailYearAmt", method = RequestMethod.POST)
	public Map<String, String> getVchDetailYearAmt(@RequestBody Map<String, String> parmMap,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 
	 * 方法描述： 修改科目表信息
	 * 
	 * @param comItem
	 * @throws Exception
	 *             void
	 * @author lzshuai
	 * @date 2017-3-2 下午3:55:24
	 */
	@RequestMapping(value = "/cwTools/updateCwcomItem", method = RequestMethod.POST)
	public void updateCwcomItem(@RequestBody CwComItem comItem,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 
	 * 方法描述： 删除科目辅助核算关系
	 * 
	 * @param cwRelation
	 * @throws Exception
	 *             void
	 * @author lzshuai
	 * @date 2017-3-3 下午6:55:48
	 */
	@RequestMapping(value = "/cwTools/deleteCwRelationBean", method = RequestMethod.POST)
	public void deleteCwRelationBean(@RequestBody CwRelation cwRelation,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 方法描述： 获取辅助核算列表数据
	 * 
	 * @param formMap
	 * @return
	 * @throws Exception
	 *             List<CwAssistData>
	 * @author Javelin
	 * @date 2017-3-3 上午11:06:01
	 */
	@RequestMapping(value = "/cwTools/getAssistAccountData", method = RequestMethod.POST)
	public List<CwAssistData> getAssistAccountData(@RequestBody Map<String, String> formMap,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 方法描述： 根据核算类别与核算项目获取核算名称
	 * 
	 * @param txType
	 * @param txCode
	 * @return
	 * @throws Exception
	 *             String
	 * @author Javelin
	 * @date 2017-3-18 上午10:19:20
	 */
	@RequestMapping(value = "/cwTools/getAssistAccountName", method = RequestMethod.POST)
	public String getAssistAccountName(@RequestBody String txType, @RequestParam("txCode") String txCode,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 
	 * 方法描述： 根据科目控制字查询科目分录表里的总数
	 * 
	 * @param accHrt
	 * @return
	 * @throws Exception
	 *             String
	 * @author lzshuai
	 * @date 2017-3-4 下午4:39:31
	 */
	@RequestMapping(value = "/cwTools/getaccHrtCntInDetil", method = RequestMethod.POST)
	public String getaccHrtCntInDetil(@RequestBody String accHrt,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 
	 * 方法描述： 获取业务记账类型bean
	 * 
	 * @param cwVchRuleMst
	 * @return
	 * @throws Exception
	 *             CwVchRuleMst
	 * @author lzshuai
	 * @date 2017-3-8 下午3:24:54
	 */
	@RequestMapping(value = "/cwTools/getCwVchRuleMstBean", method = RequestMethod.POST)
	public CwVchRuleMst getCwVchRuleMstBean(@RequestBody CwVchRuleMst cwVchRuleMst,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 方法描述： 获取业务记账类型列表
	 * 
	 * @param cwVchRuleMst
	 * @return
	 * @throws Exception
	 *             List<CwVchRuleMst>
	 * @author Javelin
	 * @date 2018-1-9 下午2:30:23
	 */
	@RequestMapping(value = "/cwTools/getCwVchRuleMstList", method = RequestMethod.POST)
	public List<CwVchRuleMst> getCwVchRuleMstList(@RequestBody CwVchRuleMst cwVchRuleMst,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 
	 * 方法描述： 获取业务凭证的模板数据
	 * 
	 * @param platebean
	 * @return
	 * @throws Exception
	 *             List<CwVchRuleMstPlate>
	 * @author lzshuai
	 * @date 2017-3-9 下午3:42:27
	 */
	@RequestMapping(value = "/cwTools/getCwRulePlageList", method = RequestMethod.POST)
	public List<CwVchRuleMstPlate> getCwRulePlageList(@RequestBody CwVchRuleMstPlate platebean,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 
	 * 方法描述： 获取业务记账规则模板记录表的数据
	 * 
	 * @param bean
	 * @return
	 * @throws Exception
	 *             List<CwVchRuleDetailPlate>
	 * @author lzshuai
	 * @date 2017-3-10 上午10:54:15
	 */
	@RequestMapping(value = "/cwTools/getCwVchRuleDetailPlateList", method = RequestMethod.POST)
	public List<CwVchRuleDetailPlate> getCwVchRuleDetailPlateList(@RequestBody CwVchRuleDetailPlate bean,@RequestParam("finBooks") String finBooks)
			throws Exception;

	/**
	 * 
	 * 方法描述： 新增业务记账数据记录表
	 * 
	 * @param cwVchRule
	 * @return
	 * @throws Exception
	 *             String
	 * @author lzshuai
	 * @date 2017-3-13 上午9:11:53
	 */
	@RequestMapping(value = "/cwTools/insertCwVchRuleDetail", method = RequestMethod.POST)
	public String insertCwVchRuleDetail(@RequestBody CwVchRuleDetail cwVchRule,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 
	 * 方法描述： 查询获取模板主表的数据
	 * 
	 * @param detailPlate
	 * @return
	 * @throws Exception
	 *             CwVchRuleDetailPlate
	 * @author lzshuai
	 * @date 2017-3-13 上午11:07:34
	 */
	@RequestMapping(value = "/cwTools/getCwVchRuleMstPlate", method = RequestMethod.POST)
	public CwVchRuleMstPlate getCwVchRuleMstPlate(@RequestBody CwVchRuleMstPlate mstPlate,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 
	 * 方法描述： 获取业务记账规则分录表的数据
	 * 
	 * @param cwVchdetail
	 * @return List<CwVchRuleDetail>
	 * @author lzshuai
	 * @date 2017-3-13 下午2:53:27
	 */
	@RequestMapping(value = "/cwTools/getListCwVchRuleDetail", method = RequestMethod.POST)
	public List<CwVchRuleDetail> getListCwVchRuleDetail(@RequestBody CwVchRuleDetail cwVchdetail,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 
	 * 方法描述： 删除业务记账规则数据分录表的数据
	 * 
	 * @param delVchRule
	 * @return
	 * @throws Exception
	 *             String
	 * @author lzshuai
	 * @date 2017-3-15 上午9:13:19
	 */
	@RequestMapping(value = "/cwTools/deleteCwVchRuleDetail", method = RequestMethod.POST)
	public String deleteCwVchRuleDetail(@RequestBody CwVchRuleDetail delVchRule,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 方法描述： 处理凭证导入的封装过的数据
	 * 
	 * @param dataList
	 * @return
	 * @throws Exception
	 *             String
	 * @author Javelin
	 * @date 2017-3-17 下午4:01:25
	 */
	@RequestMapping(value = "/cwTools/dodealImportVchData", method = RequestMethod.POST)
	public String dodealImportVchData(@RequestBody List<String[]> dataList,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 方法描述： 根据凭证编号（以，号隔开）获取凭证数据列表
	 * 
	 * @param voucherNos
	 * @return
	 * @throws Exception
	 *             List<Map<String,Object>>
	 * @author Javelin
	 * @date 2017-3-18 下午2:59:01
	 */
	@RequestMapping(value = "/cwTools/getVoucherDataByNos", method = RequestMethod.POST)
	public List<Map<String, Object>> getVoucherDataByNos(@RequestBody String voucherNos,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 方法描述： 根据凭证编号（以，号隔开）获取凭证导出使用数据列表
	 * 
	 * @param cwVchOutSoftver
	 * @param voucherNos
	 * @return
	 * @throws Exception
	 *             List<ExportVchBean>
	 * @author Javelin
	 * @date 2017-12-22 下午2:26:43
	 */
	@RequestMapping(value = "/cwTools/getExportVoucherDataByNos", method = RequestMethod.POST)
	public List<ExportVchBean> getExportVoucherDataByNos(@RequestBody CwVchOutSoftver cwVchOutSoftver,
			@RequestParam("voucherNos")String voucherNos,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 方法描述： 删除月末结账生成的结转凭证
	 * 
	 * @param weeks
	 * @return
	 * @throws Exception
	 *             String
	 * @author Javelin
	 * @date 2017-3-23 下午4:39:20
	 */
	@RequestMapping(value = "/cwTools/deleteJzVchForWeek", method = RequestMethod.POST)
	public String deleteJzVchForWeek(@RequestBody String weeks,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 方法描述： 获取出纳账户列表
	 * 
	 * @param cwCashierAccount
	 * @return
	 * @throws Exception
	 *             List<CwCashierAccount>
	 * @author Javelin
	 * @date 2017-3-29 下午12:00:19
	 */
	@RequestMapping(value = "/cwTools/getCwCashierAccountList", method = RequestMethod.POST)
	public List<CwCashierAccount> getCwCashierAccountList(@RequestBody CwCashierAccount cwCashierAccount,@RequestParam("finBooks") String finBooks)
			throws Exception;

	/**
	 * 方法描述： 获取出纳账户实体
	 * 
	 * @param cwCashierAccount
	 * @return
	 * @throws Exception
	 *             CwCashierAccount
	 * @author Javelin
	 * @date 2017-3-31 上午10:13:36
	 */
	@RequestMapping(value = "/cwTools/getCwCashierAccountBean", method = RequestMethod.POST)
	public CwCashierAccount getCwCashierAccountBean(@RequestBody CwCashierAccount cwCashierAccount,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 方法描述： 获取日记账实体
	 * 
	 * @param cwCashierJournal
	 * @return
	 * @throws Exception
	 *             CwCashierJournal
	 * @author Javelin
	 * @date 2017-3-31 上午10:07:51
	 */
	@RequestMapping(value = "/cwTools/getCwCashierJournalBean", method = RequestMethod.POST)
	public CwCashierJournal getCwCashierJournalBean(@RequestBody CwCashierJournal cwCashierJournal,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 方法描述： 修改出纳日记账数据信息
	 * 
	 * @param cwCashierJournal
	 * @return
	 * @throws Exception
	 *             String
	 * @author Javelin
	 * @date 2017-3-31 下午4:33:51
	 */
	@RequestMapping(value = "/cwTools/updateCashierJournal", method = RequestMethod.POST)
	public String updateCashierJournal(@RequestBody CwCashierJournal cwCashierJournal,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 方法描述： 获取出纳流水所有发生额或余额
	 * 
	 * @param parmMap
	 *            weeks: 查询该周期之前期的期初余额(@RequestBody 余额必填)
	 *            opnWeek：查询指定开始周期内的发生额(@RequestBody 发生额必填) endWeek： 若 weeks 与
	 *            opnWeek 都存在 优先查余额
	 * @return
	 * @throws Exception
	 *             Map<String,String>
	 * @author Javelin
	 * @date 2017-4-1 下午3:17:37
	 */
	@RequestMapping(value = "/cwTools/getCashierWeekAmtOrBal", method = RequestMethod.POST)
	public Map<String, String> getCashierWeekAmtOrBal(@RequestBody Map<String, String> parmMap,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 方法描述： 获取业务记账复核数据实体
	 * 
	 * @param cwReviewBusiness
	 * @return
	 * @throws Exception
	 *             CwReviewBusiness
	 * @author Javelin
	 * @date 2017-4-24 上午10:29:59
	 */
	@RequestMapping(value = "/cwTools/getCwReviewBusinessBean", method = RequestMethod.POST)
	public CwReviewBusiness getCwReviewBusinessBean(@RequestBody CwReviewBusiness cwReviewBusiness,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 方法描述： 修改
	 * 
	 * @param cwReviewBusiness
	 * @throws Exception
	 *             void
	 * @author Javelin
	 * @date 2017-4-24 下午3:49:19
	 */
	@RequestMapping(value = "/cwTools/updateCwReviewBusiness", method = RequestMethod.POST)
	public void updateCwReviewBusiness(@RequestBody CwReviewBusiness cwReviewBusiness,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 方法描述： 处理业务记账复核 回调问题
	 * 
	 * @param cwReviewBusiness
	 * @return
	 * @throws Exception
	 *             String
	 * @author Javelin
	 * @date 2017-4-24 下午3:53:46
	 */
	@RequestMapping(value = "/cwTools/dodealBusReviewVchData", method = RequestMethod.POST)
	public String dodealBusReviewVchData(@RequestBody CwReviewBusiness cwReviewBusiness,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 方法描述： 获取资产数据列表
	 * 
	 * @param cwAssets
	 * @return
	 * @throws Exception
	 *             List<CwAssets>
	 * @author Javelin
	 * @date 2017-5-8 上午11:44:32
	 */
	@RequestMapping(value = "/cwTools/getCwAssetsList", method = RequestMethod.POST)
	public List<CwAssets> getCwAssetsList(@RequestBody CwAssets cwAssets,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 方法描述： 获取资产数据实体
	 * 
	 * @param cwAssets
	 * @return
	 * @throws Exception
	 *             CwAssets
	 * @author Javelin
	 * @date 2017-5-8 上午11:44:35
	 */
	@RequestMapping(value = "/cwTools/getCwAssetsBean", method = RequestMethod.POST)
	public CwAssets getCwAssetsBean(@RequestBody CwAssets cwAssets,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 方法描述： 修改资产数据
	 * 
	 * @param cwAssets
	 * @return
	 * @throws Exception
	 *             String
	 * @author Javelin
	 * @date 2017-5-10 上午11:05:36
	 */
	@RequestMapping(value = "/cwTools/updateCwAssets", method = RequestMethod.POST)
	public String updateCwAssets(@RequestBody CwAssets cwAssets,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 方法描述： 获取资产类别数据列表
	 * 
	 * @param cwAssetsClass
	 * @return
	 * @throws Exception
	 *             List<CwAssetsClass>
	 * @author Javelin
	 * @date 2017-5-8 上午11:44:38
	 */
	@RequestMapping(value = "/cwTools/getCwAssetsClassesList", method = RequestMethod.POST)
	public List<CwAssetsClass> getCwAssetsClassesList(@RequestBody CwAssetsClass cwAssetsClass,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 方法描述： 获取资产类别数据实体
	 * 
	 * @param cwAssetsClass
	 * @return
	 * @throws Exception
	 *             CwAssetsClass
	 * @author Javelin
	 * @date 2017-5-8 上午11:44:40
	 */
	@RequestMapping(value = "/cwTools/getCwAssetsClassBean", method = RequestMethod.POST)
	public CwAssetsClass getCwAssetsClassBean(@RequestBody CwAssetsClass cwAssetsClass,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 方法描述： 获取资产历史列表数据
	 * 
	 * @param cwAssetsHst
	 * @return
	 * @throws Exception
	 *             List<CwAssetsHst>
	 * @author Javelin
	 * @date 2017-5-26 下午3:18:09
	 */
	@RequestMapping(value = "/cwTools/getCwAssetsHstList", method = RequestMethod.POST)
	public List<CwAssetsHst> getCwAssetsHstList(@RequestBody CwAssetsHst cwAssetsHst,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 方法描述： 获取资产历史实体
	 * 
	 * @param cwAssetsHst
	 * @return
	 * @throws Exception
	 *             CwAssetsHst
	 * @author Javelin
	 * @date 2017-5-26 下午3:18:12
	 */
	@RequestMapping(value = "/cwTools/getCwAssetsHstBean", method = RequestMethod.POST)
	public CwAssetsHst getCwAssetsHstBean(@RequestBody CwAssetsHst cwAssetsHst,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 
	 * 方法描述： 科目检查，获取交易代码配置
	 * 
	 * @param parmMap
	 * @return
	 * @throws Exception
	 *             List<Map<String,String>>
	 * @author lzshuai
	 * @date 2017-5-20 上午10:40:05
	 */
	@RequestMapping(value = "/cwTools/getCwVchruleMstData", method = RequestMethod.POST)
	public List<Map<String, String>> getCwVchruleMstData(@RequestBody Map<String, String> parmMap,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 
	 * 方法描述： 获取科目关系实体类
	 * 
	 * @param relataionbean
	 * @return
	 * @throws Exception
	 *             CwRelation
	 * @author lzshuai
	 * @date 2017-5-20 下午2:02:52
	 */
	@RequestMapping(value = "/cwTools/getCwRelationByBean", method = RequestMethod.POST)
	public CwRelation getCwRelationByBean(@RequestBody CwRelation relataionbean,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 
	 * 方法描述： 科目检查：获取报表项配置
	 * 
	 * @param parmMap
	 * @return List<Map<String,String>>
	 * @author lzshuai
	 * @date 2017-5-22 上午10:52:31
	 */
	@RequestMapping(value = "/cwTools/getReportItemRelationData", method = RequestMethod.POST)
	public List<Map<String, String>> getReportItemRelationData(@RequestBody Map<String, String> parmMap,@RequestParam("finBooks") String finBooks)
			throws Exception;

	/**
	 * 
	 * 方法描述： 保存辅助核算数据表
	 * 
	 * @param assbean
	 * @return
	 * @throws Exception
	 *             String
	 * @author lzshuai
	 * @date 2017-6-3 下午8:17:01
	 */
	@RequestMapping(value = "/cwTools/insertCwVoucherAssistBean", method = RequestMethod.POST)
	public String insertCwVoucherAssistBean(@RequestBody CwVoucherAssist assbean,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 
	 * 方法描述： 获取科目表和总账表的差值
	 * 
	 * @return
	 * @throws Exception
	 *             List<CwComItem>
	 * @author lzshuai
	 * @date 2017-6-14 下午4:48:29
	 */
	@RequestMapping(value = "/cwTools/getDataNotInLedger", method = RequestMethod.POST)
	public List<CwComItem> getDataNotInLedger(@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 方法描述：从凭证分录表获取指定周期内的每个科目类别的总发生额
	 * 
	 * @param monthedWeek
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/cwTools/getSumAmtGroupByAccType", method = RequestMethod.POST)
	public List<Map<String, String>> getSumAmtGroupByAccType(@RequestBody String week,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 方法描述：从总账表获取每个科目类别的一级科目余额汇总
	 * 
	 * @param monthedWeek
	 * @return
	 */
	@RequestMapping(value = "/cwTools/getSumBalGroupByAccType", method = RequestMethod.POST)
	public List<Map<String, String>> getSumBalGroupByAccType(@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 方法描述：获取辅助核算列表
	 * 
	 * @param cwFication
	 * @return
	 */
	@RequestMapping(value = "/cwTools/getCwFicationByBean", method = RequestMethod.POST)
	public List<CwFication> getCwFicationByBean(@RequestBody CwFication cwFication,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 
	 * 方法描述： 查询凭证分录辅助核算数据表的数据
	 * 
	 * @param assbean
	 * @return 分组查询
	 * @throws Exception
	 *             List<CwVoucherAssist>
	 * @author lzshuai
	 * @date 2017-6-20 下午5:04:33
	 */
	@RequestMapping(value = "/cwTools/findassbeanList", method = RequestMethod.POST)
	public List<CwVoucherAssist> findassbeanList(@RequestBody CwVoucherAssist assbean,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 
	 * 方法描述： 查询凭证分录辅助核算数据表的数据
	 * 
	 * @param tassbean
	 * @return
	 * @throws Exception
	 *             List<CwVoucherAssist>
	 * @author lzshuai
	 * @date 2017-6-20 下午5:28:10
	 */
	@RequestMapping(value = "/cwTools/findbypageAssbeanlist", method = RequestMethod.POST)
	public List<CwVoucherAssist> findbypageAssbeanlist(@RequestBody CwVoucherAssist tassbean,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 
	 * 方法描述： 修改财务初始化辅助核算数据
	 * 
	 * @param tassbean
	 * @return
	 * @throws Exception
	 *             String
	 * @author lzshuai
	 * @date 2017-6-21 上午11:55:18
	 */
	@RequestMapping(value = "/cwTools/updateCwVoucherAssist", method = RequestMethod.POST)
	public String updateCwVoucherAssist(@RequestBody CwVoucherAssist tassbean,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 
	 * 方法描述： 删除初始化数据操作
	 * 
	 * @param assbean
	 * @return
	 * @throws Exception
	 *             String
	 * @author lzshuai
	 * @date 2017-6-22 上午9:13:50
	 */
	@RequestMapping(value = "/cwTools/delInitBalResult", method = RequestMethod.POST)
	public String delInitBalResult(@RequestBody CwVoucherAssist assbean,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 从初始化余额表获取期初余额
	 * 
	 * @param initWeek
	 * @param string
	 * @return
	 */
	@RequestMapping(value = "/cwTools/getCashFlowAmtByInitBal", method = RequestMethod.POST)
	public String getCashFlowAmtByInitBal(@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 
	 * 方法描述： 获取辅助核算余额
	 * 
	 * @param accHrt
	 * @return
	 * @throws Exception
	 *             String
	 * @author lzshuai
	 * @date 2017-6-28 上午10:13:31
	 */
	@RequestMapping(value = "/cwTools/getBalBuAccHrt", method = RequestMethod.POST)
	public String getBalBuAccHrt(@RequestBody String accHrt,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 
	 * 方法描述： 修改财务初始余额辅助核算的accHrt,把上级科目控制字改成第一个子科目下
	 * 
	 * @param cwassbean
	 * @return
	 * @throws Exception
	 *             String
	 * @author lzshuai
	 * @date 2017-6-28 下午2:26:07
	 */
	@RequestMapping(value = "/cwTools/updateCwVoucherAssistByAccHrt", method = RequestMethod.POST)
	public String updateCwVoucherAssistByAccHrt(@RequestBody CwVoucherAssist cwassbean,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 
	 * 方法描述： 插入财务初始辅助核算余额表
	 * 
	 * @param inbalbean
	 * @throws Exception
	 *             void
	 * @author lzshuai
	 * @date 2017-6-28 下午2:59:31
	 */
	@RequestMapping(value = "/cwTools/insertCwInitBalBean", method = RequestMethod.POST)
	public void insertCwInitBalBean(@RequestBody CwInitBal inbalbean,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 方法描述：从初始余额表获取每个科目类别的一级科目余额汇总
	 * 
	 * @return
	 */
	@RequestMapping(value = "/cwTools/getSumBalGroupByAccTypeInitWeek", method = RequestMethod.POST)
	public List<Map<String, String>> getSumBalGroupByAccTypeInitWeek(@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 
	 * 方法描述： 获取凭证分录表的数据
	 * 
	 * @param tmap
	 * @return
	 * @throws Exception
	 *             List<Map<String,String>>
	 * @author lzshuai
	 * @date 2017-6-30 下午5:29:38
	 */
	@RequestMapping(value = "/cwTools/getCwVouDetialData", method = RequestMethod.POST)
	public List<Map<String, String>> getCwVouDetialData(@RequestBody Map<String, String> tmap,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 
	 * 方法描述： 获取辅助核算数据
	 * 
	 * @param tmap
	 * @return
	 * @throws Exception
	 *             List<Map<String,String>>
	 * @author lzshuai
	 * @date 2017-6-30 下午5:51:59
	 */
	@RequestMapping(value = "/cwTools/getCwVouAssistData", method = RequestMethod.POST)
	public List<Map<String, String>> getCwVouAssistData(@RequestBody Map<String, String> tmap,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 
	 * 方法描述： 获取初始化余额bean
	 * 
	 * @param initbalbean
	 * @return
	 * @throws Exception
	 *             CwInitBal
	 * @author lzshuai
	 * @date 2017-7-4 上午9:08:18
	 */
	@RequestMapping(value = "/cwTools/getCwInitBalBean", method = RequestMethod.POST)
	public CwInitBal getCwInitBalBean(@RequestBody CwInitBal initbalbean,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 
	 * 方法描述： 新增辅助核算项
	 * 
	 * @param cwfication
	 * @throws Exception
	 *             void
	 * @author lzshuai
	 * @date 2017-7-20 下午2:50:15
	 */
	@RequestMapping(value = "/cwTools/insertCwFication", method = RequestMethod.POST)
	public void insertCwFication(@RequestBody CwFication cwfication) throws Exception;

	/**
	 * 
	 * 方法描述： 获取参数表中的所有参数
	 * 
	 * @return
	 * @throws Exception
	 *             List<CwSysParam>
	 * @author lzshuai
	 * @date 2017-7-20 下午2:59:25
	 */
	@RequestMapping(value = "/cwTools/getallParamList", method = RequestMethod.POST)
	public List<CwSysParam> getallParamList(@RequestBody CwSysParam sysParam,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 
	 * 方法描述：批量插入数据
	 * 
	 * @param paramlist
	 * @throws Exception
	 *             void
	 * @author lzshuai
	 * @date 2017-7-20 下午3:48:41
	 */
	@RequestMapping(value = "/cwTools/dobatchInsertCwSysParam", method = RequestMethod.POST)
	public void dobatchInsertCwSysParam(@RequestBody List<CwSysParam> paramlist) throws Exception;

	/**
	 * 
	 * 方法描述： 这个方法只针对帐套使用
	 * 
	 * @param cwSysParam
	 * @throws Exception
	 *             void
	 * @author lzshuai
	 * @date 2017-7-21 下午6:33:51
	 */
	@RequestMapping(value = "/cwTools/updateCwSysParamNoBook", method = RequestMethod.POST)
	public String updateCwSysParamNoBook(@RequestBody CwSysParam cwSysParam) throws Exception;

	/**
	 * 
	 * 方法描述： 账套复制，查询某个账套是否已经初始化
	 * 
	 * @param code
	 * @param finBooks2
	 * @return
	 * @throws Exception
	 *             String
	 * @author lzshuai
	 * @date 2017-8-8 上午11:25:28
	 */
	@RequestMapping(value = "/cwTools/getCwSysParamBeanByCodeAndBooks", method = RequestMethod.POST)
	public CwSysParam getCwSysParamBeanByCodeAndBooks(@RequestParam("code") String code, @RequestParam("finBooks")String finBooks) throws Exception;

	/**
	 * 
	 * 方法描述： 批量插入周期
	 * 
	 * @param cycleList
	 * @throws Exception
	 *             void
	 * @author lzshuai
	 * @date 2017-8-8 上午11:48:15
	 */
	@RequestMapping(value = "/cwTools/insertCycleList", method = RequestMethod.POST)
	public void insertCycleList(@RequestBody List<CwCycle> cycleList) throws Exception;

	/**
	 * 
	 * 方法描述： 批量插入
	 * 
	 * @param hstList
	 * @throws Exception
	 *             void
	 * @author lzshuai
	 * @date 2017-8-8 上午11:52:44
	 */
	@RequestMapping(value = "/cwTools/insertHstList", method = RequestMethod.POST)
	public void insertHstList(@RequestBody List<CwCycleHst> hstList) throws Exception;

	/**
	 * 
	 * 方法描述： 批量插入
	 * 
	 * @param monthKnotList
	 * @throws Exception
	 *             void
	 * @author lzshuai
	 * @date 2017-8-8 上午11:58:42
	 */
	@RequestMapping(value = "/cwTools/domonthKnotBatchInsert", method = RequestMethod.POST)
	public void domonthKnotBatchInsert(@RequestBody List<CwMonthKnot> monthKnotList) throws Exception;

	/**
	 * 
	 * 方法描述： 复制账套，修改参数设置
	 * 
	 * @param cwSysParam
	 * @throws Exception
	 *             void
	 * @author lzshuai
	 * @date 2017-8-8 下午2:30:37
	 */
	@RequestMapping(value = "/cwTools/updateCwSysParamForZt", method = RequestMethod.POST)
	public String updateCwSysParamForZt(@RequestBody CwSysParam cwSysParam) throws Exception;

	/**
	 * 
	 * 方法描述： 新增一个凭证字，复制账套时使用，已经封装了账套号
	 * 
	 * @param cwProofWords
	 * @throws Exception
	 *             void
	 * @author lzshuai
	 * @date 2017-8-8 下午2:45:49
	 */
	@RequestMapping(value = "/cwTools/insertCwProofWordsForZt", method = RequestMethod.POST)
	public void insertCwProofWordsForZt(@RequestBody CwProofWords cwProofWords) throws Exception;

	/**
	 * 
	 * 方法描述： 复制财务余额初始数据，对账套复制功能
	 * 
	 * @param finBooks
	 * @param copyFinBooks
	 * @throws Exception
	 *             void
	 * @author lzshuai
	 * @date 2017-8-8 下午4:06:45
	 */
	@RequestMapping(value = "/cwTools/docopyInitBalDataForZt", method = RequestMethod.POST)
	public String docopyInitBalDataForZt(@RequestBody Map<String, String> finmap) throws Exception;

	/**
	 * 
	 * 方法描述： 删除辅助核算数据
	 * 
	 * @param assbean
	 * @throws Exception
	 *             void
	 * @author lzshuai
	 * @date 2017-8-8 下午4:25:40
	 */
	@RequestMapping(value = "/cwTools/delInitBalResultForZt", method = RequestMethod.POST)
	public String delInitBalResultForZt(@RequestBody CwVoucherAssist assbean) throws Exception;

	/**
	 * 
	 * 方法描述： 获取辅助核算项，复制帐套时使用
	 * 
	 * @param cwfication
	 * @return
	 * @throws Exception
	 *             List<CwFication>
	 * @author lzshuai
	 * @date 2017-8-9 下午4:23:29
	 */
	@RequestMapping(value = "/cwTools/getCwFicationListForZt", method = RequestMethod.POST)
	public List<CwFication> getCwFicationListForZt(@RequestBody CwFication cwfication) throws Exception;

	/**
	 * 
	 * 方法描述： 复制账套时，查询科目与辅助核算关系
	 * 
	 * @param copyRelation
	 * @return
	 * @throws Exception
	 *             List<CwRelation>
	 * @author lzshuai
	 * @date 2017-8-11 上午9:35:16
	 */
	@RequestMapping(value = "/cwTools/findCwRelationForZt", method = RequestMethod.POST)
	public List<CwRelation> findCwRelationForZt(@RequestBody CwRelation copyRelation) throws Exception;

	/**
	 * 
	 * 方法描述： 复制科目与辅助核算关系
	 * 
	 * @param relist
	 * @throws Exception
	 *             void
	 * @author lzshuai
	 * @date 2017-8-11 上午9:47:12
	 */
	@RequestMapping(value = "/cwTools/insertBatchRelationForZt", method = RequestMethod.POST)
	public void insertBatchRelationForZt(@RequestBody CwRelation rbean) throws Exception;

	/**
	 * 
	 * 方法描述： 复制辅助核算项数据
	 * 
	 * @param ficabean
	 * @return
	 * @throws Exception
	 *             List<CwFicationData>
	 * @author lzshuai
	 * @date 2017-8-15 下午5:56:50
	 */
	@RequestMapping(value = "/cwTools/getFicationDataList", method = RequestMethod.POST)
	public List<CwFicationData> getFicationDataList(@RequestBody CwFicationData ficabean) throws Exception;

	/**
	 * 方法描述： 获取辅助核算数据数量
	 * 
	 * @param ficabean
	 * @return
	 * @throws Exception
	 *             String
	 * @author Javelin
	 * @date 2017-12-23 上午9:39:17
	 */
	@RequestMapping(value = "/cwTools/getFicationDataCount", method = RequestMethod.POST)
	public String getFicationDataCount(@RequestBody CwFicationData ficabean,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 
	 * 方法描述： 插入辅助核算数据
	 * 
	 * @param newdata
	 * @throws Exception
	 *             void
	 * @author lzshuai
	 * @date 2017-8-15 下午6:04:19
	 */
	@RequestMapping(value = "/cwTools/insertFicationDataForzt", method = RequestMethod.POST)
	public void insertFicationDataForzt(@RequestBody CwFicationData newdata) throws Exception;

	/**
	 * 
	 * 方法描述： 保存计提bean
	 * 
	 * @param itmbean
	 * @throws Exception
	 *             void
	 * @author lzshuai
	 * @date 2017-8-23 下午7:53:16
	 */
	@RequestMapping(value = "/cwTools/insertJtItemBean", method = RequestMethod.POST)
	public void insertJtItemBean(@RequestBody CwJitiItem itmbean,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 
	 * 方法描述： 删除计提的数据
	 * 
	 * @param jtitembean
	 * @throws Exception
	 *             void
	 * @author lzshuai
	 * @date 2017-8-24 上午9:23:49
	 */
	@RequestMapping(value = "/cwTools/deleteCwJitiItem", method = RequestMethod.POST)
	public void deleteCwJitiItem(@RequestBody CwJitiItem jtitembean,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 
	 * 方法描述： 获取计提bean
	 * 
	 * @param cwJiti
	 * @return
	 * @throws Exception
	 *             CwJiti
	 * @author lzshuai
	 * @date 2017-8-24 下午3:48:14
	 */
	@RequestMapping(value = "/cwTools/getCwJitiBean", method = RequestMethod.POST)
	public CwJiti getCwJitiBean(@RequestBody CwJiti cwJiti,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 
	 * 方法描述： 修改计提
	 * 
	 * @param jtbean
	 * @throws Exception
	 *             void
	 * @author lzshuai
	 * @date 2017-8-24 下午6:53:53
	 */
	@RequestMapping(value = "/cwTools/doUpdateCwJiti", method = RequestMethod.POST)
	public void doUpdateCwJiti(@RequestBody CwJiti jtbean,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 
	 * 方法描述： 根据jitiid获取明细表内容
	 * 
	 * @param tbean
	 * @return List<CwJitiItem>
	 * @author lzshuai
	 * @date 2017-8-25 下午6:39:49
	 */
	@RequestMapping(value = "/cwTools/getCwJitiItemByjtid", method = RequestMethod.POST)
	public List<CwJitiItem> getCwJitiItemByjtid(@RequestBody CwJitiItem tbean,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 
	 * 方法描述： 新增价税明细表
	 * 
	 * @param itembean
	 * @return
	 * @throws Exception
	 *             String
	 * @author lzshuai
	 * @date 2017-9-8 上午11:20:55
	 */
	@RequestMapping(value = "/cwTools/insertCwPriceTaxSepItems", method = RequestMethod.POST)
	public String insertCwPriceTaxSepItems(@RequestBody CwPriceTaxSepItems itembean,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 
	 * 方法描述： 删除价税分离的明细表数据
	 * 
	 * @param itembean
	 * @throws Exception
	 *             void
	 * @author lzshuai
	 * @date 2017-9-8 下午3:05:43
	 */
	@RequestMapping(value = "/cwTools/deleteCwPriceTaxSepItems", method = RequestMethod.POST)
	public void deleteCwPriceTaxSepItems(@RequestBody CwPriceTaxSepItems itembean,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 
	 * 方法描述： 获取价税明细的列表
	 * 
	 * @param itembean
	 * @return
	 * @throws Exception
	 *             List<CwPriceTaxSepItems>
	 * @author lzshuai
	 * @date 2017-9-8 下午4:28:12
	 */
	@RequestMapping(value = "/cwTools/getItemListById", method = RequestMethod.POST)
	public List<CwPriceTaxSepItems> getItemListById(@RequestBody CwPriceTaxSepItems itembean,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 
	 * 方法描述： 获取价税明细的列表
	 * 
	 * @param cwPriceTaxSepItems
	 * @return
	 * @throws Exception
	 *             List<CwPriceTaxSepItems>
	 * @author lzshuai
	 * @date 2017-9-9 下午4:04:02
	 */
	@RequestMapping(value = "/cwTools/getItemListByTime", method = RequestMethod.POST)
	public List<CwPriceTaxSepItems> getItemListByTime(@RequestBody CwPriceTaxSepItems cwPriceTaxSepItems,@RequestParam("finBooks") String finBooks)
			throws Exception;

	/**
	 * 
	 * 方法描述： 获取价税分离的实体bean
	 * 
	 * @param cwPriceTaxSep
	 * @return
	 * @throws Exception
	 *             CwPriceTaxSep
	 * @author lzshuai
	 * @date 2017-9-9 下午4:04:19
	 */
	@RequestMapping(value = "/cwTools/getCwPriceTaxSepBean", method = RequestMethod.POST)
	public CwPriceTaxSep getCwPriceTaxSepBean(@RequestBody CwPriceTaxSep cwPriceTaxSep,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 
	 * 方法描述： 修改价税分离的实体bean
	 * 
	 * @param taxbean
	 * @throws Exception
	 *             void
	 * @author lzshuai
	 * @date 2017-9-9 下午5:02:06
	 */
	@RequestMapping(value = "/cwTools/doUpdateCwPriceTaxSep", method = RequestMethod.POST)
	public void doUpdateCwPriceTaxSep(@RequestBody CwPriceTaxSep taxbean,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 
	 * 方法描述： 根据类型获取模板数据
	 * 
	 * @param cwPrintTmplItem
	 * @return
	 * @throws Exception
	 *             List<Map<String,Object>>
	 * @author lzshuai
	 * @date 2017-9-15 下午4:29:00
	 */
	@RequestMapping(value = "/cwTools/getTmplCodeByType", method = RequestMethod.POST)
	public List<Map<String, Object>> getTmplCodeByType(@RequestBody CwPrintTmpl cwPrintTmpl,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 
	 * 方法描述： 根据类型获取模版实体
	 * 
	 * @param implbean
	 * @return
	 * @throws Exception
	 *             CwPrintTmpl
	 * @author lzshuai
	 * @date 2017-9-16 上午10:43:48
	 */
	@RequestMapping(value = "/cwTools/getCwPrintTmplBeanByType", method = RequestMethod.POST)
	public CwPrintTmpl getCwPrintTmplBeanByType(@RequestBody CwPrintTmpl implbean,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 
	 * 方法描述： 插入模版实体
	 * 
	 * @param implbean
	 * @throws Exception
	 *             void
	 * @author lzshuai
	 * @date 2017-9-16 上午11:05:49
	 */
	@RequestMapping(value = "/cwTools/insertCwPrintTmplBean", method = RequestMethod.POST)
	public void insertCwPrintTmplBean(@RequestBody CwPrintTmpl implbean,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 
	 * 方法描述： 修改模版实体
	 * 
	 * @param implbean
	 * @throws Exception
	 *             void
	 * @author lzshuai
	 * @date 2017-9-16 上午11:11:31
	 */
	@RequestMapping(value = "/cwTools/updateCwPrintTmpl", method = RequestMethod.POST)
	public void updateCwPrintTmpl(@RequestBody CwPrintTmpl implbean,@RequestParam("finBooks") String finBooks) throws Exception;

}
