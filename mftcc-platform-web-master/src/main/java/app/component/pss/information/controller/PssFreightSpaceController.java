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

import app.component.pss.information.entity.PssFreightSpace;
import app.component.pss.information.feign.PssFreightSpaceFeign;
import app.component.pss.utils.PssEnumBean;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.StringUtil;
import net.sf.json.JSONObject;

/**
 * Title: PssFreightSpaceAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Wed Jan 10 15:43:42 CST 2018
 **/
@Controller
@RequestMapping("/pssFreightSpace")
public class PssFreightSpaceController extends BaseFormBean {
	// 注入业务层
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private PssFreightSpaceFeign pssFreightSpaceFeign;
	// 全局变量
	// 表单变量

	/**
	 * 获取列表数据
	 * 
	 * @return
	 * @throws Exception
	 */
	private void getTableData(String tableId,PssFreightSpace pssFreightSpace) throws Exception {
		JsonTableUtil jtu = new JsonTableUtil();
		String tableHtml = jtu.getJsonStr(tableId, "tableTag", pssFreightSpaceFeign.getAll(pssFreightSpace), null,
				true);
		Map<String,Object> dataMap=new HashMap<String,Object>();
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
		FormData formdl_pssfreight_space02 = formService.getFormData("dl_pssfreight_space02");
		PssFreightSpace pssFreightSpace = new PssFreightSpace();
		Ipage ipage = this.getIpage();
		ipage.setParams(this.setIpageParams("pssFreightSpace",pssFreightSpace));
		List<PssFreightSpace> pssFreightSpaceList = (List<PssFreightSpace>) pssFreightSpaceFeign.findByPage(ipage)
				.getResult();
		model.addAttribute("formdl_pssfreight_space02", formdl_pssfreight_space02);
		model.addAttribute("pssFreightSpaceList", pssFreightSpaceList);
		model.addAttribute("query", "");
		return "/component/pss/information/PssFreightSpace_List";
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
		FormData formdl_pssfreight_space02 = formService.getFormData("dl_pssfreight_space02");
		PssFreightSpace pssFreightSpace = new PssFreightSpace();
		List<PssFreightSpace> pssFreightSpaceList = pssFreightSpaceFeign.getAll(pssFreightSpace);
		model.addAttribute("formdl_pssfreight_space02", formdl_pssfreight_space02);
		model.addAttribute("pssFreightSpaceList", pssFreightSpaceList);
		model.addAttribute("query", "");
		return "/component/pss/information/PssFreightSpace_List";
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
			FormData formdl_pssfreight_space02 = formService.getFormData("dl_pssfreight_space02");
			getFormValue(formdl_pssfreight_space02, getMapByJson(ajaxData));
			if (this.validateFormData(formdl_pssfreight_space02)) {
				PssFreightSpace pssFreightSpace = new PssFreightSpace();
				setObjValue(formdl_pssfreight_space02, pssFreightSpace);
				pssFreightSpaceFeign.insert(pssFreightSpace);
				getTableData(tableId,pssFreightSpace);// 获取列表
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
		PssFreightSpace pssFreightSpace = new PssFreightSpace();
		try {
			FormData formdl_pssfreight_space02 = formService.getFormData("dl_pssfreight_space02");
			getFormValue(formdl_pssfreight_space02, getMapByJson(ajaxData));
			if (this.validateFormData(formdl_pssfreight_space02)) {
				pssFreightSpace = new PssFreightSpace();
				setObjValue(formdl_pssfreight_space02, pssFreightSpace);
				pssFreightSpaceFeign.update(pssFreightSpace);
				getTableData(tableId,pssFreightSpace);// 获取列表
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
	public Map<String, Object> getByIdAjax(String freightSpaceId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formdl_pssfreight_space02 = formService.getFormData("dl_pssfreight_space02");
		PssFreightSpace pssFreightSpace = new PssFreightSpace();
		pssFreightSpace.setFreightSpaceId(freightSpaceId);
		pssFreightSpace = pssFreightSpaceFeign.getById(pssFreightSpace);
		getObjValue(formdl_pssfreight_space02, pssFreightSpace, formData);
		if (pssFreightSpace != null) {
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
	public Map<String, Object> deleteAjax(String freightSpaceId,String ajaxData,String tableId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssFreightSpace pssFreightSpace = new PssFreightSpace();
		pssFreightSpace.setFreightSpaceId(freightSpaceId);
		try {
			JSONObject jb = JSONObject.fromObject(ajaxData);
			pssFreightSpace = (PssFreightSpace) JSONObject.toBean(jb, PssFreightSpace.class);
			pssFreightSpaceFeign.delete(pssFreightSpace);
			getTableData(tableId,pssFreightSpace);// 获取列表
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

	/***
	 * 列表数据查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
			String storehouseId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssFreightSpace pssFreightSpace = new PssFreightSpace();
		List<PssFreightSpace> pssFreightSpaceList = null;
		try {
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setParams(this.setIpageParams("pssFreightSpace",pssFreightSpace));
			if (storehouseId == null || "".equals(storehouseId)) {
				 pssFreightSpaceList = new ArrayList<PssFreightSpace>();
			} else {
				pssFreightSpace.setStorehouseId(storehouseId);
				// 自定义查询Bo方法
				ipage = pssFreightSpaceFeign.findByPage(ipage);
				pssFreightSpaceList = (List<PssFreightSpace>) ipage.getResult();
			}

			if (pssFreightSpaceList != null && pssFreightSpaceList.size() > 0) {
				if (pssFreightSpaceList.size() < 5) {
					for (int i = pssFreightSpaceList.size() + 1; i <= 5; i++) {
						pssFreightSpace = new PssFreightSpace();
						pssFreightSpace.setSequence(i);
						pssFreightSpaceList.add(pssFreightSpace);
					}
				}
			} else {
				for (int i = 1; i <= 5; i++) {
					pssFreightSpace = new PssFreightSpace();
					pssFreightSpace.setSequence(i);
					pssFreightSpaceList.add(pssFreightSpace);
				}
			}
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, pssFreightSpaceList, ipage, true);
			ipage.setResult(tableHtml);
			ipage.setPageCounts(5);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("success", false);
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * 更新启用状态
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateEnabledStatusAjax")
	@ResponseBody
	public Map<String, Object> updateEnabledStatusAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssFreightSpace pssFreightSpace = new PssFreightSpace();
		try {
			JSONObject jsonObject = JSONObject.fromObject(ajaxData);
			String freightSpaceId = (String) jsonObject.get("freightSpaceId");
			if (StringUtil.isEmpty(freightSpaceId)) {
				throw new Exception(MessageEnum.NOT_FORM_EMPTY.getMessage("主键"));
			}
			pssFreightSpace.setFreightSpaceId(freightSpaceId);
			pssFreightSpace = pssFreightSpaceFeign.getById(pssFreightSpace);
			String enabledStatus = (String) jsonObject.get("enabledStatus");
			;
			if (StringUtil.isNotEmpty(enabledStatus)) {
				if (PssEnumBean.ENABLED_STATUS.CLOSED.getValue().equals(enabledStatus)) {
					// 关闭
					pssFreightSpaceFeign.doCloseFreightSpace(pssFreightSpace);
				} else if (PssEnumBean.ENABLED_STATUS.ENABLED.getValue().equals(enabledStatus)) {
					// 启用
					pssFreightSpaceFeign.doEnableFreightSpace(pssFreightSpace);
				} else {
					throw new Exception(MessageEnum.FAILED_SAVE_CONTENT.getMessage("启用状态不正确"));
				}
			} else {
				throw new Exception(MessageEnum.NOT_FORM_EMPTY.getMessage("启用状态"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("success", false);
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * 根据仓库主键获取对应仓位信息
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getAllByStorehouseIdAjax")
	@ResponseBody
	public Map<String, Object> getAllByStorehouseIdAjax(String storehouseId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		if (storehouseId == null || "".equals(storehouseId)) {
			throw new Exception(MessageEnum.NOT_FORM_EMPTY.getMessage("仓库ID"));
		}

		PssFreightSpace pssFreightSpace = new PssFreightSpace();
		pssFreightSpace.setStorehouseId(storehouseId);
		try {
			List<PssFreightSpace> pssFreightSpaceList = pssFreightSpaceFeign.getAll(pssFreightSpace);
			Map<String, PssFreightSpace> pssFreightSpaceMap = new HashMap<String, PssFreightSpace>();
			if (pssFreightSpaceList != null && pssFreightSpaceList.size() > 0) {
				for (PssFreightSpace pssFreightSpace1 : pssFreightSpaceList) {
					pssFreightSpaceMap.put(pssFreightSpace1.getFreightSpaceId(), pssFreightSpace1);
				}
			}
			dataMap.put("pssFreightSpaceMap", JSONObject.fromObject(pssFreightSpaceMap));
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
