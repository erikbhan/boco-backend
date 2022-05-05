INSERT INTO public.user (user_id, email, first_name, last_name, address, picture, salt, hash)
VALUES (2022, 'test@email.com', 'test', 'user', 'gløshaugen', 'ok', 'l/hjdIHi9Us2uJZ7MP/urY6ALjISdukPrN5sjpD7wTMEV+DnQkWzOF3qfnO6r2PnIQM6zP7ZcdEYh0Gdok8nFQ==', 'Ge7Y9frKWdgKcAysHdYCIoOOsAcn9We3f2+C74xlc6kWQZn2scBE8sEf4iZezwsmG/KdeeEuspZD9Q4Ojt27Hg==');

INSERT INTO public.user (user_id, email, first_name, last_name, address, picture, salt, hash)
VALUES (2023, 'test@email.com', 'test', 'user', 'gløshaugen', 'ok', 'l/hjdIHi9Us2uJZ7MP/urY6ALjISdukPrN5sjpD7wTMEV+DnQkWzOF3qfnO6r2PnIQM6zP7ZcdEYh0Gdok8nFQ==', 'Ge7Y9frKWdgKcAysHdYCIoOOsAcn9We3f2+C74xlc6kWQZn2scBE8sEf4iZezwsmG/KdeeEuspZD9Q4Ojt27Hg==');

INSERT INTO public.community(community_id, description, location, name, picture, visibility)
VALUES (3000, 'En hyggelig dag', 'Storvold', 'Vi som liker været', 'Nei takk', 0);

INSERT INTO public.community(community_id, description, location, name, picture, visibility)
VALUES (3001, 'En hyggelig dag', 'Storvold', 'Vi som liker været', 'Nei takk', 1);

INSERT INTO public.community(community_id, description, location, name, picture, visibility)
VALUES (3002, 'En hyggelig dag', 'Storvold', 'Vi som liker været', 'Nei takk', 0);

INSERT INTO public.community(community_id, description, location, name, picture, visibility)
VALUES (3003, 'En hyggelig dag', 'Storvold', 'Vi som liker været', 'Nei takk', 0);

INSERT INTO public.community(community_id, description, location, name, picture, visibility)
VALUES (3004, 'En hyggelig dag', 'Storvold', 'Vi som liker været', 'Nei takk', 0);

INSERT INTO public.community(community_id, description, location, name, picture, visibility)
VALUES (3005, 'En hyggelig dag', 'Storvold', 'Vi som liker været', 'Nei takk', 0);

INSERT INTO public.community(community_id, description, location, name, picture, visibility)
VALUES (3006, 'En hyggelig dag', 'Storvold', 'Vi som liker været', 'Nei takk', 0);

INSERT INTO public.community(community_id, description, location, name, picture, visibility)
VALUES (3007, 'En hyggelig dag', 'Storvold', 'Vi som liker været', 'Nei takk', 0);

INSERT INTO public.community(community_id, description, location, name, picture, visibility)
VALUES (3008, 'En hyggelig dag', 'Storvold', 'Vi som liker været', 'Nei takk', 0);

INSERT INTO public.community(community_id, description, location, name, picture, visibility)
VALUES (3009, 'En hyggelig dag', 'Storvold', 'Vi som liker været', 'Nei takk', 0);

INSERT INTO public.community_request(text, user_id, community_id)
VALUES ('HEI JEG VIL VÆRE MED TAKK', 2023, 3003);

INSERT INTO public.community_request(text, user_id, community_id)
VALUES ('HEI JEG VIL VÆRE MED TAKK', 2023, 3004);

INSERT INTO public.community_request(text, user_id, community_id)
VALUES ('HEI JEG VIL VÆRE MED TAKK', 2022, 3005);

INSERT INTO public.community_request(text, user_id, community_id)
VALUES ('HEI JEG VIL VÆRE MED TAKK', 2023, 3006);

INSERT INTO public.community_request(text, user_id, community_id)
VALUES ('HEI JEG VIL VÆRE MED TAKK', 2023, 3007);

INSERT INTO public.community_request(text, user_id, community_id)
VALUES ('HEI JEG VIL VÆRE MED TAKK', 2022, 3008);

INSERT INTO public.community_request(text, user_id, community_id)
VALUES ('HEI JEG VIL VÆRE MED TAKK', 2023, 3009);

INSERT INTO public.user_community(community_id, user_id, is_administrator)
VALUES (3002, 2022, false);

INSERT INTO public.user_community(community_id, user_id, is_administrator)
VALUES (3003, 2022, true);

INSERT INTO public.user_community(community_id, user_id, is_administrator)
VALUES (3004, 2022, false);

INSERT INTO public.user_community(community_id, user_id, is_administrator)
VALUES (3005, 2022, false);

INSERT INTO public.user_community(community_id, user_id, is_administrator)
VALUES (3006, 2022, true);

INSERT INTO public.user_community(community_id, user_id, is_administrator)
VALUES (3007, 2022, false);

INSERT INTO public.user_community(community_id, user_id, is_administrator)
VALUES (3008, 2022, true);