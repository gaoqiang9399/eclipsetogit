package app.component.collateral.controller;

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

import app.component.collateral.entity.CollateralWarningDetail;
import app.component.collateral.feign.CollateralWarningDetailFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * Title: CollateralWarningDetailController.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Fri Mar 31 16:49:24 CST 2017
 **/
@Controller
@RequestMapping("/collateralWarningDetail")
public class CollateralWarningDetailController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private CollateralWarningDetailFeign collateralWarningDetailFeign;

	/**
	 * 获取列表数据
	 * 
	 * @return
	 * @throws Exception
	 */

	private void getTableData(Map<String, Object> dataMap, String tableId,
			CollateralWarningDetail collateralWarningDetail) throws Exception {
		JsonTableUtil jtu = new JsonTableUtil();
		String tableHtml = jtu.getJsonStr(tableId, "tableTag",
				collateralWarningDetailFeign.getAll(collateralWarningDetail), null, true);
		dataMap.put("tableData", tableHtml);
	}

	/**
	 * 列表有翻页
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formdlcollateralwarningdetail0002 = formService.getFormData("dlcollateralwarningdetail0002");
		CollateralWarningDetail collateralWarningDetail = new CollateralWarningDetail();
		Ipage ipage = this.getIpage();
		@SuppressWarnings({ "unchecked" })
		List<CollateralWarningDetail> collateralWarningDetailList = (List<CollateralWarningDetail>) collateralWarningDetailFeign
				.findByPage(ipage, collateralWarningDetail).getResult();
		model.addAttribute("formdlcollateralwarningdetail0002", formdlcollateralwarningdetail0002);
		model.addAttribute("query", "");
		model.addAttribute("collateralWarningDetailList", collateralWarningDetailList);
		return "/component/collateral/CollateralWarningDetail_List";
	}

	/**
	 * 列表全部无翻页
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getListAll")
	public String getListAll(Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formdlcollateralwarningdetail0002 = formService.getFormData("dlcollateralwarningdetail0002");
		CollateralWarningDetail collateralWarningDetail = new CollateralWarningDetail();
		List<CollateralWarningDetail> collateralWarningDetailList = collateralWarningDetailFeign
				.getAll(collateralWarningDetail);
		model.addAttribute("collateralWarningDetailList", collateralWarningDetailList);
		model.addAttribute("formdlcollateralwarningdetail0002", formdlcollateralwarningdetail0002);
		model.addAttribute("query", "");
		return "/component/collateral/CollateralWarningDetail_List";
	}

	/**
	 * AJAX新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String tableId, String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formdlcollateralwarningdetail0002 = formService.getFormData("dlcollateralwarningdetail0002");
			getFormValue(formdlcollateralwarningdetail0002, getMapByJson(ajaxData));
			if (this.validateFormData(formdlcollateralwarningdetail0002)) {
				CollateralWarningDetail collateralWarningDetail = new CollateralWarningDetail();
				setObjValue(formdlcollateralwarningdetail0002, collateralWarningDetail);
				collateralWarningDetailFeign.insert(collateralWarningDetail);
				getTableData(dataMap, tableId, collateralWarningDetail);// 获取列表
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
	@RequestMapping("/updateAjax")
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData, String tableId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formdlcollateralwarningdetail0002 = formService.getFormData("dlcollateralwarningdetail0002");
			getFormValue(formdlcollateralwarningdetail0002, getMapByJson(ajaxData));
			if (this.validateFormData(formdlcollateralwarningdetail0002)) {
				CollateralWarningDetail collateralWarningDetail = new CollateralWarningDetail();
				setObjValue(formdlcollateralwarningdetail0002, collateralWarningDetail);
				collateralWarningDetailFeign.update(collateralWarningDetail);
				getTableData(dataMap, tableId, collateralWarningDetail);// 获取列表
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
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String warnDetailNo, String warnNo) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formdlcollateralwarningdetail0002 = formService.getFormData("dlcollateralwarningdetail0002");
		CollateralWarningDetail collateralWarningDetail = new CollateralWarningDetail();
		collateralWarningDetail.setWarnDetailNo(warnDetailNo);
		collateralWarningDetail.setWarnNo(warnNo);
		collateralWarningDetail = collateralWarningDetailFeign.getById(collateralWarningDetail);
		getObjValue(formdlcollateralwarningdetail0002, collateralWarningDetail, formData);
		if (collateralWarningDetail != null) {
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
	@RequestMapping("/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String warnDetailNo, String warnNo, String ajaxData, String tableId)
			throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		CollateralWarningDetail collateralWarningDetail = new CollateralWarningDetail();
		collateralWarningDetail.setWarnDetailNo(warnDetailNo);
		collateralWarningDetail.setWarnNo(warnNo);
		try {
			JSONObject jb = JSONObject.fromObject(ajaxData);
			collateralWarningDetail = (CollateralWarningDetail) JSONObject.toBean(jb, CollateralWarningDetail.class);
			collateralWarningDetailFeign.delete(collateralWarningDetail);
			getTableData(dataMap, tableId, collateralWarningDetail);// 获取列表
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

	/***
	 * 列表数据查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
			String warnNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		CollateralWarningDetail collateralWarningDetail = new CollateralWarningDetail();
		// mfWarningParm.setParmType("1");
		collateralWarningDetail.setWarnNo(warnNo);
		try {
			List<CollateralWarningDetail> collateralWarningDetailList = collateralWarningDetailFeign
					.getAll(collateralWarningDetail);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, "tableTag", collateralWarningDetailList, null, true);

			dataMap.put("flag", "success");
			dataMap.put("tableData", tableHtml);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

	@RequestMapping("/updateListAjax")
	@ResponseBody
	public Map<String, Object> updateListAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			JSONArray jsonArray = JSONArray.fromObject(ajaxData);
			@SuppressWarnings("unchecked")
			List<CollateralWarningDetail> collateralWarningDetailAjaxList = (List<CollateralWarningDetail>) JSONArray
					.toList(jsonArray, new CollateralWarningDetail(), new JsonConfig());
			collateralWarningDetailFeign.updateList(collateralWarningDetailAjaxList);
			dataMap.put("flag", "success");
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_UPDATE.getMessage());
			throw e;
		}
		return dataMap;
	}

}
