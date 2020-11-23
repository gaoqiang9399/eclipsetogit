package app.component.risk.controller;

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

import app.component.nmd.entity.ParmDic;
import app.component.nmdinterface.NmdInterfaceFeign;
import app.component.risk.entity.RiskPreventSceGen;
import app.component.risk.feign.RiskPreventSceGenFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.StringUtil;
import net.sf.json.JSONObject;

/**
 * Title: RiskPreventSceGenAction.java Description:
 * 
 * @author:@dhcc.com.cn
 * @Thu Mar 03 07:15:46 GMT 2016
 **/
@Controller
@RequestMapping("/riskPreventSceGen")
public class RiskPreventSceGenController extends BaseFormBean {
	// 注入业务层
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private RiskPreventSceGenFeign riskPreventSceGenFeign;
	@Autowired
	private NmdInterfaceFeign nmdInterfaceFeign;

	// 全局变量
	/***
	 * 列表数据查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
			String ajaxData, String pageStr) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		RiskPreventSceGen riskPreventSceGen = new RiskPreventSceGen();
		try {
			riskPreventSceGen.setCustomQuery(ajaxData);// 自定义查询参数赋值
			riskPreventSceGen.setCriteriaList(riskPreventSceGen, ajaxData);// 我的筛选
			riskPreventSceGen.setBusType(pageStr);
			// this.getRoleConditions(riskItem,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage = riskPreventSceGenFeign.findByPage(ipage, riskPreventSceGen);
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
	 * 新增页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/input")
	public String input(Model model, RiskPreventSceGen riskPreventSceGen) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formrisk0020 = formService.getFormData("risk0020");
		getObjValue(formrisk0020, riskPreventSceGen);
		model.addAttribute("formrisk0020", formrisk0020);
		model.addAttribute("query", "");
		return "/component/risk/RiskPreventSceGen_Insert";
	}

	

	/**
	 * 列表有翻页
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model, String pageStr) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formrisk0008 = formService.getFormData("risk0008");
		RiskPreventSceGen riskPreventSceGen = new RiskPreventSceGen();
		Ipage ipage = this.getIpage();
		riskPreventSceGen.setBusType(pageStr);
		List<RiskPreventSceGen> riskPreventSceGenList = (List<RiskPreventSceGen>) riskPreventSceGenFeign.findByPage(ipage, riskPreventSceGen)
				.getResult();
		model.addAttribute("riskPreventSceGenList", riskPreventSceGenList);
		model.addAttribute("formrisk0008", formrisk0008);
		model.addAttribute("query", "");
		return "/component/risk/RiskPreventSceGen_List";
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
		FormData formrisk0008 = formService.getFormData("risk0008");
		RiskPreventSceGen riskPreventSceGen = new RiskPreventSceGen();
		List<RiskPreventSceGen> riskPreventSceGenList = riskPreventSceGenFeign.getAll(riskPreventSceGen);
		model.addAttribute("riskPreventSceGenList", riskPreventSceGenList);
		model.addAttribute("formrisk0008", formrisk0008);
		model.addAttribute("query", "");
		return "/component/risk/RiskPreventSceGen_List";
	}

	/**
	 * 
	 * 方法描述： 启用禁用
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author zhs
	 * @date 2016-10-28 下午4:46:54
	 */
	@RequestMapping(value = "/updateStsAjax")
	@ResponseBody
	public Map<String, Object> updateStsAjax(String ajaxData, String tableId) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			RiskPreventSceGen riskPreventSceGen = new RiskPreventSceGen();
			JSONObject jb = JSONObject.fromObject(ajaxData);
			riskPreventSceGen = (RiskPreventSceGen) JSONObject.toBean(jb, RiskPreventSceGen.class);
			riskPreventSceGenFeign.update(riskPreventSceGen);
			riskPreventSceGen = riskPreventSceGenFeign.getById(riskPreventSceGen);
			List<RiskPreventSceGen> list = new ArrayList<RiskPreventSceGen>();
			list.add(riskPreventSceGen);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, "thirdTableTag", list, null, true);
			dataMap.put("tableData", tableHtml);
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED_UPDATE.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_UPDATE.getMessage());
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
	public Map<String, Object> insertAjax(String dimeVal, String genName, String rulesNo, String dimeValDes,
			String pageStr) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			if (riskPreventSceGenFeign.doCheckExist(dimeVal)) {
				dataMap.put("flag", "exist");
			} else {
				RiskPreventSceGen riskPreventSceGen = new RiskPreventSceGen();
				riskPreventSceGen.setGenName(genName);
				riskPreventSceGen.setDimeVal(dimeVal);
				riskPreventSceGen.setRulesNo(rulesNo);
				riskPreventSceGen.setDimeValDes(dimeValDes);
				riskPreventSceGen.setBusType(pageStr);
				riskPreventSceGenFeign.insert(riskPreventSceGen);
				// getTableData();//获取列表
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
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
	public Map<String, Object> updateAjax(RiskPreventSceGen riskPreventSceGen) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			if (StringUtil.isEmpty(riskPreventSceGen.getGenNo())) {
				dataMap.put("flag", "error");
				dataMap.put("msg", MessageEnum.NOT_FORM_EMPTY.getMessage("主键"));
			} else {
				if (riskPreventSceGenFeign.doCheckExist(riskPreventSceGen.getDimeVal())) {
					dataMap.put("flag", "exist");
				} else {
					riskPreventSceGenFeign.update(riskPreventSceGen);
					// getTableData();//获取列表
					dataMap.put("flag", "success");
					dataMap.put("msg", MessageEnum.SUCCEED_UPDATE.getMessage());
				}

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
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String genNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formrisk0020 = formService.getFormData("risk0020");
		getFormValue(formrisk0020);
		RiskPreventSceGen riskPreventSceGen = new RiskPreventSceGen();
		riskPreventSceGen.setGenNo(genNo);
		riskPreventSceGen = riskPreventSceGenFeign.getById(riskPreventSceGen);
		getObjValue(formrisk0020, riskPreventSceGen);
		// kindNo = riskPreventSceGen.getKindNo();
		model.addAttribute("kindNo", riskPreventSceGen.getKindNo());
		model.addAttribute("riskPreventSceGen", riskPreventSceGen);
		model.addAttribute("formrisk0020", formrisk0020);
		model.addAttribute("query", "");
		return "/component/risk/RiskPreventSceGen_Detail";
	}

	/**
	 * AJAX获取查看
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String genNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formrisk0008 = formService.getFormData("risk0008");
		RiskPreventSceGen riskPreventSceGen = new RiskPreventSceGen();
		riskPreventSceGen.setGenNo(genNo);
		riskPreventSceGen = riskPreventSceGenFeign.getById(riskPreventSceGen);
		getObjValue(formrisk0008, riskPreventSceGen, formData);
		if (riskPreventSceGen != null) {
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
	public Map<String, Object> deleteAjax(String genNo,String tableId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		RiskPreventSceGen riskPreventSceGen = new RiskPreventSceGen();
		riskPreventSceGen.setGenNo(genNo);
		try {
			// JSONObject jb = JSONObject.fromObject(ajaxData);
			// riskPreventSceGen = (RiskPreventSceGen)JSONObject.toBean(jb,
			// RiskPreventSceGen.class);
			riskPreventSceGenFeign.delete(riskPreventSceGen);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, "tableTag", riskPreventSceGenFeign.getAll(riskPreventSceGen), null,
					true);
			dataMap.put("tableData", tableHtml);
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

	/**
	 * 
	 * 方法描述： 跳转选择担保方式的页面 多选
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2017-4-20 下午2:30:39
	 */
	@RequestMapping(value = "/getVouTypeForMutiSel")
	public String getVouTypeForMutiSel(Model model, String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		ParmDic parmDic = new ParmDic();
		parmDic.setKeyName("VOU_TYPE");
		List<ParmDic> parmDiclist = nmdInterfaceFeign.findAllParmDicByKeyName(parmDic);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("vouTypelist", parmDiclist);
		JSONObject jb = JSONObject.fromObject(dataMap);
		dataMap = jb;
		model.addAttribute("dataMap", dataMap);
		model.addAttribute("query", "");
		return "/component/risk/VouType_MutiSel";
	}

	/**
	 * 
	 * 方法描述： 跳转选择业务模式的页面 多选
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2017-4-20 下午2:33:21
	 */
	@RequestMapping(value = "/getBusModelForMutiSel")
	public String getBusModelForMutiSel(Model model, String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		ParmDic parmDic = new ParmDic();
		parmDic.setKeyName("BUS_MODEL");
		List<ParmDic> parmDiclist = nmdInterfaceFeign.findAllParmDicByKeyName(parmDic);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("busModellist", parmDiclist);
		JSONObject jb = JSONObject.fromObject(dataMap);
		dataMap = jb;
		model.addAttribute("dataMap", dataMap);
		model.addAttribute("query", "");
		return "/component/risk/BusModel_MutiSel";
	}

}
