package  app.component.commissionchange.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.commissionchange.entity.MfCommissionChangeRecordDetail;
import app.util.toolkit.Ipage;

/**
 * 类名： MfCommissionChangeRecordDetailFeign
 * 描述：分润记录
 * @author 仇招
 * @date 2018年9月4日 下午9:47:17
 */
@FeignClient("mftcc-platform-factor")
public interface MfCommissionChangeRecordDetailFeign {
	
	@RequestMapping("/mfCommissionChangeRecordDetail/insert")
	public void insert(@RequestBody MfCommissionChangeRecordDetail mfCommissionChangeRecordDetail) throws Exception ;

	@RequestMapping("/mfCommissionChangeRecordDetail/delete")
	public void delete(@RequestBody MfCommissionChangeRecordDetail mfCommissionChangeRecordDetail) throws Exception ;
	@RequestMapping("/mfCommissionChangeRecordDetail/update")
	public void update(@RequestBody MfCommissionChangeRecordDetail mfCommissionChangeRecordDetail) throws Exception ;

	@RequestMapping("/mfCommissionChangeRecordDetail/getById")
	public MfCommissionChangeRecordDetail getById(@RequestBody MfCommissionChangeRecordDetail mfCommissionChangeRecordDetail);

	@RequestMapping("/mfCommissionChangeRecordDetail/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;
	
	@RequestMapping("/mfCommissionChangeRecordDetail/findByPageAjaxExcel")
	public List<MfCommissionChangeRecordDetail> findByPageAjaxExcel(@RequestBody
			MfCommissionChangeRecordDetail mfCommissionChangeRecordDetail)throws Exception;
}
