<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>列表</title>
	<script type="text/javascript" src="${webPath}/component/risk/audit/js/MfBusRiskAudit_list.js"></script>
	<script type="text/javascript" >
        $(function(){
            MfBusRiskAudit_list.init();
        });
	</script>
	<style type="text/css">
		.change-td-color-0{
			color: #ff0000 !important;
		}
		.ui-dialog-title{
			font-size: medium;/*修改指派弹框的标题字体样式*/
		}
	</style>
</head>
<body class="overflowHidden">
<div class="container">
	<div class="row clearfix bg-white tabCont">
		<div class="col-md-12 column">
			<div class="btn-div">
				<div class="col-md-2">
				</div>
				<div class="col-md-8 text-center">
					<span class="top-title">风控稽核列表</span>
				</div>
			</div>


			<div class="search-div" id="search-div">
				<jsp:include page="/component/include/mySearch.jsp?blockType=3&placeholder=客户名称"/>
			</div>
		</div>
	</div>
	<div class="row clearfix">
		<div class="col-md-12 column">
			<div id="content" class="table_content"  style="height: auto;">
			</div>
		</div>
	</div>
</div>
<%@ include file="/component/include/PmsUserFilter.jsp"%>
</body>
<script type="text/javascript">
    /*我的筛选加载的json*/
    filter_dic =[
        {
            "optName": "稽核日期",
            "optCode":"auditDate",
            "dicType":"date"
        },

        {"optCode":"auditType","optName":"稽核类型","parm":[{"optName":"项目稽核","optCode":"0"},{"optName":"合同稽核","optCode":"1"}],"dicType":"y_n"},
        {"optCode":"appSts","optName":"稽核状态","parm":[{"optName":"未提交","optCode":"0"},{"optName":"审批中","optCode":"1"},{"optName":"通过","optCode":"4"},{"optName":"否决","optCode":"5"}],"dicType":"y_n"}

    ];

</script>
</html>