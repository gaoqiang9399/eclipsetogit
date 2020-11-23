package  app.component.sys.controller;
import java.io.PrintWriter;
import java.util.List;

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

import app.component.sys.entity.SysButton;
import app.component.sys.feign.SysButtonFeign;
import app.util.toolkit.Ipage;

/**
* Title: SysButtonAction.java
* Description:
* @author:lifeng@dhcc.com.cn
* @Thu Mar 14 12:47:13 GMT 2013
**/
@Controller
@RequestMapping("/sysButton")
public class SysButtonController extends BaseFormBean {

	//页面传值

	//注入sysButtonFeign
	@Autowired
	private SysButtonFeign sysButtonFeign;

	private String query;
	
	private FormData formsys0031;
	private FormData formsys0032;
	private FormService formService = new FormService();
	
	@Autowired  
	private HttpServletRequest request;
	@Autowired  
	private HttpServletResponse response;
	//异步参数
	public SysButtonController() {
		query = "";
	}
	/**
	 * 分页查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPage")
	 public String findByPage() throws Exception {
		ActionContext.initialize(request,response);
		formsys0031 = formService.getFormData("sys0031");
		SysButton sysButton = new SysButton();
		getFormValue(formsys0031);
		setObjValue(formsys0031, sysButton);
		Ipage ipage = this.getIpage();
		ipage.setParams(this.setIpageParams("sysButton", sysButton));
		List<SysButton> sysButtonList = (List) sysButtonFeign.findByPage(ipage).getResult();
		return "/component/sys/SysButton_List";
	}
	
	/**
	 * 获取新增页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/input")
	public String input(Model model) throws Exception {
		ActionContext.initialize(request, response);
		formsys0032 = formService.getFormData("sys0032");
		model.addAttribute("formsys0032", formsys0032);
		return "/component/sys/SysButton_Insert";
	}
	
	/**
	 * 新增保存操作
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insert")
	public String insert(Model model) throws Exception {
		ActionContext.initialize(request, response);
		formsys0032 = formService.getFormData("sys0032");
		getFormValue(formsys0032);
		SysButton sysButton = new SysButton();
		setObjValue(formsys0032, sysButton);
		sysButtonFeign.insert(sysButton);
		getObjValue(formsys0032, sysButton);
		model.addAttribute("formsys0032", formsys0032);
		return "/component/sys/SysButton_Detail";
	}
	
	/**
	 * 修改保存操作
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/update")
	public String update(Model model) throws Exception {
		ActionContext.initialize(request, response);
		formsys0032 = formService.getFormData("sys0032");
		getFormValue(formsys0032);
		SysButton sysButton = new SysButton();
		setObjValue(formsys0032, sysButton);
		sysButtonFeign.update(sysButton);
		getObjValue(formsys0032, sysButton);
		model.addAttribute("formsys0032", formsys0032);
		return "/component/sys/SysButton_Detail";
	}
	
	
	
	/**
	 * 删除操作
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/del")
	public String del(Model model,String menuNo,String buttonNo) throws Exception {
		ActionContext.initialize(request, response);
		formsys0031 = formService.getFormData("sys0031");
		SysButton sysButton = new SysButton();
		sysButton.setMenuNo(menuNo);
		sysButton.setButtonNo(buttonNo);
		sysButtonFeign.del(sysButton);
		this.addActionMessage(model,"删除成功");
		sysButton = new SysButton();
		Ipage ipage = this.getIpage();
		ipage.setParams(this.setIpageParams("sysButton", sysButton));
		List<SysButton> sysButtonList = (List) sysButtonFeign.findByPage(ipage).getResult();
		model.addAttribute("sysButtonList", sysButtonList);
		model.addAttribute("formsys0031", formsys0031);
		return "/component/sys/SysButton_List";
	}

	
		/**
	 * 查询操作
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model,String menuNo,String buttonNo) throws Exception {
		ActionContext.initialize(request,response);
		formsys0032 = formService.getFormData("sys0032");
		SysButton sysButton = new SysButton();
		sysButton.setMenuNo(menuNo);
		sysButton.setButtonNo(buttonNo);
		sysButton = sysButtonFeign.getById(sysButton);
		getObjValue(formsys0032, sysButton);
		model.addAttribute("formsys0032", formsys0032);
		return "/component/sys/SysButton_Detail";
	}
	
	/**
	 * 新增保存操作校验
	 * @return
	 * @throws Exception
	 */
	public void validateInsert(){
		 ActionContext.initialize(request, response);
		 formsys0032 = formService.getFormData("sys0032");
		 getFormValue(formsys0032);
		 validateFormData(formsys0032);
   }
   
