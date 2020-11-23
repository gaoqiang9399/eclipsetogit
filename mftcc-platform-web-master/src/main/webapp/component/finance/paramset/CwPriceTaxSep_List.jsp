<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<link rel="stylesheet" href="${webPath}/component/finance/voucher/css/ui.min.css" />
		<script type="text/javascript" src="<%=webPath %>/component/finance/paramset/js/CwPriceTaxSep_List.js"></script>
		
		<script type="text/javascript" src="${webPath}/layout/view/js/openDiv.js"></script>
		<script type="text/javascript" >
			$(function(){
			   cwPriceTaxList.init();
			 	//addEvent();
			 });
			 
			 function priceTaxInsert(){
			 	cwPriceTaxList.priceTaxInsert();
			 }
			 function getPriceTaxDetail(url){
				 url=webPath+url;
			  	cwPriceTaxList.getPriceTaxDetail(url);
			 }
		</script>
		<style type="text/css">
			.voucherLink{
			    cursor: pointer;
			    text-decoration: underline;
			    margin: 5px;
			    color: #555;
			}
		</style>
	</head>
	<body class="overflowHidden">
		<div class="container">
			<div class="row clearfix bg-white tabCont">
				<div class="col-md-12">
				<!-- 	列表上部的按钮区域（单独一行的形式），如有需要去掉注释，改为实际需要即可。 -->
					<div class="btn-div">
						<button type="button" class="btn btn-primary" onclick="priceTaxInsert();">新增</button>
					</div>
					
					<!-- 自定义筛选+智能搜索区域，参数请看说明。根据类型不同，在此页面看可以相应的调整布局。
					blockType：
						1——//头部只有自定义筛选的情况（无搜索框）
						2——//仅右侧有搜索框的情况，占3列。左侧由引用页面自定义
						3——//头部左侧自定义筛选，右侧搜索框的情况
						4——//头部左侧自定义筛选（无更多选项，财务模块在用），右侧有搜索框的情况
					placeholder：
						智能搜索框的提示信息内容。
					-->
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
