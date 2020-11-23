
package app.component.pact.listener;



import com.dhcc.workflow.api.listener.NoticeListener;
import com.dhcc.workflow.api.model.OpenExecution;
import com.dhcc.workflow.api.task.Assignable;
import com.dhcc.workflow.pvm.internal.model.ExecutionImpl;
import com.dhcc.workflow.pvm.internal.task.TaskConstants;
import com.dhcc.workflow.pvm.internal.task.TaskImpl;

import app.base.SpringUtil;
import app.component.app.entity.MfBusApply;
import app.component.app.feign.MfLoanApplyFeign;
import app.component.pact.entity.MfBusFincApp;
import app.component.wkf.feign.TaskFeign;
import app.component.wkfinterface.WkfInterfaceFeign;
import cn.mftcc.util.StringUtil;

public class MfBusFincListener implements NoticeListener {
	private static final long serialVersionUID = 5763178870485205973L;

	@Override
	public void notice(Assignable arg0, OpenExecution arg1) throws Exception {
		ExecutionImpl execution = (ExecutionImpl)arg1;
		if(null!=execution.getResult()&& "pass".equals(execution.getResult())){
			execution.getHistoryActivityInstanceDbid();
			String appNo=execution.getAppId();
			MfBusFincApp mfBusFincApp = new MfBusFincApp();
			mfBusFincApp.setFincId(appNo);
			MfLoanApplyFeign  mfLoanApplyBo = (MfLoanApplyFeign)SpringUtil.getBean("mfLoanApplyFeign");
			mfBusFincApp = mfLoanApplyBo.getByFincIdOrwkfId(mfBusFincApp);
			
			MfBusApply mfBusApply = new MfBusApply();
			mfBusApply.setAppId(mfBusFincApp.getAppId());
			mfBusApply = mfLoanApplyBo.getByIdOrAppId(mfBusApply);
			if(StringUtil.isEmpty(mfBusApply.getCusNoFund())){
				WkfInterfaceFeign wkfInterface = (WkfInterfaceFeign)SpringUtil.getBean("wkfInterfaceFeign");
				TaskImpl  task = wkfInterface.getTask(appNo,null);
				TaskFeign taskFeign = (TaskFeign)SpringUtil.getBean(TaskFeign.class);
				String msg=taskFeign.complete(task.getId(),TaskConstants.PASS,"");
			}
		}
		
	}

}
