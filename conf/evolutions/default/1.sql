
# --- !Ups
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
    quantity        double
)

# --- !Downs
DROP TABLE IF EXISTS recipes, ingredients, recipe_ingredients;