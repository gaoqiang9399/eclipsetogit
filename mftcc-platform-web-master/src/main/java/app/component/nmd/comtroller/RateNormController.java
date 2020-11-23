package app.component.nmd.comtroller;

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
import app.component.common.BizPubParm;
import app.component.common.EntityUtil;
import app.component.nmd.entity.RateNorm;
import app.component.nmd.feign.RateNormFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;

/**
 * Title: RateNormAction.java Description:
 * 
 * @author:@dhcc.com.cn
 * @Tue May 17 03:02:13 GMT 2016
 **/
@Controller
@RequestMapping("/rateNorm")
public class RateNormController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入RateNormBo
	@Autowired
	private RateNormFeign rateNormFeign;

	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request, response);
		model.addAttribute("query", "");
		return "component/nmd/RateNorm_List";
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
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		RateNorm rateNorm = new RateNorm();
		try {
			rateNorm.setCustomQuery(ajaxData);// 自定义查询参数赋值
			rateNorm.setCriteriaList(rateNorm, ajaxData);// 我的筛选
			// this.getRoleConditions(rateNorm,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage = rateNormFeign.findByPage(ipage, rateNorm);
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
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPageForView")
	public String getListPageForView(Model model, String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		model.addAttribute("query", "");
		return "component/nmd/RateNorm_ListForView";
	}

	/***
	 * 列表数据查询
	 * @param tableId 
	 * @param tableType 
	 * @param pageSize 
	 * @param pageNo 
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPageForViewAjax")
	@ResponseBody
	public Map<String, Object> findByPageForViewAjax(String ajaxData, String tableId, String tableType, Integer pageSize, Integer pageNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		RateNorm rateNorm = new RateNorm();
		try {
			rateNorm.setCustomQuery(ajaxData);// 自定义查询参数赋值
			rateNorm.setCriteriaList(rateNorm, ajaxData);// 我的筛选
			rateNorm.setSts(BizPubParm.RATE_STS_EFFECT); // 生效
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage = rateNormFeign.findByPage(ipage, rateNorm);
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
			FormData formnmd0002 = formService.getFormData("nmd0002");
			getFormValue(formnmd0002, getMapByJson(ajaxData));
			if (this.validateFormData(formnmd0002)) {
				RateNorm rateNorm = new RateNorm();
				setObjValue(formnmd0002, rateNorm);
				rateNormFeign.insert(rateNorm);
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
		FormData formnmd0002 = formService.getFormData("nmd0002");
		getFormValue(formnmd0002, getMapByJson(ajaxData));
		RateNorm rateNormJsp = new RateNorm();
		setObjValue(formnmd0002, rateNormJsp);
		RateNorm rateNorm = rateNormFeign.getById(rateNormJsp);
		if (rateNorm != null) {
			try {
				rateNorm = (RateNorm) EntityUtil.reflectionSetVal(rateNorm, rateNormJsp, getMapByJson(ajaxData));
				rateNormFeign.update(rateNorm);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_SAVE.getMessage());
			} catch (Exception e) {
				e.printStackTrace();
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
	@RequestMapping(value = "/updateAjax")
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formnmd0002 = formService.getFormData("nmd0002");
			getFormValue(formnmd0002, getMapByJson(ajaxData));
			if (this.validateFormData(formnmd0002)) {
				RateNorm rateNorm = new RateNorm();
				setObjValue(formnmd0002, rateNorm);
				rateNormFeign.update(rateNorm);
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
	@RequestMapping(value = "/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String rateNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formnmd0002 = formService.getFormData("nmd0002");
		RateNorm rateNorm = new RateNorm();
		rateNorm.setRateNo(rateNo);
		rateNorm = rateNormFeign.getById(rateNorm);
		getObjValue(formnmd0002, rateNorm, formData);
		if (rateNorm != null) {
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
	@RequestMapping(value = "/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String rateNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		RateNorm rateNorm = new RateNorm();
		rateNorm.setRateNo(rateNo);
		try {
			rateNormFeign.delete(rateNorm);
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
	 * 新增页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/input")
	public String input(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formnmd0003 = formService.getFormData("nmd0003");
		RateNorm rateNorm = new RateNorm();
		rateNorm.setBrNo(User.getOrgNo(this.getHttpRequest()));
		rateNorm.setOpNo(User.getRegNo(this.getHttpRequest()));
		rateNorm.setTxDate(User.getSysDate(this.getHttpRequest()));
		rateNorm.setUpDate(User.getSysDate(this.getHttpRequest()));
		getObjValue(formnmd0003, rateNorm);
		model.addAttribute("formnmd0003", formnmd0003);
		model.addAttribute("query", "");
		return "component/nmd/RateNorm_Insert";
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
		FormData formnmd0003 = formService.getFormData("nmd0003");
		getFormValue(formnmd0003);
		RateNorm rateNorm = new RateNorm();
		setObjValue(formnmd0003, rateNorm);
		rateNormFeign.insert(rateNorm);
		getObjValue(formnmd0003, rateNorm);
		this.addActionMessage(model, "保存成功");
		// rateNormList = (List<RateNorm>)rateNormFeign.findByPage(this.getIpage(),
		// rateNorm).getResult();
		model.addAttribute("formnmd0003", formnmd0003);
		model.addAttribute("query", "");
		return "component/nmd/RateNorm_Update";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String rateNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formnmd0003 = formService.getFormData("nmd0003");
		getFormValue(formnmd0003);
		RateNorm rateNorm = new RateNorm();
		rateNorm.setRateNo(rateNo);
		rateNorm = rateNormFeign.getById(rateNorm);
		getObjValue(formnmd0003, rateNorm);
		model.addAttribute("formnmd0003", formnmd0003);
		model.addAttribute("query", "");
		return "component/nmd/RateNorm_Detail";
	}

	@RequestMapping(value = "/update")
	public String update(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formnmd0003 = formService.getFormData("nmd0003");
		getFormValue(formnmd0003);
		RateNorm rateNorm = new RateNorm();
		setObjValue(formnmd0003, rateNorm);
		rateNormFeign.update(rateNorm);
		this.addActionMessage(model, "更新成功！");
		getObjValue(formnmd0003, rateNorm);
		model.addAttribute("formnmd0003", formnmd0003);
		model.addAttribute("query", "");
		return "component/nmd/RateNorm_Update";
	}

	/**
	 * 删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model, String rateNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		RateNorm rateNorm = new RateNorm();
		rateNorm.setRateNo(rateNo);
		rateNormFeign.delete(rateNorm);
		return getListPage(model);
	}

	@RequestMapping(value = "/getUpdatePage")
	public String getUpdatePage(Model model, String rateNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formnmd0003 = formService.getFormData("nmd0003");
		RateNorm rateNorm = new RateNorm();
		rateNorm.setRateNo(rateNo);
		rateNorm = rateNormFeign.getById(rateNorm);

		getObjValue(formnmd0003, rateNorm);
		model.addAttribute("formnmd0003", formnmd0003);
		model.addAttribute("query", "");
		return "component/nmd/RateNorm_Update";
	}

	/**
	 * 新增校验
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateInsert")
	public void validateInsert(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formnmd0003 = formService.getFormData("nmd0003");
		getFormValue(formnmd0003);
		boolean validateFlag = this.validateFormData(formnmd0003);
	}

	/**
	 * 修改校验
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateUpdate")
	public void validateUpdate(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formnmd0003 = formService.getFormData("nmd0003");
		getFormValue(formnmd0003);
		boolean validateFlag = this.validateFormData(formnmd0003);
	}

}
