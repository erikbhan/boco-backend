DELETE FROM public.user_community WHERE user_id = 1;

DELETE FROM public.user_community WHERE community_id = 4000;
DELETE FROM public.user_community WHERE community_id = 4001;
DELETE FROM public.user_community WHERE community_id= 4444;
DELETE FROM public.user_community WHERE user_id = 2022;
DELETE FROM public.user_community WHERE user_id = 1;

DELETE FROM public.community WHERE community_id = 4000;
DELETE FROM public.community WHERE community_id = 4001;
DELETE FROM public.community WHERE community_id = 4002;
DELETE FROM public.community WHERE community_id = 4444;
DELETE FROM public.community WHERE community_id = 1000;
DELETE FROM public.community WHERE community_id = 1001;
DELETE FROM public.community WHERE description = 'kul klubb';

DELETE FROM public.user WHERE email = 'test@email.com';
Delete from public.user WHERE email = 'erna@solberg.com';
DELETE FROM public.user WHERE email = 'fake@user.com';