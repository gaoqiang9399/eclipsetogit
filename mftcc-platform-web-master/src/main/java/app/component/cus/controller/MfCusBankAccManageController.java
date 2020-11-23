package app.component.cus.controller;

import app.base.User;
import app.component.common.BizPubParm;
import app.component.common.EntityUtil;
import app.component.cus.entity.MfCusBankAccManage;
import app.component.cus.entity.MfCusCustomer;
import app.component.cus.entity.MfCusFormConfig;
import app.component.cus.feign.MfCusBankAccManageFeign;
import app.component.cus.feign.MfCusCustomerFeign;
import app.component.cus.feign.MfCusFormConfigFeign;
import app.component.pact.feign.MfBusPactFeign;
import app.component.sysInterface.SysInterfaceFeign;
import app.component.thirdpay.util.TdPropertiesUtil;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.PropertiesUtil;
import cn.mftcc.util.StringUtil;
import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonFormUtil;
import com.core.struts.taglib.JsonTableUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
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
 * Title: MfCusBankAccManageAction.java
 * Description:
 *
 * @author:kaifa@dhcc.com.cn
 * @Wed Jun 01 09:39:43 CST 2016
 **/
@Controller
@RequestMapping("/mfCusBankAccManage")
public class MfCusBankAccManageController extends BaseFormBean {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;
    @Autowired
    private MfCusBankAccManageFeign mfCusBankAccManageFeign;
    @Autowired
    private MfCusCustomerFeign mfCusCustomerFeign;
    @Autowired
    private MfCusFormConfigFeign mfCusFormConfigFeign;
    @Autowired
    private SysInterfaceFeign sysInterfaceFeign;
    @Autowired
    private MfBusPactFeign mfBusPactFeign;

