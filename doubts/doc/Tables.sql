CREATE TABLE IF NOT EXISTS tbl_usuario (
     id_usuario INT AUTO_INCREMENT,
     nombre VARCHAR(256) NULL,
     apellidos VARCHAR(256) NULL,
     email VARCHAR(256) NOT NULL,
     contrasena VARCHAR(256) NOT NULL,
     avatar BLOB NULL,
     PRIMARY KEY (id_usuario)
);

CREATE TABLE IF NOT EXISTS tbl_categoria (
     id_categoria INT AUTO_INCREMENT,
     nombre VARCHAR(256) NOT NULL,
     semestre INT NOT NULL,
     PRIMARY KEY (id_categoria)
);

CREATE TABLE IF NOT EXISTS tbl_post (
     id_post INT NOT NULL AUTO_INCREMENT,
     fk_usuario INT NOT NULL,
     fk_categoria INT NOT NULL,
     publicacion TEXT NOT NULL,
     imagen BLOB NULL,
     fecha DATE NOT NULL,
     likes INT DEFAULT 0,
     PRIMARY KEY (id_post),
     FOREIGN KEY (fk_usuario) REFERENCES tbl_usuario(id_usuario),
     FOREIGN KEY (fk_categoria) REFERENCES tbl_categoria(id_categoria)
);

CREATE TABLE IF NOT EXISTS tbl_respuesta (
     id_respuesta INT AUTO_INCREMENT,
     fk_usuario INT NOT NULL,
     fk_post INT NOT NULL,
     respuesta TEXT NOT NULL,
     fecha DATE NOT NULL,
     PRIMARY KEY (id_respuesta),
     FOREIGN KEY (fk_usuario) REFERENCES tbl_usuario(id_usuario),
     FOREIGN KEY (fk_post) REFERENCES tbl_post(id_post)
);