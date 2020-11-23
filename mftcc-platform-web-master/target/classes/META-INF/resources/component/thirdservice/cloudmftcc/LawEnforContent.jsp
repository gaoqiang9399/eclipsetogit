<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>法执情况</title>
		<style type="text/css">
			.footer_loader,.backToTop{
				bottom:50px;
			}
		</style>
		<script type="text/javascript" src="${webPath}/component/thirdservice/cloudmftcc/js/MfThirdMftccHighcourt.js"></script>
	</head>
	<script type="text/javascript">
		var cusNo="${cusNo}";
		var appId="${appId}";
		var thirdFlag="${thirdFlag}";
		var ajaxData = JSON.parse('${ajaxData}');
		var lawEnfo="${lawEnfo}";
		var docParm="";
		$(function(){
			myCustomScrollbarForForm({
				obj:".scroll-content",
				advanced : {
					theme : "minimal-dark",
					updateOnContentResize : true
				}
			});
			//法执情况资料块滚动条
			myCustomScrollbarForForm({
				obj:"#lawDocInfo",
				advanced : {
					theme : "minimal-dark",
					updateOnContentResize : true
				}
			});
			MfThirdMftccHighcourt.initLawContent();
			MfThirdMftccHighcourt.init();
		});
	</script>
	<body class="overflowHidden bg-white">
		<div class="container block-left padding_right_10">
	   		<div class="scroll-content">
		   		<!-- 法执查询表单 -->
		   		<div id="lawEnforQueryForm" class="col-md-12 column tabCont" style="height:100%;">
		   			<div class="col-md-10 col-md-offset-1 margin_top_20">
						<div class="bootstarpTag">
							<!-- <div class="form-title">征信查询记录表</div> -->
							<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
							<form method="post" id="MfThirdMftccHighcourtForm" theme="simple" name="operform" action="${webPath}/mfThirdMftccHighcourt/insertAjax">
								<dhcc:bootstarpTag property="formlawenforcementBase" mode="query"/>
							</form>
						</div>
                        <!-- 业务征信查询历史 -->
                        <div id="creditQueryHisList" class="row column clearfix">
                            <div class="row clearfix bg-white tabCont">
                                <div class="col-md-12 column">
                                    <div class="search-div">
                                        <jsp:include page="/component/include/mySearch.jsp?blockType=2&placeholder=被查询人名称"/>
                                    </div>
                                </div>
                            </div>
                            <div class="row clearfix">
                                <div class="col-md-12">
                                    <div id="content" class="table_content" style="height: auto;">
                                    </div>
                                </div>
                            </div>
                        </div>
					</div>
		   			<!-- 客户征信资料 身份证、授权书 -->
					<div class="row clearfix" style="display:none">
						<div id="lawDocInfo" class="col-xs-10 col-md-offset-1 margin_top_20">
						</div>
					</div>
		   		</div>
		   		<!-- 法执结果 -->
		   		<div id="creditContent" class="col-md-8 col-md-offset-2 margin_top_20">
					<div class="bootstarpTag" id="creditContent">
						
					</div>
				</div>
	   		</div>

	   		 <div id="bussButton" class="formRowCenter">
				<!-- ajaxSave方法不适用时需另行实现。新写JS代码必须闭包。 -->
	   			<dhcc:thirdButton value="查询" action="查询" onclick="MfThirdMftccHighcourt.lawEnforQuery('#MfThirdMftccHighcourtForm')"></dhcc:thirdButton>
	   			<dhcc:thirdButton value="返回" action="返回" typeclass="cancel" onclick="myclose_click();"></dhcc:thirdButton>
	   		</div> 
	   		<div id="againButton" class="formRowCenter" style="display:none">
				<!-- ajaxSave方法不适用时需另行实现。新写JS代码必须闭包。 -->
	   			<dhcc:thirdButton value="重新查询" action="重新查询" onclick="MfThirdMftccHighcourt.lawQueryAgain()" ></dhcc:thirdButton>
	   			<dhcc:thirdButton value="返回" action="返回" typeclass="cancel" onclick="MfThirdMftccHighcourt.back();"></dhcc:thirdButton>
	   		</div>
	   	</div>
	</body>
		
</html>

