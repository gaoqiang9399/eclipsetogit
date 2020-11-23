<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%-- <%@ include file="/component/include/pub_view_table.jsp"%> --%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<script type="text/javascript" src="${webPath}/component/archives/plugin/viewer.js?v=${cssJsVersion}"></script>
<link rel="stylesheet" href="${webPath}/component/archives/plugin/viewer.css" type="text/css">
<script type="text/javascript" src="${webPath}/component/model/js/mfPageOffice.js?v=${cssJsVersion}"></script>
<style type="text/css">
	.cus-type-font {
		position: relative;
		font-size: 85px;
		color: #32b5cb;
	}

	.type-name-div {
		font-size: 12px;
		color: #32b5cb;
		text-align : center;
		position: absolute;
		left: 3px;
		top: 34px;
		width: 78px;
		transform: rotate(-15deg);
		-ms-transform: rotate(-15deg);
		-moz-transform: rotate(-15deg);
		-webkit-transform: rotate(-15deg);
		-o-transform: rotate(-15deg);
	}

	.content .fieldShow {
		max-width: 18em;
	}

	td input[type=checkbox] {
		margin-top: -3px;
	}

	.list-table {
		margin-left: 15px;
		margin-right: 15px;
		margin-top: 0px;
	}
</style>
<script type="text/javascript">
	var isDoc = '${isDoc}';
	var archivePactStatus = '${archivePactStatus}';
	$(function(){
		$("body").mCustomScrollbar({
			advanced:{
				updateOnContentResize:true
			}
		});
		$('.image').viewer();
		//初始化页面时加载借出文件列表
		url = webPath+"/archiveInfoBorrow/getTableHtmlAjax?archiveMainNo=${archiveInfoMain.archiveMainNo}";
		$.ajax({
			url:url,
			type:'post',
			dataType:'json',
			success:function(data){
				if(data.flag = "success"){
					refreshLendTable(data.tableHtmlIn, data.tableHtmlOut);
				}else{
					window.top.alert(data.msg, 0);
				}
			}, error:function(){
				window.top.alert(top.getMessage("ERROR_REQUEST_URL", url), 0);
			}
		});
	});

	//加载借出借阅列表
	function refreshLendTable(tableHtmlIn, tableHtmlOut) {
		var $lendInDiv = $('#lend_in_div');
		$lendInDiv.children().remove();
		$lendInDiv.append($(tableHtmlIn));

		var $lendOutDiv = $('#lend_out_div');
		$lendOutDiv.children().remove();
		$lendOutDiv.append($(tableHtmlOut));
	}

	//预览
	function previewFile(obj, url) {
		window.top.loadding();
		notie.alert(4, '文件正在打开请稍等', -1);
		$.ajax({
			url:url,
			type:'post',
			dataType:'json',
			success:function(data){
				if(data.success){
					var archiveDocAttribute = data.archiveInfoDetail.archiveDocAttribute;
					if (!archiveDocAttribute || archiveDocAttribute !== '') {
						if (archiveDocAttribute == '01') {
							var isWin = (navigator.platform == "Win32") || (navigator.platform == "Windows");
							if(isWin){
								var poCntObj = $.parseJSON(data.poCnt);
								mfPageOffice.openPageOffice(poCntObj);
							}else{
								window.top.alert("当前操作系统不支持在线预览，请下载到本地查阅！",0);
							}
						} else if (archiveDocAttribute == '02') {
							$("#img" + data.archiveInfoDetail.archiveDetailNo).remove();
							var $image = $("<img>");
							$image.attr("class", "image");
							$image.attr("id", "img" + data.archiveInfoDetail.archiveDetailNo);
							$image.attr("img_id", data.archiveInfoDetail.archiveDetailNo);
							$image.attr("alt", data.archiveInfoDetail.docName);
							$image.attr("src", webPath+"/archiveInfoDetail/getImageByteArrayOutputStream?archiveDetailNo=" + data.archiveInfoDetail.archiveDetailNo);
							$image.hide();
							$("body").append($image);

							$('.image').viewer();
							$image.click();
						} else if (archiveDocAttribute == '03' || archiveDocAttribute == '04') {
							window.top.dhccModalDialog.open("${webPath}component/archives/plugin/video/viewer.jsp" + url.substring(url.indexOf('?')), data.archiveInfoDetail.docName, function(){}, "90", "90", "400", "300");
						} else {
							window.top.alert("无法预览该文件！", 0);
						}
					} else {
						window.top.alert("无法预览该文件！", 0);
					}
				} else {
					window.top.alert(data.msg, 0);
				}
			}, error:function(){
				alert(top.getMessage("ERROR_REQUEST_URL", url), 0);
			}, complete:function(){
				window.top.loaded();
				notie.alert_hide();
			}
		});
	}
	//下载资料-wsd
	function downloadFile(url) {
		top.LoadingAnimate.start();
		var param = url.split("?")[1];
		$.ajax({
			url:webPath+'/archiveInfoDetail/isDownLoadFileExistAjax?' + param,
			type:'post',
			dataType:'json',
			success:function(data){
				if(data.success){
					window.location.href = url;
					setTimeout("top.LoadingAnimate.stop()", 500);
				} else {
					top.LoadingAnimate.stop();
					window.top.alert(data.msg, 0);
				}
			}, error:function(){
				top.LoadingAnimate.stop();
				window.top.alert(top.getMessage("ERROR_REQUEST_URL", url), 0);
			}
		});
	}

	//查看文档详情
	function showDetail(obj, url) {
		window.parent.openBigForm(url, "归档明细信息", function() {});
	}

	function showPact() {
		window.parent.openBigForm(webPath+"/archiveInfoMain/getPactPage?appId=${archiveInfoMain.appId}", "项目详情");
	}

	function paperDetail(obj, url) {
		top.window.openBigForm( webPath + url +"&archivePactStatus="+archivePactStatus ,'其他资料详情',function() {
		});
	}

	function pactDetail(obj, url) {
		top.window.openBigForm( webPath + url +"&archivePactStatus="+archivePactStatus ,'非系统生成合同及附件详情',function() {
		});
	}
