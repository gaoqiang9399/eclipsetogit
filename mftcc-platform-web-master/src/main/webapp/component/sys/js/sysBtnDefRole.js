function BtnDef(options) {
	this.init(options);
}
BtnDef.prototype = {
	DEFAULTS : {
	},
	init : function(options) {
		this.options = $.extend({}, this.DEFAULTS, $.isPlainObject(options)
				&& options);
		this.initBindBtn();
		return this;
	},
	initBindBtn : function() {
		var options = this.options;
		var $this = this;
		$("#selectAllBtn").bind("click", function(){
			var zTree = $.fn.zTree.getZTreeObj("treeLv3");
			zTree.checkAllNodes($(this).prop("checked"));
		});
		
		$("#saveRoleBtn").bind("click",function(){
			var zTree = $.fn.zTree.getZTreeObj("treeLv3");
			var nodes = zTree.getCheckedNodes(true);
			var funNo = $("#funNo").val();
			var roleNo = $("#roleNo").val();
			$.ajax({
				type : "post",
				async : false,
				cache : false,
				url : webPath+"/sysRoleButton/insertByJSONArrayAjax",
				dataType : "json",
				data : {ajaxData:JSON.stringify(nodes),funNo:funNo,roleNo:roleNo},
				success : function(jsonData) {
					if(jsonData.flag=="success"){
					}
					window.top.alert(jsonData.msg,1);
				},
				error : function() {
					alert("error");
				}
			});
		});
		$("#refurbish").bind("click", function(){
			$.ajax({
				type : "post",
				async : false,
				cache : false,
				url : webPath+"/sysRoleButton/refurbishBtnCacheAjax",
				dataType : "json",
				success : function(jsonData) {
					window.top.alert(jsonData.msg,1);
				},
				error : function() {
					alert("error");
				}
			});
		});
		
	}
	
}
//组件tree配置
var com_setting = {
		data: {
			simpleData: {
				enable: true
			},
			key: {
				title: "componentDesc"
			}
		},
		view: {
			showLine: false,
			addDiyDom: addDiyDom
		},
		callback: {
			onClick: comOnClick
		}
	};
//功能tree配置
var fun_setting = {
		data: {
			simpleData: {
				enable: true
			},
			key: {
				title: "funName"
			}
		},
		view: {
			showLine: false,
			addDiyDom: addDiyDom
		},
		callback: {
			onClick: funOnClick
		}

	};
//按钮tree配置
var btn_setting = {
		data: {
			simpleData: {
				enable: true
			},
			key: {
				title: "btnDesc"
			}
		},
		view: {
			showLine: false,
			addDiyDom: addDiyDom
		},
		callback: {
			onClick: btnOnClick
		},
		check: {
			enable: true
		}

	};
