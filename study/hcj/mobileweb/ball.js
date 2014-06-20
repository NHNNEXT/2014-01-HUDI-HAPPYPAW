document.addEventListener("touchstart", function(e){
	if(e.target === document.querySelector(".box")){
		createBall(e);
	}
});

document.addEventListener("touchmove", function(e){
	if(e.target === document.querySelector(".box")){
		moveBall(e);
	}
});

var createBall= function (e){
	var ball = document.querySelector(".ball");
	var clone = ball.cloneNode(true);
	var left = e.touches[0].pageX;
	var top = e.touches[0].pageY;
	console.log(left +"px");
	clone.style.left = left+ "px";
	clone.style.top = top+"px";
	var boxes= document.getElementsByClassName("box");
	boxes[0].appendChild(clone);
	
}

var moveBall= function(e){
	var balls = document.querySelector(".ball");
	var len = balls.length;
	for(var i = 0; i < len; i++){
		if(e.target.children[i] === balls[i]){
			var left = e.touches[0].pageX;
			var top = e.touches[0].pageY;
			balls[i].style.left = left + "px";
			balls[i].style.top = top + "px";
		}		
	}

}