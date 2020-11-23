package app.component.cus.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.cus.entity.MfBusAgencies;
import app.util.toolkit.Ipage;

@FeignClient("mftcc-platform-factor")
public interface MfBusAgenciesFeign {
	
	@RequestMapping(value = "/mfBusAgencies/insert")	
	public void insert(@RequestBody MfBusAgencies mfBusAgencies) throws Exception;
	
	@RequestMapping(value = "/mfBusAgencies/delete")
	public void delete(@RequestBody MfBusAgencies mfBusAgencies) throws Exception;
	
	@RequestMapping(value = "/mfBusAgencies/update")
	public void update(@RequestBody MfBusAgencies mfBusAgencies) throws Exception;
	
	@RequestMapping(value = "/mfBusAgencies/getById")
	public MfBusAgencies getById(@RequestBody MfBusAgencies mfBusAgencies) throws Exception;
	
	@RequestMapping(value = "/mfBusAgencies/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;
	
	@RequestMapping(value = "/mfBusAgencies/updateInfIntegrity")
	public String updateInfIntegrity(@RequestBody MfBusAgencies mfBusAgencies) throws Exception;
	
	@RequestMapping(value = "/mfBusAgencies/findAll")
	public List<MfBusAgencies> findAll(@RequestBody MfBusAgencies mfBusAgencies) throws Exception;
	
	@RequestMapping(value = "/mfBusAgencies/getTrenchBusHisAjax")
	public Map<String, String> getTrenchBusHisAjax(@RequestParam("agenciesUid") String agenciesUid) throws Exception;
	/**
	 * 方法描述： 根据操作员编号获取登录的资金机构的信息
	 * @param opNo
	 * @return
	 * @throws Exception
	 * MfBusAgencies
	 * @author 仇招
	 * @date 2018年8月27日 下午2:21:38
	 */
	@RequestMapping(value = "/mfBusAgencies/getByOpNo")
	public MfBusAgencies getByOpNo(@RequestParam("opNo") String opNo) throws Exception;
	
	/**
	 * 方法描述： 根据产品编号查找资金机构
	 * @param mfBusAgencies
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author 仇招
	 * @date 2018年9月2日 上午10:43:24
	 */
	@RequestMapping(value = "/mfBusAgencies/getByKindNo")
	public Map<String, Object> getByKindNo(@RequestBody MfBusAgencies mfBusAgencies) throws Exception;

	@RequestMapping(value = "/mfBusAgencies/getAgenciesByKindNo")
	public List<MfBusAgencies> getAgenciesByKindNo(@RequestBody MfBusAgencies mfBusAgencies) throws Exception;
	@RequestMapping(value = "/mfBusAgencies/findAgenciesByKindNoPage")
	public Ipage findAgenciesByKindNoPage(@RequestBody Ipage ipage) throws Exception;

	@RequestMapping(value = "/mfBusAgencies/getAgenciesListByKindNo")
    Ipage getAgenciesListByKindNo(Ipage ipage) throws Exception;
}
