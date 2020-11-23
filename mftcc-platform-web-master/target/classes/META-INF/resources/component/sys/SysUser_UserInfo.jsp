<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/component/include/common.jsp" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <script type="text/javascript"
            src="${webPath}/layout/view/js/openDiv.js"></script>
    <script type="text/javascript"
            src="${webPath}/layout/view/js/fixedDivFun.js"></script>
    <script type="text/javascript"
            src="${webPath}/component/report/js/MfReportEntrance.js"></script>
    <link id="SysUser_UserInfo" rel="stylesheet"
          href="${webPath}/component/sys/css/SysUser_UserInfo${skinSuffix}.css?v=${cssJsVersion}"/>
</head>
<script type="text/javascript">
    var headImg = '${sysUser.headImg}';
    var ifUploadHead = '${sysUser.ifUploadHead}';
    var opNo = '${sysUser.opNo}';
    var reportUrl = '${reportUrl}';
    $(function () {
        $("body").mCustomScrollbar({
            advanced: {
                updateOnContentResize: true
            }
        });
    });
</script>
<style type="text/css">
    #colBusData {
        margin-top: 12px;
        margin-left: 30px;
    }

    #colBusData1 {
        margin-top: 5px;
        margin-left: 30px;
    }
</style>
<body class="overflowHidden">
<div class="row clearfix homepage" id="home-page">
    <div class="col-md-4 column"></div>
    <div class="col-md-8 column">
        <div style="margin-top: 6px;">
            <span>为了方便办公，可以设置当前页面为首页！</span>
            <button class="homebtn">设为首页</button>
            <i class="i i-x3 " id="setHomePageClose"> </i>
        </div>
    </div>
</div>
<div class="container">
    <div class="row clearfix">
        <div class="col-md-8 column block-left">
            <div class="row clearfix base-info bg-white">
                <div class="col-md-3 column head-img text-center">
                    <img id="headImgShow" name="headImgShow"
                         class="img-circle pointer"
                    />
                    <div class="margin_top_20 color_theme">
                        <i class="i i-xiaoxi pointer" onclick="sendMessage();"></i> <i
                            class="i i-youjian1"></i> <i class="i i-richeng pointer"
                                                         onclick="FullCalendar();"></i> <i class="i i-weixin"></i>
                    </div>
                </div>
                <div class="col-md-9 column base-content margin_top_10">
                    <div class="row clearfix ">
                        <div class="col-md-12 column">
                            <div class="font_size_16">${sysUser.opName} [${sysUser.opNo}]</div>
                        </div>
                    </div>
                    <div class="row clearfix margin_top_10">
                        <div class="col-md-12 column">
								<span><i class="i i-idcard2"></i> 
								<c:if test='${statu.sysUser.job!=""}'>${sysUser.job}</c:if> 
								<c:if test='${statu.sysUser.job==""}'>暂无</c:if></span>
                            <span class="margin_left_20"><i class=" i i-bumen"></i>${sysUser.brNo}</span>
                            <span class="margin_left_20"><i class=" i i-dianhua"></i>${sysUser.mobile}</span>
                        </div>
                    </div>
                    <div class="row clearfix head-content margin_top_20">
                        <div class="col-md-12 column">
                            <div class="col-md-2 column">
                                <p class="cont-title">担保项目数</p>
                                <p class="margin_top_15">
                                    <span class="content-span">${dataMap.pactCount}</span><span
                                        class="unit-span margin_right_25">笔</span>
                                </p>
                            </div>
                            <div class="col-md-2 column">
                                <p class="cont-title">担保金额</p>
                                <p class="margin_top_15">
                                    <span class="content-span">${dataMap.totleAmt}</span><span
                                        class="unit-span margin_right_25">万元</span>
                                </p>
                            </div>
                            <div class="col-md-2 column">
                                <p class="cont-title">在保合同</p>
                                <p class="margin_top_15">
                                    <span class="content-span">${dataMap.pactingCount}</span><span
                                        class="unit-span margin_right_25">笔</span>
                                </p>
                            </div>
                            <div class="col-md-2 column">
                                <p class="cont-title">在保金额</p>
                                <p class="margin_top_15">
                                    <span class="content-span">${dataMap.totleUnrepayAmt}</span><span
                                        class="unit-span">万元</span>
                                </p>
                            </div>
                            <div class="col-md-4 column">
                                <p class="cont-title">在保金额(收费口径)</p>
                                <p class="margin_top_15">
                                    <span class="content-span">${dataMap.totleUnrepayAmtForFee}</span><span
                                        class="unit-span">万元</span>
                                </p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <dhcc:pmsTag pmsId="report-swiper">
        <div class="col-md-4 column block-right ">
            <div class="bg-white right-info">
                <div class="row clearfix">
                    <div class="title-div swiper-div">
                        <label>大屏报表</label>
                        <button id="btn-swiper" class="btn block-view">
                            <span>大屏入口</span>
                        </button>
                    </div>
                    <button id="btn-swiperT" style="position:absolute; width:92%; height:70%" >
                        <img src="/component/report/images/bgi.png" height="100%" width="100%">
