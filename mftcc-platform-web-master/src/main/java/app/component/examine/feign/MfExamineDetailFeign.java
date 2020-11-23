package  app.component.examine.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.examine.entity.MfExamineDetail;
import app.util.toolkit.Ipage;

/**
* Title: MfExamineDetailBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Wed Feb 15 08:40:52 CST 2017
**/
@FeignClient("mftcc-platform-factor")
public interface MfExamineDetailFeign {
	@RequestMapping("/MfExamineDetail/insert")
	public void insert(@RequestBody MfExamineDetail mfExamineDetail) throws ServiceException;
	/**
	 * 
	 * 方法描述： 插入检查情况详情数据
	 * @param mfExamineDetail
	 * @throws ServiceException
	 * void
	 * @author 沈浩兵
	 * @date 2017-2-15 上午8:57:21
	 */
	@RequestMapping("/MfExamineDetail/insertDetail")
	public void insertDetail(@RequestBody Map<String,Object> dataMap) throws ServiceException;
	@RequestMapping("/MfExamineDetail/delete")
	public void delete(@RequestBody MfExamineDetail mfExamineDetail) throws ServiceException;
	@RequestMapping("/MfExamineDetail/update")
	public void update(@RequestBody MfExamineDetail mfExamineDetail) throws ServiceException;
	@RequestMapping("/MfExamineDetail/getById")
	public MfExamineDetail getById(@RequestBody MfExamineDetail mfExamineDetail) throws ServiceException;
	@RequestMapping("/MfExamineDetail/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("mfExamineDetail") MfExamineDetail mfExamineDetail) throws ServiceException;
	/**
	 * 
	 * 方法描述： 获得检查情况详情信息
	 * @param mfExamineDetail
	 * @return
	 * @throws ServiceException
	 * List<MfExamineDetail>
	 * @author 沈浩兵
	 * @date 2017-2-15 上午10:33:21
	 */
	@RequestMapping("/MfExamineDetail/getExamDetailList")
	public List<MfExamineDetail> getExamDetailList(@RequestBody MfExamineDetail mfExamineDetail) throws ServiceException;
	/**
	 * 
	 * 方法描述： 获得检查情况详情信息
	 * @param mfExamineDetail
	 * @return
	 * @throws ServiceException
	 * Map<String,Object>
	 * @author 沈浩兵
	 * @date 2017-2-15 下午8:47:55
	 */
	@RequestMapping("/MfExamineDetail/getExamDetailMap")
	public Map<String,Object> getExamDetailMap(@RequestBody MfExamineDetail mfExamineDetail) throws ServiceException;

	/**
	 * @Description 获取贷后检查登记列表数据
	 * @Author zhaomingguang
	 * @DateTime 2019/9/25 9:59
	 * @Param
	 * @return
	 */
	@RequestMapping(value = "/mfExamineDetail/findExamineRecordByPage")
	Ipage findExamineRecordByPage(Ipage ipage)throws Exception;

	/**
	 * @Description 贷后检查情况新增信息保存
	 * @Author zhaomingguang
	 * @DateTime 2019/9/26 15:51
	 * @Param 
	 * @return 
	 */
	@RequestMapping(value = "/mfExamineDetail/insertForExamineDetailAjax")
    Map<String,Object> insertForExamineDetailAjax(Map<String,Object> paraMap)throws Exception;

	/**
	 * @Description 获取贷后检查详情数据
	 * @Author zhaomingguang
	 * @DateTime 2019/9/26 18:57
	 * @Param 
	 * @return 
	 */
	@RequestMapping(value = "/mfExamineDetail/findExamineDetailByAjax")
	Ipage findExamineDetailByAjax(Ipage ipage)throws Exception;
}
