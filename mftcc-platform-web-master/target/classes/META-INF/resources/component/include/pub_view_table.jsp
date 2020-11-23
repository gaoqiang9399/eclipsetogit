<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!--[if lte IE 9]> 
<meta http-equiv="X-UA-Compatible" content="IE=8"/>
<![endif]--> 
<link rel="stylesheet" href="${webPath}/UIplug/zTree/metroStyle/metroStyle.css" />
<script type="text/javascript" src="${webPath}/UIplug/zTree/jquery.ztree.all-3.5.min.js"></script>
<%--列表固定表头--%>
<script type="text/javascript" src="${webPath}/component/include/table_head.js"></script>	
<%--我的筛选js,列表上的自定义查询借用该js相关功能 所以放在公共页面引用--%>
<script type="text/javascript" src="${webPath}/component/include/filter.js"></script>
<script type="text/javascript" src="${webPath}/component/include/myCustomScrollbar.js"></script>
<%--我的筛选样式--%>
<%-- <link rel="stylesheet" href="${webPath}/themes/view/css/filter.css?v=${cssJsVersion}" /> --%>
<!-- 行政区划和行业划分 -->
<script type="text/javascript" src="${webPath}/component/include/IndustryAndArea.js"></script>
<!-- <!--帮助组件  -->
<%-- <script type="text/javascript" src="${webPath}/component/help/sysHelp.js" ></script> --%>
<%-- <link rel="stylesheet" href="${webPath}/component/help/sysHelp.css" /> --%>
<%--保理样式 --%>
<link id="filter" rel="stylesheet" href="${webPath}/themes/factor/css/filter${skinSuffix}.css?v=${cssJsVersion}" />
<link id="list" rel="stylesheet" href="${webPath}/themes/factor/css/list${skinSuffix}.css?v=${cssJsVersion}" />
<!--加载动画-->
<!--<div class="footer_loader">
	 <div class="loader">
	    <span></span>
	    <span></span>
	    <span></span>
	    <span></span>
	    <span></span>
	    <span></span>
	    <span></span>
	    <span></span> 
	</div>
	<div class="loader">
	    <i class="fa fa-spinner fa-pulse fa-3x"></i>
	</div>
	<div class="pagerShow">当前显示&nbsp;<span class="loadCount">0</span>&nbsp;条数据，一共&nbsp;<span class="pageCount">0</span>&nbsp;条数据</div>
	<div class="backToTop"></div>
</div>-->
<!--<div class="messageTitle" data-example-id="static-tooltips">
	<div role="tooltip" class="tooltip bottom">
	      <div class="tooltip-arrow"></div>
	      <div class="tooltip-inner">
	         	 正在加载...
	      </div>
    </div>
</div>-->
<%
	String searchData = request.getParameter("ajaxData");
	String searchFlag = request.getParameter("searchFlag");
	if(searchFlag!=null&& "true".equals(searchFlag)){
		searchData = new String(searchData.trim().getBytes("ISO-8859-1"), "utf-8");
		searchData = "[{\"customQuery\":\""+searchData+"\"}]";
	}
%>
<script type="text/javascript">
	  $(function(){
	  		var searchData = '<%=searchData%>';
	  		searchData = searchData!=null&&searchData!="null"?eval('('+searchData+')'):[];
	  		for ( var i = 0; i < searchData.length; i++) {
	  			if(searchData[i].hasOwnProperty('customQuery')){
			  		$(".filter-btn-group").find("#filter_in_input").val(searchData[i].customQuery);
	  			}else{
		  			if(i<searchData.length-1){
		  				var zNodes = searchData[i][0];
		  				if(typeof(zNodes)!="undefined" && (zNodes.treeId=="my_filter"&&$("#my_filter").length>0)){
			  				$(function(){
					  			/* var treeObj = $.fn.zTree.getZTreeObj(zNodes.treeId);
					  			var node = treeObj.getNodeByTId(zNodes.tId);
					  			treeObj.checkNode(node, true, true);
					  			addPill(zNodes.tId,node,zNodes.treeId); */
			  				});
		  				}else{
		  					window.onload = function(){ 
		  						$(function(){
		  							//console.log($("#"+zNodes.treeId));
		  							if($("#"+zNodes.treeId).length>0){
			  							var treeObj = $.fn.zTree.getZTreeObj(zNodes.treeId);
							  			var node = treeObj.getNodeByTId(zNodes.tId);
							  			treeObj.checkNode(node, true, true);
							  			addPill(zNodes.tId,node,zNodes.treeId);
			  						}else{
					  					addPillTemp(zNodes.tId,zNodes,zNodes.treeId);
			  						}
		  						});
		  					};
		  				}
		  			}
	  			}
			}
	  });
	 //window.top.getHelp();
// 	 getHelp();
</script>