package app.component.pfs.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.core.struts.BaseFormBean;

import app.component.pfs.entity.CusFinModel;
import app.component.pfs.feign.CusFinModelFeign;

/**
 * @author lenovo
 *
 */
@Controller
@RequestMapping("/cusFinModel")
public class CusFinModelController extends BaseFormBean {

	@Autowired
	private HttpServletRequest request;
	@Autowired
	private CusFinModelFeign cusFinModelFeign;

	/**
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/input")
	public String input(Model model) throws Exception {
		return "/component/pfs/CusFinModel_InsertOrUpdate";
	}	
	
	/**
	 * 查询财务报表模型
	 * @param cusFinModel 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/listCusFinModel")
	public String listCusFinModel(Model model, String ajaxData, CusFinModel cusFinModel) throws Exception {
		List<CusFinModel> list = cusFinModelFeign.list(cusFinModel);
		request.setAttribute("list", list);
		model.addAttribute("query", "");
		return "/component/pfs/CusFinModel_list";
	}
	
	/**
	 * 更新财务报表模型
	 * @param cusFinModel 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateDragFinModel")
	public String updateDragFinModel(Model model, String ajaxData, CusFinModel cusFinModel) throws Exception {
		cusFinModel.setList(getDragListFromView(cusFinModel));
		cusFinModelFeign.updateDragFinModel(cusFinModel);
		this.setMessage("操作成功");
		model.addAttribute("query", "");
		return "/component/pfs/CusFinModel_InsertOrUpdate_result";
	}	
	
	/**
	 * 保存/更新财务报表模型
	 * @param cusFinModel 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertOrUpdate")
	public String insertOrUpdate(Model model, String ajaxData, CusFinModel cusFinModel) throws Exception {
		cusFinModel.setList(getListFromView(cusFinModel));
		cusFinModelFeign.saveOrUpdate(cusFinModel);
		this.setMessage("操作成功");
		model.addAttribute("query", "");
		return "/component/pfs/CusFinModel_InsertOrUpdate_result";
	}


	private List<CusFinModel> getListFromView(CusFinModel cusFinModel) {
		String[] cnts = cusFinModel.getCnts().split(",");
		cusFinModel.setCodeName(cusFinModel.getCodeName().replaceAll("&nbsp;", ""));
		String[] codeNames = cusFinModel.getCodeName().split(",");
		String[] codeTypes = cusFinModel.getCodeType().split(",");
		String[] codeColumns = cusFinModel.getCodeColumn().split(",");
		int length = codeNames.length;
		List<CusFinModel> list = new ArrayList<CusFinModel>(length);
		CusFinModel bean = null;
		for(int i = 0; i < length; i++) {
		 	bean = new CusFinModel();
			bean.setReportType(cusFinModel.getReportType());
			bean.setCodeName(codeNames[i]);
			bean.setCodeType(codeTypes[i]);
			bean.setCodeColumn(codeColumns[i]);
			bean.setCnt(Integer.valueOf(cnts[i]));
			bean.setAccRule(cusFinModel.getAccRule());
			list.add(bean);
		}
		return list;
	}
	
	private List<CusFinModel> getDragListFromView(CusFinModel cusFinModel) {
		String[] cnts = cusFinModel.getCnts().split(",");
		String[] codeNames = cusFinModel.getCodeName().split(",");
		int length = codeNames.length;
		List<CusFinModel> list = new ArrayList<CusFinModel>(length);
		CusFinModel bean = null;
		int startIndex = 0, endIndex = 0;
		for(int i = 0; i < length; i++) {
		 	bean = new CusFinModel();
			bean.setReportType(cusFinModel.getReportType());
			startIndex = codeNames[i].indexOf("[");
			endIndex = codeNames[i].indexOf("]");
			bean.setCodeName(codeNames[i].substring(0, startIndex));
			bean.setCodeColumn(codeNames[i].substring((startIndex+1), endIndex));
			bean.setCnt(Integer.valueOf(cnts[i]));
			bean.setAccRule(cusFinModel.getAccRule());
			list.add(bean);
		}
		return list;
	}
}
