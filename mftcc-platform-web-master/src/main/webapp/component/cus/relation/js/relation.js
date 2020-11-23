var container;
var zoom;
var rootData;

$(document).ready(function() {
	resizeScreen();
	window.top.loadding();
	getData();
});
//点击节点事件
function onclickRelationNode(node){
	console.log(node);
}
function getData() {
    var url = basepath+getQueryString("jumpLink");
    $.ajax({
        url:url,
        type: 'post',
        data:GetRequest(),
        dataType: 'JSON',
        success: function (data){
        	console.log(data);
            if(data.Status === 200){
                rootData =transformTozTreeFormat(data.Result.Node);
                console.log(rootData);
                traverseTreeId(rootData);
                draw(rootData);
            }else{
            }
        },
        complete:function(){
        	window.top.loaded();
        }
    });
}
window.onresize = function() {
	resizeScreen();
};

function changeScreen(dom) {
	if(!isFullScreen()) {
		$(dom).find('i').attr('class', 'fa fa-compress');
		launchFullScreen($('#screenArea')[0]);
	} else {
		$(dom).find('i').attr('class', 'fa fa-expand');
		exitFullScreen();
	}
}

//切换全屏
setFullScreenListener(function() {
	setTimeout(function() {
		zoom.translate([$('#main').width() / 2, $('#main').height() / 2]);
		container.transition().duration(500).attr("transform", "translate(" + zoom.translate() + ")scale(" + zoom.scale() + ")");
	}, 300);
}); //
//缩放计算
function maoScale(type) {

	var scale = zoom.scale();
	if(type == 1) {
		scale += 0.3;
	} else if(type == 2) {
		scale -= 0.3;
	}
	if(scale >= 0.3 && scale <= 2) {
		zoom.scale(scale);
		container.transition().duration(500).attr("transform", "translate(" + zoom.translate() + ")scale(" + zoom.scale() + ")");
	}
}

function maoRotate(type) {

}

function maoRefresh() {
	draw(rootData);
}

function saveImg() {
	jietu('main');
}
//截图
function jietu(svgDivId) {
	var jietuMask = document.createElement("div");
	$(jietuMask).attr('style', 'position: fixed; background: #fff; z-index: 1000; top: 0px; bottom: 0px; left: 0px; right: 0px;');
	document.body.appendChild(jietuMask);

	var _svgWidth = d3.select('svg').attr('width');
	var _svgHeight = d3.select('svg').attr('height');
	var _scale = zoom.scale();
	var _translate = zoom.translate();

	zoom.scale(1.5);
	zoom.translate([3264 / 2, 2448 / 2]);
	container.attr("transform", "translate(" + zoom.translate() + ")scale(" + zoom.scale() + ")");
	d3.select('svg').attr('width', 3264);
	d3.select('svg').attr('height', 2448);

	var svgXml = $('#' + svgDivId).html();
	var image = new Image();
	image.src = 'data:image/svg+xml;base64,' + window.btoa(unescape(encodeURIComponent(svgXml)));
	//window.win = open (image.src);   
	setTimeout(function() {
		var canvas = document.createElement('canvas'); //准备空画布
		canvas.width = d3.select('svg').attr('width');
		canvas.height = d3.select('svg').attr('height');
		zoom.scale(_scale);
		zoom.translate(_translate);
		container.attr("transform", "translate(" + zoom.translate() + ")scale(" + zoom.scale() + ")");
		d3.select('svg').attr('width', _svgWidth);
		d3.select('svg').attr('height', _svgHeight);
		$(jietuMask).css('display', 'none');
		var context = canvas.getContext('2d'); //取得画布的2d绘图上下文
		context.drawImage(image, 0, 0);
		var marker = '东华软件股份公司';
		context.font = "30px 微软雅黑";
		context.fillStyle = "#aaaaaa";
		context.fillText(marker, canvas.width / 2 - context.measureText(marker).width / 2, canvas.height - 100);
		download(canvas, 'jpg');
	}, 100);
}

