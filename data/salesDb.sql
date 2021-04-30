use abrar;

drop table if exists ItemSale;
drop table if exists Item;
drop table if exists Sale;
drop table if exists Store;
drop table if exists EmailAddress;
drop table if exists Person;
drop table if exists Address;


create table if not exists Address (
  addressId int primary key not null auto_increment,
  street varchar(60),
  city varchar(60),
  state varchar(60),
  zip varchar(60),
  country varchar(60)
);


create table if not exists Person (
  personId int primary key not null auto_increment,
  personCode varchar(60) unique,
  personType char,
  lastName varchar(60),
  firstName varchar(60),
  personAddressId int not null unique,
  foreign key (personAddressId) references Address(addressId)
);


create table if not exists EmailAddress (
  emailId int primary key not null auto_increment,
  emailAddress varchar(60),
  personId int not null,
  foreign key (personId) references Person(personId)
  );


create table if not exists Store (
  storeId int primary key not null auto_increment,
  storeCode varchar(60),
  storeManagerId int not null,
  storeAddressId int not null unique,
  foreign key (storeManagerId) references Person(personId),
  foreign key (storeAddressId) references Address(addressId)
  );


create table if not exists Item (
  itemId int primary key not null auto_increment,
  itemCode varchar(60) unique,
  itemType varchar(30),
  itemName varchar(60),

  ## Variable for sub-type Product
  basePrice double,
  ## Variable for sub-type Service
  hourlyRate double,
  ## Variable for sub-type Subsription
  annualFee double
  );
  

create table if not exists Sale (
  salesId int primary key not null auto_increment,
  salesCode varchar(60) unique,
  storeId int,
  customerId int,
  salesPersonId int, 
  
  foreign key (storeId) references Store(storeId),
  foreign key (customerId) references Person(personId),
  foreign key (salesPersonId) references Person(personId)
  );
  
  
create table if not exists ItemSale (
  itemSaleId int primary key not null auto_increment,
  itemId int,
  salesId int,

  ## Variables for Product purchase
  quantity int,
  giftCardQuantity double,
  ## Variables for sub-type Service purchase
  employeeCode varchar(60),
  numOfHours double,
  ## Variables for sub-type Subsription purchase
  beginDate varchar(40),
  endDate varchar(40),

  foreign key (itemId) references Item(itemId),
  foreign key (salesId) references Sale(salesId)
  );
  
## Insert data into the tables
## Insert values into the table Address
insert into Address(street,city,state,zip,country) values ('600 N 15th St','Lincoln','NE','68508','US');
insert into Address(street,city,state,zip,country) values ('Bandra Main Road','Mumbai','Maharashtra','33764','India');
insert into Address(street,city,state,zip,country) values ('46 Bombay St','Mumbai','Maharashtra','32982','India');
insert into Address(street,city,state,zip,country) values ('98 Carry St','Faridabad','Haryana','95833','India');
insert into Address(street,city,state,zip,country) values ('Chacha waali St','Mirzapur','UP','28372','India');
insert into Address(street,city,state,zip,country) values ('Kothe Waali St','Dhanbad','UP','13737','India');
insert into Address(street,city,state,zip,country) values ('64 Reddy St','Mirzapur','UP','39822','India');
insert into Address(street,city,state,zip,country) values ('53 Preeti Road','Delhi','Delhi','40928','India');
insert into Address(street,city,state,zip,country) values ('35 S Preeti St','Hyderabad','Telangana','28947','India');
insert into Address(street,city,state,zip,country) values ('7 Net Gali','Patna','Bihar','18479','India');
insert into Address(street,city,state,zip,country) values ('34 Potter St','Faridabad','Haryana','13495','India');
insert into Address(street,city,state,zip,country) values ('49 Link Road','Mirzapur','UP','35689','India');
insert into Address(street,city,state,zip,country) values ('Mighty Ring Road','Hyderabad','Telangana','44643','India');
insert into Address(street,city,state,zip,country) values ('13 Rocksville St','Mumbai','Maharashtra','56453','India');
insert into Address(street,city,state,zip,country) values ('83 Holy St','Dhanbad','UP','34532','India');



