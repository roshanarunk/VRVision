<?php

require "conn.php";

$user_id = $_POST['user_id'];



$data = array();

$sql = "SELECT size, date FROM versions WHERE user_id like '$user_id';";




$stmt = $conn->prepare($sql);

$stmt -> execute();

$stmt->bind_result($size, $date);

while($stmt->fetch()){
	$temp = [
	'size'=>$size,
	'date'=>$date,
	];

	array_push($data, $temp);

}

echo json_encode($data);

?>