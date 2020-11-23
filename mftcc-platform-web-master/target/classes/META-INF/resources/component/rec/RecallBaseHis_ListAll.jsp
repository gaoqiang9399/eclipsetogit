<%-- [{"width":"676px","height":"274px","left":"0px","top":"0px","name":"点此拖拽","cellid":"cell_1"},{"width":"596px","height":"576px","left":"684.25px","top":"0px","name":"点此拖拽","cellid":"cell_2"},{"width":"676px","height":"294px","left":"0px","top":"281.75px","name":"点此拖拽","cellid":"cell_3"}] --%>
<%-- {"cell_1":{"cellid":"cell_1","cellname":"点此拖拽","chart":{}},"cell_2":{"cellid":"cell_2","cellname":"点此拖拽","chart":{}},"cell_3":{"cellid":"cell_3","cellname":"点此拖拽","chart":{}}} --%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/component/include/pub_view.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<%
String cellDatas = (String)request.getAttribute("cellDatas");
String blockDatas = (String)request.getAttribute("blockDatas");
%>
<!DOCTYPE html>
<html lang="zh-cn">
<head >
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<style type="text/css">
		.cover {
		    cursor: default;
		}
		</style>
		<script type="text/javascript" src="${webPath}/component/risk/layer/layer.js"></script>
		<script type="text/javascript" src="${webPath}/component/include/initScroll.js"></script>
		<link rel="stylesheet" href="${webPath}/layout/view/themes/css/initScroll.css" type="text/css"/>
		<link href="${webPath}/component/llc/css/icheck.css" rel="stylesheet">
		<script type="text/javascript">
		window.onload = function(){
			var wall = new Freewall("#freewall");
			wall.reset({
				draggable: false,
				selector: '.cell',
				animate: true,
				fixSize:0,
				gutterX: 8,
				gutterY: 8,
				onResize: function() {
					wall.fillHoles();
					wall.refresh();
				}
			});
			if('<%=blockDatas%>'!=null){
				var blockData = eval("("+'<%=blockDatas%>'+")");
				for(var key in blockData){
					var plugintype = blockData[key].plugintype;
					var $cell = $(".cell[cellid='"+key+"']");
					var info = $cell.find('.info')[0];
					if($cell.find(".cover").length>0){
						info.style.height=($cell.height()-35)+"px";
						info.style.width=($cell.width()-10)+"px";
					}else{
						info.style.height="100%";
						info.style.width="100%";
						info.style.top="0px";
						info.style.margin="0";
					}
					if(plugintype=="charts"&&typeof(blockData[key].chart)!="undefined"){
						var myChart = echarts.init(info);
						myChart.setOption(blockData[key].chart.option);
					}
				}
			}
			wall.fillHoles();
			wall.refresh();
		}
		function submitBtnOne(formId,dataParam){
			var flag = false;
			var submitUrl = $("#"+formId).attr('action');
			//dataParam.demoId = "100002";
			var dataParam = JSON.stringify(dataParam); 
			jQuery.ajax({
				url:submitUrl,
				data:{ajaxData:dataParam},
				type:"POST",
				dataType:"json",
				async:false,//关键
				success:function(data){
					if(data.flag=="error"){
						alert(data.msg);
					}else{
						alert(data.msg);
						flag = true;//必须写
					}
				},error:function(data){
					alert(  top.getMessage("FAILED_SAVE"));
				}
			});
			return flag;//必须写
		}
		</script>
	</head>
