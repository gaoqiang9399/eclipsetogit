package app.component.cus.closingmanage.feign;

import app.base.ServiceException;
import app.component.compensatory.entity.MfBusCompensatoryApply;
import app.component.cus.closingmanage.entity.MfCusClosingManage;
import app.util.toolkit.Ipage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@FeignClient("mftcc-platform-factor")
public interface MfCusClosingManageFeign {
	@RequestMapping(value = "/mfCusClosingManage/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws ServiceException;
	
	@RequestMapping(value = "/mfCusClosingManage/getById")
	public MfCusClosingManage getById(@RequestBody MfCusClosingManage mfCusClosingManage) throws ServiceException;
	
	@RequestMapping(value = "/mfCusClosingManage/insert")
	public Map<String, Object> insert(@RequestBody MfCusClosingManage mfCusClosingManage) throws ServiceException;
	
	@RequestMapping(value = "/mfCusClosingManage/update")
	public void update(@RequestBody MfCusClosingManage mfCusClosingManage) throws ServiceException;
	
	@RequestMapping(value = "/mfCusClosingManage/delete")
	public void delete(@RequestBody MfCusClosingManage mfCusClosingManage) throws Exception;

	@RequestMapping(value = "/mfCusClosingManage/getAllList")
	public List<MfCusClosingManage> getAllList(@RequestBody MfCusClosingManage mfCusClosingManage) throws Exception;

	@RequestMapping(value = "/mfCusClosingManage/insertClosingManage")
	public  Map<String, Object> insertClosingManage(@RequestBody MfCusClosingManage mfCusClosingManage);

	@RequestMapping(value = "/mfCusClosingManage/doCommitWkf")
	public Map<String, Object> doCommitWkf(@RequestBody Map<String, Object> dataMap) throws Exception;

}
