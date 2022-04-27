DELETE FROM public.user_community WHERE user_id = 1;
DELETE FROM public.rent WHERE listing_owner_id = '1234';
DELETE FROM public.rent WHERE listing_owner_id = '1235';
DELETE FROM public.rent WHERE listing_owner_id = '1236';

DELETE FROM listing_category WHERE category_id = 420 or category_id = 520;
DELETE FROM community_listing WHERE community_id = 1000 or
community_id = 1001;

DELETE FROM public.listing WHERE listing_id = '1234';
DELETE FROM public.listing WHERE listing_id = '1235';
DELETE FROM public.listing WHERE listing_id = '1236';
DELETE FROM public.listing WHERE listing_id = '1236';
DELETE FROM public.listing WHERE user_id = 2022;

DELETE FROM public.community WHERE community_id = 1000;
DELETE FROM public.community WHERE community_id = 1001; 

DELETE FROM public.category WHERE category_id = 420;
DELETE FROM public.category WHERE category_id = 520;

DELETE FROM public.User WHERE email = 'erna@solberg.no';
DELETE FROM public.user WHERE email = 'fake@user.com';
DELETE FROM public.user WHERE email = 'test@email.com';


DELETE FROM public.community WHERE community_id = 1000;
DELETE FROM public.community WHERE community_id = 1001;
