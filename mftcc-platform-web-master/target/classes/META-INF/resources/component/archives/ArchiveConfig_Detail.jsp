<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head >
		<title>归档管理配置</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<link rel="stylesheet" href="${webPath}/UIplug/select3/css/select3.css" />
		<script type="text/javascript" src="${webPath}/UIplug/select3/js/select3-full.js"></script>
		<script type="text/javascript" >
			var ajaxData = '${ajaxData}';
	    	ajaxData = eval("("+ajaxData+")");
	    	
			$(function(){
// 				$(".scroll-content").mCustomScrollbar({
// 					advanced:{
// 						updateOnContentResize:true
// 					}
// 				});
				myCustomScrollbarForForm({
					obj:".scroll-content",
					advanced:{
						updateOnContentResize:true
					}
				});
				$("input[name=pactSignFilesOfSceneNos]").popupSelection({
					searchOn:true,//启用搜索
					inline:true,//下拉模式
					multiple:true,//多选选
					items:ajaxData.scence
				});
				
				$("input[name=pactOverFilesOfSceneNos]").popupSelection({
					searchOn:true,//启用搜索
					inline:true,//下拉模式
					multiple:true,//多选选
					items:ajaxData.scence
				});
			});
			
			function selectScenceMutiDialog4Sign() {
				selectScenceMutiDialogForArchive(scenceMutiDialogCall4Sign, $("#ArchiveConfigForm input[name=pactSignFilesOfSceneNos]").val());
			};
			
			function selectScenceMutiDialog4Over() {
				selectScenceMutiDialogForArchive(scenceMutiDialogCall4Over, $("#ArchiveConfigForm input[name=pactOverFilesOfSceneNos]").val());
			};
			
			function scenceMutiDialogCall4Sign(scence) {
				$("#ArchiveConfigForm input[name=pactSignFilesOfSceneNames]").val("");
				$("#ArchiveConfigForm input[name=pactSignFilesOfSceneNos]").val("");
				$("#ArchiveConfigForm input[name=pactSignFilesOfSceneNames]").val(scence.scNames);
				$("#ArchiveConfigForm input[name=pactSignFilesOfSceneNos]").val(scence.scNos);
			};
			
			function scenceMutiDialogCall4Over(scence) {
				$("#ArchiveConfigForm input[name=pactOverFilesOfSceneNames]").val("");
				$("#ArchiveConfigForm input[name=pactOverFilesOfSceneNos]").val("");
				$("#ArchiveConfigForm input[name=pactOverFilesOfSceneNames]").val(scence.scNames);
				$("#ArchiveConfigForm input[name=pactOverFilesOfSceneNos]").val(scence.scNos);
			};
			
			//选择场景,支持多选
			function selectScenceMutiDialogForArchive(callback, scNos){
				dialog({
					id:"scenceMutiDialog",
					title:'选择场景',
					url: webPath+'/archiveConfig/getScenceForMutiSel?scNos='+scNos,
					width:600,
					height:400,
					backdropOpacity:0,
					onshow:function(){
						this.returnValue = null;
					},onclose:function(){
						if(this.returnValue){
							//返回对象的属性docNo,docName；如果多个，使用@分隔
							if(typeof(callback) == "function"){
								callback(this.returnValue);
							}else{
							}
						}
					}
				}).showModal();
			};
			
			function save(obj) {
				var flag = submitJsMethod($(obj).get(0), '');
				if(flag){
					var url = $(obj).attr("action");
					var dataParam = JSON.stringify($(obj).serializeArray());
					LoadingAnimate.start();
					jQuery.ajax({
						url:url,
						data:{ajaxData:dataParam},
						type:"POST",
						dataType:"json",
						success:function(data){
							LoadingAnimate.stop();
							if(data.success) {
						    	alert(data.msg, 1);
						    	$("#ArchiveConfigForm input[name=updateOpNo]").val(data.archiveConfigJson.updateOpNo);
								$("#ArchiveConfigForm input[name=updateOpName]").val(data.archiveConfigJson.updateOpName);
								$("#ArchiveConfigForm input[name=updateBrNo]").val(data.archiveConfigJson.updateBrNo);
								$("#ArchiveConfigForm input[name=updateBrName]").val(data.archiveConfigJson.updateBrName);
								$("#ArchiveConfigForm input[name=updateDate]").val(data.archiveConfigJson.updateDate);
							} else {
								alert(data.msg, 0);
							}
						}, error:function() {
							LoadingAnimate.stop(); 
							alert(top.getMessage("FAILED_OPERATION"," "),0);
						}
					});
				}
			};
			
			function cancel() {
				window.location.href=webPath+"/sysDevView/setC?setType=base";
			}
		</script>
	</head>
<body class="overflowHidden bg-white">
   <div class="container form-container">
		<div class="scroll-content">
			<div class="col-md-12 column">
				<div class="search-div">
					<div class="col-xs-9 column">
						<ol class="breadcrumb pull-left">
							<li><a href="${webPath}/sysDevView/setC?setType=base">基础设置</a></li>
							<li class="active">归档管理配置</li>
						</ol>
					</div>
				</div>
			</div>
			<div class="col-md-8 col-md-offset-2 column margin_top_20" style="height: 88%;">
				<div class="bootstarpTag">
					<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
					<form  method="post" id="ArchiveConfigForm" theme="simple" name="operform" action="${webPath}/archiveConfig/saveAjax">
						<dhcc:bootstarpTag property="formdl_archive_config02" mode="query" />
					</form>	
				</div>
			</div>
   		</div>
		<div class="formRowCenter">
			<dhcc:thirdButton value="保存" action="保存" onclick="save('#ArchiveConfigForm');"></dhcc:thirdButton>
			<dhcc:thirdButton value="取消" action="取消" typeclass="cancel" onclick="cancel();"></dhcc:thirdButton>
		</div>
	</div>
</body>
</html>