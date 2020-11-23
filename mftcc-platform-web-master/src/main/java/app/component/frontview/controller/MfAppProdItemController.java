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
import app.component.frontview.entity.MfAppProdItem;
import app.component.frontview.feign.MfAppProdItemFeign;
import app.component.prdct.entity.MfSysKind;
import app.component.prdctinterface.PrdctInterfaceFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.StringUtil;
import net.sf.json.JSONObject;

/**
 * Title: MfAppProdItemAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Tue Oct 24 20:40:37 CST 2017
 **/
@Controller
@RequestMapping("/mfAppProdItem")
public class MfAppProdItemController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入MfAppProdItemBo
	@Autowired
	private MfAppProdItemFeign mfAppProdItemFeign;
	// 全局变量
	// 异步参数
	// 表单变量

	@Autowired
	private PrdctInterfaceFeign prdctInterfaceFeign;

	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model, String kindNo) throws Exception {
		ActionContext.initialize(request, response);
		model.addAttribute("kindNo", kindNo);
		return "/component/frontview/MfAppProdItem_List";
	}

	/**
	 * 选择产品列表(获取所有pc端 启用 产品供app端选择 )
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getAppProdSelectListPage")
	public String getAppProdSelectListPage(Model model, String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		MfSysKind mfSysKind = new MfSysKind();
		List<MfSysKind> mfSysKindList = (List<MfSysKind>) prdctInterfaceFeign.getSysKindList(mfSysKind);
		model.addAttribute("mfSysKindList", mfSysKindList);
		model.addAttribute("query", "");
		return "/component/frontview/AppProdSelectListPage";
	}

	/**
	 * 新增产品到产品采集项表
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertProdItemAjax")
	@ResponseBody
	public Map<String, Object> insertProdItemAjax(String kindNo, String kindName) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			dataMap = mfAppProdItemFeign.insertProdItem(kindNo, kindName);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "添加失败,请联系管理员");
			throw e;
		}
		return dataMap;
	}

	/**
	 * 获取添加的产品
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getAppProdItemListAjax")
	@ResponseBody
	public Map<String, Object> getAppProdItemListAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfAppProdItem mfAppProdItem = new MfAppProdItem();
		try {
			List<MfAppProdItem> mfAppProdItemList = mfAppProdItemFeign.getAppProdItemList(mfAppProdItem);
			dataMap.put("mfAppProdItemList", mfAppProdItemList);
			dataMap.put("flag", "success");
			dataMap.put("msg", "查询成功");
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "查询成功产品项失败");
			throw e;
		}
		return dataMap;
	}

	/***
	 * 列表数据查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType, String ajaxData, String kindNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfAppProdItem mfAppProdItem = new MfAppProdItem();
		try {
			mfAppProdItem.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfAppProdItem.setCriteriaList(mfAppProdItem, ajaxData);// 我的筛选
			// mfAppProdItem.setCustomSorts(ajaxData);//自定义排序
			// this.getRoleConditions(mfAppProdItem,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			if (StringUtil.isNotEmpty(kindNo)) {
				mfAppProdItem.setKindNo(kindNo);
			}
			ipage = mfAppProdItemFeign.findByPage(ipage, mfAppProdItem);
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

	/**
	 * 更新认证项是否必填状态
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateRequireAjax")
	@ResponseBody
	public Map<String, Object> updateRequireAjax(String ajaxData, String tableId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfAppProdItem mfAppProdItem = new MfAppProdItem();
		try {
			JSONObject jobj = JSONObject.fromObject(ajaxData);
			String kindNo = (String) jobj.get("kindNo");
			String itemId = (String) jobj.get("itemId");
			if (StringUtil.isEmpty(itemId) || StringUtil.isEmpty(kindNo)) {
				dataMap.put("flag", "error");
				dataMap.put("msg", MessageEnum.FAILED_UPDATE.getMessage());
				return dataMap;
			}

			// 更新设置项
			mfAppProdItem.setKindNo(kindNo);
			mfAppProdItem.setItemId(itemId);
			String itemRequire = (String) jobj.get("itemRequire");
			mfAppProdItem.setItemRequire(itemRequire);
			mfAppProdItemFeign.update(mfAppProdItem);

			// 查询设置项并更新列表
			mfAppProdItem = mfAppProdItemFeign.getById(mfAppProdItem);
			ArrayList<MfAppProdItem> mfAppProdItemList = new ArrayList<MfAppProdItem>();
			mfAppProdItemList.add(mfAppProdItem);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, "tableTag", mfAppProdItemList, null, true);
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
	 * 根据产品号删除产品List
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteByKindNoAjax")
	@ResponseBody
	public Map<String, Object> deleteByKindNoAjax(String kindNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfAppProdItem mfAppProdItem = new MfAppProdItem();
		mfAppProdItem.setKindNo(kindNo);
		try {
			mfAppProdItemFeign.deleteByKindNo(mfAppProdItem);
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
			FormData formappproditem0002 = formService.getFormData("appproditem0002");
			getFormValue(formappproditem0002, getMapByJson(ajaxData));
			if (this.validateFormData(formappproditem0002)) {
				MfAppProdItem mfAppProdItem = new MfAppProdItem();
				setObjValue(formappproditem0002, mfAppProdItem);
				mfAppProdItemFeign.insert(mfAppProdItem);
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
		FormData formappproditem0002 = formService.getFormData("appproditem0002");
		getFormValue(formappproditem0002, getMapByJson(ajaxData));
		MfAppProdItem mfAppProdItemJsp = new MfAppProdItem();
		setObjValue(formappproditem0002, mfAppProdItemJsp);
		MfAppProdItem mfAppProdItem = mfAppProdItemFeign.getById(mfAppProdItemJsp);
		if (mfAppProdItem != null) {
			try {
				mfAppProdItem = (MfAppProdItem) EntityUtil.reflectionSetVal(mfAppProdItem, mfAppProdItemJsp, getMapByJson(ajaxData));
				mfAppProdItemFeign.update(mfAppProdItem);
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
			FormData formappproditem0002 = formService.getFormData("appproditem0002");
			getFormValue(formappproditem0002, getMapByJson(ajaxData));
			if (this.validateFormData(formappproditem0002)) {
				MfAppProdItem mfAppProdItem = new MfAppProdItem();
				setObjValue(formappproditem0002, mfAppProdItem);
				mfAppProdItemFeign.update(mfAppProdItem);
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
	public Map<String, Object> getByIdAjax(String kindNo, String itemId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formappproditem0002 = formService.getFormData("appproditem0002");
		MfAppProdItem mfAppProdItem = new MfAppProdItem();
		mfAppProdItem.setKindNo(kindNo);
		mfAppProdItem.setItemId(itemId);
		mfAppProdItem = mfAppProdItemFeign.getById(mfAppProdItem);
		getObjValue(formappproditem0002, mfAppProdItem, formData);
		if (mfAppProdItem != null) {
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
	public Map<String, Object> deleteAjax(String kindNo, String itemId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfAppProdItem mfAppProdItem = new MfAppProdItem();
		mfAppProdItem.setKindNo(kindNo);
		mfAppProdItem.setItemId(itemId);
		try {
			mfAppProdItemFeign.delete(mfAppProdItem);
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
		FormData formappproditem0002 = formService.getFormData("appproditem0002");
		model.addAttribute("formappproditem0002", formappproditem0002);
		model.addAttribute("query", "");
		return "/component/frontview/MfAppProdItem_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String kindNo, String itemId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formappproditem0002 = formService.getFormData("appproditem0001");
		getFormValue(formappproditem0002);
		MfAppProdItem mfAppProdItem = new MfAppProdItem();
		mfAppProdItem.setKindNo(kindNo);
		mfAppProdItem.setItemId(itemId);
		mfAppProdItem = mfAppProdItemFeign.getById(mfAppProdItem);
		getObjValue(formappproditem0002, mfAppProdItem);
		model.addAttribute("mfAppProdItem", mfAppProdItem);
		model.addAttribute("formappproditem0002", formappproditem0002);
		model.addAttribute("query", "");
		return "/component/frontview/MfAppProdItem_Detail";
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
		FormData formappproditem0002 = formService.getFormData("appproditem0002");
		getFormValue(formappproditem0002);
		boolean validateFlag = this.validateFormData(formappproditem0002);
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
		FormData formappproditem0002 = formService.getFormData("appproditem0002");
		getFormValue(formappproditem0002);
		boolean validateFlag = this.validateFormData(formappproditem0002);
	}

}
