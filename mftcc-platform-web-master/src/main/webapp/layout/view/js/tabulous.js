/*!
 * strength.js
 * Original author: @aaronlumsden
 * Further changes, comments: @aaronlumsden
 * Licensed under the MIT license
 */
;(function ( $, window, document, undefined ) {

    var pluginName = "tabulous",
        defaults = {
            effect: 'scale'
        };

       // $('<style>body { background-color: red; color: white; }</style>').appendTo('head');

    function Plugin( element, options ) {
        this.element = element;
        this.$elem = $(this.element);
        this.options = $.extend( {}, defaults, options );
        this._defaults = defaults;
        this._name = pluginName;
        this.init();
    }

    Plugin.prototype = {

        init: function() {

            var links = this.$elem.find('a');
            var firstchild = this.$elem.find('li:first-child').find('a');
            //var lastchild = this.$elem.find('li:last-child').after('<span class="tabulousclear"></span>');

            if (this.options.effect == 'scale') {
             tab_content = this.$elem.find('div').not(':first').not(':nth-child(1)').addClass('hidescale');
            } else if (this.options.effect == 'slideLeft') {
                 tab_content = this.$elem.find('div').not(':first').not(':nth-child(1)').addClass('hideleft');
            } else if (this.options.effect == 'scaleUp') {
                 tab_content = this.$elem.find('div').not(':first').not(':nth-child(1)').addClass('hidescaleup');
            } else if (this.options.effect == 'flip') {
                 tab_content = this.$elem.find('div').not(':first').not(':nth-child(1)').addClass('hideflip');
            }

            var firstdiv = this.$elem.find('#tabs_container');
            var firstdivheight = firstdiv.find('div:first').height();

            var alldivs = this.$elem.find('div:first').find('div');

            alldivs.css({'position': 'absolute','top':'40px'});

            firstdiv.css('height',firstdivheight+'px');

            firstchild.addClass('tabulous_active');

            links.unbind();
            links.bind('click', {myOptions: this.options}, function(e) {
                e.preventDefault();

                var $options = e.data.myOptions;
                var effect = $options.effect;

                var mythis = $(this);
                var thisform = mythis.parent().parent().parent();
                var thislink = mythis.attr('href');


                firstdiv.addClass('transition');

                $(".tabulous_active").removeClass('tabulous_active');
                mythis.parent().addClass('tabulous_active');
                thisdivwidth = thisform.find('div'+thislink).height();

                if (effect == 'scale') {
                    alldivs.removeClass('showscale').addClass('make_transist').addClass('hidescale');
                    thisform.find('div'+thislink).addClass('make_transist').addClass('showscale');
                } else if (effect == 'slideLeft') {
                    alldivs.removeClass('showleft').addClass('make_transist').addClass('hideleft');
                    thisform.find('div'+thislink).addClass('make_transist').addClass('showleft');
                } else if (effect == 'scaleUp') {
                    alldivs.removeClass('showscaleup').addClass('make_transist').addClass('hidescaleup');
                    thisform.find('div'+thislink).addClass('make_transist').addClass('showscaleup');
                } else if (effect == 'flip') {
                    alldivs.removeClass('showflip').addClass('make_transist').addClass('hideflip');
                    thisform.find('div'+thislink).addClass('make_transist').addClass('showflip');
                }


                firstdiv.css('height',thisdivwidth+'px');
                
                var l = $(".menu_A").find(".i-"+mythis.parent().attr("name")).parent();
        		l.parent().find("li").removeClass("menu-active");
        		l.addClass("menu-active");
        		menuIndex = l.index();
        		if(menuIndex != -1){
        			setPageData(piData[mythis.parent().attr("name")],"#pageInfoContent");
        		}
                

                return false;
            });

           


         
            
        },

        yourOtherFunction: function(el, options) {
            // some logic
        }
    };

    // A really lightweight plugin wrapper around the constructor,
    // preventing against multiple instantiations
    $.fn[pluginName] = function ( options ) {
        return this.each(function () {
            new Plugin( this, options );
        });
    };

})( jQuery, window, document );

var $tab,piData={};
$(document).ready(function($) {
	initTab();
});

