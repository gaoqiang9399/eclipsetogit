package app.component.pact.assetmanage.controller;

import static org.hamcrest.CoreMatchers.nullValue;

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

import app.base.User;
import app.component.finance.bankacc.entity.CwCusBankAccManage;
import app.component.pact.assetmanage.entity.MfLitigationExpenseApply;
import app.component.pact.assetmanage.entity.MfLitigationExpenseInout;
import app.component.pact.assetmanage.feign.MfLitigationExpenseApplyFeign;
import app.component.pact.assetmanage.feign.MfLitigationExpenseInoutFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.DateUtil;
import cn.mftcc.util.WaterIdUtil;


/**
 * 
 * @author zkq
 *
 */
@Controller
@RequestMapping("/mfLitigationExpenseInput")
public class MfLitigationExpenseInoutController extends BaseFormBean{
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
    @Autowired
	private MfLitigationExpenseInoutFeign mfLitigationExpenseInputFeign;
    @Autowired
    private  MfLitigationExpenseApplyFeign     mfLitigationExpenseApplyFeign;
	/**
	 * 总产管理列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getAssetManageListPage(Model model, String ajaxData,String inoutFlag) throws Exception {
		ActionContext.initialize(request, response);
		model.addAttribute("inoutFlag", inoutFlag);
		model.addAttribute("query", "");
		if("0".equals(inoutFlag)){
		 return "component/pact/assetmanage/MfLitigationExpenseInout_List";
		}else{
		  return "component/pact/assetmanage/MfLitigationExpenseInout_List2";
		}
	}

	/**
	 * 新增页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/input")
	public String input(Model model,String inoutFlag) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		MfLitigationExpenseInout mfLitigationExpenseInout=new MfLitigationExpenseInout();
		mfLitigationExpenseInout.setRegDate(DateUtil.getDate());
		mfLitigationExpenseInout.setInoutId(WaterIdUtil.getWaterId("LEI"));
		mfLitigationExpenseInout.setApplicant(User.getRegName(request));
		mfLitigationExpenseInout.setInoutFlag(inoutFlag);
		FormData formlitigationexpenseinoutbase = null;
		if("0".equals(inoutFlag)){
	      formlitigationexpenseinoutbase = formService.getFormData("litigationexpenseinoutbase");
		}else{
		  formlitigationexpenseinoutbase = formService.getFormData("litigationexpenseinoutbase1");
		}
		getObjValue(formlitigationexpenseinoutbase, mfLitigationExpenseInout);
		model.addAttribute("inoutFlag", inoutFlag);
		model.addAttribute("formlitigationexpenseinoutbase", formlitigationexpenseinoutbase);
		model.addAttribute("mfLitigationExpenseInout", mfLitigationExpenseInout);
		model.addAttribute("query", "");
		return "/component/pact/assetmanage/MfLitigationExpenseInout_Insert";
	}
	
	
	/***
	 * 列表数据查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
			String ajaxData,String inoutFlag) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfLitigationExpenseInout mfLitigationExpenseInout=new MfLitigationExpenseInout();
		try {
			mfLitigationExpenseInout.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfLitigationExpenseInout.setCriteriaList(mfLitigationExpenseInout, ajaxData);// 我的筛选
			mfLitigationExpenseInout.setInoutFlag(inoutFlag);
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage.setParams(this.setIpageParams("mfLitigationExpenseInout",mfLitigationExpenseInout));
			ipage = mfLitigationExpenseInputFeign.findByPage(ipage);
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
	 * 
	 * <p>Title: insertInoutAjax</p>  
	 * <p>Description: </p>  
	 * @param ajaxData
	 * @param flag
	 * @return
	 * @throws Exception  
	 * @author zkq  
	 * @date 2018年9月19日 上午9:25:40
	 */
	@RequestMapping(value = "/insertInoutAjax")
	@ResponseBody
	public Map<String, Object> insertInoutAjax(String ajaxData,String flag) throws Exception{
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormService formService = new FormService();
		MfLitigationExpenseInout mfLitigationExpenseInout=new MfLitigationExpenseInout();
		try {
			   dataMap=getMapByJson(ajaxData);
			FormData formlitigationexpenseinoutbase = null;
			if("0".equals(flag)){
				formlitigationexpenseinoutbase = formService.getFormData("litigationexpenseinoutbase");
			}else{
				formlitigationexpenseinoutbase = formService.getFormData("litigationexpenseinoutbase1");
			}
				getFormValue(formlitigationexpenseinoutbase, getMapByJson(ajaxData));
				if(this.validateFormData(formlitigationexpenseinoutbase)){
					setObjValue(formlitigationexpenseinoutbase, mfLitigationExpenseInout);
					mfLitigationExpenseInputFeign.insertInoutAjax(mfLitigationExpenseInout);
					dataMap.put("flag", "success");
					dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
					
				}else{
					dataMap.put("flag", "error");
					dataMap.put("msg",this.getFormulavaliErrorMsg());
				}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
		}
		return dataMap;
	}
	
	/**
	 * 
	 * <p>Title: selectLitigationApply</p>  
	 * <p>Description:打款选择诉讼申请费用审核通过的 </p>  
	 * @param pageNo
	 * @param applyStatus
	 * @return
	 * @throws Exception  
	 * @author zkq  
	 * @date 2018年9月19日 上午9:26:29
	 */
	@RequestMapping(value = "/selectLitigationApply")
	@ResponseBody
	public Map<String,Object> selectLitigationApply(int pageNo,String applyStatus)throws Exception{
		ActionContext.initialize(request,response);
		Map<String,Object> dataMap = new HashMap<String, Object>();
		try {
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			MfLitigationExpenseApply   mfLitigationExpenseApply   =  new  MfLitigationExpenseApply();
			mfLitigationExpenseApply.setApplyStatus(applyStatus);
			ipage.setParams(this.setIpageParams("mfLitigationExpenseApply", mfLitigationExpenseApply));
			ipage = mfLitigationExpenseApplyFeign.findByPage(ipage);
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
	 * <p>Title: selectLitigationInout</p>  
	 * <p>Description:退款选择诉讼费用打过款的 </p>  
	 * @param pageNo
	 * @param inoutFlag
	 * @return
	 * @throws Exception  
	 * @author zkq  
	 * @date 2018年9月19日 上午9:29:08
	 */
	@RequestMapping(value = "/selectLitigationInout")
	@ResponseBody
	public Map<String,Object> selectLitigationInout(int pageNo,String inoutFlag)throws Exception{
		ActionContext.initialize(request,response);
		Map<String,Object> dataMap = new HashMap<String, Object>();
		try {
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			MfLitigationExpenseInout   mfLitigationExpenseInout   =  new  MfLitigationExpenseInout();
			mfLitigationExpenseInout.setInoutFlag(inoutFlag);
			ipage.setParams(this.setIpageParams("mfLitigationExpenseInout", mfLitigationExpenseInout));
			ipage = mfLitigationExpenseInputFeign.findByPage(ipage);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}
	
}
