INSERT INTO `tbl_categoria`( `nombre`, `semestre`) VALUES ("Metodología de la programación", 1);
INSERT INTO `tbl_categoria`( `nombre`, `semestre`) VALUES ("Programación básica", 2);
INSERT INTO `tbl_categoria`( `nombre`, `semestre`) VALUES ("Programación Avanzada", 3);
INSERT INTO `tbl_categoria`( `nombre`, `semestre`) VALUES ("Programación Orientada a Objetos", 4);
INSERT INTO `tbl_categoria`( `nombre`, `semestre`) VALUES ("Gráficas computacionales I", 5);
INSERT INTO `tbl_categoria`( `nombre`, `semestre`) VALUES ("Modelos de administración de datos", 5);
INSERT INTO `tbl_categoria`( `nombre`, `semestre`) VALUES ("Administración de alto volumen de datos", 5);
INSERT INTO `tbl_categoria`( `nombre`, `semestre`) VALUES ("Gráficas computacionales II", 6);
INSERT INTO `tbl_categoria`( `nombre`, `semestre`) VALUES ("Programación web I", 6);
INSERT INTO `tbl_categoria`( `nombre`, `semestre`) VALUES ("Interface y experiencia de usuarios en web", 6);
INSERT INTO `tbl_categoria`( `nombre`, `semestre`) VALUES ("Gráficas computacionales en web", 7);
INSERT INTO `tbl_categoria`( `nombre`, `semestre`) VALUES ("Base de datos multimedia", 7);
INSERT INTO `tbl_categoria`( `nombre`, `semestre`) VALUES ("Programación orientada a la internet", 7);
INSERT INTO `tbl_categoria`( `nombre`, `semestre`) VALUES ("Programación web de capa intermedia", 7);
INSERT INTO `tbl_categoria`( `nombre`, `semestre`) VALUES ("Programación de sistemas móviles", 7);
INSERT INTO `tbl_categoria`( `nombre`, `semestre`) VALUES ("Programación web II", 8);
INSERT INTO `tbl_categoria`( `nombre`, `semestre`) VALUES ("Procesamiento de imágenes", 8);

INSERT INTO `tbl_post`(`id_post`, `fk_usuario`, `fk_categoria`, `publicacion`, `imagen`, `fecha`, `likes`) VALUES (null,1,1,"Mi primer post otravez",null,null,null);