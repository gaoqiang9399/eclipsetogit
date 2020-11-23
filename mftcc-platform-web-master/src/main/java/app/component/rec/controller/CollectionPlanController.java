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
import com.core.struts.taglib.JsonFormUtil;
import com.core.struts.taglib.JsonTableUtil;

import app.component.rec.entity.CollectionPlan;
import app.component.rec.entity.MfCollectionTable;
import app.component.rec.entity.RecallConfigLaw;
import app.component.rec.entity.RecallConfigLetter;
import app.component.rec.entity.RecallConfigNote;
import app.component.rec.entity.RecallConfigOut;
import app.component.rec.entity.RecallConfigPhone;
import app.component.rec.entity.RecallConfigVisit;
import app.component.rec.feign.CollectionPlanFeign;
import app.component.rec.feign.MfCollectionTableFeign;
import app.component.rec.feign.RecallConfigLawFeign;
import app.component.rec.feign.RecallConfigLetterFeign;
import app.component.rec.feign.RecallConfigNoteFeign;
import app.component.rec.feign.RecallConfigOutFeign;
import app.component.rec.feign.RecallConfigPhoneFeign;
import app.component.rec.feign.RecallConfigVisitFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.WaterIdUtil;
import net.sf.json.JSONObject;

/**
 * Title: CollectionPlanAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Sun Mar 19 14:05:01 CST 2017
 **/
@Controller
@RequestMapping(value = "/collectionPlan")
public class CollectionPlanController extends BaseFormBean {

	// 注入业务层
	@Autowired
	private CollectionPlanFeign collectionPlanFeign;
	@Autowired
	private MfCollectionTableFeign mfCollectionTableFeign;
	@Autowired
	private RecallConfigNoteFeign recallConfigNoteFeign;
	@Autowired
	private RecallConfigPhoneFeign recallConfigPhoneFeign;
	@Autowired
	private RecallConfigLetterFeign recallConfigLetterFeign;
	@Autowired
	private RecallConfigVisitFeign recallConfigVisitFeign;
	@Autowired
	private RecallConfigOutFeign recallConfigOutFeign;
	@Autowired
	private RecallConfigLawFeign recallConfigLawFeign;

	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;

