package app.component.interfaces.mobileinterface.controller;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.struts.BaseFormBean;

import app.component.app.entity.MfBusApply;
import app.component.appinterface.AppInterfaceFeign;
import app.component.common.BizPubParm;
import app.component.doc.entity.DocManage;
import app.component.interfaces.mobileinterface.entity.AppDocManageDTO;
import app.component.interfaces.mobileinterface.entity.MfAppCusIntegrity;
import app.component.interfaces.mobileinterface.feign.AppDocUploadFeign;
import app.component.interfaces.mobileinterface.feign.MfAppCusIntegrityFeign;
import app.tech.upload.UploadUtil;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.DateUtil;
import cn.mftcc.util.StringUtil;

/**
 * 微信文件上传 Title: DocBizManageAction.java Description:
 * 
 * @author:@dhcc.com.cn
 * 
 **/
@Controller
@RequestMapping("/appDocUpload")
public class AppDocUploadController extends BaseFormBean {
	// 异步参数

	/** 暂时用不到，后期根据上传业务判断是否需要前台传入 上传参数，start By mahao 20170509 **/
	/** 暂时用不到，后期根据上传业务判断是否需要前台传入 上传参数，end By mahao **/

	/** 获取要件信息参数 **/

	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private AppDocUploadFeign appDocUploadFeign;
	@Autowired
	private MfAppCusIntegrityFeign mfAppCusIntegrityFeign;
	@Autowired
	private AppInterfaceFeign appInterfaceFeign;

