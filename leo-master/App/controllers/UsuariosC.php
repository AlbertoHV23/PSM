<?php
/**
 * Copyight(c) Antonio Ramirez Santander.All rights reserved
 * Copyight(c) Trivialsoft 2020.
 * Hecho en México
 */
namespace App;

class UsuariosC
{
    private $db;
    public function __construct($db)
    {
        $this->db = $db;
    }
    public function get_usuarios($id)
    {
        $orm = new \App\Core\Model($this->db);

        $filtro = array();
        if ($id > 0) {
            $filtro = array("id_usuario" => $id);
        }
        $list = $orm->select($filtro,
            "tbl_usuarios",
            "Usuarios",
            array("id_usuario" => "id_usuario", "nombre" => "nombre", "apellidos" => "apellidos","email" => "email","contraseña" => "contraseña","avatar" => "avatar"),
            "", "", "");

        return iterator_to_array($list);
    }
    public function save_deleteUsuario($data)
    {
        $orm = new \App\Core\Model($this->db);
        return $orm->delete();
    }
    public function save_categorias($data)
    {
        $orm = new \App\Core\Model($this->db);

        if ($data["id_usuario"] > 0) {

            $instances = $this->get_usuarios($data["id_usuario"]);
            $instance = $instances[0];
            $instance->nombre = $data["nombre"];
            $instance->apellidos = $data["apellidos"];
            $instance->email = $data["email"];
            $instance->contrasena = $data["contraseña"];
            $instance->avatar = $data["avatar"];

            return $orm->save($instance, "tbl_usuarios", "id_usuario", array("id_usuario" => "id_usuario", "nombre" => "nombre", "apellidos" => "apellidos","email" => "email","contraseña" => "contraseña","avatar" => "avatar"));
        } else {
            return $orm->save($data, "tbl_usuarios", "id_usuario", array("id_usuario" => "id_usuario", "nombre" => "nombre", "apellidos" => "apellidos","email" => "email","contraseña" => "contraseña","avatar" => "avatar"));
        }
    }
}