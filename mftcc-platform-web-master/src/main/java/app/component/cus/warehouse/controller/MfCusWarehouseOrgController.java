package app.component.cus.warehouse.controller;

import app.component.busviewinterface.BusViewInterfaceFeign;
import app.component.common.BizPubParm;
import app.component.common.EntityUtil;
import app.component.cus.controller.MfBusAgenciesController;
import app.component.cus.entity.MfCusFormConfig;
import app.component.cus.entity.MfCusType;
import app.component.cus.entity.MfCusWarehouse;
import app.component.cus.feign.MfCusCustomerFeign;
import app.component.cus.feign.MfCusFormConfigFeign;
import app.component.cus.feign.MfCusTypeFeign;
import app.component.cus.warehouse.entity.MfCusWarehouseOrg;
import app.component.cus.warehouse.feign.MfCusWarehouseOrgFeign;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.util.StringUtil;
import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Title: MfCusWarehouseOrgController.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Wed Apr 17 09:49:56 CST 2019
 **/
@Controller
@RequestMapping(value = "/mfCusWarehouseOrg")
public class MfCusWarehouseOrgController extends BaseFormBean{
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfCusWarehouseOrgFeign mfCusWarehouseOrgFeign;
    @Autowired
    private MfCusTypeFeign mfCusTypeFeign;
    @Autowired
    private MfCusFormConfigFeign mfCusFormConfigFeign;
    @Autowired
    private MfCusCustomerFeign mfCusCustomerFeign;
    @Autowired
    private BusViewInterfaceFeign busViewInterfaceFeign;