//addDiyDom
function  addDiyDom(treeId, treeNode) {
	$("#" + treeNode.tId + "_switch").remove();
	var aObj = $("#"+treeNode.tId+"_a");
	if(treeId == "treeLv3"){
		var desc = $('<span title="'+treeNode.btnDesc+'" id="'+treeNode.tId+'_desc" class="desc" >说明：'+treeNode.btnDesc+'</span>');
	    var urlSet = $('<span id="'+treeNode.tId+'_urlSet" class="urlSet"></span>');
	    var smartD = $('<div id=">'+treeNode.tId+'_smartD" class="smartD">'+ '<ul id="'+treeNode.tId+'_smartUl" class="smart_Ul">'+ '</ul><button id="'+ treeNode.tId +'_save" class="saveBtn">保存</button><button id="'+ treeNode.tId+'_close" class="closeBtn">关闭</button></div>');
        smartD.bol = false;
    	$(".sys-btn-body").append(smartD);
	    urlSet.css("display","none");
		aObj.append(desc);
		// 点击url设置按钮
	    urlSet.bind('click',function(event){ 
	    	$("#"+treeNode.tId+"_smartUl").html("");
	    	smartD.bol = !smartD.bol;
	    	var getObj ={};
    		getObj.roleNo = $("#roleNo").val();
    		getObj.btnId = treeNode.id;
    		getRoleUrlAjax(getObj);
			for(var i = 0; i < urlData.length; i++){
				var liName = $('<li>'+urlData[i].urlName +":     "+urlData[i].urlValue+ '<input type="checkbox" name="urlCheck" class="urlCheck" value="'+urlData[i].urlId+'">'+'</li>');
				$("#"+treeNode.tId+"_smartUl").append(liName);
			}
    		if(roleUrlData.formData.length > 0){
    			for(var j = 0; j < roleUrlData.formData.length; j++){
    				var nowRoleUrl = roleUrlData.formData[j];
					$("input:checkbox[name='urlCheck']").each(function(){
						if($(this).val() == nowRoleUrl.urlId){
							$(this).attr("checked","checked");
						}
					});
				}
    		}
	    	if(smartD.bol){
				smartD.addClass("smartDshow").removeClass("smartD");
	    	}else{
				smartD.addClass("smartD").removeClass("smartDshow");
	    	}
    		event.stopPropagation(); 
	    });
		
        // 点击treeLv3显示url设置按钮
	    $("#"+treeNode.tId+"_a").bind("click",function(){
	    	var urlSetArr = document.getElementsByClassName("urlSet");
	    	var smartDArr = document.getElementsByClassName("smartDshow");
            for(var j = 0; j < smartDArr.length; j++){
                smartDArr[j].className = "smartD";
            }
	    	for(var i = 0; i < urlSetArr.length; i++){
	    		urlSetArr[i].style.display = "none";
	    	}
    		urlAjax(treeNode.id,treeNode.tId);
    		if(urlData.length > 0){
    		    urlSet.css("display","inline-block");
    		    aObj.append(urlSet);
    		}
	    });
	    
	    // 关闭url弹出框
	    $("#"+treeNode.tId+"_close").bind("click",function(){
	    	$(this).parent().addClass("smartD").removeClass("smartDshow");
            roleUrlData = {};
	    });
		
	    // 保存url
	    $("#"+treeNode.tId+"_save").bind("click",function(){
	    	var delObj = new Object();
	    	delObj.btnId = treeNode.id;
	    	delObj.roleNo = $("#roleNo").val();
	    	delRoleUrlAjax(delObj);
	    	var obj = new Object();
            obj.btnId = treeNode.id;
            obj.btnNo = treeNode.btnNo;
            obj.roleNo = $("#roleNo").val();
	    	$("input:checkbox[name='urlCheck']:checked").each(function() {
	    		var nowInp = $(this).val();
	    			obj.urlId = nowInp;
	                insertUrlAjax(obj,webPath+"/sysBtnDef/insertRoleUrl");
	                roleUrlData = {};
	    	}).attr("checked","");
	    	$("#"+treeNode.tId+"_save").parent().addClass("smartD").removeClass("smartDshow");
	    });
		
	}
}
// 请求对应的url
function urlAjax(indexId,indextId){
	urlData = {};
	$.ajax({
		type : "post",
		async : false,
		cache : false,
		url : webPath+"/sysBtnDef/getUrlAjax",
		dataType : "json",
		data : {id:indexId},
		success : function(jsonData) {
			if(jsonData.flag=="success"){
				urlData = jsonData.formData;
			}else{
				console.log("null");
			}
		},
		error : function() {
			console.log("error");
		}
	});
}
// 请求角色对应的url
function getRoleUrlAjax(getObj){
	$.ajax({
		type : "post",
		async : false,
		cache : false,
		url : webPath+"/sysBtnDef/getRoleUrlAjax",
		dataType : "json",
		data : {ajaxData:JSON.stringify(getObj)},
		success : function(jsonData) {
			if(jsonData.flag=="success"){
				roleUrlData = jsonData;
			}else{
				roleUrlData = jsonData;
				console.log("暂无数据!");
			}
		},
		error : function() {
			alert("error");
		}
	});
}
// 插入角色可以访问的url
function insertUrlAjax(obj,urlAction){
	var parmData = {
		btnId: obj.btnId,
		btnNo: obj.btnNo,
		roleNo: obj.roleNo,
		urlId: obj.urlId
	}
	$.ajax({
		type : "post",
		asyc : false,
		cache : false,
		url : urlAction,
		dataType : "json",
		data : {ajaxData:JSON.stringify(parmData)},
		success : function(jsonData){
			if(jsonData.flag=="success"){
				if(typeof(callback)=="function"){
					callback.call(this,jsonData);
				}
				window.top.alert(jsonData.msg,1);
			}else{
				window.top.alert(jsonData.msg,0);
			}
		},
		error : function() {
			alert("error");
		}
	})
}

function delRoleUrlAjax(delObj){
	$.ajax({
		type : "post",
		async : false,
		cache : false,
		url : webPath+"/sysBtnDef/delRoleUrlAjax",
		dataType : "json",
		data : {ajaxData:JSON.stringify(delObj)},
		success : function(jsonData) {
			if(jsonData.flag=="success"){
                console.log("success");
			}else{
				console.log("delete error");
			}
		},
		error : function() {
			alert("error");
		}
	});
}

