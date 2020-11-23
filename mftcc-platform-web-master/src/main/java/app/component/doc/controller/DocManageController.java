package app.component.doc.controller;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.print.Doc;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.component.doc.entity.DocManageList;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;
import com.dhcc.workflow.pvm.internal.task.TaskImpl;
import com.google.gson.Gson;

import app.base.User;
import app.component.app.entity.MfBusApply;
import app.component.appinterface.AppInterfaceFeign;
import app.component.common.BizPubParm;
import app.component.common.EntityUtil;
import app.component.doc.entity.DocBizManage;
import app.component.doc.entity.DocBizManageParam;
import app.component.doc.entity.DocManage;
import app.component.doc.feign.DocFeign;
import app.component.doc.feign.DocManageFeign;
import app.component.docinterface.DocInterfaceFeign;
import app.component.wkf.AppConstant;
import app.component.wkf.feign.WorkflowDwrFeign;
import app.component.wkfinterface.WkfInterfaceFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.StringUtil;
import net.sf.json.JSONArray;

/**
 * Title: DocManageController.java Description:
 * 
 * @author:@dhcc.com.cn
 * @Tue Jan 26 11:18:01 GMT 2016
 **/
@Controller
@RequestMapping("/docManage")
public class DocManageController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private DocManageFeign docManageFeign;
	@Autowired
	private WkfInterfaceFeign wkfInterfaceFeign;
	@Autowired
	private AppInterfaceFeign appInterfaceFeign;
	@Autowired
	private DocInterfaceFeign docInterfaceFeign;
	@Autowired
	private WorkflowDwrFeign workflowDwrFeign;
	@Autowired
	private DocFeign docFeign;

	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getListPage")
	public String getListPage() throws Exception {
		ActionContext.initialize(request, response);
		return "/component/doc/DocManage_List";
	}

	/***
	 * 列表数据查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findByPageAjax")
	@ResponseBody public Map<String, Object> findByPageAjax(String ajaxData, Integer pageNo, Integer pageSize, String tableId,
			String tableType) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		DocManage docManage = new DocManage();
		try {
			docManage.setCustomQuery(ajaxData);// 自定义查询参数赋值
			docManage.setCriteriaList(docManage, ajaxData);// 我的筛选
			// getRoleConditions(docManage,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);
			ipage.setPageSize(pageSize);// 异步传页面翻页参数
			// 自定义查询Feign方法
			ipage = docManageFeign.findByPage(ipage, docManage);
			JsonTableUtil jtu = new JsonTableUtil();
			@SuppressWarnings("rawtypes")
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
	@RequestMapping("/insertAjax")
	@ResponseBody public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formdoc0011 = formService.getFormData("doc0011");
			getFormValue(formdoc0011, getMapByJson(ajaxData));
			if (this.validateFormData(formdoc0011)) {
				DocManage docManage = new DocManage();
				setObjValue(formdoc0011, docManage);
				docManageFeign.insert(docManage);
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
	 * ajax 异步 单个字段或多个字段更新
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateAjaxByOne")
	@ResponseBody public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formdoc0011 = formService.getFormData("doc0011");
		getFormValue(formdoc0011, getMapByJson(ajaxData));
		DocManage docManageJsp = new DocManage();
		setObjValue(formdoc0011, docManageJsp);
		DocManage docManage = docManageFeign.getById(docManageJsp);
		if (docManage != null) {
			try {
				docManage = (DocManage) EntityUtil.reflectionSetVal(docManage, docManageJsp, getMapByJson(ajaxData));
				docManageFeign.update(docManage);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_SAVE.getMessage());
			} catch (Exception e) {
				e.printStackTrace();
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
	@RequestMapping("/updateAjax")
	@ResponseBody public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formdoc0011 = formService.getFormData("doc0011");
			getFormValue(formdoc0011, getMapByJson(ajaxData));
			if (this.validateFormData(formdoc0011)) {
				DocManage docManage = new DocManage();
				setObjValue(formdoc0011, docManage);
				docManageFeign.update(docManage);
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
	@ResponseBody public Map<String, Object> getByIdAjax(String ajaxData,String docNo,String docBizNo) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formdoc0011 = formService.getFormData("doc0011");
		DocManage docManage = new DocManage();
		docManage.setDocNo(docNo);
		docManage.setDocBizNo(docBizNo);
		docManage = docManageFeign.getById(docManage);
		getObjValue(formdoc0011, docManage, formData);
		if (docManage != null) {
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
	@ResponseBody public Map<String, Object> deleteAjax(String ajaxData,String docNo,String docBizNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		DocManage docManage = new DocManage();
		docManage.setDocNo(docNo);
		docManage.setDocBizNo(docBizNo);
		try {
			docManageFeign.delete(docManage);
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
	 * 
	 * 方法描述： 根据业务编号删除上传的要件
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2017-10-10 下午3:40:00
	 */
	@RequestMapping("/deleteByBizNoAjax")
	@ResponseBody public Map<String, Object> deleteByBizNoAjax(String ajaxData,String docBizNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		DocManage docManage = new DocManage();
		docManage.setDocBizNo(docBizNo);
		try {
			docManageFeign.deleteByBizNo(docManage);
			dataMap.put("flag", "success");
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
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping("/input") 
	public String input(Model model) throws Exception{
		ActionContext.initialize(request,response);
		DocBizManage docBizManage = new DocBizManage();
		docBizManage.setRelNo("0000001");
		//this.docDisplays = this.docinterface.getDocDisPlayList(docBizManage);
		DocBizManageParam dm = new DocBizManageParam();
		dm.setScNo(BizPubParm.SCENCE_TYPE_DOC_APP); //场景编号
		dm.setRelNo("0000002");//业务编号
		dm.setDime("01");//维度  注：根据字典项业务代码来组合，维度为空取默认，如果默认未配置则报错
		dm.setCusNo("aaa");//客户号
		dm.setCusName("张学友");//客户名称
		dm.setRegNo("0000");//
		dm.setOrgNo("5800");//登记机构
		dm.setRegDate("20160219");//登记日期
		docInterfaceFeign.createDocBizManage(dm);
		model.addAttribute("query", "");
		return "/component/doc/DocManage_Insert";
	}

	/**
	 * 新增页面
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping("/docManageListAdd")
	public String docManageListAdd(Model model,String scNo,String cusNo,String relNo,String cusType,
                                   String appId,String pactId,String fincId,String viewType,String docType,
                                   String docTypeName,String docSplitName,String docSplitNoArr) throws Exception{
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formDocManageAdd = formService.getFormData("DocManageAdd");
		if (relNo == null) {
			relNo = "";
		}
		StringBuilder relsNo = new StringBuilder(relNo);
        relsNo.append(",");
		if (StringUtil.isNotEmpty(cusNo)) {
			relsNo.append(cusNo).append(",");
		}
		if (StringUtil.isNotEmpty(appId)) {
			relsNo.append(appId).append(",");
		}
		if (StringUtil.isNotEmpty(pactId)) {
			relsNo.append(pactId).append(",");
		}
		if (StringUtil.isNotEmpty(fincId)) {
			relsNo.append(fincId).append(",");
		}

		Gson gson = new Gson();
		JSONArray docJSONArray;
		/**
		 * 如果传入的视角参数是客户
		 * 则走客户特有方法
		 * 否则走公共方法
		 */
		if("cus".equals(viewType)){
			docJSONArray= this.docFeign.getCusDocDisPlayForUpload(scNo,cusNo,relNo,relsNo.toString(),cusType);
		}else if("single".equals(viewType)){
		    Map<String,Object> paramMap = new HashMap<>();
            paramMap.put("relNo",relNo);
            paramMap.put("docType",docType);
            paramMap.put("docTypeName",docTypeName);
            paramMap.put("docSplitName",docSplitName);
            paramMap.put("docSplitNoArr",docSplitNoArr);
            paramMap.put("appId",appId);
			String result = this.docFeign.getDocDisPlayListAloneCommForUpload(paramMap);
            docJSONArray= JSONArray.fromObject(result);
		}else{
            docJSONArray = this.docFeign.getDocDisPlayForUpload(scNo,relsNo.toString());
        }

		String param = docJSONArray.toString();
		List<Map<String,Object>> docList = gson.fromJson(param,List.class);
		List<Map<String,Object>> typeListIsMust = new ArrayList<>();
		List<Map<String,Object>> typeListNotMust = new ArrayList<>();
		List<Map<String,Object>> docListNew = gson.fromJson(param,List.class);
		List<Map<String,Object>> typeList = new ArrayList<>();


		for(Map<String,Object> map: docList){
			Map<String,Object> bigTypeMap = new HashMap<>();
			bigTypeMap.put("groupName",map.get("docTypeName"));
			Map<String,Object> imgs = (Map<String, Object>) map.get("imgs");
			List<Map<String,Object>> bigTypeList = new ArrayList<>();
			for ( String k: imgs.keySet()){
				Map<String,Object> splitMap = (Map<String, Object>) imgs.get(k);
				String docSplit = (String) splitMap.get("docSplitName");
				if("1".equals(splitMap.get("ifMustInput"))){
					docSplit += "<span style='color:red;'>（必填）</span>";
				}
				splitMap.put("name",docSplit);
				splitMap.put("id",splitMap.get("docSplitNo"));
				splitMap.put("scNo",scNo);
				bigTypeList.add(splitMap);
				docListNew.add(splitMap);
			}
			bigTypeMap.put("items",bigTypeList);
			typeList.add(bigTypeMap);
		}

		String typeListString = gson.toJson(typeList);
		String docListString = gson.toJson(docListNew);
		model.addAttribute("formDocManageAdd", formDocManageAdd);
		model.addAttribute("query", "");
		model.addAttribute("docTypeList",typeListString);
		model.addAttribute("docList",docListString);
		model.addAttribute("relNo",relNo);
		model.addAttribute("cusNo",cusNo);
		model.addAttribute("scNo",scNo);
		return "component/doc/webUpload/DocManageAdd";
	}


	/***
	 * 新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/insert")
	public String insert(Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formdoc0011 = formService.getFormData("doc0011");
		getFormValue(formdoc0011);
		DocManage docManage = new DocManage();
		setObjValue(formdoc0011, docManage);
		docManageFeign.insert(docManage);
		getObjValue(formdoc0011, docManage);
		this.addActionMessage(model, "保存成功");
		@SuppressWarnings("unused")
		List<DocManage> docManageList = (List<DocManage>) docManageFeign.findByPage(this.getIpage(), docManage)
				.getResult();
		model.addAttribute("query", "");
		model.addAttribute("formdoc0011", formdoc0011);
		model.addAttribute("docManageList", docManageList);
		return "/component/doc/DocManage_Detail";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getById")
	public String getById(Model model,String docNo,String docBizNo) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formdoc0011 = formService.getFormData("doc0011");
		getFormValue(formdoc0011);
		DocManage docManage = new DocManage();
		docManage.setDocNo(docNo);
		docManage.setDocBizNo(docBizNo);
		docManage = docManageFeign.getById(docManage);
		getObjValue(formdoc0011, docManage);
		model.addAttribute("formdoc0011", formdoc0011);
		model.addAttribute("query", "");
		return "/component/doc/DocManage_Detail";
	}

	/**
	 * 上传
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/upload")
	public String upload(Model model,String scNo,String relNo,String cusNo,String appNo,String taskId) throws Exception {
		ActionContext.initialize(request, response);
		DocBizManage docBizManage = new DocBizManage();
		String[] scNoArr = new String[0];

		if (scNo != null) {
			if (scNo.indexOf("|") > -1) {
				scNoArr = scNo.split("\\|");
			} else {
				scNoArr = new String[] { scNo };
			}
		}
		docBizManage.setRelNo(relNo);
		docBizManage.setCusNo(cusNo);
		docBizManage.setScNo(scNo);
		JSONArray docManageArrayTmp = this.docInterfaceFeign.getDocDisPlayList(docBizManage, scNoArr);
		String zTreeNodes="";
		MfBusApply mfBusApply = new MfBusApply();
		if (docManageArrayTmp.isEmpty()) {
			mfBusApply = appInterfaceFeign.getMfBusApplyByAppId(relNo);
			zTreeNodes = docInterfaceFeign.getDocDisPlayList(docBizManage, scNoArr).toString();
		} else {
			zTreeNodes = docManageArrayTmp.toString();
		}
		request.setAttribute("zTreeNodes", zTreeNodes);
		request.setAttribute("wkfAppId", appNo);
		request.setAttribute("appId", relNo);
		request.setAttribute("taskId", taskId);
		model.addAttribute("query", "");
		model.addAttribute("mfBusApply", mfBusApply);
		return "/component/doc/webUpload/upload";
	}

	/**
	 * 
	 * 方法描述： 详情页面上传
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author zhs
	 * @date 2016-8-31 下午2:36:16
	 */
	@RequestMapping("/pubUpload")
	public String pubUpload(Model model,String scNo,String relNo,String cusNo) throws Exception {
		ActionContext.initialize(request, response);
		DocBizManage docBizManage = new DocBizManage();
		String[] scNoArr = new String[0];
		/*
		 * HttpSession session = request.getSession();
		 * HashMap<String,Map<String,String>> funNoTypes =
		 * (HashMap<String,Map<String,String>>)session.getAttribute("funNoType")
		 * ; Map<String,String> rangMap = funNoTypes.get("1000200064"); String
		 * funNoType =rangMap.get("funRoleType"); String opNo =
		 * User.getOrgNo(request); if("1".equals(funNoType)){//登记人
		 * if(opNo.equals("")){
		 * 
		 * }else{
		 * 
		 * } }else if("2".equals(funNoType)){//本机构 为什么用IN，还查询了用户表
		 * 
		 * }else if("3".equals(funNoType)){//本机及其向下
		 * 
		 * }
		 */
		docBizManage.setRelNo(relNo);
		docBizManage.setCusNo(cusNo);
		docBizManage.setScNo(scNo);
		String zTreeNodes = docInterfaceFeign.getDocDisPlayList(docBizManage, scNoArr).toString();
		request.setAttribute("zTreeNodes", zTreeNodes);
		request.setAttribute("appId", relNo);
		model.addAttribute("query", "");
		return "/component/doc/pub_upload";
	}

	/**
	 * @author czk
	 * @Description: 异步获取文档信息 date 2017-1-16
	 */
	@RequestMapping("/getDocNodesAjax")
	@ResponseBody public Map<String, Object> getDocNodesAjax(String ajaxData,String relNo,String cusNo,String appId,String pactId,String fincId,String scNo,String query) throws Exception {
		ActionContext.initialize(request, response);
		if (relNo == null) {
			relNo = "";
		}
		StringBuilder relsNo = new StringBuilder(relNo);
        //如果relsNo这个值是空不可加逗号后面调用时会查询所有
        if (relNo != null && !"".equals(relNo)) {
            relsNo.append(",");
        }
		if (StringUtil.isNotEmpty(cusNo)) {
			relsNo.append(",").append(cusNo);
		}
		if (StringUtil.isNotEmpty(appId)) {
			relsNo.append(",").append(appId);
		}
		if (StringUtil.isNotEmpty(pactId)) {
			relsNo.append(",").append(pactId);
		}
		if (StringUtil.isNotEmpty(fincId)) {
			relsNo.append(",").append(fincId);
		}
		String creditQueryAppId = request.getParameter("creditQueryAppId");
        if (StringUtil.isNotEmpty(creditQueryAppId)) {
            relsNo.append(",").append(creditQueryAppId);
        }
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			scNo = scNo == null?"":scNo;
			JSONArray docJSONArray = docFeign.getDocDisPlay(scNo, relsNo.toString());
            if (StringUtil.isNotEmpty(creditQueryAppId) && docJSONArray != null) {
                //电签页面不显示个人资料
                JSONArray newDocJSONArray = new JSONArray();
                for (int i=0;i< docJSONArray.size() ;i++) {
                    JSONObject js = docJSONArray.getJSONObject(i);
                    newDocJSONArray.add(js);
                }
                dataMap.put("zTreeNodes", newDocJSONArray);
            }else if(docJSONArray != null){// 非电签资料显示页面不展示电签资料信息
                JSONArray newDocJSONArray = new JSONArray();
                for (int i=0;i< docJSONArray.size() ;i++) {
                    JSONObject js = docJSONArray.getJSONObject(i);
                    newDocJSONArray.add(js);
                }
                dataMap.put("zTreeNodes", newDocJSONArray);
            }else{
                dataMap.put("zTreeNodes", docJSONArray);
            }
            dataMap.put("query", query);
		} catch (Exception e) {
//			logger.error("获取要件配置出错", e);
			throw e;
		}
		return dataMap;
	}

	/**
	 * 
	 * 方法描述： 获取客户视角要件信息（直接读取的后台配置）
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author zhs
	 * @date 2017-10-25 下午5:36:55
	 */
	@RequestMapping("/getCusDocNodesAjax")
	@ResponseBody public Map<String, Object> getCusDocNodesAjax(String ajaxData,String relNo,String cusNo,String appId,String pactId,String fincId,String scNo,String cusType,String query,String creditQueryAppId) throws Exception {
		ActionContext.initialize(request, response);
		if (relNo == null) {
			relNo = "";
		}
		StringBuilder relsNo = new StringBuilder(relNo);
        relsNo.append(",");
		if (StringUtil.isNotEmpty(cusNo)) {
			relsNo.append(cusNo).append(",");
		}
		if (StringUtil.isNotEmpty(appId)) {
			relsNo.append(appId).append(",");
		}
		if (StringUtil.isNotEmpty(pactId)) {
			relsNo.append(pactId).append(",");
		}
		if (StringUtil.isNotEmpty(fincId)) {
			relsNo.append(fincId).append(",");
		}
		if (StringUtil.isNotEmpty(creditQueryAppId)) {
			relsNo.append(creditQueryAppId).append(",");
		}
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			//参数为null，微服务调用不好使
			scNo = scNo == null?"":scNo;
			cusNo = cusNo == null?"":cusNo;
			cusType = cusType == null?"":cusType;
			JSONArray docJSONArray = this.docFeign.getCusDocDisPlay(scNo,cusNo,relNo,relsNo.toString(),cusType);
            if (StringUtil.isNotEmpty(creditQueryAppId) && docJSONArray != null) {
                //电签页面不显示个人资料
                JSONArray newDocJSONArray = new JSONArray();
                for (int i=0;i< docJSONArray.size() ;i++) {
                    JSONObject js = docJSONArray.getJSONObject(i);
                    newDocJSONArray.add(js);
                }
                dataMap.put("zTreeNodes", newDocJSONArray);
            }else if(docJSONArray != null){// 非电签资料显示页面不展示电签资料信息
                JSONArray newDocJSONArray = new JSONArray();
                for (int i=0;i< docJSONArray.size() ;i++) {
                    JSONObject js = docJSONArray.getJSONObject(i);
                    newDocJSONArray.add(js);
                }
                dataMap.put("zTreeNodes", newDocJSONArray);
            }else{
                dataMap.put("zTreeNodes", docJSONArray);
            }
			dataMap.put("query", query);
		} catch (Exception e) {
//			logger.error("获取要件配置出错", e);
			throw e;
		}
		return dataMap;
	}

    /**
     * 获取业务视角中的列表方式展示要件的数据
     * @author yxy
     * @Description: 异步获取文档信息 date 2017-1-16
     */
    @RequestMapping("/getDocNodesForListAjax")
    @ResponseBody public Map<String, Object> getDocNodesForListAjax(String ajaxData,String relNo,String cusNo,String appId,String pactId,String fincId,String scNo,String query,String tableId ,Ipage ipage,String filterInInput,String num,String creditQueryAppId) throws Exception {
        ActionContext.initialize(request, response);
        if (relNo == null) {
            relNo = "";
        }
        StringBuilder relsNo = new StringBuilder(relNo);
        relsNo.append(",");
        if (StringUtil.isNotEmpty(cusNo)) {
            relsNo.append(",").append(cusNo);
        }
        if (StringUtil.isNotEmpty(appId)) {
            relsNo.append(",").append(appId);
        }
        if (StringUtil.isNotEmpty(pactId)) {
            relsNo.append(",").append(pactId);
        }
        if (StringUtil.isNotEmpty(fincId)) {
            relsNo.append(",").append(fincId);
        }
        if (StringUtil.isNotEmpty(creditQueryAppId)) {
            relsNo.append(",").append(creditQueryAppId);
        }
        Map<String, Object> dataMap = new HashMap<String, Object>();
		List<DocManage> docManagesTo = new ArrayList<>();//用于根据条件进行筛选。
		List<DocManage> docManages = new ArrayList<>();
        try {
            scNo = scNo == null?"":scNo;
			if(StringUtil.isNotEmpty(num)){
				docManages = this.docFeign.getDocDisPlayForRankList(scNo,relsNo.toString(),num);
			}else{
				docManages = this.docFeign.getDocDisPlayForList(scNo,relsNo.toString());
			}

			for(DocManage docManage : docManages){
				if(StringUtil.isNotEmpty(filterInInput)){
					if(docManage.getDocName().indexOf(filterInInput)!=-1 || docManage.getDocSplitName().indexOf(filterInInput)!=-1){//包含筛选字段
						docManagesTo.add(docManage);
					}
				}else{//筛选字段为空则展示全部数据
                        docManagesTo.add(docManage);
				}
			}
			JsonTableUtil jtu = new JsonTableUtil();
            String tableHtml = jtu.getJsonStr(tableId,"tableTag", docManagesTo, null,true);

            dataMap.put("query", query);
            dataMap.put("tableHtml", tableHtml);
        } catch (Exception e) {
//			logger.error("获取要件配置出错", e);
            throw e;
        }
        return dataMap;
    }

	/**
	 *
	 * 方法描述： 获取客户视角要件信息（直接读取的后台配置）
	 *
	 * @return
	 * @throws Exception
	 *             String
	 * @author
	 * @date 2017-10-25 下午5:36:55
	 */
	@RequestMapping("/getCusDocNodesForListAjax")
	@ResponseBody public Map<String, Object> getCusDocNodesForListAjax(String ajaxData,String relNo,String cusNo,String appId,String pactId,String fincId,String scNo,String cusType,String query,String tableId,String filterInInput,String num,String creditQueryAppId) throws Exception {
		ActionContext.initialize(request, response);
        List<DocManage> docManagesTo = new ArrayList<>();//用于根据条件进行筛选。
		if (relNo == null) {
			relNo = "";
		}
		StringBuilder relsNo = new StringBuilder(relNo);
        relsNo.append(",");
		if (StringUtil.isNotEmpty(cusNo)) {
			relsNo.append(cusNo).append(",");
		}
		if (StringUtil.isNotEmpty(appId)) {
			relsNo.append(appId).append(",");
		}
		if (StringUtil.isNotEmpty(pactId)) {
			relsNo.append(pactId).append(",");
		}
		if (StringUtil.isNotEmpty(fincId)) {
			relsNo.append(fincId).append(",");
		}
        if (StringUtil.isNotEmpty(creditQueryAppId)) {
            relsNo.append(",").append(creditQueryAppId);
        }
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<DocManage> docManages = new ArrayList<>();
		try {
			//参数为null，微服务调用不好使
			scNo = scNo == null?"":scNo;
			cusNo = cusNo == null?"":cusNo;
			cusType = cusType == null?"":cusType;

			if(StringUtil.isNotEmpty(num)){
				docManages = this.docFeign.getCusDocDisPlayForRankList(scNo,cusNo,relNo,relsNo.toString(),cusType,num);
			}else{
				docManages = this.docFeign.getCusDocDisPlayForList(scNo,cusNo,relNo,relsNo.toString(),cusType);
			}

            for(DocManage docManage : docManages){
                if(StringUtil.isNotEmpty(filterInInput)){
                    if(docManage.getDocName().indexOf(filterInInput)!=-1 || docManage.getDocSplitName().indexOf(filterInInput)!=-1){//包含筛选字段
                        docManagesTo.add(docManage);
                    }
                }else{//筛选字段为空则展示全部数据
                        docManagesTo.add(docManage);
                }
            }
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId,"tableTag", docManagesTo, null,true);

			dataMap.put("tableHtml",tableHtml);
			dataMap.put("docManages",docManagesTo);

			dataMap.put("query", query);
		} catch (Exception e) {
//			logger.error("获取要件配置出错", e);
			throw e;
		}
		return dataMap;
	}

	/**
	 * 
	 * 方法描述： 单独上传组件
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2017-4-6 下午8:23:13
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/getDocNodesAloneCommAjax")
	@ResponseBody 
	public Map<String, Object> getDocNodesAloneCommAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		String datatmpJson = URLDecoder.decode(ajaxData, "UTF-8");
		dataMap = new Gson().fromJson(datatmpJson, Map.class);
		String result =this.docFeign.getDocDisPlayListAloneComm(dataMap);
		JSONArray docJSONArray = JSONArray.fromObject(result);
		dataMap.put("zTreeNodes", docJSONArray.toString());
		return dataMap;
	}

	/**
	 *
	 * 方法描述： 列表单独上传组件
	 *
	 * @return
	 * @throws Exception
	 *             String
	 * @author yxy
	 * @date 2018-12-12 下午8:23:13
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/getDocNodesAloneCommForListAjax")
	@ResponseBody
	public Map<String, Object> getDocNodesAloneCommForListAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		String datatmpJson = URLDecoder.decode(ajaxData, "UTF-8");
		dataMap = new Gson().fromJson(datatmpJson, Map.class);
		String result =this.docFeign.getDocDisPlayListAloneCommForList(dataMap);
		Gson gson = new Gson();

		JSONArray docJSONArray = JSONArray.fromObject(result);
		List<Map<String,Object>> docManageMaps =gson.fromJson(result,List.class);
		List<DocManage> docManages =new ArrayList<>();
		for (Map<String,Object> m : docManageMaps){
			DocManage docManage = gson.fromJson(gson.toJson(m),DocManage.class);
			docManages.add(docManage);
		}
		JsonTableUtil jtu = new JsonTableUtil();
		String tableHtml = jtu.getJsonStr("tableDocManageList","tableTag", docManages, null,true);
		dataMap.put("tableHtml",tableHtml);
		dataMap.put("docManages",docManages);

		return dataMap;
	}

	/**
	 * 
	 * 方法描述： 审批页面处资料
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2016-9-14 下午4:52:47
	 */
	@RequestMapping("/pubUploadApprove")
	public String pubUploadApprove(Model model,String ajaxData,String relNo,String cusNo,String appId,String pactId,String scNo) throws Exception {
		ActionContext.initialize(request, response);
		DocBizManage docBizManage = new DocBizManage();
		String[] scNoArr = new String[0];

		if (scNo != null) {
			if (scNo.indexOf("|") > -1) {
				scNoArr = scNo.split("\\|");
			} else {
				scNoArr = new String[] { scNo };
			}
		}
		docBizManage.setRelNo(cusNo);
		docBizManage.setCusNo(cusNo);
		// docBizManage.setScNo(scNo);
		String docDisplayArray = docInterfaceFeign.getDocDisPlayList(docBizManage, scNoArr).toString();
		String docDisplayArray1 = null;
		String docDisplayArray2 = null;
		docDisplayArray = docDisplayArray.substring(0, docDisplayArray.length() - 1);
		if (!"".equals(appId) && appId != null) {
			docBizManage.setRelNo(appId);
			docBizManage.setCusNo(cusNo);
			// docBizManage.setScNo(scNo);
			docDisplayArray1 = docInterfaceFeign.getDocDisPlayList(docBizManage, scNoArr).toString();
			docDisplayArray1 = docDisplayArray1.substring(1, docDisplayArray1.length() - 1);
			if ("[".equals(docDisplayArray)) {
				docDisplayArray = docDisplayArray + docDisplayArray1;
			} else {
				docDisplayArray = docDisplayArray + "," + docDisplayArray1;
			}
		}
		if (!"".equals(pactId) && pactId != null) {
			docBizManage.setRelNo(pactId);
			docBizManage.setCusNo(cusNo);
			// docBizManage.setScNo(scNo);
			docDisplayArray2 = docInterfaceFeign.getDocDisPlayList(docBizManage, scNoArr).toString();
			docDisplayArray2 = docDisplayArray2.substring(1, docDisplayArray2.length() - 1);
			if ("[".equals(docDisplayArray)) {
				docDisplayArray = docDisplayArray + docDisplayArray2;
			} else {
				docDisplayArray = docDisplayArray + "," + docDisplayArray2;
			}
		}
		docDisplayArray = docDisplayArray + "]";
		request.setAttribute("zTreeNodes", docDisplayArray);
		request.setAttribute("appId", relNo);
		model.addAttribute("query", "");
		return "/component/doc/pub_upload";
	}

	/**
	 * 删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delete")
	public String delete(String docNo,String docBizNo) throws Exception {
		ActionContext.initialize(request, response);
		DocManage docManage = new DocManage();
		docManage.setDocNo(docNo);
		docManage.setDocBizNo(docBizNo);
		docManageFeign.delete(docManage);
		return getListPage();
	}

	@RequestMapping("/toNextPointAjax")
	@ResponseBody
	public Map<String, Object> toNextPointAjax(String ajaxData,String taskId,String appId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {

			/*
			 * MfBusApply mfBusApply= new MfBusApply(); mfBusApply =
			 * appInterfaceFeign.getMfBusApplyByAppId(appId); Task task=
			 * wkfInterfaceFeign.getTask(mfBusApply.getWkfAppId(), null); String
			 * title=task.getDescription(); Map<String,String> map = new
			 * HashMap<String, String>(); map.put("pleId",
			 * mfBusPledge.getPleId()); map.put("appId",
			 * mfBusPledge.getAppId()); map.put("cusNo",
			 * mfBusPledge.getCusNo()); List<String> infoList = new ArrayList();
			 * infoList.add("完成贷前调查");
			 * bizInterfaceFeign.insertInfo(BizPubParm.CHANGE_TYPE_INFO, title ,
			 * map, BizPubParm.BIZ_TYPE_APP, infoList);
			 */
			// 业务进入下一个流程节点
			String transition = workflowDwrFeign.findNextTransition(taskId);
			wkfInterfaceFeign.doCommit(taskId, AppConstant.OPINION_TYPE_ARREE, "", transition,
					User.getRegNo(request), "");
			// 处理业务阶段
			MfBusApply mfBusApply = new MfBusApply();
			mfBusApply = appInterfaceFeign.getMfBusApplyByAppId(appId);
			TaskImpl task = wkfInterfaceFeign.getTask(mfBusApply.getWkfAppId(), null);
			mfBusApply.setBusStage(task.getDescription());
			appInterfaceFeign.updateApply(mfBusApply);

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