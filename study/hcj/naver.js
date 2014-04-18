window.onload=function(){

	var menu = document.querySelector(".menu ul");
	menu.addEventListener("click", function (e){
/*
			console.log(e);
			console.log(this);
*/	
			var target = e.target || e.srcElement;
			var data = target.getAttribute("data");
			var selected = document.querySelectorAll("li.selected");
			for(var a =0; a <selected.length; a++){
				var el = selected[a];
				el.className="";
			}
			target.className = "selected";
			
/*
			var blind=document.querySelectorAll("section.mainNews");
			for(var i = 0; i< blind.length; i++){	
				blind[i].style.display="none";				
			}

			var section = document.querySelector('section.mainNews[data="' + data + '"]');
			section.style.display="block";
*/
			var board = document.querySelector(".board");
			var liList=this.children;
			console.log(liList);
			console.log(target);
			for(var i = 0; i<liList.length; i++){
				if(liList[i]==target){
					board.style.left= i*(-100) + "%";
					break;
				} 
			}
			
	});
	
	
	
}


