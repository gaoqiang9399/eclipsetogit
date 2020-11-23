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

import app.component.eval.entity.EvalScenceDlRel;
import app.component.eval.feign.EvalScenceDlRelFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import net.sf.json.JSONObject;

/**
 * Title: EvalScenceDlRelAction.java
 * Description:
 * @author:@dhcc.com.cn
 * @Tue Mar 22 01:39:54 GMT 2016
 **/
@Controller
@RequestMapping("/evalScenceDlRel")
public class EvalScenceDlRelController extends BaseFormBean{
	//注入业务层
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private EvalScenceDlRelFeign evalScenceDlRelFeign;
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
		FormData formeval0005 = formService.getFormData("eval0005");
		EvalScenceDlRel evalScenceDlRel = new EvalScenceDlRel();
		Ipage ipage = this.getIpage();
		List<EvalScenceDlRel> evalScenceDlRelList = (List<EvalScenceDlRel>)evalScenceDlRelFeign.findByPage(ipage, evalScenceDlRel).getResult();
		model.addAttribute("evalScenceDlRelList", evalScenceDlRelList);
		model.addAttribute("formeval0005", formeval0005);
		model.addAttribute("query", "");
		return "getListPage";
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
		FormData formeval0005 = formService.getFormData("eval0005");
		EvalScenceDlRel evalScenceDlRel = new EvalScenceDlRel();
		List<EvalScenceDlRel> evalScenceDlRelList = evalScenceDlRelFeign.getAll(evalScenceDlRel);
		model.addAttribute("evalScenceDlRelList", evalScenceDlRelList);
		model.addAttribute("formeval0005", formeval0005);
		model.addAttribute("query", "");
		return "/component/eval/EvalScenceDlRel_List";
	}
	/**
	 * AJAX新增
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try{
		FormData 	formeval0005 = formService.getFormData("eval0005");
			JSONObject jb = JSONObject.fromObject(ajaxData);
			getFormValue(formeval0005, jb);
			if(this.validateFormData(formeval0005)){
		EvalScenceDlRel evalScenceDlRel = new EvalScenceDlRel();
				setObjValue(formeval0005, evalScenceDlRel);
				String javaItem = evalScenceDlRel.getJavaItem();
				String scenceNo = evalScenceDlRel.getScenceNo();
				/*if(false&&javaItem!=null&&!"".endsWith(javaItem)&&evalScenceDlRelFeign.getByJavaItem(scenceNo,javaItem).size()>0){
					dataMap.put("flag", "error");
					dataMap.put("msg",MessageEnum.EXIST_INFORMATION_EVAL.getMessage("该业务参数"));
				}else{*/
					evalScenceDlRelFeign.insert(evalScenceDlRel);
					dataMap.put("entityData", evalScenceDlRel);
					dataMap.put("flag", "success");
					dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
				//}
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
	@SuppressWarnings("unused")
	@RequestMapping(value = "/updateAjax")
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try{
		FormData 	formeval0005 = formService.getFormData("eval0005");
			JSONObject jb = JSONObject.fromObject(ajaxData);
			getFormValue(formeval0005, jb);
			if(this.validateFormData(formeval0005)){
		EvalScenceDlRel evalScenceDlRel = new EvalScenceDlRel();
				setObjValue(formeval0005, evalScenceDlRel);
				String javaItem = evalScenceDlRel.getJavaItem();
				String scenceNo = evalScenceDlRel.getScenceNo();
				boolean existsFlag = true;
				/*if(javaItem!=null&&!"".equals(javaItem)){
					List<EvalScenceDlRel> list = evalScenceDlRelFeign.getByJavaItem(scenceNo,javaItem);
					if(list.size()>0&&!evalScenceDlRel.getIndexNo().equals(list.get(0).getIndexNo())){
						dataMap.put("flag", "error");
						dataMap.put("msg",MessageEnum.EXIST_INFORMATION_EVAL.getMessage("该业务参数"));
						existsFlag = false;
					}
				}*/
				if(existsFlag){
					evalScenceDlRelFeign.update(evalScenceDlRel);
					dataMap.put("entityData", evalScenceDlRel);
					dataMap.put("flag", "success");
					dataMap.put("msg",MessageEnum.SUCCEED_UPDATE.getMessage());
				}
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
	public Map<String, Object> getByIdAjax(String indexNo,String scenceNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String,Object> formData = new HashMap<String,Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		FormData formeval0005 = formService.getFormData("eval0005");
		EvalScenceDlRel evalScenceDlRel = new EvalScenceDlRel();
		evalScenceDlRel.setIndexNo(indexNo);
		evalScenceDlRel.setScenceNo(scenceNo);
		evalScenceDlRel = evalScenceDlRelFeign.getById(evalScenceDlRel);
		getObjValue(formeval0005, evalScenceDlRel,formData);
		if(evalScenceDlRel!=null){
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
	public Map<String, Object> deleteAjax(String indexNo,String scenceNo) throws Exception{
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		EvalScenceDlRel evalScenceDlRel = new EvalScenceDlRel();
		evalScenceDlRel.setIndexNo(indexNo);
		evalScenceDlRel.setScenceNo(scenceNo);
		try {
			if(evalScenceDlRelFeign.getById(evalScenceDlRel)!=null){
				evalScenceDlRelFeign.delete(evalScenceDlRel);
				dataMap.put("flag", "success");
				dataMap.put("msg",MessageEnum.SUCCEED.getMessage());
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",MessageEnum.FAILED_DELETE.getMessage());
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.ERROR.getMessage());
			throw e;
		}
		return dataMap;
	}
}
