--CREATE EXTENSION postgis;
-- Enable Topology
--CREATE EXTENSION postgis_topology;
-- Enable PostGIS Advanced 3D
-- and other geoprocessing algorithms
-- sfcgal not available with all distributions
--CREATE EXTENSION postgis_sfcgal;
-- fuzzy matching needed for Tiger
--CREATE EXTENSION fuzzystrmatch;
-- rule based standardizer
--CREATE EXTENSION address_standardizer;
-- example rule data set
--CREATE EXTENSION address_standardizer_data_us;

-- Enable US Tiger Geocoder

--CREATE EXTENSION postgis_tiger_geocoder;

CREATE TYPE public.types_of_features AS ENUM (
    'WI_FI',
    'AIR_CONDITIONER',
    'MINI_BAR',
    'CAR_RENTAL',
    'ROOM_SERVICE',
    'CABLE_TV',
    'SPA',
    'SWIMMING_POOL',
    'RESTAURANT',
    'PARKING'
);

CREATE TYPE public.types_of_tours AS ENUM (
    'TREATMENT',
    'TOURISM',
    'LEISURE',
    'BUSINESS',
    'PILGRIMAGE',
    'TRAINING',
    'SPORT_COMPETITION',
    'RURAL_TOURISM',
    'SCIENTIFIC_EXPEDITION',
    'ECOTOURISM'
);

CREATE TABLE public.countries (
    country_id serial NOT NULL,
    name character varying(50) NOT NULL,
    CONSTRAINT country_id PRIMARY KEY (country_id),
    CONSTRAINT country_name_unique UNIQUE (name),
    CONSTRAINT country_name_not_null CHECK ((name IS NOT NULL)),
    CONSTRAINT country_name_check CHECK ((length((name)::text) >= 3))
);

CREATE TABLE public.hotels (
    hotel_id serial NOT NULL,
    name character varying(50) NOT NULL,
    stars integer NOT NULL,
    website character varying(75),
    coordinate bytea NOT NULL,--public.geometry(PointZ,4326),
    features public.types_of_features[] NOT NULL,
    CONSTRAINT hotel_id PRIMARY KEY (hotel_id),
    CONSTRAINT hotel_name_unique UNIQUE (name),
    CONSTRAINT hotel_website_unique UNIQUE (website),
    CONSTRAINT hotel_website_host_regex_check CHECK (website::text ~ '(/|(www(\.))).{1,80}(\.)com$'::text),
    CONSTRAINT hotel_website_protocol_regex_check CHECK (website::text ~ '^(http|https)://'::text),
    CONSTRAINT hotel_website_not_null CHECK (website IS NOT NULL),
    CONSTRAINT hotel_stars_not_null CHECK (stars IS NOT NULL),
    CONSTRAINT hotel_stars_check CHECK (stars > 0 AND stars < 6),
    CONSTRAINT hotel_name_not_null CHECK (name IS NOT NULL),
    CONSTRAINT hotel_name_check CHECK (length(name::text) >= 3)
);

CREATE TABLE public.users (
    user_id serial NOT NULL,
    login character varying(30),
    password character varying(30),
    CONSTRAINT user_id PRIMARY KEY (user_id),
    CONSTRAINT user_login_unique UNIQUE (login),
    CONSTRAINT user_login_check CHECK ((length((login)::text) > 4)),
    CONSTRAINT user_login_not_null CHECK ((login IS NOT NULL)),
    CONSTRAINT user_password_check CHECK ((length((password)::text) > 4)),
    CONSTRAINT user_password_not_null CHECK ((password IS NOT NULL))
);

CREATE TABLE public.tours (
    tour_id serial NOT NULL,
    photo character varying(255),
    start_date timestamp with time zone,
    end_date timestamp with time zone,
    description text,
    cost numeric(19,4),
    tour_type public.types_of_tours,
    hotel_id serial NOT NULL,
    country_id serial NOT NULL,
    CONSTRAINT tour_id PRIMARY KEY (tour_id),
        CONSTRAINT tours_country_id_fkey FOREIGN KEY (country_id)
            REFERENCES public.countries (country_id) MATCH SIMPLE,
            --ON UPDATE NO ACTION,
            --ON DELETE NO ACTION,
        CONSTRAINT tours_hotel_id_fkey FOREIGN KEY (hotel_id)
            REFERENCES public.hotels (hotel_id) MATCH SIMPLE,
            --ON UPDATE NO ACTION,
            --ON DELETE NO ACTION,
    CONSTRAINT tour_cost_check CHECK ((cost > 0.0000)),
    CONSTRAINT tour_cost_not_null CHECK ((cost IS NOT NULL)),
    CONSTRAINT tour_country_not_null CHECK ((country_id IS NOT NULL)),
    CONSTRAINT tour_end_date_check CHECK ((age(end_date, start_date) > '1 day'::interval)),
    CONSTRAINT tour_end_date_not_null CHECK ((end_date IS NOT NULL)),
    CONSTRAINT tour_hotel_not_null CHECK ((hotel_id IS NOT NULL)),
    CONSTRAINT tour_start_date_check CHECK ((start_date >= now())),
    CONSTRAINT tour_start_date_not_null CHECK ((start_date IS NOT NULL))
);

