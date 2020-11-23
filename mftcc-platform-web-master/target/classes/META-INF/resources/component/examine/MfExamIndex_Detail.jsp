<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>详情</title>
		<script type="text/javascript" src='${webPath}/component/examine/js/MfExamIndex.js'> </script> 
		<style type="text/css">
			.subBtn{
				color: #32b5cb;
				margin-left:93px;
				margin-top: -30px;
				background-color:#fff
			}
		</style>
		<script type="text/javascript">
			$(function(){
				init();
			})
		</script>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-10 col-md-offset-1 column margin_top_20">
					<div class="bootstarpTag fourColumn">
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form method="post" id="ecamIndexUpdateForm" theme="simple" name="operform" action="${webPath}/mfExamIndex/updateAjax">
							<dhcc:bootstarpTag property="formexamind0002" mode="query"/>
						</form>
					</div>
					<div class="bigform_content col_content">
								<div class="title"><h5 >检查指标子项</h5>
									<button id="addEvalButton" class='btn list-add subBtn' onclick='MfExamIndex.addExamIndexSub();' title='新增'>
											<i class='i i-jia3'></i>
									</button>
								</div>
								<div id="examIndexSubList" class="table_content">
									<dhcc:tableTag paginate="mfEvalIndexSubList" property="tableexamIndexSub" head="true" />
								</div> 
						</div>
				</div>	
			</div>
			<div class="formRowCenter">
	   			<dhcc:thirdButton value="保存" action="保存" onclick="MfExamIndex.ajaxUpdateThis('#ecamIndexUpdateForm');"></dhcc:thirdButton>
	   			 <dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
	   		</div>
   		</div>
	</body>
</html>