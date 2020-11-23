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

import app.component.pms.entity.PmsBiz;
import app.component.pms.feign.PmsBizFeign;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = "/pmsBiz")
public class PmsBizController {

	@Autowired
	private PmsBizFeign pmsBizFeign;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;

	@ResponseBody
	@RequestMapping(value = "/save")
	public JSONObject save(Model model, String jsonData) throws Exception {
		JSONObject json = new JSONObject();
		try {
			JSONObject jsonObj = JSONObject.fromObject(jsonData);
			PmsBiz pmsBiz = (PmsBiz) JSONObject.toBean(jsonObj, PmsBiz.class);
			if ("insert".equals(jsonObj.getString("flag"))) {
				pmsBizFeign.insert(pmsBiz);
			} else {
				pmsBizFeign.update(pmsBiz);
			}
			json.put("flag", true);
		} catch (Exception e) {
			json.put("flag", false);
			json.put("msg", e.getMessage());
			throw e;
		}
		return json;
	}

	@ResponseBody
	@RequestMapping(value = "/getAll")
	public JSONArray getAll(Model model) throws Exception {
		List<PmsBiz> list = pmsBizFeign.getAll();
		JSONArray array = JSONArray.fromObject(list);
		return array;
	}

	@ResponseBody
	@RequestMapping(value = "/getAllBySts")
	public JSONArray getAllBySts(Model model, String bizState, String roleNo) throws Exception {
		List<PmsBiz> list = pmsBizFeign.getAllPmsBizByRole(bizState, roleNo);
		Map<String,Object> dataMap = new HashMap<String,Object>();
		JSONArray array = JSONArray.fromObject(list);
		dataMap.put("array", array.toString());
		return array;
	}

	/**
	 * 
	 * 方法描述： 根据入口动产展示功能
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2017-7-19 上午11:02:42
	 */
	@ResponseBody
	@RequestMapping(value = "/getAllPmsBizByEntr")
	public JSONArray getAllPmsBizByEntr(Model model, String bizState, String entrNoStr, String roleNo)
			throws Exception {
		List<PmsBiz> list = pmsBizFeign.getAllByEntrance(bizState, entrNoStr, roleNo);
		JSONArray array = JSONArray.fromObject(list);
		return array;
	}

	@ResponseBody
	@RequestMapping(value = "/updateStsById")
	public JSONObject updateStsById(Model model, String jsonData, String bizState) throws Exception {
		JSONObject json = new JSONObject();
		try {
			JSONArray arr = JSONArray.fromObject(jsonData);
			String[] pmsSernoArr = new String[arr.size()];
			for (int i = 0; i < arr.size(); i++) {
				pmsSernoArr[i] = arr.getString(i);
			}
			PmsBiz pmsBiz = new PmsBiz();
			pmsBiz.setBizState(bizState);
			pmsBiz.setPmsSernoArr(pmsSernoArr);
			pmsBizFeign.updateStsById(pmsBiz);
			json.put("flag", true);
		} catch (Exception e) {
			json.put("flag", false);
			json.put("msg", e.getMessage());
			throw e;
		}
		return json;
	}

	@ResponseBody
	@RequestMapping(value = "/deleteById")
	public JSONObject deleteById(Model model, String jsonData) throws Exception {
		JSONObject json = new JSONObject();
		try {
			JSONArray arr = JSONArray.fromObject(jsonData);
			String[] pmsSernoArr = new String[arr.size()];
			for (int i = 0; i < arr.size(); i++) {
				pmsSernoArr[i] = arr.getString(i);
			}
			PmsBiz pmsBiz = new PmsBiz();
			pmsBiz.setPmsSernoArr(pmsSernoArr);
			pmsBizFeign.deleteByIdArr(pmsBiz);
			json.put("flag", true);
			json.put("msg", "删除成功!");
		} catch (Exception e) {
			json.put("flag", false);
			json.put("msg", "删除失败:" + e.getMessage());
			throw e;
		}
		return json;
	}

}
