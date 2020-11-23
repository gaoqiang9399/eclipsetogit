<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<title></title>
		<script type="text/javascript">
			var basepath = '${webPath}';
			var cusNo='${dataMap.cusNo}';
			function refreshRelation(){
				
				window.location.href=webPath+"/mfCusRelation/getListPage?cusNo="+cusNo;
			}
			function busApplySearch(){
				window.location.href=webPath+"/mfBusStageSearch/getListPage";
			}
			var baseType='2';
		</script>
		<script type="text/javascript" src="${webPath}/component/cus/relation/js/d3.js" ></script>
		<script type="text/javascript" src="${webPath}/component/cus/relation/js/relationForView.js" ></script>
		<link rel="stylesheet" href="${webPath}/UIplug/Font-Awesome/css/font-awesome.min.css" />
		<link rel="stylesheet" href="${webPath}/component/cus/relation/css/relation.css" />
		
	</head>
	<body>
	
		<div class="row  bg-white">
				<div class="col-md-12 column" style="height: 50px; padding: 8px 0px 8px 15px;">
					<div class="search-div">
						<button type="button" class="btn btn-primary" onclick="refreshRelation();">查看关联关系列表</button>
						<%--<button type="button" class="btn btn-primary" onclick="busApplySearch()">业务查询</button>--%>

					</div>
				</div>
		</div>
		<div class="mao-screen-area" id="screenArea">
			<div id="main" class="mao-main"></div>
			<div class="mao-toolbar">
				<ul>
					
					<li onclick="maoScale(1)"><i class="fa fa-plus"></i></li>
					<li onclick="maoScale(2)"><i class="fa fa-minus"></i></li>
				</ul>
				<ul>
					<!-- <li onclick="changeScreen(this)"><i class="fa fa-expand"></i></li> -->
					<li onclick="maoRefresh()"><i class="fa fa-refresh"></i></li>
					<li onclick="saveImg()"><i class="fa fa-save"></i></li>
				</ul>
			</div>
		</div>

		
	</body>
</html>