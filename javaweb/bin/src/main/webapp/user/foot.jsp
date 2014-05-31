<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
	</section>
	<aside>
	<div class="login_box">
		<p>
			ID :
			<%=request.getAttribute("session")%><!-- id -->
		</p>
		<p>
			Name :
			<%=request.getAttribute("name")%></p>
		<p>
			<a href="./logout">logout</a>
		</p>
	</div>
	<div class="menu">
		<ul class="menu_ul">
			<li><a href="/nyam/ranking">Main</a></li>
			<li><a href="/nyam/nyamHistory">나의 식사기록</a></li>
			<li><a href="/nyam/historyPeriod">나의 역대 식사순위</a></li>
			<li><a href="/nyam/restaurantList">지정식당 목록</a></li>
			<li><a href="/nyam/board/boardList">지정식당 신청 게시판</a></li>
		</ul>
	</div>
	</aside>

</body>
</html>
