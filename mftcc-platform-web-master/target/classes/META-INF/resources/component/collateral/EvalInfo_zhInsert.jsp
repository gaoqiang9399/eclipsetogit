<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%
String appId = (String)request.getParameter("appId");
%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
		
	</head>
	<script type="text/javascript">
		var appId = '<%=appId%>';
		$(function() {
// 			$(".scroll-content").mCustomScrollbar({
// 				advanced : {
// 					theme : "minimal-dark",
// 					updateOnContentResize : true
// 				}
// 			});
			myCustomScrollbarForForm({
				obj:".scroll-content",
				advanced : {
					theme : "minimal-dark",
					updateOnContentResize : true
				}
			});
			var groupNameLabel = $("input[name=groupName]").parents(".rows")
					.find(".form-label");
			var groupNameLabelText = $(groupNameLabel).text();
			$(groupNameLabel).empty().append(
					"<span class='required'>*</span>" + groupNameLabelText);
			$("input[name=groupName]").attr("mustinput", "1");
		});
		
		function getLegalIdType() {
			var legalIdType = $("select[name =legalIdType]").val();
			if (legalIdType == "0") {
				$("input[name=legalIdNum]").attr("alt", "idnum");
			} else {
				$("input[name=legalIdNum]").attr("alt", "tmp");
			}
			$("input[name=legalIdNum]").val("");
		};

		function ifGroupCustomer(obj) {
			var ifGroupType = $(obj).val();
			if (ifGroupType == 0) {//非集团客户
				$("input[name=groupName]").attr("mustinput", "0");
				$("input[name=groupName]").parents(".rows").hide();
			} else {//集团客户
				$("input[name=groupName]").attr("mustinput", "1");
				$("input[name=groupName]").parents(".rows").show();
			}
		}

		function insertEvalInfo(obj) {
			console.log("insertEvalInfo");
			var flag = submitJsMethod($(obj).get(0), '');
			if (flag) {
				var url = $(obj).attr("action");
				var dataParam = JSON.stringify($(obj).serializeArray());
				LoadingAnimate.start();
				jQuery.ajax({
					url : url,
					data : {
						ajaxData : dataParam,
						appId:appId
					},
					type : "POST",
					dataType : "json",
					beforeSend : function() {
					},
					success : function(data) {
						LoadingAnimate.stop();
						if (data.flag == "success") {
							//					  alert(top.getMessage("SUCCEED_OPERATION"),1);
							top.addFlag = true;
							if (data.htmlStrFlag == "1") {
								top.htmlStrFlag = true;
								top.htmlString = data.htmlStr;
							}
							window.top.alert("保存成功，业务流转到二次提报！",3);
							var url = '${webPath}/mfBusApply/getSummary?appId='+data.appId;
							$(top.window.document).find(".pt-page-current").find("iframe").eq(0).attr("src",url);
							myclose_click();
							//window.close();
							//myclose_showDialogClick();
							if (callback && typeof (callback) == "function") {
								callback.call(this, data);
							}
						} else if (data.flag == "error") {
							alert(data.msg, 0);
						}
					},
					error : function(data) {
						LoadingAnimate.stop();
						alert(top.getMessage("FAILED_OPERATION"," "), 0);
					}
				});
			}
		}
		
		function getCusMngNameDialog(userInfo) {
			$("input[name=cusMngName]").val(userInfo.opName);
			$("input[name=cusMngNo]").val(userInfo.opNo);
		};
		
		//从集团客户放大镜赋值给表单属性
		function getGroInfoArtDialog(groupInfo) {
			$("input[name=groupName]").val(groupInfo.groupName);
			$("input[name=groupNo]").val(groupInfo.groupNo);
			$("input[name='groupName']").attr("readonly", true);
		};
		
		function selectAreaCallBack(areaInfo) {
			$("input[name=careaProvice]").val(areaInfo.disName);
		};
		
		function calConfirmAmount(){
			var value = $("input[name=evalAmount]").val();
			if(value.length == 0){
				//alert("请先输入评估价值");
				$.myAlert.Alert("请先输入评估价值");
			}
			var rate = $("input[name=mortRate]").val();
			$("input[name=confirmAmount]").val(value*rate/100);
		};
	</script>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-8 col-md-offset-2 column margin_top_20">
					<div class="bootstarpTag">
			            <div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form  method="post" id="evalInfoInsert" theme="simple" name="operform" action="${webPath}/evalInfo/insertAjax">
								<dhcc:bootstarpTag property="formdlevalinfo0002" mode="query"/>
						</form>	
					</div>
				</div>	
			</div>
			<div class="formRowCenter">
	   			<dhcc:thirdButton value="保存" action="保存" onclick="insertEvalInfo('#evalInfoInsert');"></dhcc:thirdButton>
	   			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
	   		</div>
   		</div>
	</body>
</html>
