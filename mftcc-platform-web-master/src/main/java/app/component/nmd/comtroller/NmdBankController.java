package app.component.nmd.comtroller;

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

import app.component.nmd.entity.NmdBank;
import app.component.nmd.feign.NmdBankFeign;
import app.util.toolkit.Ipage;

/**
 * Title: NmdBankAction.java Description:
 * 
 * @author:renyongxian@dhcc.com.cn
 * @Tue Mar 26 10:10:12 GMT 2013
 **/
@Controller
@RequestMapping("/nmdBank")
public class NmdBankController extends BaseFormBean {

	// 页面传值

	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入NmdBankBo
	@Autowired
	private NmdBankFeign nmdBankFeign;

	/**
	 * 分页查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPage")
	public String findByPage(Model model, String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formnmd1301 = formService.getFormData("nmd1301");
		NmdBank nmdBank = new NmdBank();
		getFormValue(formnmd1301);
		setObjValue(formnmd1301, nmdBank);
		Ipage ipage = this.getIpage();
		List nmdBankList = (List) nmdBankFeign.findByPage(ipage, nmdBank).getResult();
		model.addAttribute("formnmd1301", formnmd1301);
		model.addAttribute("nmdBankList", nmdBankList);
		model.addAttribute("nmdBank", nmdBank);
		model.addAttribute("query", "");
		return "component/nmd/NmdBank_List";
	}

	@RequestMapping(value = "/findNumdBankPop")
	public String findNumdBankPop(Model model, String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formnmd1301 = formService.getFormData("nmd1301");
		NmdBank nmdBank = new NmdBank();
		getFormValue(formnmd1301);
		setObjValue(formnmd1301, nmdBank);
		Ipage ipage = this.getIpage();
		List nmdBankList = (List) nmdBankFeign.findByPage(ipage, nmdBank).getResult();
		model.addAttribute("formnmd1301", formnmd1301);
		model.addAttribute("nmdBankList", nmdBankList);
		model.addAttribute("nmdBank", nmdBank);
		model.addAttribute("query", "");
		return "component/nmd/NmdBank_Pop";
	}

	/**
	 * 获取新增页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/input")
	public String input(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formnmd1302 = formService.getFormData("nmd1302");
		model.addAttribute("formnmd1302", formnmd1302);
		model.addAttribute("query", "");
		return "component/nmd/NmdBank_Insert";
	}

	/**
	 * 新增保存操作
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insert")
	public String insert(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formnmd1302 = formService.getFormData("nmd1302");
		getFormValue(formnmd1302);
		NmdBank nmdBank = new NmdBank();
		setObjValue(formnmd1302, nmdBank);
		nmdBankFeign.insert(nmdBank);
		getObjValue(formnmd1302, nmdBank);
		model.addAttribute("formnmd1302", formnmd1302);
		model.addAttribute("nmdBank", nmdBank);
		model.addAttribute("query", "");
		return "component/nmd/NmdBank_Detail";
	}

	/**
	 * 修改保存操作
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/update")
	public String update(Model model, String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formnmd1302 = formService.getFormData("nmd1302");
		getFormValue(formnmd1302);
		NmdBank nmdBank = new NmdBank();
		setObjValue(formnmd1302, nmdBank);
		nmdBankFeign.update(nmdBank);
		getObjValue(formnmd1302, nmdBank);
		model.addAttribute("formnmd1302", formnmd1302);
		model.addAttribute("nmdBank", nmdBank);
		model.addAttribute("query", "");
		return "component/nmd/NmdBank_Detail";
	}

	/**
	 * 删除操作
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/del")
	public String del(Model model, String bankSerno) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formnmd1301 = formService.getFormData("nmd1301");
		NmdBank nmdBank = new NmdBank();
		nmdBank.setBankSerno(bankSerno);
		nmdBankFeign.del(nmdBank);
		this.addActionMessage(model, "删除成功");
		Ipage ipage = this.getIpage();
		List nmdBankList = (List) nmdBankFeign.findByPage(ipage, nmdBank).getResult();
		model.addAttribute("formnmd1301", formnmd1301);
		model.addAttribute("nmdBank", nmdBank);
		model.addAttribute("bankSerno", bankSerno);
		model.addAttribute("query", "");
		return "component/nmd/NmdBank_List";
	}

	/**
	 * 查询操作
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String bankSerno) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formnmd1302 = formService.getFormData("nmd1302");
		NmdBank nmdBank = new NmdBank();
		nmdBank.setBankSerno(bankSerno);
		nmdBank = nmdBankFeign.getById(nmdBank);
		getObjValue(formnmd1302, nmdBank);
		model.addAttribute("formnmd1302", formnmd1302);
		model.addAttribute("nmdBank", nmdBank);
		model.addAttribute("bankSerno", bankSerno);
		model.addAttribute("query", "");
		return "component/nmd/NmdBank_Detail";
	}

	/**
	 * 新增保存操作校验
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateInsert")
	public void validateInsert(Model model) {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formnmd1302 = formService.getFormData("nmd1302");
		getFormValue(formnmd1302);
		validateFormData(formnmd1302);
	}

	/**
	 * 修改保存操作校验
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateUpdate")
	public void validateUpdate(Model model) {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formnmd1302 = formService.getFormData("nmd1302");
		getFormValue(formnmd1302);
		validateFormData(formnmd1302);
	}

}
