
--ALTER  table "order"

DROP TABLE IF EXISTS "orderrows";


CREATE TABLE "orderrows" (
   id serial NOT NULL PRIMARY KEY,
   order_id int,
   itemName VARCHAR (255) NOT NULL,
   quantity VARCHAR (255),
   price int


);

 DROP TABLE IF EXISTS "order";

CREATE TABLE "order" (
   id serial NOT NULL PRIMARY KEY,
   orderNumber VARCHAR(255) NOT NULL
);


-- ALTER TABLE "orderrows" add constraint fkey FOREIGN key (order_id) REFERENCES "order"(id);





