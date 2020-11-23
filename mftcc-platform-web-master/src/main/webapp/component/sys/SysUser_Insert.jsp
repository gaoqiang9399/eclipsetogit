<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
		<link rel="stylesheet" href="${webPath}/UIplug/select3/css/select3.css" />
		<script type="text/javascript" src="${webPath}/UIplug/select3/js/select3-full.js"></script>
		<script type="text/javascript" src="${webPath}/component/sys/js/sysUser.js"></script>
		<script type="text/javascript">
		var ajaxData = '${ajaxData}';
	    ajaxData = eval("("+ajaxData+")");
	    var opNoType = "${opNoType}";
	    //角色列表
	    var roleArray =ajaxData.role;
	    function clearError(obj) {
			$(obj).removeClass("Required");
			//console.log("下一个节点"+$(obj).next().next());
			//console.log($(obj).next().next().text());
			if($(obj).next().next().text()=="不能为空") {
				$(obj).next().next().remove();
			}
		}
		var mobilemsg = '';
		function mobilecheck(obj){	
			//var phoneNum = obj.val();
			var phoneNum = $(obj).val();
			if(phoneNum!=""){		
				mobilemsg = isMobile(obj);
				 if(mobilemsg==""){
					 $(obj).removeClass("Required");
					 $(obj).parent().find(".error").remove();
				 }else{
					 func_uior_addTips(obj,mobilemsg);
					 $("input[name=mobile]").val("");
				 }		
			}
			return mobilemsg;
		}
		</script>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-10 col-md-offset-1 column margin_top_20">
					<div class="bootstarpTag">
						<div class="form-title">新增员工</div>
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form method="post" id="saveSysUserForm" theme="simple" name="operform" action="${webPath}/sysUser/insertAjax">
							<dhcc:bootstarpTag property="formsys5002" mode="query"/>
						</form>
					</div>
				</div>	
			</div>
			<div class="formRowCenter">
				<dhcc:thirdButton value="保存" action="保存" onclick="saveSysUser('#saveSysUserForm');"></dhcc:thirdButton>
				<dhcc:thirdButton value="关闭" action="取消" typeclass="cancel" onclick="cancelSysUserMange();"></dhcc:thirdButton>
			</div>
		</div>
	</body>
</html>
