package app.component.commissionchange.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.commissionchange.entity.MfCommissionChangeRecord;
import app.util.toolkit.Ipage;

/**
 * Title: MfCommissionChangeRecordBo.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Fri Jul 13 11:34:55 CST 2018
 **/
@FeignClient("mftcc-platform-factor")
public interface MfCommissionChangeRecordFeign {

	@RequestMapping("/mfCommissionChangeRecord/insert")
	public void insert(@RequestBody MfCommissionChangeRecord mfCommissionChangeRecord) throws Exception;

	@RequestMapping("/mfCommissionChangeRecord/delete")
	public void delete(@RequestBody MfCommissionChangeRecord mfCommissionChangeRecord) throws Exception;

	@RequestMapping("/mfCommissionChangeRecord/update")
	public void update(@RequestBody MfCommissionChangeRecord mfCommissionChangeRecord) throws Exception;

	@RequestMapping("/mfCommissionChangeRecord/getById")
	public MfCommissionChangeRecord getById(@RequestBody MfCommissionChangeRecord mfCommissionChangeRecord)
			throws Exception;

	@RequestMapping("/mfCommissionChangeRecord/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;
	
	@RequestMapping("/mfCommissionChangeRecord/getByCusNo")
	public List<MfCommissionChangeRecord> getByCusNo(@RequestBody MfCommissionChangeRecord mfCommissionChangeRecord)throws Exception;
	
	@RequestMapping("/mfCommissionChangeRecord/insertAgencies")
	public void insertAgencies(@RequestBody MfCommissionChangeRecord mfCommissionChangeRecord) throws Exception;

}
