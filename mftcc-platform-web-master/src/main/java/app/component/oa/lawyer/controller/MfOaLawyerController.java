package app.component.oa.lawyer.controller;

import app.base.User;
import app.component.common.BizPubParm;
import app.component.oa.lawyer.entity.MfOaLawyer;
import app.component.oa.lawyer.feign.MfOaLawyerFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.DateUtil;
import cn.mftcc.util.WaterIdUtil;
import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jwh on 2018/6/27.
 * OA 律师管理
 */
@Controller
@RequestMapping("/mfOaLawyer")
public class MfOaLawyerController extends BaseFormBean {

    @Autowired
    private MfOaLawyerFeign mfOaLawyerFeign; //注入律师管理业务对象
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;

    /**
     * Description:律师管理--打开律师列表界面
     *
     * @param: ajaxData
     * @return: dataMap
     * @auther: jingwenhao
     * @date: 2018/6/28 10:38
     */
    @RequestMapping("/getListPage")
    public String getListPage() throws Exception {
        ActionContext.initialize(request, response);
        //model.addAttribute("opNo", User.getRegNo(request));
        return "/component/oa/lawyer/MfOaLawyer_List";
    }

    @RequestMapping("/getHireLawyerListPage")
    public String getHireLawyerListPage() throws Exception {
        ActionContext.initialize(request, response);
        //model.addAttribute("opNo", User.getRegNo(request));
        return "/component/oa/lawyer/MfOaHireLawyer_List";
    }
    
