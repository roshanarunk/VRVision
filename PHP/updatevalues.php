<?php

require "conn.php";

$user_id = $_POST['user_id'];
$version_id  = $_POST['version_id'];
$fov = $_POST['fov'];
$brightness = $_POST['brightness'];
$size = $_POST['size'];
$pos_x = $_POST['pos_x'];
$pos_y = $_POST['pos_y'];
$off_x = $_POST['off_x'];
$off_y = $_POST['off_y'];

$version_id_current = $version_id-1;

$date = date("Y-m-d");


$qrydate = "SELECT date FROM versions WHERE user_id = '$user_id' AND id = '$version_id_current'";
    $result = mysqli_query($conn, $qrydate);
    $row = mysqli_fetch_array($result);
    $date_last = $row[0];
    


if($date===$date_last){
    $mysql_qry = "UPDATE versions SET fov = '$fov', brightness = '$brightness', size = '$size', pos_x = '$pos_x', pos_y = '$pos_y', off_x = '$off_x', off_y = '$off_y' WHERE user_id = '$user_id' and id = '$version_id_current'";
    if(mysqli_query($conn, $mysql_qry)){
        echo "values updated successfully";
    }else{
         echo "Error: " . $mysql_qry . "<br>" . mysqli_error($conn);
    }
    
}else {
    $mysql_qry = "UPDATE clients SET version_id = '$version_id' WHERE id = '$user_id'";
    
    $mysql_qry2 = "INSERT INTO versions (id, fov, brightness, size, pos_x, pos_y, off_x, off_y, date, user_id) VALUES ('$version_id', '$fov', '$brightness', '$size', '$pos_x','$pos_y', '$off_x', '$off_y', '$date', '$user_id')";
    
    if(mysqli_query($conn, $mysql_qry)){
        echo "values inserted";
    }
    else{
         echo "Error: " . $mysql_qry . "<br>" . mysqli_error($conn);
    }
    if(mysqli_query($conn, $mysql_qry2)){
        echo "data inserted";
    } else{
         echo "Error: " . $mysql_qry2 . "<br>" . mysqli_error($conn);
    }


    
}

?>

