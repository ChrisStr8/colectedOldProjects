--
-- PostgreSQL database dump
--

-- Dumped from database version 10.15
-- Dumped by pg_dump version 10.16

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'SQL_ASCII';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


--
-- Name: plpgsql_call_handler(); Type: FUNCTION; Schema: public; Owner: pgsql
--

CREATE FUNCTION public.plpgsql_call_handler() RETURNS language_handler
    LANGUAGE c
    AS '$libdir/plpgsql', 'plpgsql_call_handler';


ALTER FUNCTION public.plpgsql_call_handler() OWNER TO pgsql;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: accomplices; Type: TABLE; Schema: public; Owner: straigchri
--

CREATE TABLE public.accomplices (
    robberid integer NOT NULL,
    bankname text NOT NULL,
    city text NOT NULL,
    date date NOT NULL,
    share numeric
);


ALTER TABLE public.accomplices OWNER TO straigchri;

--
-- Name: accomplices_robberid_seq; Type: SEQUENCE; Schema: public; Owner: straigchri
--

CREATE SEQUENCE public.accomplices_robberid_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.accomplices_robberid_seq OWNER TO straigchri;

--
-- Name: accomplices_robberid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: straigchri
--

ALTER SEQUENCE public.accomplices_robberid_seq OWNED BY public.accomplices.robberid;


--
-- Name: banks; Type: TABLE; Schema: public; Owner: straigchri
--

CREATE TABLE public.banks (
    bankname text NOT NULL,
    city text NOT NULL,
    noaccounts integer,
    security character(10),
    CONSTRAINT banks_noaccounts_check CHECK ((noaccounts >= 0))
);


ALTER TABLE public.banks OWNER TO straigchri;

--
-- Name: hasaccounts; Type: TABLE; Schema: public; Owner: straigchri
--

CREATE TABLE public.hasaccounts (
    robberid integer NOT NULL,
    bankname text NOT NULL,
    city text NOT NULL
);


ALTER TABLE public.hasaccounts OWNER TO straigchri;

--
-- Name: hasaccounts_robberid_seq; Type: SEQUENCE; Schema: public; Owner: straigchri
--

CREATE SEQUENCE public.hasaccounts_robberid_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.hasaccounts_robberid_seq OWNER TO straigchri;

--
-- Name: hasaccounts_robberid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: straigchri
--

ALTER SEQUENCE public.hasaccounts_robberid_seq OWNED BY public.hasaccounts.robberid;


--
-- Name: hasskils; Type: TABLE; Schema: public; Owner: straigchri
--

CREATE TABLE public.hasskils (
    robberid integer NOT NULL,
    skillid integer NOT NULL,
    preference integer,
    grade character(3),
    CONSTRAINT hasskils_preference_check CHECK ((preference > 0))
);


ALTER TABLE public.hasskills OWNER TO straigchri;

--
-- Name: hasskils_robberid_seq; Type: SEQUENCE; Schema: public; Owner: straigchri
--

CREATE SEQUENCE public.hasskils_robberid_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.hasskils_robberid_seq OWNER TO straigchri;

--
-- Name: hasskils_robberid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: straigchri
--

ALTER SEQUENCE public.hasskils_robberid_seq OWNED BY public.hasskills.robberid;


--
-- Name: hasskils_skillid_seq; Type: SEQUENCE; Schema: public; Owner: straigchri
--

CREATE SEQUENCE public.hasskils_skillid_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.hasskils_skillid_seq OWNER TO straigchri;

--
-- Name: hasskils_skillid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: straigchri
--

ALTER SEQUENCE public.hasskils_skillid_seq OWNED BY public.hasskills.skillid;


--
-- Name: plans; Type: TABLE; Schema: public; Owner: straigchri
--

CREATE TABLE public.plans (
    bankname text NOT NULL,
    city text NOT NULL,
    planneddate date NOT NULL,
    norobbers integer,
    CONSTRAINT plans_norobbers_check CHECK ((norobbers > 0))
);


ALTER TABLE public.plans OWNER TO straigchri;

