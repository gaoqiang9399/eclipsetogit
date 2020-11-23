<%@page import="net.sf.json.JSONObject"%>
<%@page import="app.component.nmd.entity.ParmDic"%>
<%@page import="net.sf.json.JSONArray"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="app.component.common.SysGlobalParams"%>
<%@ page import="app.component.sys.entity.MfSysCompanyMst"%>
<%@ include file="/component/include/webPath.jsp" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
    String layout = "layout/view";
    MfSysCompanyMst mfSysCompanyMst=( MfSysCompanyMst) SysGlobalParams.get("COMPANY");
// 	String pasTypeOperation = JSONArray.fromObject(pdMap).toString();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style='height:100%'>
<head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1" />
    <!--[if lt IE 9]>
    <script src="${webPath}/UIplug/html5shiv/html5shiv.min.js"></script>
    <script src="${webPath}/UIplug/html5shiv/respond.min.js"></script>
    <![endif]-->
    <script type="text/javascript">
        var webPath = "${webPath}";
        var basePath = "${webPath}";
        var loadingGifPath = "<%=mfSysCompanyMst.getLoadAnimationImg()%>";
    </script>

    <script type="text/javascript"
            src="${webPath}/<%=layout%>/js/jquery-1.11.2.min.js"></script>
    <!-- 因为缺少jQuery，该jsp报错，所以引入到下方 by LiuYF -->
    <%@ include file="/component/include/pub_view_table.jsp"%>
    <%@ include file="/component/wkf/Wkf_Base.jsp"%>
    <script type="text/javascript" src="${webPath}/layout/ie8/js/json2.js"></script>

    <%--滚动条js 和鼠标滚动事件js--%>
    <script type="text/javascript"
            src="${webPath}/UIplug/customScrollbar/js/jquery.mousewheel-3.0.6.min.js"></script>
    <script type="text/javascript"
            src="${webPath}/UIplug/customScrollbar/js/jquery.mCustomScrollbar.js"></script>
    <!-- 自定义js 失去焦点校验 保存校验 -->
    <script type="text/javascript" src='${webPath}/component/include/uior_val.js'> </script>
    <script type="text/javascript" src='${webPath}/component/include/xcqi_cal.js'> </script>
    <script type="text/javascript" src="${webPath}/component/include/myAlert.js"></script>
    <script type="text/javascript" src="${webPath}/layout/view/js/openDiv.js"></script>
    <!-- 日期选择器S -->
    <script src="${webPath}/component/sys/js/moment.min.js"></script>
    <script src="${webPath}/component/sys/js/jquery.daterangepicker.js"></script>
    <link rel="stylesheet" href="${webPath}/component/sys/css/daterangepicker.css" />

    <link rel="stylesheet" href="${webPath}/component/sys/jQuery_guide/guide.css" />
    <script type="text/javascript" src="${webPath}/component/sys/jQuery_guide/jquery.guide.js"></script>

    <script type="text/javascript" src="${webPath}/UIplug/bootstrap/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="${webPath}/UIplug/bootstrap/css/bootstrap.min.css" />
    <script type="text/javascript" src="${webPath}/themes/factor/js/loadingAnimate.js?v=${cssJsVersion}"></script>
    <script type="text/javascript" src="${webPath}/component/sys/js/B1.js"></script>
    <!--artdialog插件  -->
    <script type="text/javascript" src="${webPath}/UIplug/artDialog/dist/dialog-plus-min.js"></script>
    <link id="ui-dialog" rel="stylesheet" href="${webPath}/UIplug/artDialog/css/ui-dialog${skinSuffix}.css" />
    <!-- 引入自定义的弹窗扩展 -->
    <script type="text/javascript" src="${webPath}/themes/factor/js/dialog.js?v=${cssJsVersion}"></script>
    <link rel="stylesheet"
          href="${webPath}/UIplug/iconmoon/style.css" />
    <link rel="stylesheet"
          href="${webPath}/UIplug/customScrollbar/css/jquery.mCustomScrollbar.css"
          type="text/css" />
    <link rel="stylesheet" href="${webPath}/themes/factor/css/factor.css?v=${cssJsVersion}" />
    <link rel="stylesheet" type="text/css"
          href="${webPath}/component/sys/css/B1.css" />
    <link id="B1" rel="stylesheet" type="text/css"
          href="${webPath}/themes/factor/css/B1${skinSuffix}.css" />
    <script>
        var pasMaxNo = '${pasMaxNo}';
        var homePage = '${homePage}';
        var loadingGifPath = "<%=mfSysCompanyMst.getLoadAnimationImg()%>";
        $(document).ready(
            function() {
                if(homePage=="1"){
                    $("#home-page").show();
                }
                var pasTypeOperation = '';
                //pasTypeOperation = JSON.parse(pasTypeOperation){"optCode":"0","optName":"查看"};
                var aData="";
                var jData={"pageNo":1,"pasSts":0,"pasMaxNo":pasMaxNo,"pasMinNo":"","pasAware":"","createDate":""};
                $.ajax({
                    type : "POST",
                    url :webPath+"/sysTaskInfo/getListPageToBAjax",
                    dataType : "json",
                    data:{
                        jsonData : JSON.stringify(jData)
                    },
                    complete:function(){
                        LoadingAnimate.stop();
                    },
                    success : function(jsonData) {
                        LoadingAnimate.stop();
                        var initData = jsonData.ajaxData;
                        initData = $.parseJSON(initData);
                        menu_zNodes = initData.pasBigType;
                        menu_zNodes.unshift(createTreeNode("全部",""));
                        menu_zNodes.push(createTreeNode("关注任务","pasAware",initData.pasAwareCount));
                        for (var i = 0; i < menu_zNodes.length; i++) {
                            if(menu_zNodes[i].optCode=="1"){
                                menu_zNodes.splice(i,1);
                                break;
                            }
                        }


                        var pasSubType = initData.pasSubType;
                        $.fn.zTree.init($("#menu_tree"), menu_setting, menu_zNodes);
                        taskB.options.SysDate=initData.SysDate;
                        pasTypeOperation = initData.pasTypeOperation;
                        taskB.initTasks(".work-zone-tasks",initData.messageList.result, pasSubType,pasTypeOperation);
                        /* taskB.initPasAwareCount(); */
                        //createTimeLine(initData.SysDate,10);
                        createTimeLine1(initData.timeList);
                        //$("#time-line-all").find('.time-line-head').addClass('line-dot-on1 i i-duihao1');
                        $("#time-line-all").addClass('time-line-point-select');
                        $("#time-line-all").find('a').addClass('line-a-on');
                        $("#time-line-all").find('span').addClass('line-dot-on i i-duihao1');
                    },
                    error : function(xmlhq, ts, err) {

                    }

                });
            });
        function openBigForm(url,title,callback){
            window.top.openBigForm(url,title,callback);
        }
        mcTimeline($('#task-div')); // <--不知道哪个狗日的写的代码，写在这里，也不知道干啥用的

        function createTreeNode(optName,id,count){
            if(!count){
                count="";
            }

            var treeNode= {
                "criteriaLists": [
                ],
                "customQuery": [
                ],
                "endNum": 0,
                "keyName": "PAS_BIG_TYPE",
                "optCode": "",
                "optName": optName,
                "seqn": 2,
                "startNum": 0,
                "sts": "",
                "id": id,
                "name": optName,
                "open": true,
                "count": count
            }
            return treeNode;
        }

        Date.prototype.Format = function (fmt) { //author: meizz
            var o = {
                "M+": this.getMonth() + 1, //月份
                "d+": this.getDate(), //日
                "h+": this.getHours(), //小时
                "m+": this.getMinutes(), //分
                "s+": this.getSeconds(), //秒
                "q+": Math.floor((this.getMonth() + 3) / 3), //季度
                "S": this.getMilliseconds() //毫秒
            };
            if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
            for (var k in o)
                if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
            return fmt;
        }


        var time1 = new Date().Format("yyyyMMdd");
        function cancle(){
            $("#home-page").hide();
            $("#home-blank").hide();
        }
        function updateHomePage(){
            jQuery.ajax({
                url:webPath+"/sysUser/updateStsAjax?homePage=2",
                type:"POST",
                dataType:"json",
                beforeSend:function(){
                },success:function(data){
                    if(data.flag == "success"){
                        alert("设置首页成功！",1);
                        //window.location.reload();
                        $("#home-page").hide();
                    }else if(data.flag == "error"){
                        $.myAlert.Alert(data.msg);
                    }
                },error:function(data){
                    alert(top.getMessage("FAILED_OPERATION"," "),0);
                }
            });
        };
    </script>

