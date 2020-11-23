var fixedTools = [
    {"name":"安排日程","icon":"i i-richengshezhi","fun":"FullCalendar()","pmsBizNo":"tool-schedule"},
    {"name":"手机短信","icon":"i i-youjian","fun":"sendMessage()","pmsBizNo":"tool-shortmsg"},
    {"name":"账户中心","icon":"i i-rmb","fun":"cloudService()","pmsBizNo":"tool-account-center"},
    {"name":"贷款计算器","icon":"i i-jisuanqi","fun":"loanCalculatorAction()","pmsBizNo":"tool-calculator"},
    {"name":"企业微信","icon":"i i-qiyeweixin","fun":"FullCalendar()","pmsBizNo":"tool-corp-WeChat"},
    {"name":"下载中心","icon":"i i i-xiazai","fun":"downloadTools()","pmsBizNo":"tool-download"},
    {"name":"更换皮肤","icon":"i i i-pifu","fun":"changeSysSkin()","pmsBizNo":"tool-change-skin"},
    {"name":"'失信人查询","icon":"i i i-xiazai","fun":"discredit()","pmsBizNo":"tool-discredit"},
    {"name":"判决文书查询","icon":"i i i-xiazai","fun":"verdict()","pmsBizNo":"tool-verdict"},
    {"name":"被执行人信息查询","icon":"i i i-xiazai","fun":"executor()","pmsBizNo":"tool-executor"},
    {"name":"全局搜索","icon":"i i i-fangda","fun":"searchGlobalAll()","pmsBizNo":"tool-search-all"},
    {"name":"房产评估","icon":"i i i-qiye","fun":"realEstateEval()","pmsBizNo":"tool-real-estate-eval"}
];
function getfixedDiv(){
    if(!($(".leftbar").length>0)){
        var $div = $("<div></div>");
        var $hover = $("<div class='leftbar'></div>");
        var hovercss= {"position":"fixed","width":"150px","height":"calc(100% - 55px)","background-color":"#4D4D4D","right":"-70px","line-height":"100%","z-index":"9","top":"55px"};
        var divcss= {"position":"fixed","width":"80px","height":"calc(100% - 55px)","background-color":"#4D4D4D","right":"-80px","line-height":"100%"};
        $div.css(divcss);
        $hover.css(hovercss);
        $div.createfixedMenu();
        $div.appendTo($hover);
        $hover.appendTo("body");
        $div.animate({right:'0px'});
        $hover.hover(function(){
            $hover.stop(true,true);
        },function(){
            $hover.stop(true,true).delay(300).animate({right:'-80px',opacity:'0.5'},100,function(){$(this).remove();});
        });
    }else{
        $(".leftbar").stop(true,true).delay(300).animate({right:'-80px',opacity:'0.5'},100,function(){$(this).remove();});
    }
}
//设置页面侧边栏
function getfixedSetDiv(){
    if(!($(".leftbar").length>0)){
        var $div = $("<div></div>");
        var $hover = $("<div class='leftbar'></div>");
        var hovercss= {"position":"fixed","width":"150px","height":"calc(100% - 55px)","background-color":"#153754","right":"-70px","line-height":"100%","z-index":"9","top":"55px"};
        var divcss= {"position":"fixed","width":"80px","height":"calc(100% - 55px)","background-color":"#153754","right":"-80px","line-height":"100%"};
        $div.css(divcss);
        $hover.css(hovercss);
        $div.createfixedMenuSet();
        $div.appendTo($hover);
        $hover.appendTo("body");
        $div.animate({right:'0px'});
        $hover.hover(function(){
            $hover.stop(true,true);
        },function(){
            $hover.stop(true,true).delay(300).animate({right:'-80px',opacity:'0.5'},"fast",function(){$(this).remove();});
        });
    }
}


