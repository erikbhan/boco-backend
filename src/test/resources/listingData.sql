INSERT INTO public.category(category_id, name) VALUES (420, 'Fussball');
INSERT INTO public.category(category_id, name) VALUES (520, 'Utstyr');

INSERT INTO public.community(community_id, description, location, name, picture, visibility)
VALUES (100001, 'En hyggelig dag', 'Storvold', 'Vi som liker været', 'Nei takk', 1);

INSERT INTO public.community(community_id, description, location, name, picture, visibility)
VALUES (100002, 'Fisk for folk', 'Ravnkloa', 'Det regner fisk', 'imagen a place ...with fish', 1);

INSERT INTO public.user (user_id, email, first_name, last_name, address, picture, salt, hash)
VALUES (666666, 'test@email.com', 'test', 'user', 'gløshaugen', 'ok', 'l/hjdIHi9Us2uJZ7MP/urY6ALjISdukPrN5sjpD7wTMEV+DnQkWzOF3qfnO6r2PnIQM6zP7ZcdEYh0Gdok8nFQ==', 'Ge7Y9frKWdgKcAysHdYCIoOOsAcn9We3f2+C74xlc6kWQZn2scBE8sEf4iZezwsmG/KdeeEuspZD9Q4Ojt27Hg==');

INSERT INTO public.listing(listing_id, address, description, price_per_day, title, user_id)
VALUES (4040, 'hoi', 'hei', 2, 'Folk og fe', 666666);

INSERT INTO public.listing_picture(listing_picture_id, picture, listing_id)
VALUES (999, 'oki', 4040);