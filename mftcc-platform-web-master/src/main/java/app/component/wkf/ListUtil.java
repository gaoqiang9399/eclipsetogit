package app.component.wkf;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.dhcc.workflow.api.history.HistoryTask;
import com.dhcc.workflow.api.task.Task;
import com.dhcc.workflow.pvm.internal.history.model.HistoryTaskImpl;

/**
 * 
 *
 */
public class ListUtil {

	public static List<Map> changeList(List<Task> wkfTaskList) {
		if (wkfTaskList == null) {
			return new ArrayList<Map>();
		}
		List<Map> list = new ArrayList<Map>();
		for (int i = 0; i < wkfTaskList.size(); i++) {
			Task ts = (Task) wkfTaskList.get(i);
			Map obj = null;
			try {
				obj = transMapValue(ts);
			} catch (Exception e) {
				continue;
			}
			list.add(obj);
		}

		return list;
	}
	
	public static List<Map> changeHisList(List<HistoryTask> wkfTaskList) {
		if (wkfTaskList == null) {
			return new ArrayList<Map>();
		}
		List<Map> list = new ArrayList<Map>();
		for (int i = 0; i < wkfTaskList.size(); i++) {
			HistoryTaskImpl ts = (HistoryTaskImpl) wkfTaskList.get(i);
			Map obj = null;
			try {
				obj = transHisMapValue(ts);
			} catch (Exception e) {
				continue;
			}
			list.add(obj);
		}

		return list;
	}

	private static Map transMapValue(Task ts) {
		Map map = ts.getAppValueMap();
		map.put("id",ts.getId());
		map.put("description",ts.getDescription());
		map.put("appId",ts.getAppId());
		map.put("assignee",ts.getAssignee());
		map.put("createTime",ts.getCreateTimeValue());
		map.put("state",ts.getState());
		map.put("executionId",ts.getExecutionId());
		map.put("isApproved",ts.getIsApproved());
		return map;
	}
	
	private static Map transHisMapValue(HistoryTaskImpl ts) {
		Map map = ts.getAppValueMap();
		map.put("id",ts.getId());
		map.put("description",ts.getDescription());
		map.put("appId",ts.getAppId());
		map.put("assignee",ts.getAssignee());
		map.put("createTimeValue",ts.getCreateTimeValue());
		map.put("endTimeValue",ts.getEndTimeValue());
		map.put("state",ts.getState());
		map.put("executionId",ts.getExecutionId());
		map.put("formScript",ts.getFormScript());
		map.put("formMap",ts.getFormsMap());
		map.put("forms",ts.getForms());
		if(null!=ts.getForms()&&!"".equals(ts.getForms()))
		{
			String temp=ts.getForms().split(",")[2];
			String url=temp.split("'")[1];
			map.put("url",url+"&taskId="+ts.getId());
		}
		return map;
	}

	private static Object subObjectValue(Object object, Object subObject)
		throws Exception {
		Class clazz = object.getClass();
		Class clazz1 = subObject.getClass();
		Object obj = clazz1.newInstance();
		try {
			Method[] methods = clazz.getMethods();
			for (Method method : methods) {
				try {
					if (method.getName().startsWith("get") && !"getSerialVersionUID".equals(method.getName())) {
						Method m = clazz1.getMethod(method.getName().replaceFirst("get", "set"), method.getReturnType());
						if(m!=null){
							m.invoke(obj, method.invoke(object));
						}
					}
				} catch (Exception e) {
					continue;
				}
			}
		} catch (Exception e) {
			throw new Exception(e);
		}
		return obj;
	}

}
