package app.component.app.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.component.app.entity.MfBusApplyHis;
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

import app.component.app.feign.MfBusApplyHisFeign;
import app.component.common.EntityUtil;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;

/**
 * Title: MfBusApplyHisAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Thu May 26 11:35:25 CST 2016
 **/
@Controller
@RequestMapping("/mfBusApplyHis")
public class MfBusApplyHisController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入MfBusApplyHisBo
	@Autowired
	private MfBusApplyHisFeign mfBusApplyHisFeign;
	// 全局变量
	// 异步参数
	// 表单变量

	// public MfBusApplyHisAction(){
	// query = "";
	// }

	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		return "/component/app/MfBusApplyHis_List";
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
		MfBusApplyHis mfBusApplyHis = new MfBusApplyHis();
		try {
			mfBusApplyHis.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfBusApplyHis.setCriteriaList(mfBusApplyHis, ajaxData);// 我的筛选
			// this.getRoleConditions(mfBusApplyHis,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage = mfBusApplyHisFeign.findByPage(ipage, mfBusApplyHis);
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
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formapphis0002 = formService.getFormData("apphis0002");
			getFormValue(formapphis0002, getMapByJson(ajaxData));
			if (this.validateFormData(formapphis0002)) {
				MfBusApplyHis mfBusApplyHis = new MfBusApplyHis();
				setObjValue(formapphis0002, mfBusApplyHis);
				mfBusApplyHisFeign.insert(mfBusApplyHis);
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
		FormData formapphis0002 = formService.getFormData("apphis0002");
		getFormValue(formapphis0002, getMapByJson(ajaxData));
		MfBusApplyHis mfBusApplyHisJsp = new MfBusApplyHis();
		setObjValue(formapphis0002, mfBusApplyHisJsp);
		MfBusApplyHis mfBusApplyHis = mfBusApplyHisFeign.getById(mfBusApplyHisJsp);
		if (mfBusApplyHis != null) {
			try {
				mfBusApplyHis = (MfBusApplyHis) EntityUtil.reflectionSetVal(mfBusApplyHis, mfBusApplyHisJsp,
						getMapByJson(ajaxData));
				mfBusApplyHisFeign.update(mfBusApplyHis);
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
		MfBusApplyHis mfBusApplyHis = new MfBusApplyHis();
		try {
			FormData formapphis0002 = formService.getFormData("apphis0002");
			getFormValue(formapphis0002, getMapByJson(ajaxData));
			if (this.validateFormData(formapphis0002)) {
				setObjValue(formapphis0002, mfBusApplyHis);
				mfBusApplyHisFeign.update(mfBusApplyHis);
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
	public Map<String, Object> getByIdAjax(String ajaxData,String id) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formapphis0002 = formService.getFormData("apphis0002");
		MfBusApplyHis mfBusApplyHis = new MfBusApplyHis();
		mfBusApplyHis.setId(id);
		mfBusApplyHis = mfBusApplyHisFeign.getById(mfBusApplyHis);
		getObjValue(formapphis0002, mfBusApplyHis, formData);
		if (mfBusApplyHis != null) {
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
	public Map<String, Object> deleteAjax(String ajaxData,String id) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfBusApplyHis mfBusApplyHis = new MfBusApplyHis();
		mfBusApplyHis.setId(id);
		try {
			mfBusApplyHisFeign.delete(mfBusApplyHis);
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
		FormData formapphis0002 = formService.getFormData("apphis0002");
		model.addAttribute("formapphis0002", formapphis0002);
		model.addAttribute("query", "");
		return "/component/aop/MfBusApplyHis_Insert";
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
		FormData formapphis0002 = formService.getFormData("apphis0002");
		getFormValue(formapphis0002);
		MfBusApplyHis mfBusApplyHis = new MfBusApplyHis();
		setObjValue(formapphis0002, mfBusApplyHis);
		mfBusApplyHisFeign.insert(mfBusApplyHis);
		getObjValue(formapphis0002, mfBusApplyHis);
		this.addActionMessage(model, "保存成功");
		List<MfBusApplyHis> mfBusApplyHisList = (List<MfBusApplyHis>) mfBusApplyHisFeign.findByPage(this.getIpage(), mfBusApplyHis)
				.getResult();
		model.addAttribute("formapphis0002", formapphis0002);
		model.addAttribute("mfBusApplyHisList", mfBusApplyHisList);
		model.addAttribute("query", "");
		return "/component/app/MfBusApplyHis_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String ajaxData,String id) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formapphis0001 = formService.getFormData("apphis0001");
		getFormValue(formapphis0001);
		MfBusApplyHis mfBusApplyHis = new MfBusApplyHis();
		mfBusApplyHis.setId(id);
		mfBusApplyHis = mfBusApplyHisFeign.getById(mfBusApplyHis);
		getObjValue(formapphis0001, mfBusApplyHis);
		model.addAttribute("formapphis0001", formapphis0001);
		model.addAttribute("query", "");
		return "/component/app/MfBusApplyHis_Detail";
	}

	/**
	 * 删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model, String ajaxData,String id) throws Exception {
		ActionContext.initialize(request, response);
		MfBusApplyHis mfBusApplyHis = new MfBusApplyHis();
		mfBusApplyHis.setId(id);
		mfBusApplyHisFeign.delete(mfBusApplyHis);
		return getListPage(model);
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
		FormData formapphis0002 = formService.getFormData("apphis0002");
		getFormValue(formapphis0002);
		boolean validateFlag = this.validateFormData(formapphis0002);
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
		FormData formapphis0002 = formService.getFormData("apphis0002");
		getFormValue(formapphis0002);
		boolean validateFlag = this.validateFormData(formapphis0002);
	}


	/**
	 * 根据appId去查询审批历史
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getByAppId")
	@ResponseBody
	public Map<String, Object> getByAppId(String appId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object>   dataMap =  new HashMap<>();
		MfBusApplyHis mfBusApplyHis = new MfBusApplyHis();
		mfBusApplyHis.setPrimaryAppId(appId);
		List<MfBusApplyHis> mfBusApplyHisList  = mfBusApplyHisFeign.getListPriAppId(mfBusApplyHis);
		if(mfBusApplyHisList != null && mfBusApplyHisList.size() > 0){
			dataMap.put("flag", "success");
		}else{
			dataMap.put("flag", "error");
		}
		return  dataMap ;
	}

}
