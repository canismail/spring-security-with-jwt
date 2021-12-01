CREATE TABLE USER (
  id number auto_increment primary key,   
  name VARCHAR(250),
  surname VARCHAR(250),
  user_name VARCHAR(250),
  password VARCHAR(250),
  update_date TIMESTAMP default CURRENT_TIMESTAMP 
);

CREATE TABLE ROLE (
  role_id number auto_increment primary key,
  user_name VARCHAR(250),
  role VARCHAR(250),
  user_id number
);


