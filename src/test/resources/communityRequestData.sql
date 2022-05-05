INSERT INTO public.user (user_id, email, first_name, last_name, address, picture, salt, hash)
VALUES (2022, 'test@email.com', 'test', 'user', 'gløshaugen', 'ok', 'l/hjdIHi9Us2uJZ7MP/urY6ALjISdukPrN5sjpD7wTMEV+DnQkWzOF3qfnO6r2PnIQM6zP7ZcdEYh0Gdok8nFQ==', 'Ge7Y9frKWdgKcAysHdYCIoOOsAcn9We3f2+C74xlc6kWQZn2scBE8sEf4iZezwsmG/KdeeEuspZD9Q4Ojt27Hg==');

INSERT INTO public.community(community_id, description, location, name, picture, visibility)
VALUES (3000, 'En hyggelig dag', 'Storvold', 'Vi som liker været', 'Nei takk', 0);

INSERT INTO public.community(community_id, description, location, name, picture, visibility)
VALUES (3001, 'En hyggelig dag', 'Storvold', 'Vi som liker været', 'Nei takk', 1);

INSERT INTO public.community(community_id, description, location, name, picture, visibility)
VALUES (3002, 'En hyggelig dag', 'Storvold', 'Vi som liker været', 'Nei takk', 0);

INSERT INTO public.user_community(community_id, user_id, is_administrator)
VALUES (3002, 2022, false);