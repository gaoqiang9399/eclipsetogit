<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ include file="/component/include/common.jsp" %>
<%@ include file="/component/include/pub_view_table.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>列表</title>
    <script type="text/javascript">
        var processId = '${processId}';
        var fincId = '${fincId}';
        var appId = '${appId}';
        //发票总金额
        var invoiceAmount = 0;
        //发票期号
        var invoiceTerm = "";
        $(function () {
            MfBusInvoicemanage_Insert.init();
            //设置未开票以外的复选框状态为不可选
            $(":checkbox").each(function () {
                if ($(this).parent().parent().find('td').eq(10).text() != "未开"){
                    $(this).prop({"checked":false})
                        .prop({"disabled":"disabled"});
                }
            });
            //获取选中还款计划的期号和总金额
            $(":checkbox").on('click',function() {
                var total = $('#invoiceTotal').val();
                total = parseFloat(total);
                //获取每一期金额
               var  repaytotal = $(this).parent().parent().find('td').eq(7).text();
                repaytotal = repaytotal.replace(',','');
                repaytotal = parseFloat(repaytotal);
                //获取期号
                var termNum = $(this).parent().parent().find('td').eq(1).text();
                var terms = $('#invoiceTerm').val();
                if($(this).prop("checked")){
                    //增加金额
                    total = total + repaytotal;
                    $('#invoiceTotal').val(total.toFixed(2));
                    //添加期号
                    if (terms == ""){
                        terms = (termNum);
                        $('#invoiceTerm').val(terms);
                    }else {
                        terms = terms + "," + termNum;
                        $('#invoiceTerm').val(terms);
                    }
                }else {
                    //减少金额
                    total = total - repaytotal;
                    $('#invoiceTotal').val(total.toFixed(2));
                    //减少期号
                    if(terms.indexOf(",") == -1){
                        terms = terms.replace(termNum,"");
                        $('#invoiceTerm').val(terms);
                    }if (terms.charAt(0) == termNum ){
                        var termNumCut = terms.substr(0,2);
                        terms = terms.replace(termNumCut,"");
                        $('#invoiceTerm').val(terms);
                    }else {
                        var termNumCut = "," + termNum;
                        terms = terms.replace(termNumCut,"");
                        $('#invoiceTerm').val(terms);
                    }
                }
                invoiceAmount = total;
                invoiceTerm = terms;
            });
            //隐藏底部页面显示
            $(".footer_loader").hide();
        });
    </script>
</head>
<body class="overflowHidden">
<div class="container">
    <div class="row clearfix bg-white tabCont">
        <div class="col-md-12 column" >
            <div class="btn-div" >
                <div>
                    <div>
                        发票类型：<select id="invoiceType" class="select">
                        <option value="2">普通</option>
                        <option value="1">专用</option>
                        <option value="3">不开发票</option>
                    </select>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        发票金额：<input id="invoiceTotal"  value="0">
                    </div>
                    <input id="invoiceTerm"  value="" style="display:none">
                </div>
                <div class="col-md-2" >
                </div>
            </div>
        </div>
    </div>
    <div class="row clearfix">

        <div class="col-md-12 column">
            <div id="content" class="table_content" style="height: auto;">
            </div>
        </div>
    </div>

    <div class="formRowCenter">
        <dhcc:thirdButton value="保存" action="保存" onclick="MfBusInvoicemanage_Insert.invoiceSave();"></dhcc:thirdButton>
        <dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
    </div>
</div>
<script type="text/javascript" src="${webPath}/component/invoicemanage/js/MfBusInvoicemanage_Insert.js"></script>
<%@ include file="/component/include/PmsUserFilter.jsp" %>
</body>
</html>
