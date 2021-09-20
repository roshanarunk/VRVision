<?php
require "conn.php";

$client_id = $_POST['client_id'];
$code = $_POST['code'];
$doctor_id = $_POST['doctor_id'];

$mysql_qry = "UPDATE clients SET doctor_id = $doctor_id WHERE id =  $client_id AND code = $code";

$mysql_qry2 =  "INSERT INTO doctor_clients (doctor_id, client_id) VALUES ($doctor_id, $client_id)";





if(mysqli_query($conn, $mysql_qry)){

echo "Add Success";
    
}else {

echo "Add Failed";

}

mysqli_query($conn, $mysql_qry2);

?>
