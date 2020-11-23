package app.component.pact.feerefund.controller;

import app.base.User;
import app.component.common.BizPubParm;
import app.component.pact.entity.MfBusPact;
import app.component.pact.feerefund.entity.MfBusFeeRefund;
import app.component.pact.feerefund.entity.MfBusFeeRefundHis;
import app.component.pact.feerefund.feign.MfBusFeeRefundFeign;
import app.component.pact.feerefund.feign.MfBusFeeRefundHisFeign;
import app.component.pact.feign.MfBusPactFeign;
import app.component.prdctinterface.PrdctInterfaceFeign;
import app.component.wkfinterface.WkfInterfaceFeign;
import app.tech.oscache.CodeUtils;
import com.core.domain.screen.FormData;
import com.core.domain.screen.OptionsList;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.dhcc.workflow.pvm.internal.task.TaskImpl;
import net.sf.json.JSONArray;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value ="/mfBusFeeRefundHis")
public class MfBusFeeRefundHisController extends BaseFormBean {

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;
    @Autowired
    private MfBusFeeRefundFeign mfBusFeeRefundFeign;
    @Autowired
    private WkfInterfaceFeign wkfInterfaceFeign;
    @Autowired
    private MfBusFeeRefundHisFeign mfBusFeeRefundHisFeign;
    @Autowired
    private PrdctInterfaceFeign prdctInterfaceFeign;
    @Autowired
    private MfBusPactFeign mfBusPactFeign;


    /**
     * 审批页面
     * @param model
     * @param
     * @return
     * @throws Exception
     */
    @RequestMapping("/getViewPoint")
    public String getViewPoint(Model model, String feeId, String activityType, String hideOpinionType)throws Exception{
        FormService formService = new FormService();
        ActionContext.initialize(request, response);
        MfBusFeeRefund mfBusFeeRefund = new MfBusFeeRefund();
        MfBusFeeRefundHis mfBusFeeRefundHis = new MfBusFeeRefundHis();
        try {
            TaskImpl taskAppro = wkfInterfaceFeign.getTask(feeId, null);// 当前审批节点task
            String nodeNo = taskAppro.getActivityName();// 审批流程中当前审批节点编号

            mfBusFeeRefund.setFeeId(feeId);
            mfBusFeeRefund = mfBusFeeRefundFeign.getById(mfBusFeeRefund);
            MfBusPact mfBusPact = new MfBusPact();
            mfBusPact.setPactId(mfBusFeeRefund.getPactId());
            mfBusPact = mfBusPactFeign.getById(mfBusPact);

            mfBusFeeRefundHis.setFeeId(feeId);
            List<MfBusFeeRefundHis> list  = mfBusFeeRefundHisFeign.getListOrderByDESC(mfBusFeeRefundHis);
            if (list != null && list.size() > 0) {
                mfBusFeeRefundHis = list.get(0);
                PropertyUtils.copyProperties(mfBusFeeRefund, mfBusFeeRefundHis);
            }else{
                mfBusFeeRefund.setAppAmtReply(mfBusFeeRefund.getAppAmt());
            }
            String formId ;
            if(BizPubParm.fee_refund_confirm.equals(taskAppro.getActivityName())){
                formId = prdctInterfaceFeign.getFormId(BizPubParm.fee_refund_type,BizPubParm.WKF_NODE.fee_refund_confirm,null, null, User.getRegNo(request));
            }else{
                formId = prdctInterfaceFeign.getFormId(BizPubParm.fee_refund_type,BizPubParm.WKF_NODE.fee_refund_approve,null, null, User.getRegNo(request));
            }
            FormData formFeeRefund = formService.getFormData(formId);

            getObjValue(formFeeRefund, mfBusPact);
            getObjValue(formFeeRefund, mfBusFeeRefund);
            // 处理审批意见类型
            Map<String, String> opinionTypeMap = new HashMap<String, String>();
            opinionTypeMap.put("hideOpinionType", hideOpinionType); // 隐藏审批类型
            opinionTypeMap.put("processDefinitionId", taskAppro.getProcessDefinitionId());// 流程id
            opinionTypeMap.put("nodeNo", nodeNo);// 当前节点编号
            List<OptionsList> opinionTypeList = new CodeUtils().getOpinionTypeList(activityType,
                    taskAppro.getCouldRollback(), opinionTypeMap);
            this.changeFormProperty(formFeeRefund, "opinionType", "optionArray", opinionTypeList);
            // 获得当前审批岗位前面审批过得岗位信息
            JSONArray befNodesjsonArray = new JSONArray();
            befNodesjsonArray = wkfInterfaceFeign.getBefNodes1(taskAppro.getId(), User.getRegNo(request));
            request.setAttribute("befNodesjsonArray", befNodesjsonArray);
            model.addAttribute("befNodesjsonArray", befNodesjsonArray);
            model.addAttribute("formFeeRefund", formFeeRefund);
            model.addAttribute("feeId", feeId);
            model.addAttribute("mfBusFeeRefund", mfBusFeeRefund);
            model.addAttribute("taskId", taskAppro.getId());
            model.addAttribute("query", "");
            model.addAttribute("formId", formId);
            model.addAttribute("nodeNo", nodeNo);
            model.addAttribute("scNo", BizPubParm.fee_refund_type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/component/pact/feerefund/MfBusFeeRefundView";
    }
}
