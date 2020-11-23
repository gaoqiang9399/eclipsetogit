package app.component.rec.controller;

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
import com.core.struts.taglib.JsonFormUtil;
import com.core.struts.taglib.JsonTableUtil;

import app.component.rec.entity.RecallConfig;
import app.component.rec.feign.RecallConfigFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import net.sf.json.JSONObject;

/**
 * Title: RecallConfigAction.java Description:
 * 
 * @author:@dhcc.com.cn
 * @Tue Mar 15 09:20:56 GMT 2016
 **/
@Controller
@RequestMapping(value = "/recallConfig")
public class RecallConfigController extends BaseFormBean {
	// 注入业务层
	@Autowired
	private RecallConfigFeign recallConfigFeign;

	// 全局变量
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;

	/**
	 * 获取列表数据
	 * 
	 * @param recallConfig
	 * @return
	 * @throws Exception
	 */

	private void getTableData(String tableId, RecallConfig recallConfig,Map<String,Object> dataMap) throws Exception {
		JsonTableUtil jtu = new JsonTableUtil();
		String tableHtml = jtu.getJsonStr(tableId, "tableTag", recallConfigFeign.getAll(recallConfig), null, true);
		dataMap.put("tableData", tableHtml);
	}

	private void getTableData(List<RecallConfig> list, String tableId,Map<String,Object> dataMap) throws Exception {
		JsonTableUtil jtu = new JsonTableUtil();
		String tableHtml = jtu.getJsonStr(tableId, "tableTag", list, null, true);
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
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formrec0003 = formService.getFormData("rec0003");
		model.addAttribute("formrec0003", formrec0003);
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
		FormData formrec0003 = formService.getFormData("rec0003");
		RecallConfig recallConfig = new RecallConfig();
		List<RecallConfig> recallConfigList = recallConfigFeign.getAll(recallConfig);
		model.addAttribute("formrec0003", formrec0003);
		model.addAttribute("recallConfigList", recallConfigList);
		return "getListPage";
	}

	/***
	 * 标准例子 列表数据查询的异步方法
	 * 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/findByPageAjax")
	public Map<String, Object> findByPageAjax(String ajaxData, String tableId, int pageNo, String tableType)
			throws Exception {
		ActionContext.initialize(request, response);
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		RecallConfig recallConfig = new RecallConfig();
		try {
			recallConfig.setCustomQuery(ajaxData);// 自定义查询参数赋值
			recallConfig.setCriteriaList(recallConfig, ajaxData);// 我的筛选
			// this.getRoleConditions(appProject,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage = recallConfigFeign.findByPage(ipage, recallConfig);
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
	@ResponseBody
	@RequestMapping(value = "/insertAjax")
	public Map<String, Object> insertAjax(String ajaxData, String tableId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		try {
			FormData formrec0003 = formService.getFormData("rec0003");
			getFormValue(formrec0003, getMapByJson(ajaxData));
			if (this.validateFormData(formrec0003)) {
				RecallConfig recallConfig = new RecallConfig();
				setObjValue(formrec0003, recallConfig);
				recallConfigFeign.insert(recallConfig);
				// getTableData();//获取列表
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
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
	@ResponseBody
	@RequestMapping(value = "/updateAjax")
	public Map<String, Object> updateAjax(String ajaxData, String tableId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		RecallConfig recallConfig = new RecallConfig();
		try {
			FormData formrec0003 = formService.getFormData("rec0003");
			getFormValue(formrec0003, getMapByJson(ajaxData));
			if (this.validateFormData(formrec0003)) {
				recallConfig = new RecallConfig();
				setObjValue(formrec0003, recallConfig);
				// recallConfig.setRecallNo(recallNo);
				recallConfigFeign.update(recallConfig);
				recallConfig = recallConfigFeign.getById(recallConfig);
				List<RecallConfig> list = new ArrayList<RecallConfig>();
				list.add(recallConfig);
				getTableData(list, tableId,dataMap);
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
	 * 更新启用状态
	 * 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/updateStsAjax")
	public Map<String, Object> updateStsAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		try {
			RecallConfig recallConfig = new RecallConfig();
			JSONObject jb = JSONObject.fromObject(ajaxData);
			recallConfig = (RecallConfig) JSONObject.toBean(jb, RecallConfig.class);
			recallConfigFeign.updateDefSts(recallConfig);
			dataMap.put("flag", "true");
			dataMap.put("msg", MessageEnum.SUCCEED_UPDATE.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "false");
			dataMap.put("msg", MessageEnum.FAILED_UPDATE.getMessage());
			throw e;
		}
		return dataMap;
	}

	@ResponseBody
	@RequestMapping(value = "/inputAjax")
	public Map<String, Object> inputAjax(String query) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		RecallConfig recallConfig = new RecallConfig();
		FormData formrec0003 = formService.getFormData("rec0003");
		JsonFormUtil jfu = new JsonFormUtil();
		String formHtml = jfu.getJsonStr(formrec0003, "bigFormTag", query);
		dataMap.put("formHtml", formHtml);
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
	public Map<String, Object> getByIdAjax(String query) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		FormData formrec0003 = formService.getFormData("rec0003");
		RecallConfig recallConfig = new RecallConfig();
		// recallConfig.setRecallNo(recallNo);
		recallConfig = recallConfigFeign.getById(recallConfig);
		getObjValue(formrec0003, recallConfig);
		JsonFormUtil jfu = new JsonFormUtil();
		String formHtml = jfu.getJsonStr(formrec0003, "bigFormTag", query);
		dataMap.put("formHtml", formHtml);
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
	public Map<String, Object> deleteAjax(String tableId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		RecallConfig recallConfig = new RecallConfig();
		// recallConfig.setRecallNo(recallNo);
		try {
			recallConfigFeign.delete(recallConfig);
			getTableData(tableId, recallConfig,dataMap);// 获取列表
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
