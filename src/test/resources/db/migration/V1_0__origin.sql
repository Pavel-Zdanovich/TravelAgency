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
    'wi-fi',
    'air conditioner',
    'mini-bar',
    'car rental',
    'room service',
    'cable tv',
    'spa',
    'swimming pool',
    'restaurant',
    'parking'
);

CREATE TYPE public.types_of_tours AS ENUM (
    'treatment',
    'tourism',
    'leisure',
    'business',
    'pilgrimage',
    'training',
    'sport competition',
    'rural tourism',
    'scientific expedition',
    'ecotourism'
);

CREATE TABLE public.countries (
    country_id bigint NOT NULL,
    name character varying(50) NOT NULL,
    CONSTRAINT country_name_check CHECK ((length((name)::text) >= 3)),
    CONSTRAINT country_name_not_null CHECK ((name IS NOT NULL))
);

CREATE TABLE public.hotels (
    hotel_id bigint NOT NULL,
    name character varying(50) NOT NULL,
    stars integer NOT NULL,
    website character varying(75),
    --coordinate public.geometry(PointZ,4326),
    features public.types_of_features[] NOT NULL,
    CONSTRAINT hotel_name_check CHECK ((length((name)::text) >= 3)),
    CONSTRAINT hotel_name_not_null CHECK ((name IS NOT NULL)),
    CONSTRAINT hotel_stars_check CHECK (((stars > 0) AND (stars < 6))),
    CONSTRAINT hotel_stars_not_null CHECK ((stars IS NOT NULL)),
    CONSTRAINT hotel_website_host_regex_check CHECK (((website)::text ~ '(/|(www(\.))).{1,80}(\.)com$'::text)),
    CONSTRAINT hotel_website_not_null CHECK ((website IS NOT NULL)),
    CONSTRAINT hotel_website_protocol_regex_check CHECK (((website)::text ~ '^(http|https)://'::text))
);

CREATE TABLE public.reviews (
    review_id bigint NOT NULL,
    date timestamp with time zone,
    text character varying(255),
    user_id bigint NOT NULL,
    tour_id bigint NOT NULL,
    CONSTRAINT review_date_check CHECK ((date >= now())),
    CONSTRAINT review_date_not_null CHECK ((date IS NOT NULL)),
    CONSTRAINT review_text_not_null CHECK ((text IS NOT NULL)),
    CONSTRAINT review_tour_id_not_null CHECK ((tour_id IS NOT NULL)),
    CONSTRAINT review_user_not_null CHECK ((user_id IS NOT NULL))
);

CREATE TABLE public.tours (
    tour_id bigint NOT NULL,
    photo character varying(255),
    start_date timestamp with time zone,
    end_date timestamp with time zone,
    description text,
    cost money,
    tour_type public.types_of_tours,
    hotel_id bigint NOT NULL,
    country_id bigint NOT NULL,
    CONSTRAINT tour_cost_check CHECK ((cost > '$0.00'::money)),
    CONSTRAINT tour_cost_not_null CHECK ((cost IS NOT NULL)),
    CONSTRAINT tour_country_not_null CHECK ((country_id IS NOT NULL)),
    CONSTRAINT tour_end_date_check CHECK ((age(end_date, start_date) > '1 day'::interval)),
    CONSTRAINT tour_end_date_not_null CHECK ((end_date IS NOT NULL)),
    CONSTRAINT tour_hotel_not_null CHECK ((hotel_id IS NOT NULL)),
    CONSTRAINT tour_start_date_check CHECK ((start_date >= now())),
    CONSTRAINT tour_start_date_not_null CHECK ((start_date IS NOT NULL))
);

CREATE TABLE public.users (
    user_id bigint NOT NULL,
    login character varying(30),
    password character varying(30),
    CONSTRAINT user_login_check CHECK ((length((login)::text) > 4)),
    CONSTRAINT user_login_not_null CHECK ((login IS NOT NULL)),
    CONSTRAINT user_password_check CHECK ((length((password)::text) > 4)),
    CONSTRAINT user_password_not_null CHECK ((password IS NOT NULL))
);

CREATE TABLE public.users_tours (
    user_id bigint NOT NULL,
    tour_id bigint NOT NULL
);