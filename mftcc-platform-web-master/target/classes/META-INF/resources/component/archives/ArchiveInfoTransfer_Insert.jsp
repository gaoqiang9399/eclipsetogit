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

	//移交申请
	function save(obj){
		var flag = submitJsMethod($(obj).get(0), '');
		if (flag) {
			LoadingAnimate.start();
			$.ajax({
				type : "POST",
				data:{
					ajaxData:JSON.stringify($(obj).serializeArray())
				},
				url : "${webPath}/archiveInfoTransfer/insertAjax",
				dataType : "json",
				success : function(data) {
					LoadingAnimate.stop();
					if(data.flag=="success"){
						window.top.alert(data.msg,1);
						myclose_click();
					}else{
						window.top.alert(data.msg,0);
					}
				},
				error : function(xmlhq, ts, err) {
					loadingAnimate.stop();
					console.log(xmlhq);
					console.log(ts);
					console.log(err);
				}
			});
		}
	}

	//选择接收人回调函数
	var recPersonCallBack = function(userInfo){
		$("input[name=receiveNo]").val(userInfo.opNo);
		$("input[name=receiveNo]").val(userInfo.opName);
	};

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
				<form method="post" id="cwBillForm" theme="simple" name="operform" action="${webPath}/archiveInfoTransfer/insertAjax">
					<dhcc:bootstarpTag property="formarchivetransferinsert" mode="query"/>
				</form>
			</div>
		</div>
	</div>
	<div class="formRowCenter">
		<dhcc:thirdButton value="移交" action="移交" onclick="save('#cwBillForm');"></dhcc:thirdButton>
		<dhcc:thirdButton value="关闭" action="取消" typeclass="cancel" onclick="closeWindow();"></dhcc:thirdButton>
	</div>
</div>
</body>
</html>
