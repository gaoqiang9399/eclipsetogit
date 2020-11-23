package app.component.finance.menthed.controller;

import java.util.HashMap;
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
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import app.component.finance.menthed.entity.CwLedgerMst;
import app.component.finance.menthed.feign.CwLedgerMstFeign;
import app.component.finance.util.CwPublicUtil;
import app.component.finance.util.R;
import cn.mftcc.common.MessageEnum;
import net.sf.json.JSONObject;

/**
 * Title: CwLedgerMstAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Mon Jan 09 09:38:45 CST 2017
 **/
@Controller
@RequestMapping("/cwMenthEnd")
public class CwMenthEndController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入cwComItemFeign
	@Autowired
	private CwLedgerMstFeign cwLedgerMstFeign;

	// 表单变量
	private FormData formmenthed0002;
	private FormService formService = new FormService();

	/**
	 * 方法描述： 进入结账页面
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Javelin
	 * @date 2017-1-9 上午10:05:18
	 */
	@RequestMapping(value = "/CwMendthEnd")
	public String CwMendthEnd(Model model) throws Exception {
		ActionContext.initialize(request, response);
		String finBooks = (String)request.getSession().getAttribute("finBooks");
		Map<String, Object> dataMap = cwLedgerMstFeign.getInitCwMonthedData(finBooks);
		model.addAttribute("dataMap", dataMap);
		return "/component/finance/menthed/CwMendthEnd";
	}

	/**
	 * 方法描述： AJAX 获取结账进行数据数据
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Javelin
	 * @date 2017-2-24 下午3:38:49
	 */
	@RequestMapping(value = "/getCwCloseNextFlagAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getCwCloseNextFlagAjax() throws Exception {
		ActionContext.initialize(request, response);
		String finBooks = (String)request.getSession().getAttribute("finBooks");
		Map<String, Object> dataMap = cwLedgerMstFeign.getCwCloseNextFlag(finBooks);
		dataMap.put("flag", "success");
		return dataMap;
	}

	/**
	 * 方法描述： 生成月结凭证
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Javelin
	 * @date 2017-1-12 上午11:39:50
	 */
	@RequestMapping(value = "/createMonthVchAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> createMonthVchAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, String> formMap = CwPublicUtil.getMapByJson(ajaxData);
			String finBooks = (String)request.getSession().getAttribute("finBooks");
			R r = cwLedgerMstFeign.doCreateMonthVch(formMap,finBooks);
			if(r.isOk()) {
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
				dataMap.put("voucher", r.getResult());
			}else {
				dataMap.put("flag", "error");
				dataMap.put("msg", r.getMsg());
			}

		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", "新增凭证失败");
			e.printStackTrace();
			throw e;
		}
		return dataMap;
	}

	/**
	 * 方法描述： 月末结账到下期
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Javelin
	 * @date 2017-1-17 上午11:22:50
	 */
	@RequestMapping(value = "/closeToNextMonthAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> closeToNextMonthAjax() throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			String finBooks = (String)request.getSession().getAttribute("finBooks");
			R r = cwLedgerMstFeign.doCloseToNextMonth(finBooks);
			if(r.isOk()) {
				dataMap.put("flag", "success");
				// dataMap.put("msg", "月末结账成功");
				dataMap.put("msg", MessageEnum.SUCCEED_OPERATION.getMessage("月末结账"));
			}else {
				dataMap.put("flag", "error");
				dataMap.put("msg", r.getMsg());
			}
		} catch (Exception e) {
			dataMap.put("flag", "error");
			// dataMap.put("msg", "月末结账失败");FAILED_OPERATION
			dataMap.put("msg", MessageEnum.FAILED_OPERATION.getMessage("月末结账"));
			e.printStackTrace();
			throw e;
		}
		return dataMap;
	}

	/**
	 * 方法描述： 反结账到上一期
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Javelin
	 * @date 2017-2-9 上午9:12:48
	 */
	@RequestMapping(value = "/reCloseToPrevMonthAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> reCloseToPrevMonthAjax() throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			String finBooks = (String)request.getSession().getAttribute("finBooks");
			R r = cwLedgerMstFeign.doReCloseToPrevMonth(finBooks);
			if(r.isOk()) {
				dataMap.put("flag", "success");
				// dataMap.put("msg", " 反结账到上一期成功");
				dataMap.put("msg", MessageEnum.SUCCEED_OPERATION.getMessage("反结账到上一期"));
			}else {
				dataMap.put("flag", "error");
				dataMap.put("msg", r.getMsg());
			}

		} catch (Exception e) {
			dataMap.put("flag", "error");
			// dataMap.put("msg", " 反结账到上一期失败");
			dataMap.put("msg", MessageEnum.FAILED_OPERATION.getMessage("反结账到上一期"));
			e.printStackTrace();
			throw e;
		}
		return dataMap;
	}

	/**
	 * AJAX新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			formmenthed0002 = formService.getFormData("menthed0002");
			getFormValue(formmenthed0002, getMapByJson(ajaxData));
			if (this.validateFormData(formmenthed0002)) {
				CwLedgerMst cwLedgerMst = new CwLedgerMst();
				setObjValue(formmenthed0002, cwLedgerMst);
				cwLedgerMstFeign.insert(cwLedgerMst);
				// getTableData();// 获取列表
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * AJAX更新保存
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			formmenthed0002 = formService.getFormData("menthed0002");
			getFormValue(formmenthed0002, getMapByJson(ajaxData));
			if (this.validateFormData(formmenthed0002)) {
				CwLedgerMst cwLedgerMst = new CwLedgerMst();
				setObjValue(formmenthed0002, cwLedgerMst);
				String finBooks = (String) request.getSession().getAttribute("finBooks");
				cwLedgerMstFeign.update(cwLedgerMst,finBooks);
				// getTableData();// 获取列表
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
	 * AJAX获取查看
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getByIdAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getByIdAjax(String accHrt) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		formmenthed0002 = formService.getFormData("menthed0002");
		CwLedgerMst cwLedgerMst = new CwLedgerMst();
		cwLedgerMst.setAccHrt(accHrt);
		String finBooks = (String) request.getSession().getAttribute("finBooks");
		cwLedgerMst = cwLedgerMstFeign.getById(cwLedgerMst,finBooks);
		getObjValue(formmenthed0002, cwLedgerMst, formData);
		if (cwLedgerMst != null) {
			dataMap.put("flag", "success");
		} else {
			dataMap.put("flag", "error");
		}
		dataMap.put("formData", formData);
		return dataMap;
	}

	/**
	 * Ajax异步删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAjax(String accHrt, String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		CwLedgerMst cwLedgerMst = new CwLedgerMst();
		cwLedgerMst.setAccHrt(accHrt);
		try {
			JSONObject jb = JSONObject.fromObject(ajaxData);
			cwLedgerMst = (CwLedgerMst) JSONObject.toBean(jb, CwLedgerMst.class);
			String finBooks = (String) request.getSession().getAttribute("finBooks");
			cwLedgerMstFeign.delete(cwLedgerMst,finBooks);
			// getTableData();// 获取列表
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * 
	 * 方法描述： 年结获取数据分配数据
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author lzshuai
	 * @date 2017-10-12 下午4:31:25
	 */
	@RequestMapping(value = "/getYearDataFpAndTqAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getYearDataFpAndTqAjax() throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			// Map<String, String> formMap = CwPublicUtil.getMapByJson(ajaxData);
			Map<String, String> formMap = new HashMap<String, String>();
			String finBooks = (String)request.getSession().getAttribute("finBooks");
			R r = cwLedgerMstFeign.getYearPz(formMap,finBooks);
			if(r.isOk()) {
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
				dataMap.put("voucher", r.getResult());
			}else {
				dataMap.put("flag", "error");
				dataMap.put("msg", r.getMsg());
			}

		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", "新增凭证失败");
			e.printStackTrace();
			throw e;
		}
		return dataMap;
	}

	/**
	 * 
	 * 方法描述： 获取本年科目利润余额
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author lzshuai
	 * @date 2017-10-12 下午4:32:26
	 */
	@RequestMapping(value = "/getYearKemuBalAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getYearKemuBalAjax() throws Exception {

		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			String finBooks = (String)request.getSession().getAttribute("finBooks");
			R r = cwLedgerMstFeign.getYearKemuBal(finBooks);
			if(r.isOk()) {
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
				dataMap.put("yearMap", new Gson().toJson(r.getResult()));
			}else {
				dataMap.put("flag", "error");
				dataMap.put("msg", r.getMsg());
			}
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", "新增凭证失败");
			e.printStackTrace();
			throw e;
		}
		return dataMap;
	}

	/**
	 * 
	 * 方法描述：
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author lzshuai
	 * @date 2017-10-12 下午5:33:02
	 */
	@RequestMapping(value = "/getfptqAmtAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getfptqAmtAjax(String wfplrBal, String fdyygjAmt, String ybfxzbAmt, String ryyygjAmt,
			String yfglAmt) throws Exception {

		ActionContext.initialize(request, response);
		// 获取计提中需要的信息

		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			R r = cwLedgerMstFeign.getfptqAmt(wfplrBal, fdyygjAmt, ybfxzbAmt, ryyygjAmt,yfglAmt);
			
			if(r.isOk()) {
				Map<String, String> amtmap =(Map<String, String>) r.getResult();
				dataMap.put("amtmap", new Gson().toJson(amtmap));
				dataMap.put("flag", "success");
				dataMap.put("msg", "成功");
			}else {
				dataMap.put("flag", "error");
				dataMap.put("msg", r.getMsg());
			}
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", "失败");
			throw new Exception(e.getMessage());
		}

		return dataMap;
	}

	/**
	 * 
	 * 方法描述： 生成年结利润分配凭证
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author lzshuai
	 * @date 2017-10-12 下午7:48:17
	 */
	@RequestMapping(value = "/createfptqpzAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> createfptqpzAjax(String fdyygjAmt, String ybfxzbAmt, String ryyygjAmt, String yfglAmt,
			String yearVchDate, String yearVchZi) throws Exception {

		ActionContext.initialize(request, response);
		// 获取计提中需要的信息

		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, String> tmap = new HashMap<String, String>();
			tmap.put("fdyygjAmt", fdyygjAmt);
			tmap.put("ybfxzbAmt", ybfxzbAmt);
			tmap.put("ryyygjAmt", ryyygjAmt);
			tmap.put("yfglAmt", yfglAmt);

			tmap.put("yearVchDate", yearVchDate);
			tmap.put("yearVchZi", yearVchZi);
			String finBooks = (String)request.getSession().getAttribute("finBooks");
			R r = cwLedgerMstFeign.doCreatefptqPz(tmap,finBooks);
			
			if(r.isOk()) {
				Map<String, String> amtmap =(Map<String, String>) r.getResult();
				dataMap.put("amtmap", new Gson().toJson(amtmap));
				dataMap.put("flag", "success");
				dataMap.put("msg", "成功");
			}else {
				dataMap.put("flag", "error");
				dataMap.put("msg", r.getMsg());
			}
			
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", "失败");
			throw new Exception(e.getMessage());
		}

		return dataMap;
	}

}
