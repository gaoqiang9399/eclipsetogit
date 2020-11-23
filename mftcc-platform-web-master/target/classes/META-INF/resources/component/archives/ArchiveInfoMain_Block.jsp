<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>新增</title>
<link rel="stylesheet" href="${webPath}/UIplug/select3/css/select3.css" />
<script type="text/javascript" src="${webPath}/UIplug/select3/js/select3-full.js"></script>
<script type="text/javascript" src="${webPath}/component/archives/js/ArchiveInfo_Insert.js"></script>
<script type="text/javascript">
	$(function () {
		$(".scroll-content").mCustomScrollbar({
			advanced : {
				updateOnContentResize : true
			}
		})
	})

	//信息确认
	function save(obj){
		var flag = submitJsMethod($(obj).get(0), '');
		if (flag) {
			LoadingAnimate.start();
			$.ajax({
				type : "POST",
				data:{
					ajaxData:JSON.stringify($(obj).serializeArray())
				},
				url : "${webPath}/archiveInfoMain/updateBlockAjax",
				dataType : "json",
				success : function(data) {
					LoadingAnimate.stop();
					if(data.flag=="success"){
						window.top.alert(data.msg,1);
						myclose_click();
					}else{
						window.top.alert(data.msg,0);
					}
				}
			});
		}
	}

	//编辑
	function getBigForm(obj, lk){
		top.openBigForm(webPath+lk, "编辑", function () {
			window.location.reload();
		});
	}

	function closeWindow(){
		myclose_click();
	};
</script>
</head>
<body class="overflowHidden bg-white">
<div class="container form-container">
	<div class="scroll-content">
		<div class="col-md-10 col-md-offset-1 column margin_top_20">
			<div class="bootstarpTag">
				<div class="form-title"></div>
				<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
				<form method="post" id="cwBillForm" theme="simple" name="operform" action="${webPath}/archiveInfoMain/updateBlockAjax">
					<dhcc:bootstarpTag property="formarchivemainblock" mode="query"/>
				</form>
			</div>
		</div>
	</div>
	<div class="formRowCenter">
		<dhcc:thirdButton value="封存" action="封存" onclick="save('#cwBillForm');"></dhcc:thirdButton>
		<dhcc:thirdButton value="关闭" action="取消" typeclass="cancel" onclick="closeWindow();"></dhcc:thirdButton>
	</div>
</div>
</body>
</html>
