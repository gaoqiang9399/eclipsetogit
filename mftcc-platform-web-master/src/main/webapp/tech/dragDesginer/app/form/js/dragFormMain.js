define(function(require, exports, module) {
    //公用模块
    var $ = require("jquery");
    require("pace/js/pace.js");//加载效果
    require("drag/js/redips-drag-source.js");
    require("bootstrap/js/bootstrap.min.js");
    require("jquery/jquery.transit-0.9.12-min.js");
    require("../../common/js/loadFunction.js");
    //引入jquery UI
    //require("jquery-ui/js/jquery-ui.min.js");
    //日期效果
    require("datetimepicker/js/jquery.datetimepicker.js");
    require("easydropdown/js/jquery.easydropdown.js");
    require("customScrollbar/js/jquery.mCustomScrollbar.js");
    //require("json/json.js");
    require("json/json2.js");
    //控制台模块
    require("./console.js");
    //属性模块
    require("./formAttributeSetting.js");
    //验证公式模块
    require("./validate.js");

    require("./myDragDiv.js");
    require("./tableMerger.js");
    //组件操作
    var dataTypeEvent = require("./dataTypeEvent.js");
	//右键控制模块
    var rightClick = require("./rightClick.js");
    //form属性设置模块
    var formDataCtrl = require("./formDataCtrl.js");
    var commonFunction = require("../../common/js/commonFunction.js");
    var myAlert = require("../../common/js/myAlert.js");
    var initFormSetting = require("./initFormSetting.js");
    var tablePosition = require("./tablePosition.js");
    var tableMerger = require("./tableMerger.js");
    // global variables
    var redips = {},		// redips container
        rd = REDIPS.drag,	// reference to the REDIPS.drag library
        counter = 0,		// counter for cloned DIV elements
        clonedDIV = false,	// cloned flag set in event.moved
        lastCell;			// reference to the last cell in table
    var dragAddFlag = false,//判断是否是新增拖动
        fieldType="",
        selRange = "",
        targetName = "",
        moveFlag = false,
        targetId = "",
        objFlag = false,//判断标准组件(false)还是 基本组件(true)
        colSpanNo = 0, //夸列数
        dargType = "",//拖拽类型
        mosueDowFlag = false,//鼠标按下标志
        isCombination = false,//是否组合
        pWidth = false,//设定宽度
        droppedFlag = false,//拽完标志
        rdLTdColspan = 0,//左夸列
        rdRTdColspan = 0,//右夸列
        rdTdRowspan = 0,//跨行
        dbclickFlag = false,//双击标志
        leftOrRight = false,//左或右
    	dragerTimer;//拖拽动态调整滚动条计时器
    // drag初始化开始
    window.initForm = function () {
        // set reference to the last cell in table
       // lastCell = document.getElementById('lastCell');
        rd.style.borderEnabled = "none";      //元素周围不添加边框  先设置参数  再初始化
        // initialization
        rd.init();
        // shift DIV elements with animation
        // disabled elements will have opacity effect
        rd.style.opacityDisabled = 50;
        // set hover color
        rd.hover.colorTd = '#9BB3DA';
        // enable cloning DIV elements with pressed SHIFT key
        rd.clone.keyDiv = true;
        rd.dropMode = 'single';
        //rd.dropMode = 'shift';
        rd.animation.step = 1000;   //移动速度
        //拖动前
        rd.event.droppedBefore = function (targetCell) {
            fieldType = formDataCtrl.getFieldType(rd.obj);
            if(fieldType!=""){
                dragAddFlag = true;
                selRange =  formDataCtrl.judgeSelRange(rd.obj);
            }else{
                dragAddFlag = false;
            }
            targetId = $(targetCell).children(".drag").attr("id");
        };
        //拖动之后
        rd.event.dropped = function(targetCell){
            droppedFlag = true;
            //console.log("droppedFlag："+droppedFlag);
            tableMerger.removeAppendOther(rd,isCombination,pWidth,droppedFlag);
            //查看当前是否有"cloneDisplayObj"  如果有则说明是从左边工具栏拖拽过来的
            var cloneDisplayObj = $(rd.obj).find(".cloneDisplayObj");
            var dataType ="";
            var empty;
            if(cloneDisplayObj.length != 0){
                var reallyObj = $(rd.obj).find(".reallyObj");
                dataType = $(reallyObj).attr("data-type");
                rd.obj.innerHTML = $.trim(reallyObj.html());
                //将元素拖动后  获取拖拽的内容
                empty = rd.emptyCell(targetCell, 'test');
                dataTypeEvent.toolDataTypeEvent(targetCell,rd,empty,dataType,rdLTdColspan,rdRTdColspan,rdTdRowspan);
            }else{ //table中元素的拖拽
                dataType = $(rd.obj).find(".dataType").attr("data-type");
                //将元素拖动后  获取拖拽的内容
                empty = rd.emptyCell(targetCell, 'test');
                // if target cell is not empty
                dataTypeEvent.tableDataTypeEvent(targetCell,rd,empty,dataType,rdLTdColspan,rdRTdColspan,rdTdRowspan);
            }
            var $parentTd = $(rd.obj).parents("td");
            var rdId = $(rd.obj).attr("id");
            var formActiveId = $.trim(rdId.split("_")[1]);
            var formId = commonFunction.getFileName("form");
            var rowNo = tablePosition.getRowIndex($parentTd);
            if(tablePosition.isBartitle()){//判断是否存在标题
                rowNo--;
            }
            var colNo = tablePosition.getColIndex($parentTd);
            if($(rd.obj).find("div").attr("data-type").indexOf("Right")!=-1){
                if(isCombination=="right"){
                    colNo=colNo-rdLTdColspan;
                }else{
                    colNo=colNo-rdLTdColspan-rdRTdColspan;
                }
            }
            //移动定位
            if(dragAddFlag){
                var labelColSpan="1";
                var titleFlag = false;
                if(fieldType=="00"){
                    labelColSpan = tablePosition.getMaxTdLength();/*-1;*/
                    titleFlag = true;
                }
                if(fieldType=="xx"){
                    var fieldName = $.trim($(rd.obj).find(".tdlable").attr("fieldname"));
                    tablePosition.dragAddPositionForModelTool(formId,fieldName,rowNo,colNo);
                }else{
                    var labelName = $.trim($(rd.obj).find(".tdlable").text());
                    //console.log($(rd.obj).html());
                    fieldType = formDataCtrl.judgeFileIdType(fieldType);
                    tablePosition.dragAddPosition(formId,rowNo,colNo,fieldType,dataType,selRange,labelColSpan,targetName,labelName,titleFlag);
                }
            }else if(targetId===undefined||formActiveId!= $.trim(targetId.split("_")[1])){
                tablePosition.dragChangePosition(formId,formActiveId,rowNo,colNo);
            }else{
                initframe();
            }
        }
        //复制/克隆
        rd.event.cloned = function () {
        };
        rd.event.finish = function () {
            if(isCombination=="left") {
                $(rd.obj).parents("td").addClass("tdleftborder");
                $(rd.obj).find(".leftborder1 ").addClass("tdmoveborder");
            }else if(isCombination=="right") {
                $(rd.obj).parents("td").prev().addClass("tdleftborder");
                $(rd.obj).parents("td").prev().find(".drag>.leftborder1 ").addClass("tdmoveborder");
            }else{
                if(leftOrRight=="left"){
                    $(rd.obj).parents("td").addClass("tdleftborder");
                    $(rd.obj).find(".leftborder1 ").addClass("tdmoveborder");
                }
            }
            setTimeout(function(){
                if(moveFlag){
                    //tableMerger.removeRowCol("table2");
                }
                tableMerger.resetDragWidth($(rd.obj));
            },400);
            formDataCtrl.removeFlagClass();
            $(rd.obj).find(".blackBgColor").removeClass("blackBgColor");
            $(rd.obj).find(".blackBgColorR").removeClass("blackBgColorR");
            $(rd.obj).find(".blackBgColorL").removeClass("blackBgColorL");
            mosueDowFlag = false;
            //console.log("finish-droppedFlag:"+droppedFlag);
            if(droppedFlag==false){
                tableMerger.removeAppendOther(rd,isCombination,pWidth,droppedFlag);
            }else{
                droppedFlag=false;
            }
            setTimeout(function(){
                if(isCombination=="left") {
                    $(rd.obj).find(".leftborder1 ").removeClass("tdmoveborder");
                }else if(isCombination=="right") {
                    $(rd.obj).parents("td").prev().find(".drag>.leftborder1 ").removeClass("tdmoveborder");
                }else{
                    if(leftOrRight=="left"){
                        $(rd.obj).find(".leftborder1 ").removeClass("tdmoveborder");
                    }
                }
            },200);
            if(dragerTimer!==undefined&&dragerTimer!=null){//关闭计时器
            	clearInterval(dragerTimer);
            	dragerTimer = null;
            }
        };
        //移动
        rd.event.moved = function (cloned) {
            //tableMerger.appendRowCol("table2");
            //tableMerger.setDragWidth($(rd.obj));
            clonedDIV = cloned;
            moveFlag = true;
            removeSelectTd();
            var $dragObj = $(rd.obj);
        	dragerTimer = setInterval(function(){
        		var dargShowHeight = $(".mainContent").height();
        		//拖拽对象位置
        		var objTop = $dragObj.offset().top;
        		//拖拽对象高度
        		var objHeight = $dragObj.height();
        		//拖拽对象居左位置
        		var objLeft = $dragObj.offset().left;
        		var $mCSB_container = $(".tableShow").find(".mCSB_container");
        		var	dragerTop = $mCSB_container.position().top;
        		dragerTop = Math.abs(dragerTop);
        		var actualHeight = $mCSB_container.height();
        		var showHeight = $(".tableShow.mCustomScrollbar").height();
        		
        		//滚动条页面整体高度
        		var dragerAllheight = $(".tableShow").find(".mCSB_draggerContainer").height();
        		//滚动条高度
        		var $mCSB_dragger = $(".tableShow").find(".mCSB_dragger");
        		var dragerheight = $mCSB_dragger.height();
        		var proportion = (dragerAllheight-dragerheight)/(actualHeight-showHeight);
        		var movePx = function(topPx,proportion){
        			$mCSB_container.css({top:-topPx});
        			$mCSB_dragger.css({top:topPx*proportion});
        		}
        		if(objTop-20<=0){//上移滚动条
        			dragerTop = dragerTop-10>0?dragerTop-10:0;
        			movePx(dragerTop,proportion);
        		}else if(dargShowHeight-objTop-objHeight-20<=0){//下移
        			dragerTop+=10;
        			if(showHeight>actualHeight-dragerTop){
        				dragerTop = actualHeight-showHeight;
        			}
        			movePx(dragerTop,proportion);
        		}
        		$(".tableShow").mCustomScrollbar("update");
			},50);
        };
        rd.event.clicked = function(){
        	cancelFormIdTableIdUpdate();
            removeSelectTd();
            rdLTdColspan = 0;
            rdRTdColspan = 0;
            rdTdRowspan = 0;
            pWidth = tableMerger.setDragWidth($(rd.obj));
            var rdId= $(rd.obj).parents(".panel-collapse").attr("id");
            if(rdId=="collapseOne"){
                targetName = "base";
            }else{
                targetName = "";
            }
            if($(rd.obj).parents("#table2").length>0) {
                /*isCombination = */tableMerger.setAppendOther(rd.obj, pWidth);
                isCombination = tablePosition.isCombination($(rd.obj).parents("td"));
                leftOrRight = tablePosition.isLeftOrRight($(rd.obj).parents("td"));
            }
            if(isCombination=="left"){
                rdLTdColspan = $(rd.obj).parents("td").attr("colspan")!==undefined?parseInt($(rd.obj).parents("td").attr("colspan")):1;
                rdTdRowspan = $(rd.obj).parents("td").attr("rowspan")!==undefined?parseInt($(rd.obj).parents("td").attr("rowspan")):1;
                rdRTdColspan = $(rd.obj).parents("td").next("td").attr("colspan")!==undefined?parseInt($(rd.obj).parents("td").next("td").attr("colspan")):1;

            }else if(isCombination=="right"){
                rdLTdColspan = $(rd.obj).parents("td").prev("td").attr("colspan")!==undefined?parseInt($(rd.obj).parents("td").prev("td").attr("colspan")):1;
                rdTdRowspan = $(rd.obj).parents("td").prev("td").attr("rowspan")!==undefined?parseInt($(rd.obj).parents("td").prev("td").attr("rowspan")):1;
                rdRTdColspan = $(rd.obj).parents("td").attr("colspan")!==undefined?parseInt($(rd.obj).parents("td").attr("colspan")):1;
            }
            if(leftOrRight=="left"){
                rdLTdColspan = $(rd.obj).parents("td").attr("colspan")!==undefined?parseInt($(rd.obj).parents("td").attr("colspan")):1;
                rdTdRowspan = $(rd.obj).parents("td").attr("rowspan")!==undefined?parseInt($(rd.obj).parents("td").attr("rowspan")):1;
            }else if(leftOrRight=="right"){
                rdRTdColspan = $(rd.obj).parents("td").attr("colspan")!==undefined?parseInt($(rd.obj).parents("td").attr("colspan")):1;
                rdTdRowspan = $(rd.obj).parents("td").attr("rowspan")!==undefined?parseInt($(rd.obj).parents("td").attr("rowspan")):1;
            }
            //console.log("rdLTdColspan:"+rdLTdColspan+"，rdRTdColspan:"+rdRTdColspan);
        }
        //双击
        rd.event.dblClicked = function(){
            dbclickFlag = true;
            //清理其他点击的单元格
            var parentFlag = $(rd.obj).parents(".mainContent");
            if(parentFlag.length!=0){
                //获取单元格当前高度
                var $parentTd = $(rd.obj).parents("td");
                var tdTop = $parentTd.position().top;
                //获取弹出层的高度与工具栏的高度
                //当前td为选中状态
                setSelectTd(rd.obj,isCombination);
                var rdObj = $(rd.obj).attr("id");
                var formActiveId = $.trim(rdObj.split("_")[1]);
                setAttrbuteId(formActiveId);//设置属性框的ID
                var formId = commonFunction.getFileName("form");
                if(formId!=false){
                    formDataCtrl.AttributeEvaluation(formId,formActiveId);
                }
            }
        };
        rd.event.handlerOnMouseDown =function(){
            if( $("#rightPrompt").css("display")=="block"){
                //隐藏右键工具条
                $("#rightPrompt").css({display:"none"});
                $(".rightClick").removeClass("rightClick");
            }
            objFlag = formDataCtrl.getGroupOrSingle(rd.obj);//获取拖拽类型 left right head
            colSpanNo = formDataCtrl.getColSpanNo(rd.obj);
            dargType = formDataCtrl.getFieldType(rd.obj);
            if( $(rd.obj).parents("#table2").length>0){
                if($(rd.obj).find(".dataType").length>1){
                    $(rd.obj).find(".dataType").eq(0).addClass("blackBgColorL");
                    $(rd.obj).find(".dataType").eq(1).addClass("blackBgColorR");
                }else{
                    $(rd.obj).find(".dataType").addClass("blackBgColor");
                }
            }
            mosueDowFlag = true;
        }
        rd.event.dragOnMouseOver = function(obj){
            if($(obj).parents("#table2").length>0){
                if (!mosueDowFlag) {
                    //var objWidth = $(obj).parents("td").width();
                    //var objHeight = $(obj).parents("td").height();
                    var objWidth = $(obj).width();
                    var objHeight = $(obj).height();
                    var tdOldObj = obj;
                    obj =$(obj).find(".drag");
                    var i;
                    var showFlag,checkColCount,trIndex;
                    if ($(".addRowCol").length > 0) {
                        var colNo = tablePosition.getColIndex($(".addRowCol").parents("td"));
                        var colNo1 = tablePosition.getColIndex(tdOldObj);
                        if (colNo != colNo1 || $(".addRowCol").parents("tr").index() != $(tdOldObj).parents("tr").index()) {
                            $(".addRowCol span,.addRowCol").off("mouseover");
                            $(".addRowCol").removeAttr("sytle");
                            $(".addRowCol").remove();
                            $(".formDelCtrl").remove();
                            $("#delRowCtrl").find(".delRowCtrl").find("span").css("display","none");
                            //$("#delColCtrl").find(".delColCtrl").find("span").css("display","none");
                        }
                    }else if($(obj).html()!==undefined){
                        var combinationFlag = tablePosition.isCombination($(obj).parents("td"));//组合标志
                        var delStr ='<div onclick="delCurrentField(this);" class="tools2 formDelCtrl">';
                        delStr +='<b sign="trash" class="cC0 nui-ico nui-ico-smartTrash tableTrash">';
                        delStr +='<b class="nui-ico nui-ico-smartTrash-head"></b>';
                        delStr +='<b class="nui-ico nui-ico-smartTrash-body"></b>';
                        delStr +=' </b>';
                        delStr +='</div>';
                        var showStr = "";
                        var tdRowSpan =-1;//跨行
                        var tdColSpan =-1;//跨列
                        if($(obj).parents("td").attr("rowspan")!==undefined&&$(obj).parents("td").attr("rowspan")>1){
                            tdRowSpan = $(obj).parents("td").attr("rowspan");
                        }
                        if($(obj).parents("td").attr("colspan")!==undefined&&$(obj).parents("td").attr("colspan")>1){
                            tdColSpan = $(obj).parents("td").attr("colspan");
                        }
                        trIndex = $(obj).parents("tr").index();

                        var tdIndex = tablePosition.getColIndex($(obj).parents("td"));
                        var trNextTdIndex = tablePosition.getColIndexActualIndex($(obj).parents("tbody").find("tr").eq(trIndex+(tdRowSpan!=-1?tdRowSpan:1)),tdIndex);
                        var trPrevTdIndex = -1;
                        if(trIndex>0){
                            trPrevTdIndex = tablePosition.getColIndexActualIndex($(obj).parents("tr").prev("tr"),tdIndex);
                        }
                        var tdPrevIndex = -1;
                        if($(obj).parents("td").index()>0){
                            tdPrevIndex = tablePosition.getColIndexActualIndex($(obj).parents("tr"),tdIndex-1);
                        }
                        var tdNextIndex = -1;
                        if($(obj).parents("td").next("td").html()!==undefined){
                            tdNextIndex = tablePosition.getColIndexActualIndex($(obj).parents("tr"),tdIndex+parseInt((tdColSpan!=-1?tdColSpan:1)));
                        }
                        //console.log("tdRowSpan:"+tdRowSpan+",tdColSpan:"+tdColSpan);
                        //console.log("trPrevTdIndex:"+trPrevTdIndex+",trNextTdIndex:"+trNextTdIndex+",tdPrevIndex:"+tdPrevIndex+",tdNextIndex:"+tdNextIndex);
                        //----------------
                        showStr+='<div class="addRowCol current" style="top: -2px;left:' + (objWidth/2) + 'px;">';
                        if(trPrevTdIndex!=-1){
                            showFlag = false;//显示标志 false(不显示)/true(显示)
                            var trPrevTdStr = $.trim($(obj).parents("tr").prev("tr").find("td").eq(trPrevTdIndex-1).html());
                            showFlag = isNotNull(trPrevTdStr)==false?true:false;
                            checkColCount = 0;
                            if(showFlag==true) {
                                if (combinationFlag=="left") {
                                    checkColCount = parseInt(getColSpan($(obj).parents("td").prev("td"))) +
                                    parseInt(getColSpan($(obj).parents("td").next("td")));
                                    for (i = 1; i < checkColCount; i++) {
                                        if(isNotNull($(obj).parents("tr").prev("tr").find("td").eq(trPrevTdIndex-1+i).html())==true){
                                            showFlag = false;
                                            break;
                                        }
                                    }
                                } else if (combinationFlag=="right") {
                                    checkColCount = parseInt(getColSpan($(obj).parents("td")));
                                    for(i = 1; i < checkColCount; i++){
                                        if(isNotNull($(obj).parents("tr").prev("tr").find("td").eq(trPrevTdIndex-1+i).html())==true){
                                            showFlag = false;
                                            break;
                                        }
                                    }
                                    checkColCount = parseInt(getColSpan($(obj).parents("td").prev("td")));
                                    for (i = 1; i <=checkColCount; i++) {
                                        if(isNotNull($(obj).parents("tr").prev("tr").find("td").eq(trPrevTdIndex-1-i).html())==true){
                                            showFlag = false;
                                            break;
                                        }
                                    }
                                }else{
                                    checkColCount = parseInt(getColSpan($(obj).parents("td")));
                                    for (i = 1; i < checkColCount; i++) {
                                        if(isNotNull($(obj).parents("tr").prev("tr").find("td").eq(trPrevTdIndex-1+i).html())==true){
                                            showFlag = false;
                                            break;
                                        }
                                    }
                                }
                            }
                            //console.log("trPrevTdStr:"+trPrevTdStr+";");
                            if(showFlag==true){
                                showStr+='<span onclick="changRowCol(this,\'plus\',\'row\',\'up\')" class="fa fa-sort-asc"></span>';
                            }
                        }
                        if(tdRowSpan!=-1){
                            showStr+='<span onclick="changRowCol(this,\'subtract\',\'row\',\'down\')" class="fa fa-sort-desc" style="left:8px;top:-9px"></span>';
                        }
                        showStr+='</div>';
                        //-------------
                        showStr+='<div class="addRowCol current" style=" bottom:15px;left:'+(objWidth/2)+'px;">';
                        if(tdRowSpan!=-1) {
                            showStr+='<span onclick="changRowCol(this,\'subtract\',\'row\',\'up\')" class="fa fa-sort-asc" style="left:8px;bottom:-15px"></span>';
                        }
                        if(trNextTdIndex!=-1){
                            showFlag = false;//显示标志 false(不显示)/true(显示)
                            var nextTrNo = parseInt(trIndex)+parseInt(tdRowSpan!=-1?tdRowSpan:1);
                            var trNextTdStr = $.trim($(obj).parents("tbody").find("tr").eq(nextTrNo).find("td").eq(trNextTdIndex-1).html());
                            showFlag = isNotNull(trNextTdStr)==false?true:false;
                            checkColCount = 0;
                            if(showFlag==true) {
                                if (combinationFlag=="left"){
                                    checkColCount = parseInt(getColSpan($(obj).parents("td"))) +
                                    parseInt(getColSpan($(obj).parents("td").next("td")));
                                    for (i = 1; i < checkColCount; i++) {
                                        if(isNotNull($(obj).parents("tbody").find("tr").eq(nextTrNo).find("td").eq(trNextTdIndex-1+i).html())==true){
                                            showFlag = false;
                                            break;
                                        }
                                    }
                                } else if (combinationFlag=="right"){
                                    checkColCount = parseInt(getColSpan($(obj).parents("td")));
                                    for(i = 1; i < checkColCount; i++){
                                        if(isNotNull($(obj).parents("tbody").find("tr").eq(nextTrNo).find("td").eq(trNextTdIndex-1+i).html())==true){
                                            showFlag = false;
                                            break;
                                        }
                                    }
                                    checkColCount = parseInt(getColSpan($(obj).parents("td").prev("td")));
                                    for (i = 1; i <=checkColCount; i++) {
                                        if(isNotNull($(obj).parents("tbody").find("tr").eq(nextTrNo).find("td").eq(trNextTdIndex-1-i).html())==true){
                                            showFlag = false;
                                            break;
                                        }
                                    }
                                }else{
                                    checkColCount = parseInt(getColSpan($(obj).parents("td")));
                                    for (i = 1; i < checkColCount; i++) {
                                        if(isNotNull($(obj).parents("tbody").find("tr").eq(nextTrNo).find("td").eq(trNextTdIndex-1+i).html())==true){
                                            showFlag = false;
                                            break;
                                        }
                                    }
                                }
                            }
                            //console.log("trNextTdStr:"+trNextTdStr+";");
                            if(showFlag==true){
                                showStr+='<span onclick="changRowCol(this,\'plus\',\'row\',\'down\')" class="fa fa-sort-desc"></span>';
                            }
                        }
                        showStr+='</div>';
                        //---------------
                        showStr+='<div class="addRowCol current" style="left:-1px ;top:' + (objHeight/2-8) + 'px;">';
                        if(tdPrevIndex!=-1){
                            showFlag = false;//显示标志 false(不显示)/true(显示)
                            var tdPrevStr = $.trim($(obj).parents("td").prev("td").html());
                            showFlag = isNotNull(tdPrevStr)==false?true:false;
                            if(showFlag==true){
                                if(tdRowSpan!=-1){
                                    for(i=1;i<tdRowSpan;i++){
                                        var trPrevNextTdIndex = tablePosition.getColIndexActualIndex($(obj).parents("tbody").find("tr").eq(trIndex+i),tdIndex-1);
                                        if(trPrevNextTdIndex==-1||isNotNull($(obj).parents("tbody").find("tr").eq(trIndex+i).find("td").eq(tdIndex-2).html())==true){
                                            showFlag = false;
                                            break;
                                        }
                                    }
                                }
                            }
                            //console.log("tdPrevStr:"+tdPrevStr+";");
                            if(showFlag==true){
                                showStr+='<span onclick="changRowCol(this,\'plus\',\'col\',\'left\')" class="fa fa-caret-left"></span>';
                            }
                        }
                        if(tdColSpan!=-1){
                            showStr+='<span onclick="changRowCol(this,\'subtract\',\'col\',\'right\')" class="fa fa-caret-right" style="margin-left:7.5px"></span>';
                        }
                        showStr+='</div>';
                        //---------
                        showStr+='<div class="addRowCol current" style="right:6px;top:' + (objHeight/2-8) + 'px;">';
                        if(tdColSpan!=-1){
                            showStr+='<span onclick="changRowCol(this,\'subtract\',\'col\',\'left\')" class="fa fa-caret-left" style="right:2px"></span>';
                        }
                        if(tdNextIndex!=-1){
                            showFlag = false;//显示标志 false(不显示)/true(显示)
                            var tdNextStr = $.trim($(obj).parents("tr").find("td").eq(tdNextIndex-1).html());
                            showFlag = isNotNull(tdNextStr)==false?true:false;
                            if(showFlag==true){
                                if(tdRowSpan!=-1){
                                    for(i=1;i<tdRowSpan;i++){
                                        var trNextNextTdIndex = tablePosition.getColIndexActualIndex($(obj).parents("tbody").find("tr").eq(trIndex+i),tdIndex+parseInt(tdColSpan!=-1?tdColSpan:1));
                                        if(trNextNextTdIndex==-1||isNotNull($(obj).parents("tbody").find("tr").eq(trIndex+i).find("td").eq(trNextNextTdIndex).html())==true){
                                            showFlag = false;
                                            break;
                                        }
                                    }
                                }
                            }
                            //console.log("tdNextStr:"+tdNextStr+";");
                            if(showFlag==true){
                                showStr+='<span onclick="changRowCol(this,\'plus\',\'col\',\'right\')" class="fa fa-caret-right"></span>';
                            }
                        }
                        showStr+='</div>';
                        $(obj).parents("td").append(showStr);
                        $(obj).parents("td").append(delStr);
                        $("#delRowCtrl").find(".delRowCtrl").find("span").css("display","none");
                        $("#delRowCtrl").find(".delRowCtrl").eq($(obj).parents("tr").index()).find("span").css("display","block");
                        //$("#delColCtrl").find(".delColCtrl").eq(tablePosition.getColIndex($(obj).parents("td"))-1).find("span").css("display","block");
                    }else{
                        trIndex = $(tdOldObj).parents("tr").index();
                        //var tdIndex = tablePosition.getColIndex(tdOldObj);
                        $("#delRowCtrl").find(".delRowCtrl").find("span").css("display","none");
                        $("#delRowCtrl").find(".delRowCtrl").eq(trIndex).find("span").css("display","block");
                    }
                }else{
                    $(".addRowCol span,.addRowCol").off("mouseover");
                    $(".addRowCol").removeAttr("sytle");
                    $(".addRowCol").remove();
                    $(".formDelCtrl").remove();
                    $("#delRowCtrl").find(".delRowCtrl").find("span").css("display","none");
                    //$("#delColCtrl").find(".delColCtrl").find("span").css("display","none");
                }
            }else{
                //console.log("else--->");
            }
        }
        rd.event.setUsableTd =function(table,row,cell,color){
            if(isCombination=="left") {
                $(rd.obj).parents("td").removeClass("tdleftborder");
            }else if(isCombination=="right") {
                $(rd.obj).parents("td").prev().removeClass("tdleftborder");
            }else{
                if(leftOrRight=="left"){
                    $(rd.obj).parents("td").removeClass("tdleftborder");
                }
            }
            var tdMaxNo = tablePosition.getMaxTdLength();
            formDataCtrl.removeFlagClass();
            var tdObj = $("#table2").find("tbody").find("tr").eq(row).find("td").eq(cell);
            var rdAlgin = $.trim($(rd.obj).attr("id").split("_")[0]);
            var colFlag = false;
            var flagShow;
            if(rdAlgin=="left"){
                flagShow = dataTypeEvent.checkNextTrandTdLeftShow(tdObj,row,cell,rd,rdLTdColspan,rdRTdColspan,rdTdRowspan);
                if(!flagShow){
                   $(rd.obj).parents("td").addClass("oneBroder");
                   //console.log(trim($(rd.obj).attr("id").split("_")[1])+"---"+$.trim($(rd.obj).parent("td").next().find(".drag").attr("id").split("_")[1]));
                   if($(rd.obj).parent("td").next().html()!==undefined&&$(rd.obj).parent("td").next().find(".drag").length>0&& $.trim($(rd.obj).attr("id").split("_")[1])==
                       $.trim($(rd.obj).parent("td").next().find(".drag").attr("id").split("_")[1])){
                       $(rd.obj).parent("td").next().addClass("oneBroder");
                   }
               }
            }else if(rdAlgin=="right") {
                flagShow = dataTypeEvent.checkNextTrandTdRightShow(tdObj, row, cell, rd, rdLTdColspan, rdRTdColspan, rdTdRowspan);
                if (!flagShow) {
                    $(rd.obj).parents("td").addClass("oneBroder");
                    if ($(rd.obj).parent("td").prev().html() !== undefined && $(rd.obj).parent("td").prev().find(".drag").length > 0 && $.trim($(rd.obj).attr("id").split("_")[1]) ==
                        $.trim($(rd.obj).parent("td").prev().find(".drag").attr("id").split("_")[1])) {
                        $(rd.obj).parent("td").prev().addClass("oneBroder");
                    }
                }
            }else if(objFlag==true) {
                flagShow = dataTypeEvent.checkDragShow(tdObj, row, cell, rd);
                if (flagShow) {
                    $(tdObj).addClass("oneBroder");
                    $(tdObj).next().addClass("oneBroder");
                }
            }else if(objFlag == "one") {
                $(tdObj).addClass("oneBroder");
            }else if(objFlag == "head") {
                var headFlag = true;
                $(tdObj).parents("tr").find("td").each(function(){
                    if($.trim($(this).html())!=""){
                        headFlag = false;
                    }
                });
                if(headFlag) {
                    $(tdObj).parents("tr").find("td").addClass("oneBroder");
                }
            }else if(objFlag == "custom") {
                $(tdObj).addClass("oneBroder");
            }
        }
    };
    //后期样式更改代码
    window.change_tab = function(class_str){
        $(".open_tab").addClass("colse_tab").removeClass("open_tab");
        $("."+class_str).removeClass("colse_tab").addClass("open_tab");
    }
    //后期样式更改代码
    window.initChange_tab = function(){
       var class_str =  window.parent.window.$(".title_choice").attr("role");
        $(".open_tab").addClass("colse_tab").removeClass("open_tab");
        $("."+class_str).removeClass("colse_tab").addClass("open_tab");
    }
    /***
     * form 字段更新
     */
    window.formAttributeUpdate = function(){
        var formId = commonFunction.getFileName("form");
        var formActiveId = commonFunction.getFormActiveId();
        if(formActiveId==false){
            $.myAlert.Alert("请打开字段","提示信息");
        }else{
            formDataCtrl.AttributeUpdate(formId,formActiveId);
        }
    }
    window.selectInput = function(){};//防止方法不存在报错
    window.func_uior_valTypeImm = function(){};//防止方法不存在报错
    window.enterKey = function(){};//防止方法不存在报错
    window.resetTimes = function(){};//防止方法不存在报错
    $(function(){
        //给主窗体赋值高度
        $(".mainContent").css("height", $("body").height()+"px");   //5px  mainContent  margin-top的值
       if($("#table2").height()>$("body").height()-155){
           $(".tableShow").css("height",($("body").height()-155)+"px");
       }else{
           $(".tableShow").css("height","auto");
       }
        initframe();
        $(".left-nav").mCustomScrollbar({
            theme:"dark-5",
            scrollButtons:{
                enable:false,
                autoHideScrollbar: true
            },
            advanced:{
                updateOnBrowserResize:true,
                updateOnContentResize:true
            }
        });
        $(".tableShow,#calculationInsertSelect .showSelect").mCustomScrollbar({
	        theme:"dark",
	        scrollButtons:{
	            enable:false,
	            autoHideScrollbar: true
	        },
	        advanced:{
	            updateOnBrowserResize:true,
	                updateOnContentResize:true
	        }
	
	    });

        $(window).resize(function () {
            $(".mainContent").css("height", $("body").height()+"px");
            if($("#table2").height()>$("body").height()-155){
                $(".tableShow").css("height",($("body").height()-155)+"px");
            }else{
                $(".tableShow").css("height","auto");
            }
        });
        $("#labelWidth,#fieldWidth,#textAreaFieldRows,#row,#labelCol,#fieldCol,#rowSpan,#labelColSpan,#fieldColSpan," +
        "#labelStyle,#fieldStyle,#initValue,#unit,#fieldSize,#para,#maxLength,#labelLink,#buttonCondition,#alt,#writeAuth,#cutOff").change(function(){
            formAttributeUpdate();
        });

        var w = $(".menu").outerWidth(true);
        var Fromleft = w / 2 - 19;
        $(".parentFrom").css("left", Fromleft + "px");
        var FromFontMargin = (w - 60) / 2;
        var FromFontMargin1 = (w - 74) / 2;
        $(".FromFont").css("marginLeft", FromFontMargin + "px");
        $(".FromFont1").css("marginLeft", FromFontMargin1 + "px");
        $(".menu").eq(0).find(".parentFrom").css("top", "8px");
        $(".menu").eq(0).find(".parentFrom").css("left", "10px");
        $(".menu").eq(0).find(".FromFont").css("marginLeft", "50px");
        $(".menu").click(function () {
            var thisObj;
            if (!$("body").find(".menu").hasClass("menuClick")) {
                $(this).find(".parentFrom").addClass("parentFrom_little");
                $(this).find(".FromFontColor").addClass("FromFontColorClick");
                $(this).animate({
                    paddingTop: "20px",
                    height: "50px",
                    paddingBottom: "10px"
                });
                $(this).find(".parentFrom").animate({
                    top: "8px",
                    left: "10px"
                });
                $(this).find(".FromFont").animate({
                    marginLeft: "50px"
                });
                $(this).find(".FromFont1").animate({
                    marginLeft: "50px"
                });
                thisObj = this;
                setTimeout(function () {
                    $(thisObj).addClass("menuClick");
                }, 250);
            } else if ($(this).children(".FromFontColorClick").length == 0) {
                $(".parentFrom").removeClass("parentFrom_little");
                $(this).find(".parentFrom").addClass("parentFrom_little");
                $(".FromFontColor").removeClass("FromFontColorClick");
                $(this).find(".FromFontColor").addClass("FromFontColorClick");
                $(".menu").animate({
                    paddingTop: "70px",
                    height: "112px",
                    paddingBottom: "0px"
                });
                $(".parentFrom").animate({
                    top: "20px",
                    left: Fromleft + "px"
                });
                $(".FromFont").animate({
                    marginLeft: FromFontMargin + "px"
                });
                $(".FromFont1").animate({
                    marginLeft: FromFontMargin1 + "px"
                });
                $(this).animate({
                    paddingTop: "20px",
                    height: "50px",
                    paddingBottom: "10px"
                });
                $(this).find(".parentFrom").animate({
                    top: "8px",
                    left: "10px"
                })
                $(this).find(".FromFont").animate({
                    marginLeft: "50px"
                });
                $(this).find(".FromFont1").animate({
                    marginLeft: "50px"
                });
                $(".menu").removeClass("menuClick");
                thisObj = this;
                setTimeout(function () {
                    $(thisObj).addClass("menuClick");
                }, 700);
            } else {
                $(".menu").removeClass("menuClick");
                $(".parentFrom").removeClass("parentFrom_little");
                $(".FromFontColor").removeClass("FromFontColorClick");
                $(".menu").animate({
                    paddingTop: "70px",
                    height: "112px",
                    paddingBottom: "0px"
                });
                $(".parentFrom").animate({
                    top: "20px",
                    left: Fromleft + "px"
                });
                $(".FromFont").animate({
                    marginLeft: FromFontMargin + "px"
                });
                $(".FromFont1").animate({
                    marginLeft: FromFontMargin1 + "px"
                });
            }
        });
    })
});