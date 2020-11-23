package app.component.oa.filesign.feign;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.oa.filesign.entity.MfOaFileCountersign;
import app.component.wkf.entity.Result;
import app.util.toolkit.Ipage;

/**
 * Title: MfOaFileCountersignBo.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Mon Jun 12 16:40:24 CST 2017
 **/
@FeignClient("mftcc-platform-factor")
public interface MfOaFileCountersignFeign {
	@RequestMapping(value = "/mfOaFileCountersign/insert",produces = "text/html;charset=UTF-8")
	public String insert(@RequestBody MfOaFileCountersign mfOaFileCountersign) throws ServiceException;

	@RequestMapping(value = "/mfOaFileCountersign/delete")
	public void delete(@RequestBody MfOaFileCountersign mfOaFileCountersign) throws ServiceException;

	@RequestMapping(value = "/mfOaFileCountersign/update")
	public void update(@RequestBody MfOaFileCountersign mfOaFileCountersign) throws ServiceException;

	@RequestMapping(value = "/mfOaFileCountersign/getById")
	public MfOaFileCountersign getById(@RequestBody MfOaFileCountersign mfOaFileCountersign) throws ServiceException;

	@RequestMapping(value = "/mfOaFileCountersign/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws ServiceException;

	/**
	 * 方法描述： 审批提交
	 * 
	 * @param taskId
	 * @param appNo
	 * @param transition
	 * @param nextUser
	 * @param dataMap
	 * @return
	 * @throws Exception
	 *             Result
	 * @author Javelin
	 * @date 2017-6-13 上午9:01:29
	 */
	@RequestMapping(value = "/mfOaFileCountersign/commitProcess")
	public Result commitProcess(@RequestParam("taskId") String taskId, @RequestParam("appNo") String appNo,
			@RequestParam("transition") String transition, @RequestParam("nextUser") String nextUser,
			@RequestBody Map<String, Object> dataMap) throws Exception;

	/**
	 * 方法描述： 获取数据数量
	 * 
	 * @param mfOaFileCountersign
	 * @return
	 * @throws ServiceException
	 *             int
	 * @author Javelin
	 * @date 2017-6-16 下午5:46:53
	 */
	@RequestMapping(value = "/mfOaFileCountersign/getDataCount")
	public int getDataCount(@RequestBody MfOaFileCountersign mfOaFileCountersign) throws ServiceException;
}
