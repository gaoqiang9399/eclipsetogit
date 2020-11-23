<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
var archiveMainNo = "${archiveInfoMain.archiveMainNo}";
$(function(){
	$("#lend-list").click(function() {
		 top.createShowDialog(webPath+'/archiveInfoBorrow/getListAllPage?archiveMainNo=' + archiveMainNo, '借阅文件列表', '90', '90');
	});
});
</script>
<div class="row clearfix padding_top_10">
	<div class="col-xs-12 col-md-12 column padding_left_15">
		<button class="btn btn-link block-title">借阅记录</button>
		<%--<button type="button" class="btn btn-font-qiehuan pull-right his-detail-opt" id="lend-list">更多</button>--%>
	</div>
</div>
<div class="row clearfix padding_left_12 his-statistic">
	<div class="col-xs-12 col-md-12 column" >
		<div id="lend_in_div" class="list-table">
			<dhcc:thirdTableTag property="tablearchiveborrowdetailin" paginate="archiveLendInfoList" head="true"></dhcc:thirdTableTag>
		</div>
	</div>
</div>		
<div class="row clearfix">
	<div class="col-xs-12 col-md-12 column">
		<div class="line-div"></div>
	</div>
</div>					