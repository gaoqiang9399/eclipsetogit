<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head >
	<title>列表表单</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<script type="text/javascript" >
            $(function(){
                $(function(){
                    MfArchiveBorrowper_List.init();
                });
            });
	</script>
</head>
<body class="overflowHidden">
<div class="container">
	<div class="row clearfix bg-white tabCont">
		<div class="col-md-12 column">
			<div class="btn-div">
				<div class="col-md-2">
					<button type="button" class="btn btn-primary" onclick="MfArchiveBorrowper_List.toApplyInsert();">借阅申请</button>
				</div>
				<div class="col-md-8 text-center">
					<span class="top-title">借阅申请</span>
				</div>
				<div class="col-md-2">
				</div>
			</div>
			<div class="search-div">
				<jsp:include page="/component/include/mySearch.jsp?blockType=3&placeholder=档案名称/档案编号/申请人"/>
			</div>
		</div>
	</div>
	<div class="row clearfix">
		<div class="col-md-12 column">
			<div id="content" class="table_content">
			</div>
		</div>
	</div>
</div>
<%@ include file="/component/include/PmsUserFilter.jsp"%>
<script type="text/javascript" src="${webPath}/component/archives/js/MfArchiveBorrowper_List.js"></script>
</body>
<script type="text/javascript">
    filter_dic = [ {
        "optCode" : "applyTime",
        "optName" : "申请日期",
        "dicType" : "date"
    },{
        "optCode" : "borrowSts",
        "optName" : "申请状态",
        "parm" : ${cusTypeJsonArray},
        "dicType" : "y_n"
    }];
</script>
</html>