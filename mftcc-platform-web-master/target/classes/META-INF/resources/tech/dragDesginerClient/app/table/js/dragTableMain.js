/*dragTableMain.js*/
define(function(require, exports, module) {

    var $ = require("jquery");
    require("pace/js/pace.js");
    //require("jquery-ui/js/jquery-ui.min.js");
    require("drag/js/redips-drag-source.js");
    require("bootstrap/js/bootstrap.min.js");
    require("jquery/jquery.transit-0.9.12-min.js");
    require("easydropdown/js/jquery.easydropdown.js");
    require("../../common/js/myAlert.js");
    require("../../common/js/loadFunction.js");
    var initTableSetting = require("./initTableSetting.js");

    /*单独功能引用*/
    var tablePage = require("./tablePage.js");
    var tableAttributeSetting = require("./tableAttributeSetting.js");
    var tableUtil = require("./tableUtil.js");
    // redips initialization
    var redips = {},		// redips container
        rd = REDIPS.drag,	// reference to the REDIPS.drag library
        counter = 0,		// counter for cloned DIV elements
        clonedDIV = false,	// cloned flag set in event.moved
        lastCell,
        targetId,// reference to the last cell in table
        parentsDiv,
        dragAddFlag=false,
        dataType="default";
    window.initTable = function () {
            //var rd = REDIPS.drag;
            rd.style.borderEnabled = "none";      //元素周围不添加边框  先设置参数  再初始化
            rd.init();
            // set mode option to "shift"
            rd.dropMode = 'shift';
            // enable shift.animation
            rd.shift.animation = false;
			rd.animation.pause = 1;
            rd.style.opacityDisabled = 50;
            rd.shift.after="always";
            rd.shift.animation.step=1;
            /*
            * 克隆方法
            *作用：点击时记录父层对象
            */
            rd.event.clicked = function(){
            	cancelFormIdTableIdUpdate();
                $(".clickTdSelect").removeClass("clickTdSelect");
                parentsDiv=$(rd.obj);
                tableUtil.mosueClicks(parentsDiv);
                if($(parentsDiv).children().hasClass("reallyObj")){
                    dragAddFlag = true;
                    dataType = $(rd.obj).find(".reallyObj").find(".tdlable").children().attr("dataType");
                }else{
                    dragAddFlag = false;
                }
                tableUtil.setDragWidth(parentsDiv);
            }
            rd.event.dblClicked = function(){
                var parentFlag = $(rd.obj).parents(".mainContent");
                if(parentFlag.length==0){
                    return;
                }
                $(rd.obj).parents("td").addClass("clickTdSelect");
                var tableListId = $(rd.obj).attr("id");
                tableAttributeSetting.setAttributeDes(tableListId);
                tableUtil.resetDragWidth();
            }
            rd.event.droppedBefore = function (targetCell) {
                //tableUtil.addColm(rd,parentsDiv);
                targetId = $(targetCell).find(".drag").attr("id");
                if(targetId===undefined){
                	targetId = "";
                }
                var tableTrObj = $(".mainContent #table2 tbody").find("tr");
                 if(dragAddFlag==true&&tableTrObj.find("td").length>0&&tableTrObj.find("td").last().find("div").length>0){
                 	$(".mainContent #table2 ").find("colgroup").append('<col/>');
                	$(".mainContent #table2 tbody").find("tr").append('<td></td>');
                 }
                $(rd.obj).find(".cloneDisplayObj").hide(); 
                $(rd.obj).find(".reallyObj").show();
            }
            /*拖动完成*/
            rd.event.dropped = function(targetCell){
                var tableListId = $(rd.obj).attr("id");
                var  label = $.trim($(rd.obj).find(".reallyObj span").text());
                if(dragAddFlag==true){
                    var type = tableUtil.getTypeFromDateType(dataType);
                    tableAttributeSetting.insertAttributeDes(targetId,type,label);
                }else if(targetId!=tableListId){
                    tableAttributeSetting.changePosition(tableListId,targetId);
                }
            }
            REDIPS.drag.event.relocateEnd = function () {
                tableUtil.resetDragWidth();
            }
            rd.event.finish = function(){
                if(rd.shift.animation==false){
                    tableUtil.resetDragWidth();
                }
                if(dragAddFlag){
            		console.log("insert end fulsh 11:"+(new Date()).valueOf());
            		 setTimeout(initTableSetting.initTableframe,1000);
            		 console.log("insert end fulsh 11:"+(new Date()).valueOf());
            	}
                //setTimeout(tableUtil.resetDragWidth,1000);
                $(rd.obj).find(".blackBgColor").removeClass("blackBgColor");
                $(".oneBroderTable").removeClass("oneBroderTable");
            }
            rd.event.handlerOnMouseDown =function(){
                $(rd.obj).find(".tableBorder").addClass("blackBgColor");
            }
            rd.event.setUsableTd =function(table,row,cell,color){
                $(".oneBroderTable").removeClass("oneBroderTable");
                var tdObj = $("#table2").find("tbody").find("tr").eq(row).find("td").eq(cell);
                var zIndex = $(tdObj).find(".drag").css("z-index");
                if(zIndex=="auto"){
                    $(tdObj).find(".drag").find(".tableBorder").addClass("oneBroderTable");
                }else{
                    $(tdObj).addClass("oneBroderTable");
                }
            }
            // in the moment when DIV element is moved, clonedDIV will be set
            rd.event.moved = function (cloned) {
                $(".clickTdSelect").removeClass("clickTdSelect");
                //tableUtil.setDragWidth(parentsDiv);
            };
        };
    //页面加载执行
    $(function(){ 
        //redips.init();
        initTableSetting.initTableframe();
        $(".mainContent").css("height", $("body").height()+"px");
        //页面加载样式
        $("#tableWidth,#tableTypePara,#tableIndexed,#tableAuthority,#tableMytitle,#tableButtonMark,#fieldStyle,#widthType,#defaultShow,#colorCol").change(function(){
            updateAttributeDesTable();
        });
        var w = $(".menu").outerWidth(true);
        var Fromleft=w/2-19;
        $(".parentFrom").css("left",Fromleft+"px");
        var FromFontMargin=(w-60)/2;
        var FromFontMargin1=(w-74)/2;
        $(".FromFont").css("marginLeft",FromFontMargin+"px");
        $(".FromFont1").css("marginLeft",FromFontMargin1+"px");
        $(".menu").eq(0).find(".parentFrom").css("top","8px");
        $(".menu").eq(0).find(".parentFrom").css("left","10px");
        $(".menu").eq(0).find(".FromFont").css("marginLeft","50px");
        $(".menu").click(function(){
            var thisObj;
            if(!$("body").find(".menu").hasClass("menuClick")){
                $(this).find(".parentFrom").addClass("parentFrom_little");
                $(this).find(".FromFontColor").addClass("FromFontColorClick");
                $(this).animate({
                    paddingTop:"20px",
                    height:"50px",
                    paddingBottom:"10px"
                })		;
                $(this).find(".parentFrom").animate({
                    top:"8px",
                    left:"10px"
                });
                $(this).find(".FromFont").animate({
                    marginLeft:"50px"
                })	;
                $(this).find(".FromFont1").animate({
                    marginLeft:"50px"
                })	;
                thisObj =this;
                setTimeout(function(){$(thisObj).addClass("menuClick")},250);
            }else if($(this).children(".FromFontColorClick").length==0){
                $(".parentFrom").removeClass("parentFrom_little");
                $(this).find(".parentFrom").addClass("parentFrom_little");
                $(".FromFontColor").removeClass("FromFontColorClick");
                $(this).find(".FromFontColor").addClass("FromFontColorClick");
                $(".menu").animate({
                    paddingTop:"70px",
                    height:"112px",
                    paddingBottom:"0px"
                });
                $(".parentFrom").animate({
                    top:"20px",
                    left:Fromleft+"px"
                });
                $(".FromFont").animate({
                    marginLeft:FromFontMargin+"px"
                });
                $(".FromFont1").animate({
                    marginLeft:FromFontMargin1+"px"
                })		;
                $(this).animate({
                    paddingTop:"20px",
                    height:"50px",
                    paddingBottom:"10px"
                });
                $(this).find(".parentFrom").animate({
                    top:"8px",
                    left:"10px"
                })
                $(this).find(".FromFont").animate({
                    marginLeft:"50px"
                })	;
                $(this).find(".FromFont1").animate({
                    marginLeft:"50px"
                })	;
                $(".menu").removeClass("menuClick")	;
                thisObj =this;
                setTimeout(function(){$(thisObj).addClass("menuClick")},700);
            }else{
                $(".menu").removeClass("menuClick")	;
                $(".parentFrom").removeClass("parentFrom_little");
                $(".FromFontColor").removeClass("FromFontColorClick");
                $(".menu").animate({
                    paddingTop:"70px",
                    height:"112px",
                    paddingBottom:"0px"
                });
                $(".parentFrom").animate({
                    top:"20px",
                    left:Fromleft+"px"
                });
                $(".FromFont").animate({
                    marginLeft:FromFontMargin+"px"
                })	;
                $(".FromFont1").animate({
                    marginLeft:FromFontMargin1+"px"
                })	;
            }
        })
    })
});