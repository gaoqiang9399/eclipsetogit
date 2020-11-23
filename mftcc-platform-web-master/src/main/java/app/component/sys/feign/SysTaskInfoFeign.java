package app.component.sys.feign;

import java.util.List;
import java.util.Map;

import app.component.sys.entity.SysTaskInfoIgnore;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.sys.entity.SysTaskInfo;
import app.util.toolkit.Ipage;

@FeignClient("mftcc-platform-factor")
public interface SysTaskInfoFeign {
	@RequestMapping("/sysTaskInfo/getCountByUser/{regNo}")
	public int getCountByUser(@PathVariable("regNo")String regNo);
	
	@RequestMapping("/sysTaskInfo/insert")
	public void insert(@RequestBody SysTaskInfo sysTaskInfo) throws ServiceException;
	
	@RequestMapping("/sysTaskInfo/getPasMinTypeCount")
	public List<SysTaskInfo> getPasMinTypeCount(@RequestBody SysTaskInfo sysTaskInfo ) throws ServiceException;
	
	@RequestMapping("/sysTaskInfo/delete")
	public void delete(@RequestBody SysTaskInfo sysTaskInfo) throws ServiceException;
	
	@RequestMapping("/sysTaskInfo/deleteByFilter")
	public void deleteByFilter(@RequestBody SysTaskInfo sysTaskInfo) throws ServiceException;
	
	@RequestMapping("/sysTaskInfo/update")
	public void update(@RequestBody SysTaskInfo sysTaskInfo) throws ServiceException;
	
	@RequestMapping("/sysTaskInfo/updateByFilter")
	public void updateByFilter(@RequestBody SysTaskInfo sysTaskInfo) throws ServiceException;
	
	@RequestMapping("/sysTaskInfo/updatePasContent")
	public void updatePasContent(@RequestBody SysTaskInfo sysTaskInfo) throws ServiceException;
	
	@RequestMapping("/sysTaskInfo/deleteByAppNoTaskId")
	public void deleteByAppNoTaskId(@RequestBody SysTaskInfo sysTaskInfo) throws ServiceException;
	
	@RequestMapping("/sysTaskInfo/deleteByAppNoTaskIdUserNo")
	public void deleteByAppNoTaskIdUserNo(@RequestBody  SysTaskInfo sysTaskInfo) throws ServiceException;
	/**
	 * 
	 */
	@RequestMapping("/sysTaskInfo/getNoReadNewsList")
	public List<SysTaskInfo>getNoReadNewsList(@RequestBody  SysTaskInfo sysTaskInfo) throws ServiceException;
	
	@RequestMapping("/sysTaskInfo/getById")
	public SysTaskInfo getById(@RequestBody SysTaskInfo sysTaskInfo) throws ServiceException;
	
