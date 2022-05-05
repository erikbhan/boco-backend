INSERT INTO public.user (user_id, email, first_name, last_name, address, picture, salt, hash)
VALUES (2022, 'test@email.com', 'test', 'user', 'kalvskinnet', 'ok', 'l/hjdIHi9Us2uJZ7MP/urY6ALjISdukPrN5sjpD7wTMEV+DnQkWzOF3qfnO6r2PnIQM6zP7ZcdEYh0Gdok8nFQ==', 'Ge7Y9frKWdgKcAysHdYCIoOOsAcn9We3f2+C74xlc6kWQZn2scBE8sEf4iZezwsmG/KdeeEuspZD9Q4Ojt27Hg==');

INSERT INTO public.user (user_id, email, first_name, last_name, address, picture, salt, hash)
VALUES (2023, 'test2@email.com', 'test', 'user', 'kalvskinnet', 'ok', 'l/hjdIHi9Us2uJZ7MP/urY6ALjISdukPrN5sjpD7wTMEV+DnQkWzOF3qfnO6r2PnIQM6zP7ZcdEYh0Gdok8nFQ==', 'Ge7Y9frKWdgKcAysHdYCIoOOsAcn9We3f2+C74xlc6kWQZn2scBE8sEf4iZezwsmG/KdeeEuspZD9Q4Ojt27Hg==');

INSERT INTO public.listing(listing_id, address, description, price_per_day, title, user_id, deleted)
VALUES (3000, 'hoi', 'hei', 2, 'Folk og fe', 2023, false);

INSERT INTO public.community(community_id, description, location, name, picture, visibility)
VALUES (1000, 'En hyggelig dag', 'Storvold', 'Vi som liker været', 'Nei takk', 1);

INSERT INTO public.user_community(community_id, user_id, is_administrator)
VALUES (1000, 2023, false);

INSERT INTO public.rent(rent_id, from_time, is_accepted, to_time, listing_owner_id, renter_id, is_deleted, created_at)
VALUES (4000, 1610456400000, true, 1610467200000, 3000, 2022, false, 1);