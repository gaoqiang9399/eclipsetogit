package app.component.oa.communication.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonFormUtil;
import com.core.struts.taglib.JsonTableUtil;

import app.component.common.BizPubParm;
import app.component.common.EntityUtil;
import app.component.oa.communication.entity.MfOaInternalCommunication;
import app.component.oa.communication.feign.MfOaInternalCommunicationFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.util.DateUtil;
import cn.mftcc.util.StringUtil;
import cn.mftcc.util.WaterIdUtil;

/**
 * Title: MfOaInternalCommunicationAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Fri Sep 29 10:25:41 CST 2017
 **/
@Controller
@RequestMapping("/mfOaInternalCommunication")
public class MfOaInternalCommunicationController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfOaInternalCommunicationFeign mfOaInternalCommunicationFeign;

	/**
	 * 跳转收件箱列表
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getAcceptInfoListPage")
	public String getAcceptInfoListPage(Model model) throws Exception {
		ActionContext.initialize(request, response);
		model.addAttribute("query", "");
		return "/component/oa/communication/MfOaInternalCommunication_AcceptList";
	}

	/**
	 * 
	 * 方法描述：跳转发件箱列表
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2017-9-29 下午2:41:27
	 */
	@RequestMapping("/getSendInfoListPage")
	public String getSendInfoListPage(Model model) throws Exception {
		ActionContext.initialize(request, response);
		model.addAttribute("query", "");
		return "/component/oa/communication/MfOaInternalCommunication_SendList";
	}

	/***
	 * 列表数据查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/findByPageAjax")
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType, String ajaxData,String messageType) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			MfOaInternalCommunication mfOaInternalCommunication = new MfOaInternalCommunication();
			mfOaInternalCommunication.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfOaInternalCommunication.setCriteriaList(mfOaInternalCommunication, ajaxData);// 我的筛选
			mfOaInternalCommunication.setCustomSorts(ajaxData);// 自定义排序
			// this.getRoleConditions(mfOaInternalCommunication,"1000000001");//记录级权限控制方法
			mfOaInternalCommunication.setMessageType(messageType);
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setPageSize(pageSize);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage.setParams(this.setIpageParams("mfOaInternalCommunication", mfOaInternalCommunication));
			ipage = mfOaInternalCommunicationFeign.findByPage(ipage);
			Gson gson = new Gson();
			String json = gson.toJson(ipage.getResult());
			List<MfOaInternalCommunication> list=gson.fromJson(json,new TypeToken<List<MfOaInternalCommunication>>(){
			}.getType());
			for (int i = 0; i <list.size() ; i++) {
				if("2".equals(list.get(i).getMessageKind())){
                       list.get(i).setMessageContent("点击查看详情...");
                       list.get(i).setMessageKind("图文消息");
				}else{
					list.get(i).setMessageKind("文本消息");
				}
			}
             ipage.setResult(list);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * AJAX新增
	 * input
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/insertAjax")
	public Map<String, Object> insertAjax(String ajaxData,String recoveryMessageId,String messageId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
  			FormData formcommunicationaddinput = new FormService().getFormData("communicationaddinput");
			getFormValue(formcommunicationaddinput, getMapByJson(ajaxData));
			if (this.validateFormData(formcommunicationaddinput)) {
				MfOaInternalCommunication mfOaInternalCommunication = new MfOaInternalCommunication();
				setObjValue(formcommunicationaddinput, mfOaInternalCommunication);
                mfOaInternalCommunication.setMessageContent(StringUtil.urlDecoder(mfOaInternalCommunication.getMessageContent()));
			//	mfOaInternalCommunication.setMessageShow(StringUtil.urlDecoder(mfOaInternalCommunication.getMessageShow()));
				if (StringUtil.isNotEmpty(mfOaInternalCommunication.getMessageId())) {
					mfOaInternalCommunication.setRecoveryMessageId(recoveryMessageId);
					mfOaInternalCommunicationFeign.recoveryInsert(mfOaInternalCommunication);
				} else {
					mfOaInternalCommunication.setMessageId(messageId);
					mfOaInternalCommunicationFeign.insert(mfOaInternalCommunication);
				}
				dataMap.put("flag", "success");
				dataMap.put("msg", "发送成功");
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", "发送失败");
			throw e;
		}
		return dataMap;
	}

	/**
	 * ajax 异步 单个字段或多个字段更新
	 * 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/updateAjaxByOne")
	public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formcommunicationaddinput = new FormService().getFormData("communicationadd");
		getFormValue(formcommunicationaddinput, getMapByJson(ajaxData));
		MfOaInternalCommunication mfOaInternalCommunicationJsp = new MfOaInternalCommunication();
		setObjValue(formcommunicationaddinput, mfOaInternalCommunicationJsp);
		MfOaInternalCommunication mfOaInternalCommunication = mfOaInternalCommunicationFeign.getById(mfOaInternalCommunicationJsp);
		if (mfOaInternalCommunication != null) {
			try {
				mfOaInternalCommunication = (MfOaInternalCommunication) EntityUtil.reflectionSetVal(
						mfOaInternalCommunication, mfOaInternalCommunicationJsp, getMapByJson(ajaxData));
				mfOaInternalCommunicationFeign.update(mfOaInternalCommunication);
				dataMap.put("flag", "success");
				dataMap.put("msg", "保存成功");
			} catch (Exception e) {
				e.printStackTrace();
				dataMap.put("flag", "error");
				dataMap.put("msg", "新增失败");
				throw e;
			}
		} else {
			dataMap.put("flag", "error");
			dataMap.put("msg", "编号不存在,保存失败");
		}
		return dataMap;
	}

	/**
	 * AJAX更新保存
	 * 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/updateAjax")
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			MfOaInternalCommunication mfOaInternalCommunication = new MfOaInternalCommunication();
			FormData formcommunicationaddinput = new FormService().getFormData("communicationadd");
			getFormValue(formcommunicationaddinput, getMapByJson(ajaxData));
			if (this.validateFormData(formcommunicationaddinput)) {
				mfOaInternalCommunication = new MfOaInternalCommunication();
				setObjValue(formcommunicationaddinput, mfOaInternalCommunication);
				mfOaInternalCommunicationFeign.update(mfOaInternalCommunication);
				dataMap.put("flag", "success");
				dataMap.put("msg", "更新成功");
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", "更新失败");
			throw e;
		}
		return dataMap;
	}

	/**
	 * AJAX获取查看
	 * 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/getByIdAjax")
	public Map<String, Object> getByIdAjax(String messageId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formcommunicationaddinput = new FormService().getFormData("communicationadd");
		MfOaInternalCommunication mfOaInternalCommunication = new MfOaInternalCommunication();
		mfOaInternalCommunication.setMessageId(messageId);
		mfOaInternalCommunication = mfOaInternalCommunicationFeign.getById(mfOaInternalCommunication);
		getObjValue(formcommunicationaddinput, mfOaInternalCommunication, formData);
		if (mfOaInternalCommunication != null) {
			dataMap.put("flag", "success");
		} else {
			dataMap.put("flag", "error");
		}
		dataMap.put("formData", formData);
		return dataMap;
	}

	/**
	 * Ajax异步删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/deleteAjax")
	public Map<String, Object> deleteAjax(String messageId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfOaInternalCommunication mfOaInternalCommunication = new MfOaInternalCommunication();
		mfOaInternalCommunication.setMessageId(messageId);
		try {
			mfOaInternalCommunicationFeign.delete(mfOaInternalCommunication);
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
	@RequestMapping("/input")
	public String input(String messageType,Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormData formcommunicationaddinput = new FormService().getFormData("communicationaddinput");
		String messageId = WaterIdUtil.getWaterId("mess");
		MfOaInternalCommunication mfOaInternalCommunication = new MfOaInternalCommunication();
		mfOaInternalCommunication.setMessageType(messageType);
		getObjValue(formcommunicationaddinput, mfOaInternalCommunication);
		model.addAttribute("formcommunicationaddinput", formcommunicationaddinput);
		model.addAttribute("mfOaInternalCommunication", mfOaInternalCommunication);
		model.addAttribute("messageId", messageId);
		model.addAttribute("query", "");
		return "/component/oa/communication/MfOaInternalCommunication_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getById")
	public String getById(String messageId,String detailFlag,Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormData formcommunicationdetail = new FormService().getFormData("communicationdetailshow");
		getFormValue(formcommunicationdetail);
		MfOaInternalCommunication mfOaInternalCommunication = new MfOaInternalCommunication();
		mfOaInternalCommunication.setMessageId(messageId);
		mfOaInternalCommunication = mfOaInternalCommunicationFeign.getById(mfOaInternalCommunication);
      //mfOaInternalCommunication.setMessageContent(StringUtil.urlDecoder(mfOaInternalCommunication.getMessageContent()));
	  //mfOaInternalCommunication.setMessageShow(StringUtil.urlDecoder(mfOaInternalCommunication.getMessageShow()));
		// 消息未读且不是只支持查看
		String readSts = "0";
		if (BizPubParm.YES_NO_N.equals(mfOaInternalCommunication.getReadSts()) && !"onlyDetail".equals(detailFlag)) {
			// 标记为已读
			mfOaInternalCommunication.setReadSts(BizPubParm.YES_NO_Y);
			mfOaInternalCommunication.setReadDate(DateUtil.getDate());
			mfOaInternalCommunication.setReadTime(DateUtil.getDateTime());
			mfOaInternalCommunicationFeign.update(mfOaInternalCommunication);
			readSts ="1";
		}
		getObjValue(formcommunicationdetail, mfOaInternalCommunication);
		model.addAttribute("formcommunicationdetail", formcommunicationdetail);
		model.addAttribute("messageId", messageId);
		model.addAttribute("readSts", readSts);
		model.addAttribute("mfOaInternalCommunication", mfOaInternalCommunication);
		model.addAttribute("query", "");
		return "/component/oa/communication/MfOaInternalCommunication_Detail";
	}

	/**
	 * 新增校验
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/validateInsert")
	public void validateInsert() throws Exception {
		ActionContext.initialize(request, response);
		FormData formcommunicationaddinput = new FormService().getFormData("communicationadd");
		getFormValue(formcommunicationaddinput);
		boolean validateFlag = this.validateFormData(formcommunicationaddinput);
	}

	/**
	 * 修改校验
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/validateUpdate")
	public void validateUpdate() throws Exception {
		ActionContext.initialize(request, response);
		FormData formcommunicationaddinput = new FormService().getFormData("communicationadd");
		getFormValue(formcommunicationaddinput);
		boolean validateFlag = this.validateFormData(formcommunicationaddinput);
	}

	/**
	 * 
	 * 方法描述： 获得消息回复表单html
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2017-9-29 下午3:42:28
	 */
	@ResponseBody
	@RequestMapping("/getRecoveryFromHtmlAjax")
	public Map<String, Object> getRecoveryFromHtmlAjax(String messageId,String messageType) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			MfOaInternalCommunication mfOaInternalCommunication = new MfOaInternalCommunication();
			mfOaInternalCommunication.setMessageId(messageId);
			mfOaInternalCommunication = mfOaInternalCommunicationFeign.getById(mfOaInternalCommunication);
			MfOaInternalCommunication internalCommunication = new MfOaInternalCommunication();
			internalCommunication.setMessageId(messageId);
			internalCommunication.setMessageAcceptOpNo(mfOaInternalCommunication.getMessageSendOpNo());
			internalCommunication.setMessageAcceptOpName(mfOaInternalCommunication.getMessageSendOpName());
			internalCommunication.setMessageType(messageType);
			internalCommunication.setMessageKind(mfOaInternalCommunication.getMessageKind());
			FormData formcommunicationaddinput = new FormService().getFormData("communicationacceptinput");
			getFormValue(formcommunicationaddinput);
			getObjValue(formcommunicationaddinput, internalCommunication);
			JsonFormUtil jsonFormUtil = new JsonFormUtil();
			String htmlStr = jsonFormUtil.getJsonStr(formcommunicationaddinput, "bootstarpTag", "");
			dataMap.put("recoveryMessageId", WaterIdUtil.getWaterId("mess"));
			dataMap.put("htmlStr", htmlStr);
			dataMap.put("flag", "success");
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", "失败");
			throw e;
		}
		return dataMap;
	}

}
