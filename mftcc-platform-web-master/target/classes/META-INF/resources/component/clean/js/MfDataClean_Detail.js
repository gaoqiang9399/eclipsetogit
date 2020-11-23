;
var DataClean = function(window, $) {
	//初始化
	var _init = function(){
//		$(".scroll-content").mCustomScrollbar({
//			advanced:{
//				updateOnContentResize:true
//			}
//		});
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				updateOnContentResize : true
			}
		});
		var data = headImg;
		if (ifUploadHead != "1") {
			data = "themes/factor/images/" + headImg;
		}
		data = encodeURIComponent(encodeURIComponent(data));
		document.getElementById('headImgShow').src = webPath+ "/uploadFile/viewUploadImageDetail?srcPath=" + data+ "&fileName=user2.jpg";
		headImgShowSrc = webPath+"/uploadFile/viewUploadImageDetail.action?srcPath=" + data+ "&fileName=user2.jpg";
		
		//选中当前的那笔业务
//		$("span[appid="+appId+"]").addClass("curChecked");
		//复选框点击事件
		_bindCheckBoxOnClick();
		//客户有封档业务时，客户不允许勾选操作
		if($(".bus-list .fengdang").length>0){
			$("#cusCheck").addClass("boxDisabled");
			$("#cusCheck").unbind();
			//如果当前数据已经封挡，项目统计笔数设为0
			if($("#item"+appId +" .fengdang").length>0){
				$("#checkedItems").text("0");
			}
			
		}
		//当客户有一笔业务且未封档时，客户与业务都是选中状态
		if(parseInt($("#allItems").text())==1 && $(".bus-list .fengdang").length==0){
			$("#cusCheck").addClass("curChecked");
			$("#cusItems").text("1");
			
		}
		
	};
	//复选框点击事件
	var _bindCheckBoxOnClick = function(){
		$(".checkbox-span").bind("click", function(event){
			switch ($(this).attr("id")) {
				case "cusCheck":
					_doCusCheck(this);
					break;
				case "busCheck":
					_doBusCheck(this);
					break;
				default:
					break;
			}
		});
	};
	
	//客户模块复选框处理
	var _doCusCheck = function(obj){
		if($(obj).hasClass("curChecked")){
			$(".checkbox-span").removeClass("curChecked");
			$("#checkedItems").text("0");
			$("#cusItems").text("0");
		}else{
			$(".checkbox-span").addClass("curChecked");
			var checkedItems=$(".bus-list .curChecked").length;
			$("#checkedItems").text(checkedItems);
			$("#cusItems").text("1");
		}
	};
	//业务模块复选框处理
	var _doBusCheck = function(obj){
        var checkedItems;
		if($(obj).hasClass("curChecked")){
			$(obj).removeClass("curChecked");
			checkedItems =parseInt($("#checkedItems").text());
			$("#checkedItems").text(checkedItems-1);
		}else{
			$(obj).addClass("curChecked");
			checkedItems =parseInt($("#checkedItems").text());
			$("#checkedItems").text(checkedItems+1);
		}
	};
	
	//验证客户能够被清理
    function verifyCusCleanFlag(){
		var flag = true;
		$.ajax({
			url:webPath+"/mfDataClean/verifyCusCleanFlagAjax",
			type:"POST",
			dataType:"json",
			async: false,
			data:{cusNo:cusNo},
			success:function(data){
				if(data.flag == "success"){
					if(data.cleanFlag=="0"){
						alert(data.msg,0);
						$("#cusCheck").removeClass("curChecked");
						$("#cusItems").text("0");
						flag = false;
					}
				}else{
					alert(data.msg,0);
				}
			},error:function(data){
			 	
			}
		});
		return flag;
	 };
	
	//清理功能
	var _dataClean = function(){
		var datas = [];
		var count=0;
		//确定是否清理客户信息cleanFlag：true--清理，false--不清理
		var cleanFlag = $("#cusCheck").hasClass("curChecked");
		$(".bus-list .curChecked").each(function(index){
			//获取业务编号
			var entity = {};
			entity.appId = $(this).attr("appid");
			entity.pactId = $(this).attr("pactid");
			entity.pactNo = $(this).attr("pactno");
			entity.wkfAppId = $(this).attr("wkfappid");
			datas.push(entity);
			count++;
		});
		if(count==0 && !cleanFlag){
			alert(top.getMessage("FIRST_SELECT_ITEM"),4);
	   	 	return false;
		}else{
			if(cleanFlag){//如果要清理客户数据，先判断客户的押品是否被别的项目引用
				if(!verifyCusCleanFlag()){
						return false;
				}else{
					alert(top.getMessage("CONFIRM_CLEAN"),2,function(){
						//逐条清理勾选的业务信息
						_cleanBus(cusNo,JSON.stringify(datas),cleanFlag,count);
					});
				}
			}else{
				alert(top.getMessage("CONFIRM_CLEAN"),2,function(){
					_cleanBus(cusNo,JSON.stringify(datas),cleanFlag,count);
				});
			}
		}
	};
	
	//清理业务信息
	var _cleanBus = function(cusNo,ajaxData,cleanFlag,count){
		$.ajax({
			url:webPath+"/mfDataClean/deleteBusAjax",
			type:"POST",
			dataType:"json",
			data:{cusNo:cusNo,ajaxData:ajaxData},
			success:function(data){
				if(data.flag == "success"){
					$(".bus-list .curChecked").each(function(index){
						//单条业务清理完成之后处理页面效果和数据
						_busCleanFinished(this);
					});
					if(cleanFlag){
						//如果需要清理客户信息，清理完业务信息再清理客户信息
						_cleanCus(cusNo,count);
					}
				}else{
					alert(data.msg,0);
				}
			}
		});
	};
	
	//单条业务清理完成之后处理页面效果和数据
	var _busCleanFinished  = function(obj){
		var tmpid = $(obj).attr("appid");
		$("#item"+tmpid).slideUp("slow", function () {
			$(this).remove();
			$(".bus-list .list-div").each(function(index1){
				if(index1%2!=0){
					if(!$(this).hasClass("even-div")){
						$(this).addClass("even-div");
					}
				}else{
					if($(this).hasClass("even-div")){
						$(this).removeClass("even-div");
					}
				}
			});
		});
		var checkedItems =parseInt($("#checkedItems").text()); 
		$("#checkedItems").text(checkedItems-1);
		$("#allItems").text(parseInt($("#allItems").text())-1)
	};
	
	//清理客户信息
	var _cleanCus = function(cusNo,count){
		$.ajax({
			url:webPath+"/mfDataClean/deleteCusAjax",
			type:"POST",
			dataType:"json",
			data:{cusNo:cusNo},
			success:function(data){
				if(data.flag == "success"){
					$(".scroll-content").remove();
					$(".formRowCenter").remove();
					$("#cleanBusCnt").text(count);
					$("#successDiv").show("fast");
				}else{
					alert(data.msg,0);
				}
			},error:function(data){
			 	
			}
		});
	};
	
	
	return {
		init : _init,
		dataClean:_dataClean
	};
}(window, jQuery);