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
  closeDate TIMESTAMP,
  productId bigint not null,
  totalBid integer not null,
  golden boolean not null,
  closeAuction boolean,
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

create table systemSetting (
  id bigint not null,
  name varchar(200),
  value varchar(200),
  constraint ssId primary key (id)
);

create table filter (
  id bigint not null,
  name varchar(200),
  constraint fId primary key (id)
);

create table filterconditions (
  id bigint NOT NULL,
  name VARCHAR(200),
  type VARCHAR(200),
  type VARCHAR(200),
  filterId bigint not null,
  CONSTRAINT fcId PRIMARY KEY (id),
  Foreign Key (filterId) references filter(id)
);

create table filtervalue(
  id bigint NOT NULL,
  value VARCHAR(200),
  filterConditionsId bigint not null,
  CONSTRAINT fvId PRIMARY KEY (id),
  Foreign Key (filterConditionsId) references filterconditions(id)
);