    /**
     * 列表打开页面请求
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getListPage")
    public String getListPage(Model model) throws Exception {
        String tableId = "tableCusWarehouseOrgList";
        MfCusFormConfig mc = mfCusFormConfigFeign.getByCusType("base", "MfCusWarehouseOrgAction");
        if (mc != null) {
            tableId = mc.getListModelDef();
        }
        model.addAttribute("tableId", "table" + tableId);
        return "/component/cus/warehouse/MfCusWarehouseOrg_List";
    }
	/***
	 * 列表数据查询
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping(value = "/findByPageAjax")
	public Map<String, Object> findByPageAjax(String ajaxData, String tableId, String tableType, Integer pageNo,Integer pageSize) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfCusWarehouseOrg mfCusWarehouseOrg = new MfCusWarehouseOrg();
		try {
			mfCusWarehouseOrg.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfCusWarehouseOrg.setCriteriaList(mfCusWarehouseOrg, ajaxData);//我的筛选
			mfCusWarehouseOrg.setCustomSorts(ajaxData);//自定义排序
			//this.getRoleConditions(mfCusWarehouseOrg,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			ipage.setPageSize(pageSize);
			ipage.setParams(this.setIpageParams("mfCusWarehouseOrg", mfCusWarehouseOrg));
			//自定义查询Feign方法
			ipage = mfCusWarehouseOrgFeign.findByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String  tableHtml = jtu.getJsonStr(tableId,tableType,(List)ipage.getResult(), ipage,true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage",ipage);
		} catch (Exception e) {
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
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try{
            Map <String, Object> map = getMapByJson(ajaxData) == null ? new HashMap() : getMapByJson(ajaxData);
            String formId = String.valueOf(map.get("formId"));
            FormData formCusWarehouseOrgAdd = formService.getFormData(formId);
            getFormValue(formCusWarehouseOrgAdd, getMapByJson(ajaxData));
			if(this.validateFormData(formCusWarehouseOrgAdd)){
				MfCusWarehouseOrg mfCusWarehouseOrg = new MfCusWarehouseOrg();
				setObjValue(formCusWarehouseOrgAdd, mfCusWarehouseOrg);
				mfCusWarehouseOrgFeign.insert(mfCusWarehouseOrg);
				dataMap.put("flag", "success");
				dataMap.put("msg", "新增成功");
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		}catch(Exception e){
			dataMap.put("flag", "error");
			dataMap.put("msg", "新增失败");
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
	public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception{
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
        Map <String, Object> map = getMapByJson(ajaxData) == null ? new HashMap() : getMapByJson(ajaxData);
        String formId = String.valueOf(map.get("formId"));
		FormData formCusWarehouseOrgAdd = formService.getFormData(formId);
		getFormValue(formCusWarehouseOrgAdd, getMapByJson(ajaxData));
		MfCusWarehouseOrg mfCusWarehouseOrgJsp = new MfCusWarehouseOrg();
		setObjValue(formCusWarehouseOrgAdd, mfCusWarehouseOrgJsp);
		MfCusWarehouseOrg mfCusWarehouseOrg = mfCusWarehouseOrgFeign.getById(mfCusWarehouseOrgJsp);
		if(mfCusWarehouseOrg!=null){
			try{
				mfCusWarehouseOrg = (MfCusWarehouseOrg)EntityUtil.reflectionSetVal(mfCusWarehouseOrg, mfCusWarehouseOrgJsp, getMapByJson(ajaxData));
				mfCusWarehouseOrgFeign.update(mfCusWarehouseOrg);
				dataMap.put("flag", "success");
				dataMap.put("msg", "保存成功");
			}catch(Exception e){
				dataMap.put("flag", "error");
				dataMap.put("msg", "新增失败");
				throw e;
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
	@RequestMapping("/updateAjax")
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfCusWarehouseOrg mfCusWarehouseOrg = new MfCusWarehouseOrg();
		try{
			FormData formCusWarehouseOrgAdd = formService.getFormData("CusWarehouseOrgAdd");
			getFormValue(formCusWarehouseOrgAdd, getMapByJson(ajaxData));
			if(this.validateFormData(formCusWarehouseOrgAdd)){
				mfCusWarehouseOrg = new MfCusWarehouseOrg();
				setObjValue(formCusWarehouseOrgAdd, mfCusWarehouseOrg);
				mfCusWarehouseOrgFeign.update(mfCusWarehouseOrg);
				dataMap.put("flag", "success");
				dataMap.put("msg", "更新成功");
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		}catch(Exception e){
			dataMap.put("flag", "error");
			dataMap.put("msg", "更新失败");
			throw e;
		}
		return dataMap;
	}
	
	/**
	 * AJAX获取查看
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String id) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String,Object> formData = new HashMap<String,Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		FormData formCusWarehouseOrgAdd = formService.getFormData("CusWarehouseOrgAdd");
		MfCusWarehouseOrg mfCusWarehouseOrg = new MfCusWarehouseOrg();
		mfCusWarehouseOrg.setId(id);
		mfCusWarehouseOrg = mfCusWarehouseOrgFeign.getById(mfCusWarehouseOrg);
		getObjValue(formCusWarehouseOrgAdd, mfCusWarehouseOrg,formData);
		if(mfCusWarehouseOrg!=null){
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
	public Map<String, Object> deleteAjax(String id) throws Exception{
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfCusWarehouseOrg mfCusWarehouseOrg = new MfCusWarehouseOrg();
		mfCusWarehouseOrg.setId(id);
		try {
			mfCusWarehouseOrgFeign.delete(mfCusWarehouseOrg);
			dataMap.put("flag", "success");
			dataMap.put("msg", "成功");
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", "失败");
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
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        String typeNo = request.getParameter("typeNo");
        String formId = "";

        MfCusType mfCusType = new MfCusType();
        mfCusType.setBaseType(BizPubParm.CUS_BASE_TYPE_WAERHOUSE);
        mfCusType.setUseFlag("1");
        List <MfCusType> list = mfCusTypeFeign.getAllList(mfCusType);
        // 获取动态表单
        if (StringUtil.isNotEmpty(typeNo)) {
            MfCusFormConfig mc = mfCusFormConfigFeign.getByCusType(typeNo, "MfCusWarehouseOrgAction");
            if (mc != null) {
                formId = mc.getAddModelDef();
            }
        } else {
            if (list != null && list.size() > 0) {
                typeNo = list.get(0).getTypeNo();//第一个会是表单的默认项
                MfCusFormConfig mc = mfCusFormConfigFeign.getByCusType(typeNo, "MfCusWarehouseOrgAction");
                if (mc != null) {
                    formId = mc.getAddModelDef();
                }
            }
        }
        if (StringUtil.isEmpty(formId)) {
            formId = "cusWarehouseBase";
        }
        // 仓储机构
        CodeUtils cu = new CodeUtils();
        JSONArray cusTypeArray = new JSONArray();// 获取仓储机构
        for (int i = 0; i < list.size(); i++) {
            mfCusType = list.get(i);
            JSONObject obj = new JSONObject();
            obj.put("id", mfCusType.getTypeNo());
            obj.put("name", mfCusType.getTypeName());
            cusTypeArray.add(obj);
        }
        model.addAttribute("cusTypeArray", cusTypeArray.toString());

        FormData formCusWarehouseOrgAdd = formService.getFormData(formId);
        getFormValue(formCusWarehouseOrgAdd);
        MfCusWarehouseOrg mfCusWarehouseOrg = new MfCusWarehouseOrg();
        mfCusWarehouseOrg.setCusType(typeNo);
        mfCusWarehouseOrg.setIdType(BizPubParm.ID_TYPE_CREDIT);
        getObjValue(formCusWarehouseOrgAdd, mfCusWarehouseOrg);
        model.addAttribute("formCusWarehouseOrgAdd", formCusWarehouseOrgAdd);
        model.addAttribute("query", "");
        return "/component/cus/warehouse/MfCusWarehouseOrg_Insert";
	}
    /**
     * 调视角
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getWarehouseOrgView")
    public String getWarehouseOrgView(Model model, String id, String busEntrance,String cusNo) throws Exception {
        ActionContext.initialize(request, response);
        // 根据id查询仓储机构
        MfCusWarehouseOrg mfCusWarehouseOrg = new MfCusWarehouseOrg();
        mfCusWarehouseOrg.setId(id);
        mfCusWarehouseOrg.setCusNo(cusNo);
        mfCusWarehouseOrg = mfCusWarehouseOrgFeign.getById(mfCusWarehouseOrg);
        String baseType = BizPubParm.CUS_BASE_TYPE_WAERHOUSE;
        Map <String, Object> dataMap = new HashMap <String, Object>();
        Map <String, String> parmMap = new HashMap <String, String>();
        id = mfCusWarehouseOrg.getId();
        cusNo = mfCusWarehouseOrg.getCusNo();
        parmMap.put("id", id);
        parmMap.put("baseType", baseType);
        parmMap.put("cusType", mfCusWarehouseOrg.getCusType());
        parmMap.put("cusNo", cusNo);
        parmMap.put("operable", "operable");//底部显示待完善信息块
        String generalClass = "cus";
        parmMap.put("docParm", "cusNo=" + cusNo + "&relNo=" + cusNo + "&scNo=" + BizPubParm.SCENCE_TYPE_DOC_CUS);
        Map <String, Object> cusViewMap = busViewInterfaceFeign.getCommonViewMap(generalClass, busEntrance, parmMap);

        dataMap.put("cusNo", cusNo);
        dataMap.put("baseType", baseType);
        dataMap.putAll(cusViewMap);
        dataMap.put("id", id);
        model.addAttribute("dataMap", dataMap);
        model.addAttribute("opNoType", BizPubParm.OP_NO_TYPE4);
        model.addAttribute("query", "");
        return "component/cus/commonview/MfCusCustomer_ComView";
    }
    /**
     * 资金机构的历史业务统计数据
     *
     * @return
     */
    @RequestMapping(value = "/getWarehouseOrgBusHisAjax")
    @ResponseBody
    public Map<String, Object> getWarehouseOrgBusHisAjax(String cusNo)throws Exception {
        Logger logger = LoggerFactory.getLogger(MfBusAgenciesController.class);
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            Map<String, String> resulMap = mfCusWarehouseOrgFeign.getWarehouseOrgBusHisAjax(cusNo);
            dataMap.put("flag", "success");
            dataMap.putAll(resulMap);
        } catch (Exception e) {
            logger.error("获取仓储机构历史数据失败，", e);
            throw e;
        }
        return dataMap;
    }
    /**
	 * 查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getById")
	public String getById(Model model,String id) throws Exception{
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formCusWarehouseOrgDetail = formService.getFormData("CusWarehouseOrgDetail");
		getFormValue(formCusWarehouseOrgDetail);
		MfCusWarehouseOrg mfCusWarehouseOrg = new MfCusWarehouseOrg();
		mfCusWarehouseOrg.setId(id);
		mfCusWarehouseOrg = mfCusWarehouseOrgFeign.getById(mfCusWarehouseOrg);
		getObjValue(formCusWarehouseOrgDetail, mfCusWarehouseOrg);
		model.addAttribute("formCusWarehouseOrgDetail", formCusWarehouseOrgDetail);
		model.addAttribute("query", "");
		return "/component/cus/warehouse/MfCusWarehouseOrg_Detail";
	}

	/**
	 * 新增校验
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	public void validateInsert() throws Exception{
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formCusWarehouseOrgAdd = formService.getFormData("CusWarehouseOrgAdd");
		getFormValue(formCusWarehouseOrgAdd);
		boolean validateFlag = this.validateFormData(formCusWarehouseOrgAdd);
	}
	/**
	 * 修改校验
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	public void validateUpdate() throws Exception{
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formCusWarehouseOrgAdd = formService.getFormData("CusWarehouseOrgAdd");
		getFormValue(formCusWarehouseOrgAdd);
		boolean validateFlag = this.validateFormData(formCusWarehouseOrgAdd);
	}
}
