<?php

namespace App;

use PDOException;

class Usuario {

	private $db;
	function __construct($db){
		$this->db = $db;
	}

	function Usuarios($id){
		$orm = new \App\Core\Model($this->db);

       $filtro = array();
	   if($id > 0){
		  $filtro = array("id_usuario"=>$id);
	   }

		$list = $orm->select($filtro,
							"tbl_usuario",
							"mUsuario",
							array(	"id_usuario"=>"id_usuario",
                                    "nombre"=>"nombre",
                                    "apellidos"=>"apellidos",
									"email"=>"email",
									"contrasena"=>"contrasena",
									"avatar"=>"avatar"
							),
							"","","");
		$items = iterator_to_array ($list);

		foreach($items as $item){
			
			$item->avatar = "data:image/png;base64," . base64_encode($item->imgArray);
		
		}
		return  $items;
	}

	function Save($data){

		$orm = new \App\Core\Model($this->db);
        
		if($data["id_usuario"]>0){

			$instances = $this->Usuarios( $data["id_usuario"] );
            $instance = $instances[0];
	
            $instance->nombre = $data["nombre"];
            $instance->apellidos = $data["apellidos"];
			$instance->email =  $data["email"];
			$instance->contrasena =  $data["contrasena"];

            $instance->avatar = $data["avatar"];
            
			if(isset($instance->imagen) ){
				$datab = $instance->avatar;
				list($type, $datab) = explode(';', $datab);
				list(, $datab)      = explode(',', $datab);
				$instance->avatar = base64_decode($datab);
			}

			return $orm->save($instance 
							,"tbl_usuario"
							,"id_usuario"
							, array("id_usuario"=>"id_usuario",
									"nombre"=>"nombre",
									"apellidos"=>"apellidos",
									"email"=>"email",
									"contrasena"=>"contrasena",
									"avatar"=>"avatar"));
		
		}else{


			if(isset($data["avatar"]) ){
			$datab = $data["avatar"];
				list($type, $datab) = explode(';', $datab);
				list(, $datab)      = explode(',', $datab);
				$data["avatar"] = base64_decode($datab);
			}

			return $orm->save($data 
			,"tbl_usuario"
			,"id_usuario"
			, array("id_usuario"=>"id_usuario",
					"nombre"=>"nombre",
					"apellidos"=>"apellidos",
					"email"=>"email",
					"contrasena"=>"contrasena",
					"avatar"=>"avatar"));
		}
	}

	function Delete($data){

        try {
            $sentencia =  $this->db->prepare("DELETE FROM tbl_usuario WHERE id_usuario = ? " );
		    $sentencia->bindParam(1,$data["id_usuario"]);
		    $sentencia->execute();
            return $mensaje = "Eliminado correctamente";
        } catch (PDOException $exc) {
            return $mensaje = "No se pudo eliminar";
        }

		return array("mensaje"=>$mensaje);
	}

}
?>