function initTab(){
	rzzl.initMenu = initMenuForTab;
	rzzl.setPageInfo = setPageData;
	$tab = $("<div class='tabs' style='width: 100%; height: 100%;position: absolute;'><ul style='height: 29px;background-color: #EAEBEC;'>" +
			"</ul><div id='tabs_container'>" +
			"</div></div>");
	$("#a_iframe").before($tab);
	$("#a_iframe").hide();
}

function addTab(data){
	var li = $tab.find("li[name='"+data.css+"']");
	if(li.length!=0){
		li.find("a").click();
		return false;
	}
	$tab.find("ul").append("<li name='"+data.css+"'><a href='#"+data.css+"' title=''>"+data.name+"</a><i class='i i-x2'></i></li>");
	$tab.find("#tabs_container").append("<div id='"+data.css+"'>" +
					"<iframe id='"+data.css+"_iframe' src='"+data.url+"' marginheight='0' marginwidth='0' frameborder='0' scrolling='auto' width='100%' height='100%'></iframe>" +
			"</div>");
	$tab.find("li[name='"+data.css+"'] i").bind("click",{css:data.css},delTab);
	$tab.tabulous({
    	effect: 'scale'
    });
	$tab.find("li[name='"+data.css+"'] a").click();
}

function delTab(e){
	var css = e.data.css;
	delete piData[css];
	$(this).remove();
	$tab.find("li[name='"+css+"'] a").animate({width:'0px'},200,function(){
		if($(this).parent().hasClass("tabulous_active")){
			if($(this).parent().prev().length>0){
				$(this).parent().prev().find("a").click();
			}else{
				$(this).parent().next().find("a").click();
			}
		}
		$tab.find("li[name='"+css+"']").remove();
		$("#"+css).remove();
	});
	return false;
}

function setPageData(data,selector){
	if(typeof data == "undefined"){
		return;
	}
	var container = $(selector); 
	var icss = $(".menu_A").find("li").eq(menuIndex).find("i").attr("class").substring(4);
	if(typeof piData[icss] == "undefined"){
		piData[icss] = data;
	}
	if(menuType&&menuType=="A"){
		container.html(data);
		pageInfoData = data;
		$("#pageInfoIcon").attr("class",descFa);
	}else{
		container.html("");
		$("#pageInfoIcon").attr("class",null);
	}
}

function initMenuForTab(selector){
	$(selector).html("");
	isInitMenu = false;
	menuType = "B";
	var isC = false;
	for(var i in viewMenuData){
		$(selector).append("<li><i class='i i-"+viewMenuData[i].css+"'></i><a href='"+path+"/"+viewMenuData[i].url+"'pageType='"+viewMenuData[i].css+"' onclick='return false'>"+viewMenuData[i].name+"</a></li>");
		if(GetQueryString("entranceNo",viewMenuData[i].url)=="99"){
			isC = true;
//			$this.skipPage(viewMenuData[i].url);
			addTab(viewMenuData[i]);
			menuIndex = i;
		}
	}
	if(!isC){
//		$this.skipPage($(selector).find("li a").eq(0).attr("href"));
		addTab(viewMenuData[0]);
		menuIndex = 0;
	}
//	$this.addMenuEvent(".outer-west-center ul li","click");
//	$(".outer-west-logo").bind("click",$this.logoClick);
	$(selector).find("li").bind("click",function(){
			var $li = $(this);
			var a = $li.find("a").eq(0);
			descFa = $li.find("i").eq(0).attr("class");
			$li.parent().find("li").removeClass("menu-active");
			$(".middle-center").removeClass("desgin-view");
			if(isInitMenu){
				if(menuType=="A"){
					$li.addClass("menu-active");
		 			menuIndex = $li.index();
					//$this.skipPage(a.attr("href"));
		 			addTab(viewMenuData[menuIndex]);
				}else{
					alert("是否跳转到已存在入口？按“取消”继续进行当前操作！",2,function(){
						rzzl.turnAorB(0);
					},function(){
						$li.addClass("menu-active");
			 			menuIndex = $li.index();
						rzzl.turnAorB(0);
						//$this.skipPage(a.attr("href"));
						addTab(viewMenuData[menuIndex]);
					});
				}
			}else{
				$li.addClass("menu-active");
				menuIndex = $li.index();
				rzzl.turnAorB(0);
				//$this.skipPage(a.attr("href"));
				addTab(viewMenuData[menuIndex]);
			}
			isInitMenu = true;
		});
	$(".switchB").show();
}