CREATE TABLE public.users_tours (
    user_id serial NOT NULL,
    tour_id serial NOT NULL,
    CONSTRAINT users_tours_primary_key PRIMARY KEY (user_id, tour_id),
        CONSTRAINT users_tours_tour_id_fkey FOREIGN KEY (tour_id)
            REFERENCES public.tours (tour_id) MATCH SIMPLE,
            --ON UPDATE NO ACTION,
            --ON DELETE NO ACTION,
        CONSTRAINT users_tours_user_id_fkey FOREIGN KEY (user_id)
            REFERENCES public.users (user_id) MATCH SIMPLE
            --ON UPDATE NO ACTION
            --ON DELETE NO ACTION
);

CREATE TABLE public.reviews (
    review_id serial NOT NULL,
    date timestamp with time zone,
    text character varying(255),
    user_id serial NOT NULL,
    tour_id serial NOT NULL,
    CONSTRAINT review_id PRIMARY KEY (review_id),
        CONSTRAINT reviews_tour_id_fkey FOREIGN KEY (tour_id)
            REFERENCES public.tours (tour_id) MATCH SIMPLE,
            --ON UPDATE NO ACTION,
            --ON DELETE NO ACTION,
        CONSTRAINT reviews_user_id_fkey FOREIGN KEY (user_id)
            REFERENCES public.users (user_id) MATCH SIMPLE,
            --ON UPDATE NO ACTION,
            --ON DELETE NO ACTION,
    CONSTRAINT review_date_check CHECK ((date >= now())),
    CONSTRAINT review_date_not_null CHECK ((date IS NOT NULL)),
    CONSTRAINT review_text_not_null CHECK ((text IS NOT NULL)),
    CONSTRAINT review_tour_id_not_null CHECK ((tour_id IS NOT NULL)),
    CONSTRAINT review_user_not_null CHECK ((user_id IS NOT NULL))
);

INSERT INTO public.countries VALUES (1, 'France');
INSERT INTO public.countries VALUES (2, 'SPAin');
INSERT INTO public.countries VALUES (3, 'USA');
INSERT INTO public.countries VALUES (4, 'China');
INSERT INTO public.countries VALUES (5, 'Italy');
INSERT INTO public.countries VALUES (6, 'Mexico');
INSERT INTO public.countries VALUES (7, 'Great Britain');
INSERT INTO public.countries VALUES (8, 'Turkey');
INSERT INTO public.countries VALUES (9, 'Germany');
INSERT INTO public.countries VALUES (10, 'Thailand');
INSERT INTO public.countries VALUES (11, 'Netherlands');
INSERT INTO public.countries VALUES (12, 'Japan');
INSERT INTO public.countries VALUES (13, 'Austria');
INSERT INTO public.countries VALUES (14, 'Greece');
INSERT INTO public.countries VALUES (15, 'Russia');
INSERT INTO public.countries VALUES (16, 'Canada');
INSERT INTO public.countries VALUES (17, 'Australia');
INSERT INTO public.countries VALUES (18, 'Egypt');
INSERT INTO public.countries VALUES (19, 'Israel');
INSERT INTO public.countries VALUES (20, 'India');
INSERT INTO public.countries VALUES (21, 'Vietnam');
INSERT INTO public.countries VALUES (22, 'Brazil');
INSERT INTO public.countries VALUES (23, 'Indonesia');
INSERT INTO public.countries VALUES (24, 'Switzerland');
INSERT INTO public.countries VALUES (25, 'Bulgaria');

INSERT INTO public.hotels (hotel_id, coordinate, features, name, stars, website) VALUES (1, 'SRID=4326;POINT(37.617635 55.755814 42.419420)', '{AIR_CONDITIONER, WI_FI, MINI_BAR, ROOM_SERVICE, RESTAURANT, PARKING}', 'Marriott', 5, 'http://www.marriott.com');
INSERT INTO public.hotels (hotel_id, coordinate, features, name, stars, website) VALUES (2, 'SRID=4326;POINT(18.576847 61.056834 83.487543)', '{AIR_CONDITIONER, WI_FI, MINI_BAR, ROOM_SERVICE, RESTAURANT, PARKING, SPA}', 'Emirates Palace', 5, 'http://www.emirates.com');
INSERT INTO public.hotels (hotel_id, coordinate, features, name, stars, website) VALUES (3, 'SRID=4326;POINT(24.832485 12.894357 67.934827)', '{AIR_CONDITIONER, WI_FI, RESTAURANT}', 'Grand Budapest', 3, 'http://www.grand.com');
INSERT INTO public.hotels (hotel_id, coordinate, features, name, stars, website) VALUES (4, 'SRID=4326;POINT(13.479320 40.943209 20.234758)', '{AIR_CONDITIONER, WI_FI, CABLE_TV, ROOM_SERVICE, SWIMMING_POOL}', 'Hilton', 4, 'http://www.hilton.com');
INSERT INTO public.hotels (hotel_id, coordinate, features, name, stars, website) VALUES (5, 'SRID=4326;POINT(51.498572 29.348924 35.787928)', '{AIR_CONDITIONER, WI_FI, MINI_BAR, SPA, SWIMMING_POOL, PARKING}', 'The Plaze', 4, 'http://www.plaza.com');
INSERT INTO public.hotels (hotel_id, coordinate, features, name, stars, website) VALUES (6, 'SRID=4326;POINT(10.574832 19.234718 12.238345)', '{WI_FI, MINI_BAR, RESTAURANT}', 'Trash Can', 1, 'http://www.trashcan.com');

