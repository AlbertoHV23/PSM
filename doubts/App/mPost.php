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
	public $usuario_nombre;
	public $usuario_apellidos;
	public $categoria_nombre;
	
	function __construct(){

	}
}
?>