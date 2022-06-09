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
-- Name: secrating; Type: DOMAIN; Schema: public; Owner: straigchri
--

CREATE DOMAIN public.secrating AS character(10) DEFAULT 'weak'::bpchar
	CONSTRAINT allowed_values CHECK (((VALUE = 'excellent'::bpchar) OR (VALUE = 'very good'::bpchar) OR (VALUE = 'good'::bpchar) OR (VALUE = 'weak'::bpchar)));


ALTER DOMAIN public.secrating OWNER TO straigchri;

--
-- Name: skillgrade; Type: DOMAIN; Schema: public; Owner: straigchri
--

CREATE DOMAIN public.skillgrade AS character(3) DEFAULT 'C'::bpchar
	CONSTRAINT allowed_values CHECK (((VALUE = 'A+'::bpchar) OR (VALUE = 'A'::bpchar) OR (VALUE = 'B+'::bpchar) OR (VALUE = 'B'::bpchar) OR (VALUE = 'C+'::bpchar) OR (VALUE = 'C'::bpchar) OR (VALUE = 'D+'::bpchar) OR (VALUE = 'D'::bpchar)));


ALTER DOMAIN public.skillgrade OWNER TO straigchri;

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
-- Name: banks; Type: TABLE; Schema: public; Owner: straigchri
--

