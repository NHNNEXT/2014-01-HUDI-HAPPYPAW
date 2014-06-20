

var movingDown = setInterval(moveDown, 200);
var button = document.querySelector(".onoff");
var isOn = true;

var ball = document.querySelector(".ball");
var movingBall;
var whiteBall;
var BALL_SIZE = 100;//공 크기 
var width = window.innerWidth;
var height = window.innerHeight;
var MOVE_HEIGHTS = [10, 10, 10, 10, 10];
var MOVE_WIDTHS = [10, 10, 10, 10, 10];
function moveDown(){

	var balls = document.querySelectorAll(".ball");
	var num= balls.length;

	var mball;
	var testtop;
	var left;
	var top;

	for(var i = 0; i < num; i++){		
		mball = balls[i];
		testtop = window.getComputedStyle(mball);
		
		left = parseInt(testtop.left, 10);
		top = parseInt(testtop.top, 10);

		if(!MOVE_WIDTHS[i]) {
			MOVE_WIDTHS[i] = 10;
			MOVE_HEIGHTS[i] = 10;
		}
		if(top <= 0)
			MOVE_HEIGHTS[i] = 10;
		else if(top >=height - BALL_SIZE)
			MOVE_HEIGHTS[i] = -10;
			
		if(left <= 0)
			MOVE_WIDTHS[i] = 10;
		else if( left >=width - BALL_SIZE)
			MOVE_WIDTHS[i] = -10;
/*
			mball.style.top = "0px";
			mball.style.left = "0px";
			left = 0;
			top = 0;

		}
	*/
		left += MOVE_WIDTHS[i];
		top += MOVE_HEIGHTS[i];
		
		mball.style.left = left + "px";
		mball.style.top = top + "px" ;
		var last=	window.getComputedStyle(ball).getPropertyValue("top");
		last = parseInt(last);
	}


}

//온오프 버튼 바꾸기 
function stopBall(){
	if(isOn === true){
		isOn = false;
		clearInterval(movingDown);
		button.innerHTML="START";
		
	} else {
		isOn = true;
		movingDown = setInterval(moveDown, 200);
		button.innerHTML="STOP";
	}
}

button.addEventListener("click", function(e){
	stopBall();
});


/*
//빨간색으로 바꾸는 부분 
var clone;//클론 객체 생성 

ball.addEventListener("touchstart", function(e){
	e.preventDefault();
	console.log("catch");
	ball.style.background="red";

	movingBall=ball;//볼을 일단 담아둠. 
	
	var len= e.touches.length;
	if(len ===2){
		clone = ball.cloneNode(true);
		var posX = e.touches[1].pageX;
		var posY = e.touches[1].pageY;
			
		clone.style.left=posX+"px";
		clone.style.top=posY+"px";
		document.getElementsByClassName("box")[0].appendChild(clone);
	
	}
	console.log("start "+len);
	console.log("touch start");
	
});




ball.addEventListener("touchmove", function(e){
	clearInterval(movingDown);  
	var posX = e.touches[0].pageX;
	var posY = e.touches[0].pageY;
	movingBall.style.left = posX+"px";
	movingBall.style.top = posY + "px";
	
	var len= e.touches.length;
	if(len ===2){
		clone.style.left = e.touches[1].pageX+"px";
		clone.style.top = e.touches[1].pageY+"px";
	} else if(len ===1 && clone != null){
		clone.style.background= "white";
	}
	console.log("touch move len = "+ len );
});



ball.addEventListener("touchend", function(e){
	movingDown = setInterval(moveDown, 200);
	console.log("free");
	var posX = e.changedTouches[0].pageX;
	var posY = e.changedTouches[0].pageY;
	movingBall.style.left = posX+"px";
	movingBall.style.top = posY + "px";
	ball.style.background="white";


});


*/



//배경 클릭하면 하얀공 만듦.
var board = document.querySelector(".board");
var clone;

board.addEventListener("touchstart", function(e){
	whiteBall = null;
	clearInterval(movingDown);
	isOn = false;
	e.preventDefault();
	var posX = e.touches[0].pageX;
	var posY = e.touches[0].pageY;
	
	if(e.target === ball) {
		ball.style.background="red";
		return;
	} else if(e.target.className.indexOf("ball")!== -1){
		whiteBall = e.target;
	} else {
		clone = ball.cloneNode(true);
		clone.style.left=posX+"px";
		clone.style.top=posY+"px";
		clone.style.background = "white";
		document.getElementsByClassName("box")[0].appendChild(clone);
	}
	
		


});

board.addEventListener("touchmove", function(e){
	e.preventDefault();
	if(e.target === ball) {
		ball.style.left = e.touches[0].pageX+"px";
		ball.style.top = e.touches[0].pageY + "px";
	} else if(whiteBall){
		whiteBall.style.left = e.touches[0].pageX+"px";
		whiteBall.style.top = e.touches[0].pageY + "px";
	}
		
	if(clone) {
		clone.style.left = e.touches[0].pageX+"px";
		clone.style.top = e.touches[0].pageY+"px";
	}
});


board.addEventListener("touchend", function(e){
	ball.style.background="white";
	if(e.touches.length === 0){
		movingDown = setInterval(moveDown, 200);
	clone = 0;
	isOn = true;
	}

});

window.onload = function(){
	window.addEventListener("orientationchange", function(e){
		var windowOrientation = window.orientation;
			console.log(windowOrientation);
			width = window.innerWidth;
			height = window.innerHeight;
		if(windowOrientation ===0 || windowOrientation ===180){
			alert("orientation vertical!!");
		} else if (windowOrientation === 90 || windowOrientation ===-90){
			console.log(windowOrientation);
			alert("orientation horizontal");
	 
		}

	});
}




































