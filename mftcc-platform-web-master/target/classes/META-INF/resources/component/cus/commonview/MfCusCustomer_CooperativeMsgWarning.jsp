<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/tld/loan.tld" prefix="dhcc"%>
<script type="text/javascript">
function overCount(){
    window.location.href=webPath+"/mfBusFincApp/getRepayToDatePage";
}
function dueCount(){
    window.location.href=webPath+"/mfBusFincApp/getListPage";
}
</script>
<div class="row clearfix has-content padding_bottom_20 padding_top_10" >
    <div class="bg-white right-info">
        <div class="row clearfix">
            <div class="col-xs-12 col-md-12 column padding_left_15">
                <div class="btn btn-link block-title">系统消息</div>
            </div>
            <div class="row clearfix padding_left_30 padding_top_40">
                <p class="margin_top_15">还款到期预警&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp待处理
                    <span id="overCount" onclick="overCount()" class="content-span" style="cursor:pointer">${dataMap.overCount}</span><span
                        class="unit-span margin_right_25">条</span>
                </p>
            </div>
            <div class="row clearfix padding_left_30 padding_top_10">
                <p class="margin_top_15">还款逾期预警&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp待处理
                    <span type="button" onclick="dueCount()" class="content-span" style="cursor:pointer">${dataMap.overCount}</span><span
                            class="unit-span margin_right_25">条</span>
                </p>
        </div>
    </div>
</div>
</div>
<div class="col-xs-12 col-md-12 column">
    <div class="line-div"></div>
</div>