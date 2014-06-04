
$(document).ready(function() {
	var data = [
    	[30, 219], [197, 308], [308, 192], [426, 74], [574, 18], [720, 64], [690, 215], [526, 290],[372, 336],[239, 368], [108, 441]//50 ~ 60
    	,[26, 529] ,[140, 603],[295, 550],[436, 488],[594, 407],[778, 325],[979, 282],[1160, 335],[1230, 511],[1244, 704]//40 ~ 49
    	,[962, 839], [733, 720],[517, 686],[274, 676],[114, 783],[110, 970],[227, 1042],[341, 890],[525, 854],[683, 895]//30 ~ 39
    	,[855, 935] ,[1042, 958],[1213, 1113],[1229, 1310],[1136, 1479],[895, 1434],[747, 1226],[454, 1191],[178, 1222],[65, 1377]//20 ~ 29
    	,[263, 1465],[444, 1339],[678, 1372],[611, 1613],[770, 1761],[994, 1825],[1225, 1991],[1134, 2188],[869, 2206],[545, 2122]//10 ~ 19
    	,[498, 1860],[263, 1665],[74, 1900],[79, 2121],[120, 2349],[353, 2303],[509, 2250],[677, 2362],[366, 2470], [110, 2550]];//9 ~ 0
    	
    function initRankingData() {
    	var people ='<div class="people" data-num="{num}" data-id="{id}"><img src="./img/profile1.jpg" alt=""/><div class="desc">{name}</div></div>';

    	daylight.each(user_data, function(user, num) {
    		var peoples = $('[data-count="'+num+'"] .peoples');
    		peoples.template(user, people);
    	});
    	$('[data-count="'+myNum+'"]').addClass("mine");
    }
    function initRankingSign() {

    	var imgHeight = 1265 / 1757 * 4000;
    	for(var i = 0, length = data.length; i < length; i++) {
    		var num = 60- i;
    		
    		data[i] = {x:data[i][0] / 1265 * 100, y:data[i][1] / imgHeight *100, num:num, class:"group"+parseInt(i / 10)+" top" + (i+1)};
    	}
    	$(".signs").template(data, $(".sign"));
    }
	
	var classs = ["apple", "violet", "peach", "vanila"];
	var circleData = [];
	for(var i = 0; i < 60; ++i) {
		circleData.push({x:parseInt(Math.random() * 100), y:parseInt(Math.random() * 3000), class:classs[parseInt(Math.random() * classs.length)], speed: Math.random() * 2});
	}
	$(".circles").template(circleData, $(".circle"));
	
	initRankingSign();
	initRankingData();
	
	$(window).resize(function() {
		var width = $(".background").width();
		var w = width / 1265 * 60;
		var m = -w /2 + "px";
		w = w + "px";
		var f = width / 1265 * 1.5 + "em";

		$(".signs").css({width:w, height:w,lineHeight:w, fontSize:f, marginTop:m, marginLeft:m});
	});
	$(window).resize();
	
	$('.people').each(function(element, i) {
		var num = element.getAttribute("data-num");
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
		});
	});
//	function copyToClipboard( text )
//	{
//	  temp = prompt("이 글의 트랙백 주소입니다. Ctrl+C를 눌러 클립보드로 복사하세요", text);
//	}
//	
	var timeline = new $.Timeline("body");
	var others = timeline.createLayer(".other");
	timeline.addMotion(others, [
	{time:0, "ty":"0px"},
	{time:1, "ty":"10px"},
	{time:2, "ty":"0px"}
	]);
	timeline.init().start();
	
});

$(window).load(function() {
	setTimeout(function() {
		$("body").scrollTop($('[data-count="'+myNum+'"]').offset().top - 100);
	}, 200);
});