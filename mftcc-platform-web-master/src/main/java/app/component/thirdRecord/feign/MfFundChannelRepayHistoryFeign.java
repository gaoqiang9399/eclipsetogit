package app.component.thirdRecord.feign;

import java.util.List;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import app.component.channel.fund.entity.MfFundChannelRepayHistory;

@FeignClient("mftcc-platform-factor")
public interface MfFundChannelRepayHistoryFeign {
	@RequestMapping(value = "/mfFundChannelRepayHistory/insert")
	public MfFundChannelRepayHistory insert(@RequestBody MfFundChannelRepayHistory mfFundChannelRepayHistory);

	@RequestMapping(value = "/mfFundChannelRepayHistory/getById")
	public MfFundChannelRepayHistory getById(@RequestBody MfFundChannelRepayHistory mfFundChannelRepayHistory);
	
	@RequestMapping(value = "/mfFundChannelRepayHistory/getList")
	public List<MfFundChannelRepayHistory> getList(@RequestBody MfFundChannelRepayHistory mfFundChannelRepayHistory);
	
	@RequestMapping(value = "/mfFundChannelRepayHistory/getMfFundChannelRepayHistoryList")
	public List<MfFundChannelRepayHistory> getMfFundChannelRepayHistoryList(@RequestBody MfFundChannelRepayHistory mfFundChannelRepayHistory);
}
