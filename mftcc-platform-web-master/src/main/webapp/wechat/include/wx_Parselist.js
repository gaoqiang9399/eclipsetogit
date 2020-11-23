function Parselist(options){
    this.init(options);
    return this;
}
Parselist.prototype  = {
		DEFAULTS : {
			actionUrl:"",
			selector:"#parselist"
		},
		list_html : '<a href="javascript:void(0);"class="weui_media_box weui_media_appmsg">'+
						'<div class="weui_media_hd">'+
							'<i class="i ">'+
						'</div>'+
						'<div class="weui_media_bd">'+
							'<h4 class="weui_media_title">标题一</h4>'+
							'<p class="weui_media_desc">由各种物质组成的巨型球状天体，叫做星球。星球有一定的形状，有自己的运行轨道。</p>'+
						'</div>'+
					'</a>',
		init:function(options){
			this.options = $.extend({}, this.DEFAULTS, $.isPlainObject(options) && options);
			this.actionUrl = this.options.actionUrl;
			this.selector = this.options.selector;
			this.initList();
		},
		initList : function(){
			var $this = this;
			var actionUrl = this.actionUrl;
			$.ajax({
				type:"post",
				async:false,
				cache:false,
				url:actionUrl,
				dataType:"json",
				success:function(data){
					if(data.flag=="success"){
						$this.createList(data.jsonArray);
					}else{
						$.alert(data.msg);
					}
				},
				error:function(){
					$.alert("查询失败！");
				}
			});
		},
		createList:function(nodes){
			var $this = this;
			var $list = $(this.selector);
			$.each(nodes,function(i,node){
				var $a = $($this.list_html);
				$a.find(".weui_media_hd .i").addClass("i-"+node.css);
				$a.find(".weui_media_title").html(node.title);
				$a.find(".weui_media_desc").html(node.desc);
				if(node.hasOwnProperty('sts')){
					$a.find(".weui_media_bd").append('<small>'+node.sts+'</small>');
				}
				$list.append($a);
			});
		}
	};