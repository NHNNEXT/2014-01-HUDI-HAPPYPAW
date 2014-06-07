<%@include file="/user/head.jsp"%>

<script src="/nyam/js/daylight.animation.js" type="text/javascript"></script>
<script src="/nyam/js/daylight.Event.js" type="text/javascript"></script>

<link href="/nyam/user/css/ranking.css" rel="stylesheet"/>
<div class="background">
	<img src="/nyam/img/rank.png" class="background_rank"/>
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
<script>
var user_data = {};

<%
ArrayList<HashMap<String, String>> nyamRanking = (ArrayList<HashMap<String, String>>)request.getAttribute("nyamRanking");
String myId = (String)request.getAttribute("id");
String myNum = "0";
for(int i = 0; i < nyamRanking.size(); ++i) {
	HashMap<String, String> user_info = nyamRanking.get(i);
	String id = user_info.get("id");
	String name = user_info.get("name");
	String nyamNum = user_info.get("nyamNum");
	
	if(myId.equals(id))
		myNum = nyamNum;
%>
user_data[<%=nyamNum%>] = user_data[<%=nyamNum%>] || [];
user_data[<%=nyamNum%>].push({id:"<%=id%>", name:"<%=name%>", num:user_data[<%=nyamNum%>].length});
<%}%>
var myId = "${id}";
var myNum = <%=myNum%>;
</script>
<script src="/nyam/js/ranking.js" type="text/javascript"></script>
<%@include file="/user/foot.jsp"%>