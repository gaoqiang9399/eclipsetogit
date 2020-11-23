package app.component.app.controller;

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

import app.component.app.entity.MfBusAppKind;
import app.component.app.feign.MfBusAppKindFeign;
import app.component.common.EntityUtil;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.MathExtend;

/**
 * Title: MfBusAppKindAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Fri Dec 02 18:04:47 CST 2016
 **/
@Controller
@RequestMapping("/mfBusAppKind")
public class MfBusAppKindController extends BaseFormBean {

	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfBusAppKindFeign mfBusAppKindFeign;

	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage() throws Exception {
		ActionContext.initialize(request, response);
		return "/component/app/MfBusAppKind_List";
	}

	/***
	 * 列表数据查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(String ajaxData, int pageNo, String tableId, String tableType,Ipage ipage) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfBusAppKind mfBusAppKind = new MfBusAppKind();
		try {
			mfBusAppKind.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfBusAppKind.setCriteriaList(mfBusAppKind, ajaxData);// 我的筛选
			// this.getRoleConditions(mfBusAppKind,"1000000001");//记录级权限控制方法
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage.setParams(this.setIpageParams("mfBusAppKind", mfBusAppKind));
			ipage = mfBusAppKindFeign.findByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
			/**
			 * ipage.setResult(tableHtml); dataMap.put("ipage",ipage); 需要改进的方法
			 * dataMap.put("tableData",tableHtml);
			 */
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
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formappkind0002 = formService.getFormData("appkind0002");
			getFormValue(formappkind0002, getMapByJson(ajaxData));
			if (this.validateFormData(formappkind0002)) {
				MfBusAppKind mfBusAppKind = new MfBusAppKind();
				setObjValue(formappkind0002, mfBusAppKind);
				mfBusAppKindFeign.insert(mfBusAppKind);
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
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formappkind0002 = formService.getFormData("appkind0002");
		getFormValue(formappkind0002, getMapByJson(ajaxData));
		MfBusAppKind mfBusAppKindJsp = new MfBusAppKind();
		setObjValue(formappkind0002, mfBusAppKindJsp);
		MfBusAppKind mfBusAppKind = mfBusAppKindFeign.getById(mfBusAppKindJsp);
		if (mfBusAppKind != null) {
			try {
				mfBusAppKind = (MfBusAppKind) EntityUtil.reflectionSetVal(mfBusAppKind, mfBusAppKindJsp, getMapByJson(ajaxData));
				mfBusAppKindFeign.update(mfBusAppKind);
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
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfBusAppKind mfBusAppKind = new MfBusAppKind();
		try {
			FormData formappkind0002 = formService.getFormData("appkind0002");
			getFormValue(formappkind0002, getMapByJson(ajaxData));
			if (this.validateFormData(formappkind0002)) {
				mfBusAppKind = new MfBusAppKind();
				setObjValue(formappkind0002, mfBusAppKind);
				mfBusAppKindFeign.update(mfBusAppKind);
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
	public Map<String, Object> getByIdAjax(String id) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formappkind0002 = formService.getFormData("appkind0002");
		MfBusAppKind mfBusAppKind = new MfBusAppKind();
		mfBusAppKind.setId(id);
		mfBusAppKind = mfBusAppKindFeign.getById(mfBusAppKind);
		getObjValue(formappkind0002, mfBusAppKind, formData);
		if (mfBusAppKind != null) {
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
	public Map<String, Object> deleteAjax(String id) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfBusAppKind mfBusAppKind = new MfBusAppKind();
		mfBusAppKind.setId(id);
		try {
			mfBusAppKindFeign.delete(mfBusAppKind);
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
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formappkind0002 = formService.getFormData("appkind0002");
		model.addAttribute("formappkind0002", formappkind0002);
		model.addAttribute("query", "");
		return "/component/app/MfBusAppKind_Insert";
	}

	/***
	 * 新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insert")
	public String insert(Model model,Ipage ipage) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formappkind0002 = formService.getFormData("appkind0002");
		getFormValue(formappkind0002);
		MfBusAppKind mfBusAppKind = new MfBusAppKind();
		setObjValue(formappkind0002, mfBusAppKind);
		mfBusAppKindFeign.insert(mfBusAppKind);
		getObjValue(formappkind0002, mfBusAppKind);
		this.addActionMessage(model, "保存成功");
		ipage.setParams(this.setIpageParams("mfBusAppKind", mfBusAppKind));
		List<MfBusAppKind> mfBusAppKindList = (List<MfBusAppKind>) mfBusAppKindFeign.findByPage(ipage).getResult();
		model.addAttribute("mfBusAppKindList", mfBusAppKindList);
		model.addAttribute("formappkind0002", formappkind0002);
		model.addAttribute("query", "");
		return "/component/app/MfBusAppKind_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(String id, String appId, Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formappkind0001 = formService.getFormData("appkind0001");
		getFormValue(formappkind0001);
		MfBusAppKind mfBusAppKind = new MfBusAppKind();
		mfBusAppKind.setId(id);
		mfBusAppKind.setAppId(appId);
		mfBusAppKind = mfBusAppKindFeign.getById(mfBusAppKind);
		// 处理金额
		if(mfBusAppKind.getMinAmt()!=null){
			mfBusAppKind.setMinAmtStr(MathExtend.moneyStr(mfBusAppKind.getMinAmt() / 10000));
			mfBusAppKind.setMaxAmtStr(MathExtend.moneyStr(mfBusAppKind.getMaxAmt() / 10000));
		}
		// 处理业务模式
		CodeUtils cu = new CodeUtils();
		Map<String, String> busModelMap = cu.getMapByKeyName("BUS_MODEL");
		mfBusAppKind.setBusModel(busModelMap.get(mfBusAppKind.getBusModel()));
		// 担保方式
		Map<String, String> vouTypeMap = cu.getMapByKeyName("VOU_TYPE");
		mfBusAppKind.setVouTypeDef(vouTypeMap.get(mfBusAppKind.getVouTypeDef()));
		// 期限类型
		Map<String, String> termTypeMap = cu.getMapByKeyName("TERM_TYPE");
		mfBusAppKind.setTermType(termTypeMap.get(mfBusAppKind.getTermType()));

		model.addAttribute("formappkind0001", formappkind0001);
		model.addAttribute("mfBusAppKind", mfBusAppKind);
		model.addAttribute("query", "");
		return "/component/app/MfBusAppKind_Detail";
	}

	/**
	 * 删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(String id) throws Exception {
		ActionContext.initialize(request, response);
		MfBusAppKind mfBusAppKind = new MfBusAppKind();
		mfBusAppKind.setId(id);
		mfBusAppKindFeign.delete(mfBusAppKind);
		return getListPage();
	}

	/**
	 * 新增校验
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateInsert")
	public void validateInsert(Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formappkind0002 = formService.getFormData("appkind0002");
		getFormValue(formappkind0002);
		boolean validateFlag = this.validateFormData(formappkind0002);
	}

	/**
	 * 修改校验
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateUpdate")
	public void validateUpdate() throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formappkind0002 = formService.getFormData("appkind0002");
		getFormValue(formappkind0002);
		boolean validateFlag = this.validateFormData(formappkind0002);
	}

	/**
	 * 
	 * 方法描述： 根据业务编号获得业务产品信息
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2017-10-25 下午2:10:29
	 */
	@RequestMapping(value = "/getByAppIdAjax")
	@ResponseBody
	public Map<String, Object> getByAppIdAjax(String appId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfBusAppKind mfBusAppKind = new MfBusAppKind();
		mfBusAppKind.setAppId(appId);
		mfBusAppKind = mfBusAppKindFeign.getById(mfBusAppKind);
		if (mfBusAppKind != null) {
			dataMap.put("flag", "success");
			dataMap.put("busAppKind", mfBusAppKind);
		} else {
			dataMap.put("flag", "error");
		}
		return dataMap;
	}

}
