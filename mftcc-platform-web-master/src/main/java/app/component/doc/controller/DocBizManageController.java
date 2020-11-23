package app.component.doc.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.component.cus.entity.MfCusCustomer;
import app.component.cus.feign.MfCusCustomerFeign;
import app.component.doc.entity.DocTypeConfig;
import app.component.doc.feign.DocTypeConfigFeign;
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
import app.component.doc.entity.DocBizManage;
import app.component.doc.feign.DocBizManageFeign;
import app.component.docinterface.DocInterfaceFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.StringUtil;

/**
 * Title: DocBizManageController.java Description:
 * 
 * @author:@dhcc.com.cn
 * @Tue Jan 26 11:15:50 GMT 2016
 **/
@Controller
@RequestMapping("/docBizManage")
public class DocBizManageController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private DocBizManageFeign docBizManageFeign;
	@Autowired
	private DocInterfaceFeign docInterfaceFeign;
	@Autowired
	private DocTypeConfigFeign   docTypeConfigFeign;
	@Autowired
	private MfCusCustomerFeign   mfCusCustomerFeign;

	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getListPage")
	public String getListPage() throws Exception {
		ActionContext.initialize(request, response);
		return "/component/doc/DocBizManage_List";
	}

	/***
	 * 列表数据查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/findByPageAjax")
	@ResponseBody public Map<String, Object> findByPageAjax(String ajaxData, Integer pageNo, Integer pageSize, String tableId,
			String tableType) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		DocBizManage docBizManage = new DocBizManage();
		try {
			docBizManage.setCustomQuery(ajaxData);// 自定义查询参数赋值
			docBizManage.setCriteriaList(docBizManage, ajaxData);// 我的筛选
			// this.getRoleConditions(docBizManage,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);
			ipage.setPageSize(pageSize);// 异步传页面翻页参数
			// 自定义查询Feign方法
			ipage = docBizManageFeign.findByPage(ipage, docBizManage);
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
	@RequestMapping("/insertAjax")
	@ResponseBody public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formdoc0009 = formService.getFormData("doc0009");
			getFormValue(formdoc0009, getMapByJson(ajaxData));
			if (this.validateFormData(formdoc0009)) {
				DocBizManage docBizManage = new DocBizManage();
				setObjValue(formdoc0009, docBizManage);
				docBizManageFeign.insert(docBizManage);
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
		FormData formdoc0009 = formService.getFormData("doc0009");
		getFormValue(formdoc0009, getMapByJson(ajaxData));
		DocBizManage docBizManageJsp = new DocBizManage();
		setObjValue(formdoc0009, docBizManageJsp);
		DocBizManage docBizManage = docBizManageFeign.getById(docBizManageJsp);
		if (docBizManage != null) {
			try {
				docBizManage = (DocBizManage) EntityUtil.reflectionSetVal(docBizManage, docBizManageJsp,
						getMapByJson(ajaxData));
				docBizManageFeign.update(docBizManage);
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
			FormData formdoc0009 = formService.getFormData("doc0009");
			getFormValue(formdoc0009, getMapByJson(ajaxData));
			if (this.validateFormData(formdoc0009)) {
				DocBizManage docBizManage = new DocBizManage();
				setObjValue(formdoc0009, docBizManage);
				docBizManageFeign.update(docBizManage);
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
	@ResponseBody public Map<String, Object> getByIdAjax(String ajaxData, String scNo, String docBizNo) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formdoc0009 = formService.getFormData("doc0009");
		DocBizManage docBizManage = new DocBizManage();
		docBizManage.setScNo(scNo);
		docBizManage.setDocBizNo(docBizNo);
		docBizManage = docBizManageFeign.getById(docBizManage);
		getObjValue(formdoc0009, docBizManage, formData);
		if (docBizManage != null) {
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
	@ResponseBody public Map<String, Object> deleteAjax(String ajaxData, String scNo, String docBizNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		DocBizManage docBizManage = new DocBizManage();
		docBizManage.setScNo(scNo);
		docBizManage.setDocBizNo(docBizNo);
		try {
			docBizManageFeign.delete(docBizManage);
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
	 * AJAX审批时文档校验
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/valiWkfDocIsUp")
	@ResponseBody public Map<String, Object> valiWkfDocIsUp(String relNo, String scNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			if (StringUtil.isNotEmpty(relNo)) {
				if(scNo==null){
					scNo="";
				}
				System.out.println("21323123131");
				List<String> list = docBizManageFeign.valiWkfDocIsUp(relNo, scNo);
				String msg = "";
				if (list.size() > 0) {
					dataMap.put("flag", false);
					for (String str : list) {
						msg += "[" + str + "]、";
					}
					msg = msg.substring(0, msg.length() - 1);
					dataMap.put("msg", MessageEnum.NO_UPLOAD.getMessage(msg));
				} else {
					dataMap.put("flag", true);
				}
			} else {
				dataMap.put("flag", true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_UPDATE.getMessage());
			throw e;
		}
		return dataMap;
	}


	@RequestMapping("/valiCusDocIsUp")
	@ResponseBody public Map<String, Object> valiCusDocIsUp(String relNo, String scNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			if (StringUtil.isNotEmpty(relNo)) {

				MfCusCustomer  mfCusCustomer  = new MfCusCustomer();
				mfCusCustomer.setCusNo(relNo);
				mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
				DocTypeConfig   docTypeConfig  = new DocTypeConfig();
				docTypeConfig.setDocType("202");
				if(mfCusCustomer != null){
					docTypeConfig.setDocType(mfCusCustomer.getCusType());
				}
				docTypeConfig.setFormNo(relNo);
				List<String> list = docBizManageFeign.getListDocSpiltName(docTypeConfig);
				String msg = "";
				if (list.size() > 0) {
					dataMap.put("flag", false);
					for (String str : list) {
						msg += "[" + str + "]、";
					}
					msg = msg.substring(0, msg.length() - 1);
					dataMap.put("msg", MessageEnum.NO_UPLOAD.getMessage(msg));
				} else {
					dataMap.put("flag", true);
				}
			} else {
				dataMap.put("flag", true);
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
	 * 新增页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/input")
	public String input(Model model) throws Exception {
		ActionContext.initialize(request, response);
		return "/component/doc/DocBizManage_Insert";
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
		FormData formdoc0009 = formService.getFormData("doc0009");
		getFormValue(formdoc0009);
		DocBizManage docBizManage = new DocBizManage();
		setObjValue(formdoc0009, docBizManage);
		docBizManageFeign.insert(docBizManage);
		getObjValue(formdoc0009, docBizManage);
		this.addActionMessage(model, "保存成功");
		List<DocBizManage> docBizManageList = (List<DocBizManage>) docBizManageFeign
				.findByPage(this.getIpage(), docBizManage).getResult();
		model.addAttribute("docBizManageList", docBizManageList);
		model.addAttribute("formdoc0009", formdoc0009);
		model.addAttribute("query", "");
		return "/component/doc/DocBizManage_Detail";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getById")
	public String getById(Model model,String scNo,String docBizNo) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formdoc0009 = formService.getFormData("doc0009");
		getFormValue(formdoc0009);
		DocBizManage docBizManage = new DocBizManage();
		docBizManage.setScNo(scNo);
		docBizManage.setDocBizNo(docBizNo);
		docBizManage = docBizManageFeign.getById(docBizManage);
		getObjValue(formdoc0009, docBizManage);
		model.addAttribute("query", "");
		return "/component/doc/DocBizManage_Detail";
	}

	/**
	 * 删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delete")
	public String delete(String scNo,String docBizNo ) throws Exception {
		ActionContext.initialize(request, response);
		DocBizManage docBizManage = new DocBizManage();
		docBizManage.setScNo(scNo);
		docBizManage.setDocBizNo(docBizNo);
		docBizManageFeign.delete(docBizManage);
		return getListPage();
	}


	@RequestMapping("/validateDocMustInput")
	@ResponseBody
	public Map<String, Object> validateDocMustInput(String relNo) throws Exception {
		ActionContext.initialize(request, response);

		Boolean isOk = true;
		if (StringUtil.isNotEmpty(relNo)) {
			isOk = docInterfaceFeign.validateDocMustInput(relNo);
		}

		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("isOk", isOk);

		return dataMap;
	}

}
