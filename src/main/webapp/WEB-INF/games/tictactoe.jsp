<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Tic Tac Toe</title>
<meta charset="UTF-8" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/ttt.css">
</head>
<body>
	<form action="home" method="get">
		<input type="hidden" name="page" value="lobby">
		<button type="submit">Lobby</button>
	</form>
	<h3>Tic Tac Toe</h3>
	<hr>

	<section>
		<p id="message"></p>
		<form onsubmit="return messageToPerson(event)">
			<input type="text" id="msgBox" placeholder="Write something..."></input>
			<button type="submit">Submit</button>
		</form>
	</section>
	<hr>
		<p id="status">Waiting for opponent...</p>

	<div id="board"></div>
	<button id="resetBtn" onclick="requestPlayAgain()">Play Again</button>


	<script>
let username = "<%=session.getAttribute("username")%>";
let userId = "<%=session.getAttribute("userId")%>";
let gameId = "<%=request.getAttribute("gameId")%>"; // initial game
let symbol = "";
let myTurn = false;
let gameOver = false;

//Create board (5x5)
const board = document.getElementById("board");
const boardSize = 5;
for (let i = 0; i < boardSize * boardSize; i++) {
    const cell = document.createElement("div");
    cell.classList.add("cell");
    cell.dataset.index = i;
    cell.onclick = () => makeMove(i, cell);
    board.appendChild(cell);
}


// Connect to Game WebSocket
let ws = new WebSocket(
    "ws://" + location.host + "<%=request.getContextPath()%>/tictactoe?gameId=" + gameId + "&userId=" + userId
);

ws.onopen = () => {
    ws.send(JSON.stringify({ type: "join", username, userId, gameId }));
};

ws.onmessage = (event) => {
    let data = JSON.parse(event.data);

    switch (data.type) {
        case "gameStart":
            resetBoard(); // reset previous game moves
            gameId = data.gameId; // update gameId for new game
            document.getElementById("status").textContent = "Game started! Waiting for opponent move...";
            break;

        case "assignSymbol":
            symbol = data.symbol;
            myTurn = data.myTurn;
            document.getElementById("status").textContent = `You are ${symbol} ${myTurn ? "(Your turn)" : "(Opponent's turn)"}`;
            break;

        case "move":
            const cell = board.children[data.cell];
            if (!cell.textContent) {
                cell.textContent = data.symbol;
                checkGameStatus();
                myTurn = (data.symbol !== symbol) && !gameOver;
            }
            break;

        case "playAgainRequest":
            if (confirm(data.from + " wants to play again. Accept?")) {
                ws.send(JSON.stringify({ type: "playAgainResponse", accepted: true }));
            } else {
                ws.send(JSON.stringify({ type: "playAgainResponse", accepted: false }));
            }
            break;

        case "playAgainDeclined":
            alert(data.message);
            document.getElementById("resetBtn").style.display = "inline-block";
            break;
        case "chat":
        	document.getElementById("message").innerText = data.from + ": " + data.text;
        	break;
        case "opponentFeft":
        	alert("Opponent Left");
        	break;
        	
    }
};

ws.onclose = () => console.log("WebSocket closed");

// Make a move
function makeMove(index, cell) {
    if (!myTurn || cell.textContent !== "" || gameOver) return;

    cell.textContent = symbol;
    ws.send(JSON.stringify({ type: "move", cell: index, symbol }));
    checkGameStatus();
    myTurn = false;
}


function checkGameStatus() {
    const n = boardSize;      // 5
    const k = 4;              // 4 in a row wins
    const cells = Array.from(board.children).map(c => c.textContent);
    const getCell = (r, c) => cells[r * n + c];

    const directions = [
        [0, 1],  // → horizontal
        [1, 0],  // ↓ vertical
        [1, 1],  // ↘ diagonal
        [1, -1]  // ↙ anti-diagonal
    ];

    for (let r = 0; r < n; r++) {
        for (let c = 0; c < n; c++) {
            const player = getCell(r, c);
            if (!player) continue;

            for (const [dr, dc] of directions) {
                // prune if not enough room to make k in a row
                const endR = r + dr * (k - 1);
                const endC = c + dc * (k - 1);
                if (endR < 0 || endR >= n || endC < 0 || endC >= n) continue;

                let win = true;
                for (let step = 1; step < k; step++) {
                    if (getCell(r + dr * step, c + dc * step) !== player) {
                        win = false; break;
                    }
                }
                if (win) {
                    document.getElementById("status").textContent = "Player " + player + " wins!";
                    gameOver = true;
                    document.getElementById("resetBtn").style.display = "inline-block";
                    return;
                }
            }
        }
    }

    // Draw check
    if (cells.every(c => c !== "")) {
        document.getElementById("status").textContent = "It's a draw!";
        gameOver = true;
        document.getElementById("resetBtn").style.display = "inline-block";
    }
}



// Reset board locally
function resetBoard() {
    board.querySelectorAll(".cell").forEach(c => c.textContent = "");
    gameOver = false;
    //myTurn = false;
    document.getElementById("resetBtn").style.display = "none";
    document.getElementById("status").textContent = "Waiting for opponent move...";
}

// Play Again button
function requestPlayAgain() {
    ws.send(JSON.stringify({ type: "playAgain" }));
    document.getElementById("resetBtn").style.display = "none"; // hide until new game starts
}

//Messageing
function messageToPerson(event) {
    event.preventDefault(); // stop form from reloading the page

    let msg = document.getElementById("msgBox").value;
    
    let data = {
		type: "chat",
		text: msg
    };
    
    ws.send(JSON.stringify(data));
    
    document.getElementById("message").innerText = "You: " + msg;
    document.getElementById("msgBox").value = "";

     

    return false; // prevent default form action
}


</script>
</body>
</html>
