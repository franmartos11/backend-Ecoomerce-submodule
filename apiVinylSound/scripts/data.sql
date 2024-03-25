-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';
/*CREATE DATABASE catalog;
use catalog;*/
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
  `description` VARCHAR(800) NOT NULL,
  `price` FLOAT NOT NULL,
  `quantity_sells` BIGINT NULL DEFAULT NULL,
  `title` VARCHAR(150) NOT NULL,
  `id_brand` BIGINT NULL DEFAULT NULL,
  `id_subcategory` BIGINT NULL DEFAULT NULL,
   `stock` INT NOT NULL,
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
INSERT INTO `categories` (name) VALUES ('CDs');
INSERT INTO `categories` (name) VALUES ('Instrumentos');
INSERT INTO `categories` (name) VALUES ('Estereos');
INSERT INTO `categories` (name) VALUES ('Accesorios');
-- -----------------------------------------------------
-- Table `vinylSound`.`subcategories`
-- -----------------------------------------------------
INSERT INTO subcategories (name, id_category) VALUES ('Rock', 1);
INSERT INTO subcategories (name, id_category) VALUES ('Jazz', 1);
INSERT INTO subcategories (name, id_category) VALUES ('Blues', 1);
INSERT INTO subcategories (name, id_category) VALUES ('Pop', 1);
INSERT INTO subcategories (name, id_category) VALUES ('Reggae', 2);
INSERT INTO subcategories (name, id_category) VALUES ('Tech House', 2);
INSERT INTO subcategories (name, id_category) VALUES ('Hip-Hop/Rap', 2);
INSERT INTO subcategories (name, id_category) VALUES ('Indie', 2);
INSERT INTO subcategories (name, id_category) VALUES ('Guitarras & Bajos', 3);
INSERT INTO subcategories (name, id_category) VALUES ('Teclados', 3);
INSERT INTO subcategories (name, id_category) VALUES ('Vientos', 3);
INSERT INTO subcategories (name, id_category) VALUES ('Tambores', 3);
INSERT INTO subcategories (name, id_category) VALUES ('Altavoces', 4);
INSERT INTO subcategories (name, id_category) VALUES ('Auriculares', 4);
INSERT INTO subcategories (name, id_category) VALUES ('Radios', 4);
INSERT INTO subcategories (name, id_category) VALUES ('Cables de audio', 5);
INSERT INTO subcategories (name, id_category) VALUES ('Fundas', 5);
INSERT INTO subcategories (name, id_category) VALUES ('Soportes', 5);
-- -----------------------------------------------------
-- Table `vinylSound`.`brand
-- -----------------------------------------------------
INSERT INTO `brands` (name,url) VALUES ("Sony Music Entertainment","https://imgs-integrador2.s3.us-east-2.amazonaws.com/sonyMusicEnt.png");
INSERT INTO `brands` (name,url) VALUES ("Universal Music Group","https://imgs-integrador2.s3.us-east-2.amazonaws.com/universal.png");
INSERT INTO `brands` (name,url) VALUES ("Warner Music Group","https://imgs-integrador2.s3.us-east-2.amazonaws.com/warnerMusic.png");
INSERT INTO `brands` (name,url) VALUES ("Verbatim","https://imgs-integrador2.s3.us-east-2.amazonaws.com/verbatim.png");
INSERT INTO `brands` (name,url) VALUES ("Sony","https://imgs-integrador2.s3.us-east-2.amazonaws.com/sony.png");
INSERT INTO `brands` (name,url) VALUES ("Memorex","https://imgs-integrador2.s3.us-east-2.amazonaws.com/memorex.png");
INSERT INTO `brands` (name,url) VALUES ("Fender","https://imgs-integrador2.s3.us-east-2.amazonaws.com/fender.png");
INSERT INTO `brands` (name,url) VALUES ("Gibson","https://imgs-integrador2.s3.us-east-2.amazonaws.com/gibson.png");
INSERT INTO `brands` (name,url) VALUES ("Yamaha","https://imgs-integrador2.s3.us-east-2.amazonaws.com/yamaha.png");
INSERT INTO `brands` (name,url) VALUES ("Casio","https://imgs-integrador2.s3.us-east-2.amazonaws.com/casio.png");
INSERT INTO `brands` (name,url) VALUES ("Pearl","https://imgs-integrador2.s3.us-east-2.amazonaws.com/pearl.png");
INSERT INTO `brands` (name,url) VALUES ("Klipsch","https://imgs-integrador2.s3.us-east-2.amazonaws.com/klipsch.png");
INSERT INTO `brands` (name,url) VALUES ("Gator","null");
INSERT INTO `brands` (name,url) VALUES ("K&M","https://imgs-integrador2.s3.us-east-2.amazonaws.com/k%26M.png");
-- -----------------------------------------------------
-- Table `vinylSound`.`products`
-- -----------------------------------------------------
-- Rock Vinilo --
INSERT INTO `products`(title,price,quantity_sells,stock,description,id_subcategory,id_brand) VALUES (
"Arctic Monkeys - Favourite Worst Nightmare", 25400,1,5, "TRACKS. 1 Brianstorm. 2 Teddy Picker. 3 D is for Dangerous. 4 Balaclava. 5 Fluorescent Adolescent. 6 Only Ones Who Know. 7 Do Me a Favour. 8 This House Is a Circus. 9 If You Were There, Beware. 10 The Bad Thing 11. Old Yellow Bricks. 11 505." ,1,1);
INSERT INTO `products`(title,price,quantity_sells,stock,description,id_subcategory,id_brand) VALUES (
"David Bowie - Low", 28500,5,5, "TRACKS. 1 Speed of Life. 2 Breaking Glass. 3 What in the World. 4 Sound and Vision. 5 Always Crashing in the Same Car. 6 Be My Wife. 7 A New Career in a New Town. 8 Warszawa. 9 Art Decade. 10 Weeping Wall." ,1,2);
INSERT INTO `products`(title,price,quantity_sells,stock,description,id_subcategory,id_brand) VALUES (
"Deep Purple - Shades of Deep Purple", 22100,2,5, "TRACKS. 1 And the Address (instrumental). 2 Hush. 3 One More Rainy Day. 4 Prelude: Happiness/I'm So Glad. 5 Mandrake Root. 6 Help!. 7 Love Help Me. 8 Hey Joe." ,1,3);
INSERT INTO `products`(title,price,quantity_sells,stock,description,id_subcategory,id_brand) VALUES (
"Eagles - Hotel California", 28500,10,5, "TRACKS. 1 Hotel California. 2 New Kid in Town. 3 Life in the Fast Lane. 4 Wasted Time. 5 Wasted Time (Reprise). 6 Victim of Love. 7 Pretty Maids All in a Row. 8 Try and Love Again." ,1,1);
INSERT INTO `products`(title,price,quantity_sells,stock,description,id_subcategory,id_brand) VALUES (
"Eric Clapton - Unplugged", 26200,3,5, "TRACKS. 1 Signe. 2 Before You Accuse Me. 3 Hey Hey. 4 Tears in Heaven. 5 Lonely Stranger. 6 Nobody Knows You When You're Down and Out. 7 Layla. 8 Running on Faith. 9 Walkin' Blues." ,1,2);

-- Jazz Vinilo --
INSERT INTO `products`(title,price,quantity_sells,stock,description,id_subcategory,id_brand) VALUES (
"Jan Akkerman - 75", 20500,2,5, "TRACKS. 1 Revival Of The Cat. 2 Blue Boy. 3 Crackers. 4 Streetwalker. 5 Skydancer. 6 Oil In The Family (Fuel). 7 Valdez. 8 Funkology (Baby Start - One Way - Free). 9 Pietons (single version)." ,2,3);
INSERT INTO `products`(title,price,quantity_sells,stock,description,id_subcategory,id_brand) VALUES (
"Miles Davis - Kind of Blue", 28500,4,5, "TRACKS. 1 So What. 2 Freddie Freeloader. 3 Blue in Green. 4 All Blues. 5 Flamenco Sketches." ,2,1);
INSERT INTO `products`(title,price,quantity_sells,stock,description,id_subcategory,id_brand) VALUES (
"Pharoah Sanders - Black Unity", 31500,3,5, "TRACKS. 1 Black Unity. 2 The Creator Has a Master Plan." ,2,2);
INSERT INTO `products`(title,price,quantity_sells,stock,description,id_subcategory,id_brand) VALUES (
"LUIS ALBERTO SPINETTA, RODOLFO GARCIA, DANIEL FERRON – LOS AMIGOS", 28500,0,5, "TRACKS. 1 Apenas Floto. 2 Iris. 3 El Cabecitero. 4 Bagualerita. 5 El Gaitero. 6 Del Lugar. 7 Iris." ,2,3);
INSERT INTO `products`(title,price,quantity_sells,stock,description,id_subcategory,id_brand) VALUES (
"CHET BAKER - CHET BAKER & STRINGS", 28500,0,5, "TRACKS. 1 You Don't Know What Love Is. 2 I'm Thru With Love. 3 Love Walked In. 4 You Better Go Now. 5 I Married An Angel. 6 Love. 7 I Love You. 8 What A Diff'rence a Day Made. 9 My Ideal. 10 The Thrill Is Gone. 11 I'll Remember April. 12 Everything Happens to Me. 13 Just Friends. 14 Let's Get Lost." ,2,1);

-- Blues Vinilo --
INSERT INTO `products`(title,price,quantity_sells,stock,description,id_subcategory,id_brand) VALUES (
"Muddy Waters - Folk Singer", 28500,5,5, "TRACKS. 1 Mannish Boy. 2 I'm Your Hoochie Coochie Man. 3 Trouble No More. 4 Forty Days and Forty Nights. 5 You Can't Lose What You Ain't Never Had. 6 Sad Sad Day. 7 Baby Please Don't Go. 8 Good Morning Little School Girl. 9 Got My Mojo Working. 10 I Feel So Good." ,3,2);
INSERT INTO `products`(title,price,quantity_sells,stock,description,id_subcategory,id_brand) VALUES (
"B.B. King - Live at the Regal", 28500,12,5, "TRACKS. 1 Everyday I Have the Blues. 2 How Blue Can You Get. 3 You Don't Know Me. 4 B.B.'s Boogie. 5 Please Love Me. 6 You Upset Me Baby. 7 I'm Tore Down. 8 Don't Answer the Door. 9 Sweet Little Angel. 10 Woke Up This Morning." ,3,3);
INSERT INTO `products`(title,price,quantity_sells,stock,description,id_subcategory,id_brand) VALUES (
"John Lee Hooker - The Real Folk Blues", 28500,1,5, "TRACKS. 1 Boom Boom. 2 I'm Mad. 3 Dimples. 4 Crawling King Snake. 5 It Serves You Right to Suffer. 6 Tupelo. 7 Boogie Chillen. 8 One Bourbon, One Scotch, One Beer. 9 Don't Start Me Talkin. 10 You Know I Love You." ,3,1);
INSERT INTO `products`(title,price,quantity_sells,stock,description,id_subcategory,id_brand) VALUES (
"Howlin' Wolf - The London Howlin' Wolf Sessions", 28500,4,5, "TRACKS. 1 Smokestack Lightning. 2 The Red Rooster. 3 Back Door Man. 4 Spoonful. 5 Killing Floor. 6 Morning Sickness. 7 Goin' Down Slow. 8 How Many More Years. 9 Evil. 10 I Ain't Superstitious." ,3,2);
INSERT INTO `products`(title,price,quantity_sells,stock,description,id_subcategory,id_brand) VALUES (
"Albert King - Born Under a Bad Sign", 28500,6,5, "TRACKS. 1 Born Under a Bad Sign. 2 Crosscut Saw. 3 Laundromat Blues. 4 As the Years Go Passing By. 5 Personal Manager. 6 I'll Play the Blues for You. 7 The Hunter. 8 Down Don't Worry. 9 Oh, Pretty Woman. 10 You Don't Have to Love Me." ,3,3);


-- Pop Vinilo --
INSERT INTO `products`(title,price,quantity_sells,stock,description,id_subcategory,id_brand) VALUES (
"Dua Lipa - Future Nostalgia", 28500,25,5, "TRACKS. 1 Future Nostalgia. 2 Don't Start Now. 3 Cool. 4 Physical. 5 Levitating. 6 Pretty Please. 7 Hallucinate. 8 Love Again. 9 Break My Heart. 10 Good in Bed. 11 Boys Will Be Boys." ,4,1);
INSERT INTO `products`(title,price,quantity_sells,stock,description,id_subcategory,id_brand) VALUES (
"Madonna - Like a Virgin",24990,20,5,"TRACKS. 1 Material Girl. 2 Angel. 3 Like a Virgin. 4 Over and Over. 5 Love Don't Live Here Anymore. 6 Into the Groove. 7 Dress You Up. 8 Shoo-Bee-Doo. 9 Pretender. 10 Stay.",4,2);
INSERT INTO `products`(title,price,quantity_sells,stock,description,id_subcategory,id_brand) VALUES (
"Adele - 30",15800,7,5,"TRACKS. 1 Strangers by Nature. 2 Easy on Me. 3 My Little Love. 4 Cry Your Heart Out. 5 Oh My God. 6 Can I Get It. 7 I Drink Wine. 8 All Night Parking (with Erroll Garner) interlude. 9 Woman Like Me. 10 Hold On. 11 To Be Loved. 12 Love Is a Game.",4,3);
INSERT INTO `products`(title,price,quantity_sells,stock,description,id_subcategory,id_brand) VALUES (
"Miley Cyrus - Plastic Hearts",18200,1,5,"TRACKS. 1 WTF Do I Know. 2 Plastic Hearts. 3 Angels Like You. 4 Prisoner Feat Dua Lipa. 5 Gimme What I Want. 6 Night Crawling Feat Billy Idol. 7 Midnight Sky. 8 High. 9 Hate Me. 10 Bad Karma Feat Joan Jett. 11 Never Be Me. 12 Golden G String.",4,1);
INSERT INTO `products`(title,price,quantity_sells,stock,description,id_subcategory,id_brand) VALUES (
"Taylor Swift - 1989",15200,120,5,"TRACKS. 1 Welcome to New York. 2 Blank Space. 3 Style 4 Out of the Woods. 5 All You Had to Do Was Stay. 6 Shake It Off. 7 I Wish You Would. 8 Bad Blood. 9 Wildest Dreams. 10 How You Get the Girl. 11 This Love. 12 I Know Places. 13 Clean.",4,2);

-- Reggae CDs --
INSERT INTO `products`(title,price,quantity_sells,stock,description,id_subcategory,id_brand) VALUES (
" Bob Marley & The Wailers - Legend", 29000,130,5, "TRACKS. 1 Is This Love. 2 No Woman, No Cry. 3 Could You Be Loved. 4 Get Up, Stand Up. 5 Jamming. 6 One Love/People Get Ready. 7 Redemption Song. 8 Satisfy My Soul. 9 So Jah Seh. 10 War." ,5 ,4);
INSERT INTO `products`(title,price,quantity_sells,stock,description,id_subcategory,id_brand) VALUES (
"UB40 - Signing Off", 12000,15,5, "TRACKS. 1 Food for Thought. 2 Red Red Wine. 3 One in Ten. 4 Just Another Girl. 5 Madame Medusa. 6 Monday Morning. 7 You Don't Call Me. 8 I'll Be Your Baby Tonight. 9 Signing Off. 10 Reggae Music." ,5 ,5);
INSERT INTO `products`(title,price,quantity_sells,stock,description,id_subcategory,id_brand) VALUES (
"The Police - Outlandos d'Amour", 28400,2,5, "TRACKS. 1 Roxanne. 2 Can't Stand Losing You. 3 So Lonely. 4 Roxanne (Remake). 5 Hole in My Life. 6 Peanuts. 7 Reggae Jam. 8 Watching You. 9 Man in a Suitcase. 10 Fall Out." ,5 ,6);
INSERT INTO `products`(title,price,quantity_sells,stock,description,id_subcategory,id_brand) VALUES (
"The Specials - The Specials ", 18500,1,5, "TRACKS. 1 A Message to You, Rudy. 2 Too Much Too Young. 3 Gangsters. 4 The Israelites. 5 Lonely Soldier. 6 Do the Dog. 7 It's Up to You. 8 Rat Race. 9 Rudi, a Message to You. 10 Nite Klub." ,5 ,4);
INSERT INTO `products`(title,price,quantity_sells,stock,description,id_subcategory,id_brand) VALUES (
"Peter Tosh - Legalize It", 23500,0,5, "TRACKS. 1 Legalize It. 2 I Am That I Am. 3 Get Up, Stand Up. 4 Jah Is My Light. 5 Equal Rights. 6 War. 7 No Sympathy. 8 Babylon Burning. 9 Drum Song. 10 Burial." ,5 ,5);

-- Tech House CDs --
INSERT INTO `products`(title,price,quantity_sells,stock,description,id_subcategory,id_brand) VALUES (
"Maceo Plex - Solar", 21240,4,5, "TRACKS. 1 Solar. 2 Mutant. 3 The Man. 4 In The Dark. 5 The Journey. 6 Feel It. 7 The Answer. 8 The Light. 9 The End. 10 The Beginning." ,6 ,6);
INSERT INTO `products`(title,price,quantity_sells,stock,description,id_subcategory,id_brand) VALUES (
" Tale Of Us - Afterlife ", 20800,0,5, "TRACKS. 1 Elysia. 2 The Dark. 3 The Light. 4 The Journey. 5 The End. 6 The Beginning. 7 The Dream. 8 The Hope. 9 The Love. 10 The Loss." ,6 ,4);
INSERT INTO `products`(title,price,quantity_sells,stock,description,id_subcategory,id_brand) VALUES (
"Adam Beyer - Drumcode", 26500,6,5, "TRACKS. 1 The Beginning. 2 The Journey. 3 The End. 4 The Dream. 5 The Hope. 6 The Love. 7 The Loss. 8 The Pain. 9 The Fear. 10 The Joy." ,6 ,5);
INSERT INTO `products`(title,price,quantity_sells,stock,description,id_subcategory,id_brand) VALUES (
"I Hate Models - Headfirst", 28100,2,5, "TRACKS. 1 Headfirst. 2 The Drop. 3 The Groove. 4 The Bassline. 5 The Melody. 6 The Rhythm. 7 The Energy. 8 The Power. 9 The Intensity. 10 The Release." ,6 ,6);
INSERT INTO `products`(title,price,quantity_sells,stock,description,id_subcategory,id_brand) VALUES (
"Blawan - Fabric 92", 21050,1,5, "TRACKS. 1 The Beginning. 2 The Journey. 3 The End. 4 The Dream. 5 The Hope. 6 The Love. 7 The Loss. 8 The Pain. 9 The Fear. 10 The Joy." ,6 ,4);

-- Rap CDs --
INSERT INTO `products`(title,price,quantity_sells,stock,description,id_subcategory,id_brand) VALUES (
" Kendrick Lamar - To Pimp a Butterfly", 22300,80,5, "TRACKS. 1 Wesley's Theory. 2 King Kunta. 3 Alright. 4 Institutionalized. 5 These Walls. 6 The Blacker the Berry. 7 i. 8 For Free? (Interlude). 9 How Much a Dollar Cost. 10 Compton. 11 The Recipe. 12 Alright (Remix)." ,7 ,5);
INSERT INTO `products`(title,price,quantity_sells,stock,description,id_subcategory,id_brand) VALUES (
"Drake - Views ", 21500,50,5, "TRACKS. 1 One Dance. 2 Hotline Bling. 3 Controlla. 4 Views. 5 U With Me?. 6 Feel No Ways. 7 Hype. 8 Weston Road Flows. 9 Summer Sixteen. 10 Hotline Bling (Remix)." ,7 ,6);
INSERT INTO `products`(title,price,quantity_sells,stock,description,id_subcategory,id_brand) VALUES (
"J. Cole - 4 Your Eyez Only", 28200,1,5, "TRACKS. 1 Intro. 2 For Whom the Bell Tolls. 3 Change. 4 4 Your Eyez Only. 5 She's Mine Pt. 1. 6 She's Mine Pt. 2. 7 Foldin Clothes. 8 No Role Modelz. 9 Apparently. 10 Deja Vu. 11 Ville Mentality. 12 4 Your Eyez Only." ,7 ,4);
INSERT INTO `products`(title,price,quantity_sells,stock,description,id_subcategory,id_brand) VALUES (
"Chance the Rapper - Coloring Book", 18500,0,5, "TRACKS. 1 All We Got. 2 No Problem. 3 Summer Friends. 4 Juke Jam. 5 1-800-273-8255. 6 Same Drugs. 7 Parentheses. 8 Blessings (Remix). 9 Finish Line / Drown. 10 How Great. 11 Angels." ,7 ,5);
INSERT INTO `products`(title,price,quantity_sells,stock,description,id_subcategory,id_brand) VALUES (
"Migos - Culture", 21200,5,5, "TRACKS. 1 Bad and Boujee. 2 T-Shirt. 3 Culture. 4 Slippery. 5 Get Right Witcha. 6 Call Casting. 7 What the Price. 8 Big on Big. 9 Out Yo Way. 10 Dab of the Day. 11 Kelly Price. 12 Outro." ,7 ,6);

-- Indie CDs --
INSERT INTO `products`(title,price,quantity_sells,stock,description,id_subcategory,id_brand) VALUES (
"Arctic Monkeys - AM", 22100,1,5, "TRACKS. 1 Do I Wanna Know?. 2 R U Mine?. 3 One Point Perspective. 4 Snap Out of It. 5 Why'd You Only Call Me When You're High?. 6 I Want It All. 7 1 Party Anthem. 8 Mad Sounds. 9 Fireside. 10 Knee Socks. 11 Arabella. 12 I Wanna Be Yours." ,8 ,4);
INSERT INTO `products`(title,price,quantity_sells,stock,description,id_subcategory,id_brand) VALUES (
"The Strokes - Is This It", 21300,2,5, "TRACKS. 1 Is This It. 2 The Modern Age. 3 Reptilia. 4 Last Nite. 5 Barely Legal. 6 Soma. 7 Someday. 8 Alone, Together. 9 Hard to Explain. 10 New York City Cops. 11 Trying Your Luck. 12 Take It or Leave It." ,8 ,5);
INSERT INTO `products`(title,price,quantity_sells,stock,description,id_subcategory,id_brand) VALUES (
"National Boxer", 22800,4,5, "TRACKS. 1 Fake Empire. 2 Mistaken for Strangers. 3 Brainy. 4 Squalor Victoria. 5 Start a War. 6 Abel. 7 Slow Show. 8 The City. 9 Apartment Story. 10 Boxer. 11 Ada. 12 Racing Like a Man." ,8 ,6);
INSERT INTO `products`(title,price,quantity_sells,stock,description,id_subcategory,id_brand) VALUES (
"Radiohead - OK Computer", 21400,2,5, "TRACKS. 1 Airbag. 2 Paranoid Android. 3 Subterranean Homesick Alien. 5 Exit Music (For a Film). 6 Let Down. 7 Karma Police. 8 Fitter Happier. 9 Electioneering. 10 Climbing Up the Walls. 11 No Surprises. 12 Lucky. 13 The Tourist." ,8 ,4);
INSERT INTO `products`(title,price,quantity_sells,stock,description,id_subcategory,id_brand) VALUES (
"Arcade Fire - Funeral", 22500,0,5, "TRACKS. 1 Rebellion. 2 Funeral. 3 Neighborhood #1. 4 Une Annee Sans Lumiere. 5 Neighborhood #2. 6 Neighborhood #3. 7 Wake Up. 8 Haiti. 9 Half Light I. 10 Half Light II. 11 7 Kettles." ,8 ,5);

-- Instrumentos Guitarras --
INSERT INTO `products`(title,price,quantity_sells,stock,description,id_subcategory,id_brand) VALUES (
"Fender Stratocaster", 50000,3,2, "Tipo: Guitarra electrica. Madera del cuerpo: Aliso. Madera del mastil: Arce. Diapason: Palo de rosa. Trastes: 21. Escala: 25,5. Pastillas: 3 pastillas de bobina simple. Controles: Volumen, tono 1, tono 2. Selector de pastillas: 5 posiciones. Puente: Tremolo sincronizado de 2 puntos. Hardware: Cromado." ,9 ,7);
INSERT INTO `products`(title,price,quantity_sells,stock,description,id_subcategory,id_brand) VALUES (
"Fender Telecaster", 55000,0,2, "Tipo: Guitarra electrica. Madera del cuerpo: Fresno. Madera del mastil: Arce. Diapason: Palo de rosa o arce. Trastes: 21. Escala: 25,5. Pastillas: 2 pastillas de bobina simple. Controles: Volumen, tono 1, tono 2. Selector de pastillas: 3 posiciones. Puente: 3-Saddle Strings-Through-Body. Hardware: Cromado." ,9 ,7);
INSERT INTO `products`(title,price,quantity_sells,stock,description,id_subcategory,id_brand) VALUES (
"Gibson Les Paul", 45000,2,2, "Tipo: Guitarra electrica. Madera del cuerpo: Caoba. Madera del mastil: Caoba. Diapason: Palo de rosa. Trastes: 22. Escala: 24,75. Pastillas: 2 pastillas humbucker. Controles: Volumen, tono 1, tono 2. Selector de pastillas: 3 posiciones. Puente: Tune-o-matic con cordal Stopbar. Hardware: Dorado." ,9 ,8);
INSERT INTO `products`(title,price,quantity_sells,stock,description,id_subcategory,id_brand) VALUES (
"Gibson SG", 48000,0,2, "Tipo: Guitarra electrica. Madera del cuerpo: Caoba. Madera del mástil: Caoba. Diapason: Palo de rosa. Trastes: 22. Escala: 24,75. Pastillas: 2 pastillas humbucker. Controles: Volumen, tono 1, tono 2. Selector de pastillas: 3 posiciones. Puente: Tune-o-matic con cordal Stopbar. Hardware: Cromado." ,9 ,8);

-- Teclados Instrumentos --
INSERT INTO `products`(title,price,quantity_sells,stock,description,id_subcategory,id_brand) VALUES (
"Yamaha PSR-E373", 30000,1,4, "Teclado arreglista de 61 teclas con una amplia gama de voces, estilos y funciones. Perfecto para principiantes e intermedios. Cuenta con una pantalla LCD retroiluminada, un sistema de altavoces de 2 bocinas y un puerto USB para conectarse a una computadora." ,10 ,9);
INSERT INTO `products`(title,price,quantity_sells,stock,description,id_subcategory,id_brand) VALUES (
"Yamaha YPG-50", 40000,2,4, "Teclado portatil de 76 teclas con una variedad de voces, estilos y funciones. Perfecto para principiantes e intermedios. Cuenta con una pantalla LCD retroiluminada, un sistema de altavoces de 4 bocinas y un puerto USB para conectarse a una computadora." ,10 ,9);
INSERT INTO `products`(title,price,quantity_sells,stock,description,id_subcategory,id_brand) VALUES (
"Casio Privia PX-S1000", 35000,3,4, "Teclado de 61 teclas con una amplia gama de voces, estilos. Perfecto para principiantes e intermedios. Cuenta con una pantalla LCD retroiluminada, un sistema de altavoces de 2 bocinas y un puerto USB para conectarse a una computadora." ,10 ,10);
INSERT INTO `products`(title,price,quantity_sells,stock,description,id_subcategory,id_brand) VALUES (
"Casio CTK-4400", 39000,1,4, "Teclado digital de 88 teclas con acción de martillo ponderada y un sonido de piano de alta calidad. Perfecto para pianistas intermedios y avanzados. Cuenta con una pantalla LCD a color, un sistema de altavoces de 4 bocinas y Bluetooth para conectarse a dispositivos celulares." ,10, 10);

-- Vientos Instrumentos --
INSERT INTO `products`(title,price,quantity_sells,stock,description,id_subcategory,id_brand) VALUES (
"Clarinete YCL-255", 28800,2,6, "Clarinete de nivel de estudiante con cuerpo de ABS y llaves de niquel-plata. Es un instrumento duradero y de buen sonido que es perfecto para principiantes." ,11 ,9);
INSERT INTO `products`(title,price,quantity_sells,stock,description,id_subcategory,id_brand) VALUES (
"Flauta YFL-212", 30700,0,6, "Flauta travesera de nivel de estudiante con cuerpo de niquel-plata y cabeza de plata. Es un instrumento de buena calidad que es perfecto para principiantes." ,11 ,9);
INSERT INTO `products`(title,price,quantity_sells,stock,description,id_subcategory,id_brand) VALUES (
" Saxo alto YAS-280", 38900,4,6, "Saxo alto de nivel de estudiante con cuerpo de latón y llaves de niquel-plata. Es un instrumento duradero y de buen sonido que es perfecto para principiantes." ,11 ,9);

-- Tambores Intrumentos --
INSERT INTO `products`(title,price,quantity_sells,stock,description,id_subcategory,id_brand) VALUES (
"Export Series EXX725BP/C", 32000,1,7, "Tipo: Bateria completa de 5 piezas. Cascos: 100% alamo de 6 capas y 7 mm de grosor. Aros: Triple brida de acero. Parches: Remo Ambassador Coated en toms y caja, Remo Powerstroke P3 en bombo. Herrajes: Soporte de bombo de doble pata, platillos no incluidos. Acabado: Black Cherry Burst." ,12 ,11);
INSERT INTO `products`(title,price,quantity_sells,stock,description,id_subcategory,id_brand) VALUES (
"Roadshow Series RDS525SC/C", 41000,2,7, "Tipo: Bateria completa de 5 piezas. Cascos: madera dura de 9 capas y 7.5 mm de grosor. Aros: Aros simples de acero. Parches: Remo Ambassador Coated en toms y caja, Remo Powerstroke P3 en bombo. Herrajes: Soporte de bombo de doble pata, platillos no incluidos. Acabado: Charcoal Burst." ,12 ,11);
INSERT INTO `products`(title,price,quantity_sells,stock,description,id_subcategory,id_brand) VALUES (
"Decade Series DMP925SP/C", 23000,1,7, "Tipo: Bateria completa de 5 piezas. Cascos: Arce/caoba africana de 6 capas y 5.4 mm de grosor. Aros: SuperHoop II de acero. Parches: Remo Ambassador Coated en toms y caja, Remo Powerstroke P3 en bombo. Herrajes: Soporte de bombo de doble pata, platillos no incluidos. Acabado: Scarlet Fade." ,12 ,11);

-- Estereos Parlantes  --
INSERT INTO `products`(title,price,quantity_sells,stock,description,id_subcategory,id_brand) VALUES (
"HS5", 25000,1,2, "Altavoz de monitoreo de estudio de 5. Woofer de cono blanco de 5. Tweeter de 1. Respuesta de frecuencia: 54 Hz - 30 kHz. Amplificador de 45 W. SPL max: 107 dB. Entradas XLR y TRS. Acabado negro." ,13 ,9);
INSERT INTO `products`(title,price,quantity_sells,stock,description,id_subcategory,id_brand) VALUES (
"HS8", 28000,2,2, "Altavoz de monitoreo de estudio de 8. Woofer de cono blanco de 8. Tweeter de 1. Respuesta de frecuencia: 38 Hz - 30 kHz. Amplificador de 75 W. SPL max: 112 dB. Entradas XLR y TRS. Acabado negro." ,13 ,9);
INSERT INTO `products`(title,price,quantity_sells,stock,description,id_subcategory,id_brand) VALUES (
"RP-500M", 34000,2,2, "Altavoz de 5,25. Woofer de cono de Cerametallic de 5,25. Tweeter de aluminio de 1. Respuesta de frecuencia: 45 Hz - 25 kHz. Sensibilidad: 90 dB. Impedancia: 8 ohmios. Potencia de salida recomendada: 100-400 W. Acabado negro." ,13 ,12);
INSERT INTO `products`(title,price,quantity_sells,stock,description,id_subcategory,id_brand) VALUES (
"RP-600M", 23000,1,2, "Altavoz de suelo de 6,5. Woofer de cono de Cerametallic de 6,5. Tweeter de aluminio de 1. Respuesta de frecuencia: 40 Hz - 25 kHz. Sensibilidad: 91 dB. Impedancia: 8 ohmios. Potencia de salida recomendada: 100-500 W. Acabado negro." ,13 ,12);

-- Estereos Auriculares --
INSERT INTO `products`(title,price,quantity_sells,stock,description,id_subcategory,id_brand) VALUES (
"T5 II True Wireless", 24000,1,2, "Auriculares con estuche de carga. Bluetooth 5. Hasta 8 horas de uso con una sola carga (24 horas con el estuche). Sonido personalizado con la app Klipsch Connect. Acabado negro, blanco o azul." ,14 ,12);
INSERT INTO `products`(title,price,quantity_sells,stock,description,id_subcategory,id_brand) VALUES (
"R6i In-Ear", 23000,1,2, "Auriculares in-ear con cable. Controlador de 9 mm. Control remoto integrados. Respuesta de frecuencia: 10 Hz - 19 kHz. Impedancia: 17 ohmios. Sensibilidad: 102 dB. Acabado negro, gris o rojo." ,14 ,12);
INSERT INTO `products`(title,price,quantity_sells,stock,description,id_subcategory,id_brand) VALUES (
"X10 True Wireless", 22000,1,2, "Auriculares con estuche de carga. Bluetooth 5. Hasta 6 horas de uso con una sola carga (24 horas con el estuche). Sonido envolvente y graves potentes. Acabado negro, gris o azul." ,14 ,12);

-- Estereos Radios --
INSERT INTO `products`(title,price,quantity_sells,stock,description,id_subcategory,id_brand) VALUES (
"YAS-209 IntelliRadio Network Tuner", 21500,2,4, "Sintonizador de red con MusicCast para audio multisala y control por voz con Alexa o Assistant. Wi-Fi, Bluetooth y AirPlay 2 integrados. Radio DAB+ y FM." ,15 ,9);
INSERT INTO `products`(title,price,quantity_sells,stock,description,id_subcategory,id_brand) VALUES (
"R-N603 Network Stereo Receiver", 28000,1,4, "Receptor de red con MusicCast para audio multisala y control por voz con Alexa o Assistant. Wi-Fi, Bluetooth, AirPlay 2 y Spotify Connect integrados. Radio AM/FM." ,15 ,9);
INSERT INTO `products`(title,price,quantity_sells,stock,description,id_subcategory,id_brand) VALUES (
"KMC 1", 26700,0,4, "Radio de sobremesa con sintonizador AM/FM. Conectividad Bluetooth. Reloj despertador doble. Sonido rico y potente de Klipsch." ,15 ,12);

-- Accesorios cables de audio --
INSERT INTO `products`(title,price,quantity_sells,stock,description,id_subcategory,id_brand) VALUES (
"Custom Shop Cable", 23000,01,2, "Cable de instrumento de alta calidad con conductores de cobre. Conectores chapados en oro de 24 quilates. Blindaje en espiral para un rechazo de ruido superior. Disponible en longitudes de 10, 15, 20 y 25 pies." ,16 ,7);
INSERT INTO `products`(title,price,quantity_sells,stock,description,id_subcategory,id_brand) VALUES (
"Professional Series Cable", 23000,0,2, "Cable de instrumento de calidad profesional con conductores de cobre. Conectores niquelados. Blindaje trenzado para un rechazo de ruido efectivo. Disponible en longitudes de 10, 15, 20 y 25 pies." ,16 ,7);
INSERT INTO `products`(title,price,quantity_sells,stock,description,id_subcategory,id_brand) VALUES (
"Original Series Cable", 23000,3,2, "Cable de instrumento asequible con conductores de cobre. Conectores niquelados. Blindaje trenzado para un rechazo de ruido. Disponible en longitudes de 10, 15, 20 y 25 pies." ,16 ,7);

-- Accesorios Fundas --
INSERT INTO `products`(title,price,quantity_sells,stock,description,id_subcategory,id_brand) VALUES (
"Estuche G-PG Electric Guitar Case", 23000,2,3, "Estuche de madera contrachapada con exterior de ABS. Acolchado de espuma de alta densidad. Interior de felpa para un acabado suave. Cierres de mariposa con llave. Asa de transporte." ,17 ,13);
INSERT INTO `products`(title,price,quantity_sells,stock,description,id_subcategory,id_brand) VALUES (
"Estuche G-PB Bass Guitar Case", 23000,7,3, "Estuche de madera contrachapada con exterior de ABS. Acolchado de espuma de alta densidad. Interior de felpa para un acabado suave. Cierres de mariposa con llave. Asa de transporte." ,17 ,13);
INSERT INTO `products`(title,price,quantity_sells,stock,description,id_subcategory,id_brand) VALUES (
"Estuche GK-61 61-Note Keyboard Case", 23000,2,3, "Estuche de madera contrachapada con exterior de ABS. Acolchado de espuma de alta densidad. Interior de felpa para un acabado suave. Cierres de mariposa con llave. Asa de transporte y ruedas." ,17 ,13);

-- Accesorios Soportes --
INSERT INTO `products`(title,price,quantity_sells,stock,description,id_subcategory,id_brand) VALUES (
" Atril para partituras 15290", 13000,10,2, "Atril para partituras de orquesta con altura ajustable. Base plegable para mayor estabilidad. Soporte de partitura de metal resistente con bandeja. Altura: 600 - 1.220 mm. Peso: 2,3 kg." ,18 ,14);
INSERT INTO `products`(title,price,quantity_sells,stock,description,id_subcategory,id_brand) VALUES (
"Atril 210/9", 15900,1,2, "Atril con altura ajustable. Base plegable para mayor estabilidad. Altura: 1.020 - 1.650 mm. Peso: 2,8 kg." ,18 ,14);
INSERT INTO `products`(title,price,quantity_sells,stock,description,id_subcategory,id_brand) VALUES (
"Atril para director 17490", 11200,4,2, "Atril de director con altura ajustable. Base plegable para mayor estabilidad. Atril de madera de haya con superficie de lectura inclinada. Altura: 1.050 - 1.380 mm. Peso: 3,5 kg." ,18 ,14);



-- -----------------------------------------------------
-- Table `vinylSound`.`images`
-- -----------------------------------------------------
INSERT INTO `images` (name,url,id_product) VALUES ('Adele - 30','https://imgs-integrador2.s3.us-east-2.amazonaws.com/adele2.png',18);
INSERT INTO `images` (name,url,id_product) VALUES ('Adele - 30','https://imgs-integrador2.s3.us-east-2.amazonaws.com/adele30.png',18);
INSERT INTO `images` (name,url,id_product) VALUES ('Albert King - Born Under a Bad Sign','https://imgs-integrador2.s3.us-east-2.amazonaws.com/albertKing.png',15);
INSERT INTO `images` (name,url,id_product) VALUES ('Albert King - Born Under a Bad Sign','https://imgs-integrador2.s3.us-east-2.amazonaws.com/albertKing2.png',15);
INSERT INTO `images` (name,url,id_product) VALUES ('Arctic Monkeys - Favourite Worst Nightmare','https://imgs-integrador2.s3.us-east-2.amazonaws.com/ArticMonkeys.jpg',1);
INSERT INTO `images` (name,url,id_product) VALUES ('Arctic Monkeys - Favourite Worst Nightmare','https://imgs-integrador2.s3.us-east-2.amazonaws.com/ArticMonkeys2.jpeg',1);
INSERT INTO `images` (name,url,id_product) VALUES ('Arctic Monkeys - AM','https://imgs-integrador2.s3.us-east-2.amazonaws.com/articMonkeysAm.png',36);
INSERT INTO `images` (name,url,id_product) VALUES ('Atril para director 17490','https://imgs-integrador2.s3.us-east-2.amazonaws.com/atrilParitutrasoporte.png',73);
INSERT INTO `images` (name,url,id_product) VALUES ('B.B. King - Live at the Regal','https://imgs-integrador2.s3.us-east-2.amazonaws.com/bbKingLiveAtTheRegal.png',12);
INSERT INTO `images` (name,url,id_product) VALUES ('Bob Marley & The Wailers - Legend','https://imgs-integrador2.s3.us-east-2.amazonaws.com/bobMarley.png',21);
INSERT INTO `images` (name,url,id_product) VALUES ('Chance the Rapper - Coloring Book','https://imgs-integrador2.s3.us-east-2.amazonaws.com/chanceRapper.png',34);
INSERT INTO `images` (name,url,id_product) VALUES ('CHET BAKER – CHET BAKER & STRINGS','https://imgs-integrador2.s3.us-east-2.amazonaws.com/chetBaker.png',10);
INSERT INTO `images` (name,url,id_product) VALUES ('Clarinete YCL-255','https://imgs-integrador2.s3.us-east-2.amazonaws.com/clarineteYcl255.png',49);
INSERT INTO `images` (name,url,id_product) VALUES ('David Bowie - Low','https://imgs-integrador2.s3.us-east-2.amazonaws.com/davidBowieLow.png',2);
INSERT INTO `images` (name,url,id_product) VALUES ('David Bowie - Low','https://imgs-integrador2.s3.us-east-2.amazonaws.com/davidBowieLow2.png',2);
INSERT INTO `images` (name,url,id_product) VALUES ('David Bowie - Low','https://imgs-integrador2.s3.us-east-2.amazonaws.com/davidBowieLow3.png',2);
INSERT INTO `images` (name,url,id_product) VALUES ('Deep Purple - Shades of Deep Purple','https://imgs-integrador2.s3.us-east-2.amazonaws.com/deepPurple2.png',3);
INSERT INTO `images` (name,url,id_product) VALUES ('Deep Purple - Shades of Deep Purple','https://imgs-integrador2.s3.us-east-2.amazonaws.com/deepurple1.png',3);
INSERT INTO `images` (name,url,id_product) VALUES ('Drake - Views','https://imgs-integrador2.s3.us-east-2.amazonaws.com/drake.png',32);
INSERT INTO `images` (name,url,id_product) VALUES ('Dua Lipa - Future Nostalgia','https://imgs-integrador2.s3.us-east-2.amazonaws.com/duaLipaFutureNostalgia.png',16);
INSERT INTO `images` (name,url,id_product) VALUES ('Eagles - Hotel California','https://imgs-integrador2.s3.us-east-2.amazonaws.com/eagles1.png',4);
INSERT INTO `images` (name,url,id_product) VALUES ('Eagles - Hotel California','https://imgs-integrador2.s3.us-east-2.amazonaws.com/eagles2.png',4);
INSERT INTO `images` (name,url,id_product) VALUES ('Eric Clapton - Unplugged','https://imgs-integrador2.s3.us-east-2.amazonaws.com/ericClapton.png',5);
INSERT INTO `images` (name,url,id_product) VALUES ('Eric Clapton - Unplugged','https://imgs-integrador2.s3.us-east-2.amazonaws.com/ericClapton2.png',5);
INSERT INTO `images` (name,url,id_product) VALUES ('YAS-209 IntelliRadio Network Tuner','https://imgs-integrador2.s3.us-east-2.amazonaws.com/estereoRadioYas209.png',62);
INSERT INTO `images` (name,url,id_product) VALUES ('Estuche G-PG Electric Guitar Case','https://imgs-integrador2.s3.us-east-2.amazonaws.com/estucheGpgelectric.png',68);
INSERT INTO `images` (name,url,id_product) VALUES ('Fender Telecaster','https://imgs-integrador2.s3.us-east-2.amazonaws.com/fenderTelecaster.png',42);
INSERT INTO `images` (name,url,id_product) VALUES ('Fender Stratocaster','https://imgs-integrador2.s3.us-east-2.amazonaws.com/fenderFerocaster.png',41);
INSERT INTO `images` (name,url,id_product) VALUES ('Howlin Wolf - The London Howlin Wolf Sessions','https://imgs-integrador2.s3.us-east-2.amazonaws.com/howlinWolf.png',14);
INSERT INTO `images` (name,url,id_product) VALUES ('Jan Akkerman - 75','https://imgs-integrador2.s3.us-east-2.amazonaws.com/janAkkerman.png',6);
INSERT INTO `images` (name,url,id_product) VALUES ('Jan Akkerman - 75','https://imgs-integrador2.s3.us-east-2.amazonaws.com/janAkkerman2.png',6);
INSERT INTO `images` (name,url,id_product) VALUES ('J. Cole - 4 Your Eyez Only','https://imgs-integrador2.s3.us-east-2.amazonaws.com/jCole.png',33);
INSERT INTO `images` (name,url,id_product) VALUES ('John Lee Hooker - The Real Folk Blues','https://imgs-integrador2.s3.us-east-2.amazonaws.com/johnLeeHooker.png',13);
INSERT INTO `images` (name,url,id_product) VALUES ('Kendrick Lamar - To Pimp a Butterfly','https://imgs-integrador2.s3.us-east-2.amazonaws.com/kendrickLamar.png',31);
INSERT INTO `images` (name,url,id_product) VALUES ('Madonna - Like a Virgin','https://imgs-integrador2.s3.us-east-2.amazonaws.com/madona2.png',17);
INSERT INTO `images` (name,url,id_product) VALUES ('Madonna - Like a Virgin','https://imgs-integrador2.s3.us-east-2.amazonaws.com/madonna.png',17);
INSERT INTO `images` (name,url,id_product) VALUES ('Migos - Culture','https://imgs-integrador2.s3.us-east-2.amazonaws.com/madonna.png',35);
INSERT INTO `images` (name,url,id_product) VALUES ('Miles Davis - Kind of Blue','https://imgs-integrador2.s3.us-east-2.amazonaws.com/milesDavis.png',7);
INSERT INTO `images` (name,url,id_product) VALUES ('Miles Davis - Kind of Blue','https://imgs-integrador2.s3.us-east-2.amazonaws.com/milesDavis2.png',7);
INSERT INTO `images` (name,url,id_product) VALUES ('Miley Cyrus - Plastic Hearts','https://imgs-integrador2.s3.us-east-2.amazonaws.com/milleyCirus.png',19);
INSERT INTO `images` (name,url,id_product) VALUES ('Miley Cyrus - Plastic Hearts','https://imgs-integrador2.s3.us-east-2.amazonaws.com/milleyCirus2.png',19);
INSERT INTO `images` (name,url,id_product) VALUES ('Muddy Waters - Folk Singer','https://imgs-integrador2.s3.us-east-2.amazonaws.com/muddyWaters.png',11);
INSERT INTO `images` (name,url,id_product) VALUES ('Muddy Waters - Folk Singer','https://imgs-integrador2.s3.us-east-2.amazonaws.com/muddyWaters2.png',11);
INSERT INTO `images` (name,url,id_product) VALUES ('National Boxer','https://imgs-integrador2.s3.us-east-2.amazonaws.com/nationalBoxer.png',38);
INSERT INTO `images` (name,url,id_product) VALUES ('HS5','https://imgs-integrador2.s3.us-east-2.amazonaws.com/parlanteH5.png',55);
INSERT INTO `images` (name,url,id_product) VALUES ('Peter Tosh - Legalize It','https://imgs-integrador2.s3.us-east-2.amazonaws.com/peterTosh.png',25);
INSERT INTO `images` (name,url,id_product) VALUES ('Pharoah Sanders - Black Unity','https://imgs-integrador2.s3.us-east-2.amazonaws.com/PharoahSandersBlackUnity.png',8);
INSERT INTO `images` (name,url,id_product) VALUES ('Pharoah Sanders - Black Unity','https://imgs-integrador2.s3.us-east-2.amazonaws.com/pharoahSandersBlackUnity2.png',8);
INSERT INTO `images` (name,url,id_product) VALUES ('Professional Series Cable','https://imgs-integrador2.s3.us-east-2.amazonaws.com/professionalCable.png',66);
INSERT INTO `images` (name,url,id_product) VALUES ('The Specials - The Specials','https://imgs-integrador2.s3.us-east-2.amazonaws.com/specials.png',24);
INSERT INTO `images` (name,url,id_product) VALUES ('The Specials - The Specials','https://imgs-integrador2.s3.us-east-2.amazonaws.com/specials2.png',24);
INSERT INTO `images` (name,url,id_product) VALUES ('LUIS ALBERTO SPINETTA, RODOLFO GARCIA, DANIEL FERRON ‎– LOS AMIGOS','https://imgs-integrador2.s3.us-east-2.amazonaws.com/spinetta.png',9);
INSERT INTO `images` (name,url,id_product) VALUES ('Export Series EXX725BP/C','https://imgs-integrador2.s3.us-east-2.amazonaws.com/tamborExportExx725bp.png',52);
INSERT INTO `images` (name,url,id_product) VALUES ('Taylor Swift - 1989','https://imgs-integrador2.s3.us-east-2.amazonaws.com/taylor1.png',20);
INSERT INTO `images` (name,url,id_product) VALUES ('Taylor Swift - 1989','https://imgs-integrador2.s3.us-east-2.amazonaws.com/taylor2.png',20);
INSERT INTO `images` (name,url,id_product) VALUES ('The Police - Outlandos d Amour','https://imgs-integrador2.s3.us-east-2.amazonaws.com/thePolice.png',23);
INSERT INTO `images` (name,url,id_product) VALUES ('The Strokes - Is This It','https://imgs-integrador2.s3.us-east-2.amazonaws.com/theStrokes.png',37);
INSERT INTO `images` (name,url,id_product) VALUES ('UB40 - Signing Off','https://imgs-integrador2.s3.us-east-2.amazonaws.com/ub40.png',22);
INSERT INTO `images` (name,url,id_product) VALUES ('Yamaha PSR-E373','https://imgs-integrador2.s3.us-east-2.amazonaws.com/yamahaPsrE373.png',45);
