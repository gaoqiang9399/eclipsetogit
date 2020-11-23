package app.component.vouafter.feign;

import app.component.vouafter.entity.MfBankStatementRegister;
import app.component.wkf.entity.Result;
import app.util.toolkit.Ipage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient("mftcc-platform-factor")
public interface MfBankStatementRegisterFeign {
	@RequestMapping("/MfBankStatementRegister/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;

	@RequestMapping("/MfBankStatementRegister/insert")
	public void insert(@RequestBody MfBankStatementRegister mfBankStatementRegister) throws Exception;

	@RequestMapping("/MfBankStatementRegister/submitProcess")
	public MfBankStatementRegister submitProcess(@RequestBody MfBankStatementRegister mfBankStatementRegister) throws Exception;

	@RequestMapping("/MfBankStatementRegister/getById")
	public MfBankStatementRegister getById(@RequestBody MfBankStatementRegister mfBankStatementRegister) throws Exception;

	@RequestMapping("/MfBankStatementRegister/delete")
	public void delete(@RequestBody MfBankStatementRegister mfBankStatementRegister) throws Exception;

	@RequestMapping("/MfBankStatementRegister/update")
	public void update(@RequestBody MfBankStatementRegister mfBankStatementRegister) throws Exception;

	@RequestMapping("/MfBankStatementRegister/getCount")
	public int getCount() throws Exception;

	@RequestMapping("/MfBankStatementRegister/doCommit")
	public Result doCommit(@RequestParam("taskId") String taskId, @RequestParam("transition") String transition, @RequestParam("nextUser") String nextUser,
                           @RequestBody Map<String, Object> formDataMap) throws Exception;

	@RequestMapping("/MfBankStatementRegister/getMultiBusList")
    List<MfBankStatementRegister> getMultiBusList(@RequestBody MfBankStatementRegister mfBankStatementRegister)throws Exception;

	@RequestMapping("/MfBankStatementRegister/findBankStatementRegisterByPage")
    Ipage findBankStatementRegisterByPage(@RequestBody Ipage ipage)throws Exception;
}
