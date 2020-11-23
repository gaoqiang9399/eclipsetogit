package app.component.wkfBus.decision;

import java.util.List;

import com.dhcc.workflow.WFUtil;
import com.dhcc.workflow.api.Execution;
import com.dhcc.workflow.api.ExecutionService;
import com.dhcc.workflow.api.model.OpenExecution;
import com.dhcc.workflow.api.wfdl.DecisionHandler;

import app.base.ServiceException;
import app.base.SpringUtil;
import app.component.app.entity.MfBusApply;
import app.component.appinterface.AppInterfaceFeign;
import app.component.collateral.entity.MfBusCollateralDetailRel;
import app.component.collateral.entity.PledgeBaseInfo;
import app.component.collateral.feign.MfBusCollateralDetailRelFeign;
import app.component.collateral.feign.PledgeBaseInfoFeign;

/**
 * 是否走担保登记的判定节点<br>
 * 
 * 节点提交后查询当前业务的担保信息<br>
 * 是否有担保信息将作为是否走担保登记节点(storage_confirm)的判断条件<br>
 * 
 * @author WangChao
 */
public class StorageConfirmDecisionHandler implements DecisionHandler {
	private static final long serialVersionUID = 2390845355211726770L;

	private AppInterfaceFeign appInterfaceFeign;
	private MfBusCollateralDetailRelFeign mfBusCollateralDetailRelFeign;
	private PledgeBaseInfoFeign pledgeBaseInfoFeign;

	@Override
	public String decide(OpenExecution arg0) {
		String result = "isFalse";

		appInterfaceFeign = SpringUtil.getBean("appInterfaceFeign", AppInterfaceFeign.class);
		mfBusCollateralDetailRelFeign = SpringUtil.getBean("mfBusCollateralDetailRelFeign", MfBusCollateralDetailRelFeign.class);
		pledgeBaseInfoFeign = SpringUtil.getBean("pledgeBaseInfoFeign", PledgeBaseInfoFeign.class);

		String appId = (String) arg0.getVariable("appId");// 申请号

		// 查询当前申请信息
		MfBusApply mfBusApply = null;
		try {
			mfBusApply = appInterfaceFeign.getMfBusApplyByAppId(appId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}

		ExecutionService executionService = WFUtil.getExecutionService();
		Execution execution = executionService.findExecutionByAppId(mfBusApply.getWkfAppId());
		if (null != execution) {
			// 查询当前业务押品列表
			String collateralType = "pledge";
			List<MfBusCollateralDetailRel> bcdrList = mfBusCollateralDetailRelFeign.getCollateralDetailRelList(appId,collateralType);

			for (MfBusCollateralDetailRel bcdr : bcdrList) {
				// 查询抵质押物
				PledgeBaseInfo pledgeBaseInfo = new PledgeBaseInfo();
				pledgeBaseInfo.setPledgeNo(bcdr.getCollateralId());
				pledgeBaseInfo = pledgeBaseInfoFeign.getById(pledgeBaseInfo);

				if (pledgeBaseInfo != null) {// 抵质押物存在
					result = "isTrue";
					break;
				}
			}
		}

		return result;
	}
}
