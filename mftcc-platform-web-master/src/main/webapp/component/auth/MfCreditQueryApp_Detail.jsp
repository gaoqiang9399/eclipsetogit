<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>详情</title>
        <script type="text/javascript" src='${webPath}/component/auth/js/MfCreditQueryApp_Detail.js'></script>
        <script type="text/javascript">
            var cusNo_pl = "${mfCreditQueryApp.cusNo}";// 客户号
            var cusNo = "${mfCreditQueryApp.cusNo}";// 客户号
            var appId = "";// 申请号
            var fincId = "";// 借据号
            var pactId = "";// 合同id
            var ifEsignHistory = "1";
            // 文档
            var temBizNo = "${mfCreditQueryApp.id}";// 文档关联的业务主键，可以是申请号、客户号、申请号、借据号及其他功能编号
            var temParm = 'cusNo=' + cusNo_pl ;// 文档书签取值依赖条件，目前支持appId pactId cusNo fincId repayDetailId
            var nodeNo = "${cusType}";
            var querySaveFlag_pl =(typeof (querySaveFlag) == 'undefined') ? '0' : querySaveFlag; //电子文档列表查询方法（1-该节点只查询已经保存过的文档0-查询全部）
            var approvalNodeNo = '${approvalNodeNo}';
            var docParm = "creditQueryAppId="+temBizNo+"&scNo=0000000001&query=query&docType=6&cusNo="+cusNo_pl;//查询文档信息的url的参数
            $(function(){
                MfCreditQueryApp_Detail.init();
            });
        </script>
    </head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-8 col-md-offset-2 margin_top_20">
					<div class="bootstarpTag">
						<form method="post" id="MfCreditQueryAppForm" theme="simple" name="operform" action="${webPath}/mfCreditQueryApp/updateAjax">
							<dhcc:bootstarpTag property="formCreditQueryAppDetail" mode="query"/>
						</form>
					</div>
                    <div class="row clearfix">
                        <%@ include file="/component/doc/webUpload/uploadutil.jsp"%>
                    </div>
                    <div class="row clearfix">
                        <%@ include file="/component/model/templateIncludePage.jsp"%>
                    </div>
				</div>
	   		</div>
			<div class="formRowCenter">
				<!-- ajaxSave方法不适用时需另行实现。新写JS代码必须闭包。 -->
	   			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
	   		</div>	
	   	</div>
	</body>
</html>