--
-- PostgreSQL database dump
--

-- Dumped from database version 15.2
-- Dumped by pg_dump version 15.2

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: game; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA game;


ALTER SCHEMA game OWNER TO postgres;

--
-- Name: SCHEMA game; Type: COMMENT; Schema: -; Owner: postgres
--

COMMENT ON SCHEMA game IS 'Schema for Patterns and Frameworks course, quizz game.';


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: Answers; Type: TABLE; Schema: game; Owner: postgres
--

CREATE TABLE game."Answers" (
    "asw_ID" bigint NOT NULL,
    "asw_qu_ID" bigint NOT NULL,
    asw_text character varying(2000) NOT NULL,
    asw_is_correct boolean DEFAULT false NOT NULL
);


ALTER TABLE game."Answers" OWNER TO postgres;

--
-- Name: TABLE "Answers"; Type: COMMENT; Schema: game; Owner: postgres
--

COMMENT ON TABLE game."Answers" IS 'Table for anwers';


--
-- Name: COLUMN "Answers"."asw_ID"; Type: COMMENT; Schema: game; Owner: postgres
--

COMMENT ON COLUMN game."Answers"."asw_ID" IS 'ID of answer';


--
-- Name: COLUMN "Answers"."asw_qu_ID"; Type: COMMENT; Schema: game; Owner: postgres
--

COMMENT ON COLUMN game."Answers"."asw_qu_ID" IS 'Foreign Key => qu_ID';


--
-- Name: COLUMN "Answers".asw_text; Type: COMMENT; Schema: game; Owner: postgres
--

COMMENT ON COLUMN game."Answers".asw_text IS 'Text of answer';


--
-- Name: COLUMN "Answers".asw_is_correct; Type: COMMENT; Schema: game; Owner: postgres
--

COMMENT ON COLUMN game."Answers".asw_is_correct IS 'Indicate whether answer option is correct.';


--
-- Name: Answers_asw_ID_seq; Type: SEQUENCE; Schema: game; Owner: postgres
--

ALTER TABLE game."Answers" ALTER COLUMN "asw_ID" ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME game."Answers_asw_ID_seq"
    START WITH 0
    INCREMENT BY 1
    MINVALUE 0
    MAXVALUE 9999
    CACHE 1
    CYCLE
);


--
-- Name: Game_control; Type: TABLE; Schema: game; Owner: postgres
--

CREATE TABLE game."Game_control" (
    "control_ID" bigint NOT NULL,
    round bigint NOT NULL,
    "last_qu_ID" bigint NOT NULL
);


ALTER TABLE game."Game_control" OWNER TO postgres;

--
-- Name: Game_control_control_ID_seq; Type: SEQUENCE; Schema: game; Owner: postgres
--

ALTER TABLE game."Game_control" ALTER COLUMN "control_ID" ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME game."Game_control_control_ID_seq"
    START WITH 0
    INCREMENT BY 1
    MINVALUE 0
    MAXVALUE 9999
    CACHE 1
    CYCLE
);


--
-- Name: Games; Type: TABLE; Schema: game; Owner: postgres
--

CREATE TABLE game."Games" (
    "game_ID" bigint NOT NULL,
    game_round bigint NOT NULL,
    "game_pl_ID" bigint NOT NULL,
    "game_qu_ID" bigint NOT NULL,
    "game_asw_ID" bigint NOT NULL,
    game_points bigint NOT NULL
);


ALTER TABLE game."Games" OWNER TO postgres;

--
-- Name: TABLE "Games"; Type: COMMENT; Schema: game; Owner: postgres
--

COMMENT ON TABLE game."Games" IS 'Track each game: what questions were asked, answers given, points won, players involved.';


--
-- Name: COLUMN "Games"."game_ID"; Type: COMMENT; Schema: game; Owner: postgres
--

COMMENT ON COLUMN game."Games"."game_ID" IS 'ID of game';


