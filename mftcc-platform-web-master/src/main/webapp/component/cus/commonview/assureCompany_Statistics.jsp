<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>

<script type="text/javascript">
    var assureCompanyId = '${param.assureCompanyId}';
    $(function () {
        //异步获取客户的历史统计信息
        $.ajax({
            url: webPath + "/mfCusAssureCompany/getTotalData",
            data: {assureCompanyId: assureCompanyId},
            type: "POST",
            dataType: "json",
            success: function (data) {
                if (data.flag == "success") {
                    $("#personNum").text(data.cusCount);
                    $("#applyAmt").text(data.appAmt);
                    $("#assureNum").text(data.assCount);
                    $("#assureAmt").text(data.assureAmt);
                } else {
                    alert(data.msg, 0);
                }
            }
        });
    });

    // 历史完结任务
    function assureDetail() {
        top.openBigForm(webPath + '/mfCusAssureCompany/getAssureDetailListPage?assureCompanyId=' + assureCompanyId, '担保明细', function () {
        })
    }
</script>

<div class="row clearfix padding_top_10">
    <div class="col-xs-12 col-md-12 column padding_left_15">
        <button class="btn btn-link block-title" onClick="assureDetail()">担保明细记录</button>
        <button type="button" class="btn btn-font-qiehuan pull-right" onClick="assureDetail()"><i class="i i-qiehuan" style="font-size:22px;"></i></button>
    </div>
</div>
<div class="row clearfix padding_left_12 his-statistic">
    <div class="col-xs-12 col-md-12 column">
        <table>
            <tbody>
            <tr>
                <td>
                    <p class="ptitle">借款人数</p>
                    <p class="pvalue"><span id="personNum">0</span><span>&nbsp;人</span></p>
                </td>
                <td>
                    <p class="ptitle">借款金额</p>
                    <p class="pvalue"><span id="applyAmt">0</span><span>&nbsp;元</span></p>
                </td>
            </tr>
            <tr>
                <td>
                    <p class="ptitle">担保总数</p>
                    <p class="pvalue"><span id="assureNum">0</span><span>&nbsp;个</span></p>
                </td>
                <td>
                    <p class="ptitle">担保总额</p>
                    <p class="pvalue"><span id="assureAmt">0</span><span>&nbsp;元</span></p>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
</div>
<div class="row clearfix">
    <div class="col-xs-12 col-md-12 column">
        <div class="line-div"></div>
    </div>
</div>