## Insert values into the table Person
insert into Person(personCode,personType,lastName,firstName,personAddressId) values ('f7sg6d','E','Mehra','Rohit', 1);
insert into Person(personCode,personType,lastName,firstName,personAddressId) values ('7egf53','E','Winget','Jennifer', 2);
insert into Person(personCode,personType,lastName,firstName,personAddressId) values ('9wh3n7','E','Sharma','Anushka',3);
insert into Person(personCode,personType,lastName,firstName,personAddressId) values ('weiu94','P','Nagar','Ajey',4);
insert into Person(personCode,personType,lastName,firstName,personAddressId) values ('73h77g','P','Tripathi','Munna',5);
insert into Person(personCode,personType,lastName,firstName,personAddressId) values ('hfu769','P','Khan','Sardar',6);
insert into Person(personCode,personType,lastName,firstName,personAddressId) values ('83hwu1','P','Pandit','Bablu',7);
insert into Person(personCode,personType,lastName,firstName,personAddressId) values ('rthy89','G','Singh','Kabir',8);
insert into Person(personCode,personType,lastName,firstName,personAddressId) values ('ktkre0','G','Reddy','Arjun',9);
insert into Person(personCode,personType,lastName,firstName,personAddressId) values ('4u8jfg','C','Gumshuda','Binod',10);


## Insert values into the table EmailAddress
insert into EmailAddress(emailAddress,personId) values ('rohit@mehra.org',1);
insert into EmailAddress(emailAddress,personId) values ('rmehra2@edu',1);
insert into EmailAddress(emailAddress,personId) values ('ro@m.com',1);
insert into EmailAddress(emailAddress,personId) values ('jwingett@gmail.com',2);
insert into EmailAddress(emailAddress,personId) values ('jeniffer@yahoo.com',2);
insert into EmailAddress(emailAddress,personId) values ('anushkasharma@v.org',3);
insert into EmailAddress(emailAddress,personId) values ('viratkibiwi.com',3);
insert into EmailAddress(emailAddress,personId) values ('vamikakimaa.org',3);
insert into EmailAddress(emailAddress,personId) values ('ranveerkiex.bye',3);
insert into EmailAddress(emailAddress,personId) values ('carryminati@gmail.com',4);
insert into EmailAddress(emailAddress,personId) values ('carrybhai@yahoo.com',4);
insert into EmailAddress(emailAddress,personId) values ('gamereknumber.com',4);
insert into EmailAddress(emailAddress,personId) values ('bhaiyajismile.org',5);
insert into EmailAddress(emailAddress,personId) values ('kingofmirzapur@samjhe',5);
insert into EmailAddress(emailAddress,personId) values ('chacharestkarlijiye.com',5);
insert into EmailAddress(emailAddress,personId) values ('madhuribhabi@mla',5);
insert into EmailAddress(emailAddress,personId) values ('sardaarkhan@naamhai',6);
insert into EmailAddress(emailAddress,personId) values ('faizalkabaap.com',6);
insert into EmailAddress(emailAddress,personId) values ('ramadhirkadushman.org',6);
insert into EmailAddress(emailAddress,personId) values ('bablugenius@gmail.com',7);
insert into EmailAddress(emailAddress,personId) values ('drkabirsingh@yahoo.com',8);
insert into EmailAddress(emailAddress,personId) values ('roadsideromeo@nahinaan',8);
insert into EmailAddress(emailAddress,personId) values ('Reddytohdilhai@humaara.com',9);


