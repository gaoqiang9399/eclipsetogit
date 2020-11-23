package app.component.sys.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.sys.entity.SysGlobalSearch;
import app.util.toolkit.Ipage;
@FeignClient("mftcc-platform-factor")
public interface SysGlobalSearchFeign {
	@RequestMapping("/sysGlobalSearch/insert")
	public void insert(@RequestBody SysGlobalSearch sysGlobalSearch) throws ServiceException;
	@RequestMapping("/sysGlobalSearch/delete")
	public void delete(@RequestBody SysGlobalSearch sysGlobalSearch) throws ServiceException;
	@RequestMapping("/sysGlobalSearch/update")
	public void update(@RequestBody SysGlobalSearch sysGlobalSearch) throws ServiceException;
	@RequestMapping("/sysGlobalSearch/getById")
	public SysGlobalSearch getById(@RequestBody SysGlobalSearch sysGlobalSearch) throws ServiceException;
	@RequestMapping("/sysGlobalSearch/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws ServiceException;
	@RequestMapping("/sysGlobalSearch/getAll")
	public List<SysGlobalSearch> getAll(@RequestBody SysGlobalSearch sysGlobalSearch) throws ServiceException;
	@RequestMapping("/sysGlobalSearch/globalSearch")
	public List<Object> globalSearch(@RequestBody SysGlobalSearch sysGlobalSearch) throws ServiceException;
	@RequestMapping("/sysGlobalSearch/updateSts")
	public int updateSts(@RequestBody SysGlobalSearch sysGlobalSearch) throws ServiceException;
	@RequestMapping("/sysGlobalSearch/getIsUseList")
	public List<SysGlobalSearch> getIsUseList() throws ServiceException;
	
	/***
	 * 通过表名查数据
	 * @param tableName
	 * @param tableColumn
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping("/sysGlobalSearch/getTableByName")
	public List getTableByName(@RequestParam("tableName")String tableName, @RequestParam("tableColumn")String tableColumn) throws ServiceException;
	
	/***
	 * 查询表的记录行数
	 * @param tableName
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping("/sysGlobalSearch/getTableCount")
	public int getTableCount(@RequestParam("tableName")String tableName) throws ServiceException;
	
	/***
	 * 分段查询表数据(默认8000条)
	 * @param tableName
	 * @param tableColumn
	 * @param start
	 * @param count
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping("/sysGlobalSearch/getTableByRange")
	public List<Object> getTableByRange(@RequestParam("tableName")String tableName,@RequestParam("tableColumn")String tableColumn,
			@RequestParam("start")int start,  @RequestParam("count")int count) throws ServiceException;
	
	/***
	 * 根据主键查表记录
	 * @param tableName
	 * @param prmKeyDesc
	 * @param prmKey
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping("/sysGlobalSearch/getRecordByNo")
	public Object getRecordByNo(@RequestParam("tableName")String tableName, @RequestParam("prmKeyDesc")String prmKeyDesc, @RequestParam("prmKey")String prmKey) throws ServiceException;

}
