<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	<title>在线播放</title>
	<link rel="stylesheet" href="video-js.min.css" />
	<script type="text/javascript" src="ie8/videojs-ie8.min.js"></script>
	<script type="text/javascript" src="../jquery-1.11.2.min.js"></script>
	<style>
		body{background-color: #191919}
		.m{ width: 800px; height: 450px; margin-left: auto; margin-right: auto; margin-top: 60px; }
	</style>
	<script type="text/javascript">
		videojs.options.flash.swf = "video-js.swf";
	</script>
</head>
<body>
	<div class="m">
		<video
		    id="my-player"
		    class="video-js vjs-default-skin vjs-big-play-centered"
		    width="800px"
		    height="450px"
		    poster=""
		    controls
		    preload="auto"
		    data-setup='{}'>
		    <source id="mp4_source" src="" type='video/mp4' />
			<!-- <source id="webm_source" src="" type='video/webm' /> -->
			<!-- <source id="ogg_source" src="" type='video/ogg' /> -->
		  	<p class="vjs-no-js">
		    	播放视频需要启用 JavaScript，推荐使用支持HTML5的浏览器访问。
		  	</p>
		</video>
		<script type="text/javascript" src="video.min.js"></script>
		<script type="text/javascript">
			$(function(){
				var archiveDetailNo = "${param.archiveDetailNo}";
				var mp4Url = "../../cache/" + archiveDetailNo + "/" + archiveDetailNo + ".mp4";
				var img4Url = "../../cache/" + archiveDetailNo + "/" + archiveDetailNo + ".jpg";
				$("#mp4_source").attr("src", mp4Url);
				$("#my-player").attr("poster", img4Url);
				videojs("my-player").ready(function() {
					window.myPlayer = this;
					//myPlayer.play();
				});
			});
		</script>
	</div>
</body>
</html>