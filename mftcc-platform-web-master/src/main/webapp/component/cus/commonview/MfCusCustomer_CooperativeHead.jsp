<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
</head>
<script type="text/javascript">
    var headImg = '${sysUser.headImg}';
    var ifUploadHead = '${sysUser.ifUploadHead}';
    var opNo = '${sysUse.opNo}';
</script>
<!--头部主要信息 cus-bo-head-info -->
<div class="row clearfix head-info">
    <!--头像 -->
    <div class="col-xs-3 column text-center head-img">
        <div class="btn btn-link">
            <img id="headImgShow" name="headImgShow" class="img-circle" src="${webPath}/uploadFile/viewUploadImageDetail?srcPath=themes%252Ffactor%252Fimages%252Fuser_0.jpg&amp;fileName=user2.jpg">
            <a class="btn btn-link head-word" onclick="uploadHeadImg();">更换头像</a>
        </div>
    </div>

    <!--概要信息 -->
    <div class="col-xs-9 column head-content" >
        <div class="row clearfix margin_top_10">
            <div class="col-md-12 column">
                <span class="margin_left_20"><i class=" i i-bumen"></i>${sysUser.brName}</span>
                <span class="margin_left_20"><i class=" i i-dianhua"></i>${sysUser.mobile}</span>
            </div>
        </div>
        <div class="row clearfix margin_top_10">
            <div class="col-md-12 column">
                <span class="margin_left_20"><i class="i i-location "></i>${sysUser.workWeChatUserId}</span>
            </div>
        </div>
        <div class="row clearfix head-content margin_top_20" style="height: 1px">
            <div class="col-md-12 column" >
                <div class="col-md-3 column">
                    <p class="cont-title">社员人数</p>
                    <p class="margin_top_15">
                        <span class="content-span">${dataMap.cntByOpNo}</span><span
                            class="unit-span margin_right_25">人</span>
                    </p>
                </div>
                <div class="col-md-3 column">
                    <p class="cont-title">成交笔数</p>
                    <p class="margin_top_15">
                        <span class="content-span">${dataMap.pactCount}</span><span
                            class="unit-span margin_right_25">笔</span>
                    </p>
                </div>
                <div class="col-md-3 column">
                    <p class="cont-title">成交金额</p>
                    <p class="margin_top_15">
                        <span class="content-span">${dataMap.totleAmt}</span><span
                            class="unit-span margin_right_25">万元</span>
                    </p>
                </div>
                <div class="col-md-3 column">
                    <p class="cont-title">待还金额</p>
                    <p class="margin_top_15">
                        <span class="content-span">${dataMap.totleUnrepayAmt}</span><span
                            class="unit-span">万元</span>
                    </p>
                </div>
            </div>
        </div>
        <div>
        </div>
        <div class="btn-special">

        </div>
    </div>
</div>