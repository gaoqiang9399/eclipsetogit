package app.component.nmd.comtroller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.component.nmd.entity.NmdAreaProvinces;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;
import com.google.gson.Gson;

import app.base.SpringUtil;
import app.base.cache.interfaceimpl.BusiRedisCache;
import app.component.common.BizPubParm;
import app.component.common.EntityUtil;
import app.component.nmd.entity.NmdArea;
import app.component.nmd.feign.NmdAreaFeign;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import net.sf.json.JSONArray;

/**
 * Title: NmdAreaAction.java Description:
 * 
 * @author:@dhcc.com.cn
 * @Tue Apr 05 03:30:59 GMT 2016
 **/
@Controller
@RequestMapping("/nmdArea")
public class NmdAreaController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入NmdAreaBo
	@Autowired
	private NmdAreaFeign nmdAreaFeign;

	/**
	 * 获取省份列表页面
	 */
	@RequestMapping(value = "/getProvinceList")
	public String getProvinceList(Model model) throws Exception {
		ActionContext.initialize(request, response);
		model.addAttribute("query", "");
		return "component/nmd/NmdArea_ProvinceList";
	}

	/**
	 * 查询省份列表ajax数据
	 * @param tableId 
	 * @param tableType 
	 * @param pageSize 
	 * @param pageNo 
	 */
	@ResponseBody
	@RequestMapping(value = "/findByPageProvinceAjax")
	public Map<String, Object> findByPageProvinceAjax(String ajaxData, String tableId, String tableType, Integer pageSize, Integer pageNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		NmdArea area = new NmdArea();
		// 设置省份的标识
		area.setLev("1");
		try {
			area.setCustomQuery(ajaxData);// 自定义查询参数赋值
			area.setCriteriaList(area, ajaxData);// 我的筛选
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage.setParams(this.setIpageParams("nmdArea",area));
			ipage = nmdAreaFeign.findByPage(ipage);
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
			throw new Exception(e.getMessage());
		}
		return dataMap;
	}

	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request, response);
		model.addAttribute("query", "");
		return "component/nmd/NmdArea_List";
	}
	
	/**
	 * 列表打开页面请求
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getProvince")
	public String getProvince(Model model, String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		NmdArea nmdArea = new NmdArea();
		nmdArea.setLev("1");
		nmdArea.setUplev("0");
		List<NmdArea> result = nmdAreaFeign.getByLev(nmdArea);
		String NmdAreaListString = new Gson().toJson(result);
		model.addAttribute("ajaxData", NmdAreaListString);
		model.addAttribute("query", "");
		return "/component/nmd/IndustryAndArea";
	}
	
	/**
	 * 省份组件调用
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getProvinceAjax")
	@ResponseBody
	public Map<String, Object> getProvinceAjax(String ajaxData) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		ActionContext.initialize(request, response);
		NmdArea nmdArea = new NmdArea();
		nmdArea.setLev("1");
		nmdArea.setUplev("0");
		List<NmdArea> result = nmdAreaFeign.getByLev(nmdArea);
		String NmdAreaListString = new Gson().toJson(result);
		dataMap.put("items", NmdAreaListString);
		return dataMap;
	}
	
	/**
	 * 列表打开页面请求
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getCity")
	public String getCity(Model model, @RequestParam("uplev") String uplev, String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		NmdArea nmdArea = new NmdArea();
		nmdArea.setLev("2");
		nmdArea.setUplev(uplev);
		List<NmdArea> result = nmdAreaFeign.getByLev(nmdArea);
		String NmdAreaListString = new Gson().toJson(result);
		model.addAttribute("ajaxData", NmdAreaListString);
		model.addAttribute("query", "");
		return "/component/nmd/IndustryAndArea";
	}
	
	/**
	 * 列表打开页面请求
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getCityAjax")
	@ResponseBody
	public Map<String, Object> getCityAjax(@RequestParam("uplev") String uplev, String ajaxData) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		ActionContext.initialize(request, response);
		NmdArea nmdArea = new NmdArea();
		nmdArea.setLev("2");
		nmdArea.setUplev(uplev);
		List<NmdArea> result = nmdAreaFeign.getByLev(nmdArea);
		String NmdAreaListString = new Gson().toJson(result);
		dataMap.put("items", NmdAreaListString);
		return dataMap;
	}

	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListAll")
	public String getListAll(Model model) throws Exception {
		ActionContext.initialize(request, response);
		CodeUtils cu = new CodeUtils();
		List NmdAreaList = cu.getCacheByKeyName("nmd_area");
		String NmdAreaListString = new Gson().toJson(NmdAreaList);
		model.addAttribute("ajaxData", NmdAreaListString);
		model.addAttribute("showAllFlag", "1");
		model.addAttribute("query", "");
		model.addAttribute("isStepLoading", "false");
		return "/component/nmd/IndustryAndArea";
	}

	/**
	 * 区域组件调用
	 * 
	 * @author YaoWenHao
	 * @date 2017-5-17
	 */
	@ResponseBody
	@RequestMapping(value = "/getAreaListAllAjax")
	public Map<String, Object> getAreaListAllAjax(String ajaxData) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		ActionContext.initialize(request, response);
		CodeUtils cu = new CodeUtils();
		List NmdAreaList = cu.getCacheByKeyName("nmd_area");
		String NmdAreaListString = new Gson().toJson(NmdAreaList);
		dataMap.put("items", NmdAreaListString);//TODO 地区缓存需要修改
		return dataMap;
	}
	
	@RequestMapping(value = "/getLv1Page")
	public String getLv1Page(Model model) throws Exception {
		ActionContext.initialize(request, response);
		NmdArea nmdArea = new NmdArea();
		nmdArea.setLev("1");
		List<NmdArea> result = nmdAreaFeign.getLv(nmdArea);
		model.addAttribute("ajaxData", JSONArray.fromObject(result).toString());
		model.addAttribute("isStepLoading", "true");
		return "/component/nmd/IndustryAndArea";
	}
	
	@ResponseBody
	@RequestMapping(value = "/getChildAjax")
	public Map<String,Object> getChildAjax(String areaNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		NmdArea nmdArea = new NmdArea();
		nmdArea.setAreaNo(areaNo);
		List<NmdArea> nmdAreaList = nmdAreaFeign.getChild(nmdArea);
		dataMap.put("childNodeList", nmdAreaList);	
		return dataMap;
	}

	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getBankAreaListAll")
	public String getBankAreaListAll(Model model, String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		model.addAttribute("showAllFlag", "0");
		CodeUtils cu = new CodeUtils();
		List NmdAreaList = cu.getCacheByKeyName("nmd_area");
		String NmdAreaListString = new Gson().toJson(NmdAreaList);
		model.addAttribute("ajaxData", NmdAreaListString);
		model.addAttribute("query", "");
		return "/component/nmd/IndustryAndArea";
	}

	/**
	 * @Description:银行城市
	 * @return
	 * @throws Exception
	 * @author: 李伟
	 * @date: 2018-1-12 下午6:41:02
	 */
	@RequestMapping(value = "/getAllExceptDirectCityAjax")
	@ResponseBody
	public Map<String, Object> getAllExceptDirectCityAjax(String ajaxData) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		ActionContext.initialize(request, response);
		dataMap.put("items", BizPubParm.bankArea);
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
			String ajaxData) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		NmdArea nmdArea = new NmdArea();
		try {
			nmdArea.setCustomQuery(ajaxData);// 自定义查询参数赋值
			nmdArea.setCriteriaList(nmdArea, ajaxData);// 我的筛选
			// this.getRoleConditions(nmdArea,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage.setParams(this.setIpageParams("nmdArea",nmdArea));
			ipage = nmdAreaFeign.findByPage(ipage);
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
	@RequestMapping(value = "/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formnmd1002 = formService.getFormData("nmd1002");
			getFormValue(formnmd1002, getMapByJson(ajaxData));
			if (this.validateFormData(formnmd1002)) {
				NmdArea nmdArea = new NmdArea();
				setObjValue(formnmd1002, nmdArea);
				nmdAreaFeign.insert(nmdArea);
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
	@RequestMapping(value = "/updateAjaxByOne")
	@ResponseBody
	public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formnmd1002 = formService.getFormData("nmd1002");
		getFormValue(formnmd1002, getMapByJson(ajaxData));
		NmdArea nmdAreaJsp = new NmdArea();
		setObjValue(formnmd1002, nmdAreaJsp);
		NmdArea nmdArea = nmdAreaFeign.getById(nmdAreaJsp);
		if (nmdArea != null) {
			try {
				nmdArea = (NmdArea) EntityUtil.reflectionSetVal(nmdArea, nmdAreaJsp, getMapByJson(ajaxData));
				nmdAreaFeign.update(nmdArea);
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
	@RequestMapping(value = "/updateAjax")
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formnmd1002 = formService.getFormData("nmd1002");
			getFormValue(formnmd1002, getMapByJson(ajaxData));
			if (this.validateFormData(formnmd1002)) {
				NmdArea nmdArea = new NmdArea();
				setObjValue(formnmd1002, nmdArea);
				nmdAreaFeign.update(nmdArea);
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
	public Map<String, Object> getByIdAjax(String areaNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formnmd1002 = formService.getFormData("nmd1002");
		NmdArea nmdArea = new NmdArea();
		nmdArea.setAreaNo(areaNo);
		nmdArea = nmdAreaFeign.getById(nmdArea);
		getObjValue(formnmd1002, nmdArea, formData);
		if (nmdArea != null) {
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
	public Map<String, Object> deleteAjax(String areaNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		NmdArea nmdArea = new NmdArea();
		nmdArea.setAreaNo(areaNo);
		try {
			nmdAreaFeign.delete(nmdArea);
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
	 * 新增页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/input")
	public String input(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formnmd1002 = formService.getFormData("nmd1002");
		model.addAttribute("formnmd1002", formnmd1002);
		model.addAttribute("query", "");
		return "component/nmd/NmdArea_Insert";
	}

	/***
	 * 新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insert")
	public String insert(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formnmd1002 = formService.getFormData("nmd1002");
		getFormValue(formnmd1002);
		NmdArea nmdArea = new NmdArea();
		setObjValue(formnmd1002, nmdArea);
		nmdAreaFeign.insert(nmdArea);
		getObjValue(formnmd1002, nmdArea);
		this.addActionMessage(model, "保存成功");
		Ipage ipage = this.getIpage();
		ipage.setParams(this.setIpageParams("nmdArea",nmdArea));
		List<NmdArea> nmdAreaList = (List<NmdArea>) nmdAreaFeign.findByPage(ipage).getResult();
		model.addAttribute("formnmd1002", formnmd1002);
		model.addAttribute("nmdArea", nmdArea);
		model.addAttribute("nmdAreaList", nmdAreaList);
		model.addAttribute("query", "");
		return "component/nmd/NmdArea_Detail";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(String areaNo,Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formnmd1001 = formService.getFormData("nmd1001");
		getFormValue(formnmd1001);
		NmdArea nmdArea = new NmdArea();
		nmdArea.setAreaNo(areaNo);
		nmdArea = nmdAreaFeign.getById(nmdArea);
		getObjValue(formnmd1001, nmdArea);
		model.addAttribute("formnmd1001", formnmd1001);
		model.addAttribute("nmdArea", nmdArea);
		model.addAttribute("query", "");
		return "component/nmd/NmdArea_Detail";
	}

	/**
	 * 删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete( String areaNo,Model model) throws Exception {
		ActionContext.initialize(request, response);
		NmdArea nmdArea = new NmdArea();
		nmdArea.setAreaNo(areaNo);
		nmdAreaFeign.delete(nmdArea);
		return getListPage(model);
	}

	

	@RequestMapping(value = "/getAllAjax")
	@ResponseBody
	public Map<String, Object> getAllAjax(String ajaxData, String areaName, String lev, String areaSts, String query) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		NmdArea nmdArea = new NmdArea();
		String parm = "areaName=" + areaName + ";lev=" + lev + ";areaSts=" + areaSts;
		try {
			EntityUtil entityUtil = new EntityUtil();
			dataMap.put("data", entityUtil.prodAutoMenu(nmdArea, ajaxData, query, parm, null));
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}
	/**
	 * 新增校验
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateInsert")
	public void validateInsert(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formnmd1002 = formService.getFormData("nmd1002");
		getFormValue(formnmd1002);
		boolean validateFlag = this.validateFormData(formnmd1002);
	}

	/**
	 * 修改校验
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateUpdate")
	public void validateUpdate(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formnmd1002 = formService.getFormData("nmd1002");
		getFormValue(formnmd1002);
		boolean validateFlag = this.validateFormData(formnmd1002);
	}
	/**
	 * 
	 * 方法描述： 查询所有(过滤市下的县区)
	 * @return
	 * @throws Exception
	 * String
	 * @author lwq
	 * @date 2018-3-12 下午5:11:29
	 */
	@RequestMapping(value = "/getAllCityAjax")
	@ResponseBody
	public Map<String, Object> getAllCityAjax() throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		ActionContext.initialize(request, response);
		BusiRedisCache busiRedisCache =  (BusiRedisCache)SpringUtil.getBean(BusiRedisCache.class);
		
		dataMap.put("items", JSONArray.fromObject(busiRedisCache.getNmdWayList("exhibitionArea")).toString());
		return dataMap;
	}


	@RequestMapping(value = "/getLv1PageProvinces")
	public String getLv1PageProvinces(Model model) throws Exception {
		ActionContext.initialize(request, response);
		NmdAreaProvinces nmdAreaProvinces = new NmdAreaProvinces();
		nmdAreaProvinces.setLev("1");
		List<NmdAreaProvinces> result = nmdAreaFeign.getLvProvinces(nmdAreaProvinces);
		model.addAttribute("ajaxData", JSONArray.fromObject(result).toString());
		model.addAttribute("isStepLoading", "true");
		return "/component/nmd/IndustryAndAreaProvinces";
	}

	@ResponseBody
	@RequestMapping(value = "/getChildProvincesAjax")
	public Map<String,Object> getChildProvincesAjax(String areaNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		NmdAreaProvinces nmdAreaProvinces = new NmdAreaProvinces();
		nmdAreaProvinces.setAreaNo(areaNo);
		nmdAreaProvinces.setLev("2");
		List<NmdAreaProvinces> nmdAreaProvincesList = nmdAreaFeign.getChildProvinces(nmdAreaProvinces);
		dataMap.put("childNodeList", nmdAreaProvincesList);
		return dataMap;
	}
}
