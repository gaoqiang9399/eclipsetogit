package app.component.cus.trenchsubsidiary.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.cus.trenchsubsidiary.entity.MfTrenchCreditAmtModifyHis;
import app.util.toolkit.Ipage;

/**
 * Title: MfTrenchCreditAmtModifyHisBo.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Tue Mar 06 15:02:30 CST 2018
 **/
@FeignClient("mftcc-platform-factor")
public interface MfTrenchCreditAmtModifyHisFeign {
	@RequestMapping(value = "/mfTrenchCreditAmtModifyHis/insert")
	public void insert(@RequestBody MfTrenchCreditAmtModifyHis mfTrenchCreditAmtModifyHis) throws Exception;

	@RequestMapping(value = "/mfTrenchCreditAmtModifyHis/delete")
	public void delete(@RequestBody MfTrenchCreditAmtModifyHis mfTrenchCreditAmtModifyHis) throws Exception;

	@RequestMapping(value = "/mfTrenchCreditAmtModifyHis/update")
	public void update(@RequestBody MfTrenchCreditAmtModifyHis mfTrenchCreditAmtModifyHis) throws Exception;

	@RequestMapping(value = "/mfTrenchCreditAmtModifyHis/getById")
	public MfTrenchCreditAmtModifyHis getById(@RequestBody MfTrenchCreditAmtModifyHis mfTrenchCreditAmtModifyHis) throws Exception;

	@RequestMapping(value = "/mfTrenchCreditAmtModifyHis/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;
	
	@RequestMapping(value = "/mfTrenchCreditAmtModifyHis/insertAgencies")
	public void insertAgencies(@RequestBody MfTrenchCreditAmtModifyHis mfTrenchCreditAmtModifyHis)throws Exception;
	
	@RequestMapping(value = "/mfTrenchCreditAmtModifyHis/getByTrenchUid")
	public List<MfTrenchCreditAmtModifyHis> getByTrenchUid(@RequestBody MfTrenchCreditAmtModifyHis mfTrenchCreditAmtModifyHis)throws Exception;
}