--
-- Name: robberies; Type: TABLE; Schema: public; Owner: straigchri
--

CREATE TABLE public.robberies (
    bankname text NOT NULL,
    city text NOT NULL,
    date date NOT NULL,
    amount numeric
);


ALTER TABLE public.robberies OWNER TO straigchri;

--
-- Name: robbers; Type: TABLE; Schema: public; Owner: straigchri
--

CREATE TABLE public.robbers (
    robberid integer NOT NULL,
    nickname text,
    age integer,
    noyears integer,
    CONSTRAINT robbers_age_check CHECK ((age > 0)),
    CONSTRAINT robbers_check CHECK ((noyears < age))
);


ALTER TABLE public.robbers OWNER TO straigchri;

--
-- Name: robbers_robberid_seq; Type: SEQUENCE; Schema: public; Owner: straigchri
--

CREATE SEQUENCE public.robbers_robberid_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.robbers_robberid_seq OWNER TO straigchri;

--
-- Name: robbers_robberid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: straigchri
--

ALTER SEQUENCE public.robbers_robberid_seq OWNED BY public.robbers.robberid;


--
-- Name: skills; Type: TABLE; Schema: public; Owner: straigchri
--

CREATE TABLE public.skills (
    skillid integer NOT NULL,
    description text NOT NULL
);


ALTER TABLE public.skills OWNER TO straigchri;

--
-- Name: skills_skillid_seq; Type: SEQUENCE; Schema: public; Owner: straigchri
--

CREATE SEQUENCE public.skills_skillid_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.skills_skillid_seq OWNER TO straigchri;

--
-- Name: skills_skillid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: straigchri
--

ALTER SEQUENCE public.skills_skillid_seq OWNED BY public.skills.skillid;


--
-- Name: accomplices robberid; Type: DEFAULT; Schema: public; Owner: straigchri
--

ALTER TABLE ONLY public.accomplices ALTER COLUMN robberid SET DEFAULT nextval('public.accomplices_robberid_seq'::regclass);


--
-- Name: hasaccounts robberid; Type: DEFAULT; Schema: public; Owner: straigchri
--

ALTER TABLE ONLY public.hasaccounts ALTER COLUMN robberid SET DEFAULT nextval('public.hasaccounts_robberid_seq'::regclass);


--
-- Name: hasskils robberid; Type: DEFAULT; Schema: public; Owner: straigchri
--

ALTER TABLE ONLY public.hasskills ALTER COLUMN robberid SET DEFAULT nextval('public.hasskils_robberid_seq'::regclass);


--
-- Name: hasskils skillid; Type: DEFAULT; Schema: public; Owner: straigchri
--

ALTER TABLE ONLY public.hasskills ALTER COLUMN skillid SET DEFAULT nextval('public.hasskils_skillid_seq'::regclass);


--
-- Name: robbers robberid; Type: DEFAULT; Schema: public; Owner: straigchri
--

ALTER TABLE ONLY public.robbers ALTER COLUMN robberid SET DEFAULT nextval('public.robbers_robberid_seq'::regclass);


--
-- Name: skills skillid; Type: DEFAULT; Schema: public; Owner: straigchri
--

ALTER TABLE ONLY public.skills ALTER COLUMN skillid SET DEFAULT nextval('public.skills_skillid_seq'::regclass);


--
-- Data for Name: accomplices; Type: TABLE DATA; Schema: public; Owner: straigchri
--

COPY public.accomplices (robberid, bankname, city, date, share) FROM stdin;
\.


--
-- Data for Name: banks; Type: TABLE DATA; Schema: public; Owner: straigchri
--

