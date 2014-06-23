#Testausdokumentaatio

##Ohjelman logiikka
Ohjelman logiikan testaus suoritettiin pääosin tekemällä sille JUnit-yksikkötestejä. Model-pakkauksen mallien osalta tämä onnistui hyvin, sillä ne eivät juurikaan riippuneet pakkauksen ulkopuolisista luokista.

Poikkeuksen muodostivat yhden suhde moneen -relaatiot, sillä ORMLiteä käytettäessä niiden tietorakenteena on ForeignCollection. ForeignCollectionin alustaminen ilman tietokantaa osoittautui lähes mahdottomaksi. Hyvällä suunnittelulla jäi ainoastaan yksi metodidi, Heat.totalTimes(), jota ei pystynyt testaamaan edellä mainitun takia. Kyseinen metodi hajotettiin kahteen osaa, jossa toisessa ainoastaan muutetaan foreignCollection tavalliseksi arrayksi ja toisessa tehdään varsinainen toiminnallisuus, joten toiminnallisuus saatiin testattua.

Getterit ja setterit jätettiin kokonaan testaamatta.

Varsinaisen toimintalogiikan eli Controller-luokan testaus tapahtui käyttämällä muistissa pidettävää tietokantaa nopeuden vuoksi. Alunperin testaustietokantana oli H2, mutta sekin jouduttiin vaihtamaan SQLiteen ORMLiten puutteellisten relaatioiden hallinnan kanssa poiston yhteydessä. Kaikkille muille, paitsi tiedostonkäsittelyn tekeville metodeille, löytyy yksikkötestit. Yksikkötestit riippuvat toisaalta model-pakkauksen luokista, mutta se oli tässä vaiheessa hyväksyttävää.

##Käyttöliittymä
Käyttöliittymää testattiin ajamalla ohjelmaa useisiin kertoihin ja yrittämällä saada ohjelma kaatumaan esimerkiksi yrittämällä katsoa poistettua päivää. Suurin osa ongelmista liittyi lopulta tietokantavirheisiin.

##Tunnetut bugit
* Tietokannan schemaa ei tarkisteta missään vaiheessa, vaan kannan virheellinen rakenne voi aiheuttaa tietokantavirheitä
* Muut avoinna olevat ikkunat eivät päivity vaikka kanta muuttuisi. Tämä aiheuttaa lisää ongelmia esimerkiksi poistetun päivän tai heatin jäädessä näkyville, jolloin aiheutuu tietokantavirhe.
* Kaikkia ActionListenerin suorittamien metodien aiheuttamia tietokantavirheitä ei huomata, vaan AWT:n tapahtumanjonon threadi kaatuu ja tulostaa stack tracen. Tämän korjaamiseksi tarvitsisi injektoida try-catch rakenne johonkin sopivaan kohtaan.
* Heattia lisättäessa saattaa erittäin harvinaisissa tapauksissa tulla väärä virheilmoitus virheellisestä kellonajasta, vaikka ongelma olisi tiedoston lukemisessa
