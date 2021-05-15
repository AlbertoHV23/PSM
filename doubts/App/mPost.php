<?php 
namespace App;
class mPost{
	public $id_post;
	public $fk_usuario;
	public $fk_categoria;
	public $publicacion;
	public $imagen;
	public $fecha;
    public $likes;
	
	function __construct($db){

	}
}
?>