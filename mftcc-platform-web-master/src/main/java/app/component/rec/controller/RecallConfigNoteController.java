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
import com.core.struts.taglib.JsonTableUtil;

import app.component.common.EntityUtil;
import app.component.finance.util.CwPublicUtil;
import app.component.rec.entity.RecallConfig;
import app.component.rec.entity.RecallConfigNote;
import app.component.rec.feign.RecallConfigNoteFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.WaterIdUtil;
import net.sf.json.JSONObject;

/**
 * Title: RecallConfigNoteAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Mon Mar 20 09:55:30 CST 2017
 **/
@Controller
@RequestMapping(value = "/recallConfigNote")
public class RecallConfigNoteController extends BaseFormBean {

	// 注入业务层
	@Autowired
	private RecallConfigNoteFeign recallConfigNoteFeign;

	// 全局变量
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;

	/**
	 * 获取列表数据
	 * 
	 * @param tableId
	 * @return
	 * @throws Exception
	 */
	private void getTableData(String tableId,Map<String,Object> dataMap) throws Exception {

		JsonTableUtil jtu = new JsonTableUtil();
		String tableHtml = jtu.getJsonStr(tableId, "thirdTableTag",
				recallConfigNoteFeign.getAll(new RecallConfigNote()), null, true);
		dataMap.put("tableData", tableHtml);
	}

