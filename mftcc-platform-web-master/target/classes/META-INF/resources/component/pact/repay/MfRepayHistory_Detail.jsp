<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>详情</title>
	</head>
	<script type="text/javascript">
		$(function(){
// 			$(".scroll-content").mCustomScrollbar({
// 				advanced:{
// 					updateOnContentResize:true
// 				}
// 			});
			myCustomScrollbarForForm({
				obj:".scroll-content",
				advanced : {
					updateOnContentResize : true
				}
			});
		});
		
	function ajaxRepReview(obj){ 
		var url = $(obj).attr("action");
		var dataParam = JSON.stringify($(obj).serializeArray()); 
		LoadingAnimate.start();
		$.ajax({
			url:url,
			data:{ajaxData:dataParam},
			type:'post',
			dataType:'json',
			success:function(data){
				LoadingAnimate.stop();
				if(data.flag == "success"){
					 top.repayReviewFlag=true;
					 top.tableHtml=data.tableHtml;
					 myclose_click();
				}
			},error:function(){
				alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
			}
		}); 
    };
		
	</script>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-8 col-md-offset-2 column margin_top_20">
					<div class="bootstarpTag">
						<div class="form-title">客户登记</div>
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form method="post" id="repayReviewForm" theme="simple" name="operform" action="${webPath}/mfRepayHistory/repayReviewAjax">
							<dhcc:bootstarpTag property="formrepay0001" mode="query"/>
						</form>	
					</div>
				</div>	
			</div>
			<div class="formRowCenter">
	   			<dhcc:thirdButton value="保存" action="保存" onclick="ajaxRepReview('#repayReviewForm');"></dhcc:thirdButton>
	   			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
	   		</div>
   		</div>
	</body>
</html>