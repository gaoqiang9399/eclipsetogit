package app.component.queryinterface;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.query.entity.MfQueryItem;

@FeignClient("mftcc-platform-factor")
public interface QueryInterfaceFeign {
	@RequestMapping(value = "/queryInterface/getOaAttentionList")
	public List<MfQueryItem> getOaAttentionList(@RequestBody MfQueryItem mfQueryItem) throws Exception;

	@RequestMapping(value = "/queryInterface/getMfQueryItemList")
	public List<MfQueryItem> getMfQueryItemList(@RequestBody MfQueryItem mfQueryItem) throws Exception;

}
