<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/component/include/common.jsp" %>
<%@ include file="/component/include/pub_view_table.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>多笔合同</title>
</head>
<script type="text/javascript">

    var flag = '${flag}';
    $(function () {
        //处理暂无数据的情况
        if ($('#content tbody tr').length == 0) {
            var thCount = $('#content thead th').length;
            $('#content tbody').append('<tr><td style="text-align: center;" colspan="' + thCount + '">暂无数据</td></tr>');
        }
        $(".table_content").mCustomScrollbar({
            advanced: {
                updateOnContentResize: true
            }
        });
        if (flag == "false") {
            window.top.alert("获取正在放款还款的业务信息出错", 0);
        }
    });


    function doRemoveBusMapByFincId(obj, url) {
        $.ajax({
            url: webPath + url,
            type: 'post',
            async: false,
            success: function (data) {
                if (data.flag = "success") {
                    alert(top.getMessage("SUCCEED_OPERATION", data.msg), 3);
                    window.location.href = webPath + "/mfRepayment/getLoanPaymentBusMapInfo";
                } else {
                    alert(top.getMessage("FAILED_OPERATION", "删除锁失败", 0));
                }
            }, error: function () {
                alert(top.getMessage("FAILED_OPERATION", "删除失败", 0));
            }
        });
    }
</script>
<body class="bg-white">
<div class="container">
    <div class="row clearfix">
        <div class="col-md-12 column">
            <div id="content" class="table_content padding_top_20 padding_bottom_20" style="height: 100%; ">
                <dhcc:tableTag paginate="loanPaymentBusMapInfoList" property="tableloanpaymentbusmapinfolist"
                               head="true"/>
            </div>
        </div>
    </div>
</div>
</body>
</html>