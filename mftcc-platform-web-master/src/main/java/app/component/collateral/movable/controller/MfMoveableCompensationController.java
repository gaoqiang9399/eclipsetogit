package app.component.collateral.movable.controller;

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

import app.component.collateral.movable.entity.MfMoveableCompensation;
import app.component.collateral.movable.entity.MfMoveableCompensationConfirm;
import app.component.collateral.movable.feign.MfMoveableCompensationConfirmFeign;
import app.component.collateral.movable.feign.MfMoveableCompensationFeign;
import app.component.common.EntityUtil;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;

/**
 * Title: MfMoveableCompensationAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Tue Jun 13 18:53:52 CST 2017
 **/
@Controller
@RequestMapping("/mfMoveableCompensation")
public class MfMoveableCompensationController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入MfMoveableCompensationBo
	@Autowired
	private MfMoveableCompensationFeign mfMoveableCompensationFeign;
	@Autowired
	private MfMoveableCompensationConfirmFeign mfMoveableCompensationConfirmFeign;
	// 全局变量
	// 异步参数
	// 表单变量

	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		 return "/component/collateral/movable/MfMoveableCompensation_List";
	}

	/***
	 * 列表数据查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
			String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfMoveableCompensation mfMoveableCompensation = new MfMoveableCompensation();
		try {
			mfMoveableCompensation.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfMoveableCompensation.setCriteriaList(mfMoveableCompensation, ajaxData);// 我的筛选
			// mfMoveableCompensation.setCustomSorts(ajaxData);//自定义排序
			// this.getRoleConditions(mfMoveableCompensation,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage = mfMoveableCompensationFeign.findByPage(ipage, mfMoveableCompensation);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			e.printStackTrace();
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
	@RequestMapping(value = "/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formmovablecompensation0002 = formService.getFormData("movablecompensation0002");
			getFormValue(formmovablecompensation0002, getMapByJson(ajaxData));
			if (this.validateFormData(formmovablecompensation0002)) {
				MfMoveableCompensation mfMoveableCompensation = new MfMoveableCompensation();
				setObjValue(formmovablecompensation0002, mfMoveableCompensation);
				mfMoveableCompensationFeign.insert(mfMoveableCompensation);
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
	 * ajax 异步 单个字段或多个字段更新
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateAjaxByOne")
	@ResponseBody
	public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formmovablecompensation0002 = formService.getFormData("movablecompensation0002");
		getFormValue(formmovablecompensation0002, getMapByJson(ajaxData));
		MfMoveableCompensation mfMoveableCompensationJsp = new MfMoveableCompensation();
		setObjValue(formmovablecompensation0002, mfMoveableCompensationJsp);
		MfMoveableCompensation mfMoveableCompensation = mfMoveableCompensationFeign.getById(mfMoveableCompensationJsp);
		if (mfMoveableCompensation != null) {
			try {
				mfMoveableCompensation = (MfMoveableCompensation) EntityUtil.reflectionSetVal(mfMoveableCompensation,
						mfMoveableCompensationJsp, getMapByJson(ajaxData));
				mfMoveableCompensationFeign.update(mfMoveableCompensation);
				dataMap.put("flag", "success");
				dataMap.put("msg", "保存成功");
			} catch (Exception e) {
				e.printStackTrace();
				dataMap.put("flag", "error");
				dataMap.put("msg", "新增失败");
				throw e;
			}
		} else {
			dataMap.put("flag", "error");
			dataMap.put("msg", "编号不存在,保存失败");
		}
		return dataMap;
	}

	/**
	 * AJAX更新保存
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateAjax")
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formmovablecompensation0002 = formService.getFormData("movablecompensation0002");
			getFormValue(formmovablecompensation0002, getMapByJson(ajaxData));
			if (this.validateFormData(formmovablecompensation0002)) {
				MfMoveableCompensation mfMoveableCompensation = new MfMoveableCompensation();
				setObjValue(formmovablecompensation0002, mfMoveableCompensation);
				mfMoveableCompensationFeign.update(mfMoveableCompensation);
				dataMap.put("flag", "success");
				dataMap.put("msg", "更新成功");
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "更新失败");
			throw e;
		}
		return dataMap;
	}

	/**
	 * AJAX获取查看
	 * 
	 * @param compensationId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String ajaxData, String compensationId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formmovablecompensation0002 = formService.getFormData("movablecompensation0002");
		MfMoveableCompensation mfMoveableCompensation = new MfMoveableCompensation();
		mfMoveableCompensation.setCompensationId(compensationId);
		mfMoveableCompensation = mfMoveableCompensationFeign.getById(mfMoveableCompensation);
		getObjValue(formmovablecompensation0002, mfMoveableCompensation, formData);
		if (mfMoveableCompensation != null) {
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
	 * @param compensationId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String ajaxData, String compensationId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfMoveableCompensation mfMoveableCompensation = new MfMoveableCompensation();
		mfMoveableCompensation.setCompensationId(compensationId);
		try {
			mfMoveableCompensationFeign.delete(mfMoveableCompensation);
			dataMap.put("flag", "success");
			dataMap.put("msg", "成功");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			dataMap.put("msg", "失败");
			throw e;
		}
		return dataMap;
	}

	/**
	 * 新增页面
	 * 
	 * @param busPleId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/input")
	public String input(Model model, String busPleId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		// 判断是否存在跌价补偿未确认
		MfMoveableCompensation mfMoveableCompensation = new MfMoveableCompensation();

		mfMoveableCompensation.setBusPleId(busPleId);
		String confirmFlag = mfMoveableCompensationFeign.input(mfMoveableCompensation);
		if ("1".equals(confirmFlag)) {
			FormData formmovablecompensationconf0002 = formService.getFormData("movablecompensationconf0002");
			MfMoveableCompensationConfirm mfMoveableCompensationConfirm = new MfMoveableCompensationConfirm();
			mfMoveableCompensationConfirm.setBusPleId(busPleId);
			mfMoveableCompensationConfirm = mfMoveableCompensationConfirmFeign.input(mfMoveableCompensationConfirm);
			getObjValue(formmovablecompensationconf0002, mfMoveableCompensationConfirm);
			model.addAttribute("formmovablecompensationconf0002", formmovablecompensationconf0002);
			model.addAttribute("query", "");
			 return "/component/collateral/movable/MfMoveableCompensationConfirm_Insert";
		} else {
			FormData formmovablecompensation0002 = formService.getFormData("movablecompensation0002");
			getObjValue(formmovablecompensation0002, mfMoveableCompensation);
			model.addAttribute("formmovablecompensation0002", formmovablecompensation0002);
			model.addAttribute("query", "");
			 return "/component/collateral/movable/MfMoveableCompensation_Insert";
		}

	}

	/***
	 * 新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insert")
	public String insert(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formmovablecompensation0002 = formService.getFormData("movablecompensation0002");
		getFormValue(formmovablecompensation0002);
		MfMoveableCompensation mfMoveableCompensation = new MfMoveableCompensation();
		setObjValue(formmovablecompensation0002, mfMoveableCompensation);
		mfMoveableCompensationFeign.insert(mfMoveableCompensation);
		getObjValue(formmovablecompensation0002, mfMoveableCompensation);
		this.addActionMessage(model, "保存成功");
		List<MfMoveableCompensation> mfMoveableCompensationList = (List<MfMoveableCompensation>) mfMoveableCompensationFeign
				.findByPage(this.getIpage(), mfMoveableCompensation).getResult();
		model.addAttribute("mfMoveableCompensationList", mfMoveableCompensationList);
		model.addAttribute("formmovablecompensation0002", formmovablecompensation0002);
		model.addAttribute("query", "");
		 return "/component/collateral/movable/MfMoveableCompensation_Insert";
	}

	/**
	 * 查询
	 * @param compensationId 
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String ajaxData, String compensationId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formmovablecompensation0001 = formService.getFormData("movablecompensation0001");
		getFormValue(formmovablecompensation0001);
		MfMoveableCompensation mfMoveableCompensation = new MfMoveableCompensation();
		mfMoveableCompensation.setCompensationId(compensationId);
		mfMoveableCompensation = mfMoveableCompensationFeign.getById(mfMoveableCompensation);
		getObjValue(formmovablecompensation0001, mfMoveableCompensation);
		model.addAttribute("formmovablecompensation0001", formmovablecompensation0001);
		model.addAttribute("query", "");
		 return "/component/collateral/movable/MfMoveableCompensation_Detail";
	}

	/**
	 * 删除
	 * @param compensationId 
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model, String ajaxData, String compensationId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		MfMoveableCompensation mfMoveableCompensation = new MfMoveableCompensation();
		mfMoveableCompensation.setCompensationId(compensationId);
		mfMoveableCompensationFeign.delete(mfMoveableCompensation);
		return getListPage(model);
	}


}