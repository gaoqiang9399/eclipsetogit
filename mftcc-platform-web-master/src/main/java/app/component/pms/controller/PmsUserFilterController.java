package app.component.pms.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.core.struts.ActionContext;
import com.dhcc.workflow.pvm.internal.util.StringUtil;

import app.base.User;
import app.component.nmd.entity.ParmDic;
import app.component.pms.entity.PmsUserFilter;
import app.component.pms.feign.PmsUserFilterFeign;
import app.tech.oscache.CodeUtils;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.DateUtil;
import net.sf.json.JSONArray;

@RestController
@RequestMapping("/pmsUserFilter")
public class PmsUserFilterController {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private PmsUserFilterFeign pmsUserFilterFeign;
	
	{
		ActionContext.initialize(request,
				response);
	}
	
	@RequestMapping(value = "/insert")
	public Map<String, Object> insert(Model model,String filterNo,String filterName,String filterContent,String useFlag) throws Exception{
		Map<String, Object> dataMap = new HashMap<String, Object>();
		String url = (String)request.getSession().getAttribute("helpAction");
		String jsp = (String)request.getSession().getAttribute("helpJsp");
		PmsUserFilter pmsUserFilter = new PmsUserFilter();
		pmsUserFilter.setOpNo(User.getRegNo(request));
		pmsUserFilter.setUrl(url);
		pmsUserFilter.setJsp(jsp);
		pmsUserFilter.setFilterNo(filterNo);
		pmsUserFilter.setFilterName(filterName);
		pmsUserFilter.setFilterContent(filterContent);
		pmsUserFilter.setUseFlag(useFlag);
		PmsUserFilter puf = new PmsUserFilter();
		puf = pmsUserFilterFeign.getById(pmsUserFilter);
		if(puf==null){
			pmsUserFilter.setLstModTime(DateUtil.getDateTime());
			pmsUserFilter.setUseFlag("0");
			pmsUserFilter.setOptType("2");
			pmsUserFilterFeign.insert(pmsUserFilter);
		}else{
			pmsUserFilterFeign.update(pmsUserFilter);
		}
		dataMap.put("msg",MessageEnum.SUCCEED_SAVE.getMessage());
		model.addAttribute("dataMap",dataMap);
		model.addAttribute("query", "");
		return dataMap;
	}
	
	@RequestMapping(value = "/delete")
	public Map<String, Object> delete(Model model, String filterNo) throws Exception{
		String url = (String)request.getSession().getAttribute("helpAction");
		String jsp = (String)request.getSession().getAttribute("helpJsp");
		PmsUserFilter pmsUserFilter = new PmsUserFilter();
		pmsUserFilter.setOpNo(User.getRegNo(request));
		pmsUserFilter.setUrl(url);
		pmsUserFilter.setJsp(jsp);
		pmsUserFilter.setFilterNo(filterNo);
		pmsUserFilterFeign.delete(pmsUserFilter);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		model.addAttribute("dataMap",dataMap);
		model.addAttribute("query", "");
		return dataMap;
	}
	
	@RequestMapping(value = "/getById")
	public Map<String, Object> getById(Model model, String filterNo) throws Exception{
		Map<String, Object> dataMap = new HashMap<String, Object>();
		String url = (String)request.getSession().getAttribute("helpAction");
		String jsp = (String)request.getSession().getAttribute("helpJsp");
		PmsUserFilter pmsUserFilter = new PmsUserFilter();
		pmsUserFilter.setOpNo(User.getRegNo(request));
		pmsUserFilter.setUrl(url);
		pmsUserFilter.setJsp(jsp);
		pmsUserFilter.setFilterNo(filterNo);
		pmsUserFilter = pmsUserFilterFeign.getById(pmsUserFilter);
		if(pmsUserFilter!=null){
			dataMap.put("json", pmsUserFilter.getFilterContent());
		}else{
			dataMap.put("json","{}");
		}
		model.addAttribute("dataMap",dataMap);
		model.addAttribute("query", "");
		return dataMap;
	}
	
