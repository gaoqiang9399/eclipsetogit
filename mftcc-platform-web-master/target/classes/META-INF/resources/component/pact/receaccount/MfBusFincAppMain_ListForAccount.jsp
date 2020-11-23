<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<script type="text/javascript" src="${webPath}/component/pact/receaccount/js/MfBusFincAppMain_ListForAccount.js?v=${cssJsVersion}"></script>
		<style type="text/css">
			.pops-value {
				padding:0px;
			}
		</style>
		<script type="text/javascript" >
			$(function(){
                MfBusFincAppMain_ListForAccount.init();
			 });
            function getAccountView() {
                window.location.href=webPath+"/mfBusReceBaseInfo/getReceListPage";
            }
		</script>
	</head>
	<body class="overflowHidden">
	<div class="container">
		<div class="row clearfix bg-white tabCont">
			<div class="col-md-12 column">
				<div class="btn-div">
					<dhcc:pmsTag pmsId="fiveclass-storage">
						<button type="button" class="btn btn-primary" onclick="getAccountView();">账款视角</button>
					</dhcc:pmsTag>
				</div>
				<div class="search-div" id="search-div">
					<jsp:include page="/component/include/mySearch.jsp?blockType=3&placeholder=融资编号/合同号"/>
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
        filter_dic = [{
            "optName" : "融资状态",
            "parm" : ${fincStsJsonArray},
            "optCode" : "fincSts",
            "dicType" : "y_n"
        }, {
            "optName" : "融资金额",
            "parm" : [],
            "optCode" : "putoutAmt",
            "dicType" : "num"
        },{
            "optName" : "申请日期",
            "parm" : [],
            "optCode" : "putoutAppDate",
            "dicType" : "date"
        }
        ];
	</script>
</html>
