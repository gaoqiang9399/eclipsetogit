package app.component.finance.voucher.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;
import com.google.gson.Gson;

import app.base.User;
import app.component.finance.account.entity.CwFication;
import app.component.finance.assets.entity.CwAssets;
import app.component.finance.cashier.entity.CwCashierJournal;
import app.component.finance.cwtools.feign.CwMonthKnotFeign;
import app.component.finance.cwtools.feign.CwToolsFeign;
import app.component.finance.paramset.entity.CwProofWords;
import app.component.finance.util.CWEnumBean;
import app.component.finance.util.CwPublicUtil;
import app.component.finance.util.R;
import app.component.finance.voucher.entity.CwCashFlowAnalysis;
import app.component.finance.voucher.entity.CwReviewBusiness;
import app.component.finance.voucher.entity.CwVoucherMst;
import app.component.finance.voucher.feign.CwVoucherMstFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.DateUtil;
import cn.mftcc.util.MathExtend;
import cn.mftcc.util.StringUtil;
import cn.mftcc.util.WaterIdUtil;
import net.sf.json.JSONObject;

/**
 * Title: CwVoucherMstAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Thu Dec 29 15:49:43 CST 2016
 **/
@Controller
@RequestMapping("/cwVoucherMst")
public class CwVoucherMstController extends BaseFormBean {

	private Gson gson = new Gson();
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入cwComItemFeign
	@Autowired
	private CwVoucherMstFeign cwVoucherMstFeign;
	@Autowired
	private CwMonthKnotFeign cwMonthKnotFeign;
	@Autowired
	private CwToolsFeign cwToolsFeign;
	// 全局变量
	private String query;
	// 标签列表所需要的代码 start
	private FormService formService = new FormService();
	// end

	public CwVoucherMstController() {
		query = "";
	}

	/**
	 * 进入凭证查询列表页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		String finBooks = (String)request.getSession().getAttribute("finBooks");
		String noClose = cwMonthKnotFeign.getMinNoCloseWeek(finBooks);
		String txWeek = DateUtil.getDate().substring(0, 6);
		dataMap.put("week", noClose);
		if (MathExtend.comparison(noClose, txWeek) > 0) {
			dataMap.put("week", txWeek);
		}
		dataMap.put("endWeek", txWeek);
		cwToolsFeign.deleteJzVchForWeek(noClose,finBooks);
		List<CwProofWords> proofs = cwToolsFeign.getCwProofWordsList(new CwProofWords(),finBooks);
		dataMap.put("proofs", gson.toJson(proofs));
		List<CwFication> fications = cwToolsFeign.getCwFicationList(new CwFication(),finBooks);
		dataMap.put("fications", gson.toJson(fications));
		String cashOpen = cwToolsFeign.getCwSysParamBeanByCode(CWEnumBean.SYSPARAM_CODE.xjllfx.getNum(),finBooks).getPvalue();
		dataMap.put("cashOpen", cashOpen);
		model.addAttribute("dataMap", dataMap);
		return "/component/finance/voucher/CwVoucherMst_List";
	}

	/**
	 * 方法描述： 查询凭证列表数据
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Javelin
	 * @date 2017-1-7 下午3:28:52
	 */
	@RequestMapping(value = "/getVoucherListAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getVoucherListAjax(Integer pageNo, Integer pageSize, String tableId, String tableType, String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, String> formMap = CwPublicUtil.getMapByJson(ajaxData);
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setPageSize(pageSize);
			String finBooks = (String)request.getSession().getAttribute("finBooks");
			Map<String, Object> params = new HashMap<>();
			params.put("formMap", formMap);
			ipage.setParams(params);
			ipage = cwVoucherMstFeign.getVoucherList(ipage,finBooks);
			// 返回相应的HTML方法
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			e.printStackTrace();
//			logger.error("查询凭证列表数据出错", e);
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * 获得冲销列表
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getChongXiaoListAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getChongXiaoListAjax(Integer pageNo, Integer pageSize, String tableId, String tableType, String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, String> formMap = CwPublicUtil.getMapByJson(ajaxData);
			String week = formMap.get("week");
			formMap.put("beginWeek", week);
			formMap.put("endWeek", week);
			formMap.put("isInvalid", CWEnumBean.YES_OR_NO.FOU.getNum());// 非作废
			formMap.put("jzFlag", "Y");// 已记账
			formMap.put("isCx", "0");// 不是冲销凭证,也不是被冲销凭证
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setPageSize(pageSize);
			Map<String, Object> params = new HashMap<>();
			params.put("formMap", formMap);
			ipage.setParams(params);
			String finBooks = (String)request.getSession().getAttribute("finBooks");
			ipage = cwVoucherMstFeign.getVoucherList(ipage,finBooks);
			// 返回相应的HTML方法
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			e.printStackTrace();
//			logger.error("getChongXiaoListAjax方法出错，执行action层失败，抛出异常，", e);
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * 冲销
	 * 
	 * @return
	 */
	@RequestMapping(value = "/chongXiaoPzAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> chongXiaoPzAjax(String voucherNo) {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {

			String finBooks = (String)request.getSession().getAttribute("finBooks");
			cwVoucherMstFeign.chongXiaoPz(voucherNo,finBooks,User.getRegNo(request),User.getRegName(request));


			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED_OPERATION.getMessage("冲销"));
			dataMap.put("msg", "操作成功！");
		} catch (Exception e) {
//			logger.error("chongXiaoPzAjax方法出错，执行action层失败，抛出异常，", e);
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
		}
		return dataMap;
	}

