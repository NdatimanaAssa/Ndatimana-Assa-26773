<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
	<title>Search</title>
	<meta charset="UTF-8" />
	<style>
		body {
			font-family: Arial, sans-serif;
			display: flex;
			justify-content: center;
			align-items: center;
			height: 100vh;
			margin: 0;
			background-color: #f0f0f0;
		}
		.search-container {
			background: white;
			padding: 30px;
			border-radius: 8px;
			box-shadow: 0 2px 10px rgba(0,0,0,0.1);
			text-align: center;
			width: 400px;
		}
		.search-input {
			width: calc(100% - 110px);
			padding: 10px;
			border: 1px solid #ddd;
			border-radius: 4px;
			font-size: 16px;
			box-sizing: border-box;
		}
		.search-btn {
			padding: 10px 16px;
			margin-left: 8px;
			background: #007bff;
			color: #fff;
			border: none;
			border-radius: 4px;
			font-weight: bold;
			cursor: pointer;
		}
		.search-btn:hover { background: #0056b3; }
	</style>
</head>
<body>
	<div class="search-container">
		<h2>Search</h2>
		<form method="post" action="redirect">
			<input class="search-input" type="text" name="query" placeholder="Search Google..." />
			<button class="search-btn" type="submit">Go</button>
		</form>
	</div>
</body>
</html>
