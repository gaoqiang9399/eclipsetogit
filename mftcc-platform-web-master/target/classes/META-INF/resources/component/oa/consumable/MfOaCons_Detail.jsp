<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>详情</title>
<script type="text/javascript"
	src="${webPath}/UIplug/artDialog/dist/dialog-plus-min.js"></script>
</head>
<body class=" overflowHidden bg-white">
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-10 col-md-offset-1 column margin_top_20">
					<div class="bootstarpTag fourColumn">
						<form id="edit-form">
							<dhcc:bootstarpTag property="formconsumable0001" mode="query" />
						</form>
						<div class="feeInfo showOrHide">
							<div class="list-table">
								<span class="inner-center color_theme"
									style="margin-left: 30px; margin-bottom: 10px; font-size: 14px;">操作记录</span>
								<div class="content_table" id="busfee-div">
								${tableHtml}
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div id="operate-detail" class="operate-detail" style="display: none;">
	</div>
</body>
<script type="text/javascript"
	src="${webPath}/component/oa/consumable/js/MfOaConsDetail.js"></script>
<script type="text/javascript">
	OaConsDetail.path = "${webPath}";
	$(function() {
		OaConsDetail.init();
	});
	function OperateDialog(obj,url) {
		LoadingAnimate.start();
		$.ajax({
			url:url,
			dateType:"json",
			success:function(data){
				LoadingAnimate.stop();
				if (data.flag == "success") {
					var htmlStr = data.htmlStr;
					$("#operate-detail").html(htmlStr);
					artdialog = dialog({
	             		title:'操作详情',
	             		id : "operateDetail",
	             		content:$("#operate-detail"),
	             		width:700,
	             		height:450,
	             		backdropOpacity:0,
	             		onshow:function(){
	             		},onclose:function(){
	             			
	             		}
	             	});
	             	artdialog.showModal();
				} else {
					LoadingAnimate.stop();
					window.top.alert(data.msg, 0);
				}
			},
			error:function(){
				
			}
		});
	};
</script>
</html>