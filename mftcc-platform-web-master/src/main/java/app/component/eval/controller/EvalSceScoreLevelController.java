package  app.component.eval.controller;
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

import app.component.eval.entity.EvalSceScoreLevel;
import app.component.eval.feign.EvalSceScoreLevelFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import net.sf.json.JSONObject;

/**
 * Title: EvalSceScoreLevelAction.java
 * Description:
 * @author:@dhcc.com.cn
 * @Thu Mar 31 12:01:52 GMT 2016
 **/
@Controller
@RequestMapping("/evalSceScoreLevel")
public class EvalSceScoreLevelController extends BaseFormBean{
	//注入业务层
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private EvalSceScoreLevelFeign evalSceScoreLevelFeign;
	/**
	 * 获取列表数据
	 * @return
	 * @throws Exception
	 */
	private void getTableData(Map<String, Object> dataMap,String tableId,EvalSceScoreLevel evalSceScoreLevel) throws Exception {
		JsonTableUtil jtu = new JsonTableUtil();
		String  tableHtml = jtu.getJsonStr(tableId,"tableTag", evalSceScoreLevelFeign.getAll(evalSceScoreLevel), null,true);
		dataMap.put("tableData",tableHtml);
	}
	/**
	 * 列表有翻页
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		FormData formeval3001 = formService.getFormData("eval3001");
		EvalSceScoreLevel evalSceScoreLevel = new EvalSceScoreLevel();
		Ipage ipage = this.getIpage();
		List<EvalSceScoreLevel> evalSceScoreLevelList = (List<EvalSceScoreLevel>)evalSceScoreLevelFeign.findByPage(ipage, evalSceScoreLevel).getResult();
		model.addAttribute("evalSceScoreLevelList", evalSceScoreLevelList);
		model.addAttribute("formeval3001", formeval3001);
		model.addAttribute("query", "");
		return "/component/eval/EvalSceScoreLevel_List";
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
		FormData formeval3001 = formService.getFormData("eval3001");
		EvalSceScoreLevel evalSceScoreLevel = new EvalSceScoreLevel();
		List<EvalSceScoreLevel> evalSceScoreLevelList = evalSceScoreLevelFeign.getAll(evalSceScoreLevel);
		model.addAttribute("evalSceScoreLevelList", evalSceScoreLevelList);
		model.addAttribute("formeval3001", formeval3001);
		model.addAttribute("query", "");
		return "/component/eval/EvalSceScoreLevel_List";
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
		FormData 	formeval3001 = formService.getFormData("eval3001");
			getFormValue(formeval3001, getMapByJson(ajaxData));
			if(this.validateFormData(formeval3001)){
		EvalSceScoreLevel evalSceScoreLevel = new EvalSceScoreLevel();
				setObjValue(formeval3001, evalSceScoreLevel);
				evalSceScoreLevelFeign.insert(evalSceScoreLevel);
				getTableData( dataMap, tableId, evalSceScoreLevel);//获取列表
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
	@RequestMapping(value = "/updateAjax")
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData,String tableId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try{
		FormData 	formeval3001 = formService.getFormData("eval3001");
			getFormValue(formeval3001, getMapByJson(ajaxData));
			if(this.validateFormData(formeval3001)){
		EvalSceScoreLevel evalSceScoreLevel = new EvalSceScoreLevel();
				setObjValue(formeval3001, evalSceScoreLevel);
				evalSceScoreLevelFeign.update(evalSceScoreLevel);
				getTableData( dataMap, tableId, evalSceScoreLevel);//获取列表
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
	@RequestMapping(value = "/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String ajaxData,String scenceNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String,Object> formData = new HashMap<String,Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		FormData formeval3001 = formService.getFormData("eval3001");
		EvalSceScoreLevel evalSceScoreLevel = new EvalSceScoreLevel();
		evalSceScoreLevel.setScenceNo(scenceNo);
		evalSceScoreLevel = evalSceScoreLevelFeign.getById(evalSceScoreLevel);
		getObjValue(formeval3001, evalSceScoreLevel,formData);
		if(evalSceScoreLevel!=null){
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
	public Map<String, Object> deleteAjax(String ajaxData,String scenceNo,String tableId) throws Exception{
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		EvalSceScoreLevel evalSceScoreLevel = new EvalSceScoreLevel();
		evalSceScoreLevel.setScenceNo(scenceNo);
		try {
			JSONObject jb = JSONObject.fromObject(ajaxData);
			evalSceScoreLevel = (EvalSceScoreLevel)JSONObject.toBean(jb, EvalSceScoreLevel.class);
			evalSceScoreLevelFeign.delete(evalSceScoreLevel);
			getTableData( dataMap, tableId, evalSceScoreLevel);//获取列表
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
