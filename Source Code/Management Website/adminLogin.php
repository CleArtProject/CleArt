<?php
include "dbConnect.php";

session_start();
if (!empty($_SESSION['inputAdmin'])) {
    header("location: index.php");
}
?>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>CleArt Management Login</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css" integrity="sha384-GJzZqFGwb1QTTN6wy59ffF1BuGJpLSa9DkKMp0DgiMDm4iYMj70gZWKYbI706tWS" crossorigin="anonymous">
    <link rel="stylesheet" href="res/css/style.css">
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

<body id="LoginAdminForm">
    <header>
        <!-- Navbar -->
        <nav class="navbar navbar-expand-lg navbar-light sticky-top">
            <a href="index.php" class="nav-brand ml-3 mr-3">
                <img src="res/img/cleart-logo.png" width="100" height="57" class="d-inline-block align-top" alt=""> </a>
            <ul class="navbar-nav ml-auto">
                <li class="nav-item">
                    <a class="nav-link active" href="adminLogin.php" style="margin-right:40px">Cleart Management</a>
                </li>
            </ul>
        </nav>
    </header>
    <br><br><br><br><br>
    <div class="row">
        <div class="col text-center">
            <img src="res/img/cleart-logo.png" alt="CleArt" style="height:110px">
        </div>
    </div>
    <br>
    <!-- Login Form -->
    <div class="row">
        <div class="col-4 text-center">
        </div>
        <div class="col-4 text-center">
            <h1 class="form-heading">Management Login</h1>

            <div class="login-form text-center">
                <p>Please enter your username and password</p>

                <form action="adminLogin.php" method="post">
                    <div class="row">
                        <div class="col-2">
                        </div>
                        <div class="col-8">
                            <div class="form-group">
                                <input type="text" class="form-control text-center" name="inputAdmin" placeholder="Admin Username" required>
                            </div>
                            <div class="form-group">
                                <input type="password" class="form-control text-center" name="inputPassword" placeholder="Admin Password" required>
                            </div>
                            <button type="submit" name="adminLogin" class="btn btn-primary mt-2">Login</button>
                        </div>
                        <div class="col-2">
                        </div>
                    </div>

                </form>
            </div>
        </div>
        <div class="col-4 text-right">
        </div>

    </div>
    </div>
    <!-- Get and verify login data -->
    <?php
    if (isset($_POST['adminLogin'])) {
        $inputAdmin = $_POST['inputAdmin'];
        $inputPassword = $_POST['inputPassword'];
        $query = "select username from attr_account where username = '$inputAdmin' and password='$inputPassword'";
        $query_run = mysqli_query($db_connection, $query);
        $num_rows = mysqli_num_rows($query_run);
        if ($num_rows > 0) {
            $data = mysqli_fetch_assoc($query_run);
            if ($inputAdmin == $data['username']) {
                session_start();
                $_SESSION['inputAdmin'] = $inputAdmin;
                header("location:index.php");
            } else {
                echo '<script language="javascript">';
                echo 'alert("Invalid Credential")';
                echo '</script>';
            }
        } else {
            echo '<script language="javascript">';
            echo 'alert("Invalid Credential")';
            echo '</script>';
        }
    }

    ?>

</body>

</html>