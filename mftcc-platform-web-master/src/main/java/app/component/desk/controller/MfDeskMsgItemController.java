package app.component.desk.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;

import app.component.desk.entity.MfDeskMsgItem;
import app.component.desk.feign.MfDeskMsgItemFeign;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.DateUtil;

/**
 * Title: MfDeskMsgItemController.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Sun Aug 27 10:57:59 CST 2017
 **/
@Controller
@RequestMapping("/mfDeskMsgItem")
public class MfDeskMsgItemController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfDeskMsgItemFeign mfDeskMsgItemFeign;

	/**
	 * 更新预警消息排序
	 * 
	 * @return
	 */
	@RequestMapping("/updateSortAjax")
	@ResponseBody
	public Map<String, Object> updateSortAjax(String ajaxData, String[] msgTypes, String opNo) {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		if (msgTypes != null) {
			try {
				for (int i = 0; i < msgTypes.length; i++) {
					MfDeskMsgItem mfDeskMsgItem = new MfDeskMsgItem();
					mfDeskMsgItem.setOpNo(opNo);
					mfDeskMsgItem.setMsgType(msgTypes[i]);
					mfDeskMsgItem = mfDeskMsgItemFeign.getDeskMsgItemByOpNo(mfDeskMsgItem);
					mfDeskMsgItem.setSort(i + 1);
					mfDeskMsgItem.setLstModTime(DateUtil.getDateTime());
					mfDeskMsgItemFeign.updateSort(mfDeskMsgItem);
				}
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED.getMessage());
			} catch (Exception e) {
				dataMap.put("flag", "error");
				dataMap.put("msg", MessageEnum.ERROR.getMessage());
				e.printStackTrace();
			}

		} else {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR.getMessage());
		}
		return dataMap;
	}

	/**
	 * 
	 * 方法描述：获取关注的基础项
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getDeskMsgItemList")
	public String getDeskMsgItemList(Model model, String opNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		// 获取基础的查询项
		MfDeskMsgItem mfDeskMsgItem = new MfDeskMsgItem();
		mfDeskMsgItem.setOpNo(opNo);
		dataMap = mfDeskMsgItemFeign.getMsgListByOpNo(mfDeskMsgItem);
		model.addAttribute("dataMap", dataMap);
		return "/component/desk/MfDeskMsgItem_List";
	}

	/**
	 * 
	 * 方法描述：更新关注标志
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateUseFlagAjax")
	@ResponseBody
	public Map<String, Object> updateUseFlagAjax(String ajaxData, String opNo, String itemId) {
		ActionContext.initialize(request, response);
		// 获取基础的查询项
		MfDeskMsgItem mfDeskMsgItem = new MfDeskMsgItem();
		mfDeskMsgItem.setItemId(itemId);
		mfDeskMsgItem.setOpNo(opNo);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		;
		try {
			dataMap = mfDeskMsgItemFeign.updateUseFlagAjax(mfDeskMsgItem);
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED.getMessage());
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR.getMessage());
			e.printStackTrace();
		}
		return dataMap;
	}

}
