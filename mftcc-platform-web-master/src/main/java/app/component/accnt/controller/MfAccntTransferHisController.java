package app.component.accnt.controller;

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

import app.component.accnt.entity.MfAccntTransferHis;
import app.component.accnt.feign.MfAccntTransferHisFeign;
import app.component.common.EntityUtil;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;

/**
 * Title: MfAccntTransferHisAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Fri May 27 09:20:34 CST 2016
 **/
@Controller
@RequestMapping(value = "/mfAccntTransferHis")
public class MfAccntTransferHisController extends BaseFormBean {
	// 注入MfAccntTransferHisBo
	@Autowired
	private MfAccntTransferHisFeign mfAccntTransferHisFeign;

	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;

	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage() throws Exception {
		ActionContext.initialize(request, response);
		return "/component/accnt/MfAccntTransferHis_List";
	}

	/***
	 * 列表数据查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/findByPageAjax")
	public Map<String, Object> findByPageAjax(String ajaxData, String tableId, String tableType, int pageNo) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfAccntTransferHis mfAccntTransferHis = new MfAccntTransferHis();
		try {
			mfAccntTransferHis.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfAccntTransferHis.setCriteriaList(mfAccntTransferHis, ajaxData);// 我的筛选
			// this.getRoleConditions(mfAccntTransferHis,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage = mfAccntTransferHisFeign.findByPage(ipage, mfAccntTransferHis);
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
	@RequestMapping(value = "/insertAjax")
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formaccnttranshis0002 = formService.getFormData("accnttranshis0002");
			getFormValue(formaccnttranshis0002, getMapByJson(ajaxData));
			if (this.validateFormData(formaccnttranshis0002)) {
				MfAccntTransferHis mfAccntTransferHis = new MfAccntTransferHis();
				setObjValue(formaccnttranshis0002, mfAccntTransferHis);
				mfAccntTransferHisFeign.insert(mfAccntTransferHis);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
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
	@RequestMapping(value = "/updateAjaxByOne")
	public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formaccnttranshis0002 = formService.getFormData("accnttranshis0002");
		getFormValue(formaccnttranshis0002, getMapByJson(ajaxData));
		MfAccntTransferHis mfAccntTransferHisJsp = new MfAccntTransferHis();
		setObjValue(formaccnttranshis0002, mfAccntTransferHisJsp);
		MfAccntTransferHis mfAccntTransferHis = mfAccntTransferHisFeign.getById(mfAccntTransferHisJsp);
		if (mfAccntTransferHis != null) {
			try {
				mfAccntTransferHis = (MfAccntTransferHis) EntityUtil.reflectionSetVal(mfAccntTransferHis,
						mfAccntTransferHisJsp, getMapByJson(ajaxData));
				mfAccntTransferHisFeign.update(mfAccntTransferHis);
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
	@RequestMapping(value = "/updateAjax")
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfAccntTransferHis mfAccntTransferHis = new MfAccntTransferHis();
		try {
			FormData formaccnttranshis0002 = formService.getFormData("accnttranshis0002");
			getFormValue(formaccnttranshis0002, getMapByJson(ajaxData));
			if (this.validateFormData(formaccnttranshis0002)) {
				mfAccntTransferHis = new MfAccntTransferHis();
				setObjValue(formaccnttranshis0002, mfAccntTransferHis);
				mfAccntTransferHisFeign.update(mfAccntTransferHis);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_UPDATE.getMessage());
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
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
	@RequestMapping(value = "/getByIdAjax")
	public Map<String, Object> getByIdAjax(String id) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formaccnttranshis0002 = formService.getFormData("accnttranshis0002");
		MfAccntTransferHis mfAccntTransferHis = new MfAccntTransferHis();
		mfAccntTransferHis.setId(id);
		mfAccntTransferHis = mfAccntTransferHisFeign.getById(mfAccntTransferHis);
		getObjValue(formaccnttranshis0002, mfAccntTransferHis, formData);
		if (mfAccntTransferHis != null) {
			dataMap.put("flag", "success");
		} else {
			dataMap.put("flag", "error");
		}
		dataMap.put("formData", formData);
		return dataMap;
	}

	/**
	 * Ajax异步删除
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteAjax")
	public Map<String, Object> deleteAjax(String id) throws Exception{
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfAccntTransferHis mfAccntTransferHis = new MfAccntTransferHis();
		mfAccntTransferHis.setId(id);
		try {
			mfAccntTransferHisFeign.delete(mfAccntTransferHis);
			dataMap.put("flag", "success");
			dataMap.put("msg",MessageEnum.SUCCEED.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.ERROR.getMessage());
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
	@RequestMapping(value = "/input")
	public String input(Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formaccnttranshis0002 = formService.getFormData("accnttranshis0002");
		model.addAttribute("formaccnttranshis0002", formaccnttranshis0002);
		return "/component/accnt/MfAccntTransferHis_Insert";
	}

	/***
	 * 新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insert")
	public String insert(Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formaccnttranshis0002 = formService.getFormData("accnttranshis0002");
		getFormValue(formaccnttranshis0002);
		MfAccntTransferHis mfAccntTransferHis = new MfAccntTransferHis();
		setObjValue(formaccnttranshis0002, mfAccntTransferHis);
		mfAccntTransferHisFeign.insert(mfAccntTransferHis);
		getObjValue(formaccnttranshis0002, mfAccntTransferHis);
		this.addActionMessage(model,"保存成功");
		List<MfAccntTransferHis> mfAccntTransferHisList = (List<MfAccntTransferHis>) mfAccntTransferHisFeign
				.findByPage(this.getIpage(), mfAccntTransferHis).getResult();
		model.addAttribute("formaccnttranshis0002", formaccnttranshis0002);
		model.addAttribute("mfAccntTransferHisList", mfAccntTransferHisList);
		return "/component/accnt/MfAccntTransferHis_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model,String id) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formaccnttranshis0001 = formService.getFormData("accnttranshis0001");
		getFormValue(formaccnttranshis0001);
		MfAccntTransferHis mfAccntTransferHis = new MfAccntTransferHis();
		mfAccntTransferHis.setId(id);
		mfAccntTransferHis = mfAccntTransferHisFeign.getById(mfAccntTransferHis);
		getObjValue(formaccnttranshis0001, mfAccntTransferHis);
		model.addAttribute("formaccnttranshis0001", formaccnttranshis0001);
		return "/component/accnt/MfAccntTransferHis_Detail";
	}

	/**
	 * 删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(String id) throws Exception {
		ActionContext.initialize(request, response);
		MfAccntTransferHis mfAccntTransferHis = new MfAccntTransferHis();
		mfAccntTransferHis.setId(id);
		mfAccntTransferHisFeign.delete(mfAccntTransferHis);
		return getListPage();
	}

	/**
	 * 新增校验
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateInsert")
	public void validateInsert() throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formaccnttranshis0002 = formService.getFormData("accnttranshis0002");
		getFormValue(formaccnttranshis0002);
		boolean validateFlag = this.validateFormData(formaccnttranshis0002);
	}

	/**
	 * 修改校验
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateUpdate")
	public void validateUpdate() throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formaccnttranshis0002 = formService.getFormData("accnttranshis0002");
		getFormValue(formaccnttranshis0002);
		boolean validateFlag = this.validateFormData(formaccnttranshis0002);
	}

}