//构建树
function draw(root) {
	tree = d3.layout.cluster()
		.size([360, 600])
		.separation(function(a, b) { return(a.parent == b.parent ? 2 : 3) / a.depth; });
	$("#main").empty();
	svg = d3.select("#main").append("svg").attr("xmlns", "http://www.w3.org/2000/svg").attr("style", "background:#fff;");
	svg.empty();
	d3.select('svg').attr('width', $('#main').width());
	d3.select('svg').attr('height', $('#main').height());

	//drawLegend(svg);
	//drawWaterMark(svg);

	container = svg.append("g");
	linkContainer = container.append("g");
		//缩放
	zoom = d3.behavior.zoom().scaleExtent([0.4, 2]).on("zoom", zoomed);
	function zoomed() {
		container.attr("transform", "translate(" + d3.event.translate + ")scale(" + d3.event.scale + ")");
	}
	svg.call(zoom);
	initLocation();

	

	function initLocation() {
		zoom.translate([svg.attr('width') / 2, svg.attr('height') / 2]);
		zoom.scale(0.6);
		container.attr("transform", "translate(" + zoom.translate() + ")scale(" + zoom.scale() + ")");
	}

	drawTree(root);
}
//绘制图形
function drawTree(data) {

	var diagonal = d3.svg.diagonal.radial()
		.projection(function(d) { return [d.y, d.x / 180 * Math.PI]; });
	data.x0 = 0;
	data.y0 = 0;

	nodes = tree.nodes(rootData);
	links = tree.links(nodes);

	var pathLength = 150;
	if(nodes.length > 100) {
		pathLength = 200;
	}
	//计算节点 距离
	nodes.forEach(function(d) {
		if(d.depth > 2) {
			d.y = d.depth * (d.depth / 2) * 150;
		} else {
			d.y = d.depth * pathLength;
		}
	});

	var linkUpdate = linkContainer.selectAll(".link").data(links, function(d) { return d.target.id; });
	var linkEnter = linkUpdate.enter();
	var linkExit = linkUpdate.exit();

	linkEnter.append("path")
		.attr("class", "link")
		.attr("d", function(d) {
			var o = { x: data.x0, y: data.y0 };
			return diagonal({ source: o, target: o });
		})
		.transition()
		.duration(500)
		.attr("d", diagonal);

	linkUpdate.attr("stroke", function(d) {
			if(d.source.Category == 2 || d.target.Category == 2) {
				return "#49ceb0";
			}

			if(d.source.Category == 3 || d.target.Category == 3) {
				return "#7985f1";
			}

			if(d.source.Category == 4 || d.target.Category == 4) {
				return "#65d289";
			}

			if(d.source.Category == 5 || d.target.Category == 5) {
				return "#bd73e7";
			}

			if(d.source.Category == 6 || d.target.Category == 6) {
				return "#ee7698";
			}

			if(d.source.Category == 7 || d.target.Category == 7) {
				return "#f59c28";
			}

			if(d.source.Category == 8 || d.target.Category == 8) {
				return "#79a3f1";
			}

			if(d.source.Category == 9 || d.target.Category == 9) {
				return "#3dc9f7";
			}

			return "#f35151";
		})
		.transition()
		.duration(500)
		.attr("d", diagonal)
		.attr("style", "fill: none; stroke-opacity: 1; stroke: #9ecae1; stroke-width: 1px;");

	linkExit.transition()
		.duration(500)
		.attr("d", function(d) {
			var o = { x: data.x, y: data.y };
			return diagonal({ source: o, target: o });
		})
		.remove();

	var nodeUpdate = container.selectAll(".node").data(nodes, function(d) { return d.id; });
	var nodeEnter = nodeUpdate.enter();
	var nodeExit = nodeUpdate.exit();

	var enterNodes = nodeEnter.append("g")
		.attr("class", function(d) { return "node"; })
		.attr("transform", function(d) { return "translate(" + project(data.x0, data.y0) + ")"; });
	enterNodes.append("circle")
		.attr("r", 0)
		.attr("fill", function(d) {
			if(d.Category == 1) {
				return "#3ea6ff";
			}

			if(d.Category == 2) {
				return "#49ceb0";
			}

			if(d.Category == 3) {
				return "#7985f1";
			}

			if(d.Category == 4) {
				return "#65d289";
			}

			if(d.Category == 5) {
				return "#bd73e7";
			}

			if(d.Category == 6) {
				return "#ee7698";
			}

			if(d.Category == 7) {
				return "#f59c28";
			}

			if(d.Category == 8) {
				return "#79a3f1";
			}

			if(d.Category == 9) {
				return "#3dc9f7";
			}

			return "#3ea6ff";
		})
		.attr("stroke", function(d) {

			if(d.depth == 0) {
				return "#3ea6ff";
			}

			if(d.depth == 1) {
				if(d.Category == 1) {
					return "#3ea6ff";
				}

				if(d.Category == 2) {
					return "#49ceb0";
				}

				if(d.Category == 3) {
					return "#7985f1";
				}

				if(d.Category == 4) {
					return "#65d289";
				}

				if(d.Category == 5) {
					return "#bd73e7";
				}

				if(d.Category == 6) {
					return "#ee7698";
				}

				if(d.Category == 7) {
					return "#f59c28";
				}

				if(d.Category == 8) {
					return "#79a3f1";
				}

				if(d.Category == 9) {
					return "#3dc9f7";
				}
			}

			return null;
		})
		.attr("stroke-opacity", 0.5)
		.attr("stroke-width", function(d) {
			if(d.depth == 0) {
				return 10;
			}

			if(d.depth == 1) {
				return 6;
			}

			return 0;
		})
		.on("click", function(d) {
			onclickRelationNode(d);
			if(d.depth > 0) {
				toggle(d);
				drawTree(d);
			}
		});

	enterNodes.append("path")
		.attr("d", function(d) {
			if(d.depth > 0 && d._children) {
				return "M-6 -1 H-1 V-6 H1 V-1 H6 V1 H1 V6 H-1 V1 H-6 Z";
			} else if(d.depth > 0 && d.children) {
				return "M-6 -1 H6 V1 H-6 Z";
			}
		})
		.attr("fill", "#ffffff")
		.attr("stroke", "#ffffff")
		.attr("stroke-width", "0.2")
		.on("click", function(d) {
			onclickRelationNode(d);
			if(d.depth > 0) {
				toggle(d);
				drawTree(d);
			}
		});
	enterNodes.append("text")
		.attr("dy", function(d) {
			if(d.depth == 0) {
				return "-1.5em";
			}
			return "0.31em";
		})
		.attr("x", function(d) {
			if(d.depth == 0) {
				return d.name.length * 8;
			}
			return d.x < 180 ? 15 : -15;
		})
		.text(function(d) { return d.name; })
		.style("text-anchor", function(d) {
			if(d.depth == 0) {
				return "end";
			}
			return d.x < 180 ? "start" : "end";
		})
		.style("fill-opacity", 0)
		.attr("transform", function(d) {
			if(d.depth > 0) {
				return "rotate(" + (d.x < 180 ? d.x - 90 : d.x + 90) + ")";
			} else {
				return "rotate(0)";
			}
		})
		.style("font-size", function(d) {
			if(d.depth == 0) {
				return "16px";
			}
			return "14px";
		})
		.attr("fill", function(d) {
			if(d.depth == 0) {
				return "#2c91e8";
			}
			if(d.depth == 1) {
				if(d.Category == 2) {
					return "#2db092";
				}

				if(d.Category == 3) {
					return "#3d4cd4";
				}
			}
			return "#333";
		})
		.on("click", function(d) {
			onclickRelationNode(d);
		});

	var updateNodes = nodeUpdate.transition()
		.duration(500)
		.attr("transform", function(d) { return "translate(" + project(d.x, d.y) + ")"; });
	//调整节点文字描述
	updateNodes.select("text").duration(0)
		.style("fill-opacity", 1)
		.attr("transform", function(d) {
			if(d.depth > 0) {
				return "rotate(" + (d.x < 180 ? d.x - 90 : d.x + 90) + ")";
			} else {
				return "rotate(0)";
			}
		})
		.attr("x", function(d) {
			if(d.depth == 0) {
				return d.name.length * 8;
			}
			return d.x < 180 ? 15 : -15;
		})
		.attr("fill", function(d) {
			if(d.depth == 0) {
				return "#2c91e8";
			}
			if(d.depth == 1) {
				if(d.Category == 2) {
					return "#2db092";
				}

				if(d.Category == 3) {
					return "#3d4cd4";
				}
			}
			return "#333";
		})
		.style("text-anchor", function(d) {
			if(d.depth == 0) {
				return "end";
			}
			return d.x < 180 ? "start" : "end";
		});
	//调整节点圆的大小根据节点级别
	updateNodes.select("circle")
		.attr("r", function(d) {
			if(d.depth == 0) {
				return 12;
			}

			if(d.depth == 1) {
				return 10;
			}

			return 9;
		});
	//构建子节点“+/-”号
	updateNodes.select("path")
		.attr("d", function(d) {
			if(d.depth > 0 && d._children) {
				return "M-6 -1 H-1 V-6 H1 V-1 H6 V1 H1 V6 H-1 V1 H-6 Z";
			} else if(d.depth > 0 && d.children) {
				return "M-6 -1 H6 V1 H-6 Z";
			}
		});
	//对填充的空节点进行隐藏
	var exitNodes = nodeExit.transition()
		.duration(500)
		.attr("transform", function(d) { return "translate(" + project(data.x, data.y) + ")"; })
		.remove();
	exitNodes.select("circle")
		.attr("r", 0);

	exitNodes.select("text")
		.style("fill-opacity", 0);

	nodes.forEach(function(d) {
		d.x0 = d.x;
		d.y0 = d.y;
	});

}
//子节点展开收缩
function toggle(d) {
	if(d.children) {
		d._children = d.children;
		d.children = null;
	} else {
		d.children = d._children;
		d._children = null;
	}
}
//计算角度
function project(x, y) {
	var angle = (x - 90) / 180 * Math.PI,
		radius = y;
	return [radius * Math.cos(angle), radius * Math.sin(angle)];
}
//添加页面底部标识
function drawWaterMark(svg) {
	var declare_text = "东华软件股份公司";
	var text_width = $(window).width() - 20;
	var textSize = 12;
	var row = Math.ceil(declare_text.length / (Math.floor(text_width / textSize)));
	var declare = svg.append("g")
		.attr("transform", "translate(" + ($(window).width() - declare_text.length * textSize) / 2 + "," + (svg.attr('height') - 20) + ")");
	for(var i = 0; i < row; i++) {
		declare.append("text")
			.text(function() {
				return declare_text.substr(i * Math.floor(text_width / textSize), Math.floor(text_width / textSize));
			})
			.attr("fill", "#bbbbbb")
			.attr("font-size", textSize+"px")
			.attr("y", function() {
				return i * 15;
			});
	}
}

