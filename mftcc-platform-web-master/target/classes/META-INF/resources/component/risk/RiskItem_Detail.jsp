<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>详情</title>
		<style type="text/css">
			.formDiv {
				margin-left: 500px;
				margin-top:190px;
				width:50%;	
				position: absolute;
				
			}
			.bigform_content table{
				margin: 0px;
				margin-left:40px;
			}
			#fazhi{
				margin-right: 10px;
				background: #32b5cb none repeat scroll 0 0;
				color: #fff;
				border: none;
				cursor: pointer;
				height: 24px;
				font-size: 12px;
				line-height: 24px;
				padding: 0 15px;
				text-align: center;
				border-radius: 2px;
				margin-bottom:4px;
			}
			.formCol font{font-size:12px;}
		</style>
		<script type="text/javascript">
		 var pageStr = '${pageStr}';
			 $(function(){
// 				$(".scroll-content").mCustomScrollbar({
// 					advanced:{
// 						theme:"minimal-dark",
// 						updateOnContentResize:true
// 					}
// 				});
				myCustomScrollbarForForm({
					obj:".scroll-content",
					advanced : {
						theme : "minimal-dark",
						updateOnContentResize : true
					}
				});
				 var formSelectVal=$("select[name=riskPreventPlan]").val();
				 if(formSelectVal==1){//sql语句
				 	$("input[name=itemJavaClass]").parents("tr").hide();
				    $("input[name=itemJavaDesc]").parents("tr").hide();
					$("input[name=itemSqlDesc]").parents("tr").show();
					$("input[name=itemSql]").parents("tr").show();
				 }else{//java类 
				 	$("input[name=itemJavaClass]").parents("tr").show();
				    $("input[name=itemJavaDesc]").parents("tr").show();
					$("input[name=itemSqlDesc]").parents("tr").hide();
					$("input[name=itemSql]").parents("tr").hide();
				}
				
					var itemSqlLabel=$("input[name=itemSql]").parents("tr").find("label");
					var itemSqlLabelText =$(itemSqlLabel).text();
					$(itemSqlLabel).empty().append("<font color='#FF0000'>*</font>"+itemSqlLabelText); 
					var itemSqlDescLabel=$("input[name=itemSqlDesc]").parents("tr").find("label");
					var itemSqlDescLabelText =$(itemSqlDescLabel).text();
					$(itemSqlDescLabel).empty().append("<font color='#FF0000'>*</font>"+itemSqlDescLabelText);
					
					var itemJavaClassLabel=$("input[name=itemJavaClass]").parents("tr").find("label");
					var itemJavaClassLabelText =$(itemJavaClassLabel).text();
					$(itemJavaClassLabel).empty().append("<font color='#FF0000'>*</font>"+itemJavaClassLabelText);
					var itemJavaDescLabel=$("input[name=itemJavaDesc]").parents("tr").find("label");
					var itemJavaDescLabelText =$(itemJavaDescLabel).text();
					$(itemJavaDescLabel).empty().append("<font color='#FF0000'>*</font>"+itemJavaDescLabelText);
		
				var itemNo = "${itemNo}";
				 $("#iframe_content").attr("src",webPath+"/riskItemThreashode/getListPageByItemNo?itemNo="+itemNo+"&query=<s:property value='query'/>");
				
				 var sqlItemTd = $("td[lablename='itemSql']");
				 var sqlItemLabelLabel = sqlItemTd.find("label");
				 var query = $("input[name='query']").val();
				
				<c:if test="${query!='query'}">
				 	sqlItemLabelLabel.after("<font color=red>sql中阀值格式为#阀值键值英文名#</fond>").after("&nbsp<input id='fazhi' type='button' value='阀值配置'></input>");
				 </c:if>
				
				 var fazhidiv = $("div[name='formtest-2']");
				 
				 $("#fazhi").click(
				 	function() {
				 	fazhidiv.toggle();
				 	}
				 );
		
		
		});




			window.ajaxUpdate = function(obj) {
				var flag = submitJsMethod($(obj).get(0), '');
				if (flag){
					cleanRiskPreventPlan();
					var url = $(obj).attr("action")+"?pageStr="+pageStr;
					var dataParam = JSON.stringify($(obj).serializeArray());
					LoadingAnimate.start();
					$.ajax({
						url : url,
						data : {ajaxData : dataParam},
						type : 'post',
						dataType : 'json',
						success : function(data) {
						if (data.flag == "success") {
							LoadingAnimate.stop();
							window.top.alert(data.msg, 1);
							var url=webPath+"/riskItem/getListPage?pageStr="+pageStr;
							$(top.window.document).find(".pt-page-current").find("iframe").eq(0).attr("src",url);
							myclose();
						} else {
							window.top.alert(data.msg, 0);
							}
						},
						error : function() {
							alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
						}
					});
				}
			}
			
			
			function cleanRiskPreventPlan(){
				 var formSelectVal=$("select[name=riskPreventPlan]").val();
				 if(formSelectVal==1){//sql语句
				 $("input[name=itemJavaClass]").val("");
				 $("input[name=itemJavaDesc]").val("");
				 }else{//java类 
					 $("input[name=itemSql]").val("");
					 $("input[name=itemSqlDesc]").val("");
				 }	
			}
			

		
		
		
		function changeRiskPreventPlan(obj) {
				var riskPreventPlanType = $(obj).val();
				if (riskPreventPlanType == 1) {//sql语句  
					$("input[name=itemJavaClass]").parents("tr").hide();
				    $("input[name=itemJavaDesc]").parents("tr").hide();
					$("input[name=itemSqlDesc]").parents("tr").show();
					$("input[name=itemSql]").parents("tr").show();
					$("input[name=itemJavaClass]").attr("mustinput","0");
					$("input[name=itemJavaDesc]").attr("mustinput","0");
					$("input[name=itemSqlDesc]").attr("mustinput","1");
					$("input[name=itemSql]").attr("mustinput","1"); 
// 					$("input[name=itemSql]").parents(".rows").find(".required").remove();
// 					var itemSqlLabel=$("input[name=itemSql]").parents(".rows").find(".form-label");
// 					var itemSqlLabelText =$(itemSqlLabel).text();
// 					$(itemSqlLabel).empty().append("<span class='required'>*</span>"+itemSqlLabelText); 
// 					$("input[name=itemSqlDesc]").parents(".rows").find(".required"),remove();
// 					var itemSqlDescLabel=$("input[name=itemSqlDesc]").parents(".rows").find(".form-label");
// 					var itemSqlDescLabelText =$(itemSqlDescLabel).text();
// 					$(itemSqlDescLabel).empty().append("<span class='required'>*</span>"+itemSqlDescLabelText);
				} else {//Java类
					$("input[name=itemJavaClass]").parents("tr").show();
				    $("input[name=itemJavaDesc]").parents("tr").show();
					$("input[name=itemSqlDesc]").parents("tr").hide();
					$("input[name=itemSql]").parents("tr").hide();
					$("input[name=itemJavaClass]").attr("mustinput","1");
					$("input[name=itemJavaDesc]").attr("mustinput","1");
					$("input[name=itemSqlDesc]").attr("mustinput","0");
					$("input[name=itemSql]").attr("mustinput","0");				
// 					$("input[name=itemJavaClass]").parents(".rows").find(".required").remove();
// 					var itemJavaClassLabel=$("input[name=itemJavaClass]").parents(".rows").find(".form-label");
// 					var itemJavaClassLabelText =$(itemJavaClassLabel).text();
// 					$(itemJavaClassLabel).empty().append("<span class='required'>*</span>"+itemJavaClassLabelText);
// 					$("input[name=itemJavaDesc]").parents(".rows").find(".required").remove();
// 					var itemJavaDescLabel=$("input[name=itemJavaDesc]").parents(".rows").find(".form-label");
// 					var itemJavaDescLabelText =$(itemJavaDescLabel).text();
// 					$(itemJavaDescLabel).empty().append("<span class='required'>*</span>"+itemJavaDescLabelText); 
				}
			}
		
		
		function fzpz(){
			var itemNo = $("input[name='itemNo']").val();
			dialog({
				id:'fzpz-dialog',
				url:webPath+"/riskItemThreashode/getListPageByItemNo?itemNo="+itemNo+"&query=${query}/>",
				backdropOpacity:0,
				width:800,
				height:450,
				title:"阀值配置"
			}).showModal();
			
		};
		</script>
	</head>

