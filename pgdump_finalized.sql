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
    player_username character varying(100) NOT NULL
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
0	216	5
\.


--
-- Data for Name: Games; Type: TABLE DATA; Schema: game; Owner: postgres
--

COPY game."Games" ("game_ID", game_round, "game_pl_ID", "game_qu_ID", "game_asw_ID", game_points) FROM stdin;
754	178	412	0	2	1
755	178	413	1	7	1
756	178	412	2	11	0
757	178	413	3	15	1
758	178	412	4	19	1
759	178	413	5	23	1
760	179	413	6	26	1
761	179	412	7	31	0
762	179	413	8	36	0
763	179	412	9	39	0
764	179	413	10	43	1
765	179	412	11	46	1
766	180	413	12	50	1
767	180	412	13	55	1
768	180	413	14	60	1
769	181	413	1	7	1
770	181	412	2	10	0
771	181	413	3	15	1
772	181	412	4	19	1
773	181	413	5	23	1
774	181	412	6	27	0
775	182	414	0	3	0
776	182	415	1	7	1
777	182	414	2	10	0
778	182	415	3	15	1
779	182	414	4	20	0
780	182	415	5	23	1
781	183	415	0	3	0
782	183	414	1	7	1
783	183	415	2	11	0
784	183	414	3	15	1
785	183	415	4	19	1
786	183	414	5	23	1
787	184	415	0	2	1
788	184	414	1	7	1
789	184	415	2	11	0
790	184	414	3	15	1
791	184	415	4	19	1
792	184	414	5	23	1
793	185	415	0	2	1
794	185	414	1	7	1
795	185	415	2	11	0
796	185	414	3	15	1
797	185	415	4	19	1
798	185	414	5	23	1
799	186	415	0	2	1
800	186	414	1	7	1
801	186	415	2	11	0
802	186	414	3	15	1
803	186	415	4	20	0
804	186	414	5	23	1
805	187	415	0	3	0
806	187	414	1	7	1
807	187	415	2	11	0
808	187	414	3	15	1
809	187	415	4	19	1
810	187	414	5	23	1
811	188	414	0	3	0
812	188	415	1	7	1
813	188	414	2	11	0
814	188	415	3	15	1
815	188	414	4	20	0
816	188	415	5	23	1
817	189	415	0	3	0
818	189	414	1	7	1
819	189	415	2	12	1
820	189	414	3	14	0
821	189	415	4	19	1
822	189	414	5	24	0
823	190	415	0	3	0
824	190	414	1	8	0
825	190	415	2	10	0
826	190	414	3	15	1
827	190	415	4	20	0
828	190	414	5	24	0
829	191	415	0	3	0
830	191	414	1	7	1
831	191	415	2	11	0
832	191	414	3	16	0
833	191	415	4	19	1
834	191	414	5	23	1
835	192	415	0	3	0
836	192	414	1	7	1
837	192	415	2	11	0
838	192	414	3	15	1
839	192	415	4	19	1
840	192	414	5	23	1
841	193	415	0	3	0
842	193	414	1	9	0
843	193	415	2	11	0
844	193	414	3	15	1
845	194	415	0	3	0
846	194	414	1	9	0
847	194	415	2	11	0
848	194	414	3	14	0
849	194	415	4	19	1
850	194	414	5	23	1
851	195	415	0	2	1
852	195	414	1	7	1
853	195	415	2	12	1
854	195	414	3	15	1
855	195	415	4	19	1
856	195	414	5	24	0
857	196	415	0	3	0
858	196	414	1	8	0
859	196	415	2	11	0
860	196	414	3	14	0
861	196	415	4	20	0
862	196	414	5	24	0
863	197	415	0	3	0
864	197	414	1	7	1
865	197	415	2	13	0
866	197	414	3	15	1
867	197	415	4	19	1
868	197	414	5	22	0
869	198	415	0	4	0
870	198	414	1	7	1
871	198	415	2	10	0
872	198	414	3	15	1
873	198	415	4	19	1
874	198	414	5	23	1
875	199	415	0	3	0
876	199	414	1	7	1
877	199	415	2	12	1
878	199	414	3	15	1
879	199	415	4	19	1
880	199	414	5	23	1
881	200	415	0	3	0
882	200	414	1	7	1
883	200	415	2	11	0
884	201	414	0	3	0
885	201	415	1	7	1
886	201	414	2	13	0
887	201	415	3	16	0
888	201	414	4	19	1
889	201	415	5	24	0
890	202	417	0	3	0
891	202	416	1	7	1
892	202	417	2	13	0
893	202	416	3	14	0
894	202	417	4	19	1
895	202	416	5	23	1
896	203	418	0	3	0
897	203	419	1	7	1
898	203	418	2	10	0
899	203	419	3	15	1
900	203	418	4	19	1
901	203	419	5	25	0
902	205	423	0	3	0
903	205	422	1	7	1
904	205	423	2	13	0
905	205	422	3	15	1
906	205	423	4	19	1
907	205	422	5	24	0
908	206	423	6	26	1
909	206	422	7	31	0
910	206	423	8	36	0
911	206	422	9	39	0
912	206	423	10	44	0
913	206	422	11	46	1
914	207	423	12	51	0
915	207	422	13	55	1
916	207	423	14	60	1
917	209	413	0	3	0
918	209	412	1	7	1
919	209	413	2	10	0
920	209	412	3	15	1
921	210	413	0	3	0
922	210	412	1	7	1
923	210	413	2	10	0
924	210	412	3	15	1
925	210	413	4	19	1
926	210	412	5	23	1
927	211	413	6	27	0
928	211	412	7	31	0
929	211	413	8	34	1
930	211	412	9	39	0
931	211	413	10	43	1
932	211	412	11	46	1
933	212	413	12	51	0
934	212	412	13	55	1
935	212	413	14	60	1
936	214	413	0	2	1
937	214	412	1	7	1
938	214	413	2	11	0
939	214	412	3	15	1
940	214	413	4	19	1
941	214	412	5	24	0
942	215	412	0	2	1
943	215	413	1	7	1
944	215	412	2	10	0
945	215	413	3	15	1
946	215	412	4	20	0
947	215	413	5	23	1
948	216	412	0	3	0
949	216	413	1	8	0
950	216	412	2	10	0
951	216	413	3	15	1
952	216	412	4	19	1
953	216	413	5	23	1
\.


