<?php

namespace App;

use PDOException;

class Respuesta
{

    private $db;
    function __construct($db)
    {
        $this->db = $db;
    }

    function Respuestas($id)
    {
        $orm = new \App\Core\Model($this->db);

        $filtro = array();
        if ($id > 0) {
            $filtro = array("id_respuesta" => $id);
        }

        $list = $orm->select($filtro,
            "tbl_respuesta",
            "mRespuesta",
            array("id_respuesta" => "id_respuesta",
                "fk_usuario" => "fk_usuario",
                "fk_post" => "fk_post",
                "respuesta" => "respuesta",
                "fecha" => "fecha",
            ),
            "", "", "");
        $items = iterator_to_array($list);

        return $items;
    }

    function Save($data)
    {

        $orm = new \App\Core\Model($this->db);

        if ($data["id_respuesta"] > 0) {

            $instances = $this->Respuestas($data["id_respuesta"]);
            $instance = $instances[0];

            $instance->nombre = $data["fk_usuario"];
            $instance->apellidos = $data["fk_post"];
            $instance->email = $data["respuesta"];
            $instance->contrasena = $data["fecha"];

            return $orm->save($instance
                , "tbl_respuesta"
                , "id_respuesta"
                , array("id_respuesta" => "id_respuesta",
                    "fk_usuario" => "fk_usuario",
                    "fk_post" => "fk_post",
                    "respuesta" => "respuesta",
                    "fecha" => "fecha"));

        } else {

            $data["fecha"] = date("Y-m-d");
            
            return $orm->save($data
                , "tbl_respuesta"
                , "id_respuesta"
                , array("id_respuesta" => "id_respuesta",
                "fk_usuario" => "fk_usuario",
                "fk_post" => "fk_post",
                "respuesta" => "respuesta",
                "fecha" => "fecha"));
        }
    }

    function Delete($data)
    {

        try {
            $sentencia = $this->db->prepare("DELETE FROM tbl_respuesta WHERE id_respuesta = ? ");
            $sentencia->bindParam(1, $data["id_respuesta"]);
            $sentencia->execute();
            return $mensaje = "Eliminado correctamente";
        } catch (PDOException $exc) {
            return $mensaje = "No se pudo eliminar";
        }

        return array("mensaje" => $mensaje);
    }

    function RespuestasCN()
    {
        $respuestas = [];
        try {
            $sentencia = $this->db->prepare("SELECT id_respuesta,fk_usuario,fk_post,respuesta,fecha,
            tbl_usuario.nombre AS 'nombre_usuario'
            FROM tbl_respuesta
            INNER JOIN tbl_usuario on tbl_respuesta.fk_usuario = tbl_usuario.id_usuario");
            $sentencia->execute();
            while ($row = $sentencia->fetch()) {
                $respuesta = new mRespuesta();
                $respuesta->id_respuesta = $row['id_respuesta'];
                $respuesta->fk_usuario = $row['fk_usuario'];
                $respuesta->fk_post = $row['fk_post'];
                $respuesta->respuesta = $row['respuesta'];
                $respuesta->fecha = $row['fecha'];
                $respuesta->nombre_usuario = $row['nombre_usuario'];
                array_push($respuestas, $respuesta);
            }
            return $respuestas;

        } catch (PDOException $exc) {
            return [];
        }

    }

    function RespuestasPorPost($idPost)
    {
        $respuestas = [];
        try {
            $sentencia = $this->db->prepare("SELECT id_respuesta,fk_usuario,fk_post,respuesta,fecha,
            tbl_usuario.nombre AS 'nombre_usuario',
            tbl_usuario.apellidos AS 'apellidos_usuario'
            FROM tbl_respuesta
            INNER JOIN tbl_usuario on tbl_respuesta.fk_usuario = tbl_usuario.id_usuario WHERE fk_post = ?");
            $sentencia->bindParam(1, $idPost['id_post']);
            $sentencia->execute();
            while ($row = $sentencia->fetch()) {
                $respuesta = new mRespuesta();
                $respuesta->id_respuesta = $row['id_respuesta'];
                $respuesta->fk_usuario = $row['fk_usuario'];
                $respuesta->fk_post = $row['fk_post'];
                $respuesta->respuesta = $row['respuesta'];
                $respuesta->fecha = $row['fecha'];
                $respuesta->nombre_usuario = $row['nombre_usuario'].' '.$row['apellidos_usuario'];
                array_push($respuestas, $respuesta);
            }
            return $respuestas;

        } catch (PDOException $exc) {
            return [];
        }
    }

}
