<?php
require "conn.php";

$client_id = $_POST['client_id'];


$mysql_qry = "UPDATE clients SET doctor_id = 0 WHERE id =  $client_id";







if(mysqli_query($conn, $mysql_qry)){

echo "Delete Success";
    
}else {

echo "Delete Failed";

}


?>
