package app.tech.oscache;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.component.common.BizPubParm;
import com.core.domain.screen.OptionsList;

import app.base.SpringUtil;
import app.base.cacheinterface.BusiCacheInterface;
import app.component.nmd.entity.ParmDic;
import app.component.wkf.AppConstant;
import app.component.wkfBus.bean.WkfBusNode;
import app.tech.wkf.feign.ResolveProcessUtilFeign;
import cn.mftcc.util.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

public class CodeUtils {

	private int pmsIndex;
	
	private int parmDicIndex;
	
	private BusiCacheInterface busiCacheInterface;
	

	public CodeUtils() {
		this.busiCacheInterface = SpringUtil.getBean(BusiCacheInterface.class);
		this.pmsIndex=15;
		this.parmDicIndex=5;
	}

	public List getCacheByKeyName(String key) throws Exception {
		return busiCacheInterface.getParmDicList(key);
	}
	
	public void setCacheList(Object key,Collection<Object> collection) throws Exception {
		busiCacheInterface.setParmDic(key, collection);
	}
	
	public void setCacheObj(Object key,Object object) throws Exception {
		busiCacheInterface.setParmDic(key, object);
	}
	
	public Object getBeanCache(String key) throws Exception {
		return busiCacheInterface.getParmDicObj(key);
	}
	
	public Object getObjCache4ParmDic(String key) throws Exception {
		return busiCacheInterface.getParmDicObj(key);
	}
	
	
	/**
	 * mybatis过滤器使用的获取cache的东西,使用完成后需要改回原来的db编号
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public Object getParmDicCache4MybitsInterceptors(String key) throws Exception {
		return busiCacheInterface.getParmDicObj(key);
	}
	
	public Object getObjCache4Pms(String key) throws Exception {
		Object result = busiCacheInterface.getPmsObj(key);
		return result;
	}
	
	public void removeByKey(String key) throws Exception {
		busiCacheInterface.delParmDic(key);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, String> getMapByKeyName(String key) throws Exception {
		List<ParmDic> pdl = (List<ParmDic>) getCacheByKeyName(key);
		Map<String, String> map = new HashMap<String, String>();
		if(pdl != null){
			for (ParmDic pd : pdl) {
				map.put(pd.getOptCode(), pd.getOptName());
			}
		}
		return map;
	}
	
	/**
	 * 
	 * 方法描述： 获得缓存中数据字典实体
	 * @param key
	 * @return
	 * @throws Exception
	 * Map<String,String>
	 * @author 沈浩兵
	 * @date 2017-3-21 下午1:47:33
	 */
	public Map<String, ParmDic> getMapObjByKeyName(String key) throws Exception {
		List<ParmDic> pdl = (List<ParmDic>) this.getCacheByKeyName(key);
		Map<String, ParmDic> map = new HashMap<String, ParmDic>();
		if(pdl != null){
			for (ParmDic pd : pdl) {
				map.put(pd.getOptCode(), pd);
			}
		}
		return map;
	}
	
	/**
	 * 获得数据字典的JsonArray形式。多用于前台的自定义筛选组件。
	 * @param keyName
	 * @return JSONArray
	 * @author LiuYF
	 * @throws Exception
	 */
	public JSONArray getJSONArrayByKeyName(String keyName) throws Exception {
		JSONArray jArray = new JSONArray();
		List<ParmDic> parmDicList = (List<ParmDic>) getCacheByKeyName(keyName);
		if (parmDicList != null) {
			for (ParmDic p : parmDicList) {
				JSONObject json = new JSONObject();
				json.put("optName", p.getOptName());
				json.put("optCode", p.getOptCode());
				jArray.add(json);
			}
		}
		return jArray;
		
	}

