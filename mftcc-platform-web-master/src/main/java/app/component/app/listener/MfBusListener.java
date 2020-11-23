
package app.component.app.listener;



import com.dhcc.workflow.api.listener.NoticeListener;
import com.dhcc.workflow.api.model.OpenExecution;
import com.dhcc.workflow.api.task.Assignable;
import com.dhcc.workflow.pvm.internal.model.ExecutionImpl;
import com.dhcc.workflow.pvm.internal.task.TaskConstants;
import com.dhcc.workflow.pvm.internal.task.TaskImpl;

import app.base.SpringUtil;
import app.component.app.entity.MfBusApply;
import app.component.app.feign.MfLoanApplyFeign;
import app.component.common.BizPubParm;
import app.component.doc.entity.DocBizManageParam;
import app.component.wkf.feign.TaskFeign;
import app.component.wkfinterface.WkfInterfaceFeign;
import cn.mftcc.util.StringUtil;

public class MfBusListener implements NoticeListener {
	private static final long serialVersionUID = 5763178870485205973L;

	@Override
	public void notice(Assignable arg0, OpenExecution arg1) throws Exception {
		ExecutionImpl execution = (ExecutionImpl)arg1;
		if(null!=execution.getResult()&& "pass".equals(execution.getResult())){
			execution.getHistoryActivityInstanceDbid();
			String activeActivityNames = execution.getActiveActivityNames();
			//String  activeActivityName = activeActivityNames.split("\\[")[0].split("\\]")[0];
			String appNo=execution.getAppId();
			MfBusApply mfBusApply = new MfBusApply();
			mfBusApply.setAppId(appNo);
			MfLoanApplyFeign  mfLoanApplyBo = (MfLoanApplyFeign)SpringUtil.getBean("mfLoanApplyFeign");
			mfBusApply = mfLoanApplyBo.getByIdOrAppId(mfBusApply);
			if(StringUtil.isEmpty(mfBusApply.getCusNoFund())){
				WkfInterfaceFeign wkfInterface = (WkfInterfaceFeign)SpringUtil.getBean("wkfInterfaceFeign");
				TaskImpl  task = wkfInterface.getTask(appNo,null);
				TaskFeign taskFeign = (TaskFeign)SpringUtil.getBean(TaskFeign.class);
				String msg=taskFeign.complete(task.getId(),TaskConstants.PASS,"");
				if("[orgConfirm]".equals(activeActivityNames)){//跳过机构确认节点时，在此处初始化放款确认的要件信息					
					//此处需要调用文档接口初始化文档相关信息
					DocBizManageParam dm = new DocBizManageParam();
					dm.setScNo(BizPubParm.SCENCE_TYPE_DOC_FINC_APP); //场景编号
					dm.setRelNo(mfBusApply.getCusNo());//合同编号
					//维度  注：根据字典项业务代码来组合，维度为空取默认，如果默认未配置则报错
					dm.setCusNo(mfBusApply.getCusNo());//客户号
					dm.setCusName(mfBusApply.getCusName());//客户名称
					//docInterface.initiaCus(dm);
				}
			}
		}
		
	}


}