<%--                        <img src="/component/report/images/swiperT.png" height="100%" width="49%">--%>
                    </button>
                </div>
            </div>
        </div>
   <%-- </div>--%>
    </dhcc:pmsTag>
        <!--给请假事宜增加权限-->
        <dhcc:pmsTag pmsId="leav_for_leav">
            <div class="col-md-4 column block-right ">
                <div class="bg-white right-info">
                    <div class="row clearfix">
                        <div class="title-div leave-div">
                            <label>请假事宜</label>
                            <button id="btn-leave" class="btn block-view">
                                <span>请假申请</span>
                            </button>
                        </div>
                        <div class="content-div">
                            <div class="row clearfix">
                                <div class="col-md-12 column">
                                    <div class="apply-detail">
                                        <p>
                                            您今年一共请假<a href="javaScript:void(0);" id="leaveDays"
                                                      class="leave-detail">${dataMap.leaveTotal}</a>天
                                        </p>
                                        <p id="evaluation">${dataMap.evaluation}</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </dhcc:pmsTag>
        <dhcc:pmsTag pmsId="bus_overdue">
            <div class="col-md-4 column block-right ">
                <div class="bg-white right-info">
                    <div class="row clearfix">
                        <div class="row clearfix head-content margin_top_20">
                            <div class="col-md-12 column" id="colBusData">
                                <div class="col-md-4 column">
                                    <p class="cont-title">客户数量</p>
                                    <p class="margin_top_15">
                                        <span class="content-span">${dataMap.cusNumber}</span><span
                                            class="unit-span margin_right_25">个</span>
                                    </p>
                                </div>

                                <div class="col-md-4 column">
                                    <p class="cont-title">办理中笔数</p>
                                    <p class="margin_top_15">
                                        <span class="content-span">${dataMap.fincCount}</span><span
                                            class="unit-span margin_right_25">笔</span>
                                    </p>
                                </div>

                                <div class="col-md-4 column">
                                    <p class="cont-title">逾期天数</p>
                                    <p class="margin_top_15">
                                        <span class="content-span">${dataMap.overDays}</span><span
                                            class="unit-span">天</span>
                                    </p>
                                </div>


                            </div>
                            <div class="col-md-12 column" id="colBusData1">
                                <div class="col-md-12 column">
                                    <p class="cont-title">逾期金额</p>
                                    <p class="margin_top_15">
                                        <span class="content-span">${dataMap.repayPrcp}</span><span
                                            class="unit-span">万元</span>
                                    </p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </dhcc:pmsTag>

    </div>

    <div class="row clearfix">
        <div class="col-md-12 column block-whole">
            <div class="whole-div bg-white">
                <div class="title-div"
                     style="border-bottom: 1px solid #e3e3e3; height: 45px; margin: 0px 20px;">
                    <label>消息中心</label>
                </div>
                <div class="content-div">
                    <c:if test="${mfDeskMsgItemList != null}">
                        <c:forEach items="${mfDeskMsgItemList}"
                                   var="mfDeskMsgItem">
                            <dhcc:pmsTag pmsId="${mfDeskMsgItem.msgType}">
                                <div class="btn btn-app move-div"
                                     url="${webPath}${mfDeskMsgItem.jumpUrl}"
                                     isShowDialog="${mfDeskMsgItem.isShowDialog}"
                                     msgTitle="${mfDeskMsgItem.msgTitle}"
                                     menuNo="${mfDeskMsgItem.menuNo}"
                                     msgType="${mfDeskMsgItem.msgType}"
                                     style="<c:if test="${mfDeskMsgItem.useFlag == 0}">display:none</c:if>;"
                                     id="${mfDeskMsgItem.msgType}">
                                           <c:if test="${mfDeskMsgItem.msgCount > 0}">
                                                  <c:choose>
                                                      <c:when test="${mfDeskMsgItem.msgCount>99}">
                                                          <span class="badge">99+</span>
                                                      </c:when>
                                                      <c:otherwise>
                                                          <span class="badge" >${mfDeskMsgItem.msgCount}</span>
                                                      </c:otherwise>
                                                  </c:choose>
                                          </c:if>
                                    <div>
                                        <i class="i ${mfDeskMsgItem.msgImg} msg-icon"></i>
                                    </div>
                                    <div>${mfDeskMsgItem.msgTitle}</div>
                                </div>
                            </dhcc:pmsTag>
                        </c:forEach>
                    </c:if>
                    <div class="btn btn-app" id="addItem">
                        <div class="margin_top_20"><i class="i i-jia1 color_theme msg-icon"></i></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<script type="text/javascript" src="${webPath}/component/sys/js/SysUser_UserInfo.js"></script>