CREATE TABLE public.banks (
    bankname text NOT NULL,
    city text NOT NULL,
    noaccounts integer,
    security public.secrating,
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
-- Name: hasskills; Type: TABLE; Schema: public; Owner: straigchri
--

CREATE TABLE public.hasskills (
    robberid integer NOT NULL,
    skillid integer NOT NULL,
    preference integer,
    grade public.skillgrade,
    CONSTRAINT hasskills_preference_check CHECK ((preference > 0))
);


ALTER TABLE public.hasskills OWNER TO straigchri;

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
1	Bad Bank	Chicago	2017-02-02	3010
1	NXP Bank	Chicago	2019-01-08	6406
1	Loanshark Bank	Evanston	2019-02-28	4997
1	Loanshark Bank	Chicago	2019-03-30	4201
1	Inter-Gang Bank	Evanston	2016-02-16	12103
1	Inter-Gang Bank	Evanston	2018-02-14	8769
2	NXP Bank	Chicago	2019-01-08	2300
3	Penny Pinchers	Evanston	2016-08-30	16500
3	Loanshark Bank	Evanston	2019-02-28	4997
3	Loanshark Bank	Chicago	2017-11-09	8200
3	Loanshark Bank	Chicago	2019-03-30	4201
3	Inter-Gang Bank	Evanston	2018-02-14	8769
4	Penny Pinchers	Evanston	2016-08-30	16500
4	NXP Bank	Chicago	2019-01-08	6408.32
4	Loanshark Bank	Chicago	2019-03-30	4201
4	Inter-Gang Bank	Evanston	2018-02-14	8769
4	Gun Chase Bank	Evanston	2016-04-30	3291.3
5	Inter-Gang Bank	Evanston	2017-03-13	60000
5	Loanshark Bank	Evanston	2016-04-20	10000
7	Penny Pinchers	Chicago	2016-08-30	450
7	Loanshark Bank	Evanston	2017-04-20	2749
7	Inter-Gang Bank	Evanston	2018-02-14	8769
7	Gun Chase Bank	Evanston	2016-04-30	3282
8	Penny Pinchers	Evanston	2016-08-30	16500
8	Penny Pinchers	Chicago	2016-08-30	450
8	Loanshark Bank	Evanston	2017-04-20	2747
8	Inter-Gang Bank	Evanston	2016-02-16	12103
10	Penny Pinchers	Evanston	2016-08-30	16500
10	Loanshark Bank	Chicago	2017-11-09	8200
10	Inter-Gang Bank	Evanston	2016-02-16	12103
10	Gun Chase Bank	Evanston	2016-04-30	3282
11	Penny Pinchers	Evanston	2017-10-30	3000
12	PickPocket Bank	Evanston	2016-03-30	31.99
13	Dollar Grabbers	Evanston	2017-11-08	2000
14	Dollar Grabbers	Evanston	2017-06-28	1790
15	Inter-Gang Bank	Evanston	2017-03-13	30000
15	PickPocket Bank	Chicago	2018-02-28	119
15	Penny Pinchers	Evanston	2017-10-30	3000.5
15	Penny Pinchers	Evanston	2019-05-30	3250.1
15	Loanshark Bank	Chicago	2019-03-30	4201.01
15	Inter-Gang Bank	Evanston	2016-02-16	12103
15	Inter-Gang Bank	Evanston	2018-02-14	8774
16	Gun Chase Bank	Evanston	2016-04-30	5000
16	Penny Pinchers	Evanston	2016-08-30	16500.8
16	NXP Bank	Chicago	2019-01-08	6406
16	Loanshark Bank	Evanston	2016-04-20	2747
16	Loanshark Bank	Chicago	2017-11-09	8200
16	Inter-Gang Bank	Evanston	2016-02-16	12103
16	Inter-Gang Bank	Evanston	2018-02-14	8769
17	Loanshark Bank	Evanston	2016-04-20	12880
17	PickPocket Bank	Chicago	2018-02-28	120
17	Penny Pinchers	Evanston	2016-08-30	16500
17	Penny Pinchers	Evanston	2019-05-30	3250.1
17	Loanshark Bank	Evanston	2017-04-20	2747
17	Loanshark Bank	Evanston	2019-02-28	4999
17	Inter-Gang Bank	Evanston	2016-02-16	12105
18	Dollar Grabbers	Evanston	2017-06-28	1790
18	Bad Bank	Chicago	2017-02-02	3010
18	Dollar Grabbers	Evanston	2017-11-08	2000
20	PickPocket Bank	Evanston	2018-01-30	42.99
20	NXP Bank	Chicago	2019-01-08	6406
20	Loanshark Bank	Chicago	2017-11-09	8200
21	Penny Pinchers	Evanston	2019-05-30	3250.1
21	Loanshark Bank	Evanston	2019-02-28	4997
21	Loanshark Bank	Chicago	2017-11-09	8200
22	Inter-Gang Bank	Evanston	2017-03-13	2620
22	PickPocket Bank	Chicago	2015-09-21	679
22	Penny Pinchers	Evanston	2019-05-30	3250.1
23	PickPocket Bank	Chicago	2015-09-21	679
23	NXP Bank	Chicago	2019-01-08	6406
24	PickPocket Bank	Evanston	2018-01-30	500
24	PickPocket Bank	Evanston	2016-03-30	2000
24	PickPocket Bank	Chicago	2015-09-21	681
24	Penny Pinchers	Evanston	2017-10-30	3000
24	Loanshark Bank	Chicago	2019-03-30	4201
24	Gun Chase Bank	Evanston	2016-04-30	3282
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
1	Bad Bank	Chicago
1	Inter-Gang Bank	Evanston
1	NXP Bank	Chicago
2	Loanshark Bank	Chicago
2	Loanshark Bank	Deerfield
3	NXP Bank	Chicago
3	Bankrupt Bank	Evanston
4	Loanshark Bank	Evanston
5	Inter-Gang Bank	Evanston
5	Loanshark Bank	Evanston
7	Inter-Gang Bank	Chicago
8	Penny Pinchers	Evanston
9	PickPocket Bank	Chicago
9	PickPocket Bank	Evanston
9	Bad Bank	Chicago
9	Dollar Grabbers	Chicago
11	Penny Pinchers	Evanston
12	Dollar Grabbers	Evanston
12	Gun Chase Bank	Evanston
13	Gun Chase Bank	Burbank
14	PickPocket Bank	Evanston
15	PickPocket Bank	Deerfield
17	PickPocket Bank	Chicago
18	Bad Bank	Chicago
18	Gun Chase Bank	Evanston
19	Gun Chase Bank	Burbank
20	PickPocket Bank	Evanston
21	PickPocket Bank	Evanston
22	PickPocket Bank	Chicago
23	Hidden Treasure	Chicago
24	Hidden Treasure	Chicago
\.


--
-- Data for Name: hasskills; Type: TABLE DATA; Schema: public; Owner: straigchri
--

COPY public.hasskills (robberid, skillid, preference, grade) FROM stdin;
1	3	1	A+ 
1	8	3	A+ 
1	7	2	C+ 
4	2	1	A  
10	8	1	B  
15	3	1	A+ 
2	1	1	A  
17	9	1	A+ 
17	2	2	C+ 
9	5	1	B  
8	6	1	C+ 
8	3	3	C  
8	11	2	C+ 
7	9	2	C+ 
7	6	1	A+ 
22	8	1	A+ 
22	6	2	C  
14	12	1	B  
16	3	1	A  
23	9	1	A  
23	2	2	C  
20	9	1	C  
3	6	1	B+ 
3	9	2	B+ 
11	7	1	A+ 
13	12	1	B+ 
19	12	1	C  
5	9	2	C  
5	3	1	A+ 
12	7	1	A  
24	1	1	B  
24	7	2	C+ 
24	6	3	B  
6	10	1	B+ 
18	11	1	B+ 
18	4	2	A  
18	10	3	A+ 
21	5	1	C  
\.


--
-- Data for Name: plans; Type: TABLE DATA; Schema: public; Owner: straigchri
--

COPY public.plans (bankname, city, planneddate, norobbers) FROM stdin;
NXP Bank	Chicago	2019-10-30	5
Loanshark Bank	Deerfield	2019-11-15	4
Inter-Gang Bank	Evanston	2019-12-31	4
Dollar Grabbers	Chicago	2019-12-10	3
Gun Chase Bank	Evanston	2019-10-30	6
PickPocket Bank	Deerfield	2019-12-15	6
PickPocket Bank	Chicago	2020-03-10	2
Hidden Treasure	Chicago	2020-01-11	5
NXP Bank	Chicago	2019-10-10	5
Bad Bank	Chicago	2020-02-02	2
PickPocket Bank	Deerfield	2019-11-30	6
\.


--
-- Data for Name: robberies; Type: TABLE DATA; Schema: public; Owner: straigchri
--

COPY public.robberies (bankname, city, date, amount) FROM stdin;
NXP Bank	Chicago	2019-01-08	34302.3
Loanshark Bank	Evanston	2019-02-28	19990
Loanshark Bank	Chicago	2019-03-30	21005
Inter-Gang Bank	Evanston	2018-02-14	52619
Penny Pinchers	Chicago	2016-08-30	900
Penny Pinchers	Evanston	2016-08-30	99000.8
Gun Chase Bank	Evanston	2016-04-30	18131.3
PickPocket Bank	Evanston	2016-03-30	2031.99
PickPocket Bank	Chicago	2018-02-28	239
Loanshark Bank	Evanston	2017-04-20	10990
Inter-Gang Bank	Evanston	2016-02-16	72620
Penny Pinchers	Evanston	2017-10-30	9000.5
PickPocket Bank	Evanston	2018-01-30	542.99
Loanshark Bank	Chicago	2017-11-09	41000
Penny Pinchers	Evanston	2019-05-30	13000.4
PickPocket Bank	Chicago	2015-09-21	2039
Loanshark Bank	Evanston	2016-04-20	20880
Inter-Gang Bank	Evanston	2017-03-13	92620
Dollar Grabbers	Evanston	2017-11-08	4380
Dollar Grabbers	Evanston	2017-06-28	3580
Bad Bank	Chicago	2017-02-02	6020
\.


--
-- Data for Name: robbers; Type: TABLE DATA; Schema: public; Owner: straigchri
--

COPY public.robbers (robberid, nickname, age, noyears) FROM stdin;
1	Al Capone	31	2
2	Bugsy Malone	42	15
3	Lucky Luchiano	42	15
4	Anastazia	48	15
5	Mimmy The Mau Mau	18	0
6	Tony Genovese	28	16
7	Dutch Schulz	64	31
8	Clyde	20	0
9	Calamity Jane	44	3
10	Bonnie	19	0
11	Meyer Lansky	34	6
12	Moe Dalitz	41	3
13	Mickey Cohen	24	3
14	Kid Cann	14	0
15	Boo Boo Hoff	54	13
16	King Solomon	74	43
17	Bugsy Siegel	48	13
18	Vito Genovese	66	0
19	Mike Genovese	35	0
20	Longy Zwillman	35	6
21	Waxey Gordon	15	0
22	Greasy Guzik	25	1
23	Lepke Buchalter	25	1
24	Sonny Genovese	39	0
\.


--
-- Data for Name: skills; Type: TABLE DATA; Schema: public; Owner: straigchri
--

COPY public.skills (skillid, description) FROM stdin;
1	Explosives
2	Guarding
3	Planning
4	Cooking
5	Gun-Shooting
6	Lock-Picking
7	Safe-Cracking
8	Preaching
9	Driving
10	Eating
11	Scouting
12	Money Counting
\.


--
-- Name: robbers_robberid_seq; Type: SEQUENCE SET; Schema: public; Owner: straigchri
--

SELECT pg_catalog.setval('public.robbers_robberid_seq', 24, true);


--
-- Name: skills_skillid_seq; Type: SEQUENCE SET; Schema: public; Owner: straigchri
--

SELECT pg_catalog.setval('public.skills_skillid_seq', 12, true);


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
-- Name: hasskills hasskills_pkey; Type: CONSTRAINT; Schema: public; Owner: straigchri
--

ALTER TABLE ONLY public.hasskills
    ADD CONSTRAINT hasskills_pkey PRIMARY KEY (robberid, skillid);


--
-- Name: hasskills hasskills_robberid_preference_key; Type: CONSTRAINT; Schema: public; Owner: straigchri
--

ALTER TABLE ONLY public.hasskills
    ADD CONSTRAINT hasskills_robberid_preference_key UNIQUE (robberid, preference);


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
-- Name: skills skills_description_key; Type: CONSTRAINT; Schema: public; Owner: straigchri
--

ALTER TABLE ONLY public.skills
    ADD CONSTRAINT skills_description_key UNIQUE (description);


--
-- Name: skills skills_pkey; Type: CONSTRAINT; Schema: public; Owner: straigchri
--

ALTER TABLE ONLY public.skills
    ADD CONSTRAINT skills_pkey PRIMARY KEY (skillid);


--
-- Name: accomplices accomplices_bankname_fkey; Type: FK CONSTRAINT; Schema: public; Owner: straigchri
--

ALTER TABLE ONLY public.accomplices
    ADD CONSTRAINT accomplices_bankname_fkey FOREIGN KEY (bankname, city, date) REFERENCES public.robberies(bankname, city, date) ON DELETE RESTRICT;


--
-- Name: accomplices accomplices_robberid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: straigchri
--

ALTER TABLE ONLY public.accomplices
    ADD CONSTRAINT accomplices_robberid_fkey FOREIGN KEY (robberid) REFERENCES public.robbers(robberid) ON DELETE RESTRICT;


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
-- Name: hasskills hasskills_robberid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: straigchri
--

ALTER TABLE ONLY public.hasskills
    ADD CONSTRAINT hasskills_robberid_fkey FOREIGN KEY (robberid) REFERENCES public.robbers(robberid) ON DELETE CASCADE;


--
-- Name: hasskills hasskills_skillid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: straigchri
--

ALTER TABLE ONLY public.hasskills
    ADD CONSTRAINT hasskills_skillid_fkey FOREIGN KEY (skillid) REFERENCES public.skills(skillid) ON DELETE CASCADE;


--
-- Name: plans plans_bankname_fkey; Type: FK CONSTRAINT; Schema: public; Owner: straigchri
--

ALTER TABLE ONLY public.plans
    ADD CONSTRAINT plans_bankname_fkey FOREIGN KEY (bankname, city) REFERENCES public.banks(bankname, city) ON DELETE CASCADE;


--
-- Name: robberies robberies_bankname_fkey; Type: FK CONSTRAINT; Schema: public; Owner: straigchri
--

ALTER TABLE ONLY public.robberies
    ADD CONSTRAINT robberies_bankname_fkey FOREIGN KEY (bankname, city) REFERENCES public.banks(bankname, city) ON DELETE RESTRICT;


--
-- PostgreSQL database dump complete
--