--
-- Name: COLUMN "Games".game_round; Type: COMMENT; Schema: game; Owner: postgres
--

COMMENT ON COLUMN game."Games".game_round IS 'Give the same game the same id';


--
-- Name: COLUMN "Games"."game_pl_ID"; Type: COMMENT; Schema: game; Owner: postgres
--

COMMENT ON COLUMN game."Games"."game_pl_ID" IS 'Foreign key player_ID';


--
-- Name: COLUMN "Games"."game_qu_ID"; Type: COMMENT; Schema: game; Owner: postgres
--

COMMENT ON COLUMN game."Games"."game_qu_ID" IS 'Foreign key qu_ID from question table (=> id of question)';


--
-- Name: COLUMN "Games"."game_asw_ID"; Type: COMMENT; Schema: game; Owner: postgres
--

COMMENT ON COLUMN game."Games"."game_asw_ID" IS 'Foreign key asw_ID from answer table';


--
-- Name: COLUMN "Games".game_points; Type: COMMENT; Schema: game; Owner: postgres
--

COMMENT ON COLUMN game."Games".game_points IS 'Won points per question ';


--
-- Name: Games_game_ID_seq; Type: SEQUENCE; Schema: game; Owner: postgres
--

ALTER TABLE game."Games" ALTER COLUMN "game_ID" ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME game."Games_game_ID_seq"
    START WITH 0
    INCREMENT BY 1
    MINVALUE 0
    MAXVALUE 9999
    CACHE 1
    CYCLE
);


--
-- Name: Player; Type: TABLE; Schema: game; Owner: postgres
--

CREATE TABLE game."Player" (
    "player_ID" bigint NOT NULL,
    player_username character varying(100) NOT NULL,
    player_highscore bigint
);


ALTER TABLE game."Player" OWNER TO postgres;

--
-- Name: TABLE "Player"; Type: COMMENT; Schema: game; Owner: postgres
--

COMMENT ON TABLE game."Player" IS 'Table to track username, highscore and id of player ';


--
-- Name: COLUMN "Player"."player_ID"; Type: COMMENT; Schema: game; Owner: postgres
--

COMMENT ON COLUMN game."Player"."player_ID" IS 'ID of player ';


--
-- Name: COLUMN "Player".player_username; Type: COMMENT; Schema: game; Owner: postgres
--

COMMENT ON COLUMN game."Player".player_username IS 'Username of player';


--
-- Name: COLUMN "Player".player_highscore; Type: COMMENT; Schema: game; Owner: postgres
--

COMMENT ON COLUMN game."Player".player_highscore IS 'Highscore of player';


--
-- Name: Player_player_ID_seq; Type: SEQUENCE; Schema: game; Owner: postgres
--

ALTER TABLE game."Player" ALTER COLUMN "player_ID" ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME game."Player_player_ID_seq"
    START WITH 0
    INCREMENT BY 1
    MINVALUE 0
    MAXVALUE 9999
    CACHE 1
    CYCLE
);


--
-- Name: Questions; Type: TABLE; Schema: game; Owner: postgres
--

CREATE TABLE game."Questions" (
    "qu_ID" bigint NOT NULL,
    qu_text character varying(2000) NOT NULL
);


ALTER TABLE game."Questions" OWNER TO postgres;

--
-- Name: TABLE "Questions"; Type: COMMENT; Schema: game; Owner: postgres
--

COMMENT ON TABLE game."Questions" IS 'Questions ';


--
-- Name: COLUMN "Questions"."qu_ID"; Type: COMMENT; Schema: game; Owner: postgres
--

COMMENT ON COLUMN game."Questions"."qu_ID" IS 'question ID';


--
-- Name: COLUMN "Questions".qu_text; Type: COMMENT; Schema: game; Owner: postgres
--

COMMENT ON COLUMN game."Questions".qu_text IS 'Text of question';


--
-- Name: Questions_qu_ID_seq; Type: SEQUENCE; Schema: game; Owner: postgres
--

