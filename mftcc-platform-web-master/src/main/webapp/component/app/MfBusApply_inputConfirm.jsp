<%@page import="net.sf.json.JSONObject"%>
<%@ page language="java" import="java.util.*,net.sf.json.JSONArray"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<%
String path = request.getContextPath();
String scNo = (String)request.getParameter("scNo");
String appId = (String)request.getParameter("appId");
String jsonBean = (String)request.getAttribute("jsonBean");
JSONArray wkfArray = null;
if(request.getAttribute("wkfVpList")!=null){
	wkfArray = JSONArray.fromObject(request.getAttribute("wkfVpList"));
}
%>
<%@ taglib uri="/WEB-INF/tld/loan.tld" prefix="dhcc" %>
<%@ include file="/component/wkf/Wkf_Base.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
		<script type="text/javascript" src="${webPath}/component/include/idCheck.js?v=${cssJsVersion}"></script>
		<script type="text/javascript" src="${webPath}/tech/wkf/detail/wjProcessDetailV2.js"></script>
		<script type="text/javascript" src="${webPath}/tech/wkf/detail/wjProcessDetailVertical.js"></script>
		<title>新增</title>	
		<meta charset="utf-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1"/>
		<script type="text/javascript" src="${webPath}/layout/view/js/jquery.layout.min.js"></script>
		<script type="text/javascript" src="${webPath}/layout/view/page/js/lavalamp.min.js"></script>
		<script type="text/javascript" src="${webPath}/layout/view/page/js/xixi.js"></script>
		<script type="text/javascript" src="${webPath}/layout/view/page/js/viewpoint.js"></script>
		<link rel="stylesheet" href="${webPath}/layout/view/themes/css/layout.css" />
		<link rel="stylesheet" href="${webPath}/layout/view/themes/css/menu.css" />
		<link rel="stylesheet" href="${webPath}/layout/view/themes/Font-Awesome/css/font-awesome.css" />
		<link rel="stylesheet" href="${webPath}/layout/view/page/css/screen.css" />
		<link rel="stylesheet" href="${webPath}/layout/view/page/css/wkfviewpoint.css" />
		<script type="text/javascript" src="${webPath}/component/include/laydate/laydate.dev.js"></script>
		<script type="text/javascript" src="${webPath}/component/include/laydate/laydate.month.js"></script>
		<script type="text/javascript" src='${webPath}/component/include/uior_val1.js'> </script>
		<link rel="stylesheet" href="${webPath}/themes/factor/css/menu.css" />
		<link rel="stylesheet" href="${webPath}/themes/factor/css/screen.css" />
		<script type="text/javascript" src="${webPath}/component/include/myAlert.js"></script>
		<style type="text/css">
			.inner-center{
				/* height : calc(100% - 150px) !important; */
			}
			.inner-north,.inner-center,#iframe_content{
				width : calc(100% - 25px) !important;
			}
			.inner-center span{
				margin-left: 30px;
				color: #32b5cb;
				font-size: 14px;
			}
			th{
				text-align:center;
			}
			.content-box{
				width: 80%;
			}
		</style>
		<script type="text/javascript">
			var appId = '<%=appId%>';
			var vpNo = '<%=request.getParameter("vpNo") %>';
			vpNo="23";
			var viewPointData = eval("("+'<%=request.getSession().getAttribute("viewPointData") %>'+")");
			var jsonBean = eval("("+ '<%=jsonBean%>'+")");
			var termType = ${mfBusApply.termType};
		</script>
	</head>
	<script type="text/javascript">
	var cusNo = '${mfBusApply.cusNo}';
	var fromPage = '${query}';
	var firstKindNo = '${mfBusApply.kindNo}';
	var maxAmt = null;
	var minAmt = null;
	var minTerm = null;
	var maxTerm = null;
				
	$(function(){
		getKindList(firstKindNo);
// 		$(".scroll-content").mCustomScrollbar({
// 			advanced:{
// 				updateOnContentResize:true
// 			}
// 		});
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced:{
				updateOnContentResize:true
			}
		});
	});
	function selectKindNo(){
		var kindNo = $("select[name=kindNo]").val();
		getForm(kindNo);
	}
	function getForm(val){
		$.ajax({
			url:webPath+"/mfBusApply/chooseFormAjax?kindNo="+val+"&cusNo="+cusNo,
			type:"post",
			dataType:"json",
			success:function(data){
				if(data.flag=="success"){
					var html = data.htmlStr;
				$("#updateForApplyForm").empty().html(html);
				getKindList(val);
				maxAmt = data.maxAmt;
				minAmt = data.minAmt;
				minTerm = data.minTerm;
				maxTerm = data.maxTerm;
				}
			}
			});
			
	}
	function getCusMngNameDialog(userInfo){
		$("input[name=cusMngName]").val(userInfo.opName);
		$("input[name=cusMngNo]").val(userInfo.opNo);
	};
	function getKindList(val){
	
		$.ajax({
			type:"post",
			url:webPath+"/mfSysKind/getAllKindListAjax",
			success:function(data){
			
				var kindList = data.kindList;
				var len = data.len;
				for(var i=0;i<len;i++){
					var kindNo = kindList[i].kindNo;
					var kindName = kindList[i].kindName;
					var busModel = kindList[i].busModel;
					if(kindNo!=val){
						$("select[name=kindNo]").append("<option name='"+busModel+"'"+"value='"+kindNo+"'>"+kindName+"</option>");
					}else{
						$("select[name=kindNo]").append("<option selected='selected' name='"+busModel+"'"+"value='"+kindNo+"'>"+kindName+"</option>");
						
					}
				}
			}
		});
	}

	
	function getFincUse(obj){
		$("input[name=fincUse]").val(obj.fincUse);
		$("input[name=fincUseName]").val(obj.fincUseShow);
	};
	function cancelApply(){
		myclose_click();
	};

	function updateForApply(obj){
			var url = $(obj).attr("action");
			var flag=submitJsMethod($(obj).get(0), '');
 			if(flag){
				var dataParam = JSON.stringify($(obj).serializeArray()); 
				LoadingAnimate.start();
				$.ajax({
					url:url,
					data:{ajaxData:dataParam},
					type:'post',
					dataType:'json',
					success:function(data){
						LoadingAnimate.stop();
						if(data.flag == "success"){
							window.top.alert(data.msg,3);
							top.flag = true;
							var url = webPath+'/mfBusApply/getSummary?appId='+data.appId;
							$(top.window.document).find(".pt-page-current").find("iframe").eq(0).attr("src",url);
							myclose_click();
						}else{
							alert(data.msg,0);
						}
					},error:function(){
						LoadingAnimate.stop();
						alert(top.getMessage("FAILED_SAVE"),0);
					}
				});
 			}else{
//  				alert(  top.getMessage("FAILED_SAVE"),0);
 			} 
		};
	
		//验证申请金额和申请期限是否符合产品设置的申请金额和期限的范围
		function checkByKindInfo(obj){
			var name = $(obj).attr("name");
			var title = $(obj).attr("title").split("(")[0];
			//申请金额
			if(name=="appAmt"){
				if(maxAmt!=null && minAmt!=null && maxAmt!="" && minAmt!=""){
					maxAmtNum = new Number(maxAmt);
					minAmtNum = new Number(minAmt); 
					var appAmt = $(obj).val();
					appAmt = appAmt.replace(/,/g, "");
					if(parseFloat(appAmt)<parseFloat(minAmtNum)||parseFloat(appAmt)>parseFloat(maxAmtNum)){
						$(obj).val(0);
						alert(title+"必须在"+new Number(minAmt)+"到"+new Number(maxAmt)+"之间",0);
					}
				}
			}
		}
		
		function checkTerm(obj){
			var name = $(obj).attr("name");
			var title = $(obj).attr("title").split("(")[0];
			//申请金额
			if(name=="term"){
				if(minTerm!=null && maxTerm!=null && minTerm!="" && maxTerm!=""){
				
					minTermNum = new Number(minTerm);
					maxTermNum = new Number(maxTerm);
					var appTerm = $(obj).val();
					appTerm = appTerm.replace(/,/g, "");
					
					if(parseFloat(appTerm)<parseFloat(minTermNum)||parseFloat(appTerm)>parseFloat(maxTermNum)){
						$(obj).val(0);
						alert(title+"必须在"+minTerm+"到"+maxTerm+"之间",0);
					}
				}
			}
		}
		function getCusMngNameDialog(userInfo){
			$("input[name=cusMngName]").val(userInfo.opName);
			$("input[name=cusMngNo]").val(userInfo.opNo);
		};
		
	</script>
