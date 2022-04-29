DELETE FROM public.listing_picture WHERE listing_id = 4040;

DELETE FROM public.user_community WHERE community_id = 100001;
DELETE FROM public.user_community WHERE community_id = 100002;

DELETE FROM public.community_listing WHERE community_id = 100001;
DELETE FROM public.community_listing WHERE community_id = 100002;

DELETE FROM public.listing_category WHERE category_id = 420;
DELETE FROM public.listing_category WHERE category_id = 520;

DELETE FROM public.community WHERE community_id = 100001;
DELETE FROM public.community WHERE community_id = 100002;

DELETE FROM public.listing WHERE user_id = 666666;

DELETE FROM public.category WHERE category_id = 420;
DELETE FROM public.category WHERE category_id = 520;

DELETE FROM public.user WHERE user_id = 666666;