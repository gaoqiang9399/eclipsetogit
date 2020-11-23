<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/wkf/Wkf_Base.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head >
<title>列表表单</title>
<script type="text/javascript" src="${webPath}/component/archives/js/ArchiveInfoMain_Insert.js"></script>
<script type="text/javascript" >
	var appId = '${appId}';
	var creditAppId = '${creditAppId}';
	var type ="01";
	$(function(){
		$(".scroll-content").mCustomScrollbar({
			advanced : {
				updateOnContentResize : true
			}
		})
	});

	//编辑他项凭证
	function editNew(obj,url){
		var archivePactStatus = $("[name=archivePactStatus]").val();
		if(archivePactStatus == '01'){//授信
			top.window.openBigForm( webPath + url + "&appId="+creditAppId,'编辑权证',function() {
				window.location.reload();
			});
		}else if(archivePactStatus == '02'){//业务
			top.window.openBigForm( webPath + url + "&appId="+appId,'编辑权证',function() {
				window.location.reload();
			});
		}
	}

	//信息确认
	function save(obj){
		var flag = submitJsMethod($(obj).get(0), '');
		if (flag) {
			var collateralIds = "";
			$('.receAccountList #tab').find($('input[type=checkbox]:checked')).each(function () {
				var collateralIdVal = this.value.split('&') [0] ;
				collateralIds=collateralIds+","+collateralIdVal.split("=")[1];
			});
			if(collateralIds != ""){
				collateralIds=collateralIds.substr(1);
			}else{
				alert("未选择他项凭证，请先选择需要归档的他项凭证！", 0);
				return false;
			}
			LoadingAnimate.start();
			$.ajax({
				type : "POST",
				data:{
					ajaxData:JSON.stringify($(obj).serializeArray()),
					collateralIds:collateralIds
				},
				url : "${webPath}/archiveInfoMain/insertVoucherOther",
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
</script>
</head>
<body class="overflowHidden">
<div class="container">
	<div class="scroll-content" id="quanxuan">
		<div class="col-md-10 col-md-offset-1 column margin_top_20">
			<div class="bootstarpTag">
				<div class="form-title"></div>
				<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
				<form method="post" id="cwBillForm" theme="simple" name="operform" action="${webPath}/archiveInfoMain/insertVoucherOther">
					<dhcc:bootstarpTag property="formarchivevoucherother" mode="query"/>
				</form>
			</div>

			<div class="list-table">
				<div class="title">
					<span><i class="i i-xing blockDian"></i>他项凭证列表</span>
					<button class=" btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#receAccountList">
						<i class="i i-close-up"></i><i class="i i-open-down"></i>
					</button>
				</div>
				<div class="receAccountList content collapse in" id="receAccountList" name="receAccountList">
					<dhcc:tableTag property="tablearchivecertivoucher" paginate="certiInfoList" head="true"></dhcc:tableTag>
				</div>
			</div>
		</div>
	</div>

	<div class="formRowCenter">
		<dhcc:thirdButton value="保存" action="保存" onclick="save('#cwBillForm');"></dhcc:thirdButton>
		<dhcc:thirdButton value="关闭" action="取消" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
	</div>
</div>
</body>
</html>