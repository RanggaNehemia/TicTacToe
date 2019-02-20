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
<body class="text-center" background="img/background.jpg">
	<div class="container w-25" style="margin-top: 25px">
		<div class="card card-block" style="width: 18rem;">
			<img class="card-img-top" src="img/tictactoe.png"
				alt="Card image cap">
			<div class="card-body">
				<form action="TicTacToeServlet" method="post">
					<div class="container mw-25">
						<div>Board Size</div>
						<div>
							<input type="text" name="Size" value="3" required>
						</div>

						<br /> <br />
						<div class="col col-12">
							<input type="submit" class="btn btn-primary	btn-block"
								name="Single" value="Start Single Player"><br />
						</div>
						<div class="col col-12">
							<input type="submit" class="btn btn-primary	btn-block"
								name="Multi" value="Start Multiplayer"><br />
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
</body>
</html>