	@RequestMapping("/sysTaskInfo/getByIdAndUserNo")
	public SysTaskInfo getByIdAndUserNo(@RequestBody SysTaskInfo sysTaskInfo) throws ServiceException;
	//Ipage ipage,SysTaskInfo sysTaskInfo
	@RequestMapping("/sysTaskInfo/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws ServiceException;
	
	@RequestMapping("/sysTaskInfo/findByPageSysTaskCut")
	public int findByPageSysTaskCut(@RequestBody SysTaskInfo sysTaskInfo) throws ServiceException;
	
	@RequestMapping("/sysTaskInfo/getAllForWechat")
	public List<SysTaskInfo> getAllForWechat(@RequestBody SysTaskInfo sysTaskInfo) throws ServiceException;
	
	@RequestMapping("/sysTaskInfo/getAllTask")
	public List<SysTaskInfo> getAllTask(@RequestBody SysTaskInfo sysTaskInfo) throws ServiceException;
	
	@RequestMapping("/sysTaskInfo/getByAppNoTaskId")
	public List<SysTaskInfo> getByAppNoTaskId(@RequestBody SysTaskInfo sysTaskInfo) throws ServiceException;
	
	@RequestMapping("/sysTaskInfo/getPasMaxTypeCount")
	public List<SysTaskInfo> getPasMaxTypeCount(@RequestBody SysTaskInfo sysTaskInfo ) throws ServiceException;
	
	@RequestMapping("/sysTaskInfo/getCount")
	public int getCount(@RequestBody SysTaskInfo sysTaskInfo ) throws ServiceException;
	
	@RequestMapping("/sysTaskInfo/findByFilter")
	public List<SysTaskInfo> findByFilter(@RequestBody SysTaskInfo sysTaskInfo ) throws ServiceException;
	
	@RequestMapping("/sysTaskInfo/getPasMaxCount")
	public SysTaskInfo getPasMaxCount(@RequestBody SysTaskInfo sysTaskInfo ) throws ServiceException;
	
	@RequestMapping("/sysTaskInfo/getPasAwareCount")
	public String getPasAwareCount(@RequestBody SysTaskInfo sysTaskInfo ) throws ServiceException;
	
	@RequestMapping("/sysTaskInfo/getByAppNo")
	public SysTaskInfo getByAppNo(@RequestBody SysTaskInfo sysTaskInfo) throws ServiceException;
	
	@RequestMapping("/sysTaskInfo/getByAppNoUser")
	public SysTaskInfo getByAppNoUser(@RequestBody SysTaskInfo sysTaskInfo) throws ServiceException;
	
	@RequestMapping("/sysTaskInfo/deleteByAppNoUser")
	public void deleteByAppNoUser(@RequestBody SysTaskInfo sysTaskInfo) throws ServiceException;
	
	@RequestMapping("/sysTaskInfo/getByRecUser")
	public SysTaskInfo getByRecUser(@RequestBody SysTaskInfo sysTaskInfo) throws ServiceException;

	
	/**
	 * 会签中一票否决下，当某一会签人选择不同意时，删除其他未操作的会签人的审批消息
	 * @param bizPk 业务主键
	 */
	@RequestMapping("/sysTaskInfo/deleteCountersignTask")
	public void deleteCountersignTask(@RequestParam("bizPk")String bizPk) throws ServiceException;
	
	@RequestMapping("/sysTaskInfo/deleteTaskByWkfTaskNo")
	public void deleteTaskByWkfTaskNo(@RequestParam("wkfTaskNo")String wkfTaskNo) throws ServiceException;
	
	@RequestMapping("/sysTaskInfo/getTaskIdByAppNo")
	public List<SysTaskInfo> getTaskIdByAppNo(@RequestBody SysTaskInfo sysTaskInfo) throws ServiceException;
	
	@RequestMapping("/sysTaskInfo/updatePasSts")
	public void updatePasSts(@RequestBody SysTaskInfo sysTaskInfo) throws ServiceException;
	
	@RequestMapping("/sysTaskInfo/updatePasSts4SignTask")
	public void updatePasSts4SignTask(@RequestBody Map map) throws ServiceException;
	
	@RequestMapping("/sysTaskInfo/updateAllSts")
	public void updateAllSts(@RequestBody Map map) throws ServiceException;
	
	@RequestMapping("/sysTaskInfo/updateByAppNoTaskIdUserNo")
	public void updateByAppNoTaskIdUserNo(@RequestBody SysTaskInfo sysTaskInfo) throws ServiceException;
	/**
	 * 根据任务编号和接收任务的人查任务表,限有效的任务
	 * @param sysTaskInfo
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping("/sysTaskInfo/updateByAppNoTaskIdUserNo")
	public List<SysTaskInfo> getByTaskIdAndUser(@RequestBody SysTaskInfo sysTaskInfo)  throws ServiceException;
	
	/**
	 * 根据任务小类和接收任务人查所有的有效任务
	 * @param sti
	 * @return
	 */
	@RequestMapping("/sysTaskInfo/getByPasMinAndUser")
	public List<SysTaskInfo> getByPasMinAndUser(@RequestBody SysTaskInfo sti) throws ServiceException;
	/**
	 * 根据任务小类集合和用户号查询有效的任务
	 * @param selectBean
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping("/sysTaskInfo/getByUserAndPasMins")
	public List<SysTaskInfo> getByUserAndPasMins(@RequestBody SysTaskInfo selectBean) throws ServiceException;
	/**
	 * 根据接受任务的人userNo和任务功能编号pasMinNo，删除未处理的任务
	 * @param delBean
	 * @throws ServiceException
	 */
	@RequestMapping("/sysTaskInfo/deleteByPasMinAndUser")
	public void deleteByPasMinAndUser(@RequestBody SysTaskInfo delBean)  throws ServiceException;
	
	@RequestMapping("/sysTaskInfo/getSysTaskInfoList")
	public Ipage getSysTaskInfoList(@RequestBody Ipage ipage) throws ServiceException;
	
	/**
	 * 
	 * 方法描述： 根据操作员获得所有待审批的审批任务
	 * @param sysTaskInfo
	 * @return
	 * @throws Exception
	 * List<SysTaskInfo>
	 * @author 沈浩兵
	 * @date 2017-10-23 上午11:10:21
	 */
	@RequestMapping("/sysTaskInfo/getApproveTaskByUser")
	public List<SysTaskInfo> getApproveTaskByUser(@RequestBody SysTaskInfo sysTaskInfo) throws Exception;
	/**
	 * 
	 * 方法描述： 获得待办任务列表内容
	 * 包含各类任务数量、关注任务数量、任务分页列表
	 * @param ipage
	 * @param sysTaskInfo Ipage ipage,SysTaskInfo sysTaskInfo,String jsonData
	 * @return 
	 * @throws ServiceException
	 * Ipage
	 * @author 沈浩兵
	 * @date 2017-11-16 下午3:42:34
	 */
	@RequestMapping("/sysTaskInfo/findByPageToBByPage")
	public Map<String,Object> findByPageToBByPage(@RequestBody Ipage ipage) throws ServiceException;
	/**
	 * 
	 * 方法描述： 查询已否决的任务
	 * @param ipage
	 * @param sysTaskInfo
	 * @return
	 * @throws ServiceException
	 * Ipage
	 * @author 沈浩兵
	 * @date 2017-11-16 下午2:31:01
	 */
	@RequestMapping("/sysTaskInfo/findVetoTaskByPage")
	public Ipage findVetoTaskByPage(@RequestBody Ipage ipage) throws ServiceException;
	/**
	 * 
	 * 方法描述： 查询已通过的任务
	 * @param ipage
	 * @param sysTaskInfo
	 * @return
	 * @throws ServiceException
	 * Ipage
	 * @author 沈浩兵
	 * @date 2017-11-16 下午2:31:01
	 */
	@RequestMapping("/sysTaskInfo/findPassTaskByPage")
	public Ipage findPassTaskByPage(@RequestBody Ipage ipage) throws ServiceException;
	/**
	 * 
	 * 方法描述： 查询已否决的任务列表
	 * @param ipage
	 * @param sysTaskInfo
	 * @return
	 * @throws ServiceException
	 * Ipage
	 * @author 沈浩兵
	 * @date 2017-11-16 下午2:31:01
	 */
	@RequestMapping("/sysTaskInfo/findVetoTaskList")
	public List<SysTaskInfo> findVetoTaskList(@RequestBody SysTaskInfo sysTaskInfo) throws Exception;
	/**
	 * 
	 * 方法描述： 查询已通过的任务列表
	 * @param ipage
	 * @param sysTaskInfo
	 * @return
	 * @throws ServiceException
	 * Ipage
	 * @author 沈浩兵
	 * @date 2017-11-16 下午2:31:01
	 */
	@RequestMapping("/sysTaskInfo/findPassTaskList")
	public List<SysTaskInfo> findPassTaskList(@RequestBody SysTaskInfo sysTaskInfo) throws Exception;
	
	/**
	 * 方法描述： 任务锁单
	 * @param sysTaskInfo
	 * @return
	 * @throws Exception
	 * String
	 * @author Javelin
	 * @date 2017-12-8 下午5:46:33
	 */
	@RequestMapping("/sysTaskInfo/updataPasLockSts")
	public String updataPasLockSts(@RequestBody SysTaskInfo sysTaskInfo) throws Exception;
	
	@RequestMapping("/sysTaskInfo/getSysTaskInfo")
	public SysTaskInfo getSysTaskInfo(@RequestBody SysTaskInfo sysTaskInfo) throws Exception;

	/**
	 * @Description 贷后检查任务新增信息保存
	 * @Author zhaomingguang
	 * @DateTime 2019/9/21 14:10
	 * @Param
	 * @return
	 */
	@RequestMapping("/sysTaskInfo/insertForExamineTaskAjax")
	Map<String,Object> insertForExamineTaskAjax(Map<String,Object> paraMap)throws Exception;

	/**
	 * @Description 忽略贷后检查任务信息保存
	 * @Author zhaomingguang
	 * @DateTime 2019/9/21 17:36
	 * @Param 
	 * @return 
	 */
	@RequestMapping("/sysTaskInfo/insertIgnoreExamineTaskAjax")
    Map<String,Object> insertIgnoreExamineTaskAjax(Map<String,Object> paraMap)throws Exception;
	/**
	 * @Description 根据被动任务表中的主键获取该任务的忽略信息
	 * @Author zhaomingguang
	 * @DateTime 2019/9/24 20:02
	 * @Param 
	 * @return 
	 */
	@RequestMapping("/sysTaskInfo/getSysTaskInfoIgnore")
    SysTaskInfoIgnore getSysTaskInfoIgnore(String pasNo) throws Exception;
}
