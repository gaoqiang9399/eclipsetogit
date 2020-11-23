package app.component.nmd.censor.controller;

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
import com.google.gson.Gson;

import app.component.common.EntityUtil;
import app.component.nmd.censor.entity.MfBusCensorBase;
import app.component.nmd.censor.entity.MfBusCensorFile;
import app.component.nmd.censor.entity.MfBusCensorType;
import app.component.nmd.censor.feign.MfBusCensorFileFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;

/**
 * Title: MfBusCensorFileAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Sat Jul 15 14:47:34 CST 2017
 **/
@Controller
@RequestMapping("/mfBusCensorFile")
public class MfBusCensorFileController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入MfBusCensorFileBo
	@Autowired
	private MfBusCensorFileFeign mfBusCensorFileFeign;

	@ResponseBody
	@RequestMapping(value = "/updateAjax")
	public Map<String, Object> updateAjax(String ajaxData, String baseList) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formcensorfileinsert = formService.getFormData("censorfileinsert");
			getFormValue(formcensorfileinsert, getMapByJson(ajaxData));
			if (this.validateFormData(formcensorfileinsert)) {
				// 获取文件信息
				MfBusCensorFile mfBusCensorFile = new MfBusCensorFile();
				setObjValue(formcensorfileinsert, mfBusCensorFile);
				// 去掉文件名和文件编号的重复的操作
				Boolean isHaveSime = mfBusCensorFileFeign.getIsSime(mfBusCensorFile);
				// 获取审查项信息
				if ((!"".equals(baseList)) || baseList != null) {
					String[] fromJson = new Gson().fromJson(baseList, String[].class);
					if (fromJson.length == 0) {
						dataMap.put("flag", "error");
						dataMap.put("msg", MessageEnum.NOT_FORM_EMPTY.getMessage("审查项"));
					} else {
						mfBusCensorFileFeign.update(mfBusCensorFile, fromJson);
						dataMap.put("flag", "success");
						dataMap.put("msg", MessageEnum.SUCCEED_UPDATE.getMessage());
					}
				} else {
					dataMap.put("flag", "error");
					dataMap.put("msg", MessageEnum.NOT_FORM_EMPTY.getMessage("审查项"));
				}
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
	 * 获取该项id下所有的审查项
	 */
	@ResponseBody
	@RequestMapping(value = "/findItemByIdAjax")
	public Map<String, Object> findItemByIdAjax(String censorFileNo) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfBusCensorFile mfBusCensorFile = new MfBusCensorFile();
		mfBusCensorFile.setCensorFileNo(censorFileNo);
		List<MfBusCensorBase> listFile = mfBusCensorFileFeign.findCensorBaseListByCensorFileNo(mfBusCensorFile);
		Gson gson = new Gson();
		String jsonList = gson.toJson(listFile);
		dataMap.put("theList", jsonList.toString());
		dataMap.put("flag", "success");
		return dataMap;
	}

	/**
	 * 获取审查数据
	 * 
	 * @param tableId
	 * @param tableType
	 * @param pageSize
	 * @param pageNo
	 */
	@ResponseBody
	@RequestMapping(value = "/findByPageBaseTypeAjax")
	public Map<String, Object> findByPageBaseTypeAjax(String ajaxData, String tableId, String tableType,
			Integer pageSize, Integer pageNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfBusCensorType mfBusCensorType = new MfBusCensorType();
		try {
			mfBusCensorType.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfBusCensorType.setCriteriaList(mfBusCensorType, ajaxData);// 我的筛选
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage.setParams(this.setIpageParams("mfBusCensorType",mfBusCensorType));
			ipage = mfBusCensorFileFeign.findByPageBaseType(ipage/*, mfBusCensorType*/);
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
			throw new Exception(e.getMessage());
		}
		return dataMap;
	}

	/**
	 * 进入审查分类的选择界面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getCensorTypeSelectList")
	public String getCensorTypeSelectList(Model model, String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		model.addAttribute("query", "");
		return "component/nmd/censor/MfBusCensorBaseType_List";
	}

	/**
	 * <action name="MfBusCensorFileAction_getListBasePage" method="getListBasePage"
	 * class="app.component.sys.sysextend.action.MfBusCensorFileAction">
	 * <result name=
	 * "MfBusCensorBase_Insert">/component/sys/sysextend/MfBusCensorBase_List.jsp</result>
	 * </action>
	 * 
	 */

	/**
	 * 
	 * @return
	 * @throws Exception
	 * 
	 * @RequestMapping(value = "/findAllBaseType") public String
	 *                       findAllBaseType(Model model, String ajaxData) throws
	 *                       Exception{ FormService formService = new FormService();
	 *                       ActionContext.initialize(request, response);
	 *                       Map<String, Object> dataMap = new HashMap<String,
	 *                       Object>(); List<MfBusCensorType> typeList =
	 *                       mfBusCensorFileFeign.findAllType(); return null; }
	 */

	/**
	 * 
	 */
	@RequestMapping(value = "/insertBaseAjax")
	@ResponseBody
	public Map<String, Object> insertBaseAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formcensorbase0001 = formService.getFormData("censorbase0001");
			getFormValue(formcensorbase0001, getMapByJson(ajaxData));
			if (this.validateFormData(formcensorbase0001)) {
				MfBusCensorBase bbiz = new MfBusCensorBase();
				setObjValue(formcensorbase0001, bbiz);
				if (mfBusCensorFileFeign.isInstanceTypeByName(bbiz)) {
					dataMap.put("flag", "error");
					// EXIST_INFORMATION_EVAL("${content}已存在，请重新输入！", AlertType.ERROR),
					dataMap.put("msg", MessageEnum.EXIST_INFORMATION_EVAL.getMessage(bbiz.getItemName()));
				} else {
					mfBusCensorFileFeign.insertBaseType(bbiz);
					dataMap.put("flag", "success");
					dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
				}
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
	 * @param baseList
	 * 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/insertAjax")
	public Map<String, Object> insertAjax(String ajaxData, String baseList) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formcensorfileinsert = formService.getFormData("censorfileinsert");
			getFormValue(formcensorfileinsert, getMapByJson(ajaxData));
			if (this.validateFormData(formcensorfileinsert)) {
				// 获取文件信息
				MfBusCensorFile mfBusCensorFile = new MfBusCensorFile();
				setObjValue(formcensorfileinsert, mfBusCensorFile);
				// 去掉文件名和文件编号的重复的操作
				Boolean isHaveSime = mfBusCensorFileFeign.getIsSime(mfBusCensorFile);
				if (isHaveSime) {
					dataMap.put("flag", "error");
					Map<String, String> thisHash = new HashMap<String, String>();
					thisHash.put("content", "审查文件");
					thisHash.put("reason", "文件名已经存在!");
					dataMap.put("msg", MessageEnum.FAILED_SAVE_CONTENT.getMessage(thisHash));
				} else {
					// 获取审查项信息
					if ((!"".equals(baseList)) || baseList != null) {
						String[] fromJson = new Gson().fromJson(baseList, String[].class);
						if (fromJson.length == 0) {
							dataMap.put("flag", "error");
							dataMap.put("msg", MessageEnum.NOT_FORM_EMPTY.getMessage("审查项"));
						} else {
							mfBusCensorFileFeign.insert(mfBusCensorFile, fromJson);
							dataMap.put("flag", "success");
							dataMap.put("msg", "新增成功");
						}
					} else {
						dataMap.put("flag", "error");
						dataMap.put("msg", MessageEnum.NOT_FORM_EMPTY.getMessage("审查项"));
					}
				}
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

	/***
	 * 项目列表查询数据
	 * 
	 * @param tableId
	 * @param tableType
	 * @param pageSize
	 * @param pageNo
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/findByPageBaseAjax")
	public Map<String, Object> findByPageBaseAjax(String tableId, String tableType, Integer pageSize, Integer pageNo)
			throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		// MfBusCensorFile ////mfBusCensorFile = new MfBusCensorFile();
		try {
			//// mfBusCensorFile.setCustomQuery(ajaxData);//自定义查询参数赋值
			//// mfBusCensorFile.setCriteriaList(mfBusCensorFile, ajaxData);//我的筛选
			// this.getRoleConditions(mfBusCensorFile,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage.setParams(this.setIpageParams("mfBusCensorBase", new MfBusCensorBase()));
			ipage = mfBusCensorFileFeign.findByPageBase(ipage);
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
			throw new Exception(e.getMessage());
		}
		return dataMap;
	}

	/**
	 * 文件审查项列表
	 */
	@RequestMapping(value = "/getFileBaseListPage")
	public String getFileBaseListPage(Model model) throws Exception {
		ActionContext.initialize(request, response);
		model.addAttribute("query", "");
		return "component/nmd/censor/MfBusCensorFileBase_List";
	}

	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request, response);
		model.addAttribute("query", "");
		return "component/nmd/censor/MfBusCensorFile_List";
	}

	/***
	 * 列表数据查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/findByPageAjax")
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
			String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfBusCensorFile mfBusCensorFile = new MfBusCensorFile();
		try {
			mfBusCensorFile.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfBusCensorFile.setCriteriaList(mfBusCensorFile, ajaxData);// 我的筛选
			// this.getRoleConditions(mfBusCensorFile,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage.setParams(this.setIpageParams("mfBusCensorFile",mfBusCensorFile));
			ipage = mfBusCensorFileFeign.findByPage(ipage/*, mfBusCensorFile*/);
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
	@ResponseBody
	@RequestMapping(value = "/updateAjaxByOne")
	public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formsysextend0002 = formService.getFormData("sysextend0002");
		getFormValue(formsysextend0002, getMapByJson(ajaxData));
		MfBusCensorFile mfBusCensorFileJsp = new MfBusCensorFile();
		setObjValue(formsysextend0002, mfBusCensorFileJsp);
		MfBusCensorFile mfBusCensorFile = mfBusCensorFileFeign.getById(mfBusCensorFileJsp);
		if (mfBusCensorFile != null) {
			try {
				mfBusCensorFile = (MfBusCensorFile) EntityUtil.reflectionSetVal(mfBusCensorFile, mfBusCensorFileJsp,
						getMapByJson(ajaxData));
				mfBusCensorFileFeign.update(mfBusCensorFile);
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

	/**
	 * AJAX获取查看
	 * @param id 
	 * 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/getByIdAjax")
	public Map<String, Object> getByIdAjax( String id) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formsysextend0002 = formService.getFormData("sysextend0002");
		MfBusCensorFile mfBusCensorFile = new MfBusCensorFile();
		mfBusCensorFile.setId(id);
		mfBusCensorFile = mfBusCensorFileFeign.getById(mfBusCensorFile);
		getObjValue(formsysextend0002, mfBusCensorFile, formData);
		if (mfBusCensorFile != null) {
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
	public Map<String, Object> deleteAjax(String id) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfBusCensorFile mfBusCensorFile = new MfBusCensorFile();
		mfBusCensorFile.setId(id);
		try {
			mfBusCensorFileFeign.delete(mfBusCensorFile);
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
		FormData formcensorfileinsert = formService.getFormData("censorfileinsert");
		model.addAttribute("formcensorfileinsert", formcensorfileinsert);
		model.addAttribute("query", "");
		return "component/nmd/censor/MfBusCensorFile_Insert";
	}

	/**
	 * <action name="MfBusCensorFileBaseAction_baseinput" method="baseinput" class=
	 * "app.component.sys.sysextend.action.MfBusCensorFileAction"> <result name=
	 * "MfBusCensorBase_Insert">/component/sys/sysextend/MfBusCensorBase_Insert.jsp</result>
	 * </action>
	 */

	@RequestMapping(value = "/baseinput")
	public String baseinput(Model model, String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formcensorbase0001 = formService.getFormData("censorbase0001");
		model.addAttribute("formcensorbase0001", formcensorbase0001);
		model.addAttribute("query", "");
		return "component/nmd/censor/MfBusCensorBase_Insert";
	}

	/***
	 * 新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insert")
	public String insert(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formsysextend0002 = formService.getFormData("sysextend0002");
		getFormValue(formsysextend0002);
		MfBusCensorFile mfBusCensorFile = new MfBusCensorFile();
		setObjValue(formsysextend0002, mfBusCensorFile);
		mfBusCensorFileFeign.insert(mfBusCensorFile);
		getObjValue(formsysextend0002, mfBusCensorFile);
		this.addActionMessage(model, "保存成功");
		this.getIpage().setParams(this.setIpageParams("mfBusCensorFile",mfBusCensorFile));
		List<MfBusCensorFile> mfBusCensorFileList = (List<MfBusCensorFile>) mfBusCensorFileFeign.findByPage(this.getIpage()/*, mfBusCensorFile*/)
				.getResult();
		model.addAttribute("formsysextend0002", formsysextend0002);
		model.addAttribute("mfBusCensorFileList", mfBusCensorFileList);
		model.addAttribute("mfBusCensorFile", mfBusCensorFile);
		model.addAttribute("query", "");
		return "component/nmd/censor/MfBusCensorFile_Insert";
	}

	/**
	 * 查询
	 * @param censorFileNo 
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String censorFileNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formcensorfileinsert = formService.getFormData("censorfileinsert");
		getFormValue(formcensorfileinsert);
		MfBusCensorFile mfBusCensorFile = new MfBusCensorFile();
		mfBusCensorFile.setCensorFileNo(censorFileNo);
		mfBusCensorFile = mfBusCensorFileFeign.getCensorFileNo(mfBusCensorFile);
		getObjValue(formcensorfileinsert, mfBusCensorFile);
		model.addAttribute("formcensorfileinsert", formcensorfileinsert);
		model.addAttribute("mfBusCensorFile", mfBusCensorFile);
		model.addAttribute("query", "");
		return "component/nmd/censor/MfBusCensorFile_Detail";
	}

	/**
	 * 删除
	 * @param id 
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(String id,Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		MfBusCensorFile mfBusCensorFile = new MfBusCensorFile();
		mfBusCensorFile.setId(id);
		mfBusCensorFileFeign.delete(mfBusCensorFile);
		return getListPage(model);
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
		FormData formsysextend0002 = formService.getFormData("sysextend0002");
		getFormValue(formsysextend0002);
		boolean validateFlag = this.validateFormData(formsysextend0002);
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
		FormData formsysextend0002 = formService.getFormData("sysextend0002");
		getFormValue(formsysextend0002);
		boolean validateFlag = this.validateFormData(formsysextend0002);
	}

}
