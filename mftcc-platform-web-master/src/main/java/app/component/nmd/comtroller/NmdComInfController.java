package  app.component.nmd.comtroller;
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

import app.base.User;
import app.component.nmd.entity.NmdComInf;
import app.component.nmd.feign.NmdComInfFeign;
import app.util.toolkit.Ipage;

/**
* Title: NmdComInfAction.java
* Description:
* @author:renyongxian@dhcc.com.cn
* @Tue May 07 09:53:21 CST 2013
**/
@Controller
@RequestMapping("/nmdComInf")
public class NmdComInfController extends BaseFormBean {

	//页面传值

	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	//注入NmdComInfBo
	@Autowired
	private NmdComInfFeign nmdComInfFeign;

	
	/**
	 * 分页查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPage")
	 public String findByPage(Model model, String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		FormData formnmd1701 = formService.getFormData("nmd1701");
		NmdComInf nmdComInf = new NmdComInf();
		getFormValue(formnmd1701);
		setObjValue(formnmd1701, nmdComInf);
		Ipage ipage = this.getIpage();
		List nmdComInfList = (List) nmdComInfFeign.findByPage(ipage, nmdComInf).getResult();
		model.addAttribute("formnmd1701", formnmd1701);
		model.addAttribute("nmdComInf", nmdComInf);
		model.addAttribute("nmdComInfList", nmdComInfList);
		model.addAttribute("query", "");
		return "component/nmd/NmdComInf_List";
	}
	
	
	/**
	 * 获取新增页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/input")
	public String input(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formnmd1702 = formService.getFormData("nmd1702");
		model.addAttribute("formnmd1702", formnmd1702);
		model.addAttribute("query", "");
		return "component/nmd/NmdComInf_Insert";
	}
	
	/**
	 * 新增保存操作
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insert")
	public String insert(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formnmd1702 = formService.getFormData("nmd1702");
		getFormValue(formnmd1702);
		NmdComInf nmdComInf = new NmdComInf();
		setObjValue(formnmd1702, nmdComInf);
		nmdComInf.setTxDate(User.getSysDate(this.getHttpRequest()));
		nmdComInf.setOpNo(User.getRegNo(this.getHttpRequest()));
		nmdComInfFeign.insert(nmdComInf);
		getObjValue(formnmd1702, nmdComInf);
		model.addAttribute("formnmd1702", formnmd1702);
		model.addAttribute("nmdComInf", nmdComInf);
		model.addAttribute("query", "");
		return "component/nmd/NmdComInf_Detail";
	}
	
	@RequestMapping(value = "/insertOrUpdate")
	public String insertOrUpdate(Model model, String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formnmd1702 = formService.getFormData("nmd1702");
		NmdComInf nmdComInf = new NmdComInf();
		getFormValue(formnmd1702);
		setObjValue(formnmd1702, nmdComInf);
		NmdComInf nmdComInfTwo=nmdComInfFeign.getById(nmdComInf);
		if(nmdComInfTwo==null)
			{nmdComInf.setTxDate(User.getSysDate(this.getHttpRequest()));
			nmdComInf.setUpDate(User.getSysDate(this.getHttpRequest()));
		    nmdComInf.setOpNo(User.getRegNo(this.getHttpRequest()));
		    nmdComInf.setOpName(User.getRegName(this.getHttpRequest()));
		    nmdComInf.setUpOpNo(User.getRegNo(this.getHttpRequest()));
		    nmdComInf.setUpOpName(User.getRegName(this.getHttpRequest()));
			nmdComInfFeign.insert(nmdComInf);}
		else
			{
			nmdComInf.setAppNo(nmdComInfTwo.getAppNo());
			nmdComInfFeign.update(nmdComInf);
			}
		getObjValue(formnmd1702, nmdComInf);
		model.addAttribute("formnmd1702", formnmd1702);
		model.addAttribute("nmdComInf", nmdComInf);
		model.addAttribute("query", "");
		return "component/nmd/NmdComInf_Detail";
	}
	
	
	
	

	/**
	 * 修改保存操作
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/update")
	public String update(Model model, String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formnmd1702 = formService.getFormData("nmd1702");
		getFormValue(formnmd1702);
		NmdComInf nmdComInf = new NmdComInf();
		setObjValue(formnmd1702, nmdComInf);
		nmdComInf.setUpDate(User.getSysDate(this.getHttpRequest()));
		nmdComInf.setUpOpNo(User.getRegNo(this.getHttpRequest()));
		nmdComInfFeign.update(nmdComInf);
		getObjValue(formnmd1702, nmdComInf);
		model.addAttribute("formnmd1702", formnmd1702);
		model.addAttribute("nmdComInf", nmdComInf);
		model.addAttribute("query", "");
		return "component/nmd/NmdComInf_Detail";
	}
	
	
	
	/**
	 * 删除操作
	 * @param appNo 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/del")
	public String del(Model model, String appNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formnmd1701 = formService.getFormData("nmd1701");
		NmdComInf nmdComInf = new NmdComInf();
		nmdComInf.setAppNo(appNo);
		nmdComInfFeign.del(nmdComInf);
		this.addActionMessage(model, "删除成功");
		Ipage ipage = this.getIpage();
		List nmdComInfList = (List) nmdComInfFeign.findByPage(ipage, nmdComInf).getResult();
		model.addAttribute("formnmd1701", formnmd1701);
		model.addAttribute("nmdComInf", nmdComInf);
		model.addAttribute("nmdComInfList", nmdComInfList);
		model.addAttribute("query", "");
		return "component/nmd/NmdComInf_List";
	}

	
		/**
	 * 查询操作
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String appNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		FormData formnmd1702 = formService.getFormData("nmd1702");
		NmdComInf nmdComInf = new NmdComInf();
		nmdComInf.setAppNo(appNo);
		nmdComInf = nmdComInfFeign.getById(nmdComInf);
		getObjValue(formnmd1702, nmdComInf);
		model.addAttribute("formnmd1702", formnmd1702);
		model.addAttribute("nmdComInf", nmdComInf);
		model.addAttribute("query", "");
		return "component/nmd/NmdComInf_Detail";
	}
	
	/**
	 * 新增保存操作校验
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateInsert")
	public void validateInsert(Model model){
		FormService formService = new FormService();
		 ActionContext.initialize(request, response);
		FormData  formnmd1702 = formService.getFormData("nmd1702");
		 getFormValue(formnmd1702);
		 validateFormData(formnmd1702);
   }
   
	/**
	 * 修改保存操作校验
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateUpdate")
	public void validateUpdate(Model model){
		FormService formService = new FormService();
		 ActionContext.initialize(request, response);
		FormData  formnmd1702 = formService.getFormData("nmd1702");
		 getFormValue(formnmd1702);
		 validateFormData(formnmd1702);
  	}
	
}
