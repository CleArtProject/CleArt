<!DOCTYPE html>
<?php
session_start();
$inputAdmin = $_SESSION['inputAdmin'];
if (empty($inputAdmin)) {
	header("location: adminLogin.php");
}
?>
<html lang="en">

<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>CleArt Management</title>
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css" integrity="sha384-GJzZqFGwb1QTTN6wy59ffF1BuGJpLSa9DkKMp0DgiMDm4iYMj70gZWKYbI706tWS" crossorigin="anonymous">
	<link rel="stylesheet" href="res/css/style.css">
	<link rel="shortcut icon" href="res/img/cleart-logo.png" />
	<script src="js/Chart.bundle.js"></script>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"></script>

	<!-- Additional CSS -->
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

		<!-- NAVIGATION BAR -->
		<header>
			<nav class="navbar navbar-expand-lg navbar-light sticky-top">
				<a href="index.php" class="nav-brand ml-3 mr-3">
					<img src="res/img/cleart-logo.png" width="100" height="57" class="d-inline-block align-top" alt=""> </a>
				<ul class="navbar-nav mr-auto">
					<li class="nav-item active">
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
	</div>

	<!-- START OF CONTENT -->
	<div class="landing-text ml-5 mr-5 mt-3" style="padding:5px">
		<br>
		<br>

		<!-- WELCOME-->
		<div class="row">
			<div class="col-6">
				<?php
				include "dbConnect.php";
				$attrname = mysqli_query($db_connection, "SELECT attrname from touristattraction inner join attr_account on touristattraction.attrid = attr_account.attr_id where username = '$inputAdmin';");
				$attrname2 = mysqli_fetch_array($attrname);
				echo "<h1>Welcome, ", $attrname2['attrname'], "<h1>";
				?>
			</div>
			<div class="col-4 text-left">

			</div>
		</div>

		<!-- RATINGS -->
		<div class="row mt-5">
			<div class="col-6">
				<div class="card mb-4 bg-light ">
					<div class="card-body bg-light">
						<div class="row">
							<div class="col-3 align-self-center text-center">
								<?php
								include "dbConnect.php";
								$avg = mysqli_query($db_connection, "select AVG(rating) as rating from rate natural join userdata where attrid = 1;");
								$avg_res = mysqli_fetch_array($avg);
								$avg_print = round($avg_res['rating'], 1);
								echo "<h1>", $avg_print, "</h1>";
								?>
								<?php
								$result = mysqli_query($db_connection, "select count(*) as jumlah from (select * from rate inner join attr_account on rate.attrid=attr_account.attr_id where attr_account.username='$inputAdmin') a;");
								$row = mysqli_fetch_array($result);
								echo "from ", $row['jumlah'], " users";
								?>

							</div>
							<!-- RATINGS CHART -->
							<div class="col-9">
								<div class="chart-container">
									<canvas id="canvas"></canvas>
								</div>
							</div>
						</div>

					</div>

				</div>
			</div>
			<div class="col">
			</div>
		</div>

		<!-- START OF REVIEWS -->
		<h2>Reviews:</h2>
		<br>
		<div class="row mb-2">
			<div class="col-xs-12 col-sm-6">
				<!-- SEARCH BAR -->
				<form method="POST">
					<div class="input-group">
						<input type="text" name="search" class="form-control" placeholder="Search by username">
						<input class="btn btn-primary" style="margin-left:5px" type="SUBMIT" name="submit" value="Search">
						<a href="" class="btn btn-warning ml-1">Reset</a>
					</div>
				</form>
			</div>
		</div>
	</div>
	<!-- REVIEWS DATA -->
	<div class="ml-5 mr-5" style="padding:5px">
		<?php
		if (isset($_POST['search'])) {
			$key = $_POST['search'];
			$param = mysqli_real_escape_string($db_connection, $key);

			$result = mysqli_query($db_connection, "SELECT userdata.username, rate.review, rate.rating, datecreated FROM attr_account INNER JOIN touristattraction on attr_account.attr_id = touristattraction.attrid inner join rate on touristattraction.attrid = rate.attrid inner join userdata on rate.userID = userdata.userID where attr_account.username = '$inputAdmin' and userdata.username like '%" . $param . "%';");
		} else {
			$result = mysqli_query($db_connection, "SELECT userdata.username, rate.review, rate.rating, datecreated FROM attr_account INNER JOIN touristattraction on attr_account.attr_id = touristattraction.attrid inner join rate on touristattraction.attrid = rate.attrid inner join userdata on rate.userID = userdata.userID where attr_account.username = '$inputAdmin';");
		}
		echo '<div class="album">
				<div class="row"> ';
		while ($row = mysqli_fetch_array($result)) {
			?>
			<div class="col-4">
				<div class="card mb-4 bg-light ">
					<div class="card-header bg-light mr-1">
						<div class="row">
							<div class="col-10 text-left">
								<h3> <?php echo $row['username']; ?></h3>
							</div>
							<div class="col-1 text-right">
								<img src="res/img/rating.png" alt="rating" style="padding-top:5px">
							</div>
							<div class="col-1 text-left">
								<h3> <?php echo $row['rating']; ?></h3>
							</div>
						</div>
					</div>
					<div class="card-body bg-light">
						<p>"<?php echo $row['review']; ?>"
					</div>
					<div class="card-footer text-right bg-light">
						<?php echo $row['datecreated']; ?>
					</div>
				</div>
			</div>
		<?php
	}
	?>
	</div>

	<br>
	<br>

	<!-- START OF REPORTS -->
	<h2>Ongoing Reports:</h2>
	<br>
	<div class="row mb-3">
		<div class="col-xs-12 col-sm-6">
			<!-- SEARCH BAR -->
			<form method="POST">
				<div class="input-group">
					<input type="text" name="search2" class="form-control" placeholder="Search by username">
					<input class="btn btn-primary" style="margin-left:5px" type="SUBMIT" name="submit2" value="Search">
					<a href="" class="btn btn-warning ml-1">Reset</a>
				</div>
			</form>
		</div>
	</div>
	<!-- REVIEWS DATA -->
	<?php
	include "dbConnect.php";
	if (isset($_POST['search2'])) {
		$key = $_POST['search2'];
		$param = mysqli_real_escape_string($db_connection, $key);
		$result2 = mysqli_query($db_connection, "SELECT userdata.username, report.issue, report.comment, report.datecreated, report.status, report.specifics FROM attr_account INNER JOIN touristattraction on attr_account.attr_id = touristattraction.attrid inner join report on touristattraction.attrid = report.attrid inner join userdata on report.userID = userdata.userID where attr_account.username = '$inputAdmin' and report.status='On Progress' and userdata.username like '%" . $param . "%';");
	} else {
		$result2 = mysqli_query($db_connection, "SELECT userdata.username, report.issue, report.comment, report.datecreated, report.status, report.specifics FROM attr_account INNER JOIN touristattraction on attr_account.attr_id = touristattraction.attrid inner join report on touristattraction.attrid = report.attrid inner join userdata on report.userID = userdata.userID where attr_account.username = '$inputAdmin' and report.status='On Progress';");
	}
	echo '<div class="album">
					<div class="row"> ';
	while ($row2 = mysqli_fetch_array($result2)) {
		?>
		<div class="col-4">
			<div class="card mb-4 bg-light ">
				<div class="card-header bg-light">
					<h3> <?php echo $row2['username']; ?></h3>
				</div>
				<div class="card-body bg-light ml-3">
					<li>Spesifics: <?php echo $row2['specifics']; ?></li>
					<li>Issue: <?php echo $row2['issue']; ?> </li>
					<li>Comment: <?php echo $row2['comment']; ?> </li>
					<li>Status: <?php echo $row2['status']; ?> </li>
				</div>
				<div class="card-footer text-right bg-light">
					Reported on: <?php echo $row2['datecreated']; ?>
				</div>
			</div>
		</div>
	<?php
}
?>
	</div>
	<br>
	<br>
	</div>
