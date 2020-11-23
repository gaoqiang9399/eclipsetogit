<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>列表</title>
	<style type="text/css">
		#repayHisDetailInfo table{
			font-size: 14px;
		}
		#repayHisDetailInfo table td{
			font-size: 12px;
			vertical-align: middle;
		}
		#repayHisDetailInfo table tr{
			height: 30px;
		}
		.list-table-detail {
			margin-top: 27px;
		}
		.list-table-detail .content{
			padding-left: 15px;
			padding-top: 0px;
		}
		.list-table-detail .ls_list thead tr {
			height: 47px;
			line-height: 47px;
			color: #000000;
		}

		.list-table-detail .ls_list thead tr th {
			font-size: 14px;
			font-weight: normal;
			color: #000000;
			padding: 2px 0px 2px 0px;
			border: 1px solid #e9ebf2;
		}
		.list-table-detail .ls_list tbody tr {
			height: 40px ;
			line-height: 40px;
			font-size: 14px;
			color: #7f7f7f;
			background: #ffffff;
			border-bottom: 1px solid #f3f3f3;
		}

		.list-table-detail .ls_list tbody tr:hover {
			background: #E0F4F7;
		}

		.list-table-detail .ls_list tbody tr td {
			border-top: none ;
			padding: 0px 0px ;
			line-height: 40px;
			vertical-align: top;
			color: #7f7f7f;
		}
		.list-table-detail .ls_list tbody tr td input{
			height:45px;
		}
		.list-table-detail .ls_list tbody tr td {
			border-top: none !important;
			padding: 0px 0px !important;
			line-height: 40px;
			vertical-align: top;
			color: #7f7f7f;
		}
	</style>
	<script type="text/javascript" >
        $(function(){
            $.ajax({
                url : webPath+"/mfRepayHistory/findRepayHisDetailByPageAjax",
                data : {
                    repayId : '${repayId}',
                },
                type : "post",
                dataType : "json",
                success : function(data) {
                    if(data.flag == "error"){
                        alert(data.msg, 0);
                    }else{
                        $("#repayHisDetailInfo").empty().html(data.tableHtmlRepayHisDetail);
                    }
                },
                error : function(data) {
                    window.top.alert(data.msg, 0);
                }
            });
        });
	</script>
</head>
<body class="overflowHidden">
<div class="container">
	<div class="row clearfix">
		<div class="col-xs-12 column" style="width:98%;">
			<div class="list-table-detail">
				<div class="content collapse in" id="repayHisDetailInfo" name="repayHisDetailInfo">
				</div>
			</div>
		</div>
	</div>
</div>
</body>
</html>
