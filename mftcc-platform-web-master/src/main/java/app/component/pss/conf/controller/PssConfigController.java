package  app.component.pss.conf.controller;
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

import app.component.pss.conf.entity.PssConfig;
import app.component.pss.conf.feign.PssConfigFeign;
import app.component.pss.information.entity.PssGoods;
import app.component.pss.information.entity.PssStorehouse;
import app.component.pss.information.feign.PssGoodsFeign;
import app.component.pss.information.feign.PssStorehouseFeign;
import cn.mftcc.util.StringUtil;
import cn.mftcc.util.WaterIdUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Title: PssConfigAction.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Fri Aug 11 16:00:07 CST 2017
 **/
@Controller
@RequestMapping("/pssConfig")
public class PssConfigController extends BaseFormBean{
	//注入业务层
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private PssConfigFeign pssConfigFeign;
	@Autowired
	private PssStorehouseFeign pssStorehouseFeign;
	@Autowired
	private PssGoodsFeign pssGoodsFeign;
	//全局变量
	//表单变量
	/**
	 * 获取列表数据
	 * @param dataMap 
	 * @return
	 * @throws Exception
	 */
	private void getTableData(String tableId,PssConfig pssConfig,  Map<String, Object> dataMap) throws Exception {
		JsonTableUtil jtu = new JsonTableUtil();
		String  tableHtml = jtu.getJsonStr(tableId,"tableTag", pssConfigFeign.getAll(pssConfig), null,true);
		dataMap.put("tableData",tableHtml);
	}
	/**
	 * 列表有翻页
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request,
				response);
		model.addAttribute("query", "");
		return "/component/pss/conf/PssConfig_List";
	}
	
	/**
	 * 列表有翻页
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getConfPage")
	public String getConfPage(Model model, String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		FormData formpssconf0002 = formService.getFormData("pssconf0002");
		PssConfig pssConfig = new PssConfig();
		pssConfig = pssConfigFeign.getOne();
		getObjValue(formpssconf0002, pssConfig);
		model.addAttribute("formpssconf0002", formpssconf0002);
		model.addAttribute("query", "");
		return "/component/pss/conf/PssConfig";
	}
	/**
	 * 列表全部无翻页
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListAll")
	public String getListAll(Model model, String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		FormData formpssconf0002 = formService.getFormData("pssconf0002");
		PssConfig pssConfig = new PssConfig();
		List<PssConfig> pssConfigList = pssConfigFeign.getAll(pssConfig);
		model.addAttribute("formpssconf0002", formpssconf0002);
		model.addAttribute("pssConfigList", pssConfigList);
		model.addAttribute("query", "");
		return "/component/pss/conf/PssConfig_List";
	}
	/**
	 * AJAX新增
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData,String tableId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try{
		FormData formpssconf0002 = formService.getFormData("pssconf0002");
			getFormValue(formpssconf0002, getMapByJson(ajaxData));
			if(this.validateFormData(formpssconf0002)){
		PssConfig pssConfig = new PssConfig();
				setObjValue(formpssconf0002, pssConfig);
				pssConfigFeign.insert(pssConfig);
				getTableData(tableId,pssConfig,dataMap);//获取列表
				dataMap.put("flag", "success");
				dataMap.put("msg", "新增成功");
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "新增失败");
			throw new Exception(e.getMessage());
		}
		return dataMap;
	}
	/**
	 * AJAX更新保存
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateAjax")
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData,String tableId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		PssConfig pssConfig = new PssConfig();
		try{
		FormData 	formpssconf0002 = formService.getFormData("pssconf0002");
			getFormValue(formpssconf0002, getMapByJson(ajaxData));
			if(this.validateFormData(formpssconf0002)){
				pssConfig = new PssConfig();
				setObjValue(formpssconf0002, pssConfig);
				if(StringUtil.isEmpty(pssConfig.getConfId())){
					pssConfig.setConfId(WaterIdUtil.getWaterId());
					pssConfigFeign.insert(pssConfig);
				}else{
					pssConfigFeign.update(pssConfig);
				}
				getTableData(tableId,pssConfig,dataMap);//获取列表
				dataMap.put("flag", "success");
				dataMap.put("msg", "更新成功");
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "更新失败");
			throw new Exception(e.getMessage());
		}
		return dataMap;
	}
	
	/**
	 * AJAX获取查看
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String confId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String,Object> formData = new HashMap<String,Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		FormData formpssconf0002 = formService.getFormData("pssconf0002");
		PssConfig pssConfig = new PssConfig();
		pssConfig.setConfId(confId);
		pssConfig = pssConfigFeign.getById(pssConfig);
		getObjValue(formpssconf0002, pssConfig,formData);
		if(pssConfig!=null){
			dataMap.put("flag", "success");
		}else{
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
	@RequestMapping(value = "/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String ajaxData,String confId,String tableId) throws Exception{
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		PssConfig pssConfig = new PssConfig();
		pssConfig.setConfId(confId);
		try {
			JSONObject jb = JSONObject.fromObject(ajaxData);
			pssConfig = (PssConfig)JSONObject.toBean(jb, PssConfig.class);
			pssConfigFeign.delete(pssConfig);
			getTableData(tableId,pssConfig,dataMap);//获取列表
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
	
	@RequestMapping(value = "/getPssReport")
	public String getPssReport(Model model) throws Exception {
		ActionContext.initialize(request,
				response);
		JSONObject json = new JSONObject();
		//获取仓库数据
		PssStorehouse pssStorehouse = new PssStorehouse();
		pssStorehouse.setFlag("1");
		List<PssStorehouse> houseList = new ArrayList<PssStorehouse>();
		houseList = pssStorehouseFeign.getAll(pssStorehouse);
		JSONArray houseJsonArray = new JSONArray();
		for (int i = 0; i < houseList.size(); i++) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id", houseList.get(i).getStorehouseId());
			jsonObject.put("name", houseList.get(i).getStorehouseName());
			houseJsonArray.add(jsonObject);
		}
		json.put("houseJsonArray", houseJsonArray);
		//获取商品数据
		PssGoods pssGoods = new PssGoods();
		pssGoods.setFlag("1");
		List<PssGoods> goodsList = pssGoodsFeign.getAll(pssGoods);
		JSONArray goodsJsonArray = new JSONArray();
		for (int i = 0; i < goodsList.size(); i++) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id", goodsList.get(i).getGoodsNo());
			jsonObject.put("name", goodsList.get(i).getGoodsName());
			goodsJsonArray.add(jsonObject);
		}
		json.put("goodsJsonArray", goodsJsonArray);
		String ajaxData = json.toString();
		model.addAttribute("ajaxData", ajaxData);
		model.addAttribute("query", "");
		return "/component/pss/information/PssReportEntrance";
	}

}
