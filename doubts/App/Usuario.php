<?php

namespace App;

use PDOException;

class Usuario {

	private $db;
	function __construct($db){
		$this->db = $db;
	}
	/**
	* devuelve el listado de Albums
	*/
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
		/*se convierte en base64 el contenido antes de retornar el objeto*/
		foreach($items as $item){
			
			$item->avatar = "data:image/png;base64," . base64_encode($item->imgArray);
		
		}
		return  $items;
	}
	/**
	* guarda o actualiza un archivo
	*/
	function Save($data){
		//FUNCIONA COMO INSERTAR Y UPDATE
		$orm = new \App\Core\Model($this->db);
        
		if($data["id_usuario"]>0){ //SI EL ID ES MAYOR QUE CERO ENTONCES ES UN UPDATE

			/*recuperar la instancia*/
			$instances = $this->Usuarios( $data["id_usuario"] );
            $instance = $instances[0];
            
			/*se sobreescribe las propiedades*/
            $instance->nombre = $data["nombre"];
            $instance->apellidos = $data["apellidos"];
			$instance->email =  $data["email"];
			$instance->contrasena =  $data["contrasena"];
			//RECIBE LA IMAGEN CODIFICADA EN BASE64	
            $instance->avatar = $data["avatar"];
            
			/*se convierte a binario antes de guardar la propiedad "contenido"*/
			if(isset($instance->imagen) ){
				$datab = $instance->avatar;
				list($type, $datab) = explode(';', $datab);
				list(, $datab)      = explode(',', $datab);
				//DECODIFICA 
				$instance->avatar = base64_decode($datab);
			}

			//SAVE O UPDATE
			//INSTANCIA ES EL OBJETO CON LA INFORMACION A GUARDAR O ACTUALIZAR
			//NOMBRE DE LA TABLA
			//NOMBRE DE LA COLUMNA ID DE LA TABLA
			//ARRAY CON LAS COLUMNAS DE LA TABLA
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

			//EL ID ES CERO ENTONCES ES UN INSERT

			if(isset($data["avatar"]) ){
			/*se convierte a binario antes de guardar la propiedad "contenido"*/
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
		//Referencia https://diego.com.es/tutorial-de-pdo EN ESTA PAGINA VIENE MAS EJEMPLOS
		//AQUI NO SE ESTA USANDO EL ORM
		//SE GENERA LA SENTENCIA EN LENGUALE SQL
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