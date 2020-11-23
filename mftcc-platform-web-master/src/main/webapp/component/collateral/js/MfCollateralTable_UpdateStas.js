$(function(){
	LoadingAnimate.start();
	$.each(collateralTableList,function(i,collateralTable){
		collateralNo = collateralTable.collateralNo;
		//collateralType = collateralTable.collateralType;
		var flag = "1";
		if(collateralTable.delFlag == "0"){
			flag = "1";		//flag干嘛的？？？？？？
		}else{
			flag = "2";
		}
		//showType=1是form，2是table。dataFullFlag=0表示未填，1表示已填。
		setBlock(collateralTable.tableName,collateralTable.tableDes,flag,collateralTable.showType,collateralTable.action,collateralTable.isMust,collateralTable.dataFullFlag);
	});
	LoadingAnimate.stop();
});
 function setBlock (tableName,tableDes,flag,showType,action,isMust,dataFullFlag){
	var	htmlStr = "";
	countt=countt+1;
	var count = countt%4+1;
	var icon="";
	var rotateBorderColor,rotateColor,rotateTubiaoBac,tubiaoBac;

	backColor="";
	if(count==1){
		icon="i-qiye";
		backColor = "#8EAFE4";
	}else if(count==2){
		icon="i-dengji";
		backColor = "#81B960";
	}else if(count==3){
		icon="i-jibenxinxi";
		backColor = "#FCB865";
	}else if(count==4){
		icon="i-caiji";
		backColor = "#5FC8DB";
	}
	/* data-tableshowtype='"+tableShowType+"' data-action='"+action+"' data-ismust='"+isMust+"' data-fullflag='"+dataFullFlag+"'  */
	if(showType=="1"&&dataFullFlag=="1"){//表单且已填 
		if(flag == "1"){
			isMustHtmlBitian = "<span class='i i-sanjiao rotate-sanjiao color_red'></span><span class='rotate-bitian'>必填</span>";
			rotateBorderColor="rotate-borderColor"+count;
			rotateColor="rotate-color"+count;
			rotateTubiaoBac="rotate-tubiaoBac"+count;
			
			if(isMust=="1"){
				htmlStr = "<div class='rotate-div' data-name='"+action+"' data-showtype='"+showType+"' data-ismust='"+isMust+"' data-tablename='"+tableName+"' data-title='"+tableDes+"'>"+
			"<div class='rotate-obj "+rotateBorderColor+"'>"+isMustHtmlBitian+
				"<div class='rotate-des "+rotateColor+"'><div class='iconCicle' style='background-color:"+backColor+"';><li class='iconStyle info-box-icon i "+icon+"'></li></div><div class='rotate-formName pull-left'>登记"+tableDes+"</div></div>" +
				"<div class='rotate-opre'>"+
				'<div class="done">该项已完善</div>'+
				"</div></div>"+"</div>";
			$("#rotate-body").append(htmlStr);
			}else{
				htmlStr = "<div class='rotate-div' data-name='"+action+"' data-showtype='"+showType+"'data-ismust='"+isMust+"' data-tablename='"+tableName+"' data-title='"+tableDes+"'>"+
				"<div class='rotate-obj "+rotateBorderColor+"'>"+
					"<div class='rotate-des "+rotateColor+"'><div class='iconCicle' style='background-color:"+backColor+"';><li class='iconStyle info-box-icon i "+icon+"'></li></div><div class='rotate-formName pull-left'>登记"+tableDes+"</div></div>" +
					"<div class='rotate-opre'>" +
					"<div class='done'>该项已完善</div>"+
					"</div>"+"</div>"+"</div>";
				$("#rotate-body").append(htmlStr);
			}	
		}else{
			tubiaoBac = "rotate-tubiaoBacH"+count;
			htmlStr = "<div class='rotate-div' data-name="+action+" data-showtype="+showType+" data-ismust="+isMust+" data-tablename='"+tableName+"' data-title='"+tableDes+"'>"+
				"<div class='rotate-obj1'>"+
				"<div class='rotate-des1'><i style='color:#706f6f' class='info-box-icon i "+icon+"'></i><div class='rotate-formName pull-left'>登记"+tableDes+"</div></div>" +
				"<div class='rotate-opre1 done'>" +
				"该项已完善"+
				"</div>"+
			 "</div>"+
			"</div>";
			$("#rotate-body-no").append(htmlStr);
		}
		//表单且已填--结束
	}else if(showType=="2"&&dataFullFlag=="1"){//列表且已填
		if(flag == "1"){
			isMustHtmlBitian = "<span class='i i-sanjiao rotate-sanjiao color_red'></span><span class='rotate-bitian'>必填</span>";
			rotateBorderColor="rotate-borderColor"+count;
			rotateColor="rotate-color"+count;
			rotateTubiaoBac="rotate-tubiaoBac"+count;
			
			if(isMust=="1"){
				htmlStr = "<div class='rotate-div' data-name='"+action+"' data-showtype='"+showType+"' data-ismust='"+isMust+"' data-tablename='"+tableName+"' data-title='"+tableDes+"'>"+
			"<div class='rotate-obj "+rotateBorderColor+"'>"+isMustHtmlBitian+
				"<div class='rotate-des "+rotateColor+"'><div class='iconCicle' style='background-color:"+backColor+"';><li class='iconStyle info-box-icon i "+icon+"'></li></div><div class='rotate-formName pull-left'>登记"+tableDes+"</div></div>" +
				"<div class='rotate-opre'>"+
				'<button class="rotate-add i i-jia2" onclick="addCollateralFormByRotate(this);"></button>'+
				"</div></div>"+"</div>";
			$("#rotate-body").append(htmlStr);
			}else{
				htmlStr = "<div class='rotate-div' data-name='"+action+"' data-showtype='"+showType+"'data-ismust='"+isMust+"' data-tablename='"+tableName+"' data-title='"+tableDes+"'>"+
				"<div class='rotate-obj "+rotateBorderColor+"'>"+
					"<div class='rotate-des "+rotateColor+"'><div class='iconCicle' style='background-color:"+backColor+"';><li class='iconStyle info-box-icon i "+icon+"'></li></div><div class='rotate-formName pull-left'>登记"+tableDes+"</div></div>" +
					"<div class='rotate-opre'>" +
					'<button class="rotate-add i i-jia2" onclick="addCollateralFormByRotate(this);"></button>'+
					"</div>"+"</div>"+"</div>";
				$("#rotate-body").append(htmlStr);
			}	
		}else{
			tubiaoBac = "rotate-tubiaoBacH"+count;
			htmlStr = "<div class='rotate-div' data-name="+action+" data-showtype="+showType+" data-ismust="+isMust+" data-tablename='"+tableName+"' data-title='"+tableDes+"'>"+
				"<div class='rotate-obj1'>"+
				"<div class='rotate-des1'><i style='color:#706f6f' class='info-box-icon i "+icon+"'></i><div class='rotate-formName pull-left'>登记"+tableDes+"</div></div>" +
				"<div class='rotate-opre1'>" +
					"<button class='rotate-add i i-gouxuan' onclick="+
					"\"addRotate(this,"+"'"+count+"'"+","+"'"+
					tableDes+
					"'"+
					");\"></button>"+
				"</div>"+
			 "</div>"+
			"</div>";
			$("#rotate-body-no").append(htmlStr);
		}
		//列表且已填结束
	}else{
		if(flag == "1"){
			isMustHtmlBitian = "<span class='i i-sanjiao rotate-sanjiao color_red'></span><span class='rotate-bitian'>必填</span>";
			rotateBorderColor="rotate-borderColor"+count;
			rotateColor="rotate-color"+count;
			rotateTubiaoBac="rotate-tubiaoBac"+count;
			
			if(isMust=="1"){
				htmlStr = "<div class='rotate-div' data-name='"+action+"' data-showtype='"+showType+"' data-ismust='"+isMust+"' data-tablename='"+tableName+"' data-title='"+tableDes+"'>"+
			"<div class='rotate-obj "+rotateBorderColor+"'>"+isMustHtmlBitian+
				"<div class='rotate-des "+rotateColor+"'><div class='iconCicle' style='background-color:"+backColor+"';><li class='iconStyle info-box-icon i "+icon+"'></li></div><div class='rotate-formName pull-left'>登记"+tableDes+"</div></div>" +
				"<div class='rotate-opre'>"+
				'<button class="rotate-add i i-jia2" onclick="addCollateralFormByRotate(this);"></button>'+
				"</div></div>"+"</div>";
			$("#rotate-body").append(htmlStr);
			}else{
				htmlStr = "<div class='rotate-div' data-name='"+action+"' data-showtype='"+showType+"'data-ismust='"+isMust+"' data-tablename='"+tableName+"' data-title='"+tableDes+"'>"+
				"<div class='rotate-obj "+rotateBorderColor+"'>"+
					"<div class='rotate-des "+rotateColor+"'><div class='iconCicle' style='background-color:"+backColor+"';><li class='iconStyle info-box-icon i "+icon+"'></li></div><div class='rotate-formName pull-left'>登记"+tableDes+"</div></div>" +
					"<div class='rotate-opre'>" +
					'<button class="rotate-add i i-jia2" onclick="addCollateralFormByRotate(this);"></button>'+
						"<button class='rotate-add i i-x42' onclick="+"\"deleteRotate(this,"+"'"+count+"'"+","+"'"+tableDes+"'"+
						");\"></button>"+"</div>"+"</div>"+"</div>";
				$("#rotate-body").append(htmlStr);
			}	
		}else{
			tubiaoBac = "rotate-tubiaoBacH"+count;
			htmlStr = "<div class='rotate-div' data-name="+action+" data-showtype="+showType+" data-ismust="+isMust+" data-tablename='"+tableName+"' data-title='"+tableDes+"'>"+
				"<div class='rotate-obj1'>"+
				"<div class='rotate-des1'><i style='color:#706f6f' class='info-box-icon i "+icon+"'></i><div class='rotate-formName pull-left'>登记"+tableDes+"</div></div>" +
				"<div class='rotate-opre1'>" +
					"<button class='rotate-add i i-gouxuan' onclick="+
					"\"addRotate(this,"+"'"+count+"'"+","+"'"+
					tableDes+
					"'"+
					");\"></button>"+
				"</div>"+
			 "</div>"+
			"</div>";
			$("#rotate-body-no").append(htmlStr);
			//alert(htmlStr);
		}
	}

	
 }//function结束
