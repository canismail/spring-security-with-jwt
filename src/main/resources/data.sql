INSERT INTO USER (name, surname,user_name,password) VALUES
  ('can','meral','can','can'),
  ('admin','admin','admin','admin');

INSERT INTO ROLE(user_name,role,user_id) VALUES('can','user',1);
INSERT INTO ROLE(user_name,role,user_id) VALUES('admin','admin',2);
  