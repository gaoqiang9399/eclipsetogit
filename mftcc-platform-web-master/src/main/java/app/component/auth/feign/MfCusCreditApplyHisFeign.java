package  app.component.auth.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.auth.entity.MfCusCreditApplyHis;
import app.util.toolkit.Ipage;

/**
* Title: MfCusCreditApplyHisBo.java
* Description:授信审批历史信息
* @author:kaifa@dhcc.com.cn
* @Sat Mar 04 16:54:01 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface MfCusCreditApplyHisFeign {
	
	@RequestMapping(value = "/mfCusCreditApplyHis/insert")
	public void insert(@RequestBody MfCusCreditApplyHis mfCusCreditApplyHis) throws ServiceException;
	
	@RequestMapping(value = "/mfCusCreditApplyHis/delete")
	public void delete(@RequestBody MfCusCreditApplyHis mfCusCreditApplyHis) throws ServiceException;
	
	@RequestMapping(value = "/mfCusCreditApplyHis/update")
	public void update(@RequestBody MfCusCreditApplyHis mfCusCreditApplyHis) throws ServiceException;
	
	@RequestMapping(value = "/mfCusCreditApplyHis/getById")
	public MfCusCreditApplyHis getById(@RequestBody MfCusCreditApplyHis mfCusCreditApplyHis) throws ServiceException;
	
	@RequestMapping(value = "/mfCusCreditApplyHis/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("mfCusCreditApplyHis") MfCusCreditApplyHis mfCusCreditApplyHis) throws ServiceException;
	
	/**
	 * 批量插入
	 * @author LJW
	 * date 2017-3-18
	 */
	@RequestMapping(value = "/mfCusCreditApplyHis/insertByBatch")
	public void insertByBatch(@RequestBody List<MfCusCreditApplyHis> mfCusCreditApplyHiss) throws ServiceException;
	
	/**
	 * 方法描述：根据授信申请号查询最后一笔审批历史
	 * @param mfCusCreditApplyHis
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/mfCusCreditApplyHis/getLastByCreditAppId")
	public MfCusCreditApplyHis getLastByCreditAppId(@RequestBody MfCusCreditApplyHis mfCusCreditApplyHis) throws Exception;

	/**
	 * 方法描述：查询审批历史列表
	 * @param mfCusCreditApplyHis
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/mfCusCreditApplyHis/getByCreditAppId")
	public List<MfCusCreditApplyHis> getByCreditAppId(@RequestBody MfCusCreditApplyHis mfCusCreditApplyHis) throws Exception;
}
