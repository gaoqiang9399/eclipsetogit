<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>上传银行还款</title>
		<script type="text/javascript" src="${webPath}/component/include/calcUtil.js"></script>
		<script type="text/javascript" src="${webPath}/UIplug/webuploader/js/webuploader.js"></script>	
		<link rel="stylesheet" href="${webPath}/UIplug/webuploader/css/webuploader.css?v=${cssJsVersion}">
		<link rel="stylesheet" href="${webPath}/component/pfs/css/CusFincMainList.css?v=${cssJsVersion}"/> 
		<script type="text/javascript">
			var cusNo = '${cusNo}';
			var pactNo = '${pactNo}';
			var pactId = '${pactId}';
			var cusName = '${cusName}';
			var appName = '${appName}';
			var fincId = '${fincId}';
			var putoutAmt = '${putoutAmt}';
			
			$(function() {
				myCustomScrollbarForForm({
					obj:".scroll-content"
				});
				
				$("input[name=cusNo]").val(cusNo);
				$("input[name=cusName]").val(cusName);
				$("input[name=pactNo]").val(pactNo);
				$("input[name=pactId]").val(pactId);
				$("input[name=fincId]").val(fincId);
				$("input[name=appName]").val(appName);
				$("input[name=putoutAmt]").val(putoutAmt);

				$(".savePlanList").attr("disabled",true);
				//$("#uploader").attr("disabled",true);
				
				//处理上传过的财务报表列表
				//上传操作	
				var uploader = WebUploader.create({
					auto : true,
					// swf文件路径
					swf : webPath+'/UIplug/webuploader/js/Uploader.swf',
					// 文件接收服务端。
					server : webPath+'/mfFundChannelRepayPlan/uploadAjax?cusNo='+cusNo,
					// 选择文件的按钮。可选。
					// 内部根据当前运行是创建，可能是input元素，也可能是flash.
					pick : '#picker',
					// 不压缩image, 默认如果是jpeg，文件上传前会压缩一把再上传！
					fileVal : 'cusFinUpload',
					resize : false,
					fileNumLimit : 1,   //验证文件总数量, 超出则不允许加入队列
					accept : {
						title : 'Excl',
						extensions : 'xls,xlsx',
						mimeTypes : '.xls,.xlsx'
					}
				});
				//当文件被加入队列之前触 此事件的handler返回值为false，则此文件不会被添加进入队列
				uploader.on('beforeFileQueued', function(file) {
					//$("#showMsg").empty();
					$("input[name=uploadFile]").val(file.name);
				});
				//当文件被加入队列以后触发
				uploader.on('fileQueued', function(file) {
				});
				//当文件被移除队列后触发
				uploader.on('fileDequeued', function(file) {
					$("#showInfo-div").empty();
					$("#"+file.id).remove();
				});
				//上传过程中触发，携带上传进度
				uploader.on('uploadProgress', function(file,percentage) {
					LoadingAnimate.start();
				    var $li = $( '#'+file.id ),
				        $percent = $li.find('.progress .progress-bar');
				    // 避免重复创建
				    if (!$percent.length ) {
				        $percent = $('<div class="progress progress-striped active">' +
				          '<div class="progress-bar" role="progressbar" style="width: 0%">' +
				          '</div>' +
				        '</div>').appendTo( $li ).find('.progress-bar');
				    }
				    $percent.css( 'width', percentage * 100 + '%' );
				});
				//当文件上传成功时触发
				uploader.on('uploadSuccess', function(file,data) {
					LoadingAnimate.stop();
					uploader.removeFile(file);
				    if(data.flag=="success"){
				    	var mfRepayPlans = data.mfRepayPlans;
				    	//$("#dataMsg").html(data.dataMsg);
				    	$(".savePlanList").attr("disabled",false);
				    	//$("#savePlanList").removeAttr("disabled");
				    	$("#plan_content").html("");
				    	$("#plan_content").html(data.htmlStr);
				    }else if(data.flag=="error"){
				    	LoadingAnimate.stop();
				    	top.isUpload = false; //财务报表上传成标志
				    	//$("#showMsg").empty();
				    	//$("#showMsg").html(data.resMsg);
				    	uploader.removeFile(file.id);
				    	$('#'+file.id).find('p.state').text('上传出错');
				    }
				});
				//当文件上传出错时触发
				uploader.on('uploadError', function(file) {
				    $('#'+file.id ).find('p.state').text('上传出错');
				});
				//不管成功或者失败，文件上传完成时触发	
				uploader.on('uploadComplete', function(file) {
				    $( '#'+file.id ).find('.progress').fadeOut();
				});
			});

			var savePlanList = function(){
				//console.log("savePlanList");
				var url = webPath+"/mfFundChannelRepayPlan/insertNewPlanAjax";
				var jsonArray = [];
				var num=0;
				$("#plan_content").find("tr").each(function(){
					if(num!=0){
						var tdArr = $(this).children();
				        var json = new Object;
				        json.term = tdArr.eq(0).html();//期号
				        json.beginDate = tdArr.eq(1).html();//开始时间
				        json.endDate = tdArr.eq(2).html();//结束时间
				        json.repayPrcp = tdArr.eq(3).html();//每期本金（元）
				        json.repayIntst = tdArr.eq(4).html();//每期利息（元）
				        json.repayPrcpIntstSum = tdArr.eq(5).html();//期供金额（元）
				        json.repayPrcpBalAfter = tdArr.eq(6).html();//剩余本金（元）
				        jsonArray.push(json);
					}
			        num++;
			    }); 
				//var formParm = JSON.stringify($('#repayPlanTrial').serializeArray());
				var dataParam = JSON.stringify(jsonArray);
				LoadingAnimate.start();
				$.ajax({
					url : url,
					data : {
						ajaxData : dataParam,
						//formData : formParm
						cusNo : cusNo,
						cusName : cusName,
						pactId : pactId,
						putoutAmt : putoutAmt
					},
					type : 'post',
					dataType : 'json',
					success : function(data) {
						LoadingAnimate.stop();
						if (data.flag == "success") {	
							window.top.alert(data.msg, 1);
							top.flag=true;
							myclose_click();
						} else {
							window.top.alert(data.msg, 0);
						}
					},
					error : function() {
						loadingAnimate.stop();
					}
				});
			};
			
		</script>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content" style="height:100%">
				<div class="col-md-8 col-md-offset-2 column margin_top_20">
					<div class="row clearfix">
						<div class="col-md-12 column">
							<div class="table_content" style="width:100%;margin-left:-15px;">
								<div id="changediv" style="position: relative;margin-bottom:10px;margin-top:5px;" align="center">
							    </div>
						    </div>
						    <div id="plan_content" class="table_content" style="width:100%;margin-left:-15px;margin-bottom:60px;">
				
							</div>
						</div>
					</div>
				</div>
	   		</div>
			<div class="formRowCenter" style="text-align: left;position: fixed">
				<div class="col-xs-2" id="newParm-div" style="margin-left:30%;">
					<div id="uploader" class="wu-example">
						<div id="thelist" class="cb-upload-div input-group input-group-lg">
							<input name="uploadFile" readonly="readonly" type="text" class="form-control" style="display:none;">
							<span style="width:0px;height:0px;padding:9px 17px;font-size:15px;" id="picker" readonly="readonly" class="input-group-addon pointer">上传还款计划表</span>
						</div>
					</div>
					<!-- <div style="color: blue; margin-bottom: 30px;" id="dataMsg"></div> -->
				</div>		
	   			<dhcc:thirdButton value="保存还款计划" typeclass="savePlanList" action="保存还款计划" onclick="savePlanList();"></dhcc:thirdButton>
	   			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose_click();"></dhcc:thirdButton>
	   		</div>
   		</div>
	</body>
</html>