	/**
	 * 修改保存操作校验
	 * @return
	 * @throws Exception
	 */
	public void validateUpdate(){
		 ActionContext.initialize(request, response);
		 formsys0032 = formService.getFormData("sys0032");
		 getFormValue(formsys0032);
		 validateFormData(formsys0032);
  	}
	@RequestMapping(value = "/findAllByMenu")
	 public String findAllByMenu(Model model,String menuNo) throws Exception {
		 ActionContext.initialize(request,response);
		 SysButton sysButton = new SysButton();
		 sysButton.setMenuNo(menuNo);
		 List<SysButton> sysButtonList = sysButtonFeign.findAllByMenu(sysButton);
		 model.addAttribute("sysButtonList", sysButtonList);
		 return "/component/sys/SysButton_ListForMenu";
	 }
	@RequestMapping(value = "/insertBtn")
	public String insertBtn(String buttonNo,String buttonDesc,String menuNo) throws Exception{
		 buttonNo = java.net.URLDecoder.decode(new String(buttonNo.getBytes("GBK"),"ISO-8859-1"), "UTF-8");
		 buttonDesc = java.net.URLDecoder.decode(new String(buttonDesc.getBytes("GBK"),"ISO-8859-1"), "UTF-8");
		response.setContentType("text/html;charset=utf-8"); 
		PrintWriter out = response.getWriter(); 
		String result = "success";
		try{
			SysButton sysButton = new SysButton();
			sysButton.setMenuNo(menuNo);
			sysButton.setButtonNo(buttonNo);
			sysButton.setButtonDesc(buttonDesc);
			sysButtonFeign.insert(sysButton);
		}catch(Exception e){
			e.printStackTrace();
			result = "fail";
		}
		out.print(result);
		return null;
	}
	@RequestMapping(value = "/updateBtn")
	public String updateBtn(String buttonNo,String buttonDesc,String menuNo) throws Exception{
		buttonNo = java.net.URLDecoder.decode(new String(buttonNo.getBytes("GBK"),"ISO-8859-1"), "UTF-8");
		buttonDesc = java.net.URLDecoder.decode(new String(buttonDesc.getBytes("GBK"),"ISO-8859-1"), "UTF-8");
		
		this.getHttpResponse().setContentType("text/html;charset=utf-8"); 
		PrintWriter out = this.getHttpResponse().getWriter(); 
		String result = "success";
		try{
			SysButton sysButton = new SysButton();
			sysButton.setMenuNo(menuNo);
			sysButton.setButtonNo(buttonNo);
			sysButton.setButtonDesc(buttonDesc);
			sysButtonFeign.update(sysButton);
		}catch(Exception e){
			e.printStackTrace();
			result = "fail";
		}
		out.print(result);
		return null;
	}
	@RequestMapping(value = "/deleteBtn")
	public String deleteBtn(String buttonNo,String menuNo) throws Exception{
		buttonNo = java.net.URLDecoder.decode(new String(buttonNo.getBytes("GBK"),"ISO-8859-1"), "UTF-8");
		
		this.getHttpResponse().setContentType("text/html;charset=utf-8"); 
		PrintWriter out = this.getHttpResponse().getWriter(); 
		String result = "success";
		try{
			SysButton sysButton = new SysButton();
			sysButton.setMenuNo(menuNo);
			sysButton.setButtonNo(buttonNo);
			sysButtonFeign.del(sysButton);
		}catch(Exception e){
			e.printStackTrace();
			result = "fail";
		}
		out.print(result);
		return null;
	}
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public FormData getFormsys0032() {
		return formsys0032;
	}
	public void setFormsys0032(FormData formsys0032) {
		this.formsys0032 = formsys0032;
	}
	public FormData getFormsys0031() {
		return formsys0031;
	}
	public void setFormsys0031(FormData formsys0031) {
		this.formsys0031 = formsys0031;
	}
}
