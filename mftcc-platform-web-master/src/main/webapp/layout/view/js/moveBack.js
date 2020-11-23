$(function(){
	$(".TopBar-chevron a").bind({"click":function(){
		var $this = $(this).find(".i");
		if($this.hasClass("chevron-active")){
			if($this.hasClass("i-jiantou3")){//后退
				$.ajax({
					type:"post",
					url:webPath+"/moveBack/backMechanism",
					async:false,
					success:function(data){
						initMoveBackBotton(data.forwardBack);
						if(data.flag=="success"){
							var backUrl = data.backURL;
							var entranceNo = data.entranceNo;
							//console.log(backUrl);
							$("#a_iframe").attr("src",backUrl);
						}else{
							console.log("error: url-"+data.backUrl);
						}
					},error:function(){
						console.log("error:请求错误");
					}
				});
			}else if($this.hasClass("i-jiantou2")){//前进
				$.ajax({
					type:"post",
					url:webPath+"/moveBack/forwardMechanism",
					async:false,
					success:function(data){
						initMoveBackBotton(data.forwardBack);
						if(data.flag=="success"){
							var backUrl = data.forwardURL;
							var entranceNo = data.entranceNo;
							//console.log(backUrl);
							$("#a_iframe").attr("src",backUrl);
						}else{
							console.log("error: url-"+data.forwardURL);
						}
					},error:function(){
						console.log("error:请求错误");
					}
				});
			}
		}
		initMoveBackAjax();
	}/*,"mouseenter":function(){
			initMoveBackAjax();
	}*/});
	$(".TopBar-chevron a").bind("mouseenter",function(){
			initMoveBackAjax();
	})
	var initMoveBackBotton = function(dataObj){
		$(".TopBar-chevron .i").each(function(index){
			if($(this).hasClass("i-jiantou3")){
				if(dataObj.back=="false"){
					$(this).removeClass("chevron-active");
				}else if(dataObj.back=="true"){
					$(this).addClass("chevron-active");
				}
			}else if($(this).hasClass("i-jiantou2")){
				if(dataObj.forward=="false"){
					$(this).removeClass("chevron-active");
				}else if(dataObj.forward=="true"){
					$(this).addClass("chevron-active");
				}
			}
		});
	};
	
	var initMoveBackAjax = function(){
		$.ajax({
			type:"post",
			url:webPath+"/moveBack/initMoveBackBotton",
			async:false,
			success:function(data){
				if(data.flag=="success"){
					initMoveBackBotton(data.forwardBack);
				}else{
					alert("error");
				}
			},error:function(){
				//console.log("error:请求错误");
			}
		});
	};
	initMoveBackAjax();
});