//节点id复制
function traverseTreeId(node) {
	var id = 1;
	node.open = true;
	trId(node);
	function trId(node) {
		if(!node.id) {
			node.id = id;
			id++;
		}
        var i;
		if(!node.open&&node.children){
			node._children = node.children;
			node.children = null;
			for(i = 0; i < node._children.length; i++) {
				trId(node._children[i]);
			}
		}else{
			if(node.children) {
				for(i = 0; i < node.children.length; i++) {
					trId(node.children[i]);
				}
			}
		}
	}
}
//判断当前页面大小
function resizeScreen() {
	//if(document.body.clientHeight > 700) {
	//	$('#screenArea').height(document.body.clientHeight - 66);
	//} else {
	//	$('#screenArea').height(640);
	//}
}

// 获取url中的参数
function getQueryString(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
	var r = window.location.search.substr(1).match(reg);
	if(r != null) return decodeURIComponent(r[2]);
	return null;
}
function GetRequest() {   
	   var url = location.search; //获取url中"?"符后的字串   
	   var theRequest = new Object();   
	   if (url.indexOf("?") != -1) {   
	      var str = url.substr(1);   
	      strs = str.split("&");   
	      for(var i = 0; i < strs.length; i ++) {   
	         theRequest[strs[i].split("=")[0]]=unescape(strs[i].split("=")[1]);   
	      }   
	   }   
	   return theRequest;   
	} 
