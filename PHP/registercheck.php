<?php

require "conn.php";

//GET METHOD to get data from the app
$username = $_GET['username'];

//sql_qry and running of the query
$mysql_qry = "select * from clients where username = '$username';";
$result =mysqli_query($conn, $mysql_qry);

//checks if the query was run successfully
if(mysqli_num_rows($result) > 0){
echo "Exists";
}else {
echo "Ok";
}
?>