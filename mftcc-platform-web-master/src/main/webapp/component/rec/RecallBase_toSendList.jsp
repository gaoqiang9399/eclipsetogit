<%-- [{"width":"797px","height":"556px","left":"0px","top":"0px","name":"点此拖拽","cellid":"cell_1"},{"width":"475px","height":"254px","left":"805px","top":"0px","name":"点此拖拽","cellid":"cell_2"},{"width":"475px","height":"294px","left":"805px","top":"261.625px","name":"点此拖拽","cellid":"cell_3"}] --%>
<%-- {"cell_1":{"cellid":"cell_1","cellname":"点此拖拽","chart":{}},"cell_2":{"cellid":"cell_2","cellname":"点此拖拽","chart":{}},"cell_3":{"cellid":"cell_3","cellname":"点此拖拽","chart":{}}} --%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tld/loan.tld" prefix="dhcc" %>
<%@ include file="/component/include/message.jsp" %>
<%
String contextPath = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+webPath+"/";
%>
<!DOCTYPE html>
<html lang="zh-cn">
<head >
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1"/>
		<script type="text/javascript" src="${webPath}/layout/view/js/jquery-1.11.2.min.js"></script>
		<script type="text/javascript" src="${webPath}/UIplug/bootstrap/js/bootstrap.min.js"></script>
		<script type="text/javascript" src="${webPath}/component/rec/js/recallBase_send.js"></script> 
		<script type="text/javascript" src="${webPath}/component/include/form.js?v=${cssJsVersion}"></script> 
		<script type="text/javascript" src="${webPath}/component/include/jquery.autocompleter.js"></script>
		<script type="text/javascript" src="${webPath}/component/include/jquery.autocompleter.unbound.js"></script>
		<link rel="stylesheet" href="${webPath}/component/include/autocompleter.css" />
		<%--滚动条js 和鼠标滚动事件js--%>
		<script type="text/javascript" src="${webPath}/UIplug/customScrollbar/js/jquery.mousewheel.min.js"></script>
		<script type="text/javascript" src="${webPath}/UIplug/customScrollbar/js/jquery.mCustomScrollbar.js"></script>
		<script type="text/javascript" src="${webPath}/UIplug/rcswitcher-master/js/rcswitcher.min.js"></script>
		<%--滚动样式--%>
		<link rel="stylesheet" href="${webPath}/UIplug/customScrollbar/css/jquery.mCustomScrollbar.css" type="text/css" />
		<link rel="stylesheet" href="${webPath}/UIplug/rcswitcher-master/css/rcswitcher.min.css" type="text/css" />
		<link type="text/css" rel="stylesheet" href="${webPath}/UIplug/bootstrap/css/bootstrap.min.css" />
		<link rel="stylesheet" href="${webPath}/themes/view/css/view-main.css" />
		<link rel="stylesheet" href="${webPath}/themes/factor/css/view-main.css" />
		<%--字体图标--%>
		<link rel="stylesheet" href="${webPath}/layout/view/themes/iconfont/css/iconfont.css" />
		<link rel="stylesheet" href="${webPath}/UIplug/Font-Awesome/css/font-awesome.min.css" />
		<link rel="stylesheet" href="${webPath}/UIplug/Font-Awesome/css/font-awesome.css" />
		<link rel="stylesheet" href="${webPath}/themes/factor/css/budgetLog_receive.css" />
		<style type="text/css">
		.cover {
		    cursor: default;
		}
		.table_content .ls_list tr.selected{
		background-color:#e0e0e0;
		}
		.row_content{
		margin:0;
		}
		.col_content{
		padding:0;
		}
		.bgColor{
		margin:0;
		}
		.col-md-8 .bgColor{
		margin-right:10px;
		}
		.col_content .title h4{
		color:#1185b9;
		font-size:16px;
		}
		
		.btn-primary{
			background-color:#32b5cb;
			border:1px solid #32b5cb;
		}
		.btn-primary:hover{
			background-color:#009db7;
			border:1px solid #32b5cb;
		}
		</style>
		
		
	</head>
