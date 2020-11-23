//初始化文档业务场景
$(function() {
			var ScenByType_setting = {
				view: {
					showLine: false
				},
				data : {
					simpleData : {
						enable : true
					}
				},
				callback: {
					onClick: findSceDocTypeRels
				}
			};
			$.ajax({
						type : 'post',
						url : webPath+"/docBizSceConfig/findScenByTypeAjax",
						dataType : 'json',
						error : function(xmlhq, ts, err) {
							console.log(xmlhq + "||" + ts + "||" + err);
						},
						success : function(data) {
							var ScenByType_zNodes = data.zNodes;
							$.fn.zTree.init($("#ScenByTypeTree"), ScenByType_setting,ScenByType_zNodes);
						}
					});
//	$("#switchEdit").bind("click",function(){
//		var treeObj = $.fn.zTree.getZTreeObj("SplitDocsTree");
//		if(treeObj){
//			var DocDimmsTreeObj = $.fn.zTree.getZTreeObj("SplitDocsTree");
//			var nodes = DocDimmsTreeObj.getNodes();
//			var treeNode = nodes[0];
//			$("#SceDocTypeRelsEdit").modal('show');
//			$("#SceDocTypeRelsEdit #SceDocTypeRelsName").val("");
//			$("#SceDocTypeRelsSaveBtn").unbind();
//			$("#SceDocTypeRelsSaveBtn").bind("click",function(){
//				var docSplitName = $("#SceDocTypeRelsEdit #SceDocTypeRelsName").val();
//				var ajaxData = {};
//			    ajaxData.docType=treeNode.docType;
//			    ajaxData.docSplitNo = treeNode.docSplitNo;
//			    ajaxData.docSplitName = docSplitName;
//				$.ajax({
//					async:false,
//					type : 'post',
//					url : webPath+"/docTypeConfig/insertDocTypeConfigAjax",
//					dataType : 'json',
//					data:ajaxData,
//					error : function(xmlhq, ts, err) {
//						console.log(xmlhq + "||" + ts + "||" + err);
//					},
//					success : function(data) {
//						var str = JSON.stringify(treeNode);
//						var newNode = eval("("+str+")");
//						newNode.name =data.docSplitName;
//						newNode.docSplitName =data.docSplitName;
//						newNode.docType =data.docType;
//						newNode.docSplitNo =data.docSplitNo;
//						newNode.checked =false;
//						newNode = treeObj.addNodes(null, newNode);
//						$("#SceDocTypeRelsEdit").modal('hide');
//					}
//				});
//			});
//		}else{
//			myAlert("请先选择维度！");
//		}
//		
//	});
		});
//点击业务场景展示文档类别
function findSceDocTypeRels(event, treeId, treeNode) {
	addSplitDocsCurrentClass(treeNode.tId);
	var SceDocTypeRels_setting = {
				view: {
					showLine: false,
					showIcon :false,
					addDiyDom: addDiyDomSceDocTypeRels
				},
				data : {
					simpleData : {
						enable : true
					}
				},
				check: {
					enable: true
				},
				callback: {
					onClick: SceDocTypeRels,
					onCheck: SceDocTypeRelsChecked
				}
			};
	$.fn.zTree.init($("#DocDimmsTree"), "","");
	$.fn.zTree.init($("#SplitDocsTree"), "","");
    var ajaxData = {};
    ajaxData.scNo=treeNode.scNo;
    $.ajax({
			type : 'post',
			url : webPath+"/docBizSceConfig/findSceDocTypeRelsAjax",
			dataType : 'json',
			data:ajaxData,
			error : function(xmlhq, ts, err) {
				console.log(xmlhq + "||" + ts + "||" + err);
			},
			success : function(data) {
				var SceDocTypeRels_zNodes = data.zNodes;
				$.fn.zTree.init($("#SceDocTypeRelsTree"), SceDocTypeRels_setting,SceDocTypeRels_zNodes);
			}
		});
};

