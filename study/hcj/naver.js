window.onload=function(){

	var menu = document.querySelector(".menu ul");
	menu.addEventListener("click", function (e){
/*
			console.log(e);
			console.log(this);
*/	
			var MOVE_WIDTH = -100;
			var target = e.target || e.srcElement;
			var liData = target.getAttribute("data");
			var liColoured = document.querySelectorAll("li.coloured");
			for(var a =0; a <liColoured.length; a++){
				var coloured = liColoured[a];
				coloured.className="";
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
			var elBoard = document.querySelector(".board");
			var liList=this.children;
			console.log(liList);
			console.log(target);
			for(a = 0; a<liList.length; a++){
				if(liList[a]==target){
					elBoard.style.left= i*(MOVE_WIDTH) + "%";
					break;
				} 
			}
			
	});
	
	
	
}


