<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增授信申请表单</title>
	</head>
	<script type="text/javascript" src='${webPath}/component/auth/js/MfCusCreditApply_input.js'></script>
	<script type="text/javascript" src='${webPath}/component/auth/js/MfCusCreditAdjustApplyInput.js'></script>
	<script type="text/javascript" src='${webPath}/component/auth/js/MfCusCreditAdjustApplyInsert.js'></script>
	<script type="text/javascript" src='${webPath}/component/auth/js/MfCusCreditApply_Insert.js'></script>
	<script type="text/javascript" src='${webPath}/component/auth/js/creditTool.js'></script>
	<script type="text/javascript" src="${webPath}/component/include/calcUtil.js"></script>
	<script type="text/javascript" src="${webPath}/component/eval/js/cusEval.js?v=${cssJsVersion}"></script>
	<script type="text/javascript">
        var index = 0;  //动态增加产品计数使用
        var path = "${webPath}";
        var creditAppId='${creditAppId}';
        var adaptationKindNo='${adaptationKindNo}';
        var cusNo='${cusNo}';
        var jsonStr = '${json}';
        var initFlag = '${initFlag}';
        var mfSysKinds = null;
        var sign='${sign}';
        if(jsonStr != ""){
            mfSysKinds = eval("(" + '${json}' + ")").mfSysKinds;
        }
        var cusType='${mfCusCustomer.cusType}';
        var baseType = "${baseType}";
        var creditFlag='${creditFlag}';//是否授信标识0未授信1已授信
        var termFlag='${termFlag}';//如果已授信，当前日期是否在授信期限内
        var $form=$("#operform");
        var creditModel='${creditModel}';//授信模式 1客户授信 2立项授信3调整授信4临额调整
        var creditType='${creditType}';//授信类型 1客户授信 2立项授信
        var scNo='${scNo}';
        var showType = '${showType}';
        var docParm = "cusNo="+cusNo+"&relNo="+creditAppId+"&scNo="+scNo;//查询文档信息的url的参数
        var CREDIT_END_DATE_REDUCE ='${CREDIT_END_DATE_REDUCE}';// 授信结束日自动减一天
        var bankShowFlag ='${bankShowFlag}';// 显示合作银行及业务品种
        var ajaxData = '${ajaxData}';//合作银行
        var ifQuotaCalc = '${ifQuotaCalc}';
        var busModel = '${busModel}';
        var title ;
        $(function(){
            MfCusCreditApply_input.init();
            if(sign == "repeatName"){
                MfCusCreditApply_input.initCus();
            }
            if(ifQuotaCalc == '1'){
            }else{
                $("input[name='quotaCalc']").parent().parent().parent().hide();
            }
            title = $(top.window.document).find("#myModalLabel").text();
        });
        function closeMyDialog(){
            if(typeof(showType) !=  "undifined" && showType == "1"){
                window.location.href = webPath+"/mfCusCreditApply/getCusCreditApplyListPage";
            }else{
                myclose_click();
            }
        }
        var ajaxData = '${ajaxData}';
        if(ajaxData != ""){
            ajaxData = JSON.parse(ajaxData);
        }
        var bankInitFlag = 0;//判断银行选择组件是否初始化
        var areaInitFlag = 0;//判断银行区域选择组件是否初始化
        var agenciesInitFlag = 0;//判断银行支行选择组件是否初始化
        var breedInitFlag = 0;//判断业务品种选择组件是否初始化
	</script>
	<body class="overflowHidden bg-white">
		<div class="container form-container"  id ="creditInsert">
		<c:choose>
			<c:when test="${creditType=='2'}">
				<div class="scroll-content">
				<div class="col-md-10 col-md-offset-1 column margin_top_20">
					<div class="bootstarpTag fourColumn">
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form  method="post" id="MfCusCreditAdjustApplyForm" theme="simple" name="operform" action="${webPath}/mfCusCreditAdjustApply/insert">
							<dhcc:bootstarpTag property="formcreditapply0001" mode="query"/>
						</form>
					</div>
					<c:if test="${bankShowFlag=='1'}">
						<div class="list-table-replan" id="mfBusAgenciesInfo" hidden>
							<div class="title">
								<span>合作银行授信额度</span>
								<button class="btn btn-link formAdd-btn"  onclick="MfCusCreditApply_input.agenciesAdd();" title="新增"><i class="i i-jia3"></i></button>
							</div>
							<div class="content margin_left_15 collapse in" id="agenciesList">
								<dhcc:tableTag property="tablecreditAgenciesList" paginate="tablecreditAgenciesList" head="true"></dhcc:tableTag>
							</div>
						</div>
						<div class="list-table-replan" id="busBreedInfo" hidden>
							<div class="title">
								<span>业务品种授信额度</span>
								<button class="btn btn-link formAdd-btn"  onclick="MfCusCreditApply_input.breedAdd();" title="新增"><i class="i i-jia3"></i></button>
							</div>
							<div class="content margin_left_15 collapse in" id="busBreedList">
								<dhcc:tableTag property="tablecreditBreedList" paginate="tablecreditBreedList" head="true"></dhcc:tableTag>
							</div>
						</div>
					</c:if>
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
	   			<dhcc:thirdButton value="保存" action="保存" onclick="MfCusCreditApply_input.insertAdjApply('#MfCusCreditAdjustApplyForm')"></dhcc:thirdButton>
	   			<dhcc:thirdButton value="取消" action="取消" typeclass="cancel" onclick="closeMyDialog();"></dhcc:thirdButton>
	   		</div>
			</c:when>
			<c:otherwise>
				<div class="scroll-content">
				<div class="col-md-10 col-md-offset-1 column margin_top_20" id="cusCreditApplyDiv">
					<div class="bootstarpTag fourColumn">
				 		<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form  method="post" theme="simple" name="operform" id="operform" action="${webPath}/mfCusCreditApply/insertAjax">
							<dhcc:bootstarpTag property="formcreditapply0001" mode="query"/>
						</form>
					</div>
					<c:if test="${bankShowFlag=='1'}">
						<div class="list-table-replan" id="mfBusAgenciesInfo" hidden>
							<div class="title">
								<span>合作银行授信额度</span>
								<button class="btn btn-link formAdd-btn"  onclick="MfCusCreditApply_input.agenciesAdd();" title="新增"><i class="i i-jia3"></i></button>
							</div>
							<div class="content margin_left_15 collapse in" id="agenciesList">
								<dhcc:tableTag property="tablecreditAgenciesList" paginate="tablecreditAgenciesList" head="true"></dhcc:tableTag>
							</div>
						</div>
						<div class="list-table-replan" id="busBreedInfo" hidden>
							<div class="title">
								<span>业务品种授信额度</span>
								<button class="btn btn-link formAdd-btn"  onclick="MfCusCreditApply_input.breedAdd();" title="新增"><i class="i i-jia3"></i></button>
							</div>
							<div class="content margin_left_15 collapse in" id="busBreedList">
								<dhcc:tableTag property="tablecreditBreedList" paginate="tablecreditBreedList" head="true"></dhcc:tableTag>
							</div>
						</div>
					</c:if>
				</div>
					<div class="col-md-10 col-md-offset-1 column margin_top_20" style="display: none" id="quotaCalcDiv">
						<div class="bootstarpTag fourColumn">
							<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
							<form method="post" id="quotaCalc" theme="simple" name="operform" action="${webPath}/mfCusCreditApply/calcQuotaAjax">
								<dhcc:bootstarpTag property="formQuotaCalc" mode="query"/>
							</form>
						</div>
					</div>
					<!--上传文件-->
					<div class="row clearfix">
						<div class="col-xs-10 col-md-offset-1 column">
							<%@include file="/component/doc/webUpload/uploadutil.jsp"%>
						</div>
					</div>

				</div>
				<div class="formRowCenter" id="saveBtn">
                    <c:choose>
                        <c:when test="${initFlag == '1'}">
                            <dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose_click();"></dhcc:thirdButton>
                        </c:when>
                        <c:otherwise>
                            <dhcc:thirdButton value="保存" action="保存" typeclass="saveButton" onclick="MfCusCreditApply_input.ajaxInsert('#operform')"></dhcc:thirdButton>
                            <dhcc:thirdButton value="取消" action="取消" typeclass="cancel" onclick="myclose_click();"></dhcc:thirdButton>
                        </c:otherwise>
                    </c:choose>
				</div>
				<div class="formRowCenter" style="display: none" id="saveBtnCalc">
					<dhcc:thirdButton value="测算" action="测算" typeclass="insertAjax" onclick="MfCusCreditApply_input.calcQuotaAjax('#quotaCalc');"></dhcc:thirdButton>
					<dhcc:thirdButton value="返回" action="返回" typeclass="cancel" onclick="MfCusCreditApply_input.changeFormDisplay();"></dhcc:thirdButton>
				</div>
			</c:otherwise>
		</c:choose>
	</div>
		<%--新增页面--%>
		<div class="container form-container" style="display: none;" id="agenciesInsert">
			<div class="scroll-content">
				<div class="col-md-10 col-md-offset-1 column margin_top_20"  >
					<div class="bootstarpTag">
						<form  method="post" id="creditAgenciesInsert" theme="simple" name="operform" action="${webPath}/mfCusHighChildInfo/insertAjax">
							<dhcc:bootstarpTag property="formcreditAgenciesBase" mode="query" />
						</form>
					</div>
				</div>
			</div>
			<div class="formRowCenter">
				<dhcc:thirdButton value="添加授信银行" action="保存" onclick="MfCusCreditApply_input.agenciessave('#creditAgenciesInsert');"></dhcc:thirdButton>
				<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="MfCusCreditApply_input.agenciesCancle();"></dhcc:thirdButton>
			</div>
		</div>

		<%--新增页面--%>
		<div class="container form-container" style="display: none;" id="breedInsert">
			<div class="scroll-content">
				<div class="col-md-10 col-md-offset-1 column margin_top_20"  >
					<div class="bootstarpTag">
						<form  method="post" id="creditBreedInsert" theme="simple" name="operform" action="${webPath}/mfCusHighChildInfo/insertAjax">
							<dhcc:bootstarpTag property="formcreditBreedBase" mode="query" />
						</form>
					</div>
				</div>
			</div>
			<div class="formRowCenter">
				<dhcc:thirdButton value="添加业务品种" action="保存" onclick="MfCusCreditApply_input.breedsave('#creditBreedInsert');"></dhcc:thirdButton>
				<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="MfCusCreditApply_input.breedCancle();"></dhcc:thirdButton>
			</div>
		</div>
</body>




</html>
