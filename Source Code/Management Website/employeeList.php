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
	<!-- Latest compiled and minified CSS -->

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
		<header>
			<!-- Navbar -->
			<nav class="navbar navbar-expand-lg navbar-light sticky-top">
				<a href="index.php" class="nav-brand ml-3 mr-3">
					<img src="res/img/cleart-logo.png" width="100" height="57" class="d-inline-block align-top" alt=""> </a>
				<ul class="navbar-nav mr-auto">
					<li class="nav-item">
						<a class="nav-link" href="index.php" style="margin-right:20px">Home</a>
					</li>
					<li class="nav-item active">
						<a class="nav-link" href="employeeList.php" style="margin-right:20px">Manage Employee</a>
				</ul>
				<ul class="navbar-nav ml-auto">
					<li class="nav-item">
						<a href="adminLogout.php" class="nav-link" href="#" style="margin-right:40px">Logout</a>
					</li>
				</ul>
			</nav>
		</header>

		<!-- Best Employee Chart -->
		<div class="ml-5 mt-3" style="padding:5px">
			<br>
			<br>
			<h1>Employees of the Month</h1>
		</div>
		<br>
		<div class="text-center ml-5 mr-5">
			<div class="row">
				<div class="col-6">
					<div class="card mb-4 bg-light ">
						<div class="card-body bg-light">
							<div class="chart-container">
								<canvas id="canvas"></canvas>
							</div>
						</div>

					</div>
				</div>
			</div>
		</div>

		<!-- Start of Employee List -->
		<div class="mt-3 ml-5 mr-5">
			<h1>Employees List</h1>
			<br>
			<div class="row mb-3">
				<div class="col-xs-12 col-sm-6">
					<!-- Search bar -->
					<form method="POST">
						<div class="input-group">
							<input type="text" name="search" class="form-control" placeholder="Search by username">
							<input class="btn btn-primary" style="margin-left:5px" type="SUBMIT" name="submit" value="Search">
							<a href="" class="btn btn-warning ml-1">Reset</a>
						</div>
					</form>
				</div>
			</div>

			<!-- Add new Employee Button -->
			<div class="album">
				<div class="row">
					<div class="col-12">
						<a href="addEmployee.php">
							<div class="card mb-3 bg-light">
								<div class="card-body text-center">
									<h6>Add new employee</h6>
								</div>
							</div>
						</a>
					</div>
				</div>
			</div>

			<!-- Employee data -->
			<?php
			include "dbConnect.php";
			if (isset($_POST['search'])) {
				$key = $_POST['search'];
				$param = mysqli_real_escape_string($db_connection, $key);
				$result = mysqli_query($db_connection, "SELECT employee.empid, employee.name, employee.username, employee.phonenum, employee.total from employee inner join touristattraction on touristattraction.attrid = employee.attrid inner join attr_account on touristattraction.attrid = attr_account.attr_id where attr_account.username='$inputAdmin' and employee.name like '%" . $param . "%' order by employee.total desc;");
			} else {
				$result = mysqli_query($db_connection, "SELECT employee.empid, employee.name, employee.username, employee.phonenum, employee.total from employee inner join touristattraction on touristattraction.attrid = employee.attrid inner join attr_account on touristattraction.attrid = attr_account.attr_id where attr_account.username='$inputAdmin' order by employee.total desc;");
			}
			echo '<div class="album">
			<div class="row"> ';
			while ($row = mysqli_fetch_array($result)) {
				$empid = $row['empid'];
				?>
				<div class="col-3">
					<div class="card mb-4 bg-light ">
						<div class="card-header bg-light">
							<div class="row">
								<div class="col-8">
									<h3 class="display-7"><?php echo $row['name']; ?></h3>
								</div>
								<div class="col-4 text-right">
									<button type="button" class="btn btn-danger" data-toggle="modal" data-target="#basicExampleModal<?php echo $empid; ?>">
										Delete
									</button>
									<!-- Modal -->
									<div class="modal fade" id="basicExampleModal<?php echo $empid; ?>" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
										<div class="modal-dialog" role="document">
											<div class="modal-content">
												<div class="modal-header">
													<h5 class="modal-title" id="exampleModalLabel">Are you sure?</h5>
													<button type="button" class="close" data-dismiss="modal" aria-label="Close">
														<span aria-hidden="true">&times;</span>
													</button>
												</div>
												<div class="modal-body text-center">
													Click 'Delete' button to permanently delete employee account.
												</div>
												<div class="modal-footer">
													<button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
													<a href="deleteEmployee.php?empid=<?php echo $row['empid']; ?>" class="btn btn-danger">Delete</a>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="card-body bg-light">
							Username: <?php echo $row['username']; ?>
							<br>
							Phone Number: <?php echo $row['phonenum']; ?>
						</div>
						<div class="card-footer text-right bg-light">
							Total reports resolved: <?php echo $row['total']; ?>
						</div>
					</div>
				</div>

			<?php
		}
		?>
		</div>
	</div>
	<br><br><br>
</body>

<!-- Javascript -->
<script src="js/Chart.bundle.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"></script>


<!-- Best employee chart script -->
<script>
	var horizontalBarChartData = {
		labels: [
			<?php
			$sql = mysqli_query($db_connection, "select name from employee inner join attr_account on employee.attrid = attr_account.attr_id order by total desc limit 5;");
			while ($row = mysqli_fetch_array($sql)) {
				echo "'";
				echo $row['name'];
				echo "',";
			}
			?>
		],
		datasets: [{
			label: 'Total reports resolved',
			backgroundColor: [
				'rgba(255, 99, 132, 1)',
				'rgba(54, 162, 235, 1)',
				'rgba(255, 206, 86, 1)',
				'rgba(75, 192, 192, 1)',
				'rgba(153, 102, 255, 1)'
			],
			borderColor: [
				'rgba(255, 99, 132, 1)',
				'rgba(54, 162, 235, 1)',
				'rgba(255, 206, 86, 1)',
				'rgba(75, 192, 192, 1)',
				'rgba(153, 102, 255, 1)'
			],
			borderWidth: 1,
			data: [
				<?php
				$sql = mysqli_query($db_connection, "select total from employee inner join attr_account on employee.attrid = attr_account.attr_id order by total desc limit 5;");
				while ($row = mysqli_fetch_array($sql)) {
					echo $row['total'];
					echo ",";
				}
				?>
			]
		}]

	};

	window.onload = function() {
		var ctx = document.getElementById('canvas').getContext('2d');
		window.myHorizontalBar = new Chart(ctx, {
			type: 'bar',
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
					yAxes: [{
						display: true,
						ticks: {
							beginAtZero: true
						}
					}]
				},
				title: {
					display: true,
					text: 'Best 5 employees'
				}
			}
		});

	};

	var colorNames = Object.keys(window.chartColors);
</script>

</html>