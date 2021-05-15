<?php
    /*
	* Copyright(c)Antonio Ramirez Santander. All rights reserved.
	* Copyright(c)TrivialSoft 2020.
	*/
namespace App;
class Album {

	private $db;
	function __construct($db){
		$this->db = $db;
	}
	/**
	* devuelve el listado de Albums
	*/
	function Albums($id){
		$orm = new \App\Core\Model($this->db);

       $filtro = array();
	   if($id > 0){
		  $filtro = array("intID"=>$id); //PARAMATROS PARA USAR PARA EL FILTRO EJEMPLO: array("intID"=>$id, "intIdGenre"=>$idGenre)
	   }
	   //Parametros Select
	   //FILTER ARRAY PONER CADA UNO DE LOS PARAMATEROS
	   //NOMBRE TABLA
	   //NOMBRE MODELO
	   //ARRAY DE LOS PARAMETROS A SELECCIONAR
	   //STRING ORDER
	   //STRING SELECTION NO SE USA  PONER ""
	   //STRING PAGE NO SE USA PORNER ""
		$list = $orm->select($filtro,
							"Albums",
							"mAlbum",
							array(	"intID"=>"intID",
                                    "strTitle"=>"strTitle",
                                    "strDescription"=>"strDescription",
									"intIdImage"=>"intIdImage",
									"intIdGenre"=>"intIdGenre",
									"imgArray"=>"imgArray"
							),
							"","","");
		$items = iterator_to_array ($list);
		/*se convierte en base64 el contenido antes de retornar el objeto*/
		foreach($items as $item){
			
			$item->imgArray = "data:image/png;base64," . base64_encode($item->imgArray);
		
		}
		return  $items;
	}
	/**
	* guarda o actualiza un archivo
	*/
	function Save($data){
		//FUNCIONA COMO INSERTAR Y UPDATE
		$orm = new \App\Core\Model($this->db);

		if($data["intID"]>0){ //SI EL ID ES MAYOR QUE CERO ENTONCES ES UN UPDATE

			/*recuperar la instancia*/
			$instances = $this->Albums( $data["intID"] );
            $instance = $instances[0];
            
			/*se sobreescribir las propiedades*/
            $instance->strTitle = $data["strTitle"];
            $instance->strDescription = $data["strDescription"];
			$instance->intIdImage =  $data["intIdImage"];
			$instance->intIdGenre =  $data["intIdGenre"];
			//RECIBE LA IMAGEN CODIFICADA EN BASE64	
            $instance->imgArray = $data["imgArray"];
            
			/*se convierte a binario antes de guardar la propiedad "contenido"*/
			if(isset($instance->imagen) ){
				$datab = $instance->imgArray;
				list($type, $datab) = explode(';', $datab);
				list(, $datab)      = explode(',', $datab);
				//DECODIFICA 
				$instance->imgArray = base64_decode($datab);
			}

			//SAVE O UPDATE
			//INSTANCIA ES EL OBJETO CON LA INFORMACION A GUARDAR O ACTUALIZAR
			//NOMBRE DE LA TABLA
			//NOMBRE DE LA COLUMNA ID DE LA TABLA
			//ARRAY CON LAS COLUMNAS DE LA TABLA
			return $orm->save($instance 
							,"Albums"
							,"intID"
							, array("intID"=>"intID",
									"strTitle"=>"strTitle",
									"strDescription"=>"strDescription",
									"intIdImage"=>"intIdImage",
									"intIdGenre"=>"intIdGenre",
									"imgArray"=>"imgArray"));
		
		}else{

			//EL ID ES CERO ENTONCES ES UN INSERT

			if(isset($data["imgArray"]) ){
			/*se convierte a binario antes de guardar la propiedad "contenido"*/
			$datab = $data["imgArray"];
				list($type, $datab) = explode(';', $datab);
				list(, $datab)      = explode(',', $datab);
				$data["imgArray"] = base64_decode($datab);
			}

			return $orm->save($data 
			,"Albums"
			,"intID"
			, array("intID"=>"intID",
					"strTitle"=>"strTitle",
					"strDescription"=>"strDescription",
					"intIdImage"=>"intIdImage",
					"intIdGenre"=>"intIdGenre",
					"imgArray"=>"imgArray"));
		}
	}

	function Delete($data){
		//Referencia https://diego.com.es/tutorial-de-pdo EN ESTA PAGINA VIENE MAS EJEMPLOS
		//AQUI NO SE ESTA USANDO EL ORM
		//SE GENERA LA SENTENCIA EN LENGUALE SQL
		$sentencia =  $this->db->prepare("DELETE FROM Albums WHERE intID = ? " );
		$sentencia->bindParam(1,$data["intID"]);
		$sentencia->execute();

		return array(0=>"OK");
	}

}
?>