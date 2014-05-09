window.onload=function(){
	var elNav = document.querySelector(".menu ul");
	var attrData;
	var MOVE_RIGHT = 100;
	var MOVE_LEFT = -100;
	var elBoard = document.querySelector(".board");
	elBoard.style.left = 0+"%";	
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
	
	var xPosition;
	var boolCheck;
	var standard;
	elBoard.addEventListener("touchstart", function(e){
		e.preventDefault();
		
		style = elBoard.style.left;
		console.log("test" + style);
		standard = parseFloat(style);

		
		
/*
		standard = elBoard.style.left;
		standard = parseFloat(standard) || 0;
		console.log(standard);
*/
		
		
		xPosition = e.touches[0].pageX;

		boolCheck = true;
	});
	
	var width = window.innerWidth;	
	
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
	//console.log("tab  " + (tab+1));
	if(tab< 7 && tab>=0 ){
			liList[tab].className="coloured";
		
	} else {
		liList[6].className="coloured";
	}
	
	});

		
	
	
	
}