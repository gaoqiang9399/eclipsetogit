package app.component.interfaces.appinterface.controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.struts.BaseFormBean;
import com.google.gson.Gson;

import app.component.doc.entity.DocManage;
import app.component.docinterface.DocInterfaceFeign;
import app.component.frontview.util.Base64Util;
import app.component.interfaces.appinterface.feign.DingFileUploadFeign;
import app.tech.upload.ImageUtil;
import app.tech.upload.UploadUtil;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.DateUtil;
import cn.mftcc.util.StringUtil;
import net.sf.json.JSONArray;

@Controller
@RequestMapping("/dingUploadFile")
public class DingUploadFileController extends BaseFormBean {

	private Gson gson = new Gson();

	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private DocInterfaceFeign docInterfaceFeign;
	@Autowired
	private DingFileUploadFeign dingFileUploadFeign;

	@RequestMapping(value = "/toUploadPage")
	public String toUploadPage(Model model) {
		return "/component/interfaces/appinterface/DingFileUpload";
	}

	/**
	 * 获取用户所有需要上传的要件{cusNo,scNo,appId(可空)}
	 */
	@RequestMapping(value = "/getCusNeedDocs")
	@ResponseBody
	public Map<String, Object> getCusNeedDocs(Model model, String paramJson, String cusNo) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, String> paramMap = gson.fromJson(paramJson, Map.class);
			// 进件场景先初始化客户要件 ,初始化客户要件 不在需要 直接在获取需上传要件地方读取配置 by Mahao 20171030
			/*
			 * if (BizPubParm.SCENCE_TYPE_DOC_CUS.equals(paramMap.get("scNo")))
			 * {// 进件时候刷新 // 配置文档 DocBizManageParam dbmp = new
			 * DocBizManageParam(); dbmp.setCusNo(paramMap.get("cusNo"));// 客户号
			 * docInterfaceFeign.initiaCus(dbmp); }
			 */
			String relNo = paramMap.get("cusNo");
			String scNo = paramMap.get("scNo");
			if (relNo == null) {
				relNo = "";
			}
			StringBuilder relsNo = new StringBuilder(relNo);
			if (StringUtil.isNotEmpty(cusNo)) {
				relsNo.append(",").append(cusNo);
			}
			// 直接读取配置文件信息
			JSONArray docJSONArray = docInterfaceFeign.getCusDocDisPlay(scNo, paramMap.get("cusNo"), relsNo.toString(),
					paramMap.get("cusType"));
			if (docJSONArray == null) {
				docJSONArray = new JSONArray();
			}
			for (int i = 0; i < docJSONArray.size(); i++) {
				@SuppressWarnings("unchecked")
				Map<String, Object> docTypeMap = (Map<String, Object>) docJSONArray.get(i);
				@SuppressWarnings("unchecked")
				List<Map<String, String>> list = (List<Map<String, String>>) docTypeMap.get("imgs");
				for (Map<String, String> mp : list) {
					String imgAds = mp.get("src");
					if (StringUtil.isNotEmpty(imgAds)) {
						try {// 系统找不到指定路径的处理
							String base64str = Base64Util.encodeBase64ByPath(imgAds);
							mp.put("docEncryptAddr", base64str);
						} catch (Exception e) {
							// logger.error("系统未找到指定路径", e);
							mp.put("docEncryptAddr", "");
						}
					}
				}
			}
			dataMap.put("data", gson.toJson(docJSONArray));
			dataMap.put("errorCode", "00000");
		} catch (Exception e) {
			// logger.error("供应链前端交易获取用户所有需要上传的要件失败，抛出异常，paramJson=" +
			// paramJson, e);
			dataMap.put("errorCode", "99999");
			dataMap.put("errorMsg", "系统异常，请联系管理员。");
		}
		return dataMap;
	}

	@RequestMapping(value = "/uploadFileAjax")
	@ResponseBody
	public Map<String, Object> uploadFileAjax(String uploadFileName, Integer fileNameLength, String relNo,
			String docType, String docBizNo, String docSplitNo, String cusNo, String photourl, String uploadContentType,
			String address, String longitude, String latitude) {
		Map<String, Object> dataMap = new HashMap<>();
		try {
			// 验证文件名长度
			String fileName = uploadFileName;
			if (length(fileName) > fileNameLength) {
				dataMap.put("errorCode", "99999");
				dataMap.put("errorMsg", MessageEnum.ERROR_FILE_NAME_TOOLONG.getMessage());
				return dataMap;
			}

			// 验证图片格式是否支持
			if (fileName.endsWith(".jpg") || fileName.endsWith(".png") || fileName.endsWith(".gif")
					|| fileName.endsWith(".jpeg")) {

			} else {
				dataMap.put("errorCode", "99999");
				dataMap.put("errorMsg", "图片格式不支持,请重试");
				return dataMap;
			}

			StringBuilder path = new StringBuilder();
			path.append(UploadUtil.getFileUploadPath());
			UUID uuid = UUID.randomUUID();
			path.append(File.separator).append(relNo).append(File.separator).append(docType);
			path.append(File.separator).append(uuid.toString().replaceAll("-", ""));
			String realpath = path.toString();
			if (path.indexOf("../") > -1 || path.indexOf("./") > -1 || path.indexOf("..%2F") > -1
					|| path.indexOf("..%5C") > -1) {
				dataMap.put("errorCode", "99999");
				dataMap.put("errorMsg", "文件上传路径不合法！");
				return dataMap;
			}
			DocManage docManage = new DocManage();
			docManage.setRegDate(DateUtil.getDate());
			String brno = (String) request.getSession().getAttribute("orgNo");
			String regNo = (String) request.getSession().getAttribute("regNo");
			docManage.setRegNo(regNo);
			docManage.setOrgNo(brno);
			docManage.setDocName(fileName);
			docManage.setDocBizNo(docBizNo);
			docManage.setDocSplitNo(docSplitNo);
			docManage.setDocAddr(realpath + File.separator + fileName);
			docManage.setDocEncryptAddr(docManage.getDocAddr());
			docManage.setDocType(docType);
			docManage.setRelNo(relNo);
			docManage.setCusNo(cusNo);
			Map<String, String> paramMap = new HashMap<>();
			paramMap.put("realpath", realpath);
			paramMap.put("photourl", photourl);
			paramMap.put("uploadContentType", uploadContentType);
			paramMap.put("address", address);
			paramMap.put("longitude", longitude);
			paramMap.put("latitude", latitude);

			dingFileUploadFeign.FileUpload(paramMap, docManage);

			dataMap.put("data", docManage);
			dataMap.put("errorCode", "00000");
			dataMap.put("errorMsg", MessageEnum.SUCCEED_UPLOAD.getMessage());

		} catch (Exception e) {
			// logger.error("微信文件上传失败，客户号：" + cusNo, e);
			e.printStackTrace();
		}
		return dataMap;
	}

	// 查看图片
	@RequestMapping(value = "/viewImage")
	public String viewImage(Model model, String docNo, String docBizNo) throws Exception {
		DocManage docManage = new DocManage();
		docManage.setDocNo(docNo);
		docManage.setDocBizNo(docBizNo);
		docManage = docInterfaceFeign.docManageBoGetById(docManage);
		String sPath = docManage.getDocEncryptAddr();
		ByteArrayInputStream inputStream = ImageUtil.getImageInputStream(sPath);
		model.addAttribute("docManage", docManage);
		if (null == inputStream) {
            return "imageNotExist";
        }
		return "";
	}

	// 查看缩略图
	@RequestMapping(value = "/viewCompressImage")
	public String viewCompressImage(Model model, String docNo, String docBizNo) throws Exception {
		DocManage docManage = new DocManage();
		docManage.setDocNo(docNo);
		docManage.setDocBizNo(docBizNo);
		docManage = docInterfaceFeign.docManageBoGetById(docManage);
		String sPath = docManage.getCompressPath();
		ByteArrayInputStream inputStream = ImageUtil.getImageInputStream(sPath);
		model.addAttribute("docManage", docManage);
		if (null == inputStream) {
            return "imageNotExist";
        }
		return "";
	}

	/**
	 * 得到一个字符串的长度,显示的长度,一个汉字或日韩文长度为2,英文字符长度为1
	 * 
	 * @param String
	 *            s 需要得到长度的字符串
	 * @return int 得到的字符串长度
	 */
	public static int length(String s) {
		if (s == null) {
            return 0;
        }
		char[] c = s.toCharArray();
		int len = 0;
		for (int i = 0; i < c.length; i++) {
			len++;
			if (!isLetter(c[i])) {
				len++;
			}
		}
		return len;
	}

	public static boolean isLetter(char c) {
		int k = 0x80;
		return c / k == 0 ? true : false;
	}

}
