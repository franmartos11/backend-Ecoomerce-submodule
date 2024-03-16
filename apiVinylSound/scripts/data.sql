-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Table `vinylsound`.`brands`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `brands` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `url` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `UK_oce3937d2f4mpfqrycbr0l93m` (`name` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `vinylsound`.`categories`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `categories` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(150) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `UK_t8o6pivur7nn124jehx7cygw5` (`name` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `vinylsound`.`subcategories`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `subcategories` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(150) NOT NULL,
  `id_category` BIGINT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `UK_adqq9mi616mxeynhid14e4j4q` (`name` ASC) VISIBLE,
  INDEX `FKmfbmih0rouycr6k0987u77kei` (`id_category` ASC) VISIBLE,
  CONSTRAINT `FKmfbmih0rouycr6k0987u77kei`
    FOREIGN KEY (`id_category`)
    REFERENCES `categories` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `vinylsound`.`products`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `products` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `description` VARCHAR(255) NOT NULL,
  `price` FLOAT NOT NULL,
  `title` VARCHAR(150) NOT NULL,
  `quantity_sells` INT NOT NULL,
  `id_brand` BIGINT NULL DEFAULT NULL,
  `id_subcategory` BIGINT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `UK_8xtpej5iy2w4cte2trlvrlayy` (`title` ASC) VISIBLE,
  INDEX `FK2bhwoop8hfnjmvqv8qa0dpbn0` (`id_brand` ASC) VISIBLE,
  INDEX `FK8j62d7p0cpidmbfee53dbkat9` (`id_subcategory` ASC) VISIBLE,
  CONSTRAINT `FK2bhwoop8hfnjmvqv8qa0dpbn0`
    FOREIGN KEY (`id_brand`)
    REFERENCES `brands` (`id`),
  CONSTRAINT `FK8j62d7p0cpidmbfee53dbkat9`
    FOREIGN KEY (`id_subcategory`)
    REFERENCES `subcategories` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `vinylsound`.`images`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `images` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `url` VARCHAR(255) NOT NULL,
  `id_product` BIGINT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `FK5ijxcjmodo87whc7u87mqnui1` (`id_product` ASC) VISIBLE,
  CONSTRAINT `FK5ijxcjmodo87whc7u87mqnui1`
    FOREIGN KEY (`id_product`)
    REFERENCES `products` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;


-- -----------------------------------------------------
-- Table `vinylSound`.`categories`
-- -----------------------------------------------------
INSERT INTO `categories` (name) VALUES ('Vinilos');
INSERT INTO `categories` (name) VALUES ('CD´s');
INSERT INTO `categories` (name) VALUES ('Instrumentos');
INSERT INTO `categories` (name) VALUES ('Estereos');
INSERT INTO `categories` (name) VALUES ('Accesorios');
-- -----------------------------------------------------
-- Table `vinylSound`.`subcategories`
-- -----------------------------------------------------
INSERT INTO subcategories (name, id_category) VALUES ('Rock', 1);
INSERT INTO subcategories (name, id_category) VALUES ('Reggae', 1);
INSERT INTO subcategories (name, id_category) VALUES ('Jazz', 1);
INSERT INTO subcategories (name, id_category) VALUES ('Pop', 2);
INSERT INTO subcategories (name, id_category) VALUES ('Urbano', 2);
INSERT INTO subcategories (name, id_category) VALUES ('Tango', 2);
INSERT INTO subcategories (name, id_category) VALUES ('Guitarras', 3);
INSERT INTO subcategories (name, id_category) VALUES ('Baterias', 3);
INSERT INTO subcategories (name, id_category) VALUES ('Teclados', 3);
INSERT INTO subcategories (name, id_category) VALUES ('Estereos Vintage', 4);
INSERT INTO subcategories (name, id_category) VALUES ('Estereos Modernos', 4);
INSERT INTO subcategories (name, id_category) VALUES ('Altavoces', 5);
INSERT INTO subcategories (name, id_category) VALUES ('Auriculares', 5);
INSERT INTO subcategories (name, id_category) VALUES ('Cables y Conectores', 5);
-- -----------------------------------------------------
-- Table `vinylSound`.`brand
-- -----------------------------------------------------
INSERT INTO `brands` (name,url) VALUES ("RCA Records","https://res.cloudinary.com/dnb5fwh6o/image/upload/v1709931995/descarga_ukdhkg.png");
INSERT INTO `brands` (name,url) VALUES ("Universal Music Group","https://res.cloudinary.com/dnb5fwh6o/image/upload/v1709932488/descarga_takmzc.png");
INSERT INTO `brands` (name,url) VALUES ("SONY Music","https://res.cloudinary.com/dnb5fwh6o/image/upload/v1709933068/descarga_daghaw.png");
INSERT INTO `brands` (name,url) VALUES ("Ariola Records","https://res.cloudinary.com/dnb5fwh6o/image/upload/v1709933576/Ariola-Logo.svg_jcutcx.png");
INSERT INTO `brands` (name,url) VALUES ("Valencia","https://res.cloudinary.com/dnb5fwh6o/image/upload/v1709933908/images_2_bfpt8c.jpg");
-- -----------------------------------------------------
-- Table `vinylSound`.`products`
-- -----------------------------------------------------
INSERT INTO `products`(title,price,description,quantity_sells,id_subcategory,id_brand) VALUES (
"Miley Cirus - Plastic Hearts",18200,"TRACKS 1 WTF Do I Know 2 Plastic Hearts 3 Angels Like You 4 Prisoner Feat. Dua Lipa 5 Gimme What I Want 6 Night Crawling Feat. Billy Idol 7 Midnight Sky 8 High 9 Hate Me 10 Bad Karma Feat. Joan Jett 11 Never Be Me 12 Golden G String",50,4,1);

INSERT INTO `products`(title,price,description,quantity_sells,id_subcategory,id_brand) VALUES (
"Taylor Swift - 1989",15200,"TRACKS 1 Welcome to New York 2 Blank Space 3 Style 4 Out of the Woods 5 All You Had to Do Was Stay 6 Shake It Off 7 I Wish You Would 8 Bad Blood 9 Wildest Dreams 10 How You Get the Girl 11 This Love 12 I Know Places 13 Clean",70,4,2);

INSERT INTO `products`(title,price,description,quantity_sells,id_subcategory,id_brand) VALUES ("Shakira - El Dorado",12200,"TRACKS 1 Me Enamoré 2 Nada 3 Chantaje 4 When a Woman 5 Amarillo 6 Perro Fiel 7 Trap - Shakira 8 Comme moi 9 Coconut Tree 10 La Bicicleta 11 Deja vu 12 What We Said 13 Toneladas",20,4,3);

INSERT INTO `products`(title,price,description,quantity_sells,id_subcategory,id_brand) VALUES ("Gustavo Cerati - Bocanada",55000,"TRACKS 1 Tabú 2 Engaña 3 Bocanada 4 Puente 5 Río Babel 6 Beautiful 7 Perdonar es divino 8 Verbo Carne 9 Raíz 10 Y el humo está en foco... 11 Paseo inmoral 12 Aquí & ahora (Los primeros tres minutos) 13 Aquí & ahora (Y después) 14 Alma 15 Balsa",200,1,4);

INSERT INTO `products`(title,price,description,quantity_sells,id_subcategory,id_brand) VALUES ("Guitarra Clasica Valencia Vc204 Natural",11700," Guitarra Clásica Valencia Tamaño: 4/4 Fabricada en Indonesia. Una guitarra con gran volumen y claridad. Un instrumento que aporta un amplia gama de tonos para crear diferentes clases de timbre y color en el sonido.",15,7,5);

INSERT INTO `products`(title,price,description,quantity_sells,id_subcategory,id_brand) VALUES ("Guitarra Clásica Valencia Vc104 Natural",11200,"La Serie Valencia 100 nos ofrece una guitarra de cuerda de nylon con todas las características, que es lo suficientemente asequible para cualquier guitarrista. Su longitud de la escala del mástil le permite tocar con el estilo clásico.",5,7,5);

-- -----------------------------------------------------
-- Table `vinylSound`.`images`
-- -----------------------------------------------------
INSERT INTO `images` (name,url,id_product) VALUES ('Miley Cyrus - Plastic Hearts','https://res.cloudinary.com/dnb5fwh6o/image/upload/v1709932276/descarga_vtmvc8.jpg',1);
INSERT INTO `images` (name,url,id_product) VALUES ('Miley Cyrus - Plastic Hearts','https://res.cloudinary.com/dnb5fwh6o/image/upload/v1709932271/1605511209930_xuv8vo.jpg',1);
INSERT INTO `images` (name,url,id_product) VALUES ('Miley Cyrus - Plastic Hearts','https://res.cloudinary.com/dnb5fwh6o/image/upload/v1709932262/miley-cyrus-revelo-el-tracklist-de-nuevo-disco-plastic-hearts_jooz5d.jpg',1);
INSERT INTO `images` (name,url,id_product) VALUES ('Taylor Swift - 1989','https://res.cloudinary.com/dnb5fwh6o/image/upload/v1709932857/images_alvfeo.jpg',2);
INSERT INTO `images` (name,url,id_product) VALUES ('Taylor Swift - 1989','https://res.cloudinary.com/dnb5fwh6o/image/upload/v1709932859/Captura_janpim.png',2);
INSERT INTO `images` (name,url,id_product) VALUES ('Shakira - El Dorado','https://res.cloudinary.com/dnb5fwh6o/image/upload/v1709933130/descarga_zsse00.jpg',3);
INSERT INTO `images` (name,url,id_product) VALUES ('Shakira - El Dorado','https://res.cloudinary.com/dnb5fwh6o/image/upload/v1709933158/shakira___el_dorado_by_flavs9701_dbaggb6-fullview_n7qz9m.jpg',3);
INSERT INTO `images` (name,url,id_product) VALUES ('Shakira - El Dorado','https://res.cloudinary.com/dnb5fwh6o/image/upload/v1709933193/images_1_r0dpox.jpg',3);
INSERT INTO `images` (name,url,id_product) VALUES ('Gustavo Cerati - Bocanada','https://res.cloudinary.com/dnb5fwh6o/image/upload/v1709933681/descarga_1_zrugpk.jpg',4);
INSERT INTO `images` (name,url,id_product) VALUES ('Gustavo Cerati - Bocanada','https://res.cloudinary.com/dnb5fwh6o/image/upload/v1709933307/gustavo-cerati-bocanada_p9h7db.jpg',4);
INSERT INTO `images` (name,url,id_product) VALUES ('Gustavo Cerati - Bocanada','https://res.cloudinary.com/dnb5fwh6o/image/upload/v1709933573/95909e314f3d1970f0a592700bf5f205.425x425x1_qegvl2.jpg',4);
INSERT INTO `images` (name,url,id_product) VALUES ('Gustavo Cerati - Bocanada','https://res.cloudinary.com/dnb5fwh6o/image/upload/v1709933277/2c4b84f6b92a22d870c46d045d9e7de4_aekcma.jpg',4);
INSERT INTO `images` (name,url,id_product) VALUES ('Guitarra Clasica Valencia Vc204 Natural','https://res.cloudinary.com/dnb5fwh6o/image/upload/v1709934001/images_4_sovnaw.jpg',5);
INSERT INTO `images` (name,url,id_product) VALUES ('Guitarra Clasica Valencia Vc204 Natural','https://res.cloudinary.com/dnb5fwh6o/image/upload/v1709933986/images_3_hkxzvj.jpg',5);
INSERT INTO `images` (name,url,id_product) VALUES ('Guitarra Clasica Valencia Vc204 Natural','https://res.cloudinary.com/dnb5fwh6o/image/upload/v1709933983/guitarra-clasica-valencia-vc204_kicugm.jpg',5);
INSERT INTO `images` (name,url,id_product) VALUES ('Guitarra Clásica Valencia Vc104 Natural','https://res.cloudinary.com/dnb5fwh6o/image/upload/v1709934170/descarga_2_gre8tn.jpg',6);
INSERT INTO `images` (name,url,id_product) VALUES ('Guitarra Clásica Valencia Vc104 Natural','https://res.cloudinary.com/dnb5fwh6o/image/upload/v1709934195/11613_small_xfla6z.jpg',6);