package app.util;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.core.util.oscache.ScreenBaseCache;
import com.core.util.oscache.ScreenRedisUtil;

import app.component.nmd.entity.ParmDic;
import app.component.sys.entity.SysMsgConfig;

public class CodeUtils {
	public Map<String, String> getMapByKeyName(String key) throws Exception {
		List<ParmDic> pdl = (List<ParmDic>)JSONArray.fromObject(ScreenRedisUtil.getCollection(key));
		Map<String, String> map = new HashMap<String, String>();
		for (ParmDic pd : pdl) {
			map.put(pd.getOptCode(), pd.getOptName());
		}
		return map;
	}
	
	public static String getMsgConfigByKey(String key) throws Exception {
		SysMsgConfig smc = (SysMsgConfig)JSONObject.toBean(JSONObject.fromObject(ScreenRedisUtil.getCollection(key)),SysMsgConfig.class);
		return smc.getMsgContent();
	}
	
	public Object getCacheByKeyName(String key) throws Exception {
		return ScreenBaseCache.getParamDicInstance().get(key);
	}
}
