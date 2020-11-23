<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head >
	<title>列表表单</title>
	<script type="text/javascript" src="${webPath}/component/pact/receaccount/js/MfBusReceBaseInfo_InputList.js?v=${cssJsVersion}"></script>
	<script type="text/javascript" >
        var appId = '${appId}';
        var cusNo = '${cusNo}';
        $(function(){
            MfBusReceBaseInfo_InputList.init();
        });
	</script>
</head>
<body class="overflowHidden">
<div class="container">
	<div class="scroll-content">
		<div class="col-md-10 col-md-offset-1 column margin_top_20">
			<%--<div class="bootstarpTag fourColumn">--%>
				<%--<div class="form-tips">说明：</div>--%>
			<%--</div>--%>
			<div class="list-table">
				<div class="title">
					<span><i class="i i-xing blockDian"></i>应收账款信息</span>
					<button class="btn btn-link formAdd-btn" onclick="MfBusReceBaseInfo_InputList.insertReceBaseInfo();" title="新增"><i class="i i-jia3"></i></button>
					<button class=" btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#receAccountList">
						<i class="i i-close-up"></i><i class="i i-open-down"></i>
					</button>
				</div>
				<div class="content collapse in" id="receAccountList" name="receAccountList">
					<dhcc:tableTag property="tablereceInfoCollectList" paginate="mfBusReceBaseInfoList" head="true"></dhcc:tableTag>
				</div>
			</div>
			<%@ include file="/component/include/biz_node_plugins.jsp"%><!-- 功能挂载(要件、文档、费用...) -->
		</div>
	</div>

	<div class="formRowCenter">
		<dhcc:thirdButton value="跳过" action="跳过"  onclick="MfBusReceBaseInfo_InputList.doSkip();"></dhcc:thirdButton>
		<dhcc:thirdButton value="提交" action="提交"  onclick="MfBusReceBaseInfo_InputList.doSubmit();"></dhcc:thirdButton>
		<dhcc:thirdButton value="关闭" action="取消" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
	</div>
</div>
</body>
</html>