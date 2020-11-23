<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		
		<link rel="stylesheet" href="${webPath}/component/finance/voucher/css/ui.min.css" />
		<script type="text/javascript" src="<%=webPath %>/component/finance/paramset/js/CwJiti_List.js"></script>
		<script type="text/javascript" src="${webPath}/layout/view/js/openDiv.js"></script>
		<style type="text/css">
			.voucherLink{
			    cursor: pointer;
			    text-decoration: underline;
			    margin: 5px;
			    color: #555;
			}
		</style>
		<script type="text/javascript" >
			$(function(){
			   cwJiTiList.init();
			 	//addEvent();
			 });
			 function getJtItemByjtId(url){
				cwJiTiList.getJtItemByjtId(url);
			}
			
		</script>
	</head>
	<body class="overflowHidden">
		<div class="container">
			<div class="row clearfix bg-white tabCont">
				<div class="col-md-12">
					<!-- 列表上部的按钮区域（单独一行的形式），如有需要去掉注释，改为实际需要即可。-->
					<div class="btn-div">
						<button type="button" class="btn btn-primary" onclick="cwJiTiList.jiTiInsert();">新增</button>
					</div>
					<div class="search-div">
						<jsp:include page="/component/include/mySearch.jsp?blockType=2&placeholder="/>
					</div>
				</div>
			</div>
			<div class="row clearfix">
				<div class="col-md-12">
					<div id="content" class="table_content" style="height: auto;">
					</div>
				</div>
			</div>
		</div>
	</body>
	
</html>
