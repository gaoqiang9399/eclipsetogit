package  app.component.msgconf.controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.component.model.entity.MfTemplateTagBase;
import app.component.model.entity.MfTemplateTagSet;
import app.component.model.feign.MfNewTemplateTagBaseFeign;
import app.component.model.feign.MfNewTemplateTagSetFeign;
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

import app.component.examine.entity.MfExamineTemplateConfig;
import app.component.examinterface.ExamInterfaceFeign;
import app.component.msgconf.entity.MfMsgVar;
import app.component.msgconf.entity.PliWarning;
import app.component.msgconf.feign.MfMsgVarFeign;
import app.component.msgconf.feign.PliWarningFeign;
import app.component.sys.feign.SysRoleFeign;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.WaterIdUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Title: PliWarningAction.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Tue Jul 11 10:38:28 CST 2017
 **/
@Controller
@RequestMapping("/pliWarning")
public class PliWarningController extends BaseFormBean{
	//注入业务层
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private PliWarningFeign pliWarningFeign;
	@Autowired
	private MfMsgVarFeign mfMsgVarFeign;
	@Autowired
	private SysRoleFeign sysRoleFeign;
	//全局变量
	@Autowired
	private ExamInterfaceFeign examInterfaceFeign;
	@Autowired
	private MfNewTemplateTagBaseFeign mfNewTemplateTagBaseFeign;
	@Autowired
	private MfNewTemplateTagSetFeign mfNewTemplateTagSetFeign;
	//表单变量
	
	/**
	 * 获取列表数据
	 * @param tableId 
	 * @param pliWarning 
	 * @return
	 * @throws Exception
	 */
	private void getTableData(String tableId,Map<String,Object> dataMap, PliWarning pliWarning) throws Exception {
		JsonTableUtil jtu = new JsonTableUtil();
		String  tableHtml = jtu.getJsonStr(tableId,"tableTag", pliWarningFeign.getAll(pliWarning), null,true);
		dataMap.put("tableData",tableHtml);
	}
	
