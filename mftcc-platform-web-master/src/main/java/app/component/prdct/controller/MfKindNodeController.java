package  app.component.prdct.controller;
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

import app.component.prdct.entity.MfKindNode;
import app.component.prdct.feign.MfKindNodeFeign;
import cn.mftcc.common.MessageEnum;
import net.sf.json.JSONArray;

/**
 * Title: MfKindNodeAction.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Sun Jul 02 16:48:46 CST 2017
 **/
@Controller
@RequestMapping("/mfKindNode")
public class MfKindNodeController extends BaseFormBean{
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	//注入MfKindNodeBo
	@Autowired
	private MfKindNodeFeign mfKindNodeFeign;
	//全局变量
	//异步参数
	//表单变量
	
	/**
	 * 
	 * 方法描述： 获取产品设置页面中的节点定制弹层数据
	 * @return
	 * @throws Exception
	 * String
	 * @author zhs
	 * @date 2017-7-4 下午3:45:11
	 */
	@RequestMapping(value = "/getKindNodeList")
    public String getKindNodeList(Model model, String ajaxData, String busModel, String kindNo, String configType, String nodeNo, String mainApprove) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		MfKindNode mfKindNode = new MfKindNode();
		mfKindNode.setBusModel(busModel);
		mfKindNode.setKindNo(kindNo);
		mfKindNode.setConfigType(configType);
        //如果是审批子流程则会传这两个参数
        mfKindNode.setNodeNo(nodeNo);
        mfKindNode.setMainApprove(mainApprove);

