# DC schema
 
# --- !Ups

CREATE TABLE PROJECT (
    ID integer NOT NULL AUTO_INCREMENT PRIMARY KEY,
    NAME varchar(255) NOT NULL
);

CREATE TABLE TASK (
    ID integer NOT NULL AUTO_INCREMENT PRIMARY KEY,
    COLOR varchar(255) NOT NULL,
    STATUS varchar(255) NOT NULL,
    PROJECT integer NOT NULL,
    FOREIGN KEY (PROJECT) REFERENCES PROJECT (ID)
);



CREATE TABLE recipes (
	id 		        bigint auto_increment primary key,
    name            varchar(255) not null,
    description     text,
    instructions    text,
    notes           text
);

CREATE TABLE ingredients (
    id              bigint auto_increment primary key,
    name            varchar(255) not null,
    description     text
);

CREATE TABLE recipe_ingredients (
    id              bigint auto_increment primary key,
    recipe          bigint,
    ingredient      bigint,
    quantity        varchar(40)
)


 
# --- !Downs

DROP TABLE TASK;
DROP TABLE PROJECT;

DROP TABLE IF EXISTS recipes, ingredients, recipe_ingredients;
