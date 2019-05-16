<?php
    class dbConnect{
        private $con;

        function connect(){
            require_once 'constant.php';
            $this->con=new mysqli(DB_HOST, DB_USER, DB_PASS, DB_NAME);

            if(mysqli_connect_errno()){
                echo('failed to connect'.mysqli_connect_error());
                return null;
            }
            else{
                return $this->con;
            }
        } 
    }