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
			//AQUI DEFINIMOS LA CONEXION DE LA BASE DE DATOS USANDO POD
			//PRIMERO PARAMETRO DNS
			//SEGUNDO NOMBRE DE LA BASE DE DATOS
			//TERCERO ES EL PASSWORD DE LA BASE DE DATOS
			$this->db = new \PDO("mysql:host=localhost;dbname=leonard9_test;charset=utf8mb4", "leonard9_test" , "Root210186@" );	
	}

	//ES LA FUNCION QUE SE EJECUTA CUANDO USAMOS EL METODO GET EN NUESTA LLAMADA AL API
	function read($name,$endpoint,$id){
		 $aux = "\\App\\" . $name;
		 $controller = new $aux($this->db);
		 return $controller->{$endpoint}($id);
	}

	//ES LA FUNCION QUE SE EJECUTA CUANDO USAMOS EL METODO POST EN NUESTRA LLAMADA API
	function write($name, $endpoint){
		 $aux = "\\App\\" . $name;
		 $controller = new $aux($this->db);
		 $aux_method = "" . $endpoint;

		 //$aux_method = "save_" . $endpoint;
		 $data = $this->getdataparamaters_json();
		
		 return $controller->{$aux_method}($data);
	}

 	/*
	* Content-Type application/json
	* Body= raw
	* JSON(application/json)
	*/

	//IMPORTANTE PARA QUE ESTO FUNCION ES USAR EL HEADER (application/json)
 function getdataparamaters_json()
	{
	  $json_params = file_get_contents("php://input");
	  $json_params  = utf8_encode($json_params);
	  $dd = json_decode( $json_params, true  );
	  return $dd;
	}
}
?>