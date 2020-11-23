<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/wkf/Wkf_Base.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head >
	<title>列表表单</title>
	<script type="text/javascript" >
        var appId = '${appId}';
        var cusNo = '${cusNo}';
        var pactId = '${pactId}';
        var appNo = '${appNo}';
        var nodeNo = '${nodeNo}';
        var ajaxData = '${ajaxData}';
        $(function(){
			//滚动条
			myCustomScrollbarForForm({
				obj: ".scroll-content",
				advanced: {
					updateOnContentResize: true
				}
			});
			$("select[name=opinionType] option:contains(否决)").hide();
			$(".formAdd-btn").hide();
        });

        function doSubmit(obj) {
            var flag = submitJsMethod($(obj).get(0), '');
            if (flag) {
                var opinionType = $("select[name=opinionType]").val();
                var approvalOpinion = $("textarea[name=approvalOpinion]").val();
                commitProcess(webPath + "/certiInfo/approveSubmitAjax?&appId=" + appId + "&appNo=" + appNo + '&opinionType=' + opinionType + '&approvalOpinion=' + approvalOpinion, obj, 'sp');
            }
        }

	</script>
</head>
<body class="overflowHidden">
<div class="container">
	<div class="scroll-content" style="padding-bottom: 50px;">
		<div class="col-md-10 col-md-offset-1 column margin_top_20">
			<%--<div class="bootstarpTag fourColumn">--%>
				<%--<div class="form-tips">说明：</div>--%>
			<%--</div>--%>
				<div class="list-table">
				<div class="title">
					<span><i class="i i-xing blockDian"></i>权证登记审批</span>
					<button class="btn btn-link formAdd-btn" onclick="CertiInfo_InputList.inputNew();" title="新增"><i class="i i-jia3"></i></button>
					<button class=" btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#receAccountList">
						<i class="i i-close-up"></i><i class="i i-open-down"></i>
					</button>
				</div>
					<div class="bootstarpTag">
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form id="cus-form"><!-- certiInfo信息 -->
							<dhcc:bootstarpTag property="formapplyCertiInfo" mode="query" />
						</form>
					</div>
				<div class="content collapse in" id="receAccountList" name="receAccountList">
					<dhcc:tableTag property="tablewkfcertiinfo" paginate="certiInfoList" head="true"></dhcc:tableTag>
				</div>

			</div>
			<%@ include file="/component/include/biz_node_plugins.jsp"%><!-- 功能挂载(要件、文档、费用...) -->
		</div>
	</div>

	<div class="formRowCenter">
		<dhcc:thirdButton value="提交" action="提交"  onclick="doSubmit('#cus-form');"></dhcc:thirdButton>
		<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose_task();"></dhcc:thirdButton>
	</div>
</div>
</body>
</html>