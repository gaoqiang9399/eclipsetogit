package app.component.frontview.controller;

import app.component.frontview.entity.VwSetManage;
import app.component.frontview.feign.VwImageFeign;
import app.component.frontview.feign.VwSetManageFeign;
import app.component.frontview.util.UploadUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.util.PropertiesUtil;
import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonFormUtil;
import com.core.struts.taglib.JsonTableUtil;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Title: VwSetManageAction.java Description:
 * vwSetManage/getSetFormHtml
 * @author:kaifa@dhcc.com.cn
 * @Fri Apr 28 15:40:30 CST 2017
 **/
@Controller
@RequestMapping("/vwSetManage")
public class VwSetManageController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入VwSetManageBo
	@Autowired
	private VwSetManageFeign vwSetManageFeign;
	@Autowired
	private VwImageFeign vwImageFeign;
	// 全局变量
	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request, response);
		return "/component/frontview/VwSetManage_List";
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
			String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		VwSetManage vwSetManage = new VwSetManage();
		try {
			vwSetManage.setCustomQuery(ajaxData);// 自定义查询参数赋值
			vwSetManage.setCriteriaList(vwSetManage, ajaxData);// 我的筛选
			// this.getRoleConditions(vwSetManage,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage = vwSetManageFeign.findByPage(ipage, vwSetManage);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
			/**
			 * ipage.setResult(tableHtml); dataMap.put("ipage",ipage); 需要改进的方法
			 * dataMap.put("tableData",tableHtml);
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
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formfrontview0002 = formService.getFormData("frontview0002");
			getFormValue(formfrontview0002, getMapByJson(ajaxData));
			if (this.validateFormData(formfrontview0002)) {
				VwSetManage vwSetManage = new VwSetManage();
				setObjValue(formfrontview0002, vwSetManage);
				vwSetManageFeign.insert(vwSetManage);
				dataMap.put("flag", "success");
				dataMap.put("msg", "新增成功");
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "新增失败");
			throw e;
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
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, Object> mp = getMapByJson(ajaxData);// 现在是单字段编辑
			VwSetManage vwSetManage = new Gson().fromJson(new Gson().toJson(mp), VwSetManage.class);
			vwSetManageFeign.update(vwSetManage);
			dataMap.put("flag", "success");
			dataMap.put("msg", "保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "保存失败");
//			logger.error("updateAjax方法出错，执行action层失败，抛出异常，", e);
			throw e;
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
		VwSetManage vwSetManage = new VwSetManage();
		vwSetManage.setId(id);
		try {
			vwSetManageFeign.delete(vwSetManage);
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
	 * Ajax异步删除
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteImage")
	@ResponseBody
	public Map<String, Object> deleteImage(String filePath) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			vwImageFeign.deleteImage(filePath);
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
	 * 新增页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/input")
	public String input(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formfrontview0002 = formService.getFormData("frontview0002");
		model.addAttribute("formfrontview0002", formfrontview0002);
		model.addAttribute("query", "");
		return "/component/frontview/VwSetManage_Insert";
	}

	/***
	 * 新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insert")
	public String insert(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formfrontview0002 = formService.getFormData("frontview0002");
		getFormValue(formfrontview0002);
		VwSetManage vwSetManage = new VwSetManage();
		setObjValue(formfrontview0002, vwSetManage);
		vwSetManageFeign.insert(vwSetManage);
		getObjValue(formfrontview0002, vwSetManage);
		this.addActionMessage(model, "保存成功");
		List<VwSetManage> vwSetManageList = (List<VwSetManage>) vwSetManageFeign.findByPage(this.getIpage(), vwSetManage).getResult();
		model.addAttribute("vwSetManageList", vwSetManageList);
		model.addAttribute("formfrontview0002", formfrontview0002);
		model.addAttribute("query", "");
		return "/component/frontview/VwSetManage_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String query) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formfrontview0002 = formService.getFormData("frontview0001");
		getFormValue(formfrontview0002);
		VwSetManage vwSetManage = new VwSetManage();
		vwSetManage = vwSetManageFeign.getVwSetBean();
		getObjValue(formfrontview0002, vwSetManage);
		JsonFormUtil jsonFormUtil = new JsonFormUtil();
		String htmlStr = jsonFormUtil.getJsonStr(formfrontview0002, "propertySeeTag", query);
		model.addAttribute("vwSetManage", vwSetManage);
		model.addAttribute("formfrontview0002", formfrontview0002);
		model.addAttribute("query", "");
		return "/component/frontview/VwSetManage_Detail";
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax() throws Exception {
		ActionContext.initialize(request, response);
		VwSetManage vwSetManage = vwSetManageFeign.getVwSetBean();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		if(vwSetManage!=null) {
			dataMap.put("data", vwSetManage);
		}
		return dataMap;
	}

	@RequestMapping(value = "/getSetFormHtml")
	@ResponseBody
	public Map<String, Object> getSetFormHtml(Model model, String query) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		request.setAttribute("ifBizManger", "3");// 没有这个参数,不能生成单字段编辑的表单
		VwSetManage vwSetManage = new VwSetManage();
		vwSetManage = vwSetManageFeign.getVwSetBean();
		String formId="frontview0001";
		String sysProjectName = PropertiesUtil.getSysParamsProperty("sys.project.name");
		FormData formfrontview0001 = formService.getFormData(formId);
		getFormValue(formfrontview0001);
		getObjValue(formfrontview0001, vwSetManage);
		JsonFormUtil jsonFormUtil = new JsonFormUtil();
		String htmlStr = jsonFormUtil.getJsonStr(formfrontview0001, "propertySeeTag", "");
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("data", htmlStr);
		//dataMap.put("ifBizManger", "3");
		return dataMap;
	}

	/**
	 * 获取上传的二维码图片的base64字符
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getImgBase64")
	@ResponseBody
	public Map<String, Object> getImgBase64(Model model, String type) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			String str = vwSetManageFeign.getImgBase64(type);
			dataMap.put("str", str);
			dataMap.put("flag", "success");
			dataMap.put("msg", "成功");
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", "获取图片失败");
//			logger.error("获取二维码图片失败，", e);
		}
		return dataMap;
	}

	@RequestMapping(value = "/uploadImg")
	@ResponseBody
	public Map<String, Object> uploadImg(String fileType,  String flag,  MultipartFile upload) throws Exception {
		Map<String,Object> dataMap=new HashMap<>();
		try{
			String fileName=null;
			if("qq".equals(flag)){
				fileName="set_qqCodeImg."+fileType;
			}else if("wx".equals(flag)){
				fileName="set_wxCodeImg."+fileType;
			}else if("logo".equals(flag)){
				fileName="set_logoImg."+fileType;
			}else {
			}
			UploadUtils.uploadVwImg(upload,"vw/set",fileName);
			fileName = vwSetManageFeign.updateImg("vw/set/"+fileName, fileType, flag);
			dataMap.put("data", fileName);
			dataMap.put("flag", "success");
			return dataMap;
		}catch (Exception e){
			//logger.error("上传失败，", e);
			e.printStackTrace();
			//throw e;
		}
		dataMap.put("flag", "error");
		return dataMap;
	}

}
