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

import app.component.collateral.entity.WarnInfo;
import app.component.collateral.feign.WarnInfoFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import net.sf.json.JSONObject;

/**
 * Title: WarnInfoController.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Wed Mar 08 11:22:01 CST 2017
 **/
@Controller
@RequestMapping("/warnInfo")
public class WarnInfoController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private WarnInfoFeign warnInfoFeign;

	/**
	 * 获取列表数据
	 * 
	 * @return
	 * @throws Exception
	 */
	private void getTableData(Map<String, Object> dataMap, String tableId, WarnInfo warnInfo) throws Exception {
		JsonTableUtil jtu = new JsonTableUtil();
		String tableHtml = jtu.getJsonStr(tableId, "tableTag", warnInfoFeign.getAll(warnInfo), null, true);
		dataMap.put("tableData", tableHtml);
	}

	/**
	 * 列表有翻页
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formdlcollateralwarn0002 = formService.getFormData("dlcollateralwarn0002");
		WarnInfo warnInfo = new WarnInfo();
		Ipage ipage = this.getIpage();
		List<WarnInfo> warnInfoList = (List<WarnInfo>) warnInfoFeign.findByPage(ipage, warnInfo).getResult();
		model.addAttribute("formdlcollateralwarn0002", formdlcollateralwarn0002);
		model.addAttribute("warnInfoList", warnInfoList);
		model.addAttribute("query", "");
		return "/component/collateral/WarnInfo_List";
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
		FormData formdlcollateralwarn0002 = formService.getFormData("dlcollateralwarn0002");
		WarnInfo warnInfo = new WarnInfo();
		List<WarnInfo> warnInfoList = warnInfoFeign.getAll(warnInfo);
		model.addAttribute("formdlcollateralwarn0002", formdlcollateralwarn0002);
		model.addAttribute("warnInfoList", warnInfoList);
		model.addAttribute("query", "");
		return "/component/collateral/WarnInfo_List";
	}

	/**
	 * AJAX新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/insertAjax")
	@ResponseBody public Map<String, Object> insertAjax(String ajaxData,String tableId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formdlcollateralwarn0002 = formService.getFormData("dlcollateralwarn0002");
			getFormValue(formdlcollateralwarn0002, getMapByJson(ajaxData));
			if (this.validateFormData(formdlcollateralwarn0002)) {
				WarnInfo warnInfo = new WarnInfo();
				setObjValue(formdlcollateralwarn0002, warnInfo);
				warnInfoFeign.insert(warnInfo);
				getTableData(dataMap, tableId, warnInfo);// 获取列表
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
	@RequestMapping("/updateAjax")
	@ResponseBody public Map<String, Object> updateAjax(String ajaxData,String tableId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formdlcollateralwarn0002 = formService.getFormData("dlcollateralwarn0002");
			getFormValue(formdlcollateralwarn0002, getMapByJson(ajaxData));
			if (this.validateFormData(formdlcollateralwarn0002)) {
				WarnInfo warnInfo = new WarnInfo();
				setObjValue(formdlcollateralwarn0002, warnInfo);
				warnInfoFeign.update(warnInfo);
				getTableData(dataMap, tableId, warnInfo);// 获取列表
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
	@RequestMapping("/getByIdAjax")
	@ResponseBody public Map<String, Object> getByIdAjax(String ajaxData,String warnId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formdlcollateralwarn0002 = formService.getFormData("dlcollateralwarn0002");
		WarnInfo warnInfo = new WarnInfo();
		warnInfo.setWarnId(warnId);
		warnInfo = warnInfoFeign.getById(warnInfo);
		getObjValue(formdlcollateralwarn0002, warnInfo, formData);
		if (warnInfo != null) {
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
	public Map<String, Object> deleteAjax(String ajaxData,String warnId,String tableId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		WarnInfo warnInfo = new WarnInfo();
		warnInfo.setWarnId(warnId);
		try {
			JSONObject jb = JSONObject.fromObject(ajaxData);
			warnInfo = (WarnInfo) JSONObject.toBean(jb, WarnInfo.class);
			warnInfoFeign.delete(warnInfo);
			getTableData(dataMap, tableId, warnInfo);// 获取列表
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

}
