<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>新增</title>
<link rel="stylesheet" href="${webPath}/UIplug/select3/css/select3.css" />
<script type="text/javascript" src="${webPath}/UIplug/select3/js/select3-full.js"></script>
<script type="text/javascript" src="${webPath}/component/stamp/js/MfStampPact_input.js"></script>
<link rel="stylesheet" type="text/css" href="${webPath}/component/model/css/templateIncludePage.css?v=${cssJsVersion}" />
<script type="text/javascript" src="${webPath}/component/model/js/mfPageOffice.js?v=${cssJsVersion}"></script>
<script type="text/javascript" src="${webPath}/component/model/js/templateIncludePage.js?v=${cssJsVersion}"></script>
<script type="text/javascript" src="${webPath}/component/model/js/MfBusTemplate_fileListByPage.js?v=${cssJsVersion}"></script>
<script type="text/javascript" src="${webPath}/component/pact/js/MfBusPactInsert.js?v=${cssJsVersion}"></script>
<script type="text/javascript" src='${webPath}/component/auth/js/MfCusCreditContract_Detail.js'></script>
<script type="text/javascript">
	var stampId ='${stampId}';
    var appId  = '${mfStampPact.appId}';
    var creditAppId = '${mfStampPact.creditAppId}';
    var cusNo  = '${mfStampPact.cusNo}';
    var queryType  = '${queryType}';
    var stampType  = '${mfStampPact.stampType}';
    var temParm = 'appId=' + appId+"&creditAppId="+ '${mfStampPact.creditAppId}'+"&cusNo="+ cusNo ;
    $(function () {
        MfStampPact_input.init();
        templateIncludePage.init();
	})

	function save(obj){
        //选中的id
        var templateId = "";
        var extendId = "";
        //文档份数
        var docNum="";
        var docNumflag=0;
        //文档份数
        var docExtendNum="";
        var docExtendNumflag=0;
        $('#mfBusPactExtendListDiv input:checkbox:checked').each(function(){
            extendId += "," + $(this).val().split("=")[1];


            var docExtendNumThis =  $(this).parent().parent().find("input[name='docExtendNum']").val();
            if(docExtendNumThis==""){
                docExtendNumflag=1;
            }else{
                docExtendNum += "," + docExtendNumThis;
            }
        });
        $('#template_div input:checkbox:checked').each(function(){
            templateId += "," + $(this).val().split("=")[1];
            var docNumThis =  $(this).parent().parent().find("input[name='ext10']").val();
            if(docNumThis==""){
                docNumflag=1;
            }else{
                docNum += "," + docNumThis;
            }
        });
        if(templateId==""&&extendId==""){
            window.top.alert("请选择需要用印的文档",0);
            return;
		}
        if(docNumflag==1){
            window.top.alert("选择的用印的文档，未填写份数",0);
            return;
		}
        if(docExtendNumflag==1){
            window.top.alert("选择的用印的文档，未填写份数",0);
            return;
        }
		var flag = submitJsMethod($(obj).get(0), '');
		if (flag) {
			LoadingAnimate.start();
			$.ajax({
				type : "POST",
				data:{ajaxData:JSON.stringify($(obj).serializeArray()),templateId:templateId,docNum:docNum,extendId:extendId,docExtendNum:docExtendNum,queryType:queryType},
				url : "${webPath}/mfStampPact/insertAjax",
				dataType : "json",
				success : function(data) {
					LoadingAnimate.stop();
					if(data.flag=="success"){
						window.top.alert(data.msg,3);
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
				<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
				<form method="post" id="stampPactForm" theme="simple" name="operform" action="${webPath}/mfStampPact/insertAjax">
					<dhcc:bootstarpTag property="formstamppactedit" mode="query"/>
				</form>
			</div>
			<div class="list-table" id="mfBusPactExtendListDiv">
				<div class="title">
					<span><i class="i i-xing blockDian"></i>非系统生成相关合同</span>
					<c:if test="${queryType=='2'}">
						<c:choose>
							<c:when test="${stampType == '1'}">
								<button class="btn btn-link formAdd-btn" onclick="mfCusCreditContractDetail.addPactExtend('${queryType}');" title="新增"><i class="i i-jia3"></i></button>
							</c:when>
							<c:otherwise>
								<button class="btn btn-link formAdd-btn" onclick="MfBusPactInsert.addPactExtend('${queryType}');" title="新增"><i class="i i-jia3"></i></button>
							</c:otherwise>
						</c:choose>
					</c:if>
					<button class=" btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#mfBusPactExtendList">
						<i class="i i-close-up"></i><i class="i i-open-down"></i>
					</button>
					<button type="button" class="btn btn-link pull-right  download-btn" onclick="MfStampPact_input.checkExtendAll();">全选</button>
				</div>
				<div class="content collapse in" id="mfBusPactExtendList" name="mfBusPactExtendList">
					<dhcc:tableTag property="tablepactExtendStamp" paginate="mfBusPactExtendList" head="true"></dhcc:tableTag>
				</div>
			</div>
			<div name="stampTemplateInfo" title="文档信息" class="dynamic-block list-table" >
				<div id="template_div" class="template_div" style="display: none;">
					<div class="list-table">
						<div class="title">
							<span><i class="i i-xing blockDian"></i>电子文档</span>
							<button class="btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#bizConfigs">
								<i class="i i-close-up"></i>
								<i class="i i-open-down"></i>
							</button>
							<button type="button" class=" btn btn-link pull-right  download-btn" onclick="MfBusTemplate_fileListByPage.downloadSelTemplateZip();">打包下载</button>
							<button type="button" class="btn btn-link pull-right  download-btn" onclick="MfBusTemplate_fileListByPage.checkAll();">全选</button>
						</div>
						<div id="bizConfigs" class="content collapse in" aria-expanded="true"></div>
						<div id="qrcode"></div>
					</div>
				</div>
			</div>
		</div>

	</div>
	<div class="formRowCenter">
		<dhcc:thirdButton value="保存" action="保存" onclick="save('#stampPactForm');"></dhcc:thirdButton>
		<dhcc:thirdButton value="关闭" action="取消" typeclass="cancel" onclick="closeWindow();"></dhcc:thirdButton>
	</div>
</div>
</body>
</html>