	@RequestMapping(value = "/findByList")
	public Map<String, Object> findByList(Model model, String ajaxData) throws Exception{
		Map<String, Object> dataMap = new HashMap<String, Object>();
		String url = (String)request.getSession().getAttribute("helpAction");
		String jsp = (String)request.getSession().getAttribute("helpJsp");
		PmsUserFilter pmsUserFilter = new PmsUserFilter();
		pmsUserFilter.setOpNo(User.getRegNo(request));
		pmsUserFilter.setUrl(url);
		pmsUserFilter.setJsp(jsp);
		List<PmsUserFilter> pufList = pmsUserFilterFeign.findByList(pmsUserFilter);
		JSONArray dicArray = JSONArray.fromObject(pufList);
		List<PmsUserFilter> topList= new ArrayList<PmsUserFilter>();
		List<PmsUserFilter> moreList= new ArrayList<PmsUserFilter>();
		for (int i = 0; i < pufList.size(); i++) {
			if("1".equals(pufList.get(i).getUseFlag())){
				topList.add(pufList.get(i));
			}else{
				moreList.add(pufList.get(i));
			}
		}
		JSONArray topArray = JSONArray.fromObject(topList);
		JSONArray moreArray = JSONArray.fromObject(moreList);
		for(int i=0;i<dicArray.size();i++){
			dicArray.getJSONObject(i).put("opNo", dicArray.getJSONObject(i).getString("opNo"));
			dicArray.getJSONObject(i).put("id", dicArray.getJSONObject(i).getString("filterNo"));
			dicArray.getJSONObject(i).put("name", dicArray.getJSONObject(i).getString("filterName"));
			dicArray.getJSONObject(i).put("pId", "0");
			dicArray.getJSONObject(i).put("open",true);
		}
		for(int i=0;i<topArray.size();i++){
			topArray.getJSONObject(i).put("opNo", topArray.getJSONObject(i).getString("opNo"));
			topArray.getJSONObject(i).put("id", topArray.getJSONObject(i).getString("filterNo"));
			topArray.getJSONObject(i).put("name", topArray.getJSONObject(i).getString("filterName"));
			topArray.getJSONObject(i).put("pId", "0");
			topArray.getJSONObject(i).put("open",true);
		}
		for(int i=0;i<moreArray.size();i++){
			moreArray.getJSONObject(i).put("opNo", moreArray.getJSONObject(i).getString("opNo"));
			moreArray.getJSONObject(i).put("id", moreArray.getJSONObject(i).getString("filterNo"));
			moreArray.getJSONObject(i).put("name", moreArray.getJSONObject(i).getString("filterName"));
			moreArray.getJSONObject(i).put("pId", "0");
			moreArray.getJSONObject(i).put("open",true);
		}
		
		dataMap.put("json", dicArray);
		dataMap.put("topjson", topArray);
		dataMap.put("morejson", moreArray);
//		dataMap.put("toplistSize", topList.size());
		dataMap.put("morelistSize", moreList.size());
		model.addAttribute("dataMap",dataMap);
		model.addAttribute("query", "");
		return dataMap;
	}
	
	@RequestMapping(value = "/getTableName")
	public Map<String, Object> getTableName(Model model, String ajaxData) throws Exception{
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<PmsUserFilter> pufList = pmsUserFilterFeign.getTableName();
		JSONArray dicArray = JSONArray.fromObject(pufList);
		for(int i=0;i<dicArray.size();i++){
			dicArray.getJSONObject(i).put("id", dicArray.getJSONObject(i).getString("filterNo"));
			dicArray.getJSONObject(i).put("name", dicArray.getJSONObject(i).getString("filterName"));
			dicArray.getJSONObject(i).put("pId", "0");
			dicArray.getJSONObject(i).put("open",true);
		}
		dataMap.put("json", dicArray);
		model.addAttribute("dataMap",dataMap);
		model.addAttribute("query", "");
		return dataMap;
	}
	@RequestMapping(value = "/getTableColumn")
	public Map<String, Object> getTableColumn(Model model, String filterName) throws Exception{
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<PmsUserFilter> pufList = pmsUserFilterFeign.getTableColumn(filterName);
		JSONArray dicArray = JSONArray.fromObject(pufList);
		for(int i=0;i<dicArray.size();i++){
			dicArray.getJSONObject(i).put("id", underlineToCamel2(dicArray.getJSONObject(i).getString("filterNo")));
			dicArray.getJSONObject(i).put("name", dicArray.getJSONObject(i).getString("filterName"));
			dicArray.getJSONObject(i).put("pId", "0");
			dicArray.getJSONObject(i).put("open",true);
		}
		dataMap.put("json", dicArray);
		model.addAttribute("dataMap",dataMap);
		model.addAttribute("query", "");
		return dataMap;
	}
	
