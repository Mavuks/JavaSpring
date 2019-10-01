DROP TABLE IF EXISTS "order";

CREATE TABLE "order" (
   id serial NOT NULL PRIMARY KEY,
   orderNumber VARCHAR(255) NOT NULL,
   orderRows varchar (255)
);
