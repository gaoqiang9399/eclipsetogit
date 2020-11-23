function BtnDef(options) {
	this.init(options);
}
BtnDef.prototype = {
	DEFAULTS : {
		ctrlFlag:"insert",
		add_com : "#add_com",
		add_fun : "#add_fun",
		add_btn : "#add_btn",
		save_com : "#comSaveBtn",
		save_fun : "#funSaveBtn",
		save_btn : "#btnSaveBtn",
		link_btn : "#linkSaveBtn",
		delUrl_btn: "#deleteBtn",
		add_link_btn : "#add_url_btn",
		com_vals : [ "#componentName", "#componentDesc","#comId" ],
		fun_vals : [ "#funNo", "#funName","#funUpId","#funId"],
		btn_vals : [ "#btnNo", "#btnName", "#btnDesc","#btnUpId","#btnId"],
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
		// 打开表单
		$(options.add_com).bind("click", function() {
			$this.options.ctrlFlag="insert";
			$(options.com_vals[0]).val("");
			$(options.com_vals[1]).val("");
			$(options.com_vals[2]).val("");
			$("#add-com-modal").modal('show');
		});
		$(options.add_fun).bind("click", function() {
			$this.options.ctrlFlag="insert";
			$(options.fun_vals[0]).val("").attr("disabled",false);
			$(options.fun_vals[1]).val("");
			$(options.fun_vals[3]).val("");
			$("#add-fun-modal").modal('show');
		});
		$(options.add_btn).bind("click", function() {
			$this.options.ctrlFlag="insert";
			$(options.btn_vals[0]).val("").attr("disabled",false);
			$(options.btn_vals[1]).val("");
			$(options.btn_vals[2]).val("");
			$(options.btn_vals[4]).val("");
			$("#add-btn-modal").modal('show');
		});
		// 保存
		$(options.save_com).bind("click", function() {
			var parmData = {
				componentName : $(options.com_vals[0]).val(),
				componentDesc : $(options.com_vals[1]).val(),
				lv : 1,
				upId : 0,
				id:$(options.com_vals[2]).val()
			}
			switch ($this.options.ctrlFlag) {
				case "insert":
					$this.insertNode(parmData,function(jsonData){
						$("#add-com-modal").modal('hide');
						var treeObj = $.fn.zTree.getZTreeObj("treeLv1");
						var newNode = jsonData.node;
						newNode.name = newNode.componentName+"("+newNode.componentDesc+")";
						newNode.iconSkin = "pIcon01";
						newNode = treeObj.addNodes(null, newNode);
						$(options.com_vals[0]).val("");
						$(options.com_vals[1]).val("");
						$(options.com_vals[2]).val("");
					});
					break;
				case "update":
					$this.updateNode(parmData,function(jsonData){
						console.log(jsonData);
						var newNode = jsonData.node;
						var treeObj = $.fn.zTree.getZTreeObj("treeLv1");
						var node = treeObj.getNodeByParam("id", newNode.id, null);
						node.name = newNode.componentName+"("+newNode.componentDesc+")";
						node.componentName = newNode.componentName;
						node.componentDesc = newNode.componentDesc;
						treeObj.updateNode(node);
						treeObj.refresh();
						$("#add-com-modal").modal('hide');
						$(options.com_vals[0]).val("");
						$(options.com_vals[1]).val("");
						$(options.com_vals[2]).val("");
					});
					break;
				default:
					console.log("程序异常！")
					break;
			}

		});
		$(options.save_fun).bind("click", function() {
			var parmData = {
					funNo : $(options.fun_vals[0]).val(),
					funName : $(options.fun_vals[1]).val(),
					lv : 2,
					upId : $(options.fun_vals[2]).val(),
					id:$(options.fun_vals[3]).val()
				}
			switch ($this.options.ctrlFlag) {
				case "insert":
					$this.insertNode(parmData,function(jsonData){
						$("#add-fun-modal").modal('hide');
						var treeObj = $.fn.zTree.getZTreeObj("treeLv2");
						var newNode = jsonData.node;
						newNode.name = newNode.funNo+"("+newNode.funName+")";
						newNode.iconSkin = "pIcon02";
						newNode = treeObj.addNodes(null, newNode);
						$(options.fun_vals[0]).val("");
						$(options.fun_vals[1]).val("");
						$(options.fun_vals[3]).val("");
					});
					break;
				case "update":
					$this.updateNode(parmData,function(jsonData){
						console.log(jsonData);
						var newNode = jsonData.node;
						var treeObj = $.fn.zTree.getZTreeObj("treeLv2");
						var node = treeObj.getNodeByParam("id", newNode.id, null);
						node.name = newNode.funNo+"("+newNode.funName+")";
						node.funNo = newNode.funNo;
						node.funName = newNode.funName;
						treeObj.updateNode(node);
						treeObj.refresh();
						$("#add-fun-modal").modal('hide');
						$(options.com_vals[0]).val("");
						$(options.com_vals[1]).val("");
						$(options.com_vals[2]).val("");
					});
					break;
				default:
					console.log("程序异常！")
					break;
			}
		});
		$(options.save_btn).bind("click", function() {
			var parmData = {
					btnNo : $(options.btn_vals[0]).val(),
					btnName : $(options.btn_vals[1]).val(),
					btnDesc : $(options.btn_vals[2]).val(),
					lv : 3,
					upId : $(options.btn_vals[3]).val(),
					id:$(options.btn_vals[4]).val()
				}
			switch ($this.options.ctrlFlag) {
				case "insert":
					$this.insertNode(parmData,function(jsonData){
						$("#add-btn-modal").modal('hide');
						var treeObj = $.fn.zTree.getZTreeObj("treeLv3");
						var newNode = jsonData.node;
						newNode.name = newNode.btnNo+"("+newNode.btnName+")";
						newNode.iconSkin = "pIcon03";
						newNode = treeObj.addNodes(null, newNode);
						$(options.btn_vals[0]).val("");
						$(options.btn_vals[1]).val("");
						$(options.btn_vals[2]).val("");
						$(options.btn_vals[4]).val("");
					});
					break;
				case "update":
					$this.updateNode(parmData,function(jsonData){
						console.log(jsonData);
						var newNode = jsonData.node;
						var treeObj = $.fn.zTree.getZTreeObj("treeLv3");
						var node = treeObj.getNodeByParam("id", newNode.id, null);
						node.name = newNode.btnNo+"("+newNode.btnName+")";
						node.btnNo = newNode.btnNo;
						node.btnName = newNode.btnName;
						node.btnDesc = newNode.btnDesc;
						treeObj.updateNode(node);
						treeObj.refresh();
						$("#add-btn-modal").modal('hide');
						$(options.com_vals[0]).val("");
						$(options.com_vals[1]).val("");
						$(options.com_vals[2]).val("");
					});
					break;
				default:
					console.log("程序异常！")
					break;
			}
		});
		
//	    link_btn 保存url
		$(options.link_btn).bind("click", function() {
			var nameArr = document.getElementsByName("urlName");
			var valueArr = document.getElementsByName("urlValue");
            var obj;
			for(var i = 0; i < urlData.length; i++){
					obj = new Object();
					// 只修改名称
					if(nameArr[i].value != "" && nameArr[i].value != urlData[i].urlName && valueArr[i].value == ""){
						obj.urlId = urlData[i].urlId;
						obj.urlName = nameArr[i].value;
						obj.urlValue = urlData[i].urlValue;
						updateUrlAjax(obj,webPath+"/sysBtnDef/updateUrlAjax");
					// 只修改地址
					}else if(valueArr[i].value != "" && valueArr[i].value != urlData[i].urlValue && nameArr[i].value ==""){
						obj.urlId = urlData[i].urlId;
						obj.urlName = urlData[i].urlName;
						obj.urlValue = valueArr[i].value;
						updateUrlAjax(obj,webPath+"/sysBtnDef/updateUrlAjax");
					// 同时修改名称和地址
					}else if(nameArr[i].value != "" && nameArr[i].value != urlData[i].urlName && valueArr[i].value != "" && valueArr[i].value != urlData[i].urlValue){
						obj.urlId = urlData[i].urlId;
						obj.urlName = nameArr[i].value;
						obj.urlValue = valueArr[i].value;
						updateUrlAjax(obj,webPath+"/sysBtnDef/updateUrlAjax");
					}
			}
			if(nameArr.length > urlData.length){
				console.log(nameArr.length,urlData.length);
				for(var j = urlData.length; j < nameArr.length; j++){
					console.log(j);
				    if(nameArr[j].value != "" && valueArr[j].value != ""){
						obj = {
								btnId: nowBtnId,
								btnNo: nowBtnNo,
								urlName: nameArr[j].value,
								urlValue: valueArr[j].value
						}
						console.log(obj);
						insertUrlAjax(obj,webPath+"/sysBtnDef/insertUrlAjax");
					}
				}
			}
			$("#chan-btn-modal").modal('hide');
		});
		$(options.add_link_btn).bind("click", function() {
			var urlDivN = document.createElement("DIV");
			urlDivN.className = "form-group";
			var urlLabelN = document.createElement("LABEL");
			var inpName = document.createElement("INPUT");
			inpName.name = "urlName";
			inpName.type = "text";
			inpName.style.width = "110px";
			inpName.style.maxlength = "50px";
			inpName.placeholder = "请输入名称";
			urlDivN.appendChild(urlLabelN);
			urlDivN.appendChild(inpName);

		    
		    var urlLabelV = document.createElement("LABEL");
		    var inpValue = document.createElement("INPUT");
		    inpValue.name = "urlValue";
		    inpValue.type = "text";
		    inpValue.style.width = "220px";
		    inpValue.style.maxlength = "50px";
		    inpValue.placeholder = "请输入地址";
		    var delBtn = createDel(inpIndex);
		    if(delBol){
		    	delBtn.style.width = "20px";
		    	delBtn.style.height = "20px";
		    	delBtn.style.opacity = 1;
			    delBtn.innerHTML = "X";
		    }
		    urlDivN.appendChild(urlLabelV);
		    urlDivN.appendChild(inpValue);
		    urlDivN.appendChild(delBtn);
			$("#form-container").append(urlDivN);
			inpIndex++;
			console.log(inpIndex);
		});
		
		$(options.delUrl_btn).bind("click",function(){
			delBol = !delBol;
			var delUrlArr = document.getElementsByClassName("delUrl");
            var i;
			if(delBol){
				for(i = 0; i < delUrlArr.length; i++){
				    delUrlArr[i].style.width = "20px";
				    delUrlArr[i].style.height = "20px";
				    delUrlArr[i].style.opacity = 1;
				    delUrlArr[i].innerHTML = "X";
				}
			}else{
				for(i = 0; i < delUrlArr.length; i++){
				    delUrlArr[i].style.width = "0px";
				    delUrlArr[i].style.height = "0px";
				    delUrlArr[i].style.opacity = 0;
				    delUrlArr[i].innerHTML = "";
				}
			}
		})
		
		$("#refurbish").bind("click", function(){
			$.ajax({
				type : "post",
				async : false,
				cache : false,
				url : webPath+"/sysRoleButton/refurbishBtnCacheAjax",
				dataType : "json",
				success : function(jsonData) {
					if(jsonData.flag=="success"){
						window.top.alert(jsonData.msg,1);
					}else{
						window.top.alert(jsonData.msg,0);
					}
				},
				error : function() {
					alert("error");
				}
			});
		});
	},
	insertNode:function(parmData,callback){
		$.ajax({
			type : "post",
			async : false,
			cache : false,
			url : webPath+"/sysBtnDef/insertAjax",
			dataType : "json",
			data : {ajaxData:JSON.stringify(parmData)},
			success : function(jsonData) {
				console.log(jsonData);
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
		});
	},
	updateNode:function(parmData,callback){
		$.ajax({
			type : "post",
			async : false,
			cache : false,
			url : webPath+"/sysBtnDef/updateAjax",
			dataType : "json",
			data : {ajaxData:JSON.stringify(parmData)},
			success : function(jsonData) {
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
		});
	},
	updateUrl:function(parmData,callback){
		$.ajax({
			type  : "post",
			async : false,
			cache : false,
			url   : webPath+"/sysBtnDef/updateUrlAjax",
			dataType : "json",
			data  : {ajaxData:JSON.stringify(parmData)},
			success: function(jsonData){
				if(jsonData.flag == "success"){
					if(typeof(callback) == "function"){
						callback.call(this,jsonData);
					}
					window.top.alert(jsonData.msg,1);
				}else{
					window.top.alert(jsonData.msg,0);
				}
			},
			error : function(){
				alert("error");
			}
		})
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
				enable: true     // true / false 分别表示 使用 / 不使用 简单数据模式
			},
			key: {
				title: "btnDesc" //zTree 节点数据保存节点提示信息的属性名称
			}
		},
		view: {
			showLine: false,     //设置 zTree 是否显示节点之间的连线。
			addDiyDom: addDiyDom //用于在节点上固定显示用户自定义控件
		},
		callback: {
			onClick: btnOnClick  //用于捕获节点被点击的事件回调函数
		}

	};
//addDiyDom
function  addDiyDom(treeId, treeNode) {
	$("#" + treeNode.tId + "_switch").remove();
	var aObj = $("#"+treeNode.tId+"_a");
	var delBtn = $('<span title="删除" id="'+treeNode.tId+'_delBtn" class="button delBtn" ></span>');
	var editBtn = $('<span title="修改" id="'+treeNode.tId+'_editBtn" class="button editBtn" ></span>');
	var changeBtn = $('<span title="修改链接" id="'+treeNode.tId+'_editBtn" class="button editBtn" ></span>');
	aObj.append(delBtn).append(editBtn)
	if(treeId == "treeLv3"){
		var desc = $('<span title="'+treeNode.btnDesc+'" id="'+treeNode.tId+'_desc" class="desc" >说明：'+treeNode.btnDesc+'</span>');
		aObj.append(changeBtn).append(desc);
		desc.qtip({ 
			content: treeNode.btnDesc,
			position: {
				my: 'top left',
				at: 'bottom left'
			}
		}); 
	}
	$("#" + treeNode.tId + "_span").qtip({ 
		  content: treeNode.name,
		  position: {
		        my: 'top left',
		        at: 'bottom left'
		  }
		}); 
	delBtn.bind("click",function(){
		window.top.alert("确认删除？",2,function(){
			$.ajax({
				type : "post",
				async : false,
				cache : false,
				url : webPath+"/sysBtnDef/deleteAjax",
				dataType : "json",
				data : {id:treeNode.id,lv:treeNode.lv},
				success : function(jsonData) {
					if(jsonData.flag=="success"){
						var treeObj = $.fn.zTree.getZTreeObj(treeId);
						switch (treeNode.lv) {
						case "1":
							$.fn.zTree.destroy("treeLv2");
						    $.fn.zTree.destroy("treeLv3");
						    $(".sys-btn-lv.lv2 .sys-btn-head i").hide();
						    $(".sys-btn-lv.lv3 .sys-btn-head i").hide();
							break;
						case "2":
							$.fn.zTree.destroy("treeLv3");
 						    $(".sys-btn-lv.lv3 .sys-btn-head i").hide();
							break;
						case "3":
							break;
						default:
							break;
						}
						
					    treeObj.removeNode(treeNode);
						window.top.alert(jsonData.msg,1);
					}else{
						window.top.alert(jsonData.msg,0);
					}
				},
				error : function() {
					alert("error");
				}
			});
		})
		
	});
	editBtn.bind("click",function(){
		def.options.ctrlFlag="update";
		switch (treeId){
			case "treeLv1"://
				$("#add-com-modal").modal('show');
				$("#componentName").val(treeNode.componentName);
				$("#componentDesc").val(treeNode.componentDesc);
				$("#comId").val(treeNode.id);
				break;
			case "treeLv2"://
				$("#add-fun-modal").modal('show');
				$("#funNo").val(treeNode.funNo).attr("disabled",false);
				$("#funName").val(treeNode.funName);
				$("#funId").val(treeNode.id);
				break;
			case "treeLv3"://
				$("#add-btn-modal").modal('show');
				$("#btnNo").val(treeNode.btnNo).attr("disabled",false);
				$("#btnName").val(treeNode.btnName);
				$("#btnDesc").val(treeNode.btnDesc);
				$("#btnId").val(treeNode.id);
				break;
			default:
				break;
		}
	});
	// 点击修改url，进行ajax请求获取数据
	changeBtn.bind("click",function(){
		$("#chan-btn-modal").modal('show');
		$("#chan-btn-modal").find(".modal-body").empty();
		$("#urlName").val("");
		$("#urlValue").val("");
		nowBtnId = treeNode.id;
		nowBtnNo = treeNode.btnNo;
		delBol = false;
		
		$.ajax({
			type : "post",
			async : false,
			cache : false,
			url : webPath+"/sysBtnDef/getUrlAjax",
			dataType : "json",
			data : {id:treeNode.id},
			success : function(jsonData) {
				if(jsonData.flag=="success"){
					urlData = jsonData.formData;
                    createInput(jsonData,"#form-container");
				}else{
					console.log("暂无数据!");
					urlData = [];
				}
			},
			error : function() {
				alert("error");
			}
		});
	});
};
function updateUrlAjax(obj,urlAction){
	var parmData = {
		urlId: obj.urlId,
		urlName: obj.urlName,
		urlValue: obj.urlValue
	};
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

function insertUrlAjax(obj,urlAction){
	var parmData = {
		btnId: obj.btnId,
		urlName: obj.urlName,
		urlValue: obj.urlValue,
		btnNo: obj.btnNo
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

function createInput(jsonData, container){
	$(container).html("");
	inpIndex = 1;
	for(var i = 0; i < jsonData.formData.length; i++){
		inpIndex++;
		var urlDivN = document.createElement("DIV");
		urlDivN.className = "form-group";
		var urlLabelN = document.createElement("LABEL");
		//urlLabelN.innerHTML = "名称"+ (i + 1) + ":";
		var inpName = document.createElement("INPUT");
		inpName.name = "urlName";
		inpName.type = "text";
		inpName.style.width = "110px";
		inpName.style.maxlength = "50px";
		inpName.placeholder = jsonData.formData[i].urlName;
		urlDivN.appendChild(urlLabelN);
		urlDivN.appendChild(inpName);

	    
	    var urlLabelV = document.createElement("LABEL");
	   // urlLabelV.innerHTML = "地址" + (i + 1) + ":";
	    var inpValue = document.createElement("INPUT");
	    inpValue.name = "urlValue";
	    inpValue.type = "text";
	    inpValue.style.width = "220px";
	    inpValue.style.maxlength = "50px";
	    inpValue.placeholder = jsonData.formData[i].urlValue;
	    var delBtn = createDel();
	    urlDivN.appendChild(urlLabelV);
	    urlDivN.appendChild(inpValue);
	    urlDivN.appendChild(delBtn);
		$(container).append(urlDivN);
	}
}

function createDel() {
	var deleteUrl = document.createElement("SPAN");
    deleteUrl.className = "delUrl";
    deleteUrl.title = "删除";
    deleteUrl.style.width = "0px";
    deleteUrl.style.height = "0px";
    deleteUrl.style.opacity = 0;
    deleteUrl.style.color = "red";
    deleteUrl.style.fontSize = "20px";
    deleteUrl.style.cursor = "pointer";
    deleteUrl.style.textAlign = "center";
    deleteUrl.style.lineHeight = "20px";
    deleteUrl.style.transition = "all 0.3s ease-in";
    deleteUrl.style.display = "inline-block";
    deleteUrl.innerHTML = "X";
    
    deleteUrl.inpIndex = inpIndex - 2;
    
    deleteUrl.onclick = function(){
    	var _thisNode = this;
        if(typeof(urlData[this.inpIndex]) != "undefined"){
    	  var parmData = {
    	    		urlId: urlData[this.inpIndex].urlId	
    	    	};
    	    		$.ajax({
    	    			type : "post",
    	    			async : false,
    	    			cache : false,
    	    			url : webPath+"/sysBtnDef/delUrlAjax",
    	    			dataType : "json",
    	    			data : {ajaxData:JSON.stringify(parmData)},
    	    			success : function(jsonData) {
    	    				if(jsonData.flag=="success"){
    	    					console.log(inpIndex);
    	    					$(_thisNode).parent().remove();
    	    					urlData.pop();
    	    					inpIndex--;
    	    					console.log(inpIndex);
    	    				}else{
    	    					window.top.alert(jsonData.msg,0);
    	    				}
    	    			},
    	    			error : function() {
    	    				alert("error");
    	    			}
    	    		});  
           }else{
				$(_thisNode).parent().remove();
				inpIndex--;
           }
    };
    
    return deleteUrl;
}

//单击组件
function comOnClick(event, treeId, treeNode) {
	$("#"+treeNode.tId).addClass("curSelectedNode").siblings("li").removeClass("curSelectedNode");
    $("#funUpId").val(treeNode.id);
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
			}else{
				window.top.alert(jsonData.msg,0);
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
    $(".sys-btn-lv.lv3 .sys-btn-head i").show();
    var parmData = {
			lv : 3,
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
				$.fn.zTree.init($("#treeLv3"), btn_setting, jsonData.json);
			}else{
				window.top.alert(jsonData.msg,0);
			}
		},
		error : function() {
			alert("error");
		}
	});
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
				$.fn.zTree.init($("#treeLv1"), com_setting, jsonData.json);
			}else{
				window.top.alert(jsonData.msg,0);
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
	
});
//保存当前有几条地址
var inpIndex = 1;

// 保存sys_btn_url中的数据
var urlData;

// 保存btnId
var nowBtnId;

// 保存btnNo
var nowBtnNo;

// deleteUrlBtn
var delBol;