function addDiyDomSceDocTypeRels(treeId, treeNode){
	$("#" + treeNode.tId + "_switch").remove();
    var aObj,editStr,editBtn;
	if (treeNode.level == 0) {
		var SceDocTypeRelsTreeObj = $.fn.zTree.getZTreeObj(treeId);
		aObj = $("#" + treeNode.tId + "_a");
		editStr = "<i class='i i-jia3' id='diyNewDocTypeBtn_" + treeNode.tId + "' title='新增' onfocus='this.blur();'></i></i>";
		aObj.html(treeNode.filterName);
		aObj.removeAttr("href");
		aObj.append(editStr);
		editBtn = $("#diyNewDocTypeBtn_" + treeNode.tId);
		if (editBtn) {
			editBtn.bind("click", function() {
				$("#SceDocTypeRelsEdit").modal('show');
				$("#SceDocTypeRelsEdit #SceDocTypeRelsName").val("");
				$("#SceDocTypeRelsSaveBtn").unbind();
				$("#SceDocTypeRelsSaveBtn").bind("click",function(){
					var docSplitName = $("#SceDocTypeRelsEdit #SceDocTypeRelsName").val();
					var ajaxData = {};
				    ajaxData.docType=treeNode.docType;
				    ajaxData.docSplitNo = treeNode.docSplitNo;
				    ajaxData.docSplitName = docSplitName;
					$.ajax({
						async:false,
						type : 'post',
						url : webPath+"/docTypeConfig/insertDocTypeConfigAjax",
						dataType : 'json',
						data:ajaxData,
						error : function(xmlhq, ts, err) {
							console.log(xmlhq + "||" + ts + "||" + err);
						},
						success : function(data) {
							var newNode = {};
							newNode.pId =data.docType;
							newNode.id =data.docSplitNo;
							newNode.docSplitNo =data.docSplitNo;
							newNode.name =data.docSplitName;
							newNode.docSplitName =data.docSplitName;
							newNode.docType =data.docType;
							newNode.docSplitNo =data.docSplitNo;
							newNode.checked =false;
							newNode = SceDocTypeRelsTreeObj.addNodes(treeNode, newNode);
							$("#SceDocTypeRelsEdit").modal('hide');
						}
					});
				});
			}); 
		}
	} else if (treeNode.level == 1) {
		var treeObj = $.fn.zTree.getZTreeObj(treeId);
		aObj = $("#" + treeNode.tId + "_a");
		$("#" + treeNode.tId + "_check").remove();
		editStr = "<i class='OverViewDelete' id='diyDelBtn_" + treeNode.tId + "' title='删除' onfocus='this.blur();'></i><i class='OverViewModify	' id='diyEditBtn_" + treeNode.tId + "' title='编辑' onfocus='this.blur();'></i>";
		aObj.html(treeNode.filterName);
		aObj.removeAttr("href");
		aObj.after(editStr);
		editBtn = $("#diyEditBtn_" + treeNode.tId);
		if (editBtn) {
			editBtn.bind("click", function() {
				$("#SceDocTypeRelsEdit").modal('show');
				$("#SceDocTypeRelsEdit #SceDocTypeRelsName").val(treeNode.docSplitName);
				$("#SceDocTypeRelsSaveBtn").unbind();
				$("#SceDocTypeRelsSaveBtn").bind("click",function(){
					var docSplitName = $("#SceDocTypeRelsEdit #SceDocTypeRelsName").val();
					var ajaxData = {};
				    ajaxData.docType=treeNode.docType;
				    ajaxData.docSplitNo = treeNode.docSplitNo;
				    ajaxData.docSplitName = docSplitName;
				    treeNode.docSplitName = docSplitName;
				    treeNode.name = docSplitName;
					$.ajax({
						async:false,
						type : 'post',
						url : webPath+"/docTypeConfig/updateDocTypeConfigAjax",
						dataType : 'json',
						data:ajaxData,
						error : function(xmlhq, ts, err) {
							console.log(xmlhq + "||" + ts + "||" + err);
						},
						success : function(data) {
							$("#SceDocTypeRelsEdit").modal('hide');
							treeObj.updateNode(treeNode);
						}
					});
				});
			});
		}
		var delBtn = $("#diyDelBtn_" + treeNode.tId);
		if (delBtn) {
			delBtn.bind("click", function() {
					var ajaxData = {};
				    ajaxData.docType=treeNode.docType;
				    ajaxData.docSplitNo = treeNode.docSplitNo;
				    ajaxData.docSplitName = treeNode.docSplitName;
					$.ajax({
						async:false,
						type : 'post',
						url : webPath+"/docTypeConfig/deleteDocTypeConfigAjax",
						dataType : 'json',
						data:ajaxData,
						error : function(xmlhq, ts, err) {
							console.log(xmlhq + "||" + ts + "||" + err);
						},
						success : function(data) {
							treeObj.removeNode(treeNode);
						}
					});
			});
		}
	}
}

