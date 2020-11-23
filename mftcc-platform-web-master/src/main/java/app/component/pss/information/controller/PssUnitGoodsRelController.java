package app.component.pss.information.controller;

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

import app.component.pss.information.entity.PssUnitGoodsRel;
import app.component.pss.information.feign.PssUnitGoodsRelFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import net.sf.json.JSONObject;

/**
 * Title: PssUnitGoodsRelAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Tue Dec 12 17:08:28 CST 2017
 **/
@Controller
@RequestMapping("/pssUnitGoodsRel")
public class PssUnitGoodsRelController extends BaseFormBean {
	// 注入业务层
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private PssUnitGoodsRelFeign pssUnitGoodsRelFeign;
	// 全局变量
	// 表单变量

	/**
	 * 获取列表数据
	 * 
	 * @return
	 * @throws Exception
	 */
	private void getTableData(String tableId, PssUnitGoodsRel pssUnitGoodsRel) throws Exception {
		JsonTableUtil jtu = new JsonTableUtil();
		String tableHtml = jtu.getJsonStr(tableId, "tableTag", pssUnitGoodsRelFeign.getAll(pssUnitGoodsRel), null,
				true);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("tableData", tableHtml);
	}

	/**
	 * 列表有翻页
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formpssugr02 = formService.getFormData("pssugr02");
		PssUnitGoodsRel pssUnitGoodsRel = new PssUnitGoodsRel();
		Ipage ipage = this.getIpage();
		ipage.setParams(this.setIpageParams("pssUnitGoodsRel", pssUnitGoodsRel));
		List<PssUnitGoodsRel> pssUnitGoodsRelList = (List<PssUnitGoodsRel>) pssUnitGoodsRelFeign.findByPage(ipage)
				.getResult();
		model.addAttribute("formpssugr02", formpssugr02);
		model.addAttribute("pssUnitGoodsRelList", pssUnitGoodsRelList);
		model.addAttribute("query", "");
		return "/component/pss/information/PssUnitGoodsRel_List";
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
		FormData formpssugr02 = formService.getFormData("pssugr02");
		PssUnitGoodsRel pssUnitGoodsRel = new PssUnitGoodsRel();
		List<PssUnitGoodsRel> pssUnitGoodsRelList = pssUnitGoodsRelFeign.getAll(pssUnitGoodsRel);
		model.addAttribute("formpssugr02", formpssugr02);
		model.addAttribute("pssUnitGoodsRelList", pssUnitGoodsRelList);
		model.addAttribute("query", "");
		return "/component/pss/information/PssUnitGoodsRel_List";
	}

	/**
	 * AJAX新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData, String tableId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formpssugr02 = formService.getFormData("pssugr02");
			getFormValue(formpssugr02, getMapByJson(ajaxData));
			if (this.validateFormData(formpssugr02)) {
				PssUnitGoodsRel pssUnitGoodsRel = new PssUnitGoodsRel();
				setObjValue(formpssugr02, pssUnitGoodsRel);
				pssUnitGoodsRelFeign.insert(pssUnitGoodsRel);
				getTableData(tableId, pssUnitGoodsRel);// 获取列表
				dataMap.put("flag", "success");
				dataMap.put("msg", "新增成功");
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "新增失败");
			throw new Exception(e.getMessage());
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
		PssUnitGoodsRel pssUnitGoodsRel = new PssUnitGoodsRel();
		try {
			FormData formpssugr02 = formService.getFormData("pssugr02");
			getFormValue(formpssugr02, getMapByJson(ajaxData));
			if (this.validateFormData(formpssugr02)) {
				pssUnitGoodsRel = new PssUnitGoodsRel();
				setObjValue(formpssugr02, pssUnitGoodsRel);
				pssUnitGoodsRelFeign.update(pssUnitGoodsRel);
				getTableData(tableId, pssUnitGoodsRel);// 获取列表
				dataMap.put("flag", "success");
				dataMap.put("msg", "更新成功");
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "更新失败");
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
	@RequestMapping(value = "/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String ugrId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formpssugr02 = formService.getFormData("pssugr02");
		PssUnitGoodsRel pssUnitGoodsRel = new PssUnitGoodsRel();
		pssUnitGoodsRel.setUgrId(ugrId);
		pssUnitGoodsRel = pssUnitGoodsRelFeign.getById(pssUnitGoodsRel);
		getObjValue(formpssugr02, pssUnitGoodsRel, formData);
		if (pssUnitGoodsRel != null) {
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
	public Map<String, Object> deleteAjax(String ugrId, String ajaxData, String tableId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssUnitGoodsRel pssUnitGoodsRel = new PssUnitGoodsRel();
		pssUnitGoodsRel.setUgrId(ugrId);
		try {
			JSONObject jb = JSONObject.fromObject(ajaxData);
			pssUnitGoodsRel = (PssUnitGoodsRel) JSONObject.toBean(jb, PssUnitGoodsRel.class);
			pssUnitGoodsRelFeign.deleteByUgrId(pssUnitGoodsRel);
			getTableData(tableId, pssUnitGoodsRel);// 获取列表
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
	 * 根据商品主键获取对应单位和价格信息
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getAllByGoodsIdAjax")
	@ResponseBody
	public Map<String, Object> getAllByGoodsIdAjax(String goodsId, List<PssUnitGoodsRel> pssUnitGoodsRelList)
			throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		if (goodsId == null || "".equals(goodsId)) {
			throw new Exception(MessageEnum.NOT_FORM_EMPTY.getMessage("商品ID"));
		}

		PssUnitGoodsRel pssUnitGoodsRel = new PssUnitGoodsRel();
		pssUnitGoodsRel.setGoodsId(goodsId);
		try {
			pssUnitGoodsRelList = pssUnitGoodsRelFeign.getAllByGoodsId(pssUnitGoodsRel);
			Map<String, PssUnitGoodsRel> pssUnitGoodsRelMap = new HashMap<String, PssUnitGoodsRel>();
			if (pssUnitGoodsRelList != null && pssUnitGoodsRelList.size() > 0) {
				for (PssUnitGoodsRel pssUnitGoodsRel1 : pssUnitGoodsRelList) {
					pssUnitGoodsRelMap.put(pssUnitGoodsRel1.getUnitId(), pssUnitGoodsRel1);
				}
			}
			dataMap.put("pssUnitGoodsRelMap", JSONObject.fromObject(pssUnitGoodsRelMap));
			dataMap.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("success", false);
			dataMap.put("msg", e.getMessage());
			throw e;
		}

		return dataMap;
	}
}
