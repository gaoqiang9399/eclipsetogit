package app.component.pact.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.connector.ClientAbortException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import app.component.model.entity.MfTemplateBizConfig;
import app.component.modelinterface.ModelInterfaceFeign;
import app.component.pact.entity.MfBusPact;
import app.component.pact.feign.MfBusPactFeign;
import app.component.pactinterface.PactInterfaceFeign;
import cn.mftcc.util.DateUtil;
import cn.mftcc.util.MathExtend;

import cn.mftcc.util.PropertiesUtil;

import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;

/**
 * Title: ThirdPartyPayForAnotherAction.java Description: 客户确定签章
 * 
 * @author:kaifa@dhcc.com.cn
 * @Mon Aug 14 15:16:07 CST 2017
 **/
@Controller
@RequestMapping("/cusConform")
public class CusConformController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	//注入MfBusCreditorBo
	@Autowired
	private ModelInterfaceFeign modelInterfaceFeign;
	@Autowired
	private PactInterfaceFeign pactInterface;
	@Autowired
	private MfBusPactFeign mfBusPactFeign;
	/**
	 * 扫码打开页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewer")
	public String viewer(Model model,String templateBizConfigId) throws Exception {
		ActionContext.initialize(request, response);
		MfTemplateBizConfig mfTemplateBizConfig=new MfTemplateBizConfig();
		mfTemplateBizConfig.setTemplateBizConfigId(templateBizConfigId);
		// 根据templateBizId 查询数据,
		mfTemplateBizConfig=modelInterfaceFeign.getMfTemplateBizConfig(mfTemplateBizConfig);
		//读取e签宝是否启用
		String flag=PropertiesUtil.getEsignProperty("OPENG_FLAG");
		model.addAttribute("flag", flag);
		model.addAttribute("mfTemplateBizConfig", mfTemplateBizConfig);
		model.addAttribute("templateBizConfigId", templateBizConfigId);
		return "component/pact/CusConform_viewer";
	}
	/**
	 * 下载合同
	 */
	@RequestMapping(value = "/download")
	@ResponseBody
	public void download(String templateBizConfigId) {
		ActionContext.initialize(request, response);
		InputStream in = null;
		try {
			// 根据templateBizId 查询数据，下载pdf
			MfTemplateBizConfig mfTemplateBizConfig=new MfTemplateBizConfig();
			mfTemplateBizConfig.setTemplateBizConfigId(templateBizConfigId);
			// 根据templateBizId 查询数据,
			mfTemplateBizConfig=modelInterfaceFeign.getMfTemplateBizConfig(mfTemplateBizConfig);
			String path =mfTemplateBizConfig.getDocFilePath();
			String fileName =mfTemplateBizConfig.getDocFileName();
			File f = new File(path+File.separator+fileName);
			if (f != null && f.exists()) {
				String extName= fileName.substring(fileName.lastIndexOf(".")+1);
				if("pdf".equalsIgnoreCase(extName)){
					response.setContentType("application/pdf");
				}else if("doc".equalsIgnoreCase(extName)){
					response.setContentType("application/msword");
				}else if("docx".equalsIgnoreCase(extName)){
					response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
				}else{
					response.setContentType("multipart/form-data");
				}
				response.setCharacterEncoding("UTF-8");
				response.setHeader("Content-Disposition","attachment; filename=" + fileName);
				response.setHeader("Content-Length", String.valueOf(f.length()));
				in = new FileInputStream(f);
				byte[] data = new byte[1024*4];
				int len = 0;
				while ((len = in.read(data, 0, data.length)) > -1) {
					 OutputStream stream = response.getOutputStream();  
					    stream.write(data);  
					    stream.flush();  
					    stream.close(); 
				}
			}
		} catch (Exception e) {
			if(e instanceof ClientAbortException){
			}else{
				//logger.error("下载合同", e);
			}
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					//logger.error("下载合同", e);
				}
			}
		}
	}
	/**
	 * 同意合同
	 * @return
	 * @throws Exception
	@RequestMapping(value = "/dealAjax")
	@ResponseBody
	public Map<String, Object> dealAjax(String templateBizConfigId,String agreeSign) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			MfTemplateBizConfig mfTemplateBizConfig=new MfTemplateBizConfig();
			mfTemplateBizConfig.setTemplateBizConfigId(templateBizConfigId);
			// 根据templateBizId 查询数据,
			mfTemplateBizConfig=modelInterfaceFeign.getMfTemplateBizConfig(mfTemplateBizConfig);
			if(!"1".equals(mfTemplateBizConfig.getAgreeSign())){
				
				if("0".equals(agreeSign)){//不同意
					//
					mfTemplateBizConfig.setAgreeSign("0");
					this.modelInterfaceFeign.updateMfTemplateBizConfig(mfTemplateBizConfig);
					dataMap.put("code", "0000");
					dataMap.put("msg", "成功拒绝");
					return dataMap;
				}else if("1".equals(agreeSign)){//同意
					//调用签章业务
					boolean res = mfBusPactFeign.doElecSignPactWzty(mfTemplateBizConfig.getTemBizNo(), mfTemplateBizConfig.getTemplateNo());
					if(res){
						dataMap.put("code", "0000");
						dataMap.put("msg", "成功签章");
					}else{
						dataMap.put("code", "1111");
						dataMap.put("msg", "操作失败");
					}
					return dataMap;
				}
			}else if("1".equals(mfTemplateBizConfig.getAgreeSign())){
				dataMap.put("code", "1111");
				dataMap.put("msg", "文档已签章");
				return dataMap;
			}
		} catch (Exception e) {
			//logger.error("用户确认合同", e);
		}
		dataMap.put("code", "1111");
		dataMap.put("msg", "操作失败");
		return dataMap;
	}*/

	/**
	 * 
	 * 方法描述： 扫描合同二维码查看合同信息
	 * @return
	 * @throws Exception
	 * String
	 * @author lwq
	 * @date 2018-2-23 下午3:58:35
	 */
	@RequestMapping(value = "/pactView")
	public String pactView(Model model,String pactId) throws Exception {
		ActionContext.initialize(request, response);
		MfBusPact mfBusPact = new MfBusPact(); 
		mfBusPact.setPactId(pactId);
		MfBusPact busPact = pactInterface.getById(mfBusPact);
		busPact.setBeginDate(DateUtil.getShowDateTime(busPact.getBeginDate()));
		busPact.setEndDate(DateUtil.getShowDateTime(busPact.getEndDate()));
		busPact.setFincAmt(MathExtend.moneyStr(busPact.getPactAmt()));
		model.addAttribute("MfBusPact", busPact);
		return "pactView";
	}

}
