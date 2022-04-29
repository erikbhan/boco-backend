INSERT INTO public.user (user_id, email, first_name, last_name, address, picture, salt, hash)
VALUES (2022, 'test@email.com', 'test', 'user', 'kalvskinnet', 'ok', 'l/hjdIHi9Us2uJZ7MP/urY6ALjISdukPrN5sjpD7wTMEV+DnQkWzOF3qfnO6r2PnIQM6zP7ZcdEYh0Gdok8nFQ==', 'Ge7Y9frKWdgKcAysHdYCIoOOsAcn9We3f2+C74xlc6kWQZn2scBE8sEf4iZezwsmG/KdeeEuspZD9Q4Ojt27Hg==');

INSERT INTO public.user (user_id, email, first_name, last_name, address, picture, salt, hash)
VALUES (3034, 'fake@user.com', 'fake', 'user', 'gløshaugen', 'ok', 'l/hjdIHi9Us2uJZ7MP/urY6ALjISdukPrN5sjpD7wTMEV+DnQkWzOF3qfnO6r2PnIQM6zP7ZcdEYh0Gdok8nFQ==', 'Ge7Y9frKWdgKcAysHdYCIoOOsAcn9We3f2+C74xlc6kWQZn2scBE8sEf4iZezwsmG/KdeeEuspZD9Q4Ojt27Hg==');

INSERT INTO public.listing(listing_id, address, description, price_per_day, title, user_id)
VALUES (1234, 'kjopmansgata', 'you look cute', 14, 'pls holp', 3034);

INSERT INTO public.listing(listing_id, address, description, price_per_day, title, user_id)
VALUES (1235, 'kjopmansgata', 'hi', 12, 'come here', 3034);

INSERT INTO public.listing(listing_id, address, description, price_per_day, title, user_id)
VALUES (1236, 'kjopmansgata', 'yo', 21, 'get over here', 3034);

INSERT INTO public.rent(rent_id, from_time, is_accepted, to_time, listing_owner_id, renter_id, is_deleted)
VALUES (10000, 1610445600000, true, 1610467200000, 1234, 2022, false);

INSERT INTO public.rent(rent_id, from_time, is_accepted, to_time, listing_owner_id, renter_id, is_deleted)
VALUES (10001, 1610445600000, false, 1610467200000, 1236, 2022, false);

INSERT INTO public.community(community_id, name, description, visibility, location, picture)
VALUES (4000, 'Einars MC klubb', 'vi som liker motorsykkel og heter Einar', 0, 'Elgeseter gate(midt på natta)', 'bilde');

INSERT INTO public.community(community_id, name, description, visibility, location, picture)
VALUES (4001, 'Haakon MC klubb', 'vi som liker motorsykkel og heter Haakon', 0, 'Elgeseter gate(midt på natta)', 'bilde');

INSERT INTO public.community(community_id, name, description, visibility, location, picture)
VALUES (4002, 'Sander MC klubb', 'vi som liker motorsykkel og heter Sander', 0, 'Elgeseter gate(midt på natta)', 'bilde');

INSERT INTO public.community(community_id, name, description, visibility, location, picture)
VALUES (4444, 'Aleks MC klubb', 'vi som liker motorsykkel og heter Aleks', 0, 'Elgeseter gate(midt på natta)', 'bilde');

INSERT INTO public.community(community_id, description, location, name, picture, visibility)
VALUES (1000, 'En hyggelig dag', 'Storvold', 'Vi som liker været', 'Nei takk', 1);

INSERT INTO public.community(community_id, description, location, name, picture, visibility)
VALUES (1001, 'Fisk for folk', 'Ravnkloa', 'Det regner fisk', 'imagen a place ...with fish', 1);

INSERT INTO public.user_community(community_id, user_id, is_administrator)
VALUES (4000, 2022, true);

INSERT INTO public.user_community(community_id, user_id, is_administrator)
VALUES (4001, 2022, false);

INSERT INTO public.user_community(community_id, user_id, is_administrator)
VALUES (4444, 3034, false);

INSERT INTO public.user_community(community_id, user_id, is_administrator)
VALUES (1001, 3034, false);
