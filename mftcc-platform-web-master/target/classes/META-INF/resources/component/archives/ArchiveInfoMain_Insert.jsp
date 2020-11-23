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
	var isShow = '${isShow}';
	var isFirstArchive = '${isFirstArchive}';
	var archivePactStatus = '${archivePactStatus}';
	var archiveMainNo = '${archiveMainNo}';
	var appId = '${appId}';
	var creditAppId = '${creditAppId}';
	var agenciesId;
	var temParm = '';
	$(function () {
		$(".scroll-content").mCustomScrollbar({
			advanced : {
				updateOnContentResize : true
			}
		})

		//授信项下业务需要 授信合同必选
		$('.templateList #tab tr').each(function(index,item){//给编辑按钮绑定单击事件
			var ext9 = $(item).find("input[type=hidden][name=ext9]").val();
			if(ext9 == "1"){
				$(item).find("input[type=checkbox]").attr("checked",true)
				$(item).find("input[type=checkbox]").attr("disabled","disabled")
			}else{
				$(item).find("input[type=checkbox]").attr("checked",false);
			}

			$(this).parent().parent().find("input[name='templateNum']").val("1");
		});
	});

	//信息确认
	function save(obj){
		var flag = submitJsMethod($(obj).get(0), '');
		if (flag) {
			//合同模板
			var templateStr = "";
			var templateNumStr = "";//份数
			var templateTypeStr = "";//模板类型
			var typeFlag = 0;
			var numFlag = 0;
			$('.templateList #tab').find($('input[type=checkbox]:checked')).each(function () {
				var templateVal = this.value.split('&') [0] ;
				templateStr=templateStr+","+templateVal.split("=")[1];
				//可编辑的字段
				var templateNumVal = $(this).parent().parent().find("input[name='templateNum']").val();
				if(templateNumVal == ""){
					numFlag = 1;
				}
				templateNumStr=templateNumStr+","+templateNumVal;
				var templateTypeVal = $(this).parent().parent().find("[name='templateType']").val();
				if(templateTypeVal == ""){
					typeFlag = 1;
				}
				templateTypeStr=templateTypeStr+","+templateTypeVal;
			});
			if(typeFlag == 1){
				window.top.alert("选择的归档的合同，未填写合同类型",0);
				return false;
			}
			if(numFlag == 1){
				window.top.alert("选择的归档的合同，未填写合同份数",0);
				return false;
			}
			if(templateStr != ""){
				templateStr=templateStr.substr(1);
				templateNumStr=templateNumStr.substr(1);
				templateTypeStr=templateTypeStr.substr(1);
			}

			LoadingAnimate.start();
			$.ajax({
				type : "POST",
				data:{
					ajaxData:JSON.stringify($(obj).serializeArray()),
					templateNos:templateStr,
					templateNumStr:templateNumStr,
					templateTypeStr:templateTypeStr
				},
				url : "${webPath}/archiveInfoMain/insertArchiveInfo",
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

	//添加其他资料
	function addPaper(obj){
		if(appId == "" && creditAppId == ""){
			alert("请先选择要归档的授信或者业务！", 0);
			return false;
		}
		var archivePactStatus = $("[name=archivePactStatus]").val();
		if(archivePactStatus == '01'){//授信
			top.openBigForm(webPath+"/archiveInfoMain/editForPaper?archiveMainNo="+archiveMainNo+"&relationId="+creditAppId+"&archivePactStatus="+archivePactStatus, "添加其他资料", function(){
				getPaperListHtmlAjax();
			});
		}else if(archivePactStatus == '02'){//业务
			top.openBigForm(webPath+"/archiveInfoMain/editForPaper?archiveMainNo="+archiveMainNo+"&relationId="+appId+"&archivePactStatus="+archivePactStatus, "添加其他资料", function(){
				getPaperListHtmlAjax();
			});
		}

	}

	//关闭弹窗后展示其他资料
	function getPaperListHtmlAjax(){
		$.ajax({
			url: webPath + "/archiveInfoMain/getListHtmlAjax",
			data:{
				archiveMainNo:archiveMainNo,
				tableId:"tablearchivepaperapply"
			},
			type:'post',
			dataType:'json',
			success: function (data) {
				if (data.flag == "success") {
					$("#paperList").html(data.tableData);
				} else {
					alert("查询其他资料失败！",0);
				}
			}
		});
	}

	//删除其他资料
	function deletePaperAjax(lk){
		alert(1)
		$.ajax({
			url: webPath + lk,
			data:{},
			type:'post',
			dataType:'json',
			success: function (data) {
				if (data.flag == "success") {
					alert(data.msg, 1);
				} else {
					alert(data.msg, 0);
				}
			}
		});
	}

	function addPactExtend(){
		if(appId == ""  && creditAppId == ""){
			alert("请先选择要归档的项目！", 0);
			return false;
		}
		if(archivePactStatus == "01"){//授信
			top.openBigForm(webPath+"/archiveInfoMain/addPactExtend?archiveMainNo="+archiveMainNo+"&relationId="+creditAppId+"&archivePactStatus="+archivePactStatus, "添加非系统生成合同及附件", function(){
				getExendListHtmlAjax();
			});
		}else if(archivePactStatus == "02"){//单笔
			top.openBigForm(webPath+"/archiveInfoMain/addPactExtend?archiveMainNo="+archiveMainNo+"&relationId="+appId+"&archivePactStatus="+archivePactStatus, "添加非系统生成合同及附件", function(){
				getExendListHtmlAjax();
			});
		}
	}

	//关闭非系统合同弹窗后的回调
	function getExendListHtmlAjax(){
		$.ajax({
			url: webPath + "/archiveInfoMain/getExendListHtmlAjax",
			data:{
				archiveMainNo:archiveMainNo,
				tableId:"tablearchiveextendapply"
			},
			type:'post',
			dataType:'json',
			success: function (data) {
				if (data.flag == "success") {
					$("#expandTemplateList").html(data.tableData);
				} else {

				}
			}
		});
	};

	function extendDetail(obj, url) {
		top.window.openBigForm( webPath + url ,'非系统生成合同及附件详情',function() {
		});
	}

	function paperDetail(obj, url) {
		top.window.openBigForm( webPath + url +"&archivePactStatus="+archivePactStatus ,'其他资料详情',function() {
		});
	}

	function pactDetail(obj, url) {
		top.window.openBigForm( webPath + url +"&archivePactStatus="+archivePactStatus ,'非系统生成合同及附件详情',function() {
		});
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

	//预览资料和凭证
	function viewFile(lk){
		//首先获取路径
		$.ajax({
			type:"post",
			url:webPath+lk,
			dataType:"json",
			data:{},
			success:function(data){
				if(data.flag=="success"){
					var isWin = (navigator.platform == "Win32") || (navigator.platform == "Windows");
					if(isWin){
						notie.alert(4, '如果无法正常打开文档，请在系统右侧工具栏下载pageoffice控件并安装，重启浏览器后再次打开文档', -1);
						$.ajax({
							type:"post",
							url:webPath+"/docUpLoad/viewFileAjax",
							dataType:"json",
							data:data.docManage,
							success:function(jsonData){
								if(jsonData.flag=="success"){
									var poCntObj = $.parseJSON(jsonData.poCnt);
									mfPageOffice.openPageOffice(poCntObj);

								}else{
									window.top.alert(jsonData.msg,0);
								}
							},
							error:function(){
								window.top.alert("不支持的文档类型或文件不存在！",0);
							},complete:function(){
								setTimeout(function() {
									notie.alert_hide();
								}, 1500);
							}
						});
					}else{
						window.top.alert("当前操作系统不支持在线预览，请下载到本地查阅！",0);
					}
				}else{
					window.top.alert(data.msg,0);
				}
			},
			error:function(){
				window.top.alert("文件不存在！",0);
			}
		});
	}
</script>
</head>
<body class="overflowHidden bg-white">
<div class="container form-container">
	<div class="scroll-content" id="quanxuan">
		<div class="col-md-10 col-md-offset-1 column margin_top_20">
			<div class="bootstarpTag">
				<div class="form-title"></div>
				<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
				<form method="post" id="cwBillForm" theme="simple" name="operform" action="${webPath}/archiveInfoMain/insertArchiveInfo">
					<dhcc:bootstarpTag property="formarchivemaininsert" mode="query"/>
				</form>
			</div>
			<div class="list-table">
				<div class="title">
					<span><i class="i i-xing blockDian"></i>资料列表</span>
				</div>
				<div class="docList content collapse in" id="docList" name="docList">
					<dhcc:tableTag property="tablearchiveapplydoclist" paginate="docList" head="true"></dhcc:tableTag>
				</div>
			</div>
			<div class="list-table">
				<div class="title">
					<span><i class="i i-xing blockDian"></i>其他资料列表</span>
					<button class="btn btn-link formAdd-btn" onclick="addPaper();" title="新增"><i class="i i-jia3"></i></button>
				</div>
				<div class="paperList content collapse in" id="paperList" name="paperList">
					<dhcc:tableTag property="tablearchivepaperapply" paginate="paperInfoDetailList" head="true"></dhcc:tableTag>
				</div>
			</div>

			<div class="list-table">
				<div class="title">
					<span><i class="i i-xing blockDian"></i>合同列表</span>
					<button type="button" class="btn btn-link pull-right  download-btn" onclick="checkAll();">全选</button>
				</div>
				<div class="templateList content collapse in" id="templateList" name="templateList">
					<dhcc:tableTag property="tablearchiveapplytemplatelist" paginate="successTemplateList" head="true"></dhcc:tableTag>
				</div>
			</div>
			<div class="list-table">
			<div class="title">
				<span><i class="i i-xing blockDian"></i>非系统生成合同及附件</span>
				<button class="btn btn-link formAdd-btn" onclick="addPactExtend();" title="新增"><i class="i i-jia3"></i></button>
			</div>
			<div class="expandTemplateList content collapse in" id="expandTemplateList" name="expandTemplateList">
				<dhcc:tableTag property="tablearchiveextendapply" paginate="pactDetailList" head="true"></dhcc:tableTag>
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
