<%@page import="id.co.tictactoe.TicTacToe"%>

<jsp:useBean id="tictactoeBean" scope="session"
	class="id.co.tictactoe.TicTacToe" />

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="css/bootstrap.min.css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Tic Tac Toe</title>
</head>
<body class="text-center" background="img/background.jpg"
	style="margin-top: 2%">
	<div style="margin-top: 25px; display: table; margin: 0 auto;">
		<div class="card card-block" style="align-content: center;">
			<div class="card-body">
				<table border="1" style="margin: auto;">
					<%
						int[][] board = tictactoeBean.getBoard();
						for (int row = 0; row < tictactoeBean.getSize(); row++) {
					%>
					<tr>
						<%
							for (int col = 0; col < tictactoeBean.getSize(); col++) {
									if (board[row][col] == tictactoeBean.CROSS) {
						%>
						<td><img src="img/Cross.png" alt="X" /></td>
						<%
							} else if (board[row][col] == tictactoeBean.NOUGHT) {
						%>
						<td><img src="img/Nought.png" alt="O" /></td>
						<%
							} else if (board[row][col] == tictactoeBean.EMPTY) {
										if (tictactoeBean.getCurrentState() == tictactoeBean.PLAYING) {
						%>
						<td><a href="TicTacToeServlet?row=<%=row%>&col=<%=col%>">
								<img src="img/Null.png" alt=" " />
						</a></td>
						<%
							} else {
						%>
						<td><img src="img/Null.png" alt=" " /></td>
						<%
							}
									}
								}
						%>
					</tr>
					<%
						}
					%>
				</table>
				<%
					if (tictactoeBean.getCurrentState() == tictactoeBean.PLAYING) {
						if (tictactoeBean.getCurrentPlayer() == tictactoeBean.CROSS) {
				%>
				<h4 class="card-title">Cross turn to move</h4>
				<%
					} else if (tictactoeBean.getCurrentPlayer() == tictactoeBean.NOUGHT) {
				%>
				<h4 class="card-title">Nought turn to move</h4>
				<%
					}
					} else {
						if (tictactoeBean.getCurrentState() == tictactoeBean.NOUGHT_WON) {
				%>
				<h4 class="card-title">Nought has won the game!</h4>
				<%
					} else if (tictactoeBean.getCurrentState() == tictactoeBean.CROSS_WON) {
				%>
				<h4 class="card-title">Cross has won the game!</h4>
				<%
					} else if (tictactoeBean.getCurrentState() == tictactoeBean.DRAW) {
				%>
				<h4 class="card-title">It is a draw!</h4>
				<%
					}
				%>
				<form action="index.jsp" method="post">
					<input type="submit" class="btn btn-block btn-success" name="menu"
						value="Back to main menu"><br />
				</form>
				<%
					}
				%>
			</div>
		</div>
	</div>
</body>
</html>
