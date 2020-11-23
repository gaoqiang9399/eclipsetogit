package app.component.forewarning.controller;

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
import app.component.forewarning.entity.MfSysMsgInfo;
import app.component.forewarning.feign.MfSysMsgInfoFeign;
import app.util.toolkit.Ipage;

/**
 * Title: MfSysMsgInfoAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Wed Jul 05 09:14:26 CST 2017
 **/
@Controller
@RequestMapping("/mfSysMsgInfo")
public class MfSysMsgInfoController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入MfSysMsgInfoBo
	@Autowired
	private MfSysMsgInfoFeign mfSysMsgInfoFeign;
	// 全局变量

	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request, response);
		return "/component/forewarning/MfSysMsgInfo_List";
	}

	/***
	 * 列表数据查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType, String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfSysMsgInfo mfSysMsgInfo = new MfSysMsgInfo();
		try {
			mfSysMsgInfo.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfSysMsgInfo.setCriteriaList(mfSysMsgInfo, ajaxData);// 我的筛选
			// mfSysMsgInfo.setCustomSorts(ajaxData);//自定义排序
			// this.getRoleConditions(mfSysMsgInfo,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage = mfSysMsgInfoFeign.findByPage(ipage, mfSysMsgInfo);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
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
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formmsg0002 = formService.getFormData("msg0002");
			getFormValue(formmsg0002, getMapByJson(ajaxData));
			if (this.validateFormData(formmsg0002)) {
				MfSysMsgInfo mfSysMsgInfo = new MfSysMsgInfo();
				setObjValue(formmsg0002, mfSysMsgInfo);
				mfSysMsgInfoFeign.insert(mfSysMsgInfo);
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
	 * ajax 异步 单个字段或多个字段更新
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateAjaxByOne")
	@ResponseBody
	public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formmsg0002 = formService.getFormData("msg0002");
		getFormValue(formmsg0002, getMapByJson(ajaxData));
		MfSysMsgInfo mfSysMsgInfoJsp = new MfSysMsgInfo();
		setObjValue(formmsg0002, mfSysMsgInfoJsp);
		MfSysMsgInfo mfSysMsgInfo = mfSysMsgInfoFeign.getById(mfSysMsgInfoJsp);
		if (mfSysMsgInfo != null) {
			try {
				mfSysMsgInfo = (MfSysMsgInfo) EntityUtil.reflectionSetVal(mfSysMsgInfo, mfSysMsgInfoJsp, getMapByJson(ajaxData));
				mfSysMsgInfoFeign.update(mfSysMsgInfo);
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
	@RequestMapping(value = "/updateAjax")
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formmsg0002 = formService.getFormData("msg0002");
			getFormValue(formmsg0002, getMapByJson(ajaxData));
			if (this.validateFormData(formmsg0002)) {
				MfSysMsgInfo mfSysMsgInfo = new MfSysMsgInfo();
				setObjValue(formmsg0002, mfSysMsgInfo);
				mfSysMsgInfoFeign.update(mfSysMsgInfo);
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
	public Map<String, Object> getByIdAjax(String id) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formmsg0002 = formService.getFormData("msg0002");
		MfSysMsgInfo mfSysMsgInfo = new MfSysMsgInfo();
		mfSysMsgInfo.setId(id);
		mfSysMsgInfo = mfSysMsgInfoFeign.getById(mfSysMsgInfo);
		getObjValue(formmsg0002, mfSysMsgInfo, formData);
		if (mfSysMsgInfo != null) {
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
		MfSysMsgInfo mfSysMsgInfo = new MfSysMsgInfo();
		mfSysMsgInfo.setId(id);
		try {
			mfSysMsgInfoFeign.delete(mfSysMsgInfo);
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
		FormData formmsg0002 = formService.getFormData("msg0002");
		model.addAttribute("formmsg0002", formmsg0002);
		model.addAttribute("query", "");
		return "/component/forewarning/MfSysMsgInfo_Insert";
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
		FormData formmsg0002 = formService.getFormData("msg0002");
		getFormValue(formmsg0002);
		MfSysMsgInfo mfSysMsgInfo = new MfSysMsgInfo();
		setObjValue(formmsg0002, mfSysMsgInfo);
		mfSysMsgInfoFeign.insert(mfSysMsgInfo);
		getObjValue(formmsg0002, mfSysMsgInfo);
		this.addActionMessage(model, "保存成功");
		List<MfSysMsgInfo> mfSysMsgInfoList = (List<MfSysMsgInfo>) mfSysMsgInfoFeign.findByPage(this.getIpage(), mfSysMsgInfo).getResult();
		model.addAttribute("mfSysMsgInfoList", mfSysMsgInfoList);
		model.addAttribute("formmsg0002", formmsg0002);
		model.addAttribute("query", "");
		return "/component/forewarning/MfSysMsgInfo_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String id) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formmsg0001 = formService.getFormData("msg0001");
		getFormValue(formmsg0001);
		MfSysMsgInfo mfSysMsgInfo = new MfSysMsgInfo();
		mfSysMsgInfo.setId(id);
		mfSysMsgInfo = mfSysMsgInfoFeign.getById(mfSysMsgInfo);
		getObjValue(formmsg0001, mfSysMsgInfo);
		model.addAttribute("formmsg0001", formmsg0001);
		model.addAttribute("query", "");
		return "/component/forewarning/MfSysMsgInfo_Detail";
	}

	/**
	 * 删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model, String id) throws Exception {
		ActionContext.initialize(request, response);
		MfSysMsgInfo mfSysMsgInfo = new MfSysMsgInfo();
		mfSysMsgInfo.setId(id);
		mfSysMsgInfoFeign.delete(mfSysMsgInfo);
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
		FormData formmsg0002 = formService.getFormData("msg0002");
		getFormValue(formmsg0002);
		boolean validateFlag = this.validateFormData(formmsg0002);
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
		FormData formmsg0002 = formService.getFormData("msg0002");
		getFormValue(formmsg0002);
		boolean validateFlag = this.validateFormData(formmsg0002);
	}

	@RequestMapping(value = "/add")
	public void add(Model model, MfSysMsgInfo mfSysMsgInfo) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			mfSysMsgInfoFeign.insert(mfSysMsgInfo);
			dataMap.put("errorCode", "000000");
			dataMap.put("errorMessage", "success");
		} catch (Exception e) {
//			logger.error("插入消息异常", e);
			e.printStackTrace();
			dataMap.put("errorCode", "000011");
			dataMap.put("errorMessage", e.getMessage());
		}
		model.addAttribute("dataMap", dataMap);
	}

	/**
	 * 获取未读的消息总数
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/getUnReadMsgCount")
	public void getUnReadMsgCount(Model model, MfSysMsgInfo mfSysMsgInfo) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Long count = mfSysMsgInfoFeign.getUnReadMsgCount(mfSysMsgInfo);
			dataMap.put("data", count);
			dataMap.put("errorCode", "000000");
			dataMap.put("errorMessage", "success");
		} catch (Exception e) {
//			logger.error("获取未完成预警消息异常", e);
			e.printStackTrace();
			dataMap.put("errorCode", "000011");
			dataMap.put("errorMessage", e.getMessage());
		}
		model.addAttribute("dataMap", dataMap);
	}

	/**
	 * 将消息设为已读
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/setMsgRead")
	public void setMsgRead(Model model, MfSysMsgInfo mfSysMsgInfo) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			mfSysMsgInfoFeign.setMsgRead(mfSysMsgInfo);
			dataMap.put("errorCode", "000000");
			dataMap.put("errorMessage", "success");
		} catch (Exception e) {
//			logger.error("将消息设为已读异常", e);
			e.printStackTrace();
			dataMap.put("errorCode", "000011");
			dataMap.put("errorMessage", e.getMessage());
		}
		model.addAttribute("dataMap", dataMap);
	}


}
