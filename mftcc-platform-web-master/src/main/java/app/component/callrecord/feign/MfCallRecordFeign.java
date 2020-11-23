package app.component.callrecord.feign;

import app.util.toolkit.Ipage;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.callrecord.entity.MfCallRecord;

/**
 * @类名： MfCallRecordBo
 * 
 * @描述：通话记录
 * @author 仇招
 * @date 2018年9月15日 上午11:39:03
 */
@FeignClient("mftcc-platfotm-factor")
public interface MfCallRecordFeign {

	@RequestMapping(value = "/mfCallRecord/insert")
	public void insert(@RequestBody MfCallRecord mfCallRecord) throws Exception;

	@RequestMapping(value = "/mfCallRecord/delete")
	public void delete(@RequestBody MfCallRecord mfCallRecord) throws Exception;

	@RequestMapping(value = "/mfCallRecord/update")
	public void update(@RequestBody MfCallRecord mfCallRecord) throws Exception;

	@RequestMapping(value = "/mfCallRecord/getById")
	public MfCallRecord getById(@RequestBody MfCallRecord mfCallRecord) throws Exception;

	@RequestMapping(value = "/mfCallRecord/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;

}