## Insert values into the table Store
insert into Store(storeCode,storeManagerId,storeAddressId) values ('842rfh','1','11');
insert into Store(storeCode,storeManagerId,storeAddressId) values ('rjuhf4','3','12');
insert into Store(storeCode,storeManagerId,storeAddressId) values ('rf43fk','1','13');
insert into Store(storeCode,storeManagerId,storeAddressId) values ('4tgerj','2','14');
insert into Store(storeCode,storeManagerId,storeAddressId) values ('897bk','3','15');

 
## Insert values into the table Item
INSERT INTO Item(itemCode,itemType,ItemName,basePrice) VALUES ('yet7q8','PN','Nintendo Switch Lite',289.9);
INSERT INTO Item(itemCode,itemType,ItemName,basePrice) VALUES ('84hufb','PN','Minecraft (Digital Copy)',95);
INSERT INTO Item(itemCode,itemType,ItemName,basePrice) VALUES ('9heho','PN','Play Station 5',449.9);
INSERT INTO Item(itemCode,itemType,ItemName,basePrice) VALUES ('i8u0j','PU','Oculus Rift',229.99);
INSERT INTO Item(itemCode,itemType,ItemName,basePrice) VALUES ('urfh8e','PU','Legend of Zelda (NES)',26.3);
INSERT INTO Item(itemCode,itemType,ItemName) VALUES ('u9h97y','PG','Android Store');
INSERT INTO Item(itemCode,itemType,ItemName) VALUES ('uffuh','PG','Robux');
INSERT INTO Item(itemCode,itemType,ItemName) VALUES ('us8h8','PG','Vive');
INSERT INTO Item(itemCode,itemType,ItemName,hourlyRate) VALUES ('0vguy8','SV','Repair',49.9);
INSERT INTO Item(itemCode,itemType,ItemName,hourlyRate) VALUES ('yt79yg','SV','Counseling Services',1.3);
INSERT INTO Item(itemCode,itemType,ItemName,annualFee) VALUES ('765fyv','SB','Disney Plus',69.9);
INSERT INTO Item(itemCode,itemType,ItemName,annualFee) VALUES ('wiq19h','SB','X-Box Live',60);


## Insert values into the table Sale
INSERT INTO Sale(salesCode,storeId,customerId,salesPersonId) VALUES ('g8ogb8',1,1,1);
INSERT INTO Sale(salesCode,storeId,customerId,salesPersonId) VALUES ('utgyt9',2,6,2);
INSERT INTO Sale(salesCode,storeId,customerId,salesPersonId) VALUES ('yyu8y7',1,9,1);
INSERT INTO Sale(salesCode,storeId,customerId,salesPersonId) VALUES ('gt8u6t',5,10,3);

## Insert values into the table ItemSale
INSERT INTO ItemSale(itemId,salesId,quantity) VALUES (1,1,1);
INSERT INTO ItemSale(itemId,salesId,employeeCode,numOfHours) VALUES (9,1,'9wh3n7',2);
INSERT INTO ItemSale(itemId,salesId,quantity) VALUES (4,2,2);
INSERT INTO ItemSale(itemId,salesId,giftCardQuantity) VALUES (7,2,85);
INSERT INTO ItemSale(itemId,salesId,beginDate,endDate) VALUES (11,3,'2020-02-20','2021-01-30');
INSERT INTO ItemSale(itemId,salesId,quantity) VALUES (1,3,1);
INSERT INTO ItemSale(itemId,salesId,quantity) VALUES (2,4,3);
INSERT INTO ItemSale(itemId,salesId,quantity) VALUES (5,4,1);
INSERT INTO ItemSale(itemId,salesId,employeeCode,numOfHours) VALUES (10,4,'f7sg6d',4);


 -- select * from Sale;
-- ;
-- select * from Address;
-- select p.firstName, s.storeCode from Person p 
-- right join Store s on p.personId = s.storeManagerId;

-- delete from ItemSale where salesId = 1;
-- delete from Sale where salesId = 1;

select p.personId,p.personCode,p.personType,p.lastName,p.firstName
,p.personAddressId,a.street,a.city,a.state,a.zip,a.country from Person p 
left join Address a on p.personAddressId = a.addressId;

select s.storeCode,s.storeManagerId,a.street,a.city,a.state,a.zip,a.country from Store s 
left join Address a on s.storeAddressId = a.addressId;