<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<!-- 
				两列表单使用 col-md-8 col-md-offset-2
				四列表单使用 col-md-10 col-md-offset-1
				 -->
				<div class="col-md-10 col-md-offset-1 margin_top_20">
					<div class="bootstarpTag">
						<!-- <div class="form-title">客户信息变更申请表</div> -->
						<c:if test="${flagMap.flag == '1'}">
							<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						</c:if>
						<c:if test="${flagMap.flag == '0'}">
							<div class="form-tips">${ flagMap.msg}</div>
						</c:if>
						<form method="post" id="MfCusInfoChangeForm" theme="simple" name="operform" action="${webPath }/mfCusInfoChange/insertAjax">
							<dhcc:bootstarpTag property="forminfochangebase" mode="query"/>
						</form>
					</div>
					<div style="display: table" class="col-md-12 col-md-offset-0 margin_top_10" id="test">
						<div class="row clearfix">
							<div class="col-xs-12 column">
								<div class="list-table-replan">
									<div class="content margin_left_15 collapse in" id="tablechangeInfo0001">
										<dhcc:tableTag property="tablechangeInfo0001" paginate="tablechangeInfo0001" head="true"></dhcc:tableTag>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<!--上传文件-->
				<div class="row clearfix">
					<div class="col-xs-10 col-md-offset-1 column">
						<%@include file="/component/doc/webUpload/uploadutil.jsp"%>
					</div>
				</div>
	   		</div>
			<div class="formRowCenter">
				<!-- ajaxSave方法不适用时需另行实现。新写JS代码必须闭包。 -->
				<c:if test="${flagMap.flag == '1'}">
	   				<dhcc:thirdButton value="保存" action="保存" onclick="MfCusInfoChange_Insert.ajaxSave('#MfCusInfoChangeForm')"></dhcc:thirdButton>
	   			</c:if>
	   			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
	   		</div>	
		</div>
	</body>
	<script type="text/javascript" src="${webPath}/component/cus/infochange/js/MfCusInfoChange_Insert.js"></script>
	<script type="text/javascript">
        var webPath = "${webPath}";
        var changeId = '${mfCusInfoChange.changeId}';
        var aloneFlag = true;
        var updateMap = '${updateMap}';
        var flag = '${flagMap.flag}';
        var beforeValueShow="${beforeValueShow}";
        var aftervalueShow="${aftervalueShow}";
        var changeFieldName="${changeFieldName}";
        var fieldType="${fieldType}";
        var query = "";
        if(flag == "0"){
            query = "query";
        }
        var dataDocParm={
            relNo:changeId,
            docType:"messageDoc",
            docTypeName:"附件资料",
            docSplitName:"附件资料",
            query:query
        };
        $(function(){
            MfCusInfoChange_Insert.init();
        });
	</script>
</html>
