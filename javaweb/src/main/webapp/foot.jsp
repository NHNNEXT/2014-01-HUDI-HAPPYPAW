		
	</section>
	<aside>
	<div class="login_box">
		<p>
			학번:
			<%=request.getAttribute("session")%><!-- id -->
		</p>
		<p>
			이름:
			<%=request.getAttribute("name")%></p>
		<p>
			<a href="./logout">logout</a>
		</p>
	</div>
	<div class="menu">
		<ul class="menu_ul">
			<li><a href="/nyam/nyamHistory">나의 식사기록</a></li>
			<li><a href="">나의 식사순위</a></li>
			<li><a href="">지정식당 목록</a></li>
			<li><a href="">지정식당 신청 게시판</a></li>
		</ul>
	</div>
	</aside>

</body>
</html>
