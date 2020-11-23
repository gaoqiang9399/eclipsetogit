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

import app.component.eval.entity.EvalScenceRestrictVal;
import app.component.eval.feign.EvalScenceRestrictValFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import net.sf.json.JSONObject;

/**
 * Title: EvalScenceRestrictValAction.java
 * Description:
 * @author:@dhcc.com.cn
 * @Thu Mar 31 06:39:47 GMT 2016
 **/
@Controller
@RequestMapping("/evalScenceRestrictVal")
public class EvalScenceRestrictValController extends BaseFormBean{
	//注入业务层
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private EvalScenceRestrictValFeign evalScenceRestrictValFeign;
	//全局变量
	//表单变量
	
	/**
	 * 获取列表数据
	 * @return
	 * @throws Exception
	 */
	private void getTableData(Map<String, Object> dataMap,String tableId,EvalScenceRestrictVal evalScenceRestrictVal) throws Exception {
		JsonTableUtil jtu = new JsonTableUtil();
		String  tableHtml = jtu.getJsonStr(tableId,"tableTag", evalScenceRestrictValFeign.getAll(evalScenceRestrictVal), null,true);
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
		FormData formeval0002 = formService.getFormData("eval0002");
		EvalScenceRestrictVal evalScenceRestrictVal = new EvalScenceRestrictVal();
		Ipage ipage = this.getIpage();
		List<EvalScenceRestrictVal> evalScenceRestrictValList = (List<EvalScenceRestrictVal>)evalScenceRestrictValFeign.findByPage(ipage, evalScenceRestrictVal).getResult();
		model.addAttribute("evalScenceRestrictValList", evalScenceRestrictValList);
		model.addAttribute("formeval0002", formeval0002);
		model.addAttribute("query", "");
		return "/component/eval/EvalScenceRestrictRel_List";
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
		FormData formeval0002 = formService.getFormData("eval0002");
		EvalScenceRestrictVal evalScenceRestrictVal = new EvalScenceRestrictVal();
		List<EvalScenceRestrictVal> evalScenceRestrictValList = evalScenceRestrictValFeign.getAll(evalScenceRestrictVal);
		model.addAttribute("evalScenceRestrictValList", evalScenceRestrictValList);
		model.addAttribute("formeval0002", formeval0002);
		model.addAttribute("query", "");
		return "/component/eval/EvalScenceRestrictRel_List";
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
		FormData 	formeval0002 = formService.getFormData("eval0002");
			getFormValue(formeval0002, getMapByJson(ajaxData));
			if(this.validateFormData(formeval0002)){
		EvalScenceRestrictVal evalScenceRestrictVal = new EvalScenceRestrictVal();
				setObjValue(formeval0002, evalScenceRestrictVal);
				evalScenceRestrictValFeign.insert(evalScenceRestrictVal);
				getTableData( dataMap, tableId, evalScenceRestrictVal);//获取列表
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
		FormData 	formeval0002 = formService.getFormData("eval0002");
			getFormValue(formeval0002, getMapByJson(ajaxData));
			if(this.validateFormData(formeval0002)){
		EvalScenceRestrictVal evalScenceRestrictVal = new EvalScenceRestrictVal();
				setObjValue(formeval0002, evalScenceRestrictVal);
				evalScenceRestrictValFeign.update(evalScenceRestrictVal);
				getTableData( dataMap, tableId, evalScenceRestrictVal);//获取列表
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
	public Map<String, Object> getByIdAjax(String ajaxData,String evalAppNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String,Object> formData = new HashMap<String,Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		FormData formeval0002 = formService.getFormData("eval0002");
		EvalScenceRestrictVal evalScenceRestrictVal = new EvalScenceRestrictVal();
		evalScenceRestrictVal.setEvalAppNo(evalAppNo);
		evalScenceRestrictVal = evalScenceRestrictValFeign.getById(evalScenceRestrictVal);
		getObjValue(formeval0002, evalScenceRestrictVal,formData);
		if(evalScenceRestrictVal!=null){
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
	public Map<String, Object> deleteAjax(String ajaxData,String tableId,String evalAppNo) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		EvalScenceRestrictVal evalScenceRestrictVal = new EvalScenceRestrictVal();
		evalScenceRestrictVal.setEvalAppNo(evalAppNo);
		try {
			JSONObject jb = JSONObject.fromObject(ajaxData);
			evalScenceRestrictVal = (EvalScenceRestrictVal)JSONObject.toBean(jb, EvalScenceRestrictVal.class);
			evalScenceRestrictValFeign.delete(evalScenceRestrictVal);
			getTableData( dataMap, tableId, evalScenceRestrictVal);//获取列表
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
