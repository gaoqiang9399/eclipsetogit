$(function() {
	$("body").bind("click", function(e) {
		if(e.target.id != "mulitSelector" && $(e.target).parents("#mulitSelector").length == 0 && e.target.className.indexOf("mulitSelectorDiv") ==-1) {
			$("#mulitSelector").remove();
		}
	});
});
(function($) {

	$.fn.mulitselector = function(options) {
		var $input = $(this);
		var timestamp = Date.parse(new Date());
		var $div = $('<div class="mulitSelectorDiv '+timestamp+'"></div>');
		$input.parent().append($div);
		$input.attr("mark",timestamp);
		$input.attr("type","hidden");
		$div.bind("click", function() {
			$("#mulitSelector").remove();
			$input.addClass("mulitSelectorElem");
			var ms_html;

			var settings = {
				title: "请选择类别",
				data: null
			};

			if(options) {
				jQuery.extend(settings, options);
			}

			function initialise() {
				initContent();
				initEvent();
			}
			
			function MulitSetVal(){
					var result = "";
					var resultRel = "";
					var html = [];
					var obj = $("#allItems1 input:checked");
					for(var i = 0; i < obj.length; i++) {
						result += (i == 0 ? "" : "|") + obj[i].value.split("@")[1];
						html.push('<label class="mulitValLabel">'+obj[i].value.split("@")[1]+'<i relVal="'+obj[i].value.split("@")[0]+'" class="mulit_del_val"></i></label>');
						
						resultRel += (i == 0 ? "" : "|") + obj[i].value.split("@")[0];
					}
					if(obj.length == 0) {
						$("#ms" + $input.attr("id").substring(2)).removeAttr("checked");
					} else {
						$("#ms" + $input.attr("id").substring(2)).attr("checked", "true");
					}
					$(html.join("")).appendTo($div.empty());
					$div.find(".mulit_del_val").bind("click",function(){
						$(this).parent().remove();
						var tempRel = "";
						$div.find(".mulit_del_val").each(function(i,obj){
							tempRel += (i == 0 ? "" : "|") + $(obj).attr("relVal");
						});
						$input.val(tempRel);
					})
					$input.val(resultRel);
					
			}
			
			function closeMulit(){
				ms_html.remove();
			}
			
			function initEvent() {

				$("#ms_bt_ok").click(function() {
					MulitSetVal();
				});

				$("#ms_bt_clear").click(function() {
					ms_html.remove();
					$("#ms" + $input.attr("id").substring(2)).removeAttr("checked");
					$input.val("");
					$div.html("");
				});
				$("#ms_bt_cancel").click(function() {
					ms_html.remove();
				});

				//全选
				$("#ms_bt_checkall").click(function() {

					$('label input:checkbox').prop('checked', true);
				});
				//全不选
				$("#ms_bt_checkno").click(function() {
					$('label input:checkbox').prop('checked', false);
				});
				$("#mulitSearch").bind({
					//focus:function(){showLi(this)},
					//blur:function(){showLi(this)},
					keydown:function(){showLi(this)},
					keyup:function(){showLi(this)},
					change:function(){showLi(this)}
				});
				
			}
			
			function showLi(obj){
				var kw = obj.value;
				//console.log(ms_html);
				ms_html.find(".nonelay span").each(function(){
					var str = this.innerHTML;
					console.log(str,kw,str.indexOf(kw));
					if(str.indexOf(kw)==-1){
						$(this).parents(".nonelay").hide();
					}else{
						$(this).parents(".nonelay").show();
					}
				});
			}

			function initContent() {

				var offset = $div.offset();
				var divtop = offset.top + 'px';
				var divleft =1 + offset.left + $div[0].offsetWidth + 'px';
				if((1 + offset.left + $div[0].offsetWidth+200)>window.innerWidth){
					divtop = 1 + offset.top + $div[0].offsetHeight + 'px';
					divleft = offset.left + 'px';
				}
				var popmask = document.createElement('div');

				var html = [];

				html.push('<div class="'+timestamp+'" id="mulitSelector" style="display:block; top:' + divtop + ';left:' + divleft + '; position: absolute; z-index: 1999;">');
				html.push('    <div id="pslayer"  class="alert_div ">');
				html.push('        <div class="box">');
				html.push('            <div class="psHeader"><span id="psHeader">' + settings.title + '</span><b id="ms_bt_cancel" class="mulit_del_val" ></b></div>');
				html.push('            <div class="psSearch"><input type="text" id="mulitSearch"></div>');
				html.push('				<div class="blk">');
				//html.push('				<div id="divSelecting" class="sech_layt">');
				//html.push('						<input id="ms_bt_checkall" name="" type="button" value="全选" />');
				//html.push('						<input id="ms_bt_checkno" name="" type="button" value="全不选" />');
				//html.push('						<input id="ms_bt_clear" name="" type="button" value="清空" />');
				//html.push('						<input id="ms_bt_ok" name="" type="button" value="确定" />');
				//html.push('						<input id="ms_bt_cancel" name="" type="button" value="取消" /></b>');
				//html.push('				</div>');
				html.push('				<div class="sech_layb"> ');
				html.push('					<ul id="allItems1">');

				var dataArray = settings.data;
				if(dataArray != null) {
					var len = dataArray.length;
					for(var i = 0; i < len; i++) {
						var d = dataArray[i];
						var status = findStatus(d.id);
						html.push('						<li id=$' + d.id + ' name=100 class="nonelay">');
						html.push('							<input class="regular-checkbox" id="' + d.id + '" type="checkbox" ' + status + ' value="' + (d.id + '@' + d.name) + '" />');
						html.push('							<label for="' + d.id + '"></label><span for="' + d.id + '">'+ d.name+'</span>');
						html.push('						</li>');
					}
				}

				html.push('					</ul>');
				html.push('				</div>');
				html.push('			</div>');
				html.push('		</div>');
				html.push('   </div>');
				html.push('</div>');

				ms_html = $(html.join("")).appendTo('body');
				ms_html.find(".regular-checkbox").bind("change",function(){
					MulitSetVal();
				});
			}

			function findStatus(d) {

				var content = $input.val();
				if(jQuery.trim(content) == "") {
					return "";
				}

				var obj = content.split("|");
				for(var i = 0; i < obj.length; i++) {
					if(obj[i] == d) {
						return "checked"
					}
				}

			}

			initialise();
		});

	}

})(jQuery);