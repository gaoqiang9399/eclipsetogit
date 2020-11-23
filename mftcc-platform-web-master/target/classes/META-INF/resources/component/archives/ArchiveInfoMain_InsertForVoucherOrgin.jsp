<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>新增</title>
<link rel="stylesheet" href="${webPath}/UIplug/select3/css/select3.css" />
<script type="text/javascript" src="${webPath}/UIplug/select3/js/select3-full.js"></script>
<script type="text/javascript" src="${webPath}/component/archives/js/ArchiveInfoMain_Insert.js"></script>
<script type="text/javascript" src="${webPath}/component/model/js/templateIncludePage.js?v=${cssJsVersion}"></script>
<script type="text/javascript" src="${webPath}/component/model/js/mfPageOffice.js?v=${cssJsVersion}"></script>
<script type="text/javascript">
	var archiveMainNo = '${archiveMainNo}';
	var type ="01";
	$(function () {
		$(".scroll-content").mCustomScrollbar({
			advanced : {
				updateOnContentResize : true
			}
		})
	});

	function addOriginVoucher(){
		var archivePactStatus = $("[name=archivePactStatus]").val();
		var creditAppId = $("[name=creditAppId]").val();
		var appId = $("[name=appId]").val();
		var busiNo = $("[name=busiNo]").val();
		if(busiNo == ""){
			alert("请先选择要归档的业务合同！", 0);
			return false;
		}
		var url = "";
		if(archivePactStatus == '01'){//授信
			url = webPath+"/archiveInfoMain/addOriginVoucher?archiveMainNo="+archiveMainNo+"&archivePactStatus="+archivePactStatus+"&relationId="+creditAppId;
		}else if(archivePactStatus == '02'){
			url = webPath+"/archiveInfoMain/addOriginVoucher?archiveMainNo="+archiveMainNo+"&archivePactStatus="+archivePactStatus+"&relationId="+appId;
		}
		top.openBigForm(url, "添加原始凭证", function(){
			getOriginListHtmlAjax();
		});
	}

	//关闭原始凭证新增界面弹窗后的回调
	function getOriginListHtmlAjax(){
		$.ajax({
			url: webPath + "/archiveInfoMain/getOriginListHtmlAjax",
			data:{
				archiveMainNo:archiveMainNo,
				tableId:"tablearchiveapplyvoucher"
			},
			type:'post',
			dataType:'json',
			success: function (data) {
				if (data.flag == "success") {
					$("#voucherList").html(data.tableData);
				} else {

				}
			}
		});
	};

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
				url : "${webPath}/archiveInfoMain/insertVoucherOrgin",
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

	//全选
	function checkAll(){
		$("#quanxuan").find("input[type='checkbox']").each(function(i,n){
			$(n).prop('checked', true);
			/*if ($(n).is(":checked")) {
				$(n).prop('checked', false);
			}else{
				$(n).prop('checked', true);
			}*/
		});
	}

	function closeWindow(){
		myclose_click();
	};

</script>
</head>
<body class="overflowHidden bg-white">
<div class="container form-container">
	<div class="scroll-content" id="quanxuan">
		<div class="col-md-10 col-md-offset-1 column margin_top_20">
			<div class="bootstarpTag">
				<div class="form-title"></div>
				<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
				<form method="post" id="cwBillForm" theme="simple" name="operform" action="${webPath}/archiveInfoMain/insertVoucherOrgin">
					<dhcc:bootstarpTag property="formarchivevoucherorgininsert" mode="query"/>
				</form>
			</div>

			<div class="list-table">
				<div class="title">
					<span><i class="i i-xing blockDian"></i>原始凭证列表</span>
					<button class="btn btn-link formAdd-btn" onclick="addOriginVoucher();" title="新增"><i class="i i-jia3"></i></button>
				</div>
				<div class="voucherList content collapse in" id="voucherList" name="voucherList">
					<dhcc:tableTag property="tablearchiveapplyvoucher" paginate="voucherDetailList" head="true"></dhcc:tableTag>
				</div>
			</div>
		</div>
	</div>
	<div class="formRowCenter">
		<dhcc:thirdButton value="保存" action="保存" onclick="save('#cwBillForm');"></dhcc:thirdButton>
		<dhcc:thirdButton value="关闭" action="取消" typeclass="cancel" onclick="closeWindow();"></dhcc:thirdButton>
	</div>
	<script type="text/javascript" src="${webPath}/UIplug/notie/notie.js"></script>
</div>
</body>
</html>
