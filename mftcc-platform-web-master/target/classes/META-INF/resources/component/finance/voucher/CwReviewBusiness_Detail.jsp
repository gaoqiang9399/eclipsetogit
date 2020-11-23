<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>详情</title>
		<script type="text/javascript">
			function cwReview(){
					var uid = $('input[name=traceNo]').val();
					var param = JSON.stringify({'which': 'review', 'uid': uid});
					//判断是否有记账规则
					$.ajax({
						url:webPath+'/cwReviewBusiness/doCheckHaveRulesAjax',
						type:'post',
						data:{"traceNo":uid},
						async:false,
						dataType:'json',
						error:function(data){
							alert(top.getMessage(data.msg), 1);
						},
						success:function(data){
							if(data){
								if(data.flag=='success'){
									window.location.href = encodeURI('${webPath}/cwVoucherMst/toVoucherAddSet?ajaxData='+param);
								}else{
									alert(data.msg, 0);
								}
							}
						}
					})
				
			}
			$(function(){
				$(".scroll-content").mCustomScrollbar({
					advanced:{
						theme:"minimal-dark",
						updateOnContentResize:true
					}
				});
			})
		</script>
		<style type="text/css">
		.hidden-content{
			display: none;
		}
		</style>
	</head>
	
	<body class="overflowHidden bg-white">
		
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-10 col-md-offset-1 column margin_top_20">
					<div class="bootstarpTag fourColumn">
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form method="post" theme="simple" name="cwReviewForm" id="cwReviewForm" action="#">
							<dhcc:bootstarpTag property="formreview0001" mode="query" />
						</form>
					</div>
				</div>
			</div>
			
			<div class="formRowCenter">
				<dhcc:thirdButton value="复核" action="复核" onclick="cwReview();"></dhcc:thirdButton>
				<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
			</div>
		</div>
		
	</body>
</html>