--
-- Data for Name: Player; Type: TABLE DATA; Schema: game; Owner: postgres
--

COPY game."Player" ("player_ID", player_username) FROM stdin;
410	lisa
411	lena
412	ernie
413	bert
414	Mickey_Mouse
415	Donald_Duck
416	Madonna
417	Cher
418	Elon Musk
419	Jeff Bezos
420	Tom
421	Jerry
422	Pünktchen
423	Anton
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

SELECT pg_catalog.setval('game."Games_game_ID_seq"', 953, true);


--
-- Name: Player_player_ID_seq; Type: SEQUENCE SET; Schema: game; Owner: postgres
--

SELECT pg_catalog.setval('game."Player_player_ID_seq"', 423, true);


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
-- Name: Games Games_game_pl_ID_fkey; Type: FK CONSTRAINT; Schema: game; Owner: postgres
--

ALTER TABLE ONLY game."Games"
    ADD CONSTRAINT "Games_game_pl_ID_fkey" FOREIGN KEY ("game_pl_ID") REFERENCES game."Player"("player_ID") NOT VALID;


--
-- Name: Games Games_game_qu_ID_fkey; Type: FK CONSTRAINT; Schema: game; Owner: postgres
--

ALTER TABLE ONLY game."Games"
    ADD CONSTRAINT "Games_game_qu_ID_fkey" FOREIGN KEY ("game_qu_ID") REFERENCES game."Questions"("qu_ID") NOT VALID;


--
-- PostgreSQL database dump complete
--