ALTER TABLE game."Questions" ALTER COLUMN "qu_ID" ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME game."Questions_qu_ID_seq"
    START WITH 0
    INCREMENT BY 1
    MINVALUE 0
    MAXVALUE 9999
    CACHE 1
    CYCLE
);


--
-- Data for Name: Answers; Type: TABLE DATA; Schema: game; Owner: postgres
--

COPY game."Answers" ("asw_ID", "asw_qu_ID", asw_text, asw_is_correct) FROM stdin;
2	0	Metal	t
3	0	Plastic	f
4	0	Wood	f
5	0	The wrong man	f
6	1	Humanity	f
7	1	Health	t
8	1	Honour	f
9	1	Household	f
10	2	Pocahontas	f
11	2	Sleeping Beauty	f
13	2	Elsa	f
12	2	Cinderella	t
14	3	Republicanism	f
15	3	Communism	t
16	3	Conservatism	f
17	3	Liberalism	f
18	4	Bow-tie, braces and tweed jacket	f
19	4	Wide-brimmed hat and extra long scarf	t
20	4	Pinstripe suit and trainers	f
21	4	Cape, velvet jacket and frilly shirt	f
22	5	Ramadan	f
23	5	Diwali	t
24	5	Lent	f
25	5	Hanukkah	f
26	6	Bahamas	t
27	6	US Virgin Islands	f
28	6	Turks and Caicos Islands	f
29	6	Bermuda	f
30	7	Empire State Building	f
31	7	Royal Albert Hall	f
32	7	Eiffel Tower	f
33	7	Big Ben Clock Tower	t
34	8	Moth	t
35	8	Roach	f
36	8	Fly	f
37	8	Japanese beetle	f
38	9	Albert Einstein	f
39	9	Niels Bohr	f
40	9	Isaac Newton	t
41	9	Enrico Fermi	f
42	10	India	f
43	10	Peru	t
44	10	Canada	f
45	10	Iceland	f
46	11	N	t
47	11	A	f
48	11	U	f
49	11	L	f
50	12	Meat inspector	t
51	12	Mail deliverer	f
52	12	Historian	f
53	12	Weapons mechanic	f
54	13	50 billion	f
55	13	100 billion	t
56	13	1 trillion	f
57	13	5 trillion	f
58	14	365	f
59	14	400	f
60	14	354	t
61	14	376	f
\.


--
-- Data for Name: Game_control; Type: TABLE DATA; Schema: game; Owner: postgres
--

COPY game."Game_control" ("control_ID", round, "last_qu_ID") FROM stdin;
0	174	11
\.


--
-- Data for Name: Games; Type: TABLE DATA; Schema: game; Owner: postgres
--

COPY game."Games" ("game_ID", game_round, "game_pl_ID", "game_qu_ID", "game_asw_ID", game_points) FROM stdin;
\.


--
-- Data for Name: Player; Type: TABLE DATA; Schema: game; Owner: postgres
--

COPY game."Player" ("player_ID", player_username, player_highscore) FROM stdin;
\.


--
-- Data for Name: Questions; Type: TABLE DATA; Schema: game; Owner: postgres
--

COPY game."Questions" ("qu_ID", qu_text) FROM stdin;
0	A magnet would most likely attract which of the following?
1	In the UK, the abbreviation NHS stands for National what Service?
2	Which Disney character famously leaves a glass slipper behind at a royal ball?
3	The hammer and sickle is one of the most recognisable symbols of which political ideology?
4	In Doctor Who, what was the signature look of the fourth Doctor, as portrayed by Tom Baker?
5	Which of these religious observances lasts for the shortest period of time during the calendar year?
6	At the closest point, which island group is only 50 miles south-east of the coast of Florida?
7	Construction of which of these famous landmarks was completed first?
8	Which insect shorted out an early supercomputer and inspired the term "computer bug"?
9	Which of the following men does not have a chemical element named for him?
10	In the children’s book series, where is Paddington Bear originally from?
11	What letter must appear on the beginning of the registration number of all non-military aircraft in the US?
12	The US icon Uncle Sam was based on Samuel Wilson who worked during the War of 1812 as a what?
13	According to the Population Reference Bureau, what is the approximate number of people who have ever lived on earth?
14	How many days make up a non-leap year in the Islamic calendar?
\.


