$(document).ready(function() {

	var data = [
	[30, 219],//60
	[197, 308]//59
	,[308, 192]//58
	, [426, 74]
	, [574, 18]
	,[720, 64]
	,[690, 215]
	, [526, 290]
	,[372, 336]
	,[239, 368]
	,[108, 441]//50
	,[26, 529]
	,[140, 603]
	,[295, 550]
	,[436, 488]
	,[594, 407]//45
	,[778, 325],[979, 282],[1160, 335],[1230, 511],[1244, 704]
	,[962, 839], [733, 720],[517, 686],[274, 676],[114, 783],[110, 970],[227, 1042],[341, 890],[525, 854],[683, 895]//30 ~ 39
	,[855, 935] ,[1042, 958],[1213, 1113],[1229, 1310],[1136, 1479],[895, 1434],[747, 1226],[454, 1191],[178, 1222],[65, 1377]//20 ~ 29
	,[263, 1465],[444, 1339],[678, 1372],[611, 1613],[770, 1761],[994, 1825],[1225, 1991],[1134, 2178],[869, 2196],[545, 2122]//10 ~ 19
	,[498, 1860],[263, 1665],[74, 1900],[79, 2121],[130, 2349],[353, 2293],[509, 2245],[677, 2362],[246, 2473]];//9 ~ 0
	var imgHeight = 1265 / 1757 * 4000;
	for(var i = 0; i < data.length; i++) {
		var num = 60- i;
		
		data[i] = {x:data[i][0] / 1265 * 100, y:data[i][1] / imgHeight *100, num:num, class:"group"+parseInt(i / 10)+" top" + (i+1)};
	}
	var classs = ["apple", "violet", "peach", "vanila"];
	var circleData = [];
	for(var i = 0; i < 60; ++i) {
		circleData.push({x:parseInt(Math.random() * 100), y:parseInt(Math.random() * 3000), class:classs[parseInt(Math.random() * classs.length)], speed: Math.random() * 2});
	}
	
	

	
	$(".signs").template(data, $(".sign"));
	$(".circles").template(circleData, $(".circle"));
	
	var people ='<div class="people" data-num="{num}"><img src="profile1.jpg" alt=""/><div class="desc">{name}</div></div>';
	
	$('[data-count="60"] .peoples').append(daylight.template({num : 1, name:"나?"}, people));
	$('[data-count="60"] .peoples').append(daylight.template({num : 2, name:"나2?"}, people));
	$('[data-count="60"] .peoples').append(daylight.template({num : 3, name:"나3?"}, people));
	$('[data-count="60"] .peoples').append(daylight.template({num : 4, name:"나?4"}, people));
	$('[data-count="60"] .peoples').append(daylight.template({num : 5, name:"나3?"}, people));
	$('[data-count="59"] .peoples').append(daylight.template({num : 1, name:"나?"}, people));
	$('[data-count="49"] .peoples').append(daylight.template({num : 1, name:"나?"}, people));
	
	$(window).resize(function() {
		var width = $(".background").width();
		var w = width / 1265 * 60;
		var m = -w /2 + "px";
		w = w + "px";
		var f = width / 1265 * 1.5 + "em";

		$(".signs").css({width:w, height:w,lineHeight:w, fontSize:f, marginTop:m, marginLeft:m});
	});
	$('.people').each(function(element, i) {
		var num = element.getAttribute("data-num") - 1;
		daylight.css(element, "left", num % 4 * 25 +"%" );
		daylight.css(element, "top", parseInt(num / 4) * 25 +"%" );
	});
	$(".signs").click(function(e) {
		var o_e = $.Event(e);
		if(daylight.hasClass(o_e.target, "sign"))
			daylight.toggleClass(o_e.target, "hide", "show");
	});
	$(".background").click(function(e) {
		var o_e = daylight.Event(e);
		console.log(",",[o_e.pos().layerX, o_e.pos().layerY]);
		//copyToClipboard(",["+o_e.pos().pageX+", " + o_e.pos().pageY+"]");
	});
	var circles = $(".circle");
	$(window).scroll(function() {
		var top = $("body").scrollTop();
		circles.each(function(element) {
			var speed = element.getAttribute("data-speed");
			var ostyle = element.getAttribute("data-style");
			element.setAttribute("style", ostyle + daylight.objectToCSS({ty:-top * speed+"px"}));
		})
	})
	function copyToClipboard( text )
	{
	  temp = prompt("이 글의 트랙백 주소입니다. Ctrl+C를 눌러 클립보드로 복사하세요", text);
	}
	
	var timeline = new $.Timeline("body");
	var others = timeline.createLayer(".other");
	timeline.addMotion(others, [
	{time:0, "ty":"0px"},
	{time:1, "ty":"10px"},
	{time:2, "ty":"0px"}
	])
	timeline.init().start();
	
});