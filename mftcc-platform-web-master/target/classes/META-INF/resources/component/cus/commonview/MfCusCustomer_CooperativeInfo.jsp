<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/tld/loan.tld" prefix="dhcc"%>
<script type="text/javascript">
    var mfCusCooperativeAgency = '${mfCusCooperativeAgency}';
    function getCoopView(){
        window.location.href=webPath+"/mfCusCooperativeAgency/getCooperativeAgencyView?orgaNo="+cusNo+"&opNo="+opNo+"&busEntrance=cus_coopAgency";
    }
</script>
<div class="row clearfix has-content padding_bottom_20 padding_top_10" >
    <div class="bg-white right-info">
        <div class="row clearfix">
            <div class="col-xs-12 col-md-12 column padding_left_15">
                <button class="btn btn-link block-title" onclick="getCoopView();">合作社信息</button>
                <button type="button" class="btn btn-font-qiehuan pull-right" onclick="getCoopView();"><i class="i i-qiehuan" style="font-size:22px;"></i></button>
            </div>
            <div class="content-div">
                <div class="row clearfix">
                    <div class="col-md-12 column">
                        <div class="apply-detail">
                            <p style="font-size:15px;">合作社名称 :
                                <span >${mfCusCooperativeAgency.orgaName}</span></p>
                            <p style="font-size:15px;">社会信用代码 :
                                <span >${mfCusCooperativeAgency.legalIdNum}</span></p>
                            <p style="font-size:15px;">法人代表姓名 :
                                <span >${mfCusCooperativeAgency.legalRepresName}</span></p>
                            <p style="font-size:15px;">注册资金 :
                                <span >${mfCusCooperativeAgency.assetSum}万 </span>注册日期 :
                                <span ><fmt:formatDate value="${mfCusCooperativeAgency.regTime}" pattern="yyyy-MM-dd"/></span></p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="col-xs-12 col-md-12 column">
    <div class="line-div"></div>
</div>