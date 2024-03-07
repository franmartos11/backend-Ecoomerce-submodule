USE `vinylSound` ;

-- -----------------------------------------------------
-- Table `vinylSound`.`categories`
-- -----------------------------------------------------
INSERT INTO `categories` (idcategory, name) VALUES (1, 'Vinilos');
INSERT INTO `categories` (idcategory, name) VALUES (2, 'CD´s');
INSERT INTO `categories` (idcategory, name) VALUES (3, 'Instrumentos');
INSERT INTO `categories` (idcategory, name) VALUES (4, 'Estereos');
INSERT INTO `categories` (idcategory, name) VALUES (5, 'Accesorios');
-- -----------------------------------------------------
-- Table `vinylSound`.`subcategories`
-- -----------------------------------------------------
INSERT INTO `subcategories` (idsubcategory,name,idcategory) VALUES (1,"Rock",1);
INSERT INTO `subcategories` (idsubcategory,name,idcategory) VALUES (2,"Pop",2);
INSERT INTO `subcategories` (idsubcategory,name,idcategory) VALUES (3,"Guitarras",3);
INSERT INTO `subcategories` (idsubcategory,name,idcategory) VALUES (4,"Cajas Acusticas",4);
INSERT INTO `subcategories` (idsubcategory,name,idcategory) VALUES (5,"Fundas",5);
-- -----------------------------------------------------
-- Table `vinylSound`.`brands`
-- -----------------------------------------------------
INSERT INTO `brands` (idbrand,name) VALUES (1,"CD´s");
INSERT INTO `brands` (idbrand,name) VALUES (2,"Vinilos");
INSERT INTO `brands` (idbrand,name) VALUES (3,"Valencia");
INSERT INTO `brands` (idbrand,name) VALUES (4,"JBL");
INSERT INTO `brands` (idbrand,name) VALUES (5,"Probags");
-- -----------------------------------------------------
-- Table `vinylSound`.`productsproducts`
-- -----------------------------------------------------
INSERT INTO `products`(idProduct,title,price,description,image,fav,idsubcategory,idbrand) VALUES (1,"Miley Cirus - Plastic Hearts (cd)",18.200,"TRACKS

1 WTF Do I Know
2 Plastic Hearts
3 Angels Like You
4 Prisoner Feat. Dua Lipa
5 Gimme What I Want
6 Night Crawling Feat. Billy Idol
7 Midnight Sky
8 High
9 Hate Me
10 Bad Karma Feat. Joan Jett
11 Never Be Me
12 Golden G String","",0,2,1 );
INSERT INTO `products`(idProduct,title,price,description,image,fav,idsubcategory,idbrand) VALUES (2,"Taylor Swift - 1989 (cd)",15.200,"TRACKS

1 Welcome to New York (Taylor's Version)
2 Blank Space (Taylor's Version)
3 Style (Taylor's Version)
4 Out of the Woods (Taylor's Version)
5 All You Had to Do Was Stay (Taylor's Version)
6 Shake It Off (Taylor's Version)
7 I Wish You Would (Taylor's Version)
8 Bad Blood (Taylor's Version)
9 Wildest Dreams (Taylor's Version)
10 How You Get the Girl (Taylor's Version)

","",0,2,1 );
INSERT INTO `products`(idProduct,title,price,description,image,fav,idsubcategory,idbrand) VALUES (3,"Shakira - El Dorado (cd)",12.200,"TRACKS

1 Me Enamoré - Shakira
2 Nada - Shakira
3 Chantaje - Shakira feat. Maluma
4 When a Woman - Shakira
5 Amarillo - Shakira
6 Perro Fiel - Shakira feat. Nicky Jam
7 Trap - Shakira feat. Maluma
8 Comme moi - Black M feat. Shakira
9 Coconut Tree - Shakira
10 La Bicicleta - Carlos Vives & Shakira
11 Deja vu - Prince Royce & Shakira
12 What We Said - Shakira feat. MAGIC!
13 Toneladas - Shakira

","",0,2,1 );
INSERT INTO `products`(idProduct,title,price,description,image,fav,idsubcategory,idbrand) VALUES (4,"Gustavo Cerati - Siempre es Hoy (2LP)",55.000,"TRACKS

Cosas imposibles
No te creo
Artefacto
Nací para esto
Amo dejarte así
Tu cicatriz en mí
Señales luminosas
Karaoke
Sulky
Casa
Camuflaje
Altar
Torre de marfil
Fantasma
Vivo
Sudestada
Especie
","",0,1,2);

INSERT INTO `products`(idProduct,title,price,description,image,fav,idsubcategory,idbrand) VALUES (6,"Guitarra Clasica Valencia Vc204 Natural",117000,"
Guitarra Clásica Valencia
Tamaño: 4/4
Fabricada en Indonesia.

Una guitarra con gran volumen y claridad. Un instrumento que aporta un amplia gama de tonos para crear diferentes clases de timbre y color en el sonido.

Acabado tapa: Mate
Acabado fondos y aros: Mate
Diapasón: Caoba
Número de trastes: 19
Parte delantera: abeto Sitka.
Parte trasera y laterales: Nato.
Cuello: Palo Rosa y reforzado con tiras de caoba.
Longitud de la escala es de 650 mm y la anchura es de 52 mm
","",0,3,3);
INSERT INTO `products`(idProduct,title,price,description,image,fav,idsubcategory,idbrand) VALUES (7,"Guitarra Clásica Valencia Vc104 Natural",112000,"
La Serie Valencia 100 nos ofrece una guitarra de cuerda de nylon con todas las características, que es lo suficientemente asequible para cualquier guitarrista. Su longitud de la escala del mástil le permite tocar con el estilo clásico; justo lo que usted necesita para desarrollar la técnica adecuada. La serie 104 es la mejor elección para poder iniciarse en el mundo de la guitarra clásica.

Descripción:
Tapa: Linden
Trasera y Laterales: Linden
Mástil: Nato
Diapasón: Arce Ebonizado
Puntos laterales del mástil: Sí
Trastes: 19 trastes, Latón
Puente: Arce ebonizado
Cejuela Puente: Plástico
Clavijero: Niquelado con botones blancos
Color: Natural
","",0,3,3);
INSERT INTO `products`(idProduct,title,price,description,image,fav,idsubcategory,idbrand) VALUES (8,"Guitarra Clásica Valencia Vc404 Sunburst",150000,"
Guitarra clásica Valencia VC404.

Entre sus características encontramos una cubierta de abeto Sitka, trasera y lateralea de Nato, mástil de caoba con diapasón de palisandro y 19 trastes de níquel.
Tamaño 4/4.
Escala de 650 mm x 52 mm.
Color Sunburst.
Herrajes cromados.
","",0,3,3);
INSERT INTO `products`(idProduct,title,price,description,image,fav,idsubcategory,idbrand) VALUES (9,"Caja Acustica Jbl J104bt Par",181728,"
Características
El controlador LF coaxial de 4,5 pulgadas (118 mm) y el controlador HF de cúpula blanda de 0,75 pulgadas (19 mm) ofrecen fidelidad de rango completo
Mezclas de referencia a través de Bluetooth con transmisión Bluetooth 5.0 incorporada
Amplificador de potencia integrado Clase D de 60 vatios; 30 vatios distribuidos a cada altavoz
Optimizado acústicamente para ubicación en el escritorio
Gabinete de ABS moldeado con puerto de baja frecuencia contorneado
Tres conjuntos de entradas permiten la conexión con equipos profesionales, reproductores de música personales y dispositivos de consumo
El monitor host incluye componentes electrónicos para el monitor de extensión, lo que minimiza el cableado y el desorden.
El control de entrada del panel frontal selecciona Bluetooth, Aux, RCA, TRS o combina todas las entradas
Control de volumen en el panel frontal
Conector para auriculares en el panel frontal con función de silencio del altavoz
Fuente de alimentación integrada
","",0,4,4);
INSERT INTO `products`(idProduct,title,price,description,image,fav,idsubcategory,idbrand) VALUES (10,"Caja Acustica Jbl Control 2p Activa Par",288230,"

Características
Transductores de calidad de estudio para precisión y respuesta rica en graves: 80 Hz - 20 Khz
El amplificador de potencia de 35 vatios por canal y los controladores de alta sensibilidad producen una salida excepcional:
115 DB max SPL por par
Las entradas balanceadas XLR, ¼ y RCA no balanceadas permiten la conexión a una variedad de fuentes de señal
El conector para auriculares estéreo permite monitorear usando auriculares
Control de volumen ergonómico de montaje lateral: controla simultáneamente los altavoces izquierdo y derecho
Control de ajuste de alta frecuencia: permite al usuario adaptar la respuesta de HF a los requisitos de la aplicación
Transductores protegidos magnéticamente para usar cerca de monitores CRT y equipos magnéticamente sensibles
Limitador de picos interno y circuito de protección contra sobrecarga: protege el sistema de daños causados ??por una sobrecarga continua
Capacidad de montaje en pared usando el kit de montaje MTC-2P opcional
Los pedestales a presión incluidos optimizan el ángulo de escucha para aplicaciones de escritorio
","",0,4,4);
INSERT INTO `products`(idProduct,title,price,description,image,fav,idsubcategory,idbrand) VALUES (11,"Funda Clasica Probags 600c",9297,"

Si buscas un estuche o funda para tu guitarra o bajo Probag es tu marca. Fabricado con los mejores materiales y siempre buscando el equilibrio perfecto entre la calidad y el precio estas fundas y estuches son lo mejor que puedes buscar
","",0,5,5);
INSERT INTO `products`(idProduct,title,price,description,image,fav,idsubcategory,idbrand) VALUES (12,"Funda Clasica Probags 615ac",29583,"

Funda para guitarra clásica de Probags, cuenta con un acolchada de 15 milímetros para ofrecer la máxima protección a nuestro instrumenta y evitar que sufra cualquier tipo de golpe o accidente.
Cuenta con bolsillos de amplia capacidad
","",0,5,5);
INSERT INTO `products`(idProduct,title,price,description,image,fav,idsubcategory,idbrand) VALUES (13,"Estuche Clasica Probags 150c Semi Rigido",75227,"

Fabricado en foam.
Máxima protección.
Diseño ultra ligero.
Tirantes tipo mochila.
Asa ergonómica.Cuenta con bolsillos de amplia capacidad
","",0,5,5);

select * from products;



