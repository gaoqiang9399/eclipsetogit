<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>角色管理</title>
</head>
<body class="overflowHidden">
<div class="container">
    <div class="row clearfix bg-white tabCont">
        <div class="btn-div">
            <button type="button" class="btn btn-primary"  onclick="sysRoleManagement.openCusInfoForm();">新增角色</button>
        </div>
        <div class="col-md-12">
            <div class="search-div">
                <jsp:include page="/component/include/mySearch.jsp?blockType=2&placeholder=角色名称/角色编号"/>
            </div>
        </div>
    </div>

    <div class="row clearfix">
        <div class="col-md-12">
            <div id="content" class="table_content" style="height: auto;">
            </div>
        </div>
    </div>

</div>
</body>
<script type="text/javascript" src="${webPath}/component/sys/js/sysRoleManagement.js"></script>
<script>
    $(function () {
        sysRoleManagement.init();
    })

</script>
</html>