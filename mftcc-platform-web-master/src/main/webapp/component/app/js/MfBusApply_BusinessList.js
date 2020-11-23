var MfBusApply_BusinessList = function(window, $) {
	var _init = function(){
		// 加载列表数据
		myCustomScrollbar({
			obj : "#content",//页面内容绑定的id
			url : webPath + "/mfBusApply/findBusinessByPageAjax?score="+score+"&regDate="+regDate,//列表数据查询的url
			tableId : "tablebusinseeapplylist",//列表数据查询的table编号
			tableType : "tableTag",//table所需解析标签的种类
			pageSize : 30,//加载默认行数(不填为系统默认行数)
			callback:function(){
                _color();
			}
		});
	};
	//根据分数改变分数和等级颜色
    var _color = function(){
        $('#content tbody tr').each(function(){
            var score = $(".score").html();
            if("" != score && score != null){
                if(score>="90"){
                    $(".score").css("color","red");
                    $(".grade").css("color","red");
                }else if(score>="80" && score<"90"){
                    $(".score").css("color","Violet");
                    $(".grade").css("color","Violet");
                }else if(score>="65" && score<"80"){
                    $(".score").css("color","GoldenRod");
                    $(".grade").css("color","GoldenRod");
                }else if(score>="50" && score<"65"){
                    $(".score").css("color","blue");
                    $(".grade").css("color","blue");
                }else {
                    $(".score").css("color","green");
                    $(".grade").css("color","green");
                }
            }
        });
    }


    var _getDetailPage = function (obj,url){

        if(url.substr(0,1)=="/"){
            url =webPath + url;
        }else{
            url =webPath + "/" + url;
        }
        top.window.openBigForm(url, '客户详情', function() {
        });
    };
    var _getAppDetailPage = function(obj, url) {
        top.window.openBigForm(webPath + url, '项目详情', function() {
        });
    }

	var _getYouYuDetail = function(obj,url){
		top.openBigForm(url, "天天有余查询", function(){
			_init();
		});
	};
	return {
		init:_init,
        color:_color,
        getYouYuDetail:_getYouYuDetail,
        getDetailPage:_getDetailPage,
        getAppDetailPage:_getAppDetailPage
	};
}(window, jQuery);