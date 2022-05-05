INSERT INTO public.user (user_id, email, first_name, last_name, address, picture, salt, hash)
VALUES (2022, 'test@email.com', 'test', 'user', 'gløshaugen', 'ok', 'l/hjdIHi9Us2uJZ7MP/urY6ALjISdukPrN5sjpD7wTMEV+DnQkWzOF3qfnO6r2PnIQM6zP7ZcdEYh0Gdok8nFQ==', 'Ge7Y9frKWdgKcAysHdYCIoOOsAcn9We3f2+C74xlc6kWQZn2scBE8sEf4iZezwsmG/KdeeEuspZD9Q4Ojt27Hg==');

INSERT INTO public.user (user_id, email, first_name, last_name, address, picture, salt, hash)
VALUES (4321, 'test@email.com', 'test', 'user', 'gløshaugen', 'ok', 'l/hjdIHi9Us2uJZ7MP/urY6ALjISdukPrN5sjpD7wTMEV+DnQkWzOF3qfnO6r2PnIQM6zP7ZcdEYh0Gdok8nFQ==', 'Ge7Y9frKWdgKcAysHdYCIoOOsAcn9We3f2+C74xlc6kWQZn2scBE8sEf4iZezwsmG/KdeeEuspZD9Q4Ojt27Hg==');

INSERT INTO public.listing(listing_id, address, description, price_per_day, title, user_id, deleted)
VALUES (1234, 'kjopmansgata', 'you look cute', 14, 'pls holp', 4321, false);

INSERT INTO public.rent(rent_id, from_time, is_accepted, to_time, listing_owner_id, renter_id, is_deleted, created_at)
VALUES (10000, 1609329600000, true, 1609358400000, 1234, 4321, false, 1);

INSERT INTO public.rent(rent_id, from_time, is_accepted, to_time, listing_owner_id, renter_id, is_deleted,  created_at)
VALUES (10001, 1610456400000, false, 1610467200000, 1234, 4321, false, 1);

INSERT INTO public.rent(rent_id, from_time, is_accepted, to_time, listing_owner_id, renter_id, is_deleted,  created_at)
VALUES (10002, 1610456400000, false, 1610467200000, 1234, 2022, false, 1);

INSERT INTO public.rating(rating_id, score, comment, renter_is_receiver_of_rating, rent_id)
VALUES (9876, 5, 'fy dævven, detta var sykt bra ass my dude!', true, 10000);

INSERT INTO public.rating(rating_id, score, comment, renter_is_receiver_of_rating, rent_id)
VALUES (9877, 1, 'fy dævven, detta var sykt dårlig ass my dude!', true, 10001);

INSERT INTO public.rating(rating_id, score, comment, renter_is_receiver_of_rating, rent_id)
VALUES (9878, 1, 'Fint støvsuger!', false, 10002);