--
-- Name: Answers_asw_ID_seq; Type: SEQUENCE SET; Schema: game; Owner: postgres
--

SELECT pg_catalog.setval('game."Answers_asw_ID_seq"', 61, true);


--
-- Name: Game_control_control_ID_seq; Type: SEQUENCE SET; Schema: game; Owner: postgres
--

SELECT pg_catalog.setval('game."Game_control_control_ID_seq"', 0, true);


--
-- Name: Games_game_ID_seq; Type: SEQUENCE SET; Schema: game; Owner: postgres
--

SELECT pg_catalog.setval('game."Games_game_ID_seq"', 746, true);


--
-- Name: Player_player_ID_seq; Type: SEQUENCE SET; Schema: game; Owner: postgres
--

SELECT pg_catalog.setval('game."Player_player_ID_seq"', 405, true);


--
-- Name: Questions_qu_ID_seq; Type: SEQUENCE SET; Schema: game; Owner: postgres
--

SELECT pg_catalog.setval('game."Questions_qu_ID_seq"', 14, true);


--
-- Name: Answers Answers_pkey; Type: CONSTRAINT; Schema: game; Owner: postgres
--

ALTER TABLE ONLY game."Answers"
    ADD CONSTRAINT "Answers_pkey" PRIMARY KEY ("asw_ID");


--
-- Name: Game_control Game_control_pkey; Type: CONSTRAINT; Schema: game; Owner: postgres
--

ALTER TABLE ONLY game."Game_control"
    ADD CONSTRAINT "Game_control_pkey" PRIMARY KEY ("control_ID");


--
-- Name: Games Games_pkey; Type: CONSTRAINT; Schema: game; Owner: postgres
--

ALTER TABLE ONLY game."Games"
    ADD CONSTRAINT "Games_pkey" PRIMARY KEY ("game_ID");


--
-- Name: Player Player_pkey; Type: CONSTRAINT; Schema: game; Owner: postgres
--

ALTER TABLE ONLY game."Player"
    ADD CONSTRAINT "Player_pkey" PRIMARY KEY ("player_ID");


--
-- Name: Questions Questions_pkey; Type: CONSTRAINT; Schema: game; Owner: postgres
--

ALTER TABLE ONLY game."Questions"
    ADD CONSTRAINT "Questions_pkey" PRIMARY KEY ("qu_ID");


--
-- Name: Answers Answers_asw_qu_ID_fkey; Type: FK CONSTRAINT; Schema: game; Owner: postgres
--

ALTER TABLE ONLY game."Answers"
    ADD CONSTRAINT "Answers_asw_qu_ID_fkey" FOREIGN KEY ("asw_qu_ID") REFERENCES game."Questions"("qu_ID") NOT VALID;


--
-- Name: Games Games_game_asw_ID_fkey; Type: FK CONSTRAINT; Schema: game; Owner: postgres
--

ALTER TABLE ONLY game."Games"
    ADD CONSTRAINT "Games_game_asw_ID_fkey" FOREIGN KEY ("game_asw_ID") REFERENCES game."Answers"("asw_ID") NOT VALID;


--
-- Name: Games Games_game_qu_ID_fkey; Type: FK CONSTRAINT; Schema: game; Owner: postgres
--

ALTER TABLE ONLY game."Games"
    ADD CONSTRAINT "Games_game_qu_ID_fkey" FOREIGN KEY ("game_qu_ID") REFERENCES game."Questions"("qu_ID") NOT VALID;


--
-- PostgreSQL database dump complete
--

