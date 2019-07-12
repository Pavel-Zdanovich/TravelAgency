CREATE EXTENSION postgis;
-- Enable Topology
CREATE EXTENSION postgis_topology;
-- Enable PostGIS Advanced 3D
-- and other geoprocessing algorithms
-- sfcgal not available with all distributions
CREATE EXTENSION postgis_sfcgal;
-- fuzzy matching needed for Tiger
CREATE EXTENSION fuzzystrmatch;
-- rule based standardizer
CREATE EXTENSION address_standardizer;
-- example rule data set
CREATE EXTENSION address_standardizer_data_us;

-- Enable US Tiger Geocoder

CREATE EXTENSION postgis_tiger_geocoder;

CREATE TYPE public.types_of_features AS ENUM (
    'wi-fi',
    'air conditioner',
    'mini-bar',
    'car rental',
    'room service',
    'cable tv'
);


ALTER TYPE public.types_of_features OWNER TO postgres;

--
-- TOC entry 2084 (class 1247 OID 34427)
-- Name: types_of_tours; Type: TYPE; Schema: public; Owner: postgres
--

CREATE TYPE public.types_of_tours AS ENUM (
    'treatment',
    'tourism',
    'leisure',
    'business'
);


ALTER TYPE public.types_of_tours OWNER TO postgres;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 209 (class 1259 OID 34128)
-- Name: countries; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.countries (
    country_id integer NOT NULL,
    name character varying(50) NOT NULL,
    CONSTRAINT country_name_check CHECK ((length((name)::text) >= 3)),
    CONSTRAINT country_name_not_null CHECK ((name IS NOT NULL))
);


ALTER TABLE public.countries OWNER TO postgres;

--
-- TOC entry 208 (class 1259 OID 34126)
-- Name: countries_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.countries_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.countries_id_seq OWNER TO postgres;

--
-- TOC entry 4858 (class 0 OID 0)
-- Dependencies: 208
-- Name: countries_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.countries_id_seq OWNED BY public.countries.country_id;


--
-- TOC entry 214 (class 1259 OID 34323)
-- Name: hotels; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.hotels (
    hotel_id integer NOT NULL,
    name character varying(50) NOT NULL,
    stars integer NOT NULL,
    website inet,
    coordinate public.geometry(PointZ,4326),
    CONSTRAINT hotel_name_check CHECK ((length((name)::text) >= 3)),
    CONSTRAINT hotel_name_not_null CHECK ((name IS NOT NULL)),
    CONSTRAINT hotel_stars_check CHECK (((stars > 0) AND (stars < 6))),
    CONSTRAINT hotel_stars_not_null CHECK ((stars IS NOT NULL)),
    CONSTRAINT hotel_website_not_null CHECK ((website IS NOT NULL)),
    CONSTRAINT hotel_website_regexp CHECK (((website)::text ~ 'www.%.{2,3}'::text))
);


ALTER TABLE public.hotels OWNER TO postgres;

--
-- TOC entry 212 (class 1259 OID 34319)
-- Name: hotels_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.hotels_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.hotels_id_seq OWNER TO postgres;

--
-- TOC entry 4859 (class 0 OID 0)
-- Dependencies: 212
-- Name: hotels_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.hotels_id_seq OWNED BY public.hotels.hotel_id;


--
-- TOC entry 213 (class 1259 OID 34321)
-- Name: hotels_stars_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.hotels_stars_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.hotels_stars_seq OWNER TO postgres;

--
-- TOC entry 4860 (class 0 OID 0)
-- Dependencies: 213
-- Name: hotels_stars_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.hotels_stars_seq OWNED BY public.hotels.stars;


--
-- TOC entry 221 (class 1259 OID 34464)
-- Name: reviews; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.reviews (
    review_id integer NOT NULL,
    date timestamp with time zone,
    text character varying(255),
    user_id integer NOT NULL,
    tour_id integer NOT NULL,
    CONSTRAINT review_date_check CHECK ((date >= now())),
    CONSTRAINT review_date_not_null CHECK ((date IS NOT NULL)),
    CONSTRAINT review_text_not_null CHECK ((text IS NOT NULL)),
    CONSTRAINT review_tour_id_not_null CHECK ((tour_id IS NOT NULL)),
    CONSTRAINT review_user_not_null CHECK ((user_id IS NOT NULL))
);


