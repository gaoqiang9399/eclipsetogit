<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>新增</title>
</head>
<script type="text/javascript">
    var pledgeNo = '${keepInfo.collateralId}';
    var keepId =  '${keepInfo.keepId}';
    $(function() {
        myCustomScrollbarForForm({
            obj:".scroll-content",
            advanced : {
                updateOnContentResize : true
            }
        });
        var groupNameLabel = $("input[name=groupName]").parents(".rows")
            .find(".form-label");
        var groupNameLabelText = $(groupNameLabel).text();
        $(groupNameLabel).empty().append(
            "<span class='required'>*</span>" + groupNameLabelText);
        $("input[name=groupName]").attr("mustinput", "1");
        //KeepInfo_instock_Insert.getGoodsDetailList();

        var pledgeNo=$("input[name=collateralId]").val();
        jQuery.ajax({
            url : webPath+"/keepInfo/getGoodsDetailList",
            type : "POST",
            dataType : "json",
            data:{tableId:"tablepledgebaseinfobillselect",keepId:keepId},
            beforeSend : function() {
            },
            success : function(data) {
                if (data.flag == "success") {
                    var pledgeBillNoStr ="";
                    $("#goodsDetailList").html(data.tableData);
                    if($('.table_content #tab').find($('input[type=checkbox]'))>0){
                    }
                    $("#goodsDetailListdiv").show();
                    // $("th[name=pledgeBillNo]").html('<a href="javascript:void(0);" onclick="MfMoveableClaimGoodsApply.checkAllGoodsDetail()">全选</a>');
                    $('.table_content #tab').find($('input[type=checkbox]')).each(function () {
                        $(this).bind("click",function(){
                            var val=this.value;

                        });
                    });
                }
            },
            error : function(data) {
                LoadingAnimate.stop();
                window.top.alert(top.getMessage("FAILED_OPERATION"," "), 0);
            }
        });
    });

    function getLegalIdType() {
        var legalIdType = $("select[name =legalIdType]").val();
        if (legalIdType == "0") {
            $("input[name=legalIdNum]").attr("alt", "idnum");
        } else {
            $("input[name=legalIdNum]").attr("alt", "tmp");
        }
        $("input[name=legalIdNum]").val("");
    };

    function ifGroupCustomer(obj) {
        var ifGroupType = $(obj).val();
        if (ifGroupType == 0) {//非集团客户
            $("input[name=groupName]").attr("mustinput", "0");
            $("input[name=groupName]").parents(".rows").hide();
        } else {//集团客户
            $("input[name=groupName]").attr("mustinput", "1");
            $("input[name=groupName]").parents(".rows").show();
        }
    }

    function insertChkInfo(obj) {
        var flag = submitJsMethod($(obj).get(0), '');
        if (flag) {
            var url = $(obj).attr("action");
            var dataParam = JSON.stringify($(obj).serializeArray());
            LoadingAnimate.start();
            jQuery.ajax({
                url : url,
                data : {
                    ajaxData : dataParam,
                },
                type : "POST",
                dataType : "json",
                beforeSend : function() {
                },
                success : function(data) {
                    LoadingAnimate.stop();
                    if (data.flag == "success") {
                        //					  alert(top.getMessage("SUCCEED_OPERATION", ""),1);
                        top.addFlag = true;
                        if (data.htmlStrFlag == "1") {
                            top.htmlStrFlag = true;
                            top.htmlString = data.htmlStr;
                        }
                        myclose_click();
                        //window.close();
                        //myclose_showDialogClick();
                        if (callback && typeof (callback) == "function") {
                            callback.call(this, data);
                        }
                    } else if (data.flag == "error") {
                        alert(data.msg, 0);
                    }
                },
                error : function(data) {
                    LoadingAnimate.stop();
                    alert(top.getMessage("FAILED_OPERATION"," "), 0);
                }
            });
        }
    }

    function getCusMngNameDialog(userInfo) {
        $("input[name=cusMngName]").val(userInfo.opName);
        $("input[name=cusMngNo]").val(userInfo.opNo);
    };

    //从集团客户放大镜赋值给表单属性
    function getGroInfoArtDialog(groupInfo) {
        $("input[name=groupName]").val(groupInfo.groupName);
        $("input[name=groupNo]").val(groupInfo.groupNo);
        $("input[name='groupName']").attr("readonly", true);
    };

    function selectAreaCallBack(areaInfo) {
        $("input[name=careaProvice]").val(areaInfo.disName);
    };


</script>
<script type="text/javascript" src="${webPath}/component/collateral/js/KeepInfo_instock_Insert.js"></script>
<body class="overflowHidden bg-white">
<div class="container form-container">
	<div class="scroll-content">
		<div class="col-md-8 col-md-offset-2 column margin_top_20">
			<div class="bootstarpTag">
				<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
				<form  method="post" id="instockInsert" theme="simple" name="operform" action="${webPath}/keepInfo/instockAjax1">
					<dhcc:bootstarpTag property="formdlkeepinfoview0007" mode="query"/>
				</form>
			</div>
			<div id="goodsDetailListdiv" class="bigform_content col_content" style="display:none">
				<div class="title"><h5>货物明细</h5></div>
				<div id="goodsDetailList" class="table_content padding_0">
					<dhcc:tableTag paginate="goodsDetailList" property="tablepledgebaseinfobillselect" head="true" />
				</div>
			</div>
		</div>
	</div>
	<div class="formRowCenter">
		<dhcc:thirdButton value="关闭" action="取消" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
	</div>
</div>
</body>
</html>
