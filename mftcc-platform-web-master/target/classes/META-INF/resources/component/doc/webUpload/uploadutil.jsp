<%@ page language="java" pageEncoding="utf-8" %>
<%@ page import="cn.mftcc.util.PropertiesUtil" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String fileSize = PropertiesUtil.getUploadProperty("fileSize");
%>
<%--列表形式展示要件 字典项 DOC_SHOW_TYPE --%>
<c:choose>
    <c:when test="${docShowType == '1'}">
        <script type="text/javascript"
                src="${webPath}/component/doc/webUpload/webuploader/webuploader.js"></script>
        <link id="main-detail" rel="stylesheet"
              href="${webPath}/themes/factor/css/main-detail${skinSuffix}.css"/>
        <link rel="stylesheet"
              href="${webPath}/component/doc/webUpload/webuploader/webuploader.css"/>
        <script type="text/javascript"
                src="${webPath}/UIplug/zTree/jquery.ztree.all.js"></script>
        <script type="text/javascript"
                src="${webPath}/component/doc/webUpload/js/uploadListForMainPage.js"></script>
        <link rel="stylesheet"
              href="${webPath}/component/doc/webUpload/css/upload.css?v=${cssJsVersion}"/>
        <link rel="stylesheet"
              href="${webPath}/component/doc/webUpload/css/uploadForMainPage.css?v=${cssJsVersion}"/>
        <script type="text/javascript"
                src="${webPath}/UIplug/ViewImg/viewerForList.js?v=${cssJsVersion}"></script>
        <link rel="stylesheet" href="${webPath}/UIplug/ViewImg/viewer.css"
              type="text/css"/>
        <script type="text/javascript"
                src="${webPath}/component/model/js/mfPageOffice.js?v=${cssJsVersion}"></script>
        <script type="text/javascript">
            var fileSize =<%=fileSize%>;
            if (fileSize == null) {
                fileSize = 100 * 1024 * 1024;//如果配置文件中没有配置upload.fileSize属性，默认大小是20M
            }
            $(function () {
                initDocNodes();
                isSupportBase64 = (function () {
                    var data = new Image();
                    var support = true;
                    data.onload = data.onerror = function () {
                        if (this.width != 1 || this.height != 1) {
                            support = false;
                        }
                    };
                    data.src = "data:image/gif;base64,R0lGODlhAQABAIAAAAAAAP///ywAAAAAAQABAAACAUwAOw==";
                    return support;
                })();

            });

            function getFilterValArr() {
                var s = docParm.split("&");
                var ParamData = new Object();
                for (var i = 0; i < s.length; i++) {
                    var kv = s[i].split("=");
                    ParamData[kv[0]] = kv[1];
                }
                return JSON.stringify(ParamData);
            }

        </script>
        <div class="overflowHidden bg-white">
            <div class="row clearfix">
                <div class="upload_body">
                    <ul class="ztree" id="uploadTree">
                        <li id="uploadTree_1" class="level0" tabindex="0" hidefocus="true" treenode=""><a
                                id="uploadTree_1_a" class="level0" treenode_a="" onclick="" target="_blank"
                                style=""><span id="uploadTree_1_span" class="node_name"><i
                                class="i i-xing blockDian"></i>要件列表</span>
                            <button title="新增" onclick="docManageAdd();return false;"
                                    style="position: relative;display: inline-block;top: -3px;left: 2px;" class="btn btn-link formAdd-btnFile">
                                <i class="i i-jia3"></i>
                            </button>
                            <button class=" btn btn-link pull-right formAdd-btn" data-toggle="collapse"
                                    data-target=".doc-manage-table" aria-expanded="true">
                                <i class="i i-close-up"></i>
                                <i class="i i-open-down"></i>
                            </button>
                                <div class="input-group pull-right" style = "width:180px;position:absolute;left:550px;top:1px";>
                                    <input type="text" class="form-control" name="filter_in_input" onkeypress = "initDocNodes()"  id="filter_in_input" placeholder="根据文件分类或文件名搜索" value="">
                                    <%--<span class="input-group-addon" id="filter_btn_search" onclick="initDocNodes()" >搜索</span>--%>
                                    <span class="input-group-addon">
                                        &nbsp;<i class="i i-fangdajing"  id="filter_btn_search" onclick="initDocNodes()"></i>&nbsp
                                    </span>
                                </div>
                            <button class=" btn btn-link pull-right  download-btn"
                                    onclick="downloadZip('uploadTree','uploadTree_1');">打包下载
                            </button>
                        </a>
                        </li>
                    </ul>
                    <div id="content" class="table_content list-table doc-manage-table" style="height: auto;">
                    </div>
                </div>
                <div id="view-imgs">
                    <div class="enlarge-img">
                        <input class="close_btn" type="button" value=""
                               onclick="close_view();"/>
                        <div class="img-tools">
                            <span class="rotateRight">向右旋转</span> <span class="rotateLeft">向左旋转</span>
                            <strong class="title"></strong>
                            <div class="view-img last-img">
                                <div></div>
                            </div>
                            <div class="view-img next-img">
                                <div></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <script type="text/javascript"
                    src="${webPath}/UIplug/notie/notie.js"></script>
        </div>
    </c:when>
<%--块状形式展示要件--%>
    <c:otherwise>
        <script type="text/javascript"
                src="${webPath}/component/doc/webUpload/webuploader/webuploader.js"></script>
        <link rel="stylesheet"
              href="${webPath}/component/doc/webUpload/webuploader/webuploader.css"/>
        <script type="text/javascript"
                src="${webPath}/UIplug/zTree/jquery.ztree.all.js"></script>
        <script type="text/javascript"
                src="${webPath}/component/doc/webUpload/js/uploadForMainPage.js"></script>
        <link rel="stylesheet"
              href="${webPath}/component/doc/webUpload/css/upload.css?v=${cssJsVersion}"/>
        <link rel="stylesheet"
              href="${webPath}/component/doc/webUpload/css/uploadForMainPage.css?v=${cssJsVersion}"/>
        <script type="text/javascript"
                src="${webPath}/UIplug/ViewImg/viewer.js?v=${cssJsVersion}"></script>
        <link rel="stylesheet" href="${webPath}/UIplug/ViewImg/viewer.css"
              type="text/css"/>
        <script type="text/javascript"
                src="${webPath}/component/model/js/mfPageOffice.js?v=${cssJsVersion}"></script>
        <script type="text/javascript">
            var fileSize =<%=fileSize%>;
            if (fileSize == null) {
                fileSize = 100 * 1024 * 1024;//如果配置文件中没有配置upload.fileSize属性，默认大小是20M
            }
        </script>
        <div class="overflowHidden bg-white">
            <div class="row clearfix">
                <div class="upload_body">
                    <ul class="ztree" id="uploadTree"></ul>
                </div>
                <div id="view-imgs">
                    <div class="enlarge-img">
                        <input class="close_btn" type="button" value=""
                               onclick="close_view();"/>
                        <div class="img-tools">
                            <span class="rotateRight">向右旋转</span> <span class="rotateLeft">向左旋转</span>
                            <strong class="title"></strong>
                            <div class="view-img last-img">
                                <div></div>
                            </div>
                            <div class="view-img next-img">
                                <div></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <script type="text/javascript"
                    src="${webPath}/UIplug/notie/notie.js"></script>
        </div>
    </c:otherwise>
</c:choose>
