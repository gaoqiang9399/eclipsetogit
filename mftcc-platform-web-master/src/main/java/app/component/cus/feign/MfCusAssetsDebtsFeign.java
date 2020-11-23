package app.component.cus.feign;

import app.component.cus.entity.MfCusAssetsDebts;
import app.util.toolkit.Ipage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;



@FeignClient("mftcc-platform-factor")
public interface MfCusAssetsDebtsFeign {
	@RequestMapping("/mfCusAssetsDebts/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;

	@RequestMapping("/mfCusAssetsDebts/insert")
	public void insert(@RequestBody MfCusAssetsDebts mfCusAssetsDebts) throws Exception;

	@RequestMapping("/mfCusAssetsDebts/getById")
	public MfCusAssetsDebts getById(@RequestBody MfCusAssetsDebts mfCusAssetsDebts) throws Exception;

	@RequestMapping("/mfCusAssetsDebts/delete")
	public void delete(@RequestBody MfCusAssetsDebts mfCusAssetsDebts) throws Exception;

	@RequestMapping("/mfCusAssetsDebts/update")
	public void update(@RequestBody MfCusAssetsDebts mfCusAssetsDebts) throws Exception;

}
