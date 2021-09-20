<?php

require "conn.php";

$username = $_POST['username'];



$data = array();

$sql = "SELECT id, name, code, doctor_id, version_id FROM clients WHERE username like '$username';";




$stmt = $conn->prepare($sql);

$stmt -> execute();

$stmt->bind_result($id, $name, $code, $doctor_id, $version_id);

while($stmt->fetch()){
	$temp = [
	'id'=>$id,
	'name'=>$name,
	'code'=>$code,
	'doctor_id'=>$doctor_id,
	'version_id'=>$version_id,
	];

	array_push($data, $temp);

}

echo json_encode($data);

?>