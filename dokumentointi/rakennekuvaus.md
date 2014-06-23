#Rakennekuvaus

Ohjelma on toteutettu käyttäen ORMLite-persistointikirjastoa, joka asetti rajapuitteet toteutustavalle.

![Luokkakaavio](luokkakaavio.png "LapReader -ohjelman luokkakaavio")

##Controller
Ohjelman perustan muodostaa Controller-luokka, joka pitää sisällään lähes kaiken ohjelman toimnallisuuden. Controller pitää huolta myös tietokantayhteyksistä ja olioiden persistoinnista ORMLiten data access olioiden (DAO) avulla. Jokaisella mallilla on oma daonsa, joka huolehtii kyseisten mallien persistoinnista. Tietokantayhteyksiä on siis auki useita samaan aikaan. Controller hoitaa myös tiedostojen lukemisen.

##Mallit
Varsinaista tietosisältöä edustavat luokat on sijoitettu model-pakkaukseen. Model-luokkaan oli ajatus sijoittaa kaikkia malleja koskevaa toiminnallisuutta, jonka toteutus jäi tekemättä ajan puutteen vuoksi. Ohjelman rakenteessa haluttiin kuitenkin säilyttää helppo laajennettavuus, jonka takia Model jätettiin yläluokaksi kaikille malleille. Nykyisessä toteutuksessa se voisi aivan hyvin olla pelkkä rajapinta. Modelin perintä on jätetty selvyyden vuoksi pois luokkakaaviosta.

Varsinaisen datan ylimmän luokan muodostaa TestDay. Se kuvaa yhtä harjoituspäivää, pelkkä Day olisi kuvaavampi nimi, mutta se oli jo varattu Javassa vakiokirjastojen Date-luokalle. TestDay koostuu useista erillisistä ajokerroista eli Heat-olioista. Ajokertaan liittyy kiinteästi kierrosajat, jotka esitetään Lap-luokkaa edustavilla olioilla. Yhdellä Heatilla on siis useita Lap-olioita. Ajokertaan liittyy myös siinä saavutettu tulos, joka käsitellään Result-luokan avulla.

ORMLiteä käytettäessä yksi taulu muodostaa yhden luokan. Tämä asetti hiukan rajoitteita varsinaisen datan mallintamiseen olioiksi. Ilman rajoitteita esimerkiksi ajasta olisi voinut tehdä oman olionsa.

##Parser

![Orion LCS](http://www.rctech.net/forum/attachments/r-c-items-sale-trade/97357d1130820481-team-orion-pro-lcs-lap-counting-system-mvc-099s.jpg "Kuva Orionin LCS -laitteesta")

Kierrosaikadataa tuodessa ohjelmaan se täytyy parsia helposti käsiteltävään muotoon, sillä yleensä automaattisten ajanottojärjestelmien data on eksoottisessa valmistajakohtaisessa muodossa. Datan parsimista varten tehdyt luokat on sijoitettu pakkaukseen parser. Parser-rajapinta muodostaa yhtenäisen rajapinnan parserin toimintaa varten, jotta parseri voidaan valita tuotavan datan mukaan. Rajapinnan toteuttaa tällä hetkellä ainoastaan OrionParser-luokka, jonka tehtävänä on parsia Team Orionin valmistaman PRO LCS -laitteen sarjaportista tallennettu kierrosaikadata puhtaiksi numeroiksi.
Kun parser-muodostaa hyvin yksinkertaisen rajapinnan, niin uusien parsereiden toteuttaminen on todella yksinkertaista.

##Käyttöliittymä

TODO

##Suunnitellut tulevat laajennukset

![Laajennettu luokkakaavio](luokkakaavio__laajennusmahdollisuudet.png "LapReader -ohjelman laajennuste luokkakaavio")

Yllä on esitetty vielä luokkakaavio suunniteltujen laajennuksen kanssa. Testipäivä suoritetaan tietyllä radalla, jota esittä Track-luokka. Lisäksi Heattiin yhdistetään tieto autosta Car-luokan olioiden avulla. Parser pakkaukseen on lisäksi lisätty MyLapsParser mylaps-nettisivuston käyttämää formaattia varten
