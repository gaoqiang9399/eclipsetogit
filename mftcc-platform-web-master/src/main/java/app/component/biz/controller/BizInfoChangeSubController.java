package app.component.biz.controller;

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

import app.component.biz.entity.BizInfoChangeSub;
import app.component.biz.feign.BizInfoChangeSubFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import net.sf.json.JSONObject;

/**
 * Title: BizInfoChangeSubAction.java Description:
 * 
 * @author:@dhcc.com.cn
 * @Wed Apr 20 06:09:29 GMT 2016
 **/
@Controller
@RequestMapping("/bizInfoChangeSub")
public class BizInfoChangeSubController extends BaseFormBean {
	// 注入业务层
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private BizInfoChangeSubFeign bizInfoChangeSubFeign;

	/**
	 * 获取列表数据
	 * 
	 * @return
	 * @throws Exception
	 */
	private void getTableData(Map<String, Object> dataMap, String tableId, BizInfoChangeSub bizInfoChangeSub)
			throws Exception {
		JsonTableUtil jtu = new JsonTableUtil();
		String tableHtml = jtu.getJsonStr(tableId, "tableTag", bizInfoChangeSubFeign.getAll(bizInfoChangeSub), null,
				true);
		dataMap.put("tableData", tableHtml);
	}

	/**
	 * 列表有翻页
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formbiz0002 = formService.getFormData("biz0002");
		BizInfoChangeSub bizInfoChangeSub = new BizInfoChangeSub();
		Ipage ipage = this.getIpage();
		List<BizInfoChangeSub> bizInfoChangeSubList = (List<BizInfoChangeSub>) bizInfoChangeSubFeign
				.findByPage(ipage, bizInfoChangeSub).getResult();
		model.addAttribute("bizInfoChangeSubList", bizInfoChangeSubList);
		model.addAttribute("formbiz0002", formbiz0002);
		model.addAttribute("query", "");
		return "/component/biz/BizInfoChangeSub_List";
	}

	/**
	 * 列表全部无翻页
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListAll")
	public String getListAll(Model model, String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formbiz0002 = formService.getFormData("biz0002");
		BizInfoChangeSub bizInfoChangeSub = new BizInfoChangeSub();
		List<BizInfoChangeSub> bizInfoChangeSubList = bizInfoChangeSubFeign.getAll(bizInfoChangeSub);
		model.addAttribute("bizInfoChangeSubList", bizInfoChangeSubList);
		model.addAttribute("formbiz0002", formbiz0002);
		model.addAttribute("query", "");
		return "/component/biz/BizInfoChangeSub_List";
	}

	/**
	 * AJAX新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListAjax")
	@ResponseBody
	public Map<String, Object> getListAjax(String changeNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			BizInfoChangeSub bizInfoChangeSub = new BizInfoChangeSub();
			bizInfoChangeSub.setChangeNo(changeNo);
			List<BizInfoChangeSub> bizInfoChangeSubList = bizInfoChangeSubFeign.getAll(bizInfoChangeSub);

			dataMap.put("bizInfoChangeSubList", bizInfoChangeSubList);
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
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
	@RequestMapping(value = "/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData,String tableId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formbiz0002 = formService.getFormData("biz0002");
			getFormValue(formbiz0002, getMapByJson(ajaxData));
			if (this.validateFormData(formbiz0002)) {
				BizInfoChangeSub bizInfoChangeSub = new BizInfoChangeSub();
				setObjValue(formbiz0002, bizInfoChangeSub);
				bizInfoChangeSubFeign.insert(bizInfoChangeSub);
				getTableData(dataMap, tableId, bizInfoChangeSub);// 获取列表
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
	 * AJAX更新保存
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateAjax")
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData, String tableId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formbiz0002 = formService.getFormData("biz0002");
			getFormValue(formbiz0002, getMapByJson(ajaxData));
			if (this.validateFormData(formbiz0002)) {
				BizInfoChangeSub bizInfoChangeSub = new BizInfoChangeSub();
				setObjValue(formbiz0002, bizInfoChangeSub);
				bizInfoChangeSubFeign.update(bizInfoChangeSub);
				getTableData(dataMap, tableId, bizInfoChangeSub);// 获取列表
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
	@RequestMapping(value = "/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String id) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formbiz0002 = formService.getFormData("biz0002");
		BizInfoChangeSub bizInfoChangeSub = new BizInfoChangeSub();
		bizInfoChangeSub.setId(id);
		bizInfoChangeSub = bizInfoChangeSubFeign.getById(bizInfoChangeSub);
		getObjValue(formbiz0002, bizInfoChangeSub, formData);
		if (bizInfoChangeSub != null) {
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
	public Map<String, Object> deleteAjax(String ajaxData, String tableId, String id) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		BizInfoChangeSub bizInfoChangeSub = new BizInfoChangeSub();
		bizInfoChangeSub.setId(id);
		try {
			JSONObject jb = JSONObject.fromObject(ajaxData);
			bizInfoChangeSub = (BizInfoChangeSub) JSONObject.toBean(jb, BizInfoChangeSub.class);
			bizInfoChangeSubFeign.delete(bizInfoChangeSub);
			getTableData(dataMap, tableId, bizInfoChangeSub);// 获取列表
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR.getMessage());
			throw e;
		}
		return dataMap;
	}

}
