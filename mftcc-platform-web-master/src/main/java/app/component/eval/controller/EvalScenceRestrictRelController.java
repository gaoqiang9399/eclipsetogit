
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

import app.component.eval.entity.EvalScenceRestrictRel;
import app.component.eval.feign.EvalScenceRestrictRelFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import net.sf.json.JSONObject;

/**
 * Title: EvalScenceRestrictRelAction.java
 * Description:
 * @author:@dhcc.com.cn
 * @Tue Mar 22 01:41:22 GMT 2016
 **/
@Controller
@RequestMapping("/evalScenceRestrictRel")
public class EvalScenceRestrictRelController extends BaseFormBean{
	//注入业务层
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private EvalScenceRestrictRelFeign evalScenceRestrictRelFeign;
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
		FormData formeval0006 = formService.getFormData("eval0006");
		EvalScenceRestrictRel evalScenceRestrictRel = new EvalScenceRestrictRel();
		Ipage ipage = this.getIpage();
		List<EvalScenceRestrictRel> evalScenceRestrictRelList = (List<EvalScenceRestrictRel>)evalScenceRestrictRelFeign.findByPage(ipage, evalScenceRestrictRel).getResult();
		model.addAttribute("evalScenceRestrictRelList", evalScenceRestrictRelList);
		model.addAttribute("formeval0006", formeval0006);
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
		FormData formeval0006 = formService.getFormData("eval0006");
		EvalScenceRestrictRel evalScenceRestrictRel = new EvalScenceRestrictRel();
		List<EvalScenceRestrictRel> evalScenceRestrictRelList = evalScenceRestrictRelFeign.getAll(evalScenceRestrictRel);
		model.addAttribute("formeval0006", formeval0006);
		model.addAttribute("evalScenceRestrictRelList", evalScenceRestrictRelList);
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
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try{
		FormData 	formeval0006 = formService.getFormData("eval0006");
			JSONObject jb = JSONObject.fromObject(ajaxData);
			getFormValue(formeval0006, jb);
			if(this.validateFormData(formeval0006)){
		EvalScenceRestrictRel evalScenceRestrictRel = new EvalScenceRestrictRel();
				setObjValue(formeval0006, evalScenceRestrictRel);
				String javaItem = evalScenceRestrictRel.getJavaItem();
				String scenceNo = evalScenceRestrictRel.getScenceNo();
				if(javaItem!=null&&!"".equals(javaItem)&&evalScenceRestrictRelFeign.getForJavaItem(scenceNo,javaItem).size()>0){
					dataMap.put("flag", "error");
					dataMap.put("msg",MessageEnum.EXIST_INFORMATION_EVAL.getMessage("该业务参数"));
				}else{
					evalScenceRestrictRelFeign.insert(evalScenceRestrictRel);
					dataMap.put("entityData", evalScenceRestrictRel);
					dataMap.put("flag", "success");
					dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
				}
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
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try{
		FormData 	formeval0006 = formService.getFormData("eval0006");
			JSONObject jb = JSONObject.fromObject(ajaxData);
			getFormValue(formeval0006, jb);
			if(this.validateFormData(formeval0006)){
		EvalScenceRestrictRel evalScenceRestrictRel = new EvalScenceRestrictRel();
				setObjValue(formeval0006, evalScenceRestrictRel);
				String javaItem = evalScenceRestrictRel.getJavaItem();
				String scenceNo = evalScenceRestrictRel.getScenceNo();
				boolean existsFlag = true;
				if(javaItem!=null&&!"".equals(javaItem)){
					List<EvalScenceRestrictRel> list = evalScenceRestrictRelFeign.getForJavaItem(scenceNo,javaItem);
					if(list.size()>0&&!evalScenceRestrictRel.getIndexNo().equals(list.get(0).getIndexNo())){
						dataMap.put("flag", "error");
						dataMap.put("msg",MessageEnum.EXIST_INFORMATION_EVAL.getMessage("该业务参数"));
						existsFlag = false;
					}
				}
				if(existsFlag){
					evalScenceRestrictRelFeign.update(evalScenceRestrictRel);
					dataMap.put("entityData", evalScenceRestrictRel);
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
	public Map<String, Object> getByIdAjax(String ajaxData,String indexNo,String scenceNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String,Object> formData = new HashMap<String,Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		FormData formeval0006 = formService.getFormData("eval0006");
		EvalScenceRestrictRel evalScenceRestrictRel = new EvalScenceRestrictRel();
		evalScenceRestrictRel.setIndexNo(indexNo);
		evalScenceRestrictRel.setScenceNo(scenceNo);
		evalScenceRestrictRel = evalScenceRestrictRelFeign.getById(evalScenceRestrictRel);
		getObjValue(formeval0006, evalScenceRestrictRel,formData);
		if(evalScenceRestrictRel!=null){
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
	public Map<String, Object> deleteAjax(String ajaxData,String indexNo,String scenceNo) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		EvalScenceRestrictRel evalScenceRestrictRel = new EvalScenceRestrictRel();
		evalScenceRestrictRel.setIndexNo(indexNo);
		evalScenceRestrictRel.setScenceNo(scenceNo);
		try {
			evalScenceRestrictRelFeign.delete(evalScenceRestrictRel);
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