COPY public.banks (bankname, city, noaccounts, security) FROM stdin;
NXP Bank	Chicago	1593311	very good 
Bankrupt Bank	Evanston	444000	weak      
Loanshark Bank	Evanston	7654321	excellent 
Loanshark Bank	Deerfield	3456789	very good 
Loanshark Bank	Chicago	121212	excellent 
Inter-Gang Bank	Chicago	100000	excellent 
Inter-Gang Bank	Evanston	555555	excellent 
NXP Bank	Evanston	656565	excellent 
Penny Pinchers	Chicago	156165	weak      
Dollar Grabbers	Chicago	56005	very good 
Penny Pinchers	Evanston	130013	excellent 
Dollar Grabbers	Evanston	909090	good      
Gun Chase Bank	Evanston	656565	excellent 
Gun Chase Bank	Burbank	1999	weak      
PickPocket Bank	Evanston	2000	very good 
PickPocket Bank	Deerfield	6565	excellent 
PickPocket Bank	Chicago	130013	weak      
Hidden Treasure	Chicago	999999	excellent 
Bad Bank	Chicago	6000	weak      
Outside Bank	Chicago	5000	good      
\.


--
-- Data for Name: hasaccounts; Type: TABLE DATA; Schema: public; Owner: straigchri
--

COPY public.hasaccounts (robberid, bankname, city) FROM stdin;
\.


--
-- Data for Name: hasskils; Type: TABLE DATA; Schema: public; Owner: straigchri
--

COPY public.hasskills (robberid, skillid, preference, grade) FROM stdin;
\.


--
-- Data for Name: plans; Type: TABLE DATA; Schema: public; Owner: straigchri
--

COPY public.plans (bankname, city, planneddate, norobbers) FROM stdin;
\.


--
-- Data for Name: robberies; Type: TABLE DATA; Schema: public; Owner: straigchri
--

COPY public.robberies (bankname, city, date, amount) FROM stdin;
\.


--
-- Data for Name: robbers; Type: TABLE DATA; Schema: public; Owner: straigchri
--

COPY public.robbers (robberid, nickname, age, noyears) FROM stdin;
\.


--
-- Data for Name: skills; Type: TABLE DATA; Schema: public; Owner: straigchri
--

COPY public.skills (skillid, description) FROM stdin;
\.


--
-- Name: accomplices_robberid_seq; Type: SEQUENCE SET; Schema: public; Owner: straigchri
--

SELECT pg_catalog.setval('public.accomplices_robberid_seq', 1, false);


--
-- Name: hasaccounts_robberid_seq; Type: SEQUENCE SET; Schema: public; Owner: straigchri
--

SELECT pg_catalog.setval('public.hasaccounts_robberid_seq', 1, false);


--
-- Name: hasskils_robberid_seq; Type: SEQUENCE SET; Schema: public; Owner: straigchri
--

SELECT pg_catalog.setval('public.hasskils_robberid_seq', 1, false);


--
-- Name: hasskils_skillid_seq; Type: SEQUENCE SET; Schema: public; Owner: straigchri
--

SELECT pg_catalog.setval('public.hasskils_skillid_seq', 1, false);


--
-- Name: robbers_robberid_seq; Type: SEQUENCE SET; Schema: public; Owner: straigchri
--

SELECT pg_catalog.setval('public.robbers_robberid_seq', 1, false);


--
-- Name: skills_skillid_seq; Type: SEQUENCE SET; Schema: public; Owner: straigchri
--

SELECT pg_catalog.setval('public.skills_skillid_seq', 1, false);


--
-- Name: accomplices accomplices_pkey; Type: CONSTRAINT; Schema: public; Owner: straigchri
--

ALTER TABLE ONLY public.accomplices
    ADD CONSTRAINT accomplices_pkey PRIMARY KEY (bankname, city, date, robberid);


--
-- Name: banks banks_pkey; Type: CONSTRAINT; Schema: public; Owner: straigchri
--

ALTER TABLE ONLY public.banks
    ADD CONSTRAINT banks_pkey PRIMARY KEY (bankname, city);


--
-- Name: hasaccounts hasaccounts_pkey; Type: CONSTRAINT; Schema: public; Owner: straigchri
--

ALTER TABLE ONLY public.hasaccounts
    ADD CONSTRAINT hasaccounts_pkey PRIMARY KEY (robberid, bankname, city);


--
-- Name: hasskils hasskils_pkey; Type: CONSTRAINT; Schema: public; Owner: straigchri
--

