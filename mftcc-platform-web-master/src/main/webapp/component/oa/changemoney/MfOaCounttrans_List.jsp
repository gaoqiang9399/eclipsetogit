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
					<dhcc:pmsTag pmsId="oa-trans-apply-btn">
						<button type="button" class="btn btn-primary pull-left"id="consInsert">申请转账</button>
					</dhcc:pmsTag>
				</div>
				<div class="search-div">
					<jsp:include page="/component/include/mySearch.jsp?blockType=2&placeholder=账户名称/账号/转账事由"/>
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
     <script type="text/javascript" src="${webPath}/component/oa/changemoney/js/MfOaCounttrans_List.js"></script>
<script type="text/javascript">
MfOaCounttransList.path = "${webPath}";
		$(function() {
			MfOaCounttransList.init(); 
		});	
		
		/*我的筛选加载的json*/
		filter_dic = [
		     
          ];
          </script>
</html>
