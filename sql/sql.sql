create table product (
  id BIGINT NOT NULL,
  referenceId VARCHAR(200),
  name VARCHAR(200),
  CONSTRAINT pId PRIMARY KEY (id)
);

create table item (
  id bigint NOT NULL,
  ebayItemId VARCHAR(200),
  createDate TIMESTAMP NOT NULL,
  productId bigint not null,
  CONSTRAINT iId PRIMARY KEY (id),
  Foreign Key (productId) references product(id)
);

create table itemProperties (
  id bigint NOT NULL,
  name VARCHAR(200),
  value VARCHAR(200),
  itemId bigint not null,
  CONSTRAINT ipId PRIMARY KEY (id),
  Foreign Key (itemId) references item(id)
);