    /**
     * Description:律师管理--查询所有律师信息
     *
     * @param: ajaxData
     * @return: dataMap
     * @auther: jingwenhao
     * @date: 2018/6/28 10:38
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping("/findByPageAjax")
    @ResponseBody
    public Map<String, Object> findByPageAjax(String ajaxData, Integer pageNo, Integer pageSize, String tableId,
                                              String tableType,String flag) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        MfOaLawyer mfOaLawyer = new MfOaLawyer();
        System.out.println(ajaxData);
        try {
            mfOaLawyer.setCustomQuery(ajaxData);// 自定义查询参数赋值
            mfOaLawyer.setCriteriaList(mfOaLawyer, ajaxData);// 我的筛选
            if (!"".equals(flag)&&"1".equals(flag)) {
                ;
            }
            {
                SimpleDateFormat sdf =   new SimpleDateFormat( "yyyyMMdd" );
                String nowDate = sdf.format(new Date());
                mfOaLawyer.setHireEndTime(nowDate);
            }
            Ipage ipage = this.getIpage();
            ipage.setPageNo(pageNo);// 异步传页面翻页参数
            ipage.setPageSize(pageSize);// 异步传页面翻页尺寸
            ipage.setParams(this.setIpageParams("mfOaLawyer", mfOaLawyer));
            ipage = mfOaLawyerFeign.findByPage(ipage);
            JsonTableUtil jtu = new JsonTableUtil();
            String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
            ipage.setResult(tableHtml);
            dataMap.put("ipage", ipage);
            dataMap.put("flag", "success");
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", e.getMessage());
        }
        return dataMap;
    }
    /**
     * Description:律师聘用到期提醒
     *
     * @param: ajaxData
     * @return: dataMap
     * @auther: sunyongqiang
     * @date: 2018/10/09 18:38
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping("/findHireLawyerByPageAjax")
    @ResponseBody
    public Map<String, Object> findHireLawyerByPageAjax(String ajaxData, Integer pageNo, Integer pageSize, String tableId,
    		String tableType,String flag) throws Exception {
    	ActionContext.initialize(request, response);
    	Map<String, Object> dataMap = new HashMap<String, Object>();
    	MfOaLawyer mfOaLawyer = new MfOaLawyer();
    	System.out.println(ajaxData);
    	try {
    		mfOaLawyer.setCustomQuery(ajaxData);// 自定义查询参数赋值
    		mfOaLawyer.setCriteriaList(mfOaLawyer, ajaxData);// 我的筛选
    		if (!"".equals(flag)&&"1".equals(flag)) {
                ;
            }
            {
    			SimpleDateFormat sdf =   new SimpleDateFormat( "yyyyMMdd" );
    			String nowDate = sdf.format(new Date());
    			mfOaLawyer.setHireEndTime(nowDate);
    		}
    		Ipage ipage = this.getIpage();
    		ipage.setPageNo(pageNo);// 异步传页面翻页参数
    		ipage.setPageSize(pageSize);// 异步传页面翻页尺寸
    		ipage.setParams(this.setIpageParams("mfOaLawyer", mfOaLawyer));
    		ipage = mfOaLawyerFeign.findHireLawyerByPageAjax(ipage);
    		JsonTableUtil jtu = new JsonTableUtil();
    		String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
    		ipage.setResult(tableHtml);
    		dataMap.put("ipage", ipage);
    		dataMap.put("flag", "success");
    	} catch (Exception e) {
    		e.printStackTrace();
    		dataMap.put("flag", "error");
    		dataMap.put("msg", e.getMessage());
    	}
    	return dataMap;
    }


    /**
     * Description:律师管理--打开新增律师界面
     *
     * @param: ajaxData
     * @return: dataMap
     * @auther: jingwenhao
     * @date: 2018/6/28 10:38
     */
    @RequestMapping("/input")
    public String input(Model model) throws Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        //获取表单对象
        FormData formlawyer0002 = formService.getFormData("lawyer0002");
        model.addAttribute("formlawyer0002", formlawyer0002);
        model.addAttribute("query", "");
        return "/component/oa/lawyer/MfOaLawyer_Insert";
    }

    /**
     * Description:律师管理--完成新增律师信息
     *
     * @param: ajaxData
     * @return: dataMap
     * @auther: jingwenhao
     * @date: 2018/6/28 10:38
     */
    @ResponseBody
    @RequestMapping(value = "/insertAjax")
    public Map<String, Object> insertAjax(String ajaxData) throws Exception {
        ActionContext.initialize(request,
                response);
        FormService formService = new FormService();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            FormData formlawyer0002 = formService.getFormData("lawyer0002");
            getFormValue(formlawyer0002, getMapByJson(ajaxData));
            if (this.validateFormData(formlawyer0002)) {
                MfOaLawyer mfOaLawyer = new MfOaLawyer();
                setObjValue(formlawyer0002, mfOaLawyer);
                // 设置律师对象当前操作人信息
                mfOaLawyer.setOpNo(User.getRegNo(request)); //设置操作人编号
                mfOaLawyer.setOpName(User.getRegName(request)); //设置操作人姓名
                mfOaLawyer.setBrNo(User.getOrgNo(request));//设置操作人组织编号
                mfOaLawyer.setBrName(User.getOrgName(request));//设置操作人组织名称
                System.out.println("律师信息:" + mfOaLawyer);
                mfOaLawyerFeign.insert(mfOaLawyer);
                dataMap.put("flag", "success");
                dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
            } else {
                dataMap.put("flag", "error");
                dataMap.put("msg", this.getFormulavaliErrorMsg());
            }
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
            throw e;
        }
        return dataMap;
    }


    /**
     * Description: 律师管理--查看律师详情信息
     *
     * @param:
     * @return:
     * @auther: jingwenhao
     * @date: 2018/6/28 15:52
     */
    @RequestMapping("/getById")
    public String getById(Model model, String id) throws Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        MfOaLawyer mfOaLawyer = new MfOaLawyer();
        mfOaLawyer.setId(id);
        mfOaLawyer = mfOaLawyerFeign.getById(mfOaLawyer);
        //获取表单对象
        FormData formlawyerDetail0002 = formService.getFormData("lawyerDetail0002");
        getObjValue(formlawyerDetail0002, mfOaLawyer);
        model.addAttribute("mfOaLawyer", mfOaLawyer);
        model.addAttribute("formlawyerDetail0002", formlawyerDetail0002);
        model.addAttribute("query", "");
        return "/component/oa/lawyer/MfOaLawyer_Detail";
    }

    @RequestMapping("/getById1")
    public String getById1(Model model, String id) throws Exception {
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        MfOaLawyer mfOaLawyer = new MfOaLawyer();
        mfOaLawyer.setId(id);
        mfOaLawyer = mfOaLawyerFeign.getById(mfOaLawyer);
        //获取表单对象
        FormData formlawyer0002 = formService.getFormData("lawyer0002");
        getObjValue(formlawyer0002, mfOaLawyer);
        model.addAttribute("mfOaLawyer", mfOaLawyer);
        model.addAttribute("formlawyer0002", formlawyer0002);
        model.addAttribute("query", "");
        return "/component/oa/lawyer/MfOaLawyer_Detail1";
    }


    /**
     * Description: 律师管理--根据id删除律师
     *
     * @param:
     * @return:
     * @auther: jingwenhao
     * @date: 2018/6/28 16:36
     */
    @ResponseBody
    @RequestMapping("/deleteAjax")
    public Map<String, Object> deleteAjax(String id) throws Exception {
        ActionContext.initialize(request, response);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            MfOaLawyer mfOaLawyer = new MfOaLawyer();
            mfOaLawyer.setId(id);
            mfOaLawyerFeign.delete(mfOaLawyer);
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


    @ResponseBody
    @RequestMapping(value = "/updateAjax")
    public Map<String, Object> updateAjax(String ajaxData) throws Exception {
        System.out.println("更新信息事:" + ajaxData);
        ActionContext.initialize(request,
                response);
        FormService formService = new FormService();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            FormData formlawyer0002 = formService.getFormData("lawyer0002");
            getFormValue(formlawyer0002, getMapByJson(ajaxData));
            if (this.validateFormData(formlawyer0002)) {
                MfOaLawyer mfOaLawyer = new MfOaLawyer();
                setObjValue(formlawyer0002, mfOaLawyer);
                // 设置最后一次修改时间
                mfOaLawyer.setLastModTime(DateUtil.getTime());
                // 设置最后一次修改日期
                mfOaLawyer.setLstModDate(DateUtil.getDate());
                mfOaLawyerFeign.update(mfOaLawyer);
                dataMap.put("flag", "success");
                dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
            } else {
                dataMap.put("flag", "error");
                dataMap.put("msg", this.getFormulavaliErrorMsg());
            }
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
            throw e;
        }
        return dataMap;
    }


}
