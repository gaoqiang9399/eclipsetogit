	//jquery与dwr.util冲突处理
	var jq = jQuery.noConflict();
	
	//报表类别下拉列表change响应事件
	function func_query() {
		var reportType = jq("select[name=reportType]").val();
		if( reportType == "" ) {
			alert("请选择报表类别!");
			return false;
		}
		var accRule = jq("select[name=accRule]").val();
		if( accRule == "" ) {
			alert("请选择会计准则!");
			return false;
		}
		//调用dwr查询数据
		finmodeldwr.getFinModelList(reportType, accRule, function(data){
			if(data != null) {
				var pos = data.indexOf("}||{");
				var fin_param_result = data.substr(0,(pos+1));
				var fin_model_result = data.substr((pos+3));
				fillLeftTable(fin_param_result);
				fillRightTable(fin_model_result);
			} else {
				jq("#fin_parm_body").empty();
			}
		});
	}
	
	//初始化left_table数据行
	function fillLeftTable(fin_param_result) {
		if( fin_param_result == "{}" ) {
			jq("#fin_parm_body").empty();
			return false;
		}
		var results = eval("["+fin_param_result+"]");
		var length = results.length;
		var strHtml = "", code_name = "", code_column  = "", cnt;
		for(var i = 0; i < length; i++) {
			cnt = (1000 + parseInt(results[i].cnt));//防止与right_table.tr中id重复
			code_name = results[i].codeName;
			code_column = results[i].codeColumn;
			var style='';
			if((i+1)%2==0){
				style='background-color:#F3FAF3;height:21px; line-height:21px;'
			}else{
				style='background-color:#fff;height:21px; line-height:21px;';
			}
			strHtml += "<tr id=\"left_tr_" + cnt + "\" style="+style+" >";
			strHtml += "<td class=\"TDstyle01\" align=\"center\"><input type=\"checkbox\" id=\"left_checkbox_";
			strHtml += cnt + "\" value='" + cnt + "' onclick=\"left_checkbox_click(this)\"/>";
			strHtml += "<input type=\"hidden\" id=\"left_hidden_" + cnt + "\" value='" + code_column + "'/></td>";
			strHtml += "<td id=\"left_td_" + cnt + "\" class=\"TDstyle01\" onclick=\"left_td_click(this)\">";
			strHtml += "&nbsp;&nbsp;" + code_name + "</td>";
			strHtml += "</tr>";
		}
		jq("#fin_parm_body").html(strHtml);
	}
	
	//初始化right_table数据行
	function fillRightTable(fin_model_result) {
		if( fin_model_result == "{}" ) {
			jq("#fin_model_body").empty();
			return false;
		}
		var results = eval("["+fin_model_result+"]");
		var length = results.length;
		var strHtml = "", code_name = "", code_type = "", code_column = "", acc_rule = "", cnt;
		for(var i = 0; i < length; i++) {
			cnt = results[i].cnt;
			code_name = results[i].codeName;
			code_type = results[i].codeType;
			code_column = results[i].codeColumn;
			acc_rule = results[i].accRule;
			var style='';
			if((i+1)%2==0){
				style='background-color:#F3FAF3;height:21px; line-height:21px;'
			}else{
				style='background-color:#fff;height:21px; line-height:21px;';
			}
	    	strHtml += "<tr id=\"right_tr_" + cnt + "\" style="+style+" >";
			strHtml += "<td class=\"TDstyle01\" align=\"center\"><input type=\"checkbox\" id=\"right_checkbox_";
			strHtml += cnt + "\" value='" + cnt + "' onclick=\"right_checkbox_click(this)\"/>";
			strHtml += "<input type=\"hidden\" id=\"right_hidden_" + cnt + "\" value='" + code_column + "'/></td>";
			strHtml += "<td id=\"right_td_" + cnt + "\" class=\"TDstyle01\" width=\"50%\" onclick=\"right_td_click(this)\">&nbsp;&nbsp;" + code_name + "</td>";//参数名称
			jq("select[name=code_type]").attr("value",code_type);
			strHtml += "<td class=\"TDstyle01\" align=\"center\">" + jq("#DIV_CODE_TYPE").html() + "</td>";//数据类型
			jq("select[name=acc_rule]").attr("value",acc_rule);
			strHtml += "<td class=\"TDstyle01\" align=\"center\">" + cnt + "</td>";//行次
			strHtml += "</tr>";
		}
		jq("#fin_model_body").html(strHtml);
	}	
	
	//左边模板中单元格单击响应事件
	function left_td_click(obj) {
		var hasSelected = !(jq(obj).parents("tr").find(":checkbox").attr("checked"));
		if(hasSelected) {
			jq(obj).parents("tr").children().removeClass("TDstyle01");
			jq(obj).parents("tr").css("background-color","#cccccc").css("font-size","12px");
		} else {
			jq(obj).parents("tr").children().addClass("TDstyle01");
		}
		jq(obj).parents("tr").find(":checkbox").attr("checked",hasSelected);
	}
	
	//右边模型中单元格单击响应事件
	function right_td_click(obj) {
		var hasSelected = !(jq(obj).parents("tr").find(":checkbox").attr("checked"));
		if(hasSelected) {
			jq(obj).parents("tr").children().removeClass("TDstyle01");
			jq(obj).parents("tr").css("background-color","#cccccc").css("font-size","12px");
		} else {
			jq(obj).parents("tr").children().addClass("TDstyle01");
		}
		jq(obj).parents("tr").find(":checkbox").attr("checked",hasSelected);
	}	
	
	//左边模板中复选框响应事件
	function left_checkbox_click(obj) {
		var tr_obj = jq(obj).parents("tr");
		var hasSelected = obj.checked;
		if(hasSelected) {
			jq(tr_obj).children().removeClass("TDstyle01");
			jq(tr_obj).css("background-color","#cccccc").css("font-size","12px");
		} else {
			jq(tr_obj).children().addClass("TDstyle01");
		}
		obj.checked=hasSelected;
	}
	
	//右边模型中复选框响应事件
	function right_checkbox_click(obj) {
		var tr_obj = jq(obj).parents("tr");
		var hasSelected = obj.checked;
		if(hasSelected) {
			jq(tr_obj).children().removeClass("TDstyle01");
			jq(tr_obj).css("background-color","#cccccc").css("font-size","12px");
		} else {
			jq(tr_obj).children().addClass("TDstyle01");
		}
		obj.checked=hasSelected;
	}	
	
	//向右移动选中模板
	function func_toright() {
		var total = jq("input[type='checkbox'][id*='left_checkbox_']:checked").length;
    	if(total <= 0) {
      		alert("请选择一条记录");
	      	return false;
	    }
	    jq("input[type=button]").attr("disabled","disabled");
	    var strHtml = "", code_name = "", code_column = "", cnt, left_tr;
	    jq("input[type=checkbox][id*=left_checkbox_]:checked").each(function() {
	    	left_tr = jq(this).parent().parent();
	    	//读取所选择行的cnt和code_name
	    	cnt = jq(this).val();
	    	code_name = jq(("#left_td_"+cnt)).text();
	    	code_column = jq(this).parents("td").find(":hidden").val();
	    	var style='background-color:#F3FAF3;height:21px; line-height:21px;'
	    	//追加到right_table中
	    	strHtml = "<tr id=\"right_tr_" + cnt + "\" style="+style+" >";
			strHtml += "<td class=\"TDstyle01\" align=\"center\"><input type=\"checkbox\" id=\"right_checkbox_";
			strHtml += cnt + "\" value='" + cnt + "' onclick=\"right_checkbox_click(this)\"/>";
			strHtml += "<input type=\"hidden\" id=\"right_hidden_" + cnt + "\" value='" + code_column + "'/></td>";
			strHtml += "<td id=\"right_td_" + cnt + "\" class=\"TDstyle01\" width=\"50%\" onclick=\"right_td_click(this)\">" + code_name + "</td>";//参数名称
			strHtml += "<td class=\"TDstyle01\" align=\"center\">" + jq("#DIV_CODE_TYPE").html() + "</td>";//数据类型
			strHtml += "<td class=\"TDstyle01\" align=\"center\">&nbsp;</td>";//行次
			strHtml += "</tr>";
	    	jq("#fin_model_body").append(strHtml);
	    	//删除所选择行
	    	left_tr.remove();
	    });
	    reorg_table("fin_model_body");
	    jq("input[type=button]").removeAttr("disabled");
	}
	
	//向左移动选中模板
	function func_toleft() {
		var total = jq("input[type=checkbox][id*=right_checkbox_]:checked").length;
    	if(total <= 0) {
      		alert("请选择一条记录");
	      	return false;
	    }
	    jq("input[type=button]").attr("disabled","disabled");
	    var strHtml = "", code_name = "", code_column = "", cnt, right_tr;
	    jq("input[type=checkbox][id*=right_checkbox_]:checked").each(function() {
	    	right_tr = jq(this).parent().parent();
	    	//读取所选择行的cnt和code_name
	    	cnt = jq(this).val();
	    	code_name = jq(("#right_td_"+cnt)).text();
	    	code_column = jq(this).parents("td").find(":hidden").val();
	    	var style='background-color:#F3FAF3;height:21px; line-height:21px;'
	    	//追加到left_table中
	    	strHtml = "<tr id=\"left_tr_" + cnt + "\" style="+style+" >";
			strHtml += "<td class=\"TDstyle01\" ><input type=\"checkbox\" id=\"left_checkbox_";
			strHtml += cnt + "\" value='" + cnt + "' onclick=\"left_checkbox_click(this)\"/>";
			strHtml += "<input type=\"hidden\" id=\"right_hidden_" + cnt + "\" value='" + code_column + "'/></td>";
			strHtml += "<td id=\"left_td_" + cnt + "\" class=\"TDstyle01\" onclick=\"left_td_click(this)\">" + code_name + "</td>";
			strHtml += "</tr>";
	    	jq("#fin_parm_body").append(strHtml);
	    	//删除所选择行
	    	jq(right_tr).remove();
	    });
	    reorg_table("fin_model_body");
	    jq("input[type=button]").removeAttr("disabled");
	}
	
	//向上移动选中行
	function func_moveup() {
		var total = jq("input[type=checkbox][id*=right_checkbox_]:checked").length;
    	if(total <= 0) {
      		alert("请选择一条记录");
	      	return false;
	    }
	    if( total > 1 ) {
	    	alert("对不起，只能选择一行记录移动位置！");
	      	return false;
	    }
	    var checkbox_obj = (jq("input[type=checkbox][id*=right_checkbox_]:checked"))[0];
	    moveUp(checkbox_obj);
	}
	
	//向下移动选中行
	function func_movedown() {
		var total = jq("input[type=checkbox][id*=right_checkbox_]:checked").length;
    	if(total <= 0) {
      		alert("请选择一条记录");
	      	return false;
	    }
	    if( total > 1 ) {
	    	alert("对不起，只能选择一行记录移动位置！");
	      	return false;
	    }
	    var checkbox_obj = (jq("input[type=checkbox][id*=right_checkbox_]:checked"))[0];
	    moveDown(checkbox_obj);
	}	
	
	//使表格行上移，接收参数为单元格内的对象(当前是复选框)
	function moveUp(checkbox_obj) {
		//通过复选框对象获取表格行的引用
 		var tr_obj=checkbox_obj.parentNode.parentNode;
 		//如果不是第一行，则与上一行交换顺序
 		if(tr_obj.previousSibling)swapNode(tr_obj,tr_obj.previousSibling);
	}
	
	//使表格行下移，接收参数为单元格内的对象(当前是复选框)
	function moveDown(checkbox_obj) {
 		//通过复选框对象获取表格行的引用
 		var tr_obj=checkbox_obj.parentNode.parentNode;
 		//如果不是最后一行，则与下一行交换顺序
 		if(tr_obj.nextSibling)swapNode(tr_obj,tr_obj.nextSibling);
	}
	
	//定义通用的函数交换两个结点的位置
	function swapNode(node1,node2) {
 		//获取父结点
		var _parent=node1.parentNode;
		//获取两个结点的相对位置
		var _t1=node1.nextSibling;
		var _t2=node2.nextSibling;
		//将node2插入到原来node1的位置
		if(_t1)_parent.insertBefore(node2,_t1);
		else _parent.appendChild(node2);
		//将node1插入到原来node2的位置
		if(_t2)_parent.insertBefore(node1,_t2);
		else _parent.appendChild(node1);
		reorg_table("fin_model_body");
	}
	
	//财务报表模型保存
	function func_save() {
		var report_type = jq("select[name=reportType]").val();
		if(report_type == "") {
      		alert("请选择报表类别!");
	      	return false;
	    }
	    var acc_rule = jq("select[name=accRule]").val();
		if( acc_rule == "" ) {
			alert("请选择会计准则!");
			return false;
		}
		var total = jq("input[type=checkbox][id*=right_checkbox_]").length;
    	if(total <= 0) {
      		alert("请从左边表格中选择记录创建财务报表模型!");
	      	return false;
	    }
	    jq("input[type=button]").attr("disabled","disabled");
	    
	    var cnt_number = 1, code_name = "", code_type = "", code_column = "";
	    var str_cnt = "", str_code_name = "", str_code_type = "", str_code_column = "";
	    var select_obj, right_tr;
	    jq("input[type=checkbox][id*=right_checkbox_]").each(function() {
	    	right_tr = jq(this).parent().parent();
	    	//读取所选择行的cnt和code_name
	    	cnt = jq(this).val();
	    	code_name = jq(("#right_td_"+cnt)).text();
	    	code_column = jq(this).parents("td").find(":hidden").val();
	    	//拼参数名称
	    	str_code_name += code_name + ",";
	    	//拼数据类型
	    	select_obj = jq(right_tr).find("select");
	    	code_type = (select_obj[0]).value;
	    	str_code_type += code_type + ",";
	    	str_code_column += code_column + ",";
	    	//拼行次
	    	str_cnt += cnt_number + ",";
	    	cnt_number++;
	    });
	    jq("#cusFinModel_reportType").val(report_type);
	    jq("#cusFinModel_codeName").val(str_code_name);
	    jq("#cusFinModel_codeType").val(str_code_type);
	    jq("#cusFinModel_codeColumn").val(str_code_column);
	    jq("#cusFinModel_accRule").val(acc_rule);
	    jq("#cusFinModel_cnts").val(str_cnt);
	    document.operform.action = '/factor/cusFinModel/insertOrUpdate';
	    document.operform.submit();
	}
	
	//财务报表模型查询用于拖拽编辑
	function func_list() {
		var reportType = jq("select[name=reportType]").val();
		if(reportType == "") {
      		alert("请选择报表类别!");
	      	return false;
	    }
	    var accRule = jq("select[name=accRule]").val();
		if( accRule == "" ) {
			alert("请选择会计准则!");
			return false;
		}
		jq("input[type=button]").attr("disabled","disabled");
	    jq("#cusFinModel_reportType").val(reportType);
	    jq("#cusFinModel_accRule").val(accRule);
	    document.operform.action = '/factor/cusFinModel/listCusFinModel';
	    document.operform.submit();
	}
	
	//遍历right_table(tbody)中的行，并重新生成行次
	function reorg_table(obj_id) {
		jq(("#"+obj_id+">tr")).each(function() {
	    	var row_number = (jq(this)[0]).rowIndex;
	    	jq(this).find("td:eq(3)").html(row_number);
	    });
	}
	
	function checkbox_select(obj, objName) {
		var hasSelected = obj.checked;
		jq(("input[type=checkbox][id*=" + objName + "]")).attr("checked",hasSelected);
		if(hasSelected) {
			jq(("input[type=checkbox][id*=" + objName + "]")).each(
				function() {
					jq(this).parent().parent().children().removeClass("TDstyle01");
					//jq(this).parents("tr").css("background-color","#cccccc").css("font-size","12px");
				}
			);
		} else {
			jq(("input[type=checkbox][id*=" + objName + "]")).each(
				function() {
					jq(this).parent().parent().children().addClass("TDstyle01");
				}
			);
		}
	}