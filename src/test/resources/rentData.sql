INSERT INTO public.user (user_id, email, first_name, last_name, address, picture, salt, hash)
VALUES (2022, 'test@email.com', 'test', 'user', 'gløshaugen', 'ok', 'l/hjdIHi9Us2uJZ7MP/urY6ALjISdukPrN5sjpD7wTMEV+DnQkWzOF3qfnO6r2PnIQM6zP7ZcdEYh0Gdok8nFQ==', 'Ge7Y9frKWdgKcAysHdYCIoOOsAcn9We3f2+C74xlc6kWQZn2scBE8sEf4iZezwsmG/KdeeEuspZD9Q4Ojt27Hg==');

INSERT INTO public.user (user_id, email, first_name, last_name, address, picture, salt, hash)
VALUES (3034, 'fake@user.com', 'fake', 'user', 'gløshaugen', 'ok', 'l/hjdIHi9Us2uJZ7MP/urY6ALjISdukPrN5sjpD7wTMEV+DnQkWzOF3qfnO6r2PnIQM6zP7ZcdEYh0Gdok8nFQ==', 'Ge7Y9frKWdgKcAysHdYCIoOOsAcn9We3f2+C74xlc6kWQZn2scBE8sEf4iZezwsmG/KdeeEuspZD9Q4Ojt27Hg==');

INSERT INTO public.listing(listing_id, address, description, price_per_day, title, user_id, deleted)
VALUES (1234, 'kjopmansgata', 'you look cute', 14, 'pls holp', 3034, false);

INSERT INTO public.listing(listing_id, address, description, price_per_day, title, user_id, deleted)
VALUES (1235, 'kjopmansgata', 'hi', 12, 'come here', 3034, false);

INSERT INTO public.listing(listing_id, address, description, price_per_day, title, user_id, deleted)
VALUES (1236, 'kjopmansgata', 'yo', 21, 'get over here', 3034, false);

INSERT INTO public.rent(rent_id, from_time, is_accepted, to_time, listing_owner_id, renter_id, is_deleted, created_at)
VALUES (10000, 1610456400000, true, 1610467200000, 1234, 2022 , false, 1);

INSERT INTO public.rent(rent_id, from_time, is_accepted, to_time, listing_owner_id, renter_id, is_deleted, created_at)
VALUES (10001, 1610456400000, false, 1610467200000, 1236, 2022, false, 1);
