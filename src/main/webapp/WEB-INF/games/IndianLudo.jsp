<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/iludo.css">
</head>
<body>
		 <div id="ludoBoard"></div>

	<script type="text/javascript">
		let username = "<%=session.getAttribute("username")%>";
		let userId = "<%= session.getAttribute("userId")%>";
		let gameId = "<%= request.getAttribute("gameId")%>";
		
		let color = "";
		let myTurn = false;
		let gameOver = false;
		
		  const board = document.getElementById("ludoBoard");

		    for (let i = 0; i < 25; i++) {  // 5x5 board
		      const cell = document.createElement("div");
		      cell.classList.add("cell");
		      if (i === 12) cell.classList.add("home");
		      if (i == 22) cell.classList.add("home1");
		      if (i == 14) cell.classList.add("home2");
		      if (i == 2) cell.classList.add("home3");
		      if (i == 10) cell.classList.add("home4");
		      board.appendChild(cell);
		    }
		  
		let ws = new webSocket(
				"ws://" + location.host + "<%= request.getContextPath() %>/iludo?gameId=" + gameId + "&userId=" + userId
		);
		
		ws.onopen = () =>{
			ws.send(JSON.stringify({type: "join", username, userId, gameId}));
		};
		
		
		  
		  
		  
		  
	</script>
</body>
</html>