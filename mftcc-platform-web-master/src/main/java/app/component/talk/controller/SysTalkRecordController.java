package app.component.talk.controller;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;

import app.base.User;
import app.component.common.BizPubParm;
import cn.mftcc.util.DateUtil;
import app.component.sys.entity.SysUser;
import app.component.sys.feign.SysUserFeign;
import app.component.talk.entity.SysTalkRecord;
import app.component.talk.feign.SysTalkRecordFeign;
import app.util.toolkit.Ipage;

/**
 * Title: SysTalkRecordAction.java Description:
 * 
 * @author:@dhcc.com.cn
 * @Mon Jan 14 07:02:01 GMT 2013
 **/
@Controller
@RequestMapping(value = "/sysTalkRecord")
public class SysTalkRecordController extends BaseFormBean {

	// 页面传值
	private SysTalkRecord sysTalkRecord;
	private List<SysTalkRecord> sysTalkRecordList;

	// 注入SysTalkRecordBo
	@Autowired
	private SysTalkRecordFeign sysTalkRecordFeign;
	@Autowired
	private SysUserFeign sysUserFeign;

	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;

	/*
	 * private String query; private String targetUserId; private FormData
	 * formtalk0001; private FormData formtalk0002; private FormData
	 * formtalk0003; private FormService formService = new FormService();
	 * 
	 * public SysTalkRecordController() { query = ""; }
	 */
	/**
	 * 分页查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPage")
	public String findByPage(Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formtalk0001 = formService.getFormData("talk0001");
		sysTalkRecord = new SysTalkRecord();
		getFormValue(formtalk0001);
		setObjValue(formtalk0001, sysTalkRecord);
		if (sysTalkRecord.getSendTime() != null && !"".equals(sysTalkRecord.getSendTime())) {
			sysTalkRecord.setSendTime(DateUtil.getStr(sysTalkRecord.getSendTime()));
		}
		Ipage ipage = this.getIpage();
		sysTalkRecordList = (List) sysTalkRecordFeign.findByPage(ipage, sysTalkRecord).getResult();
		model.addAttribute("formtalk0001", formtalk0001);
		model.addAttribute("query", "");
		return "/component/talk/SysTalkRecord_List";
	}

	/**
	 * 分页查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findMyMsg")
	public String findMyMsg(Model model) throws Exception {
		ActionContext.initialize(request, response);
		String tlrno = User.getRegNo(getHttpRequest());
		String target = getHttpRequest().getParameter("target");
		FormService formService = new FormService();
		FormData formtalk0002 = formService.getFormData("talk0002");
		sysTalkRecord = new SysTalkRecord();
		getFormValue(formtalk0002);
		setObjValue(formtalk0002, sysTalkRecord);
		if (target != null && !"".equals(target)) {
			String name = target.substring(0, target.indexOf("("));
			String id = target.substring(target.indexOf("(") + 1, target.indexOf(")"));
			sysTalkRecord.setSoruceUserId(id);
			sysTalkRecord.setSoruceUserName(name);
		}
		if ((sysTalkRecord.getSoruceUserId() == null || "".equals(sysTalkRecord.getSoruceUserId()))
				&& (sysTalkRecord.getSoruceUserName() == null || "".equals(sysTalkRecord.getSoruceUserName()))) {
			sysTalkRecord.setSoruceUserId(tlrno);
		} else {
			sysTalkRecord.setTargetUserId(tlrno);
		}
		if (sysTalkRecord.getSendTime() != null && !"".equals(sysTalkRecord.getSendTime())) {
			sysTalkRecord.setSendTime(DateUtil.getStr(sysTalkRecord.getSendTime()));
		}
		Ipage ipage = this.getIpage();
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("sysTalkRecord", sysTalkRecord);
		ipage.setParams(paramsMap);
		sysTalkRecordList = (List) sysTalkRecordFeign.findMyMsg(ipage).getResult();
		model.addAttribute("formtalk0002", formtalk0002);
		model.addAttribute("sysTalkRecordList", sysTalkRecordList);
		model.addAttribute("query", "");
		return "/component/talk/SysTalkRecord_MyList";
	}

	/**
	 * 分页查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findHisMsg")
	public String findHisMsg(Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		String tlrno = User.getRegNo(getHttpRequest());
		String target = getHttpRequest().getParameter("target");
		getHttpRequest().setAttribute("target", target);
		FormData formtalk0003 = formService.getFormData("talk0003");
		sysTalkRecord = new SysTalkRecord();
		getFormValue(formtalk0003);
		setObjValue(formtalk0003, sysTalkRecord);
		if (target != null && !"".equals(target) && target.indexOf("(") >= 0) {
			String name = target.substring(0, target.indexOf("("));
			String id = target.substring(target.indexOf("(") + 1, target.indexOf(")"));
			sysTalkRecord.setSoruceUserId(id);
			sysTalkRecord.setSoruceUserName(name);
		}
		if ((sysTalkRecord.getSoruceUserId() == null || "".equals(sysTalkRecord.getSoruceUserId()))
				&& (sysTalkRecord.getSoruceUserName() == null || "".equals(sysTalkRecord.getSoruceUserName()))) {
			sysTalkRecord.setSoruceUserId(tlrno);
		} else {
			sysTalkRecord.setTargetUserId(tlrno);
		}
		if (sysTalkRecord.getSendTime() != null && !"".equals(sysTalkRecord.getSendTime())) {
			sysTalkRecord.setSendTime(DateUtil.getStr(sysTalkRecord.getSendTime()));
		}
		getObjValue(formtalk0003, sysTalkRecord);
		Ipage ipage = this.getIpage();
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("sysTalkRecord", sysTalkRecord);
		ipage.setParams(paramsMap);
		sysTalkRecordList = (List) sysTalkRecordFeign.findMyMsg(ipage).getResult();
		model.addAttribute("formtalk0003", formtalk0003);
		model.addAttribute("ipage", ipage);
		model.addAttribute("query", "");
		return "/component/talk/SysTalkRecord_HisList";
	}

	@RequestMapping(value = "/login")
	public String login(Model model) throws Exception {
		return "/component/talk/SysTalkRecord_Login";
	}

	/**
	 * 获取新增页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/input")
	public String input(Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formtalk0002 = formService.getFormData("talk0002");
		model.addAttribute("formtalk0002", formtalk0002);
		model.addAttribute("query", "");
		return "/creditapp/talk/SysTalkRecord_Insert";
	}

	/**
	 * 新增保存操作
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "insert")
	public String insert(Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formtalk0002 = formService.getFormData("talk0002");
		getFormValue(formtalk0002);
		sysTalkRecord = new SysTalkRecord();
		setObjValue(formtalk0002, sysTalkRecord);
		sysTalkRecordFeign.insert(sysTalkRecord);
		getObjValue(formtalk0002, sysTalkRecord);
		model.addAttribute("formtalk0002", formtalk0002);
		model.addAttribute("query", "");
		return "/creditapp/talk/SysTalkRecord_Detail";
	}

	/**
	 * ajax 保存信息
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "insertmsg")
	public String insertmsg(Model model) throws Exception {
		HttpServletRequest request = getHttpRequest();
		request.setCharacterEncoding("GBK");
		String content = request.getParameter("content");
		String targetIds = request.getParameter("targetIds");
		if (targetIds != null && !"".equals(targetIds)) {
			String[] ids = targetIds.split(",");
			for (int i = 0; i < ids.length; i++) {
				String id = ids[i];
				int index = id.indexOf(":");
				if (id != null && !"".equals(id) && !"".equals(content) && index > 0) {
					sysTalkRecord = new SysTalkRecord();
					sysTalkRecord.setSoruceUserId(User.getRegNo(request));
					sysTalkRecord.setSoruceUserName(User.getRegName(request));
					sysTalkRecord.setTargetUserId(id.substring(0, index));
					sysTalkRecord.setTargetUserName(id.substring(index + 1));
					sysTalkRecord.setSendTime(cn.mftcc.util.DateUtil.getTime());
					sysTalkRecord.setRecordStatus("0");
					sysTalkRecord.setRecordContent(content);
					sysTalkRecordFeign.insert(sysTalkRecord);
				}
			}
		}
		model.addAttribute("content", content);
		model.addAttribute("targetIds", targetIds);
		model.addAttribute("time", content);
		model.addAttribute("query", "");
		return null;
	}

	/**
	 * ajax 获取信息
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getmsg")
	public String getmsg(Model model) throws Exception {
		HttpServletRequest request = getHttpRequest();
		String soruceUserId = request.getParameter("sourceId");
		String status = request.getParameter("status");
		sysTalkRecord = new SysTalkRecord();
		sysTalkRecord.setTargetUserId(User.getRegNo(request));
		sysTalkRecord.setSoruceUserId(soruceUserId);
		sysTalkRecord.setRecordStatus(status);
		List<SysTalkRecord> list = sysTalkRecordFeign.getUnReadRecords(sysTalkRecord);
		StringBuffer text = new StringBuffer();
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				sysTalkRecord = new SysTalkRecord();
				sysTalkRecord = list.get(i);
				String sendTime = sysTalkRecord.getSendTime();
				String nowDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
				if ((nowDateTime.substring(0, 9)).equals(sendTime.substring(0, 9))) {
					sendTime = sendTime.substring(10);
				}
				text.append("<font color='#006EFE'>" + sysTalkRecord.getSoruceUserName() + "("
						+ sysTalkRecord.getSoruceUserId() + ")" + "&nbsp;&nbsp;" + sendTime + "</font>" + "<br/>");
				text.append("&nbsp;&nbsp;" + sysTalkRecord.getRecordContent() + "</br>");
			}
		}
		sysTalkRecord.setRecordStatus("1");
		sysTalkRecordFeign.updateStatus(sysTalkRecord);
		HttpServletResponse response = getHttpResponse();
		response.setContentType("text/html;charset=GBK");
		PrintWriter pw = response.getWriter();
		pw.write(text.toString());
		pw.flush();
		pw.close();
		model.addAttribute("response", response);
		model.addAttribute("query", "");
		return null;
	}

	/**
	 * ajax 获取用户
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getuser")
	public String getuser(Model model) throws Exception {
		SysUser sysUser = new SysUser();
		sysUser.setOpNoType(BizPubParm.OP_NO_TYPE1);
		List<SysUser> list = sysUserFeign.getAllUserList(sysUser);
		StringBuffer text = new StringBuffer();
		text.append("<table id='users'>");
		for (int i = 0; i < list.size(); i++) {
			SysUser user = new SysUser();
			user = list.get(i);
			text.append("<tr><td><input type='checkbox' name='" + user.getOpNo() + ":" + user.getOpName() + "'>"
					+ user.getOpName() + "(" + user.getOpNo() + ")</td><tr>");
		}
		text.append("</table>");
		HttpServletResponse response = getHttpResponse();
		response.setContentType("text/html;charset=GBK");
		PrintWriter pw = response.getWriter();
		pw.write(text.toString());
		pw.flush();
		pw.close();
		model.addAttribute("text", text);
		model.addAttribute("query", "");
		return null;
	}

	/**
	 * 修改保存操作
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "update")
	public String update(Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formtalk0002 = formService.getFormData("talk0002");
		getFormValue(formtalk0002);
		sysTalkRecord = new SysTalkRecord();
		setObjValue(formtalk0002, sysTalkRecord);
		sysTalkRecordFeign.update(sysTalkRecord);
		getObjValue(formtalk0002, sysTalkRecord);
		model.addAttribute("formtalk0002", formtalk0002);
		model.addAttribute("query", "");
		return "/creditapp/talk/SysTalkRecord_Detail";
	}

	/**
	 * 删除操作
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "del")
	public String del(Model model, String targetUserId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formtalk0001 = formService.getFormData("talk0001");
		sysTalkRecord = new SysTalkRecord();
		sysTalkRecord.setTargetUserId(targetUserId);
		sysTalkRecordFeign.del(sysTalkRecord);
		this.addActionMessage(model, "删除成功");
		sysTalkRecord = new SysTalkRecord();
		Ipage ipage = this.getIpage();
		sysTalkRecordList = (List) sysTalkRecordFeign.findByPage(ipage, sysTalkRecord).getResult();
		model.addAttribute("formtalk0001", formtalk0001);
		model.addAttribute("ipage", ipage);
		model.addAttribute("query", "");
		return "/creditapp/talk/SysTalkRecord_List";
	}

	/**
	 * 查询操作
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getById")
	public String getById(Model model, String targetUserId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formtalk0002 = formService.getFormData("talk0002");
		sysTalkRecord = new SysTalkRecord();
		sysTalkRecord.setTargetUserId(targetUserId);
		sysTalkRecord = sysTalkRecordFeign.getById(sysTalkRecord);
		getObjValue(formtalk0002, sysTalkRecord);
		model.addAttribute("formtalk0002", formtalk0002);
		model.addAttribute("query", "");
		return "/creditapp/talk/SysTalkRecord_Detail";
	}

	/**
	 * 新增保存操作校验
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "validateInsert")
	public void validateInsert() {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formtalk0002 = formService.getFormData("talk0002");
		getFormValue(formtalk0002);
		validateFormData(formtalk0002);
	}

	/**
	 * 修改保存操作校验
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "validateUpdate")
	public void validateUpdate() {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formtalk0002 = formService.getFormData("talk0002");
		getFormValue(formtalk0002);
		validateFormData(formtalk0002);
	}

}