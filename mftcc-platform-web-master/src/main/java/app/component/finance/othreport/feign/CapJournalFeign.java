package app.component.finance.othreport.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.finance.account.entity.CwComItem;
import app.component.finance.othreport.entity.CapJournalBean;



/**
 * 现金日记账业务接口
 * @author Yanght
 *
 */
@FeignClient("mftcc-platform-fiscal")
public interface CapJournalFeign {
	/**
	 * 获取现金日记账的数据
	 * @param paramp
	 * @return
	 */
	@RequestMapping(value = "/capJournal/getCapJournalList", method = RequestMethod.POST)
	public List<CapJournalBean> getCapJournalList(@RequestBody Map<String, String> paramp,@RequestParam("finBooks") String finBooks) throws Exception;
	/**
	 * 获取下拉框的数据
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/capJournal/getselectDate", method = RequestMethod.POST)
	public List<CwComItem> getselectDate(@RequestBody String style,@RequestParam("finBooks") String finBooks) throws Exception;
	
}