//点击文档类别展示维度划分
function SceDocTypeRels(event, treeId, treeNode){
	addSplitDocsCurrentClass(treeNode.tId);
	if(treeNode.level!=0){
		return false;
	}
	if(!treeNode.checked){
		//myAlert("未选中!");
		alert("未选中!",0);
		return false;
	}
//	var treeObj = $.fn.zTree.getZTreeObj("SceDocTypeRelsTree");
//	treeObj.expandNode(treeNode, !treeNode.open, true, true);
	var DocDimmsTree_setting = {
				view: {
					showLine: false,
					showIcon :false
				},
				data : {
					simpleData : {
						enable : true
					}
				},
				callback: {
					onClick: DocDimmsTree
				}
			};
	//$.fn.zTree.init($("#SplitDocsTree"), "","");
	$.fn.zTree.destroy("SplitDocsTree");
    var ajaxData = {};
    ajaxData.scNo=treeNode.scNo;
    ajaxData.docType = treeNode.docType;
    $.ajax({
			type : 'post',
			url : webPath+"/docBizSceConfig/findDocDimmsAjax",
			dataType : 'json',
			data:ajaxData,
			error : function(xmlhq, ts, err) {
				console.log(xmlhq + "||" + ts + "||" + err);
			},
			success : function(data) {
				var DocDimmsTree_zNodes = data.zNodes;
				$.fn.zTree.init($("#DocDimmsTree"), DocDimmsTree_setting,DocDimmsTree_zNodes);
			}
		});
}
//勾选文档类型
function SceDocTypeRelsChecked(event, treeId, treeNode){
	 var ajaxData = {};
    ajaxData.scNo=treeNode.scNo;
    ajaxData.docType = treeNode.docType;
    if(treeNode.checked){
    	 $.ajax({
			type : 'post',
			url : webPath+"/docBizSceConfig/insertSceDocTypeRelAjax",
			dataType : 'json',
			data:ajaxData,
			error : function(xmlhq, ts, err) {
				console.log(xmlhq + "||" + ts + "||" + err);
			},
			success : function(data) {
				console.log(data);
			}
		});
    }else{
    	$.ajax({
			type : 'post',
			url : webPath+"/docBizSceConfig/deleteSceDocTypeRelAjax",
			dataType : 'json',
			data:ajaxData,
			error : function(xmlhq, ts, err) {
				console.log(xmlhq + "||" + ts + "||" + err);
			},
			success : function(data) {
				console.log(data);
			}
		});
    }
   
}
//点击维度划分展示文档细分类型
function DocDimmsTree(event, treeId, treeNode){
	addSplitDocsCurrentClass(treeNode.tId);
	var SplitDocsTree_setting = {
//				edit: {
//						enable: true
//					},
				view: {
					showLine: false,
					showIcon :false,
					addDiyDom: addDiyDom
				},
				data : {
					simpleData : {
						enable : true
					}
				},
				check: {
					enable: true
				},
				callback: {
					onClick: SplitDocsTree,
					onCheck: SplitDocsTreeChecked
				}
			};
    var ajaxData = {};
    ajaxData.scNo=treeNode.scNo;
    ajaxData.docType = treeNode.docType;
    ajaxData.dimmNo = treeNode.dimmNo;
    $.ajax({
			type : 'post',
			url : webPath+"/docBizSceConfig/findSplitDocsAjax",
			dataType : 'json',
			data:ajaxData,
			error : function(xmlhq, ts, err) {
				console.log(xmlhq + "||" + ts + "||" + err);
			},
			success : function(data) {
				var SplitDocsTree_zNodes = data.zNodes;
				$.fn.zTree.init($("#SplitDocsTree"), SplitDocsTree_setting,SplitDocsTree_zNodes);
			}
		});
}

