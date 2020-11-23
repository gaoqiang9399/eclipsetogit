package app.component.oa.consumable.controller;

import java.util.ArrayList;
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
import app.component.common.EntityUtil;
import app.component.oa.consumable.entity.MfOaConsClass;
import app.component.oa.consumable.feign.MfOaConsClassFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.DateUtil;
import net.sf.json.JSONArray;

/**
 * Title: MfOaConsClassAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Sat Dec 24 11:58:00 CST 2016
 **/
@Controller
@RequestMapping("/mfOaConsClass")
public class MfOaConsClassController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfOaConsClassFeign mfOaConsClassFeign;
	/**
	 * 列表打开页面请求
	 * @param model 
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request, response);
		model.addAttribute("query", "");
		return "/component/oa/consumable/MfOaConsClass_List";
	}

	/**
	 * 查询所有类别，树形结构
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getSelectListPage")
	public String getSelectListPage(Model model) throws Exception {
		ActionContext.initialize(request, response);
		// 查询一级类别
		List<MfOaConsClass> superList = mfOaConsClassFeign.getSuperClass();
		List<MfOaConsClass> nextClass = null;// 记录下级节点
		JSONArray superArray = JSONArray.fromObject(superList);

		// 遍历一级类别查询其子类
		for (int i = 0; i < superList.size(); i++) {
			superArray.getJSONObject(i).put("id", superArray.getJSONObject(i).getString("classId"));
			superArray.getJSONObject(i).put("name", superArray.getJSONObject(i).getString("className"));
			superArray.getJSONObject(i).put("pId", "0");
			superArray.getJSONObject(i).put("open", false);
			superArray.getJSONObject(i).put("isParent", true);
			// 获取父节点下所有子节点
			superArray = getNextNode(superArray, superList.get(i));

		}
		model.addAttribute("ajaxData", superArray.toString());
		model.addAttribute("query", "");
		return "/component/oa/consumable/MfOaConsClassSelect_List";
	}

	// 为数组添加剩下的点
	private JSONArray getNextNode(JSONArray jsonArray, MfOaConsClass consClass) {
		List<MfOaConsClass> nextClass = mfOaConsClassFeign.getChildClassList(consClass);
		if (!nextClass.isEmpty()) {
			JSONArray nextArray = JSONArray.fromObject(nextClass);
			for (int j = 0; j < nextClass.size(); j++) {
				nextArray.getJSONObject(j).put("id", nextArray.getJSONObject(j).getString("classId"));
				nextArray.getJSONObject(j).put("name", nextArray.getJSONObject(j).getString("className"));
				nextArray.getJSONObject(j).put("pId", nextArray.getJSONObject(j).getString("superClassId"));
				nextArray.getJSONObject(j).put("open", true);
				jsonArray.add(nextArray.getJSONObject(j));
				jsonArray = getNextNode(jsonArray, nextClass.get(j));
			}
		}
		return jsonArray;
	}

	/***
	 * 列表数据查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/findByPageAjax")
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType, String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new  HashMap<String, Object>();
		try {
			MfOaConsClass mfOaConsClass = new MfOaConsClass();
			mfOaConsClass.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfOaConsClass.setCriteriaList(mfOaConsClass, ajaxData);// 我的筛选
			// this.getRoleConditions(mfOaConsClass,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage.setParams(this.setIpageParams("mfOaConsClass", mfOaConsClass));
			ipage = mfOaConsClassFeign.findByPage(ipage);
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
	public Map<String, Object> insertAjax(String ajaxData,String isChild) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new  HashMap<String, Object>();
		try {
			FormData formconsclass0002 = new FormService().getFormData("consclass0002");
			getFormValue(formconsclass0002, getMapByJson(ajaxData));
			if (this.validateFormData(formconsclass0002)) {
				MfOaConsClass mfOaConsClass = new MfOaConsClass();
				setObjValue(formconsclass0002, mfOaConsClass);
				dataMap.put("bean", mfOaConsClassFeign.insert(mfOaConsClass, isChild));
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
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
		Map<String, Object> dataMap = new  HashMap<String, Object>();
		FormData formconsclass0002 = new FormService().getFormData("consclass0002");
		getFormValue(formconsclass0002, getMapByJson(ajaxData));
		MfOaConsClass mfOaConsClassJsp = new MfOaConsClass();
		setObjValue(formconsclass0002, mfOaConsClassJsp);
		MfOaConsClass mfOaConsClass = mfOaConsClassFeign.getById(mfOaConsClassJsp);
		if (mfOaConsClass != null) {
			try {
				mfOaConsClass = (MfOaConsClass) EntityUtil.reflectionSetVal(mfOaConsClass, mfOaConsClassJsp,
						getMapByJson(ajaxData));
				mfOaConsClassFeign.update(mfOaConsClass);
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
		Map<String, Object> dataMap = new  HashMap<String, Object>();
		try {
			MfOaConsClass mfOaConsClass = new MfOaConsClass();
			FormData formconsclass0002 = new FormService().getFormData("consclass0002");
			getFormValue(formconsclass0002, getMapByJson(ajaxData));
			if (this.validateFormData(formconsclass0002)) {
				mfOaConsClass = new MfOaConsClass();
				setObjValue(formconsclass0002, mfOaConsClass);
				mfOaConsClassFeign.update(mfOaConsClass);
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
	public Map<String, Object> getByIdAjax(String classId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new  HashMap<String, Object>();
		Map<String, Object> formData = new HashMap<String, Object>();
		FormData formconsclass0002 = new FormService().getFormData("consclass0002");
		MfOaConsClass mfOaConsClass = new MfOaConsClass();
		mfOaConsClass.setClassId(classId);
		mfOaConsClass = mfOaConsClassFeign.getById(mfOaConsClass);
		getObjValue(formconsclass0002, mfOaConsClass, formData);
		if (mfOaConsClass != null) {
			dataMap.put("flag", "success");
		} else {
			dataMap.put("flag", "error");
		}
		dataMap.put("formData", formData);
		return dataMap;
	}

	/**
	 * ajax获取所有类别列表
	 * 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/getListAjax")
	public Map<String, Object> getListAjax() throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new  HashMap<String, Object>();
		try {
			List<MfOaConsClass> classList = new ArrayList<MfOaConsClass>();
			for (int i = 0; i <= 5; i++) {
				MfOaConsClass mfOaConsClass = new MfOaConsClass();
				mfOaConsClass.setClassId("" + i);
				mfOaConsClass.setClassName("第" + i + "个");
				classList.add(mfOaConsClass);
			}
			dataMap.put("classList", classList);
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * AJAX获取子项
	 * 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/getChildByIdAjax")
	public Map<String, Object> getChildByIdAjax(String classId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new  HashMap<String, Object>();
		Map<String, Object> formData = new HashMap<String, Object>();
		// MfOaConsClass mfOaConsClass = new MfOaConsClass();
		// mfOaConsClass.setClassId(classId);
		// List<MfOaConsClass> childList =
		// mfOaConsClassFeign.getChildClassList(mfOaConsClass);
		List<MfOaConsClass> childList = new ArrayList<MfOaConsClass>();
		if (!"2".equals(classId)) {// id为"2"，没有子项
			for (int i = 0; i < 4; i++) {
				MfOaConsClass mfOaConsClass = new MfOaConsClass();
				mfOaConsClass.setClassId(classId + "-" + i);
				mfOaConsClass.setClassName(classId + "的第" + i);
				childList.add(mfOaConsClass);
			}
		}
		if (!childList.isEmpty()) {
			dataMap.put("flag", "hasChild");
		} else {
			dataMap.put("flag", "noChild");
		}

		dataMap.put("childList", childList);
		return dataMap;
	}

	/**
	 * Ajax异步删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/deleteAjax")
	public Map<String, Object> deleteAjax(String classId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new  HashMap<String, Object>();
		try {
			MfOaConsClass mfOaConsClass = new MfOaConsClass();
			mfOaConsClass.setClassId(classId);
			mfOaConsClassFeign.delete(mfOaConsClass);
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
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/input")
	public String input(Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormData formconsclass0002 = new FormService().getFormData("consclass0002");
		MfOaConsClass mfOaConsclass = new MfOaConsClass();
		mfOaConsclass.setCreateTime(DateUtil.getDate("yyyy-MM-dd"));
		mfOaConsclass.setBrNo(User.getOrgNo(request));
		mfOaConsclass.setBrName(User.getOrgName(request));
		mfOaConsclass.setOpNo(User.getRegNo(request));
		mfOaConsclass.setOpName(User.getRegName(request));
		getObjValue(formconsclass0002, mfOaConsclass);
		model.addAttribute("formconsclass0002", formconsclass0002);
		model.addAttribute("mfOaConsclass", mfOaConsclass);
		model.addAttribute("query", "");
		return "/component/oa/consumable/MfOaConsClass_Insert";
	}

	/***
	 * 新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/insert")
	public String insert(Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormData formconsclass0002 = new FormService().getFormData("consclass0002");
		getFormValue(formconsclass0002);
		MfOaConsClass mfOaConsClass = new MfOaConsClass();
		setObjValue(formconsclass0002, mfOaConsClass);
		mfOaConsClassFeign.insert(mfOaConsClass, null);
		getObjValue(formconsclass0002, mfOaConsClass);
		Ipage ipage = this.getIpage();
		ipage.setParams(this.setIpageParams("mfOaConsClass", mfOaConsClass));
		List<MfOaConsClass> mfOaConsClassList = (List<MfOaConsClass>) mfOaConsClassFeign.findByPage(ipage).getResult();
		model.addAttribute("formconsclass0002", formconsclass0002);
		model.addAttribute("mfOaConsClassList", mfOaConsClassList);
		model.addAttribute("mfOaConsClass", mfOaConsClass);
		model.addAttribute("query", "");
		return "/component/oa/consumable/MfOaConsClass_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getById")
	public String getById(String classId,Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormData formconsclass0001 = new FormService().getFormData("consclass0001");
		getFormValue(formconsclass0001);
		MfOaConsClass mfOaConsClass = new MfOaConsClass();
		mfOaConsClass.setClassId(classId);
		mfOaConsClass = mfOaConsClassFeign.getById(mfOaConsClass);
		getObjValue(formconsclass0001, mfOaConsClass);
		model.addAttribute("formconsclass0001", formconsclass0001);
		model.addAttribute("mfOaConsClass", mfOaConsClass);
		model.addAttribute("query", "");
		return "/component/oa/consumable/MfOaConsClass_Detail";
	}

	/**
	 * 删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delete")
	public String delete(String classId,Model model) throws Exception {
		ActionContext.initialize(request, response);
		MfOaConsClass mfOaConsClass = new MfOaConsClass();
		mfOaConsClass.setClassId(classId);
		mfOaConsClassFeign.delete(mfOaConsClass);
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
		FormData formconsclass0002 = new FormService().getFormData("consclass0002");
		getFormValue(formconsclass0002);
		boolean validateFlag = this.validateFormData(formconsclass0002);
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
		FormData formconsclass0002 = new FormService().getFormData("consclass0002");
		getFormValue(formconsclass0002);
		boolean validateFlag = this.validateFormData(formconsclass0002);
	}

}
