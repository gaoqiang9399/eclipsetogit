package app.component.vouafter.feign;

import app.component.vouafter.entity.MfRefundManage;
import app.component.wkf.entity.Result;
import app.util.toolkit.Ipage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient("mftcc-platform-factor")
public interface MfRefundManageFeign {
	@RequestMapping("/MfRefundManage/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;

	@RequestMapping("/MfRefundManage/insert")
	public void insert(@RequestBody MfRefundManage mfRefundManage) throws Exception;

	@RequestMapping("/MfRefundManage/submitProcess")
	public MfRefundManage submitProcess(@RequestBody MfRefundManage mfRefundManage) throws Exception;

	@RequestMapping("/MfRefundManage/getById")
	public MfRefundManage getById(@RequestBody MfRefundManage mfRefundManage) throws Exception;

	@RequestMapping("/MfRefundManage/delete")
	public void delete(@RequestBody MfRefundManage mfRefundManage) throws Exception;

	@RequestMapping("/MfRefundManage/update")
	public void update(@RequestBody MfRefundManage mfRefundManage) throws Exception;

	@RequestMapping("/MfRefundManage/getCount")
	public int getCount() throws Exception;

	@RequestMapping("/MfRefundManage/doCommit")
	public Result doCommit(@RequestParam("taskId") String taskId, @RequestParam("transition") String transition, @RequestParam("nextUser") String nextUser,
                           @RequestBody Map<String, Object> formDataMap) throws Exception;

	@RequestMapping("/MfRefundManage/getMultiBusList")
    List<MfRefundManage> getMultiBusList(@RequestBody MfRefundManage mfRefundManage)throws Exception;

	@RequestMapping("/MfRefundManage/findRefundManageByPage")
    Ipage findRefundManageByPage(@RequestBody Ipage ipage)throws Exception;
}
