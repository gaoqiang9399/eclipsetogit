<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>

<!DOCTYPE html>
<html lang="zh-cn">
	<head >
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	</head>
<body style="overflow-y: hidden;">
	<dhcc:markPoint markPointName="MfSysFeeItem_List"/>
	<div class="container">
		<div class="row clearfix bg-white tabCont">
			<div class="col-md-12 column">
				<div class="search-div">
					<button type="button" class="btn btn-primary pull-left" onclick="newFeeItem(webPath+'/mfSysFeeItem/input?feeStdNo=${feeStdNo}');">新增</button>
					<ol class="breadcrumb pull-left">
						<li><a href="${webPath}/sysDevView/setC?setType=model">模型设置</a></li>
						<li><a href="${webPath}/MfSysFeeStdAction_getListPage.action">费用设置</a></li>
						<li class="active">费用模型配置</li>
					</ol>
				</div>
			</div>
		</div>
		<div class="row clearfix">
			<div class="col-md-12 column">
				<div id="content" class="table_content"  style="height: auto;">
						<dhcc:tableTag property="tablefeeitem0001" paginate="mfSysFeeItemList" head="true"></dhcc:tableTag>
				</div>
			</div>
		</div>
	</div>
</body>	
   <script type="text/javascript">
    var options;
    function initOptions(){
    	options = $("select[name=standard]").find("option");
		makeOptionsJQ(options, '1');
		$("select[name=standard]").val("1");
    };
   /*  function getStandard(){
		var takeNode = $("select[name=takeNode]").val();  
		if (takeNode == "1") {
			makeOptionsJQ(options, '1');
			$("select[name=standard]").val("1");
		} else if (takeNode == "2") {
			makeOptionsJQ(options, '1,2,3');
			$("select[name=standard]").val("1");
		}else if (takeNode == "3") {
			makeOptionsJQ(options, '1,4');
			$("select[name=standard]").val("1");
		}else if (takeNode == "4") {
			makeOptionsJQ(options, '1,5');
			$("select[name=standard]").val("1");
		}else if (takeNode == "5") {
			makeOptionsJQ(options, '1,6');
			$("select[name=standard]").val("1");
		}
	}; */
	function ajaxGetByIdThis(obj,url){
		top.updateFlag = false;
    	top.tableId = 'tablefeeitem0001';
    	top.tableData = null;
//     	top.window.createShowDialog(url,'修改费用项','60','60',closeCallBackUpdate);
    	top.window.openBigForm(url,'修改费用项',closeCallBackUpdate);
		/* var $obj = $(obj);
		if(ajaxUrl!==undefined&&ajaxUrl!=null&&ajaxUrl!=""){
			jQuery.ajax({
				url:ajaxUrl,
				type:"POST",
				dataType:"json",
				beforeSend:function(){  
				},success:function(data){
					if(data.flag == "success"){
						$obj.parents(".content_table").find(".content_form").find("form").attr("action","");
						  var $content_from =  $(".content_table").find(".content_form");
					  	 $.each(data.formData,function(name,value) {
						   	setFormEleValue(name, value,$content_from.find("form"));
						  });
					  	  $obj.parents("table").find(".selected").removeClass("selected");
					  	  if( $obj.parents("tr").length>0){
					  	  	$obj.parents("tr").addClass("selected");
					  	  }else{
					  	  	$obj.addClass("selected");
					  	  }
						  $content_from.show();
						  initIfreamHeight();
						  initOptions1();
					}else{
						 $.myAlert.Alert(top.getMessage("ERROR_SELECT"));
					}
				},error:function(data){
					 $.myAlert.Alert(top.getMessage("ERROR_SELECT"));
				}
			});
		}else{
			$.myAlert.Alert("请检查列表链接");
		}
		 if(window.event){
	        //e.returnValue=false;//阻止自身行为
	        window.event.cancelBubble=true;//阻止冒泡
	     }else if(arguments.callee.caller.arguments[0].preventDefault){
	        //e.preventDefault();//阻止自身行为
	        arguments.callee.caller.arguments[0].stopPropagation();//阻止冒泡
	     } */
		
	};
	 function closeCallBackUpdate(){
	    	if(top.updateFlag){
	    		var tableData = top.tableData;
	    		if(tableData !=undefined && tableData!=null){
					var tableHtml = $(tableData).find("tbody").html();
				 	$("table").find("tbody").html(tableHtml);
				 }
	    	}
	    };
	/* function initOptions1(){
		options = $("select[name=standard]").find("option");
		var takeNode = $("select[name=takeNode]").val();  
		if (takeNode == "1") {
			makeOptionsJQ(options, '1');
			$("select[name=standard]").val("1");
		} else if (takeNode == "2") {
			makeOptionsJQ(options, '1,2,3');
			$("select[name=standard]").val("1");
		}else if (takeNode == "3") {
			makeOptionsJQ(options, '1,4');
			$("select[name=standard]").val("1");
		}else if (takeNode == "4") {
			makeOptionsJQ(options, '1,5');
			$("select[name=standard]").val("1");
		}else if (takeNode == "5") {
			makeOptionsJQ(options, '1,6');
			$("select[name=standard]").val("1");
		}
	}; */
    function newFeeItem(url){
    	top.addFlag = false;
    	top.tableData = null;
    	top.tableId = 'tablefeeitem0001';
//     	top.window.createShowDialog(url,'新增费用项','60','60',closeCallBack);
    	top.window.openBigForm(url,'新增费用项',closeCallBack);
    };
    function closeCallBack(){
    	if(top.addFlag){
    		var tableData = top.tableData;
    		if(tableData !=undefined && tableData!=null){
				var tableHtml = $(tableData).find("tbody").html();
			 	$("table").find("tbody").html(tableHtml);
			 }
    	}
    };
   </script>

</html>