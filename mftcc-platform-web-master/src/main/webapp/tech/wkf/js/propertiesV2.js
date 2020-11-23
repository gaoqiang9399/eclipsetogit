var prop;
(function($) {
	 function main(){
	 	this.$processInfoData={};
	 	this.$stencilsData={};
	 	this.$properyPackagesData={};
	 	this.$lineInfoData={};
	 	this.$selector;
	 	this.nodeJson;
	 	this.lineJson;
	 	this.jsonData;
	 	this.selectorId;
	 	this.procsJson;
	 	this.treeinput;
	 	this.saveFlag = true;
	 	this.biz = false;
	 	this.display = "";
	 	this.$bizPropery={};
	 	var self = this;
	 	$.extend(this, {
	 		init:function(){
	 			var biz = GetQueryString("biz");
	 			var jsonPath = "./stencilsetV3.json";
	 			if(biz){
	 				self.biz = true;
	 				jsonPath = "./stencilset-biz.json";
	 				jQuery.ajax( {
						type : "POST",
						cache:false,
						url : realPath+"/wkf_getBizByXml.action",
						success : function(data) {
							self.$bizPropery = data;
						}
					});
	 			}
	 			$.getJSON(jsonPath, function(data){ 
					self.$processInfoData = data.processInfo;
					self.$stencilsData = data.stencils;
					self.$properyPackagesData = data.properyPackages;
					self.$lineInfoData = data.lines;
					
					//编辑修改
					var processId = GetQueryString("objectId");
					 if(processId!=null){
						 var openUrl = realPath+"/wkfDesgin/findById";
						 Reload();
						 $(".double-bounce").show();
						 jQuery.ajax( {
							type : "POST",
							cache:false,
							url : openUrl,
							data :{jsonData:processId},
							success : function(data) {
								data = JSON.parse(data);
					        	golFlow.loadData(data);
					        	 $(".double-bounce").hide();
							}
						});
					 }
				}); 
	 		},
	 		createPropDialog:function($selector,that){
	 			var className = that.className;
	 			var id = that.id;
	 			self.selectorId = id;
	 			var flag = false;
	 			if(className=="process"){
	 				if(self.procsJson.properties==null){
	 					self.procsJson['properties']={};
	 				}
	 				self.jsonData = $.extend(true,{},self.procsJson);
	 			}else if(className=="line"){
	 				var $lineData = self.lineJson[id];
	 				if($lineData.properties==null){
	 					$lineData['properties']={};
	 					self.jsonData = $lineData;
	 				}else{
	 					self.jsonData = $.extend(true,{},$lineData);
	 				}
	 			}else{
	 				self.nodeJson = golFlow.getNodeJson(id);
					if(self.nodeJson.properties==null){
						self.nodeJson['properties']={};
						self.jsonData = self.nodeJson;
					}else{
						self.jsonData = $.extend(true,{},self.nodeJson);
					}
	 			}
	 			$selector.removeClass("openList");
	 			$selector.find(".property").remove();
	 			$selector.find(".tc-main").remove();
	 			self.$selector = $selector;
	 			var $property = $('<div class="property"></div>');
	 			var pages = $('<ul id="pages"></ul>');
	 			var tabs = $('<div id="tabs" class="tabs active"></div>');
	 			var d = $('<div class="tabs-list"></dvi>');
	 			var bottomBtn = $('<div class="tc-btn"><div class="tc-btnr"><a class="apply">确定</a><a class="cancel">取消</a></div></div>');
	 			
	 			tabs.append(d);
	 			$property.append(pages).append(tabs).append(bottomBtn);
	 			$selector.append($property);
	 			
	 			var nodeType;
                var i;
	 			for(i in self.$stencilsData){
	 				nodeType = self.$stencilsData[i].type;
	 				if(className.indexOf(nodeType)!=-1){
	 					var ProperPackages = self.$stencilsData[i].properPackages;
	 					for(i in ProperPackages){
	 						var packageName = ProperPackages[i].title;
	 						var packageValue = ProperPackages[i].href;
	 						self.createPropContent(packageName,packageValue);
	 					}
						break;
	 				}
	 			}
	 			
	 			bottomBtn.find(".cancel").bind("click",function(){
	 				hideModelAlert();
	 			});
	 			bottomBtn.find(".apply").bind("click",{$property:$property,nodeType:nodeType},self.prop.common.saveProp);
	 			
	 			$("body").bind('keypress',function(event){
		            if(event.keyCode == "13")    
		            {
		                bottomBtn.find(".apply").click();
		            }
		        });
	 			
	 			$selector.find('#pages').lavaLamp();
	 			
	 			//划屏
	 			$(".tabs .tabs-list").css("width",$selector.find('#pages').children("li").length*100 + "%");
	 			$(".tabs .tabs-list .item").css("width",1/$selector.find('#pages').children("li").length * 100 + "%");
	 			$selector.find('#pages').on('click', '.pages-btn', function(event) {
				    var curIndex = $(this).index()
				      , mlValue = '-' + curIndex * 100 + '%';
				    if( $selector.find('#tabs').hasClass('active') ){
				      $selector.find('#tabs .tabs-list').animate({marginLeft: mlValue});
				    }
				    else{
				      $selector.find('#tabs .tabs-list').css({marginLeft: mlValue});
				    }
				    var device = $(this).data('device');
				    $(this).addClass('btn-active').siblings().removeClass('btn-active');
				    $(device).addClass('item-active').siblings().removeClass('item-active');
				    return false;
				});
				$selector.find('#pages').children("li").eq(0).click();
				
	 		},
	 		createPropContent:function(name,value){
	 			self.$selector.find('#pages').append('<li class="pages-btn" data-device="#'+value+'">'+name+'</li>');
	 			$(".tabs-list").append('<div id="'+value+'" class="item"></div>');
	 			self.prop.init(value);
	 		},
		 	prop:{
		 		init:function(key){
		 			var $propCon = self.$selector.find("#"+key);
		 			var properyPackages = self.$properyPackagesData[key]; 
		 			for(var p in properyPackages){
		 				var title = properyPackages[p].title;
			 			var data = properyPackages[p].data;
			 			var $row = $('<div class="prop-row"></div>');
			 			if(title!=null&&title!="")
			 				$row.append('<div class="prop-title"><span>'+title+'</span><i></i></div>');
			 			for(var i in data){
			 				if(!data[i].visibility) continue;
			 				var value =data[i].value;
			 				if(self.jsonData.properties[data[i].id])
			 					value = self.jsonData.properties[data[i].id].value;
			 				switch(data[i].type){
								case "String":
									self.prop.create.string($row,data[i],value);
								 	break;
								case "Text":
									self.prop.create.text($row,data[i],value);
								  	break;
								case "Tree":
									self.prop.create.tree($row,data[i],value);
								  	break;
								case "Tab":
									self.prop.create.tab($row,data[i],value);
								  	break;
								case "Choice":
									self.prop.create.choice($row,data[i],value);
								  	break;
								case "Select":
									self.prop.create.select($row,data[i],value);
								  	break;
								case "Form":
									self.prop.create.form($row,data[i],value);
								  	break;
								case "Radio":
									if(self.jsonData.properties["normal-"+data[i].id])
					 					value = self.jsonData.properties["normal-"+data[i].id].value;
									self.prop.create.radio($row,data[i],value);
								  	break;
								default:
								  	break;
							}
			 				self.display = "";
			 				self.prop.initProperties(data[i]);
			 			}
			 			$propCon.append($row);
		 			}
		 		},
		 		create:{
		 			string:function($row,data,value){
		 				if(!value) value = "";
		 				if(typeof(self.selectorId)=="undefined"){
		 					if(data.id=="basedescription"){
			 					value = typeof(self.jsonData.properties.basedescription)=="undefined"?"":self.jsonData.properties.basedescription.value;
			 				}else if(data.id=="basename"){
			 					value = typeof(self.jsonData.properties.basename)=="undefined"?"":self.jsonData.properties.basename.value;
			 				}
		 				}else{
		 					/*if(data.id=="basedescription"){
			 					value = typeof(self.jsonData.name)=="undefined"?"":self.jsonData.name;
			 				}else if(data.id=="basename"){
			 					value = typeof(self.selectorId)=="undefined"?"":self.selectorId;
			 				}*/
		 				}
		 				if(data.id=="basename"&&self.biz){
		 					self.display = "none";
		 				}
		 				if(data.id=="overrideid"){
		 					self.display = "none";
		 					if(""==value){
		 						value = self.selectorId;
		 					}
		 				}
		 				if(data.id=="create"){
		 					value = new Date().format("yyyy-MM-dd hh:mm:ss");
		 				}
		 				var $input = $('<div class="prop-string" style="display:'+self.display+'"><div class="prop-string-title"><span>'+data.title+'</span></div><div class="prop-string-value '+data.id+'"><input id="'+data.id+'" type="text" value="'+value+'"></div></div>');
					 	$row.append($input);
					 	$input.click(function(){
					 		return false;
					 	});
		 			},
		 			text:function($row,data,value){
		 				var $area = $('<div class="prop-text"><div class="prop-text-title"><span>'+data.title+'</span></div><div class="prop-text-value"><textarea id="'+data.id+'" cols="40" rows="3">'+value+'</textarea></div></div>');
					 	$row.append($area);
					 	$area.click(function(){
					 		return false;
					 	});
		 			},
		 			tree:function($row,data,value,pid){
		 				var $input = $('<div class="prop-tree"><div class="prop-tree-title"><span>'+data.title+'</span></div><div class="prop-tree-value"><input id="'+pid+"-"+data.id+'" type="text" value="'+((typeof(value.name)=="undefined")?"":value.name)+'" placeholder="'+data.description+'"></div><button>选择</button></div>');
		 				var menuContent = $('<div id="menuContent" class="menuContent" style="display:none; position: absolute;"><ul id="'+pid+"-"+data.id+'_tree" class="ztreemenu" style="margin-top:0; width:250px;"></ul></div>');
		 				$input.find("button").bind("click",{dom:$input.find("input").eq(0)},self.prop.common.showMenu);
		 				$row.append($input);
		 				$(".cd-popup").append(menuContent);
		 				
		 				var setting = {
							view: {
								dblClickExpand: false
							},
							data: {
								simpleData: {
									enable: true
								}
							},
							callback: {
								onClick: function(e, treeId, treeNode){
									var zTree = $.fn.zTree.getZTreeObj(pid+"-"+data.id+"_tree"),
									nodes = zTree.getSelectedNodes(),
									v = "";
									nodes.sort(function compare(a,b){return a.id-b.id;});
									for (var i=0, l=nodes.length; i<l; i++) {
										v += nodes[i].name + ",";
									}
									if (v.length > 0 ) v = v.substring(0, v.length-1);
									self.treeinput.val(v);
									self.treeinput.attr("tid",nodes[0].tId);
									$("#menuContent").fadeOut("fast");
								}
							}
						};
		 				
		 				 $.ajax({
		 		             type: "POST",
		 		             url: realPath+"/"+data.url,
		 		             dataType: "json",
		 		             success: function(item){
		 		            	var ztree = $.fn.zTree.init($("#"+pid+"-"+data.id+"_tree"), setting, item);
		 		            	if(typeof(value.id)=="undefined"||value.id=="") return;
		 		            	var node = ztree.getNodeByParam("id", value.id, null);
		 		            	ztree.selectNode(node);
		 		            	$input.find("input").val(node.name);
		 		             }
		 		         });
		 			},
		 			tab:function($row,data,value){
		 				var $tab = $('<div class="prop-tab"><ul class="prop-tab-ul"></ul></div>');
						var $ul = $tab.children("ul").eq(0);
						$row.append($tab);
						var items = data.items;
						var appType;
						for(var t in items){
							$ul.append('<li id="'+items[t].id+'">'+items[t].title+'</li>');
							$tab.append('<div class="prop-con" name="'+items[t].id+'"></div>');
							var optItems = items[t].items;
							for(var o in optItems){
								if(!optItems[o].visibility) continue;
								value = optItems[o].value;
								var propCon = $tab.find(".prop-con[name='"+items[t].id+"']");
				 				//value = self.jsonData.properties[data[i].id].value;
				 				switch(optItems[o].type){
									case "Select":
										var multiple = "";
										if(optItems[o].parm=="multiple"){
											multiple = "multiple";
										}
										if(self.jsonData.properties[data.id+"-"+optItems[o].id]){
											value = self.jsonData.properties[data.id+"-"+optItems[o].id].value;
											appType = self.jsonData.properties[data.id+"-"+optItems[o].id].id;
										}else{
											value = [];
											appType="";
										}
										var $select = $('<div class="prop-select"><div class="prop-select-title"><span>'+optItems[o].title+'</span></div>'
										+'<div class="prop-select-value"><i></i><select id="'+data.id+"-"+optItems[o].id+'" '+multiple+'></div></div>');
										var desc = optItems[o].description;
										propCon.append($select);
                                        var option;
                                        var i;
                                        var ms;
										if(typeof(optItems[o].url)!="undefined"){
											ms = $select.find("#"+data.id+"-"+optItems[o].id).select2({
										        placeholder: desc,  
										        tags:true,  
										        delay: 250,
										        createTag:function (decorated, params) {  
										            return null;  
										        },
										        templateSelection : function (item) { return item.text; },  // 选择结果中的显示
										        templateResult    : function (item) { return item.text+"("+item.id+")"; },  // 搜索列表中的显示
										        ajax : {
										            url:realPath+"/"+optItems[o].url,              // 异步请求地址
										            dataType:"json",  
										            data:function (params) {// 请求参数（GET）
										                return { query: params.term };
										            },
										            processResults:function (data) { // 构造返回结果
										            	return {results: data};
										            }  
										        },
										        escapeMarkup: function (markup) { return markup; }
										    });
											
											for(i in value){
												option = new Option(value[i].text,value[i].id,true, true);
												ms.append(option);
											}
											ms.trigger('change');
										}else{
											if(self.jsonData.properties[optItems[o].id]){
												value = self.jsonData.properties[optItems[o].id].value;
												$select.find("#"+data.id+"-"+optItems[o].id).append(' <option value="" selected="selected"></option>');
											}else{
												value = "";
											}
											var selectData = self.$bizPropery[optItems[o].id];
											ms = $select.find("#"+data.id+"-"+optItems[o].id).select2({
												data: selectData,
										        placeholder: desc,  
										        tags:true,  
										        delay: 250,
										        createTag:function (decorated, params) {  
										            return null;  
										        },
										        templateSelection : function (item) { return item.text; },  // 选择结果中的显示
										        escapeMarkup: function (markup) { return markup; }
										    });
											if (typeof(value) === 'object'){
												for(i in value){
													ms.val(value[i].id).trigger('change');
												}
											}else if(self.biz){
												if($select.find("#"+data.id+"-"+optItems[o].id).find("option[value='"+value+"']").length==0){
													option = new Option(value,value,true, true);
													ms.append(option);
													ms.trigger('change');
												}else{
													ms.val(value).trigger('change');
												}
											}else{
												ms.val(value).trigger('change');
											}
										}
										break;
									case "Radio":
										if(self.jsonData.properties[items[t].id+"-"+optItems[o].id]){
											value = self.jsonData.properties[items[t].id+"-"+optItems[o].id].value;
										}else{
											value = "";
										}
										self.prop.create.radio(propCon,optItems[o],value,items[t].id);
										if(value=="pass_number"||value=="pass_ratio"){
											var strategy = self.jsonData.properties["strategy-value"];
											if(typeof(strategy)=="undefined"){
												strategy={value:""};
											}
											var $input = $('<div class="prop-string-value"><input id="strategy-value" type="text" value="'+strategy.value+'"></div>');
											propCon.find(".prop-radio-value span[name='"+value+"']").parent().parent().append($input);
											$input.click(function(){
										 		return false;
										 	});
										}
										break;
									case "Tree":
										if(self.jsonData.properties[data.id+"-"+optItems[o].id]){
											value = self.jsonData.properties[data.id+"-"+optItems[o].id].value;
										}else{
											value = {};
										}
										self.prop.create.tree(propCon,optItems[o],value,data.id);
										break;
									case "String":
										if(self.jsonData.properties[optItems[o].id]){
											value = self.jsonData.properties[optItems[o].id].value;
										}else{
											value = "";
										}
										self.prop.create.string(propCon,optItems[o],value);
										break;
									default:
									  	break;
								}
							}
						}
						if((appType==""||appType==null)&&self.jsonData.properties["user-is-assign-next"]){
								appType = "appType-chooseUser";
						}
						if(appType=="appType-chooseUser"||appType=="copytoType-chooseUser"){
							$ul.children("li").eq(1).addClass("curr");
							$tab.children(".prop-con").eq(1).addClass("curr");
						}else{
							$ul.children("li").eq(0).addClass("curr");
							$tab.children(".prop-con").eq(0).addClass("curr");
						}
						$ul.children("li").click(function(){
							$(this).siblings("li").removeClass("curr");
							$(this).addClass("curr");
							$(this).parent().parent().find(".prop-con").removeClass("curr");
							$(this).parent().parent().find(".prop-con[name='"+this.id+"']").addClass("curr");
						});
		 			},
		 			choice:function($row,data,value){
		 				var $choice = $('<div class="prop-choice"><div class="prop-choice-title"><span>'+data.title+'</span></div><div class="prop-choice-value"><select id="'+data.id+'"><option value=""></option></select></div></div>');
					 	
					 	if(data.items)
					 	for(var i in data.items){
					 		var selected = "";
					 		if(value == data.items[i].id){
					 			selected = "selected = 'selected'";
					 		}
					 		$("<option value='"+data.items[i].id+"' "+selected+" >"+data.items[i].title+"</option>").appendTo($choice.find("select"));
					 	}
					 	$row.append($choice);
					 	
					 	if(data.id=="defaultRollbackNode"){
					 		if(typeof(value.id)!="undefined")
					 			value = value.id.split(",")[0];
					 		else
					 			value = "";
					 		self.prop.common.rollback($choice,value);
					 	}
					 	
					 	$choice.find("select").select2({
							minimumResultsForSearch: Infinity
						});
		 			},
		 			select:function($row,data,value){
		 				var selectData = self.$bizPropery[data.id];
		 				var multiple = "";
						if(data.parm=="multiple"){
							multiple = "multiple";
						}
						if(self.jsonData.properties[data.id]){
							value = self.jsonData.properties[data.id].value;
							appType = self.jsonData.properties[data.id].id;
						}else{
							value = [];
							appType="";
						}
						var $select = $('<div class="prop-select"><div class="prop-select-title"><span>'+data.title+'</span></div>'
						+'<div class="prop-select-value"><i></i><select id="'+data.id+'" '+multiple+'></div></div>');
						var desc = data.description;
						$row.append($select);
						if(value&&value!=""){
//							$select.find("#"+data.id).append(' <option value="'+value[0].id+'" selected="selected">'+value[0].text+'</option>');
						}else{
							$select.find("#"+data.id).append(' <option value="" selected="selected"></option>');
						}
						var ms = $select.find("#"+data.id).select2({  
							data: selectData,
					        placeholder: desc,  
					        tags:true,  
					        delay: 250,
					        createTag:function (decorated, params) {  
					            return null;  
					        },
					        templateSelection : function (item) { return item.text; },  // 选择结果中的显示
//					        templateResult    : function (item) { return item.text+"("+item.id+")"; },  // 搜索列表中的显示
//					        ajax : {
//					            url:realPath+"/"+data.url,              // 异步请求地址
//					            dataType:"json",  
//					            data:function (params) {// 请求参数（GET）
//					                return { query: params.term };
//					            },
//					            processResults:function (data) { // 构造返回结果
//					            	return {results: data};
//					            }  
//					        },
					        escapeMarkup: function (markup) { return markup; }
					    });
						if (typeof(value) === 'object'){
							for(var i in value){
								ms.val(value[i].id).trigger('change');
							}
						}else if(self.biz){
							if($select.find("#"+data.id).find("option[value='"+value+"']").length==0){
								var option = new Option(value,value,true, true);
								ms.append(option);
								ms.trigger('change');
							}else{
								ms.val(value).trigger('change');
							}
						}else{
							ms.val(value).trigger('change');
						}
		 			},
		 			form:function($row,data,value){
		 				var $form = $('<div id="'+data.id+'" class="form"></div>');
		 				var $btnDiv = $('<div class="tc-layout-row"></div>').appendTo($form);
		 				var $table = $(' <div class="tc-main"></div>').appendTo($form);
		 				var $addBtn = $('<a class="tcadd">增加</a>').appendTo($btnDiv);
		 				var $delBtn = $('<a class="tcdelete">删除</a>').appendTo($btnDiv);
		 				var $header = $('<table width="100%" border="0" cellspacing="0" cellpadding="0" class="tabletit"><tr><td width="10%">&nbsp;</td></tr></table>').appendTo($table);
		 				var complexItems = data.complexItems,
		 					compWidth = 90/data.complexItems.length;
                        var i;
		 				for(i in complexItems){
		 					$('<td width="'+compWidth+'%">'+complexItems[i].title+'</td>').appendTo($header.find("tr").eq(0));
		 				}
		 				var $ul = $('<ul class="tc-mainul"></ul>').appendTo($table);

		 				for(i=0;i<value.length;i++){
		 					var $li = $('<li><table width="100%" border="0" cellspacing="0" cellpadding="0" class="addtab"><tr><td width="10%"><input type="checkbox"></td></tr></table></li>').appendTo($ul);
			 				var trData = value[i];
			 				for(var j=0;j<trData.length;j++){
			 					if(trData[j].name=="java.lang.Boolean"){
			 						trData[j].name = "布尔型";
			 					}else if(trData[j].name=="java.lang.String"){
			 						trData[j].name = "字符型";
			 					}else if(trData[j].name=="java.lang.Integer"){
			 						trData[j].name = "数值型";
			 					}else if(trData[j].name=="java.lang.Double"){
			 						trData[j].name = "金额型";
			 					}else if(trData[j].name=="java.lang.Byte"){
			 						trData[j].name = "字节型";
			 					}
			 					$li.find("tr").append('<td width="'+compWidth+'%" id="'+trData[j].id+'" name="'+trData[j].value+'">'+trData[j].name+'</td>')
			 				}
			 				$li.bind("dblclick",{complexItems:complexItems},self.prop.common.editRow);
		 				}
		 				$row.append($form);
		 				$addBtn.bind("click",{complexItems:complexItems},self.prop.common.addRow);
		 			},
		 			radio:function($row,data,value,pid){
		 				var title = data.title;
						var raItems = data.items;
						if(typeof(pid)=="undefined"){
							pid = "normal";
						}
						var $radio = $('<div class="prop-radio"><div class="prop-radio-title"><span>'+title+'</span></div><div class="prop-radio-value"></div></div>');
						for(var r in raItems){
							$radio.find(".prop-radio-value").append("<span id="+pid+"-"+data.id+" name="+raItems[r].id+">"+raItems[r].title+"</span>")
							$radio.find(".prop-radio-value span[name='"+raItems[r].id+"']").click(function(){
								$(this).siblings('span').removeClass("curr"); 
								$(this).addClass("curr");
								$("#strategy-value").parent().remove();
								if($(this).attr("name")=="pass_number"||$(this).attr("name")=="pass_ratio"){
									var $input = $('<div class="prop-string-value"><input id="strategy-value" type="text" value=""></div>');
									$(this).parent().parent().append($input);
									$input.click(function(){
								 		return false;
								 	});
								}
								return false;
							});
						}
						if(value!=""){
							$radio.find(".prop-radio-value span[name='"+value+"']").eq(0).addClass("curr");
						}else{
							$radio.find(".prop-radio-value span").eq(0).addClass("curr");
						}
						$row.append($radio);
		 			}
		 		},
		 		common:{
		 			addRow:function(evt){
		 				var complexItems = evt.data.complexItems;
		 				var $form = $(this).parents(".form");
		 				if($form.find(".addcontent").length>0) return;
		 				var $addcontent = $("<div class='addcontent'></div>").insertBefore($form.find(".tc-main").eq(0));
		 				for(var i in complexItems){
		 					switch(complexItems[i].type){
		 						case "String":
		 							self.prop.create.string($addcontent,complexItems[i]);
		 							break;
		 						case "Choice":
		 							self.prop.create.choice($addcontent,complexItems[i]);
		 							break;
		 					}
		 				}
		 				$addcontent.append("<i class='addcsave'></i><i class='addcdel'></i>")
		 				$addcontent.slideDown(function(){
		 					$(this).css("display","inline-block");
		 				});
		 				
		 				$addcontent.find(".addcdel").bind("click",{$addcontent:$addcontent,complexItems:complexItems},self.prop.common.closeRow);
		 				$addcontent.find(".addcsave").bind("click",{$addcontent:$addcontent,complexItems:complexItems},self.prop.common.submitRow);
		 			},
		 			editRow:function(evt){
		 				
		 				var complexItems = evt.data.complexItems;
		 				var $ul = $(this).parent();
		 				var et = $ul.find(".editcontent");
		 				if(et.length>0){
		 					et.slideUp(function(){
		 						et.remove();
		 					});
		 				}
		 				var $form = $(this).parents(".form");
		 				if($form.find(".addcontent").length>0){
		 					$form.find(".addcontent").slideUp(function(){
		 						$form.find(".addcontent").remove();
		 					});
		 				}
		 				var $editcontent = $("<div class='editcontent'></div>").insertAfter($(this));
		 				for(var i in complexItems){
		 					switch(complexItems[i].type){
		 						case "String":
		 							self.prop.create.string($editcontent,complexItems[i],$(this).find("td").eq(i).next().html());
		 							break;
		 						case "Choice":
		 							self.prop.create.choice($editcontent,complexItems[i],$(this).find("td").eq(i).next().attr("name"));
		 							break;
		 					}
		 				}
		 				$editcontent.append("<i class='addcsave'></i><i class='addcdel'></i>")
		 				$editcontent.slideDown(function(){
		 					$(this).css("display","inline-block");
		 				});
		 					
		 				
		 				$editcontent.find(".addcdel").bind("click",{$addcontent:$editcontent},self.prop.common.closeRow);
		 				$editcontent.find(".addcsave").bind("click",{$editcontent:$editcontent},self.prop.common.updateRow);
		 			},
		 			closeRow:function(evt){
		 				var $addcontent = evt.data.$addcontent;
		 				$addcontent.slideUp(function(){
	 						$addcontent.remove();
	 					});
		 			},
		 			submitRow:function(evt){
		 				var $addcontent = evt.data.$addcontent;
		 				var complexItems = evt.data.complexItems;
		 				var $ul = $(this).parents(".form").find(".tc-mainul");
		 				var $li = $('<li><table width="100%" border="0" cellspacing="0" cellpadding="0" class="addtab"><tr><td width="10%"><input type="checkbox"></td></tr></table></li>').appendTo($ul);
		 				var compWidth = 90/($addcontent.children().length-2);
		 				for(var i =0;i<$addcontent.children().length-2;i++){
		 					var className = $addcontent.children()[i].className;
		 					var id,str,val="";
		 					if(className.indexOf("string")!=-1){
		 						id = $addcontent.children().eq(i).find("input").attr("id");
		 						str = $addcontent.children().eq(i).find("input").val();
		 					}else if(className.indexOf("choice")!=-1){
		 						id = $addcontent.children().eq(i).find("select").attr("id");
		 						str = $addcontent.children().eq(i).find("select").select2('data')[0].text;
		 						val = $addcontent.children().eq(i).find("select").select2('data')[0].id;
		 					}else{
		 						continue;
		 					}
		 					$li.find("tr").append('<td width="'+compWidth+'%" id="'+id+'" name="'+val+'">'+str+'</td>')
		 				}
		 				$li.bind("dblclick",{complexItems:complexItems},self.prop.common.editRow);
		 				$addcontent.slideUp(function(){
	 						$addcontent.remove();
	 					});
		 			},
		 			updateRow:function(evt){
		 				var $editcontent = evt.data.$editcontent;
		 				var $li = $(this).parents(".editcontent").prev();
		 				for(var i =0;i<$editcontent.children().length-2;i++){
		 					var className = $editcontent.children()[i].className;
		 					var str,val="";
		 					if(className.indexOf("string")!=-1){
		 						str = $editcontent.children().eq(i).find("input").val();
		 					}else if(className.indexOf("choice")!=-1){
		 						str = $editcontent.children().eq(i).find("select").select2('data')[0].text;
		 						val = $editcontent.children().eq(i).find("select").select2("val");
		 					}else{
		 						continue;
		 					}
		 					$li.find("td").eq(i+1).html(str);
		 					$li.find("td").eq(i+1).attr("name",val);
		 				}
		 				$editcontent.slideUp(function(){
	 						$editcontent.remove();
	 					});
		 			},
		 			saveProp:function(evt){
		 				var $property = evt.data.$property;
		 				var nodeType = (evt.data.nodeType=="line")?"line":(evt.data.nodeType=="process")?"process":"node";
		 				var tabsList = $property.find(".tabs-list").eq(0).find(".prop-row");
		 				var key = self.jsonData.properties.key;
		 				self.jsonData['properties']={};
		 				self.jsonData.properties["key"] = key;
		 				saveerror:
		 				for(var i=0;i<tabsList.length;i++){
		 					var propRow = tabsList.eq(i).children();
		 					for(var j=0;j<propRow.length;j++){
		 						if(self.prop.setProperties(propRow.eq(j),nodeType,self.selectorId)){
		 							break saveerror;
		 						}	 					
		 					}
		 				}
		 				if(!self.saveFlag){
		 					alert("节点名称重复！");
		 					return;
		 				}
		 				if(nodeType=="process"){
		 					golFlow.$process = self.jsonData;
		 				}else if(nodeType=="line"){
		 					golFlow.$lineData[self.selectorId] = self.jsonData;
		 				}else{
		 					golFlow.setNodeJson(self.selectorId,self.jsonData);
		 				}
		 				
		 				hideModelAlert();
		 				$("body").unbind("keypress");
		 				console.log(self.jsonData);
		 			},
		 			showMenu:function(event){
		 				if(!$("#menuContent").is(":hidden")){return;}
		 				var st = $('#modeler')[0].scrollTop;
		 				var sl = $('#modeler')[0].scrollLeft;
		 				var cityOffset = event.data.dom.offset();
		 				self.treeinput = event.data.dom;
						$("#menuContent").css({left:cityOffset.left - 3+ "px", top:cityOffset.top+26+ "px",zIndex:11111}).slideDown("fast");
						$("body").bind("mousedown", self.prop.common.onBodyDown);
		 			},
		 			onBodyDown:function(event){
		 				if (!(event.target.id == "menuContent" || $(event.target).parents("#menuContent").length>0)) {
							$("#menuContent").fadeOut("fast");
							$("body").unbind("mousedown", self.prop.common.onBodyDown);
						}
		 			},
		 			rollback:function($choice,selectedId){
				 		var arr = new Array();
				 		var data = golFlow.exportData();
				 		var flag =true;
				 		var id = self.selectorId;
                        var i;
				 		while(flag){
				 			flag =false;
				 			for(i in data['lines']){
				 				var to = data['lines'][i].to;
				 				if(to==id){
				 					id = data['lines'][i].from;
				 					arr[arr.length]=data['lines'][i].from+","+data['nodes'][data['lines'][i].from].type;
				 					flag =true;
				 					break;
				 				}
				 			}
				 		}
				 		for(i=0;i<arr.length;i++){
				 			var tmp = arr[i].split(",")[0];
				 			var tmp1 = arr[i].split(",")[1];
				 			var selected = "";
				 			if(selectedId!=""&&tmp==selectedId){
				 				selected = "selected = 'selected'";
				 			}
				 			if(data['nodes'][tmp].type=="task round"||data['nodes'][tmp].type=="node round"){
				 				$("<option value='"+tmp+","+tmp1+"' "+selected+">"+data['nodes'][tmp].name+"</option>").appendTo($choice.find("select"));
				 			}
				 		}
				 	}
		 		},
		 		initProperties:function(properyData){
					var tmp = {id:properyData.id,value:properyData.value,type:properyData.type};
					self.jsonData.properties[properyData.id] = tmp;
		 		},
		 		setProperties:function(propRow,nodeType,nodeId){
                    var data;
                    var i;
                    var id;
                    var properyData;
                    var flag;
		 			switch(propRow[0].className){
 						case "prop-string":
 							properyData = propRow.find("input")[0];
 							data = {id:properyData.id,value:properyData.value,type:"String"};
 							self.prop.initProperties(data);
 							if(properyData.id=="overrideid"){
 								if(nodeId!=properyData.value){
 									flag = golFlow.transNewId(nodeId,properyData.value,nodeType);
 									if(typeof(nodeId)!="undefined"){
 										golFlow.$focus=properyData.value;
 									}
 			 						if(!flag){
 			 							self.saveFlag = false;
 			 							return true;
 			 						}else{
 			 							self.selectorId = properyData.value;
 			 							self.saveFlag = true;
 			 						}
 			 						self.selectorId = properyData.value;
 								}
 							}else if(properyData.id=="basename"){
 								if(propRow.closest("#linebase").length>0) break;
 								if(nodeId!=properyData.value){
 									flag = golFlow.transNewId(nodeId,properyData.value,nodeType);
 									if(typeof(nodeId)!="undefined"){
 										golFlow.$focus=properyData.value;
 									}
 			 						if(!flag){
 			 							self.saveFlag = false;
 			 							return true;
 			 						}else{
 			 							self.selectorId = properyData.value;
 			 							self.saveFlag = true;
 			 						}
 			 						self.selectorId = properyData.value;
 								}
		 					}else if(properyData.id=="basedescription"){
				 				golFlow.setName(nodeId,properyData.value,nodeType);
				 				self.jsonData["name"] = properyData.value;
		 					}
 							break;
 						case "prop-text":
 							properyData = propRow.find("textarea")[0];
 							data = {id:properyData.id,value:properyData.value,type:"Text"};
 							self.prop.initProperties(data);
 							break;
 						case "prop-tree":
 							id = propRow.find("input").attr("id");
 							var tId = propRow.find("input").attr("tid");
 							var zTree = $.fn.zTree.getZTreeObj(id+"_tree");
 							//var data;
 							if(zTree==null){
 								data = {id:id,value:{},type:"Tree"};
 							}else{
 								var node = zTree.getNodeByTId(tId);
 								if(node==null) node = {};
 	 							data = {id:id,value:node,type:"Tree"};
 							}
 							self.prop.initProperties(data);
 							break;
 						case "prop-radio":
 							properyData = propRow.find(".prop-radio-value .curr").eq(0);
 							data = {id:properyData.attr("id"),value:properyData.attr("name"),type:"Radio"};
 							self.prop.initProperties(data);
 							if(properyData.attr("name")=="pass_number"||properyData.attr("name")=="pass_ratio"){
 								var strategy = properyData.parent().parent().find("#strategy-value");
 								data = {id:strategy.attr("id"),value:strategy.val(),type:"String"};
 								self.prop.initProperties(data);
 							}
 							break;
 						case "prop-choice":
 							properyData = propRow.find("select").eq(0);
 							data = {id:properyData.attr("id"),value:properyData.select2("data")[0],type:"Choice"};
 							self.prop.initProperties(data);
 							break;
 						case "prop-select":
 							properyData = propRow.find("select").eq(0);
 							data = {id:properyData.attr("id"),value:properyData.select2("data"),type:"Select"};
 							self.prop.initProperties(data);
 							break;
 						case "prop-tab":
 							id = propRow.parent().parent().attr("id");
 							var curr= ".curr";
 							if(id=="copytobase"){
 								curr = "";
 								propCon = propRow.find(".prop-con").eq(1).children();
 	 							for(i=0;i<propCon.length;i++){
 	 								self.prop.setProperties(propCon.eq(i));
 	 							}
 							}
 							var propCon = propRow.find(".prop-con"+curr).eq(0).children();
 							for(i=0;i<propCon.length;i++){
 								self.prop.setProperties(propCon.eq(i));
 							}
 							break;
 						case "form":
 							var arr=[];
 							var list = propRow.find("li tr");
 							for(i=0;i<list.length;i++){
 								var tarr=[];
 								var tdlist = list.eq(i).find("td");
 								for(var j=1;j<tdlist.length;j++){
 									var obj={};
 									if(tdlist.eq(j).attr("name")==""){
 	 									obj["type"] = "String";
 	 								}else{
 	 									obj["type"] = "Choice";
 	 								}
 									obj["id"] = tdlist.eq(j).attr("id");
									obj["value"] = tdlist.eq(j).attr("name");
 									obj["name"] = tdlist.eq(j).html();
 									tarr.push(obj);
 								}
 								arr.push(tarr);
 							}
 							data = {id:propRow.attr("id"),value:arr,type:"Form"};
 							self.prop.initProperties(data);
 							break;
 					}

		 		}
		 	},
		 	line:{
		 		createPropDialog:function(that,$selector,id){
		 			self.lineJson = that.$lineData;
					self.createPropDialog($selector,{className:"line",id:id});
		 		}
		 	}
	 	});
	 }
	 window.prop = main;
})(jQuery)

$(function(){
	prop = new prop();
	prop.init();
});
