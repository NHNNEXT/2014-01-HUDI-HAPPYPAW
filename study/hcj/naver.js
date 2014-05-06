window.onload=function(){

	var menu = document.querySelector(".menu ul");
	menu.addEventListener("click", function (e){/*이벤트 추가시키는 것*/
/*
		console.log(e);
		console.log(this);
*/	
		//this랑 target의 차이
		var target = e.target || e.srcElement;//클릭한 요소? 그게 뭐일 것 같애? 사람이 클릭한 것들
		var data = target.getAttribute("data");
		var coloured = document.querySelectorAll("li.coloured");
		for(var a =0; a < coloured.length; a++){
			var el = coloured[a];
			el.className="";
		}
		target.className = "coloured";
		
/*
		var blind=document.querySelectorAll("section.mainNews");
		for(var i = 0; i< blind.length; i++){	
			blind[i].style.display="none";				
		}

		var section = document.querySelector('section.mainNews[data="' + data + '"]');
		section.style.display="block";
*/
		var board = document.querySelector(".board");
		var liList = this.children;//children(텍스트 빠진거) vs childNodes(텍스트 포함)
		console.log(liList);
		console.log(target);
		for(var i = 0; i < liList.length; i++){
			if(liList[i]!== target)
				continue;

			board.style.left= i*(-100) + "%";
			break;
		}
	});
	
	
	
	
	var clickPoint;
	var board = document.querySelector(".board");
	var is_down;
	var standard;
	board.addEventListener("mousedown", function(e){
		is_down = true;
		e.preventDefault();
		console.log(e);
		clickPoint= e.pageX; 
		standard = board.style.left;
		standard = parseFloat(standard) || 0;// 레프트가 안 정해져 있어서 0으로 설정. 
	});

	board.addEventListener("mousemove", function(e){
		if(!is_down)
			return;
			
		var distance = e.pageX - clickPoint;
		console.log(distance);
		board.style.left = (standard +(distance/document.body.clientWidth)*100)+"%";
		
	});

	board.addEventListener("mouseup", function(e){
		is_down = false;
		var distance = e.pageX - clickPoint;
		distance = (distance/document.body.clientWidth)*100;
		if(distance > 15 && standard < 0){//수학은 어려워 
			board.style.left = standard + 100 + "%";
		} else if(distance < -15 && standard > -600 ){
			board.style.left = standard - 100 + "%";
		} else {
			board.style.left = standard + "%";
		}
		
		var left = parseFloat(board.style.left);
		var tab_no = left / -100;
		var liList = document.querySelectorAll("li");
		for(var a =0; a < liList.length; a++){
			var liEle = liList[a];
			liEle.className="";
		}
		liList[tab_no].className="coloured";
	});
}





/*
function drag(){
	var section = document.querySelector(".view");
	section.addEventListener("mousedown", function(e){
		//mousedown이랑 직접 onmousedown을 쓰는 것 차이는 뭐지?ㅠ
		var posX=e.clientX;
		console.log(posX);
		/* 내가 생각한 방식..
		마우스 다운한 x값을 구한다.
		마우스가 움직이면 화면도 함께 움직인다(translate를 실시간으로 어떻게 하지?)
		x값에서 -30px이상, +30이상 움직이면 
		section의 레프트 값을 -100% 해버린다.
		
	});
}
*/