INSERT INTO public.users (user_id, login, password) VALUES (1, 'ElonMusk', 'SpaceXXX');
INSERT INTO public.users (user_id, login, password) VALUES (2, 'StephenHawking', 'Universe');
INSERT INTO public.users (user_id, login, password) VALUES (3, 'NicolaTesla', 'HighVoltage');
INSERT INTO public.users (user_id, login, password) VALUES (4, 'CharlesDarwin', 'Evolution');
INSERT INTO public.users (user_id, login, password) VALUES (5, 'AlbertEinstein', 'TheoryOfRelativity');
INSERT INTO public.users (user_id, login, password) VALUES (6, 'AlexLuca', 'Potato');

INSERT INTO public.tours (tour_id, cost, description, end_date, photo, start_date, tour_type, country_id, hotel_id) VALUES (1, 100.0000, 'good tour', '2019-11-30 00:00:00'::timestamp with time zone, 'src/main/resources/application.properties', '2019-10-01 00:00:00'::timestamp with time zone, 'LEISURE', 1, 1);
INSERT INTO public.tours (tour_id, cost, description, end_date, photo, start_date, tour_type, country_id, hotel_id) VALUES (2, 200.0000, 'best sale', '2019-10-30 00:00:00'::timestamp with time zone, 'src/main/resources/application.properties', '2019-10-15 00:00:00'::timestamp with time zone, 'TREATMENT', 11, 2);
INSERT INTO public.tours (tour_id, cost, description, end_date, photo, start_date, tour_type, country_id, hotel_id) VALUES (3, 300.0000, 'two for one deal', '2019-10-01 00:00:00'::timestamp with time zone, 'src/main/resources/application.properties', '2019-09-20 00:00:00'::timestamp with time zone, 'SPORT_COMPETITION', 21, 3);
INSERT INTO public.tours (tour_id, cost, description, end_date, photo, start_date, tour_type, country_id, hotel_id) VALUES (4, 400.0000, 'back to the future', '2019-11-11 00:00:00'::timestamp with time zone, 'src/main/resources/application.properties', '2019-11-01 00:00:00'::timestamp with time zone, 'LEISURE', 25, 4);
INSERT INTO public.tours (tour_id, cost, description, end_date, photo, start_date, tour_type, country_id, hotel_id) VALUES (5, 500.0000, 'time travel', '2019-11-10 00:00:00'::timestamp with time zone, 'src/main/resources/application.properties', '2019-10-09 00:00:00'::timestamp with time zone, 'BUSINESS', 6, 5);
INSERT INTO public.tours (tour_id, cost, description, end_date, photo, start_date, tour_type, country_id, hotel_id) VALUES (6, 600.0000, 'school trip', '2019-11-12 00:00:00'::timestamp with time zone, 'src/main/resources/application.properties', '2019-09-30 00:00:00'::timestamp with time zone, 'RURAL_TOURISM', 18, 6);

INSERT INTO public.users_tours VALUES (1, 1);
INSERT INTO public.users_tours VALUES (2, 2);
INSERT INTO public.users_tours VALUES (3, 3);
INSERT INTO public.users_tours VALUES (4, 4);
INSERT INTO public.users_tours VALUES (5, 5);
INSERT INTO public.users_tours VALUES (6, 6);

INSERT INTO public.reviews (review_id, date, text, user_id, tour_id) VALUES (1, '2019-09-10 00:00:00'::timestamp with time zone, 'sounds good', 1, 1);
INSERT INTO public.reviews (review_id, date, text, user_id, tour_id) VALUES (2, '2019-09-11 00:00:00'::timestamp with time zone, 'interesting', 2, 2);
INSERT INTO public.reviews (review_id, date, text, user_id, tour_id) VALUES (3, '2019-09-12 00:00:00'::timestamp with time zone, 'i hope i will die', 3, 3);
INSERT INTO public.reviews (review_id, date, text, user_id, tour_id) VALUES (4, '2019-10-10 00:00:00'::timestamp with time zone, 'must have', 4, 4);
INSERT INTO public.reviews (review_id, date, text, user_id, tour_id) VALUES (5, '2019-10-15 00:00:00'::timestamp with time zone, 'It was relatively well', 5, 5);
INSERT INTO public.reviews (review_id, date, text, user_id, tour_id) VALUES (6, '2019-10-10 00:00:00'::timestamp with time zone, 'death better', 6, 6);