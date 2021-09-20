<?php
require "conn.php";

$username = "mukund";

$qrydate = "SELECT date FROM versions WHERE user_id = '5' AND id = '1'";
    $result = mysqli_query($conn, $qrydate);
    $row = mysqli_fetch_array($result);
    $date_last = $row[0];
$date = date("Ymd");


if($date===$date_last){
    echo "yeet";
}


?>