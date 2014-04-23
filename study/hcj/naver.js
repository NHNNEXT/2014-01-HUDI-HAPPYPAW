window.onload=function(){

	//별거 아닌거 같지만 자바스크립트는 변수명으로 타입을 알려주는 게 좋음. (동적타입이리 미리 타입을 정의해 둘 수가 없음)
	//Array같은 경우 aMenu라고 사용하면 되고, 아래의 경우는 element임으로 el 이라는 접두사와 같은 팀내에서 정한 약속을 따르는 방법이 있음.
	//이런거부터가 가독성있는 코드 유지보수에 좋은코드의 시작임.
	//javascript coding convention 이라고 찾아보기
	var menu = document.querySelector(".menu ul");
	menu.addEventListener("click", function (e){
/*
			console.log(e);
			console.log(this);
*/	
			var target = e.target || e.srcElement;
			var data = target.getAttribute("data");
			//selected라는 이름도 별로임. querySelecotAll의 반환값이 무엇인지 확인하고 그거에 어울리는 prefix를 포함한 이름을 지어줄 것 
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
			//위에서는 a라는 변수를 사용하고 여기서는 i라는 변수를 사용했는데, 여기서 또 a 라는 변수를 사용하지 못하는 이유가 뭘까? 
			for(var i = 0; i<liList.length; i++){
				if(liList[i]==target){
					board.style.left= i*(-100) + "%";  // -100이 의미하는 게 뭔지 코드만 보면 알 수가 없음. 상수개념이면 대문자변수로 상수라고 표시하고 어딘가 정의해두기. LEFT_STATIC = -100 이런식으로.
					break;
				} 
			}
			
	});
	
	
	
}


