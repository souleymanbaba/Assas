-- MySQL dump 10.13  Distrib 8.4.7, for Linux (x86_64)
--
-- Host: localhost    Database: assas
-- ------------------------------------------------------
-- Server version	8.4.7-0ubuntu0.25.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `anniers`
--

DROP TABLE IF EXISTS `anniers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `anniers` (
  `id_annier` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(150) NOT NULL,
  `image_url` varchar(500) DEFAULT NULL,
  `matrucule` varchar(150) NOT NULL,
  `nom` varchar(100) NOT NULL,
  `numero_telephone` varchar(20) DEFAULT NULL,
  `numero_whatsapp` varchar(20) DEFAULT NULL,
  `specialite` varchar(100) DEFAULT NULL,
  `nom_arabe` varchar(100) DEFAULT NULL,
  `specialite_arabe` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id_annier`),
  UNIQUE KEY `UK_b3oa41tgc913o0bnu9wo4mqu6` (`email`),
  UNIQUE KEY `UK_3vu1ta927w17y2nkxguemie8t` (`matrucule`)
) ENGINE=InnoDB AUTO_INCREMENT=94 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `anniers`
--

LOCK TABLES `anniers` WRITE;
/*!40000 ALTER TABLE `anniers` DISABLE KEYS */;
INSERT INTO `anniers` VALUES (24,'boubacar_messaoud@yahoo.fr','https://assasmr.duckdns.org/api/anniers/images/09cc985e-6e1e-41aa-8265-5242ecd01768.jpg','AR1974/002','Boubacar Messaoud','46408397','45254602','Cabinet d\'architectes associés','بوبكر مسعود','مجلس المهندسين المعماريين المساعدين'),(25,'diabirafmi@yahoo.fr','https://assasmr.duckdns.org/api/anniers/images/c9152c04-b4b3-49cd-8fdd-b2dba233fe78.jpg','AR1978/003','Diabira Maryannick','36302689','36302689','cabinet d\'etude d\'architecture','ديابيرا ماريانيك','مكتب دراسات هندسة معمارية'),(26,'Mamikeme@yahoo.fr','https://assasmr.duckdns.org/api/anniers/images/5be757bc-95ba-4f05-9288-91d2f48cdc31.jpg','AR1984/004','Bintou Kaba','46431923','46431923','Cabinet : Djiokaba','بنتو كابا','المكتب : دجيوكابا'),(27,'moodibocarmoodi@yahoo.fr','https://assasmr.duckdns.org/api/anniers/images/d9f106c6-b329-4cec-80eb-cbce601054b4.jpg','AR1986/005','Sall Abderahmane','22205151','46732262','Cabinet d\'architectes associés','صال عبد الرحمن','مجلس المهندسين المعماريين المساعدين'),(28,'ndiayemhcau@yahoo.fr','https://assasmr.duckdns.org/api/anniers/images/3de050ff-76c2-4286-9f74-1b2d0d58a96d.jpg','AR1987/006','N\'diaye Mohamed EL Habib','46418943','46418943','Cabinet d\'Architecture et d\'urbanisme','ندياي محمد الحبيب','شركة الهندسة المعمارية وتخطيط المدن'),(29,'tacheikhna@yahoo.fr','https://assasmr.duckdns.org/api/anniers/images/98a3620d-c798-4b89-8ffa-1325c8d1a4bd.jpg','AR1988/007','Cheikhna Taleb Ahmed','46418943','46418943','G.I.B','شيخنا الطالب أحمد','G.I.B'),(30,'Chahmedoutas@gmail.com','https://assasmr.duckdns.org/api/anniers/images/70a231ec-dc62-4216-a193-d8f127323690.jpg','AR1992/008','Cheikh Ahmedou Mohamed Mokhtar','46447813','46447813','Cabinet : TASMIM','الشيخ أحمدو محمد مختار','مكتب:TASMIM'),(31,'Lematt1987@yahoo.fr','https://assasmr.duckdns.org/api/anniers/images/01fe1eae-0598-46e2-bfd2-866a362e4ab8.jpg','AR1992/009','Lemat Hamadi El Vadel','36309017','46570448','Ministére de l\'habitat (MHUAT)','لمات حمادي الفاضل','وزارة الإسكان (MHUAT)'),(32,'betaconsulte@yahoo.fr','https://assasmr.duckdns.org/api/anniers/images/a85e5d3e-12c5-4d4e-b85a-9bcc489b8431.jpg','AR1993/010','Sidi Mohamed Mohamed Saleh','36301289','36301289','Cabinet : BETACONSULT','سيدي محمد محمد محمد صالح','المكتب: BETACONSULT'),(33,'Abdellahiaad@yahoo.fr','https://assasmr.duckdns.org/api/anniers/images/ed08575b-ac70-44c0-a7dc-80713b4501da.jpg','AR1994/011','Abdellahi Ahmed Baba','46457473','46457473','Cabinet : ADD','عبد الله أحمد باب','مكتب:ADD'),(34,'baidrissaoumar@gmail.com','https://assasmr.duckdns.org/api/anniers/images/db4f03d1-f233-4397-947a-8c03072cb6cc.jpg','AR1994/012','Ba idrissa','47188801','47188801','Cabinet : ARCHIFORM','با إدريسا','مكتب : أركيفورم'),(35,'omerarchitecte@gmail.com','https://assasmr.duckdns.org/api/anniers/images/2d9e67b4-6cf3-4d4d-8fd2-929b542e779b.jpg','AR1995/013','Oumeir Houssein Houessou','46440331','36304940','Cabinet : CREA Aménagement','عمير حسين حويسو','المكتب: شركة CREA Aménagement'),(36,'mcbingenieries@gmail.com','https://assasmr.duckdns.org/api/anniers/images/99660ce6-16bd-4ddb-bf1b-2b12f9482f0a.jpg','AR1996/014','Mohameden Menih TAH','20020061','43183313','Multidisciplinary consulting Bureau','محمدن منيه التاه','المكتب الاستشاري متعدد التخصصات'),(37,'bat_controle@yahoo.fr','https://assasmr.duckdns.org/api/anniers/images/2157e20b-67ca-43e8-b481-565d1cc2d797.jpg','AR1996/015','Diakhate issakha','46411944','46411944','Cabinet : BATIS CONTROLE','ادياخاتي اسحاقا','المكتب: باتيس كونترول'),(38,'souleysod@gmail.com','https://assasmr.duckdns.org/api/anniers/images/c7c95279-2881-46b9-bf81-621ee5225d20.jpg','AR1996/016','Souleymane Boubecar Dramane','44481705','44481705','Ministére de l\'Equipement (MET)','سليمان بوبكر درامان','وزارة التجهيزات (MET)'),(39,'elsidarchitecte@gmail.com','https://assasmr.duckdns.org/api/anniers/images/5f8a7bd7-3e75-4b92-987d-74f102d83161.jpeg','AR1997/017','Med Moktar El said','49858502','49858502','Cabinet : E&T ARCHITECTS','محمد مختار السيد','المكتب: E&T ARCHITECTS'),(40,'NEANT@pasdeemail.com','https://assasmr.duckdns.org/api/anniers/images/78f2d607-2ad9-4f3b-823f-e3aebe1da1a4.jpg','AR2000/019','Aziz Mohamed Abdellahi','42922682','41494090','Ministére de l\'habitat (MHUAT)','عزيز محمد عبد الله','وزارة الإسكان (MHUAT)'),(41,'mohamedelhassene.bou@gmail.com','https://assasmr.duckdns.org/api/anniers/images/2bafe944-f8fa-42f5-b039-48e89adfa5e7.jpeg','AR2001/020','Mohamed El Hacene BOU','0033622453288','0033622843879','Baudin Châteauneuf - France','محمد الحسن بو','بودين شاتونوف - فرنسا'),(42,'Yahya@yahoo.fr','https://assasmr.duckdns.org/api/anniers/images/6da4e260-6269-4e67-90bb-055e9922a0e1.jpg','AR2002/021','Yahya Babane','36855051','36855051','Cabinet : BECI','يحيى ببانا','الشركة : BECI'),(44,'Yahfd5@gmail.com','https://assasmr.duckdns.org/api/anniers/images/ce8089ec-cfe2-4f44-8021-d8b38ad23b50.jpg','AR2003/022','Yahevdou Cherif','46801614','46801614','Ministére de l\'habitat (MHUAT)','يحفظو ولد الشريف','وزارة الإسكان (MHUAT)'),(45,'abobi1@yahoo.fr','https://assasmr.duckdns.org/api/anniers/images/cfddc34d-a25d-413e-8b9b-c1a9359fa6db.jpg','AR2004/023','Abba Fall','46786710','46786710','Cabinet : CAUPID','أب فال','مكتب:CAUPID'),(46,'moujtaba_w@yahoo.fr','https://assasmr.duckdns.org/api/anniers/images/6b0dc186-c55f-4b78-b886-94ab112e09b0.jpg','AR2005/024','Moujtaba Mohamed Saleck','44685541','22131017','Ministére de l\'habitat (MHUAT)','مجتبى محمد السالك','وزارة الإسكان (MHUAT)'),(47,'dg@caupid.com','https://assasmr.duckdns.org/api/anniers/images/9af6ac49-5618-4f39-835a-abdb3a081164.jpeg','AR2005/025','Med Elmoktar ROUEIHA','22379905','20109381','Cabinet : CAUPID','محمد مختار ارويحه','مكتب:CAUPID'),(48,'elmechry@yahoo.com','https://assasmr.duckdns.org/api/anniers/images/4c445ba3-c771-4f51-9fa4-545b22b7a752.jpg','AR2007/026','El Mechri Bedde','36317402','36317402','Cabinet : BACET','المشري بده','المكتب: BACET'),(62,'habib_mr2002@yahoo.fr','https://assasmr.duckdns.org/api/anniers/images/c6f0d3fe-1d63-49d4-b959-9a2da52382b6.jpg','AR 2007/027','Habiboullah CHEIKH','00974 30487999','00974 30487999','United states','حبيب الله الشيخ','الولايات المتحدة الأمريكية'),(63,'ahmedarchitecte@yahoo.fr','https://assasmr.duckdns.org/api/anniers/images/39169d2a-8fab-464b-9f8e-6b5aa63f7f24.jpeg','AR2009/028','Ahmed salem HAMOUD','44899676','27156887','Cabinet:STYLE ARCHITECTE','أحمد سالم حمود','مكتب:STYLE ARCHITECTE'),(66,'mammehacen@gmail.com','https://assasmr.duckdns.org/api/anniers/images/a01f5c39-7934-4859-a610-462d40598fb3.jpg','AR2010/029','Mohamed Yedali Hacen','44660166','44660166','Cabinet:2A-ARCHITECTS','محمد اليدالي حسن','مكتب:2A-ARCHITECTS'),(67,'ismaelchouaib@gmail.com','https://assasmr.duckdns.org/api/anniers/images/682c6041-c50e-4e4d-952a-70b55ae89f46.jpg','AR2010/030','Ismail CHOUAIB','47167891','27167891','Cabinet:2A-ARCHITECTS','إسماعيل شعيب','مكتب:2A-ARCHITECTS'),(68,'archymed@gmail.com','https://assasmr.duckdns.org/api/anniers/images/3031e15e-c433-4fd1-a3db-7cd88f3f06fa.jpg','AR2011/031','Mohamed El Hadj Brahim','46737108','33355513','Cabinet : ARCHI-MED','محمد الحاج إبراهيم','المكتب: ARCHI-MED'),(69,'snacoulibaly@gmail.com','https://assasmr.duckdns.org/api/anniers/images/752daa26-37c7-4fd4-8fc5-d545b4c9117c.jpeg','AR1995/032','Coulibaly SANA','20137219','20137219','Cabinet: BBI','كوليبالي سانا','المكتب: BBI'),(70,'haballasidi@gmail.com','https://assasmr.duckdns.org/api/anniers/images/f35f0575-f4a9-42b5-9e5f-523dd21f2945.jpeg','AR2012/033','Cheikh El Moustapha Mohamed Yahya','36302088','26302088','Cabinet : Mimar Etude et Consultation','الشيخ المصطفى محمد يحيى','المكتب: ميمار إيتود والاستشارات'),(71,'lo.aminata@gmail.com','https://assasmr.duckdns.org/api/anniers/images/b5e6e373-aa09-40d3-a62b-e2e7c29afd31.jpg','AR2012/034','Lo Aminata','45558343','45558343','Région de Nouakchott','آميناتا لو','منطقة نواكشوط'),(72,'aucaarchitecture@gmail.com','https://assasmr.duckdns.org/api/anniers/images/07f31e5f-74b0-4b18-b9bb-82b4d010e441.jpeg','AR2014/035','Hadyetou Aly Camara','48194425','48194425','Cabinet: AUCA ARCHITECTURE','هاديتو عالي كامارا','المكتب: شركة AUCA ARCHITECTURE'),(73,'cheikh18@gmail.com','https://assasmr.duckdns.org/api/anniers/images/60422070-8ac6-4dfa-a504-c354782d9808.png','AR2014/036','Cheikhani Mohameden Nahwi','44144044','44144044','Cabinet: REDWANE','الشيخاني محمدن نحوي','مكتب:الرضوان'),(74,'arktek-arch-ing@outlook.com','https://assasmr.duckdns.org/api/anniers/images/22eb4a8e-9e77-4aed-b379-242f86d2f020.jpeg','AR2014/037','Mohamed Abdel Wedoud Mohamed El kherchy','41443130','34443130','Cabinet: ARKTEK','محمد عبد الودود محمد الخراشي','الشركة: ARKTEK'),(75,'khalil9198@gmail.com','https://assasmr.duckdns.org/api/anniers/images/6531ceb6-a0ad-4bd9-83e6-afefebefedee.jpg','AR2014/038','Mohamed El khalil Elemine','26210112','26210112','Ministére de l\'habitat (MHUAT)','محمد الخليل ألمين','وزارة الإسكان (MHUAT)'),(76,'fatimetounahoui@gmail.com','https://assasmr.duckdns.org/api/anniers/images/d42f2f33-e777-4ea0-8a3f-ca5fc7e49e9f.jpg','AR2014/039','Fatimetou Nahwi','20792525','20792525','Ministére de l\'habitat ( MHUAT)','فاطمة النحوي','وزارة الإسكان (MHUAT)'),(77,'m.khadija@gmail.com','https://assasmr.duckdns.org/api/anniers/images/ef6efeed-d71f-434c-83d1-ae61cdd7d039.png','AR2014/040','Khadija Mohamed Moussa','44224043','44224043','Cabinet: Najah','خديجة محمد موسى','شركة:النجاح'),(78,'Chemed7@hotmail.com','https://assasmr.duckdns.org/api/anniers/images/cd76ebf9-49db-48f7-8fca-0fb9e88cf1f5.png','AR2014/041','Cheikh Ahmed Mohamedou','36106909','36106909','Ministère de l\'habitat (MHUAT)','الشيخ أحمد محمدو','وزارة الإسكان (MHUAT)'),(79,'Ah.ragel2002@gmail.com','https://assasmr.duckdns.org/api/anniers/images/6560c244-97d4-4581-8e76-d9a4ba423c31.png','AR2015/042','Ahmed Mohamed Sidi','36460001','36460001','Cabinet : MIEMAR','أحمد محمد سيدي','مكتب:معمار'),(80,'mettouhamdi04@gmail.com','https://assasmr.duckdns.org/api/anniers/images/e6af1b5c-b5e6-4817-91ae-0c046e7ad553.jpg','AR2015/043','Mettou Hamdi Abdellahi','36330402','36330402','Cabinet : CREA','ميتو حمدي عبد الله','مكتب:CREA Amenagement'),(81,'kelthomsser@yahoo.com','https://assasmr.duckdns.org/api/anniers/images/14efb5d0-6521-437c-a750-8e5975dcec31.jpg','AR2015/044','Oumekelthoum Brahim Brahim Sidina','26448017','32909192','Cabinet: Kelthom Architect Design Intérieur','أم كلثوم إبراهيم إبراهيم سيدينا','مكتب:كلثوم للتصميم الداخلي المعماري'),(82,'kanesawdatou0@gmail.com','https://assasmr.duckdns.org/api/anniers/images/d2fd0c9d-55ad-472a-9ee0-cf40b3d9889d.jpeg','AR2015/045','Sawdatou Hamedine Kane','36861189','43477362','Cabinet : MAHDE DESIGN','سودتو حامدين كان','المكتب:  MAHDE DESIGN'),(83,'bllyarg@gmail.com','https://assasmr.duckdns.org/api/anniers/images/1ed98231-bf06-40db-9f02-6b4fb85bf2c3.png','AR2015/046','Yarg Bilal','49293024','49293024','Cabinet : L\'architecte','يرك بلال','المكتب:L\'ARCHITECTE'),(84,'tabidine@graphiq.archi','https://assasmr.duckdns.org/api/anniers/images/c0f845e6-bd5f-4384-8673-d406cc2364fc.jpg','AR2015/047','Tarba Abidine','33126685','0049 176 30391299','Cabinet: GRAPHIQ','تربه عابدين','المكتب: GRAPHIQ'),(85,'ctijane2@yahoo.fr','https://assasmr.duckdns.org/api/anniers/images/6eafe1ec-8fa1-430d-b954-ee4f87aca62b.png','AR2001/048','Galledou Cheikh Tidiane','47728888','47728888','Cabinet : ADG','خالدو الشيخ تيجان','المكتب:ADG'),(86,'tangi@larchitecte.co','https://assasmr.duckdns.org/api/anniers/images/5a0951f3-fef5-465c-b0f8-ebe797686edf.png','AR2020/049','Hamdinou Mohamed Salem TANGI','36383798','36383798','Cabinet : L\'ARCHITECTE','حامدينو محمد سالم الطنجي','المكتب : L\'ARCHITECTE'),(87,'Imk.architecture.design@gmail.com','https://assasmr.duckdns.org/api/anniers/images/f95184a1-90b3-49fc-85c5-d2e0f1e3efab.png','AR2021/050','Iman Mohamed Khaled','36046161','00221 776675788','Cabinet: IMK','إيمان محمد خالد','المكتب: IMK'),(88,'gailanibarikalla@gmail.com','https://assasmr.duckdns.org/api/anniers/images/aeb63566-afe3-4f9d-9fbf-f50cbec8b9db.jpg','AR2017/051','Ahmed Ghaylani Ahmed','27753289','27753289','Cabinet:L\'ARCHITECTE','أحمد قيلاني أحمد','المكتب:L\'ARCHITECTE'),(89,'kassarchitecture@icloud.com','https://assasmr.duckdns.org/api/anniers/images/80dec2f3-a6a6-4971-b40f-26cdb60545e3.jpg','AR2012/052','Boubacar Ousmane Sy','42867044','47685151','Cabinet: KASS ARCHITECTURE','بوبكر عثمان سي','المكتب: KASS ARCHITECTURE'),(90,'hassanlembard@gmail.com','https://assasmr.duckdns.org/api/anniers/images/0afdd6ca-acb3-41b0-9d56-e682f5a0728d.jpg','AR2021/053','EL Hassen NAH','42712210','42712210','Cabinet : ATIS','الحسن النح','المكتب:ATIS'),(91,'contact@archizone.org','https://assasmr.duckdns.org/api/anniers/images/a029c1cc-cf19-474a-ae88-734b302ea082.jpg','AR2022/054','Ahmed Mohamed Mahmoud','44959599','44989888','Cabinet: ARCHI-ZONE','أحمد محمد محمود','المكتب: آرتشي زون'),(92,'Sambeitsalem@gmail.com','https://assasmr.duckdns.org/api/anniers/images/ae727292-d181-4729-8bd3-27a115eea66c.jpg','AR2016/055','SALEM Sambeit','26966920','26966920','Cabinet: WARDA CONSTRUCTION','سالم صمبيت','الشركة: شركة واردا للإنشاءات');
/*!40000 ALTER TABLE `anniers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categories`
--

DROP TABLE IF EXISTS `categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categories` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `display_order` int DEFAULT NULL,
  `icon` varchar(255) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `is_active` bit(1) NOT NULL,
  `name` varchar(255) NOT NULL,
  `parent_category_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK9il7y6fehxwunjeepq0n7g5rd` (`parent_category_id`),
  CONSTRAINT `FK9il7y6fehxwunjeepq0n7g5rd` FOREIGN KEY (`parent_category_id`) REFERENCES `categories` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categories`
--

LOCK TABLES `categories` WRITE;
/*!40000 ALTER TABLE `categories` DISABLE KEYS */;
INSERT INTO `categories` VALUES (1,1,'fas fa-building','https://assasmr.duckdns.org/uploads/categories/7ebb4519-0cc0-46d9-a105-4dd8532081ca.jpg',_binary '','Construction gros œuvres',NULL),(2,1,'fas fa-building','https://assasmr.duckdns.org/uploads/categories/304b73b6-055c-4d71-956a-55aba0885ccd.jpg',_binary '','Électricité et luminaires',NULL),(3,1,'fas fa-building','https://assasmr.duckdns.org/uploads/categories/70aecb57-d0f8-48e1-80a5-208f7421075c.jpg',_binary '','Menuiserie',NULL),(4,1,'fas fa-building','https://assasmr.duckdns.org/uploads/categories/659c87ac-da55-4a27-ade8-00d072effcff.jpg',_binary '','Plomberie sanitaires',NULL),(5,1,'fas fa-building','https://assasmr.duckdns.org/uploads/categories/c42148f5-8cda-4249-b34f-d3ec749675be.jpg',_binary '','Sécurité incendie',NULL),(6,1,'fas fa-building','https://assasmr.duckdns.org/uploads/categories/c9b58e31-12df-4bac-ad22-e7f8a1c1fd25.jpg',_binary '','Isolation',NULL),(7,1,'fas fa-building','https://assasmr.duckdns.org/uploads/categories/b629c8e6-2a6a-4eaf-a664-c098adab6a66.jpg',_binary '','Revêtement et décoration',NULL),(8,1,'fas fa-building','https://assasmr.duckdns.org/uploads/categories/bf65cd17-b210-4808-9767-1879db9e2b0a.jpg',_binary '','Conditionnement d\'air',NULL),(9,1,'fas fa-building','https://assasmr.duckdns.org/uploads/categories/fdecfce8-1fe6-41b6-a513-f897ee956a45.jpg',_binary '','Terrassement',NULL),(10,1,'fas fa-building',NULL,_binary '','Ingénieur géotechnicien',9),(11,2,'fas fa-building',NULL,_binary '','Technicien géotechnicien',9),(12,3,'fas fa-building',NULL,_binary '','Vente et location d\'égins c travaux',9),(13,4,'fas fa-building',NULL,_binary '','Produits de remblais',9),(14,1,'fas fa-building',NULL,_binary '','Ingénieur GC spécialisé béton armé',1),(15,2,'fas fa-building',NULL,_binary '','Ingénieur GC spécialisé structure métallique',1),(16,3,'fas fa-building',NULL,_binary '','Agrégats',1),(17,4,'fas fa-building',NULL,_binary '','Ciment',1),(18,5,'fas fa-building',NULL,_binary '','fer à béton',1),(19,6,'fas fa-building',NULL,_binary '','Équipements chantier',1),(20,1,'fas fa-building',NULL,_binary '','Technicien d\'Électricité',2),(21,2,'fas fa-building',NULL,_binary '','Vente produits électriques',2),(22,3,'fas fa-building',NULL,_binary '','Vente de luminaires',2),(23,4,'fas fa-building',NULL,_binary '','Ingénieur d\'Électricité',2),(24,1,'fas fa-building',NULL,_binary '','Ingénieur de façades',3),(25,2,'fas fa-building',NULL,_binary '','Technicien Menuiserie',3),(26,3,'fas fa-building',NULL,_binary '','Menuiserie bois',3),(27,4,'fas fa-building',NULL,_binary '','Menuiserie métallique',3),(28,5,'fas fa-building',NULL,_binary '','Menuiserie Aluminium',3),(29,6,'fas fa-building',NULL,_binary '','Habillage de façades',3),(31,1,'fas fa-building',NULL,_binary '','Ingénieur hydro-sanitaire',4),(32,2,'fas fa-building',NULL,_binary '','Technicien hydro-sanitaire',4),(33,3,'fas fa-building',NULL,_binary '',' Technicien d\'ascenseurs',4),(34,4,'fas fa-building',NULL,_binary '','Produits de plomberie et Équipements méca Sanitaires',4),(35,5,'fas fa-building',NULL,_binary '','Ascenseurs',4),(36,1,'fas fa-building',NULL,_binary '','Technicien en sécurité incendie',5),(37,2,'fas fa-building',NULL,_binary '','Technicien en télésurveillance',5),(38,3,'fas fa-building',NULL,_binary '','Produits de sécurité incendie',5),(39,4,'fas fa-building',NULL,_binary '','système de sécurité incendie',5),(40,5,'fas fa-building',NULL,_binary '','télésurveillance',5),(41,6,'fas fa-building',NULL,_binary '','système anti intrusion',5),(42,1,'fas fa-building',NULL,_binary '','Poseur de revêtements',6),(43,2,'fas fa-building',NULL,_binary '','Carreaux de sol',6),(44,3,'fas fa-building',NULL,_binary '','Vente de marbre tg granite',6),(45,4,'fas fa-building',NULL,_binary '','Revêtements souples',6),(46,1,'fas fa-building',NULL,_binary '','Architecte paysagiste',7),(47,2,'fas fa-building',NULL,_binary '','Technicien Paysagiste',7),(48,3,'fas fa-building',NULL,_binary '','Vente de peinture',7),(49,4,'fas fa-building',NULL,_binary '','Vente produits d\'étanchéité',7),(50,5,'fas fa-building',NULL,_binary '','Pépinière',7),(51,1,'fas fa-building',NULL,_binary '','Ingénieur génie climatique',8),(52,2,'fas fa-building',NULL,_binary '','Technicien conditionnement d\'air',8),(53,3,'fas fa-building',NULL,_binary '','Technicien isolation',8),(54,4,'fas fa-building',NULL,_binary '','Vente climatiseur',8),(55,5,'fas fa-building',NULL,_binary '','système de conditionnement',8),(56,6,'fas fa-building',NULL,_binary '','Isolation thermique',8),(57,1,'fas fa-building','https://assasmr.duckdns.org/uploads/categories/914134a1-fbbf-482c-a3d8-15475f4b44c9.png',_binary '','Les Lois',NULL);
/*!40000 ALTER TABLE `categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `certification_activities`
--

DROP TABLE IF EXISTS `certification_activities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `certification_activities` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `action_type` varchar(255) NOT NULL,
  `admin_id` bigint DEFAULT NULL,
  `created_at` datetime(6) NOT NULL,
  `details` varchar(1000) DEFAULT NULL,
  `ip_address` varchar(255) DEFAULT NULL,
  `new_status` enum('PENDING','ACCEPTED','REJECTED','REVOKED') DEFAULT NULL,
  `old_status` enum('PENDING','ACCEPTED','REJECTED','REVOKED') DEFAULT NULL,
  `professional_id` bigint NOT NULL,
  `user_agent` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `certification_activities`
--

LOCK TABLES `certification_activities` WRITE;
/*!40000 ALTER TABLE `certification_activities` DISABLE KEYS */;
/*!40000 ALTER TABLE `certification_activities` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `certification_documents`
--

DROP TABLE IF EXISTS `certification_documents`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `certification_documents` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `document_name` varchar(255) NOT NULL,
  `document_type` enum('CERTIFICATE','NNI','IMAGE') DEFAULT NULL,
  `document_url` varchar(255) NOT NULL,
  `uploaded_at` datetime(6) NOT NULL,
  `professional_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKe8ggs4fmrvdrb0l2siottf2vt` (`professional_id`),
  CONSTRAINT `FKe8ggs4fmrvdrb0l2siottf2vt` FOREIGN KEY (`professional_id`) REFERENCES `professionals` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `certification_documents`
--

LOCK TABLES `certification_documents` WRITE;
/*!40000 ALTER TABLE `certification_documents` DISABLE KEYS */;
/*!40000 ALTER TABLE `certification_documents` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `certification_requests`
--

DROP TABLE IF EXISTS `certification_requests`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `certification_requests` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `admin_notes` varchar(1000) DEFAULT NULL,
  `identity_card_url` varchar(255) DEFAULT NULL,
  `processed_at` datetime(6) DEFAULT NULL,
  `processed_by` bigint DEFAULT NULL,
  `profile_photo_url` varchar(255) DEFAULT NULL,
  `rejection_reason` varchar(255) DEFAULT NULL,
  `specialization_document_url` varchar(255) DEFAULT NULL,
  `status` enum('PENDING','ACCEPTED','REJECTED','REVOKED') NOT NULL,
  `submitted_at` datetime(6) NOT NULL,
  `professional_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_sgavyb74x2erdtrm9003i89bj` (`professional_id`),
  CONSTRAINT `FKhgo04fib3y753awp3hkej9y96` FOREIGN KEY (`professional_id`) REFERENCES `professionals` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `certification_requests`
--

LOCK TABLES `certification_requests` WRITE;
/*!40000 ALTER TABLE `certification_requests` DISABLE KEYS */;
INSERT INTO `certification_requests` VALUES (19,'Professionnel qualifié avec de bonnes références','https://assasmr.duckdns.org/uploads/identity-cards/e2947643-f572-4809-bc82-017112b1361c.jpg','2025-09-01 17:23:10.979201',1,'https://assasmr.duckdns.org/uploads/profiles/010e2acb-5786-4e23-898c-f4ee24f4e8d4.jpg',NULL,'https://assasmr.duckdns.org/uploads/specializations/33545c80-1078-468b-b5ae-8d166d4044ba.pdf','ACCEPTED','2025-09-01 17:22:52.894175',113),(20,'Professionnel qualifié avec de bonnes références','https://assasmr.duckdns.org/uploads/identity-cards/3b19f727-8fa2-4940-be49-d7412941b89a.jpg','2025-09-20 06:20:32.090018',1,'https://assasmr.duckdns.org/uploads/profiles/3c92784b-9fd4-4941-817f-dcb42446b956.jpg',NULL,'https://assasmr.duckdns.org/uploads/specializations/73321476-bad9-425b-a10e-079f12cf8655.pdf','ACCEPTED','2025-09-20 06:03:38.762538',114),(22,'Professionnel qualifié avec de bonnes références','https://assasmr.duckdns.org/uploads/identity-cards/67f158a5-c8c2-4918-a90f-0b7aa814691a.jpg','2025-10-01 22:45:32.966849',1,'https://assasmr.duckdns.org/uploads/profiles/d7d37a77-9bd5-4810-bb98-fc9fc674e954.jpg',NULL,'https://assasmr.duckdns.org/uploads/specializations/c803cdae-238c-4358-b653-8b188d8b7435.pdf','ACCEPTED','2025-10-01 21:19:42.424994',138),(23,'Professionnel qualifié avec de bonnes références','https://assasmr.duckdns.org/uploads/identity-cards/a9996a7c-e98d-4802-b63b-5404269fae55.jpg','2025-10-01 22:45:26.129000',1,'https://assasmr.duckdns.org/uploads/profiles/3d12dbd3-90ab-4864-9966-6c244a6aed54.jpg',NULL,'https://assasmr.duckdns.org/uploads/specializations/c32e448b-3d3b-4e1b-a623-907ef18641b9.pdf','ACCEPTED','2025-10-01 21:22:26.865236',130),(24,'Professionnel qualifié avec de bonnes références','https://assasmr.duckdns.org/uploads/identity-cards/67422167-947b-47e0-b020-826db7535358.jpg','2025-10-01 22:45:24.068420',1,'https://assasmr.duckdns.org/uploads/profiles/776f4d4b-96db-41b5-8239-ef5e62bdbd45.jpg',NULL,'https://assasmr.duckdns.org/uploads/specializations/caacde45-443b-4c56-80ac-307abd9378e1.pdf','ACCEPTED','2025-10-01 21:27:54.260688',133),(25,'Professionnel qualifié avec de bonnes références','https://assasmr.duckdns.org/uploads/identity-cards/a68c6f65-27a9-4b4d-aafd-2e525aae7b8b.jpg','2025-10-01 22:45:14.855326',1,'https://assasmr.duckdns.org/uploads/profiles/7ced1c50-7b44-4de0-90f8-b5cf3817e508.jpg',NULL,'https://assasmr.duckdns.org/uploads/specializations/3938a138-ab70-4d12-a738-596deb408e2a.pdf','ACCEPTED','2025-10-01 21:32:52.056940',127),(26,'Professionnel qualifié avec de bonnes références','https://assasmr.duckdns.org/uploads/identity-cards/399eeb38-9c59-4b91-8c7e-e53136b1415d.jpg','2025-10-01 22:45:10.855902',1,'https://assasmr.duckdns.org/uploads/profiles/fd709e37-b84b-41c4-b4e4-efbdbb7bbf32.jpg',NULL,'https://assasmr.duckdns.org/uploads/specializations/fc5e54d8-5e5c-474e-9669-88ed8bcca8fa.pdf','ACCEPTED','2025-10-01 21:37:27.772552',144),(27,'Professionnel qualifié avec de bonnes références','https://assasmr.duckdns.org/uploads/identity-cards/ba64fb54-1b66-41e1-b2ad-4d6f59623d44.jpg','2025-10-01 22:44:51.518255',1,'https://assasmr.duckdns.org/uploads/profiles/110e3f22-aa54-4037-a8c8-070f0aac43ac.jpg',NULL,'https://assasmr.duckdns.org/uploads/specializations/db38f2c4-83a9-400c-8354-4d854cc9ebb2.pdf','ACCEPTED','2025-10-01 21:45:00.864767',139),(28,'Professionnel qualifié avec de bonnes références','https://assasmr.duckdns.org/uploads/identity-cards/9d2dacc6-de25-4f2d-a996-8e868f8afd49.jpg','2025-10-01 22:44:38.843838',1,'https://assasmr.duckdns.org/uploads/profiles/3b3be8da-e995-4a4a-960e-74fbcc739211.jpg',NULL,'https://assasmr.duckdns.org/uploads/specializations/5435d801-b7ee-483b-86d0-9d1d60e7324f.pdf','ACCEPTED','2025-10-01 21:59:41.708452',124),(29,'Professionnel qualifié avec de bonnes références','https://assasmr.duckdns.org/uploads/identity-cards/264a6f5e-34c6-4107-8849-42c516c9a26d.jpg','2025-10-02 15:16:54.707142',1,'https://assasmr.duckdns.org/uploads/profiles/6057ef4b-55c3-4944-add6-a6e701a15cb5.jpg',NULL,'https://assasmr.duckdns.org/uploads/specializations/046bcc9d-5650-43ce-b5e9-3030c9c25e12.pdf','ACCEPTED','2025-10-02 14:44:21.403354',120),(31,'Professionnel qualifié avec de bonnes références','https://assasmr.duckdns.org/uploads/identity-cards/70581b90-dbab-487c-83cd-e4ad9e34fe5d.jpg','2025-10-28 10:36:34.390427',1,'https://assasmr.duckdns.org/uploads/profiles/104ea0e9-bbad-4684-ae02-da03f68824d8.jpg',NULL,'https://assasmr.duckdns.org/uploads/specializations/0b687367-4098-497f-a03c-468618f621a7.pdf','ACCEPTED','2025-10-23 20:15:53.719870',128),(32,'Professionnel qualifié avec de bonnes références','https://assasmr.duckdns.org/uploads/identity-cards/66a08023-e18f-49ed-aac0-72ee9d568fdb.jpg','2025-10-28 10:36:38.482662',1,'https://assasmr.duckdns.org/uploads/profiles/4358cdb0-24d6-4bd9-8544-af0955b14b8b.jpg',NULL,'https://assasmr.duckdns.org/uploads/specializations/12fb5c5a-b49e-424c-9115-19460fdd5f59.pdf','ACCEPTED','2025-10-27 13:54:03.637960',154),(33,'Professionnel qualifié avec de bonnes références','https://assasmr.duckdns.org/uploads/identity-cards/38a4ad6a-0f35-4950-b159-80de2e47b947.jpg','2025-11-09 23:49:42.680353',1,'https://assasmr.duckdns.org/uploads/profiles/39dd3da4-0110-4268-8163-fec82642984a.jpg',NULL,'https://assasmr.duckdns.org/uploads/specializations/a0de2bf5-45cc-4c8e-8178-1a0614245ef3.pdf','ACCEPTED','2025-11-09 23:48:26.445243',134);
/*!40000 ALTER TABLE `certification_requests` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `certification_stats_view`
--

DROP TABLE IF EXISTS `certification_stats_view`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `certification_stats_view` (
  `id` varchar(255) NOT NULL,
  `avg_processing_time` double DEFAULT NULL,
  `certification_rate` double DEFAULT NULL,
  `certified_count` bigint DEFAULT NULL,
  `pending_count` bigint DEFAULT NULL,
  `rejected_count` bigint DEFAULT NULL,
  `total_count` bigint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `certification_stats_view`
--

LOCK TABLES `certification_stats_view` WRITE;
/*!40000 ALTER TABLE `certification_stats_view` DISABLE KEYS */;
/*!40000 ALTER TABLE `certification_stats_view` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `formations`
--

DROP TABLE IF EXISTS `formations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `formations` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `end_date` datetime(6) DEFAULT NULL,
  `image_arabe` varchar(255) DEFAULT NULL,
  `image_francais` varchar(255) DEFAULT NULL,
  `is_visible` bit(1) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `formations`
--

LOCK TABLES `formations` WRITE;
/*!40000 ALTER TABLE `formations` DISABLE KEYS */;
/*!40000 ALTER TABLE `formations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lois`
--

DROP TABLE IF EXISTS `lois`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `lois` (
  `id_loi` bigint NOT NULL AUTO_INCREMENT,
  `date_creation` datetime(6) DEFAULT NULL,
  `pdf_filename` varchar(255) DEFAULT NULL,
  `pdf_url` varchar(500) DEFAULT NULL,
  `titre` varchar(200) NOT NULL,
  `id_categorie` bigint NOT NULL,
  PRIMARY KEY (`id_loi`),
  KEY `FK9gcm8ry0u3ihajgoyp7lo3esn` (`id_categorie`),
  CONSTRAINT `FK9gcm8ry0u3ihajgoyp7lo3esn` FOREIGN KEY (`id_categorie`) REFERENCES `categories` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lois`
--

LOCK TABLES `lois` WRITE;
/*!40000 ALTER TABLE `lois` DISABLE KEYS */;
INSERT INTO `lois` VALUES (17,'2025-07-13 18:11:30.472908','الهيكل الانشائي .pdf','https://assasmr.duckdns.org/api/lois/pdf/3842476c-f19e-487a-ad6f-1b4a79e0aeca.pdf','الهيكل الانشائي Structures',9),(18,'2025-07-13 18:16:33.809814','المتطلبات الانشائية.pdf','https://assasmr.duckdns.org/api/lois/pdf/922fd3d2-ddff-4206-bf76-26d50e69a12a.pdf','المتطلبات الانشائية Les exigences structurales',1),(19,'2025-07-13 18:23:43.366761','الاضاءة ٠٢.pdf','https://assasmr.duckdns.org/api/lois/pdf/2dcc6fc4-4fd3-4eeb-8e1a-6d0b1469f784.pdf','الاضاءة  Eclairage',2),(20,'2025-07-13 18:27:51.259807','التكييف.pdf','https://assasmr.duckdns.org/api/lois/pdf/7a72223d-b390-4fff-b121-2260f59ee164.pdf','التكييف Climatisation',8),(21,'2025-07-13 18:31:29.623315','الحريق ٠٥.pdf','https://assasmr.duckdns.org/api/lois/pdf/0830fbe6-7486-4646-9807-be9419c49568.pdf','الحرايق Sécurité Incendies',5),(22,'2025-07-13 18:33:48.152581','الحمامات و دورات المياه ٠٤.pdf','https://assasmr.duckdns.org/api/lois/pdf/36a8e39c-7599-479b-87bc-9b22f6450e10.pdf','الحمامات و دورات المياه Les sanitaires',4),(23,'2025-07-13 18:39:55.221011','المتطلبات الصحية ٠٤.pdf','https://assasmr.duckdns.org/api/lois/pdf/2c87ac00-1ff6-4921-a332-718c8f4964b4.pdf','المتطلبات الصحية Exigences Sanitaires',4),(24,'2025-07-13 18:42:16.515725','المتطلبات الكهربائية ٠٢.pdf','https://assasmr.duckdns.org/api/lois/pdf/d1a05c89-3d50-49fb-aecb-58c104d93501.pdf','المتطلبات الكهربائية Exigences Eléctricité',2),(25,'2025-07-13 18:46:39.005587','المتطلبات المعمارية ٠١.pdf','https://assasmr.duckdns.org/api/lois/pdf/08390566-a874-4eae-a0c5-83d3a8c6b676.pdf','المتطلبات المعمارية Exigences Architecturales',7),(26,'2025-07-13 18:54:26.243341','المتطلبات الميكانيكية ٠٤.pdf','https://assasmr.duckdns.org/api/lois/pdf/0331497d-0ad6-4d62-a659-f2f87caea419.pdf','المتطلبات الميكانيكية Exigences mécaniques',4),(27,'2025-07-13 19:00:20.045862','المسابح و احواض الاستحمام .٤.pdf','https://assasmr.duckdns.org/api/lois/pdf/9f5d3ef1-2cac-4c25-9cd1-b1d0169ae35a.pdf','المسابح و احواض الاستحمام Les piscines et les baignoires',4),(28,'2025-07-13 19:05:41.159345','المساحات القابلة للسكن  ٠١.pdf','https://assasmr.duckdns.org/api/lois/pdf/ab8a436c-ad88-4889-9196-f4ebeb02939b.pdf','المساحات القابلة للسكن Les espaces habitables',1),(29,'2025-07-13 19:07:21.508689','النوافذ ٠٣.pdf','https://assasmr.duckdns.org/api/lois/pdf/2806dfc2-011e-4f7a-af58-2c224c309c6e.pdf','النوافذ Les fénétres',3),(30,'2025-07-13 19:09:17.106908','ترشيد الطاقة ٠٢.pdf','https://assasmr.duckdns.org/api/lois/pdf/d6028e4d-8698-4785-808c-9fb08a4001d7.pdf','ترشيد الطاقة Les exigences énergétiques',2),(31,'2025-07-13 19:11:03.799433','عزل الجدران و الاسطح ٠٦.pdf','https://assasmr.duckdns.org/api/lois/pdf/515d2158-4e4e-4eec-bb72-3b7dbec8cdfc.pdf','عزل الجدران و الاسطح Isolation des murs et des toits',6),(32,'2025-07-13 19:12:07.399988','نسق الابواب و النوافذ ٠٣.pdf','https://assasmr.duckdns.org/api/lois/pdf/7a17d3b5-6121-4b97-ab2d-72bcdd0c083e.pdf','نسق الابواب و النوافذ Le style des fenêtres et des portes',3),(33,'2025-07-13 19:41:27.489394','Loi n° 2024-003 relative à l’urbanisme et à la construction.pdf','https://assasmr.duckdns.org/api/lois/pdf/1b7077a9-a219-4fed-9573-c2e8f563243f.pdf','Loi n° 2024-003 relative à l’urbanisme et à la construction',57),(34,'2025-07-13 19:49:28.266545','قانون رقم 2024  -003 المتعلق بالعمران و البناء.pdf','https://assasmr.duckdns.org/api/lois/pdf/1801159a-a0b7-49f2-8515-b428e302c913.pdf','قانون رقم 2024  -003 المتعلق بالعمران و البناء',57),(35,'2025-07-13 20:11:43.395011','loi architecture.pdf','https://assasmr.duckdns.org/api/lois/pdf/fbfaf813-e1ee-4f15-a87b-0dc8e445f9b9.pdf','قانون  العمران و البناء Loi d\'architecture',57);
/*!40000 ALTER TABLE `lois` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `professional_specialties`
--

DROP TABLE IF EXISTS `professional_specialties`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `professional_specialties` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `category_id` bigint NOT NULL,
  `professional_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKpcug9qs77fhnrf4qsdkgrr8q3` (`category_id`),
  KEY `FKk3h4t71u9xqj1oqlcpdsnf1sq` (`professional_id`),
  CONSTRAINT `FKk3h4t71u9xqj1oqlcpdsnf1sq` FOREIGN KEY (`professional_id`) REFERENCES `professionals` (`id`),
  CONSTRAINT `FKpcug9qs77fhnrf4qsdkgrr8q3` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `professional_specialties`
--

LOCK TABLES `professional_specialties` WRITE;
/*!40000 ALTER TABLE `professional_specialties` DISABLE KEYS */;
/*!40000 ALTER TABLE `professional_specialties` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `professionals`
--

DROP TABLE IF EXISTS `professionals`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `professionals` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `certification_status` enum('PENDING','ACCEPTED','REJECTED','REVOKED') NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `description` varchar(1000) DEFAULT NULL,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `phone` varchar(255) NOT NULL,
  `position` varchar(255) DEFAULT NULL,
  `profile_photo_url` varchar(255) DEFAULT NULL,
  `service_type` varchar(255) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `whatsapp` varchar(255) DEFAULT NULL,
  `specialty_id` bigint DEFAULT NULL,
  `user_id` bigint NOT NULL,
  `description_arabic` varchar(1000) DEFAULT NULL,
  `first_name_arabic` varchar(255) DEFAULT NULL,
  `last_name_arabic` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_r9p60nf406aly6o2o701fh7wa` (`user_id`),
  KEY `FK9pa4521nrr217wxk334os84h1` (`specialty_id`),
  CONSTRAINT `FK9pa4521nrr217wxk334os84h1` FOREIGN KEY (`specialty_id`) REFERENCES `categories` (`id`),
  CONSTRAINT `FKgkfsdbkwd8shxk9isiddv2n43` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=172 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `professionals`
--

LOCK TABLES `professionals` WRITE;
/*!40000 ALTER TABLE `professionals` DISABLE KEYS */;
INSERT INTO `professionals` VALUES (113,'ACCEPTED','2025-09-01 17:16:06.031465','Ingénieur en génie civil, spécialiste en structure, Chercheur en matérieaux de construction, Concepteur et conduction de travaux de construction  ','El hacen','Beddy','+22234926794','Nouakchott','https://assasmr.duckdns.org/uploads/profiles/010e2acb-5786-4e23-898c-f4ee24f4e8d4.jpg','Étude','2025-10-02 17:46:53.060246','46048881',14,124,'هندس مدني مختص في الهياكل و باحث في مجال مواد البناء، مصمم و مدير تنفيذ','الحسن','بدي'),(114,'ACCEPTED','2025-09-20 05:55:07.270285','ingénieur en génie civile','el cheikh eltijani','ehwibibe','33405863','nuakchoot','https://assasmr.duckdns.org/uploads/profiles/3c92784b-9fd4-4941-817f-dcb42446b956.jpg','Étude','2025-10-02 18:27:00.083302','33405863',10,125,'مهندس مدني','الشيخ التيجاني','احويبيب'),(115,'PENDING','2025-09-24 13:12:44.277589','grand société de vente des de dicoration à côté de banablahe','elmenara','décoration ','+22236043603','nuakchott',NULL,'Fourniture','2025-10-02 19:04:46.620764','+22236043603',49,126,'مؤسسة كبرى للديكور تقع قرب بانابلاه','المنارة','للديكور'),(118,'PENDING','2025-10-01 02:57:59.744798','Ingénieur géotechnicien','Brahim Salem ','El khoumani','27404968','nuackchott',NULL,'Exécution','2025-10-02 19:10:10.385910','27404968',10,129,'مهندس جيوتقني','ابراهيم السالم','الخوماني'),(119,'PENDING','2025-10-01 03:01:22.274765','Ingénieur géotechnicien','Bebih ','Cheikh Bouke','36039068','nuackchoot',NULL,'Fourniture','2025-10-02 19:10:48.475213','36039068',10,130,'مهندس جيوتقني','ببيه','الشيخ بوكه'),(120,'ACCEPTED','2025-10-01 03:05:08.222761','Ingénieur en génie  climatique','Mohamed EL Moctar ','limam','49688870','nuackchoot','https://assasmr.duckdns.org/uploads/profiles/6057ef4b-55c3-4944-add6-a6e701a15cb5.jpg','Étude','2025-10-02 18:30:13.642597','49688870',51,131,'مهندس تكييف','محمد المختار','لمام'),(121,'PENDING','2025-10-01 03:08:24.429037','Technicien en conditionnement d’air','Mohamed Lemine ','SIDI','30659707','Nouakchoot',NULL,'Exécution','2025-10-02 19:15:41.979821','30659707',52,132,'فني تكييف الهواء','محمد لمين','سيدي'),(122,'PENDING','2025-10-01 03:11:43.424083','Nous vous proposons des climatiseurs et leurs accessoires de haute qualité et garantis. L’emplacement : près de Atak El Khir, dans l’immeuble selsebile','SELSEBIL','Darelkair','20110408','Nouakchoot',NULL,'Fourniture','2025-10-02 19:29:36.541651','20110408',54,133,'نحن نوفر لكم المكيفات و مستلزماتها بجودة عالية و مضمونه الموقع قرب أتاك الخير في عمارة سلسبيل','سلسبيل','دار الخير'),(123,'PENDING','2025-10-01 03:15:00.469568','Ingénieur en decoration','Mohamed Abdel Mejid ','BOUH','33385050','Nouakchoot',NULL,'Étude','2025-10-02 19:37:37.591717','33385050',47,134,'مهندس ديكور','محمد عبد المجيد','ابوه'),(124,'ACCEPTED','2025-10-01 03:18:41.927442','Entreprise de décoration intérieure','ALQDS','decoration','47385050','Nouakchoot','https://assasmr.duckdns.org/uploads/profiles/3b3be8da-e995-4a4a-960e-74fbcc739211.jpg','Exécution','2025-10-02 18:32:40.255178','47385050',47,135,'مؤسسة لهندسة ديكور','القدس','للديكور'),(125,'PENDING','2025-10-01 03:22:02.290332','Ingénieur  en hydraulique',' MOHAMED MAHMOUD ','ABDI ','36114445','Nouakchott',NULL,'Étude','2025-10-02 19:41:39.605254','36114445',31,136,'مهندس هيدروليك','محمد محمود','عبدي'),(126,'PENDING','2025-10-01 03:25:19.620129','Technicien hydro-sanitaire','Cheikh ','Mouloud','27814999','Nouakchott',NULL,'Exécution',NULL,'27814999',32,137,'تقني هيدروليك','الشيخ','مولود'),(127,'ACCEPTED','2025-10-01 03:29:29.966840','une Quincaillerie A coté de parc de chahrezade','Quincaillerie ','Enejah','37078080','Nouakchott','https://assasmr.duckdns.org/uploads/profiles/7ced1c50-7b44-4de0-90f8-b5cf3817e508.jpg','Fourniture','2025-10-02 18:33:45.202689','37078080',34,138,'مؤسسة لبيع مواد السباكة بالقرب من منتزه شهرزاد','كانكييري','النجاح'),(128,'ACCEPTED','2025-10-01 04:20:32.553295','Ingénieur en génie électrique','AHMED ','HAMZA','37146314','Nouakcuott','https://assasmr.duckdns.org/uploads/profiles/104ea0e9-bbad-4684-ae02-da03f68824d8.jpg','Étude','2025-10-28 10:36:36.460479','37146314',23,139,'مهندس كهربائي','أحمد','حمزة'),(129,'PENDING','2025-10-01 04:23:21.674850','Ingénieur en génie électrique','YAHYA ','Mouhamed Abdellahi','33387565','Nouakchott',NULL,'Exécution','2025-10-02 19:47:50.810052','33387565',23,140,'مهندس كهربائي','يحي','محمد عبد الله'),(130,'ACCEPTED','2025-10-01 04:28:59.167982','Route Sokok, à 100 m du restaurant El-Khal, direction Chahrazad, siège à gauche','Norma ','Construction Moderne','24040303','Nuakchott','https://assasmr.duckdns.org/uploads/profiles/3d12dbd3-90ab-4864-9966-6c244a6aed54.jpg','Fourniture','2025-10-01 22:45:26.561601','24040303',21,141,'عنوان المحل طريق صكوك – على بُعد 100 متر من مطعم الخال باتجاه شهرزاد، المقر على اليسار','نورما','لموارد البناء العصرية'),(131,'PENDING','2025-10-01 04:33:02.077041','Technicien en menuiserie','Elhassen ','Mohamed welaty','37342920','Nouakchott',NULL,'Étude','2025-10-02 19:49:49.378306','37342920',25,142,'تقني نجارة','الحسن','محمد الولاتي'),(132,'PENDING','2025-10-01 04:36:53.088597','Technicien en menuiserie','Isselmou ','Mohamed Essalem','31410834','Nouakchoot',NULL,'Exécution','2025-10-02 19:50:38.409456','31410834',25,143,'تقني نجارة','ايسلم','محمد السالم'),(133,'ACCEPTED','2025-10-01 04:40:06.704323','Vente de tous les articles de menuiserie','ESSAVA','COMMERCE ET SERVICES','34444830','Nouakchott','https://assasmr.duckdns.org/uploads/profiles/776f4d4b-96db-41b5-8239-ef5e62bdbd45.jpg','Fourniture','2025-10-02 18:37:37.513509','34444830',27,144,'بيع جميع بضائع النجارة','الصفاء','للتجارة و الخدمات'),(134,'ACCEPTED','2025-10-01 04:43:47.590992','Ingénieur en génie civile','hamoud',' oumar','46419694','Nouakchott','https://assasmr.duckdns.org/uploads/profiles/39dd3da4-0110-4268-8163-fec82642984a.jpg','Étude','2025-11-09 23:49:45.362154','46419694',14,145,'مهندس مدني','حمود','عمر'),(135,'PENDING','2025-10-01 04:50:28.152953','Ingénieur en génie civil','Mohamed El Moustafa',' Dahane','33339388','Nouakchott',NULL,'Exécution','2025-10-02 19:52:46.503323','33339388',14,146,'مهندس مدني','محمد المصطفى','دحان'),(136,'PENDING','2025-10-01 04:53:20.198902','Ingénieur en génie civil','Saad','Lhreitani','44393824','Nouakchott',NULL,'Exécution','2025-10-02 19:53:45.484237','44393824',14,147,'مهندس مدني','السعد','لحريطاني'),(137,'PENDING','2025-10-01 04:56:28.601073','Ingénieur en génie civil','Mohamed Abdellahi','EBBAH','32773487','Nouakchoot',NULL,'Exécution','2025-10-02 19:54:18.265135','32773487',14,148,'مهندس مدني','محمد عبد الله','اباه'),(138,'ACCEPTED','2025-10-01 04:59:50.941478','Notre entreprise est à votre service dans les secteurs du bâtiment, de l’eau et de l’électricité, en offrant :\n\nDes études techniques et d’ingénierie\n\nL’exécution de projets selon les plus hauts standards de qualité\n\nLa fourniture de matériaux et d’équipements\n\n\nNous disposons d’une équipe d’ingénieurs experts afin de garantir des solutions complètes et efficaces qui répondent à vos besoins.','SOLID','BUILD','46 04 88 81','Nouakchott','https://assasmr.duckdns.org/uploads/profiles/d7d37a77-9bd5-4810-bb98-fc9fc674e954.jpg','Fourniture','2025-11-09 12:58:36.649445','46 04 88 81',19,149,'شركتنا في خدمتك في قطاعات البناء والمياه والكهرباء، حيث نقدم: الدراسات الفنية والهندسية و تنفيذ المشاريع بأعلى معايير الجودة توفير المواد والمستلزمات يعمل لدينا فريق من المهندسين الخبراء لضمان تقديم حلول متكاملة وفعالة تلبي احتياجاتكم.','سوليد','بويلد'),(139,'ACCEPTED','2025-10-01 05:04:47.924674','Entreprise de construction offrant un service d’étude d’isolation','COLORE','DE MAROC','20439698','Nouakchott','https://assasmr.duckdns.org/uploads/profiles/110e3f22-aa54-4037-a8c8-070f0aac43ac.jpg','Étude','2025-10-02 18:44:19.258757','20439698',42,150,'مؤسسة بناء توفر خدمة دراسة العزل','الوان','المغرب'),(140,'PENDING','2025-10-01 05:13:16.658038','Poseur de sols et de revêtements','MOHAMED ','SIDI','46101004','Nouakchott',NULL,'Exécution','2025-10-02 20:09:38.330328','46101004',42,151,'عامل تركيب الارضيات و التكسية','محمد','سيدي'),(141,'PENDING','2025-10-01 05:17:27.014967','Une entreprise qui fournit tous les matériaux et moyens d\'isolation','KHARDEWATE ','DAMAS','20444469','Nouakchott',NULL,'Fourniture','2025-10-02 20:15:39.481693','20444469',44,152,'مؤسسة توفر جميع مواد و تجهيزات العزل','خردوات','دمشق'),(142,'PENDING','2025-10-01 05:20:24.186017','Technicien en sécurité incendie','Mohamed Mahmoude ',' EL Hacen','46110666','Nouakchott',NULL,'Étude','2025-10-02 20:20:39.003553','46110666',36,153,'تقني فني مكافحة الحرائق','محمد محمود','الحسن'),(143,'PENDING','2025-10-01 05:23:27.988657','Entreprise de systèmes de lutte contre l\'incendie','Atlantique ','service','33221201','Nouakchott',NULL,'Exécution','2025-10-02 20:22:25.642940','33221201',39,154,'مؤسسة لأنظمة مكافحة الحرائق','آتلانتيك','للخدمات'),(144,'ACCEPTED','2025-10-01 05:28:56.563829','Entreprise fournissant des matériaux de lutte contre l’incendie','Zein ','matériaux de construction','20415383','Nouakchott','https://assasmr.duckdns.org/uploads/profiles/fd709e37-b84b-41c4-b4e4-efbdbb7bbf32.jpg','Fourniture','2025-10-02 19:03:13.732890','20415383',38,155,'مؤسسة توفر مواد مكافحة الحرائق','زين','لمواد البناء'),(146,'PENDING','2025-10-21 09:02:53.678174','je suis un infographiste designer et entrepreneur mauritanien, fondateur et directeur de la société Ants Will, spécialisée dans la décoration, la menuiserie, la communication visuelle et la gravure CNC. \nParmi nos réalisations phares, on retrouve la décoration du Sommet Arabe de 2016, la communication visuelle de grandes entreprises telles que Kinross et Kosmos, ainsi que des projets architecturaux et décoratifs emblématiques comme la première façade gravée en Alucobond en Mauritanie pour la banque GBM, la façade de l’hôtel Dolphin, et l’aménagement de plusieurs boutiques, pharmacies et résidences.\n\nDiplômé d’une licence en informatique et d’un BTS en infographie obtenu au Maroc, je suis également reconnu comme le premier Mauritanien à introduire la culture CNC, révolutionnant ainsi la gravure sur bois.','emssabou','Bechir','+22222822444','Tevragh zeina ',NULL,'Exécution','2025-11-09 13:33:52.392676','+22236108606',46,157,'أنا مصمم جرافيك ورائد أعمال موريتاني، مؤسس ومدير شركة Ants Will المتخصصة في الديكور، النجارة، الاتصال البصري، والنقش باستخدام آلة CNC.من بين أبرز إنجازاتنا: زخرفة القمة العربية لعام 2016، والإشراف على الاتصال البصري لشركات كبرى مثل Kinross وKosmos، بالإضافة إلى تنفيذ مشاريع معمارية وزخرفية بارزة، مثل أول واجهة منقوشة على مادة الألكوبوند في موريتانيا لصالح بنك GBM، وواجهة فندق Dolphin، وكذلك تصميم وتأثيث العديد من المتاجر والصيدليات والمساكن.أنا حاصل على إجازة في الإعلاميات ودبلوم تقني (BTS) في الجرافيك من المغرب، كما أُعترف بي كـ أول موريتاني أدخل ثقافة استخدام تقنية CNC، مساهماً بذلك في ثورة فن النقش على الخشب في البلاد.','بوصابو','بشير'),(147,'PENDING','2025-10-21 09:26:08.249458','Ingénieur en génie civil ','Mohameden ','Elkory','+22249588837','Nktt',NULL,'Étude',NULL,'+22249588837',14,158,'مهندس مدني','محمدن','الكوري'),(148,'PENDING','2025-10-21 22:57:47.926790','ingénieur travaux publics ','Elkhalifa','Cheikh Ahmed ','+22248939345','ksar ',NULL,'Exécution',NULL,'+22234939345',15,159,'اشغال عامة مدني','الخليفة','الشيخ أحمد'),(149,'PENDING','2025-10-22 13:27:50.613010','Notre entreprise est spécialisée dans le bâtiment et les travaux publics, sachant que le professionnalisme et la gestion sont notre ligne de conduite.','emapac','emapac','+22242923044',' https://maps.google.com/?q=18.114187,-15.961961',NULL,'Exécution',NULL,'+22222363646',1,160,'شركتنا متخصصة في البناء و الأشغال العامة علما ان المهنية و الإدارة هما نهجنا','إمابك','إمابك'),(150,'PENDING','2025-10-26 19:40:27.088431','ingénieur ','med','sidi','+22243312070','nuakchoot',NULL,'Étude',NULL,'+22243312070',15,161,'مهندس','محمد','سيدي'),(153,'PENDING','2025-10-27 13:17:09.184834','Génie Civil, Électricité, Plomberie,Assainissement ','Aly','DIAKITE','+22246488112','El mina lot 144',NULL,'Exécution',NULL,'+22246488112',1,164,'هندسة مدنية، كهرباء، سباكة، صرف صحي','علي','دجاكيتي'),(154,'ACCEPTED','2025-10-27 13:35:24.970352','construction et travaux publics','Pro bati','batiment et travaux public','+22234165247','Noukchott','https://assasmr.duckdns.org/uploads/profiles/4358cdb0-24d6-4bd9-8544-af0955b14b8b.jpg','Exécution','2025-10-28 10:36:40.427669','+22234165247',1,165,'البناء و الأشغال العامة','برو باتي','للبناء و الأشغال العامة'),(155,'PENDING','2025-10-27 19:58:51.214323','Controle Technique ','Abdellah ','Mohamedou','+22204657882','276 Lotissement Sukuk TV ZEINA-Nouakchott',NULL,'Étude',NULL,'+22246578823',15,166,'مراقبة تقنية','عبد الله','محمدو'),(156,'PENDING','2025-10-27 22:29:46.723242','Société Mohamed El Amin Chamekh et Frères de Bâtiment et Travaux Publics','chamekh','BochTojni','+22234128026','توجونين الشمالية',NULL,'Exécution',NULL,'+22222338705',1,167,'مؤسسة محمد الامين شامخ وأخوانه البناء ولأشقال العمومية','شامخ','بوشتوجن'),(157,'PENDING','2025-10-27 22:41:00.980334','Société Mohamed El Amin Chamekh et Frères de Bâtiment et Travaux Publics','mohamedlamine','ebouchama','+22234128026','توجونين الشمالية',NULL,'Exécution',NULL,'+22222338705',1,168,'مؤسسة محمد الامين شامخ وأخوانه البناء ولأشقال العمومية','محمد لمين','بوشامة'),(158,'PENDING','2025-10-29 20:29:30.986773','Ahwyd','Ahmed Mahmoud ','Eymane','+22237151017','nktt',NULL,'Étude',NULL,'+22237151017',14,169,'احويد','أحمد محمود','إيمان'),(159,'PENDING','2025-11-09 17:55:17.442151','commercant de produits luminaires','med','moussa','43312070','Nuakchoott',NULL,'Fourniture',NULL,'37343447',22,170,'تاجر اضاءة','محمد','موسى'),(165,'PENDING','2025-12-08 10:48:33.914457','Étude et exécution de tous types de travaux en béton, métalliques et même bitumineux.','Ahmedou','Taleb Ahmed','+22220567785','نواكشوط',NULL,'Exécution',NULL,'+22220567785',14,176,'دراسة و تنفيذ جميع أنواع الأعمال بيـتونية و معدنية و حتى بيـتومينية','أحمدو','الطالب أحمد'),(166,'PENDING','2025-12-11 17:18:49.817940','plombier traditionnel','issaga','Konte','49552523','nuakchoot',NULL,'Exécution',NULL,'49552523',32,177,'سباك تقليدي','إسحاقا','كونتي'),(167,'PENDING','2025-12-11 17:29:41.264996','Electricien traditionnel','nala','kane','36818427','nuakchoot',NULL,'Exécution',NULL,'36818427',20,178,'كهربائي تقليدي','نالا','كان'),(168,'PENDING','2025-12-11 17:35:54.372092','Electricien traditionnel','abou','si','41133570','NOUAKCHOOT',NULL,'Exécution',NULL,'41133570',20,179,'كهربائي تقليدي','آبو','سي'),(169,'PENDING','2025-12-11 17:39:25.874718','plombier traditionnel','mamadou','si','49573244','Nouakchoot',NULL,'Exécution',NULL,'49573244',32,180,'سباك تقليدي','مامادو','سي'),(170,'PENDING','2025-12-11 17:43:19.606207','plombier traditionnel','mamadou','si','49573244','Nouakchoot',NULL,'Exécution',NULL,'49573244',32,181,'سباك تقليدي','مامادو','سي'),(171,'PENDING','2025-12-11 17:45:47.929027','Electricien traditionnel','djibrile','si','46419152','ksar',NULL,'Exécution',NULL,'46419152',20,182,'كهربائي تقليدي','جبريل','سي');
/*!40000 ALTER TABLE `professionals` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `publications`
--

DROP TABLE IF EXISTS `publications`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `publications` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `email` varchar(255) NOT NULL,
  `end_date` datetime(6) NOT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `place` int NOT NULL,
  `start_date` datetime(6) NOT NULL,
  `title` varchar(255) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=110 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `publications`
--

LOCK TABLES `publications` WRITE;
/*!40000 ALTER TABLE `publications` DISABLE KEYS */;
INSERT INTO `publications` VALUES (35,'2025-06-30 01:39:34.804089','ass12@gmail.com','2025-11-08 09:00:00.000000','https://assasmr.duckdns.org/uploads/publications/1eb7ba7d-96b6-46c9-92d6-68c7a5c750ef.jpg',10,'2025-10-31 09:00:00.000000','test154','2025-10-30 08:46:38.518019'),(36,'2025-06-30 01:39:43.562427','ass11@gmail.com','2025-10-31 09:00:00.000000','https://assasmr.duckdns.org/uploads/publications/494290dc-c3c3-4bb3-886d-a23397c7d1f5.jpg',11,'2025-07-01 09:00:00.000000','test1',NULL),(37,'2025-06-30 01:39:51.375366','ass10@gmail.com','2025-11-08 09:00:00.000000','https://assasmr.duckdns.org/uploads/publications/6e92ed35-d2fd-4ce5-bb3b-dfd3c3f10947.jpg',7,'2025-10-31 09:00:00.000000','test123','2025-10-30 08:44:00.498293'),(40,'2025-06-30 01:40:20.178563','ass8@gmail.com','2025-11-08 09:00:00.000000','https://assasmr.duckdns.org/uploads/publications/0b1ee805-8f75-4672-9147-bd41899a68f5.jpg',5,'2025-10-31 09:00:00.000000','test15','2025-10-30 08:33:50.905682'),(41,'2025-06-30 01:40:31.410368','ass7@gmail.com','2025-11-08 09:00:00.000000','https://assasmr.duckdns.org/uploads/publications/234bca7d-4a97-4391-b8b4-669a3c9f4f95.jpg',4,'2025-10-31 09:00:00.000000','test14','2025-10-30 08:32:53.806814'),(42,'2025-06-30 01:40:39.337102','ass6@gmail.com','2025-11-08 09:00:00.000000','https://assasmr.duckdns.org/uploads/publications/3476ba79-3842-47a0-a202-cc7f574e4e1b.jpg',3,'2025-10-31 09:00:00.000000','test12','2025-10-30 08:31:36.937271'),(46,'2025-06-30 01:41:08.321728','ass2@gmail.com','2025-10-31 09:00:00.000000','https://assasmr.duckdns.org/uploads/publications/c169c93d-8d29-4e0d-a929-2129ee19409c.jpg',1,'2025-07-01 09:00:00.000000','test12','2025-09-13 19:18:34.484953'),(58,'2025-07-06 23:43:38.852016','22003@example.com','2025-11-08 09:00:00.000000','https://assasmr.duckdns.org/uploads/publications/ed2ca535-9c7d-456c-a770-f06ebc118470.jpeg',7,'2025-10-31 09:00:00.000000','pppppp','2025-10-30 08:43:08.587569'),(62,'2025-07-06 23:44:36.417808','22007@example.com','2025-11-08 09:00:00.000000','https://assasmr.duckdns.org/uploads/publications/95674968-7f05-4934-adf2-9d58a5890142.jpeg',2,'2025-10-31 07:47:00.000000','hhhhpp','2025-10-30 08:29:41.937270'),(63,'2025-07-06 23:44:48.134420','22008@example.com','2025-11-08 09:00:00.000000','https://assasmr.duckdns.org/uploads/publications/9b141bf8-975a-4367-97f0-d3b841f7621f.jpeg',3,'2025-10-31 09:00:00.000000','hhhhhhHH','2025-10-30 08:30:56.551022'),(64,'2025-07-06 23:44:56.848412','22009@example.com','2025-11-08 09:00:00.000000','https://assasmr.duckdns.org/uploads/publications/a479b052-731a-45e5-aa5e-ff56dccfc66d.jpeg',1,'2025-10-31 09:00:00.000000','hhhhhhHH','2025-10-30 08:29:11.660967'),(65,'2025-07-06 23:45:07.074649','22010@example.com','2025-11-08 09:00:00.000000','https://assasmr.duckdns.org/uploads/publications/a0cc7ebe-9acf-48ae-a431-105351f6e112.jpeg',4,'2025-10-31 09:00:00.000000','hhhhhhHH','2025-10-30 08:32:12.580644'),(66,'2025-07-06 23:45:13.547318','22011@example.com','2025-11-08 09:00:00.000000','https://assasmr.duckdns.org/uploads/publications/be0d9e87-f8b3-4f03-8e47-815c8919834a.jpeg',5,'2025-10-31 09:00:00.000000','hhhhhhHH','2025-10-30 08:33:13.119240'),(67,'2025-07-06 23:45:22.620100','22012@example.com','2025-11-08 09:00:00.000000','https://assasmr.duckdns.org/uploads/publications/df408332-0e21-47bf-b678-4e34ce24d8f7.jpeg',6,'2025-10-31 09:00:00.000000','hhhhhhHH','2025-10-30 08:42:07.472561'),(68,'2025-07-06 23:45:31.600424','22013@example.com','2025-11-08 09:00:00.000000','https://assasmr.duckdns.org/uploads/publications/7584f53b-1334-410d-929c-5dab739ffd6c.jpeg',8,'2025-10-31 09:00:00.000000','hhhhhhHH','2025-10-30 08:44:42.576835'),(69,'2025-07-06 23:46:04.647968','22014@example.com','2025-11-08 09:00:00.000000','https://assasmr.duckdns.org/uploads/publications/0cbd6951-9279-4b03-9775-396be8b9c1d9.jpeg',9,'2025-10-31 09:00:00.000000','hhhhhhHH','2025-10-30 08:45:34.430907'),(70,'2025-07-06 23:46:10.289718','22015@example.com','2025-11-08 09:00:00.000000','https://assasmr.duckdns.org/uploads/publications/99f2918a-901a-4bc5-9ea3-1d733f1fea56.jpeg',10,'2025-10-31 09:00:00.000000','hhhhhhHH','2025-10-30 08:47:14.677286'),(88,'2025-07-07 00:24:42.986648','2200018@example.com','2025-11-08 09:00:00.000000','https://assasmr.duckdns.org/uploads/publications/a708fd3b-7d1f-4520-bbc9-e88af94c1833.jpg',6,'2025-10-31 09:00:00.000000','hhhhhhHH','2025-10-30 08:42:45.698847'),(104,'2025-10-30 08:07:47.865383','applicationassascontact@gmail.com','2025-11-08 22:00:00.000000','https://assasmr.duckdns.org/uploads/publications/c4f950d5-0d9f-41c8-a3a5-88d8692a80b0.jpg',11,'2025-10-31 08:05:00.000000','moussa1',NULL),(105,'2025-10-30 08:10:06.196422','fatimetoubeddy694@gmail.com','2025-11-07 18:00:00.000000','https://assasmr.duckdns.org/uploads/publications/4873706e-d6f3-4807-a60f-58d33847a818.jpg',8,'2025-10-31 10:00:00.000000','sidisa','2025-10-30 08:45:08.937507'),(106,'2025-10-30 08:13:37.468648','medmoussa3734@gmail.com','2025-11-07 18:00:00.000000','https://assasmr.duckdns.org/uploads/publications/48bb86ae-cdaa-4919-916d-7086e9c8170b.jpg',12,'2025-10-31 10:00:00.000000','tvarahe',NULL),(107,'2025-10-30 08:18:38.619606','maroccolore968@gmail.com','2025-11-08 18:00:00.000000','https://assasmr.duckdns.org/uploads/publications/4e922a3c-2f0f-45d6-94c9-2743c4535cb6.jpg',12,'2025-10-31 10:00:00.000000','saiide',NULL),(108,'2025-10-30 08:24:38.580464','elnejah02@gmail.com','2025-11-08 18:00:00.000000','https://assasmr.duckdns.org/uploads/publications/b397831d-e7d1-4f89-887e-f6170f8a74d4.jpg',9,'2025-10-31 10:00:00.000000','elnejahe','2025-10-30 08:46:05.490723'),(109,'2025-10-30 08:27:53.265802','m88387989@gmail.com','2025-11-08 18:00:00.000000','https://assasmr.duckdns.org/uploads/publications/4bf19b52-cc6b-4733-97cd-6b8fd4c8eecb.jpg',2,'2025-10-31 10:00:00.000000','mohamed','2025-10-30 08:30:36.012362');
/*!40000 ALTER TABLE `publications` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `regulations`
--

DROP TABLE IF EXISTS `regulations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `regulations` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `category` varchar(255) NOT NULL,
  `content` varchar(5000) NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `document_url` varchar(255) DEFAULT NULL,
  `is_active` bit(1) NOT NULL,
  `title` varchar(255) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `updated_by` bigint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `regulations`
--

LOCK TABLES `regulations` WRITE;
/*!40000 ALTER TABLE `regulations` DISABLE KEYS */;
/*!40000 ALTER TABLE `regulations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reviews`
--

DROP TABLE IF EXISTS `reviews`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reviews` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `client_email` varchar(255) NOT NULL,
  `client_name` varchar(255) DEFAULT NULL,
  `comment` varchar(1000) DEFAULT NULL,
  `created_at` datetime(6) NOT NULL,
  `rating` int NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `professional_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKavfn1tshygiol8oopimv5tgmu` (`professional_id`),
  CONSTRAINT `FKavfn1tshygiol8oopimv5tgmu` FOREIGN KEY (`professional_id`) REFERENCES `professionals` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reviews`
--

LOCK TABLES `reviews` WRITE;
/*!40000 ALTER TABLE `reviews` DISABLE KEYS */;
INSERT INTO `reviews` VALUES (18,'user@assas.com','Utilisateur Assas','Excellent service professionnel','2025-11-15 04:44:16.558129',5,'2025-11-15 04:44:16.558132',113),(19,'medmoussa3734@gmail.com','محمد موسى','مهندس جيد','2025-11-15 04:45:11.250162',1,'2025-11-15 04:45:11.250162',113);
/*!40000 ALTER TABLE `reviews` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `email` varchar(255) NOT NULL,
  `email_verified` bit(1) NOT NULL,
  `is_active` bit(1) NOT NULL,
  `last_login` datetime(6) DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `role` enum('CLIENT','PROFESSIONAL','ADMIN') NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_6dotkott2kjsp8vw4d0m25fb7` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=183 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (3,'2025-06-26 03:06:12.915568','ahmed.Kh@hgg.com',_binary '',_binary '','2025-06-26 03:29:32.705481','$2a$10$wB7qzRdersDKD8WEA7xtAuYgjPYLQK/i.rvi8iog4L1PKqiycdKhu','ADMIN'),(7,'2025-06-26 13:30:56.800440','test.i@gmail.com',_binary '',_binary '','2025-06-26 14:11:19.227583','$2a$10$hmcGnwSMNIatL/0IByY8Ke1xdpnXQ31PtHxsBCIRkT4NiXZgt0ADG','ADMIN'),(11,'2025-06-26 14:05:42.072160','22034@supnum.mr',_binary '\0',_binary '',NULL,'$2a$10$GSfR1t.I4Sj0bC9xRdq0aeIwnPqag9/UTt7clVQJgYkA71rWjPNee','PROFESSIONAL'),(13,'2025-06-26 16:33:08.805083','admin@gmail.com',_binary '\0',_binary '',NULL,'$2a$10$LjTQhYH./txrWxvYA7QhUuuEnXlKNq9L9ZU70WWWOAc6.3w9M89ky','PROFESSIONAL'),(14,'2025-06-26 16:36:04.194410','uu.Tggg@email.com',_binary '',_binary '','2025-07-01 16:27:19.237833','$2a$10$4PP3m6x1ZYY6wjXKQ/twG.BkraAzIV/1XxO1eV6C6F9mpfvih//cm','ADMIN'),(15,'2025-06-26 16:38:37.665567','adming@gmail.com',_binary '',_binary '',NULL,'$2a$10$8xcCx3pM2QfVoDF/4pm8ROxIieWRw/3dCggUSekKseKlkljHeKe06','ADMIN'),(16,'2025-06-27 01:33:52.119163','ben@gmail.com',_binary '',_binary '','2025-06-27 01:50:02.709611','$2a$10$2p4h2OGp8NSB5cgpDkm0hecwAbMZRGJkPG8Ko8kquVv3iD/g1p7ni','ADMIN'),(17,'2025-06-27 01:43:59.499580','admin2@gmail.com',_binary '',_binary '','2025-12-11 17:06:45.597547','$2a$10$uuxImkmaJNQ0P9p3PhFuTeKEzhpRkM1O.4fXWexJYckY.1MfdPa2.','ADMIN'),(18,'2025-06-27 01:50:12.587449','souleymanbaba94@gmail.com',_binary '',_binary '','2025-06-27 03:06:25.527661','$2a$10$uhjDxGZsSj4Fm3Pbi.K5t.76VbwbQ7BlvdNQeAR1kU11QKUDXJ2WK','PROFESSIONAL'),(20,'2025-06-27 10:45:16.132042','22028@supnum.mr',_binary '',_binary '','2025-06-28 02:02:26.400745','$2a$10$GDoYKpp0JAu0VlTKa1DZfujjEHAHGkXCngJ2dEu9dfr7cqgNZ09DG','PROFESSIONAL'),(61,'2025-07-04 23:18:09.980339','adminmed@gmail.com',_binary '',_binary '',NULL,'$2a$10$zPdx2MS14IHC6xpFbaH.wu8Cj.Cv2QX6LPSKdsHaUCsjzHlGxhPsa','ADMIN'),(124,'2025-09-01 17:16:05.736930','beddyelhacen@gmail.com',_binary '',_binary '','2025-09-01 17:18:16.631750','$2a$10$4gb5/wNumMR78oPLvoHaI.Og6x9RI7IOTe9.gJXF3kgSRzbWINANu','PROFESSIONAL'),(125,'2025-09-20 05:55:05.521188','Cheikhtijani442@gmail.com',_binary '',_binary '','2025-09-20 05:57:22.324450','$2a$10$hkKqp2zGb1fFaXbvfEJgEeA5liVEjftpqeGWQiz5MzC3W7Los9IJy','PROFESSIONAL'),(126,'2025-09-24 13:12:42.487212','damansouvi@gmail.com',_binary '',_binary '',NULL,'$2a$10$oYXh7xxSe.NfhagXAj./qOJzbMFvzp3GfqJjETAgjQTzDBJugekOK','PROFESSIONAL'),(129,'2025-10-01 02:57:59.228732','khomanibrahim42@gmail.com',_binary '',_binary '',NULL,'$2a$10$pyzMvOwjSfKruFr0MMnj8uyXeDhOmUq8Qq92g6fDAghQ08AXY0IC2','PROFESSIONAL'),(130,'2025-10-01 03:01:22.038404','Dbsbebih33@gmail.com',_binary '',_binary '',NULL,'$2a$10$Aj/pyxvYA2BiMhCTAZpBh.UOSHCgR3a2LV8hZD74C4AlPxUowOUMi','PROFESSIONAL'),(131,'2025-10-01 03:05:08.000485','limamy20@gmail.com',_binary '',_binary '','2025-10-02 14:42:56.501203','$2a$10$S.zCB2EwXle9rFZiViewQ.u0aLC2AZddzd.y49pFWhu.nqDs1InW6','PROFESSIONAL'),(132,'2025-10-01 03:08:24.213791','mohamedleminne.sidi@gmail.com',_binary '',_binary '',NULL,'$2a$10$4P94r1FIUprgx0C8HwnxPO3L9uiOv.Q7EZnfnaGI.YM5vMnsxlrxe','PROFESSIONAL'),(133,'2025-10-01 03:11:43.176064','darelkhair77@gmail.com',_binary '',_binary '',NULL,'$2a$10$9J6HzZOpMoqwc7.XgJHN/eERQ1QI8JjZGTanlb46U5scTtsdO6HWy','PROFESSIONAL'),(134,'2025-10-01 03:15:00.229621','Abdelmejidbouh@gmail.com',_binary '',_binary '',NULL,'$2a$10$qC.EvnjzwQxQadEFxVo4juOAKdIHCzLdmRV7k0f267yAGGi5ToEm.','PROFESSIONAL'),(135,'2025-10-01 03:18:41.708414','Fatmabouh30@gmail.com',_binary '',_binary '','2025-10-01 21:55:21.451971','$2a$10$rksIxHDkAZLVCo1iRsZlpO1LqRXTo0uN2STeVdyljp9Rj20zZKoGC','PROFESSIONAL'),(136,'2025-10-01 03:22:02.079469','Mlhamou7@gmail.com',_binary '',_binary '',NULL,'$2a$10$dKyX/Gp8JT8QDA34e/CvZukIqM45CiIXuYdVuwSeK1aD204uOWkAO','PROFESSIONAL'),(137,'2025-10-01 03:25:19.403759','Mcheikhbay@gmail.com',_binary '',_binary '',NULL,'$2a$10$cnJPbCPxE3VB60P2SlfVIek/1Q6iKrOx1K3cxbbuUuxzo/Sh/XEUO','PROFESSIONAL'),(138,'2025-10-01 03:29:29.761549','Elnejah02@gmail.com',_binary '',_binary '','2025-10-01 21:31:10.137657','$2a$10$9no5Qitqk1/rvjRgS89PRO6ooOe2.FHL1bum5P9.jQ6ye5T7517vm','PROFESSIONAL'),(139,'2025-10-01 04:20:32.151683','Wellhamza@gmmail.com',_binary '',_binary '','2025-11-09 16:06:48.073835','$2a$10$z8uCIx66xN/wHAFg6zuoiuR4/omjT9kureR.teP7s05KVXf0F2nwC','PROFESSIONAL'),(140,'2025-10-01 04:23:21.436541','Mymmoussa@gmail.com',_binary '',_binary '',NULL,'$2a$10$a9yODnTQ7QcLzDgmWVD7NOCJm2lquef3l83o9m2gRozrktyDJim4q','PROFESSIONAL'),(141,'2025-10-01 04:28:58.949099','2211norma@gmail.com',_binary '',_binary '','2025-10-01 21:21:33.974300','$2a$10$I5TqwJLYuP9utnBsNIw9quhub/UJFZTdy9oCqyX0/CfjxyTulsCTe','PROFESSIONAL'),(142,'2025-10-01 04:33:01.827213','elhassenmw@gmail.com',_binary '',_binary '',NULL,'$2a$10$mX83w9Y4uN4CHC1oEX9OrOS/cQN02MUyZn.OcHFE5UWl6acH.9jIC','PROFESSIONAL'),(143,'2025-10-01 04:36:52.864634','Isselmou.rgh@gmail.com',_binary '',_binary '',NULL,'$2a$10$1dQQCFuA8Mt6muPZoeeKxOwuOcoaRO2Fhu1YBi7FKTt3tiZTCYTt.','PROFESSIONAL'),(144,'2025-10-01 04:40:06.505625','Brahim34444430@jmail.com',_binary '',_binary '','2025-10-01 21:26:17.235203','$2a$10$lVjshjErB8OC7Lv5B4J4Ae9CzEeTDzPko1mlSafQv7kdLtduO3W1q','PROFESSIONAL'),(145,'2025-10-01 04:43:47.373858','hamoud.oumar9020@gmail.com',_binary '',_binary '','2025-11-10 10:41:28.174019','$2a$10$jmULw4dNLClP/.nPr7BV7uyprO1kEY2mJabkoayOT4yO6oMj7Gm.G','PROFESSIONAL'),(146,'2025-10-01 04:50:27.939230','Yenjadh183@gmail.com',_binary '',_binary '',NULL,'$2a$10$wNqEZY/B6xKFTHE7sIwtQuDjmxlLNggvtz.RYNd81l08o3PBNLOOi','PROFESSIONAL'),(147,'2025-10-01 04:53:19.990354','Saadhreitani@gmail.com',_binary '',_binary '',NULL,'$2a$10$m5jb7L8bD5d7awuceDFkieX.DRoEJzZIuxCcP/jLaRsVdxgmVhF6.','PROFESSIONAL'),(148,'2025-10-01 04:56:28.395453','Bahmed4466@gmail.com',_binary '',_binary '',NULL,'$2a$10$TxD7MRlAwerWPysGcB4yLeROaZCn5L1CH7CeOoJoY3oPUUVJ66MOC','PROFESSIONAL'),(149,'2025-10-01 04:59:50.745751','Solidbuildcontact@gmail.com',_binary '',_binary '','2025-10-01 21:16:56.731942','$2a$10$9U/Yg1cgr5vos2mFArjXF.in/xt8rFb5iaJsckUMQvYTYQrN9sOWS','PROFESSIONAL'),(150,'2025-10-01 05:04:47.710558','Maroccolore968@gmail.com',_binary '',_binary '','2025-10-01 21:42:55.148762','$2a$10$4Fv3sfcDeLGI1cMe5IOxg.3s95CQqaUoxQdPiG.LkQMfw4ri.se2a','PROFESSIONAL'),(151,'2025-10-01 05:13:16.439350','m88387989@gmail.com',_binary '',_binary '',NULL,'$2a$10$O1mQPWPeoulNt1xWpJQ5QeHNImzyz6WKbo9IJ6bO6.2asmOT32h4a','PROFESSIONAL'),(152,'2025-10-01 05:17:26.810031','medsaid2821989@gmail.com',_binary '',_binary '',NULL,'$2a$10$ytdKbJ1u6YZBUsKsGF1MuupC4hyEmAH2yNisCE2sZjbEgC9fDsfPm','PROFESSIONAL'),(153,'2025-10-01 05:20:23.965443','Medbeddy71@gmail.com',_binary '',_binary '',NULL,'$2a$10$/bFlR3ZEt7/yGoT5PlUkOOEfGg8jsDrzHdNT1HndUxV97RiJns9iK','PROFESSIONAL'),(154,'2025-10-01 05:23:27.776317','atlanticservice100@gmail.com',_binary '',_binary '',NULL,'$2a$10$H/t1Cjv0LlzGKBuG7VAS8.hcZ8DKDZWxeOPHJA3DBNcqU8IbN/mO.','PROFESSIONAL'),(155,'2025-10-01 05:28:56.353069','Mohamdi.maouloud194@gmail.com',_binary '',_binary '','2025-10-01 21:34:58.972263','$2a$10$5Lnz1yUIInLjK063EI0RNO7xU.Np6UL.9YEFYH..aKUPrG55AOzie','PROFESSIONAL'),(157,'2025-10-21 09:02:53.379592','bsb.bechir@gmail.com',_binary '',_binary '','2025-10-21 17:26:09.629732','$2a$10$kVY8sIElpzHL5R/KEFtMeen6qJht3cBoBuMlx78Orc1.hRc65ELS6','PROFESSIONAL'),(158,'2025-10-21 09:26:08.028992','elkorygc@gmail.com',_binary '',_binary '','2025-10-21 09:26:51.922908','$2a$10$MSWchGcQ0/WRjklWlyGLi.jGYhS/JRi1hFhsutb26.vRsSYP08m1m','PROFESSIONAL'),(159,'2025-10-21 22:57:47.453464','khalifatalebe@gmail.com',_binary '',_binary '','2025-10-21 23:45:19.320797','$2a$10$9gTddE9pnsUFatmS6zAJz.QRyZ4FVpN7iKXx.qc5tQzB5RhgmpDTq','PROFESSIONAL'),(160,'2025-10-22 13:27:50.053980','emapac2010@gmail.com',_binary '',_binary '',NULL,'$2a$10$/ynMVaiQPXF4y/TbNNB2pOwys2fl7dzEPLmqjEaspYxfb7zr4Po1m','PROFESSIONAL'),(161,'2025-10-26 19:40:26.488439','Mos6m222@gmail.om',_binary '',_binary '','2025-10-26 19:42:15.292316','$2a$10$d55KR57Uvzo4TrTsuU3LMuN.RYwlkFObT6y.wJaUcY8oZ8EbMG/cq','PROFESSIONAL'),(164,'2025-10-27 13:17:08.860879','alidiakite39@gmail.com',_binary '',_binary '',NULL,'$2a$10$M/B0UOK2AzhvC1BCjNx/CuWnn0nucJs8Lh0z3MHGKQXS0F8Z1vpNu','PROFESSIONAL'),(165,'2025-10-27 13:35:24.747397','elbacha2015@gmail.com',_binary '',_binary '','2025-10-27 13:36:34.482867','$2a$10$G2UIj1VrDOiHHbs0buvCvO0iqVug4F92/ymOci.eXAVLyGtd7m.pO','PROFESSIONAL'),(166,'2025-10-27 19:58:50.725669','abdellah.mohamedou@gmail.com',_binary '',_binary '','2025-10-27 19:59:33.232001','$2a$10$36LQlUIsdzO0tZNBJe93iuBu.Vi9ZTC6YoQhEsmHBxwE/mWLaUbXm','PROFESSIONAL'),(167,'2025-10-27 22:29:46.336978','mohamedlaminebouchama36@gmail.com',_binary '',_binary '',NULL,'$2a$10$oYG.tl.6Y8lqBq0FnNnG6Ovzw9T1ECMDs9DYWvdPC8IxCsFj33Vem','PROFESSIONAL'),(168,'2025-10-27 22:41:00.761723','medleminchamekh35@gmail.com',_binary '',_binary '','2025-11-05 22:17:05.555212','$2a$10$UNZfLvp6lAa/uoh51lrOW.XYKtFZak1kcaIrP2M3eDYChabzhdwM.','PROFESSIONAL'),(169,'2025-10-29 20:29:30.487819','ahwyd04092@gmail.com',_binary '',_binary '','2025-10-29 20:29:57.102707','$2a$10$UC6cM8pQOo8B2WGA7b63W.1Maolr.jzqG9aYopTwgeEpOLNt01vpi','PROFESSIONAL'),(170,'2025-11-09 17:55:16.908715','medmoussa3734@gmail.com',_binary '',_binary '',NULL,'$2a$10$dh2i6KgrQKdXUIc9RxTwAO5cWqrCheLTB2At1jTha3te5RDUpP1lS','PROFESSIONAL'),(176,'2025-12-08 10:48:32.554358','baba20081@gmail.com',_binary '',_binary '','2025-12-08 10:51:13.763124','$2a$10$ZnzWdFBDS4AVJOYdG.7g4uCb7jMy1u5mKSauZCW8H2OBMBw4fpQSa','PROFESSIONAL'),(177,'2025-12-11 17:18:49.348451','Konteissaga445@gmail.com',_binary '',_binary '',NULL,'$2a$10$5.E51In/fUYDrrTK0EgcZOQg7mw.iM8ZLKtrihEVgQHLP1BDEX8Hm','PROFESSIONAL'),(178,'2025-12-11 17:29:40.972912','Nallakane6@gmail.com',_binary '',_binary '',NULL,'$2a$10$eufK.5Oe0S/h01i9xEhWJOkrpOdh56wVYnG6ocYkMOH6FI2V7ijje','PROFESSIONAL'),(179,'2025-12-11 17:35:54.130962','abous9704@gmail.com',_binary '',_binary '',NULL,'$2a$10$jQNOAVkY3bCJpZZ4S3gkMuVPLGQaYZ63OaBbvDSXlKvqyQATt5c.G','PROFESSIONAL'),(180,'2025-12-11 17:39:25.644191','m60038737@gmail.com',_binary '',_binary '',NULL,'$2a$10$tc3K5uLKR2oIPft4r6056ecTEmckBrnc2uQGlodxIdidmyZADI9su','PROFESSIONAL'),(181,'2025-12-11 17:43:19.361659','Sym071024@gmail.com',_binary '',_binary '',NULL,'$2a$10$Oltt0ULbmoFg2jiQCd0ZY.jn04ELtLc7lnWjgDgCga6VR811.RqVe','PROFESSIONAL'),(182,'2025-12-11 17:45:47.707005','Sy3113650@gmail.com',_binary '',_binary '',NULL,'$2a$10$wHnJdkwJMX/nI7J9z6SNVuwCcUKcar2c0Epm7vrvQ5aburVeI.Dau','PROFESSIONAL');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `before_user_insert` BEFORE INSERT ON `users` FOR EACH ROW BEGIN
    IF NEW.role = 'PROFESSIONAL' THEN
        SET NEW.email_verified = 1;
        SET NEW.is_active = 1;
    END IF;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-12-19 14:08:37