    /**
     * 列表打开页面请求
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getListPage")
    public String getListPage(Model model, String cusNo) throws Exception {
        ActionContext.initialize(request, response);
        MfCusBankAccManage mfCusBankAccManage = new MfCusBankAccManage();
        mfCusBankAccManage.setCusNo(cusNo);
        Ipage ipage = this.getIpage();
        ipage.setParams(this.setIpageParams("mfCusBankAccManage", mfCusBankAccManage));
        List<MfCusBankAccManage> mfCusBankAccManageList = (List<MfCusBankAccManage>) mfCusBankAccManageFeign.findByPage(ipage).getResult();
        model.addAttribute("mfCusBankAccManageList", mfCusBankAccManageList);
        return "/component/cus/MfCusBankAccManage_List";
    }

    @RequestMapping(value = "/getListPageForSelect")
    public String getListPageForSelect(Model model,String cusNo) throws Exception {
        ActionContext.initialize(request, response);
        model.addAttribute("cusNo",cusNo);
        return "/component/cus/MfCusBankAccManage_List";
    }

    @RequestMapping(value = "/findByPageByCusNoAjax")
    @ResponseBody
    public Map<String, Object> findByPageByCusNoAjax(String ajaxData, int pageNo, String tableId, String tableType, String cusNo, String useFlag) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfCusBankAccManage mfCusBankAccManage = new MfCusBankAccManage();
        try {
            mfCusBankAccManage.setCustomQuery(ajaxData);//自定义查询参数赋值
            mfCusBankAccManage.setCriteriaList(mfCusBankAccManage, ajaxData);//我的筛选
            mfCusBankAccManage.setCusNo(cusNo);
            mfCusBankAccManage.setUseFlag(useFlag);
            //this.getRoleConditions(mfCusBankAccManage,"1000000001");//记录级权限控制方法
            Ipage ipage = this.getIpage();
            ipage.setPageNo(pageNo);//异步传页面翻页参数
            //自定义查询Feign方法
            ipage.setParams(this.setIpageParams("mfCusBankAccManage", mfCusBankAccManage));
            ipage = mfCusBankAccManageFeign.findByPage(ipage);
            JsonTableUtil jtu = new JsonTableUtil();
            String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
            ipage.setResult(tableHtml);
            dataMap.put("ipage", ipage);
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
            throw e;
        }
        return dataMap;
    }

    /**
     * 方法描述： 获得大表单中的银行卡信息列表
     *
     * @return
     * @throws Exception String
     * @author 沈浩兵
     * @date 2016-6-1 上午11:46:17
     */
    @RequestMapping(value = "/getListPageBig")
    public String getListPageBig(Model model, String cusNo) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        FormService formService = new FormService();
        try {
            FormData formcusbank00001 = formService.getFormData("cusbank00001");
            MfCusBankAccManage mfCusBankAccManage = new MfCusBankAccManage();
            mfCusBankAccManage.setCusNo(cusNo);
            Ipage ipage = this.getIpage();
            ipage.setParams(this.setIpageParams("mfCusBankAccManage", mfCusBankAccManage));
            List<MfCusBankAccManage> mfCusBankAccManageList = (List<MfCusBankAccManage>) mfCusBankAccManageFeign.findByPage(ipage).getResult();
            model.addAttribute("mfCusBankAccManageList", mfCusBankAccManageList);
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
            throw e;
        }
        return "/component/cus/MfCusBankAccManage_ListBig";
    }

    /***
     * 列表数据查询
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/findByPageAjax")
    @ResponseBody
    public Map<String, Object> findByPageAjax(String ajaxData, int pageNo, String tableId, String tableType, String cusNo) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfCusBankAccManage mfCusBankAccManage = new MfCusBankAccManage();
        try {
            mfCusBankAccManage.setCustomQuery(ajaxData);//自定义查询参数赋值
            mfCusBankAccManage.setCriteriaList(mfCusBankAccManage, ajaxData);//我的筛选
            //this.getRoleConditions(mfCusBankAccManage,"1000000001");//记录级权限控制方法
            Ipage ipage = this.getIpage();
            ipage.setPageNo(pageNo);//异步传页面翻页参数
            mfCusBankAccManage.setCusNo(cusNo);
            //自定义查询Feign方法
            ipage.setParams(this.setIpageParams("mfCusBankAccManage", mfCusBankAccManage));
            ipage = mfCusBankAccManageFeign.findByPage(ipage);
			/*JsonTableUtil jtu = new JsonTableUtil();
			String  tableHtml = jtu.getJsonStr(tableId,tableType,(List)ipage.getResult(), ipage,true);
			ipage.setResult(tableHtml);*/
            dataMap.put("ipage", ipage);
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
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/insertAjax")
    @ResponseBody
    public Map<String, Object> insertAjax(String ajaxData) throws Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            Map<String, Object> map = getMapByJson(ajaxData);
			
			/*// 判断银行是否认证
			String smsCode = (String) map.get("smsCode");
			String bankAuthSts = null;
			if (smsCode != null && !"".equals(smsCode) && StringUtil.isNotEmpty(smsCode)) {
				// 对客户的银行卡进行签约认证
				Map<String, Object> parmMap = new HashMap<>();
				String accountNo = (String) map.get("accountNo");
				String cusNo = (String) map.get("cusNo");
				parmMap.put("smsCode", smsCode);
				parmMap.put("accountNo", accountNo);
				parmMap.put("cusNo", cusNo);
				Map<String, String> resultMap = mfCusBankAccManageFeign.signConfirmReq(parmMap);
				String code = resultMap.get("code");
				if ("0000".equals(code)) {
					bankAuthSts = "1";
				} else if ("9999".equals(code)) {
					bankAuthSts = "2";
					String msg = resultMap.get("msg");
					dataMap.put("msg", msg);
					dataMap.put("flag", "error");
					return dataMap;
				}
			}*/
            String formId = (String) map.get("formId");
            if (StringUtil.isEmpty(formId)) {
                formId = mfCusFormConfigFeign.getByCusType("base", "MfCusBankAccManageAction").getAddModel();
            }
            FormData formcusbank00003 = formService.getFormData(formId);
            getFormValue(formcusbank00003, map);
            if (this.validateFormData(formcusbank00003)) {
                MfCusBankAccManage mfCusBankAccManage = new MfCusBankAccManage();
                setObjValue(formcusbank00003, mfCusBankAccManage);

                String payFlag = TdPropertiesUtil.propertiesThridPay.getProperty("PAY_FLAG");


                MfCusCustomer mfCusCustomer = new MfCusCustomer();
                mfCusCustomer.setCusNo(mfCusBankAccManage.getCusNo());
                mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
                String cusName = mfCusCustomer.getCusName();
                mfCusBankAccManage.setCusName(cusName);
                /* mfCusBankAccManage.setBankAuthSts(bankAuthSts);*/
                mfCusBankAccManage = mfCusBankAccManageFeign.insert(mfCusBankAccManage);
                dataMap.put("mfCusBankAccManage", mfCusBankAccManage);
                String infIntegrity = mfCusCustomerFeign.updateInfIntegrity(mfCusBankAccManage.getCusNo(),
                        mfCusBankAccManage.getRelNo());// 更新客户信息完整度

                String tableId = "tablecusAccountListBase";
                MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(),
                        "MfCusBankAccManageAction");
                if (mfCusFormConfig != null && StringUtil.isNotEmpty(mfCusFormConfig.getListModelDef())) {
                    tableId = "table" + mfCusFormConfig.getListModelDef();
                }

                String cusNo = mfCusBankAccManage.getCusNo();
                String relNo = mfCusBankAccManage.getRelNo();
                mfCusBankAccManage = new MfCusBankAccManage();
                mfCusBankAccManage.setCusNo(cusNo);
                mfCusBankAccManage.setRelNo(relNo);
                Ipage ipage = this.getIpage();
                ipage.setParams(this.setIpageParams("mfCusBankAccManage", mfCusBankAccManage));
                JsonTableUtil jtu = new JsonTableUtil();
                @SuppressWarnings("unchecked")
                List<MfCusBankAccManage> list = (List<MfCusBankAccManage>) mfCusBankAccManageFeign.findByPage(ipage)
                        .getResult();
                String tableHtml = jtu.getJsonStr(tableId, "tableTag", list, null, true);
                dataMap.put("htmlStr", tableHtml);
                dataMap.put("htmlStrFlag", "1");
                dataMap.put("infIntegrity", infIntegrity);
                dataMap.put("flag", "success");
                dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
            } else {
                dataMap.put("flag", "error");
                dataMap.put("msg", this.getFormulavaliErrorMsg());
            }
        } catch (Exception e) {
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
            throw e;
        }
        return dataMap;
    }

    /**
     * ajax 异步 单个字段或多个字段更新
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/updateAjaxByOne")
    @ResponseBody
    public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        FormData formcusbank00002 = formService.getFormData("cusbank00002");
        getFormValue(formcusbank00002, getMapByJson(ajaxData));
        MfCusBankAccManage mfCusBankAccManageJsp = new MfCusBankAccManage();
        setObjValue(formcusbank00002, mfCusBankAccManageJsp);
        MfCusBankAccManage mfCusBankAccManage = mfCusBankAccManageFeign.getById(mfCusBankAccManageJsp);
        if (mfCusBankAccManage != null) {
            try {
                mfCusBankAccManage = (MfCusBankAccManage) EntityUtil.reflectionSetVal(mfCusBankAccManage, mfCusBankAccManageJsp, getMapByJson(ajaxData));
                mfCusBankAccManageFeign.update(mfCusBankAccManage);
                dataMap.put("flag", "success");
                dataMap.put("msg", MessageEnum.SUCCEED_SAVE.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
                dataMap.put("flag", "error");
                dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
                throw e;
            }
        } else {
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.FAILED_SAVE_CONTENT.getMessage("编号不存在"));
        }
        return dataMap;
    }

    /**
     * AJAX更新保存
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/updateAjax")
    @ResponseBody
    public Map<String, Object> updateAjax(String ajaxData) throws Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            Map map = getMapByJson(ajaxData);
            String formId = (String) map.get("formId");
            if (StringUtil.isEmpty(formId)) {
                formId = mfCusFormConfigFeign.getByCusType("base", "MfCusBankAccManageAction").getAddModel();
            }
            FormData formcusbank00003 = formService.getFormData(formId);

            getFormValue(formcusbank00003, map);
            if (this.validateFormData(formcusbank00003)) {
                MfCusBankAccManage mfCusBankAccManage = new MfCusBankAccManage();
                setObjValue(formcusbank00003, mfCusBankAccManage);
                mfCusBankAccManageFeign.update(mfCusBankAccManage);


                MfCusCustomer mfCusCustomer = new MfCusCustomer();
                mfCusCustomer.setCusNo(mfCusBankAccManage.getCusNo());
                mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);

                String tableId = "tablecusAccountListBase";
                MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(),
                        "MfCusBankAccManageAction");
                if (mfCusFormConfig != null && StringUtil.isNotEmpty(mfCusFormConfig.getListModelDef())) {
                    tableId = "table" + mfCusFormConfig.getListModelDef();
                }

                String cusNo = mfCusBankAccManage.getCusNo();
                mfCusBankAccManage = new MfCusBankAccManage();
                mfCusBankAccManage.setCusNo(cusNo);
                Ipage ipage = this.getIpage();
                ipage.setParams(this.setIpageParams("mfCusBankAccManage", mfCusBankAccManage));
                JsonTableUtil jtu = new JsonTableUtil();
                //String  tableHtml = jtu.getJsonStr("tablecusAccountListBase","tableTag", (List<MfCusBankAccManage>)mfCusBankAccManageFeign.findByPage(ipage).getResult(), null,true);
                String tableHtml = jtu.getJsonStr(tableId, "tableTag", (List<MfCusBankAccManage>) mfCusBankAccManageFeign.findByPage(ipage).getResult(), null, true);
                dataMap.put("htmlStr", tableHtml);
                dataMap.put("htmlStrFlag", "1");

                dataMap.put("flag", "success");
                dataMap.put("msg", MessageEnum.SUCCEED_UPDATE.getMessage());
            } else {
                dataMap.put("flag", "error");
                dataMap.put("msg", this.getFormulavaliErrorMsg());
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
     * AJAX获取查看
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getByIdAjax")
    @ResponseBody
    public Map<String, Object> getByIdAjax(String id) throws Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        Map<String, Object> formData = new HashMap<String, Object>();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        FormData formcusbank00002 = formService.getFormData("cusbank00002");
        MfCusBankAccManage mfCusBankAccManage = new MfCusBankAccManage();
        mfCusBankAccManage.setId(id);
        mfCusBankAccManage = mfCusBankAccManageFeign.getById(mfCusBankAccManage);
        getObjValue(formcusbank00002, mfCusBankAccManage, formData);
        if (mfCusBankAccManage != null) {
            dataMap.put("flag", "success");
            dataMap.put("mfCusBankAccManage", mfCusBankAccManage);
        } else {
            dataMap.put("flag", "error");
        }
        dataMap.put("formData", formData);
        return dataMap;
    }

    /**
     * AJAX获取查看
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getByIdForOneAjax")
    @ResponseBody
    public Map<String, Object> getByIdForOneAjax(String id) throws Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        Map<String, Object> formData = new HashMap<String, Object>();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        FormData formcusbank00002 = formService.getFormData("cusbank00002");
        MfCusBankAccManage mfCusBankAccManage = new MfCusBankAccManage();
        mfCusBankAccManage.setId(id);
        mfCusBankAccManage = mfCusBankAccManageFeign.getById(mfCusBankAccManage);
        getObjValue(formcusbank00002, mfCusBankAccManage, formData);
        if (mfCusBankAccManage != null) {
            dataMap.put("flag", "success");
            dataMap.put("mfCusBankAccManage", mfCusBankAccManage);
            String userType = mfCusBankAccManage.getUseType();
            Map<String, String> map = new CodeUtils().getMapByKeyName("USE_TYPE");
            userType = map.get(userType);
            dataMap.put("userType", userType);
            String bankAuthSts = mfCusBankAccManage.getBankAuthSts();
            map = new CodeUtils().getMapByKeyName("BANK_AUTH_STS");
            bankAuthSts = map.get(bankAuthSts);
            dataMap.put("bankAuthSts", bankAuthSts);
        } else {
            dataMap.put("flag", "error");
        }
        dataMap.put("formData", formData);
        return dataMap;
    }

    /**
     * 方法描述： 放款申请处使用
     *
     * @return
     * @throws Exception String
     * @author 沈浩兵
     * @date 2016-9-30 上午11:34:28
     */
    @RequestMapping(value = "/getBankInfoByIdAjax")
    @ResponseBody
    public Map<String, Object> getBankInfoByIdAjax(String accountNo) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> formData = new HashMap<String, Object>();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfCusBankAccManage mfCusBankAccManage = new MfCusBankAccManage();
        mfCusBankAccManage.setAccountNo(accountNo);
        mfCusBankAccManage = mfCusBankAccManageFeign.getById(mfCusBankAccManage);
        if (mfCusBankAccManage != null) {
            dataMap.put("flag", "success");
            dataMap.put("accountName", mfCusBankAccManage.getAccountName());
            dataMap.put("bankName", mfCusBankAccManage.getBank());
            dataMap.put("bankAccId", mfCusBankAccManage.getId());
        } else {
            dataMap.put("flag", "error");
        }
        return dataMap;
    }

    /**
     * Ajax异步删除
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/deleteAjax")
    @ResponseBody
    public Map<String, Object> deleteAjax(String id) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfCusBankAccManage mfCusBankAccManage = new MfCusBankAccManage();
        mfCusBankAccManage.setId(id);
        try {
            //JSONObject jb = JSONObject.fromObject(ajaxData);
            //mfCusBankAccManage = (MfCusBankAccManage)JSONObject.toBean(jb, MfCusBankAccManage.class);
            int count = mfBusPactFeign.getMfBusPactListByBankId(id);//已经有业务关联银行账号的未否决业务信息
            if (count > 0) {
                dataMap.put("flag", "error");
                dataMap.put("msg", "该银行账号已有关联的业务信息！");
                return dataMap;
            }
            mfCusBankAccManageFeign.delete(mfCusBankAccManage);
            //	getTableData();
            dataMap.put("flag", "success");
            dataMap.put("msg", MessageEnum.SUCCEED.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.ERROR.getMessage());
            throw e;
        }
        return dataMap;
    }

    /**
     * 新增页面
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/input")
    public String input(Model model, String cusNo, String relNo) throws Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        FormData formcusbank00003 = null;
        MfCusBankAccManage mfCusBankAccManage = new MfCusBankAccManage();
        mfCusBankAccManage.setCusNo(cusNo);
        mfCusBankAccManage.setRelNo(cusNo);
        String cusName = "";
        String cusType = "";
        String formId = "";
        MfCusCustomer mfCusCustomer = new MfCusCustomer();
        mfCusCustomer.setCusNo(cusNo);
        mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
        cusName = mfCusCustomer.getCusName();
        cusType = mfCusCustomer.getCusType();

        //设置账户默认初始信息
        mfCusBankAccManage.setAccountName(mfCusCustomer.getCusName());
        mfCusBankAccManage.setIdNum(mfCusCustomer.getIdNum());
        mfCusBankAccManage.setReservedPhone(mfCusCustomer.getCusTel());
        mfCusBankAccManage.setIdType(mfCusCustomer.getIdType());
        MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(cusType, "MfCusBankAccManageAction");
        if (mfCusFormConfig == null) {

        } else {
            formId = mfCusFormConfig.getAddModelDef();
        }
        if (StringUtil.isEmpty(formId)) {
//			logger.error("客户类型为"+cusType+"的MfCusBankAccManageAction表单信息没有查询到");
        } else {
            formcusbank00003 = formService.getFormData(formId);
            if (formcusbank00003.getFormId() == null) {
//				logger.error("客户类型为"+cusType+"的MfCusBankAccManageAction表单form"+formId+".xml文件不存在");
            } else {
                getFormValue(formcusbank00003);
                mfCusBankAccManage.setCusName(cusName);
                mfCusBankAccManage.setUseFlag("1");//默认启用
                String cusBaseType = mfCusCustomer.getCusType().substring(0, 1);
                if (BizPubParm.CUS_BASE_TYPE_PERSON.equals(cusBaseType) || BizPubParm.CUS_BASE_TYPE_QUDAOB.equals(cusBaseType)) {
                    mfCusBankAccManage.setAccProperty(BizPubParm.PUB_NOT_02);
                } else {
                    mfCusBankAccManage.setAccProperty(BizPubParm.PUB_NOT_01);
                }
                getObjValue(formcusbank00003, mfCusBankAccManage);
            }
        }
        Map<String, Object> dataMap = new HashMap<String, Object>();
        String sysProjectName = PropertiesUtil.getSysParamsProperty("sys.project.name");
        dataMap.put("sysProjectName", sysProjectName);
        model.addAttribute("dataMap", dataMap);
        model.addAttribute("formcusbank00003", formcusbank00003);
        model.addAttribute("query", "");
        return "/component/cus/MfCusBankAccManage_Insert";
    }

    @RequestMapping(value = "/inputBiz")
    public String inputBiz(Model model, String cusNo, String relNo, String kindNo) throws Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        MfCusBankAccManage mfCusBankAccManage = new MfCusBankAccManage();
        mfCusBankAccManage.setCusNo(cusNo);
        mfCusBankAccManage.setRelNo(relNo);
        String cusName = "";
        String cusType = "";
        String formId = "";
        MfCusCustomer mfCusCustomer = new MfCusCustomer();
        mfCusCustomer.setCusNo(cusNo);
        mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
        cusName = mfCusCustomer.getCusName();
        cusType = mfCusCustomer.getCusType();

        MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(kindNo, "MfCusBankAccManageAction");
        if (mfCusFormConfig == null) {

        } else {
            formId = mfCusFormConfig.getAddModelDef();
        }
        if (StringUtil.isEmpty(formId)) {
//			logger.error("产品类型为"+kindNo+"的MfCusBankAccManageAction表单信息没有查询到");
        } else {
            FormData formcusbank00003 = formService.getFormData(formId);
            if (formcusbank00003.getFormId() == null) {
//				logger.error("产品类型为"+kindNo+"的MfCusBankAccManageAction表单form"+formId+".xml文件不存在");
            } else {
                getFormValue(formcusbank00003);
                mfCusBankAccManage.setCusName(cusName);
                mfCusBankAccManage.setUseFlag("1");//默认启用
                getObjValue(formcusbank00003, mfCusBankAccManage);
            }
            model.addAttribute("formcusbank00003", formcusbank00003);
            model.addAttribute("query", "");
        }

        return "/component/cus/MfCusBankAccManage_Insert";
    }

    /**
     * 方法描述： 跳转至账号信新增页面（供业务办理中添加银行账号使用）
     *
     * @return
     * @throws Exception String
     * @author zhs
     * @date 2017-7-30 下午3:10:38
     */
    @RequestMapping(value = "/inputForBiz")
    public String inputForBiz(Model model, String cusNo, String relNo, String useType, String title) throws Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        MfCusBankAccManage mfCusBankAccManage = new MfCusBankAccManage();
        mfCusBankAccManage.setCusNo(cusNo);
        mfCusBankAccManage.setRelNo(relNo);
        MfCusCustomer mfCusCustomer = new MfCusCustomer();
        mfCusCustomer.setCusNo(cusNo);
        mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);

        //阳光设置账户默认初始信息
        String formId = "";
        mfCusBankAccManage.setAccountName(mfCusCustomer.getCusName());
        mfCusBankAccManage.setIdNum(mfCusCustomer.getIdNum());
        mfCusBankAccManage.setReservedPhone(mfCusCustomer.getCusTel());

        MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(), "MfCusBankAccManageAction");
        if (mfCusFormConfig == null) {

        } else {
            formId = mfCusFormConfig.getAddModelDef();
        }
        if (StringUtil.isEmpty(formId)) {
//			logger.error("客户类型为"+mfCusCustomer.getCusType()+"的MfCusBankAccManageAction表单信息没有查询到");
        } else {
            FormData formcusbank00003 = formService.getFormData(formId);
            if (formcusbank00003.getFormId() == null) {
//				logger.error("客户类型为"+mfCusCustomer.getCusType()+"的MfCusBankAccManageAction表单form"+formId+".xml文件不存在");
            } else {
                getFormValue(formcusbank00003);
                mfCusBankAccManage.setCusName(mfCusCustomer.getCusName());
                mfCusBankAccManage.setUseFlag("1");//默认启用
                mfCusBankAccManage.setUseType(useType);
                String cusBaseType = mfCusCustomer.getCusType().substring(0, 1);
                if (BizPubParm.CUS_BASE_TYPE_PERSON.equals(cusBaseType) || BizPubParm.CUS_BASE_TYPE_QUDAOB.equals(cusBaseType)) {
                    mfCusBankAccManage.setAccProperty(BizPubParm.PUB_NOT_02);
                } else {
                    mfCusBankAccManage.setAccProperty(BizPubParm.PUB_NOT_01);
                }
                getObjValue(formcusbank00003, mfCusBankAccManage);
                model.addAttribute("formcusbank00003", formcusbank00003);
            }
        }
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("title", title);
        String sysProjectName = PropertiesUtil.getSysParamsProperty("sys.project.name");
        dataMap.put("sysProjectName", sysProjectName);
        model.addAttribute("dataMap", dataMap);
        model.addAttribute("query", "");
        return "/component/cus/MfCusBankAccManage_InsertForBiz";
    }

    /***
     * 新增
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/insert")
    public String insert(Model model) throws Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        FormData formcusbank00002 = formService.getFormData("cusbank00002");
        getFormValue(formcusbank00002);
        MfCusBankAccManage mfCusBankAccManage = new MfCusBankAccManage();
        setObjValue(formcusbank00002, mfCusBankAccManage);
        mfCusBankAccManageFeign.insert(mfCusBankAccManage);
        getObjValue(formcusbank00002, mfCusBankAccManage);
        this.addActionMessage(null, "保存成功");
        Ipage ipage = this.getIpage();
        ipage.setParams(this.setIpageParams("mfCusBankAccManage", mfCusBankAccManage));
        List<MfCusBankAccManage> mfCusBankAccManageList = (List<MfCusBankAccManage>) mfCusBankAccManageFeign.findByPage(ipage).getResult();
        model.addAttribute("mfCusBankAccManageList", mfCusBankAccManageList);
        model.addAttribute("formcusbank00002", formcusbank00002);
        model.addAttribute("query", "");
        return "/component/cus/MfCusBankAccManage_Insert";
    }

    /**
     * 查询
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getById")
    public String getById(Model model, String id) throws Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        MfCusBankAccManage mfCusBankAccManage = new MfCusBankAccManage();
        mfCusBankAccManage.setId(id);
        mfCusBankAccManage = mfCusBankAccManageFeign.getById(mfCusBankAccManage);
        String formId = "";
        MfCusCustomer mfCusCustomer = new MfCusCustomer();
        mfCusCustomer.setCusNo(mfCusBankAccManage.getCusNo());
        mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
        MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(), "MfCusBankAccManageAction");
        if (mfCusFormConfig == null) {

        } else {
            if ("1".equals(mfCusFormConfig.getShowType())) {
                formId = mfCusFormConfig.getShowModelDef();
            } else {
                formId = mfCusFormConfig.getAddModelDef();
            }
        }
        if (StringUtil.isEmpty(formId)) {
//			logger.error("客户类型为"+mfCusCustomer.getCusType()+"的MfCusBankAccManageAction表单信息没有查询到");
        } else {
            FormData formcusbank00003 = formService.getFormData(formId);
            if (formcusbank00003.getFormId() == null) {
//				logger.error("客户类型为"+mfCusCustomer.getCusType()+"的MfCusBankAccManageAction表单form"+formId+".xml文件不存在");
            } else {
                getFormValue(formcusbank00003);
                getObjValue(formcusbank00003, mfCusBankAccManage);
            }
            model.addAttribute("formcusbank00003", formcusbank00003);
            model.addAttribute("query", "");
        }

        return "/component/cus/MfCusBankAccManage_Detail";
    }

    /**
     * 删除
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/delete")
    public String delete(Model model, String id) throws Exception {
        ActionContext.initialize(request,
                response);
        MfCusBankAccManage mfCusBankAccManage = new MfCusBankAccManage();
        mfCusBankAccManage.setId(id);
        mfCusBankAccManageFeign.delete(mfCusBankAccManage);
        return getListPage(model, null);
    }

    /**
     * 新增校验
     *
     * @return
     * @throws Exception
     */
    public void validateInsert() throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        FormData formcusbank00002 = formService.getFormData("cusbank00002");
        getFormValue(formcusbank00002);
        boolean validateFlag = this.validateFormData(formcusbank00002);
    }

    /**
     * 修改校验
     *
     * @return
     * @throws Exception
     */
    public void validateUpdate() throws Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        FormData formcusbank00002 = formService.getFormData("cusbank00002");
        getFormValue(formcusbank00002);
        boolean validateFlag = this.validateFormData(formcusbank00002);
    }

    /**
     * 方法描述： 操作完成刷新列表
     *
     * @throws Exception void
     * @author 沈浩兵
     * @date 2016-6-1 上午11:38:32
     */
    @RequestMapping(value = "/getTableData")
    @ResponseBody
    private Map<String, Object> getTableData(String cusNo, String tableId) throws Exception {
        Map<String, Object> dataMap = new HashMap<String, Object>();
        JsonTableUtil jtu = new JsonTableUtil();
        MfCusBankAccManage mfCusBankAccManage = new MfCusBankAccManage();
        mfCusBankAccManage.setCusNo(cusNo);
        Ipage ipage = this.getIpage();
        ipage.setParams(this.setIpageParams("mfCusBankAccManage", mfCusBankAccManage));
        String tableHtml = jtu.getJsonStr(tableId, "tableTag", (List<MfCusBankAccManage>) mfCusBankAccManageFeign.findByPage(ipage).getResult(), null, true);
        dataMap.put("tableData", tableHtml);
        return dataMap;
    }

    //列表展示详情，单字段编辑
    @RequestMapping(value = "/listShowDetailAjax")
    @ResponseBody
    public Map<String, Object> listShowDetailAjax(String cusNo, String id) throws Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        String cusType = "";
        String formId = "";
        String query = "";
        MfCusCustomer mfCusCustomer = new MfCusCustomer();
        mfCusCustomer.setCusNo(cusNo);
        mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
        cusType = mfCusCustomer.getCusType();
        MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(cusType, "MfCusBankAccManageAction");
        if (mfCusFormConfig == null) {

        } else {
            formId = mfCusFormConfig.getShowModelDef();
        }
        if (StringUtil.isEmpty(formId)) {
//			logger.error("客户类型为"+cusType+"的MfCusBankAccManageAction表单信息没有查询到");
            dataMap.put("msg", "获取详情失败");
            dataMap.put("flag", "error");
        } else {
            Map<String, Object> formData = new HashMap<String, Object>();
            request.setAttribute("ifBizManger", "3");
            JsonFormUtil jsonFormUtil = new JsonFormUtil();
            FormData formcusbank00002 = formService.getFormData(formId);
            MfCusBankAccManage mfCusBankAccManage = new MfCusBankAccManage();
            mfCusBankAccManage.setId(id);
            mfCusBankAccManage = mfCusBankAccManageFeign.getById(mfCusBankAccManage);
            getObjValue(formcusbank00002, mfCusBankAccManage, formData);
            query = sysInterfaceFeign.getQueryResult(User.getRegNo(request));
            if (query == null) {
                query = "";
            }
            String htmlStrCorp = jsonFormUtil.getJsonStr(formcusbank00002, "propertySeeTag", query);
            if (mfCusBankAccManage != null) {
                dataMap.put("formHtml", htmlStrCorp);
                dataMap.put("flag", "success");
            } else {
                dataMap.put("msg", "获取详情失败");
                dataMap.put("flag", "error");
            }
            dataMap.put("formData", mfCusBankAccManage);
        }
        return dataMap;
    }

    /**
     * ajax 异步 单个字段或多个字段更新
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/updateByOneAjax")
    @ResponseBody
    public Map<String, Object> updateByOneAjax(String formId, String ajaxData) throws Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfCusBankAccManage mfCusBankAccManage = new MfCusBankAccManage();
        Map map = getMapByJson(ajaxData);
        formId = (String) map.get("formId");
        FormData formcusbank00002 = formService.getFormData(formId);
        getFormValue(formcusbank00002, getMapByJson(ajaxData));
        MfCusBankAccManage mfCusBankAccManageNew = new MfCusBankAccManage();
        setObjValue(formcusbank00002, mfCusBankAccManageNew);
        mfCusBankAccManage.setId(mfCusBankAccManageNew.getId());
        mfCusBankAccManage = mfCusBankAccManageFeign.getById(mfCusBankAccManage);
        if (mfCusBankAccManage != null) {
            try {
                mfCusBankAccManage = (MfCusBankAccManage) EntityUtil.reflectionSetVal(mfCusBankAccManage, mfCusBankAccManageNew, getMapByJson(ajaxData));
                mfCusBankAccManageFeign.update(mfCusBankAccManage);
                dataMap.put("flag", "success");
                dataMap.put("msg", MessageEnum.SUCCEED_SAVE.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
                dataMap.put("flag", "error");
                dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
                throw new Exception(e.getMessage());
            }
        } else {
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.FAILED_SAVE_CONTENT.getMessage("编号不存在"));
        }
        return dataMap;
    }

    @RequestMapping(value = "/getBankAccData")
    @ResponseBody
    public Map<String, Object> getBankAccData() throws Exception {
        ActionContext.initialize(request, response);
        String cusNo = request.getParameter("cusNo");
        String useType = request.getParameter("useType");
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfCusBankAccManage mfCusBankAccManage = new MfCusBankAccManage();
        mfCusBankAccManage.setUseType(useType);
        mfCusBankAccManage.setCusNo(cusNo);
        mfCusBankAccManage.setUseFlag(BizPubParm.YES_NO_Y);//启用
        mfCusBankAccManage.setDelFlag(BizPubParm.YES_NO_N);//未删除
        List<MfCusBankAccManage> mfCusBankAccManageList = mfCusBankAccManageFeign.getMfCusBankAccListByCusNo(mfCusBankAccManage);
        JSONArray array = new JSONArray();
        if (mfCusBankAccManageList != null && mfCusBankAccManageList.size() > 0) {
            for (int i = 0; i < mfCusBankAccManageList.size(); i++) {
                JSONObject obj = new JSONObject();
                obj.put("id", mfCusBankAccManageList.get(i).getId());
                obj.put("name", mfCusBankAccManageList.get(i).getAccountNo());
                array.add(obj);
            }
            dataMap.put("mfCusBankAccManageList", mfCusBankAccManageList);
            dataMap.put("flag", "success");
        } else {
            dataMap.put("flag", "error");
        }
        dataMap.put("items", array);
        return dataMap;
    }

    @RequestMapping("/getAccIdDataAjax")
    @ResponseBody
    public Map<String, Object> getAccIdDataAjax(String ajaxData, int pageNo, String cusNo) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfCusBankAccManage mfCusBankAccManage = new MfCusBankAccManage();
        try {
            mfCusBankAccManage.setCustomQuery(ajaxData);// 自定义查询参数赋值
            mfCusBankAccManage.setCusNo(cusNo);
            mfCusBankAccManage.setUseFlag(BizPubParm.YES_NO_Y);
            mfCusBankAccManage.setDelFlag(BizPubParm.YES_NO_N);
            Ipage ipage = this.getIpage();
            ipage.setPageNo(pageNo);// 异步传页面翻页参数
            ipage.setParams(this.setIpageParams("mfCusBankAccManage", mfCusBankAccManage));
            // 自定义查询Bo方法
            ipage = mfCusBankAccManageFeign.getMfCusBankAccListByCusNoPage(ipage);
            dataMap.put("ipage", ipage);
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
            throw new Exception(e.getMessage());
        }
        return dataMap;
    }


    /**
     * <p>Title: getBankCusAccMangeAjax</p>
     * <p>Description:用款申请的时候选择银行账号 </p>
     *
     * @param pageNo
     * @return
     * @throws Exception
     * @author zkq
     * @date 2018年7月30日 下午2:59:48
     */
    @RequestMapping(value = "/getBankCusAccMangeAjax")
    @ResponseBody
    public Map<String, Object> getBankCusAccMangeAjax(int pageNo) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            Ipage ipage = this.getIpage();
            ipage.setPageNo(pageNo);// 异步传页面翻页参数
            MfCusBankAccManage mfCusBankAccManage = new MfCusBankAccManage();
            ipage.setParams(this.setIpageParams("mfCusBankAccManage", mfCusBankAccManage));
            ipage = mfCusBankAccManageFeign.findByPage(ipage);
            dataMap.put("ipage", ipage);
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
            throw e;
        }
        return dataMap;
    }


    /**
     * <p>Title: signReq</p>
     * <p>Description:银行账号的预签约接口 </p>
     *
     * @param accoutNo
     * @param cusNo
     * @return
     * @throws Exception
     * @author zkq
     * @date 2018年9月5日 上午11:54:44
     */
    @RequestMapping(value = "/signReq")
    @ResponseBody
    public Map<String, Object> signReq(String accountNo, String cusNo) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        Map<String, Object> parmMap = new HashMap<String, Object>();
        try {
            parmMap.put("cusNo", cusNo);
            accountNo = accountNo.replaceAll(" ", "");
            parmMap.put("accountNo", accountNo);
            Map<String, String> map = mfCusBankAccManageFeign.signReq(parmMap);
            String code = map.get("code");
            String msg = map.get("msg");
            if ("0000".equals(code)) {
                dataMap.put("flag", "success");
            } else if ("9999".equals(code)) {
                dataMap.put("flag", "error");
                dataMap.put("msg", msg);
            }else {
            }

        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
            throw e;
        }
        return dataMap;
    }
}
