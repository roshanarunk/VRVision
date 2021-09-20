<?php

require "conn.php";

$name = $_POST['name'];
$username = $_POST['username'];
$password = $_POST['password'];
$code = rand(1000,9999);

if (!$conn) {
    die("Connection failed: " . mysqli_connect_error());
}

$mysql_qry = "INSERT INTO clients (name, username, password, code, doctor_id, version_id) VALUES ( '$name', '$username', '$password', '$code', '0', '1')";
$date = date("Y-m-d");



if (mysqli_query($conn, $mysql_qry)) {
    echo "New record created successfully. ";
    
    $qryid = "SELECT id FROM clients WHERE username = '$username'";
    $result = mysqli_query($conn, $qryid);
    $row = mysqli_fetch_array($result);
    $id = $row[0];
    
    $mysql_qry2 = "INSERT INTO versions (id, fov, brightness, size, pos_x, pos_y, off_x, off_y, date, user_id) VALUES (1, 100, 1, 0, 0.1, 0.25, 0.5, 0.25, '$date', '$id')";
    
    if (mysqli_query($conn, $mysql_qry2)){
        echo "Default Values added successfully";
    }
    
} else {
    echo "Error: " . $mysql_qry . "<br>" . mysqli_error($conn);
}

mysqli_close($conn);

?>