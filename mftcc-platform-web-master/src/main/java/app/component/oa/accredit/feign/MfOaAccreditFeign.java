package  app.component.oa.accredit.feign;
import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.oa.accredit.entity.MfOaAccredit;
import app.util.toolkit.Ipage;

/**
* Title: MfOaAccreditBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Tue Apr 25 11:16:07 CST 2017
**/
@FeignClient("mftcc-platform-factor")
public interface MfOaAccreditFeign {
	@RequestMapping(value="/mfOaAccredit/insert")
	public void insert(@RequestBody MfOaAccredit mfOaAccredit) throws ServiceException;
	
	@RequestMapping(value="/mfOaAccredit/delete")
	public void delete(@RequestBody MfOaAccredit mfOaAccredit) throws ServiceException;
	
	@RequestMapping(value="/mfOaAccredit/update")
	public int[] update(@RequestBody MfOaAccredit mfOaAccredit) throws ServiceException;
	
	@RequestMapping(value="/mfOaAccredit/getById")
	public MfOaAccredit getById(@RequestBody MfOaAccredit mfOaAccredit) throws ServiceException;
	
	@RequestMapping(value="/mfOaAccredit/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws ServiceException;

	@RequestMapping(value="/mfOaAccredit/")
	public List<MfOaAccredit> getListForLogin(@RequestBody String authorizerNo) throws ServiceException;
	
	@RequestMapping(value="/mfOaAccredit/updateAll")
	public void updateAll(@RequestBody MfOaAccredit mfOaAccredit) throws ServiceException;
	
	@RequestMapping(value="/mfOaAccredit/getSum")
	public Map<String , String > getSum(@RequestParam("authorizerNo") String authorizerNo) throws ServiceException;
	
	@RequestMapping(value="/mfOaAccredit/getCount")
	public String  getCount(@RequestParam("opNo") String opNo) throws ServiceException;
	/**
	 * 将参与托管的任务记录到托管辅助表里（记录来自哪个托管人，来自流程还是生效时）
	 * @param parmMap
	 * @throws ServiceException
	 */
	@RequestMapping(value="/mfOaAccredit/insertAccreditTask")
	public void insertAccreditTask(@RequestBody Map<String, String> parmMap) throws ServiceException;
	/**
	 * 检查托管是否有冲突
	 * 多次托管情况禁止
	 * @param mfOaAccredit
	 * @return
	 */
	@RequestMapping(value="/mfOaAccredit/checkAccredit")
	public Map<String, Object> checkAccredit(@RequestBody MfOaAccredit mfOaAccredit) throws ServiceException;
	/**
	 * 仅替换流程中的用户集合//String[] userArr,String pasMinNo
	 */
	@RequestMapping(value="/mfOaAccredit/updateUserArryAccredited")
	public String[] updateUserArryAccredited(@RequestParam("userArr") String[] userArr,@RequestParam("pasMinNo") String pasMinNo);
}
