<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
var archiveMainNo = "${archiveInfoMain.archiveMainNo}";
$(function(){
	$("#lend-list").click(function() {
		 top.createShowDialog(webPath+'/archiveLendInfo/getListAllPage?archiveMainNo=' + archiveMainNo, '借出文件列表', '90', '90');
	});
});
</script>
<div class="row clearfix padding_top_10">
	<div class="col-xs-12 col-md-12 column padding_left_15">
		<button class="btn btn-link block-title">借出文件</button>
		<button type="button" class="btn btn-font-qiehuan pull-right his-detail-opt" id="lend-list">更多</button>
	</div>
</div>
<div class="row clearfix padding_left_12 his-statistic">
	<div class="col-xs-12 col-md-12 column" >
		<div id="lend_div" class="list-table">
			  <c:if test="${dataFlag=='1'}">   
				<dhcc:thirdTableTag property="tabledl_archive_lend01" paginate="archiveLendInfoList" head="true"></dhcc:thirdTableTag>
			  </c:if> 
			   <c:if test="${dataFlag=='0'}">  
				<div class="no-content" align="center">暂无数据</div>
			  </c:if>
		</div>
	</div>
</div>		
<div class="row clearfix">
	<div class="col-xs-12 col-md-12 column">
		<div class="line-div"></div>
	</div>
</div>					