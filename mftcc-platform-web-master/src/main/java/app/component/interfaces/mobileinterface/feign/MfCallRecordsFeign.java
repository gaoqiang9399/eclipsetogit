package  app.component.interfaces.mobileinterface.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.interfaces.mobileinterface.entity.MfCallRecords;
import app.util.toolkit.Ipage;

/**
* Title: MfCallRecordsBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Thu Jul 20 10:28:02 CST 2017
**/
@FeignClient("mftcc-platform-factor")
public interface MfCallRecordsFeign{
	
	@RequestMapping(value = "/mfCallRecords/insert")
	public void insert(@RequestBody  MfCallRecords mfCallRecords) throws Exception;

	@RequestMapping(value = "/mfCallRecords/insertList")
	public void insertList(@RequestBody  List<MfCallRecords> mfCallRecordList) throws Exception;

	@RequestMapping(value = "/mfCallRecords/delete")
	public void delete(@RequestBody  MfCallRecords mfCallRecords) throws Exception;

	@RequestMapping(value = "/mfCallRecords/update")
	public void update(@RequestBody  MfCallRecords mfCallRecords) throws Exception;

	@RequestMapping(value = "/mfCallRecords/getById")
	public MfCallRecords getById(@RequestBody  MfCallRecords mfCallRecords) throws Exception;

	@RequestMapping(value = "/mfCallRecords/findByPage")
	public Ipage findByPage(@RequestBody  Ipage ipage,@RequestParam("mfCallRecords") MfCallRecords mfCallRecords) throws Exception;
	
	
	
}
