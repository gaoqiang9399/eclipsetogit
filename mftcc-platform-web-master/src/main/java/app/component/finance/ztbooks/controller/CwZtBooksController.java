package app.component.finance.ztbooks.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.component.cus.institutions.entity.MfBusInstitutions;
import app.component.cus.institutions.feign.MfBusInstitutionsFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;

import app.base.User;
import app.component.common.EntityUtil;
import app.component.finance.util.CWEnumBean;
import app.component.finance.util.CwCacheUtil;
import app.component.finance.util.R;
import app.component.finance.ztbooks.entity.CwZtBooks;
import app.component.finance.ztbooks.feign.CwZtBooksFeign;
import app.component.sys.entity.SysUser;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Title: CwZtBooksAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Wed Jul 19 09:27:42 CST 2017
 **/
@Controller
@RequestMapping("/cwZtBooks")
public class CwZtBooksController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入CwZtBooksFeign
	@Autowired
	private CwZtBooksFeign cwZtBooksFeign;
	@Autowired
	private MfBusInstitutionsFeign mfBusInstitutionsFeign;

	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage() throws Exception {
		ActionContext.initialize(request, response);
		return "/component/finance/ztbooks/CwZtBooks_List";
	}

	/***
	 * 列表数据查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPageAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType, String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		CwZtBooks cwZtBooks = new CwZtBooks();
		try {
			cwZtBooks.setCustomQuery(ajaxData);// 自定义查询参数赋值
			cwZtBooks.setCriteriaList(cwZtBooks, ajaxData);// 我的筛选
			// cwZtBooks.setCustomSorts(ajaxData);//自定义排序
			// this.getRoleConditions(cwZtBooks,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setPageSize(pageSize);
			// 自定义查询Bo方法
			Map<String, Object> params = new HashMap<>();
			params.put("cwZtBooks", cwZtBooks);
			ipage.setParams(params);
			ipage = cwZtBooksFeign.findByPage(ipage);
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
	 * 方法描述： 选择帐套
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Javelin
	 * @date 2017-7-20 上午9:58:15
	 */
	@RequestMapping(value = "/chooseFinBooksAjax", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> chooseFinBooksAjax() throws Exception {
//		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		CwZtBooks cwZtBooks = new CwZtBooks();
		try {
			// 自定义查询Bo方法

			String regNo = User.getRegNo(request);// 操作员账号

			cwZtBooks.setOperator(User.getRegNo(request));// 操作人员
			cwZtBooks.setUseFlag(CWEnumBean.YES_OR_NO.SHI.getNum());
			cwZtBooks.setDelSts(CWEnumBean.YES_OR_NO.FOU.getNum());
			List<CwZtBooks> cwZtBooksList = cwZtBooksFeign.getCwZtBooksList(cwZtBooks);
			dataMap.put("flag", "success");
			if (cwZtBooksList != null && cwZtBooksList.size() > 0) {
				// 若只有一个帐套操作权限，封装session 直接进入
				if (cwZtBooksList.size() == 1) {
					String finBooks = cwZtBooksList.get(0).getFinBooks();
					request.getSession().setAttribute("finBooks", finBooks);
					// this.getHttpRequest().getSession().setAttribute(regNo+"finBooks", finBooks);
					dataMap.put("setSession", true);
				} else {
					// 多个帐套操作权限 提供选择
					dataMap.put("setSession", false);
				}
			} else {
				request.getSession().setAttribute("finBooks", CwCacheUtil.CW_BOOKS);
				// this.getHttpRequest().getSession().setAttribute(regNo+"finBooks",
				// CwCacheUtil.CW_BOOKS);
				dataMap.put("setSession", true);
				// 若没有帐套操作权限，
				// 查看是否就basic帐套
				// cwZtBooksList = cwZtBooksFeign.getCwZtBooksList(new CwZtBooks());
				// if (cwZtBooksList.size() == 1 &&
				// CwCacheUtil.CW_BOOKS.equals(cwZtBooksList.get(0).getFinBooks())) {
				// this.getHttpRequest().getSession().setAttribute("finBooks",
				// CwCacheUtil.CW_BOOKS);
				// dataMap.put("setSession", true);
				// }else {
				// dataMap.put("flag", "error");
				// dataMap.put("msg", MessageEnum.FIRST_OPERATION_ADD.getMessage("财务帐套权限"));
				// }
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw new Exception(e.getMessage());
		}
		return dataMap;
	}

	/**
	 * 方法描述： 帐套选择列表
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Javelin
	 * @date 2017-7-20 下午2:29:08
	 */
	@RequestMapping(value = "/getBooksSelectList")
	public String getBooksSelectList(Model model) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		CwZtBooks cwZtBooks = new CwZtBooks();
		try {
			// 自定义查询Bo方法
			cwZtBooks.setOperator(User.getRegNo(request));// 操作人员
			cwZtBooks.setUseFlag(CWEnumBean.YES_OR_NO.SHI.getNum());
			cwZtBooks.setDelSts(CWEnumBean.YES_OR_NO.FOU.getNum());
			List<CwZtBooks> cwZtBooksList = cwZtBooksFeign.getCwZtBooksList(cwZtBooks);
			model.addAttribute("cwZtBooksList", cwZtBooksList);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw new Exception(e.getMessage());
		}
		model.addAttribute("dataMap", dataMap);
		return "/component/finance/ztbooks/CwZtBooks_Select";
	}

	/**
	 * 方法描述： 设置帐套session
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Javelin
	 * @date 2017-7-20 上午10:01:10
	 */
	@RequestMapping(value = "/setFinBooksSessionAjax")
	@ResponseBody
	public Map<String, Object> setFinBooksSessionAjax(Model model, String bookCode) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		//FIXME 存session
		this.getHttpRequest().getSession().setAttribute("finBooks", bookCode);
		model.addAttribute("finBooks", bookCode);
		// this.getHttpRequest().getSession().setAttribute(regNo+"finBooks", bookCode);
		return dataMap;
	}

	/**
	 * 选择帐套列表 lzs
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getBooksSelectListForLoginAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getBooksSelectListForLoginAjax(String userNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		CwZtBooks cwZtBooks = new CwZtBooks();
		try {
			// 自定义查询Bo方法
			cwZtBooks.setOperator(userNo);// 操作人员
			cwZtBooks.setUseFlag(CWEnumBean.YES_OR_NO.SHI.getNum());
			cwZtBooks.setDelSts(CWEnumBean.YES_OR_NO.FOU.getNum());
			List<CwZtBooks> cwZtBooksList = cwZtBooksFeign.getCwZtBooksList(cwZtBooks);
			if (cwZtBooksList != null && cwZtBooksList.size() > 0) {
				// dataMap.put("cwZtBooksList", cwZtBooksList);
			} else {
				CwZtBooks basicBooks = new CwZtBooks();
				basicBooks.setFinBooks("basic");
				CwZtBooks byFinBooks = cwZtBooksFeign.getByFinBooks(basicBooks);
				if (byFinBooks == null) {
					throw new Exception();
				} else {
					cwZtBooksList = new ArrayList<CwZtBooks>();
					cwZtBooksList.add(byFinBooks);
				}

			}
			dataMap.put("cwZtBooksList", cwZtBooksList);
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
	@RequestMapping(value = "/insertAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formztbooks0001 = new FormService().getFormData("ztbooks0002");
			getFormValue(formztbooks0001, getMapByJson(ajaxData));
			if (this.validateFormData(formztbooks0001)) {
				CwZtBooks cwZtBooks = new CwZtBooks();
				setObjValue(formztbooks0001, cwZtBooks);
				String finBooks = (String)request.getSession().getAttribute("finBooks");
				R r = cwZtBooksFeign.insert(cwZtBooks,finBooks);
				if(r.isOk()) {
					dataMap.put("flag", "success");
					// dataMap.put("msg", "新增成功");
					dataMap.put("msg", MessageEnum.SUCCEED_SAVE.getMessage());
				}else {
					dataMap.put("flag", "error");
					dataMap.put("msg", r.getMsg());
				}
			} else {
				dataMap.put("flag", "error");
				// dataMap.put("msg",this.getFormulavaliErrorMsg());
				dataMap.put("msg", MessageEnum.FAILED_SAVE.getMessage());
			}
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_SAVE.getMessage());
			e.printStackTrace();
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
	@RequestMapping(value = "/updateAjaxByOne", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formztbooks0001 = new FormService().getFormData("ztbooks0002");
		getFormValue(formztbooks0001, getMapByJson(ajaxData));
		CwZtBooks cwZtBooksJsp = new CwZtBooks();
		setObjValue(formztbooks0001, cwZtBooksJsp);
		CwZtBooks cwZtBooks = cwZtBooksFeign.getById(cwZtBooksJsp);
		if (cwZtBooks != null) {
			try {
				cwZtBooks = (CwZtBooks) EntityUtil.reflectionSetVal(cwZtBooks, cwZtBooksJsp, getMapByJson(ajaxData));
				cwZtBooksFeign.update(cwZtBooks);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_SAVE.getMessage());
				// dataMap.put("msg", "保存成功");
			} catch (Exception e) {
				e.printStackTrace();
				dataMap.put("flag", "error");
				// dataMap.put("msg", "新增失败");
				dataMap.put("msg", MessageEnum.FAILED_SAVE.getMessage());
				throw new Exception(e.getMessage());
			}
		} else {
			dataMap.put("flag", "error");
			// dataMap.put("msg", "编号不存在,保存失败");
			dataMap.put("msg", MessageEnum.FAILED_SAVE.getMessage());
		}
		return dataMap;
	}

	/**
	 * AJAX更新保存
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formztbooks0001 = new FormService().getFormData("ztbooks0002");
			getFormValue(formztbooks0001, getMapByJson(ajaxData));
			if (this.validateFormData(formztbooks0001)) {
				CwZtBooks cwZtBooks = new CwZtBooks();
				setObjValue(formztbooks0001, cwZtBooks);
				cwZtBooksFeign.update(cwZtBooks);
				dataMap.put("flag", "success");// SUCCEED_UPDATE
				dataMap.put("msg", MessageEnum.SUCCEED_UPDATE.getMessage());//
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_UPDATE.getMessage());
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
	@RequestMapping(value = "/getByIdAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getByIdAjax(String bookCode) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formztbooks0001 = new FormService().getFormData("ztbooks0002");
		CwZtBooks cwZtBooks = new CwZtBooks();
		cwZtBooks.setBookCode(bookCode);
		cwZtBooks = cwZtBooksFeign.getById(cwZtBooks);
		getObjValue(formztbooks0001, cwZtBooks, formData);
		if (cwZtBooks != null) {
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
	@RequestMapping(value = "/deleteAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAjax(String bookCode) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		CwZtBooks cwZtBooks = new CwZtBooks();
		cwZtBooks.setBookCode(bookCode);
		try {
			cwZtBooksFeign.delete(cwZtBooks);
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
		ActionContext.initialize(request, response);
		FormData formztbooks0001 = new FormService().getFormData("ztbooks0002");

		try {
			JSONObject json = new JSONObject();
			List<SysUser> suList = cwZtBooksFeign.getAllUserList(new SysUser());
			JSONArray userArray = JSONArray.fromObject(suList);
			for (int i = 0; i < userArray.size(); i++) {
				userArray.getJSONObject(i).put("id", userArray.getJSONObject(i).getString("opNo"));
				userArray.getJSONObject(i).put("name", userArray.getJSONObject(i).getString("opName"));
			}
			json.put("userArray", userArray);
			Ipage ipage = this.getIpage();
			ipage = mfBusInstitutionsFeign.getAgenciesDataSourse(ipage);
			List<MfBusInstitutions> brList = (List<MfBusInstitutions>) ipage.getResult();
			JSONArray brArray = JSONArray.fromObject(brList);
			for (int i = 0; i < brArray.size(); i++) {
                brArray.getJSONObject(i).put("id", brArray.getJSONObject(i).getString("agenciesId"));
                brArray.getJSONObject(i).put("name", brArray.getJSONObject(i).getString("agenciesName"));
			}
			json.put("brArray", brArray);
			String ajaxData = json.toString();
			model.addAttribute("formztbooks0001", formztbooks0001);
			model.addAttribute("ajaxData", ajaxData);
			model.addAttribute("query", "");
		} catch (Exception e) {
			e.printStackTrace();
			// dataMap.put("flag", "error");
			// dataMap.put("msg",MessageEnum.FAILED_SAVE.getMessage());
			throw new Exception(e.getMessage());
		}
		return "component/finance/ztbooks/CwZtBooks_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String bookCode) throws Exception {
		ActionContext.initialize(request, response);
		try {
			FormData formztbooks0001 = new FormService().getFormData("ztbooks0001");
			getFormValue(formztbooks0001);
			CwZtBooks cwZtBooks = new CwZtBooks();
			cwZtBooks.setBookCode(bookCode);
			cwZtBooks = cwZtBooksFeign.getById(cwZtBooks);
			getObjValue(formztbooks0001, cwZtBooks);
			if (cwZtBooks != null) {
				String finBooks = cwZtBooks.getFinBooks();
				this.getHttpRequest().setAttribute("finBooks", finBooks);
			}

			JSONObject json = new JSONObject();
			List<SysUser> suList = cwZtBooksFeign.getAllUserList(new SysUser());
			JSONArray userArray = JSONArray.fromObject(suList);
			for (int i = 0; i < userArray.size(); i++) {
				userArray.getJSONObject(i).put("id", userArray.getJSONObject(i).getString("opNo"));
				userArray.getJSONObject(i).put("name", userArray.getJSONObject(i).getString("opName"));
			}
			json.put("userArray", userArray);
			Ipage ipage = this.getIpage();
			ipage = mfBusInstitutionsFeign.getAgenciesDataSourse(ipage);
			List<MfBusInstitutions> brList = (List<MfBusInstitutions>) ipage.getResult();
			JSONArray brArray = JSONArray.fromObject(brList);
			for (int i = 0; i < brArray.size(); i++) {
				brArray.getJSONObject(i).put("id", brArray.getJSONObject(i).getString("agenciesId"));
				brArray.getJSONObject(i).put("name", brArray.getJSONObject(i).getString("agenciesName"));
			}
            json.put("brArray", brArray);
			String ajaxData = json.toString();
			model.addAttribute("cwZtBooks", cwZtBooks);
			model.addAttribute("formztbooks0001", formztbooks0001);
			model.addAttribute("ajaxData", ajaxData);
			model.addAttribute("query", "");
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}

		return "/component/finance/ztbooks/CwZtBooks_Detail";
	}

	/**
	 * 新增校验
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateInsert")
	public void validateInsert(Model model,String query) throws Exception {
		ActionContext.initialize(request, response);
		FormData formztbooks0001 = new FormService().getFormData("ztbooks0002");
		getFormValue(formztbooks0001);
		boolean validateFlag = this.validateFormData(formztbooks0001);
		model.addAttribute("formztbooks0001", formztbooks0001);
		model.addAttribute("query", query);
	}

	/**
	 * 修改校验
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateUpdate")
	public void validateUpdate(Model model,String query) throws Exception {
		ActionContext.initialize(request, response);
		FormData formztbooks0001 = new FormService().getFormData("ztbooks0002");
		getFormValue(formztbooks0001);
		boolean validateFlag = this.validateFormData(formztbooks0001);
		model.addAttribute("formztbooks0001", formztbooks0001);
		model.addAttribute("query", query);
	}

	/**
	 * 
	 * 方法描述： 删除帐套，修改删除的状态
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author lzshuai
	 * @date 2017-7-19 下午5:06:06
	 */
	@RequestMapping(value = "/deleteUpdateBooksAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteUpdateBooksAjax(String bookCode) throws Exception {

		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		CwZtBooks cwZtBooks = new CwZtBooks();
		cwZtBooks.setBookCode(bookCode);
		cwZtBooks.setDelSts("Y");
		try {
			if (StringUtil.isEmpty(bookCode)) {
				dataMap.put("flag", "error");
				dataMap.put("msg", MessageEnum.FAILED_SAVE.getMessage());
			} else {
				cwZtBooks.setUseOpNo(null);
				cwZtBooksFeign.update(cwZtBooks);
				dataMap.put("flag", "success");
				// dataMap.put("msg", "成功");
				dataMap.put("msg", MessageEnum.SUCCEED_SAVE.getMessage());
			}
		} catch (Exception e) {
			// System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			// dataMap.put("msg", "失败");
			dataMap.put("msg", MessageEnum.FAILED_SAVE.getMessage());
			throw new Exception(e.getMessage());
		}
		return dataMap;
	}

	/**
	 * 控制账套是否显示 lzs
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getztShowAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getztShowAjax() throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			String result = cwZtBooksFeign.getztShowOrHide();
			if ("0000".equals(result)) {
				dataMap.put("flag", "success");
			} else {
				dataMap.put("flag", "error");
			}
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_OPERATION.getMessage());
			throw new Exception(e.getMessage());
		}
		return dataMap;
	}

	/**
	 * 
	 * 方法描述： 复制功能
	 * 
	 * @return String
	 * @author lzshuai
	 * @date 2017-8-7 下午3:15:31
	 */
	@RequestMapping(value = "/copyAddInput")
	public String copyAddInput(Model model, String bookCode) throws Exception {
		ActionContext.initialize(request, response);

		try {
			FormData formztbooks0001 = new FormService().getFormData("ztbooks0003");

			// formztbooks0001 = formService.getFormData("ztbooks0001");
			getFormValue(formztbooks0001);
			CwZtBooks cwZtBooks = new CwZtBooks();
			cwZtBooks.setBookCode(bookCode);
			cwZtBooks = cwZtBooksFeign.getCopyZtById(cwZtBooks);

			getObjValue(formztbooks0001, cwZtBooks);

			Map<String, Object> dataMap = new HashMap<String, Object>();
			// dataMap.put("bookCode", bookCode);

			JSONObject json = new JSONObject();
			List<SysUser> suList = cwZtBooksFeign.getAllUserList(new SysUser());
			JSONArray userArray = JSONArray.fromObject(suList);
			for (int i = 0; i < userArray.size(); i++) {
				userArray.getJSONObject(i).put("id", userArray.getJSONObject(i).getString("opNo"));
				userArray.getJSONObject(i).put("name", userArray.getJSONObject(i).getString("opName"));
			}
			json.put("userArray", userArray);
			Ipage ipage = this.getIpage();
			ipage = mfBusInstitutionsFeign.getAgenciesDataSourse(ipage);
			List<MfBusInstitutions> brList = (List<MfBusInstitutions>) ipage.getResult();
			JSONArray brArray = JSONArray.fromObject(brList);
			for (int i = 0; i < brArray.size(); i++) {
				brArray.getJSONObject(i).put("id", brArray.getJSONObject(i).getString("agenciesId"));
				brArray.getJSONObject(i).put("name", brArray.getJSONObject(i).getString("agenciesName"));
			}
            json.put("brArray", brArray);
			String ajaxData = json.toString();
			model.addAttribute("cwZtBooks", cwZtBooks);
			model.addAttribute("formztbooks0001", formztbooks0001);
			model.addAttribute("suList", suList);
			model.addAttribute("ajaxData", ajaxData);
			model.addAttribute("query", "");
		} catch (Exception e) {
			throw e;
		}

		return "/component/finance/ztbooks/CwZtBooks_copyAddInsert";
	}

	/**
	 * 
	 * 方法描述： 复制账套的功能
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author lzshuai
	 * @date 2017-8-7 下午2:52:06
	 */
	@RequestMapping(value = "/copyAddBooksAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> copyAddBooksAjax(String ajaxData) throws Exception {

		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formztbooks0001 = new FormService().getFormData("ztbooks0003");
			getFormValue(formztbooks0001, getMapByJson(ajaxData));
			if (this.validateFormData(formztbooks0001)) {
				CwZtBooks cwZtBooks = new CwZtBooks();
				setObjValue(formztbooks0001, cwZtBooks);
				String finBooks = (String)request.getSession().getAttribute("finBooks");
				R r = cwZtBooksFeign.doCopyAddBooks(cwZtBooks,finBooks);
				if(r.isOk()) {
					dataMap.put("flag", "success");
					dataMap.put("msg", MessageEnum.SUCCEED_SAVE.getMessage());
				}else {
					dataMap.put("flag", "error");
					dataMap.put("msg", r.getMsg());
				}
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", MessageEnum.FAILED_SAVE.getMessage());
			}
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_SAVE.getMessage());
			e.printStackTrace();
			throw e;
		}
		return dataMap;

	}

}