</script>
</head>
<body class="overflowHidden">
	<div class="container">
		<div class="row clearfix">	
			<!--归档信息 -->
			<div class="col-md-8 column block-left">
				<div class="bg-white block-left-div">
					<div class="row clearfix head-info">
						<div class="col-xs-3 column text-center head-img">
							<div style="position:relative;">
								<button type="button" class="btn btn-font-pledge font-pledge-div padding_left_15">
									<i class="i i-applyinfo font-icon"></i>
									<div class="font-text-left">归档信息</div>
								</button>
							</div>
						  	<div class="btn btn-link" onclick="">${archiveInfoMain.archiveMainNo}</div>
						</div>
						<!--概要信息 -->
						<div class="col-xs-9 column head-content">
							<div class="row clearfix">
								<div class="col-xs-10 column">
									<div class="margin_bottom_5 clearfix">
										<div>
											<div class="bus head-title pull-left" style="cursor:hand" title="${archiveInfoMain.creditAppNo}">${archiveInfoMain.creditAppNo}</div>
										</div>
									</div>
									<div>
										<table>
											<tr>
												<td>
													<p class="cont-title">授信金额</p>
													<p><span class="content-span">${mfCusCreditApply.applySum}</span><span class="unit-span margin_right_25">万</span> </p>
												</td>
												<td>
													<p class="cont-title">授信期限</p>
													<p><span id="term" class="content-span">${mfCusCreditApply.creditTerm}</span><span class="unit-span margin_right_25"><c:if  test="${mfBusPact.termType=='1'}">个月</c:if><c:if  test='${mfBusPact.termType!="1"}'>天</c:if></span> </p>
												</td>
												<%--<td>
													<p class="cont-title">合同利率</p>
													<p><span class="content-span">${mfCusCreditApply.fincRate}</span><span class="unit-span">${rateUnit}</span></p>
												</td>--%>
											</tr>
										</table>
									</div>
								</div>
								<!--归档状态图标-->
								<div class="col-xs-2 column">
									<div class="i i-warehouse cus-type-font">
										<div class="type-name-div">${archiveInfoMain.archiveStatusString}</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<!--归档信息操作入口 -->
					<div class="row clearfix btn-opt-group">
						<div class="col-xs-12 column">
							<div class="btn-group pull-right">
							</div>
						</div>
					</div>
					<!--归档信息与业务信息 -->
					<div class="row clearfix">
						<div class="col-xs-12 column">
							<div class="form-table base-info">
								<div class="content">
									<form  method="post" theme="simple" name="operform" action="">
										${htmlStr}
									</form>
								</div>
							</div>
						</div>
					</div>
					<!--文件列表 -->
					<div class="row clearfix" id="detail_div">
						<div class="block-new-block">
							<c:if test="${isDoc==true}">
								<div class="list-table">
									<div class="title">
										<span><i class="i i-xing blockDian"></i>资料列表</span>
									</div>
									<div class="docList content collapse in" id="docList" name="docList">
										<dhcc:tableTag property="tablearchivedetaildoclist" paginate="docManageList" head="true"></dhcc:tableTag>
									</div>
								</div>
								<div class="list-table">
									<div class="title">
										<span><i class="i i-xing blockDian"></i>纸质资料列表</span>
									</div>
									<div class="paperList content collapse in" id="paperList" name="docList">
										<dhcc:tableTag property="tablearchivepaperdetail" paginate="paperInfoDetailList" head="true"></dhcc:tableTag>
									</div>
								</div>
								<div class="list-table">
									<div class="title">
										<span><i class="i i-xing blockDian"></i>合同列表</span>
									</div>
									<div class="templateList content collapse in" id="templateList" name="templateList">
										<dhcc:tableTag property="tablearchivedetailtemplatelist" paginate="successTemplateList" head="true"></dhcc:tableTag>
									</div>
								</div>
								<div class="list-table">
									<div class="title">
										<span><i class="i i-xing blockDian"></i>非系统生成合同列表</span>
									</div>
									<div class="expandTemplateList content collapse in" id="expandTemplateList" name="expandTemplateList">
										<dhcc:tableTag property="tablearchiveextenddetail" paginate="pactDetailList" head="true"></dhcc:tableTag>
									</div>
								</div>
							</c:if>
							<c:if test="${isDoc==false}">
								<div class="list-table">
									<div class="title">
										<span><i class="i i-xing blockDian"></i>凭证列表</span>
									</div>
									<div class="voucherList content collapse in" id="voucherList" name="voucherList">
										<dhcc:tableTag property="tablearchivedetailvoucher" paginate="voucherList" head="true"></dhcc:tableTag>
									</div>
								</div>
							</c:if>
						</div>
					</div>
				</div>
			</div>
			<!-- 归档文件借阅信息、操作日志 -->
			<div class="col-md-4 column block-right">
				<div class="bg-white block-right-div">
					<!--归档文件借阅信息-->
					<jsp:include page="/component/archives/ArchiveInfoBorrow_MainInList.jsp?archiveMainNo=${archiveInfoMain.archiveMainNo}"/>
				</div>
				<div class="bg-white block-right-div">
					<!--归档文件借出信息-->
					<jsp:include page="/component/archives/ArchiveInfoBorrow_MainOutList.jsp?archiveMainNo=${archiveInfoMain.archiveMainNo}"/>
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript" src="${webPath}/UIplug/notie/notie.js"></script>
</html>