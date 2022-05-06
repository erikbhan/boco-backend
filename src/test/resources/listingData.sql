INSERT INTO public.category(category_id, name) VALUES (420, 'Fussball');
INSERT INTO public.category(category_id, name) VALUES (520, 'Utstyr');

INSERT INTO public.community(community_id, description, location, name, picture, visibility)
VALUES (100001, 'En hyggelig dag', 'Storvold', 'Vi som liker været', 'Nei takk', 1);

INSERT INTO public.community(community_id, description, location, name, picture, visibility)
VALUES (100002, 'Fisk for folk', 'Ravnkloa', 'Det regner fisk', 'imagen a place ...with fish', 1);

INSERT INTO public.user (user_id, email, first_name, last_name, address, picture, salt, hash)
VALUES (666666, 'test@email.com', 'test', 'user', 'gløshaugen', 'ok', 'l/hjdIHi9Us2uJZ7MP/urY6ALjISdukPrN5sjpD7wTMEV+DnQkWzOF3qfnO6r2PnIQM6zP7ZcdEYh0Gdok8nFQ==', 'Ge7Y9frKWdgKcAysHdYCIoOOsAcn9We3f2+C74xlc6kWQZn2scBE8sEf4iZezwsmG/KdeeEuspZD9Q4Ojt27Hg==');

INSERT INTO public.listing(listing_id, address, description, price_per_day, title, user_id, deleted)
VALUES (4040, 'hoi', 'hei', 2, 'Folk og fe', 666666, false);

INSERT INTO public.listing_picture(listing_picture_id, picture, listing_id)
VALUES (999, 'oki', 4040);

INSERT INTO public.user (user_id, email, first_name, last_name, address, picture, salt, hash)
VALUES (4321, 'test@email.com', 'test', 'user', 'gløshaugen', 'ok', 'l/hjdIHi9Us2uJZ7MP/urY6ALjISdukPrN5sjpD7wTMEV+DnQkWzOF3qfnO6r2PnIQM6zP7ZcdEYh0Gdok8nFQ==', 'Ge7Y9frKWdgKcAysHdYCIoOOsAcn9We3f2+C74xlc6kWQZn2scBE8sEf4iZezwsmG/KdeeEuspZD9Q4Ojt27Hg==');

INSERT INTO public.listing(listing_id, address, description, price_per_day, title, user_id, deleted)
VALUES (1234, 'kjopmansgata', 'you look cute', 14, 'pls holp', 4321, false);

INSERT INTO public.listing(listing_id, address, description, price_per_day, title, user_id, deleted)
VALUES (12345, 'kjopmansgata', 'you look cute', 14, 'pls holp', 4321, false);

INSERT INTO public.rent(rent_id, from_time, is_accepted, to_time, listing_owner_id, renter_id, is_deleted, created_at)
VALUES (10000, 2609329600000, true, 2609358400000, 1234, 4321, false, 1);

INSERT INTO public.rent(rent_id, from_time, is_accepted, to_time, listing_owner_id, renter_id, is_deleted, created_at)
VALUES (10001, 2610456400000, false, 2610467200000, 1234, 4321, false, 1);

INSERT INTO public.rent(rent_id, from_time, is_accepted, to_time, listing_owner_id, renter_id, is_deleted, created_at)
VALUES (10002, 1000000000000, false, 1000000000001, 12345, 4321, false, 1);
