<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>详情</title>
        <script type="text/javascript">
            var cusNo_pl = "${mfEsignHistory.cusNo}";// 客户号
            var cusNo = "${mfEsignHistory.cusNo}";// 客户号
            var appId = "${mfEsignHistory.appId}";// 申请号
            var fincId = "${mfEsignHistory.fincShowId}";// 借据号
            // 文档
            var temBizNo = "${mfEsignHistory.temBizNo}";// 文档关联的业务主键，可以是申请号、客户号、申请号、借据号及其他功能编号
            var temParm = 'cusNo=' + cusNo_pl ;// 文档书签取值依赖条件，目前支持appId pactId cusNo fincId repayDetailId
            var nodeNo = "${mfEsignHistory.nodeNo}";
            var esignType = "${mfEsignHistory.esignType}"
            var querySaveFlag_pl =(typeof (querySaveFlag) == 'undefined') ? '0' : querySaveFlag; //电子文档列表查询方法（1-该节点只查询已经保存过的文档0-查询全部）
            var approvalNodeNo = '${approvalNodeNo}';
            var pactId = "${mfEsignHistory.pactId}";
            var scNo = nodeNo;
            var ifEsignHistory = "1";
            var creditQueryAppId = temBizNo;
            if("1" == esignType){
                scNo = "0000000001";
            }else if("3" == esignType){
                creditQueryAppId = pactId;
            }
            var docParm = "creditQueryAppId="+creditQueryAppId+"&scNo=" + scNo + "&query=query&docType=6&cusNo="+cusNo_pl;//查询文档信息的url的参数
            $(function(){
                $("#uploadTree_1").hide();
                $(".scroll-content").mCustomScrollbar({
                    advanced : {
                        theme : "minimal-dark",
                        updateOnContentResize : true
                    }
                });
            });
        </script>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-8 col-md-offset-2 margin_top_20">
                    <div class="row clearfix">
                        <%@ include file="/component/doc/webUpload/uploadutil.jsp"%>
                    </div>
                    <c:if test="${mfBusFollowPactList != null}">
                        <div class="list-table">
                            <div class="title">
                                <span><i class="i i-xing blockDian"></i>从合同文档</span>
                                <button class="btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#followPact">
                                    <i class="i i-close-up"></i>
                                    <i class="i i-open-down"></i>
                                </button>
                            </div>
                            <div id="followPact" class="content collapse in" aria-expanded="true">
                                <dhcc:tableTag paginate="mfBusFollowPactList" property="tablemfBusFollowPactNo" head="true"/>
                            </div>
                        </div>
                    </c:if>
                    <div class="row clearfix">
                        <%@ include file="/component/model/templateIncludePage.jsp"%>
                    </div>
				</div>
	   		</div>
			<div class="formRowCenter">
	   			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
	   		</div>	
	   	</div>
	</body>
</html>