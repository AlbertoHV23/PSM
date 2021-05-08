<?php
/**
* Copyight(c) Antonio Ramirez Santander.All rights reserved
* Copyight(c) Trivialsoft 2020.
* Hecho en México
*/
namespace App;
class Api{
	private $db;
	function __construct(){	
			$this->db = new \PDO("mysql:host=localhost;dbname=leonard9_test;charset=utf8mb4", "leonard9_test" , "Root210186@" );	
	}
	function read($name,$endpoint,$id){
		 $aux = "\\App\\" . $name;
		 $controller = new $aux($this->db);
		 return $controller->{$endpoint}($id);
	}
	function write($name, $endpoint){
		 $aux = "\\App\\" . $name;
		 $controller = new $aux($this->db);
		 $data = $this->getdataparamaters_json();
		 $aux_method = "save_" . $endpoint;
		 return $controller->{$aux_method}($data);
	}
 	/*
	* Content-Type application/json
	* Body= raw
	* JSON(application/json)
	*/
 function getdataparamaters_json()
	{
	  $json_params = file_get_contents("php://input");
	  $json_params  = utf8_encode($json_params);
	  $dd = json_decode( $json_params, true  );
	  return $dd;
	}
}
?>