	/**
	 * 获取审批意见选择项列表。用于公共的为表单指定审批意见选项时使用。  e.g.:
	 * <pre>
	 * this.changeFormProperty(<i>formapply0003</i>, "opinionType", "optionArray", opinionTypeList);
	 * </pre>
	 * @param activityType 审批页面的全局属性，由工作流传递参数，通过框架赋值，传入到此方法中。
	 * @param taskCouldRollBack 从流程节点task中获取CouldRollBack属性。
	 * @param map 根据传过来的hideOpinionType值隐藏审批意见选项 ,多个值之间用@分隔
	 * @return List<OptionsList> 审批意见选项List。
	 * @author LiuYF
	 * @throws Exception 
	 * @since 2017-6-15 17:59:20
	 */
	public List<OptionsList> getOpinionTypeList(String activityType, String taskCouldRollBack, Map<String, String> map) throws Exception {
		if (map == null) {
			map = new HashMap<String, String>();
		}
		String opinionType = "OPINION_TYPE";
		if(map.containsKey("opinionType")){//意见类型支持自定义数据字典，默认是“OPINION_TYPE”
			opinionType = map.get("opinionType");
		}
		List<OptionsList> opinionTypeList = new ArrayList<OptionsList>();
		List<ParmDic> opinionTypes = (List<ParmDic>) getCacheByKeyName(opinionType);
		//隐藏审批意见选项
		String[] codeArray = null;
		if(map!=null){
			String hideCode = map.get("hideOpinionType");
			if(StringUtil.isNotEmpty(hideCode)){
				codeArray = hideCode.split("@");
			}
		}
		if(opinionTypes != null && opinionTypes.size()>0){
			for(ParmDic parmDic:opinionTypes){
				OptionsList s= new OptionsList();
				if ("signtask".equals(activityType)) {
					String reconsideration = getSingleValByKey("RECONSIDERATION");//审贷会是否支持复议0-否  1-是
					if(BizPubParm.YES_NO_N.equals(reconsideration)){//审贷会审批意见在审批流程url中配置需要隐藏的类型
						if(!AppConstant.OPINION_TYPE_ARREE.equals(parmDic.getOptCode())&&!AppConstant.OPINION_TYPE_DISARREE.equals(parmDic.getOptCode())){//1-同意 5-不同意
							continue;
						}
					}
				}else{
					if(!"Y".equals(taskCouldRollBack)){
						if(AppConstant.OPINION_TYPE_ROLLBACK.equals(parmDic.getOptCode())
								||AppConstant.OPINION_TYPE_RESTART.equals(parmDic.getOptCode())
								|| AppConstant.OPINION_TYPE_BACKUPDATE.equals(parmDic.getOptCode())
								|| AppConstant.OPINION_TYPE_CANCEL.equals(parmDic.getOptCode())){//3-退回上一环节 4-发回初审 6.1-驳回 6.2-取消
							continue;
						}
					}
					// 非会签节点过滤掉“不同意”类型。
					if (AppConstant.OPINION_TYPE_DISARREE.equals(parmDic.getOptCode())) {
						continue;
					}
				}
				//隐藏审批意见选项
				if(codeArray!=null && StringUtil.arrayContainsString(codeArray,parmDic.getOptCode())){
					continue;
				}
	
				String nodeNo = map.get("nodeNo");
				String processDefinitionId = map.get("processDefinitionId");
				if (StringUtil.isNotEmpty(processDefinitionId)) {
					ResolveProcessUtilFeign resolveProcessUtilFeign = (ResolveProcessUtilFeign) SpringUtil.getBean(ResolveProcessUtilFeign.class);
					// 获取此流程的所有节点
					JSONArray nodes = resolveProcessUtilFeign.getDeploymentById(processDefinitionId);
					List<WkfBusNode> nodeList = JSONArray.toList(nodes, new WkfBusNode(), new JsonConfig());
	
					Boolean isOk = true;
	
					// 退回补充资料
					isOk = isOk && supplementForOption(parmDic, nodeNo, nodeList);
	
					// 流程第一节点
					isOk = isOk && firstForOption(parmDic, nodeNo, nodeList);
	
					if (!isOk) {
						continue;
					}
				}
	
				s.setOptionLabel(parmDic.getOptName());
				s.setOptionValue(parmDic.getOptCode());
				opinionTypeList.add(s);
			}
		}
		return opinionTypeList;
	}
	/**
	 * 流程第一节点相关选项排除
	 * 
	 * @param parmDic
	 * @param nodeNo
	 * @param nodeList
	 * @return true需要此选项(不排除), false不需要此选项(需排除)
	 * @throws Exception
	 * @author WangChao
	 * @date 2017-12-25 上午9:34:42
	 */
	private Boolean firstForOption(ParmDic parmDic, String nodeNo, List<WkfBusNode> nodeList) throws Exception {
		Boolean result = true;

		for (WkfBusNode node : nodeList) {
			if (node.getNextName().equals(nodeNo) && ("start".equals(node.getId()) || "supplement_data".equals(node.getId()) || "pact_supplement_data".equals(node.getId()) || "finc_supplement_data".equals(node.getId())|| "pledge_supplement_data".equals(node.getId()))) {// 第一节点
				if ("3".equals(parmDic.getOptCode()) || "4".equals(parmDic.getOptCode())) {// 3退回上一环节, 4发回重审
					result = false;
				}
			}
		}

		return result;
	}
	
	/**
	 * 退回补充资料相关选项排除
	 * @param parmDic
	 * @param nodeNo
	 * @param nodeList
	 * @return true需要此选项(不排除), false不需要此选项(需排除)
	 * @throws Exception
	 * @author WangChao
	 * @date 2017-12-22 下午5:20:35
	 */
	private Boolean supplementForOption(ParmDic parmDic, String nodeNo, List<WkfBusNode> nodeList) throws Exception {
		Boolean result = true;

		if ("6".equals(parmDic.getOptCode())) {// 6退回补充资料
			result = false;// 退回补充资料选项默认不添加
			// 循环判断是否存在退回补充资料节点
			for (WkfBusNode node : nodeList) {
				if ("supplement_data".equals(node.getId()) || "pact_supplement_data".equals(node.getId())
						|| "finc_supplement_data".equals(node.getId())|| "pledge_supplement_data".equals(node.getId())
						|| node.getId().startsWith("credit_supplement") || "reviewMeetingSecretary".equals(node.getId())) {
					result = true;// 当有退回补充资料节点时才添加补充资料选项
					break;
				}
			}
		}
// 当前是补充资料节点, 下面意见项不添加。授信中补充资料节点都是以开头credit_supplement
		if ("supplement_data".equals(nodeNo) || "pact_supplement_data".equals(nodeNo)
				|| "finc_supplement_data".equals(nodeNo)|| "pledge_supplement_data".equals(nodeNo)
				|| nodeNo.startsWith("credit_supplement") || "reviewMeetingSecretary".equals(nodeNo)) {
			if ("3".equals(parmDic.getOptCode()) || "4".equals(parmDic.getOptCode()) || "6".equals(parmDic.getOptCode())) {// 3退回上一环节, 4发回重审, 6退回补充资料
				result = false;
			}
		}

		return result;
	}
	
	
	public String getSingleValByKey(String key) {
		List<ParmDic> pdl;
		try {
			pdl = (List<ParmDic>) getCacheByKeyName(key);
			if (pdl != null && pdl.size() > 0) {
				return pdl.get(0).getOptCode();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
