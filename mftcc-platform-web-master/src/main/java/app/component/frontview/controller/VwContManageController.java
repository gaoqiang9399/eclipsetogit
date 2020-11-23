package app.component.frontview.controller;

import app.component.frontview.entity.VwContManage;
import app.component.frontview.entity.VwMenuManage;
import app.component.frontview.feign.VwContManageFeign;
import app.component.frontview.feign.VwImageFeign;
import app.component.frontview.feign.VwMenuManageFeign;
import app.component.frontview.util.UploadUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.util.DateUtil;
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
 * Title: VwContManageAction.java Description:新闻内容管理
 * 
 * @author:kaifa@dhcc.com.cn
 * @Wed May 03 21:51:39 CST 2017
 **/
@Controller
@RequestMapping("/vwContManage")
public class VwContManageController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入VwContManageBo
	@Autowired
	private VwContManageFeign vwContManageFeign;
	@Autowired
	private VwMenuManageFeign vwMenuManageFeign;
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
		return "/component/frontview/VwContManage_List";
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
		VwContManage vwContManage = new VwContManage();
		try {
			vwContManage.setCustomQuery(ajaxData);// 自定义查询参数赋值
			vwContManage.setCriteriaList(vwContManage, ajaxData);// 我的筛选
			// this.getRoleConditions(vwContManage,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage.setParams(this.setIpageParams("vwContManage",vwContManage));
			ipage = vwContManageFeign.findByPage(ipage);
			List<Map<String,Object>> list=(List)ipage.getResult();
			for(Map<String,Object> vc:list){
				String contType=(String)vc.get("contType");
				if("1".equals(contType)){
					vc.put("contType","列表");
				}
				if("0".equals(contType)){
					vc.put("contType","内容");
				}
			}
			JsonTableUtil jtu = new JsonTableUtil();

			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			// logger.error("列表数据查询失败，", e);
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
	@ResponseBody
	@RequestMapping(value = "/insertAjax")
	public Map<String, Object> insertAjax(String title, String remark, String keywords, String contType, String top,
			String block, String isOnline, String outLink, String link, String content, String fileType,
			String fileType1, MultipartFile upload, MultipartFile upload1) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			// 检查一下 如果菜单不是列表，又已经添加了数据，则提示会覆盖老数据。
			VwContManage vwContManage = new VwContManage();
			// content是插件提交的html，不在表单设计器的form表单中。
			String id=WaterIdUtil.getWaterId();
			vwContManage.setId(id);
			vwContManage.setTitle(title);
			vwContManage.setRemark(remark);
			vwContManage.setKeywords(keywords);
			vwContManage.setContType(contType);
			vwContManage.setTop(top);
			vwContManage.setBlock(block);
			vwContManage.setIsOnline(isOnline);
			vwContManage.setOutLink(outLink);
			vwContManage.setLink(link);
			vwContManage.setContent(content);
			vwContManage.setFileType(fileType);
			vwContManage.setFileType1(fileType1);
			String foler="vw/news/"+DateUtil.getDate();
			if(upload!=null) {
				String fileName="cont_"+id+"."+vwContManage.getFileType();
				UploadUtils.uploadVwImg(upload,foler,fileName);
				vwContManage.setFileName(foler+"/"+fileName);
			}
			if(upload1!=null) {
				String fileName1="out_"+id+"."+vwContManage.getFileType1();
				UploadUtils.uploadVwImg(upload,foler,fileName1);
				vwContManage.setOutLinkImg(foler+"/"+fileName1);
			}
			vwContManageFeign.insert(vwContManage);
			dataMap.put("flag", "success");
			dataMap.put("msg", "新增成功");
		} catch (Exception e) {
			// logger.error("AJAX新增失败，", e);
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
	public Map<String, Object> updateAjax(String id, String title, String remark, String keywords, String contType, String top,
			String block, String isOnline, String outLink, String link, String content, String fileType,
			String fileType1, MultipartFile upload, MultipartFile upload1) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		VwContManage vwContManage = new VwContManage();
		try {
			vwContManage.setId(id);
			vwContManage.setTitle(title);
			vwContManage.setRemark(remark);
			vwContManage.setKeywords(keywords);
			vwContManage.setContType(contType);
			vwContManage.setTop(top);
			vwContManage.setBlock(block);
			vwContManage.setIsOnline(isOnline);
			vwContManage.setOutLink(outLink);
			vwContManage.setLink(link);
			vwContManage.setContent(content);
			vwContManage.setFileType(fileType);
			vwContManage.setFileType1(fileType1);
			String uploadBase64 =null;
			String upload1Base64 =null;
			String foler="vw/news/"+DateUtil.getDate();
			if(upload!=null) {
				String fileName="cont_"+id+"."+vwContManage.getFileType();
				UploadUtils.uploadVwImg(upload,foler,fileName);//直接上传覆盖
				vwContManage.setFileName(foler+"/"+fileName);
			}
			if(upload1!=null) {
				String fileName="out_"+id+"."+vwContManage.getFileType();
				UploadUtils.uploadVwImg(upload1,foler,fileName);//直接上传覆盖
				vwContManage.setOutLinkImg(foler+"/"+fileName);
			}
			vwContManageFeign.update(vwContManage);
			dataMap.put("flag", "success");
			dataMap.put("msg", "更新成功");
		} catch (Exception e) {
			e.printStackTrace();
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

	@ResponseBody
	@RequestMapping(value = "/deleteAjax")
	public Map<String, Object> deleteAjax(String id) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		VwContManage vwContManage = new VwContManage();
		vwContManage.setId(id);
		try {
			vwContManage=vwContManageFeign.getById(vwContManage);
			vwContManageFeign.delete(vwContManage);
			String fileName=vwContManage.getFileName();
			String outLinkImg=vwContManage.getOutLinkImg();


			//删除文件
			if(StringUtil.isNotEmpty(fileName)){
				vwImageFeign.deleteImage(fileName);
			}
			if(StringUtil.isNotEmpty(outLinkImg)){
				vwImageFeign.deleteImage(outLinkImg);
			}
			dataMap.put("flag", "success");
			dataMap.put("msg", "成功");
		} catch (Exception e) {
//			logger.error("Ajax异步删除，", e);
			dataMap.put("flag", "error");
			dataMap.put("msg", "失败");
			throw e;
		}
		return dataMap;
	}

	/**
	 * 获取菜单内容
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getContentById")
	@ResponseBody
	public Map<String, Object> getContentById(Model model, String id) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		VwContManage vwContManage = new VwContManage();
		vwContManage.setId(id);
		try {
			vwContManage = vwContManageFeign.getById(vwContManage);
			if (vwContManage != null) {
				dataMap.put("str", vwContManage.getContent());
			}
			dataMap.put("flag", "success");
			dataMap.put("msg", "成功");
		} catch (Exception e) {
//			logger.error("获取菜单内容失败，", e);
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
		FormData formvwcont0002 = formService.getFormData("vwcont0002");
		model.addAttribute("formvwcont0002", formvwcont0002);
		model.addAttribute("query", "");
		model.addAttribute("factorWebUrl", UploadUtils.getFactorWebUrl());
		model.addAttribute("folder", "news");
		return "/component/frontview/VwContManage_Insert";
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
		FormData formvwcont0001 = formService.getFormData("vwcont0001");
		getFormValue(formvwcont0001);
		VwContManage vwContManage = new VwContManage();
		vwContManage.setId(id);
		vwContManage = vwContManageFeign.getById(vwContManage);
		getObjValue(formvwcont0001, vwContManage);
		model.addAttribute("vwContManage", vwContManage);
		model.addAttribute("formvwcont0001", formvwcont0001);
		model.addAttribute("query", "");
		model.addAttribute("factorWebUrl", UploadUtils.getFactorWebUrl());
		model.addAttribute("folder", "news");
		return "/component/frontview/VwContManage_Detail";
	}

	/**
	 * 获取内容相关图片 in:内容展示图,out:外部链接图片
	 */
	@RequestMapping(value = "/getImgBase64")
	@ResponseBody
	public Map<String, Object> getImgBase64(Model model, String id, String type) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			String str = vwContManageFeign.getBase64(id, type);
			dataMap.put("flag", "success");
			dataMap.put("msg", "更新成功");
			dataMap.put("str", str);
		} catch (Exception e) {
//			logger.error("获取菜单内容失败，抛出异常", e);
			dataMap.put("flag", "error");
			dataMap.put("msg", "更新失败");
		}
		return dataMap;
	}

	/**
	 * 检查此内容是否会覆盖之前的内容 如果菜单类型不是列表，新增时会清空此菜单下所有的内容
	 */
	@ResponseBody
	@RequestMapping(value = "/checkCountAjax")
	public Map<String, Object> checkCountAjax(String block) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		String menuType = vwContManageFeign.getMenuType(block);
		try {
			if ("0".equals(menuType)) {// 如果不是列表
				Integer count = vwContManageFeign.getCountByBlock(block);
				if (count.compareTo(0) != 0) {// 已经有了数据
					dataMap.put("data", "Y");// 提示
				} else {
					dataMap.put("data", "N");// 不提示
				}
			}
			dataMap.put("flag", "success");
		} catch (Exception e) {
//			logger.error("检查菜单内容条数失败，抛出异常", e);
			dataMap.put("flag", "error");
			dataMap.put("msg", "更新失败");
		}
		return dataMap;
	}

	/**
	 * 新增校验
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateInsert")
	public void validateInsert(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formvwcont0002 = formService.getFormData("vwcont0002");
		getFormValue(formvwcont0002);
		boolean validateFlag = this.validateFormData(formvwcont0002);
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
		FormData formvwcont0002 = formService.getFormData("vwcont0002");
		getFormValue(formvwcont0002);
		boolean validateFlag = this.validateFormData(formvwcont0002);
	}

	/**
	 * 获取内容上面的菜单，用以生成下拉框
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getAllMenu")
	@ResponseBody
	public Map<String, Object> getAllMenu(Model model, String ajaxData) {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			List<VwMenuManage> list = vwMenuManageFeign.getAllMenu();
			dataMap.put("list", list);
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", "失败");
//			logger.error("获取所有菜单失败，", e);
		}
		return dataMap;
	}


}