	/**
	 * 获得现金流量分析的凭证列表
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getCashFlowAnalysisListAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getCashFlowAnalysisListAjax(Integer pageNo, Integer pageSize, String tableId, String tableType, String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, String> formMap = CwPublicUtil.getMapByJson(ajaxData);
			String week = formMap.get("week");
			formMap.put("beginWeek", week);
			formMap.put("endWeek", week);

			formMap.put("isInvalid", CWEnumBean.YES_OR_NO.FOU.getNum());// 非作废
			formMap.put("cashSts", "0");// mybatis为!=0，即包含已分析和未分析的凭证
			formMap.put("isCashList", "cash");// 是现金流量分析列表

			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setPageSize(pageSize);
			Map<String, Object> params = new HashMap<>();
			params.put("formMap", formMap);
			ipage.setParams(params);
			String finBooks = (String)request.getSession().getAttribute("finBooks");
			ipage = cwVoucherMstFeign.getVoucherList(ipage,finBooks);
			// 返回相应的HTML方法
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			e.printStackTrace();
//			logger.error("getCashFlowAnalysisListAjax方法出错，执行action层失败，抛出异常，", e);
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * 方法描述： 进入凭证录入页面
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Javelin
	 * @date 2016-12-30 上午9:03:00
	 */
	@RequestMapping(value = "/toVoucherAdd")
	public String toVoucherAdd(Model model) throws Exception {
		// Map<String, Object> dataMap = new HashMap<String, Object>();
		// dataMap.putAll(cwVoucherMstFeign.getInitVchData());
		ActionContext.initialize(request, response);
		String voucherNo = WaterIdUtil.getWaterId("pz");// 生成凭证编号
		model.addAttribute("voucherNo", voucherNo);// 财务需要
		return "/component/finance/voucher/voucher_add";
	}