<body class="overflowHidden bg-white">
	<div class="container form-container">
		<div class="scroll-content">
			<div class="col-md-10 col-md-offset-1 column margin_top_20">
				<div class="bootstarpTag fourColumn">
					<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
					<form method="post" id="riskItemDetailForm" theme="simple" name="operform" action="${webPath}/riskItem/updateAjax">
						<dhcc:bootstarpTag property="formrisk0002" mode="query" />
					</form>
				</div>
			</div>
		</div>
		<div class="formRowCenter">
			<%-- <dhcc:thirdButton value="阀值配置" action="阀值配置" onclick="fzpz();"></dhcc:thirdButton> --%>
			<dhcc:thirdButton value="保存" action="提交" onclick="ajaxUpdate('#riskItemDetailForm');"></dhcc:thirdButton>
			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
		</div>
	</div>
</body>
<script type="text/javascript">

if(pageStr == 'cus'){
	$("input[name='itemType']").val("客户准入");
	$("input[name='riskPreventClass']").val("客户");
}
if(pageStr == 'pro'){
	$("input[name='itemType']").val("产品准入");
	$("input[name='riskPreventClass']").val("产品");
}
if(pageStr == 'type'){
    $("input[name='itemType']").val("客户分类");
    $("input[name='riskPreventClass']").val("客户");
}
</script>
</html>