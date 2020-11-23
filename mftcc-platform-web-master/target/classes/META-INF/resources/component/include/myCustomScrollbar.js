var mcsOptions;
var myCustomScrollbar = function(options){
	var $conentObj = $(options.obj);
	var parentHeight =  options && options.hasOwnProperty('parentHeight') ? options.parentHeight : false;
	options.parentHeight = parentHeight;
	options["callData"] = {};
	// 通过将列表上部的高度动态传入来计算列表块的高度。by LiuYF
	if (typeof options.topHeight === "number") {
		$conentObj.height($(document).height() -options.topHeight - 30 - 10);
	} else if(options.ownHeight){//类似于进件列表页面样式走这个分支，列表页面ownHeight属性设置为true
		$conentObj.height($(document).height() -50 -50 - 30 - 10);
	}else{
		if(parentHeight){
			$conentObj.height($conentObj.parent().height()-30-10);
		}else{
			$conentObj.height($(window).height()-50-30-10);
		}
	}
	var url = options.url;
	var pageTurningFlag = false;
	var ajaxType = "init";
	var tableId = options.tableId;
	var tableType = options.tableType;
	var myFilter = options.myFilter;
	var callback = options.callback;
	var formEditFlag = options.formEditFlag;
	var initMytitle = function(){
		$("#tablist td[mytitle]:contains('...')").initMytitle();
	};
	var postData = {};
	window.updateTableData = function(isAsync){
		//判断是否手动翻页
		if(ajaxType=="init"){
			postData.ajaxData = getFilterValArr();
			postData.pageSize = $conentObj.find(".pageer").attr("pageSize");
			postData.pageNo = 1;
		}else{
			if(pageTurningFlag){ return false; }
		}
		postData.tableId = tableId;
		postData.tableType = tableType;
		$.extend(postData,options.data);
		$.ajax({
	 		type:"post",
	 		url:url,
	 		data:postData,
	 		async: isAsync || false,
	 		beforeSend:function(){
	 			LoadingAnimate.start();
				if(ajaxType=="update"){
					$(".footer_loader .loader span,.footer_loader .loader .fa").css("display","inline-block");
				}
			},success:function(data){
				options["callData"] = data;
                var tableHtml;
	 			if(ajaxType=="init"){
	 				if(data.flag=="error"){
	 					alert(data.msg,0);
	 				}else{
	 					tableHtml = $("<div>"+data.ipage.result+"</div>");
	 					tableHtml.find("colgroup").remove();
	 					//处理暂无数据的情况
	 					if(data.ipage.pageCounts==0){
	 						var thCount = $(tableHtml).find("thead th").length;
	 						$(tableHtml).find("tbody").html('<tr><td style="text-align: center;" colspan="'+thCount+'">暂无数据</td></tr>');	
	 					}
	 					if($conentObj.find("table tbody").html()===undefined){
		 					$conentObj.append(tableHtml.find("#tablist"));
		 					if(tableHtml.find(".page_w").length>0){
		 						pageTurningFlag = true;
		 						initPageTurning(tableHtml);
		 					}else{
		 						setPageer($conentObj,data.ipage);
		 					}
		 				}else{
		 					$conentObj.find("table#tablist tbody").html(tableHtml.find("#tablist").find("tbody").html());
		 					if(tableHtml.find(".page_w").length>0){
		 						pageTurningFlag = true;
		 						initPageTurning(tableHtml);
		 					}else{
		 						setPageer($conentObj,data.ipage);
		 					}
		 				}
			 			$conentObj.mCustomScrollbar("update");
			 			initMytitle();
			 			$conentObj.find("table").tableHead();
			 			if(typeof(callback)=="function"){
			 				callback.call(this,options,data);
			 			}
	 				}
	 				
	 			}else if(ajaxType=="update"){
	 				//showMessageTitle("加载第"+postData.pageNo+"页");
	 				tableHtml = $("<div>"+data.ipage.result+"</div>");
		 			$conentObj.find("table#tablist tbody").append(tableHtml.find("#tablist").find("tbody").html());
		 			if(tableHtml.find(".page_w").length>0){
 						pageTurningFlag = true;
 						initPageTurning(tableHtml);
 					}else{
 						setPageer($conentObj,data.ipage);
 					}
		 			$conentObj.mCustomScrollbar("update");
		 			$(".footer_loader .loader span,.footer_loader .loader .fa").fadeOut(1000);
		 			initMytitle();
		 			if(typeof(callback)=="function"){
		 				callback.call(this,options,data);
		 			}
	 			}
	 			if(myFilter){
	 				showTable(false,$conentObj.find("table").attr("class"));
	 			}else{
	 				$conentObj.find(".ls_list").show();
	 			}
	 			//initHover();
	 			$conentObj.mCustomScrollbar("update");
	 		},
	 		complete : function() {
	 			if (LoadingAnimate) {
	 				LoadingAnimate.stop();
				}
				if (typeof(formEditFlag) != "undefined" && formEditFlag == "query") {
					$(".formAdd-btn").hide();
					$(".editBtn").html("编辑");;
					$(".delBtn").html("删除");
				}
	 		}
	 	});
		
	};
	//初始化手动翻页
	var initPageTurning = function(tableHtml){
		tableHtml.find(".page_w").find("form").remove();
		$(".footer_loader").css({"padding-left":"initial"});
		tableHtml.find(".page_w").find("li a").each(function(){
			var onclick = $(this).attr("onclick");
			$(this).attr("onclick","cusBar_"+onclick);
		});
		tableHtml.find(".page_w").find("input[name='submitgo']").attr("onclick","cusBar_submitgo(this)");
		if($(".footer_loader").find("table.page_w").length==0){
			$(".footer_loader").html(tableHtml.find(".page_w"));
		}else if($(".footer_loader").find("table.page_w").length>0){
			$(".footer_loader table.page_w").html(tableHtml.find(".page_w").html());
		}
	};
	//翻页
	window.cusBar_doEadisPage = function(dourl,pageNo){
		var pageSize = $(".footer_loader table.page_w").find("input[name='pageSize']").val();
		cusBar_nextPage(pageNo,pageSize);
	};
	//go跳页
	window.cusBar_submitgo = function(obj){
		var pageNo = $(obj).parents("table").find("input[name='null']").val();
		var pageSize = $(obj).parents("table").find("input[name='pageSize']").val();
		cusBar_nextPage(pageNo,pageSize);
	};
	//翻页更新数据
	var cusBar_nextPage = function(pageNo,pageSize){
		postData.ajaxData = getFilterValArr();
		postData.pageNo = pageNo;
		postData.eadis_page = pageNo;
		postData.pageSize = pageSize;
		postData.tableId = tableId;
		postData.tableType = tableType;
		$.extend(postData,options.data);
		$.ajax({
	 		type:"post",
	 		url:url,
	 		data:postData,
	 		async:false,
	 		beforeSend:function(){
			},success:function(data){
				options["callData"] = data;
 				var tableHtml = $("<div>"+data.ipage.result+"</div>");
	 			$conentObj.find("table#tablist tbody").html(tableHtml.find("#tablist").find("tbody").html());
	 			initPageTurning(tableHtml);
	 			$conentObj.mCustomScrollbar("update");
	 			initMytitle();
	 			if(typeof(callback)=="function"){
	 				callback.call(this,options);
	 			}
	 			if(myFilter){
	 				showTable(false,$conentObj.find("table").attr("class"));
	 			}else{
	 				$conentObj.find(".ls_list").show();
	 			}
				if (typeof(formEditFlag) != "undefined" && formEditFlag == "query") {
					$(".formAdd-btn").hide();
					$(".editBtn").html("编辑");;
					$(".delBtn").html("删除");
				}
	 		},error:function(){
	 			console.log("翻页加载出错");
	 		}
	 	});
		$conentObj.mCustomScrollbar("update");
	};
	/**
	 * 滚动条初始化
	 */
	var init = function(){
		$conentObj.mCustomScrollbar({
	        scrollButtons:{
	            enable:true,
	            autoHideScrollbar: true
	        },advanced:{
	            autoExpandHorizontalScroll:true,
	            updateOnBrowserResize:true,
			    updateOnContentResize:true,
			    autoScrollOnFocus:true,
	            scrollSpeed:50
	        },
//	        mouseWheelPixels:100,//滚动像素
		 	callbacks:{
		 		onTotalScrollOffset:50,//回调距什么位置回调
		 		whileScrollingInterval:1,
		 		onTotalScroll:function(){
		 			var obj = $conentObj;
		 			var pageerJosn = getPageer(obj);
		 			if(pageerJosn.pageNo==pageerJosn.pageSum){
		 				//showMessageTitle("加载完毕");
		 			}else if(pageerJosn.pageNo<pageerJosn.pageSum){
		 				pageerJosn.pageNo=(pageerJosn.pageNo+1);
		 				pageerJosn.ajaxData = getFilterValArr();
		 				postData =  jQuery.extend(true,{}, pageerJosn);
		 				ajaxType = "update";//异步追加
		 				updateTableData();
		 				ajaxType = "init";//第一加载
		 			}
		 		},onTotalScrollBack:function(){
		 			//showMessageTitle("已置顶");
		 		}
		 	}
		 });
		$conentObj.find("table").tableHead();
	};
	/**
	 * 鼠标移上事件
	 */
	window.initHover = function(){
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
			 	delObj.find("button").attr("delTrId",trId);
			 	delObj.find("button").attr("trIndex",trIndex);
			 	delObj.css({top:cssTop,left:cssLeft});
			 },mouseleave:function(e){
			 	//delObj.find("input").removeAttr("delTrId");
			 	//$(".delTableTr").hide();
			 }
		 });
	};
	 /**
	  * 显示加载
	  * @param {Object} msg
	  */
	 var showMessageTitle = function(msg){
	 	var obj = $(".messageTitle").find(".tooltip");
	 	$(obj).find(".tooltip-inner").text(msg);
	 	$(obj).animate({opacity:1},1000);
	 	$(obj).animate({opacity:0},1000);;
	 };
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
	 };
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
	 	$footerLoader = $(".footer_loader .pagerShow");
	 	$footerLoader.find(".pageCount").html(pageerJosn.pageCounts);
	 	$footerLoader.find(".loadCount").html(pageerJosn.endNum);
	 };
	 /**
	  * 初始化翻页
	  * @param {Object} obj
	  * @param {Object} pageSize
	  */
	 var initPagerHtml =function(obj,pageSize){
	 	if(pageSize===undefined||pageSize==""||pageSize==null){
	 		var wsh = window.screen.height;
	 		if(SysConstant){
	 			if(wsh<768){
		 			pageSize = SysConstant.pageSize_s;
		 		}else if(wsh>=768&&wsh<1080){
		 			pageSize = SysConstant.pageSize_m;
		 		}else if(wsh>=1080){
		 			pageSize = SysConstant.pageSize_l;
		 		}
	 		}else{
	 			pageSize = 0;
	 		}
	 	}
	 	var html = '<div class="pageer" pageNo="1" pageSum="1" pageSize="'+pageSize+'"></div>';
	 	if($(obj).find(".pageer").length==0){
	 		$(obj).append(html);
	 	}
	 };
	 var intiLoadingHtml = function(){
	 	if($(".footer_loader").length==0){
	 		var loadingHtml = '<div class="footer_loader">';
	 		loadingHtml+= '<div class="loader">';
			loadingHtml+= '<i class="fa fa-spinner fa-pulse fa-3x"></i>';
			loadingHtml+= '</div>';
			loadingHtml+= '<div class="pagerShow">当前显示&nbsp;<span class="loadCount">0</span>&nbsp;条数据，一共&nbsp;<span class="pageCount">0</span>&nbsp;条数据</div>';
			loadingHtml+= '<div class="backToTop"></div>';
			loadingHtml+= '</div>';
		$("body").append(loadingHtml);
	 	}
	 };
	 
	 var initBackToTop = function(){
	 	$(".footer_loader .backToTop").click(function(){
	 		$conentObj.mCustomScrollbar("scrollTo","top");
	 	});
	 };
	 initPagerHtml($conentObj,options.pageSize);
	 intiLoadingHtml();
	 updateTableData();//首次加载
	 init();//初始化滚动条
	 initBackToTop();
	 $(window).resize(function() {
		 if(options.ownHeight){
				$conentObj.height($(document).height()-50-30-50);
			}else{
				if(options.parentHeight){
						$conentObj.height($conentObj.parent().height()-30-10);
				}else{
						$conentObj.height($(document).height()-50-30-10);
				}
			}
		
	 	$conentObj.mCustomScrollbar("update");
	 	if(myFilter){
			showTable(false,$conentObj.find("table").attr("class"));
			$conentObj.find("table").tableHead();
		}else{
			$conentObj.find(".ls_list").show();
			$conentObj.find("table").tableHead();
		}
	 });
	 options.reload = function(){
		 updateTableData();//首次加载
	 };
	 mcsOptions = options;
	return options;
};
var updateMyCustomScrollbar = {
	setPageer:function(obj,pageerJosn,callback){
		var tableHtml = $(pageerJosn.result);
		$(obj).find("table tbody").html($(tableHtml).find("tbody").html());
		$(obj).mCustomScrollbar("update");
		var $pageer = $(obj).find(".pageer");
	 	$pageer.attr("pageNo",pageerJosn.pageNo);
	 	$pageer.attr("pageSum",pageerJosn.pageSum );
	 	$pageer.attr("pageSize",pageerJosn.pageSize);
	 	var $footerLoader = $(".footer_loader .pagerShow");
	 	$footerLoader.find(".pageCount").html(pageerJosn.pageCounts);
	 	$footerLoader.find(".loadCount").html(pageerJosn.endNum);
	 	if(typeof(callback)=="function"){
			callback.call(this);
		}
	},
	delTrData:function(){
	 	var $footerLoader = $(".footer_loader .pagerShow");
	 	var pageCount = $footerLoader.find(".pageCount").html();
	 	var loadCount = $footerLoader.find(".loadCount").html();
	 	$footerLoader.find(".pageCount").html(pageCount-1);
	 	$footerLoader.find(".loadCount").html(loadCount-1);
	}
};
