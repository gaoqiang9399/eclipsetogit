<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
		<script type="text/javascript"  src='${webPath}/component/cus/js/MfCusPersonDebtInfo.js'> </script>
		<script type="text/javascript" src="${webPath}/component/include/calcUtil.js"></script>
		<script type="text/javascript" src="${webPath}/dwr/engine.js"></script>
		<script type='text/javascript' src="${webPath}/dwr/util.js"></script>  
  		<script type="text/javascript" src="${webPath}/dwr/interface/UtilDwr.js"></script>
		<script type="text/javascript" src="${webPath}/component/cus/js/MfCusDyForm.js"></script>
	</head>
	<script type="text/javascript">
        var projectName = '${projectName}';
        var pageView = '';
        var cusNo;
        var cusType = '${cusType}';
		$(function() {
            cusNo=$("input[name=cusNo]").val();
			CusPersonDebtInfoInsert.init();
			
		});
        //验证页面“请选择”是否全部完成
        function validateAndInsert(){
            CusPersonDebtInfoInsert.saveCusPersonDebtInfo('#MfCusPersonDebtInfoInsert');
        }
        //选择负债人
        function selectDebtInfo(){
            CusPersonDebtInfoInsert.selectDebtInfo();
        }
	</script>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<span id="noSign" style="display: none;position: absolute;top: 8px;left: 220px;z-index: 999" >
				<div  >
					<span style="height: 40px; line-height: 40px">
						有无负债信息：有信息： <input type="radio" name="useflag"  class="write"  value="1"  style="cursor: pointer">
					</span>
					<span style="height: 40px; line-height: 40px">无信息：<input   class="write" value="0" type="radio" name="useflag"></span>
				</div>
				</span>
				<div class="col-md-10 col-md-offset-1 column margin_top_20">
					<div class="bootstarpTag fourColumn">
					<div class="form-title"></div>
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form  method="post" id="MfCusPersonDebtInfoInsert" theme="simple" name="operform" action="${webPath}/mfCusPersonDebtInfo/insertAjax">
							<dhcc:bootstarpTag property="formcuspersdebt0002" mode="query"/>
						</form>	
					</div>
				</div>
			</div>
			<div class="formRowCenter">
						<dhcc:pmsTag pmsId="cus-saveAndAdd">
							<dhcc:thirdButton value="保存并新增" action="保存并新增" onclick="CusPersonDebtInfoInsert.saveCusPersonDebtInfoAndAdd('#MfCusPersonDebtInfoInsert')"></dhcc:thirdButton>
						</dhcc:pmsTag>
		    			<dhcc:thirdButton value="保存" action="保存" onclick="validateAndInsert();"></dhcc:thirdButton>
						  <dhcc:pmsTag pmsId="return-page">
						  <dhcc:thirdButton value="返回上级页面" action="返回上级页面" typeclass="cancel" onclick="MfCusDyForm.updateCusFormStas();"></dhcc:thirdButton>
						  </dhcc:pmsTag>
		    			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
		    </div>
	    </div>
		<script>
            $(function(){
                noSign();
            });
            function noSign(){
				$("#noSign").hide();
            }
            //暂停/启用切换
            $('.write').click(function(){
                var  flag = $(this).val();
                $(this).toggleClass('btn_on').toggleClass('btn_off');
                //var className =document.getElementById("noSign").className;
                if (flag=='0'){
                    $("input[name=loanDeadline]").val("0");
                    $("input[name=loanDeadline]").attr({ readonly: 'true' });
                    $("input[name=balanceOut]").val("0");
                    $("input[name=balanceOut]").attr({ readonly: 'true' });
                    var d = new Date();
                    var datetime=d.getFullYear() + '-' + (d.getMonth() + 1) + '-' + (d.getDate() < 10 ? "0" + d.getDate() : d.getDate());
                    $("input[name=beginDate]").val(datetime);
                    $("input[name=beginDate]").attr({ readonly: 'true' });
                    $("input[name=loanPeriod]").val("0");
                    $("input[name=loanPeriod]").attr({ readonly: 'true' });
                    $("select[name=debtType]").find("option[value = '7']").prop("selected","true");
                    $("select[name=debtType]").find("option[value = '0']").hide();
                    $("select[name=debtType]").find("option[value = '1']").hide();
                    $("select[name=debtType]").find("option[value = '2']").hide();
                    $("select[name=debtType]").find("option[value = '3']").hide();
                    $("select[name=debtType]").find("option[value = '4']").hide();
                    $("select[name=debtType]").find("option[value = '5']").hide();
                    $("select[name=debtType]").find("option[value = '6']").hide();
                }else {
                    $("input[name=loanDeadline]").val("");
                    $("input[name=loanDeadline]").removeAttr("readonly");
                    $("input[name=balanceOut]").val("");
                    $("input[name=balanceOut]").removeAttr("readonly");
                    $("input[name=beginDate]").val("");
                    $("input[name=beginDate]").removeAttr("readonly");
                    $("input[name=loanPeriod]").val("");
                    $("input[name=loanPeriod]").removeAttr("readonly");
                    $("select[name=debtType]").find("option[value = '0']").show();
                    $("select[name=debtType]").find("option[value = '0']").prop("selected","true");
                    $("select[name=debtType]").find("option[value = '1']").show();
                    $("select[name=debtType]").find("option[value = '2']").show();
                    $("select[name=debtType]").find("option[value = '3']").show();
                    $("select[name=debtType]").find("option[value = '4']").show();
                    $("select[name=debtType]").find("option[value = '5']").show();
                    $("select[name=debtType]").find("option[value = '6']").show();
                    $("select[name=debtType]").find("option[value = '7']").show();
                }
            });
		</script>
	</body>
</html>
