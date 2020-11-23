package  app.component.examine.controller;
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

import app.component.common.EntityUtil;
import app.component.examine.entity.MfExamineCard;
import app.component.examine.entity.MfExamineTemplateIndexRel;
import app.component.examine.feign.MfExamineCardFeign;
import app.component.examine.feign.MfExamineTemplateIndexRelFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;

/**
 * Title: MfExamineCardAction.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Mon Jul 24 14:26:01 CST 2017
 **/
@Controller
@RequestMapping(value="/mfExamineCard")
public class MfExamineCardController extends BaseFormBean{
	//注入MfExamineCardBo
	@Autowired
	private MfExamineCardFeign mfExamineCardFeign;
	@Autowired
	private MfExamineTemplateIndexRelFeign mfExamineTemplateIndexRelFeign;

	//异步参数
	private Map<String,Object> dataMap;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	
	/**
	 * 列表打开页面请求
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getListPage")
	public String getListPage() throws Exception {
		ActionContext.initialize(request,
				response);
		return "MfExamineCard_List";
	}
	/***
	 * 列表数据查询
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/findByPageAjax")
	public Map<String,Object> findByPageAjax(String ajaxData,int pageNo,String tableId,String tableType) throws Exception {
		ActionContext.initialize(request,
				response);
		
		dataMap = new HashMap<String, Object>(); 
		MfExamineCard mfExamineCard = new MfExamineCard();
		try {
			mfExamineCard.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfExamineCard.setCriteriaList(mfExamineCard, ajaxData);//我的筛选
			//mfExamineCard.setCustomSorts(ajaxData);//自定义排序
			//this.getRoleConditions(mfExamineCard,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			//自定义查询Bo方法
			ipage = mfExamineCardFeign.findByPage(ipage, mfExamineCard);
			JsonTableUtil jtu = new JsonTableUtil();
			String  tableHtml = jtu.getJsonStr(tableId,tableType,(List)ipage.getResult(), ipage,true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage",ipage);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw new Exception(e.getMessage());
		}
		return dataMap;
	}
	
	/**
	 * AJAX新增
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/insertAjax")
	public Map<String,Object> insertAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request,
				response);
		FormService formService = new FormService();
		dataMap = new HashMap<String, Object>(); 
		try{
			FormData formexaminecard0001 = formService.getFormData("examinecard0001");
			getFormValue(formexaminecard0001, getMapByJson(ajaxData));
			if(this.validateFormData(formexaminecard0001)){
				MfExamineCard mfExamineCard = new MfExamineCard();
				setObjValue(formexaminecard0001, mfExamineCard);
				mfExamineCard=mfExamineCardFeign.insert(mfExamineCard);
				dataMap.put("examCard", mfExamineCard);
				dataMap.put("flag", "success");
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
	 * ajax 异步 单个字段或多个字段更新
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/updateAjaxByOne")
	public Map<String,Object> updateAjaxByOne(String ajaxData) throws Exception{
		ActionContext.initialize(request,
				response);
		dataMap = new HashMap<String, Object>();
		FormService formService = new FormService();
		FormData formexaminecard0002 = formService.getFormData("examinecard0002");
		getFormValue(formexaminecard0002, getMapByJson(ajaxData));
		MfExamineCard mfExamineCardJsp = new MfExamineCard();
		setObjValue(formexaminecard0002, mfExamineCardJsp);
		MfExamineCard mfExamineCard = mfExamineCardFeign.getById(mfExamineCardJsp);
		if(mfExamineCard!=null){
			try{
				mfExamineCard = (MfExamineCard)EntityUtil.reflectionSetVal(mfExamineCard, mfExamineCardJsp, getMapByJson(ajaxData));
				mfExamineCardFeign.update(mfExamineCard);
				dataMap.put("flag", "success");
				dataMap.put("msg", "保存成功");
			}catch(Exception e){
				e.printStackTrace();
				dataMap.put("flag", "error");
				dataMap.put("msg", "新增失败");
				throw new Exception(e.getMessage());
			}
		}else{
			dataMap.put("flag", "error");
			dataMap.put("msg", "编号不存在,保存失败");
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
	public Map<String,Object> updateAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request,
				response);
		FormService formService = new FormService();
		dataMap = new HashMap<String, Object>(); 
		MfExamineCard mfExamineCard = new MfExamineCard();
		try{
			FormData formexaminecard0001 = formService.getFormData("examinecard0001");
			getFormValue(formexaminecard0001, getMapByJson(ajaxData));
			if(this.validateFormData(formexaminecard0001)){
				mfExamineCard = new MfExamineCard();
				setObjValue(formexaminecard0001, mfExamineCard);
				mfExamineCardFeign.update(mfExamineCard);
				dataMap.put("examCard", mfExamineCard);
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
	@ResponseBody
	@RequestMapping(value="/getByIdAjax")
	public Map<String,Object> getByIdAjax(String examineCardId) throws Exception {
		ActionContext.initialize(request,
				response);
		FormService formService = new FormService();
		Map<String,Object> formData = new HashMap<String,Object>();
		dataMap = new HashMap<String, Object>(); 
		FormData formexaminecard0002 = formService.getFormData("examinecard0002");
		MfExamineCard mfExamineCard = new MfExamineCard();
		mfExamineCard.setExamineCardId(examineCardId);
		mfExamineCard = mfExamineCardFeign.getById(mfExamineCard);
		getObjValue(formexaminecard0002, mfExamineCard,formData);
		if(mfExamineCard!=null){
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
	public Map<String,Object> deleteAjax(String examineCardId) throws Exception{
		ActionContext.initialize(request,
				response);
		FormService formService = new FormService();
		dataMap = new HashMap<String, Object>(); 
		MfExamineCard mfExamineCard = new MfExamineCard();
		mfExamineCard.setExamineCardId(examineCardId);
		try {
			MfExamineTemplateIndexRel examineTemplateIndexRel =new MfExamineTemplateIndexRel();
			examineTemplateIndexRel.setExamineCardId(examineCardId);
			List<MfExamineTemplateIndexRel> list=mfExamineTemplateIndexRelFeign.getTemplateIndexRel(examineTemplateIndexRel);
 			if(list!=null&&list.size()>0){
 				dataMap.put("flag", "error");
 				dataMap.put("msg", "检查卡已配置检查指标,不能直接删除");
 			}else{
 				mfExamineCardFeign.delete(mfExamineCard);
 				dataMap.put("flag", "success");
 				dataMap.put("msg",MessageEnum.SUCCEED_DELETE.getMessage());
 			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			dataMap.put("msg", "失败");
			throw new Exception(e.getMessage());
		}
		return dataMap;
	}
	/**
	 * 新增页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/input")
	public String input(Model model,String examTemplateId) throws Exception{
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		FormData formexaminecard0001 = formService.getFormData("examinecard0001");
		MfExamineCard mfExamineCard = new MfExamineCard();
		mfExamineCard.setExamTemplateId(examTemplateId);
		getObjValue(formexaminecard0001, mfExamineCard);
		model.addAttribute("mfExamineCard", mfExamineCard);
		model.addAttribute("formexaminecard0001", formexaminecard0001);
		model.addAttribute("query", "");
		return "component/examine/MfExamineCard_Insert";
	}
	/***
	 * 新增
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/insert")
	public String insert(Model model) throws Exception{
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		FormData formexaminecard0002 = formService.getFormData("examinecard0002");
		 getFormValue(formexaminecard0002);
		 MfExamineCard mfExamineCard = new MfExamineCard();
		 setObjValue(formexaminecard0002, mfExamineCard);
		 mfExamineCardFeign.insert(mfExamineCard);
		 getObjValue(formexaminecard0002, mfExamineCard);
		 this.addActionMessage(model,"保存成功");
		 List<MfExamineCard> mfExamineCardList = (List<MfExamineCard>)mfExamineCardFeign.findByPage(this.getIpage(), mfExamineCard).getResult();
		 model.addAttribute("mfExamineCardList", mfExamineCardList);
		 model.addAttribute("mfExamineCard", mfExamineCard);
		return "component/examine/MfExamineCard_Insert";
	}
	/**
	 * 查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getById")
	public String getById(Model model,String examineCardId) throws Exception{
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		FormData formexaminecard0001 = formService.getFormData("examinecard0001");
		 getFormValue(formexaminecard0001);
		 MfExamineCard mfExamineCard = new MfExamineCard();
		mfExamineCard.setExamineCardId(examineCardId);
		 mfExamineCard = mfExamineCardFeign.getById(mfExamineCard);
		 getObjValue(formexaminecard0001, mfExamineCard);
		 model.addAttribute("mfExamineCard", mfExamineCard);
		 model.addAttribute("formexaminecard0001", formexaminecard0001);
		 model.addAttribute("query", "");
		return "component/examine/MfExamineCard_Detail";
	}
	/**
	 * 删除
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/delete")
	public String delete(String examineCardId) throws Exception {
		ActionContext.initialize(request,
				response);
		MfExamineCard mfExamineCard = new MfExamineCard();
		mfExamineCard.setExamineCardId(examineCardId);
		mfExamineCardFeign.delete(mfExamineCard);
		return getListPage();
	}
	/**
	 * 新增校验
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/validateInsert")
	public void validateInsert() throws Exception{
		 ActionContext.initialize(request, response);
		 FormService formService = new FormService();
		 FormData formexaminecard0002 = formService.getFormData("examinecard0002");
		 getFormValue(formexaminecard0002);
		 boolean validateFlag = this.validateFormData(formexaminecard0002);
	}
	/**
	 * 修改校验
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/validateUpdate")
	public void validateUpdate() throws Exception{
		 ActionContext.initialize(request, response);
		 FormService formService = new FormService();
		 FormData formexaminecard0002 = formService.getFormData("examinecard0002");
		 getFormValue(formexaminecard0002);
		 boolean validateFlag = this.validateFormData(formexaminecard0002);
	}
	
	


	
}