	/**
	 * 上传要件type;//判断1、身份证信息 2、授信申请附件 3、融资申请附件
	 * 
	 * @return
	 */
	@RequestMapping(value = "/uploadFile")
	public Map<String, Object> uploadFile(String cusNo, String type, String uploadFileName, File upload) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		AppDocManageDTO appDocManageDTO = new AppDocManageDTO();
		try {
			// appDocManageDTO.setRelNo(relNo);
			// appDocManageDTO.setDocType(docType);
			// appDocManageDTO.setDocBizNo(docBizNo);
			// appDocManageDTO.setDocSplitNo(docSplitNo);
			appDocManageDTO.setCusNo(cusNo);
			appDocManageDTO.setType(type);
			dataMap = appDocUploadFeign.upload(uploadFileName, upload, appDocManageDTO, request);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("errorCode", "11111");
			dataMap.put("errorMsg", e.getMessage());
			// logger.error("移动端上传要件出错", e);
		}
		return dataMap;
	}

	/**
	 * 上传要件
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/uploadDocFileAjax")
	@ResponseBody
	public Map<String, Object> uploadDocFileAjax(String uploadFileName, Integer fileNameLength, String relNo,
			String docType, String docBizNo, String docSplitNo, String cusNo, String idCardName,
			String uploadContentType, File upload, String regNo) throws Exception {

		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {

			String fileName = uploadFileName;
			if (StringUtil.isNotEmpty(idCardName)) {
				fileName = idCardName;
			}
			// 验证图片格式是否支持
			if (fileName.endsWith(".jpg") || fileName.endsWith(".png") || fileName.endsWith(".gif")
					|| fileName.endsWith(".jpeg")) {
			} else {
				dataMap.put("errorCode", "99999");
				dataMap.put("errorMsg", "图片格式不支持,请重试");
				return dataMap;
			}
			// 验证文件名长度
			if (length(fileName) > fileNameLength) {
				dataMap.put("errorCode", "99999");
				dataMap.put("errorMsg", MessageEnum.ERROR_FILE_NAME_TOOLONG.getMessage());
				return dataMap;
			}
			// 判断 文件信息是否存在 如果存在 就更新 如果不存在就插入
			DocManage dm = appDocUploadFeign.getDocManageByName(fileName, docBizNo, docSplitNo);
			if (null != dm) {
				// 判断合同授信通过后不支持修改身份证
				MfBusApply mfBusApply = new MfBusApply();
				mfBusApply.setCusNo(docBizNo);
				List<MfBusApply> list = appInterfaceFeign.getApplyList(mfBusApply);
				for (MfBusApply mfBusApply2 : list) {
					// 审批通过不支持修改
					if (BizPubParm.APP_STS_PASS.equals(mfBusApply2.getAppSts())) {
						dataMap.put("errorCode", "99999");
						dataMap.put("errorMsg", "您有授信已审批通过，不支持修改！");
						return dataMap;
					}
				}
				dataMap = appDocUploadFeign.updateDocManage(upload, dm, uploadContentType);
			} else {
				StringBuilder path = new StringBuilder();
				StringBuilder pathNew = new StringBuilder();
				path.append(UploadUtil.getFileUploadPath());
				UUID uuid = UUID.randomUUID();
				path.append(File.separator).append(relNo).append(File.separator).append(docType);
				path.append(File.separator).append(uuid.toString().replaceAll("-", ""));
				pathNew.append(File.separator).append(relNo).append(File.separator).append(docType);
				pathNew.append(File.separator).append(uuid.toString().replaceAll("-", ""));
				String realpath = path.toString();
				String realpathNew = pathNew.toString();
				if (path.indexOf("../") > -1 || path.indexOf("./") > -1 || path.indexOf("..%2F") > -1
						|| path.indexOf("..%5C") > -1) {
					dataMap.put("errorCode", "99999");
					dataMap.put("errorMsg", "文件上传路径不合法！");
					return dataMap;
				}
				DocManage docManage = new DocManage();

				docManage.setRegDate(DateUtil.getDate());
				docManage.setRegNo(regNo);
				docManage.setDocName(fileName);
				docManage.setDocBizNo(docBizNo);
				docManage.setDocSplitNo(docSplitNo);
				docManage.setDocAddr(realpathNew + File.separator + fileName);
				docManage.setDocEncryptAddr(docManage.getDocAddr());
				docManage.setDocType(docType);
				docManage.setRelNo(relNo);
				docManage.setCusNo(cusNo);
				dataMap = appDocUploadFeign.insertDocManage(realpath, upload, docManage, uploadContentType);
				if (fileName.contains("teleInfo")) {
					// 更新客户信息完整度表
					MfAppCusIntegrity mfAppCusIntegrity = new MfAppCusIntegrity();
					mfAppCusIntegrity.setCusNo(cusNo);
					mfAppCusIntegrity.setPhoneInfoFlag("1");
					mfAppCusIntegrityFeign.update(mfAppCusIntegrity);
				}
			}

		} catch (Exception e) {
			// logger.error("上传文件失败异常", e);
			dataMap.put("errorCode", "11111");
			dataMap.put("errorMsg", "上传文件失败异常");
		}
		return dataMap;
	}

	/**
	 * 上传要件 (网信)
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/uploadDocFileForWxAjax")
	@ResponseBody
	public Map<String, Object> uploadDocFileForWxAjax(String uploadFileName, String relNo, String docType,
			String docBizNo, String docSplitNo, String cusNo, String idCardName, String uploadContentType, File upload)
			throws Exception {

		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			String fileName = uploadFileName;
			if (StringUtil.isNotEmpty(idCardName)) {
				fileName = idCardName;
			}
			dataMap = appDocUploadFeign.insertOrUpdateDocManage(cusNo, fileName, upload, relNo, docBizNo, docSplitNo,
					docType, uploadContentType);
		} catch (Exception e) {
			// logger.error("上传文件失败异常", e);
			dataMap.put("errorCode", "11111");
			dataMap.put("errorMsg", "上传文件失败异常");
		}
		return dataMap;
	}

	/**
	 * 获取展示要件
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getShowDocFileAjax")
	@ResponseBody
	public Map<String, Object> getShowDocFileAjax(String docNames, String cusNo, String docType, String scNo)
			throws Exception {

		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			dataMap = appDocUploadFeign.getDocFileByNames(docNames, cusNo, docType, scNo);
		} catch (Exception e) {
			// logger.error("获取展示要件异常", e);
			dataMap.put("errorCode", "11111");
			dataMap.put("errorMsg", "获取展示要件异常");
		}
		return dataMap;
	}

	/**
	 * 获取展示要件的缩略图
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getShowThumbnailAjax")
	@ResponseBody
	public Map<String, Object> getShowThumbnailAjax(String docNames, String cusNo, String docType, String scNo)
			throws Exception {

		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			// 封装关联业务号
			dataMap = appDocUploadFeign.getThumbnailByNames(docNames, cusNo, docType, scNo);

		} catch (Exception e) {
			// logger.error("获取展示要件异常", e);
			dataMap.put("errorCode", "11111");
			dataMap.put("errorMsg", "获取展示要件异常");
		}
		return dataMap;
	}

	/**
	 * 检查要件是否上传
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/checkIsUploadAjax")
	@ResponseBody
	public Map<String, Object> checkIsUploadAjax(String docNames, String cusNo) throws Exception {

		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			dataMap = appDocUploadFeign.checkIsUpload(docNames, cusNo);
		} catch (Exception e) {
			// logger.error("检查要件是否上传异常", e);
			dataMap.put("errorCode", "11111");
			dataMap.put("errorMsg", "检查要件是否上传异常");
		}
		return dataMap;
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
