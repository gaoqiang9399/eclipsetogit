package  app.component.sys.controller;
import java.io.File;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.base.User;
import app.component.doc.feign.DocFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;

import app.component.common.EntityUtil;
import app.component.common.SysGlobalParams;
import app.component.sys.entity.MfSysCompanyMst;
import app.component.sys.feign.CompanyFileFeign;
import app.component.sys.feign.MfSysCompanyMstFeign;
import app.tech.upload.FeignSpringFormEncoder;
import app.tech.upload.ImageUtil;
import app.util.toolkit.Ipage;
import feign.Feign;

/**
 * Title: MfSysCompanyMstAction.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Wed May 03 15:16:02 CST 2017
 **/
@Controller
@RequestMapping("/mfSysCompanyMst")
public class MfSysCompanyMstController extends BaseFormBean{
	@Autowired
	private MfSysCompanyMstFeign mfSysCompanyMstFeign;
	@Autowired
	private DiscoveryClient discoveryClient;
	
	@Autowired  
	private HttpServletRequest request;
	@Autowired  
	private HttpServletResponse response;
	@Autowired
	private DocFeign docFeign;
	
   
	
	/**
	 * 列表打开页面请求
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage() throws Exception {
		ActionContext.initialize(request,response);
		return "/component/sys/MfSysCompanyMst_List";
	}
	/***
	 * 列表数据查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(String ajaxData,int pageNo,String tableId,String tableType) throws Exception {
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfSysCompanyMst mfSysCompanyMst = new MfSysCompanyMst();
		try {
			mfSysCompanyMst.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfSysCompanyMst.setCriteriaList(mfSysCompanyMst, ajaxData);//我的筛选
			//this.getRoleConditions(mfSysCompanyMst,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			//自定义查询Bo方法
			ipage.setParams(this.setIpageParams("mfSysCompanyMst", mfSysCompanyMst));
			ipage = mfSysCompanyMstFeign.findByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String  tableHtml = jtu.getJsonStr(tableId,tableType,(List)ipage.getResult(), ipage,true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage",ipage);
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
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		FormService formService = new FormService();
		try{
			FormData formsyscompany0002 = formService.getFormData("syscompany0002");
			getFormValue(formsyscompany0002, getMapByJson(ajaxData));
			if(this.validateFormData(formsyscompany0002)){
				MfSysCompanyMst mfSysCompanyMst = new MfSysCompanyMst();
				setObjValue(formsyscompany0002, mfSysCompanyMst);
				mfSysCompanyMstFeign.insert(mfSysCompanyMst);
				dataMap.put("flag", "success");
				dataMap.put("msg", "新增成功");
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		}catch(Exception e){
			e.printStackTrace();
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
	@RequestMapping(value = "/updateAjaxByOne")
	@ResponseBody
	public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception{
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormService formService = new FormService();
		FormData formsyscompany0002 = formService.getFormData("syscompany0002");
		getFormValue(formsyscompany0002, getMapByJson(ajaxData));
		MfSysCompanyMst mfSysCompanyMstJsp = new MfSysCompanyMst();
		setObjValue(formsyscompany0002, mfSysCompanyMstJsp);
		MfSysCompanyMst mfSysCompanyMst = mfSysCompanyMstFeign.getById(mfSysCompanyMstJsp);
		if(mfSysCompanyMst!=null){
			try{
				mfSysCompanyMst = (MfSysCompanyMst)EntityUtil.reflectionSetVal(mfSysCompanyMst, mfSysCompanyMstJsp, getMapByJson(ajaxData));
				mfSysCompanyMstFeign.update(mfSysCompanyMst);
				dataMap.put("flag", "success");
				dataMap.put("msg", "保存成功");
			}catch(Exception e){
				e.printStackTrace();
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
	@RequestMapping(value = "/updateAjax")
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfSysCompanyMst mfSysCompanyMst = new MfSysCompanyMst();
		FormService formService = new FormService();
		try{
			FormData formsyscompany0002 = formService.getFormData("syscompany0003");
			getFormValue(formsyscompany0002, getMapByJson(ajaxData));
			if(this.validateFormData(formsyscompany0002)){
				mfSysCompanyMst = new MfSysCompanyMst();
				setObjValue(formsyscompany0002, mfSysCompanyMst);
				mfSysCompanyMstFeign.update(mfSysCompanyMst);
				//重新加载全局变量
				SysGlobalParams.reloadParams(SysGlobalParams.ParamTypeEnum.COMPANY);
				dataMap.put("flag", "success");
				dataMap.put("msg", "更新成功");
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		}catch(Exception e){
			e.printStackTrace();
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
	@RequestMapping(value = "/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax() throws Exception {
		ActionContext.initialize(request,
				response);
		Map<String,Object> formData = new HashMap<String,Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfSysCompanyMst mfSysCompanyMst = new MfSysCompanyMst();
		mfSysCompanyMst.setComNo("100000");
		mfSysCompanyMst = mfSysCompanyMstFeign.getById(mfSysCompanyMst);
		if(mfSysCompanyMst!=null){
			dataMap.put("flag", "success");
			dataMap.put("data", mfSysCompanyMst);
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
	@RequestMapping(value = "/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String comNo) throws Exception{
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfSysCompanyMst mfSysCompanyMst = new MfSysCompanyMst();
		mfSysCompanyMst.setComNo(comNo);
		try {
			mfSysCompanyMstFeign.delete(mfSysCompanyMst);
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
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/input")
	public String input(Model model) throws Exception{
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		FormData formsyscompany0002 = formService.getFormData("syscompany0002");
		model.addAttribute("formsyscompany0002", formsyscompany0002);
		return "/component/sys/MfSysCompanyMst_Insert";
	}
	/***
	 * 新增
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insert")
	public String insert(Model model) throws Exception{
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		FormData formsyscompany0002 = formService.getFormData("syscompany0002");
		 getFormValue(formsyscompany0002);
		 MfSysCompanyMst mfSysCompanyMst = new MfSysCompanyMst();
		 setObjValue(formsyscompany0002, mfSysCompanyMst);
		 mfSysCompanyMstFeign.insert(mfSysCompanyMst);
		 getObjValue(formsyscompany0002, mfSysCompanyMst);
		 this.addActionMessage(model,"保存成功");
		 Ipage ipage = this.getIpage();
		 ipage.setParams(this.setIpageParams("mfSysCompanyMst", mfSysCompanyMst));
		 List<MfSysCompanyMst> mfSysCompanyMstList = (List<MfSysCompanyMst>)mfSysCompanyMstFeign.findByPage(ipage).getResult();
		 model.addAttribute("mfSysCompanyMstList", mfSysCompanyMstList);
		 return "/component/sys/MfSysCompanyMst_Insert";
	}
	/**
	 * 查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model) throws Exception{
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		FormData formsyscompany0001 = formService.getFormData("syscompany0003");
		 getFormValue(formsyscompany0001);
		 MfSysCompanyMst  mfSysCompanyMst = new MfSysCompanyMst();
		mfSysCompanyMst.setComNo("100000");
		 mfSysCompanyMst = mfSysCompanyMstFeign.getById(mfSysCompanyMst);
		 getObjValue(formsyscompany0001, mfSysCompanyMst);
		 model.addAttribute("formsyscompany0001", formsyscompany0001);
		 model.addAttribute("query", "");
		return "component/sys/MfSysCompanyMst_Detail";
	}
	/**
	 * 仅删除文件
	 * @throws Exception 
	 * 
	 */
	@RequestMapping(value = "/deleteFileAjax")
	@ResponseBody
	public Map<String, Object> deleteFileAjax(String flag) throws Exception{
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap = docFeign.deleteFile(flag,"100000");
		return dataMap;
	}
	/**
	 * 删除
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(String comNo) throws Exception {
		ActionContext.initialize(request,response);
		MfSysCompanyMst mfSysCompanyMst = new MfSysCompanyMst();
		mfSysCompanyMst.setComNo(comNo);
		mfSysCompanyMstFeign.delete(mfSysCompanyMst);
		return getListPage();
	}
	
	/**
	 * 图片查看
	 */
	@RequestMapping(value = "/viewImage")
	@ResponseBody
	public Map<String, Object> viewImage(String uploadFileName) throws Exception{
		 ActionContext.initialize(request, response);
		 Map<String, Object> dataMap = new HashMap<String, Object>(); 
		String srcPath= System.getProperty("catalina.home")+File.separator +"webapps"+File.separator+"factor"+File.separator;
		 srcPath = URLDecoder.decode(srcPath+File.separator+"sysImg"+File.separator+uploadFileName,"UTF-8");
		String fileType=uploadFileName.substring(uploadFileName.lastIndexOf(".")+1);
		String uploadContentType=fileType;
		InputStream inputStream = ImageUtil.getImageInputStream(srcPath);
		MfSysCompanyMst mfSysCompanyMst = new MfSysCompanyMst();
		mfSysCompanyMst.setComNo("100000");
		mfSysCompanyMst = mfSysCompanyMstFeign.getById(mfSysCompanyMst);
		request.setAttribute("systemName", mfSysCompanyMst.getSystemName());
		dataMap.put("mfSysCompanyMst", mfSysCompanyMst);
		dataMap.put("inputStream", inputStream);
		dataMap.put("uploadContentType", uploadContentType);
		return dataMap;
	}
	
	private String getHttpUrl() {
		List<ServiceInstance> list = discoveryClient.getInstances("mftcc-platform-fileService");
		String httpurl = "http://";
		if (list != null && list.size() > 0) {
			ServiceInstance instance = list.get(0);
			String url = instance.getHost();
			int port = instance.getPort();
			httpurl = httpurl+url + ":" + port;
		}
		return httpurl;
	}
	@RequestMapping(value = "/uploadImg")
	@ResponseBody
	public Map<String,Object> uploadImg(@RequestParam("upload") MultipartFile upload,String fileType,String flag) throws Exception{
		String httpurl = getHttpUrl();
		CompanyFileFeign mfSysCompanyMstFeign1 = Feign.builder().encoder(new FeignSpringFormEncoder()).target(CompanyFileFeign.class, httpurl);
		String fileName=mfSysCompanyMstFeign1.updateImg(upload,fileType,flag, User.getRegNo(this.request),"100000");
		Map<String,Object> dataMap=new HashMap<String,Object>();
		dataMap.put("data", fileName);
		return dataMap;
	}
}
