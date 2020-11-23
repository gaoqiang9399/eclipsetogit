<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
		<script type="text/javascript" src='${webPath}/component/include/uior_val1.js'> </script>
	</head>
	<script type="text/javascript">
	
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

		function insertChkInfo(obj) {
			var flag = submitJsMethod($(obj).get(0), '');
			if (flag) {
				var url = $(obj).attr("action");
				var dataParam = JSON.stringify($(obj).serializeArray());
				LoadingAnimate.start();
				jQuery.ajax({
					url : url,
					data : {
						ajaxData : dataParam,
					},
					type : "POST",
					dataType : "json",
					beforeSend : function() {
					},
					success : function(data) {
						LoadingAnimate.stop();
						if (data.flag == "success") {
							//					  alert("操作成功！",1);
							top.addFlag = true;
							if (data.htmlStrFlag == "1") {
								top.htmlStrFlag = true;
								top.htmlString = data.htmlStr;
							}
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
						alert("操作失败！", 0);
					}
				});
			}
		}
	</script>
	<body class=" overflowHidden bg-white">
	<div class="container form-container">
		<div class="scroll-content">
			<div class="col-md-8 col-md-offset-2 column margin_top_20">
				<div class="bootstarpTag">
					<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
					<form  method="post" id="instockInsert" theme="simple" name="operform" action="${webPath}/keepInfo/handleAjax1">
						<dhcc:bootstarpTag property="formdlkeephandle0009" mode="query"/>
					</form>
				</div>
			</div>	
		</div>
		<div class="formRowCenter">
   				<dhcc:thirdButton value="保存" action="保存" onclick="insertChkInfo('#instockInsert');"></dhcc:thirdButton>
	   			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
   		</div>
   		</div>
	</body>
</html>