	private void getTableData(String tableId,Map<String,Object> dataMap,List<PliWarning> list) throws Exception {
		JsonTableUtil jtu = new JsonTableUtil();
		String tableHtml = jtu.getJsonStr(tableId, "thirdTableTag", list, null,
				true);
		dataMap.put("tableData", tableHtml);
	}
	/**
	 * 列表有翻页
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		FormData formpliwarn0002 = formService.getFormData("pliwarn0002");
		Ipage ipage = this.getIpage();
		ipage = pliWarningFeign.findByPage(ipage);
		model.addAttribute("formpliwarn0002", formpliwarn0002);
		model.addAttribute("ipage", ipage);
		model.addAttribute("query", "");
		return "/component/msgconf/PliWarning_List";
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
		FormData formpliwarn0002 = formService.getFormData("pliwarn0002");
		PliWarning pliWarning = new PliWarning();
		List<PliWarning> pliWarningList = pliWarningFeign.getAll(pliWarning);
		model.addAttribute("formpliwarn0002", formpliwarn0002);
		model.addAttribute("pliWarningList", pliWarningList);
		model.addAttribute("query", "");
		return "/component/msgconf/PliWarning_List";
	}
	/**
	 * 新增页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getInputPage")
	public String getInputPage(Model model, String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		FormData formpliwarn0002 = formService.getFormData("pliwarn0002");
		Map<String, Object> dataMap = new HashMap<String,Object>();
		List<MfExamineTemplateConfig> allExamineTemplate = examInterfaceFeign.getAllExamineTemplate();
		JSONArray examineTempArray = JSONArray.fromObject(allExamineTemplate);
		for (int i = 0; i < examineTempArray.size(); i++) {
			examineTempArray.getJSONObject(i).put("id",
					examineTempArray.getJSONObject(i).getString("templateId"));
			examineTempArray.getJSONObject(i).put("name",
					examineTempArray.getJSONObject(i).getString("templateName"));
		}
		dataMap.put("examineTempArray", examineTempArray);
		
		//发送对象
		JSONArray reciverTypeMap =new CodeUtils().getJSONArrayByKeyName("MSG_RECIVER_TYPE");
		String reciverTypeItems=reciverTypeMap.toString().replaceAll("optName", "name").replace("optCode", "id");
		dataMap.put("reciverTypeItems", reciverTypeItems);
			
		//发送方式
		JSONArray sendTypeMap =new CodeUtils().getJSONArrayByKeyName("MSG_SEND_TYPE");
		String sendTypeItems=sendTypeMap.toString().replaceAll("optName", "name").replace("optCode", "id");
		dataMap.put("sendTypeItems", sendTypeItems);

		//变量来源类型
		JSONArray varUsageMap =new CodeUtils().getJSONArrayByKeyName("MSG_VAR_USAGE");
		String varUserageItems=varUsageMap.toString().replaceAll("optName", "name").replace("optCode", "id");
		dataMap.put("varUserageItems", varUserageItems);
		model.addAttribute("formpliwarn0002", formpliwarn0002);
		model.addAttribute("dataMap", dataMap);
		model.addAttribute("query", "");
		return "/component/msgconf/PliWarning_Insert";
	}
	
	@RequestMapping(value = "/getModelInputPage")
	public String getModelInputPage(Model model, String pliWarnNo,String varUsage) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		FormData formpliwarnmodel0002 = formService.getFormData("pliwarnmodel0002");
		PliWarning pliWarning = new PliWarning();
		pliWarning.setPliWarnNo(pliWarnNo);
		pliWarning = pliWarningFeign.getById(pliWarning);
		getObjValue(formpliwarnmodel0002, pliWarning);
		
		Map<String, String> vumap =new CodeUtils().getMapByKeyName("BOOKMARK_CLASS");
		MfMsgVar mfMsgVar = new MfMsgVar();
		List<MfMsgVar> mmvlist = null;
		Map<String, List<MfMsgVar>> vuListMap = new HashMap<String, List<MfMsgVar>>();
		//获取已经设置的标签信息
		MfTemplateTagSet mfTemplateTagSet=new MfTemplateTagSet();
		mfTemplateTagSet.setTemplateNo(pliWarnNo);
		mfTemplateTagSet=mfNewTemplateTagSetFeign.getById(mfTemplateTagSet);
		if(mfTemplateTagSet!=null){
			//根据设置的标签好查询标签信息
			String keyNo=mfTemplateTagSet.getTagKeyNo();
			String[] keyNos=keyNo.split("@");
			for(String key:keyNos){
				MfTemplateTagBase mfTemplateTagBase=new MfTemplateTagBase();
				mfTemplateTagBase.setKeyNo(key);
				mfTemplateTagBase=mfNewTemplateTagBaseFeign.getById(mfTemplateTagBase);
				String vuStr=mfTemplateTagBase.getGroupFlag();
				String mapKey=vuStr + "_" + vumap.get(vuStr);
				List list=new ArrayList();
				MfTemplateTagBase mfTemplateTagBase2=new MfTemplateTagBase();
				mfTemplateTagBase2.setKeyNo(mfTemplateTagBase.getKeyNo());
				mfTemplateTagBase2.setTagKeyName(mfTemplateTagBase.getTagKeyName());
				if(vuListMap.get(mapKey)!=null){
					list=vuListMap.get(mapKey);
					list.add(mfTemplateTagBase2);
				}else{
					list.add(mfTemplateTagBase2);
				}
				vuListMap.put(mapKey, list);
			}
		}else{
			model.addAttribute("msg", "请先设置标签信息");
		}

		JSONObject json = new JSONObject();
		
		json.put("vuListMap", JSONObject.fromObject(vuListMap));
		
		String ajaxData = json.toString();
		
		model.addAttribute("formpliwarnmodel0002", formpliwarnmodel0002);
		model.addAttribute("ajaxData", ajaxData);
		model.addAttribute("query", "");
		return "/component/msgconf/Model_Input";
	}
	/**
	 * AJAX获取查看
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getDetailPage")
	public String getDetailPage(Model model, String pliWarnNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<MfExamineTemplateConfig> allExamineTemplate = examInterfaceFeign.getAllExamineTemplate();
		JSONArray examineTempArray = JSONArray.fromObject(allExamineTemplate);
		for (int i = 0; i < examineTempArray.size(); i++) {
			examineTempArray.getJSONObject(i).put("id",
					examineTempArray.getJSONObject(i).getString("templateId"));
			examineTempArray.getJSONObject(i).put("name",
					examineTempArray.getJSONObject(i).getString("templateName"));
		}
		dataMap.put("examineTempArray", examineTempArray);
		//发送对象
		JSONArray reciverTypeMap =new CodeUtils().getJSONArrayByKeyName("MSG_RECIVER_TYPE");
		String reciverTypeItems=reciverTypeMap.toString().replaceAll("optName", "name").replace("optCode", "id");
		dataMap.put("reciverTypeItems", reciverTypeItems);
					
		//发送方式
		JSONArray sendTypeMap =new CodeUtils().getJSONArrayByKeyName("MSG_SEND_TYPE");
		String sendTypeItems=sendTypeMap.toString().replaceAll("optName", "name").replace("optCode", "id");
		dataMap.put("sendTypeItems", sendTypeItems);

		//变量来源类型
		JSONArray varUsageMap =new CodeUtils().getJSONArrayByKeyName("MSG_VAR_USAGE");
		String varUserageItems=varUsageMap.toString().replaceAll("optName", "name").replace("optCode", "id");
		dataMap.put("varUserageItems", varUserageItems);
		
		FormData formpliwarn0002 = formService.getFormData("pliwarn0002");
		PliWarning pliWarning = new PliWarning();
		pliWarning.setPliWarnNo(pliWarnNo);
		pliWarning = pliWarningFeign.getById(pliWarning);
		getObjValue(formpliwarn0002, pliWarning);
		
		model.addAttribute("formpliwarn0002", formpliwarn0002);
		model.addAttribute("dataMap", dataMap);
		model.addAttribute("query", "");
		return "/component/msgconf/PliWarning_Detail";
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
		String args ="";
		try{
		FormData 	formpliwarn0002 = formService.getFormData("pliwarn0002");
			getFormValue(formpliwarn0002, getMapByJson(ajaxData));
			if(this.validateFormData(formpliwarn0002)){
		PliWarning pliWarning = new PliWarning();
				setObjValue(formpliwarn0002, pliWarning);
				pliWarning.setPliWarnNo(WaterIdUtil.getWaterId());
				pliWarning.setFlag("0");
				args = getArgsStr(pliWarning.getVarUsageRel(), pliWarning.getPliWarnContent());
				pliWarning.setPliContentArgs(args);
				pliWarningFeign.insert(pliWarning);
				getTableData(tableId, dataMap, pliWarning);//获取列表
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
	@RequestMapping(value = "/updateAjaxp")
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData,String tableId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		PliWarning pliWarning = new PliWarning();
		try{
		FormData 	formpliwarn0002 = formService.getFormData("pliwarn0002");
			getFormValue(formpliwarn0002, getMapByJson(ajaxData));
			if(this.validateFormData(formpliwarn0002)){
				setObjValue(formpliwarn0002, pliWarning);
				String args = getArgsStr(pliWarning.getVarUsageRel(), pliWarning.getPliWarnContent());
				pliWarning.setPliContentArgs(args);
				pliWarningFeign.update(pliWarning);
				getTableData(tableId, dataMap, pliWarning);//获取列表
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
	 * AJAX更新保存
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateFlagAjax")
	@ResponseBody
	public Map<String, Object> updateFlagAjax(String ajaxData,String tableId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			JSONObject jobj = JSONObject.fromObject(ajaxData);
		FormData 	formpliwarn0002 = formService.getFormData("pliwarn0002");
			getFormValue(formpliwarn0002, jobj);
		PliWarning 	pliWarning = new PliWarning();
			setObjValue(formpliwarn0002, pliWarning);
			int count = pliWarningFeign.updateFlag(pliWarning);
			if (count > 0) {
				pliWarning = pliWarningFeign.getById(pliWarning);
		ArrayList<PliWarning> pliWarningList = new ArrayList<PliWarning>();
				pliWarningList.add(pliWarning);
				getTableData(tableId, dataMap, pliWarningList);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_UPDATE.getMessage());
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", MessageEnum.FAILED_UPDATE.getMessage());
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
	 * 列表有翻页
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType, String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PliWarning pliWarning = new PliWarning();
		pliWarning.setCustomQuery(ajaxData);
		pliWarning.setCriteriaList(pliWarning, ajaxData);
		Ipage ipage = this.getIpage();
		ipage.setPageSize(pageSize);
		ipage.setPageNo(pageNo);
 		ipage.setParams(this.setIpageParams("pliWarning", pliWarning));
		ipage = pliWarningFeign.findByPage(ipage);
		JsonTableUtil jtu = new JsonTableUtil();
		String tableHtml = jtu.getJsonStr(tableId, tableType,
				(List) ipage.getResult(), ipage, true);
		ipage.setResult(tableHtml);
		dataMap.put("ipage", ipage);
		return dataMap;
	}
	/**
	 * AJAX获取查看
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String pliWarnNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String,Object> formData = new HashMap<String,Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		FormData formpliwarn0002 = formService.getFormData("pliwarn0002");
		PliWarning pliWarning = new PliWarning();
		pliWarning.setPliWarnNo(pliWarnNo);
		pliWarning = pliWarningFeign.getById(pliWarning);
		getObjValue(formpliwarn0002, pliWarning,formData);
		if(pliWarning!=null){
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
	public Map<String, Object> deleteAjax(String pliWarnNo,String ajaxData,String tableId) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		PliWarning pliWarning = new PliWarning();
		pliWarning.setPliWarnNo(pliWarnNo);
		try {
			JSONObject jb = JSONObject.fromObject(ajaxData);
			pliWarning = (PliWarning)JSONObject.toBean(jb, PliWarning.class);
			pliWarningFeign.delete(pliWarning);
			getTableData(tableId, dataMap, pliWarning);//获取列表
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

	
	private String getArgsStr(String varUsageTem, String modelContentTem) throws Exception{
		if(varUsageTem.isEmpty()||modelContentTem.isEmpty()){
			return "";
		}
		String argsStr = "";
		String varNameTem = "";
		String varUsageTemStr = "";
		Set<String> argsStrSet = new HashSet<String>();
		Map<String, String> argsStrMap = new HashMap<String, String>();
		//对变量来源类型进行处理
		String[] strArr = varUsageTem.split("\\|");
		for(String str:strArr){
			if(!"".equals(str)){
				varUsageTemStr = varUsageTemStr + ",'" + str + "'";
			}
		}
		if(varUsageTemStr.startsWith(",")){
			varUsageTemStr = varUsageTemStr.substring(1);
		}
		varUsageTemStr = "(" + varUsageTemStr + ")";
		MfMsgVar mfMsgVar = new MfMsgVar();
		mfMsgVar.setVarUsage(varUsageTemStr);
		List<MfMsgVar> mmvlist = mfMsgVarFeign.getListByVarUsages(mfMsgVar);
		for(MfMsgVar mmv:mmvlist){
			if("02".equals(mmv.getVarType())){
//				argsStr = argsStr + "|" + mmv.getVarId();
				if(null != argsStrMap.get(mmv.getVarTb())){
					argsStrMap.put(mmv.getVarTb(), argsStrMap.get(mmv.getVarTb())+"|" + mmv.getVarId());
				}else{
					argsStrMap.put(mmv.getVarTb(), "|" + mmv.getVarId());
				}
				continue;
			}
			varNameTem = mmv.getVarName();
			varNameTem = "{" + varNameTem + "}";
			if(modelContentTem.contains(varNameTem)){
				argsStr = argsStr + "|" + mmv.getVarId();
				argsStrSet.add(mmv.getVarTb());
			}
		}
		//set遍历
		for(String str:argsStrSet){
			if(null != argsStrMap.get(str)){
				argsStr = argsStr + argsStrMap.get(str);
			}
		} 
		
		if(argsStr.startsWith("|")){
			argsStr = argsStr.substring(1);
		}
		return argsStr;
	}
	
}
