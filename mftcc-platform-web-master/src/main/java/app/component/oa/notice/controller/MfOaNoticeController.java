package app.component.oa.notice.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.base.User;
import app.component.sys.entity.SysTaskInfo;
import app.component.sys.feign.SysTaskInfoFeign;
import org.apache.commons.lang3.StringEscapeUtils;
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
import app.component.oa.notice.entity.MfOaNotice;
import app.component.oa.notice.entity.MfOaNoticeLook;
import app.component.oa.notice.feign.MfOaNoticeFeign;
import app.component.sys.entity.SysOrg;
import app.component.sys.entity.SysUser;
import app.component.sys.feign.SysUserFeign;
import app.component.sysInterface.SysInterfaceFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.DateUtil;
import cn.mftcc.util.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Title: MfOaNoticeAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Fri Dec 09 16:16:55 CST 2016
 **/
@Controller
@RequestMapping(value = "/mfOaNotice")
public class MfOaNoticeController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfOaNoticeFeign mfOaNoticeFeign;
	@Autowired
	private SysUserFeign sysUserFeign;
	@Autowired
	private SysInterfaceFeign sysInterfaceFeign;
	@Autowired
	private SysTaskInfoFeign sysTaskInfoFeign;

	

	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getListPage")
	public String getListPage(Model model,String menuNo) throws Exception {
		ActionContext.initialize(request, response);
		MfOaNotice mfOaNotice = new MfOaNotice();
		if(StringUtil.isNotEmpty(menuNo) && menuNo.equals("80")){//综合入口下办公
			mfOaNotice.setNoticeType("发布产品");
		}else{//办公入口下
			mfOaNotice.setNoticeType("通知公告");
		}
		List<MfOaNotice> mfOaNoticeList = mfOaNoticeFeign.getAllList(mfOaNotice);
		List<MfOaNotice> mfOaNoticeListTo = new ArrayList<>();
		String opNo = User.getRegNo(request);
		for (MfOaNotice notice : mfOaNoticeList) {
			notice.setPublishTime(DateUtil.getShowDateTime(notice.getPublishTime()));
			//先判断消息发布人是否是操作员\\\\\\\\\\\\\\\\\\\
			if(!opNo.equals(notice.getOpNo()) && !notice.getNoticeScorp().equals("全体人员")){
				//再判断该操作员是否是接收人
				if(notice.getNoticeScorp().indexOf(opNo)==-1){
					continue;
				}
			}
			mfOaNoticeListTo.add(notice);
		}
		model.addAttribute("mfOaNoticeList", mfOaNoticeListTo);
		model.addAttribute("menuNo", menuNo);
		return "/component/oa/notice/MfOaNotice_List";
	}

	/***
	 * 列表数据查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping("/findByPageAjax")
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
			String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			MfOaNotice mfOaNotice = new MfOaNotice();
			mfOaNotice.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfOaNotice.setCriteriaList(mfOaNotice, ajaxData);// 我的筛选
			this.getRoleConditions(mfOaNotice, "1000000066");// 记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage.setParams(this.setIpageParams("mfOaNotice", mfOaNotice));
			ipage = mfOaNoticeFeign.findByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
			/**
			 * ipage.setResult(tableHtml); dataMap.put("ipage",ipage); 需要改进的方法
			 * dataMap.put("tableData",tableHtml);
			 */
		} catch (Exception e) {
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
	@RequestMapping("/insertAjax")
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formoanotice0002 = new FormService().getFormData("oanotice0002");
			ajaxData = java.net.URLDecoder.decode(ajaxData,"UTF-8");
			getFormValue(formoanotice0002, getMapByJson(ajaxData));
			if (this.validateFormData(formoanotice0002)) {
				MfOaNotice mfOaNotice = new MfOaNotice();
				setObjValue(formoanotice0002, mfOaNotice);
				mfOaNoticeFeign.insert(mfOaNotice);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
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
	@ResponseBody
	@RequestMapping("/updateAjaxByOne")
	public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formoanotice0002 = new FormService().getFormData("oanotice0002");
		getFormValue(formoanotice0002, getMapByJson(ajaxData));
		MfOaNotice mfOaNoticeJsp = new MfOaNotice();
		setObjValue(formoanotice0002, mfOaNoticeJsp);
		MfOaNotice mfOaNotice = mfOaNoticeFeign.getById(mfOaNoticeJsp);
		if (mfOaNotice != null) {
			try {
				mfOaNotice = (MfOaNotice) EntityUtil.reflectionSetVal(mfOaNotice, mfOaNoticeJsp,
						getMapByJson(ajaxData));
				mfOaNoticeFeign.update(mfOaNotice);
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
	@ResponseBody
	@RequestMapping("/updateAjax")
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			MfOaNotice mfOaNotice = new MfOaNotice();
			FormData formoanotice0003 = new FormService().getFormData("oanotice0003");
			getFormValue(formoanotice0003, getMapByJson(ajaxData));
			if (this.validateFormData(formoanotice0003)) {
				mfOaNotice = new MfOaNotice();
				setObjValue(formoanotice0003, mfOaNotice);
				mfOaNoticeFeign.update(mfOaNotice);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_UPDATE.getMessage());
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
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
	@RequestMapping("/getByIdAjax")
	public Map<String, Object> getByIdAjax(String noticeId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formoanotice0002 = new FormService().getFormData("oanotice0002");
		MfOaNotice mfOaNotice = new MfOaNotice();
		mfOaNotice.setNoticeId(noticeId);
		mfOaNotice = mfOaNoticeFeign.getById(mfOaNotice);
		mfOaNotice.setNoticeContent(StringEscapeUtils.unescapeHtml4(mfOaNotice.getNoticeContent()));
		getObjValue(formoanotice0002, mfOaNotice, formData);
		dataMap.put("flag", "success");
		// dataMap.put("mfOaNotice", "mfOaNotice");
		dataMap.put("formData", formData);
		return dataMap;
	}

	/**
	 * 发布公告
	 * 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/publishNoticeAjax")
	public Map<String, Object> publishNoticeAjax(String noticeId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfOaNotice mfOaNotice = new MfOaNotice();
		mfOaNotice.setNoticeId(noticeId);
		mfOaNotice = mfOaNoticeFeign.getById(mfOaNotice);
		mfOaNoticeFeign.insert(mfOaNotice);
		dataMap.put("flag", "success");
		dataMap.put("msg", MessageEnum.SUCCEED_OPERATION.getMessage("发布"));
		return dataMap;
	}

	/**
	 * Ajax异步删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/deleteAjax")
	public Map<String, Object> deleteAjax(String noticeId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			MfOaNotice mfOaNotice = new MfOaNotice();
			mfOaNotice.setNoticeId(noticeId);
			mfOaNoticeFeign.delete(mfOaNotice);
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED.getMessage());
		} catch (Exception e) {
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
	@RequestMapping("/input")
	public String input(Model model,String menuNo) throws Exception {
		ActionContext.initialize(request, response);
		MfOaNotice mfOaNotice = new MfOaNotice();
		mfOaNotice.setNoticeScorp("全体人员");
		mfOaNotice.setNoticeScorpName("全体人员");
		mfOaNotice.setIsTop("1");
		if(StringUtil.isNotEmpty(menuNo) && menuNo.equals("80")){//综合入口下办公
			mfOaNotice.setNoticeType("发布产品");
		}else{
			mfOaNotice.setNoticeType("通知公告");
		}
		FormData formoanotice0002 = new FormService().getFormData("oanotice0002");
		getObjValue(formoanotice0002, mfOaNotice);
		model.addAttribute("formoanotice0002", formoanotice0002);
		model.addAttribute("mfOaNotice", mfOaNotice);
		model.addAttribute("query", "");
		return "/component/oa/notice/MfOaNotice_Insert";
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
		FormData formoanotice0002 = new FormService().getFormData("oanotice0002");
		getFormValue(formoanotice0002);
		MfOaNotice mfOaNotice = new MfOaNotice();
		setObjValue(formoanotice0002, mfOaNotice);
		mfOaNoticeFeign.insert(mfOaNotice);
		getObjValue(formoanotice0002, mfOaNotice);
		this.addActionMessage(model, "保存成功");
		Ipage ipage = this.getIpage();
		ipage.setParams(this.setIpageParams("mfOaNotice", mfOaNotice));
		List<MfOaNotice> mfOaNoticeList = (List<MfOaNotice>) mfOaNoticeFeign.findByPage(ipage).getResult();
		model.addAttribute("formoanotice0002", formoanotice0002);
		model.addAttribute("mfOaNotice", mfOaNotice);
		model.addAttribute("mfOaNoticeList", mfOaNoticeList);
		model.addAttribute("query", "");
		return "/component/oa/notice/MfOaNotice_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getById")
	public String getById(String noticeId, Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormData formoanotice0003 = new FormService().getFormData("oanotice0003");
		getFormValue(formoanotice0003);
		MfOaNotice mfOaNotice = new MfOaNotice();
		mfOaNotice.setNoticeId(noticeId);
		mfOaNotice = mfOaNoticeFeign.getById(mfOaNotice);
		getObjValue(formoanotice0003, mfOaNotice);
		SysTaskInfo sysTaskInfo = new SysTaskInfo();
		sysTaskInfo.setBizPkNo(mfOaNotice.getNoticeId());
		sysTaskInfo.setUserNo(User.getRegNo(request));
		List<SysTaskInfo> sysList = sysTaskInfoFeign.findByFilter(sysTaskInfo);
		if(sysList!=null && sysList.size()>0){
			sysTaskInfo = sysList.get(0);
			sysTaskInfo.setPasSts("1");
			sysTaskInfo.setLookTime(DateUtil.getDate());
			sysTaskInfoFeign.update(sysTaskInfo);
		}
		model.addAttribute("formoanotice0003", formoanotice0003);
		model.addAttribute("mfOaNotice", mfOaNotice);
		model.addAttribute("noticeId", noticeId);
		model.addAttribute("query", "");
		return "/component/oa/notice/MfOaNotice_Detail";
	}

	/**
	 * 查询修改
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getById1")
	public String getById1(String noticeId, Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormData formoanotice0003 = new FormService().getFormData("oanotice0003");
		getFormValue(formoanotice0003);
		MfOaNotice mfOaNotice = new MfOaNotice();
		mfOaNotice.setNoticeId(noticeId);
		mfOaNotice = mfOaNoticeFeign.getById(mfOaNotice);
		getObjValue(formoanotice0003, mfOaNotice);
		model.addAttribute("formoanotice0003", formoanotice0003);
		model.addAttribute("mfOaNotice", mfOaNotice);
		model.addAttribute("noticeId", noticeId);
		model.addAttribute("query", "");
		return "/component/oa/notice/MfOaNotice_Detail1";
	}


	/**
	 * 新增校验
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	@RequestMapping("/validateInsert")
	public void validateInsert() throws Exception {
		ActionContext.initialize(request, response);
		FormData formoanotice0002 = new FormService().getFormData("oanotice0002");
		getFormValue(formoanotice0002);
		boolean validateFlag = this.validateFormData(formoanotice0002);
	}

	/**
	 * 修改校验
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	@RequestMapping("/validateUpdate")
	public void validateUpdate() throws Exception {
		ActionContext.initialize(request, response);
		FormData formoanotice0002 = new FormService().getFormData("oanotice0002");
		getFormValue(formoanotice0002);
		boolean validateFlag = this.validateFormData(formoanotice0002);
	}

	/**
	 * 方法描述： 通知公告的生效和终止方法
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author lcl
	 * @date 2016-12-14 下午4:56:37
	 */
	@ResponseBody
	@RequestMapping("/updateNoticeStsAjax")
	public Map<String, Object> updateNoticeStsAjax(String ajaxData, String tableId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			MfOaNotice mfOaNotice = new MfOaNotice();
			JSONObject jobj = JSONObject.fromObject(ajaxData);
			FormData formoanotice0002 = new FormService().getFormData("oanotice0002");
			getFormValue(formoanotice0002, jobj);
			setObjValue(formoanotice0002, mfOaNotice);
			mfOaNoticeFeign.updateNoticeSts(mfOaNotice);
			// mfOaNoticeFeign.update(mfOaNotice);
			mfOaNotice = mfOaNoticeFeign.getById(mfOaNotice);
			List<MfOaNotice> list = new ArrayList<MfOaNotice>();
			list.add(mfOaNotice);
			String tableHtml = getTableDataList(list, tableId);
			dataMap.put("tableHtml", tableHtml);

			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED_UPDATE.getMessage());
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_UPDATE.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * 方法描述： 改变置顶状态后，重新加载操作的公告
	 * 
	 * @param list
	 *            通知公告
	 * @throws Exception
	 *             void
	 * @author lcl
	 * @date 2016-12-14 下午4:57:26
	 */
	@SuppressWarnings("unused")
	private String getTableDataList(List<MfOaNotice> list, String tableId) throws Exception {
		JsonTableUtil jtu = new JsonTableUtil();
		Ipage ipage = this.getIpage();
		String tableHtml = jtu.getJsonStr(tableId, "tableTag", list, null, true);
		return tableHtml;
	}

	/**
	 * 方法描述： 更新通知公告的置顶状态
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author lcl
	 * @date 2016-12-14 下午4:55:23
	 */
	@ResponseBody
	@RequestMapping("/updateIsTopAjax")
	public Map<String, Object> updateIsTopAjax(String ajaxData, String tableId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			MfOaNotice mfOaNotice = new MfOaNotice();
			JSONObject jobj = JSONObject.fromObject(ajaxData);
			FormData formoanotice0002 = new FormService().getFormData("oanotice0002");
			getFormValue(formoanotice0002, jobj);
			setObjValue(formoanotice0002, mfOaNotice);
			mfOaNoticeFeign.updateisTop(mfOaNotice);
			mfOaNotice = mfOaNoticeFeign.getById(mfOaNotice);
			List<MfOaNotice> list = new ArrayList<MfOaNotice>();
			list.add(mfOaNotice);
			String tableHtml = getTableDataList(list, tableId);
			// getListPage();
			dataMap.put("tableHtml", tableHtml);
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

	/**
	 * 方法描述： 获得发布范围
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 谢静霞
	 * @date 2016-12-27 下午2:31:48
	 */
	@RequestMapping("/getSysUserListPage")
	public String getSysUserListPage(Model model,String opNoType) throws Exception {
		ActionContext.initialize(request, response);
		String checkedNode = ActionContext.getActionContext().getRequest().getParameter("checkedNode");
		String[] arr = null;
		if (StringUtil.isNotEmpty(checkedNode)) {
			arr = checkedNode.split(",");
		}
		SysOrg sysOrg = new SysOrg();
		sysOrg.setOpNoType(opNoType);
		JSONArray orgArray = JSONArray.fromObject(sysInterfaceFeign.getAllOrgJson(sysOrg));
		SysUser sysUser = new SysUser();
		sysUser.setOpSts("1");
		sysUser.setOpNoType(opNoType);
		JSONArray userArray = JSONArray.fromObject(sysUserFeign.getAllUserList(sysUser));
		for (int i = 0; i < orgArray.size(); i++) {
			orgArray.getJSONObject(i).put("id", orgArray.getJSONObject(i).getString("brNo"));
			orgArray.getJSONObject(i).put("name", orgArray.getJSONObject(i).getString("brName"));
			orgArray.getJSONObject(i).put("pId", "0");
			orgArray.getJSONObject(i).put("open", false);
			orgArray.getJSONObject(i).put("isParent", true);
		}
		for (int i = 0; i < userArray.size(); i++) {
			userArray.getJSONObject(i).put("id", userArray.getJSONObject(i).getString("opNo"));
			userArray.getJSONObject(i).put("name", userArray.getJSONObject(i).getString("opName"));
			userArray.getJSONObject(i).put("pId", userArray.getJSONObject(i).getString("brNo"));
			if (arr != null && arr.length > 0) {// 详情页面需要展示已选的默认选中
				for (int k = 0; k < arr.length; k++) {
					if (userArray.getJSONObject(i).getString("opNo").equals(arr[k])) {
						userArray.getJSONObject(i).put("checked", true);
					}
				}
			}
			orgArray.add(userArray.getJSONObject(i));
		}
		model.addAttribute("ajaxData", orgArray.toString());
		model.addAttribute("query", "");
		return "/component/oa/notice/MfOaNotice_Scorp";
	}

	@RequestMapping("/getNoticeLooking")
	public String getNoticeLooking(Model model,String noticeId) throws Exception {
		model.addAttribute("noticeId", noticeId);
		return "/component/oa/notice/MfOaNoticeSituation_list";
	}

	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping("/findNoticeLookingByPageAjax")
	public Map<String, Object> findNoticeLookingByPageAjax(Integer pageNo, Integer pageSize, String tableId,
			String tableType, String ajaxData, String noticeId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfOaNoticeLook mfOaNoticeLook = new MfOaNoticeLook();
		try {
			mfOaNoticeLook.setNoticeId(noticeId);
			mfOaNoticeLook.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfOaNoticeLook.setCustomSorts(ajaxData);
			mfOaNoticeLook.setCriteriaList(mfOaNoticeLook, ajaxData);// 我的筛选
			// this.getRoleConditions(mfOaNotice,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setPageSize(pageSize);// 异步传页面翻页参数
			// 自定义查询Bo方法
			// List<SysTaskInfo> SysTaskInfolist=
			// SysTaskInfoInterfaceFeignImpl.getAllForWechat();
			ipage.setParams(this.setIpageParams("mfOaNoticeLook", mfOaNoticeLook));
			ipage = mfOaNoticeFeign.getLooking(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
			/**
			 * ipage.setResult(tableHtml); dataMap.put("ipage",ipage); 需要改进的方法
			 * dataMap.put("tableData",tableHtml);
			 */
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

}
