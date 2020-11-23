package app.component.oa.debt.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;

import app.base.User;
import app.component.common.DateUtil;
import app.component.common.EntityUtil;
import app.component.oa.debt.entity.MfOaDebtReturnHis;
import app.component.oa.debt.feign.MfOaDebtFeign;
import app.component.oa.debt.feign.MfOaDebtReturnHisFeign;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.WaterIdUtil;

/**
 * Title: MfOaDebtReturnHisAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Thu Dec 22 10:32:09 CST 2016
 **/
@Controller
@RequestMapping("/mfOaDebtReturnHis")
public class MfOaDebtReturnHisController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfOaDebtReturnHisFeign mfOaDebtReturnHisFeign;
	@Autowired
	private MfOaDebtFeign mfOaDebtFeign;
	

	/**
	 * 列表打开页面请求
	 * 
	 * @param model
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request, response);
		model.addAttribute("query", "");
		return "/component/oa/debt/MfOaDebtReturnHis_List";
	}

	/***
	 * 列表数据查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/findByPageAjax")
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
			String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			MfOaDebtReturnHis mfOaDebtReturnHis = new MfOaDebtReturnHis();
			mfOaDebtReturnHis.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfOaDebtReturnHis.setCriteriaList(mfOaDebtReturnHis, ajaxData);// 我的筛选
			// this.getRoleConditions(mfOaDebtReturnHis,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage.setParams(this.setIpageParams("mfOaDebtReturnHis", mfOaDebtReturnHis));
			ipage = mfOaDebtReturnHisFeign.findByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
			/**
			 * ipage.setResult(tableHtml); dataMap.put("ipage",ipage); 需要改进的方法
			 * dataMap.put("tableData",tableHtml);
			 */
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
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
	@ResponseBody
	@RequestMapping("/insertAjax")
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formreturndebt0002 = new FormService().getFormData("returndebt0002");
			getFormValue(formreturndebt0002, getMapByJson(ajaxData));
			if (this.validateFormData(formreturndebt0002)) {
				MfOaDebtReturnHis mfOaDebtReturnHis = new MfOaDebtReturnHis();
				setObjValue(formreturndebt0002, mfOaDebtReturnHis);
				BigDecimal sumAmt[] = mfOaDebtFeign.sumAmt(User.getRegNo(request));
				System.out.println("mfOaDebtReturnHis.getReturnAmt()" + mfOaDebtReturnHis.getReturnAmt());
				System.out.println("sumAmt[0].subtract(sumAmt[1]))" + sumAmt[0].subtract(sumAmt[1]));
				if (mfOaDebtReturnHis.getReturnAmt().compareTo(sumAmt[0].subtract(sumAmt[1])) == -1
						|| mfOaDebtReturnHis.getReturnAmt().compareTo(sumAmt[0].subtract(sumAmt[1])) == 0) {
					mfOaDebtReturnHisFeign.insert(mfOaDebtReturnHis, User.getRegNo(request));

					dataMap.put("flag", "success");
					dataMap.put("msg", MessageEnum.SUCCEED_OPERATION.getMessage("还款"));
				} else {
					dataMap.put("flag", "error");
					Map<String, String> paramMap = new HashMap<String, String>();
					paramMap.put("timeOne", "还款金额");
					paramMap.put("timeTwo", "欠款金额");
					dataMap.put("msg", MessageEnum.NOT_FORM_TIME.getMessage(paramMap));
				}
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
	 * ajax 异步 单个字段或多个字段更新
	 * 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/updateAjaxByOne")
	public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formreturndebt0002 = new FormService().getFormData("returndebt0002");
		getFormValue(formreturndebt0002, getMapByJson(ajaxData));
		MfOaDebtReturnHis mfOaDebtReturnHisJsp = new MfOaDebtReturnHis();
		setObjValue(formreturndebt0002, mfOaDebtReturnHisJsp);
		MfOaDebtReturnHis mfOaDebtReturnHis = mfOaDebtReturnHisFeign.getById(mfOaDebtReturnHisJsp);
		if (mfOaDebtReturnHis != null) {
			try {
				mfOaDebtReturnHis = (MfOaDebtReturnHis) EntityUtil.reflectionSetVal(mfOaDebtReturnHis,
						mfOaDebtReturnHisJsp, getMapByJson(ajaxData));
				mfOaDebtReturnHisFeign.update(mfOaDebtReturnHis);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_SAVE.getMessage());
			} catch (Exception e) {
				dataMap.put("flag", "error");
				dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
				throw e;
			}
		} else {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_SAVE_CONTENT.getMessage("编号不存在"));
		}
		return dataMap;
	}

	/**
	 * AJAX更新保存
	 * 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/updateAjax")
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			MfOaDebtReturnHis mfOaDebtReturnHis = new MfOaDebtReturnHis();
			FormData formreturndebt0002 = new FormService().getFormData("returndebt0002");
			getFormValue(formreturndebt0002, getMapByJson(ajaxData));
			if (this.validateFormData(formreturndebt0002)) {
				mfOaDebtReturnHis = new MfOaDebtReturnHis();
				setObjValue(formreturndebt0002, mfOaDebtReturnHis);
				mfOaDebtReturnHisFeign.update(mfOaDebtReturnHis);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_UPDATE.getMessage());
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
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
	@ResponseBody
	@RequestMapping("/getByIdAjax")
	public Map<String, Object> getByIdAjax(String hisId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formreturndebt0002 = new FormService().getFormData("returndebt0002");
		MfOaDebtReturnHis mfOaDebtReturnHis = new MfOaDebtReturnHis();
		mfOaDebtReturnHis.setHisId(hisId);
		mfOaDebtReturnHis = mfOaDebtReturnHisFeign.getById(mfOaDebtReturnHis);
		getObjValue(formreturndebt0002, mfOaDebtReturnHis, formData);
		if (mfOaDebtReturnHis != null) {
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
	@ResponseBody
	@RequestMapping("/deleteAjax")
	public Map<String, Object> deleteAjax(String hisId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			MfOaDebtReturnHis mfOaDebtReturnHis = new MfOaDebtReturnHis();
			mfOaDebtReturnHis.setHisId(hisId);
			mfOaDebtReturnHisFeign.delete(mfOaDebtReturnHis);
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED.getMessage());
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * 新增页面
	 * 
	 * @param model
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/input")
	public String input(Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormData formreturndebt0002 = new FormService().getFormData("returndebt0002");
		model.addAttribute("formreturndebt0002", formreturndebt0002);
		model.addAttribute("query", "");
		return "/component/oa/debt/MfOaDebtReturnHis_Insert";
	}

	/***
	 * 新增
	 * 
	 * @param model
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/insert")
	public String insert(Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormData formreturndebt0002 = new FormService().getFormData("returndebt0002");
		getFormValue(formreturndebt0002);
		MfOaDebtReturnHis mfOaDebtReturnHis = new MfOaDebtReturnHis();
		setObjValue(formreturndebt0002, mfOaDebtReturnHis);
		mfOaDebtReturnHisFeign.insert(mfOaDebtReturnHis);
		getObjValue(formreturndebt0002, mfOaDebtReturnHis);
		this.addActionMessage(model, "保存成功");
		Ipage ipage = this.getIpage();
		ipage.setParams(this.setIpageParams("mfOaDebtReturnHis", mfOaDebtReturnHis));
		List<MfOaDebtReturnHis> mfOaDebtReturnHisList = (List<MfOaDebtReturnHis>) mfOaDebtReturnHisFeign
				.findByPage(ipage).getResult();
		model.addAttribute("formreturndebt0002", formreturndebt0002);
		model.addAttribute("mfOaDebtReturnHisList", mfOaDebtReturnHisList);
		model.addAttribute("mfOaDebtReturnHis", mfOaDebtReturnHis);
		model.addAttribute("query", "");
		return "/component/oa/debt/MfOaDebtReturnHis_Insert";
	}

	/**
	 * 查询
	 * 
	 * @param model
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getById")
	public String getById(Model model) throws Exception {
		ActionContext.initialize(request, response);
		String opNo = User.getRegNo(request);
		BigDecimal sumAmt[] = mfOaDebtFeign.sumAmt(opNo);
		FormData formreturndebt0002 = new FormService().getFormData("returndebt0002");
		MfOaDebtReturnHis mfOaDebtReturnHis = new MfOaDebtReturnHis();
		mfOaDebtReturnHis.setHisId(WaterIdUtil.getWaterId());
		mfOaDebtReturnHis.setSumApplyAmt((sumAmt[0].subtract(sumAmt[1])).doubleValue());
		mfOaDebtReturnHis.setRegTime(DateUtil.getDateTime());
		mfOaDebtReturnHis.setReturnAmt((sumAmt[0].subtract(sumAmt[1])).doubleValue());
		mfOaDebtReturnHis.setBrName(User.getOrgName(request));
		mfOaDebtReturnHis.setOpName(User.getRegName(request));
		getObjValue(formreturndebt0002, mfOaDebtReturnHis);
		model.addAttribute("mfOaDebtReturnHis", mfOaDebtReturnHis);
		model.addAttribute("formreturndebt0002", formreturndebt0002);
		model.addAttribute("query", "");
		return "/component/oa/debt/MfOaDebtReturnHis_Detail";
	}

	/**
	 * 查询
	 * 
	 * @param model
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getByRealId")
	public String getByRealId(String hisId, Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormData formreturndebt00003 = new FormService().getFormData("returndebt00003");
		MfOaDebtReturnHis mfOaDebtReturnHis = new MfOaDebtReturnHis();
		mfOaDebtReturnHis.setHisId(hisId);
		mfOaDebtReturnHis = mfOaDebtReturnHisFeign.getById(mfOaDebtReturnHis);
		CodeUtils codeUtils = new CodeUtils();
		Map<String, String> map = codeUtils.getMapByKeyName("PAY_TYPE");
		mfOaDebtReturnHis.setReturnType(map.get(mfOaDebtReturnHis.getReturnType()));
		mfOaDebtReturnHis.setBrName(User.getOrgName(request));
		mfOaDebtReturnHis.setOpName(User.getRegName(request));
		getObjValue(formreturndebt00003, mfOaDebtReturnHis);
		model.addAttribute("mfOaDebtReturnHis", mfOaDebtReturnHis);
		model.addAttribute("formreturndebt00003", formreturndebt00003);
		model.addAttribute("query", "");
		return "/component/oa/debt/MfOaDebtReturnHis_Find";
	}

	/**
	 * 删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delete")
	public String delete(String hisId, Model model) throws Exception {
		ActionContext.initialize(request, response);
		MfOaDebtReturnHis mfOaDebtReturnHis = new MfOaDebtReturnHis();
		mfOaDebtReturnHis.setHisId(hisId);
		mfOaDebtReturnHisFeign.delete(mfOaDebtReturnHis);
		return getListPage(model);
	}

	/**
	 * 新增校验
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/validateInsert")
	public void validateInsert() throws Exception {
		ActionContext.initialize(request, response);
		FormData formreturndebt0002 = new FormService().getFormData("returndebt0002");
		getFormValue(formreturndebt0002);
		boolean validateFlag = this.validateFormData(formreturndebt0002);
	}

	/**
	 * 修改校验
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/validateUpdate")
	public void validateUpdate() throws Exception {
		ActionContext.initialize(request, response);
		FormData formreturndebt0002 = new FormService().getFormData("returndebt0002");
		getFormValue(formreturndebt0002);
		boolean validateFlag = this.validateFormData(formreturndebt0002);
	}

}