<body  style="overflow: hidden;overflow: auto\9;">
    <div class="layout">
	<div id="freewall" style="margin: 8px;" class="free-wall">
			<div class='cell' cellid='cell_1' style='top:0px; left:0px; width:676px; height: 274px; background-color:#EBEBEB' data-handle=".handle">
				<div class='cover'>
					<div class='handle'>
						<span>五级分类信息</span>
					</div>
				</div>
				 	
					<div id="claTaskList_div" class="info scroll_y tableBgNew">
						<dhcc:tableTag property="tablecla0008" paginate="claTaskLstList" head="true"></dhcc:tableTag>
					</div>
			</div>
			<div class='cell' cellid='cell_2' style='top:0px; left:684.25px; width:596px; height: 576px; background-color:#EBEBEB' data-handle=".handle">
				<div class='cover'>
					<div class='handle'>
						<span>催收信息</span>
					<!-- 	<span style="float:right;margin-right: 2px"><a href="#" class="memberAdd" onclick="inputRecallBase('<s:property value='conNo' />');">+新增</a></span> -->
					</div>
				</div>
			 	<div class="info">
				 	<div class="info scroll_y tableBgNew">
						<dhcc:tableTag property="tablerec0004" paginate="recallBaseHisList" head="true"></dhcc:tableTag>
					</div>
				</div> 
			</div>
			<div class='cell' cellid='cell_3' style='top:281.75px; left:0px; width:676px; height: 294px; background-color:#EBEBEB' data-handle=".handle">
				<div class='cover'>
					<div class='handle'>
						<span>租后检查信息</span>
					<!-- 	<span style="float:right;margin-right: 2px"><a href="#" class="memberAdd" onclick="inputLlcTaskBase('<s:property value='conNo' />');">+新增</a></span> -->
					</div>
				</div>
				
				<div class="info scroll_y tableBgNew">
						<dhcc:tableTag property="tablellc0006" paginate="llcTaskBaseList" head="true"></dhcc:tableTag>
					</div>
				
			</div>
			
			<!-- 五级分类的认定 begin-->
		 
			<div id="gridSystemModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="gridModalLabel">
			    <div class="modal-dialog" role="document">
			      <div class="modal-content">
			        <div class="modal-header">
			          <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
			          <h4 class="modal-title" id="gridModalLabel">人工认定</h4>
			        </div>
			        <div class="modal-body" style="height:150px">
			         <div class="bs-example" data-example-id="striped-table" >
					   <div class="form_content">
							<form method="post" theme="simple" name="operform" action="${webPath}/llcTaskArea/insert">
								<dhcc:formTag property="formcla0013" mode="query"/>
							</form>	
						</div>
			  		</div>
			        </div>
			        <div class="modal-footer">
			          <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
			          <button type="button" class="btn btn-primary" onclick="updatePersClaLeve()">确认</button>
			        </div>
			      </div><!-- /.modal-content -->
			    </div><!-- /.modal-dialog -->
			  </div><!-- /.modal -->
			 
			<!-- 五级分类认定 end -->
			
			
	</div>
    </div>
    <script type="text/javascript">
	function toFindsLevel(id){
		 $('.bs-example').find('input[name=id]').val(id.split("=")[1]);
		 var id = id.split("=")[1];
		 jQuery.ajax({
				url:webPath+"/claTaskLst/getByIdAjaxForPersClaResult",
				data:{id:id},
				type:"POST",
				dataType:"json",
				beforeSend:function(){  
				},success:function(data){
					if(data.flag == "success"){
					//	console.log(data.formData);
						var $content_from =  $("#gridSystemModal").find(".form_content");
						
						 $.each(data.formData,function(name,value) {
							   setFormEleValue(name, value,$content_from.find("form"));//调用公共js文件的方法表单赋值
						  });
						 $('#gridSystemModal').modal('show');
					}
				},error:function(data){
					 //$.myAlert.Alert(top.getMessage("FAILED_OPERATION"," "));
					 alert(top.getMessage("FAILED_OPERATION"," "),0);
				}
			});
	}
	/***更新人工认定结果**/
	function updatePersClaLeve(){
		var perClaResultValue=$('.bs-example').find('input[name=persClaResult]:checked').val();
		var claDescValue = $('.bs-example').find('textarea[name=claDesc]').val();
		var idValue=$('.bs-example').find('input[name=id]').val();
		var dataParam={persClaResult:perClaResultValue,claDesc:claDescValue,id:idValue};
			jQuery.ajax({
			url:webPath+"/claTaskLst/updatePersClaResult",
			data:{ajaxData:JSON.stringify(changeDateParm(dataParam))},
			type:"POST",
			dataType:"json",
			beforeSend:function(){  
			},success:function(data){
				if(data.flag == "success"){
					  location.reload();
				}
			},error:function(data){
				 //$.myAlert.Alert(top.getMessage("FAILED_OPERATION"," "));
				 alert(top.getMessage("FAILED_OPERATION"," "),0);
			}
		});
	}
	function changeDateParm(json) {
		  var jsonArray = [
		  ];
		  $.each(json, function (name, value) {
		    var jsonObj = {
		    };
		    jsonObj.name = name;
		    jsonObj.value = value;
		    jsonArray.push(jsonObj);
		  });
		  return jsonArray;
		}
	/**执行完成操作**/
	function updateSts(idStr){
		alert("是否继续进行此操作!",2,function(){
			var idValue=idStr.split("&")[0].split("=")[1];
			var claResultValue=idStr.split("&")[1].split("=")[1];
			var conNoValue=idStr.split("&")[2].split("=")[1];
			var dataParam={id:idValue,claResult:claResultValue,conNo:conNoValue};
			jQuery.ajax({
				url:webPath+"/claTaskLst/updateTaskSts",
				data:{ajaxData:JSON.stringify(changeDateParm(dataParam))},
				type:"POST",
				dataType:"json",
				beforeSend:function(){  
				},success:function(data){
					if(data.flag == "success"){
						  location.reload();
					}
				},error:function(data){
					 //$.myAlert.Alert(top.getMessage("FAILED_OPERATION"," "));
					 alert(top.getMessage("FAILED_OPERATION"," "),0);
				}
			});
		});
		/* $.myAlert.Confirm("是否继续进行此操作!","",function(){
		var idValue=idStr.split("&")[0].split("=")[1];
		var claResultValue=idStr.split("&")[1].split("=")[1];
		var conNoValue=idStr.split("&")[2].split("=")[1];
		var dataParam={id:idValue,claResult:claResultValue,conNo:conNoValue};
		jQuery.ajax({
			url:webPath+"/claTaskLst/updateTaskSts",
			data:{ajaxData:JSON.stringify(changeDateParm(dataParam))},
			type:"POST",
			dataType:"json",
			beforeSend:function(){  
			},success:function(data){
				if(data.flag == "success"){
					  location.reload();
				}
			},error:function(data){
				 $.myAlert.Alert(top.getMessage("FAILED_OPERATION"," "));
			}
		});
		}); */
	}

	/***页面加载时候处理列表**/
	$(function(){
		var obj=$("#claTaskList_div").find("#tab");
		$(obj).find("tr td:nth-child(5)").each(function () { 
			var newItemDescVal= $(this).text(); 
			var newItemDescValEasy =  "";
			if(newItemDescVal.length>6) {
			newItemDescValEasy = newItemDescVal.substr(0,6)+"...";
			}else{
				newItemDescValEasy=newItemDescVal;
			}
			var itemDescLabel = $("<label style=display:inline-block;font-weight:normal;width:100px; onmouseover=openTip(this,'"+newItemDescVal+"')>"+newItemDescValEasy+"</label>");
			$(this).empty().append(itemDescLabel);
		});
		
	});
	function openTip(obj,mes) {
		layer.tips(mes, $(obj),{
			tips: [4, '#73C0EC']
		});
	}
	function getHisView(obj,url){
		var hisNo = url.split("?")[1].split("&")[0].split("=")[1];
		window.top.window.showDialog(webPath+"/recallBaseHis/getHisView?hisNo="+hisNo,"催收详情",80,90);
	}
	/***增加人工检查功能**/
	function inputLlcTaskBase(conNo){
		var url=webPath+"/llcTaskBase/inputInfo?conNo="+conNo;
	window.top.window.createShowDialog(url,'新增租后检查任务',70,85,"iframepage");
	}
	
	/**检查文档**/
	function getCheckingInfo(url){
		window.top.window.showDialog(url,'租后任务检查',90,100,"iframepage");
	}
	
	/***增加催收功能**/
	function inputRecallBase(conNo){
		var url=webPath+"/recallBase/getInputInfo?conNo="+conNo;
	window.top.window.createShowDialog(url,'新增催收任务',90,100,"iframepage");
	}
	</script>
</body>
</html>