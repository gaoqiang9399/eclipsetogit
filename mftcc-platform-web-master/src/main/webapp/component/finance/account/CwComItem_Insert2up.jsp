<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%-- <%@ include file="/component/include/pub_view.jsp"%> --%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
		<style type="text/css">
		.seqspan{
		 		margin-left: 20px;
		}
	</style>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-10 col-md-offset-1 column margin_top_20">
					<div class="bootstarpTag fourColumn">
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form method="post" id="account_form"  theme="simple" name="operform" action="${webPath}/cwComItem/insertAjax2upKm">
							<dhcc:bootstarpTag property="formaccount0003" mode="query" />
						</form>
					</div>
				</div>
			</div>
			<div class="formRowCenter">
				<c:if test="${acclvlInt < 11}">
					 <dhcc:thirdButton value="保存" action="保存" onclick="ajaxInsertThis('#account_form');"></dhcc:thirdButton>
				</c:if>
				<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose_showDialog();"></dhcc:thirdButton>
			</div>
		</div>
	</body>
	<script type="text/javascript" src="${webPath}/component/finance/account/js/CwComItem_Insert.js"></script>
	<script type="text/javascript">
		//获取要添加的级别
	
		var type = '${acclvlInt}';
		
		var kemuLvl = '${kemuLvl}';
		
		var upaccno = '${accNo}';
		var pvalueType = '${pvalueType}';//支持的哪种格式
		
		if(!type){
			type=2;
		}
		if(!kemuLvl){
			kemuLvl = "二级科目";
		}
		
		//jsp加载完成后调用此function
		 $(function(){
			dealLoadedMethod(type,kemuLvl,upaccno,pvalueType);
		}); 
	</script>
</html>