<body class="overflowHidden bg-white">
	<div class="inner-north" style="background-color: #F8F9FC;">
		<div id="wrapper">
			<ul class="lavaLamp">
			</ul>
		</div>
	</div>
	<div class="inner-center">
		<div class="mf_content showOrHide container form-container" id="normal">
			<div class="scroll-content">
				<div class="col-md-8 col-md-offset-2 column margin_top_20 showOrHide">
					<div class="bootstarpTag">
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
	
						<form id="updateForApplyForm" method="post" theme="simple" name="operform" action="${webPath}/mfBusApplyActionAjax/updateForApplyAjax_confirm">
						    <dhcc:bootstarpTag property="formapply0001" mode="query" />  
						</form>
					</div>
				</div>
			</div>
			<div class="formRowCenter showOrHide">
				<dhcc:thirdButton value="保存" action="保存" onclick="updateForApply('#updateForApplyForm');"></dhcc:thirdButton>
				<dhcc:thirdButton value="取消" action="取消" typeclass="cancel" onclick="cancelApply();"></dhcc:thirdButton>
			</div>
			<div style="display: none;" id="fincUse-div"></div>
		</div>
		<iframe src="" marginheight="0" marginwidth="0" frameborder="0" scrolling="auto" width="100%" id="iframepage" name="iframepage"></iframe>
	</div>
	
</body>
</html>