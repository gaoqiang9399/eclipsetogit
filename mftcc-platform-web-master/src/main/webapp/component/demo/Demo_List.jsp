<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/pub_view.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>列表</title>
		<script type="text/javascript" src="${webPath}/UIplug/rcswitcher-master/js/rcswitcher.min.js"></script>
		<link rel="stylesheet" href="${webPath}/UIplug/rcswitcher-master/css/rcswitcher.min.css" type="text/css" />
		<script type="text/javascript" src="${webPath}/component/include/myRcswitcher.js"></script>
		<link rel="stylesheet" href="${webPath}/component/include/RoleUrl.css" type="text/css" />
		<script type="text/javascript" src="${webPath}/component/include/RoleUrl.js"></script>
		<link rel="stylesheet" href="${webPath}/UIplug/webuploader/css/webuploader.css" type="text/css" />
		<script type="text/javascript" src="${webPath}/UIplug/webuploader/js/webuploader.js"></script>
		<script type="text/javascript" >
			 $(function(){
				 myCustomScrollbar({
			    	obj:"#content",//页面内容绑定的id
			    	url:webPath+"/demo/findByPageAjax",//列表数据查询的url
			    	updateByOneurl:webPath+"/demo/updateAjaxByOne",//更新单字段URL
			    	tableId:"tabledemo0001",//列表数据查询的table编号
			    	tableType:"thirdTableTag",//table所需解析标签的种类
			    	myFilter:false, //是否有我的筛选(列表列动态切换)
			    //	pageSize:30,//加载默认行数(不填为系统默认行数)
			    	data:{},//指定参数
			    	callback:function(){
			    		//$("table").tableRcswitcher({name:"demoPosition"});
			    	}//方法执行完回调函数（取完数据做处理的时候）
			    });
			 });
			 function openView(obj,url){
			 	window.top.openBigForm(url,"DEMO");
			 } 
			 
			 
		</script>
		<style>
		    #pills{
		       position:relative;
		    }
	     	#picker .webuploader-pick {
			    padding: 6px 12px;
			    display: block;
			    width: 120px;
			    height: 30px;
			}
		</style>
	</head>
	<body class="body_bg">
		<!--标记点 未后退准备-->
		<dhcc:markPoint markPointName="demo_list"/>
		<div>
			<div style="vertical-align: bottom;display: block;" class="tabCont">
				<strong>用户信息列表</strong>
				<div class="search-group">
					<!--我的筛选选中后的显示块-->
					<div class="search-lable" id="pills">
					    <dhcc:thirdButton value="测试URL" action="测试URL"
							onclick="getRoleUrl('demo0001')" >
						</dhcc:thirdButton>
						<div id="role-ul-content">
						    <ul id="url-ul"></ul>
						</div>
						<!-- 按钮触发模态框 -->
						<input data-toggle="modal" data-target="#myModal" type="button" value="文件上传" />
						<input type="button" value="测试按钮" onclick="test()"/>
						<input type="button" value="打开关联关系图" onclick="window.top.openBigForm('${webPath}/component/cus/relation/relation.jsp?jumpLink=/component/cus/relation/json/data3.json&keyNo=a474b83164dc5917cc93d8ef41beffc0')">
						<dhcc:thirdButton value="新增" action="新增"
							onclick="window.top.openBigForm('${webPath}/demo/input')"></dhcc:thirdButton>
						<!--我的筛选按钮-->
						<div class="filter-btn-group">
							<!--自定义查询输入框-->
							<input placeholder="智能搜索" id="filter_in_input" class="filter_in_input" type="text" />
							<div class="filter-sub-group">
								<button id="filter_btn_search" class="filter_btn_search"  type="button" ><i class="i i-fangdajing"></i></button>
						 		<button id="fiter_ctrl_btn"  class="filter_btn_myFilter" type="button"  ><i class="i i-jiantou7"></i></button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!--页面显示区域-->
		<div id="content" class="table_content"  style="height: auto;">
			<!--待定是否放置自定义table标签?-->
		</div>
	    <%@ include file="/component/include/PmsUserFilter.jsp"%>
	    <!-- 模态框（Modal） -->
	    <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	   		 <div class="modal-dialog">
	        	<div class="modal-content">
		            <div class="modal-header">
		                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
		                <h4 class="modal-title" id="myModalLabel">文件上传</h4>
		            </div>
		            <div class="modal-body">
			            <div id="uploader" class="wu-example" >
						    <!--用来存放文件信息-->
						    <div id="thelist" class="uploader-list"></div>
						    <div class="btns">
						        <div id="picker">选择文件</div>
						    </div>
						</div>
		            </div>
		            <div class="modal-footer">
		                <button id="ctlBtn"  type="button" class="btn btn-primary">上传</button>
		                <button onclick="uploader.reset();" type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
		            </div>
		        </div><!-- /.modal-content -->
		    </div><!-- /.modal -->
	    </div>
	</body>	
	<script type="text/javascript">filter_dic =[{"optCode":"demoTime","optName":"时间","dicType":"date"},{"optCode":"demoAmt","optName":"金额","dicType":"num"},{"optCode":"demoOther","optName":"其他","dicType":"val"}];</script>
	<script type="text/javascript">
          function test(){
          	$.ajax({
				type: "get",
				url:webPath+"/sysGlobal/getAllFormXmlAjax",
				datatype: "json",
				async: false,
				success: function(data) {
					console.log(data);
				},
				error: function() {
					console.log("数据源加载失败");
				}
			});
          }
          $(function(){
        	  var uploader = WebUploader.create({

        		    // swf文件路径
        		    swf: "${webPath}" + '/UIplug/webuploader/js/Uploader.swf',

        		    // 文件接收服务端。
        		    server: '${webPath}/wxSysUpload/uploadFile',

        		    // 选择文件的按钮。可选。
        		    // 内部根据当前运行是创建，可能是input元素，也可能是flash.
        		    pick: '#picker',

        		    // 不压缩image, 默认如果是jpeg，文件上传前会压缩一把再上传！
        		    resize: false
        		});
	        	// 当有文件被添加进队列的时候
	        	  uploader.on( 'fileQueued', function( file ) {
	        	      $("#thelist").append( '<div id="' + file.id + '" class="item">' +
	        	          '<h4 class="info">' + file.name + '</h4>' +
	        	          '<p class="state">等待上传...</p>' +
	        	      '</div>' );
	        	  });
	        	// 文件上传过程中创建进度条实时显示。
	        	  uploader.on( 'uploadProgress', function( file, percentage ) {
	        	      var $li = $( '#'+file.id ),
	        	          $percent = $li.find('.progress .progress-bar');

	        	      // 避免重复创建
	        	      if ( !$percent.length ) {
	        	          $percent = $('<div class="progress progress-striped active">' +
	        	            '<div class="progress-bar" role="progressbar" style="width: 0%">' +
	        	            '</div>' +
	        	          '</div>').appendTo( $li ).find('.progress-bar');
	        	      }

	        	      $li.find('p.state').text('上传中');

	        	      $percent.css( 'width', percentage * 100 + '%' );
	        	  });
	        	  uploader.on( 'uploadSuccess', function( file ) {
	        		    $( '#'+file.id ).find('p.state').text('已上传');
	        		});

	        		uploader.on( 'uploadError', function( file ) {
	        		    $( '#'+file.id ).find('p.state').text('上传出错');
	        		});

	        		uploader.on( 'uploadComplete', function( file ) {
	        		    $( '#'+file.id ).find('.progress').fadeOut();
	        		});
	        		$("#ctlBtn").bind("click",function(){
	        			uploader.upload();
	        		});
          });
	</script>
</html>
