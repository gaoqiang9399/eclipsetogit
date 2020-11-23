package app.component.pms.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;

import app.component.pms.entity.PmsDataRang;
import app.component.pms.entity.PmsDataRangRole;
import app.component.pms.entity.PmsEntrance;
import app.component.pms.entity.PmsEntranceRole;
import app.component.pms.entity.PmsViewpoint;
import app.component.pms.entity.PmsViewpointRole;
import app.component.pms.feign.PmsDataRangFeign;
import app.component.pms.feign.PmsDataRangRoleFeign;
import app.component.pms.feign.PmsEntranceFeign;
import app.component.pms.feign.PmsEntranceRoleFeign;
import app.component.pms.feign.PmsViewpointFeign;
import app.component.pms.feign.PmsViewpointRoleFeign;
import app.component.sys.entity.SysRole;
import app.component.sysInterface.SysInterfaceFeign;
import app.tech.oscache.CodeUtils;
import cn.mftcc.common.MessageEnum;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/pmsConfigure")
public class PmsConfigureController extends BaseFormBean {
	// 数据权限
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private PmsDataRangFeign pmsDataRangFeign;
	@Autowired
	private PmsDataRangRoleFeign pmsDataRangRoleFeign;
	@Autowired
	private SysInterfaceFeign sysInterfaceFeign;
	// 入口权限
	@Autowired
	private PmsEntranceFeign pmsEntranceFeign;
	@Autowired
	private PmsEntranceRoleFeign pmsEntranceRoleFeign;
	// 视角权限
	@Autowired
	private PmsViewpointFeign pmsViewpointFeign;
	@Autowired
	private PmsViewpointRoleFeign pmsViewpointRoleFeign;

