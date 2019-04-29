CREATE TABLE tyosuoritus (
	tyosuoritusnumero SERIAL NOT NULL,
	tyokohdenumero INT,
	PRIMARY KEY (tyosuoritusnumero),
	FOREIGN KEY (tyokohdenumero) REFERENCES tyokohde(tyokohdenumero)
);

CREATE TABLE tyokohde (
	tyokohdenumero SERIAL NOT NULL,
	asiakasnro INT NOT NULL,
	osoitenumero INT NOT NULL,
	PRIMARY KEY (tyokohdenumero),
	FOREIGN KEY (asiakasnro) REFERENCES asiakas(asiakasnumero),
	FOREIGN KEY (osoitenumero) REFERENCES osoite(osoitenumero)
);

CREATE TABLE asiakas (
	asiakasnumero INT NOT NULL AUTO_INCREMENT,
	osoitenumero INT NOT NULL,
	PRIMARY KEY (asiakasnumero),
	FOREIGN KEY (osoitenumero) REFERENCES osoite(osoitenumero)
);

CREATE TABLE osoite (
	osoitenumero SERIAL NOT NULL,
	katuosoite VARCHAR(100) NOT NULL,
	postinumero INT NOT NULL,
	postitoimipaikka VARCHAR(50) NOT NULL,
	PRIMARY KEY (osoitenumero)
);

CREATE TABLE henkilo (
	asiakasnumero INT NOT NULL,
	etunimi VARCHAR(50) NOT NULL,
	sukunimi VARCHAR(50) NOT NULL,
	PRIMARY KEY (asiakasnumero),
	FOREIGN KEY (asiakasnumero) REFERENCES asiakas(asiakasnumero)
);

CREATE TABLE yritys (
	asiakasnumero INT NOT NULL,
	nimi VARCHAR(50) NOT NULL,
	ytunnus INT NOT NULL UNIQUE,
	PRIMARY KEY (asiakasnumero),
	FOREIGN KEY (asiakasnumero) REFERENCES asiakas(asiakasnumero)
);

CREATE TABLE urakka (
	tyokohdenumero INT NOT NULL,
	urakkahinta DECIMAL(10,2) NOT NULL,
	PRIMARY KEY (tyokohdenumero),
	FOREIGN KEY (tyokohdenumero) REFERENCES tyokohde(tyokohdenumero)
);

CREATE TABLE tyyppiyksikko (
	tyyppiyksikkonumero INT NOT NULL,
	tyyppi VARCHAR(50) NOT NULL,
	yksikko VARCHAR(3) NOT NULL,
	PRIMARY KEY (tyyppiyksikkonumero),
);

CREATE TABLE tarvike (
	tarvikenumero INT NOT NULL AUTO_INCREMENT,
	sisaanostohinta DECIMAL(10,2) NOT NULL,
	nimi VARCHAR(150) NOT NULL,
	varastotilanne INT NOT NULL,
	myyntihinta DECIMAL(10,2) NOT NULL,
	tyyppiyksikkonumero INT NOT NULL,
	PRIMARY KEY (tarvikenumero),
	FOREIGN KEY (tyyppiyksikkonumero) REFERENCES tyyppiyksikko(tyyppiyksikkonumero)
);

CREATE TABLE myyty_tarvike (
	laskutunnus INT NOT NULL,
	tarvikenumero INT NOT NULL,
	alennusprosentti INT,
	kappalemaara INT NOT NULL,
	PRIMARY KEY (laskutunnus, tarvikenumero),
	FOREIGN KEY (tarvikenumero) REFERENCES tarvike(tarvikenumero),
	FOREIGN KEY (laskutunnus) REFERENCES lasku(laskutunnus)
);

CREATE TABLE lasku (
	laskutunnus SERIAL NOT NULL,
	maksupaiva DATE,
	laskunumero INT NOT NULL,
	muistutus_laskusta INT,
	asiakasnumero INT NOT NULL,
	tyosuoritusnumero INT,
	paivamaara DATE,
	erapaiva DATE,
	PRIMARY KEY (laskutunnus),
	FOREIGN KEY (asiakasnumero) REFERENCES asiakas(asiakasnumero),
	FOREIGN KEY (tyosuoritusnumero) REFERENCES tyosuoritus(tyosuoritusnumero)
);

CREATE TABLE hinnoiteltu_tyosuoritus (
    tyotyyppi VARCHAR(30) NOT NULL,
    tyosuoritusnumero INT NOT NULL,
    maara INT NOT NULL,
    alennusprosentti INT,
    PRIMARY KEY (tyosuoritusnumero, tyotyyppi),
    FOREIGN KEY (tyosuoritusnumero) REFERENCES tyosuoritus(tyosuoritusnumero),
    FOREIGN KEY (tyotyyppi) REFERENCES tyohinnasto(tyyppi)
);

CREATE TABLE tyohinnasto (
    tyyppi VARCHAR(30) NOT NULL,
    hinta DECIMAL(6,2) NOT NULL,
    PRIMARY KEY (tyyppi, hinta),
);