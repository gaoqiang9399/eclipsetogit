package app.component.vouafter.feign;

import app.component.vouafter.entity.MfBankStatementRegisterHis;
import app.util.toolkit.Ipage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("mftcc-platform-factor")
public interface MfBankStatementRegisterHisFeign {
	@RequestMapping("/MfBankStatementRegisterHis/insert")
	public void insert(@RequestBody MfBankStatementRegisterHis mfBankStatementRegisterHis) throws Exception;

	@RequestMapping("/MfBankStatementRegisterHis/delete")
	public void delete(@RequestBody MfBankStatementRegisterHis mfBankStatementRegisterHis) throws Exception;

	@RequestMapping("/MfBankStatementRegisterHis/update")
	public void update(@RequestBody MfBankStatementRegisterHis mfBankStatementRegisterHis) throws Exception;

	@RequestMapping("/MfBankStatementRegisterHis/getById")
	public MfBankStatementRegisterHis getById(@RequestBody MfBankStatementRegisterHis mfBankStatementRegisterHis) throws Exception;

	@RequestMapping("/MfBankStatementRegisterHis/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;
}
