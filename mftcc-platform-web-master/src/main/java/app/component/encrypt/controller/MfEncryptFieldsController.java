package app.component.encrypt.controller;

import app.base.User;
import app.base.cache.feign.BusiCacheFeign;
import app.component.encrypt.entity.MfEncryptFields;
import app.component.encrypt.feign.MfEncryptFieldsFegin;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.DateUtil;
import cn.mftcc.util.WaterIdUtil;
import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;
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

@Controller
@RequestMapping(value = "/mfEncryptFields")
public class MfEncryptFieldsController  extends BaseFormBean {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;
    @Autowired
    private MfEncryptFieldsFegin  mfEncryptFieldsFegin;
    @Autowired
    private BusiCacheFeign busiCacheFeign;
    /***
     * 列表数据查询
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/findByPageAjax")
    @ResponseBody
    public Map<String, Object> findByPageAjax(String ajaxData, Integer pageNo, Integer pageSize, String tableId,
                                              String tableType) throws Exception {
        ActionContext.initialize(request, response);
        Map <String, Object> dataMap = new HashMap<String, Object>();
        MfEncryptFields mfEncryptFields = new MfEncryptFields();
        try {
            mfEncryptFields.setCustomQuery(ajaxData);// 自定义查询参数赋值
            mfEncryptFields.setCriteriaList(mfEncryptFields, ajaxData);// 我的筛选
            Ipage ipage = this.getIpage();
            ipage.setPageNo(pageNo);// 异步传页面翻页参数
            ipage.setParams(this.setIpageParams("mfEncryptFields",mfEncryptFields));
            // 自定义查询Feign方法
            ipage = mfEncryptFieldsFegin.findByPage(ipage);
            JsonTableUtil jtu = new JsonTableUtil();
            String tableHtml = jtu.getJsonStr(tableId, tableType, (List<?>) ipage.getResult(), ipage, true);
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
     *
     * 方法描述： 跳转入口列表
     * @return
     * @throws Exception
     * String
     * @author 沈浩兵
     * @date 2018-2-11 下午2:35:35
     */
    @RequestMapping(value = "/getEntListPage")
    public String getEntListPage(Model model) throws Exception {
        ActionContext.initialize(request, response);		// 前台自定义筛选组件的条件项，从数据字典缓存获取。

        return "/component/encrypt/MfEncryptFields_List";
    }



