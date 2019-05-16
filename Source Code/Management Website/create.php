<?php
session_start();
$inputAdmin = $_SESSION['inputAdmin'];
if (empty($_SESSION['inputAdmin'])) {
    header("location: adminLogin.php");
}
include "dbConnect.php";
$fullname = $_POST['fullname'];

$phonenum = $_POST['phonenum'];
$username = $_POST['uname'];
$password = $_POST['password'];
$hashed_password = md5($password);
$basic = mysqli_query($db_connection, "select touristattraction.attrid from touristattraction inner join attr_account on touristattraction.attrid = attr_account.attr_id where username='$inputAdmin'");
$res = mysqli_fetch_array($basic);
$res2 = $res['attrid'];
$query = "insert into employee (attrid, name, phonenum, username, password, total) values ($res2, '$fullname', '$phonenum', '$username', '$hashed_password', 0);";
$query_run = mysqli_query($db_connection, $query);
if ($query_run) {
    echo 'Success';
    header("location: employeeList.php");
} else {
    echo "Some error occured, please try again!";
}
?>