</body>

<!-- CHART SCRIPT -->
<script>
	var color = Chart.helpers.color;
	var horizontalBarChartData = {
		labels: ['5', '4', '3', '2', '1'],
		datasets: [{
			label: 'User count',
			backgroundColor: [
				'rgba(255, 99, 132, 1)',
				'rgba(54, 162, 235, 1)',
				'rgba(255, 206, 86, 1)',
				'rgba(75, 192, 192, 1)',
				'rgba(153, 102, 255, 1)',
				'rgba(255, 159, 64, 1)'
			],
			borderColor: [
				'rgba(255, 99, 132, 1)',
				'rgba(54, 162, 235, 1)',
				'rgba(255, 206, 86, 1)',
				'rgba(75, 192, 192, 1)',
				'rgba(153, 102, 255, 1)',
				'rgba(255, 159, 64, 1)'
			],
			borderWidth: 1,
			data: [
				<?php
				$sql = mysqli_query($db_connection, "select count(*) as jumlah from (select rating from rate inner join attr_account on rate.attrid = attr_account.attr_id where attr_account.username='$inputAdmin' and rating=5) a;");
				$row = mysqli_fetch_array($sql);
				echo $row['jumlah'];
				?>,
				<?php
				$sql = mysqli_query($db_connection, "select count(*) as jumlah from (select rating from rate inner join attr_account on rate.attrid = attr_account.attr_id where attr_account.username='$inputAdmin' and rating=4) a;");
				$row = mysqli_fetch_array($sql);
				echo $row['jumlah'];
				?>,
				<?php
				$sql = mysqli_query($db_connection, "select count(*) as jumlah from (select rating from rate inner join attr_account on rate.attrid = attr_account.attr_id where attr_account.username='$inputAdmin' and rating=3) a;");
				$row = mysqli_fetch_array($sql);
				echo $row['jumlah'];
				?>,
				<?php
				$sql = mysqli_query($db_connection, "select count(*) as jumlah from (select rating from rate inner join attr_account on rate.attrid = attr_account.attr_id where attr_account.username='$inputAdmin' and rating=2) a;");
				$row = mysqli_fetch_array($sql);
				echo $row['jumlah'];
				?>,
				<?php
				$sql = mysqli_query($db_connection, "select count(*) as jumlah from (select rating from rate inner join attr_account on rate.attrid = attr_account.attr_id where attr_account.username='$inputAdmin' and rating=1) a;");
				$row = mysqli_fetch_array($sql);
				echo $row['jumlah'];
				?>
			]
		}]

	};

	window.onload = function() {
		var ctx = document.getElementById('canvas').getContext('2d');
		window.myHorizontalBar = new Chart(ctx, {
			type: 'horizontalBar',
			data: horizontalBarChartData,
			options: {
				elements: {
					rectangle: {
						borderWidth: 2,
					}
				},
				responsive: true,
				maintainAspectRatio: true,
				legend: {
					position: 'right',
				},
				scales: {
					xAxes: [{
						display: true,
						ticks: {
							beginAtZero: true
						}
					}],
					yAxes: [{
						barPercentage: 0.8
					}]
				}
			}
		});

	};

	var colorNames = Object.keys(window.chartColors);
</script>

</html>