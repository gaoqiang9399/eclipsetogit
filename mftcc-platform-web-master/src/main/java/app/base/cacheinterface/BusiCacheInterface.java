package app.base.cacheinterface;

import java.util.List;

import app.component.sys.entity.SysUser;


public interface BusiCacheInterface {
	
	/**
	 * 删库
	 * @throws Exception
	 */
	public void removeButton() throws Exception;
	
	public List<Object> getParmDic(String key) throws Exception;
	
	public void setSessionAttr(String key ,SysUser sysUser) throws Exception;
//	/**
//	 * 更新某个key的数据字典缓存
//	 * 
//	 * @throws Exception
//	 */
//	public void refreshParmDic(Object key) throws Exception;

	void delParmDic(Object key) throws Exception;

	void addParmDic(Object key, Object object) throws Exception;

	List<Object> getParmDicList(Object key) throws Exception;

	Object getParmDicObj(Object key) throws Exception;

	Object getPmsObj(Object key) throws Exception;

	void setParmDic(Object key, Object value) throws Exception;
	
	/**
	 *
	 * 方法描述： 根据key获取行业分类的list
	 * @param key
	 * @return
	 * @throws Exception
	 * List<NmdWay>
	 * @author zhs
	 * @date 2018年6月5日 上午11:03:53
	 */
	public List<Object> getNmdWayList(Object key) throws Exception;

}
