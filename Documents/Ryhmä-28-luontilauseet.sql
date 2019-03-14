CREATE TABLE tyyppiyksikko (
	tyyppiyksikkonumero INT NOT NULL,
	tyyppi VARCHAR(50) NOT NULL,
	yksikko VARCHAR(3) NOT NULL,
	PRIMARY KEY (tyyppiyksikkonumero),
);

CREATE TABLE osoite (
	osoitenumero INT NOT NULL AUTO_INCREMENT,
	katuosoite VARCHAR(100) NOT NULL,
	postinumero INT NOT NULL,
	postitoimipaikka VARCHAR(50) NOT NULL,
	PRIMARY KEY (osoitenumero)
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

CREATE TABLE asiakas (
	asiakasnumero INT NOT NULL AUTO_INCREMENT,
	osoitenumero INT NOT NULL,
	PRIMARY KEY (asiakasnumero),
	FOREIGN KEY (osoitenumero) REFERENCES osoite(osoitenumero)
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
	y-tunnus VARCHAR(50) NOT NULL UNIQUE,
	nimi VARCHAR(50) NOT NULL,
	PRIMARY KEY (asiakasnumero),
	FOREIGN KEY (asiakasnumero) REFERENCES asiakas(asiakasnumero)
);

CREATE TABLE tyokohde (
	tyokohdenumero INT NOT NULL AUTO_INCREMENT,
	asiakasnro INT NOT NULL,
	osoitenumero INT NOT NULL,
	PRIMARY KEY (tyokohdenumero),
	FOREIGN KEY (asiakasnro) REFERENCES asiakas(asiakasnumero),
	FOREIGN KEY (osoitenumero) REFERENCES osoite(osoitenumero)
);

CREATE TABLE urakka (
	tyokohdenumero INT NOT NULL,
	urakkahinta DECIMAL(10,2) NOT NULL,
	PRIMARY KEY (tyokohdenumero),
	FOREIGN KEY (tyokohdenumero) REFERENCES tyokohde(tyokohdenumero)
);

CREATE TABLE tyosuoritus (
	tyosuoritusnumero INT NOT NULL AUTO_INCREMENT,
	aputyo_h INT,
	suunnittelu_h INT,
	tyo_h INT,
	alennusprosentti INT,
	tyokohdenumero INT,
	PRIMARY KEY (tyosuoritusnumero),
	FOREIGN KEY (tyokohdenumero) REFERENCES tyokohde(tyokohdenumero)
);

CREATE TABLE lasku (
	laskutunnus INT NOT NULL AUTO_INCREMENT,
	paivamaara DATE,
	erapaiva DATE NOT NULL,
	maksupaiva DATE,
	laskunumero INT NOT NULL,
	edellinen_lasku INT,
	asiakasnumero INT NOT NULL,
	tyokohdenumero INT,
	PRIMARY KEY (laskutunnus),
	FOREIGN KEY (asiakasnumero) REFERENCES asiakas(asiakasnumero),
	FOREIGN KEY (tyokohdenumero) REFERENCES tyokohde(tyokohdenumero)
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