function deleteRotate (obj,count,tableDes){
	var $rotateDiv = $(obj).parents(".rotate-div");
	//alert($rotateDiv.data("tablename"));
	$.ajax({
		url:webPath+"/mfCollateralTable/updateDelFlagAjax?collateralNo="+collateralNo+"&tableName="+$rotateDiv.data("tablename")+"&delFlag=1",
		type:"POST",
		dataType:"json",
		success:function(data){
			if(data.flag =="success"){
				top.updateFlag = true;
				var tablename = $rotateDiv.attr("data-tablename");
				var action = $rotateDiv.attr("data-name");
				var isMust = $rotateDiv.attr("data-ismust");
				var showType = $rotateDiv.attr("data-showtype");
				var icon="";
				backColor="";
				if(count==1){
					icon="i-qiye";
				}else if(count==2){
					icon="i-dengji";
				}else if(count==3){
					icon="i-jibenxinxi";
				}else if(count==4){
					icon="i-caiji";
				}
				$("#rotate-body").find("div[data-tablename="+tablename+"]").remove();
				$("#rotate-body-no").append("<div class='rotate-div' data-name="+action+" data-ismust="+isMust+" data-showtype="+showType+" data-tablename='"+tablename+"' data-title='"+tableDes+"'>"
						+"<div class='rotate-obj1 '>"
							+"<div class='rotate-des1 '>"
								+"<i style='color:#706f6f' class='info-box-icon i "+icon+"'></i>"
								+"<div class='rotate-formName pull-left'>登记"+tableDes
								+"</div>"
							+"</div>"
							+"<div class='rotate-opre1'>"
							 	+"<button class='rotate-add i i-gouxuan' onclick="+"\"addRotate(this,"+"'"+count+"'"+","+"'"+tableDes+"'"+");\"></button>"
							+"</div>"+
						"</div></div>");
			}else{
				alert(top.getMessage("FAILED_DELETE"),0);
			}
		},error:function(){
			alert(top.getMessage("FAILED_DELETE"),0);
		}
	});
	
};
function addRotate (obj,count,tableDes){
	var $rotateDiv = $(obj).parents(".rotate-div");
	//alert($rotateDiv.data("tablename"));
	$.ajax({
		url:webPath+"/mfCollateralTable/updateDelFlagAjax?collateralNo="+collateralNo+"&tableName="+$rotateDiv.data("tablename")+"&delFlag=0",
		type:"POST",
		dataType:"json",
		success:function(data){
			if(data.flag =="success"){
				top.updateFlag = true;
				var tablename = $rotateDiv.attr("data-tablename");
				var action = $rotateDiv.attr("data-name");
				var isMust = $rotateDiv.attr("data-ismust");
				var showType = $rotateDiv.attr("data-showtype");
				var icon="";
				backColor="";
				if(count==1){
					icon="i-qiye";
					backColor = "#8EAFE4";
				}else if(count==2){
					icon="i-dengji";
					backColor = "#81B960";
				}else if(count==3){
					icon="i-jibenxinxi";
					backColor = "#FCB865";
				}else if(count==4){
					icon="i-caiji";
					backColor = "#5FC8DB";
				}
				$("#rotate-body-no").find("div[data-tablename="+tablename+"]").remove();
				$("#rotate-body").append("<div class='rotate-div' data-name="+action+" data-ismust="+isMust+" data-showtype="+showType+" data-tablename='"+tablename+"' data-title='"+tableDes+"'>"
						+"<div class='rotate-obj rotate-borderColor"+count+"'>"
						+"<div class='rotate-des rotate-color"+count+"'>"
							+"<div class='iconCicle' style='background-color:"+backColor+"';><li class='iconStyle info-box-icon i "+icon+"'></li></div>"
							+"<div class='rotate-formName pull-left'>登记"+tableDes
							+"</div>"
						+"</div>"
						+"<div class='rotate-opre'>"
						+'<button class="rotate-add i i-jia2" onclick="addCollateralFormByRotate(this);"></button>'
						 	+"<button class='rotate-add i i-x42' onclick="+"\"deleteRotate(this,"+"'"+count+"'"+","+"'"+tableDes+"'"+");\"></button>"
						+"</div>"+
					"</div></div>");
			}else{
				alert(top.getMessage("FAILED_DELETE"),0);
			}
		},error:function(){
			alert(top.getMessage("FAILED_DELETE"),0);
		}
	});
};
function addCollateralFormByRotate(obj){
	var $rotateDiv = $(obj).parents(".rotate-div");
	var title =$rotateDiv.data("title");
	var action = $rotateDiv.data("name");
	//处理action为controller;
	action = webPath+"/"+action.substring(0,1).toLowerCase()+action.substring(1);
	action =action.replace("Action","");
	top.action = action;
	top.title = title;
	top.isMust = $rotateDiv.data("ismust");
	top.showType = $rotateDiv.data("showtype");
	var inputUrl =action + "/input?collateralNo="+collateralNo;
	top.tableName = $rotateDiv.data("tablename");
	top.addFlag = false;
	top.flag="add";
	top.collateralNo=collateralNo;
	top.htmlStrFlag = false;//标识是否将客户表单信息的html字符串放在top下
	top.htmlString = null;
	window.location.href=inputUrl;
};