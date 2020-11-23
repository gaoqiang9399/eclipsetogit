<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%
	String wkfAppId = (String)request.getParameter("wkfAppId");
	String appId = (String)request.getParameter("appId");
	String cusNo = (String)request.getParameter("cusNo");
%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
		<script type="text/javascript">
			var wkfAppId="${wkfAppId}";
			var appId='<%=appId%>';
			var cusNo='<%=cusNo%>';
			var busModel = '${mfBusApply.busModel}';
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
			});
			/* $(function(){
				var mfBusPledgeList = eval("(" + '${json}' + ")").mfBusPledgeList;
				$(".content_table").empty();
				$.each(mfBusPledgeList,function(i,pledge){
					$.ajax({
						url:webPath+"/mfBusPledge/getimportInputForm?pleId="+pledge.pleId+"&appId="+appId,
						type:'post',
						dataType:'html',
						success:function(data){
							var $html = $(data);
							var formStr = $html.find("form").prop("outerHTML");
							$(".content_table").append(formStr);
							}
					});
				});
			}); */
			window.ajaxInsert1 = function(obj,callback){
				var flag = submitJsMethod($(obj).get(0), '');
				if(flag){
					var url = $(obj).attr("action");
					var dataParam = JSON.stringify($(obj).serializeArray()); 
					LoadingAnimate.start();
					jQuery.ajax({
						url:url,
						data:{ajaxData:dataParam,wkfAppId:wkfAppId,appId:appId,busModel:busModel},
						type:"POST",
						dataType:"json",
						beforeSend:function(){  
						},success:function(data){
							LoadingAnimate.stop();
							if(data.flag == "success"){
								  //$.myAlert.Alert(top.getMessage("SUCCEED_OPERATION"));
// 								  window.top.alert(top.getMessage("SUCCEED_OPERATION"),1);
								  $.each(data,function(name,value) {
									   setFormEleValue(name, value);//调用公共js文件的方法表单赋值
								  });
								  var pleInfoThis = new Object();
								  pleInfoThis.pledgeName = $("input[name=pledgeName]").val();
								  pleInfoThis.envalue = parseInt($("input[name=envalue]").val())/10000;
								  pleInfoThis.pledgeRate = $("input[name=pledgeRate]").val();
								  pleInfoThis.pledgeNo = $("input[name=pledgeNoShow]").val();
								  top.flag=true;
								  top.pleFlag = true;
								  top.pleInfo = pleInfoThis;
								  
								 myclose_click();
							}else{
								 window.top.alert(data.msg,0);
							}
						},error:function(data){
							 //$.myAlert.Alert(top.getMessage("FAILED_OPERATION"," "));
							 LoadingAnimate.stop();
							 alert(top.getMessage("FAILED_OPERATION"," "),0);
						}
					});
				}
			}
			
			
		</script>
	</head>
	<%-- <body class="overflowAuto body_bg">
		<div class="bigform_content">
			<div class="content_table">
				<form  method="post" theme="simple" name="operform" action="${webPath}/mfBusPledge/pledgeImportAjax">
					<dhcc:bigFormTag property="formple0005" mode="query"/>
					<div class="title bgColor">
								<h4>押品清单</h4>
					<div class="content">
							<dhcc:tableTag paginate="mfBusPledgeDetailList" property="tablepledetail0002" head="true" />
					</div>
					</div>
					<div class="formRowCenter">
		    			<dhcc:thirdButton value="保存" action="保存"  onclick="ajaxInsert1(this.form)"></dhcc:thirdButton>
		    		</div>
				</form>	
			</div>
		</div>
	</body>
 --%>
 <body class="overflowHidden bg-white">
	<div class="container form-container">
		<div class="scroll-content">
			<div class="col-md-8 col-md-offset-2 column margin_top_20">
				<div class="bootstarpTag">
					<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
					<form  method="post" theme="simple" name="operform" id= "operform" action="${webPath}/mfBusCollateralRel/collateralInstockAffirmAjax">
						<div class="form-content">
							<dhcc:bootstarpTag property="formcollateralinstockAffirm0001" mode="query" />
							<div class="bigform_content col_content">
								<div class="">
									<div class="title"><h5 >押品清单</h5></div>
									<div class="table_content">
										<dhcc:tableTag paginate="mfBusPledgeDetailList" property="tablepledetail0002" head="true" />
									</div> 
								</div> 
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
		<div class="formRowCenter">
			<dhcc:thirdButton value="保存" action="保存" onclick="ajaxInsert1('#operform');"></dhcc:thirdButton>
			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
		</div>
</div>
</body> 
</html>