//单击组件
function comOnClick(event, treeId, treeNode) {
	$("#"+treeNode.tId).addClass("curSelectedNode").siblings("li").removeClass("curSelectedNode");
    $("#funUpId").val(treeNode.id);
    $("#selectAllBtn").hide();
    $("#saveRoleBtn").hide();
    $(".sys-btn-lv.lv2 .sys-btn-head i").show();
    $.fn.zTree.destroy("treeLv3");
    $(".sys-btn-lv.lv3 .sys-btn-head i").hide();
    var parmData = {
			lv : 2,
			upId : treeNode.id
		}
    $.ajax({
		type : "post",
		async : false,
		cache : false,
		url : webPath+"/sysBtnDef/findByLvAjax",
		dataType : "json",
		data : {ajaxData:JSON.stringify(parmData)},
		success : function(jsonData) {
			if(jsonData.flag=="success"){
				$.fn.zTree.init($("#treeLv2"), fun_setting, jsonData.json);
			}
		},
		error : function() {
			alert("error");
		}
	});
};

//单击功能
function funOnClick(event, treeId, treeNode) {
	$("#"+treeNode.tId).addClass("curSelectedNode").siblings("li").removeClass("curSelectedNode");
    $("#btnUpId").val(treeNode.id);
    $(".smartD").remove();
    $("#funNo").val(treeNode.funNo);
    $("#selectAllBtn").show();
    $("#saveRoleBtn").show();
    $("#selectAllBtn").prop("checked",false);
    $(".sys-btn-lv.lv3 .sys-btn-head i").show();
    var roleNo = $("#roleNo").val();
    var parmData = {
			lv : 3,
			upId : treeNode.id,
			roleNo:roleNo
		}
    $.ajax({
		type : "post",
		async : false,
		cache : false,
		url : webPath+"/sysBtnDef/findByLvForRoleNoAjax",
		dataType : "json",
		data : {ajaxData:JSON.stringify(parmData)},
		success : function(jsonData) {
			if(jsonData.flag=="success"){
				$.fn.zTree.init($("#treeLv3"), btn_setting, jsonData.json);
			}
		},
		error : function() {
			alert("error");
		}
	});
   /* var zTrees = $.fn.zTree.getZTreeObj("treeLv3");
	var nodees = zTrees.getCheckedNodes(true);
	var chkArr = document.getElementsByClassName("chk");
	var trueBolArr = [];
	for(var j = 0 ; j < chkArr.length; j++){
		//trueBolArr.push(nodeBol[1]);
		chkArr[j].tId = chkArr[j].id.split("_");
		chkArr[j].setBol = false;
		$("#treeLv3_"+ chkArr[j].tId[1] +"_urlSet").css("display","none");
		(function(index){
			for(var s = 0; s < nodees.length; s++){
				if(nodees[s].tId.split("_")[1] == chkArr[index].tId[1]){
					chkArr[index].setBol = true;
    				$("#treeLv3_"+ chkArr[index].tId[1] +"_urlSet").css("display","inline-block");
					break;
				}
			}
		})(j);
	}
	
	
	console.log(nodees);
	for(var i = 0; i < chkArr.length; i++){
        (function(arg){
            $(chkArr[arg]).bind("click",function(){
    			chkArr[arg].setBol = !chkArr[arg].setBol;
    			if(chkArr[arg].setBol){
    				$("#treeLv3_"+ chkArr[arg].tId[1] +"_urlSet").css("display","inline-block");
    			}else{
    				$("#treeLv3_"+ chkArr[arg].tId[1] +"_urlSet").css("display","none");
    			}
    		});  
        })(i);
	}*/
};
//单击按钮
function btnOnClick(event, treeId, treeNode) {
	$("#"+treeNode.tId).addClass("curSelectedNode").siblings("li").removeClass("curSelectedNode");
};


var def;
$(function() {
	def  = new BtnDef();
	var parmData = {
			lv : 1,
			upId : 0
		};
	$.ajax({
		type : "post",
		async : false,
		cache : false,
		url : webPath+"/sysBtnDef/findByLvAjax",
		dataType : "json",
		data : {ajaxData:JSON.stringify(parmData)},
		success : function(jsonData) {
			if(jsonData.flag=="success"){
				$.fn.zTree.init($("#treeLv1"), com_setting, jsonData.json);
			}
		},
		error : function() {
			alert("error");
		}
	});
	$(".lv1 .sys-btn-tree").mCustomScrollbar({
		advanced : {
			updateOnContentResize : true
		}
	});
	$(".lv2 .sys-btn-tree").mCustomScrollbar({
		advanced : {
			updateOnContentResize : true
		}
	});
	$(".lv3 .sys-btn-tree").mCustomScrollbar({
		advanced : {
			updateOnContentResize : true
		}
	});
	
	var roleUrlData = {};
	
	var urlSetBol = false;
	
});