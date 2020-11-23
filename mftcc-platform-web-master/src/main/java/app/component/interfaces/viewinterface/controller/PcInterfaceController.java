package app.component.interfaces.viewinterface.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;

import com.core.struts.BaseFormBean;
import com.google.gson.Gson;

import app.component.app.entity.MfBusApply;
import app.component.doc.entity.DocManage;
import app.component.docinterface.DocInterfaceFeign;
import app.component.frontview.entity.VwBannerManage;
import app.component.frontview.entity.VwContManage;
import app.component.frontview.entity.VwFeatureManage;
import app.component.frontview.entity.VwLinkManage;
import app.component.frontview.entity.VwMenuManage;
import app.component.frontview.entity.VwSetManage;
import app.component.frontview.util.Base64Util;
import app.component.frontviewinterface.FrontviewinterfaceFeign;
import app.component.interfaces.mobileinterface.entity.WebCusLineReg;
import app.component.interfaces.mobileinterface.feign.WebCusLineRegFeign;
import app.component.interfaces.viewinterface.feign.PcInterfaceFeign;
import app.tech.upload.ImageUtil;
import app.tech.upload.UploadUtil;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.DateUtil;
import cn.mftcc.util.Md5Util;
import cn.mftcc.util.StringUtil;
import net.sf.json.JSONArray;

/**
 * 前端交易接口 Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Wed Apr 26 11:06:06 CST 2017
 **/
@Controller
@RequestMapping("/pcInterface")
public class PcInterfaceController extends BaseFormBean {
	private Logger logger = LoggerFactory.getLogger(PcInterfaceController.class);
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	private PcInterfaceFeign pcInterfaceFeign;// 对外接口bo层
	@Autowired
	private WebCusLineRegFeign webCusLineRegFeign;

	@Autowired
	private DocInterfaceFeign docInterfaceFeign;
	@Autowired
	private FrontviewinterfaceFeign frontviewinterfaceFeign;

	/**
	 * 生成base64字符串
	 * 
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getVwBase64Str")
	public String getVwBase64Str(String fileName) throws Exception {
		return Base64Util.getFrontViewImgStr(fileName);
	}

	/**
	 * 校验请求密码
	 * 
	 * @return
	 */
	@RequestMapping(value = "/checkPassWord")
	@ResponseBody
	public Map<String, String> checkPassWord(String jsonStr) {
		@SuppressWarnings("unchecked")
		Map<String, String> mp = new Gson().fromJson(jsonStr, Map.class);
		String traceNo = mp.get("traceNo");// 流水号
		if (StringUtil.isEmpty(traceNo)) {
			mp.put("APPLY__RESULT_FLAG", "0");// 0为提供密码错误
			return mp;
		}
		String pw0 = Md5Util.getMD5Str(traceNo + "mftcc").toUpperCase();// 正确密码
		String pw = mp.get("passWord");// 请求密码
		if (pw0.equals(pw)) {
			mp.put("APPLY__RESULT_FLAG", "1");
		} else {
			mp.put("APPLY__RESULT_FLAG", "0");
		}
		if (StringUtil.isNotEmpty(mp.get("pageNo")) && StringUtil.isNotEmpty(mp.get("pageSize"))) {
			int pageSize = new Integer(mp.get("pageSize")).intValue();// 每页显示条数
			int pageNo = new Integer(mp.get("pageNo")).intValue();// 当前页数
			int start = (pageNo - 1) * pageSize;// 开始条数
			mp.put("LIMIT", "LIMIT " + start + "," + pageSize);// 往paramMap里添加"LIMIT"键值对
		}
		return mp;
	}