		List<MfKindNode> mfKindNodeList = mfKindNodeFeign.getKindNodeList(mfKindNode);
		JSONArray jsonArray = JSONArray.fromObject(mfKindNodeList);
		for(int index=0;index<jsonArray.size();index++){
			jsonArray.getJSONObject(index).put("id", jsonArray.getJSONObject(index).getString("nodeNo"));
			jsonArray.getJSONObject(index).put("name", jsonArray.getJSONObject(index).getString("nodeName"));
			jsonArray.getJSONObject(index).put("useFlag", jsonArray.getJSONObject(index).getString("ext1"));
		}
		ajaxData = jsonArray.toString();
		model.addAttribute("ajaxData", ajaxData);
		model.addAttribute("jsonArray", jsonArray);
		model.addAttribute("query", "");
		return "/component/prdct/MfKindNode_List";
	}
	/**
     *
	 * 方法描述： 获取产品设置页面中的节点的设置挂载情况
	 * @return
	 * @throws Exception
	 * String
	 * @author zhs
	 * @date 2017-7-4 下午3:45:11
	 */
	@RequestMapping(value = "/getNodeSetList")
	public String getNodeSetList(Model model, String ajaxData,String kindNodeId,String kindNo) throws Exception {
		ActionContext.initialize(request,
				response);
		MfKindNode mfKindNode = new MfKindNode();
		mfKindNode.setKindNodeId(kindNodeId);
		mfKindNode.setKindNo(kindNo);
		List<MfKindNode> mfKindNodeList = mfKindNodeFeign.getNodeSetList(mfKindNode);
		JSONArray jsonArray = JSONArray.fromObject(mfKindNodeList);
		for(int index=0;index<jsonArray.size();index++){
			jsonArray.getJSONObject(index).put("id", jsonArray.getJSONObject(index).getString("nodeNo"));
			jsonArray.getJSONObject(index).put("name", jsonArray.getJSONObject(index).getString("nodeName"));
			jsonArray.getJSONObject(index).put("useFlag", jsonArray.getJSONObject(index).getString("ext1"));
		}
		ajaxData = jsonArray.toString();
		model.addAttribute("ajaxData", ajaxData);
		model.addAttribute("jsonArray", jsonArray);
		model.addAttribute("query", "");
		return "/component/prdct/MfKindNode_List";
	}

	/**
     *
	 * 方法描述： 定制节点，插入数据
	 * @return
	 * @throws Exception
	 * String
	 * @author zhs
	 * @date 2017-7-5 下午7:32:27
	 */
	@RequestMapping(value = "/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String busModel,String nodeNo,String kindNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
		try{
            MfKindNode mfKindNode = new MfKindNode();
			mfKindNode.setBusModel(busModel);
			mfKindNode.setKindNo(kindNo);
			mfKindNode.setNodeNo(nodeNo);
			dataMap = mfKindNodeFeign.insert(mfKindNode);
			dataMap.put("flag", "success");
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "新增失败");
			throw e;
		}
		return dataMap;
	}
	/**
     *
	 * 方法描述： 产品下费用、影像、模板、表单配置定制节点保存
	 * @return
	 * @throws Exception
	 * String
	 * @author zhs
	 * @date 2018-3-16 下午6:06:08
	 */
	@RequestMapping(value = "/insertForPrdctAjax")
	@ResponseBody
    public Map<String, Object> insertForPrdctAjax(String busModel, String nodeNo, String kindNo, String configType, String nodeNoNew) throws Exception {
		ActionContext.initialize(request,response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
		try{
			MfKindNode mfKindNode = new MfKindNode();
			mfKindNode.setBusModel(busModel);
			mfKindNode.setKindNo(kindNo);
			mfKindNode.setNodeNo(nodeNo);
			mfKindNode.setConfigType(configType);
            mfKindNode.setExt10(nodeNoNew);
			dataMap = mfKindNodeFeign.insertForPrdct(mfKindNode);
			dataMap.put("flag", "success");
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "新增失败");
			throw e;
		}
		return dataMap;
	}


    /**
	 * Ajax异步删除
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String kindNodeId) throws Exception{

		ActionContext.initialize(request,
				response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
		MfKindNode mfKindNode = new MfKindNode();
		mfKindNode.setKindNodeId(kindNodeId);
		try {
			mfKindNodeFeign.delete(mfKindNode);
			dataMap.put("flag", "success");
			dataMap.put("msg", "成功");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			dataMap.put("msg", "失败");
			throw e;
		}
		return dataMap;
	}

	/**
	 * 查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateAjax")
	@ResponseBody
	public Map<String, Object> updateAjax(String kindNodeId,String nodeNo,String kindNo) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
		try{
            MfKindNode mfKindNode = new MfKindNode();
			mfKindNode.setKindNodeId(kindNodeId);
			mfKindNode.setKindNo(kindNo);
			mfKindNode.setNodeNo(nodeNo);
			dataMap = mfKindNodeFeign.update(mfKindNode);
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED_OPERATION.getMessage("配置"));
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_OPERATION.getMessage("配置"));
			throw e;
		}
		return dataMap;
	}
	/**
	 * 新增校验
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateInsert")
	public void validateInsert(Model model) throws Exception{
		FormService formService = new FormService();
        ActionContext.initialize(request, response);
		FormData  formkindnode0002 = formService.getFormData("kindnode0002");
        getFormValue(formkindnode0002);
        boolean validateFlag = this.validateFormData(formkindnode0002);
	}
	/**
	 * 修改校验
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateUpdate")
	public void validateUpdate(Model model) throws Exception{
		FormService formService = new FormService();
        ActionContext.initialize(request, response);
		FormData  formkindnode0002 = formService.getFormData("kindnode0002");
        getFormValue(formkindnode0002);
        boolean validateFlag = this.validateFormData(formkindnode0002);
	}

	@RequestMapping(value = "/getCreditNodeList")
	public String getCreditNodeList(Model model, String ajaxData, String busModel, String kindNo, String configType, String mainApprove, String nodeNo) throws Exception {
		ActionContext.initialize(request,response);
		MfKindNode mfKindNode = new MfKindNode();
		mfKindNode.setBusModel(busModel);
		mfKindNode.setKindNo(kindNo);
		mfKindNode.setConfigType(configType);
        //如果是审批子流程则会传这两个参数
        mfKindNode.setNodeNo(nodeNo);
        mfKindNode.setMainApprove(mainApprove);
		List<MfKindNode> mfKindNodeList = mfKindNodeFeign.getCreditNodeList(mfKindNode);
		JSONArray jsonArray = JSONArray.fromObject(mfKindNodeList);
		for(int index=0;index<jsonArray.size();index++){
			jsonArray.getJSONObject(index).put("id", jsonArray.getJSONObject(index).getString("nodeNo"));
			jsonArray.getJSONObject(index).put("name", jsonArray.getJSONObject(index).getString("nodeName"));
			jsonArray.getJSONObject(index).put("useFlag", jsonArray.getJSONObject(index).getString("ext1"));
		}
		ajaxData = jsonArray.toString();
		model.addAttribute("ajaxData", ajaxData);
		model.addAttribute("jsonArray", jsonArray);
		model.addAttribute("query", "");
		return "/component/prdct/MfKindNode_List";
	}
	@RequestMapping(value = "/insertForCreditAjax")
	@ResponseBody
	public Map<String, Object> insertForCreditAjax(String busModel,String nodeNo,String kindNo,String configType, String nodeNoNew) throws Exception {
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try{
			MfKindNode mfKindNode = new MfKindNode();
			mfKindNode.setBusModel(busModel);
			mfKindNode.setKindNo(kindNo);
			mfKindNode.setNodeNo(nodeNo);
			mfKindNode.setConfigType(configType);
			mfKindNode.setExt10(nodeNoNew);
			dataMap = mfKindNodeFeign.insertForCredit(mfKindNode);
			dataMap.put("flag", "success");
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "新增失败");
			throw e;
		}
		return dataMap;
	}
	
}
