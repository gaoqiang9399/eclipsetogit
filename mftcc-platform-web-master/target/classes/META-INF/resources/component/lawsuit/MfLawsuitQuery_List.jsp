<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
	</head>
	<body class="overflowHidden">
		<div class="container">
			<div class="row clearfix bg-white tabCont">
				<div class="col-md-12 column">
					<div class="btn-div">
						<%--法律诉讼--%>
							<div class="col-md-2">
								<button type="button" class="btn btn-primary" onclick="lawsuitQueryList.input()">新增</button>
							</div>
							<div class="col-md-8 text-center">
								<span class="top-title">法律诉讼</span>
							</div>
					</div>
					<div class="search-div">
						<jsp:include page="/component/include/mySearch.jsp?blockType=3&placeholder=案件名称/案件编号"/>
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
<script type="text/javascript" src="${webPath}/component/lawsuit/js/MfLawsuitQuery_List.js"></script>
	<script type="text/javascript">
		$(function(){
			lawsuitQueryList.init();
		});
		function getById(obj,url,flag){
			if(url.substr(0,1)=="/"){
				//url =webPath + url;
				url=url;
			}else{
				//url =webPath + "/" + url;
				url="/"+url;
			}
			//lawsuitQueryList.getById(obj,url,flag);
            top.openBigForm(url+"&flag=show","案件详情", function(){});
		};
        function getDetailCus(obj,url){
            if(url.substr(0,1)=="/"){
                url =webPath + url;
            }else{
                url =webPath + "/" + url;
            }
            top.openBigForm(url,"客户详情", function(){});
            //window.location.href=url;

        };
		/*我的筛选加载的json*/
		filter_dic = [{
				"optCode" : "caseType",
				"optName" : "诉讼类型",
				"parm" : ${caseTypeJsonArray},
				"dicType" : "y_n"
			},{
				"optCode" : "caseTime",
				"optName" : "起诉时间",
				"dicType" : "date"
			},{
				"optCode" : "caseState",
				"optName" : "诉讼阶段",
				"parm" : ${caseStateJsonArray},
				"dicType" : "y_n"
			},
			 {
            		"optName" : "诉讼金额",
					"parm" : [],
					"optCode" : "caseAmt",
					"dicType" : "num"
              }];
	</script>
</html>