(function($) {
    $.fn.createfixedMenu =  function (){
        $this = $(this);
        $fixed_body=$("<div></div>");
        $fixed_body.css("display","block");
        $fixed_body.css("top","10px");
        //$fixed_body.css("transform","translateY(-50%)");
        $fixed_body.css("position","absolute");
        $.each(fixedTools,function(i,list){
            var checkPmsBizFlag=BussNodePmsBiz.checkPmsBiz(list.pmsBizNo);
            if(checkPmsBizFlag){
                var div = $("<div></div>");
                var divcss = {"width":"80px","height":"70px","padding-top":"6px","margin":"5px 0px","position":"relative","font-size":"18px","color":"#000000","text-align":"center","cursor":"pointer"};
                div.css(divcss);
                var $span = $("<span>"+list.name+"</span>");
                var $spancss = {"color":"white","font-size":"14px","display":"block"};
                $span.css($spancss);
                $i = $("<i></i>");
                $i.addClass(list.icon);
                var icss={"margin":"0 10px 0 10px","height":"30px","line-height":"30px","color":"white","font-size":"20px"};
                $i.css(icss);
                div.bind("click",function(){
                    eval(list.fun);
                })
                $i.attr("title",list.name);
                $i.appendTo(div);
                $span.appendTo(div);
                $i.btnHover();
                div.appendTo($fixed_body);
            }
        });
        var $quit = $("<div></div>");
        var $divcss = {"width":"80px","height":"70px","margin-bottom":"28px","position":"absolute","font-size":"18px","color":"#000000","text-align":"center","bottom":"0px","cursor":"pointer"};
        $quit.css($divcss);
        var $spanQuit = $("<span>退出</span>");
        var $spancss = {"color":"white","font-size":"14px","display":"block"};
        $spanQuit.css($spancss);
        $iQuit = $("<i></i>");
        $iQuit.addClass("i i-tuichu");
        var $icss={"margin":"0 10px 0 10px","height":"30px","line-height":"30px","color":"white","font-size":"20px"};
        $iQuit.css($icss);
        $iQuit.attr("title","退出");
        /*$iQuit.appendTo($quit);*/
        /*$spanQuit.appendTo($quit);*/
        $iQuit.btnHover();
        $iQuit.sysQuit();
        $quit.appendTo($this);
        $fixed_body.appendTo($this);
    };
    $.fn.sysQuit = function(){
        $(this).parent().click(function(){
            window.top.location.href = path+"/logout.action";
        });
    };
    $.fn.btnHover= function(){
        $(this).parent().hover(function(){
            $(this).css("background-color","#353535");
            $(this).children().css("color","#fff");
        },function(){
            $(this).children().css("color","#fff");
            $(this).css("background-color","");
        });
    };

    //设置页面使用 --start--
    $.fn.createfixedMenuSet =  function (){
        $this = $(this);
        $fixed_body=$("<div></div>");
        $fixed_body.css("display","block");
        $fixed_body.css("top","10px");
        //$fixed_body.css("transform","translateY(-50%)");
        $fixed_body.css("position","absolute");
        $.each(fixedTools,function(i,list){
            var checkPmsBizFlag=BussNodePmsBiz.checkPmsBiz(list.pmsBizNo);
            if(checkPmsBizFlag){
                var div = $("<div></div>");
                var divcss = {"width":"80px","height":"70px","padding-top":"6px","margin":"5px 0px","position":"relative","font-size":"18px","color":"#000000","text-align":"center","cursor":"pointer"};
                div.css(divcss);
                var $span = $("<span>"+list.name+"</span>");
                var $spancss = {"color":"white","font-size":"14px","display":"block"};
                $span.css($spancss);
                $i = $("<i></i>");
                $i.addClass(list.icon);
                var icss={"margin":"0 10px 0 10px","height":"30px","line-height":"30px","color":"white","font-size":"20px"};
                $i.css(icss);
                div.bind("click",function(){
                    eval(list.fun);
                })
                $i.attr("title",list.name);
                $i.appendTo(div);
                $span.appendTo(div);
                $i.btnHoverSet();
                div.appendTo($fixed_body);
            }
        });
        $fixed_body.appendTo($this);
    };
    $.fn.btnHoverSet= function(){
        $(this).parent().hover(function(){
            $(this).css("background-color","#0D2840");
            $(this).children().css("color","#fff");
        },function(){
            $(this).children().css("color","#fff");
            $(this).css("background-color","");
        });
    };
    //设置页面使用--end--
})(jQuery);