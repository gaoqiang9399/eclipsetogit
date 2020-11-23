package  app.component.lawsuit.feign;

import java.util.List;
import java.util.Map;

import app.component.pact.assetmanage.entity.MfAssetManage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.base.ServiceException;
import app.component.lawsuit.entity.MfLawsuit;
import app.component.lawsuit.entity.MfLawsuitFollow;
import app.component.pact.entity.MfBusPact;
import app.util.toolkit.Ipage;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
* Title: MfLawsuitBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Wed Feb 22 21:19:41 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface MfLawsuitFeign {
	
	@RequestMapping(value = "/mfLawsuit/insert")
	public void insert(@RequestBody MfLawsuit mfLawsuit) throws ServiceException;
	
	@RequestMapping(value = "/mfLawsuit/delete")
	public void delete(@RequestBody MfLawsuit mfLawsuit) throws ServiceException;
	
	@RequestMapping(value = "/mfLawsuit/update")
	public void update(@RequestBody MfLawsuit mfLawsuit) throws ServiceException;
	
	@RequestMapping(value = "/mfLawsuit/getById")
	public MfLawsuit getById(@RequestBody MfLawsuit mfLawsuit) throws ServiceException;
	
	@RequestMapping(value = "/mfLawsuit/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws ServiceException;

	@RequestMapping(value = "/mfLawsuit/initLawsuit")
	public Map<String, Object> initLawsuit(@RequestBody MfLawsuit mfLawsuit) throws ServiceException;

	@RequestMapping(value = "/mfLawsuit/getByPact")
	public List<MfLawsuit> getByPact(@RequestBody MfLawsuit mfLawsuit) throws ServiceException;

	@RequestMapping(value = "/mfLawsuit/follow")
	public void follow(@RequestBody MfLawsuitFollow lawsuitFollow) throws ServiceException;

	/**
	 * 跟进案件中合同号查询合同信息
	 * @param mfLawsuit
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/mfLawsuit/getPactById")
	public MfBusPact getPactById(@RequestBody MfLawsuit mfLawsuit) throws ServiceException;

	/**
	 * 新增案件时，如果需要与诉讼申请关联，该方法用于展示已经通过的诉讼申请
	 * 2018年07月03日   刘豪彬
	 * @param applyFlag
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/mfLawsuit/getAssetManageList")
	public Ipage getAssetManageList(@RequestBody Ipage ipage,@RequestParam("applyFlag") String applyFlag) throws ServiceException;

	@RequestMapping(value = "/mfLawsuit/getCusInfo",method= RequestMethod.POST)
	public MfLawsuit getCusInfo(@RequestBody MfLawsuit mfLawsuit,@RequestParam("assetId") String assetId) throws Exception;
}