ALTER TABLE ONLY public.hasskills
    ADD CONSTRAINT hasskils_pkey PRIMARY KEY (robberid, skillid);


--
-- Name: hasskils hasskils_robberid_preference_key; Type: CONSTRAINT; Schema: public; Owner: straigchri
--

ALTER TABLE ONLY public.hasskills
    ADD CONSTRAINT hasskils_robberid_preference_key UNIQUE (robberid, preference);


--
-- Name: plans plans_pkey; Type: CONSTRAINT; Schema: public; Owner: straigchri
--

ALTER TABLE ONLY public.plans
    ADD CONSTRAINT plans_pkey PRIMARY KEY (bankname, city, planneddate);


--
-- Name: robberies robberies_pkey; Type: CONSTRAINT; Schema: public; Owner: straigchri
--

ALTER TABLE ONLY public.robberies
    ADD CONSTRAINT robberies_pkey PRIMARY KEY (bankname, city, date);


--
-- Name: robbers robbers_pkey; Type: CONSTRAINT; Schema: public; Owner: straigchri
--

ALTER TABLE ONLY public.robbers
    ADD CONSTRAINT robbers_pkey PRIMARY KEY (robberid);


--
-- Name: skills skills_pkey; Type: CONSTRAINT; Schema: public; Owner: straigchri
--

ALTER TABLE ONLY public.skills
    ADD CONSTRAINT skills_pkey PRIMARY KEY (skillid);


--
-- Name: accomplices accomplices_bankname_fkey; Type: FK CONSTRAINT; Schema: public; Owner: straigchri
--

ALTER TABLE ONLY public.accomplices
    ADD CONSTRAINT accomplices_bankname_fkey FOREIGN KEY (bankname, city, date) REFERENCES public.robberies(bankname, city, date) ON DELETE CASCADE;


--
-- Name: accomplices accomplices_robberid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: straigchri
--

ALTER TABLE ONLY public.accomplices
    ADD CONSTRAINT accomplices_robberid_fkey FOREIGN KEY (robberid) REFERENCES public.robbers(robberid) ON DELETE CASCADE;


--
-- Name: hasaccounts hasaccounts_bankname_fkey; Type: FK CONSTRAINT; Schema: public; Owner: straigchri
--

ALTER TABLE ONLY public.hasaccounts
    ADD CONSTRAINT hasaccounts_bankname_fkey FOREIGN KEY (bankname, city) REFERENCES public.banks(bankname, city) ON DELETE CASCADE;


--
-- Name: hasaccounts hasaccounts_robberid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: straigchri
--

ALTER TABLE ONLY public.hasaccounts
    ADD CONSTRAINT hasaccounts_robberid_fkey FOREIGN KEY (robberid) REFERENCES public.robbers(robberid) ON DELETE CASCADE;


--
-- Name: hasskils hasskils_robberid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: straigchri
--

ALTER TABLE ONLY public.hasskills
    ADD CONSTRAINT hasskils_robberid_fkey FOREIGN KEY (robberid) REFERENCES public.robbers(robberid) ON DELETE CASCADE;


--
-- Name: hasskils hasskils_skillid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: straigchri
--

ALTER TABLE ONLY public.hasskills
    ADD CONSTRAINT hasskils_skillid_fkey FOREIGN KEY (skillid) REFERENCES public.skills(skillid) ON DELETE CASCADE;


--
-- Name: plans plans_bankname_fkey; Type: FK CONSTRAINT; Schema: public; Owner: straigchri
--

ALTER TABLE ONLY public.plans
    ADD CONSTRAINT plans_bankname_fkey FOREIGN KEY (bankname, city) REFERENCES public.banks(bankname, city) ON DELETE CASCADE;


--
-- Name: robberies robberies_bankname_fkey; Type: FK CONSTRAINT; Schema: public; Owner: straigchri
--

ALTER TABLE ONLY public.robberies
    ADD CONSTRAINT robberies_bankname_fkey FOREIGN KEY (bankname, city) REFERENCES public.banks(bankname, city) ON DELETE CASCADE;


--
-- PostgreSQL database dump complete
--

