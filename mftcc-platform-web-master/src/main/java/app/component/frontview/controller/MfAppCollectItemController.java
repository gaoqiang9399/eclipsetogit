package app.component.frontview.controller;

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

import app.component.common.EntityUtil;
import app.component.frontview.entity.MfAppCollectItem;
import app.component.frontview.feign.MfAppCollectItemFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.StringUtil;
import net.sf.json.JSONObject;

/**
 * Title: MfAppCollectItemAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Tue Oct 24 12:01:38 CST 2017
 **/
@Controller
@RequestMapping("/mfAppCollectItem")
public class MfAppCollectItemController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入MfAppCollectItemBo
	@Autowired
	private MfAppCollectItemFeign mfAppCollectItemFeign;
	// 全局变量

	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request, response);
		return "/component/frontview/MfAppCollectItem_List";
	}

	/***
	 * 列表数据查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType, String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfAppCollectItem mfAppCollectItem = new MfAppCollectItem();
		try {
			mfAppCollectItem.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfAppCollectItem.setCriteriaList(mfAppCollectItem, ajaxData);// 我的筛选
			// mfAppCollectItem.setCustomSorts(ajaxData);//自定义排序
			// this.getRoleConditions(mfAppCollectItem,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage = mfAppCollectItemFeign.findByPage(ipage, mfAppCollectItem);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw new Exception(e.getMessage());
		}
		return dataMap;
	}

	@RequestMapping(value = "/updateStsAjax")
	@ResponseBody
	public Map<String, Object> updateStsAjax(String ajaxData, String tableId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfAppCollectItem mfAppCollectItem = new MfAppCollectItem();
		try {
			JSONObject jobj = JSONObject.fromObject(ajaxData);
			String id = (String) jobj.get("id");
			if (StringUtil.isEmpty(id)) {
				dataMap.put("flag", "error");
				dataMap.put("msg", MessageEnum.FAILED_UPDATE.getMessage());
				return dataMap;
			}

			// 更新设置项
			mfAppCollectItem.setId(id);
			String itemUseFlag = (String) jobj.get("itemUseFlag");
			mfAppCollectItem.setItemUseFlag(itemUseFlag);
			mfAppCollectItemFeign.update(mfAppCollectItem);

			// 查询设置项并更新列表
			mfAppCollectItem = mfAppCollectItemFeign.getById(mfAppCollectItem);
			ArrayList<MfAppCollectItem> mfAppCollectItemList = new ArrayList<MfAppCollectItem>();
			mfAppCollectItemList.add(mfAppCollectItem);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, "tableTag", mfAppCollectItemList, null, true);
			dataMap.put("tableData", tableHtml);
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED_UPDATE.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_UPDATE.getMessage());
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
			FormData formappcollectitem0002 = formService.getFormData("appcollectitem0002");
			getFormValue(formappcollectitem0002, getMapByJson(ajaxData));
			if (this.validateFormData(formappcollectitem0002)) {
				MfAppCollectItem mfAppCollectItem = new MfAppCollectItem();
				setObjValue(formappcollectitem0002, mfAppCollectItem);
				mfAppCollectItemFeign.insert(mfAppCollectItem);
				dataMap.put("flag", "success");
				dataMap.put("msg", "新增成功");
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "新增失败");
			throw new Exception(e.getMessage());
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
		FormData formappcollectitem0002 = formService.getFormData("appcollectitem0002");
		getFormValue(formappcollectitem0002, getMapByJson(ajaxData));
		MfAppCollectItem mfAppCollectItemJsp = new MfAppCollectItem();
		setObjValue(formappcollectitem0002, mfAppCollectItemJsp);
		MfAppCollectItem mfAppCollectItem = mfAppCollectItemFeign.getById(mfAppCollectItemJsp);
		if (mfAppCollectItem != null) {
			try {
				mfAppCollectItem = (MfAppCollectItem) EntityUtil.reflectionSetVal(mfAppCollectItem, mfAppCollectItemJsp, getMapByJson(ajaxData));
				mfAppCollectItemFeign.update(mfAppCollectItem);
				dataMap.put("flag", "success");
				dataMap.put("msg", "保存成功");
			} catch (Exception e) {
				e.printStackTrace();
				dataMap.put("flag", "error");
				dataMap.put("msg", "新增失败");
				throw new Exception(e.getMessage());
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
			FormData formappcollectitem0002 = formService.getFormData("appcollectitem0002");
			getFormValue(formappcollectitem0002, getMapByJson(ajaxData));
			if (this.validateFormData(formappcollectitem0002)) {
				MfAppCollectItem mfAppCollectItem = new MfAppCollectItem();
				setObjValue(formappcollectitem0002, mfAppCollectItem);
				mfAppCollectItemFeign.update(mfAppCollectItem);
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
			throw new Exception(e.getMessage());
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
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formappcollectitem0002 = formService.getFormData("appcollectitem0002");
		MfAppCollectItem mfAppCollectItem = new MfAppCollectItem();
		mfAppCollectItem.setId(id);
		mfAppCollectItem = mfAppCollectItemFeign.getById(mfAppCollectItem);
		getObjValue(formappcollectitem0002, mfAppCollectItem, formData);
		if (mfAppCollectItem != null) {
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
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfAppCollectItem mfAppCollectItem = new MfAppCollectItem();
		mfAppCollectItem.setId(id);
		try {
			mfAppCollectItemFeign.delete(mfAppCollectItem);
			dataMap.put("flag", "success");
			dataMap.put("msg", "成功");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			dataMap.put("msg", "失败");
			throw new Exception(e.getMessage());
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
		FormData formappcollectitem0002 = formService.getFormData("appcollectitem0002");
		model.addAttribute("formappcollectitem0002", formappcollectitem0002);
		model.addAttribute("query", "");
		return "/component/frontview/MfAppCollectItem_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String id) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formappcollectitem0001 = formService.getFormData("appcollectitem0001");
		getFormValue(formappcollectitem0001);
		MfAppCollectItem mfAppCollectItem = new MfAppCollectItem();
		mfAppCollectItem.setId(id);
		mfAppCollectItem = mfAppCollectItemFeign.getById(mfAppCollectItem);
		getObjValue(formappcollectitem0001, mfAppCollectItem);
		model.addAttribute("mfAppCollectItem", mfAppCollectItem);
		model.addAttribute("formappcollectitem0001", formappcollectitem0001);
		model.addAttribute("query", "");
		return "/component/frontview/MfAppCollectItem_Detail";
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
		FormData formappcollectitem0002 = formService.getFormData("appcollectitem0002");
		getFormValue(formappcollectitem0002);
		boolean validateFlag = this.validateFormData(formappcollectitem0002);
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
		FormData formappcollectitem0002 = formService.getFormData("appcollectitem0002");
		getFormValue(formappcollectitem0002);
		boolean validateFlag = this.validateFormData(formappcollectitem0002);
	}


}
