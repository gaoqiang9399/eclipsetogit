package app.component.cus.intangibleassets.feign;

import app.component.cus.institutions.entity.MfBusInstitutions;
import app.component.cus.intangibleassets.entity.MfCusIntangibleAssets;
import app.component.wkf.entity.Result;
import app.util.toolkit.Ipage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.awt.*;
import java.util.List;
import java.util.Map;

@FeignClient("mftcc-platform-factor")
public interface MfCusIntangibleAssetsFeign {
	@RequestMapping("/mfCusIntangibleAssets/getByCusNo")
	List<MfCusIntangibleAssets> getByCusNo(@RequestParam(name = "cusNo") String cusNo) throws Exception;

	@RequestMapping("/mfCusIntangibleAssets/insert")
	void insert(@RequestBody MfCusIntangibleAssets mfCusIntangibleAssets) throws Exception;

	@RequestMapping("/mfCusIntangibleAssets/getById")
	MfCusIntangibleAssets getById(@RequestParam(name = "intangibleAssetsId") String intangibleAssetsId) throws Exception;

	@RequestMapping("/mfCusIntangibleAssets/update")
	void update(@RequestBody MfCusIntangibleAssets mfCusIntangibleAssets) throws Exception;

	@RequestMapping("/mfCusIntangibleAssets/delete")
	boolean delete(@RequestParam(name = "intangibleAssetsId") String intangibleAssetsId) throws Exception;

	@RequestMapping("/mfCusIntangibleAssets/getByAssetsTypeAndAssetsNo")
	MfCusIntangibleAssets getByAssetsTypeAndAssetsNo(MfCusIntangibleAssets mfCusIntangibleAssets) throws Exception;

	@RequestMapping("/mfCusIntangibleAssets/getByAssetsTypeAndAssetsNoExcludeSelf")
	MfCusIntangibleAssets getByAssetsTypeAndAssetsNoExcludeSelf(MfCusIntangibleAssets mfCusIntangibleAssets) throws Exception;

	@RequestMapping("/mfCusIntangibleAssets/findByPage")
	Ipage findByPage(@RequestBody Ipage ipage)throws Exception;
}
