package app.component.pss.information.controller;

import java.util.ArrayList;
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

import app.component.pss.information.entity.PssStorageWarning;
import app.component.pss.information.entity.PssStorehouse;
import app.component.pss.information.feign.PssStorageWarningFeign;
import app.component.pss.information.feign.PssStorehouseFeign;
import app.component.pss.utils.PssEnumBean;
import app.component.pss.utils.PssPublicUtil;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.WaterIdUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Title: PssStorageWarningAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Wed Aug 30 10:07:13 CST 2017
 **/
@Controller
@RequestMapping("/pssStorageWarning")
public class PssStorageWarningController extends BaseFormBean {
	// 注入业务层
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private PssStorageWarningFeign pssStorageWarningFeign;
	@Autowired
	private PssStorehouseFeign pssStorehouseFeign;
	// 全局变量
	// 表单变量

	/**
	 * 获取列表数据
	 * 
	 * @return
	 * @throws Exception
	 */
	private void getTableData(String tableId, PssStorageWarning pssStorageWarning) throws Exception {
		JsonTableUtil jtu = new JsonTableUtil();
		String tableHtml = jtu.getJsonStr(tableId, "tableTag", pssStorageWarningFeign.getAll(pssStorageWarning), null,
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
	public String getListPage(Model model, String goodsId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssStorageWarning pssStorageWarning = new PssStorageWarning();
		pssStorageWarning.setGoodsId(goodsId);
		Ipage ipage = this.getIpage();
		ipage.setParams(this.setIpageParams("pssStorageWarning", pssStorageWarning));
		dataMap.put("goodsId", goodsId);
		List<PssStorageWarning> pssStorageWarningList = (List<PssStorageWarning>) pssStorageWarningFeign
				.findByPage(ipage).getResult();
		JSONObject json = new JSONObject();
		// 仓库选择组件
		PssStorehouse pssStorehouse = new PssStorehouse();
		pssStorehouse.setFlag(PssEnumBean.YES_OR_NO.YES.getNum());
		List<PssStorehouse> storehouseList = pssStorehouseFeign.getAll(pssStorehouse);
		JSONArray storehouseArray = JSONArray.fromObject(storehouseList);
		for (int i = 0; i < storehouseArray.size(); i++) {
			storehouseArray.getJSONObject(i).put("id", storehouseArray.getJSONObject(i).getString("storehouseId"));
			storehouseArray.getJSONObject(i).put("name", storehouseArray.getJSONObject(i).getString("storehouseName"));
		}
		json.put("storehouse", storehouseArray);
		String ajaxData = json.toString();
		model.addAttribute("ajaxData", ajaxData);
		model.addAttribute("query", "");
		return "/component/pss/information/PssStorageWarning_List";
	}

	/**
	 * 列表有翻页
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPageAjax")
	@ResponseBody
	public Map<String, Object> getListPageAjax(String goodsId,Integer pageSize,Integer pageNo,String tableId,String tableType) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssStorageWarning pssStorageWarning = new PssStorageWarning();
		pssStorageWarning.setGoodsId(goodsId);
		dataMap.put("goodsId", goodsId);
		try {
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setParams(this.setIpageParams("pssStorageWarning", pssStorageWarning));
			// 自定义查询Bo方法
			@SuppressWarnings("unchecked")
			List<PssStorageWarning> pssWarnlList = (List<PssStorageWarning>) pssStorageWarningFeign
					.findByPage(ipage).getResult();
			if (pssWarnlList == null || pssWarnlList.size() == 0) {
				ArrayList<PssStorageWarning> pssWarnlList1 = new ArrayList<PssStorageWarning>();
			}
			for (int i = 0; i < 5; i++) {
				pssStorageWarning = new PssStorageWarning();
				pssStorageWarning.setSequence(pssWarnlList.size() + 1);
				pssWarnlList.add(pssStorageWarning);
			}

			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, pssWarnlList, ipage, true);
			ipage.setResult(tableHtml);
			ipage.setPageCounts(pssWarnlList.size());
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			e.printStackTrace();
			//Log.error("库存预警列表数据出错", e);
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
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
		FormData formstorewarn0002 = formService.getFormData("storewarn0002");
		PssStorageWarning pssStorageWarning = new PssStorageWarning();
		List<PssStorageWarning> pssStorageWarningList = pssStorageWarningFeign.getAll(pssStorageWarning);
		model.addAttribute("formstorewarn0002", formstorewarn0002);
		model.addAttribute("pssStorageWarningList", pssStorageWarningList);
		model.addAttribute("query", "");
		return "/component/pss/information/PssStorageWarning_List";
	}

	/**
	 * AJAX新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveWarnAjax")
	@ResponseBody
	public Map<String, Object> saveWarnAjax(String goodsId,String jsonArr) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("goodsId", goodsId);
		try {
			List<PssStorageWarning> tableList = PssPublicUtil.getMapByJsonObj(new PssStorageWarning(), jsonArr);
			// 分录信息校验
			tableList = PssPublicUtil.filterSepList(tableList, "getStorehouseId");
			if (null == tableList || tableList.isEmpty()) {
				// 分录信息为空
				dataMap.put("flag", "error");
				dataMap.put("msg", MessageEnum.NOT_FORM_EMPTY.getMessage("分录信息"));
				return dataMap;
			} else {
				PssStorageWarning pssStorageWarning = new PssStorageWarning();
				pssStorageWarning.setGoodsId(goodsId);
				pssStorageWarningFeign.deleteByGoodsId(pssStorageWarning);
				for (PssStorageWarning pw : tableList) {
					pw.setWarningId(WaterIdUtil.getWaterId());
					pw.setGoodsId(goodsId);
					pssStorageWarningFeign.insert(pw);
				}
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
			FormData formstorewarn0002 = formService.getFormData("storewarn0002");
			getFormValue(formstorewarn0002, getMapByJson(ajaxData));
			if (this.validateFormData(formstorewarn0002)) {
				PssStorageWarning pssStorageWarning = new PssStorageWarning();
				setObjValue(formstorewarn0002, pssStorageWarning);
				pssStorageWarningFeign.insert(pssStorageWarning);
				getTableData(tableId,pssStorageWarning);// 获取列表
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
	public Map<String, Object> updateAjax(String ajaxData,String tableId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssStorageWarning pssStorageWarning = new PssStorageWarning();
		try {
			FormData formstorewarn0002 = formService.getFormData("storewarn0002");
			getFormValue(formstorewarn0002, getMapByJson(ajaxData));
			if (this.validateFormData(formstorewarn0002)) {
				pssStorageWarning = new PssStorageWarning();
				setObjValue(formstorewarn0002, pssStorageWarning);
				pssStorageWarningFeign.update(pssStorageWarning);
				getTableData(tableId,pssStorageWarning);// 获取列表
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
	public Map<String, Object> getByIdAjax(String warningId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formstorewarn0002 = formService.getFormData("storewarn0002");
		PssStorageWarning pssStorageWarning = new PssStorageWarning();
		pssStorageWarning.setWarningId(warningId);
		pssStorageWarning = pssStorageWarningFeign.getById(pssStorageWarning);
		getObjValue(formstorewarn0002, pssStorageWarning, formData);
		if (pssStorageWarning != null) {
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
	public Map<String, Object> deleteAjax(String warningId,String ajaxData,String tableId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssStorageWarning pssStorageWarning = new PssStorageWarning();
		pssStorageWarning.setWarningId(warningId);
		try {
			JSONObject jb = JSONObject.fromObject(ajaxData);
			pssStorageWarning = (PssStorageWarning) JSONObject.toBean(jb, PssStorageWarning.class);
			pssStorageWarningFeign.delete(pssStorageWarning);
			getTableData(tableId,pssStorageWarning);// 获取列表
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

}
