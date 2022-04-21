//INSERT INTO PUBLIC_USER (email, first_name, last_name, address ,password , picture) VALUES ( 'test@email' , 'guy' , 'guysson', 'add' , 'pass' , 'this is picture');
//INSERT INTO PUBLIC_USER (email, first_name, last_name, address ,password , picture) VALUES ( 'test@email' , 'guy22' , 'guysson33', 'address' , 'passw' , 'this is anotherpicture');
//INSERT INTO PUBLIC_GROUP (name, description, visibility, location ,picture) VALUES ( 'testgroup' , 'thi is test group' , true, 'located here' , 'this is group pic');
//INSERT INTO PUBLIC_USER_GROUP (group_id , user_id , is_administrator) VALUES ( 1, 1 , true);
INSERT INTO PUBLIC_USER_GROUP (group_id , user_id , is_administrator) VALUES ( 1, 2 , true);