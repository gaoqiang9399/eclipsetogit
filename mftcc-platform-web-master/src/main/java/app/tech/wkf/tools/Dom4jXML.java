package app.tech.wkf.tools;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Dom4jXML {
	private JSONObject linesData;
	private JSONObject nodesData;
	private JSONObject processData;
	
	public Map<String,Object> DOM4JCreateXML (String jsonStr) throws Exception{
		JSONObject data = JSONObject.fromObject(jsonStr);
		
		processData = data.getJSONObject("process").getJSONObject("properties");
		nodesData = data.getJSONObject("nodes");
		linesData = data.getJSONObject("lines");
		
		
		Document document = DocumentHelper.createDocument();
		Element process = document.addElement("process", "http://www.dhcc.com/workflow/1.0/wfdl"); 
		/**
		 * process
		 */
		String processKey = "";
		if(!processData.getJSONObject("key").isNullObject()){
			processKey = processData.getJSONObject("key").getString("value");
		}else{
			Calendar ca = Calendar.getInstance();
			processKey = /*processData.getJSONObject("basename").getString("value")+"_"+*/ca.get(Calendar.MARCH)+ca.get(Calendar.YEAR)+ca.get(Calendar.DATE)+(System.currentTimeMillis()+"").substring(5,13);
		}
		
		process.addAttribute("key", processKey)
			.addAttribute("name", processData.getJSONObject("basename").getString("value")) 
			.addAttribute("description", processData.getJSONObject("basedescription").getString("value")) 
			.addAttribute("author", processData.get("author")==null?"":processData.getJSONObject("author").getString("value")) 
			.addAttribute("create", processData.get("create")==null?"":processData.getJSONObject("create").getString("value")) 
			.addAttribute("expire", processData.get("expire")==null?"":processData.getJSONObject("expire").getString("value"));
		
		createVars(processData,process);
		
		createEvent("start", processData,process);
		createEvent("end", processData,process);
		createEvent("notice-listener", processData,process);
		createEvent("app-listener", processData,process);
		
		createApprove(processData,process,null);
		
		Iterator keys = nodesData.keys();  
        while(keys.hasNext()){  
        	String key = keys.next().toString();
        	JSONObject node = nodesData.getJSONObject(key);
        	Element e = null;
			if(node.getString("type").contains("start")){
				e = createStart(process, node);
			}else if(node.getString("type").contains("end")){
				e = createEnd(process, node);
			}else if(node.getString("type").contains("task")){
				e = createTask(process, node);
			}else if(node.getString("type").contains("node")){
				e = createSignTask(process, node);
			}else if(node.getString("type").contains("fork")){
				e = createDecision(process, node);
			}else if(node.getString("type").contains("foreach")){
				e = createForeach(process, node);
			}else if(node.getString("type").contains("decision")){
				e = createFork(process, node);;
			}else if(node.getString("type").contains("join")){
				e = createJoin(process, node);
			}else {
			}
			if(e!=null){
				createLine(e,key);
			}
        }  
        Map<String,Object> map = new HashMap<String,Object>();
        OutputFormat format = new OutputFormat("    ",true);  
        StringWriter writer = new StringWriter();
        XMLWriter output = new XMLWriter(writer, format);
        output.write(document);
        writer.close();
        output.close();
        System.out.println(writer.toString());
        map.put("text", writer.toString());
        map.put("id", processKey);
        map.put("doc", document);
        return map;
	}

	
	
	/**
	 * 线
	 * @param root
	 * @param node
	 */
	private void createLine(Element root,String nodeId){
		Iterator keys = linesData.keys();  
        while(keys.hasNext()){
        	String key = keys.next().toString();
        	JSONObject lineData = linesData.getJSONObject(key);
        	if(lineData.getString("from").equals(nodeId)){
        		JSONObject lineProp = lineData.getJSONObject("properties");
        		Element line = root.addElement("transition");
        		Calendar ca = Calendar.getInstance();
        		line.addAttribute("id",lineProp.getJSONObject("overrideid").getString("value"))
        			.addAttribute("name",lineProp.getJSONObject("basename").getString("value"))
        			.addAttribute("description",lineProp.getJSONObject("basedescription").getString("value"))
        			.addAttribute("type","forward")
        			.addAttribute("from",lineData.getString("from"))
        			.addAttribute("to",lineData.getString("to"))
        			.addAttribute("g",lineData.getString("g")+":{0,0}");
//        			.addAttribute("type",lineData.getString("type"));
//        			.addAttribute("M",lineData.getString("M"));
        		if(!lineProp.getJSONObject("expr").isNullObject()){
        			String exprVal = lineProp.getJSONObject("expr").getString("value");
        			Element condition = line.addElement("condition");
        			condition.addAttribute("expr", exprVal);
            	}
        	}
        }
	}
	/**
	 * 开始节点
	 * @param root
	 * @param node
	 */
	private Element createStart(Element root,JSONObject node){
		JSONObject nodeProp = node.getJSONObject("properties");
		Element start = root.addElement("start");
		start.addAttribute("name",nodeProp.getJSONObject("basename").getString("value"));
		start.addAttribute("description",nodeProp.getJSONObject("basedescription").getString("value"));
		start.addAttribute("g",node.getString("left")+","+node.getString("top"));
		createEvent("start", nodeProp, start);
		createEvent("end", nodeProp, start);
		createEvent("notice-listener", nodeProp,start);
		createEvent("app-listener", nodeProp,start);
		return start;
	}
	/**
	 * 结束节点
	 * @param root
	 * @param node
	 */
	private Element createEnd(Element root,JSONObject node){
		JSONObject nodeProp = node.getJSONObject("properties");
		Element end = root.addElement("end");
		end.addAttribute("name",nodeProp.getJSONObject("basename").getString("value"));
		end.addAttribute("description",nodeProp.getJSONObject("basedescription").getString("value"));
		end.addAttribute("g",node.getString("left")+","+node.getString("top"));
		createEvent("start", nodeProp, end);
		createEvent("end", nodeProp, end);
		createEvent("notice-listener", nodeProp,end);
		createEvent("app-listener", nodeProp,end);
		return end;
	}
	/**
	 * 人工任务
	 * @param root
	 * @param node
	 */
	private Element createTask(Element root,JSONObject node){
		JSONObject nodeProp = node.getJSONObject("properties");
//		if(nodeProp.isNullObject()) return;
		Element task = root.addElement("task");
		task.addAttribute("name",nodeProp.getJSONObject("basename").getString("value"));
		task.addAttribute("description",nodeProp.getJSONObject("basedescription").getString("value"));
		
		JSONObject candidateLevel = nodeProp.getJSONObject("role-candidate-level");
		if(!nodeProp.getJSONObject("appType-chooseRole").isNullObject()){//role
			if(nodeProp.get("appType-chooseRole")!=null){
				JSONArray roleValueArr= nodeProp.getJSONObject("appType-chooseRole").getJSONArray("value");
				String groupsStr = "";
				String groupsDesc = "";
				for(int r=0;r<roleValueArr.size();r++){
					groupsStr += roleValueArr.getJSONObject(r).getString("id");
					groupsDesc += roleValueArr.getJSONObject(r).getString("text");
					if(r < roleValueArr.size()-1){
						groupsStr += ",";
						groupsDesc += ",";
					}
				}
				if(!"".equals(groupsStr) &&!"".equals(groupsDesc)){
					task.addAttribute("candidate-groups",groupsStr);
					task.addAttribute("candidate-groups-desc",groupsDesc);
				}
			}
			task.addAttribute("app-type","role");
			JSONObject branch = nodeProp.getJSONObject("appType-chooseBr");
			if(!branch.isNullObject()&&!branch.isEmpty()&&!branch.getJSONObject("value").isEmpty()){
				task.addAttribute("candidate-branch",branch.getJSONObject("value").getString("id"));
			}
			task.addAttribute("candidate-level",candidateLevel.getString("value"));
			JSONObject isAssignNext = nodeProp.getJSONObject("role-is-assign-next");
			task.addAttribute("is-assign-next",isAssignNext.getString("value"));
		}else if(!nodeProp.getJSONObject("appType-chooseUser").isNullObject()){//user
			JSONArray userValueArr= nodeProp.getJSONObject("appType-chooseUser").getJSONArray("value");
			String usersStr = "",userDesc = "";
			for(int r=0;r<userValueArr.size();r++){
				usersStr += userValueArr.getJSONObject(r).getString("id");
				userDesc += userValueArr.getJSONObject(r).getString("text");
				if(r < userValueArr.size()-1){
					usersStr += ",";
					userDesc += ",";
				}
			}
			if(!"".equals(usersStr)){
				if(userValueArr.size()>1) {
                    task.addAttribute("assignee",usersStr);
                } else {
                    task.addAttribute("candidate-users",usersStr);
                }
				task.addAttribute("user-desc",userDesc);
			}
			task.addAttribute("app-type","user");
			JSONObject isAssignNext = nodeProp.getJSONObject("user-is-assign-next");
			task.addAttribute("is-assign-next",isAssignNext.getString("value"));
		}else {
		}
		task.addAttribute("g",node.getString("left")+","+node.getString("top"));
		
		createForms(nodeProp, task);
		
		createVars(nodeProp, task);
		
		createEvent("start", nodeProp, task);
		createEvent("end", nodeProp, task);
		createEvent("notice-listener", nodeProp,task);
		createEvent("app-listener", nodeProp,task);
		
		createApprove(nodeProp, task,"task");
		
		createCopyTo(nodeProp, task);
		return task;
	}
	/**
	 * 会签节点
	 * @param root
	 * @param node
	 */
	private Element createSignTask(Element root,JSONObject node){
		JSONObject nodeProp = node.getJSONObject("properties");
//		if(nodeProp.isNullObject()) return;
		Element signtask = root.addElement("signtask");
		signtask.addAttribute("name",nodeProp.getJSONObject("basename").getString("value"));
		signtask.addAttribute("description",nodeProp.getJSONObject("basedescription").getString("value"));
		JSONObject candidateLevel = nodeProp.getJSONObject("role-candidate-level");
		if(!nodeProp.getJSONObject("appType-chooseRole").isNullObject()){//role
			JSONArray roleValueArr= nodeProp.getJSONObject("appType-chooseRole").getJSONArray("value");
			String groupsStr = "";
			String groupsDesc = "";
			for(int r=0;r<roleValueArr.size();r++){
				groupsStr += roleValueArr.getJSONObject(r).getString("id");
				groupsDesc += roleValueArr.getJSONObject(r).getString("text");
				if(r < roleValueArr.size()-1){
					groupsStr += ",";
					groupsDesc += ",";
				}
			}
			if(!"".equals(groupsStr) &&!"".equals(groupsDesc)){
				signtask.addAttribute("candidate-groups",groupsStr);
				signtask.addAttribute("candidate-groups-desc",groupsDesc);
			}
			JSONObject branch = nodeProp.getJSONObject("appType-chooseBr");
			if(!branch.isNullObject()&&!branch.isEmpty()&&!branch.getJSONObject("value").isEmpty()){
				signtask.addAttribute("candidate-branch",branch.getJSONObject("value").getString("id"));
			}
			signtask.addAttribute("candidate-level",candidateLevel.getString("value"));
			JSONObject strategyType = nodeProp.getJSONObject("role-strategy-type");
			signtask.addAttribute("strategy-type",strategyType.getString("value"));
			JSONObject strategyValue = nodeProp.getJSONObject("strategy-value");
			if(!strategyValue.isNullObject()){
				if("pass_ratio".equals(strategyType.getString("value"))){
					signtask.addAttribute("strategy-value",strategyValue.getString("value"));
				}else{
					signtask.addAttribute("strategy-value",strategyValue.getString("value"));
				}
			}
		}else if(!nodeProp.getJSONObject("appType-chooseUser").isNullObject()){//user
			JSONArray userValueArr= nodeProp.getJSONObject("appType-chooseUser").getJSONArray("value");
			String usersStr = "",userDesc = "";
			for(int r=0;r<userValueArr.size();r++){
				usersStr += userValueArr.getJSONObject(r).getString("id");
				userDesc += userValueArr.getJSONObject(r).getString("text");
				if(r < userValueArr.size()-1){
					usersStr += ",";
					userDesc += ",";
				}
			}
			if(!"".equals(usersStr)){
				if(userValueArr.size()>1) {
                    signtask.addAttribute("assignee",usersStr);
                } else {
                    signtask.addAttribute("candidate-users",usersStr);
                }
				signtask.addAttribute("user-desc",userDesc);
			}
			JSONObject strategyType = nodeProp.getJSONObject("user-strategy-type");
			signtask.addAttribute("strategy-type",strategyType.getString("value"));
			JSONObject strategyValue = nodeProp.getJSONObject("strategy-value");
			if(!strategyValue.isNullObject()){
				if("pass_ratio".equals(strategyType.getString("value"))){
					signtask.addAttribute("strategy-value",strategyValue.getString("value"));
				}else{
					signtask.addAttribute("strategy-value",strategyValue.getString("value"));
				}
			}
		}else {
		}
		signtask.addAttribute("g",node.getString("left")+","+node.getString("top"));
		
		if(!nodeProp.getJSONObject("normal-signnotpass").isNullObject()){
			signtask.addAttribute("signnotpass",nodeProp.getJSONObject("normal-signnotpass").getString("value"));
		}
		
		if(!nodeProp.getJSONObject("defaultRollbackNode").isNullObject()){
			String rollbackNode = nodeProp.getJSONObject("defaultRollbackNode").getJSONObject("value").getString("id");
			if(!"".equals(rollbackNode)){
				rollbackNode = rollbackNode.split(",")[0];
			}
			signtask.addAttribute("defaultRollbackNode",rollbackNode);
		}
		
		createForms(nodeProp, signtask);
		
		createVars(nodeProp, signtask);
		
		createEvent("start", nodeProp, signtask);
		createEvent("end", nodeProp, signtask);
		createEvent("notice-listener", nodeProp,signtask);
		createEvent("app-listener", nodeProp,signtask);
		
		createApprove(nodeProp, signtask,"signtask");
		return signtask;
	}
	/**
	 * 判定节点
	 * @param root
	 * @param node
	 */
	private Element createDecision(Element root,JSONObject node){
		System.out.println("**********解析判定节点start*********");
		JSONObject nodeProp = node.getJSONObject("properties");
		Element decision = root.addElement("decision");
		decision.addAttribute("name",nodeProp.getJSONObject("basename").getString("value"));
		System.out.println("**********解析判定节点basename*********");
		decision.addAttribute("description",nodeProp.getJSONObject("basedescription").getString("value"));
		System.out.println("**********解析判定节点basedescription*********");
		JSONObject expr = nodeProp.getJSONObject("expr");
		if(!expr.isNullObject()){
			System.out.println("**********解析判定节点表达式*********");
			String value;
			try {
				value = expr.getJSONArray("value").getJSONObject(0).getString("id");
			} catch (Exception e) {
				value = expr.getString("value");
			}
			decision.addAttribute("expr",value);
		}else{
			System.out.println("**********解析判定节点类名*********");
			if(!nodeProp.getJSONObject("handler").isNullObject()){
				decision.addElement("handler").addAttribute("class", nodeProp.getJSONObject("handler").getString("value"));
			}else{
				decision.addElement("handler").addAttribute("class", "");
			}
		}
		System.out.println("**********解析判定节点end*********");
		decision.addAttribute("g",node.getString("left")+","+node.getString("top"));
		createEvent("start", nodeProp, decision);
		createEvent("end", nodeProp, decision);
		createEvent("notice-listener", nodeProp,decision);
		createEvent("app-listener", nodeProp,decision);
		System.out.println("**********解析判定节点事件*********");
		return decision;
	}
	/**
	 * 循环节点
	 * @param root
	 * @param node
	 */
	private Element createForeach(Element root,JSONObject node){
		JSONObject nodeProp = node.getJSONObject("properties");
		Element foreach = root.addElement("foreach");
		foreach.addAttribute("name",nodeProp.getJSONObject("basename").getString("value"));
		foreach.addAttribute("description",nodeProp.getJSONObject("basedescription").getString("value"));
		JSONObject var = nodeProp.getJSONObject("var");
		if(!var.isNullObject()){
			foreach.addAttribute("var",var.getString("value"));
		}
		JSONObject in = nodeProp.getJSONObject("in");
		if(!in.isNullObject()){
			foreach.addAttribute("in",in.getString("value"));
		}
		foreach.addAttribute("g",node.getString("left")+","+node.getString("top"));
		createEvent("start", nodeProp, foreach);
		createEvent("end", nodeProp, foreach);
		createEvent("notice-listener", nodeProp,foreach);
		createEvent("app-listener", nodeProp,foreach);
		return foreach;
	}
	/**
	 * 分支节点
	 * @param root
	 * @param node
	 */
	private Element createFork(Element root,JSONObject node){
		JSONObject nodeProp = node.getJSONObject("properties");
		Element fork = root.addElement("fork");
		fork.addAttribute("name",nodeProp.getJSONObject("basename").getString("value"));
		fork.addAttribute("description",nodeProp.getJSONObject("basedescription").getString("value"));
		fork.addAttribute("g",node.getString("left")+","+node.getString("top"));
		createEvent("start", nodeProp, fork);
		createEvent("end", nodeProp, fork);
		createEvent("notice-listener", nodeProp,fork);
		createEvent("app-listener", nodeProp,fork);
		return fork;
	}
	/**
	 * 聚合节点
	 * @param root
	 * @param node
	 */
	private Element createJoin(Element root,JSONObject node){
		JSONObject nodeProp = node.getJSONObject("properties");
		Element join = root.addElement("join");
		join.addAttribute("name",nodeProp.getJSONObject("basename").getString("value"));
		join.addAttribute("description",nodeProp.getJSONObject("basedescription").getString("value"));
		JSONObject multiplicity = nodeProp.getJSONObject("multiplicity");
		if(!multiplicity.isNullObject()){
			join.addAttribute("multiplicity",multiplicity.getString("value"));
		}
		
		join.addAttribute("g",node.getString("left")+","+node.getString("top"));
		createEvent("start", nodeProp, join);
		createEvent("end", nodeProp, join);
		createEvent("notice-listener", nodeProp,join);
		createEvent("app-listener", nodeProp,join);
		return join;
	}
	
	
	/**
	 * 表单
	 * @param data
	 * @param root
	 */
	private void createForms(JSONObject data,Element root){
		if(data.getJSONObject("forms").isNullObject()) {
            return;
        }
		JSONArray formsArray = data.getJSONObject("forms").getJSONArray("value");
		if(formsArray.size()>0){
			Element forms = root.addElement("forms");
			for(int v=0;v<formsArray.size();v++){
				JSONArray valueArray = formsArray.getJSONArray(v);
				Element form = forms.addElement("form");
				for(int vp=0;vp<valueArray.size();vp++){
					String id = valueArray.getJSONObject(vp).getString("id");
					String name = valueArray.getJSONObject(vp).getString("name");
					String value = valueArray.getJSONObject(vp).getString("value");
					if("type".equals(id)){
						form.addAttribute(id,value);
					}else{
						form.addAttribute(id,name);
					}
				}
			}
		}
	}
	/**
	 * 流程变量
	 * @param root 父节点节点
	 */
	private void createVars(JSONObject data,Element root){
		if(data.getJSONObject("vars").isNullObject()) {
            return;
        }
		JSONArray varsArray = data.getJSONObject("vars").getJSONArray("value");
		if(varsArray.size()>0){
			Element vars = root.addElement("vars");
			for(int v=0;v<varsArray.size();v++){
				JSONArray valueArray = varsArray.getJSONArray(v);
				Element var = vars.addElement("var");
				for(int vp=0;vp<valueArray.size();vp++){
					String id = valueArray.getJSONObject(vp).getString("id");
					String name = valueArray.getJSONObject(vp).getString("name");
					String value = valueArray.getJSONObject(vp).getString("value");
					if("type".equals(id)){
						var.addAttribute(id,value);
					}else{
						var.addAttribute(id,name);
					}
				}
			}
		}
	}
	/**
	 * 事件
	 * @param type 类型
	 * @param root 父节点节点
	 */
	private void createEvent(String type,JSONObject data,Element root){
		if(data.getJSONObject(type).isNullObject()) {
            return;
        }
		String value;
		try {
			value = data.getJSONObject(type).getJSONArray("value").getJSONObject(0).getString("id");
		} catch (Exception e) {
			value = data.getJSONObject(type).getString("value");
		}
		if("notice-listener".equals(type) || "app-listener".equals(type)){
			if(!"".equals(value)){
				Element listener = root.addElement(type);
				listener.addAttribute("class",value);
			}
		}else{
			if(!"".equals(value)){
				Element on = root.addElement("on");
				on.addAttribute("event",type);
				Element eventListener = on.addElement("event-listener");
				eventListener.addAttribute("class",value);
			}
		}
	}
	/**
	 * 流程审批
	 * @param root 父节点
	 */
	private void createApprove(JSONObject data,Element root,String type){
		Element approve = root.addElement("approve");
		if(!data.getJSONObject("approve").isNullObject()&&!"".equals(data.getJSONObject("approve").getString("value"))){
			try {
				approve.addAttribute("url",data.getJSONObject("approve").getJSONArray("value").getJSONObject(0).getString("id"));
			} catch (Exception e) {
				approve.addAttribute("url",data.getJSONObject("approve").getString("value"));
//				Map<String,String> map = new HashMap<String,String>();
//				map.put("description", data.getJSONObject("approveDesc").getString("value"));
//				map.put("url", data.getJSONObject("approve").getString("value"));
//				createBizXML("approve", map);
			}
		}
		JSONObject couldRollback = data.getJSONObject("normal-couldRollback");
		if(!couldRollback.isNullObject()){
			approve.addAttribute("couldRollback",couldRollback.getString("value"));
		}
		JSONObject timeType = data.getJSONObject("time-type");
		if(!timeType.isNullObject()){
			try {
				approve.addAttribute("time-type",timeType.getJSONObject("value").getString("id"));
			} catch (Exception e) {
				approve.addAttribute("time-type",timeType.getString("value"));
			}
		}
		JSONObject timeValue = data.getJSONObject("time-value");
		if(!timeValue.isNullObject()){
			approve.addAttribute("time-value",timeValue.getString("value"));
		}
		if(!data.getJSONObject("normal-viewhistory").isNullObject()){
			approve.addAttribute("viewhistory",data.getJSONObject("normal-viewhistory").getString("value"));
		}
		if(!data.getJSONObject("defaultRollbackNode").getJSONObject("value").isEmpty()){
			String rollbackNode = data.getJSONObject("defaultRollbackNode").getJSONObject("value").getString("id");
			if(!"".equals(rollbackNode)){
				rollbackNode = rollbackNode.split(",")[0];
			}
			if(type!=null && "signtask".equals(type) &&!"".equals(rollbackNode)){
				Element rollbackLine = root.addElement("transition");
				String temp = (System.currentTimeMillis()+"").substring(0,7);
				String fromId = data.getJSONObject("basename").getString("value");
//				JSONObject fromProp = nodesData.getJSONObject(fromId).getJSONObject("properties");
//				String g1 = "{"+(fromProp.getInt("left")+fromProp.getInt("width")/2)+","+(fromProp.getInt("top")+fromProp.getInt("height")/2)+"}";
//				String g2 = "{"+(fromProp.getInt("left")+fromProp.getInt("width")/2)+","+(fromProp.getInt("top")+fromProp.getInt("height")/2)+200+"}";
//				JSONObject toProp = nodesData.getJSONObject(rollbackNode).getJSONObject("properties");
//				String g3 = "{"+(toProp.getInt("left")+toProp.getInt("width")/2)+","+(toProp.getInt("top")+toProp.getInt("height")/2)+200+"}";
//				String g4 = "{"+(toProp.getInt("left")+toProp.getInt("width")/2)+","+(toProp.getInt("top")+toProp.getInt("height")/2)+"}";
				rollbackLine.addAttribute("id", temp)
							.addAttribute("name", temp)
							.addAttribute("type", "rollback")
							.addAttribute("description", "rollback")
							.addAttribute("from", fromId)
							.addAttribute("to", rollbackNode);
//							.addAttribute("g", g1+","+g2+","+g3+","+g4);
			}else{
				approve.addAttribute("defaultRollbackNode",rollbackNode);
			}
		}else{
			approve.addAttribute("defaultRollbackNode","");
		}
	}
	/**
	 * 抄送
	 * @param data
	 * @param root
	 */
	private void createCopyTo(JSONObject data,Element root){
		Element copyto = root.addElement("copyto");
		JSONArray copytoRoles = new JSONArray();
		if(!data.getJSONObject("copytoType-chooseRole").isNullObject()){
			copytoRoles = data.getJSONObject("copytoType-chooseRole").getJSONArray("value");
		}
		String groupsStr = "",groupsDesc = "";
		if(copytoRoles.size()>0){
			for(int r=0;r<copytoRoles.size();r++){
				groupsStr += copytoRoles.getJSONObject(r).getString("id");
				groupsDesc += copytoRoles.getJSONObject(r).getString("text");
				if(r < copytoRoles.size()-1){
					groupsStr += ",";
					groupsDesc += ",";
				}
			}
			copyto.addAttribute("group",groupsStr);
			copyto.addAttribute("groups-desc",groupsDesc);
		}
		JSONObject branch = new JSONObject();
		if(!data.getJSONObject("copytoType-chooseBr").isNullObject()){
			branch = data.getJSONObject("copytoType-chooseBr").getJSONObject("value");
		}
		if(!branch.isNullObject()&&!branch.isEmpty()){
			copyto.addAttribute("branch",branch.getString("id"));
		}
		JSONArray copytoUsers = new JSONArray();
		if(!data.getJSONObject("copytoType-chooseUser").isNullObject()){
			copytoUsers = data.getJSONObject("copytoType-chooseUser").getJSONArray("value");
		}
		String userStr = "",userDesc = "";
		if(copytoUsers.size()>0){
			for(int u=0;u<copytoUsers.size();u++){
				userStr += copytoUsers.getJSONObject(u).getString("id");
				userDesc += copytoUsers.getJSONObject(u).getString("text");
				if(u < copytoUsers.size()-1){
					userStr += ",";
					userDesc += ",";
				}
			}
			copyto.addAttribute("user",userStr);
			copyto.addAttribute("user-desc",userDesc);
		}
	}
	
	//TODO 解析XML
	/**
	 * 解析XML
	 * @param uploadFile
	 * @return
	 * @throws Exception
	 */
	public JSONObject DOM4JResXML(Document document) throws Exception{
		JSONObject data = new JSONObject();
		processData = new JSONObject();
		nodesData = new JSONObject();
		linesData = new JSONObject();
	    Element node = document.getRootElement();
	    //process
	    processData.put("properties", new JSONObject());
	    resProcess(processData.getJSONObject("properties"), node);
	    resStartEnd(nodesData, node, "start");
	    resStartEnd(nodesData, node, "end");
	    resTask(nodesData, node, "task");
	    resSignTask(nodesData, node, "signtask");
	    resDecision(nodesData, node,"decision");
	    resForeach(nodesData, node, "foreach");
	    resFork(nodesData, node, "fork");
	    resJoin(nodesData, node, "join");
		/*resEvent(processData.getJSONObject("properties"), node);
		resVars(processData.getJSONObject("properties"), node);
		resApprove(processData.getJSONObject("properties"), node);
		resForm(processData.getJSONObject("properties"), node);
		resCopyTo(processData.getJSONObject("properties"), node);
		resLine(linesData, node);*/
	    data.put("process", processData);
	    data.put("nodes", nodesData);
	    data.put("lines", linesData);
		return data;
	}
	
	/**
	 * 解析流程节点
	 * @param obj
	 * @param node
	 */
	private void resProcess(JSONObject obj,Element node){
		List<Attribute> list = node.attributes();
		for (Attribute attr : list) { 
			if("xmlns".equals(attr.getName())) {
                continue;
            }
			if("key".equals(attr.getName())){
				obj.put("key", attr.getValue());
			}
			JSONObject attrObj = new JSONObject();
			if("name".equals(attr.getName())||"description".equals(attr.getName())){
				attrObj.put("id", "base"+attr.getName());
				attrObj.put("value", attr.getValue());
				attrObj.put("type", "String");
			}else{
				attrObj.put("id",attr.getName());
				attrObj.put("type","String");
				attrObj.put("value",attr.getValue());
			}
			obj.put(attrObj.getString("id"), attrObj);
        } 
		resEvent(obj, node);
		resVars(obj, node);
		resApprove(obj, node);
	}
	/**
	 * 解析开始/结束节点
	 * @param obj
	 * @param node
	 */
	private void resStartEnd(JSONObject nodeData,Element node,String nodeType){
		List<Element> nodeList = node.elements(nodeType);
		for(Iterator<Element> it = nodeList.iterator(); it.hasNext();) {    
			Element el = (Element) it.next();
			List<Attribute> list = el.attributes();
			JSONObject start = new JSONObject();
			JSONObject propData = new JSONObject();
			String startId = null;
			for (Attribute attr : list) {
				JSONObject attrObj = new JSONObject();
				attrObj.put("id", "base"+attr.getName());
				attrObj.put("value", attr.getValue());
				attrObj.put("type", "String");
				if("name".equals(attr.getName())){
					startId = attr.getValue();
					propData.put("basename", attrObj);
				}else if("description".equals(attr.getName())){
					start.put("name", attr.getValue());
					propData.put("basedescription", attrObj);
				}else if("g".equals(attr.getName())){
					String[] arr = attr.getValue().split(",");
					start.put("left", Double.parseDouble(arr[0]));
					start.put("top", Double.parseDouble(arr[1]));
				}else {
				}
				
			}
			resEvent(propData, el);
			resLine(linesData, el);
			start.put("properties", propData);
			start.put("alt", true);
			start.put("type", nodeType+" round");
			nodeData.put(startId, start);
		}
	}
	/**
	 * 解析人工任务节点
	 * @param obj
	 * @param node
	 */
	private void resTask(JSONObject nodeData,Element node, String nodeType){
		List<Element> nodeList = node.elements(nodeType);
		for(Iterator<Element> it = nodeList.iterator(); it.hasNext();) {    
			Element el = (Element) it.next();
			List<Attribute> list = el.attributes();
			JSONObject task = new JSONObject();
			JSONObject propData = new JSONObject();
			String taskId = null;
			String appType = el.attributeValue("app-type");
			if(null==appType||"".equals(appType)) {
                appType = "role";
            }
			for (Attribute attr : list) {
				JSONObject attrObj = new JSONObject();
				if("candidate-groups".equals(attr.getName())){
					JSONArray jsonArr = new JSONArray();
					String[] descArr = el.attribute("candidate-groups-desc").getValue().split(",");
					String[] valueArr = attr.getValue().split(",");
					for(int i =0;i<valueArr.length;i++){
						JSONObject obj = new JSONObject();
						obj.put("id", valueArr[i]);
						obj.put("text", descArr[i]);
						jsonArr.add(obj);
					}
					attrObj.put("id", "appType-chooseRole");
					attrObj.put("value", jsonArr);
					attrObj.put("type", "Select");
					appType = "role";
				}
				if("candidate-users".equals(attr.getName())||"assignee".equals(attr.getName())){
					JSONArray jsonArr = new JSONArray();
					String[] arr = attr.getValue().split(",");
					String[] arrDesc;
					if(el.attribute("user-desc")!=null) {
                        arrDesc = el.attribute("user-desc").getValue().split(",");
                    } else {
                        arrDesc = arr;
                    }
					for(int i =0;i<arr.length;i++){
						JSONObject obj = new JSONObject();
						obj.put("id", arr[i]);
						obj.put("text", arrDesc[i]);
						jsonArr.add(obj);
					}
					attrObj.put("id", "appType-chooseUser");
					attrObj.put("value", jsonArr);
					attrObj.put("type", "Select");
					appType = "user";
				}else if("candidate-branch".equals(attr.getName())){
					JSONObject obj = new JSONObject();
					obj.put("id", attr.getValue());
					attrObj.put("id", "appType-chooseBr");
					attrObj.put("value", obj);
					attrObj.put("type", "Tree");
				}else if("candidate-level".equals(attr.getName())){
					attrObj.put("id", appType+"-candidate-level");
					attrObj.put("value", attr.getValue());
					attrObj.put("type", "Radio");
				}else if("is-assign-next".equals(attr.getName())){
					attrObj.put("id", appType+"-is-assign-next");
					attrObj.put("value", attr.getValue());
					attrObj.put("type", "Radio");
				}else if("g".equals(attr.getName())){
					String[] arr = attr.getValue().split(",");
					task.put("left", Double.parseDouble(arr[0]));
					task.put("top", Double.parseDouble(arr[1]));
				}else if("name".equals(attr.getName())){
					attrObj.put("id", "base"+attr.getName());
					attrObj.put("value", attr.getValue());
					attrObj.put("type", "String");
					taskId = attr.getValue();
				}else if("description".equals(attr.getName())){
					attrObj.put("id", "base"+attr.getName());
					attrObj.put("value", attr.getValue());
					attrObj.put("type", "String");
					task.put("name", attr.getValue());
				}else {
				}
				if(!attrObj.isEmpty()) {
                    propData.put(attrObj.getString("id"), attrObj);
                }
			}
			
			resForm(propData, el);
			resVars(propData, el);
			resApprove(propData, el);
			resEvent(propData, el);
			resCopyTo(propData, el);
			resLine(linesData, el);
			task.put("properties", propData);
			task.put("alt", true);
			task.put("type", nodeType+" round");
			nodeData.put(taskId, task);
		}
	}
	/**
	 * 解析会签任务节点
	 * @param obj
	 * @param node
	 */
	private void resSignTask(JSONObject nodeData,Element node, String nodeType){
		List<Element> nodeList = node.elements(nodeType);
		for(Iterator<Element> it = nodeList.iterator(); it.hasNext();) {    
			Element el = (Element) it.next();
			List<Attribute> list = el.attributes();
			JSONObject task = new JSONObject();
			JSONObject propData = new JSONObject();
			String taskId = null;
			String appType = null;
			for (Attribute attr : list) {
				JSONObject attrObj = new JSONObject();
				if("candidate-groups".equals(attr.getName())){
					JSONArray jsonArr = new JSONArray();
					String[] descArr = el.attribute("candidate-groups-desc").getValue().split(",");
					String[] valueArr = attr.getValue().split(",");
					for(int i =0;i<valueArr.length;i++){
						JSONObject obj = new JSONObject();
						obj.put("id", valueArr[i]);
						obj.put("text", descArr[i]);
						jsonArr.add(obj);
					}
					attrObj.put("id", "appType-chooseRole");
					attrObj.put("value", jsonArr);
					attrObj.put("type", "Select");
					appType = "role";
				}
				if("candidate-users".equals(attr.getName())||"assignee".equals(attr.getName())){
					JSONArray jsonArr = new JSONArray();
					String[] arr = attr.getValue().split(",");
					String[] arrDesc;
					if(el.attribute("user-desc")!=null) {
                        arrDesc = el.attribute("user-desc").getValue().split(",");
                    } else {
                        arrDesc = arr;
                    }
					for(int i =0;i<arr.length;i++){
						JSONObject obj = new JSONObject();
						obj.put("id", arr[i]);
						obj.put("text", arrDesc[i]);
						jsonArr.add(obj);
					}
					attrObj.put("id", "appType-chooseUser");
					attrObj.put("value", jsonArr);
					attrObj.put("type", "Select");
					appType = "user";
				}else if("candidate-branch".equals(attr.getName())){
					JSONObject obj = new JSONObject();
					obj.put("id", attr.getValue());
					attrObj.put("id", "appType-chooseBr");
					attrObj.put("value", obj);
					attrObj.put("type", "Tree");
				}else if("candidate-level".equals(attr.getName())){
					attrObj.put("id", appType+"-candidate-level");
					attrObj.put("value", attr.getValue());
					attrObj.put("type", "Radio");
				}else if("strategy-type".equals(attr.getName())){
					attrObj.put("id", appType+"-strategy-type");
					attrObj.put("value", attr.getValue());
					attrObj.put("type", "Radio");
				}else if("strategy-value".equals(attr.getName())){
					attrObj.put("id", "strategy-value");
					attrObj.put("value", attr.getValue().replaceAll("%", ""));
					attrObj.put("type", "String");
				}else if("g".equals(attr.getName())){
					String[] arr = attr.getValue().split(",");
					task.put("left", Double.parseDouble(arr[0]));
					task.put("top", Double.parseDouble(arr[1]));
				}else if("name".equals(attr.getName())){
					attrObj.put("id", "base"+attr.getName());
					attrObj.put("value", attr.getValue());
					attrObj.put("type", "String");
					taskId = attr.getValue();
				}else if("description".equals(attr.getName())){
					attrObj.put("id", "base"+attr.getName());
					attrObj.put("value", attr.getValue());
					attrObj.put("type", "String");
					task.put("name", attr.getValue());
				}else if("signnotpass".equals(attr.getName())){
					attrObj.put("id", "normal-"+attr.getName());
					attrObj.put("value", attr.getValue());
					attrObj.put("type", "Radio");
				}else if("defaultRollbackNode".equals(attr.getName())){
					attrObj.put("id",attr.getName());
					attrObj.put("type","Choice");
					JSONObject rb = new JSONObject();
					rb.put("id", attr.getValue());
					attrObj.put("value",rb);
				}else {
				}
				if(!attrObj.isEmpty()) {
                    propData.put(attrObj.getString("id"), attrObj);
                }
			}
			
			resForm(propData, el);
			resVars(propData, el);
			resApprove(propData, el);
			resEvent(propData, el);
			resLine(linesData, el);
			task.put("properties", propData);
			task.put("alt", true);
			task.put("type", "node"+" round");
			nodeData.put(taskId, task);
		}
	}
	/**
	 * 解析判定节点
	 * @param obj
	 * @param node
	 */
	private void resDecision(JSONObject nodeData,Element node, String nodeType){
		List<Element> nodeList = node.elements(nodeType);
		for(Iterator<Element> it = nodeList.iterator(); it.hasNext();) {    
			Element el = (Element) it.next();
			List<Attribute> list = el.attributes();
			JSONObject decision = new JSONObject();
			JSONObject propData = new JSONObject();
			String decisionId = null;
			for (Attribute attr : list) {
				JSONObject attrObj = new JSONObject();
				attrObj.put("id", attr.getName());
				attrObj.put("value", attr.getValue());
				attrObj.put("type", "String");
				if("name".equals(attr.getName())){
					decisionId = attr.getValue();
					propData.put("basename", attrObj);
				}else if("description".equals(attr.getName())){
					decision.put("name", attr.getValue());
					propData.put("basedescription", attrObj);
				}else if("g".equals(attr.getName())){
					String[] arr = attr.getValue().split(",");
					decision.put("left", Double.parseDouble(arr[0]));
					decision.put("top", Double.parseDouble(arr[1]));
				}else{
					propData.put(attrObj.getString("id"), attrObj);
				}
				
			}
			Element handler = el.element("handler");
			if(handler!=null){
				JSONObject attrObj = new JSONObject();
				attrObj.put("id", "handler");
				attrObj.put("value", handler.attribute("class").getValue());
				attrObj.put("type", "String");
				propData.put("handler", attrObj);
			}
			resLine(linesData, el);
			decision.put("properties", propData);
			decision.put("alt", true);
			decision.put("type", "fork round");
			nodeData.put(decisionId, decision);
		}
	}
	/**
	 * 解析循环节点
	 * @param obj
	 * @param node
	 */
	private void resForeach(JSONObject nodeData,Element node, String nodeType){
		List<Element> nodeList = node.elements(nodeType);
		for(Iterator<Element> it = nodeList.iterator(); it.hasNext();) {    
			Element el = (Element) it.next();
			List<Attribute> list = el.attributes();
			JSONObject foreach = new JSONObject();
			JSONObject propData = new JSONObject();
			String foreachId = null;
			for (Attribute attr : list) {
				JSONObject attrObj = new JSONObject();
				attrObj.put("id", attr.getName());
				attrObj.put("value", attr.getValue());
				attrObj.put("type", "String");
				if("name".equals(attr.getName())){
					foreachId = attr.getValue();
					propData.put("basename", attrObj);
				}else if("description".equals(attr.getName())){
					foreach.put("name", attr.getValue());
					propData.put("basedescription", attrObj);
				}else if("g".equals(attr.getName())){
					String[] arr = attr.getValue().split(",");
					foreach.put("left", Double.parseDouble(arr[0]));
					foreach.put("top", Double.parseDouble(arr[1]));
				}else{
					propData.put(attrObj.getString("id"), attrObj);
				}
				
			}
			resEvent(propData, el);
			resLine(linesData, el);
			foreach.put("properties", propData);
			foreach.put("alt", true);
			foreach.put("type", nodeType+" round");
			nodeData.put(foreachId, foreach);
		}
	}
	/**
	 * 解析分支节点
	 * @param obj
	 * @param node
	 */
	private void resFork(JSONObject nodeData,Element node, String nodeType){
		List<Element> nodeList = node.elements(nodeType);
		for(Iterator<Element> it = nodeList.iterator(); it.hasNext();) {    
			Element el = (Element) it.next();
			List<Attribute> list = el.attributes();
			JSONObject fork = new JSONObject();
			JSONObject propData = new JSONObject();
			String forkId = null;
			for (Attribute attr : list) {
				JSONObject attrObj = new JSONObject();
				attrObj.put("id", attr.getName());
				attrObj.put("value", attr.getValue());
				attrObj.put("type", "String");
				if("name".equals(attr.getName())){
					forkId = attr.getValue();
					propData.put("basename", attrObj);
				}else if("description".equals(attr.getName())){
					fork.put("name", attr.getValue());
					propData.put("basedescription", attrObj);
				}else if("g".equals(attr.getName())){
					String[] arr = attr.getValue().split(",");
					fork.put("left", Double.parseDouble(arr[0]));
					fork.put("top", Double.parseDouble(arr[1]));
				}else{
					propData.put(attrObj.getString("id"), attrObj);
				}
				
			}
			resEvent(propData, el);
			resLine(linesData, el);
			fork.put("properties", propData);
			fork.put("alt", true);
			fork.put("type", "decision round");
			nodeData.put(forkId, fork);
		}
	}
	/**
	 * 解析聚合节点
	 * @param obj
	 * @param node
	 */
	private void resJoin(JSONObject nodeData,Element node, String nodeType){
		List<Element> nodeList = node.elements(nodeType);
		for(Iterator<Element> it = nodeList.iterator(); it.hasNext();) {    
			Element el = (Element) it.next();
			List<Attribute> list = el.attributes();
			JSONObject join = new JSONObject();
			JSONObject propData = new JSONObject();
			String joinId = null;
			for (Attribute attr : list) {
				JSONObject attrObj = new JSONObject();
				attrObj.put("id", attr.getName());
				attrObj.put("value", attr.getValue());
				attrObj.put("type", "String");
				if("name".equals(attr.getName())){
					joinId = attr.getValue();
					propData.put("basename", attrObj);
				}else if("description".equals(attr.getName())){
					join.put("name", attr.getValue());
					propData.put("basedescription", attrObj);
				}else if("g".equals(attr.getName())){
					String[] arr = attr.getValue().split(",");
					join.put("left", Double.parseDouble(arr[0]));
					join.put("top", Double.parseDouble(arr[1]));
				}else{
					propData.put(attrObj.getString("id"), attrObj);
				}
				
			}
			resEvent(propData, el);
			resLine(linesData, el);
			join.put("properties", propData);
			join.put("alt", true);
			join.put("type", nodeType+" round");
			nodeData.put(joinId, join);
		}
	}
	
	
	/**
	 * 解析事件节点
	 * @param obj
	 * @param node
	 */
	private void resEvent(JSONObject obj,Element node){
		List<Element> nodeList = node.elements("on");
		for(Iterator<Element> it = nodeList.iterator(); it.hasNext();) {    
			Element el = (Element) it.next();
			String onType = el.attribute("event").getValue();
			Element elm=el.element("event-listener");
			JSONObject attrObj = new JSONObject();
			attrObj.put("id", onType);
			attrObj.put("type", "Text");
			attrObj.put("value", elm.attribute("class").getValue());
			obj.put(onType, attrObj);
		}
		List<Element> noticeListeners = node.elements("notice-listener");
		for(Iterator<Element> it = noticeListeners.iterator(); it.hasNext();) {    
			Element el = (Element) it.next();
			String nlv = el.attribute("class").getValue();
			JSONObject attrObj = new JSONObject();
			attrObj.put("id", "notice-listener");
			attrObj.put("type", "Text");
			attrObj.put("value", nlv);
			obj.put("notice-listener", attrObj);
		}
		List<Element> appListeners = node.elements("app-listener");
		for(Iterator<Element> it = appListeners.iterator(); it.hasNext();) {    
			Element el = (Element) it.next();
			String alv = el.attribute("class").getValue();
			JSONObject attrObj = new JSONObject();
			attrObj.put("id", "app-listener");
			attrObj.put("type", "Text");
			attrObj.put("value", alv);
			obj.put("app-listener", attrObj);
		}
	}
	/**
	 * 解析流程变量节点
	 * @param obj
	 * @param node
	 */
	private void resVars(JSONObject obj,Element node){
		Element vars = node.element("vars");
		if(vars==null) {
            return;
        }
		List<Element> varList = vars.elements("var");
		JSONArray varArr = new JSONArray();
		for(Iterator<Element> it = varList.iterator(); it.hasNext();) {    
			Element el = (Element) it.next();
			List<Attribute> list = el.attributes();
			JSONArray elArr = new JSONArray();
			for (Attribute attr : list) { 
				JSONObject attrObj = new JSONObject();
				attrObj.put("id",attr.getName());
				attrObj.put("name",attr.getValue());
				if("type".equals(attr.getName())){
					attrObj.put("type", "Choice");
					attrObj.put("value", attr.getValue());
				}else{
					attrObj.put("type", "String");
					attrObj.put("value", "");
				}
				elArr.add(attrObj);
	        } 
			varArr.add(elArr);
		}
		JSONObject varObj = new JSONObject();
		varObj.put("id", vars.getName());
		varObj.put("type", "Form");
		varObj.put("value", varArr);
		obj.put("vars", varObj);
	}
	/**
	 * 解析流程审批节点
	 * @param obj
	 * @param node
	 */
	private void resApprove(JSONObject obj,Element node){
		Element approve = node.element("approve");
		if(approve==null) {
            return;
        }
		List<Attribute> list = approve.attributes();
		for (Attribute attr : list) { 
			JSONObject attrObj = new JSONObject();
			if("url".equals(attr.getName())){
				attrObj.put("id","approve");
				attrObj.put("type","Text");
				attrObj.put("value",attr.getValue());
			}else if("couldRollback".equals(attr.getName())||"viewhistory".equals(attr.getName())){
				attrObj.put("id","normal-"+attr.getName());
				attrObj.put("type","Radio");
				attrObj.put("value",attr.getValue());
			}else if("defaultRollbackNode".equals(attr.getName())){
				attrObj.put("id",attr.getName());
				attrObj.put("type","Choice");
				JSONObject rb = new JSONObject();
				rb.put("id", attr.getValue());
				attrObj.put("value",rb);
			}else if("time-type".equals(attr.getName())){
				attrObj.put("id",attr.getName());
				attrObj.put("type","Choice");
				JSONObject rb = new JSONObject();
				rb.put("id", attr.getValue());
				attrObj.put("value",rb);
			}else if("time-value".equals(attr.getName())){
				attrObj.put("id",attr.getName());
				attrObj.put("type","String");
				attrObj.put("value",attr.getValue());
			}else {
			}
			obj.put(attrObj.getString("id"), attrObj);
        }
	}
	/**
	 * 解析表单节点
	 * @param obj
	 * @param node
	 */
	private void resForm(JSONObject obj,Element node){
		Element forms = node.element("forms");
		if(forms==null) {
            return;
        }
		List<Element> formList = forms.elements("form");
		JSONArray varArr = new JSONArray();
		for(Iterator<Element> it = formList.iterator(); it.hasNext();) {    
			Element el = (Element) it.next();
			List<Attribute> list = el.attributes();
			JSONArray elArr = new JSONArray();
			for (Attribute attr : list) { 
				JSONObject attrObj = new JSONObject();
				attrObj.put("id",attr.getName());
				attrObj.put("name",attr.getValue());
				attrObj.put("type", "String");
				attrObj.put("value", "");
				elArr.add(attrObj);
	        } 
			varArr.add(elArr);
		}
		JSONObject varObj = new JSONObject();
		varObj.put("id", forms.getName());
		varObj.put("type", "Form");
		varObj.put("value", varArr);
		obj.put(forms.getName(), varObj);
	}
	/**
	 * 解析推送节点
	 * @param obj
	 * @param node
	 */
	private void resCopyTo(JSONObject obj,Element node){
		Element copyto = node.element("copyto");
		if(copyto==null) {
            return;
        }
		List<Attribute> list = copyto.attributes();
		for (Attribute attr : list) { 
			JSONObject attrObj = new JSONObject();
			String value="";
			if("user".equals(attr.getName())){
				attrObj.put("id","copytoType-chooseUser");
				attrObj.put("type","Select");
				value = attr.getValue();
				String[] arr = value.split(",");
				String[] arrDesc;
				if(copyto.attribute("user-desc")!=null) {
                    arrDesc = copyto.attribute("user-desc").getValue().split(",");
                } else {
                    arrDesc = arr;
                }
				JSONArray va = new JSONArray();
				for(int i=0;i<arr.length;i++){
					JSONObject vo = new JSONObject();
					vo.put("id", arr[i]);
					vo.put("text", arrDesc[i]);
					va.add(vo);
				}
				attrObj.put("value",va);
				obj.put(attrObj.getString("id"), attrObj);
			}else if("group".equals(attr.getName())){
				attrObj.put("id","copytoType-chooseRole");
				attrObj.put("type","Select");
				value = attr.getValue();
				String[] arr = value.split(",");
				String[] arrDesc;
				if(copyto.attribute("groups-desc")!=null) {
                    arrDesc = copyto.attribute("groups-desc").getValue().split(",");
                } else {
                    arrDesc = arr;
                }
				JSONArray va = new JSONArray();
				for(int i=0;i<arr.length;i++){
					JSONObject vo = new JSONObject();
					vo.put("id", arr[i]);
					vo.put("text", arrDesc[i]);
					va.add(vo);
				}
				attrObj.put("value",va);
				obj.put(attrObj.getString("id"), attrObj);
			}else if("branch".equals(attr.getName())){
				attrObj.put("id","copytoType-chooseBr");
				attrObj.put("type","Tree");
				JSONObject vo = new JSONObject();
				vo.put("id", attr.getValue());
				attrObj.put("value",vo);
				obj.put(attrObj.getString("id"), attrObj);
			}else {
			}
        } 
	}
	/**
	 * 解析连线
	 * @param obj
	 * @param node
	 */
	private void resLine(JSONObject lineData,Element node){
		JSONObject line = new JSONObject();
		JSONObject propData = new JSONObject();
//		JSONObject propData = obj.getJSONObject("properties");
		List<Element> transitionList = node.elements("transition");
		if(transitionList.size()==0) {
            return;
        }
		for(Element transition : transitionList){
			String lineId = null;
			List<Attribute> list = transition.attributes();
			for (Attribute attr : list) { 
				JSONObject attrObj = new JSONObject();
				if("id".equals(attr.getName())){
					lineId = attr.getValue();
					attrObj.put("id","overrideid");
					attrObj.put("type","String");
					attrObj.put("value",lineId);
					propData.put(attrObj.getString("id"), attrObj);
				};
				if("name".equals(attr.getName())){
					attrObj.put("id","base"+attr.getName());
					attrObj.put("type","String");
					attrObj.put("value",attr.getValue());
					propData.put(attrObj.getString("id"), attrObj);
				}else if("description".equals(attr.getName())){
					attrObj.put("id","base"+attr.getName());
					attrObj.put("type","String");
					attrObj.put("value",attr.getValue());
					propData.put(attrObj.getString("id"), attrObj);
					line.put("name", attr.getValue());
				}else if("g".equals(attr.getName())){
					String[] arr = attr.getValue().split("\\},\\{");
					String[] arr2 = attr.getValue().split(":");
					if(arr.length>2){
						line.put("type", "tb");
					}else{
						line.put("type", "sl");
					}
					line.put("g", arr2[0]);
				}else if("type".equals(attr.getName())){
					line.put("type", attr.getValue());
				}else if("from".equals(attr.getName())||"to".equals(attr.getName())){
					line.put(attr.getName(), attr.getValue());
				}else if("M".equals(attr.getName())){
					line.put(attr.getName(), attr.getValue());
				}else {
				}
				line.put("alt", true);
	        } 
			Element condition = transition.element("condition");
			if(condition!=null){
				String expr = condition.attribute("expr").getValue();
				JSONObject ex = new JSONObject();
				ex.put("id", "expr");
				ex.put("type", "String");
				ex.put("value", expr);
				propData.put("expr", ex);
			}
			line.put("properties", propData);
			lineData.put(lineId, line);
		}
//		resEvent(propData, node);
	}
	
	public void doCreateXmlFile(String path,String processKey,Document document){
		OutputFormat format = new OutputFormat("    ",true);  
        format.setEncoding("UTF-8");//设置编码格式  
        XMLWriter xmlWriter;
        File f = new File(path);
        if (!f.exists()) {
        	f.mkdir();
        }
		try {
			xmlWriter = new XMLWriter(new FileOutputStream(path+"/"+processKey+".wfdl.xml"),format);
			xmlWriter.write(document);  
	        xmlWriter.close();  
		} catch (Exception e) {
			e.printStackTrace();
		}  
	}
	public void doDownLoad(String path, String name,
	         HttpServletResponse response) {
		BufferedInputStream bis = null;
		InputStream fis = null;
	     try {
	         response.reset();
	         response.setHeader("Content-disposition","attachment;success=true;filename ="+ new String( name.getBytes("gb2312"), "ISO8859-1" ));
	         bis = null;
	         BufferedOutputStream bos = null;
	         OutputStream fos = null;
	         fis = null;
	         File uploadFile = new File(path);
	         fis = new FileInputStream(uploadFile);
	         bis = new BufferedInputStream(fis);
	         fos = response.getOutputStream();
	         bos = new BufferedOutputStream(fos);
	         // 弹出下载对话框
	         int bytesRead = 0;
	         byte[] buffer = new byte[8192];
	         while ((bytesRead = bis.read(buffer, 0, 8192)) != -1) {
	             bos.write(buffer, 0, bytesRead);
	         }
	         bos.flush();
	         //fis.close();
	         //bis.close();
	         fos.close();
	         bos.close();
	     } catch (Exception e) {
	         e.printStackTrace();
	     }finally {
	     	if (fis != null){
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			 if (bis != null){
				 try {
					 bis.close();
				 } catch (IOException e) {
					 e.printStackTrace();
				 }
			 }
		 }
	 } 
	
	public JSONObject DOM4JResBizXML(File file) throws Exception{
		JSONObject data = new JSONObject();
		SAXReader reader = new SAXReader();
		Element root = reader.read(file).getRootElement();
		data.put("approve", Dom4jXML.resBizXML("approve", root));
		data.put("start", Dom4jXML.resBizXML("start", root));
		data.put("end", Dom4jXML.resBizXML("end", root));
		data.put("notice-listener", Dom4jXML.resBizXML("notice-listener", root));
		data.put("app-listener", Dom4jXML.resBizXML("app-listener", root));
		data.put("expr", Dom4jXML.resBizXML("expr", root));
		data.put("handler", Dom4jXML.resBizXML("handler", root));
		return data;
	}
	
	private static JSONArray resBizXML(String type,Element root){
		Element end = root.element(type);
		JSONArray arr = new JSONArray();
		List<Element> propertyList = end.elements("property");
		for(Iterator<Element> it = propertyList.iterator(); it.hasNext();) {    
			Element el = (Element) it.next();
			String description = el.attribute("description").getValue();
			String value = el.attribute("value").getValue();
			JSONObject obj = new JSONObject();
			obj.put("text", description);
			obj.put("id", value);
			arr.add(obj);
		}
		return arr;
	}
	
	private void createBizXML(String type,Map<String,String> map){
		try{
			String filePath = getClass().getResource("/workflow.biz.cfg.xml").getPath();
			File file = new File(filePath); 
			SAXReader reader = new SAXReader();
			reader.setEncoding("UTF-8");
			Document doc = reader.read(file);
			Element root = doc.getRootElement();
			Element node = root.element(type);
			Element property = node.addElement("property");
			for(String key : map.keySet()){
				property.addAttribute(key, map.get(key));
			}
            OutputFormat format = OutputFormat.createPrettyPrint();
            format.setEncoding("UTF-8");
            XMLWriter newWriter = new XMLWriter(new FileOutputStream(filePath),format);
            newWriter.write(doc);
            newWriter.close();
        }catch(Exception e){
            e.printStackTrace();
        }
	}
}