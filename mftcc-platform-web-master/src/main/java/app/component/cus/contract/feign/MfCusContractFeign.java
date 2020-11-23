package app.component.cus.contract.feign;

import app.component.cus.contract.entity.MfCusContract;
import app.util.toolkit.Ipage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("mftcc-platform-factor")
public interface MfCusContractFeign {
	@RequestMapping("/mfCusContract/getByCusNo")
	List<MfCusContract> getByCusNo(@RequestParam(name = "cusNo") String cusNo) throws Exception;

	@RequestMapping("/mfCusContract/insert")
	void insert(@RequestBody MfCusContract mfCusContract) throws Exception;

	@RequestMapping("/mfCusContract/getById")
	MfCusContract getById(@RequestParam(name = "contractId") String contractId) throws Exception;

	@RequestMapping("/mfCusContract/update")
	void update(@RequestBody MfCusContract mfCusContract) throws Exception;

	@RequestMapping("/mfCusContract/deleteById")
	boolean deleteById(@RequestParam(name = "contractId") String contractId) throws Exception;

	@RequestMapping("/mfCusContract/getBySignYearAndCusNo")
	MfCusContract getBySignYearAndCusNo(MfCusContract mfCusContract) throws Exception;

	@RequestMapping("/mfCusContract/getBySignYearAndCusNoExcludeSelf")
	MfCusContract getBySignYearAndCusNoExcludeSelf(MfCusContract mfCusContract) throws Exception;
	@RequestMapping("/mfCusContract/findByPage")
	Ipage findByPage(@RequestBody Ipage ipage)throws Exception;
}
