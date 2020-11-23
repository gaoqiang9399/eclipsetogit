<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>详情</title>
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
		</script>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-8 col-md-offset-2 column margin_top_20">
					<div class="bootstarpTag">
						<div class="form-title">员工详情</div>
		           		<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form method="post" id="updateSysUserForm" theme="simple" name="operform" action="${webPath}/sysUser/updateAjax">
							<dhcc:bootstarpTag property="formsys5005" mode="query"/>						
						</form>
					</div>
				</div>	
			</div>
			<div class="formRowCenter">
				<dhcc:thirdButton value="保存" action="保存" onclick="saveSysUser('#updateSysUserForm');"></dhcc:thirdButton>
				<dhcc:thirdButton value="关闭" action="取消" typeclass="cancel" onclick="cancelSysUserMange();"></dhcc:thirdButton>
			</div>
		</div>
	</body>
</html>