	public static String underlineToCamel2(String param){
    	param = param.toLowerCase();
        if (param==null||"".equals(param.trim())){
            return "";
        }
        StringBuilder sb=new StringBuilder(param);
        String pattern = "-";
        Matcher mc= Pattern.compile(pattern).matcher(param);
        int i=0;
        while (mc.find()){
            int position=mc.end()-(i++);
            //String.valueOf(Character.toUpperCase(sb.charAt(position)));
            sb.replace(position-1,position+1,sb.substring(position,position+1).toUpperCase());
        }
        return sb.toString();
    }
	
	@RequestMapping(value = "/getParmDic")
	public Map<String, Object> getParmDic(Model model, String filterName) throws Exception{
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<PmsUserFilter> pufList = pmsUserFilterFeign.getParmDic(filterName);
		JSONArray dicArray = JSONArray.fromObject(pufList);
		for(int i=0;i<dicArray.size();i++){
			dicArray.getJSONObject(i).put("id", dicArray.getJSONObject(i).getString("filterNo"));
			dicArray.getJSONObject(i).put("name", dicArray.getJSONObject(i).getString("filterName"));
			dicArray.getJSONObject(i).put("pId", "0");
			dicArray.getJSONObject(i).put("open",true);
		}
		dataMap.put("json", dicArray);
		model.addAttribute("dataMap",dataMap);
		model.addAttribute("query", "");
		return dataMap;
	}
	@RequestMapping(value = "/getCacheParmDic")
	public Map<String, Object> getCacheParmDic(Model model, String filterName) throws Exception{
		Map<String, Object> dataMap = new HashMap<String, Object>();
		CodeUtils cu =new CodeUtils();
		List<ParmDic> list =  (List<ParmDic>) cu.getCacheByKeyName(filterName);
		JSONArray dicArray = JSONArray.fromObject(list);
		dataMap.put("json", dicArray);
		model.addAttribute("dataMap",dataMap);
		model.addAttribute("query", "");
		return dataMap;
	}
	@RequestMapping(value = "/getCacheSelectFromDB")
	public Map<String, Object> getCacheSelectFromDB(Model model, String filterName, String methodName) throws Exception{
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<ParmDic> list = pmsUserFilterFeign.getCacheSelectFromDB(filterName,methodName);
		JSONArray dicArray = JSONArray.fromObject(list);
		dataMap.put("json", dicArray);
		model.addAttribute("dataMap",dataMap);
		model.addAttribute("query", "");
		return dataMap;
	}
	
	@RequestMapping(value = "/getSomeCacheParmDic")
	public Map<String, Object> getSomeCacheParmDic(Model model, String filterName) throws Exception{
		Map<String, Object> dataMap = new HashMap<String, Object>();
		CodeUtils cu =new CodeUtils();
		List<ParmDic> list = new ArrayList();
		if(!StringUtil.isEmpty(filterName)){
			String[] keyName=filterName.split("@");
			for(int i=0,j=keyName.length;i<j;i++){
				list.addAll((List<ParmDic>) cu.getCacheByKeyName(keyName[i]));
			}
		}
		JSONArray dicArray = JSONArray.fromObject(list);
		dataMap.put("json", dicArray);
		model.addAttribute("dataMap",dataMap);
		model.addAttribute("query", "");
		return dataMap;
	}
	
	@RequestMapping(value = "/update")
	public Map<String, Object> update(Model model, String ajaxData) throws Exception{
		Map<String, Object> dataMap = new HashMap<String, Object>();
		model.addAttribute("query", "");
		model.addAttribute("dataMap", dataMap);
		return dataMap;
	}
	@RequestMapping(value = "/updateStsAjax")
	public Map<String, Object> updateStsAjax(PmsUserFilter pmsUserFilter) throws Exception {
		//FormService formService = new FormService();
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try{
			//pmsUserFilter.setLastModTime(DateUtil.getDateTime());
			pmsUserFilterFeign.update(pmsUserFilter);
			dataMap.put("flag", "success");
			dataMap.put("msg",MessageEnum.SUCCEED_UPDATE.getMessage());
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.FAILED_UPDATE.getMessage());
			throw e;
		}
		return dataMap;
	}

}
