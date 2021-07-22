

CREATE TABLE `db`.`productos` (
  `idproductos` INT NOT NULL AUTO_INCREMENT,
  `codigo` VARCHAR(45) NULL,
  `seccion` VARCHAR(45) NULL,
  `nombre_articulo` VARCHAR(45) NULL,
  `precio` DECIMAL NULL,
  `fecha` DATE NULL,
  PRIMARY KEY (`idproductos`),
  UNIQUE INDEX `idproductos_UNIQUE` (`idproductos` ASC));

INSERT INTO db.productos (`codigo`, `seccion`,`nombre_articulo`,`precio`,`fecha`) VALUES ('AAA','ahorro','cuenta ahorro',0,NOW());
INSERT INTO db.productos (`codigo`, `seccion`,`nombre_articulo`,`precio`,`fecha`) VALUES ('AAA','ahorro','cuenta nómina',0,NOW());
INSERT INTO db.productos (`codigo`, `seccion`,`nombre_articulo`,`precio`,`fecha`) VALUES ('AAA','credito','prestamo personal',10,NOW());
INSERT INTO db.productos (`codigo`, `seccion`,`nombre_articulo`,`precio`,`fecha`) VALUES ('AAA','credito','hipoteca joven',2,NOW());
INSERT INTO db.productos (`codigo`, `seccion`,`nombre_articulo`,`precio`,`fecha`) VALUES ('AAA','ahorro','plan de pensiones',0,NOW());
INSERT INTO db.productos (`codigo`, `seccion`,`nombre_articulo`,`precio`,`fecha`) VALUES ('AAA','inversión','fondos indexados',0,NOW());


SELECT * FROM db.productos;
