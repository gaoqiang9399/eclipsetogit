<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>新增</title>
<script type="text/javascript" src="${webPath}/component/include/calcUtil.js"></script>
<link rel="stylesheet" href="${webPath}/UIplug/select3/css/select3.css" />
<script type="text/javascript" src="${webPath}/UIplug/select3/js/select3-full.js"></script>
<script type="text/javascript" src="${webPath}/component/archives/js/ArchiveInfoVoucer_Insert.js"></script>
<style type="text/css">
	.cover {
		cursor: default;
	}
	.infoTilte{
		margin-left: 14px;
		margin-bottom: 20px;
		margin-top:0px;
	}
	.form-margin{
		margin-top: 20px;
		margin-left: 4px;
	}
	.set-disabled{
		color:#aaa;
	}
	.button-bac32B5CB{
		background-color:#32B5CB
	}
	.button-bac32B5CB:hover{
		color: #fff;
		background-color: #018FA7;
	}
	.button-bacFCB865{
		background-color:#FCB865
	}
	.button-bacFCB865:hover{
		background-color : #E9AA64;
		color : #fff;
	}
	.button-bacE14A47{
		background-color:#E14A47
	}
	.button-bacE14A47:hover{
		color : #fff;
		background-color:#C9302C
	}
	.button-his{
		margin-top: 20px;
	}
	.info-collect{
		margin-top: -30px;
	}
</style>
<script type="text/javascript">
	var id = '${id}';
	var aloneFlag = true;//必须
	var dataDocParm = {
		relNo:id,
		docType:"22",
		docTypeName: "还款凭证",
		docSplitNoArr : "[{'docSplitNo':20200611113100001}]",
		docSplitName: "还款凭证",
		query:''
	};
	$(function () {
		$(".scroll-content").mCustomScrollbar({
			advanced : {
				updateOnContentResize : true
			}
		})

//		showDocInfo(id);
	});

	//展示要件资料
	function showDocInfo (id) {
		initAloneDocNodes(id);
		isSupportBase64 = (function () {
			var data = new Image();
			var support = true;
			data.onload = data.onerror = function () {
				if (this.width != 1 || this.height != 1) {
					support = false;
				}
			};
			data.src = "data:image/gif;base64,R0lGODlhAQABAIAAAAAAAP///ywAAAAAAQABAAACAUwAOw==";
			return support;
		})();
	};

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
				url : "${webPath}/archiveInfoVoucherReturn/insertAjax",
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

	//编辑他项凭证
	function editNew(obj,url){
		top.addReceInfoFlag=false;
		top.window.openBigForm( webPath + url,'编辑权证',function() {
			window.location.reload();
		});
	}
	//编辑业务
	function editForApp(obj,url){
		top.addReceInfoFlag=false;
		top.window.openBigForm( webPath + url,'上传还款凭证',function() {
			if(top.addReceInfoFlag){
//					_getCertiListHtmlAjax();
			}
		});
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
				<form method="post" id="cwBillForm" theme="simple" name="operform" action="${webPath}/archiveInfoVoucherReturn/insertAjax">
					<dhcc:bootstarpTag property="formarchivevoucherinsert" mode="query"/>
				</form>
			</div>
			<div class="list-table">
				<div class="title">
					<span><i class="i i-xing blockDian"></i>业务列表</span>
					<button class=" btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#mfBusApplyList">
						<i class="i i-close-up"></i><i class="i i-open-down"></i>
					</button>
				</div>
				<div class="mfBusApplyList content collapse in" id="mfBusApplyList" name="receAccountList">
					<dhcc:tableTag property="tablearchivevoucherreturnapply" paginate="mfBusApplyList" head="true"></dhcc:tableTag>
				</div>
			</div>
			<%--<div class="list-table">
				<div class="title">
					<span><i class="i i-xing blockDian"></i>他项凭证登记列表</span>
					<button class=" btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#certiInfoList">
						<i class="i i-close-up"></i><i class="i i-open-down"></i>
					</button>
				</div>
				<div class="certiInfoList content collapse in" id="certiInfoList" name="certiInfoList">
					<dhcc:tableTag property="tablearchivevoucherreturn" paginate="certiInfoList" head="true"></dhcc:tableTag>
				</div>
			</div>--%>
			<%--<%@ include file="/component/doc/webUpload/pub_uploadForMainPage.jsp"%>--%>
		</div>
	</div>
	<div class="formRowCenter">
		<dhcc:thirdButton value="退还" action="退还" onclick="save('#cwBillForm');"></dhcc:thirdButton>
		<dhcc:thirdButton value="关闭" action="取消" typeclass="cancel" onclick="closeWindow();"></dhcc:thirdButton>
	</div>
</div>
</body>
</html>
