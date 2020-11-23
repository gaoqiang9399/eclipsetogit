package app.component.frontview.controller;

import app.component.frontview.entity.VwBannerManage;
import app.component.frontview.feign.VwBannerManageFeign;
import app.component.frontview.feign.VwImageFeign;
import app.component.frontview.util.Base64Util;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Title: VwBannerManageAction.java Description:banner管理
 * 
 * @author:yht@dhcc.com.cn
 * @Wed Apr 26 11:06:06 CST 2017
 **/
@Controller
@RequestMapping("/vwBannerManage")
public class VwBannerManageController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入VwBannerManageBo
	@Autowired
	private VwBannerManageFeign vwBannerManageFeign;
	@Autowired
	private VwImageFeign vwImageFeign;


	/**
	 * 先上传到服务器不保存，再浏览图片图片(前端交易共用)
	 */
	@ResponseBody
	@RequestMapping(value = "/uploadShowImg",method=RequestMethod.POST)
	public Map<String, Object> uploadShowImg(Model model, String uploadFileName, MultipartFile upload) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			String str = Base64Util.encodeBase64MultFile(upload);
			str = Base64Util.addFrontStr(uploadFileName, str);
			dataMap.put("flag", "success");
			dataMap.put("base64", str);
		} catch (Exception e) {
			// logger.error("预览失败", e);
			e.printStackTrace();
		}
		return dataMap;
	}


	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request, response);
		return "/component/frontview/VwBannerManage_List";
	}

	/***
	 * 列表数据查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType, String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		VwBannerManage vwBannerManage = new VwBannerManage();
		try {
			vwBannerManage.setCustomQuery(ajaxData);// 自定义查询参数赋值
			vwBannerManage.setCriteriaList(vwBannerManage, ajaxData);// 我的筛选
			// this.getRoleConditions(vwBannerManage,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage.setParams(this.setIpageParams("vwBannerManage",vwBannerManage));
			ipage = vwBannerManageFeign.findByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
//			logger.error("findByPageAjax方法出错，执行action层失败，抛出异常，");
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
	public Map<String, Object> insertAjax(String name,String type,String remark,String sort,String link,String useFlag,MultipartFile upload) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formvwbanner0002 = formService.getFormData("vwbanner0002");
			Map<String,Object> map=new HashMap<>();
			map.put("name",name);
			map.put("type",type);
			map.put("remark",remark);
			map.put("sort",sort);
			map.put("link",link);
			map.put("useFlag",useFlag);

			getFormValue(formvwbanner0002, map);
			if (this.validateFormData(formvwbanner0002)) {
				VwBannerManage vwBannerManage = new VwBannerManage();
				setObjValue(formvwbanner0002, vwBannerManage);
				String id=WaterIdUtil.getWaterId();
				vwBannerManage.setId(id);
				//文件上传
				if(upload==null){
					dataMap.put("flag", "error");
					dataMap.put("msg", "请上传文件");
					return dataMap;
				}
				String originalFilename=upload.getOriginalFilename();
				String ext=originalFilename.substring(originalFilename.lastIndexOf("."));

				String folder="vw/banner";
				String fieName="banner_"+id+ext;

				UploadUtils.uploadVwImg(upload,folder,fieName);
				vwBannerManage.setImgAds(folder+"/"+fieName);
				vwBannerManage = vwBannerManageFeign.insert(vwBannerManage);
				//文件上传
				dataMap.put("flag", "success");
				dataMap.put("msg", "新增成功");
				dataMap.put("fileName", vwBannerManage.getImgAds());
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
//			logger.error("异常：", e);
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
	public Map<String, Object> updateAjax(String id,String name,String type,String remark,String sort,String link,String useFlag,String imgAds,MultipartFile upload) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formvwbanner0002 = formService.getFormData("vwbanner0001");
			Map<String,Object> map=new HashMap<>();
			map.put("name",name);
			map.put("type",type);
			map.put("remark",remark);
			map.put("sort",sort);
			map.put("link",link);
			map.put("useFlag",useFlag);
			map.put("imgAds",imgAds);

			getFormValue(formvwbanner0002, map);
			if (this.validateFormData(formvwbanner0002)) {
				VwBannerManage vwBannerManage = new VwBannerManage();
				setObjValue(formvwbanner0002, vwBannerManage);
				if(upload!=null){
					String originalFilename=upload.getOriginalFilename();
					String ext=originalFilename.substring(originalFilename.lastIndexOf("."));
					String folder="vw/banner";
					String fieName="banner_"+id+ext;
					UploadUtils.uploadVwImg(upload,folder,fieName);
					vwBannerManage.setImgAds(folder+"/"+fieName);
				}
				vwBannerManage = vwBannerManageFeign.update(vwBannerManage);
				dataMap.put("flag", "success");
				dataMap.put("msg", "更新成功");
				dataMap.put("fileName", vwBannerManage.getImgAds());
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
//			logger.error("updateAjax方法出错，执行action层失败，", e);
			dataMap.put("flag", "error");
			dataMap.put("msg", "更新失败");
			throw e;
		}
		return dataMap;
	}

	/**
	 * 仅删除文件
	 * 
	 * @throws Exception
	 * 
	 */
	@RequestMapping(value = "/deleteFileAjax")
	@ResponseBody
	public Map<String, Object> deleteFileAjax(String uploadFileName) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		vwImageFeign.deleteImage(uploadFileName);
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
	public Map<String, Object> deleteAjax(String id, String sort) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		VwBannerManage vwBannerManage = new VwBannerManage();
		vwBannerManage.setId(id);
		vwBannerManage.setSort(sort);
		try {
			vwBannerManage=vwBannerManageFeign.getById(vwBannerManage);
			vwBannerManageFeign.delete(vwBannerManage);
			String imgAds=vwBannerManage.getImgAds();
			if(StringUtil.isNotEmpty(imgAds)){
				vwImageFeign.deleteImage(imgAds);
			}
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
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/input")
	public String input(Model model) throws Exception {
		try {
			FormService formService = new FormService();
			ActionContext.initialize(request, response);
			FormData formvwbanner0002 = formService.getFormData("vwbanner0002");
			model.addAttribute("formvwbanner0002", formvwbanner0002);
			model.addAttribute("query", "");
		} catch (Exception e) {
//			logger.error("异常：", e);
		}
		return "/component/frontview/VwBannerManage_Insert";
	}

	@RequestMapping(value = "/getMaxSort")
	@ResponseBody
	public Map<String, Object> getMaxSort(String type) {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			String maxSort = vwBannerManageFeign.getMaxSort(type);
			dataMap.put("maxSort", maxSort);
		} catch (Exception e) {
//			logger.error("异常：", e);
			dataMap.put("flag", "error");
			dataMap.put("msg", "失败");
		}
		return dataMap;
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String id) throws Exception {
		try {
			FormService formService = new FormService();
			ActionContext.initialize(request, response);
			FormData formvwbanner0002 = formService.getFormData("vwbanner0001");
			getFormValue(formvwbanner0002);
			VwBannerManage vwBannerManage = new VwBannerManage();
			vwBannerManage.setId(id);
			vwBannerManage = vwBannerManageFeign.getById(vwBannerManage);
			getObjValue(formvwbanner0002, vwBannerManage);
			model.addAttribute("formvwbanner0002", formvwbanner0002);
			model.addAttribute("query", "");
			model.addAttribute("factorWebUrl", UploadUtils.getFactorWebUrl());
		} catch (Exception e) {
//			logger.error("异常，", e);
		}
		return "/component/frontview/VwBannerManage_Detail";
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
		FormData formvwbanner0002 = formService.getFormData("vwbanner0002");
		getFormValue(formvwbanner0002);
		boolean validateFlag = this.validateFormData(formvwbanner0002);
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
		FormData formvwbanner0002 = formService.getFormData("vwbanner0002");
		getFormValue(formvwbanner0002);
		boolean validateFlag = this.validateFormData(formvwbanner0002);
	}

	/**
	 * 获取图片的base64字符串格式。
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getImgBase64")
	public Map<String, Object> getImgBase64(String id) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			String str = vwBannerManageFeign.getImgBase64(id);
			dataMap.put("str", str);
		} catch (Exception e) {
			// logger.error("banner管理：图片转base64字符串错误。");
			dataMap.put("flag", "error");
			dataMap.put("msg", "获取图片失败");
			throw e;
		}
		return dataMap;
	}

}
