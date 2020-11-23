package  app.component.prdct.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.util.toolkit.Ipage;
import com.core.struts.taglib.JsonTableUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;

import app.component.prdct.entity.MfKindNodeIntercept;
import app.component.prdct.feign.MfKindNodeInterceptFeign;
import cn.mftcc.common.MessageEnum;

/**
 * Title: MfKindNodeInterceptAction.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Tue Jul 04 10:25:37 CST 2017
 **/
@Controller
@RequestMapping("/mfKindNodeIntercept")
public class MfKindNodeInterceptController extends BaseFormBean{
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	//注入MfKindNodeInterceptBo
	@Autowired
	private MfKindNodeInterceptFeign mfKindNodeInterceptFeign;
	//全局变量
	//异步参数
	//表单变量
	
	
	/**
	 * 
	 * 方法描述： 根据类型获取不同类别的未配置风险项列表
	 * @return
	 * @throws Exception
	 * String
	 * @author zhs
	 * @date 2017-7-6 下午7:47:22
	 */
	@RequestMapping(value = "/getRiskInterceptList")
	public String getRiskInterceptList(Model model, String kindNo,String nodeNo) throws Exception{
		ActionContext.initialize(request,response);
//		MfKindNodeIntercept mfKindNodeIntercept = new MfKindNodeIntercept();
//		mfKindNodeIntercept.setKindNo(kindNo);
//		mfKindNodeIntercept.setNodeNo(nodeNo);
//		List<MfKindNodeIntercept> mfKindNodeInterceptList = mfKindNodeInterceptFeign.getRiskInterceptList(mfKindNodeIntercept);
//		model.addAttribute("mfKindNodeInterceptList", mfKindNodeInterceptList);
		model.addAttribute("query", "");
		model.addAttribute("kindNo", kindNo);
		model.addAttribute("nodeNo", nodeNo);
		return "/component/prdct/MfKindNodeIntercept_InterceList";
	}


	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(Ipage ipage, Integer pageNo, Integer pageSize, String tableId, String tableType, String ajaxData,String  kindNo,String nodeNo ) throws Exception {
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfKindNodeIntercept mfKindNodeIntercept = new MfKindNodeIntercept();
		try {
			mfKindNodeIntercept.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfKindNodeIntercept.setCustomSorts(ajaxData);//自定义排序参数赋值
			mfKindNodeIntercept.setCriteriaList(mfKindNodeIntercept, ajaxData);//我的筛选
			mfKindNodeIntercept.setKindNo(kindNo);
			mfKindNodeIntercept.setNodeNo(nodeNo);
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			//自定义查询Bo方法
			ipage.setParams(this.setIpageParams("mfKindNodeIntercept", mfKindNodeIntercept));
			ipage = mfKindNodeInterceptFeign.findByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String  tableHtml = jtu.getJsonStr(tableId,tableType,(List)ipage.getResult(), ipage,true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage",ipage);
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
	@RequestMapping(value = "/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData,String kindNo,String nodeNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfKindNodeIntercept mfKindNodeIntercept = new MfKindNodeIntercept();
		mfKindNodeIntercept.setKindNo(kindNo);
		mfKindNodeIntercept.setNodeNo(nodeNo);
		mfKindNodeIntercept.setItemNo(ajaxData);
		try{
			dataMap = mfKindNodeInterceptFeign.insert(mfKindNodeIntercept);
			dataMap.put("flag", "success");
			dataMap.put("msg", "新增成功");
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
	public Map<String, Object> deleteAjax(String kindNodeInterceptId,String kindNo,String nodeNo) throws Exception{
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfKindNodeIntercept mfKindNodeIntercept = new MfKindNodeIntercept();
		mfKindNodeIntercept.setKindNodeInterceptId(kindNodeInterceptId);
		mfKindNodeIntercept.setKindNo(kindNo);
		mfKindNodeIntercept.setNodeNo(nodeNo);
		try {
			dataMap = mfKindNodeInterceptFeign.delete(mfKindNodeIntercept);
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED_DELETE.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_DELETE.getMessage());
			throw e;
		}
		return dataMap;
	}
	/**
	 * 查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String kindNodeInterceptId) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		FormData  formintercept0001 = formService.getFormData("intercept0001");
		 getFormValue(formintercept0001);
		MfKindNodeIntercept  mfKindNodeIntercept = new MfKindNodeIntercept();
		mfKindNodeIntercept.setKindNodeInterceptId(kindNodeInterceptId);
		 mfKindNodeIntercept = mfKindNodeInterceptFeign.getById(mfKindNodeIntercept);
		 getObjValue(formintercept0001, mfKindNodeIntercept);
		model.addAttribute("formintercept0001", formintercept0001);
		model.addAttribute("query", "");
		return "/component/prdct/MfKindNodeIntercept_Detail";
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
		FormData  formintercept0002 = formService.getFormData("intercept0002");
		 getFormValue(formintercept0002);
		 boolean validateFlag = this.validateFormData(formintercept0002);
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
		FormData  formintercept0002 = formService.getFormData("intercept0002");
		 getFormValue(formintercept0002);
		 boolean validateFlag = this.validateFormData(formintercept0002);
	}
	
}
