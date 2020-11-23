<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
		<style type="text/css">
			.formDiv {
				margin-left: 460px;
				margin-top:190px;
				width:50%;	
				position: absolute;
				
			}
			.bigform_content table{
				margin: 0px;
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
			
			.ui-dialog-body .ui-dialog-content{
				    overflow-y: scroll;
			}
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
				$("textarea[name=itemSqlDesc]").parents("tr").show();
				$("input[name=itemSql]").parents("tr").show();
				
				$("textarea[name=itemSqlDesc]").attr("mustinput","1");
				$("input[name=itemSql]").attr("mustinput","1");
				var itemSqlLabel=$("input[name=itemSql]").parents("tr").find("label");
				var itemSqlLabelText =$(itemSqlLabel).text();
				$(itemSqlLabel).empty().append("<font color='#FF0000'>*</font>"+itemSqlLabelText); 
				var itemSqlDescLabel=$("textarea[name=itemSqlDesc]").parents("tr").find("label");
				var itemSqlDescLabelText =$(itemSqlDescLabel).text();
				$(itemSqlDescLabel).empty().append("<font color='#FF0000'>*</font>"+itemSqlDescLabelText);
				$("input[name=itemJavaClass]").parents("tr").hide();
				$("textarea[name=itemJavaDesc]").parents("tr").hide(); 
				$("input[name=itemJavaClass]").attr("mustinput","0");
				$("textarea[name=itemJavaDesc]").attr("mustinput","0");
				var itemJavaClassLabel=$("input[name=itemJavaClass]").parents("tr").find("label");
				var itemJavaClassLabelText =$(itemJavaClassLabel).text();
				$(itemJavaClassLabel).empty().append("<font color='#FF0000'>*</font>"+itemJavaClassLabelText); 
				var itemJavaDescLabel=$("textarea[name=itemJavaDesc]").parents("tr").find("label");
				var itemJavaDescLabelText =$(itemJavaDescLabel).text();
				$(itemJavaDescLabel).empty().append("<font color='#FF0000'>*</font>"+itemJavaDescLabelText);
			}); 
	
			  
			function changeRiskPreventPlan(obj) {
				var riskPreventPlanType = $(obj).val();
				if (riskPreventPlanType == 1) {//sql语句  
					$("input[name=itemJavaClass]").parents("tr").hide();
				    $("textarea[name=itemJavaDesc]").parents("tr").hide();
					$("input[name=itemJavaClass]").attr("mustinput","0");
					$("textarea[name=itemJavaDesc]").attr("mustinput","0");
				    
					$("input[name=itemSql]").parents("tr").show();
					$("textarea[name=itemSqlDesc]").parents("tr").show();
					$("input[name=itemSql]").attr("mustinput","1"); 
					$("textarea[name=itemSqlDesc]").attr("mustinput","1");
				} else {//Java类
					$("input[name=itemJavaClass]").parents("tr").show();
				    $("textarea[name=itemJavaDesc]").parents("tr").show();
					$("textarea[name=itemSqlDesc]").parents("tr").hide();
					$("input[name=itemSql]").parents("tr").hide();
					$("input[name=itemJavaClass]").attr("mustinput","1");
					$("textarea[name=itemJavaDesc]").attr("mustinput","1");
					$("textarea[name=itemSqlDesc]").attr("mustinput","0");
					$("input[name=itemSql]").attr("mustinput","0");			
				}
			}
		
		
		
			function cleanRiskPreventPlan(){
				var riskPreventPlanType = $("select[name=riskPreventPlan]").val();
				if(1==riskPreventPlanType){
					$("input[name=itemJavaClass]").val("");
					$("textarea[name=itemJavaDesc]").val("");
				}else{
					$("input[name=itemSql]").val("");
					$("textarea[name=itemSqlDesc]").val("");
				}
			}
			
			window.ajaxInsert = function(obj) {
				var flag = submitJsMethod($(obj).get(0), '');
				cleanRiskPreventPlan();
				if (flag){
					var url = $(obj).attr("action")+"?pageStr="+pageStr;
					var dataParam = JSON.stringify($(obj).serializeArray());
					LoadingAnimate.start();
					$.ajax({
							url : url,
							data : {ajaxData : dataParam},
							type : 'post',
							dataType : 'json',
							success : function(data) {
							LoadingAnimate.stop();
								if (data.flag == "success") {
									window.top.alert(data.msg, 1);
									var url=webPath+"/riskItem/getListPage?pageStr="+pageStr;
									$(top.window.document).find(".pt-page-current").find("iframe").eq(0).attr("src",url);
									myclose();
								} else {
									window.top.alert(data.msg, 0);
								}
							},
							error : function() {
								alert(top.getMessage("ERROR_REQUEST_URL", ""));
							}
						});
					}
			}

		function fzpz(){
			var itemNo = $("input[name='itemNo']").val();
			dialog({
				id:'fzpz-dialog',
				url:webPath+"/riskItemThreashode/getListPageByItemNo?itemNo="+itemNo+"&query=",		
				backdropOpacity:0,
				width:800,
				height:400,
				title:"阀值配置"
			}).showModal();
			/* layer.open({
				  type: 1, 
				  content: $("#fzpz-div") 
			}); */
		};
		
		</script>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-10 col-md-offset-1 column margin_top_20">					
					<div class="bootstarpTag fourColumn">
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form method="post" id="riskItemInsertForm" theme="simple" name="operform" action="${webPath}/riskItem/insertAjax">
							<dhcc:bootstarpTag property="formrisk0002" mode="query" />
						</form>
					</div>
				</div>
			</div>
			<div class="formRowCenter">
				<%-- <dhcc:thirdButton value="阀值配置" action="阀值配置" onclick="fzpz();"></dhcc:thirdButton> --%>
				<dhcc:thirdButton value="保存" action="提交" onclick="ajaxInsert('#riskItemInsertForm');"></dhcc:thirdButton>
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
