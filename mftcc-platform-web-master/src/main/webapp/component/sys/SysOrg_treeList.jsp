<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@page import="app.component.common.BizPubParm"%>
<%@ include file="/component/include/common.jsp" %>
<%@ include file="/component/include/pub_view_table.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <base target="_self">
    <title>通过角色选择</title>

    <script type='text/javascript' src='${webPath}/dwr/engine.js'></script>
    <script type='text/javascript' src='${webPath}/dwr/util.js'></script>
    <script type='text/javascript' src='${webPath}/dwr/interface/SysOrgDwr.js'></script>
    <script type="text/javascript" src="${webPath}/UIplug/rcswitcher-master/js/rcswitcher.min.js"></script>
    <link rel="stylesheet" href="${webPath}/UIplug/rcswitcher-master/css/rcswitcher.min.css" type="text/css"/>
    <script type="text/javascript" src="${webPath}/component/include/myRcswitcher.js"></script>
    <link rel="stylesheet" href="${webPath}/component/sys/css/sysOrg.css"/>
    <style type="text/css">
        /* 禁止操作列换行。该页列表只有一个超链。如果增加其他超链，需要注意。 */
        #tablist a {
            white-space: nowrap;
        }
    </style>
    <script src="${webPath}/component/sys/js/sysOrg.js" type="text/javascript"></script>
    <script type="text/javascript">
        var ajaxData = ${ajaxData};
        var opNoType = "${opNoType}";
        $(function () {
            if (opNoType == "<%=BizPubParm.OP_NO_TYPE2%>"
                || opNoType == "<%=BizPubParm.OP_NO_TYPE3%>"
                || opNoType == "<%=BizPubParm.OP_NO_TYPE4%>") {
                $("#sysOrgTree_1_a").click();
            } else {
                $("a.level0").click();
            }
            $(".sys-org-ztree").mCustomScrollbar({
                advanced: {
                    updateOnContentResize: true
                }
            });

            $(".sys-org-ztree").height($(document).height() - 140);
        });

        function resetPassword(obj, url) {
            if (url.substr(0, 1) == "/") {
                url = webPath + url;
            } else {
                url = webPath + "/" + url;
            }
            $.post(url, function (data) {
                if (data.flag == "success") {
                    alert(data.msg, 1);
                } else {
                    alert(data.msg, 0);
                }
            });
        };

        function unlockAccount (obj, url){
            if (url.substr(0, 1) == "/") {
                url = webPath + url;
            } else {
                url = webPath + "/" + url;
            }
            $.post(url, function (data) {
                if (data.flag == "success") {
                    alert(data.msg, 1);
                } else {
                    alert(data.msg, 0);
                }
            });
        };
        //删除操作员
        window.deleteOp = function (obj, url) {
            var ajaxParam = {};
            if (url.indexOf("ActionAjax_") != -1 && url.indexOf("?") != -1) {//ajax提交
                var urlParams = url.split("?");
                url = urlParams[0];
                $.each(urlParams[1].split("&"), function (index, val) {
                    var key = val.split("=")[0];
                    var value = val.split("=")[1];
                    ajaxParam[key] = value;
                });
            }
            $.ajax({
                url: webPath + "/sysUser/getCountByOpNoAjax",
                type: "POST",
                dataType: "json",
                data: ajaxParam,
                success: function (data) {
                    if (data.flag == "success") {
                        if (data.count == 0) {
                            window.top.alert(top.getMessage("CONFIRM_DELETE"), 2, function () {
                                deleteOpNo(ajaxParam);
                            });
                        } else {
                            window.top.alert(top.getMessage("CONFIRM_DELETE_ASSOCIATE"), 4, function () {
                                deleteOpNo(ajaxParam);
                            });
                        }

                    } else if (data.flag == "error") {
                        window.top.alert(data.msg, 0);
                    }
                }, error: function (data) {
                    alert(top.getMessage("FAILED_OPERATION"), 0);
                }
            });

        }

        function deleteOpNo(ajaxParam) {
            $.ajax({
                url: webPath + "/sysUser/deleteAjax",
                dataType: 'json',
                data: ajaxParam,
                type: 'post',
                success: function (data) {
                    if (data.flag == "success") {
                        window.top.alert(data.msg, 1);
                        updateTableData();//重新加载列表数据
                    } else {
                        window.top.alert(data.msg, 0);
                    }
                }
            })
        }

        function refreshBusData() {
            $.ajax({
                url: webPath + "/sysOrgInfoChange/refreshBusData",
                dataType: 'json',
                type: 'post',
                success: function (data) {
                    if (data.flag == "success") {
                        window.top.alert("刷新组织架构成功", 3);
                    } else {
                        window.top.alert("刷新组织架构失败", 0);
                    }
                },
                error: function () {
                    window.top.alert("刷新组织架构失败", 0);
                }
            })
        }
    </script>
</head>
<body class="overflowHidden">
<div class="container">
    <c:if test="${opNoType == '1' }"><!-- 系统操作员显示  -->
    <div class="row clearfix tabCont">
        <div class="col-md-12 column">
            <div class="btn-div">
                <div class="col-xs-9 column">
                    <dhcc:pmsTag pmsId="set-new-add-dept-btn">
                        <button type="button" class="btn btn-primary" onclick="addSysOrg();">新增部门</button>
                    </dhcc:pmsTag>
                    <dhcc:pmsTag pmsId="set-new-add-emp-btn">
                        <button type="button" class="btn btn-primary" onclick="addSysUser();">新增员工</button>
                    </dhcc:pmsTag>
                    <dhcc:pmsTag pmsId="refresh-bus-data-btn">
                        <button type="button" class="btn btn-primary" onclick="refreshBusData();">刷新组织架构</button>
                    </dhcc:pmsTag>

                    <dhcc:pmsTag pmsId="sys-role-management-btn">
                        <button type="button" class="btn btn-primary" onclick="addRolr();">角色管理</button>
                    </dhcc:pmsTag>
                </div>
                <jsp:include page="/component/include/mySearch.jsp?blockType=2&placeholder=用户名称/登录账户"/>
            </div>
        </div>
    </div>
    </c:if>
    <div class="row clearfix padding_left_8">
        <div class="sys-org-ztree col-md-2">
            <ul id="sysOrgTree" class="ztree"></ul>
        </div>
        <div class="sys-org-list col-md-10 " id="sysUserListByNo">
            <div class="row clearfix">
                <div class="col-md-12 column">
                    <div id="sysUserList" class="table_content" style="height: auto;padding-left:0px;">
                    </div>
                </div>
            </div>
        </div>

    </div>
</div>
<input id="brNo_hide" style="display: none;"/>
<!-- <div id="rMenu">
<button type="button" class="btn btn-primary pull-left" onclick="addSysOrg();">新增部门</button>
<button type="button" class="btn btn-primary pull-left" style="margin-left: 16;" onclick="addSysUser();">新增员工</button>
<input id="brNo_hide" style="display: none;"/>
    <ul>
        <li id="m_add" onclick="addTreeNode();">新增机构</li>
        <li id="m_del" onclick="removeTreeNode();">删除机构</li>
        <li id="m_reset" onclick="resetTree();">刷新机构</li>
    </ul>
</div> -->
</body>
</html>