<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ include file="/component/include/common.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>新增</title>
    <style type="text/css">
        div.hidden-content .pops-value {
            display: none;
        }
    </style>
    <script type="text/javascript" src='${webPath}/component/cus/js/MfCusFamilyInfo.js'></script>
    <script type="text/javascript" src='${webPath}/component/cus/identitycheck/js/IdentityCheck.js?v=${cssJsVersion}'></script>
    <script type="text/javascript" src="${webPath}/component/cus/js/MfCusDyForm.js"></script>

</head>

<script type="text/javascript">
    var projectName = '${projectName}';
    var pageView = '';
    var cusNo = '${cusNo}';
    $(function () {
        var cusNo = '${cusNo}';
        var ajaxData = '${ajaxData}';
        MfCusFamilyInfo.init(cusNo);
        ajaxData = JSON.parse(ajaxData);
        $("select[name=relative]").popupSelection({
            searchOn: true,//启用搜索
            inline: true,
            multiple: false,
            selectClass: "old",
            items: ajaxData.cusPersRelType,
            addBtn: {
                "title": '新增',
                "fun": function (elem) {
                    $(".pops-select").hide();
                    BASE.openDialogForSelect('新增与客户关系', 'CUS_PERS_REL', elem);
                }
            }
            , changeCallback: function (elem, obj) {
                // 如果是配偶关系自动带出性别。
                var relative = $("input[name='relative']").val();
                var relativeClass = $("input[name=relative]").parents("td").attr("class");
                var marrige = ajaxData.marrige;
                if (typeof(relativeClass) != "undefined" && relativeClass.indexOf("newHope") != -1) {
                    if (relative == "1") {
                        if(marrige == "1"){
                            alert("未婚状态不能登记配偶信息",0);
                            $("input[name=relative]").parents("td").find(".pops-value").html(" ");
                        }
                        $("input[name=idNum]").attr("mustinput", "1");
                    } else {
                        $("input[name=idNum]").attr("mustinput", "0");
                    }
                }

                if (relative == "1") {
                    if(marrige == "1"){
                        alert("未婚状态不能登记配偶信息",0);
                        $("input[name=relative]").parents("td").find(".pops-value").html(" ")
                    }
                    $(":input[name=sex]").val(ajaxData.baseSex);
                    $("select[name=popssex]").popupSelection("selectedById", ajaxData.baseSex);
                }

            }
        });
        //证件选择组件
        $("select[name=idType]").popupSelection({
            searchOn: true,//启用搜索
            inline: true,//下拉模式
            multiple: false,//单选
            changeCallback: idTypeChange
        });
        // //个人健康选择组件
        // $("select[name=healthStat]").popupSelection({
        // 			searchOn:true,//启用搜索
        // 			inline:true,//下拉模式
        // 			multiple:false//单选
        // });

        //所在企业贷款卡编码
        $("input[name=entlnno]").parents("tr").hide();
        var entlnnoLabel = $("input[name=entlnno]").parents("tr").children().eq(0).find(".control-label");
        var entlnnoLabelText = $(entlnnoLabel).text();
        $(entlnnoLabel).empty().append("<font color='#FF0000'>*</font>" + entlnnoLabelText);
    });

    function ifLegalFamilyFirmMember(obj) {
        var legalFamilyFirmMemberVal = $(obj).val();
        if (legalFamilyFirmMemberVal == 0) {//非法人代表家族企业成员
            $("input[name=entlnno]").attr("mustinput", "0");
            $("input[name=entlnno]").parents("tr").hide();
        } else {//法人代表家族企业成员
            //$("input[name=entlnno]").attr("mustinput","1");
            $("input[name=entlnno]").parents("tr").show();
        }
    };

    //验证页面“请选择”是否全部完成
    function validateAndInsert() {
        saveCusFamilyInfoInsert('#MfCusFamilyInfoInsert', 'insert');
    }
    var initCusSelect = function(){
        selectCusDialog(_selectCusBack,"2","","5");
    }

    //选择客户回调
    var _selectCusBack=function(cus){
        $("input[name=relName]").val(cus.cusName);
        $("[name=idType]").val(cus.idType);
        $("input[name=idNum]").val(cus.idNum);
        $("[name=sex]").val(cus.sex);
        $("input[name=ext1]").val(cus.age);
        $("[name=nationality]").val(cus.nationality);
        $("input[name=relTel]").val(cus.cusTel);
        $("input[name=ext2]").val(cus.ext2);
        $("input[name=postalAddress]").val(cus.commAddress);
    };
</script>
<body class="overflowHidden bg-white">
<div class="container form-container">
    <div class="scroll-content">
        <div class="col-md-10 col-md-offset-1 column margin_top_20">
            <div class="bootstarpTag fourColumn">
                <div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
                <form method="post" id="MfCusFamilyInfoInsert" theme="simple" name="operform"
                      action="${webPath}/mfCusFamilyInfo/insertAjax">
                    <dhcc:bootstarpTag property="formcusfam00002" mode="query"/>
                </form>
            </div>
        </div>
    </div>
    <div class="formRowCenter">
        <dhcc:pmsTag pmsId="cus-saveAndAdd">
            <dhcc:thirdButton value="保存并新增" action="保存并新增"
                              onclick="saveCusFamilyInfoInsertAndAdd('#MfCusFamilyInfoInsert','insert')"></dhcc:thirdButton>
        </dhcc:pmsTag>
        <dhcc:thirdButton value="保存" action="保存" onclick="validateAndInsert();"></dhcc:thirdButton>
        <dhcc:pmsTag pmsId="return-page">
            <dhcc:thirdButton value="返回上级页面" action="返回上级页面" typeclass="cancel"
                              onclick="MfCusDyForm.updateCusFormStas();"></dhcc:thirdButton>
        </dhcc:pmsTag>
        <dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
    </div>
</div>
</body>
</html>