	/**
	 * 列表有翻页
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage() throws Exception {
		ActionContext.initialize(request, response);
		return "RecallConfigNote_List";
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
		FormData formdlrecallconfignote0002 = formService.getFormData("dlrecallconfignote0002");
		RecallConfigNote recallConfigNote = new RecallConfigNote();
		List<RecallConfigNote> recallConfigNoteList = recallConfigNoteFeign.getAll(recallConfigNote);
		model.addAttribute("formdlrecallconfignote0002", formdlrecallconfignote0002);
		model.addAttribute("recallConfigNoteList", recallConfigNoteList);
		return "RecallConfigNote_List";
	}

	/**
	 * AJAX新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/insertAjax")
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			// formdlrecallconfignote0004 =
			// formService.getFormData("dlrecallconfignote0004");
			// getFormValue(formdlrecallconfignote0004, getMapByJson(ajaxData));
			FormData formdlrecallconfig0001 = formService.getFormData("dlrecallconfig0001");
			getFormValue(formdlrecallconfig0001, getMapByJson(ajaxData));
			// if(this.validateFormData(formdlrecallconfignote0004)){
			RecallConfigNote recallConfigNote = new RecallConfigNote();
			setObjValue(formdlrecallconfig0001, recallConfigNote);
			List<RecallConfigNote> list = recallConfigNoteFeign.getByRecKindNo(recallConfigNote);
			if (list.size() == 0) {
				String noteNo = WaterIdUtil.getWaterId();
				recallConfigNote.setNoteNo(noteNo);
				if ("1".equals(recallConfigNote.getNoteCondition())) {
					recallConfigNote.setModelName("短信催收" + "(应还款日前)");
				} else if ("2".equals(recallConfigNote.getNoteCondition())) {
					recallConfigNote.setModelName("短信催收" + "(逾期后)");
				} else {
					recallConfigNote.setModelName("短信催收" + "(按固定日期)");
				}
				recallConfigNote.setReturnAfterInteval("1");// 短信规则的"逾期发送频率"默认每天发送.
				recallConfigNoteFeign.insert(recallConfigNote);
				RecallConfig recallConfig = new RecallConfig();
				recallConfig.setId(recallConfigNote.getNoteNo());
				recallConfig.setRecallWay("1");
				recallConfig.setNoteCondition(recallConfigNote.getNoteCondition());
				recallConfig.setModelName(recallConfigNote.getModelName());
				recallConfig.setRecallDesc(recallConfigNote.getRecallDesc());
				recallConfig.setRecKindNo(recallConfigNote.getRecKindNo());
				dataMap.put("recItem", recallConfig);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", "该产品已增加过此种催收方案");
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
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		RecallConfigNote recallConfigNote = new RecallConfigNote();
		try {
			FormData formdlrecallconfignote0004 = formService.getFormData("dlrecallconfignote0004");
			getFormValue(formdlrecallconfignote0004, getMapByJson(ajaxData));
			if (this.validateFormData(formdlrecallconfignote0004)) {
				recallConfigNote = new RecallConfigNote();
				setObjValue(formdlrecallconfignote0004, recallConfigNote);
				if ("1".equals(recallConfigNote.getNoteCondition())) {
					recallConfigNote.setModelName("短信催收" + "(应还款日前)");
				} else if ("2".equals(recallConfigNote.getNoteCondition())) {
					recallConfigNote.setModelName("短信催收" + "(逾期后)");
				} else {
					recallConfigNote.setModelName("短信催收" + "(按固定日期)");
				}

				// List<RecallConfigNote> list =
				// recallConfigNoteFeign.getByRecKindNo(recallConfigNote);
				// if(list.size() == 0){
				recallConfigNoteFeign.update(recallConfigNote);
				// getTableData();//获取列表
				RecallConfig recallConfig = new RecallConfig();
				recallConfig.setId(recallConfigNote.getNoteNo());
				recallConfig.setRecallWay("1");
				recallConfig.setNoteCondition(recallConfigNote.getNoteCondition());
				recallConfig.setModelName(recallConfigNote.getModelName());
				recallConfig.setRecallDesc(recallConfigNote.getRecallDesc());
				recallConfig.setRecKindNo(recallConfigNote.getRecKindNo());
				dataMap.put("recItem", recallConfig);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_UPDATE.getMessage());
				// }else{
				// dataMap.put("flag", "error");
				// dataMap.put("msg", "该产品已增加过此种催收方案");
				// }
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
	public Map<String, Object> getByIdAjax(String noteNo) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formdlrecallconfignote0002 = formService.getFormData("dlrecallconfignote0002");
		RecallConfigNote recallConfigNote = new RecallConfigNote();
		recallConfigNote.setNoteNo(noteNo);
		recallConfigNote = recallConfigNoteFeign.getById(recallConfigNote);
		getObjValue(formdlrecallconfignote0002, recallConfigNote, formData);
		if (recallConfigNote != null) {
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
	public Map<String, Object> deleteAjax(String noteNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		RecallConfigNote recallConfigNote = new RecallConfigNote();
		recallConfigNote.setNoteNo(noteNo);
		try {
			// JSONObject jb = JSONObject.fromObject(ajaxData);
			// recallConfigNote = (RecallConfigNote)JSONObject.toBean(jb,
			// RecallConfigNote.class);
			recallConfigNoteFeign.delete(recallConfigNote);
			// getTableData();//获取列表
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
		FormData formdlrecallconfignote0004 = formService.getFormData("dlrecallconfignote0004");

		return "RecallConfigNote_Insert";
	}

	@ResponseBody
	@RequestMapping(value = "/updateAjaxByOne")
	public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();

		FormData formdlrecallconfignote0006 = formService.getFormData("dlrecallconfignote0006");
		getFormValue(formdlrecallconfignote0006, getMapByJson(ajaxData));

		RecallConfigNote recallConfigNoteJsp = new RecallConfigNote();
		setObjValue(formdlrecallconfignote0006, recallConfigNoteJsp);

		RecallConfigNote recallConfigNote = recallConfigNoteFeign.getById(recallConfigNoteJsp);
		if (recallConfigNote != null) {
			try {
				recallConfigNote = (RecallConfigNote) EntityUtil.reflectionSetVal(recallConfigNote, recallConfigNoteJsp,
						getMapByJson(ajaxData));
				recallConfigNoteFeign.update(recallConfigNote);

				dataMap.put("flag", "success");
				dataMap.put("msg", "保存成功");
			} catch (Exception e) {
				e.printStackTrace();
				dataMap.put("flag", "error");
				dataMap.put("msg", "新增失败");
				throw e;
			}
		} else {
			dataMap.put("flag", "error");
			dataMap.put("msg", "编号不存在,保存失败");
		}
		return dataMap;
	}

	@ResponseBody
	@RequestMapping(value = "/findByPageAjax")
	public Map<String, Object> findByPageAjax(String ajaxData, int pageNo, String tableId, String tableType)
			throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		RecallConfigNote recallConfigNote = new RecallConfigNote();
		try {
			recallConfigNote.setCustomQuery(ajaxData);// 自定义查询参数赋值
			recallConfigNote.setCriteriaList(recallConfigNote, ajaxData);// 我的筛选
			// this.getRoleConditions(recallConfigNote,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage = recallConfigNoteFeign.findByPage(ipage, recallConfigNote);
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

	@ResponseBody
	@RequestMapping(value = "/updateStatusAjax")
	public Map<String, Object> updateStatusAjax(String ajaxData, String tableId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		RecallConfigNote recallConfigNote = new RecallConfigNote();
		try {
			JSONObject jobj = JSONObject.fromObject(ajaxData);
			FormData formdlrecallconfignote0004 = formService.getFormData("dlrecallconfignote0004");
			getFormValue(formdlrecallconfignote0004, jobj);
			setObjValue(formdlrecallconfignote0004, recallConfigNote);
			int count = recallConfigNoteFeign.updateSts(recallConfigNote);
			if (count > 0) {
				recallConfigNote = recallConfigNoteFeign.getById(recallConfigNote);
				switch (Integer.parseInt(recallConfigNote.getNoteCondition())) {
				case 1: {
					recallConfigNote.setReturnAfterDays("-");
					recallConfigNote.setReturnAfterInteval("-");
					recallConfigNote.setNoteDay("-");
					break;
				}
				case 2: {
					recallConfigNote.setReturnBeforeDays("-");
					recallConfigNote.setNoteDay("-");
					break;
				}
				case 3: {
					recallConfigNote.setReturnBeforeDays("-");
					recallConfigNote.setReturnAfterDays("-");
					recallConfigNote.setReturnAfterInteval("-");
					break;
				}
				default:
				}
				String recallDesc = "";
				recallDesc += "<p title='" + recallConfigNote.getRecallDesc() + "'>"
						+ CwPublicUtil.cutStrToLenth(recallConfigNote.getRecallDesc(), 15) + "</p>";
				recallConfigNote.setRecallDesc(recallDesc);
				ArrayList<RecallConfigNote> recallConfigNoteList = new java.util.ArrayList<RecallConfigNote>();
				recallConfigNoteList.add(recallConfigNote);
				getTableData(recallConfigNoteList, tableId,dataMap);
				dataMap.put("flag", "success");
				dataMap.put("msg", "更新成功");
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", "更新失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "更新失败");
			throw e;
		}
		return dataMap;
	}

	private void getTableData(List<RecallConfigNote> list, String tableId,Map<String,Object> dataMap) throws Exception {
		JsonTableUtil jtu = new JsonTableUtil();
		String tableHtml = jtu.getJsonStr(tableId, "thirdTableTag", list, null, true);
		dataMap.put("tableData", tableHtml);
	}

	@RequestMapping(value = "/getById")
	public String getById(Model model, String noteNo) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formdlrecallconfignote0004 = formService.getFormData("dlrecallconfignote0004");
		getFormValue(formdlrecallconfignote0004);
		RecallConfigNote recallConfigNote = new RecallConfigNote();
		recallConfigNote.setNoteNo(noteNo);
		recallConfigNote = recallConfigNoteFeign.getById(recallConfigNote);
		getObjValue(formdlrecallconfignote0004, recallConfigNote);
		model.addAttribute("formdlrecallconfignote0004", formdlrecallconfignote0004);
		return "RecallConfigNote_Detail";
	}

}
