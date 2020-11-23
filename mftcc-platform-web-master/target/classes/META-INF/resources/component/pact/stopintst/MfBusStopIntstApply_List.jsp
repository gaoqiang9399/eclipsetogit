<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<script type="text/javascript" src="${webPath}/component/pact/stopintst/js/MfBusStopIntstApply_List.js"></script>
		<script type="text/javascript" >
            $(function(){
                $(function(){
                    MfBusStopIntstApply_List.init();
                });
            });
            function getDetailPage(obj,url){
                if(url.substr(0,1)=="/"){
                    url =webPath + url;
                }else{
                    url =webPath + "/" + url;
                }
                top.LoadingAnimate.start();
                window.location.href=url;
                event.stopPropagation();
            }
		</script>
	</head>
	<body class="overflowHidden">
		<div class="container">
			<div class="row clearfix bg-white tabCont">
				<div class="col-md-12">
					<div class="btn-div">
						<div class="col-md-2">
							<button type="button" class="btn btn-primary" onclick="MfBusStopIntstApply_List.toApplyInsert();">新增</button>
						</div>
						<div class="col-md-8 text-center">
							<span class="top-title">停息申请</span>
						</div>
						<div class="col-md-2">
						</div>
					</div>
					<div class="search-div">
						<jsp:include page="/component/include/mySearch.jsp?blockType=3&placeholder=借据号"/>
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
	</body>
	<script type="text/javascript">
        filter_dic = [ {
            "optCode" : "applyDate",
            "optName" : "申请日期",
            "dicType" : "date"
        },{
            "optCode" : "stopSts",
            "optName" : "申请状态",
            "parm" : ${STOP_INTST},
            "dicType" : "y_n"
        }];
	</script>
</html>
