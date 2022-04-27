DELETE FROM public.rent WHERE listing_owner_id = '1234';
DELETE FROM public.rent WHERE listing_owner_id = '1235';
DELETE FROM public.rent WHERE listing_owner_id = '1236';

DELETE FROM public.user_community WHERE community_id = 4000;
DELETE FROM public.user_community WHERE community_id = 4001;
DELETE FROM public.user_community WHERE community_id= 4444;
DELETE FROM public.user_community WHERE user_id = 2022;
DELETE FROM public.user_community WHERE user_id = 3034;

DELETE FROM public.community WHERE community_id = 4000;
DELETE FROM public.community WHERE community_id = 4001;
DELETE FROM public.community WHERE community_id = 4002;
DELETE FROM public.community WHERE community_id = 4444;
DELETE FROM public.community WHERE community_id = 1000;
DELETE FROM public.community WHERE community_id = 1001;
DELETE FROM public.community WHERE description = 'kul klubb';
DELETE FROM public.community WHERE name = 'MCklubb';

DELETE FROM public.listing WHERE listing_id = 1234;
DELETE FROM public.listing WHERE listing_id = 1235;
DELETE FROM public.listing WHERE listing_id = 1236;

DELETE FROM public.User WHERE email = 'erna@solberg.no';
DELETE FROM public.user WHERE email = 'fake@user.com';
DELETE FROM public.user WHERE email = 'test@email.com';