ALTER TABLE public.reviews OWNER TO postgres;

--
-- TOC entry 218 (class 1259 OID 34458)
-- Name: reviews_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.reviews_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.reviews_id_seq OWNER TO postgres;

--
-- TOC entry 4861 (class 0 OID 0)
-- Dependencies: 218
-- Name: reviews_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.reviews_id_seq OWNED BY public.reviews.review_id;


--
-- TOC entry 220 (class 1259 OID 34462)
-- Name: reviews_tour_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.reviews_tour_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.reviews_tour_id_seq OWNER TO postgres;

--
-- TOC entry 4862 (class 0 OID 0)
-- Dependencies: 220
-- Name: reviews_tour_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.reviews_tour_id_seq OWNED BY public.reviews.tour_id;


--
-- TOC entry 219 (class 1259 OID 34460)
-- Name: reviews_user_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.reviews_user_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.reviews_user_id_seq OWNER TO postgres;

--
-- TOC entry 4863 (class 0 OID 0)
-- Dependencies: 219
-- Name: reviews_user_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.reviews_user_id_seq OWNED BY public.reviews.user_id;


--
-- TOC entry 211 (class 1259 OID 34313)
-- Name: sets_of_features; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.sets_of_features (
    hotel_id integer NOT NULL,
    feature public.types_of_features NOT NULL
);


ALTER TABLE public.sets_of_features OWNER TO postgres;

--
-- TOC entry 210 (class 1259 OID 34311)
-- Name: sets_of_features_id_of_hotel_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.sets_of_features_id_of_hotel_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.sets_of_features_id_of_hotel_seq OWNER TO postgres;

--
-- TOC entry 4864 (class 0 OID 0)
-- Dependencies: 210
-- Name: sets_of_features_id_of_hotel_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.sets_of_features_id_of_hotel_seq OWNED BY public.sets_of_features.hotel_id;


--
-- TOC entry 225 (class 1259 OID 34483)
-- Name: tours; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tours (
    tour_id integer NOT NULL,
    photo bytea,
    date timestamp with time zone,
    duration timestamp with time zone,
    description text,
    cost money,
    tour_type public.types_of_tours,
    hotel_id integer NOT NULL,
    country_id integer NOT NULL,
    CONSTRAINT tour_cost_check CHECK ((cost > '$0.00'::money)),
    CONSTRAINT tour_cost_not_null CHECK ((cost IS NOT NULL)),
    CONSTRAINT tour_country_not_null CHECK ((country_id IS NOT NULL)),
    CONSTRAINT tour_date_check CHECK ((date >= now())),
    CONSTRAINT tour_date_not_null CHECK ((date IS NOT NULL)),
    CONSTRAINT tour_duration_check CHECK ((age(duration, date) > '1 day'::interval)),
    CONSTRAINT tour_duration_not_null CHECK ((duration IS NOT NULL)),
    CONSTRAINT tour_hotel_not_null CHECK ((hotel_id IS NOT NULL))
);


ALTER TABLE public.tours OWNER TO postgres;

--
-- TOC entry 224 (class 1259 OID 34481)
-- Name: tours_country_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.tours_country_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.tours_country_id_seq OWNER TO postgres;

--
-- TOC entry 4865 (class 0 OID 0)
-- Dependencies: 224
-- Name: tours_country_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.tours_country_id_seq OWNED BY public.tours.country_id;


--
-- TOC entry 223 (class 1259 OID 34479)
-- Name: tours_hotel_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.tours_hotel_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.tours_hotel_id_seq OWNER TO postgres;

--
-- TOC entry 4866 (class 0 OID 0)
-- Dependencies: 223
-- Name: tours_hotel_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.tours_hotel_id_seq OWNED BY public.tours.hotel_id;


