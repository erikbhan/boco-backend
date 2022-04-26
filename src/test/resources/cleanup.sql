DELETE FROM public.user_community WHERE user_id = 1;
DELETE FROM public.rent WHERE listing_owner_id = '1234';
DELETE FROM public.rent WHERE listing_owner_id = '1235';
DELETE FROM public.rent WHERE listing_owner_id = '1236';

delete from listing_category;
delete from community_listing;

DELETE FROM public.listing WHERE listing_id = '1234';
DELETE FROM public.listing WHERE listing_id = '1235';
DELETE FROM public.listing WHERE listing_id = '1236';
DELETE FROM public.listing WHERE listing_id = '1236';
DELETE FROM public.listing;

DELETE FROM public.community WHERE community_id = 1000;
DELETE FROM public.community WHERE community_id = 1001; 

DELETE FROM public.category where category_id = 420;
DELETE FROM public.category where category_id = 520;

DELETE FROM public.user WHERE email = 'fake@user.com';
DELETE FROM public.user WHERE email = 'test@email.com';


