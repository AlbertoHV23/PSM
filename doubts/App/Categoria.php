<?php

namespace App;

class Categoria
{

    private $db;
    public function __construct($db)
    {
        $this->db = $db;
    }

    public function Categorias($id)
    {
        $orm = new \App\Core\Model($this->db);

        $filtro = array();
        if ($id > 0) {
            $filtro = array("id_categoria" => $id);
        }

        $list = $orm->select($filtro,
            "tbl_categoria",
            "mCategoria",
            array("id_categoria" => "id_categoria",
                "nombre" => "nombre",
                "semestre" => "semestre",
            ),
            "", "", "");
        $items = iterator_to_array($list);

        return $items;
    }

}
