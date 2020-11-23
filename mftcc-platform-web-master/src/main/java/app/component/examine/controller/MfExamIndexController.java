package  app.component.examine.controller;
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

import app.component.common.EntityUtil;
import app.component.examine.entity.MfExamIndex;
import app.component.examine.entity.MfExamineIndexSub;
import app.component.examine.feign.MfExamIndexFeign;
import app.component.examine.feign.MfExamineIndexSubFeign;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Title: MfExamIndexAction.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Thu Feb 16 14:59:05 CST 2017
 **/
@RequestMapping("mfExamIndex")
@Controller
public class MfExamIndexController extends BaseFormBean{
	private static final long serialVersionUID = 9196454891709523438L;
	//注入MfExamIndexBo
	@Autowired
	private MfExamIndexFeign mfExamIndexFeign;
	@Autowired
	private MfExamineIndexSubFeign mfExamineIndexSubFeign;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	

	/**
	 * 列表打开页面请求
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getListPage")
	public String getListPage() throws Exception {
		ActionContext.initialize(request,response);
		return "MfExamIndex_List";
	}
	/***
	 * 列表数据查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findByPageAjax")
	@ResponseBody
	public Map<String,Object> findByPageAjax(String ajaxData,String tableId,String tableType,int pageNo) throws Exception {
		ActionContext.initialize(request,response);
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		MfExamIndex mfExamIndex = new MfExamIndex();
		try {
			mfExamIndex.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfExamIndex.setCriteriaList(mfExamIndex, ajaxData);//我的筛选
			//this.getRoleConditions(mfExamIndex,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			//自定义查询Bo方法
			ipage = mfExamIndexFeign.findByPage(ipage, mfExamIndex);
			JsonTableUtil jtu = new JsonTableUtil();
			String  tableHtml = jtu.getJsonStr(tableId,tableType,(List)ipage.getResult(), ipage,true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage",ipage);
			/**
			 	ipage.setResult(tableHtml);
				dataMap.put("ipage",ipage);
				需要改进的方法
				dataMap.put("tableData",tableHtml);
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
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/insertAjax")
	@ResponseBody
	public Map<String,Object> insertAjax(String ajaxData,String indexSubDataList) throws Exception {
		ActionContext.initialize(request,response);
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		FormService formService = new FormService();
		try{
			FormData formexamind0002 = formService.getFormData("examind0002");
			getFormValue(formexamind0002, getMapByJson(ajaxData));
			if(this.validateFormData(formexamind0002)){
				MfExamIndex mfExamIndex = new MfExamIndex();
				setObjValue(formexamind0002, mfExamIndex);
				JSONArray indexSubJsonArray = JSONArray.fromObject(indexSubDataList);
				Map<String,Object> paramMap = new HashMap<>();
				paramMap.put("mfExamIndex", mfExamIndex);
				paramMap.put("indexSubJsonArray", indexSubJsonArray);
				mfExamIndexFeign.insertExamIndexAndSub(paramMap);
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
	 * ajax 异步 单个字段或多个字段更新
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateAjaxByOne")
	@ResponseBody
	public Map<String,Object> updateAjaxByOne(String ajaxData) throws Exception{
		ActionContext.initialize(request,response);
		Map<String,Object> dataMap = new HashMap<String, Object>();
		FormService formService = new FormService();
		FormData formexamind0002 = formService.getFormData("examind0002");
		getFormValue(formexamind0002, getMapByJson(ajaxData));
		MfExamIndex mfExamIndexJsp = new MfExamIndex();
		setObjValue(formexamind0002, mfExamIndexJsp);
		MfExamIndex mfExamIndex = mfExamIndexFeign.getById(mfExamIndexJsp);
		if(mfExamIndex!=null){
			try{
				mfExamIndex = (MfExamIndex)EntityUtil.reflectionSetVal(mfExamIndex, mfExamIndexJsp, getMapByJson(ajaxData));
				//mfExamIndexFeign.update(mfExamIndex);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_SAVE.getMessage());
			}catch(Exception e){
				e.printStackTrace();
				dataMap.put("flag", "error");
				dataMap.put("msg",MessageEnum.ERROR_INSERT.getMessage());
				throw e;
			}
		}else{
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.FAILED_SAVE_CONTENT.getMessage("编号不存在"));
		}
		return dataMap;
	}
	/**
	 * AJAX更新保存
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateAjax")
	@ResponseBody
	public Map<String,Object> updateAjax(String ajaxData,String indexSubDataList) throws Exception {
		ActionContext.initialize(request,response);
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		MfExamIndex mfExamIndex = new MfExamIndex();
		FormService formService = new FormService();
		try{
			FormData formexamind0002 = formService.getFormData("examind0002");
			getFormValue(formexamind0002, getMapByJson(ajaxData));
			if(this.validateFormData(formexamind0002)){
				mfExamIndex = new MfExamIndex();
				setObjValue(formexamind0002, mfExamIndex);
				JSONArray indexSubJsonArray = JSONArray.fromObject(indexSubDataList);
				mfExamIndexFeign.update(mfExamIndex,indexSubJsonArray);
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
	
	
	@RequestMapping("/updateStsAjax")
	@ResponseBody
	public Map<String,Object> updateStsAjax(String ajaxData,String tableId) throws Exception {
		ActionContext.initialize(request,response);
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		MfExamIndex mfExamIndex = new MfExamIndex();
		FormService formService = new FormService();
		try{
			JSONObject jobj = JSONObject.fromObject(ajaxData);
			FormData formexamind0002 = formService.getFormData("examind0002");
			getFormValue(formexamind0002, jobj);
			setObjValue(formexamind0002, mfExamIndex);
			int count = mfExamIndexFeign.updateSts(mfExamIndex);
			if(count>0){
				mfExamIndex = mfExamIndexFeign.getById(mfExamIndex);
				List<MfExamIndex> mfExamIndexList = new ArrayList<MfExamIndex>();
				mfExamIndexList.add(mfExamIndex);
				getTableData(dataMap,mfExamIndexList,tableId);
				dataMap.put("flag", "success");
				dataMap.put("msg",MessageEnum.SUCCEED_UPDATE.getMessage());
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",MessageEnum.FAILED_UPDATE.getMessage());
			}
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.FAILED_UPDATE.getMessage());
			throw e;
		}
		return dataMap;
	}
	
	private void getTableData(Map<String,Object> dataMap,List<MfExamIndex> list,String tableId) throws Exception {
		JsonTableUtil jtu = new JsonTableUtil();
		String  tableHtml = jtu.getJsonStr(tableId,"thirdTableTag", list, null,true);
		dataMap.put("tableData",tableHtml);
	}
	/**
	 * AJAX获取查看
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getByIdAjax")
	@ResponseBody
	public Map<String,Object> getByIdAjax(String indexId) throws Exception {
		ActionContext.initialize(request,response);
		Map<String,Object> formData = new HashMap<String,Object>();
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		FormService formService = new FormService();
		FormData formexamind0002 = formService.getFormData("examind0002");
		MfExamIndex mfExamIndex = new MfExamIndex();
		mfExamIndex.setIndexId(indexId);
		mfExamIndex = mfExamIndexFeign.getById(mfExamIndex);
		getObjValue(formexamind0002, mfExamIndex,formData);
		if(mfExamIndex!=null){
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
	@RequestMapping("/deleteAjax")
	@ResponseBody
	public Map<String,Object> deleteAjax(String indexId) throws Exception{
		ActionContext.initialize(request,response);
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		MfExamIndex mfExamIndex = new MfExamIndex();
		mfExamIndex.setIndexId(indexId);
		try {
			mfExamIndexFeign.delete(mfExamIndex);
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
	 * 新增页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/input")
	public String input(Model model) throws Exception{
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		FormData formexamind0002 = formService.getFormData("examind0002");
		model.addAttribute("formexamind0002", formexamind0002);
		model.addAttribute("query", "");
		return "component/examine/MfExamIndex_Insert";
	}
	/***
	 * 新增
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/insert")
	public String insert(Model model) throws Exception{
		 ActionContext.initialize(request,response);
		 FormService formService = new FormService();
		 FormData formexamind0002 = formService.getFormData("examind0002");
		 getFormValue(formexamind0002);
		 MfExamIndex mfExamIndex = new MfExamIndex();
		 setObjValue(formexamind0002, mfExamIndex);
		 mfExamIndexFeign.insert(mfExamIndex);
		 getObjValue(formexamind0002, mfExamIndex);
		 this.addActionMessage(model,"保存成功");
		 List<MfExamIndex> mfExamIndexList = (List<MfExamIndex>)mfExamIndexFeign.findByPage(this.getIpage(), mfExamIndex).getResult();
		return "component/examine/MfExamIndex_Insert";
	}
	/**
	 * 查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getById")
	public String getById(String indexId) throws Exception{
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		FormData formexamind0002 = formService.getFormData("examind0002");
		 getFormValue(formexamind0002);
		 MfExamIndex mfExamIndex = new MfExamIndex();
		 mfExamIndex.setIndexId(indexId);
		 mfExamIndex = mfExamIndexFeign.getById(mfExamIndex);
		 getObjValue(formexamind0002, mfExamIndex);
		 MfExamineIndexSub mfExamineIndexSub=new MfExamineIndexSub();
		 mfExamineIndexSub.setIndexId(indexId);
		 List<MfExamineIndexSub> mfEvalIndexSubList=mfExamineIndexSubFeign.getMfEvalIndexSubList(mfExamineIndexSub);
		return "component/examine/MfExamIndex_Detail";
	}
	
	@RequestMapping("/getExamIndexsForModel")
	public String getExamIndexsForModel() throws Exception{
		ActionContext.initialize(request,response);
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		MfExamIndex mfExamIndex = new MfExamIndex();
		mfExamIndex.setUseFlag("1");
		List<MfExamIndex> mfExamIndexList = new ArrayList<MfExamIndex>();
		mfExamIndexList = mfExamIndexFeign.getMfExamIndexList(mfExamIndex);
		if(mfExamIndexList!=null&&mfExamIndexList.size()>0){
			dataMap.put("examIndexList", mfExamIndexList);
			JSONObject jb = JSONObject.fromObject(dataMap);
			dataMap = jb;
		}
		return "getExamIndexsForModel";
	}
	/**
	 * 
	 * 方法描述： 获得所有的检查指标
	 * @return
	 * @throws Exception
	 * String
	 * @author 沈浩兵
	 * @date 2017-7-24 下午3:32:34
	 */
	@RequestMapping("/getAllExamIndexsAjax")
	@ResponseBody
	public Map<String,Object> getAllExamIndexsAjax() throws Exception{
		ActionContext.initialize(request,response);
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		MfExamIndex mfExamIndex = new MfExamIndex();
		mfExamIndex.setUseFlag("1");
		List<MfExamIndex> mfExamIndexList = new ArrayList<MfExamIndex>();
		mfExamIndexList = mfExamIndexFeign.getMfExamIndexList(mfExamIndex);
		if(mfExamIndexList!=null&&mfExamIndexList.size()>0){
			dataMap.put("examIndexList", mfExamIndexList);
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED.getMessage());
		}
		CodeUtils cu =new CodeUtils();
		dataMap.put("riskLevelDatas", JSONObject.fromObject(cu.getMapObjByKeyName("EXAM_RISK_LEVEL")));
		return dataMap;
	}
	/**
	 * 删除
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delete")
	public String delete(String indexId) throws Exception {
		ActionContext.initialize(request,response);
		MfExamIndex mfExamIndex = new MfExamIndex();
		mfExamIndex.setIndexId(indexId);
		mfExamIndexFeign.delete(mfExamIndex);
		return getListPage();
	}
	
	
//	/**
//	 * 新增校验
//	 * @return
//	 * @throws Exception
//	 */
//	public void validateInsert() throws Exception{
//		ActionContext.initialize(request,response);
//		FormService formService = new FormService();
//		FormData formexamind0002 = formService.getFormData("examind0002");
//		getFormValue(formexamind0002);
//		boolean validateFlag = this.validateFormData(formexamind0002);
//	}
//	/**
//	 * 修改校验
//	 * @return
//	 * @throws Exception
//	 */
//	public void validateUpdate() throws Exception{
//		ActionContext.initialize(request,response);
//		FormService formService = new FormService();
//		FormData formexamind0002 = formService.getFormData("examind0002");
//		getFormValue(formexamind0002);
//		boolean validateFlag = this.validateFormData(formexamind0002);
//	}
	
	
	
}