--
-- TOC entry 222 (class 1259 OID 34477)
-- Name: tours_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.tours_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.tours_id_seq OWNER TO postgres;

--
-- TOC entry 4867 (class 0 OID 0)
-- Dependencies: 222
-- Name: tours_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.tours_id_seq OWNED BY public.tours.tour_id;


--
-- TOC entry 207 (class 1259 OID 34114)
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
    user_id integer NOT NULL,
    login character varying(30),
    password character varying(30),
    CONSTRAINT user_login_check CHECK ((length((login)::text) > 4)),
    CONSTRAINT user_login_not_null CHECK ((login IS NOT NULL)),
    CONSTRAINT user_password_check CHECK ((length((password)::text) > 4)),
    CONSTRAINT user_password_not_null CHECK ((password IS NOT NULL))
);


ALTER TABLE public.users OWNER TO postgres;

--
-- TOC entry 206 (class 1259 OID 34112)
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.users_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.users_id_seq OWNER TO postgres;

--
-- TOC entry 4868 (class 0 OID 0)
-- Dependencies: 206
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.users_id_seq OWNED BY public.users.user_id;


--
-- TOC entry 217 (class 1259 OID 34344)
-- Name: users_tours; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users_tours (
    user_id integer NOT NULL,
    tour_id integer NOT NULL
);


ALTER TABLE public.users_tours OWNER TO postgres;

--
-- TOC entry 216 (class 1259 OID 34342)
-- Name: users_tours_tour_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.users_tours_tour_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.users_tours_tour_id_seq OWNER TO postgres;

--
-- TOC entry 4869 (class 0 OID 0)
-- Dependencies: 216
-- Name: users_tours_tour_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.users_tours_tour_id_seq OWNED BY public.users_tours.tour_id;


--
-- TOC entry 215 (class 1259 OID 34340)
-- Name: users_tours_user_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.users_tours_user_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.users_tours_user_id_seq OWNER TO postgres;

--
-- TOC entry 4870 (class 0 OID 0)
-- Dependencies: 215
-- Name: users_tours_user_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.users_tours_user_id_seq OWNED BY public.users_tours.user_id;


--
-- TOC entry 4585 (class 2604 OID 34131)
-- Name: countries country_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.countries ALTER COLUMN country_id SET DEFAULT nextval('public.countries_id_seq'::regclass);


--
-- TOC entry 4589 (class 2604 OID 34326)
-- Name: hotels hotel_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.hotels ALTER COLUMN hotel_id SET DEFAULT nextval('public.hotels_id_seq'::regclass);


--
-- TOC entry 4590 (class 2604 OID 34327)
-- Name: hotels stars; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.hotels ALTER COLUMN stars SET DEFAULT nextval('public.hotels_stars_seq'::regclass);


--
-- TOC entry 4599 (class 2604 OID 34467)
-- Name: reviews review_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.reviews ALTER COLUMN review_id SET DEFAULT nextval('public.reviews_id_seq'::regclass);


--
-- TOC entry 4600 (class 2604 OID 34468)
-- Name: reviews user_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.reviews ALTER COLUMN user_id SET DEFAULT nextval('public.reviews_user_id_seq'::regclass);


--
-- TOC entry 4601 (class 2604 OID 34469)
-- Name: reviews tour_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.reviews ALTER COLUMN tour_id SET DEFAULT nextval('public.reviews_tour_id_seq'::regclass);


--
-- TOC entry 4588 (class 2604 OID 34316)
-- Name: sets_of_features hotel_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sets_of_features ALTER COLUMN hotel_id SET DEFAULT nextval('public.sets_of_features_id_of_hotel_seq'::regclass);


--
-- TOC entry 4607 (class 2604 OID 34486)
-- Name: tours tour_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tours ALTER COLUMN tour_id SET DEFAULT nextval('public.tours_id_seq'::regclass);


--
-- TOC entry 4608 (class 2604 OID 34487)
-- Name: tours hotel_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tours ALTER COLUMN hotel_id SET DEFAULT nextval('public.tours_hotel_id_seq'::regclass);


