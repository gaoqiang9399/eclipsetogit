package app.component.pact.assetmanage.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.dhcc.workflow.pvm.internal.task.TaskImpl;

import app.component.oa.debt.entity.MfOaDebt;
import app.component.pact.assetmanage.entity.MfAssetManage;
import app.component.pact.entity.MfBusFincApp;
import app.component.pact.fiveclass.entity.MfPactFiveclass;
import app.component.wkf.entity.Result;
import app.util.toolkit.Ipage;

/**
 * 
 * @ClassName MfAssetManageFeign
 * @Description 
 * @Author Liu Haobin
 * @Date 2018年5月30日 下午4:29:51
 */
@FeignClient("mftcc-platform-factor")
public interface MfAssetManageFeign {

	@RequestMapping(value = "/mfAssetManage/getOverdueCus")
	public Ipage getOverdueCus(@RequestBody Ipage ipage) throws Exception;
	
	@RequestMapping(value = "/mfAssetManage/getDetailByCusNo",method=RequestMethod.POST)
	public List<MfAssetManage> getDetailByCusNo(@RequestParam("cusNo") String cusNo) throws Exception;
	
	@RequestMapping(value = "/mfAssetManage/getOverdueCusInfo",method=RequestMethod.POST)
	public MfAssetManage getOverdueCusInfo(@RequestParam("cusNo") String cusNo) throws Exception;
	
	@RequestMapping(value = "/mfAssetManage/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;
	
	@RequestMapping(value = "/mfAssetManage/mfAssetManageApply")
	public Ipage mfAssetManageApply(@RequestBody MfAssetManage mfAssetManage) throws Exception;
	
	@RequestMapping(value = "/mfAssetManage/getById")
	public MfAssetManage getById(@RequestBody MfAssetManage mfAssetManage) throws Exception;
	
	@RequestMapping(value = "/mfAssetManage/update")
	public void update(@RequestBody MfAssetManage mfAssetManage) throws Exception;
	
	@RequestMapping(value = "/mfAssetManage/getTask",method=RequestMethod.POST)
	public TaskImpl getTask(@RequestParam("wkfFinId") String wkfFinId) throws Exception;
	
	@RequestMapping(value = "/mfAssetManage/submitProcess",method=RequestMethod.POST)
	public MfAssetManage submitProcess(@RequestBody MfAssetManage mfAssetManage,@RequestParam(value="firstApprovalUser",required=false) String firstApprovalUser) throws Exception;

	@RequestMapping(value = "/mfAssetManage/submitProcess1",method=RequestMethod.POST)
	public MfAssetManage submitProcess1(@RequestBody MfAssetManage mfAssetManage,@RequestParam(value="firstApprovalUser",required=false) String firstApprovalUser) throws Exception;

	@RequestMapping(value = "/mfAssetManage/updateForSubmit",method=RequestMethod.POST)
	public Result updateForSubmit(@RequestParam("taskId") String taskId, @RequestParam("wkfAppId") String wkfAppId,
			@RequestParam("opinionType") String opinionType, @RequestParam("approvalOpinion") String approvalOpinion,
			@RequestParam("transition") String transition, @RequestParam("regNo") String regNo,
			@RequestParam("nextUser") String nextUser,@RequestBody Map<String, Object> dataMap) throws Exception;

	@RequestMapping(value = "/mfAssetManage/isApproval",method=RequestMethod.POST)
	public String isApproval() throws Exception;
	
	
	@RequestMapping(value = "/mfAssetManage/updateLawForSubmit",method=RequestMethod.POST)
	public Result updateLawForSubmit(@RequestParam("taskId") String taskId, @RequestParam("wkfAppId") String wkfAppId,
			@RequestParam("opinionType") String opinionType, @RequestParam("approvalOpinion") String approvalOpinion,
			@RequestParam("transition") String transition, @RequestParam("regNo") String regNo,
			@RequestParam("nextUser") String nextUser,@RequestBody Map<String, Object> dataMap) throws Exception;
}