</head>

<body style="overflow-y:hidden; overflow-x:hidden">
<div class="_mCS_B1"id="task-div" style="background: #fff;margin-top:10px;height:100%">
    <div style="width: 10px;height: 100%;background: white;position: fixed;"></div>
    <div class="work-zone-tree">
        <div class="work-zone-head">
            <%--<div  class="task-sts" >--%>
                <%--<span class="input-btn" id="all-task" >全部</span>--%>
                <%--<!-- <span class="input-btn" id="yb-task">已办理</span> -->--%>
                <%--<span class="input-btn input-select" id="wb-task">未办理</span>--%>
                <%--<span class="input-btn" id="pass-task">已通过</span>--%>
                <%--<span class="input-btn" id="veto-task">已否决</span>--%>
            <%--</div>--%>
            <div class="work-zone-title" style="position: relative;margin-top: 15px; ">
                <label class="bt-title">类型</label>
                <ul id="menu_tree" class="ztree" style="position: absolute;top: 0px;left: 0px">
                    <!-- <li id="pasAware" class="level0">
                            <a class="level0"  title="关注任务">
                                <span >关注任务</span>
                                <span class="task_count" ></span>
                            </a>
                    </li> -->
                </ul>
            </div>


        </div>
    </div>
    <div style="margin-top: 15px;min-height: 500px;">
        <div class="col-sm-10">
            <div class="work-zone-tasks" id="work-zone-tasks"></div>
        </div>

        <div class="work-zone-timeLine col-sm-2">
            <div class="time_contents">
                <div class="time-line-bg">
                    <div class="time-line-border time-line-border1 time-line-point" data-dit="all" id="time-line-all">
                        <span class="time-line-dot1"></span>
                        <a class="time-line-title">全部</a>
                    </div>

                    <div class="time-line-line"></div>
                    <div class="time-line-body">
                        <dl class="time-line-dl"></dl>
                    </div>
                    <div class="time-line-border time-line-border2 time-line-point" data-dit="all" style="display:none;">
                        <!-- <div class="time-line-head time-line-head2"></div> -->
                        <span class="time-line-dot2"></span>
                        <a class="time-line-title">更多</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>

</html>
