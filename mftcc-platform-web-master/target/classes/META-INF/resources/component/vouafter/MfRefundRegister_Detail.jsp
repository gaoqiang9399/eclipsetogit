<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<title>详情</title>
	<script type="text/javascript">
        var refundId = '${refundId}';
        var relNo = '${relNo}';// 要件业务编号
        var nodeNo = '${nodeNo}';// 节点编号
        var refundQueryFile = '${refundQueryFile}';// 节点编号
        var temBizNo1 = '${relNo}';
        var temParm1 = '${temParm1}';
	</script>
</head>
<body class="overflowHidden bg-white">
<div class="container form-container">
	<div class="scroll-content">
		<div class="col-xs-10 col-xs-offset-1" style="margin-top:20px;">
			<div class="arch_sort" style="border: 0px;">
				<div class="dynamic-block" title="退费登记" name="MfRefundRegisterAction" data-sort="14" data-tablename="mf_vou_after_track">
					<div class="list-table">
						<div class="title">
							<span class="formName"><i class="i i-xing blockDian"></i>退费登记</span>
							<button class=" btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#MfRefundRegisterAction">
								<i class="i i-close-up"></i><i class="i i-open-down"></i>
							</button>
						</div>
						<div disable="true" class="content collapse in" id="MfRefundRegisterAction" style="margin-top: 10px;">
							<form method="post" id="MfRefundRegisterForm" theme="simple" name="operform" action="${webPath}/mfRefundRegister/updateAjax">
								<dhcc:propertySeeTag property="formRefundRegisterdetail" mode="query" />
							</form>
						</div>
					</div>
				</div>
			</div>
			<div class="row clearfix">
				<div class="col-xs-12 column">
					<%@ include file="/component/include/biz_node_plugins.jsp"%><!-- 功能挂载(要件、文档、费用...) -->
				</div>
			</div>
		</div>
	</div>
	<div class="formRowCenter">
		<div id="approvalBtn" class="formRowCenter " style="display:block;">
			<dhcc:thirdButton value="返回" action="返回" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
		</div>
	</div>
</div>
<script type="text/javascript">
    $(function(){
        $(".scroll-content").mCustomScrollbar({
            advanced:{
                //滚动条根据内容实时变化
                updateOnContentResize:true
            }
        });
    });
</script>
</body>
</html>