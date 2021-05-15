<?php

namespace App;

use PDOException;

class Post
{

    private $db;
    public function __construct($db)
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
        /*se convierte en base64 el contenido antes de retornar el objeto*/
        foreach ($items as $item) {

            $item->imagen = "data:image/png;base64," . base64_encode($item->imagen);

        }
        return $items;
    }
    /**
     * guarda o actualiza un archivo
     */
    function Save($data)
    {
        //FUNCIONA COMO INSERTAR Y UPDATE
        $orm = new \App\Core\Model($this->db);

        if ($data["id_post"] > 0) { //SI EL ID ES MAYOR QUE CERO ENTONCES ES UN UPDATE

            /*recuperar la instancia*/
            $instances = $this->Posts($data["id_post"]);
            $instance = $instances[0];

            /*se sobreescribe las propiedades*/
            $instance->fk_usuario = $data["fk_usuario"];
            $instance->fk_categoria = $data["fk_categoria"];
            $instance->publicacion = $data["publicacion"];
            //RECIBE LA IMAGEN CODIFICADA EN BASE64
            $instance->imagen = $data["imagen"];
            $instance->fecha = $data["fecha"];
            $instance->likes = $data["likes"];

            /*se convierte a binario antes de guardar la propiedad "contenido"*/
            if (isset($instance->imagen)) {
                $datab = $instance->imagen;
                list($type, $datab) = explode(';', $datab);
                list(, $datab) = explode(',', $datab);
                //DECODIFICA
                $instance->imagen = base64_decode($datab);
            }

            //SAVE O UPDATE
            //INSTANCIA ES EL OBJETO CON LA INFORMACION A GUARDAR O ACTUALIZAR
            //NOMBRE DE LA TABLA
            //NOMBRE DE LA COLUMNA ID DE LA TABLA
            //ARRAY CON LAS COLUMNAS DE LA TABLA
            return $orm->save($instance
                , "tbl_post"
                , "id_post"
                , array("id_post" => "id_post",
                    "fk_usuario" => "fk_usuario",
                    "fk_categoria" => "fk_categoria",
                    "publicacion" => "publicacion",
                    "imagen" => "imagen",
                    "fecha" => $orm->tsCurrentTime(),
                    "likes" => "likes"));

        } else {

            //EL ID ES CERO ENTONCES ES UN INSERT

            if (isset($data["imagen"])) {
                /*se convierte a binario antes de guardar la propiedad "contenido"*/
                $datab = $data["imagen"];
                list($type, $datab) = explode(';', $datab);
                list(, $datab) = explode(',', $datab);
                $data["imagen"] = base64_decode($datab);
            }

            return $orm->save($data
                , "tbl_post"
                , "id_post"
                , array("id_post" => "id_post",
                    "fk_usuario" => "fk_usuario",
                    "fk_categoria" => "fk_categoria",
                    "publicacion" => "publicacion",
                    "imagen" => "imagen",
                    "fecha" => $orm->tsCurrentTime(),
                    "likes" => "likes"));
        }
    }

    function Delete($data)
    {
        //Referencia https://diego.com.es/tutorial-de-pdo EN ESTA PAGINA VIENE MAS EJEMPLOS
        //AQUI NO SE ESTA USANDO EL ORM
        //SE GENERA LA SENTENCIA EN LENGUALE SQL
        try {
            $sentencia = $this->db->prepare("DELETE FROM tbl_post WHERE id_usuario = ? ");
            $sentencia->bindParam(1, $data["id_post"]);
            $sentencia->execute();
            return $mensaje = "Eliminado correctamente";
        } catch (PDOException $exc) {
            return $mensaje = "No se pudo eliminar";
        }

        return array("mensaje" => $mensaje);
    }

}