	/**
	 * 获取列表数据
	 * 
	 * @return
	 * @throws Exception
	 */
	private void getTableData(String tableId, CollectionPlan collectionPlan,Map<String,Object> dataMap) throws Exception {
		JsonTableUtil jtu = new JsonTableUtil();
		String tableHtml = jtu.getJsonStr(tableId, "thirdTableTag", collectionPlanFeign.getAll(collectionPlan), null,
				true);
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
		FormData formdlcollectionplan0002 = formService.getFormData("dlcollectionplan0002");
		CollectionPlan collectionPlan = new CollectionPlan();
		Ipage ipage = this.getIpage();
		List<CollectionPlan> collectionPlanList = (List<CollectionPlan>) collectionPlanFeign
				.findByPage(ipage, collectionPlan).getResult();
		model.addAttribute("collectionPlanList", collectionPlanList);
		return "CollectionPlan_List";
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
		FormData formdlcollectionplan0002 = formService.getFormData("dlcollectionplan0002");
		CollectionPlan collectionPlan = new CollectionPlan();
		List<CollectionPlan> collectionPlanList = collectionPlanFeign.getAll(collectionPlan);
		model.addAttribute("collectionPlanList", collectionPlanList);
		return "CollectionPlan_List";
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
		Map<String, Object>dataMap = new HashMap<String, Object>();
		try {
			FormData formdlcollectionplan0002 = formService.getFormData("dlcollectionplan0002");
			getFormValue(formdlcollectionplan0002, getMapByJson(ajaxData));
			if (this.validateFormData(formdlcollectionplan0002)) {
				CollectionPlan collectionPlan = new CollectionPlan();
				setObjValue(formdlcollectionplan0002, collectionPlan);

				// 生成主键
				String collectionPlanNo = WaterIdUtil.getWaterId();
				collectionPlan.setCollectionPlanNo(collectionPlanNo);
				collectionPlanFeign.insert(collectionPlan);

				// getTableData();//获取列表

				dataMap.put("collectionPlanNo", collectionPlanNo);
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
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object>dataMap = new HashMap<String, Object>();
		CollectionPlan collectionPlan = new CollectionPlan();
		try {
			FormData formdlcollectionplan0002 = formService.getFormData("dlcollectionplan0002");
			getFormValue(formdlcollectionplan0002, getMapByJson(ajaxData));
			if (this.validateFormData(formdlcollectionplan0002)) {
				collectionPlan = new CollectionPlan();
				setObjValue(formdlcollectionplan0002, collectionPlan);
				collectionPlanFeign.update(collectionPlan);
				// getTableData();//获取列表
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
	public Map<String, Object> getByIdAjax(String collectionPlanNo) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object>dataMap = new HashMap<String, Object>();
		FormData formdlcollectionplan0002 = formService.getFormData("dlcollectionplan0002");
		CollectionPlan collectionPlan = new CollectionPlan();
		collectionPlan.setCollectionPlanNo(collectionPlanNo);
		collectionPlan = collectionPlanFeign.getById(collectionPlan);
		getObjValue(formdlcollectionplan0002, collectionPlan, formData);
		if (collectionPlan != null) {
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
	public Map<String, Object> deleteAjax(String collectionPlanNo, String tableId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object>dataMap = new HashMap<String, Object>();
		CollectionPlan collectionPlan = new CollectionPlan();
		collectionPlan.setCollectionPlanNo(collectionPlanNo);
		try {
			// JSONObject jb = JSONObject.fromObject(ajaxData);
			// collectionPlan = (CollectionPlan)JSONObject.toBean(jb,
			// CollectionPlan.class);
			MfCollectionTable mfCollectionTable = new MfCollectionTable();
			mfCollectionTable.setCollectionNo(collectionPlanNo);

			RecallConfigNote recallConfigNote = new RecallConfigNote();
			recallConfigNote.setCollectionPlanNo(collectionPlanNo);

			RecallConfigPhone recallConfigPhone = new RecallConfigPhone();
			recallConfigPhone.setCollectionPlanNo(collectionPlanNo);

			RecallConfigLetter recallConfigLetter = new RecallConfigLetter();
			recallConfigLetter.setCollectionPlanNo(collectionPlanNo);

			RecallConfigVisit recallConfigVisit = new RecallConfigVisit();
			recallConfigVisit.setCollectionPlanNo(collectionPlanNo);

			RecallConfigOut recallConfigOut = new RecallConfigOut();
			recallConfigOut.setCollectionPlanNo(collectionPlanNo);

			RecallConfigLaw recallConfigLaw = new RecallConfigLaw();
			recallConfigLaw.setCollectionPlanNo(collectionPlanNo);

			collectionPlanFeign.delete(collectionPlan);

			mfCollectionTableFeign.delete(mfCollectionTable);
			recallConfigNoteFeign.delete(recallConfigNote);
			recallConfigPhoneFeign.delete(recallConfigPhone);
			recallConfigLetterFeign.delete(recallConfigLetter);
			recallConfigVisitFeign.delete(recallConfigVisit);
			recallConfigOutFeign.delete(recallConfigOut);
			recallConfigLawFeign.delete(recallConfigLaw);

			getTableData(tableId, collectionPlan,dataMap);// 获取列表
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

	/***
	 * 标准例子 列表数据查询的异步方法
	 * 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/findByPageAjax")
	public Map<String, Object> findByPageAjax(String ajaxData, int pageNo, String tableId, String tableType)
			throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object>dataMap = new HashMap<String, Object>();
		CollectionPlan collectionPlan = new CollectionPlan();
		try {
			collectionPlan.setCustomQuery(ajaxData);// 自定义查询参数赋值
			collectionPlan.setCriteriaList(collectionPlan, ajaxData);// 我的筛选
			// this.getRoleConditions(appProject,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Feign方法
			ipage = collectionPlanFeign.findByPage(ipage, collectionPlan);
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
	 * 新增页面
	 * 
	 * @return
	 * @throws Exception
	 */
	/**
	 * public String input() throws Exception {
	 * ActionContext.initialize(request, response); formdlcollectionplan0002 =
	 * formService.getFormData("dlcollectionplan0002"); //获取产品种类 MfSysKind
	 * mfSysKind= new MfSysKind(); List<MfSysKind> list =
	 * prdctInterface.getSysKindList(mfSysKind); JSONArray array =
	 * JSONArray.fromObject(list); json = new JSONObject(); json.put("kindList",
	 * array); return "CollectionPlan_Insert"; }
	 **/

	@RequestMapping(value = "/input")
	public String input(Model model, String recKindNo) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formdlrecallconfig0001 = formService.getFormData("dlrecallconfig0001");
		RecallConfigNote recallConfigNote = new RecallConfigNote();
		recallConfigNote.setRecKindNo(recKindNo);
		getObjValue(formdlrecallconfig0001, recallConfigNote);
		model.addAttribute("formdlrecallconfig0001", formdlrecallconfig0001);
		return "RecallConfig_Input";
	}

	/***
	 * 方法描述： 催收方案详情主页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String collectionPlanNo, String query) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formcommon = null;
		CollectionPlan collectionPlan = new CollectionPlan();
		collectionPlan.setCollectionPlanNo(collectionPlanNo);
		collectionPlan = collectionPlanFeign.getById(collectionPlan);

		MfCollectionTable mfCollectionTable = new MfCollectionTable();
		mfCollectionTable.setCollectionNo(collectionPlanNo);
		List<MfCollectionTable> collectionTableList = mfCollectionTableFeign.getList(mfCollectionTable);
		JsonFormUtil jsonFormUtil = new JsonFormUtil();
		JsonTableUtil jtu = new JsonTableUtil();
		/*
		 * 当query为"query"或者ifBizManger为"0"时，解析的表单中不可单字段编辑；
		 * 当ifBizManger为"1"或""时，解析的表单中设置的可编辑的字段可以单字段编辑；
		 * 当ifBizManger为"2"时，解析的表单中所有非只读的字段可以单字段编辑；
		 */
		request.setAttribute("ifBizManger", "1");
		for (int i = 0; i < collectionTableList.size(); i++) {
			if ("0".equals(collectionTableList.get(i).getDataFullFlag())) {
				continue;
			}
			String action = collectionTableList.get(i).getAction();
			String htmlStr = "";
			if ("RecallConfigNoteAction".equals(action)) {
				RecallConfigNote recallConfigNote = new RecallConfigNote();
				recallConfigNote.setCollectionPlanNo(collectionPlanNo);
				recallConfigNote = recallConfigNoteFeign.getByCollectionPlanNo(recallConfigNote);
				formcommon = formService.getFormData(collectionTableList.get(i).getShowModelDef());

				getFormValue(formcommon);
				getObjValue(formcommon, recallConfigNote);
				htmlStr = jsonFormUtil.getJsonStr(formcommon, "propertySeeTag", query);
			} else if ("RecallConfigPhoneAction".equals(action)) {
				RecallConfigPhone recallConfigPhone = new RecallConfigPhone();
				recallConfigPhone.setCollectionPlanNo(collectionPlanNo);
				recallConfigPhone = recallConfigPhoneFeign.getByCollectionPlanNo(recallConfigPhone);
				formcommon = formService.getFormData(collectionTableList.get(i).getShowModelDef());

				getFormValue(formcommon);
				getObjValue(formcommon, recallConfigPhone);
				htmlStr = jsonFormUtil.getJsonStr(formcommon, "propertySeeTag", query);
			} else if ("RecallConfigLetterAction".equals(action)) {
				RecallConfigLetter recallConfigLetter = new RecallConfigLetter();
				recallConfigLetter.setCollectionPlanNo(collectionPlanNo);
				recallConfigLetter = recallConfigLetterFeign.getByCollectionPlanNo(recallConfigLetter);
				formcommon = formService.getFormData(collectionTableList.get(i).getShowModelDef());

				getFormValue(formcommon);
				getObjValue(formcommon, recallConfigLetter);
				htmlStr = jsonFormUtil.getJsonStr(formcommon, "propertySeeTag", query);
			} else if ("RecallConfigVisitAction".equals(action)) {
				RecallConfigVisit recallConfigVisit = new RecallConfigVisit();
				recallConfigVisit.setCollectionPlanNo(collectionPlanNo);
				recallConfigVisit = recallConfigVisitFeign.getByCollectionPlanNo(recallConfigVisit);
				formcommon = formService.getFormData(collectionTableList.get(i).getShowModelDef());

				getFormValue(formcommon);
				getObjValue(formcommon, recallConfigVisit);
				htmlStr = jsonFormUtil.getJsonStr(formcommon, "propertySeeTag", query);
			} else if ("RecallConfigOutAction".equals(action)) {
				RecallConfigOut recallConfigOut = new RecallConfigOut();
				recallConfigOut.setCollectionPlanNo(collectionPlanNo);
				recallConfigOut = recallConfigOutFeign.getByCollectionPlanNo(recallConfigOut);
				formcommon = formService.getFormData(collectionTableList.get(i).getShowModelDef());

				getFormValue(formcommon);
				getObjValue(formcommon, recallConfigOut);
				htmlStr = jsonFormUtil.getJsonStr(formcommon, "propertySeeTag", query);
			} else if ("RecallConfigLawAction".equals(action)) {
				RecallConfigLaw recallConfigLaw = new RecallConfigLaw();
				recallConfigLaw.setCollectionPlanNo(collectionPlanNo);
				recallConfigLaw = recallConfigLawFeign.getByCollectionPlanNo(recallConfigLaw);
				formcommon = formService.getFormData(collectionTableList.get(i).getShowModelDef());

				getFormValue(formcommon);
				getObjValue(formcommon, recallConfigLaw);
				htmlStr = jsonFormUtil.getJsonStr(formcommon, "propertySeeTag", query);
			}else {
			}

			collectionTableList.get(i).setHtmlStr(htmlStr);
		}

		Map<String, Object>dataMap = new HashMap<String, Object>();
		dataMap.put("collectionTableList", collectionTableList);
		JSONObject jb = JSONObject.fromObject(dataMap);
		dataMap = jb;

		model.addAttribute("dataMap", dataMap);
		model.addAttribute("formcommon", formcommon);

		return "CollectionPlan_Detail";
	}

	/***
	 * 方法描述： 催收方案启用
	 * 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/updateStatusAjax")
	public Map<String, Object> updateStatusAjax(String ajaxData, String tableId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object>dataMap = new HashMap<String, Object>();
		CollectionPlan collectionPlan = new CollectionPlan();

		try {
			JSONObject jsonObject = JSONObject.fromObject(ajaxData);
			collectionPlan = (CollectionPlan) JSONObject.toBean(jsonObject, CollectionPlan.class);
			collectionPlanFeign.update(collectionPlan);
			List<CollectionPlan> list = collectionPlanFeign.getAll(new CollectionPlan());
			getTableData(list, tableId, dataMap);
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

	private void getTableData(List<CollectionPlan> list, String tableId,Map<String,Object> dataMap) throws Exception {
		JsonTableUtil jtu = new JsonTableUtil();
		String tableHtml = jtu.getJsonStr(tableId, "thirdTableTag", list, null, true);
		dataMap.put("tableData", tableHtml);
	}

	/***
	 * 方法描述： 催收方案修改主页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getByIdForUpdate")
	public String getByIdForUpdate(Model model, String collectionPlanNo) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formdlcollectionplan0002 = formService.getFormData("dlcollectionplan0002");

		CollectionPlan collectionPlan = new CollectionPlan();
		collectionPlan.setCollectionPlanNo(collectionPlanNo);
		collectionPlan = collectionPlanFeign.getById(collectionPlan);

		getObjValue(formdlcollectionplan0002, collectionPlan);
		model.addAttribute("formdlcollectionplan0002", formdlcollectionplan0002);
		return "CollectionPlan_DetailForUpdate";
	}

	@RequestMapping(value = "/openSysKindSelectTableList")
	public String openSysKindSelectTableList() throws Exception {
		ActionContext.initialize(request, response);
		return "CollectionPlan_SysKindList";
	}

}