	@RequestMapping(value = "/configureAjax")
	@ResponseBody
	public Map<String, Object> configureAjax(String roleNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			JSONObject json = new JSONObject();
			json.put("roleNo", roleNo);
			json.put("dataArray", dataConvertJson(roleNo));
			json.put("entrArray", entrConvertJson(roleNo));
			json.put("viewArray", viewConvertJson(roleNo));
			dataMap.put("json", json);
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED_SAVE.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_SAVE.getMessage());
			throw e;
		}
		return dataMap;
	}

	@RequestMapping(value = "/configure")
	public String configure(Model model) throws Exception {
		// json = new JSONObject();
		// json.put("roleNo", roleNo);
		// json.put("dataArray", dataConvertJson());
		// json.put("entrArray", entrConvertJson());
		// json.put("viewArray", viewConvertJson());

		return "/component/pms/pms_Configure";
	}

	/**
	 * 
	 * 方法描述：打开权限配置页面
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2017-2-22 下午3:35:15
	 */
	@RequestMapping(value = "/configureNew")
	public String configureNew(Model model, String roleNo,String opNoType) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formsys2001 = formService.getFormData("sysRole0001");
		SysRole sysRole = new SysRole();
		sysRole = sysInterfaceFeign.getSysRole(roleNo);
		getObjValue(formsys2001, sysRole);
		model.addAttribute("formsys2001", formsys2001);
		model.addAttribute("sysRole", sysRole);
		model.addAttribute("opNoType", opNoType);
		model.addAttribute("query", "");
		return "/component/pms/pms_ConfigureNew";
	}

	/**
	 * 
	 * @Description: 入口转JSON
	 * @param @return
	 * @param @throws
	 *            Exception
	 * @return JSONArray
	 * @throws @author
	 *             wangcong
	 * @date 2015年12月30日下午2:34:44
	 */
	private JSONArray entrConvertJson(String roleNo) throws Exception {
		List<PmsEntranceRole> perList = pmsEntranceRoleFeign.findByRoleNo(roleNo);
		List<PmsEntrance> peAll = pmsEntranceFeign.getAllList();
		List<PmsEntrance> peEntr = pmsEntranceFeign.getEntranceList();
		JSONArray array = new JSONArray();
		for (int i = 0; i < peEntr.size(); i++) {
			JSONObject obj = new JSONObject();
			obj.put("name", peEntr.get(i).getEntranceName());
			obj.put("id", peEntr.get(i).getEntranceNo());
			obj.put("pId", "0");
			obj.put("open", true);
			array.add(obj);
			for (int j = 0; j < peAll.size(); j++) {
				if (peEntr.get(i).getEntranceNo().equals(peAll.get(j).getEntranceNo())) {
					JSONObject subobj = new JSONObject();
					subobj.put("name", peAll.get(j).getEntranceUrlDesc());
					subobj.put("id", peAll.get(j).getPmsNo());
					subobj.put("pId", peEntr.get(i).getEntranceNo());
					for (PmsEntranceRole pre : perList) {
						if (pre.getPmsNo().equals(peAll.get(j).getPmsNo())) {
							subobj.put("checked", true);
							break;
						} else {
							subobj.put("checked", false);
						}
					}
					array.add(subobj);
				}
			}
		}
		return array;
	}

	/**
	 * 
	 * @Description: 视角转换
	 * @param @return
	 * @param @throws
	 *            Exception
	 * @return JSONArray
	 * @throws @author
	 *             wangcong
	 * @date 2015年12月30日下午3:33:37
	 */
	private JSONArray viewConvertJson(String roleNo) throws Exception {
		CodeUtils cu = new CodeUtils();
		JSONArray dicArray = JSONArray.fromObject(cu.getCacheByKeyName("PMS_VIEWPOINT_NAME"));
		for (int i = 0; i < dicArray.size(); i++) {
			dicArray.getJSONObject(i).put("id", dicArray.getJSONObject(i).getString("optCode"));
			dicArray.getJSONObject(i).put("name", dicArray.getJSONObject(i).getString("optName"));
			dicArray.getJSONObject(i).put("pId", dicArray.getJSONObject(i).getString("keyName"));
			// dicArray.getJSONObject(i).put("open",true);
		}
		JSONArray array = new JSONArray();
		List<PmsViewpoint> pv = pmsViewpointFeign.getAllList();
		List<PmsViewpointRole> pvrList = pmsViewpointRoleFeign.findByRoleNo(roleNo);
		for (int i = 0; i < pv.size(); i++) {
			JSONObject obj = new JSONObject();
			obj.put("name", pv.get(i).getViewpointMenuName());
			obj.put("id", pv.get(i).getViewpointMenuNo());
			obj.put("pId", pv.get(i).getUpViewpointMenuNo());
			// obj.put("open",true);
			for (PmsViewpointRole pvr : pvrList) {
				if (pvr.getViewpointMenuNo().equals(pv.get(i).getViewpointMenuNo())) {
					obj.put("checked", true);
					break;
				} else {
					obj.put("checked", false);
				}
			}
			array.add(obj);
		}
		array.addAll(dicArray);
		return array;
	}

	/**
	 * 
	 * @Description: 数据权限转换
	 * @param @return
	 * @param @throws
	 *            Exception
	 * @return JSONArray
	 * @throws @author
	 *             wangcong
	 * @date 2015年12月30日下午7:24:54
	 */
	private JSONArray dataConvertJson(String roleNo) throws Exception {
		JSONArray array = new JSONArray();
		List<PmsDataRang> pd = pmsDataRangFeign.getAllList();
		List<PmsDataRangRole> pdrList = pmsDataRangRoleFeign.findByRoleNo(roleNo);
		Map<String, PmsDataRangRole> map = new HashMap<String, PmsDataRangRole>();
		if ((pdrList != null) && (pdrList.size() != 0)) {
			for (PmsDataRangRole prd : pdrList) {
				map.put(prd.getFunNo(), prd);
			}
		}
		for (int i = 0; i < pd.size(); i++) {
			JSONObject obj = new JSONObject();
			obj.put("name", pd.get(i).getFunDisc());
			obj.put("id", pd.get(i).getFunNo());
			if (map.get(pd.get(i).getFunNo()) != null) {
				obj.put("roleType", map.get(pd.get(i).getFunNo()).getFunRoleType());
			}
			obj.put("list", pd.get(i).getPmsDataSubList());
			array.add(obj);
		}
		return array;
	}

}
