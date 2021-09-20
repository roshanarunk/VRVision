<?php

require "conn.php";

$username = $_POST['username'];



$data = array();

$sql = "SELECT id, name FROM doctors WHERE username like '$username';";




$stmt = $conn->prepare($sql);

$stmt -> execute();

$stmt->bind_result($id, $name);

while($stmt->fetch()){
	$temp = [
	'id'=>$id,
	'name'=>$name,
	];

	array_push($data, $temp);

}

echo json_encode($data);

?>