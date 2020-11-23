package app.component.pss.information.controller;

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
import com.core.struts.taglib.JsonFormUtil;
import com.core.struts.taglib.JsonTableUtil;

import app.component.pss.information.entity.PssBillnoConf;
import app.component.pss.information.feign.PssBillnoConfFeign;
import cn.mftcc.util.WaterIdUtil;
import net.sf.json.JSONObject;

/**
 * Title: PssBillnoConfAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Tue Sep 12 14:31:41 CST 2017
 **/
@Controller
@RequestMapping("/pssBillnoConf")
public class PssBillnoConfController extends BaseFormBean {
	// 注入业务层
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private PssBillnoConfFeign pssBillnoConfFeign;

	// 全局变量
	// 表单变量
	/**
	 * 获取列表数据
	 * 
	 * @return
	 * @throws Exception
	 */
	private void getTableData(String tableId,PssBillnoConf pssBillnoConf) throws Exception {
		JsonTableUtil jtu = new JsonTableUtil();
		String tableHtml = jtu.getJsonStr(tableId, "tableTag", pssBillnoConfFeign.getAll(pssBillnoConf), null, true);
		Map<String,Object> dataMap=new HashMap<String,Object>();
		dataMap.put("tableData", tableHtml);
	}

	/**
	 * 列表有翻页
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formbillnoconf0002 = formService.getFormData("billnoconf0002");
		model.addAttribute("formbillnoconf0002", formbillnoconf0002);
		model.addAttribute("query", "");
		return "/component/pss/information/PssBillnoConf_List";
	}

	/**
	 * 列表全部无翻页
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListAll")
	public String getListAll(Model model, String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formbillnoconf0002 = formService.getFormData("billnoconf0002");
		PssBillnoConf pssBillnoConf = new PssBillnoConf();
		List<PssBillnoConf> pssBillnoConfList = pssBillnoConfFeign.getAll(pssBillnoConf);
		model.addAttribute("formbillnoconf0002", formbillnoconf0002);
		model.addAttribute("pssBillnoConfList", pssBillnoConfList);
		model.addAttribute("query", "");
		return "/component/pss/information/PssBillnoConf_List";
	}

	@RequestMapping(value = "/getBillNoFormAjax")
	@ResponseBody
	public Map<String, Object> getBillNoFormAjax(String billType) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formbillnoconf0002 = formService.getFormData("billnoconf0002");
		try {
			PssBillnoConf pssBillnoConf = new PssBillnoConf();
			pssBillnoConf.setBillType(billType);
			pssBillnoConf = pssBillnoConfFeign.getByType(pssBillnoConf);
			if (null == pssBillnoConf) {
				pssBillnoConf = new PssBillnoConf();
				pssBillnoConf.setNoId(WaterIdUtil.getWaterId());
				pssBillnoConf.setBillType(billType);
			}
			getObjValue(formbillnoconf0002, pssBillnoConf);
			JsonFormUtil jsonFormUtil = new JsonFormUtil();
			String formHtml = jsonFormUtil.getJsonStr(formbillnoconf0002, "bootstarpTag", "");
			dataMap.put("flag", "success");
			dataMap.put("formHtml", formHtml);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "操作失败");
			throw new Exception(e.getMessage());
		}
		return dataMap;
	}

	@RequestMapping(value = "/saveConfAjax")
	@ResponseBody
	public Map<String, Object> saveConfAjax(String ajaxData,String tableId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formbillnoconf0002 = formService.getFormData("billnoconf0002");
			getFormValue(formbillnoconf0002, getMapByJson(ajaxData));
			if (this.validateFormData(formbillnoconf0002)) {
				PssBillnoConf pssBillnoConf = new PssBillnoConf();
				setObjValue(formbillnoconf0002, pssBillnoConf);
				PssBillnoConf old = pssBillnoConfFeign.getByType(pssBillnoConf);
				if (null == old) {
					pssBillnoConfFeign.insert(pssBillnoConf);
				} else {
					pssBillnoConfFeign.update(pssBillnoConf);
				}
				getTableData(tableId,pssBillnoConf);// 获取列表
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
	 * AJAX新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData,String tableId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formbillnoconf0002 = formService.getFormData("billnoconf0002");
			getFormValue(formbillnoconf0002, getMapByJson(ajaxData));
			if (this.validateFormData(formbillnoconf0002)) {
				PssBillnoConf pssBillnoConf = new PssBillnoConf();
				setObjValue(formbillnoconf0002, pssBillnoConf);
				pssBillnoConfFeign.insert(pssBillnoConf);
				getTableData(tableId,pssBillnoConf);// 获取列表
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
	 * AJAX更新保存
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateAjax")
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData,String tableId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssBillnoConf pssBillnoConf = new PssBillnoConf();
		try {
			FormData formbillnoconf0002 = formService.getFormData("billnoconf0002");
			getFormValue(formbillnoconf0002, getMapByJson(ajaxData));
			if (this.validateFormData(formbillnoconf0002)) {
				pssBillnoConf = new PssBillnoConf();
				setObjValue(formbillnoconf0002, pssBillnoConf);
				pssBillnoConfFeign.update(pssBillnoConf);
				getTableData(tableId,pssBillnoConf);// 获取列表
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
	public Map<String, Object> getByIdAjax(String noId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formbillnoconf0002 = formService.getFormData("billnoconf0002");
		PssBillnoConf pssBillnoConf = new PssBillnoConf();
		pssBillnoConf.setNoId(noId);
		pssBillnoConf = pssBillnoConfFeign.getById(pssBillnoConf);
		getObjValue(formbillnoconf0002, pssBillnoConf, formData);
		if (pssBillnoConf != null) {
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
	public Map<String, Object> deleteAjax(String noId,String ajaxData,String tableId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssBillnoConf pssBillnoConf = new PssBillnoConf();
		pssBillnoConf.setNoId(noId);
		try {
			JSONObject jb = JSONObject.fromObject(ajaxData);
			pssBillnoConf = (PssBillnoConf) JSONObject.toBean(jb, PssBillnoConf.class);
			pssBillnoConfFeign.delete(pssBillnoConf);
			getTableData(tableId,pssBillnoConf);// 获取列表
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

}
