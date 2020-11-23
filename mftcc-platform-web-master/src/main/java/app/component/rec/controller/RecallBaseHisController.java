package app.component.rec.controller;

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

import app.component.rec.entity.RecallBaseHis;
import app.component.rec.feign.RecallBaseHisFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import net.sf.json.JSONObject;

/**
 * Title: RecallBaseHisAction.java Description:
 * 
 * @author:@dhcc.com.cn
 * @Tue Mar 15 09:27:00 GMT 2016
 **/
@Controller
@RequestMapping(value = "/recallBaseHis")
public class RecallBaseHisController extends BaseFormBean {
	// 注入业务层
	@Autowired
	private RecallBaseHisFeign recallBaseHisFeign;

	// 全局变量
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;

	/**
	 * 获取列表数据
	 * 
	 * @param recallBaseHis
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getTableData")
	private void getTableData(String tableId, RecallBaseHis recallBaseHis,Map<String,Object> dataMap) throws Exception {
		JsonTableUtil jtu = new JsonTableUtil();
		String tableHtml = jtu.getJsonStr(tableId, "tableTag", recallBaseHisFeign.getAll(recallBaseHis), null, true);
		dataMap.put("tableData", tableHtml);
	}

	/**
	 * 列表有翻页
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model, String conNo) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formrec0005 = formService.getFormData("rec0005");
		RecallBaseHis recallBaseHis = new RecallBaseHis();
		recallBaseHis.setConNo(conNo);
		Ipage ipage = this.getIpage();
		List<RecallBaseHis> recallBaseHisList = (List<RecallBaseHis>) recallBaseHisFeign
				.findByPage(ipage, recallBaseHis).getResult();
		model.addAttribute("formrec0005", formrec0005);
		model.addAttribute("recallBaseHisList", recallBaseHisList);
		return "getListPage";
	}

	/**
	 * 列表全部无翻页
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListAll")
	public String getListAll(Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formrec0005 = formService.getFormData("rec0005");
		RecallBaseHis recallBaseHis = new RecallBaseHis();
		List<RecallBaseHis> recallBaseHisList = recallBaseHisFeign.getAll(recallBaseHis);
		model.addAttribute("formrec0005", formrec0005);
		model.addAttribute("recallBaseHisList", recallBaseHisList);
		return "getListPage";
	}

	/**
	 * 合同中催收历史查看页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getHisView")
	public String getHisView(Model model, String hisNo) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formrec0005 = formService.getFormData("rec0005");
		RecallBaseHis recallBaseHis = new RecallBaseHis();
		recallBaseHis.setHisNo(hisNo);
		recallBaseHis = recallBaseHisFeign.getById(recallBaseHis);
		getObjValue(formrec0005, recallBaseHis);

		// acLnPayPlnList =
		// accInterface.getAcLnPayPlnByConNo(recallBaseHis.getConNo());
		String query = "query";

		model.addAttribute("formrec0005", formrec0005);
		model.addAttribute("query", query);
		return "view";
	}

	/**
	 * AJAX新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/insertAjax")
	public Map<String, Object> insertAjax(String ajaxData, String tableId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		try {

			FormData formrec0005 = formService.getFormData("rec0005");
			getFormValue(formrec0005, getMapByJson(ajaxData));
			if (this.validateFormData(formrec0005)) {
				RecallBaseHis recallBaseHis = new RecallBaseHis();
				setObjValue(formrec0005, recallBaseHis);
				recallBaseHisFeign.insert(recallBaseHis);
				getTableData(tableId, recallBaseHis,dataMap);// 获取列表
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
	@ResponseBody
	@RequestMapping(value = "/updateAjax")
	public Map<String, Object> updateAjax(String ajaxData, String tableId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		RecallBaseHis recallBaseHis = new RecallBaseHis();
		try {
			FormData formrec0005 = formService.getFormData("rec0005");
			getFormValue(formrec0005, getMapByJson(ajaxData));
			if (this.validateFormData(formrec0005)) {
				recallBaseHis = new RecallBaseHis();
				setObjValue(formrec0005, recallBaseHis);
				recallBaseHisFeign.update(recallBaseHis);
				getTableData(tableId, recallBaseHis,dataMap);// 获取列表
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
	@ResponseBody
	@RequestMapping(value = "/getByIdAjax")
	public Map<String, Object> getByIdAjax(String hisNo) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		FormData formrec0005 = formService.getFormData("rec0005");
		RecallBaseHis recallBaseHis = new RecallBaseHis();
		recallBaseHis.setHisNo(hisNo);
		recallBaseHis = recallBaseHisFeign.getById(recallBaseHis);
		getObjValue(formrec0005, recallBaseHis, formData);
		if (recallBaseHis != null) {
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
	@ResponseBody
	@RequestMapping(value = "/deleteAjax")
	public Map<String, Object> deleteAjax(String hisNo, String ajaxData, String tableId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		RecallBaseHis recallBaseHis = new RecallBaseHis();
		recallBaseHis.setHisNo(hisNo);
		try {
			JSONObject jb = JSONObject.fromObject(ajaxData);
			recallBaseHis = (RecallBaseHis) JSONObject.toBean(jb, RecallBaseHis.class);
			recallBaseHisFeign.delete(recallBaseHis);
			getTableData(tableId, recallBaseHis,dataMap);// 获取列表
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
	 * 租后跟踪历史信息（含租后检查，催收，五级分类）
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getAllListPage")
	public String getAllListPage(Model model, String conNo) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		// 根据合同号获取催收信息
		RecallBaseHis recallBaseHis = new RecallBaseHis();
		recallBaseHis.setConNo(conNo);
		List<RecallBaseHis> recallBaseHisList = recallBaseHisFeign.getAll(recallBaseHis);
		// 根据合同号获取五级分类信息
		// AppProjectCont appProjectCont = appInterfaceFeign.getContByConNo(conNo);

		// claTaskLstList =
		// claInterface.getAllList(conNo,appProjectCont.getManageNo());
		FormData formcla0013 = formService.getFormData("cla0013");
		// 根据合同号 获取租后检查信息
		FormData formllc0009 = formService.getFormData("llc0009");
		// llcTaskBaseList = llcInterface.getAllList(conNo,"");
		model.addAttribute("recallBaseHisList", recallBaseHisList);
		model.addAttribute("formcla0013", formcla0013);
		model.addAttribute("formllc0009", formllc0009);
		return "getListPage";
	}

}
