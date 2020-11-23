var myCustomScrollbar = function(options){
	var $conentObj = $(options.obj);
	$conentObj.height($(document).height()-50-30);
	var url = options.url;
	var ajaxType = "init";
	var tableId = options.tableId;
	var tableType = options.tableType;
	var postData = {};
	window.updateTableData = function(){
		if(ajaxType=="init"){
			postData.ajaxData = getFilterValArr();
			postData.pageSize = 25;
		}
		postData.tableId = tableId;
		postData.tableType = tableType;
		$.ajax({
	 		type:"post",
	 		url:url,
	 		data:postData,
	 		async:false,
	 		beforeSend:function(){
				if(ajaxType=="update"){
					$(".footer_loader").show();
				}
			},success:function(data){
	 			if(ajaxType=="init"){
	 				if(data.flag=="error"){
	 					alert(data.msg);
	 				}else{
	 					if($conentObj.find("table tbody").html()===undefined){
		 					$conentObj.append(data.ipage.result);
		 				}else{
		 					$conentObj.find("table tbody").html($(data.ipage.result).find("tbody").html());
		 				}
		 				setPageer($conentObj,data.ipage);
			 			$conentObj.mCustomScrollbar("update");
	 				}
	 			}else if(ajaxType=="update"){
	 				showMessageTitle("加载第"+postData.pageNo+"页");
		 			$conentObj.find("table tbody").append($(data.ipage.result).find("tbody").html());
					setPageer($conentObj,data.ipage);
		 			$conentObj.mCustomScrollbar("update");
		 			$(".footer_loader").fadeOut(1000);
	 			}
	 			showTable();
	 			initHover();
	 		}
	 	});
	}
	/**
	 * 滚动条初始化
	 */
	var init = function(){
		$conentObj.mCustomScrollbar({
	        scrollButtons:{
	            enable:true,
	            autoHideScrollbar: true,
	            scrollAmount:200
	        },advanced:{
	            autoExpandHorizontalScroll:true,
	            updateOnBrowserResize:true,
			    updateOnContentResize:false,
			    autoScrollOnFocus:true,
	            scrollSpeed:50
	        },scrollInertia:300,//滚动延时
	        mouseWheelPixels:50,//滚动像素
		 	callbacks:{
		 		onTotalScrollOffset:30,//回调距什么位置回调
		 		whileScrollingInterval:1,
		 		onTotalScroll:function(){
		 			var obj = $conentObj;
		 			var pageerJosn = getPageer(obj);
		 			if(pageerJosn.pageNo==pageerJosn.pageSum){
		 				showMessageTitle("加载完毕");
		 			}else if(pageerJosn.pageNo<pageerJosn.pageSum){
		 				pageerJosn.pageNo=(pageerJosn.pageNo+1);
		 				pageerJosn.ajaxData = getFilterValArr();
		 				postData =  jQuery.extend(true,{}, pageerJosn);
		 				ajaxType = "update";//异步追加
		 				updateTableData();
		 				ajaxType = "init";//第一加载
		 			}
		 		},onTotalScrollBack:function(){
		 			showMessageTitle("已置顶");
		 		}
		 	}
		 });
	};
	/**
	 * 鼠标移上事件
	 */
	var initHover = function(){
		var delObj = $(".delTableTr");
		 if(delObj.html()===undefined){
			var delHtml = '<div class="delTableTr" style="position: fixed;">';
		     delHtml += '<button onclick="delTableTr(this);" class="btn btn-del btn-danger">删除</button>';
		 	 delHtml += '</div>';
		 	 $conentObj.append(delHtml);
		 	 delObj = $(".delTableTr");
		 }
		 $conentObj.find("tbody tr").unbind();
		 $conentObj.find("tbody tr").bind({
		 	mouseenter:function(e){
		 		$(".delTableTr").show();
			 	var trIndex = $(this).index();
			 	var cssTop =  $(this).offset().top;;
			 	var cssLeft =  $(this).offset().left+$(this).width()-50;
			 	var trId = $(this).find("td").eq(0).text().trim();
			 	delObj.find("input").attr("delTrId",trId);
			 	delObj.find("input").attr("trIndex",trIndex);
			 	delObj.css({top:cssTop,left:cssLeft});
			 },mouseleave:function(e){
			 	//delObj.find("input").removeAttr("delTrId");
			 	//$(".delTableTr").hide();
			 }
		 });
	}
	 /**
	  * 显示加载
	  * @param {Object} msg
	  */
	 var showMessageTitle = function(msg){
	 	var obj = $(".messageTitle").find(".tooltip");
	 	$(obj).find(".tooltip-inner").text(msg);
	 	$(obj).animate({opacity:1},1000);
	 	$(obj).animate({opacity:0},1000);;
	 }
	 /**
	  * 获取pegeer页面属性
	  * @param {Object} obj
	  */
	 var getPageer = function(obj){
	 	$pageer = $(obj).find(".pageer");
	 	var pageerJosn = {};
	 	pageerJosn.pageNo = parseInt($pageer.attr("pageno"));
	 	pageerJosn.pageSum = parseInt($pageer.attr("pagesum"));
	 	pageerJosn.pageSize = parseInt($pageer.attr("pagesize"));
	 	return pageerJosn;
	 }
	 /**
	  * 配置配置也属性
	  * @param {Object} obj
	  * @param {Object} pageerJosn
	  */
	 var setPageer = function(obj,pageerJosn){
	 	$pageer = $(obj).find(".pageer");
	 	$pageer.attr("pageNo",pageerJosn.pageNo);
	 	$pageer.attr("pageSum",pageerJosn.pageSum );
	 	$pageer.attr("pageSize",pageerJosn.pageSize);
	 }
	 updateTableData();//首次加载
	 init();//初始化滚动条
}
