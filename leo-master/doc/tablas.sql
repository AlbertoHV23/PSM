CREATE TABLE tbl_usuarios (
     id_usuario INT NOT NULL AUTO_INCREMENT,
     nombre VARCHAR(256) NOT NULL,
     apellidos VARCHAR(256) NOT NULL,
     email VARCHAR(100) NOT NULL,
     contraseña VARCHAR(50) NOT NULL,
     avatar VARCHAR(256) NOT NULL,
     PRIMARY KEY (id_usuario)
);

CREATE TABLE tbl_categorias (
     id_categoria INT NOT NULL AUTO_INCREMENT,
     nombre VARCHAR(256) NOT NULL,
     semestre INT NOT NULL,
     PRIMARY KEY (id_categoria)
);

CREATE TABLE tbl_publicaciones (
     id_post INT NOT NULL AUTO_INCREMENT,
     fk_usuario INT NOT NULL,
     fk_categoria INT NOT NULL,
     publicacion VARCHAR(256) NOT NULL,
     imagen VARCHAR(256) NOT NULL,
     fecha DATE NOT NULL,
     PRIMARY KEY (id_post),
     FOREIGN KEY (fk_usuario) REFERENCES tbl_usuarios(id_usuario),
     FOREIGN KEY (fk_categoria) REFERENCES tbl_categorias(id_categoria)
);

