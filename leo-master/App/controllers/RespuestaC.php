<?php
/**
 * Copyight(c) Antonio Ramirez Santander.All rights reserved
 * Copyight(c) Trivialsoft 2020.
 * Hecho en México
 */
namespace App;

class RespuestaC
{
    private $db;
    public function __construct($db)
    {
        $this->db = $db;
    }
    public function get_respuesta($id)
    {
        $orm = new \App\Core\Model($this->db);

        $filtro = array();
        if ($id > 0) {
            $filtro = array("id_comentarios" => $id);
        }
        $list = $orm->select($filtro,
            "tbl_respuesta",
            "Respuesta",
            array("id_comentarios" => "id_comentarios", "fk_usuario" => "fk_usuario", "fecha" => "fecha"),
            "", "", "");

        return iterator_to_array($list);
    }
    public function save_deleteRespuesta($data)
    {
        $orm = new \App\Core\Model($this->db);
        return $orm->delete();
    }
    public function save_respuesta($data)
    {
        $orm = new \App\Core\Model($this->db);

        if ($data["id_usuario"] > 0) {

            $instances = $this->get_respuesta($data["id_comentarios"]);
            $instance = $instances[0];
            $instance->nombre = $data["fk_usuario"];
            $instance->apellidos = $data["respuesta"];
            $instance->email = $data["fecha"];

            return $orm->save($instance, "tbl_respuesta", "id_comentarios", array("id_comentarios" => "id_comentarios", "fk_usuario" => "fk_usuario", "fecha" => "fecha"));
        } else {
            return $orm->save($data, "tbl_respuesta", "id_comentarios", array("id_comentarios" => "id_comentarios", "fk_usuario" => "fk_usuario", "fecha" => "fecha"));
        }
    }
}