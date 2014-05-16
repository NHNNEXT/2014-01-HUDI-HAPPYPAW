var DATA = {
	TabCount : 7;
}

window.onload=function(){
	var elNav = document.querySelector(".menu ul");
	var attrData;
	var xPosition;
	var boolCheck;
	var standard;
	var width = window.innerWidth;
	var MOVE_RIGHT = 100;
	var MOVE_LEFT = -100;
	
	var elBoard = document.querySelector(".board");
	elBoard.style.left = 0+"%";	
	
	//네비게이션 탭 클릭하면 색깔 변하고 밑에 있는 컨텐츠도 움직여서 변경. 
	elNav.addEventListener("click", function(e){
		e.preventDefault();
		var elClick= e.target;
		var liColoured = document.querySelector(".coloured");
		liColoured.className="";
		elClick.className="coloured";

		var liList = document.querySelectorAll(".menu ul li");
		for(var i =0; i<liList.length; i++){
			if(liList[i] === elClick){
				elBoard.style.left = i * MOVE_LEFT + "%";
			}
		}
	});
	
	//보드를 터치해서 옮기면 움직이도록 하는 이벤트 리스너 세개. 
	elBoard.addEventListener("touchstart", function(e){
		e.preventDefault();
		
		style = elBoard.style.left;
		console.log("test" + style);
		standard = parseFloat(style);			
		xPosition = e.touches[0].pageX;
		console.log(e.touches[0]);
		boolCheck = true;
	});
	
	
	
	elBoard.addEventListener("touchmove", function(e){

		if(boolCheck!==true)
			return;

		var move = e.touches[0].pageX;
		var a = (standard + (move-xPosition)/width* MOVE_RIGHT) + "%";
		elBoard.style.left = a;
		
	});

	elBoard.addEventListener("touchend", function(e){
		boolCheck = false;
		var leavePosition = e.changedTouches[0].pageX;
		

/*
		if((leavePosition - xPosition) / width * MOVE_RIGHT >15){
			elBoard.style.left = standard + 100 +"%";
		}
*/	

	var distance = (leavePosition - xPosition)/ width * MOVE_RIGHT;
		if(distance > 15 && standard < 0){//수학은 어려워 
			standard += 100;
			elBoard.style.left = standard + "%";
		} else if(distance < -15 && standard > -600 ){
			standard -= 100;
			elBoard.style.left = standard + "%";
		} else {
			elBoard.style.left = standard + "%";
		}
		console.log("out standard__"+standard);
	
	var tab = standard / MOVE_LEFT;
	
	var liList = document.querySelectorAll(".menu ul li");
	for(var a = 0; a <liList.length; a++){
		liList[a].className ="";

	}

		//컨텐츠가 바뀔 때마다 위에 있는 탭의 색깔도 움직이게 
		if(tab< DATA.TabCount && tab>=0 ){
			liList[tab].className="coloured";
			
		} else {
			liList[6].className="coloured";
		}
	
	});

		
	
	
	
}