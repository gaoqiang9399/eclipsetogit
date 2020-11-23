<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
		<script type="text/javascript" src='${webPath}/component/eval/js/AppProperty.js'> </script> 
		<style type="text/css">
			.subBtn{
				margin-left:93px;
				margin-top: -30px;
				background-color:#fff
			}
		</style>
		<script type="text/javascript">
			var jsonObj= ${json};
		var allUseablelist = jsonObj.allUseablelist;
		var byformEnList = jsonObj.byformEnList;
		$(function(){
			initData();
		});
		</script>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-8 col-md-offset-2 column margin_top_20">
					<div class="bootstarpTag">
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form  method="post" id="evalSubInsertForm" theme="simple" name="operform" action="${webPath}/appProperty/insertAjax">
							<div class="form-content">
							<dhcc:bootstarpTag property="formapp0091" mode="query"/>
							</div>
						</form>
					</div>
						<div class="bigform_content col_content">
								<div class="title"><h5 >评级指标子项</h5>
									<button id="addEvalSub" class='btn list-add color_theme subBtn hide' onclick='addEvalSub();' disabled title='新增'>
											<i class='i i-jia3'></i>
									</button>
									<button id="addEvalIndexSub" class='btn list-add color_theme subBtn hide' onclick='addEvalIndexSub();' disabled title='新增'>
											<i class='i i-jia3'></i>
									</button>
								</div>
								<div id="evalIndexSubList" class="table_content">
									<dhcc:tableTag paginate="mfEvalIndexSubList" property="tableevalsub0001" head="true" />
								</div> 
						</div>
				</div>
			</div>
			<div class="formRowCenter">
	   			<dhcc:thirdButton value="保存" action="保存" onclick="ajaxInsertOrUpdateThis('#evalSubInsertForm');"></dhcc:thirdButton>
	   			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose_click();"></dhcc:thirdButton>
	   		</div>
		</div>
   		<div style="display: none;" id="formItem-div">
			<div id = "titleStr-div">
			</div>
			<c:forEach var = "tags" items="${getByformEnList}">
				<div id = "${tags.formNameEn}" >
				</div>
			</c:forEach>
		</div>
	</body>
</html>
