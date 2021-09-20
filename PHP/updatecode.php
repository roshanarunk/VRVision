<?php
require "conn.php";
$username= "roshan";
$password = "1234";
$mysql_qry = "UPDATE clients SET code = '7878' where username like '$username' and password like '$password'";
$result = mysqli_query($conn, $mysql_qry);