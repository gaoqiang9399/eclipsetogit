package app.component.vouafter.feign;

import app.component.vouafter.entity.MfHandAmtManage;
import app.component.wkf.entity.Result;
import app.util.toolkit.Ipage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient("mftcc-platform-factor")
public interface MfHandAmtManageFeign {
	@RequestMapping("/MfHandAmtManage/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;

	@RequestMapping("/MfHandAmtManage/insert")
	public void insert(@RequestBody MfHandAmtManage mfHandAmtManage) throws Exception;

	@RequestMapping("/MfHandAmtManage/getById")
	public MfHandAmtManage getById(@RequestBody MfHandAmtManage mfHandAmtManage) throws Exception;

	@RequestMapping("/MfHandAmtManage/delete")
	public void delete(@RequestBody MfHandAmtManage mfHandAmtManage) throws Exception;

	@RequestMapping("/MfHandAmtManage/update")
	public void update(@RequestBody MfHandAmtManage mfHandAmtManage) throws Exception;

	@RequestMapping("/MfHandAmtManage/getCount")
	public int getCount() throws Exception;

	@RequestMapping("/MfHandAmtManage/getMultiBusList")
    List<MfHandAmtManage> getMultiBusList(@RequestBody MfHandAmtManage mfHandAmtManage)throws Exception;

	@RequestMapping("/MfHandAmtManage/findHandAmtManageByPage")
    Ipage findHandAmtManageByPage(@RequestBody Ipage ipage)throws Exception;
}