	@RequestMapping(value = "/demo")
	@ResponseBody
	public Map<String, Object> demo(Model model, String paramJson) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, String> paramMap = checkPassWord(paramJson);
			String f = paramMap.get("APPLY__RESULT_FLAG");
			if ("0".equals(f)) {
				dataMap.put("errorcode", "1111");
				dataMap.put("errormessage", "请求密码错误!");
				return dataMap;
			}
			dataMap.put("errorcode", "0000");
			dataMap.put("errormessage", "请求成功！");
		} catch (Exception e) {
			//logger.error("PcInterfaceAction出错，执行action层失败，抛出异常，paramJson=" + paramJson, e);
			dataMap.put("errorcode", "1111");
			dataMap.put("errormessage", "系统异常，请联系管理员。");
		}
		return dataMap;
	}

	/**
	 * 验证bannerIds是否和库里要展示的banner图一致，不一致 把新查询出来的结果发送回去
	 * 
	 * @return
	 */
	@RequestMapping(value = "/validateBannerList")
	@ResponseBody
	public Map<String, Object> validateBannerList(Model model, String paramJson) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, String> paramMap = checkPassWord(paramJson);// 验证密码
			String f = paramMap.get("APPLY__RESULT_FLAG");
			if ("0".equals(f)) {
				dataMap.put("errorcode", "1111");
				dataMap.put("errormessage", "请求密码错误!");
				return dataMap;
			}
			dataMap = frontviewinterfaceFeign.validateBannerList(paramMap);
		} catch (Exception e) {
			//logger.error("PcInterfaceAction出错，执行action层失败，抛出异常，paramJson=" + paramJson, e);
			dataMap.put("errorcode", "1111");
			dataMap.put("errormessage", "系统异常，请联系管理员。");
		}
		return dataMap;
	}

	/**
	 * 获取banner图
	 */
	@RequestMapping(value = "/getBannerList")
	@ResponseBody
	public Map<String, Object> getBannerList(Model model, String paramJson) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, String> paramMap = checkPassWord(paramJson);
			String f = paramMap.get("APPLY__RESULT_FLAG");
			if ("0".equals(f)) {
				dataMap.put("errorcode", "1111");
				dataMap.put("errormessage", "请求密码错误!");
				return dataMap;
			}
			List<VwBannerManage> list = frontviewinterfaceFeign.getBannerList(paramMap);
			dataMap.put("data", list);
			dataMap.put("errorcode", "0000");
			dataMap.put("errormessage", "请求成功！");
		} catch (Exception e) {
			//logger.error("PcInterfaceAction出错，执行action层失败，抛出异常，paramJson=" + paramJson, e);
			dataMap.put("errorcode", "1111");
			dataMap.put("errormessage", "系统异常，请联系管理员。");
		}
		return dataMap;
	}

	@RequestMapping(value = "/getBannerBasicList")
	@ResponseBody
	public Map<String, Object> getBannerBasicList(Model model, String paramJson) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, String> paramMap = checkPassWord(paramJson);
			String f = paramMap.get("APPLY__RESULT_FLAG");
			if ("0".equals(f)) {
				dataMap.put("errorcode", "1111");
				dataMap.put("errormessage", "请求密码错误!");
				return dataMap;
			}
			List<VwBannerManage> list = frontviewinterfaceFeign.getBannerBasicList(paramMap);
			dataMap.put("data", list);
			dataMap.put("errorcode", "0000");
			dataMap.put("errormessage", "请求成功！");
		} catch (Exception e) {
			//logger.error("PcInterfaceAction出错，执行action层失败，抛出异常，paramJson=" + paramJson, e);
			dataMap.put("errorcode", "1111");
			dataMap.put("errormessage", "系统异常，请联系管理员。");
		}
		return dataMap;
	}

	@RequestMapping(value = "/getBannerListForId")
	@ResponseBody
	public Map<String, Object> getBannerListForId(Model model, String paramJson) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, String> paramMap = checkPassWord(paramJson);
			String f = paramMap.get("APPLY__RESULT_FLAG");
			if ("0".equals(f)) {
				dataMap.put("errorcode", "1111");
				dataMap.put("errormessage", "请求密码错误!");
				return dataMap;
			}
			List<VwBannerManage> list = frontviewinterfaceFeign.getBannerListForId(paramMap);
			dataMap.put("data", list);
			dataMap.put("errorcode", "0000");
			dataMap.put("errormessage", "请求成功！");
		} catch (Exception e) {
			//logger.error("PcInterfaceAction出错，执行action层失败，抛出异常，paramJson=" + paramJson, e);
			dataMap.put("errorcode", "1111");
			dataMap.put("errormessage", "系统异常，请联系管理员。");
		}
		return dataMap;
	}

	/**
	 * 获取网站基础设置信息
	 */
	@RequestMapping(value = "/getSetInfo")
	@ResponseBody
	public Map<String, Object> getSetInfo(Model model, String paramJson) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, String> paramMap = checkPassWord(paramJson);
			String f = paramMap.get("APPLY__RESULT_FLAG");
			if ("0".equals(f)) {
				dataMap.put("errorcode", "1111");
				dataMap.put("errormessage", "请求密码错误!");
				return dataMap;
			}
			VwSetManage set = frontviewinterfaceFeign.getVwSetBean();
			set.setQqGroupCodeImg(getVwBase64Str(set.getQqGroupCodeImg()));
			set.setWechatCodeImg(getVwBase64Str(set.getWechatCodeImg()));
			set.setLogoImg(getVwBase64Str(set.getLogoImg()));
			dataMap.put("data", set);
			dataMap.put("errorcode", "0000");
			dataMap.put("errormessage", "请求成功！");
		} catch (Exception e) {
			//logger.error("PcInterfaceAction出错，执行action层失败，抛出异常，paramJson=" + paramJson, e);
			dataMap.put("errorcode", "1111");
			dataMap.put("errormessage", "系统异常，请联系管理员。");
		}
		return dataMap;
	}

	/**
	 * 获取合作伙伴和友情链接
	 */
	@RequestMapping(value = "/getLinkGroup")
	@ResponseBody
	public Map<String, Object> getLinkGroup(Model model, String paramJson) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, String> paramMap = checkPassWord(paramJson);
			String f = paramMap.get("APPLY__RESULT_FLAG");
			if ("0".equals(f)) {
				dataMap.put("errorcode", "1111");
				dataMap.put("errormessage", "请求密码错误!");
				return dataMap;
			}
			List<VwLinkManage> list = frontviewinterfaceFeign.getLinkGroup(paramMap);
			dataMap.put("data", list);
			dataMap.put("errorcode", "0000");
			dataMap.put("errormessage", "请求成功！");
		} catch (Exception e) {
			//logger.error("PcInterfaceAction出错，执行action层失败，抛出异常，paramJson=" + paramJson, e);
			dataMap.put("errorcode", "1111");
			dataMap.put("errormessage", "系统异常，请联系管理员。");
		}
		return dataMap;
	}

	/**
	 * 获取新闻菜单和内容
	 */
	@RequestMapping(value = "/getAllMenuCont")
	@ResponseBody
	public Map<String, Object> getAllMenuCont(Model model, String paramJson) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, String> paramMap = checkPassWord(paramJson);
			String f = paramMap.get("APPLY__RESULT_FLAG");
			if ("0".equals(f)) {
				dataMap.put("errorcode", "1111");
				dataMap.put("errormessage", "请求密码错误!");
				return dataMap;
			}
			List<VwMenuManage> list = frontviewinterfaceFeign.getAllMenuCont();
			dataMap.put("data", list);
			dataMap.put("errorcode", "0000");
			dataMap.put("errormessage", "请求成功！");
		} catch (Exception e) {
			//logger.error("供应链前端交易获取新闻菜单和内容失败，抛出异常，paramJson=" + paramJson, e);
			dataMap.put("errorcode", "1111");
			dataMap.put("errormessage", "系统异常，请联系管理员。");
		}
		return dataMap;
	}

	/**
	 * 获取新闻菜单
	 */
	@RequestMapping(value = "/getAllMenu")
	@ResponseBody
	public Map<String, Object> getAllMenu(Model model, String paramJson) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, String> paramMap = checkPassWord(paramJson);
			String f = paramMap.get("APPLY__RESULT_FLAG");
			if ("0".equals(f)) {
				dataMap.put("errorcode", "1111");
				dataMap.put("errormessage", "请求密码错误!");
				return dataMap;
			}
			List<VwMenuManage> list = frontviewinterfaceFeign.getAllMenu();
			dataMap.put("data", list);
			dataMap.put("errorcode", "0000");
			dataMap.put("errormessage", "请求成功！");
		} catch (Exception e) {
			//logger.error("供应链前端交易获取新闻菜单和内容失败，抛出异常，paramJson=" + paramJson, e);
			dataMap.put("errorcode", "1111");
			dataMap.put("errormessage", "系统异常，请联系管理员。");
		}
		return dataMap;
	}

	/**
	 * 获取新闻菜单内容
	 */
	@RequestMapping(value = "/getContByMenu")
	@ResponseBody
	public Map<String, Object> getContByMenu(Model model, String paramJson) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, String> paramMap = checkPassWord(paramJson);
			String f = paramMap.get("APPLY__RESULT_FLAG");
			if ("0".equals(f)) {
				dataMap.put("errorcode", "1111");
				dataMap.put("errormessage", "请求密码错误!");
				return dataMap;
			}
			List<VwContManage> list = frontviewinterfaceFeign.getContByBlock(paramMap);
			dataMap.put("data", list);
			dataMap.put("errorcode", "0000");
			dataMap.put("errormessage", "请求成功！");
		} catch (Exception e) {
			//logger.error("供应链前端交易获取新闻菜单和内容失败，抛出异常，paramJson=" + paramJson, e);
			dataMap.put("errorcode", "1111");
			dataMap.put("errormessage", "系统异常，请联系管理员。");
		}
		return dataMap;
	}

	/**
	 * 根据内容id查询内容
	 */
	@RequestMapping(value = "/getContentById")
	@ResponseBody
	public Map<String, Object> getContentById(Model model, String paramJson) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, String> paramMap = checkPassWord(paramJson);
			String f = paramMap.get("APPLY__RESULT_FLAG");
			if ("0".equals(f)) {
				dataMap.put("errorcode", "1111");
				dataMap.put("errormessage", "请求密码错误!");
				return dataMap;
			}
			VwContManage vc = frontviewinterfaceFeign.getContentById(paramMap);
			dataMap.put("data", vc);
			dataMap.put("errorcode", "0000");
			dataMap.put("errormessage", "请求成功！");
		} catch (Exception e) {
			//logger.error("供应链前端交易获取新闻内容失败，抛出异常，paramJson=" + paramJson, e);
			dataMap.put("errorcode", "1111");
			dataMap.put("errormessage", "系统异常，请联系管理员。");
		}
		return dataMap;
	}

	/**
	 * 获取所有平台特点
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getVwFeature")
	@ResponseBody
	public Map<String, Object> getVwFeature(Model model, String paramJson) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, String> paramMap = checkPassWord(paramJson);
			String f = paramMap.get("APPLY__RESULT_FLAG");
			if ("0".equals(f)) {
				dataMap.put("errorcode", "1111");
				dataMap.put("errormessage", "请求密码错误!");
				return dataMap;
			}
			List<VwFeatureManage> list = frontviewinterfaceFeign.getAllFeature();
			dataMap.put("data", list);
			dataMap.put("errorcode", "0000");
			dataMap.put("errormessage", "请求成功！");
		} catch (Exception e) {
			//logger.error("供应链前端交易获取平台特点失败，抛出异常，paramJson=" + paramJson, e);
			dataMap.put("errorcode", "1111");
			dataMap.put("errormessage", "系统异常，请联系管理员。");
		}
		return dataMap;
	}

	/**
	 * 注册
	 */
	@RequestMapping(value = "/register")
	@ResponseBody
	public Map<String, Object> register(Model model, String paramJson) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, String> paramMap = checkPassWord(paramJson);
			String f = paramMap.get("APPLY__RESULT_FLAG");
			if ("0".equals(f)) {
				dataMap.put("errorcode", "1111");
				dataMap.put("errormessage", "请求密码错误!");
				return dataMap;
			}
			// -->需要提交的参数
			String cusTel = paramMap.get("cusTel");// 客户手机号
			String cusPassword = paramMap.get("cusPassword");// 设置密码
			String verifyNum = paramMap.get("verifyNum");// 短信验证码
			// <--
			WebCusLineReg webCusLineReg = new WebCusLineReg();
			webCusLineReg.setCusTel(cusTel);
			webCusLineReg.setCusPassword(cusPassword);
			dataMap = webCusLineRegFeign.registerCustomer(webCusLineReg, verifyNum, "3", "202", request, "1");
		} catch (Exception e) {
			//logger.error("供应链前端交易注册失败，抛出异常，paramJson=" + paramJson, e);
			dataMap.put("errorCode", "99999");
			dataMap.put("errorMsg", "系统异常，请联系管理员。");
		}
		return dataMap;
	}

	/**
	 * 登陆
	 */
	@RequestMapping(value = "/login")
	@ResponseBody
	public Map<String, Object> login(Model model, String paramJson) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, String> paramMap = checkPassWord(paramJson);
			String f = paramMap.get("APPLY__RESULT_FLAG");
			if ("0".equals(f)) {
				dataMap.put("errorcode", "99999");
				dataMap.put("errormessage", "请求密码错误!");
				return dataMap;
			}
			dataMap = webCusLineRegFeign.PClogin(paramMap);
		} catch (Exception e) {
			// //logger.error("供应链前端交易登陆失败，抛出异常，paramJson=" + paramJson, e);
			dataMap.put("errorCode", "99999");
			dataMap.put("errorMsg", "系统异常，请联系管理员。");
		}
		return dataMap;
	}

	/**
	 * 完善账户信息
	 */
	@RequestMapping(value = "/insertAccountInfo")
	@ResponseBody
	public Map<String, Object> insertAccountInfo(Model model, String paramJson) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, String> paramMap = checkPassWord(paramJson);
			String f = paramMap.get("APPLY__RESULT_FLAG");
			if ("0".equals(f)) {
				dataMap.put("errorCode", "99999");
				dataMap.put("errorMsg", "请求密码错误!");
				return dataMap;
			}
			dataMap = pcInterfaceFeign.insertAccountInfo(paramMap, request);
		} catch (Exception e) {
			//logger.error("供应链前端交易完善客户信息失败，抛出异常，paramJson=" + paramJson, e);
			dataMap.put("errorCode", "99999");
			dataMap.put("errorMsg", "系统异常，请联系管理员。");
		}
		return dataMap;
	}

	/**
	 * 查询客户资料
	 */
	@RequestMapping(value = "/getCifInfoByCusNo")
	@ResponseBody
	public Map<String, Object> getCifInfoByCusNo(Model model, String paramJson) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, String> paramMap = checkPassWord(paramJson);
			String f = paramMap.get("APPLY__RESULT_FLAG");
			if ("0".equals(f)) {
				dataMap.put("errorCode", "99999");
				dataMap.put("errorMsg", "请求密码错误!");
				return dataMap;
			}
			dataMap = pcInterfaceFeign.getCifInfoByCusNo(paramMap);
		} catch (Exception e) {
			//logger.error("供应链前端交易获取客户信息失败，抛出异常，paramJson=" + paramJson, e);
			dataMap.put("errorCode", "99999");
			dataMap.put("errorMsg", "系统异常，请联系管理员。");
		}
		return dataMap;
	}

	/**
	 * 企业账户信息维护
	 */
	@RequestMapping(value = "/updateCorpAccountInfo")
	@ResponseBody
	public Map<String, Object> updateCorpAccountInfo(Model model, String paramJson) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, String> paramMap = checkPassWord(paramJson);
			String f = paramMap.get("APPLY__RESULT_FLAG");
			if ("0".equals(f)) {
				dataMap.put("errorCode", "99999");
				dataMap.put("errorMsg", "请求密码错误!");
				return dataMap;
			}
			dataMap = pcInterfaceFeign.updateAccountInfo(paramMap);
		} catch (Exception e) {
//			logger.error("供应链前端交易维护企业客户信息失败，抛出异常，paramJson=" + paramJson, e);
			dataMap.put("errorCode", "99999");
			dataMap.put("errorMsg", "系统异常，请联系管理员。");
		}
		return dataMap;
	}

	/**
	 * 个人账户信息维护
	 */
	@RequestMapping(value = "/updatePersAccountInfo")
	@ResponseBody
	public Map<String, Object> updatePersAccountInfo(Model model, String paramJson) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, String> paramMap = checkPassWord(paramJson);
			String f = paramMap.get("APPLY__RESULT_FLAG");
			if ("0".equals(f)) {
				dataMap.put("errorCode", "99999");
				dataMap.put("errorMsg", "请求密码错误!");
				return dataMap;
			}
			dataMap = pcInterfaceFeign.updateAccountInfo_pers(paramMap);
		} catch (Exception e) {
//			logger.error("供应链前端交易维护个人客户信息失败，抛出异常，paramJson=" + paramJson, e);
			dataMap.put("errorCode", "99999");
			dataMap.put("errorMsg", "系统异常，请联系管理员。");
		}
		return dataMap;
	}

	/**
	 * 申请页面需要_产品选择onchange之后表单填充需要数据（kindNo,cusNo）
	 */
	@RequestMapping(value = "/selectedKindNo")
	@ResponseBody
	public Map<String, Object> selectedKindNo(Model model, String paramJson) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, String> paramMap = checkPassWord(paramJson);
			String f = paramMap.get("APPLY__RESULT_FLAG");
			if ("0".equals(f)) {
				dataMap.put("errorCode", "99999");
				dataMap.put("errorMsg", "请求密码错误!");
				return dataMap;
			}
			dataMap = pcInterfaceFeign.selectedKindNo(paramMap);
		} catch (Exception e) {
//			logger.error("供应链前端交易查询产品信息失败，抛出异常，paramJson=" + paramJson, e);
			dataMap.put("errorCode", "99999");
			dataMap.put("errorMsg", "系统异常，请联系管理员。");
		}
		return dataMap;
	}

	/**
	 * 获取融资申请贷款投向数据
	 */
	@RequestMapping(value = "/getPageFinUse")
	@ResponseBody
	public Map<String, Object> getPageFinUse(Model model, String paramJson) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, String> paramMap = checkPassWord(paramJson);
			String f = paramMap.get("APPLY__RESULT_FLAG");
			if ("0".equals(f)) {
				dataMap.put("errorCode", "99999");
				dataMap.put("errorMsg", "请求密码错误!");
				return dataMap;
			}
			dataMap = pcInterfaceFeign.getPageFinUse(paramMap);
		} catch (Exception e) {
//			logger.error("供应链前端交易获取贷款投向列表失败，抛出异常，paramJson=" + paramJson, e);
			dataMap.put("errorCode", "99999");
			dataMap.put("errorMsg", "系统异常，请联系管理员。");
		}
		return dataMap;
	}

	/**
	 * 保存融资申请(formapply0007bl_query)
	 */
	@RequestMapping(value = "/mfBusApplyInsert")
	@ResponseBody
	public Map<String, Object> mfBusApplyInsert(Model model, String paramJson) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, String> paramMap = checkPassWord(paramJson);
			String f = paramMap.get("APPLY__RESULT_FLAG");
			if ("0".equals(f)) {
				dataMap.put("errorCode", "99999");
				dataMap.put("errorMsg", "请求密码错误!");
				return dataMap;
			}
			dataMap = pcInterfaceFeign.mfBusApplyInsert(paramMap, request);
		} catch (Exception e) {
//			logger.error("供应链前端交易融资申请失败，抛出异常，paramJson=" + paramJson, e);
			dataMap.put("errorCode", "99999");
			dataMap.put("errorMsg", "系统异常，请联系管理员。");
		}
		return dataMap;
	}

	/**
	 * 获取客户最近的一笔融资业务(cusNo)
	 */
	@RequestMapping(value = "/getRecentBusApply")
	@ResponseBody
	public Map<String, Object> getRecentBusApply(Model model, String paramJson) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, String> paramMap = checkPassWord(paramJson);
			String f = paramMap.get("APPLY__RESULT_FLAG");
			if ("0".equals(f)) {
				dataMap.put("errorCode", "99999");
				dataMap.put("errorMsg", "请求密码错误!");
				return dataMap;
			}
			dataMap = pcInterfaceFeign.getRecentBusApply(paramMap);
		} catch (Exception e) {
//			logger.error("供应链前端交易获取客户最近的一笔融资业务失败，抛出异常，paramJson=" + paramJson, e);
			dataMap.put("errorCode", "99999");
			dataMap.put("errorMsg", "系统异常，请联系管理员。");
		}
		return dataMap;
	}

	/**
	 * 获取所有的字典项
	 */
	@RequestMapping(value = "/getDicParmList")
	@ResponseBody
	public Map<String, Object> getDicParmList(Model model, String paramJson) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, String> paramMap = checkPassWord(paramJson);
			String f = paramMap.get("APPLY__RESULT_FLAG");
			if ("0".equals(f)) {
				dataMap.put("errorCode", "99999");
				dataMap.put("errorMsg", "请求密码错误!");
				return dataMap;
			}
			dataMap = pcInterfaceFeign.getDicParmList(paramMap);
		} catch (Exception e) {
//			logger.error("供应链前端获取所有的字典项失败，抛出异常，paramJson=" + paramJson, e);
			dataMap.put("errorCode", "99999");
			dataMap.put("errorMsg", "系统异常，请联系管理员。");
		}
		return dataMap;
	}

	/**
	 * 获取用户的融资申请列表
	 */
	@RequestMapping(value = "/getMfBusApplyList")
	@ResponseBody
	public Map<String, Object> getMfBusApplyList(Model model, String paramJson) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, String> paramMap = checkPassWord(paramJson);
			String f = paramMap.get("APPLY__RESULT_FLAG");
			if ("0".equals(f)) {
				dataMap.put("errorCode", "99999");
				dataMap.put("errorMsg", "请求密码错误!");
				return dataMap;
			}
			dataMap = pcInterfaceFeign.getMfBusApplyList(paramMap);
		} catch (Exception e) {
//			logger.error("供应链前端交易获取用户融资申请列表失败，抛出异常，paramJson=" + paramJson, e);
			dataMap.put("errorCode", "99999");
			dataMap.put("errorMsg", "系统异常，请联系管理员。");
		}
		return dataMap;
	}

	/**
	 * 根据融资申请编号获取详情
	 */
	@RequestMapping(value = "/getMfBusApplyByAppId")
	@ResponseBody
	public Map<String, Object> getMfBusApplyByAppId(Model model, String paramJson) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, String> paramMap = checkPassWord(paramJson);
			String f = paramMap.get("APPLY__RESULT_FLAG");
			if ("0".equals(f)) {
				dataMap.put("errorCode", "99999");
				dataMap.put("errorMsg", "请求密码错误!");
				return dataMap;
			}
			dataMap = pcInterfaceFeign.getMfBusApplyByAppId(paramMap);
		} catch (Exception e) {
//			logger.error("供应链前端交易获取用户融资申请列表失败，抛出异常，paramJson=" + paramJson, e);
			dataMap.put("errorCode", "99999");
			dataMap.put("errorMsg", "系统异常，请联系管理员。");
		}
		return dataMap;
	}

	/**
	 * 行政区划树形数据
	 */
	@RequestMapping(value = "/getNmdAreaData")
	@ResponseBody
	public Map<String, Object> getNmdAreaData(Model model, String paramJson) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, String> paramMap = checkPassWord(paramJson);
			String f = paramMap.get("APPLY__RESULT_FLAG");
			if ("0".equals(f)) {
				dataMap.put("errorCode", "99999");
				dataMap.put("errorMsg", "请求密码错误!");
				return dataMap;
			}
			dataMap = pcInterfaceFeign.getNmdAreaData(paramMap);
		} catch (Exception e) {
//			logger.error("供应链前端交易获取行政区划树形数据失败，抛出异常，paramJson=" + paramJson, e);
			dataMap.put("errorCode", "99999");
			dataMap.put("errorMsg", "系统异常，请联系管理员。");
		}
		return dataMap;
	}

	/**
	 * 获取用户所有需要上传的要件{cusNo,scNo,appId(可空)}
	 */
	@RequestMapping(value = "/getCusNeedDocs")
	@ResponseBody
	public Map<String, Object> getCusNeedDocs(Model model, String paramJson, String cusNo) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, String> paramMap = checkPassWord(paramJson);
			String f = paramMap.get("APPLY__RESULT_FLAG");
			if ("0".equals(f)) {
				dataMap.put("errorCode", "99999");
				dataMap.put("errorMsg", "请求密码错误!");
				return dataMap;
			}
			// 进件场景先初始化客户要件 ，初始化客户要件 不在需要 直接在获取需上传要件地方读取配置 by Mahao 20171030
			/*
			 * if(BizPubParm.SCENCE_TYPE_DOC_CUS.equals(paramMap.get("scNo"))){/
			 * /进件时候刷新 配置文档 DocBizManageParam dbmp = new DocBizManageParam();
			 * dbmp.setCusNo(paramMap.get("cusNo"));// 客户号
			 * docInterfaceFeign.initiaCus(dbmp); }
			 */
			String relNo = paramMap.get("cusNo");
			// String cusNo=paramMap.get("cusNo");
			String scNo = paramMap.get("scNo");
			// String appId=paramMap.get("appId");
			if (relNo == null) {
				relNo = "";
			}
			StringBuilder relsNo = new StringBuilder(relNo);
			if (StringUtil.isNotEmpty(cusNo)) {
				relsNo.append(",").append(cusNo);
			}
			// if (StringUtil.isNotEmpty(appId)) {
			// relsNo.append(",").append(appId);
			// }
			// if (StringUtil.isNotEmpty(pactId)) {
			// relsNo.append(",").append(pactId);
			// }
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
//							logger.error("系统未找到指定路径", e);
							mp.put("docEncryptAddr", "");
						}
					}
				}
			}
			dataMap.put("data", new Gson().toJson(docJSONArray));
			dataMap.put("errorCode", "00000");
		} catch (Exception e) {
//			logger.error("供应链前端交易获取用户所有需要上传的要件失败，抛出异常，paramJson=" + paramJson, e);
			dataMap.put("errorCode", "99999");
			dataMap.put("errorMsg", "系统异常，请联系管理员。");
		}
		return dataMap;
	}

	/**
	 * 单文件上传
	 */
	@ResponseBody
	@RequestMapping(value = "/uploadDocFile")
	public Map<String, Object> uploadDocFile(String relNo, String uploadFileName, String docType,int fileNameLength, MultipartFile upload, String docBizNo, String docSplitNo, String cusNo) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		InputStream is = null;
		OutputStream os = null;
		try {
			String fileName = uploadFileName;
			if (length(fileName) > fileNameLength) {
				dataMap.put("errorCode", "99999");
				dataMap.put("errorMsg", MessageEnum.ERROR_FILE_NAME_TOOLONG.getMessage());
				return dataMap;
			}
			String prePath=UploadUtil.getFileUploadPath();
			StringBuilder path = new StringBuilder();
			UUID uuid = UUID.randomUUID();
			path.append(File.separator).append(relNo).append(File.separator).append(docType);
			path.append(File.separator).append(uuid.toString().replaceAll("-", ""));
			String realpath = path.toString();
			DocManage docManage = new DocManage();
			if (path.indexOf("../") > -1 || path.indexOf("./") > -1 || path.indexOf("..%2F") > -1
					|| path.indexOf("..%5C") > -1) {
				dataMap.put("flag", false);
				dataMap.put("msg", "文件上传路径不合法！");
				return dataMap;
			}
			File f = new File(prePath+realpath);
			if (!f.exists()) {
				f.mkdirs();
			}
			is = upload.getInputStream();
			os = new FileOutputStream(new File(prePath+realpath, fileName));
			docManage.setDocSize(this.bytes2kb(is.available()));
			byte[] buffer = new byte[1024];
			int length = 0;
			while (-1 != (length = is.read(buffer, 0, buffer.length))) {
				os.write(buffer);
			}
			/*os.close();
			is.close();*/
			docManage.setRegDate(DateUtil.getDate());
			docManage.setRegNo("");
			docManage.setOrgNo("");
			docManage.setDocName(fileName);
			docManage.setDocBizNo(docBizNo);
			docManage.setDocSplitNo(docSplitNo);
			docManage.setDocAddr(realpath + File.separator + fileName);
			docManage.setDocEncryptAddr(docManage.getDocAddr());
			docManage.setDocType(docType);
			docManage.setRelNo(relNo);
			docManage.setCusNo(cusNo);
			docInterfaceFeign.insertDocManageNew(docManage);
			dataMap.put("data", docManage);
			dataMap.put("errorCode", "00000");
			dataMap.put("errorMsg", MessageEnum.SUCCEED_UPLOAD.getMessage());
		} catch (Exception e) {
			logger.error("供应链前端交易上传文件失败，抛出异常，", e.getMessage());
			dataMap.put("errorCode", "99999");
			dataMap.put("errorMsg", "系统异常，请联系管理员。");
		} finally {
			try {
				if (os != null) {
					os.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
				logger.error(e.getMessage());
			}
			try {
				if (is != null) {
					is.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
				logger.error(e.getMessage());
			}
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

	/**
	 * @Description long转文件大小KB单位方法,保留兩位小數點
	 * @param bytes
	 * @return
	 */
	public Double bytes2kb(long bytes) {
		BigDecimal filesize = new BigDecimal(bytes);
		BigDecimal megabyte = new BigDecimal(1024);
		double returnValue = filesize.divide(megabyte, 2, BigDecimal.ROUND_UP).doubleValue();
		BigDecimal b = new BigDecimal(returnValue);
		returnValue = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		return returnValue;
	}

	/**
	 * 获取图片流
	 */
	@RequestMapping(value = "/viewImage")
	@ResponseBody
	public Map<String, Object> viewImage(Model model, String paramJson) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, String> paramMap = checkPassWord(paramJson);
			String flag = paramMap.get("APPLY__RESULT_FLAG");
			if ("0".equals(flag)) {
				throw new Exception("请求密码错误");
			}
			DocManage docManage = new DocManage();
			docManage.setDocNo(paramMap.get("docNo"));// 文档编号
			docManage.setDocBizNo(paramMap.get("docBizNo"));// 与客户号关联
			docManage = docInterfaceFeign.getDocManageById(docManage);
			String sPath = docManage.getDocEncryptAddr();
			ByteArrayInputStream inputStream = ImageUtil.getImageInputStream(sPath);
			if (null == inputStream) {
				// TODO
				dataMap.put("viewName", "imageNotExist");
				return dataMap;
			}
		} catch (Exception e) {
			// logger.error("供应链前端交易查看图片，抛出异常，paramJson=" + paramJson, e);
			throw new Exception("系统异常，请练习管理员");
		}
		return dataMap;
	}

	/**
	 * 删除单个文件
	 */
	@RequestMapping(value = "/delFile")
	@ResponseBody
	public Map<String, Object> delFile(Model model, String paramJson) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, String> paramMap = checkPassWord(paramJson);
			String f = paramMap.get("APPLY__RESULT_FLAG");
			if ("0".equals(f)) {
				dataMap.put("errorCode", "99999");
				dataMap.put("errorMsg", "请求密码错误!");
				return dataMap;
			}
			DocManage docManage = new DocManage();
			docManage.setDocNo(paramMap.get("docNo"));// 文件编号
			docManage.setDocBizNo(paramMap.get("docBizNo"));// 客户号关联字段
			docManage = docInterfaceFeign.getDocManageById(docManage);
			String sPath = docManage.getDocEncryptAddr();
			try {
				File file = new File(sPath);
				// 判断目录或文件是否存在
				if (!file.exists()) { // 不存在返回 false
					// DocManageFeign.delete(docManage);
					docInterfaceFeign.deleteDocManageByDocNoDocBizNo(docManage);
					dataMap.put("errorCode", "99999");
					dataMap.put("errorMsg", MessageEnum.NO_FILE.getMessage());
				} else {
					// 判断是否为文件
					if (file.isFile()) { // 为文件时调用删除文件方法
						deleteFile(sPath);
					} else { // 为目录时调用删除目录方法
						deleteDirectory(sPath);
					}
					docInterfaceFeign.deleteDocManageByDocNoDocBizNo(docManage);
					dataMap.put("errorCode", "00000");
					dataMap.put("errorMsg", MessageEnum.SUCCEED_DELETE.getMessage());
				}
			} catch (Exception e) {
				throw e;
			}
		} catch (Exception e) {
			// logger.error("供应链前端交易删除文件失败，抛出异常，paramJson=" + paramJson, e);
			dataMap.put("errorCode", "99999");
			dataMap.put("errorMsg", "系统异常，请联系管理员。");
		}
		return dataMap;
	}

	/**
	 * 删除单个文件
	 * 
	 * @param sPath
	 *            被删除文件的文件名
	 * @return 单个文件删除成功返回true，否则返回false
	 */
	public boolean deleteFile(String sPath) {
		boolean flag = false;
		File file = new File(sPath);
		// 路径为文件且不为空则进行删除
		if (file.isFile() && file.exists()) {
			flag = file.delete();
		}
		return flag;
	}

	/**
	 * 删除目录（文件夹）以及目录下的文件
	 * 
	 * @param sPath
	 *            被删除目录的文件路径
	 * @return 目录删除成功返回true，否则返回false
	 */
	public boolean deleteDirectory(String sPath) {
		// 如果sPath不以文件分隔符结尾，自动添加文件分隔符
		if (!sPath.endsWith(File.separator)) {
			sPath = sPath + File.separator;
		}
		File dirFile = new File(sPath);
		// 如果dir对应的文件不存在，或者不是一个目录，则退出
		if (!dirFile.exists() || !dirFile.isDirectory()) {
			return false;
		}
		boolean flag = true;
		// 删除文件夹下的所有文件(包括子目录)
		File[] files = dirFile.listFiles();
		for (int i = 0; i < files.length; i++) {
			// 删除子文件
			if (files[i].isFile()) {
				flag = deleteFile(files[i].getAbsolutePath());
				if (!flag) {
					break;
				}
			} // 删除子目录
			else {
				flag = deleteDirectory(files[i].getAbsolutePath());
				if (!flag) {
					break;
				}
			}
		}
		if (!flag) {
			return false;
		}
		// 删除当前目录
		if (dirFile.delete()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断业务申请状态（A:申请中,B:还款中,C:已完结）
	 */
	@RequestMapping(value = "/getAppSts")
	@ResponseBody
	public Map<String, Object> getAppSts(Model model, String paramJson) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, String> paramMap = checkPassWord(paramJson);
			String f = paramMap.get("APPLY__RESULT_FLAG");
			if ("0".equals(f)) {
				dataMap.put("errorCode", "99999");
				dataMap.put("errorMsg", "请求密码错误!");
				return dataMap;
			}
			MfBusApply mba = pcInterfaceFeign.getAppSts(paramMap);
			dataMap.put("data", mba);
			dataMap.put("errorCode", "00000");
		} catch (Exception e) {
			// logger.error("供应链前端交易删除文件失败，抛出异常，paramJson=" + paramJson, e);
			dataMap.put("errorCode", "99999");
			dataMap.put("errorMsg", "系统异常，请联系管理员。");
		}
		return dataMap;
	}

	/**
	 * 修改密码
	 */
	@RequestMapping(value = "/updatePasswd")
	@ResponseBody
	public Map<String, Object> updatePasswd(Model model, String paramJson) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, String> paramMap = checkPassWord(paramJson);
			String f = paramMap.get("APPLY__RESULT_FLAG");
			if ("0".equals(f)) {
				dataMap.put("errorCode", "99999");
				dataMap.put("errorMsg", "请求密码错误!");
				return dataMap;
			}
			dataMap = pcInterfaceFeign.updatePasswd(paramMap);
		} catch (Exception e) {
			// logger.error("供应链前端交易修改密码失败，抛出异常，paramJson=" + paramJson, e);
			dataMap.put("errorCode", "99999");
			dataMap.put("errorMsg", "系统异常，请联系管理员。");
		}
		return dataMap;
	}
	/**
	 * 登陆ip历史
	 */
	// 未建表
	/**
	 * 绑定的手机、邮箱、身份证，用来接收提醒或账户认证
	 */
	// 未建表
}
