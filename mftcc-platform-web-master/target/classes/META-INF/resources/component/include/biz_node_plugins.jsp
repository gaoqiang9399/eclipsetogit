<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- 业务流程节点-功能挂载jsp -->
<script type="text/javascript">
	var docOffline =(typeof (docOffline) == 'undefined') ? '0' : docOffline;//线下要件确认标志
	var cusNo_pl = (typeof (cusNo) == 'undefined') ? '' : cusNo;// 客户号
	var appId_pl = (typeof (appId) == 'undefined') ? '' : appId;// 申请号
	var pactId_pl = (typeof (pactId) == 'undefined') ? '' : pactId;// 合同号
	var fincId_pl = (typeof (fincId) == 'undefined') ? '' : fincId;// 借据号
	var fincChildId_pl = (typeof (fincChildId) == 'undefined') ? '' : fincChildId;// 子借据号
	var repayDetailId_pl = (typeof (repayDetailId) == 'undefined') ? '' : repayDetailId;// 还款历史id
	var extensionApplyId_pl = (typeof (extensionApplyId) == 'undefined') ? '' : extensionApplyId;//展期业务申请id
	var feePower_pl = (typeof (feePower) == 'undefined') ? '' : feePower;// 费用权限字段 1：改；2：查；3：收'
	var querySaveFlag_pl =(typeof (querySaveFlag) == 'undefined') ? '0' : querySaveFlag; //电子文档列表查询方法（1-该节点只查询已经保存过的文档0-查询全部）
	var nodeNo = '${nodeNo}';// 节点编号
    var approvalNodeNo = '${approvalNodeNo}';// 用于加载配置在审批节点要件或模板, primary_apply_approval-初选审批, apply_approval-融资审批, contract_approval-合同审批, certidInfo_appr-权证审批, putout_approval-支用审批

	// 要件
	var scNo = nodeNo;// 要件场景 ('${scNo}'不再使用)
	var relNo_pl = (typeof (relNo) == 'undefined') ? appId_pl : relNo;// 要件业务编号(业务页面有定义值时使用业务页面的值，如果没指定则默认申请号)
	relNo_pl=extensionApplyId_pl==""||extensionApplyId_pl==null?relNo_pl:extensionApplyId_pl;
	var docParm = 'cusNo=' + cusNo_pl + '&appId=' + appId_pl + '&pactId=' + pactId_pl + '&fincId=' + fincId_pl + '&relNo=' + relNo_pl + '&scNo=' + scNo;
	if(typeof (refundQueryFile) != "undefined" && refundQueryFile != null && refundQueryFile != ""){
        docParm = docParm + '&query=' + refundQueryFile;
	}
	// 文档
	var temBizNo = appId_pl;// 文档关联的业务主键，可以是申请号、客户号、申请号、借据号及其他功能编号
	temBizNo=extensionApplyId_pl==""||extensionApplyId_pl==null?appId_pl:extensionApplyId_pl;
	var temParm = 'cusNo=' + cusNo_pl + '&appId=' + appId_pl + '&pactId=' + pactId_pl + '&fincId=' + fincId_pl + '&repayDetailId=' + repayDetailId_pl;// 文档书签取值依赖条件，目前支持appId pactId cusNo fincId repayDetailId
    if("putout_print" == nodeNo){//出账签约环节按借据出
        temBizNo = fincId_pl;
    }
    if("loanAfterExamine" == nodeNo){//保后跟踪
        temBizNo = relNo_pl;
    }
    if(typeof (temBizNo1) != "undefined"){
        temBizNo = temBizNo1;
    }
    if(typeof (temParm1) != "undefined"){
        temParm = temParm1;
    }
	// 费用
// 	var feePower = '';//权限字段1：改；2：查；3：收'
	var feeParm = 'nodeNo=' + nodeNo + '&appId=' + appId_pl + '&feePower=' + feePower_pl;
</script>

<!-- 文档 -->
<div class="row clearfix">
	<%@ include file="/component/model/templateIncludePage.jsp"%>
</div>

<!-- 要件 -->
<div class="row clearfix">
	<%@ include file="/component/doc/webUpload/uploadutil.jsp"%>
</div>

<!-- 费用 -->
<div class="row clearfix">
	<%@ include file="/component/calc/fee/pubNodeBindFeePage.jsp"%>
</div>


<%--
		<%@ include file="/component/include/biz_node_plugins.jsp"%><!-- 功能挂载(要件、文档、费用...) -->

		scNo = WKF_NODE.reinsurance_policy.getScenceTypeDoc();// 要件场景
		nodeNo = WKF_NODE.reinsurance_policy.getNodeNo();// 功能节点编号
--%>