--
-- TOC entry 4609 (class 2604 OID 34488)
-- Name: tours country_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tours ALTER COLUMN country_id SET DEFAULT nextval('public.tours_country_id_seq'::regclass);


--
-- TOC entry 4580 (class 2604 OID 34117)
-- Name: users user_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users ALTER COLUMN user_id SET DEFAULT nextval('public.users_id_seq'::regclass);


--
-- TOC entry 4597 (class 2604 OID 34347)
-- Name: users_tours user_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users_tours ALTER COLUMN user_id SET DEFAULT nextval('public.users_tours_user_id_seq'::regclass);


--
-- TOC entry 4598 (class 2604 OID 34348)
-- Name: users_tours tour_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users_tours ALTER COLUMN tour_id SET DEFAULT nextval('public.users_tours_tour_id_seq'::regclass);


--
-- TOC entry 4693 (class 2606 OID 34135)
-- Name: countries country_id; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.countries
    ADD CONSTRAINT country_id PRIMARY KEY (country_id);


--
-- TOC entry 4695 (class 2606 OID 34137)
-- Name: countries country_name_unique; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.countries
    ADD CONSTRAINT country_name_unique UNIQUE (name);


--
-- TOC entry 4699 (class 2606 OID 34337)
-- Name: hotels hotel_id; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.hotels
    ADD CONSTRAINT hotel_id PRIMARY KEY (hotel_id);


--
-- TOC entry 4701 (class 2606 OID 34339)
-- Name: hotels hotel_name_unique; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.hotels
    ADD CONSTRAINT hotel_name_unique UNIQUE (name);


--
-- TOC entry 4705 (class 2606 OID 34476)
-- Name: reviews review_id; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.reviews
    ADD CONSTRAINT review_id PRIMARY KEY (review_id);


--
-- TOC entry 4697 (class 2606 OID 34318)
-- Name: sets_of_features sets_of_features_primary_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sets_of_features
    ADD CONSTRAINT sets_of_features_primary_key PRIMARY KEY (hotel_id, feature);


--
-- TOC entry 4707 (class 2606 OID 34501)
-- Name: tours tour_id; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tours
    ADD CONSTRAINT tour_id PRIMARY KEY (tour_id);


--
-- TOC entry 4689 (class 2606 OID 34123)
-- Name: users user_id; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT user_id PRIMARY KEY (user_id);


--
-- TOC entry 4691 (class 2606 OID 34125)
-- Name: users user_login_unique; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT user_login_unique UNIQUE (login);


--
-- TOC entry 4703 (class 2606 OID 34350)
-- Name: users_tours users_tours_primary_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users_tours
    ADD CONSTRAINT users_tours_primary_key PRIMARY KEY (user_id, tour_id);


--
-- TOC entry 4711 (class 2606 OID 39175)
-- Name: reviews reviews_tour_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.reviews
    ADD CONSTRAINT reviews_tour_id_fkey FOREIGN KEY (tour_id) REFERENCES public.tours(tour_id);


--
-- TOC entry 4710 (class 2606 OID 39180)
-- Name: reviews reviews_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.reviews
    ADD CONSTRAINT reviews_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(user_id);


--
-- TOC entry 4713 (class 2606 OID 39190)
-- Name: tours tours_country_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tours
    ADD CONSTRAINT tours_country_id_fkey FOREIGN KEY (country_id) REFERENCES public.countries(country_id);


--
-- TOC entry 4712 (class 2606 OID 39185)
-- Name: tours tours_hotel_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tours
    ADD CONSTRAINT tours_hotel_id_fkey FOREIGN KEY (hotel_id) REFERENCES public.hotels(hotel_id);


--
-- TOC entry 4708 (class 2606 OID 39170)
-- Name: users_tours users_tours_tour_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users_tours
    ADD CONSTRAINT users_tours_tour_id_fkey FOREIGN KEY (tour_id) REFERENCES public.tours(tour_id);


--
-- TOC entry 4709 (class 2606 OID 39165)
-- Name: users_tours users_tours_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users_tours
    ADD CONSTRAINT users_tours_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(user_id);