package  app.component.rec.controller;
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

import app.component.rec.entity.MfCollectionFormConfig;
import app.component.rec.feign.MfCollectionFormConfigFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import net.sf.json.JSONObject;

/**
 * Title: MfCollectionFormConfigAction.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Sun Mar 19 15:13:37 CST 2017
 **/
@Controller
@RequestMapping(value="/mfCollectionFormConfig")
public class MfCollectionFormConfigController extends BaseFormBean{
	//注入业务层
	@Autowired
	private MfCollectionFormConfigFeign mfCollectionFormConfigFeign;
	
	
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	/**
	 * 获取列表数据
	 * @param mfCollectionFormConfig 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getTableData")
	private void getTableData(String tableId, MfCollectionFormConfig mfCollectionFormConfig,Map<String,Object> dataMap) throws Exception {
		JsonTableUtil jtu = new JsonTableUtil();
		String  tableHtml = jtu.getJsonStr(tableId,"tableTag", mfCollectionFormConfigFeign.getAll(mfCollectionFormConfig), null,true);
		dataMap.put("tableData",tableHtml);
	}
	/**
	 * 列表有翻页
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request,
				response);
		FormService formService = new FormService();
		FormData formdlmfcollectionformcfg0002 = formService.getFormData("dlmfcollectionformcfg0002");
		MfCollectionFormConfig mfCollectionFormConfig = new MfCollectionFormConfig();
		Ipage ipage = this.getIpage();
		List<MfCollectionFormConfig> mfCollectionFormConfigList = (List<MfCollectionFormConfig>)mfCollectionFormConfigFeign.findByPage(ipage, mfCollectionFormConfig).getResult();
		model.addAttribute("formdlmfcollectionformcfg0002", formdlmfcollectionformcfg0002);
		model.addAttribute("mfCollectionFormConfigList", mfCollectionFormConfigList);
		return "MfCollectionFormConfig_List";
	}
	/**
	 * 列表全部无翻页
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getListAll")
	public String getListAll(Model model) throws Exception {
		ActionContext.initialize(request,
				response);
		FormService formService = new FormService();
		FormData formdlmfcollectionformcfg0002 = formService.getFormData("dlmfcollectionformcfg0002");
		MfCollectionFormConfig mfCollectionFormConfig = new MfCollectionFormConfig();
		List<MfCollectionFormConfig> mfCollectionFormConfigList = mfCollectionFormConfigFeign.getAll(mfCollectionFormConfig);
		model.addAttribute("formdlmfcollectionformcfg0002", formdlmfcollectionformcfg0002);
		model.addAttribute("mfCollectionFormConfigList", mfCollectionFormConfigList);
		return "MfCollectionFormConfig_List";
	}
	/**
	 * AJAX新增
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/insertAjax")
	public Map<String,Object> insertAjax(String ajaxData,String tableId) throws Exception {
		ActionContext.initialize(request,
				response);
		Map<String,Object> dataMap = new HashMap<String, Object>();  
		FormService formService = new FormService();
		try{
			FormData formdlmfcollectionformcfg0002 = formService.getFormData("dlmfcollectionformcfg0002");
			getFormValue(formdlmfcollectionformcfg0002, getMapByJson(ajaxData));
			if(this.validateFormData(formdlmfcollectionformcfg0002)){
				MfCollectionFormConfig mfCollectionFormConfig = new MfCollectionFormConfig();
				setObjValue(formdlmfcollectionformcfg0002, mfCollectionFormConfig);
				mfCollectionFormConfigFeign.insert(mfCollectionFormConfig);
				getTableData(tableId,mfCollectionFormConfig,dataMap);//获取列表
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.ERROR_INSERT.getMessage());
			throw e;
		}
		return dataMap;
	}
	/**
	 * AJAX更新保存
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/updateAjax")
	public Map<String,Object> updateAjax(String ajaxData,String tableId) throws Exception {
		ActionContext.initialize(request,
				response);
		Map<String,Object> dataMap = new HashMap<String, Object>();  
		FormService formService = new FormService();
		MfCollectionFormConfig mfCollectionFormConfig = new MfCollectionFormConfig();
		try{
			FormData formdlmfcollectionformcfg0002 = formService.getFormData("dlmfcollectionformcfg0002");
			getFormValue(formdlmfcollectionformcfg0002, getMapByJson(ajaxData));
			if(this.validateFormData(formdlmfcollectionformcfg0002)){
				mfCollectionFormConfig = new MfCollectionFormConfig();
				setObjValue(formdlmfcollectionformcfg0002, mfCollectionFormConfig);
				mfCollectionFormConfigFeign.update(mfCollectionFormConfig);
				getTableData(tableId,mfCollectionFormConfig,dataMap);//获取列表
				dataMap.put("flag", "success");
				dataMap.put("msg",MessageEnum.SUCCEED_UPDATE.getMessage());
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.FAILED_UPDATE.getMessage());
			throw e;
		}
		return dataMap;
	}
	
	/**
	 * AJAX获取查看
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/getByIdAjax")
	public Map<String,Object> getByIdAjax(String id) throws Exception {
		ActionContext.initialize(request,
				response);
		FormService formService = new FormService();
		Map<String,Object> formData = new HashMap<String,Object>();
		Map<String,Object> dataMap = new HashMap<String, Object>();  
		FormData formdlmfcollectionformcfg0002 = formService.getFormData("dlmfcollectionformcfg0002");
		MfCollectionFormConfig mfCollectionFormConfig = new MfCollectionFormConfig();
		mfCollectionFormConfig.setId(id);
		mfCollectionFormConfig = mfCollectionFormConfigFeign.getById(mfCollectionFormConfig);
		getObjValue(formdlmfcollectionformcfg0002, mfCollectionFormConfig,formData);
		if(mfCollectionFormConfig!=null){
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
	@ResponseBody
	@RequestMapping(value="/deleteAjax")
	public Map<String,Object> deleteAjax(String id,String ajaxData,String tableId) throws Exception{
		ActionContext.initialize(request,
				response);
		Map<String,Object> dataMap = new HashMap<String, Object>();  
		MfCollectionFormConfig mfCollectionFormConfig = new MfCollectionFormConfig();
		mfCollectionFormConfig.setId(id);
		try {
			JSONObject jb = JSONObject.fromObject(ajaxData);
			mfCollectionFormConfig = (MfCollectionFormConfig)JSONObject.toBean(jb, MfCollectionFormConfig.class);
			mfCollectionFormConfigFeign.delete(mfCollectionFormConfig);
			getTableData(tableId,mfCollectionFormConfig,dataMap);//获取列表
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

	
}
