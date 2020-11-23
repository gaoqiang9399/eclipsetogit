package app.component.channel.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.channel.fund.entity.MfFundChannelFinc;
import app.util.toolkit.Ipage;

@FeignClient("mftcc-platform-factor")
public interface MfChannelBusFeign {
	@RequestMapping(value = "/mfChannelBus/findChannelBusFincByPageAjax")
	public Ipage findChannelBusFincByPageAjax(@RequestBody Ipage ipage) throws Exception;

	@RequestMapping(value = "/mfChannelBus/getMfFundChannelFincByPactId")
	public MfFundChannelFinc getMfFundChannelFincByPactId(@RequestBody  MfFundChannelFinc mfFundChannelFinc ) throws Exception;

}
