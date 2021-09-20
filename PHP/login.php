<?php
require "conn.php";
$username = $_POST['username'];
$password = $_POST['password'];
$mysql_qry = "select * from clients where username like '$username' and password like '$password'";
$result =mysqli_query($conn, $mysql_qry);

if(mysqli_num_rows($result) > 0){
echo "Login Success";
}else {
echo "Login Failed";
}