	/**
	 * 方法描述： 进入凭证录入页面(预设定凭证数据)
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Javelin
	 * @date 2017-3-31 上午9:38:16
	 */
	@RequestMapping(value = "/toVoucherAddSet")
	public String toVoucherAddSet(Model model, String ajaxData) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, String> paramMap = CwPublicUtil.toMap(ajaxData);
			String finBooks = (String)request.getSession().getAttribute("finBooks");
			R r = cwVoucherMstFeign.getAllVchDataByType(paramMap,finBooks);
			if(r.isOk()) {
				Map<String, Object> result =(Map<String, Object>) r.getResult();
				dataMap.putAll(result);
				dataMap.put("voucherNo", WaterIdUtil.getWaterId("pz"));
			}else {
				dataMap.put("flag", "error");
				dataMap.put("msg", r.getMsg());
			}

		} catch (Exception e) {
			dataMap.put("flag", "error");
			e.printStackTrace();
			throw e;
		}
		model.addAttribute("dataMap", dataMap);
		return "/component/finance/voucher/voucher_add_set";
	}

	/**
	 * 方法描述： 进入凭证详情页面
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Javelin
	 * @date 2017-1-6 下午2:42:53
	 */
	@RequestMapping(value = "/toVoucherEdit")
	public String toVoucherEdit(Model model, String voucherNo, String params, String which, String businessNo) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		CwVoucherMst cwVoucherMst = new CwVoucherMst();
		cwVoucherMst.setVoucherNo(voucherNo);
		String finBooks = (String)request.getSession().getAttribute("finBooks");
		cwVoucherMst = cwVoucherMstFeign.getById(cwVoucherMst,finBooks);
		String closeWeek = cwMonthKnotFeign.getMaxCloseWeek(finBooks);
		dataMap.put("isClose", MathExtend.comparison(closeWeek, cwVoucherMst.getWeeks()) >= 0 ? true : false);
		dataMap.put("jzFlag", cwVoucherMst.getJzFlag());
		dataMap.put("voucherNo", voucherNo);
		dataMap.put("allVchNo", params);
		if (StringUtil.isNotEmpty(which)) {
			dataMap.put("which", which);
			// 如果是出纳，凭证信息需要更新
			if ("cashier".equals(which)) {
				CwCashierJournal journal = new CwCashierJournal();
				journal.setVoucherNo(voucherNo);
				journal = cwToolsFeign.getCwCashierJournalBean(journal,finBooks);
				if (journal != null) {
					dataMap.put("businessNo", journal.getUid());
				}
			} else if ("review".equals(which)) {
				CwReviewBusiness review = new CwReviewBusiness();
				review.setVoucherNo(voucherNo);
				review = cwToolsFeign.getCwReviewBusinessBean(review,finBooks);
				if (review != null) {
					dataMap.put("businessNo", review.getTraceNo());
				}
			} else if ("assets".equals(which)) {
				CwAssets cwAssets = new CwAssets();
				cwAssets.setBuyVchNo(voucherNo);
				cwAssets.setCleanVchNo(voucherNo);
				cwAssets = cwToolsFeign.getCwAssetsBean(cwAssets,finBooks);
				if (cwAssets != null) {
					dataMap.put("businessNo", cwAssets.getUuid());
				}
			} else if ("taxPrice".equals(which)) {
				dataMap.put("businessNo", businessNo);

			}else {
			}
		}
		model.addAttribute("dataMap", dataMap);
		// dataMap.putAll(cwVoucherMstFeign.getInitVchData());
		return "/component/finance/voucher/voucher_edit";
	}

	/**
	 * 方法描述： 进入凭证冲销页面
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @date 2017-2-10 下午2:42:53
	 */
	@RequestMapping(value = "/toChongXiao")
	public String toChongXiao(Model model, String voucherNo) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("voucherNo", voucherNo);
		String finBooks = (String)request.getSession().getAttribute("finBooks");
		dataMap.putAll(cwVoucherMstFeign.getInitVchData(finBooks,User.getRegName(request)));
		model.addAttribute("dataMap", dataMap);
		return "/component/finance/voucher/CwChongXiao_CX";
	}

	/**
	 * 方法描述： 进入现金流量分析页面
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @date 2017-2-13 下午2:42:53
	 */
	@RequestMapping(value = "/toCashFlowAnalysis")
	public String toCashFlowAnalysis(Model model, String voucherNo) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("voucherNo", voucherNo);
		String finBooks = (String)request.getSession().getAttribute("finBooks");
		dataMap.putAll(cwVoucherMstFeign.getInitVchData(finBooks,User.getRegName(request)));
		model.addAttribute("dataMap", dataMap);
		return "/component/finance/voucher/CwCashFlowAnalysis_Analysis";
	}

	/**
	 * 方法描述：进入现金流量分析的保存页面
	 */
	@RequestMapping(value = "/toCashFlowAnalysisSave")
	public String toCashFlowAnalysisSave(Model model, String voucherNo) throws Exception {
		try {
			String finBooks = (String)request.getSession().getAttribute("finBooks");
			List<CwCashFlowAnalysis> cwCashFlowAnalysisList = cwVoucherMstFeign.getCashFlowData(voucherNo,finBooks);
			FormData tablecashflowanalysissave0001 = formService.getFormData("cashflowanalysissave0001");
			model.addAttribute("cwCashFlowAnalysisList", cwCashFlowAnalysisList);
			model.addAttribute("tablecashflowanalysissave0001", tablecashflowanalysissave0001);
		} catch (Exception e) {
//			logger.error("toCashFlowAnalysisSave方法出错，执行action层失败，抛出异常，", e);
			throw e;
		}
		return "/component/finance/voucher/CwCashFlowAnalysis_Save";
	}

	/**
	 * 方法描述： AJAX 页面凭证新增
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Javelin
	 * @date 2016-12-30 上午10:57:40
	 */
	@RequestMapping(value = "/addVoucherAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addVoucherAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, Object> formMap = new Gson().fromJson(ajaxData, Map.class);
			// Map<String, String> formMap = CwPublicUtil.getMapByJson(ajaxData);
			String finBooks = (String)request.getSession().getAttribute("finBooks");
			formMap.put("regNo", User.getRegNo(request));
			formMap.put("regName", User.getRegName(request));
			 R r = cwVoucherMstFeign.addVoucher(formMap,finBooks);
			if(r.isOk()) {
				if ("0000".equals(r.getResult())) {
					dataMap.put("flag", "success");
					dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
				} else {
					dataMap.put("flag", "error");
					dataMap.put("msg", this.getFormulavaliErrorMsg());
				}
			}else {
				dataMap.put("flag", "error");
				dataMap.put("msg", r.getMsg());
			}
		} catch (Exception e) {
			dataMap.put("flag", "error");
			// dataMap.put("msg", "新增凭证失败");
			dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
			e.printStackTrace();
			throw e;
		}
		return dataMap;
	}

	/**
	 * AJAX 获取凭证详情数据
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getVoucherByNoAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getVoucherByNoAjax(String voucherNo, String which) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();

		String finBooks = (String)request.getSession().getAttribute("finBooks");
		formData = cwVoucherMstFeign.getVoucherByNo(voucherNo, which,finBooks,User.getRegName(request));

		if (formData != null) {
			dataMap.put("flag", "success");
		} else {
			dataMap.put("flag", "error");
		}
		dataMap.put("formData", formData);
		System.out.println(new Gson().toJson(dataMap));
		return dataMap;
	}

	/**
	 * 方法描述： 进入凭证打印页面
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Javelin
	 * @date 2017-3-15 上午11:01:35
	 */
	@RequestMapping(value = "/toVoucherPrint")
	public String toVoucherPrint(Model model, String voucherNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();

		String finBooks = (String)request.getSession().getAttribute("finBooks");
		List<Map<String, Object>> list = cwVoucherMstFeign.getVchPrintData(voucherNo,finBooks,User.getOrgName(request));

		dataMap.put("list", list);
		model.addAttribute("dataMap", dataMap);
		return "/component/finance/voucher/voucher_print";
	}

	/**
	 * 方法描述： 获取凭证打印数据
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Javelin
	 * @date 2017-3-14 下午4:22:31
	 */
	@RequestMapping(value = "/getVchPrintDataAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getVchPrintDataAjax(String voucherNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();

		String finBooks = (String)request.getSession().getAttribute("finBooks");
		List<Map<String, Object>> list = cwVoucherMstFeign.getVchPrintData(voucherNo,finBooks,User.getOrgName(request));

		if (list != null && list.size() > 0) {
			dataMap.put("flag", "success");
		} else {
			dataMap.put("flag", "error");
		}
		dataMap.put("data", list);
		return dataMap;
	}

	/**
	 * 方法描述： 根据日期与凭证字获取最大凭证字号
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Javelin
	 * @date 2017-2-17 下午3:03:23
	 */
	@RequestMapping(value = "/getVchMaxNoteNoAjax")
	@ResponseBody
	public Map<String, Object> getVchMaxNoteNoAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, String> formMap = new Gson().fromJson(ajaxData, Map.class);
			String finBooks = (String)request.getSession().getAttribute("finBooks");
			R r = cwVoucherMstFeign.getVchMaxNoteNo(formMap,finBooks);
			if(r.isOk()) {
				Map<String, Object> formData = (Map<String, Object>) r.getResult();
				dataMap.put("vchData", formData);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
			}else {
				dataMap.put("flag", "error");
				dataMap.put("msg", r.getMsg());
			}
		} catch (Exception e) {
			dataMap.put("flag", "error");
			// dataMap.put("msg", "新增凭证失败");
			dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
			e.printStackTrace();
			throw e;
		}
		return dataMap;
	}

	/**
	 * AJAX 凭证信息更新保存
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateVoucherAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateVoucherAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		String result = "0000";
		try {
			Map<String, Object> formMap = new Gson().fromJson(ajaxData, Map.class);
			// Map<String, String> formMap = CwPublicUtil.getMapByJson(ajaxData);
			String finBooks = (String)request.getSession().getAttribute("finBooks");
			result = cwVoucherMstFeign.updateVoucher(formMap,finBooks);
			if ("0000".equals(result)) {
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_UPDATE.getMessage());
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_UPDATE.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * Ajax 凭证查询列表删除单个凭证
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAjax(String voucherNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		CwVoucherMst cwVoucherMst = new CwVoucherMst();
		cwVoucherMst.setVoucherNo(voucherNo);
		try {
			String finBooks = (String)request.getSession().getAttribute("finBooks");
			//!0:清理出纳日记账凭证数据
			R r = cwVoucherMstFeign.delete(cwVoucherMst, "1",finBooks);
			if(r.isOk()) {
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED.getMessage());
			}else {
				dataMap.put("flag", "error");
				dataMap.put("msg", r.getMsg());
			}
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * 方法描述： Ajax 凭证查询列表 批量删除
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Javelin
	 * @date 2017-1-6 下午4:31:39
	 */
	@RequestMapping(value = "/deleteBatchAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBatchAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, String> formMap = CwPublicUtil.getMapByJson(ajaxData);
			String finBooks = (String)request.getSession().getAttribute("finBooks");
			Map<String, String> reMap = cwVoucherMstFeign.deleteBatch(formMap,finBooks);
			dataMap.put("data", reMap);
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED.getMessage());
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR.getMessage());
//			logger.error("删除凭证出错", e);
			throw e;
		}
		return dataMap;
	}

	/**
	 * 方法描述： 凭证反审核
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Javelin
	 * @date 2017-1-6 下午4:22:01
	 */
	@RequestMapping(value = "/voucherRevAuditAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> voucherRevAuditAjax(String voucherNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		CwVoucherMst cwVoucherMst = new CwVoucherMst();
		cwVoucherMst.setVoucherNo(voucherNo);
		try {
			String finBooks = (String)request.getSession().getAttribute("finBooks");
			R r = cwVoucherMstFeign.doVoucherRevAudit(cwVoucherMst,finBooks);
			if(r.isOk()) {
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED.getMessage());
			}else {
				dataMap.put("flag", "error");
				dataMap.put("msg", r.getMsg());
			}
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * 
	 * 方法描述： 凭证整理：查询凭证信息
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 刘争帅
	 * @date 2017-1-6 下午1:35:48
	 */
	@RequestMapping(value = "/checkPzByFieldsAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> checkPzByFieldsAjax(String  weeks, String pzProofNo, String pzradioType) throws Exception {

		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, String> parmMap = new HashMap<String, String>();
			parmMap.put("weeks", weeks);
			parmMap.put("pzProofNo", pzProofNo);
			parmMap.put("pzradioType", pzradioType);
			String finBooks = (String)request.getSession().getAttribute("finBooks");
			List<Map<String, String>> listmap = cwVoucherMstFeign.getNoteNoInfoByMap(parmMap,finBooks);
			dataMap.put("listmap", listmap);
			dataMap.put("flag", "success");

		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * 
	 * 方法描述： 凭证整理:开始整理
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 刘争帅
	 * @date 2017-1-7 下午2:11:19
	 */
	@RequestMapping(value = "/dealpingzhengliAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> dealpingzhengliAjax(String  weeks, String pzProofNo, String pzradioType) throws Exception {

		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, String> parmMap = new HashMap<String, String>();
			parmMap.put("weeks", weeks);
			parmMap.put("pzProofNo", pzProofNo);
			parmMap.put("pzradioType", pzradioType);
			String finBooks = (String)request.getSession().getAttribute("finBooks");
			String result = cwVoucherMstFeign.doDealPingzheng(parmMap,finBooks);

			dataMap.put("result", result);
			dataMap.put("flag", "success");

		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR.getMessage());
			throw e;
		}

		return dataMap;
	}

	/**
	 * 
	 * 方法描述： 凭证整理：获取凭证字信息
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 刘争帅
	 * @date 2017-1-7 下午5:28:14
	 */
	@RequestMapping(value = "/getPzInfoAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getPzInfoAjax() throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			String finBooks = (String)request.getSession().getAttribute("finBooks");
			List<Map<String, String>> pzlistmap = cwVoucherMstFeign.getpzzInfo(finBooks);

			dataMap.put("pzlistmap", pzlistmap);
			dataMap.put("flag", "success");

		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR.getMessage());
			throw e;
		}

		return dataMap;
	}

	/**
	 * 方法描述： 凭证 批量反审核
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Javelin
	 * @date 2017-1-6 下午4:31:14
	 */
	@RequestMapping(value = "/voucherBatchRevAuditAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> voucherBatchRevAuditAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, String> formMap = CwPublicUtil.getMapByJson(ajaxData);
			String finBooks = (String)request.getSession().getAttribute("finBooks");
			R r = cwVoucherMstFeign.doVoucherBatchRevAudit(formMap,finBooks);
			if(r.isOk()) {
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED.getMessage());
			}else {
				dataMap.put("flag", "error");
				dataMap.put("msg", r.getMsg());
			}
			
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * 方法描述： 凭证审核
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Javelin
	 * @date 2017-1-6 下午4:22:01
	 */
	@RequestMapping(value = "/voucherAuditAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> voucherAuditAjax(String voucherNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		CwVoucherMst cwVoucherMst = new CwVoucherMst();
		cwVoucherMst.setVoucherNo(voucherNo);
		try {
			String finBooks = (String)request.getSession().getAttribute("finBooks");
			cwVoucherMstFeign.doVoucherAudit(cwVoucherMst,finBooks);
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED.getMessage());
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR.getMessage());
//			logger.error("凭证审核出错", e);
			throw e;
		}
		return dataMap;
	}

	/**
	 * 方法描述： 凭证 批量审核
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Javelin
	 * @date 2017-1-6 下午4:31:14
	 */
	@RequestMapping(value = "/voucherBatchAuditAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> voucherBatchAuditAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, String> formMap = CwPublicUtil.getMapByJson(ajaxData);
			String finBooks = (String)request.getSession().getAttribute("finBooks");
			cwVoucherMstFeign.doVoucherBatchAudit(formMap,finBooks);
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED.getMessage());
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR.getMessage());
//			logger.error("凭证审核出错", e);
			throw e;
		}
		return dataMap;
	}

	/**
	 * 现金流量分析的保存
	 */
	@RequestMapping(value = "/cashFlowAnalysisSaveAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> cashFlowAnalysisSaveAjax(String ajaxData) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			System.out.println();
			JSONObject jsonObject = JSONObject.fromObject(ajaxData);
			String finBooks = (String)request.getSession().getAttribute("finBooks");
			cwVoucherMstFeign.doSaveCashFlowAnalysis(jsonObject,finBooks);
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED_SAVE.getMessage());
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.SUCCEED_SAVE.getMessage());
//			logger.error("cashFlowAnalysisSaveAjax方法出错，执行action层失败，抛出异常，");
		}
		return dataMap;
	}

	/**
	 * 
	 * 方法描述： 跳转到凭证整理页面
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author lzshuai
	 * @date 2017-2-15 下午4:36:00
	 */
	@RequestMapping(value = "/goZhengliPage")
	public String goZhengliPage(Model model) throws Exception {
		ActionContext.initialize(request, response);
		try {
			String finBooks = (String)request.getSession().getAttribute("finBooks");
			String date = cwMonthKnotFeign.getMinNoCloseWeek(finBooks);
			// String date = DateUtil.getDate("yyyyMM");
			model.addAttribute("date", date);
		} catch (Exception e) {
//			logger.error("跳转到凭证整理页面出错，" + e.getMessage(), e);
			e.printStackTrace();
		}
		return "component/finance/voucher/pingzhengzhengli";
	}

}
