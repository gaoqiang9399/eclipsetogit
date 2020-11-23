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
import app.component.pact.assetmanage.entity.MfLitigationExpenseInout;
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
public interface MfLitigationExpenseInoutFeign {

	
	@RequestMapping(value = "/mfLitigationExpenseInout/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;
	
	@RequestMapping(value = "/mfLitigationExpenseInout/insertInoutAjax")
	public void insertInoutAjax(@RequestBody MfLitigationExpenseInout  mfLitigationExpenseInout) throws Exception;

	
}
