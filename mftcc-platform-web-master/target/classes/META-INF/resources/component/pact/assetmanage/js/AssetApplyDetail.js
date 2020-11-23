;
var AssetApplyDetail = function(window, $) {
	var _init = function() {
		$("body").mCustomScrollbar(
				{
					advanced : {
						updateOnContentResize : true
					},
					callbacks : {//解决单字段编辑输入框位置随滚动条变化问题
						whileScrolling : function() {
							if ($(".changeval").length > 0) {
								$(".changeval").css(
										"top",
										parseInt($(".changeval").data("top"))
												+ parseInt(this.mcs.top)
												- $(".changeval").data("msctop"));
							}
						}
					}
				}); 
		$(".content .fieldshow").css("max-width","20em");
		if(wkfAppId != null && wkfAppId != ""){
			showWkfFlow($("#wj-modeler1"),wkfAppId);
			$(".next-div").removeClass("hide");
			$(".next-div").addClass("show");
			$("#showWkf").removeClass("hide");
			$("#showWkf").addClass("show");
		}
		getNextBusPoint();
		showApproveHis();
	};

	// 获取下一个业务节点 获取当前业务节点的参数信息
	function getNextBusPoint(){
		 $.ajax({
				url : "/mfAssetManage/getTaskInfoAjax",
				type : 'post',
				data : {cusNo:cusNo,assetId:assetId},
				dataType : 'json',
				success : function(data) {
					var wkfFlag = data.wkfFlag;
					var status=data.mfAssetManage.applyStatus;
					var assetId=data.mfAssetManage.assetId;
					var title=data.title;
					var url=data.url;
					var nodeName=data.nodeName;
					var approveInfo="";
					var approveNodeName=null;
					var approvePartName=null;
					var busPointInfo=null;
					if(status == "1"){
						approveNodeName=data.mfAssetManage.approveNodeName;
						approvePartName=data.mfAssetManage.approvePartName;
						if(approvePartName!=""&&approvePartName!=null){
							var nodeNameArr = approveNodeName.split(",");
							var partNameArr = approvePartName.split(",");
							var tipStr = "";
							for(var i=0;i<nodeNameArr.length;i++){
								tipStr = tipStr + title +"岗位的【" + partNameArr[i]+"】,";
							}
							tipStr = tipStr.substring(0, tipStr.length-1);
							approveInfo="正在"+tipStr+"审批";
						}else{
							approveInfo="正在"+approveNodeName+"岗位审批";
						}
						if(approveInfo.length>40){
							//提示内容长度大于40时，截取展示并添加鼠标title提示。
							var approveTitleInfo=approveInfo;
							approveInfo=approveInfo.substring(0, 40)+"...";
							busPointInfo = "<span title="+approveTitleInfo+">申请已提交，"+approveInfo+"</span>";
						}else{
							busPointInfo = "<span>申请已提交，"+approveInfo+"</span>";
						}
					}else if(status == "3"){
						busPointInfo = "<span>申请已被否决</span>";
					}
					else if(status == "2"){
						busPointInfo = "<span>审核通过</span>";
					}
					$(".block-next").empty();
					$(".block-next").append(busPointInfo);
				}
			});
	};
 
 	//跳转至下一业务节点
	function toNextBusPoint(url, title, nodeName) {
			top.flag = false;
			if (nodeName == "1527922241998") { //申请确认
				top.window.openBigForm(url+"?wkfAppId="+wkfAppId+"&assetId="+assetId, "申请确认", function(){
						if(top.flag){
							$("#wj-modeler1").html("");
							getNextBusPoint();
							showWkfFlow($("#wj-modeler1"),wkfFinId);
						}
				});
			}else if(nodeName=="invest_plan"){// 收益计划 
				top.window.openBigForm(url+"&wkfFinId="+wkfFinId+"&busPactNo="+busPactNo, "收益计划", function(){
					if(top.flag){
							$("#wj-modeler1").html("");
							getNextBusPoint();
							showWkfFlow($("#wj-modeler1"),wkfFinId);
						}
				});
			} 
		};
		var _submitProgress = function(){
			window.top.alert("是否确认提交？",2,function(){
				//var dataParam = JSON.stringify($(obj).serializeArray());
				jQuery.ajax({
					url:"/mfAssetManage/applyUpdate",
					data:{assetId:assetId},
					type:"POST",
					dataType:"json",
					beforeSend:function(){ 
						LoadingAnimate.start();
					},success:function(data){
						if(data.flag == "success"){
							top.flag = true;
							window.location.reload();
							$(".next-div").removeClass("hide");
							$(".next-div").addClass("show");
							$("#showWkf").removeClass("hide");
							$("#showWkf").addClass("show");
							showWkfFlow($("#wj-modeler1"),data.mfAssetManage.wkfAppId);
							window.top.alert(data.msg,3);
							//window.location.reload();
							myclose_click();
							//$(top.window.document).find("#showDialog .close").click();
						}else if(data.flag == "error"){
							 alert(data.msg,0);
						}
					},error:function(data){
						alert(top.getMessage("FAILED_OPERATION"," "),0);
					},complete:function(){
						LoadingAnimate.stop();
					}
				});
			})
		};
		//展示审批历史
		function showApproveHis() {
			//获得审批历史信息
			showWkfFlowVertical($("#wj-modeler2"), wkfAppId, "33","asset_dispose_approval");
			$("#leaveApproveHis").show();
		}
	return {
		init:_init,
		submitProgress:_submitProgress
	};
}(window, jQuery);
