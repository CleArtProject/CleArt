<!DOCTYPE html>
<?php
session_start();
$inputAdmin = $_SESSION['inputAdmin'];
if (empty($_SESSION['inputAdmin'])) {
	header("location: adminLogin.php");
}
?>
<html lang="en">

<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>CleArt</title>
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css" integrity="sha384-GJzZqFGwb1QTTN6wy59ffF1BuGJpLSa9DkKMp0DgiMDm4iYMj70gZWKYbI706tWS" crossorigin="anonymous">

	<link rel="stylesheet" href="res/css/style.css">

	<style>
		body {
			background: #ccdce9 no-repeat fixed center;
			height: 100%;
			background-position: center;
			background-repeat: no-repeat;
			background-size: cover;
		}

		.align-middle {
			vertical-align: middle !important;
		}
	</style>
</head>

<body>
	<div class="site-content">
		<header>
			<nav class="navbar navbar-expand-lg navbar-light sticky-top">
				<a href="index.php" class="nav-brand ml-3 mr-3">
					<img src="res/img/cleart-logo.png" width="100" height="57" class="d-inline-block align-top" alt=""> </a>
				<ul class="navbar-nav mr-auto">
					<li class="nav-item">
						<a class="nav-link" href="index.php" style="margin-right:20px">Home</a>
					</li>
					<li class="nav-item">
						<a class="nav-link" href="employeeList.php" style="margin-right:20px">Manage Employee</a>
				</ul>
				<ul class="navbar-nav ml-auto">
					<li class="nav-item">
						<a href="adminLogout.php" class="nav-link" href="#" style="margin-right:40px">Logout</a>
					</li>
				</ul>
			</nav>
		</header>
		<br>
		<br>
		<br>
		<br>
		<div class="row" style="padding:5px">
			<br>
			<br>
			<div class="col-3">
			</div>
			<div class="col-6">
			<h1>Create new employee account</h1>
			<div class="landing-text mt-3" style="padding:5px">
			<form action="create.php" method="post" class="was-validated">
				<div class="form-group">
					<label for="fullname">Full name:</label>
					<input type="text" class="form-control" id="fullname" placeholder="Enter full name" name="fullname" required>
					<div class="invalid-feedback">Please fill out this field.</div>
				</div>
				<div class="form-group">
					<label for="phonenum">Phone number:</label>
					<input type="text" class="form-control" id="phonenum" placeholder="Enter phone number" name="phonenum" required>
					<div class="invalid-feedback">Please fill out this field.</div>
				</div>
				<div class="form-group">
					<label for="uname">Username:</label>
					<input type="text" class="form-control" id="uname" placeholder="Enter username" name="uname" required>
					<div class="invalid-feedback">Please fill out this field.</div>
				</div>
				<div class="form-group">
					<label for="password">Password:</label>
					<input type="password" class="form-control" id="password" placeholder="Enter password" name="password" required>
					<div class="invalid-feedback">Please fill out this field.</div>
				</div>
				<div class="form-group form-check">
					<label class="form-check-label">
						<input class="form-check-input" type="checkbox" name="remember" required> I agreed to create this account.
						<div class="invalid-feedback">Check this checkbox to continue.</div>
					</label>
				</div>
				<a href="create.php"><button type="submit" class="btn btn-primary">Submit</button></a>
				<a href="employeeList.php" class="btn btn-secondary">Cancel </a>
			</form>
		</div>
			</div>
			<div class="col-3">
			</div>
			
		</div>
		
	</div>
</body>

</html>