<script type="text/javascript" src="${webPath}/UIplug/jqueryUI/js/jquery-ui-1.10.1.custom.min.js"></script>
<script type="text/javascript" src="${webPath}/component/finance/invoice/js/jquery.cookie.js"></script>
<script type="text/javascript" src="${webPath}/component/sys/js/notice.js"></script>
<script type="text/javascript">
    var isMousemove = false;//全局变量，用来存贮鼠标移动状态
    var opNo = '${sysUser.opNo}';
    var homePage = '${sysUser.homePage}';
    $(function () {
        userInfo.init();
        $(".content-div").sortable({
            cursor: "move",
            items: ".move-div",
            start: function (event, ui) {
            },
            update: function (event, ui) {
            }
        });
        $(".content-div").sortable("option", "cursor", "move");
        $(".content-div").sortable("option", "items", ".move-div");
        // 鼠标拖动开始时触发
        $(".content-div").on("sortstart", function (event, ui) {
            isMousemove = true;
        });
        // 拖动事件结束时触发
        $(".content-div").on("sortupdate", function (event, ui) {
            var msgTypes = [];
            $.each($(".move-div"), function (index, item) {
                msgTypes[index] = $(item).attr("msgType");
            });
            $.ajax({
                url: webPath + "/mfDeskMsgItem/updateSortAjax",
                data: {
                    opNo: opNo,
                    msgTypes: msgTypes
                },
                type: 'post',
                dataType: 'json',
                traditional: true,
                success: function (data) {
                    if (data.flag == "success") {
                    } else {
                        alert(top.getMessage("FAILED_OPERATION", "排序"), 0);
                    }
                },
                error: function () {
                    alert(top.getMessage("FAILED_OPERATION", "排序"), 0);
                }
            });
        });
        Notice.init();
    });
</script>
</html>