DELETE FROM public.rent WHERE listing_owner_id = '1234';
DELETE FROM public.rent WHERE listing_owner_id = '1235';
DELETE FROM public.rent WHERE listing_owner_id = '1236';

DELETE FROM public.listing WHERE listing_id = 1234;
DELETE FROM public.listing WHERE listing_id = 1235;
DELETE FROM public.listing WHERE listing_id = 1236;

DELETE FROM public.user WHERE email = 'fake@user.com';
DELETE FROM public.user WHERE email = 'test@email.com';