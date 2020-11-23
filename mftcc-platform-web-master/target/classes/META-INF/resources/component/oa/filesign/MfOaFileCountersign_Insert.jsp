<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>新增</title>
<script type="text/javascript">
	var aloneFlag = true;
	var dataDocParm = {
		relNo : '${fileNo}',
		docType : "fileSignDoc",
		docTypeName : "协议文件",
		docSplitName : "",
		query : "",
	};

	$(function() {
		// 		$(".scroll-content").mCustomScrollbar({
		// 			advanced:{
		// 				updateOnContentResize:true
		// 			}
		// 		});
		myCustomScrollbarForForm({
			obj : ".scroll-content",
			advanced : {
				updateOnContentResize : true
			}
		});
	})

    function fileSelectCusDialog() {
        selectCusDialog(fileSelectCusBack,"","","8");
    }

    //选择客户回调
    var fileSelectCusBack=function(cus){
        cusNo=cus.cusNo;
        $("input[name=cusNo]").val(cusNo);
        $("input[name=cusName]").val(cus.cusName);
        $("input[name=idNum]").val(cus.idNum);
        $("input[name=contactsTel]").val(cus.contactsTel);
    };
</script>
</head>
<body class="overFlowHidden bg-white">
	<div class="container form-container">
		<div class="scroll-content">
			<div class="col-md-8 col-md-offset-2 column margin_top_20">
				<div class="bootstarpTag">
					<div class="form-title"></div>
					<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
					<form method="post" id="fileSignInsert" theme="simple"
						name="operform"
						action="${webPath}/mfOaFileCountersign/insertAjax">
						<dhcc:bootstarpTag property="formfilesign0002" mode="query" />
					</form>
				</div>
				<div class="row clearfix">
					<div class="col-xs-12 column">
						<div id="doc_div"></div>
						<%@ include
							file="/component/doc/webUpload/uploadutil.jsp"%>
					</div>
				</div>
			</div>
		</div>
		<div class="formRowCenter">
			<dhcc:thirdButton value="保存" action="提交" onclick="MfOaFileCountersign_Insert.myInsert('#fileSignInsert');"></dhcc:thirdButton>
			<dhcc:thirdButton value="关闭" action="关闭" onclick="myclose_click();" typeclass="cancel"></dhcc:thirdButton>
		</div>
	</div>
</body>
<script>
	var MfOaFileCountersign_Insert = function(window, $) {
		var _myInsert = function(obj) {
			var flag = submitJsMethod($(obj).get(0), '');
			if (flag) {
				var url = $(obj).attr("action");
				var dataParam = JSON.stringify($(obj).serializeArray());
				LoadingAnimate.start();
				$.ajax({
					url : url,
					data : {
						ajaxData : dataParam
					},
					type : 'post',
					dataType : 'json',
					success : function(data) {
						LoadingAnimate.stop();
						if (data.flag == "success") {
							window.top.alert(data.msg, 3);
							myclose_click();
						} else {
							window.top.alert(data.msg, 0);
						}

					},
					error : function() {
						LoadingAnimate.stop();
						alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
					}
				});
			}
		}
		return {
			myInsert : _myInsert
		}
	}(window, jQuery);
</script>
</html>