<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>Lobby</title>
	
</head>
<body>
	<h3>Select Game: </h3>
    <select id="gameMode">
        <option value="ttt">Tic Tac Toe</option>
        <option value="indianludo">IndianLudo</option>
    </select>
	
	</select>

	<h3>Online Player</h3>
	<ul id="users"></ul>




<script>
let username = "<%= (session.getAttribute("username")) %>";
let userId = "<%= (session.getAttribute("userId")) %>";

let tttWs = new WebSocket("ws://" + location.host + "<%= request.getContextPath() %>/lobby");

tttWs.onopen = () => {
    tttWs.send(JSON.stringify({type: "join", username: username, userId: userId}));
};

tttWs.onmessage = (msg) => {
    let data = JSON.parse(msg.data);

    if (data.type === "users") {
        const usersEl = document.getElementById("users");
        usersEl.innerHTML = "";

        data.list
            .filter(u => u.username !== username)
            .forEach(u => {
                const li = document.createElement("li");
                li.textContent = u.username + " ";
                const btn = document.createElement("button");
                btn.textContent = "Invite";
                btn.addEventListener("click", () => invite(u.userId));
                li.appendChild(btn);
                usersEl.appendChild(li);
            });
    } else if (data.type === "invite") {
        if (confirm(data.fromName + " invited you to play " + data.mode + ". Do you accept?")) {
            tttWs.send(JSON.stringify({
                type: "accept",
                from: userId,      
                to: data.fromId,
                mode: data.mode
            }));
        }
    } else if (data.type === "startGame") {
        window.location.href = "game?gameId=" + data.gameId + "&mode=" + data.mode;
    }
};

function invite(toUserId) {
	 const selectedMode = document.getElementById("gameMode").value;
	    if (!selectedMode) {
	        alert("Please select a game before inviting.");
	        return;
	    }

	    tttWs.send(JSON.stringify({
	        type: "invite",
	        to: toUserId,
	        mode: selectedMode
	    }));
}
</script>
	<form action="home" method="get">
		<input type="hidden" name="page"value="welcome">
		<button type="submit">Home</button>
	</form>
</body>
</html>
