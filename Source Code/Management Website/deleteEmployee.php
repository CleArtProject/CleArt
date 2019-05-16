<?php
session_start();
$inputAdmin = $_SESSION['inputAdmin'];
if (empty($inputAdmin)) {
	header("location: adminLogin.php");
}
include 'dbConnect.php';
$empid = $_GET['empid'];
$delete="DELETE FROM employee where empid=$empid";
mysqli_query($db_connection,$delete);
header("location:employeeList.php");
?>