
# --- !Ups
INSERT INTO recipes (name, instructions, description, notes) VALUES ('burrito', 'mouth-watering mexican delight', 'make burritos', 'it is delicious');
INSERT INTO recipes (name, instructions, description, notes) VALUES ('taco', 'traditional tex-mex dish', 'make tacos', 'it is taco tuesday');

# --- !Downs
DELETE FROM recipes;
