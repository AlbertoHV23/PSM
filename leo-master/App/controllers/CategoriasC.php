<?php
/**
 * Copyight(c) Antonio Ramirez Santander.All rights reserved
 * Copyight(c) Trivialsoft 2020.
 * Hecho en México
 */
namespace App;

class CategoriasC
{
    private $db;
    public function __construct($db)
    {
        $this->db = $db;
    }
    public function get_categorias($id)
    {
        $orm = new \App\Core\Model($this->db);

        $filtro = array();
        if ($id > 0) {
            $filtro = array("id_categoria" => $id);
        }
        $list = $orm->select($filtro,
            "tbl_categorias",
            "Categorias",
            array("id_categoria" => "id_categoria", "nombre" => "nombre", "semestre" => "semestre"),
            "", "", "");

        return iterator_to_array($list);
    }
    public function save_deleteCategorias($data)
    {
        $orm = new \App\Core\Model($this->db);
        return $orm->delete();
    }
    public function save_categorias($data)
    {
        $orm = new \App\Core\Model($this->db);

        if ($data["id_categoria"] > 0) {

            $instances = $this->get_categorias($data["id_categoria"]);
            $instance = $instances[0];
            $instance->nombre = $data["nombre"];
            $instance->semestre = $data["semestre"];

            return $orm->save($instance, "tbl_categorias", "id_categoria", array("id_categoria" => "id_categoria", "nombre" => "nombre", "semestre" => "semestre"));
        } else {
            return $orm->save($data, "tbl_categorias", "id_categoria", array("id_categoria" => "id_categoria", "nombre" => "nombre", "semestre" => "semestre"));
        }
    }
}
