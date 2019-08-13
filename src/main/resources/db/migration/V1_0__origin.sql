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
    CONSTRAINT country_id PRIMARY KEY (country_id),
    CONSTRAINT country_name_unique UNIQUE (name),
    CONSTRAINT country_name_not_null CHECK ((name IS NOT NULL)),
    CONSTRAINT country_name_check CHECK ((length((name)::text) >= 3))
);

CREATE TABLE public.hotels (
    hotel_id bigint NOT NULL,
    name character varying(50) NOT NULL,
    stars integer NOT NULL,
    website character varying(75),
    --coordinate public.geometry(PointZ,4326),
    features public.types_of_features[] NOT NULL,
    CONSTRAINT hotel_id PRIMARY KEY (hotel_id),
    CONSTRAINT hotel_name_unique UNIQUE (name),
    CONSTRAINT hotel_website_host_regex_check CHECK (website::text ~ '(/|(www(\.))).{1,80}(\.)com$'::text),
    CONSTRAINT hotel_website_protocol_regex_check CHECK (website::text ~ '^(http|https)://'::text),
    CONSTRAINT hotel_website_not_null CHECK (website IS NOT NULL),
    CONSTRAINT hotel_stars_not_null CHECK (stars IS NOT NULL),
    CONSTRAINT hotel_stars_check CHECK (stars > 0 AND stars < 6),
    CONSTRAINT hotel_name_not_null CHECK (name IS NOT NULL),
    CONSTRAINT hotel_name_check CHECK (length(name::text) >= 3)
);

CREATE TABLE public.reviews (
    review_id bigint NOT NULL,
    date timestamp with time zone,
    text character varying(255),
    user_id bigint NOT NULL,
    tour_id bigint NOT NULL,
    CONSTRAINT review_id PRIMARY KEY (review_id),
        CONSTRAINT reviews_tour_id_fkey FOREIGN KEY (tour_id)
            REFERENCES public.tours (tour_id) MATCH SIMPLE
            ON UPDATE NO ACTION
            ON DELETE NO ACTION,
        CONSTRAINT reviews_user_id_fkey FOREIGN KEY (user_id)
            REFERENCES public.users (user_id) MATCH SIMPLE
            ON UPDATE NO ACTION
            ON DELETE NO ACTION,
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
    cost numeric(19,4),
    tour_type public.types_of_tours,
    hotel_id bigint NOT NULL,
    country_id bigint NOT NULL,
    CONSTRAINT tour_id PRIMARY KEY (tour_id),
        CONSTRAINT tours_country_id_fkey FOREIGN KEY (country_id)
            REFERENCES public.countries (country_id) MATCH SIMPLE
            ON UPDATE NO ACTION
            ON DELETE NO ACTION,
        CONSTRAINT tours_hotel_id_fkey FOREIGN KEY (hotel_id)
            REFERENCES public.hotels (hotel_id) MATCH SIMPLE
            ON UPDATE NO ACTION
            ON DELETE NO ACTION,
    CONSTRAINT tour_cost_check CHECK ((cost > 0.0000)),
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
    CONSTRAINT user_id PRIMARY KEY (user_id),
    CONSTRAINT user_login_unique UNIQUE (login),
    CONSTRAINT user_login_check CHECK ((length((login)::text) > 4)),
    CONSTRAINT user_login_not_null CHECK ((login IS NOT NULL)),
    CONSTRAINT user_password_check CHECK ((length((password)::text) > 4)),
    CONSTRAINT user_password_not_null CHECK ((password IS NOT NULL))
);

CREATE TABLE public.users_tours (
    user_id bigint NOT NULL,
    tour_id bigint NOT NULL,
    CONSTRAINT users_tours_primary_key PRIMARY KEY (user_id, tour_id),
        CONSTRAINT users_tours_tour_id_fkey FOREIGN KEY (tour_id)
            REFERENCES public.tours (tour_id) MATCH SIMPLE
            ON UPDATE NO ACTION
            ON DELETE NO ACTION,
        CONSTRAINT users_tours_user_id_fkey FOREIGN KEY (user_id)
            REFERENCES public.users (user_id) MATCH SIMPLE
            ON UPDATE NO ACTION
            ON DELETE NO ACTION
);