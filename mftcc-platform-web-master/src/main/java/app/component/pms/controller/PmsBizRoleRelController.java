package app.component.pms.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import app.base.cache.feign.BusiCacheFeign;
import app.component.pms.entity.PmsBizRoleRel;
import app.component.pms.feign.PmsBizRoleRelFeign;
import cn.mftcc.common.MessageEnum;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;



@Controller
@RequestMapping(value = "/pmsBizRoleRel")
public class PmsBizRoleRelController {
	
	@Autowired
	private PmsBizRoleRelFeign pmsBizRoleRelFeign;
	@Autowired
	private BusiCacheFeign busiCacheFeign;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	
	@RequestMapping(value = "/getPage")
	public String getPage()throws Exception{
		return "/component/pms/pms_biz";
	}
	
	@ResponseBody
	@RequestMapping(value = "/getAllByRoleNo")
	public JSONArray getAllByRoleNo(Model model,String roleNo) throws Exception{
		List<PmsBizRoleRel> list = pmsBizRoleRelFeign.getAllByRoleNo(roleNo);
		JSONArray array = JSONArray.fromObject(list);
		return array;
	}
	
	@ResponseBody
	@RequestMapping(value = "/save")
	public JSONObject save(Model model,String roleNo,String pmsSerno) throws Exception{
		JSONObject json = new JSONObject();
		try {
			PmsBizRoleRel pmsBizRoleRel = new PmsBizRoleRel();
			pmsBizRoleRel.setRoleNo(roleNo);
			pmsBizRoleRelFeign.deleteByRoleNo(pmsBizRoleRel);
			JSONArray arr = JSONArray.fromObject(pmsSerno);
			List<PmsBizRoleRel> list = new ArrayList<PmsBizRoleRel>();
			for(int i =0;i<arr.size();i++){
				pmsSerno = arr.getString(i);
				pmsBizRoleRel = new PmsBizRoleRel();
				pmsBizRoleRel.setRoleNo(roleNo);
				pmsBizRoleRel.setPmsSerno(pmsSerno);
				list.add(pmsBizRoleRel);
			}
			pmsBizRoleRelFeign.insertBatch(list);
			busiCacheFeign.refreshSysRoleButtonCache();
			json.put("flag", true);
			json.put("msg",MessageEnum.SUCCEED_SAVE.getMessage());
		} catch (Exception e) {
			json.put("flag", false);
			json.put("msg",MessageEnum.FAILED_SAVE.getMessage());
			throw new Exception(e.getMessage());
		}
		
		return json;
	}

}