    /**
     * 新增页面
     *
     * @return
     * @throws Exception
     */
    @SuppressWarnings({ "unchecked" })
    @RequestMapping(value = "/input")
    public String input(Model model,String flag,String fieldId) throws Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        FormData formEncryptFields0001 = formService.getFormData("EncryptFields0001");
        if("update".equals(flag)){
            MfEncryptFields tmp = new MfEncryptFields();
            tmp.setFieldId(fieldId);
            tmp=mfEncryptFieldsFegin.getById(tmp);
            getObjValue(formEncryptFields0001, tmp);
        }
        model.addAttribute("flag", flag);
        model.addAttribute("formEncryptFields0001", formEncryptFields0001);
        model.addAttribute("query", "");
        return "/component/encrypt/MfEncryptFields_Insert";
    }


    /**
     * 新增
     *
     * @param ajaxData
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/insertAjax")
    @ResponseBody
    public Map<String, Object> insertAjax(String ajaxData) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        boolean isExists=false;
        try {
            Map<String, Object> map = getMapByJson(ajaxData);
            FormService formService = new FormService();
            FormData formEncryptFields0001 = formService.getFormData("EncryptFields0001");
            getFormValue(formEncryptFields0001, map);
            if(this.validateFormData(formEncryptFields0001)){
                MfEncryptFields mfEncryptFields = new MfEncryptFields();
                setObjValue(formEncryptFields0001, mfEncryptFields);
                mfEncryptFieldsFegin.getCount(mfEncryptFields);
                isExists=true;
                MfEncryptFields tmp = new MfEncryptFields();
                tmp.setColumnName(mfEncryptFields.getColumnName());
                tmp.setTableName(mfEncryptFields.getTableName());
                List list=mfEncryptFieldsFegin.getList(tmp);
                if(list!=null&&!list.isEmpty()){
                    dataMap.put("flag", "error");
                    dataMap.put("msg", "表名+字段信息已存在!");
                }else{
                    mfEncryptFields.setFieldId(WaterIdUtil.getWaterId());
                    mfEncryptFields.setCreateTime(DateUtil.getDateTime());
                    mfEncryptFields.setLstModeTime(DateUtil.getDateTime());
                    mfEncryptFields.setOpNo(User.getRegNo(request));
                    mfEncryptFields.setOpName(User.getRegName(request));
                    mfEncryptFieldsFegin.insert(mfEncryptFields);
                    dataMap.put("flag", "success");
                    dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
                }
            }else{
                dataMap.put("flag", "error");
                dataMap.put("msg", this.getFormulavaliErrorMsg());
            }
        } catch (Exception e) {
            e.printStackTrace();
            if(!isExists){
                dataMap.put("flag", "error");
                dataMap.put("msg", "请检查表名或者字段是否正确");
            }else{
                dataMap.put("flag", "error");
                dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
                throw e;
            }
        }
        return dataMap;
    }


    /**
     * 修改
     *
     * @param ajaxData
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/updateAjax")
    @ResponseBody
    public Map<String, Object> updateAjax(String ajaxData) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        boolean isExists=false;
        try {
            Map<String, Object> map = getMapByJson(ajaxData);
            FormService formService = new FormService();
            FormData formEncryptFields0001 = formService.getFormData("EncryptFields0001");
            getFormValue(formEncryptFields0001, map);
            if(this.validateFormData(formEncryptFields0001)){
                MfEncryptFields mfEncryptFields = new MfEncryptFields();
                setObjValue(formEncryptFields0001, mfEncryptFields);
                mfEncryptFieldsFegin.getCount(mfEncryptFields);
                isExists=true;
                mfEncryptFields.setLstModeTime(DateUtil.getDateTime());
                mfEncryptFields.setOpNo(User.getRegNo(request));
                mfEncryptFields.setOpName(User.getRegName(request));
                mfEncryptFieldsFegin.update(mfEncryptFields);
                dataMap.put("flag", "success");
                dataMap.put("msg", MessageEnum.SUCCEED_UPDATE.getMessage());
            }else{
                dataMap.put("flag", "error");
                dataMap.put("msg", this.getFormulavaliErrorMsg());
            }
        } catch (Exception e) {
            e.printStackTrace();
            if(!isExists){
                dataMap.put("flag", "error");
                dataMap.put("msg", "请检查表名或者字段是否正确");
            }else{
                dataMap.put("flag", "error");
                dataMap.put("msg", MessageEnum.FAILED_UPDATE.getMessage());
                throw e;
            }
        }
        return dataMap;
    }


    /**
     * 修改
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/updateIsUsedAjax")
    @ResponseBody
    public Map<String, Object> updateIsUsedAjax(String ajaxData,String tableId) throws Exception {
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            JSONObject jobj = JSONObject.fromObject(ajaxData);
            MfEncryptFields mfEncryptFields = new MfEncryptFields();
            mfEncryptFields.setFieldId(jobj.getString("fieldId"));
            mfEncryptFields.setIsUsed(jobj.getString("isUsed"));
            mfEncryptFields.setLstModeTime(DateUtil.getDateTime());
            mfEncryptFields.setOpNo(User.getRegNo(request));
            mfEncryptFields.setOpName(User.getRegName(request));
            mfEncryptFieldsFegin.update(mfEncryptFields);
            dataMap.put("flag", "success");
            dataMap.put("msg", MessageEnum.SUCCEED_UPDATE.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.FAILED_UPDATE.getMessage());
            throw e;
        }
        return dataMap;
    }


    /**
     * 同步缓存
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/cleanCache")
    @ResponseBody
    public Map<String, Object> cleanCache() throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        busiCacheFeign.refreshEncryptCache();
        dataMap.put("flag", "success");
        dataMap.put("msg", "同步成功");
        return dataMap;
    }



    /**
     * 加密历史数据
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/encryptHistoryData")
    @ResponseBody
    public Map<String, String> encryptHistoryData(String fieldId) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, String> dataMap=new HashMap<>();
        dataMap=mfEncryptFieldsFegin.encryptHistoryData(fieldId);
        return dataMap;
    }

    /**
     * 解密历史数据
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/dencryptHistoryData")
    @ResponseBody
    public Map<String, String> dencryptHistoryData(String fieldId) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, String> dataMap=new HashMap<>();
        dataMap=mfEncryptFieldsFegin.dencryptHistoryData(fieldId);
        return dataMap;
    }



}