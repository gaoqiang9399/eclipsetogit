package  app.component.eval.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.component.eval.entity.EvalScenceDxVal;
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

import app.component.eval.feign.EvalScenceDxValFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import net.sf.json.JSONObject;

/**
 * Title: EvalScenceDxValAction.java
 * Description:
 * @author:@dhcc.com.cn
 * @Thu Mar 31 06:38:05 GMT 2016
 **/
@Controller
@RequestMapping("/evalScenceDxVal")
public class EvalScenceDxValController extends BaseFormBean{
	//注入业务层
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private EvalScenceDxValFeign evalScenceDxValFeign;
	/**
	 * 获取列表数据
	 * @return
	 * @throws Exception
	 */
	private void getTableData(Map<String, Object> dataMap,String tableId,EvalScenceDxVal evalScenceDxVal)  throws Exception {
		JsonTableUtil jtu = new JsonTableUtil();
		String  tableHtml = jtu.getJsonStr(tableId,"tableTag", evalScenceDxValFeign.getAll(evalScenceDxVal), null,true);
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
		FormData formeval2003 = formService.getFormData("eval2003");
		EvalScenceDxVal evalScenceDxVal = new EvalScenceDxVal();
		Ipage ipage = this.getIpage();
		List<EvalScenceDxVal> evalScenceDxValList = (List<EvalScenceDxVal>)evalScenceDxValFeign.findByPage(ipage, evalScenceDxVal).getResult();
		model.addAttribute("evalScenceDxValList", evalScenceDxValList);
		model.addAttribute("formeval2003", formeval2003);
		model.addAttribute("query", "");
		return "/component/eval/EvalScenceDxVal_List";
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
		FormData formeval2003 = formService.getFormData("eval2003");
		EvalScenceDxVal evalScenceDxVal = new EvalScenceDxVal();
		List<EvalScenceDxVal> evalScenceDxValList = evalScenceDxValFeign.getAll(evalScenceDxVal);
		model.addAttribute("evalScenceDxValList", evalScenceDxValList);
		model.addAttribute("formeval2003", formeval2003);
		model.addAttribute("query", "");
		return "/component/eval/EvalScenceDxVal_List";
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
		FormData 	formeval2003 = formService.getFormData("eval2003");
			getFormValue(formeval2003, getMapByJson(ajaxData));
			if(this.validateFormData(formeval2003)){
		EvalScenceDxVal evalScenceDxVal = new EvalScenceDxVal();
				setObjValue(formeval2003, evalScenceDxVal);
				evalScenceDxValFeign.insert(evalScenceDxVal);
				getTableData( dataMap, tableId, evalScenceDxVal);//获取列表
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
	public Map<String, Object> updateAjax(String ajaxData,String evalAppNo,String evalScenceNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try{
		EvalScenceDxVal evalScenceDxVal = new EvalScenceDxVal();
			evalScenceDxVal.setEvalAppNo(evalAppNo);
			evalScenceDxVal.setEvalScenceNo(evalScenceNo);
			JSONObject jb = JSONObject.fromObject(ajaxData);
			String scoreList = "";
			double score = 0;
			for(Object key:jb.keySet()){
				scoreList+=key+":"+jb.get(key)+"@";
				score+=jb.getDouble((String)key);
			}
			evalScenceDxVal.setScoreList(scoreList);
			evalScenceDxVal.setScore(score);
			evalScenceDxValFeign.update(evalScenceDxVal);
			dataMap.put("formData", evalScenceDxVal);
			dataMap.put("flag", "success");
			dataMap.put("msg",MessageEnum.SUCCEED_UPDATE.getMessage());
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
	public Map<String, Object> getByIdAjax(String ajaxData,String evalAppNo,String evalScenceNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String,Object> formData = new HashMap<String,Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		FormData formeval2003 = formService.getFormData("eval2003");
		EvalScenceDxVal evalScenceDxVal = new EvalScenceDxVal();
		evalScenceDxVal.setEvalScenceNo(evalScenceNo);
		evalScenceDxVal.setEvalAppNo(evalAppNo);
		evalScenceDxVal = evalScenceDxValFeign.getById(evalScenceDxVal);
		getObjValue(formeval2003, evalScenceDxVal,formData);
		if(evalScenceDxVal!=null){
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
	public Map<String, Object> deleteAjax(String ajaxData,String evalAppNo,String evalScenceNo,String tableId) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		EvalScenceDxVal evalScenceDxVal = new EvalScenceDxVal();
		evalScenceDxVal.setEvalScenceNo(evalScenceNo);
		evalScenceDxVal.setEvalAppNo(evalAppNo);
		try {
			JSONObject jb = JSONObject.fromObject(ajaxData);
			evalScenceDxVal = (EvalScenceDxVal)JSONObject.toBean(jb, EvalScenceDxVal.class);
			evalScenceDxValFeign.delete(evalScenceDxVal);
			getTableData( dataMap, tableId, evalScenceDxVal);//获取列表
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
	 * AJAX更新保存
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertOrUpdateProjectEvalAjax")
	@ResponseBody
	public Map<String, Object> insertOrUpdateProjectEvalAjax(String ajaxData,String evalAppNo,String evalScenceNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try{
		EvalScenceDxVal 	evalScenceDxVal = new EvalScenceDxVal();
			evalScenceDxVal.setEvalAppNo(evalAppNo);
			evalScenceDxVal.setEvalScenceNo(evalScenceNo);
			JSONObject jb = JSONObject.fromObject(ajaxData);
			String scoreList = "";
			double score = 0;
			for(Object key:jb.keySet()){
				scoreList+=key+":"+jb.get(key)+"@";
				score+=jb.getDouble((String)key);
			}
			evalScenceDxVal.setScoreList(scoreList);
			evalScenceDxVal.setScore(score);
			evalScenceDxValFeign.insertOrUpdateProjectEval(evalScenceDxVal);
			dataMap.put("formData", evalScenceDxVal);
			dataMap.put("flag", "success");
			dataMap.put("msg",MessageEnum.SUCCEED_UPDATE.getMessage());
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.FAILED_UPDATE.getMessage());
			throw e;
		}
		return dataMap;
	}
	
}
