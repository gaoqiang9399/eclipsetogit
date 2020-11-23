$(function(){
	var tableDescMaxLength=0;//根据table的名字计算信息块div的最小宽度
	LoadingAnimate.start();
	for(var i=0;i<cusTableList.length;i++){
		var cusTable=cusTableList[i];
		var pmsBizNo ='cus-edit-'+cusTable.action;//pms_no 规则:"cus-edit-"+action
		var checkPmsBizFlag=BussNodePmsBiz.checkPmsBiz(pmsBizNo);
		if(checkPmsBizFlag){
			if(cusTable.tableDes.length>tableDescMaxLength){
				//tableDescMaxLength=cusTable.tableDes.length;
			}
			cusNo = cusTable.cusNo;
			var flag = "1";
			if(cusTable.delFlag == "0"){
				flag = "1";		//delFlag 0已选 1未选 
			}else{				//flag 1已选 2未选
				flag = "2";
			}
			//showType=1是form，2是table。dataFullFlag=0表示未填，1表示已填。
			setBlock(cusTable.tableName,cusTable.tableDes,flag,cusTable.showType,cusTable.action,cusTable.isMust,cusTable.dataFullFlag,i,cusTable.sort);
		}
	}
	LoadingAnimate.stop();
	$(".scroll-content").mCustomScrollbar({
		advanced:{
			updateOnContentResize:true
		}
	});
	//根据table名字的长度给每个信息块儿赋最小宽度
	//tableDescMaxLength=tableDescMaxLength+2;//加上 登记 二字
	//var minWidth=16*tableDescMaxLength+165;
	//$(".rotate-div").css("min-width",minWidth+"px");
});
 function setBlock (tableName,tableDes,flag,showType,action,isMust,dataFullFlag,location,sort){
	 var	htmlStr = "";
	countt=countt+1;
	var count = countt%4+1;
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
	var addButtonHtml = '<button class="rotate-add i i-jia2 color_theme" onclick="addCusFormByRotate(this);"></button>';
	/* data-tableshowtype='"+tableShowType+"' data-action='"+action+"' data-ismust='"+isMust+"' data-fullflag='"+dataFullFlag+"'  */

     var rotateBorderColor,rotateColor,rotateTubiaoBac,tubiaoBac;
	if(showType=="1"&&dataFullFlag=="1"){//表单且已填 
		
		if(flag == "1"){
			isMustHtmlBitian = "<span class='i i-sanjiao rotate-sanjiao color_red'></span><span class='rotate-bitian'>必填</span>";
			rotateBorderColor="rotate-borderColor"+count;
			rotateColor="rotate-color"+count;
			rotateTubiaoBac="rotate-tubiaoBac"+count;
			if(isMust=="1"){
				htmlStr = "<div class='rotate-div' flag='1' location='"+location+"' data-name='"+action+"' data-showtype='"+showType+"' data-ismust='"+isMust+"' data-sort='"+sort+"' data-tablename='"+tableName+"' data-title='"+tableDes+"'>"+
			"<div class='rotate-obj rotate-bgcolor "+rotateBorderColor+"' >"+isMustHtmlBitian+
				"<div class='rotate-des "+rotateColor+"'><div style='margin-left: 20px; width: 50px; height: 50px; float: left; background-color:"+backColor+"; border-radius: 25px; margin-top: 10px;'><li style='padding-right: 10px; height: 50px; line-height: 50px; color: #ffffff; text-align: center;' class='info-box-icon i "+icon+"'></li></div><div style='margin-left: 20px' class='rotate-formName pull-left rotate-JLcolor'>登记"+tableDes+"</div></div>" +
				"<div class='rotate-opre'>"+
				'<div class="done">该项已完善</div>'+
				"</div></div>"+"</div>";
			$("#rotate-body").append(htmlStr);
			}else{
				htmlStr = "<div class='rotate-div' flag='1' location='"+location+"' data-name='"+action+"' data-showtype='"+showType+"'data-ismust='"+isMust+"' data-sort='"+sort+"' data-tablename='"+tableName+"' data-title='"+tableDes+"'>"+
				"<div class='rotate-obj rotate-bgcolor "+rotateBorderColor+"'>"+
					"<div class='rotate-des "+rotateColor+"'><div style='margin-left: 20px; width: 50px; height: 50px; float: left; background-color:"+backColor+"; border-radius: 25px; margin-top: 10px;'><li style='padding-right: 10px; height: 50px; line-height: 50px; color: #ffffff; text-align: center;' class='info-box-icon i "+icon+"'></li></div><div style='margin-left: 20px'  class='rotate-formName pull-left rotate-JLcolor'>登记"+tableDes+"</div></div>" +
					"<div class='rotate-opre'>" +
					"<div class='done'>该项已完善</div>"+
					"</div>"+"</div>"+"</div>";
				$("#rotate-body").append(htmlStr);
			}	
		}else{
			tubiaoBac = "rotate-tubiaoBacH"+count;
			htmlStr = "<div class='rotate-div' flag='2' location='"+location+"' data-name="+action+" data-showtype="+showType+" data-ismust="+isMust+" data-sort='"+sort+"' data-tablename='"+tableName+"' data-title='"+tableDes+"'>"+
				"<div class='rotate-obj1 rotate-bgcolor' >"+
				"<div class='rotate-des1'><i style='color:#706f6f' class='info-box-icon i "+icon+"'><div class='rotate-formName pull-left rotate-JLcolor'>登记"+tableDes+"</div></div>" +
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
				htmlStr = "<div class='rotate-div' flag='1' location='"+location+"' data-name='"+action+"' data-showtype='"+showType+"' data-ismust='"+isMust+"' data-sort='"+sort+"' data-tablename='"+tableName+"' data-title='"+tableDes+"'>"+
				"<div class='rotate-obj rotate-bgcolor "+rotateBorderColor+"'>"+isMustHtmlBitian+
				"<div class='rotate-des "+rotateColor+"'><div style='margin-left: 20px; width: 50px; height: 50px; float: left; background-color:"+backColor+"; border-radius: 25px; margin-top: 10px;'><li style='padding-right: 10px; height: 50px; line-height: 50px; color: #ffffff; text-align: center;' class='info-box-icon i "+icon+"'></li></div><div style='margin-left: 20px' class='rotate-formName pull-left rotate-JLcolor'>登记"+tableDes+"</div></div>";
				htmlStr +="<div class='rotate-opre'>"+addButtonHtml+"</div>";
				htmlStr +="</div>"+"</div>";
				$("#rotate-body").append(htmlStr);
			}else{
				htmlStr = "<div class='rotate-div' flag='1' location='"+location+"' data-name='"+action+"' data-showtype='"+showType+"'data-ismust='"+isMust+"' data-sort='"+sort+"' data-tablename='"+tableName+"' data-title='"+tableDes+"'>"+
					"<div class='rotate-obj rotate-bgcolor "+rotateBorderColor+"'>"+
					"<div class='rotate-des "+rotateColor+"'><div style='margin-left: 20px; width: 50px; height: 50px; float: left; background-color:"+backColor+"; border-radius: 25px; margin-top: 10px;'><li style='padding-right: 10px; height: 50px; line-height: 50px; color: #ffffff; text-align: center;' class='info-box-icon i "+icon+"'></li></div><div style='margin-left: 20px' class='rotate-formName pull-left rotate-JLcolor'>登记"+tableDes+"</div></div>";
				htmlStr +="<div class='rotate-opre'>"+addButtonHtml+"</div>";
				htmlStr +="</div>"+"</div>";
				$("#rotate-body").append(htmlStr);
			}	
		}else{
			tubiaoBac = "rotate-tubiaoBacH"+count;
			htmlStr = "<div class='rotate-div' flag='2' location='"+location+"' data-name="+action+" data-showtype="+showType+" data-ismust="+isMust+" data-sort='"+sort+"' data-tablename='"+tableName+"' data-title='"+tableDes+"'>"+
				"<div class='rotate-obj1 rotate-bgcolor'>"+
				"<div class='rotate-des1'><i style='color:#706f6f' class='info-box-icon i "+icon+"'></i><div class='rotate-formName pull-left rotate-JLcolor'>登记"+tableDes+"</div></div>" +
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
	}else if(showType=="3"&&dataFullFlag=="1"){//特殊列表只能添加一条且已填 
		if(flag == "1"){
			isMustHtmlBitian = "<span class='i i-sanjiao rotate-sanjiao color_red'></span><span class='rotate-bitian'>必填</span>";
			rotateBorderColor="rotate-borderColor"+count;
			rotateColor="rotate-color"+count;
			rotateTubiaoBac="rotate-tubiaoBac"+count;
			if(isMust=="1"){
				htmlStr = "<div class='rotate-div' flag='1' location='"+location+"' data-name='"+action+"' data-showtype='"+showType+"' data-ismust='"+isMust+"' data-sort='"+sort+"' data-tablename='"+tableName+"' data-title='"+tableDes+"'>"+
			"<div class='rotate-obj rotate-bgcolor "+rotateBorderColor+"'>"+isMustHtmlBitian+
				"<div class='rotate-des "+rotateColor+"'><div style='margin-left: 20px; width: 50px; height: 50px; float: left; background-color:"+backColor+"; border-radius: 25px; margin-top: 10px;'><li style='padding-right: 10px; height: 50px; line-height: 50px; color: #ffffff; text-align: center;' class='info-box-icon i "+icon+"'></li></div><div style='margin-left: 20px' class='rotate-formName pull-left rotate-JLcolor'>登记"+tableDes+"</div></div>" +
				"<div class='rotate-opre'>"+
				'<div class="done">该项已完善</div>'+
				"</div></div>"+"</div>";
			$("#rotate-body").append(htmlStr);
			}else{
				htmlStr = "<div class='rotate-div' flag='1' location='"+location+"' data-name='"+action+"' data-showtype='"+showType+"'data-ismust='"+isMust+"' data-sort='"+sort+"' data-tablename='"+tableName+"' data-title='"+tableDes+"'>"+
				"<div class='rotate-obj rotate-bgcolor "+rotateBorderColor+"'>"+
					"<div class='rotate-des "+rotateColor+"'><div style='margin-left: 20px; width: 50px; height: 50px; float: left; background-color:"+backColor+"; border-radius: 25px; margin-top: 10px;'><li style='padding-right: 10px; height: 50px; line-height: 50px; color: #ffffff; text-align: center;' class='info-box-icon i "+icon+"'></li></div><div style='margin-left: 20px'  class='rotate-formName pull-left rotate-JLcolor'>登记"+tableDes+"</div></div>" +
					"<div class='rotate-opre'>" +
					"<div class='done'>该项已完善</div>"+
					"</div>"+"</div>"+"</div>";
				$("#rotate-body").append(htmlStr);
			}	
		}else{
			tubiaoBac = "rotate-tubiaoBacH"+count;
			htmlStr = "<div class='rotate-div' flag='2' location='"+location+"' data-name="+action+" data-showtype="+showType+" data-ismust="+isMust+" data-sort='"+sort+"' data-tablename='"+tableName+"' data-title='"+tableDes+"'>"+
				"<div class='rotate-obj1 rotate-bgcolor'>"+
				"<div class='rotate-des1'><i style='color:#706f6f' class='info-box-icon i "+icon+"'><div class='rotate-formName pull-left rotate-JLcolor'>登记"+tableDes+"</div></div>" +
				"<div class='rotate-opre1 done'>" +
				"该项已完善"+
				"</div>"+
			 "</div>"+
			"</div>";
			$("#rotate-body-no").append(htmlStr);
		}
		//表单且已填--结束
	}else{
		if(flag == "1"){
			isMustHtmlBitian = "<span class='i i-sanjiao rotate-sanjiao color_red'></span><span class='rotate-bitian'>必填</span>";
			rotateBorderColor="rotate-borderColor"+count;
			rotateColor="rotate-color"+count;
			rotateTubiaoBac="rotate-tubiaoBac"+count;
			
			if(isMust=="1"){
				htmlStr = "<div class='rotate-div' flag='1' location='"+location+"' data-name='"+action+"' data-showtype='"+showType+"' data-ismust='"+isMust+"' data-sort='"+sort+"' data-tablename='"+tableName+"' data-title='"+tableDes+"'>"+
				"<div class='rotate-obj "+rotateBorderColor+"'>"+isMustHtmlBitian+
				"<div class='rotate-des "+rotateColor+"'><div style='margin-left: 20px; width: 50px; height: 50px; float: left; background-color:"+backColor+"; border-radius: 25px; margin-top: 10px;'><li style='padding-right: 10px; height: 50px; line-height: 50px; color: #ffffff; text-align: center;' class='info-box-icon i "+icon+"'></li></div><div style='margin-left: 20px' class='rotate-formName pull-left'>登记"+tableDes+"</div></div>";
				htmlStr +="<div class='rotate-opre'>"+addButtonHtml;
				htmlStr +="</div></div>"+"</div>";
			$("#rotate-body").append(htmlStr);
			}else{
				htmlStr = "<div class='rotate-div' flag='1' location='"+location+"' data-name='"+action+"' data-showtype='"+showType+"'data-ismust='"+isMust+"' data-sort='"+sort+"' data-tablename='"+tableName+"' data-title='"+tableDes+"'>"+
					"<div class='rotate-obj "+rotateBorderColor+"' >"+
					"<div class='rotate-des "+rotateColor+"'><div style='margin-left: 20px; width: 50px; height: 50px; float: left; background-color:"+backColor+"; border-radius: 25px; margin-top: 10px;'><li style='padding-right: 10px; height: 50px; line-height: 50px; color: #ffffff; text-align: center;' class='info-box-icon i "+icon+"'></li></div><div style='margin-left: 20px' class='rotate-formName pull-left'>登记"+tableDes+"</div></div>";
				htmlStr +="<div class='rotate-opre'>"+addButtonHtml+
				"<button class='rotate-add i i-x42 color_theme' onclick="+"\"deleteRotate(this,"+"'"+count+"'"+","+"'"+tableDes+"'"+
				");\"></button>"+"</div>";
				htmlStr +="</div>"+"</div>";
				$("#rotate-body").append(htmlStr);
			}	
		}else{
			tubiaoBac = "rotate-tubiaoBacH"+count;
			htmlStr = "<div class='rotate-div' flag='2' location='"+location+"' data-name="+action+" data-showtype="+showType+" data-ismust="+isMust+" data-sort='"+sort+"' data-tablename='"+tableName+"' data-title='"+tableDes+"'>"+
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
	}

	
 }//function结束
function deleteRotate (obj,count,tableDes){
	var $rotateDiv = $(obj).parents(".rotate-div");
	$.ajax({
		url:webPath+"/mfCusTable/updateDelFlagAjax?cusNo="+cusNo+"&tableName="+$rotateDiv.data("tablename")+"&delFlag=1",
		type:"POST",
		dataType:"json",
		success:function(data){
			if(data.flag =="success"){
				top.updateFlag = true;
				var tablename = $rotateDiv.attr("data-tablename");
				var action = $rotateDiv.attr("data-name");
				var isMust = $rotateDiv.attr("data-ismust");
				var showType = $rotateDiv.attr("data-showtype");
				var location = $rotateDiv.attr("location");
				var sort = $rotateDiv.attr("data-sort");
				var flag = $rotateDiv.attr("flag");
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
				$("#rotate-body-no").append("<div class='rotate-div' flag='"+flag+"' location='"+location+"' data-name="+action+" data-ismust="+isMust+" data-sort='"+sort+"' data-showtype="+showType+" data-tablename='"+tablename+"' data-title='"+tableDes+"'>"
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
	$.ajax({
		url:webPath+"/mfCusTable/updateDelFlagAjax?cusNo="+cusNo+"&tableName="+$rotateDiv.data("tablename")+"&delFlag=0",
		type:"POST",
		dataType:"json",
		success:function(data){
			if(data.flag =="success"){
				top.updateFlag = true;
				var tablename = $rotateDiv.attr("data-tablename");
				var action = $rotateDiv.attr("data-name");
				var isMust = $rotateDiv.attr("data-ismust");
				var showType = $rotateDiv.attr("data-showtype");
				var location = $rotateDiv.attr("location");
				var sort = $rotateDiv.attr("data-sort");
				var flag = $rotateDiv.attr("flag");
                var location_before;
				if(location>0){
					location_before = location-1;
				}else{
					location_before = 0;
				}
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
				var htmlAdd = "<div class='rotate-div' flag='"+flag+"' location='"+location+"' data-name="+action+" data-ismust="+isMust+" data-sort='"+sort+"' data-showtype="+showType+" data-tablename='"+tablename+"' data-title='"+tableDes+"'>"
						+"<div class='rotate-obj rotate-borderColor"+count+"'>"
						+"<div class='rotate-des rotate-color"+count+"'>"
							+"<div style='margin-left: 20px; width: 50px; height: 50px; float: left; background-color:"+backColor+"; border-radius: 25px; margin-top: 10px;'><li style='padding-right: 10px; height: 50px; line-height: 50px; color: #ffffff; text-align: center;' class='info-box-icon i "+icon+"'></li></div>"
							+"<div class='rotate-formName pull-left'>登记"+tableDes
							+"</div>"
						+"</div>"
						+"<div class='rotate-opre'>"
						 	+"<button class='rotate-add i i-x42 color_theme' onclick="+"\"deleteRotate(this,"+"'"+count+"'"+","+"'"+tableDes+"'"+");\"></button>"
						+"</div>"+
					"</div></div>";
				if(flag=='1'){//已选到未选
					if($("div[location="+location_before+"]").length > 0){
					$("div[location="+location_before+"]").after(htmlAdd);
					}else{
					$("#rotate-body").append(htmlAdd);
					}
				}else if(flag=='2'){//刚就来就是未选
					$("#rotate-body").append(htmlAdd);
				}
			}else{
				alert(top.getMessage("FAILED_DELETE"),0);
			}
		},error:function(){
			alert(top.getMessage("FAILED_DELETE"),0);
		}
	});
};
function addCusFormByRotate(obj){
	var $rotateDiv = $(obj).parents(".rotate-div");
	var title =$rotateDiv.data("title");
	var action = $rotateDiv.data("name");
	var tableName = $rotateDiv.data("tablename");
	top.action = action;
	top.title = title;
	top.isMust = $rotateDiv.data("ismust");
	top.showType = $rotateDiv.data("showtype");
	top.sort = $rotateDiv.data("sort");
	//处理action为controller;
	action = webPath+"/"+action.substr(0,1).toLowerCase()+action.substr(1);
	action =action.replace("Action","");
	var inputUrl =action + "/input?cusNo="+cusNo+"&relNo="+relNo;
	if(pageView=="busView"){
		inputUrl =action + "/input?cusNo="+cusNo+"&relNo="+relNo;
	}
	top.addFlag = false;
	top.htmlStrFlag = false;//标识是否将客户表单信息的html字符串放在top下
	top.htmlString = null;
	top.tableName = tableName;
	$("#myModalLabel", window.parent.document).text("登记" + title);
	window.location.href=inputUrl;
};