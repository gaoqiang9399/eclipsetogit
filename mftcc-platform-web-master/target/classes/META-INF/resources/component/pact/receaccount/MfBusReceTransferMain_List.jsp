<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<script type="text/javascript" src="${webPath}/component/pact/receaccount/js/MfBusReceTransferMain_List.js?v=${cssJsVersion}"></script>
		<style type="text/css">
			.pops-value {
				padding:0px;
			}
		</style>
		<script type="text/javascript" >
			$(function(){
                MfBusReceTransferMain_List.init();
			 });
		</script>
	</head>
	<body class="overflowHidden">
	<div class="container">
		<div class="row clearfix bg-white tabCont">
			<div class="col-md-12 column">
				<div class="btn-div">
					<%--<dhcc:pmsTag pmsId="start-of-biz">--%>
						<button type="button" class="btn btn-primary" onclick="MfBusReceTransferMain_List.addReceTransfer();">转让</button>
						<input  name="pactNo" type="hidden" value=""/>
						<input  name="pactId" type="hidden" value=""/>
						<%--</dhcc:pmsTag>--%>
				</div>
				<div class="search-div" id="search-div">
					<jsp:include page="/component/include/mySearch.jsp?blockType=3&placeholder=转让编号/合同号"/>
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
            "optName" : "转让状态",
            "parm" : ${transStsJsonArray},
            "optCode" : "transSts",
            "dicType" : "y_n"
        }, {
            "optName" : "转让金额",
            "parm" : [],
            "optCode" : "transAmtSum",
            "dicType" : "num"
        },{
            "optName" : "申请日期",
            "parm" : [],
            "optCode" : "transAppDate",
            "dicType" : "date"
        }
        ];
	</script>
</html>
