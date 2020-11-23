<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
		
	</head>
<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<span id="noSign" style="display: none;position: absolute;top: 8px;left: 220px;z-index: 999" >
				<div  >
					<span style="height: 40px; line-height: 40px">
						有无反担保信息：有信息： <input type="radio" name="useflag"  class="write"  value="1"  style="cursor: pointer">
					</span>
					<span style="height: 40px; line-height: 40px">无信息：<input   class="write" value="0" type="radio" name="useflag"></span>
				</div>
				</span>
				<div class="col-md-8 col-md-offset-2 column margin_top_20">
					<div class="bootstarpTag">
						
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
				<form  method="post" id="cusBankAccInsert" theme="simple" name="operform" action="${webPath}/mfCusGuaranteeOuter/insertAjax">
					<dhcc:bootstarpTag property="formcusguaranteeouter0002" mode="query" />
				</form>
			</div>
		</div>
	</div>
	<div class="formRowCenter">
		<dhcc:thirdButton value="保存" action="保存" onclick="asdInsert();"></dhcc:thirdButton>
		<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
	</div>
	</div>
<script type="text/javascript">
    var projectName = '${projectName}';
    var cusType = '${cusType}';
	$(function() {
		//var url = $(".tab-content form").attr("action") + '?formId='+'${formId}';
		//$(".tab-content form").attr("action",url);
// 		$(".scroll-content").mCustomScrollbar({
// 					advanced:{
// 						theme:"minimal-dark",
// 						updateOnContentResize:true
// 					}
// 		});
        noSign();
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				theme : "minimal-dark",
				updateOnContentResize : true
			}
		});
        // 证件类型选择组件
        $("select[name=idType]").popupSelection({
            searchOn:true,//启用搜索
            inline:true,//下拉模式
            multiple:false,//单选
            changeCallback : function (obj, elem) {
                var blur = $("input[name=idNum]").attr("onblur");
                var newAddBlur = blur.substring(0,blur.indexOf("func_uior_valFormat_tips"));
                $("input[name=idNum]").attr("onblur",newAddBlur);
                var idTypeValue = obj.data('values').val();  //当前选中的值
                $("select[name=sex]").val('');
                $("input[name=idNum]").val('');
                $("input[name=idNum]").parent().find('.error').remove();
                $("input[name=brithday]").val('');
                if(idTypeValue == "0"){ //身份证
                    var blurAdd = blur + "func_uior_valFormat_tips(this,'idnum');";
                    $("input[name=idNum]").attr("onblur",blurAdd);
                }
            }
        });
	});
    //证件类型变化是修改证件号码验证规则
    function idTypeChange(obj){
        //证件号码格式验证
        var idType = $(obj).val();
        var $idNum = $(obj).parents("table").find("input[name=idNum]")[0];
        if(idType=="0"){//身份证样式格式
            //如果是身份证，添加校验
            changeValidateType($idNum, "idnum");
        }else if (idType=="B"){
            //添加社会信用该代码校验
            changeValidateType($idNum, "allidtype");
		}else{
            changeValidateType($idNum, "");
        }
        $(obj).parents("table").find("input[name=idNum]").val("");
        $(obj).parents("table").find("input[name=idNum]").focus();
    };
    //验证银行卡
	function getBankByCardNumber(obj){
		var identifyNumber = obj.value;
		$.ajax({
			url:webPath+"/bankIdentify/getByIdAjax",
			data:{identifyNumber:identifyNumber},
			type:'post',
			dataType:'json',
			success:function(data){
				if(data.flag == "success"){
					$("input[name=bankNumbei]").val(data.bankId);
					$("input[name=bank]").val(data.bankName);
				}else{
					$("input[name=bankNumbei]").val("");
					$("input[name=bank]").val("");
				}
			},error:function(){
				window.top.alert(top.getMessage("FAILED_OPERATION"," "),0);
			}
		});
		}
	 function insertCallBack() {
		top.addFlag = true;
		top.formOrTable = "table";
		myclose_click();
	};
	//验证请选择
    function asdInsert(){
        ajaxInsertCusForm('#cusBankAccInsert');
    }

    function noSign(){
		$("#noSign").hide();
    }
    //暂停/启用切换
    $('.write').click(function(){
        var  flag = $(this).val();
        $(this).toggleClass('btn_on').toggleClass('btn_off');
        //var className =document.getElementById("noSign").className;
        if (flag=='0'){
            $("input[name=balanceOut]").val("0");
            $("input[name=balanceOut]").attr({ readonly: 'true' });
            $("select[name=guaType]").find("option[value = '5']").prop("selected","true");
            $("select[name=guaType]").find("option[value = '0']").hide();
            $("select[name=guaType]").find("option[value = '1']").hide();
            $("select[name=guaType]").find("option[value = '2']").hide();
            $("select[name=guaType]").find("option[value = '3']").hide();
            $("select[name=guaType]").find("option[value = '4']").hide();
        }else {
            $("input[name=balanceOut]").val("");
            $("input[name=balanceOut]").removeAttr("readonly");
            $("select[name=guaType]").find("option[value = '0']").show();
            $("select[name=guaType]").find("option[value = '0']").prop("selected","true");
            $("select[name=guaType]").find("option[value = '1']").show();
            $("select[name=guaType]").find("option[value = '2']").show();
            $("select[name=guaType]").find("option[value = '3']").show();
            $("select[name=guaType]").find("option[value = '4']").show();
            $("select[name=guaType]").find("option[value = '5']").show();
        }
    });
</script>
</body>
</html>