//全屏
function setFullScreenListener(fullScreenChange){
  document.addEventListener('fullscreenchange', function(){ fullScreenChange(); });
  document.addEventListener('webkitfullscreenchange', function(){ fullScreenChange();});
  document.addEventListener('mozfullscreenchange', function(){ fullScreenChange();});
  document.addEventListener('MSFullscreenChange', function(){ fullScreenChange();});
}

function launchFullScreen(element) { 
  if(element.requestFullscreen) { 
    element.requestFullscreen(); 
  }else if(element.mozRequestFullScreen) { 
    element.mozRequestFullScreen(); 
  }else if(element.webkitRequestFullscreen) { 
    element.webkitRequestFullscreen(); 
  } else if(element.msRequestFullscreen) { 
    element.msRequestFullscreen(); 
  } 
}
function exitFullScreen(){
  if(document.exitFullscreen){
    document.exitFullscreen();
  }
  else if(document.mozCancelFullScreen){
    document.mozCancelFullScreen();
  }
  else if(document.msExitFullscreen){
    document.msExitFullscreen();
  }
  else if(document.webkitCancelFullScreen){
    document.webkitCancelFullScreen();
  }
}
function isFullScreen(){
  if(document.fullscreen){
    return true;
  }else if(document.mozFullScreen){
    return true;
  }else if(document.webkitIsFullScreen){
    return true;
  }else if(document.msFullscreenElement){
    return true;
  }else{
    return false;
  }
}
//保存关系图
function download(canvas,type) {
	type = 'png';
    //设置保存图片的类型
    var imgdata = canvas.toDataURL(type);
    //将mime-type改为image/octet-stream,强制让浏览器下载
    var fixtype = function (type) {
        type = type.toLocaleLowerCase().replace(/jpg/i, 'jpeg');
        var r = type.match(/png|jpeg|bmp|gif/)[0];
        return 'image/' + r;
    };
    imgdata = imgdata.replace(fixtype(type), 'image/octet-stream');
    //将图片保存到本地
    var saveFile = function (data, filename) {
        var link = document.createElement('a');
        link.href = data;
        link.download = filename;
        var event = document.createEvent('MouseEvents');
        event.initMouseEvent('click', true, false, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null);
        link.dispatchEvent(event);
    };
    var filename = rootData.name +"-"+new Date().toLocaleDateString() + '.' + type;
    saveFile(imgdata, filename);
}
function transformTozTreeFormat(sNodes) {
	var i,l,
	key = 'id',
	parentKey = 'pId',
	childKey = 'children';
	if (!key || key=="" || !sNodes) return [];

	if (dataIsArray(sNodes)) {
		var r = [];
		var tmpMap = [];
		for (i=0, l=sNodes.length; i<l; i++) {
			tmpMap[sNodes[i][key]] = sNodes[i];
		}
		for (i=0, l=sNodes.length; i<l; i++) {
			if (tmpMap[sNodes[i][parentKey]] && sNodes[i][key] != sNodes[i][parentKey]) {
				if (!tmpMap[sNodes[i][parentKey]][childKey])
					tmpMap[sNodes[i][parentKey]][childKey] = [];
				tmpMap[sNodes[i][parentKey]][childKey].push(sNodes[i]);
			} else {
				r.push(sNodes[i]);
			}
		}
		return r[0];
	}else {
		return sNodes;
	}
}
function dataIsArray(arr) {
	return Object.prototype.toString.apply(arr) === "[object Array]";
}