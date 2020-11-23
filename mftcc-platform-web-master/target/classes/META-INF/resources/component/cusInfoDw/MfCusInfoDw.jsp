<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
         contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>

<!DOCTYPE html>
<html>
<head>
  <title>客户信息下载</title>
</head>
<body class="overflowHidden">
<div class="container">
  <div class="row clearfix bg-white tabCont">
    <div class="col-md-12 column">
      <div class="search-div">
        <div class="col-xs-9 column">
          <dhcc:pmsTag pmsId="clear-all-cus-bus-button">
            <button type="button" class="btn btn-primary pull-left" onclick="MfCusInfoDw.openCusInfoForm();">查询</button>
          </dhcc:pmsTag>
        </div>
        <jsp:include page="/component/include/mySearch.jsp?blockType=2&placeholder=客户名称/合同号"/>
      </div>
    </div>
  </div>
  <div class="row clearfix">
    <div class="col-md-12 column">
      <div id="content" class="table_content"  style="height: auto;">
      </div>
    </div>
  </div>
</div>
</body>
<script type="text/javascript" src="${webPath}/component/cusInfoDw/js/MfCusInfoDw.js"></script>
<script>
    $(function () {
        MfCusInfoDw.init();
    })
</script>
</html>