<body class="overflow otherStyleBody">
<dhcc:markPoint markPointName="RecallBase_ListForPers"/>
<div class="content">
			<div class="row row_content">
    			<div class="col-md-8 col_content">
    				<div class="bgColor" style='height:100%'> 
    					<div class="title">
    						<h4>催收任务</h4>
    						<span style="top:-2px;">
    							
    							<input type="text" id="searchRb" class="searchRb">
    					<button type="button" onclick="selectRecallBase()" class="searchAcBtn"><i class="i i-fangdajing "  style="position:relative;"></i></button>						
								<dhcc:thirdButton typeclass="choiceType"  value="指派" action="指派" onclick="getSysUserList(this,'2');" ></dhcc:thirdButton>       						
    							</span>
    					</div>
    					
    					<div class="info  scroll_y table_content recallBase ">
    						<dhcc:tableTag paginate="recallBaseList" property="tablerec0007" head="true"></dhcc:tableTag>
    						<div class="pageer" pageNo="1" pageSum="1" pageSize="25"></div>
			 			</div> 
			 			
    				</div>
    			</div>
    			<div class="col-md-4 col_content">
    				<div class="bgColor" style='height:100%'> 
    					<div class="title">
    						<h4>执行人员</h4>
    						
    					</div>
    					<div class="info scroll_y table_content userTaskList ">
							<dhcc:tableTag paginate="recallBaseUserList" property="tablerec0006" head="true"></dhcc:tableTag>
    						<div class="pageer" pageNo="1" pageSum="1" pageSize="25"></div>
						</div>
    				</div>
    			</div>
    		</div>
    		<!-- 弹出 begin -->
			  <div id="gridSystemModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="gridModalLabel">
			    <div class="modal-dialog" role="document">
			      <div class="modal-content">
			        <div class="modal-header">
			          <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
			          <h4 class="modal-title" id="gridModalLabel">用户列表</h4>
			        </div>
			        <div class="modal-body" >
			         <div class="bs-example" data-example-id="striped-table" >
			    		 <dhcc:formTag property="formrec0007" mode="query" />
			  		</div>
			        </div>
					<div class="modal-footer">
			          <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
			          <button type="button" class="btn btn-primary"  onclick="SelectedUser()">确认</button>
			        </div>
			      </div><!-- /.modal-content -->
			    </div><!-- /.modal-dialog -->
			  </div><!-- /.modal -->
			<!-- 弹出 end --> 

</div>


	<script type="text/javascript">
	var webPath = "${webPath}";
	var choiceTypeInit = function(obj){
		var column = obj.column,
		array = obj.array,
		inputUrl = obj.inputUrl,
		insertUrl = obj.insertUrl;
		$(".choiceType").hover(function(){
			choiceType($(this),column,array,inputUrl,insertUrl);
		});
	}
	var choiceType = function(obj,column,array,url,insertUrl){
		var $obj = $(obj);
		var top = $obj.position().top+$obj.height();
		var right =$(".bgColor").width()-($obj.position().left+$obj.width()+136);
		$(".showType").remove();
		var $show = $('<div class="showType" style=" border: 2px solid #eef2f5;'+
					  ' border-radius: 2px; color: #a7aebb;font-size: 12px;'+
					  'position: absolute; background-color: white;">'+
					  '<div class="show"><ul style=" margin: 0;padding:0;max-height:160px;overflow:auto;min-width:100px">'+
					  '</ul></div></div>');
		$show.css({"top":top+"px","right":right+"px"});
		$.each(array,function(index,val){
			var vals = val.split("-");
			var typeHtml = '<li onclick="getSysUserList(this,\''+vals[0]+'\')" style="padding: 1px 10px;">'+vals[1]+'</li>';
			$show.find("ul").append(typeHtml);
		});
		$show.find("ul li").hover(function(){
			$(this).parent().find(".hovers").removeClass("hovers");
			$(this).addClass("hovers");
		});
		$("body").append($show);
		$(".showType").hover(function(){
			$(this).show();
		},function(){
			$(this).hide();
		});
	}
	
	</script>
	
</body>
</html>