<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>新增</title>
<link rel="stylesheet" href="${webPath}/UIplug/select3/css/select3.css" />
<script type="text/javascript" src="${webPath}/UIplug/select3/js/select3-full.js"></script>
<link rel="stylesheet" type="text/css" href="${webPath}/component/model/css/templateIncludePage.css?v=${cssJsVersion}" />
	<script type="text/javascript" src="${webPath}/component/collateral/js/PledgeInfo_account.js?v=${cssJsVersion}"></script>
	<script type="text/javascript">
	var appId = '${appId}';
	var pactId = '${pactId}';
	var collateralId = '${collateralId}';
    $(function () {
		$(".scroll-content").mCustomScrollbar({
			advanced : {
				updateOnContentResize : true
			}
		})
	})

	function save(obj){
		var flag = submitJsMethod($(obj).get(0), '');
		if (flag) {
			LoadingAnimate.start();
			$.ajax({
				type : "POST",
				data:{ajaxData:JSON.stringify($(obj).serializeArray())},
				url : "${webPath}/mfStampSealPact/insertAjax",
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
				<form method="post" id="accountPactForm" theme="simple" name="operform" action="${webPath}/mfStampSealPact/insertAjax">
					<dhcc:bootstarpTag property="formaccoutPactDetail" mode="query"/>
				</form>
				<span class="top-title">应收账款信息</span>
				<form method="post" id="accountDetailForm" theme="simple" name="operform" action="${webPath}/mfStampSealPact/insertAjax">
					<dhcc:bootstarpTag property="formbusinessAccountsBase2" mode="query"/>
				</form>

				<div class="list-table" id="mfBusPactExtendListDiv">
					<div class="title">
						<span><i class="i i-xing blockDian"></i>合同清单</span>
						<button class="btn btn-link formAdd-btn"  onclick="PledgeInfo_account.addBill()" title="手动添加">手动添加合同清单</button>
						<button class="btn btn-link formAdd-btn"  onclick="PledgeInfo_account.uploadBill()" title="上传">上传</button>
						<button class="btn btn-link formAdd-btn"  onclick="PledgeInfo_account.changeReword()" title="登记变更记录">登记变更记录</button>
						<button class=" btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#mfBusPactExtendList">
							<i class="i i-close-up"></i><i class="i i-open-down"></i>
						</button>
					</div>
					<div class="content collapse in" id="accountInfoBaseList" name="accountInfoBaseList">
						<dhcc:tableTag property="tableaccountInfoBase" paginate="accountInfoBaseList" head="true"></dhcc:tableTag>
					</div>
				</div>
				<div class="list-table" id="insInfoListDiv">
					<div class="title">
						<span><i class="i i-xing blockDian"></i>变更记录</span>
						<button class=" btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#insInfoListDiv">
							<i class="i i-close-up"></i><i class="i i-open-down"></i>
						</button>
					</div>
					<div class="content collapse in" id="insInfoList" name="insInfoList">
						<dhcc:tableTag property="tabledlinsinfo0005" paginate="insInfoList" head="true"></dhcc:tableTag>
					</div>
				</div>
		</div>
	</div>
	</div>
	<div class="formRowCenter">
		<dhcc:thirdButton value="关闭" action="取消" typeclass="cancel" onclick="closeWindow();"></dhcc:thirdButton>
	</div>
</div>
</body>
</html>
