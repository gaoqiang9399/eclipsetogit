package app.component.frontview.controller;

import app.component.frontview.entity.VwLinkManage;
import app.component.frontview.feign.VwImageFeign;
import app.component.frontview.feign.VwLinkManageFeign;
import app.component.frontview.util.UploadUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.util.StringUtil;
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
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Title: VwLinkManageAction.java Description:友情链接和合作伙伴管理
 * 
 * @author:kaifa@dhcc.com.cn
 * @Tue May 02 14:15:45 CST 2017
 **/
@Controller
@RequestMapping("/vwLinkManage")
public class VwLinkManageController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入VwLinkManageBo
	@Autowired
	private VwImageFeign vwImageFeign;
	@Autowired
	private VwLinkManageFeign vwLinkManageFeign;
	// 全局变量
	// 异步参数

	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request, response);
		return "/component/frontview/VwLinkManage_List";
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
		VwLinkManage vwLinkManage = new VwLinkManage();
		try {
			vwLinkManage.setCustomQuery(ajaxData);// 自定义查询参数赋值
			vwLinkManage.setCriteriaList(vwLinkManage, ajaxData);// 我的筛选
			// this.getRoleConditions(vwLinkManage,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			Map<String, Object> params=new HashMap<>();
			params.put("vwLinkManage", vwLinkManage);
			// 自定义查询Bo方法
			ipage.setParams(params);
			ipage = vwLinkManageFeign.findByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			// logger.error("异常：", e);
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
	public Map<String, Object> insertAjax(String link, String name, String fileType, String linkType, MultipartFile upload)
			throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			String id=WaterIdUtil.getWaterId();
			VwLinkManage vwLinkManage = new VwLinkManage();
			vwLinkManage.setId(id);
			vwLinkManage.setName(name);
			vwLinkManage.setFileType(fileType);
			vwLinkManage.setLink(link);
			vwLinkManage.setLinkType(linkType);
			if(upload!=null) {
				if(StringUtil.isNotEmpty(vwLinkManage.getFileType())){//页面已控制 fileType和文件同时存在同时消失
					String fileName="link_"+id+"."+vwLinkManage.getFileType();
					String folder="vw/link";
					UploadUtils.uploadVwImg(upload,folder,fileName);
					vwLinkManage.setLinkFile(folder+"/"+fileName);
				}
			}
			vwLinkManageFeign.insert(vwLinkManage);
			dataMap.put("flag", "success");
			dataMap.put("msg", "新增成功");
		} catch (Exception e) {
			// logger.error("异常：", e);
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
	public Map<String, Object> updateAjax(String id, String link, String name, String fileType, String linkType, MultipartFile upload) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			VwLinkManage vwLinkManage = new VwLinkManage();
			vwLinkManage.setName(name);
			vwLinkManage.setFileType(fileType);
			vwLinkManage.setLink(link);
			vwLinkManage.setLinkType(linkType);
			vwLinkManage.setId(id);
			if(StringUtil.isNotEmpty(vwLinkManage.getFileType())){//文件类型不为空，说明要上传文件
				String fileName="link_"+vwLinkManage.getId()+"."+vwLinkManage.getFileType();
				String folder="vw/link";
				UploadUtils.uploadVwImg(upload,folder,fileName);
				vwLinkManage.setLinkFile(folder+"/"+fileName);
				
			}//如果文件类型为空，说明不上传文件。linkFile还是原来的值
			
			vwLinkManageFeign.update(vwLinkManage);
			dataMap.put("flag", "success");
			dataMap.put("msg", "更新成功");
		} catch (Exception e) {
//			logger.error("异常：", e);
			dataMap.put("flag", "error");
			dataMap.put("msg", "更新失败");
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
		VwLinkManage vwLinkManage = new VwLinkManage();
		vwLinkManage.setId(id);
		try {
			VwLinkManage old=vwLinkManageFeign.getById(vwLinkManage);
			vwLinkManageFeign.delete(vwLinkManage);
			String linkFile=old.getLinkFile();
			if(StringUtil.isNotEmpty(linkFile)){
				vwImageFeign.deleteImage(linkFile);
			}

			dataMap.put("flag", "success");
			dataMap.put("msg", "成功");
		} catch (Exception e) {
//			logger.error("异常：", e);
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
		FormData formvwlink0002 = formService.getFormData("vwlink0002");
		model.addAttribute("formvwlink0002", formvwlink0002);
		model.addAttribute("query", "");
		return "/component/frontview/VwLinkManage_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String id) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formvwlink0002 = formService.getFormData("vwlink0001");
		getFormValue(formvwlink0002);
		VwLinkManage vwLinkManage = new VwLinkManage();
		vwLinkManage.setId(id);
		vwLinkManage = vwLinkManageFeign.getById(vwLinkManage);
		getObjValue(formvwlink0002, vwLinkManage);
		model.addAttribute("vwLinkManage", vwLinkManage);
		model.addAttribute("formvwlink0002", formvwlink0002);
		model.addAttribute("query", "");
		model.addAttribute("factorWebUrl", UploadUtils.getFactorWebUrl());
		return "/component/frontview/VwLinkManage_Detail";
	}


	/**fileServiceUrl
	 * 新增校验
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateInsert")
	public void validateInsert(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formvwlink0002 = formService.getFormData("vwlink0002");
		getFormValue(formvwlink0002);
		boolean validateFlag = this.validateFormData(formvwlink0002);
	}

	/**
	 * 修改校验
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateUpdate")
	public void validateUpdate(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formvwlink0002 = formService.getFormData("vwlink0002");
		getFormValue(formvwlink0002);
		boolean validateFlag = this.validateFormData(formvwlink0002);
	}

}
