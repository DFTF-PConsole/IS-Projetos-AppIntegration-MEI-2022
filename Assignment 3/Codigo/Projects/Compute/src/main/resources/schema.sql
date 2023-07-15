-- Table: public.location

-- DROP TABLE IF EXISTS public.location;

CREATE TABLE IF NOT EXISTS public.location
(
    id SERIAL NOT NULL,
    weather_station character varying(512) COLLATE pg_catalog."default" NOT NULL,
    location character varying(512) COLLATE pg_catalog."default" NOT NULL,
    created_at timestamp with time zone NOT NULL DEFAULT clock_timestamp(),
    CONSTRAINT location_pkey PRIMARY KEY (id),
    CONSTRAINT weather_location_pair UNIQUE (weather_station, location)
    )
                         WITH (
                             OIDS = FALSE
                             )
    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.location
    OWNER to postgres;

-- Table: public.req1

-- DROP TABLE IF EXISTS public.req1;

CREATE TABLE IF NOT EXISTS public.req1
(
    id SERIAL NOT NULL,
    weather_station character varying(512) COLLATE pg_catalog."default" NOT NULL,
    number_temperature_readings integer NOT NULL DEFAULT 0,
    created_at timestamp with time zone NOT NULL DEFAULT clock_timestamp(),
    CONSTRAINT req1_pkey PRIMARY KEY (id)
    )
                         WITH (
                             OIDS = FALSE
                             )
    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.req1
    OWNER to postgres;

CREATE TABLE IF NOT EXISTS public.req2
(
    id SERIAL NOT NULL,
    location character varying(512) NOT NULL,
    number_temperature_readings integer NOT NULL DEFAULT 0,
    created_at timestamp with time zone NOT NULL DEFAULT clock_timestamp(),
    PRIMARY KEY (id)
    )
                         WITH (
                             OIDS = FALSE
                             );

CREATE TABLE IF NOT EXISTS public.req3
(
    id SERIAL NOT NULL,
    weather_station character varying(512) COLLATE pg_catalog."default" NOT NULL,
    min_temp double precision NOT NULL DEFAULT 0.00,
    max_temp double precision NOT NULL DEFAULT 0.00,
    created_at timestamp with time zone NOT NULL DEFAULT clock_timestamp(),
    CONSTRAINT req3_pkey PRIMARY KEY (id)
    )
                         WITH (
                             OIDS = FALSE
                             )
    TABLESPACE pg_default;

-- Table: public.req4

-- DROP TABLE IF EXISTS public.req4;

CREATE TABLE IF NOT EXISTS public.req4
(
    id SERIAL NOT NULL,
    location character varying(512) COLLATE pg_catalog."default" NOT NULL,
    min_temp double precision NOT NULL DEFAULT 0.00,
    max_temp double precision NOT NULL DEFAULT 0.00,
    created_at timestamp with time zone NOT NULL DEFAULT clock_timestamp(),
    CONSTRAINT req4_pkey PRIMARY KEY (id)
    )
                         WITH (
                             OIDS = FALSE
                             )
    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.req4
    OWNER to postgres;


CREATE TABLE IF NOT EXISTS public.req5
(
    id serial NOT NULL,
    weather_station character varying(512) NOT NULL,
    number_alerts integer NOT NULL DEFAULT 0,
    created_at timestamp with time zone NOT NULL DEFAULT clock_timestamp(),
    PRIMARY KEY (id)
    )
                         WITH (
                             OIDS = FALSE
                             );

ALTER TABLE IF EXISTS public.req5
    OWNER to postgres;

CREATE TABLE IF NOT EXISTS public.req6
(
    id serial NOT NULL,
    alert_type character varying(512) NOT NULL,
    number_alerts integer NOT NULL DEFAULT 0,
    created_at timestamp with time zone NOT NULL DEFAULT clock_timestamp(),
    PRIMARY KEY (id)
    )
                         WITH (
                             OIDS = FALSE
                             );

ALTER TABLE IF EXISTS public.req6
    OWNER to postgres;


CREATE TABLE IF NOT EXISTS public.req7
(
    id serial NOT NULL,
    weather_station character varying(512) NOT NULL,
    min_temp double precision NOT NULL DEFAULT 0.00,
    created_at timestamp with time zone NOT NULL DEFAULT clock_timestamp(),
    PRIMARY KEY (id)
    )
                         WITH (
                             OIDS = FALSE
                             );

ALTER TABLE IF EXISTS public.req7
    OWNER to postgres;


CREATE TABLE IF NOT EXISTS public.req9
(
    id serial NOT NULL,
    min_temp double precision NOT NULL DEFAULT 0.00,
    weather_station character varying(512) NOT NULL,
    created_at timestamp with time zone NOT NULL DEFAULT clock_timestamp(),
    PRIMARY KEY (id)
    )
                         WITH (
                             OIDS = FALSE
                             );

ALTER TABLE IF EXISTS public.req9
    OWNER to postgres;


CREATE TABLE IF NOT EXISTS public.req10
(
    id serial NOT NULL,
    weather_station character varying(512) NOT NULL,
    avg_temp double precision NOT NULL DEFAULT 0.00,
    created_at timestamp with time zone NOT NULL DEFAULT clock_timestamp(),
    PRIMARY KEY (id)
    )
                         WITH (
                             OIDS = FALSE
                             );

ALTER TABLE IF EXISTS public.req10
    OWNER to postgres;

CREATE TABLE IF NOT EXISTS public.req11
(
    id serial NOT NULL,
    weather_station character varying(512) NOT NULL,
    avg_temp double precision NOT NULL,
    created_at timestamp with time zone NOT NULL DEFAULT clock_timestamp(),
    PRIMARY KEY (id)
    )
                         WITH (
                             OIDS = FALSE
                             );

ALTER TABLE IF EXISTS public.req11
    OWNER to postgres;
-- ...
