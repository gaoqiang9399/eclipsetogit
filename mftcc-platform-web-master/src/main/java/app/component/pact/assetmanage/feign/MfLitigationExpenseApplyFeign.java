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
import app.component.pact.assetmanage.entity.MfLitigationExpenseApply;
import app.component.pact.entity.MfBusFincApp;
import app.component.pact.fiveclass.entity.MfPactFiveclass;
import app.component.wkf.entity.Result;
import app.util.toolkit.Ipage;

/**
 * 
 * @ClassName MfLitigationExpenseApplyFeign
 * @Description 
 * @Author Liu Haobin
 * @Date 2018年6月15日 下午4:29:51
 */
@FeignClient("mftcc-platform-factor")
public interface MfLitigationExpenseApplyFeign {

	@RequestMapping(value = "/mfLitigationExpenseApply/getAssetManageList")
	public Ipage getAssetManageList(@RequestBody Ipage ipage,@RequestParam("applyFlag") String applyFlag) throws Exception;
	
	@RequestMapping(value = "/mfLitigationExpenseApply/getDetailByCusNo",method=RequestMethod.POST)
	public List<MfAssetManage> getDetailByCusNo(@RequestParam("cusNo") String cusNo) throws Exception;
	
	@RequestMapping(value = "/mfLitigationExpenseApply/getOverdueCusInfo",method=RequestMethod.POST)
	public MfAssetManage getOverdueCusInfo(@RequestParam("cusNo") String cusNo) throws Exception;
	
	@RequestMapping(value = "/mfLitigationExpenseApply/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;

	@RequestMapping(value = "/mfLitigationExpenseApply/findByAssetId")
	public List<MfLitigationExpenseApply> findByAssetId(@RequestBody MfLitigationExpenseApply mfLitigationExpenseApply) throws Exception;
	
	@RequestMapping(value = "/mfLitigationExpenseApply/mfLitigationExpenseApply")
	public void mfLitigationExpenseApply(@RequestBody MfLitigationExpenseApply mfLitigationExpenseApply) throws Exception;
	
	@RequestMapping(value = "/mfLitigationExpenseApply/getById")
	public MfLitigationExpenseApply getById(@RequestBody MfLitigationExpenseApply mfLitigationExpenseApply) throws Exception;
	
	@RequestMapping(value = "/mfLitigationExpenseApply/update")
	public void update(@RequestBody MfLitigationExpenseApply mfLitigationExpenseApply) throws Exception;
	
	@RequestMapping(value = "/mfLitigationExpenseApply/getTask",method=RequestMethod.POST)
	public TaskImpl getTask(@RequestParam("wkfFinId") String wkfFinId) throws Exception;
	
	@RequestMapping(value = "/mfLitigationExpenseApply/submitProcess",method=RequestMethod.POST)
	public MfLitigationExpenseApply submitProcess(@RequestBody MfLitigationExpenseApply mfLitigationExpenseApply,@RequestParam(value="firstApprovalUser",required=false) String firstApprovalUser) throws Exception;
	
	@RequestMapping(value = "/mfLitigationExpenseApply/updateForSubmit",method=RequestMethod.POST)
	public Result updateForSubmit(@RequestParam("taskId") String taskId, @RequestParam("wkfAppId") String wkfAppId,
			@RequestParam("opinionType") String opinionType, @RequestParam("approvalOpinion") String approvalOpinion,
			@RequestParam("transition") String transition, @RequestParam("regNo") String regNo,
			@RequestParam("nextUser") String nextUser,@RequestBody Map<String, Object> dataMap) throws Exception;

	@RequestMapping(value = "/mfLitigationExpenseApply/isApproval",method=RequestMethod.POST)
	public String isApproval() throws Exception;
	@RequestMapping(value = "/mfLitigationExpenseApply/calculateFeeSum")
	public String calculateFeeSum(@RequestBody MfLitigationExpenseApply mfLitigationExpenseApply) throws Exception;


}