function addDiyDom(treeId, treeNode) {
	var treeObj = $.fn.zTree.getZTreeObj(treeId);
	var aObj = $("#" + treeNode.tId + "_a");
	var editStr = "<i class='i i-bianji1' style='color:#ACACAC;' id='diyEditBtn_" + treeNode.tId + "' title='编辑' onfocus='this.blur();'></i>";
	aObj.html(treeNode.filterName);
	aObj.removeAttr("href");
	aObj.after(editStr);
	var editBtn = $("#diyEditBtn_" + treeNode.tId);
	if (editBtn) {
		editBtn.bind("click", function() {
			$("#SplitDocsEdit").modal('show');
			$("#SplitDocsEdit #docSplitName").val(treeNode.docSplitName);
			$("#ifMustInput").prop("checked",treeNode.ifMustInput==1?function(){ return true;}:function(){ return false;});
			$("#ifMustRead").prop("checked",treeNode.ifMustRead==1?true:false);
			$("#docSizeLimit").val(treeNode.docSizeLimit);
			$("#formId").val(treeNode.formId);
			$("#splitSaveBtn").unbind();
			$("#splitSaveBtn").bind("click",function(){
				treeNode.ifMustInput = $("#ifMustInput").prop("checked")?1:0;
				treeNode.ifMustRead =  $("#ifMustRead").prop("checked")?1:0;
				treeNode.docSizeLimit =  $("#docSizeLimit").val();
				treeNode.formId =  $("#formId").val();
				/*if($("#formId").val()==""){
					//myAlert(top.getMessage("NOT_FORM_EMPTY", "表单编号"));
					alert(top.getMessage("NOT_FORM_EMPTY", "表单编号"),0);
					return false;
				}*/
				$.ajax({
					async:false,
					type : 'post',
					url : webPath+"/docBizSceConfig/updateDocBizSceConfigAjax",
					dataType : 'json',
					data:{ajaxData:JSON.stringify(treeNode)},
					error : function(xmlhq, ts, err) {
						console.log(xmlhq + "||" + ts + "||" + err);
					},
					success : function(data) {
						$("#SplitDocsEdit").modal('hide');
						treeObj.updateNode(treeNode);
					}
				});
			});
		});
	}
}
function SplitDocsTree(event, treeId, treeNode){
	addSplitDocsCurrentClass(treeNode.tId);
	//console.log(treeNode);
}
function SplitDocsTreeChecked(event, treeId, treeNode){
	    var dataParam = JSON.stringify(treeNode); 
	    var url;
	    if(treeNode.checked){
	    	url=webPath+"/docBizSceConfig/insertDocBizSceConfigAjax";
	    }else{
	    	url=webPath+"/docBizSceConfig/deleteDocBizSceConfigAjax";
	    }
		$.ajax({
			async:false,
			type : 'post',
			url : url,
			dataType : 'json',
			data:{ajaxData:dataParam},
			error : function(xmlhq, ts, err) {
				console.log(xmlhq + "||" + ts + "||" + err);
			},
			success : function(data) {
				//勾选后
				console.log(data);
				if(treeNode.checked){
					$("#SplitDocsEdit").modal('show');
					$("#SplitDocsEdit #docSplitName").val(treeNode.docSplitName);
					$("#ifMustInput").prop("checked",treeNode.ifMustInput==1?function(){ return true;}:function(){ return false;});
					$("#ifMustRead").prop("checked",treeNode.ifMustRead==1?true:false);
					$("#docSizeLimit").val(treeNode.docSizeLimit);
					$("#formId").val(treeNode.formId);
					$("#splitSaveBtn").unbind();
					$("#splitSaveBtn").bind("click",function(){
						treeNode.ifMustInput = $("#ifMustInput").prop("checked")?1:0;
						treeNode.ifMustRead =  $("#ifMustRead").prop("checked")?1:0;
						treeNode.docSizeLimit =  $("#docSizeLimit").val();
						/*if($("#formId").val()==""){
							myAlert(top.getMessage("NOT_FORM_EMPTY", "表单编号"));
							return false;
						}*/
						treeNode.formId =  $("#formId").val();
						$.ajax({
							async:false,
							type : 'post',
							url : webPath+"/docBizSceConfig/updateDocBizSceConfigAjax",
							dataType : 'json',
							data:{ajaxData:JSON.stringify(treeNode)},
							error : function(xmlhq, ts, err) {
								console.log(xmlhq + "||" + ts + "||" + err);
							},
							success : function(data) {
								$("#SplitDocsEdit").modal('hide');
								treeObj.updateNode(treeNode);
							}
						});
					});
				}
			}
		});
}
function addSplitDocsCurrentClass(tId){
	$("#" + tId).addClass("SplitDocsCurrent").siblings('li').removeClass("SplitDocsCurrent");
}