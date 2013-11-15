--
-- PostgreSQL database dump
--

-- Dumped from database version 9.0.1
-- Dumped by pg_dump version 9.1.4
-- Started on 2013-08-27 11:27:07

SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = off;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET escape_string_warning = off;

--
-- TOC entry 7 (class 2615 OID 136012)
-- Name: simbirsoft; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA simbirsoft;


ALTER SCHEMA simbirsoft OWNER TO postgres;

--
-- TOC entry 456 (class 2612 OID 11621)
-- Name: plpgsql; Type: PROCEDURAL LANGUAGE; Schema: -; Owner: postgres
--

CREATE OR REPLACE PROCEDURAL LANGUAGE plpgsql;


ALTER PROCEDURAL LANGUAGE plpgsql OWNER TO postgres;

SET search_path = simbirsoft, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 146 (class 1259 OID 136023)
-- Dependencies: 7
-- Name: product; Type: TABLE; Schema: simbirsoft; Owner: postgres; Tablespace: 
--

CREATE TABLE product (
    product_id numeric(5,0) NOT NULL,
    product_name character varying(50),
    recommended_price numeric(10,2)
);


ALTER TABLE simbirsoft.product OWNER TO postgres;

--
-- TOC entry 145 (class 1259 OID 136018)
-- Dependencies: 7
-- Name: shop; Type: TABLE; Schema: simbirsoft; Owner: postgres; Tablespace: 
--

CREATE TABLE shop (
    shop_id numeric(5,0) NOT NULL,
    shop_name character varying(50)
);


ALTER TABLE simbirsoft.shop OWNER TO postgres;

--
-- TOC entry 144 (class 1259 OID 136013)
-- Dependencies: 7
-- Name: shop_product; Type: TABLE; Schema: simbirsoft; Owner: postgres; Tablespace: 
--

CREATE TABLE shop_product (
    shop_id numeric(5,0) NOT NULL,
    product_id numeric(5,0) NOT NULL,
    product_price numeric(10,2),
    product_count numeric(3,0)
);


ALTER TABLE simbirsoft.shop_product OWNER TO postgres;

--
-- TOC entry 1802 (class 0 OID 136023)
-- Dependencies: 146
-- Data for Name: product; Type: TABLE DATA; Schema: simbirsoft; Owner: postgres
--

COPY product (product_id, product_name, recommended_price) FROM stdin;
1	Бананы	30.00
2	Помидоры	40.00
4	Ананас	150.00
6	Молоко	30.00
7	Сахар	30.00
5	Волжанка	10.00
3	Апельсин	60.00
\.


--
-- TOC entry 1801 (class 0 OID 136018)
-- Dependencies: 145
-- Data for Name: shop; Type: TABLE DATA; Schema: simbirsoft; Owner: postgres
--

COPY shop (shop_id, shop_name) FROM stdin;
1	Гулливер
2	Пятерочка
3	Лента
4	Магнит
5	Симбирка
6	Айсберг
7	Лампочка
8	Свет
\.


--
-- TOC entry 1800 (class 0 OID 136013)
-- Dependencies: 144
-- Data for Name: shop_product; Type: TABLE DATA; Schema: simbirsoft; Owner: postgres
--

COPY shop_product (shop_id, product_id, product_price, product_count) FROM stdin;
1	1	40.00	4
1	2	30.00	7
1	3	50.00	34
1	4	65.00	53
1	6	23.00	43
1	7	64.00	23
2	1	23.00	45
2	2	50.00	23
2	3	65.00	23
2	4	54.00	23
2	6	34.00	23
2	7	564.00	45
3	2	23.00	56
3	7	12.00	67
4	1	43.00	65
4	4	130.00	34
4	7	23.00	45
5	2	54.00	67
5	7	23.00	23
6	3	56.00	546
6	2	234.00	34
6	7	645.00	45
7	1	23.00	23
7	2	53.00	56
7	7	23.00	45
8	1	543.00	564
8	7	23.00	34
2	5	20.00	2
1	5	140.00	23
\.


--
-- TOC entry 1797 (class 2606 OID 136027)
-- Dependencies: 146 146
-- Name: PRODUCT_pkey; Type: CONSTRAINT; Schema: simbirsoft; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY product
    ADD CONSTRAINT "PRODUCT_pkey" PRIMARY KEY (product_id);


--
-- TOC entry 1795 (class 2606 OID 136022)
-- Dependencies: 145 145
-- Name: SHOP_pkey; Type: CONSTRAINT; Schema: simbirsoft; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY shop
    ADD CONSTRAINT "SHOP_pkey" PRIMARY KEY (shop_id);


--
-- TOC entry 1793 (class 2606 OID 136053)
-- Dependencies: 144 144 144
-- Name: shop_product_pkey; Type: CONSTRAINT; Schema: simbirsoft; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY shop_product
    ADD CONSTRAINT shop_product_pkey PRIMARY KEY (shop_id, product_id);


--
-- TOC entry 1790 (class 1259 OID 136033)
-- Dependencies: 144
-- Name: fki_product_pk; Type: INDEX; Schema: simbirsoft; Owner: postgres; Tablespace: 
--

CREATE INDEX fki_product_pk ON shop_product USING btree (product_id);


--
-- TOC entry 1791 (class 1259 OID 136039)
-- Dependencies: 144
-- Name: fki_shop_pk; Type: INDEX; Schema: simbirsoft; Owner: postgres; Tablespace: 
--

CREATE INDEX fki_shop_pk ON shop_product USING btree (shop_id);


--
-- TOC entry 1798 (class 2606 OID 136054)
-- Dependencies: 1796 144 146
-- Name: shop_product_product_id_fkey; Type: FK CONSTRAINT; Schema: simbirsoft; Owner: postgres
--

ALTER TABLE ONLY shop_product
    ADD CONSTRAINT shop_product_product_id_fkey FOREIGN KEY (product_id) REFERENCES product(product_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 1799 (class 2606 OID 136059)
-- Dependencies: 144 145 1794
-- Name: shop_product_shop_id_fkey; Type: FK CONSTRAINT; Schema: simbirsoft; Owner: postgres
--

ALTER TABLE ONLY shop_product
    ADD CONSTRAINT shop_product_shop_id_fkey FOREIGN KEY (shop_id) REFERENCES shop(shop_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 1807 (class 0 OID 0)
-- Dependencies: 5
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2013-08-27 11:27:08

--
-- PostgreSQL database dump complete
--

