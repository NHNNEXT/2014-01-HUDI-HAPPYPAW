<%@include file="./head.jsp"%>

<script src="./js/daylight.animation.js" type="text/javascript"></script>
<script src="./js/daylight.Event.js" type="text/javascript"></script>

<link href="./css/ranking.css" rel="stylesheet"/>
<div class="background">
	<img src="rank.png" class="background_rank"/>
	<div class="signs">
		<div class="sign hide {class}" style="left:{x}%; top:{y}%;" data-count="{num}">{num}<div class="peoples"></div></div>
	</div>
	<div class="others">
		<div class="other group1">
			<img src="./img/fruit.png"/>
		</div>
		<div class="other group3">
			<img src="./img/dish.png"/>
		</div>
	</div>
</div>
<div class="circles">
	<div class="circle {class}" style="left:{x}%; top:{y}px;" data-style="left:{x}%; top:{y}px;" data-speed="{speed}"></div>
</div>

<script src="./js/ranking.js" type="text/javascript"></script>
<%@include file="./foot.jsp"%>