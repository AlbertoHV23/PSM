<?php

namespace App;

use PDOException;

class Post
{

    private $db;
    function __construct($db)
    {
        $this->db = $db;
    }

    function Posts($id)
    {
        $orm = new \App\Core\Model($this->db);

        $filtro = array();
        if ($id > 0) {
            $filtro = array("id_post" => $id);
        }

        $list = $orm->select($filtro,
            "tbl_post",
            "mPost",
            array("id_post" => "id_post",
                "fk_usuario" => "fk_usuario",
                "fk_categoria" => "fk_categoria",
                "publicacion" => "publicacion",
                "imagen" => "imagen",
                "fecha" => "fecha",
                "likes" => "likes",
            ),
            "", "", "");
        $items = iterator_to_array($list);

        foreach ($items as $item) {

            $item->imagen = "data:image/png;base64," . base64_encode($item->imagen);

        }
        return $items;
    }

    function Save($data)
    {
        $orm = new \App\Core\Model($this->db);

        if ($data["id_post"] > 0) {

            $instances = $this->Posts($data["id_post"]);
            $instance = $instances[0];

            $instance->fk_usuario = $data["fk_usuario"];
            $instance->fk_categoria = $data["fk_categoria"];
            $instance->publicacion = $data["publicacion"];
            $instance->imagen = $data["imagen"];
            $instance->fecha = $data["fecha"];
            $instance->likes = $data["likes"];

            if (isset($instance->imagen)) {
                $datab = $instance->imagen;
                list($type, $datab) = explode(';', $datab);
                list(, $datab) = explode(',', $datab);
                //DECODIFICA
                $instance->imagen = base64_decode($datab);
            }

            return $orm->save($instance,
                "tbl_post",
                "id_post",
                array("id_post" => "id_post",
                    "fk_usuario" => "fk_usuario",
                    "fk_categoria" => "fk_categoria",
                    "publicacion" => "publicacion",
                    "imagen" => "imagen",
                    "fecha" => "fecha",
                    "likes" => "likes"));

        } else {

            if (isset($data["imagen"])) {
                $datab = $data["imagen"];
                list($type, $datab) = explode(';', $datab);
                list(, $datab) = explode(',', $datab);
                $data["imagen"] = base64_decode($datab);
            }

            $data["fecha"] = date("Y-m-d");

            return $orm->save($data
                , "tbl_post"
                , "id_post"
                , array("id_post" => "id_post",
                    "fk_usuario" => "fk_usuario",
                    "fk_categoria" => "fk_categoria",
                    "publicacion" => "publicacion",
                    "imagen" => "imagen",
                    "fecha" =>"fecha",
                    "likes" => "likes"));
        }
    }

    function Delete($data)
    {
        try {
            $sentencia = $this->db->prepare("DELETE FROM tbl_post WHERE id_post = ? ");
            $sentencia->bindParam(1, $data["id_post"]);
            $sentencia->execute();
            return $mensaje = "Eliminado correctamente";
        } catch (PDOException $exc) {
            return $mensaje = "No se pudo eliminar";
        }

        return array("mensaje" => $mensaje);
    }

}
?>