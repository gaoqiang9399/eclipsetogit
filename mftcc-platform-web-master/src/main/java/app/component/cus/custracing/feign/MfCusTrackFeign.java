package  app.component.cus.custracing.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.cus.custracing.entity.MfCusTrack;
import app.util.toolkit.Ipage;
import net.sf.json.JSONArray;

/**
* Title: MfCusTrackBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Tue Jun 14 09:53:43 CST 2016
**/

@FeignClient("mftcc-platform-factor")
public interface MfCusTrackFeign {
	
	@RequestMapping(value = "/mfCusTrack/insert")
	public MfCusTrack insert(@RequestBody MfCusTrack mfCusTrack) throws ServiceException;
	@RequestMapping(value = "/mfCusTrack/insertForApp")
	public MfCusTrack insertForApp(@RequestBody MfCusTrack mfCusTrack) throws ServiceException;
	
	@RequestMapping(value = "/mfCusTrack/delete")
	public void delete(@RequestBody MfCusTrack mfCusTrack) throws ServiceException;
	
	@RequestMapping(value = "/mfCusTrack/update")
	public void update(@RequestBody MfCusTrack mfCusTrack) throws ServiceException;
	
	@RequestMapping(value = "/mfCusTrack/getById")
	public MfCusTrack getById(@RequestBody MfCusTrack mfCusTrack) throws ServiceException;
	
	@RequestMapping(value = "/mfCusTrack/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("mfCusTrack") MfCusTrack mfCusTrack) throws ServiceException;
	
	@RequestMapping(value = "/mfCusTrack/getList")
	public List<MfCusTrack> getList(@RequestBody MfCusTrack mfCusTrack) throws ServiceException;
	
	@RequestMapping(value = "/mfCusTrack/getTopList")
	public List<MfCusTrack> getTopList(@RequestBody Map<String, Object> map) throws ServiceException;

	@RequestMapping(value = "/mfCusTrack/getTrackTypeArray")
	public JSONArray getTrackTypeArray()throws ServiceException;
	/**
	 * @Description:获取未跟进客户标志 1-未跟进 0-已跟进 
	 * @param cusNo
	 * @return
	 * @author: 李伟
	 * @date: 2017-9-22 上午9:17:39
	 */
	@RequestMapping(value = "/mfCusTrack/getNotFollowupFlag")
	public String getNotFollowupFlag(@RequestParam(name="cusNo") String cusNo)  throws ServiceException;
	/**
	 * 根据客户号获取客户跟踪列表
	 * @param mfCusTrack
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/mfCusTrack/getMfCusTraclListByCusNo")
	public List<MfCusTrack> getMfCusTraclListByCusNo(@RequestBody MfCusTrack mfCusTrack)throws Exception;
	/**
	 * 根据跟踪ID获取评论列表
	 * @param mfCusTrack
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/mfCusTrack/getMfCusTraclListByTarctId")
	public List<MfCusTrack> getMfCusTraclListByTarctId(@RequestBody MfCusTrack mfCusTrack)throws Exception;
}
