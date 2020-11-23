package app.component.pfs.controller;

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

import app.component.pfs.entity.CusDuBangModel;
import app.component.pfs.entity.CusFinRatios;
import app.component.pfs.feign.CusFinRatiosFeign;

@Controller
@RequestMapping("/cusFinRatios")
public class CusFinRatiosController extends BaseFormBean {

	// 异步参数
	// 表单变量

	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private CusFinRatiosFeign cusFinRatiosFeign;

	@RequestMapping(value = "/getDuBang")
	public String getDuBang(Model model, String ajaxData, Object cusNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formpfs1003 = formService.getFormData("pfs1003");
		this.changeFormProperty(formpfs1003, "cusNo", "initValue", cusNo);
		model.addAttribute("formpfs1003", formpfs1003);
		model.addAttribute("query", "");
		return "/component/pfs/CusFInRatios_List";
	}

	/**
	 * 查询杜邦分析数据
	 * 
	 * @param startDate
	 * @param endDate
	 * @param cusNo
	 * @param accRule
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getDuBangData")
	public String getDuBangData(Model model, String ajaxData, String startDate, String endDate, String cusNo,
			String accRule) throws Exception {
		ActionContext.initialize(request, response);
		try {
			CusFinRatios cusFinRatios = new CusFinRatios();
			cusFinRatios.setStartDate(startDate);
			cusFinRatios.setEndDate(endDate);
			cusFinRatios.setCusNo(cusNo);
			cusFinRatios.setAccRule(accRule);
			
			/*
			 * CusCorpInfo cusCorpInfo = new CusCorpInfo();
			 * cusCorpInfo.setCusNo(cusNo); cusCorpInfo =
			 * cusFinRatiosFeign.getCusNo(cusCorpInfo); if (cusCorpInfo == null
			 * || StringUtils.isEmpty(cusCorpInfo.getCusNo())) {
			 * this.addActionMessage(model, "客户信息不存在");
			 * model.addAttribute("formpfs1003", formpfs1003);
			 * model.addAttribute("query", ""); return "duBangList"; }
			 * cusFinRatios.setCusNo(cusCorpInfo.getCusNo());
			 */
			List<CusDuBangModel> dubanglist = cusFinRatiosFeign.getDuBangData(cusFinRatios);
			model.addAttribute("dubanglist", dubanglist);
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("query", "");
		return "/component/pfs/CusFInRatios_DuBangList";
	}
}
