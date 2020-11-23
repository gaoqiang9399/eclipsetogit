<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>详情</title>
		<style type="text/css">
			.seqspan{
				 		margin-left: 20px;
			}
	</style>
	</head>
	<!-- <body class="body_bg"> -->
		<%-- <div class="bigform_content">
			<div class="bigForm_content_form">
				<div class="form_title">科目表</div>
				<form method="post" theme="simple" id="operform" name="operform" action="#">
					<dhcc:formTag property="formaccount0001" mode="query" />
				</form>	
			</div>
		</div> --%>
		<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-10 col-md-offset-1 column margin_top_20">
					<div class="bootstarpTag fourColumn">
							<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form method="post" id="countDetailForm" theme="simple" name="operform" action="${webPath}/cwComItem/updateAjax">
							<dhcc:bootstarpTag property="formaccount0001" mode="query"/>
						</form>	
					</div>
				</div>
			</div>
	   		<div class="formRowCenter">
	   			 <dhcc:thirdButton value="保存" action="保存" onclick="ajaxUpdateThis('#countDetailForm');"></dhcc:thirdButton> 
	   			 <dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose_showDialog();"></dhcc:thirdButton>
	   		</div>
   		</div>
	</body>
	<script type="text/javascript" src="${webPath}/component/finance/account/js/CwComItem_Detail.js"></script>
	<script type="text/javascript">
		var type = '${acclvlInt}';
		var kemuLvl = '${kemuLvl}';
		var listbean = '${listbean}';
		
		
		if(!type){
			type=2;
		}
		if(!kemuLvl){
			kemuLvl = "二级科目";
		}
	 $(function(){
			dealkemuLvl(type,kemuLvl,listbean);
		}); 
	</script>
</html>