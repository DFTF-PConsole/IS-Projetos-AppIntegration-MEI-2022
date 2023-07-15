CREATE TABLE IF NOT EXISTS public.location
(
    weather_station character varying(512) COLLATE pg_catalog."default" NOT NULL,
    location character varying(512) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT location_pkey PRIMARY KEY (weather_station, location)
) TABLESPACE pg_default;

CREATE TABLE IF NOT EXISTS public.req1
(
    "ID" SERIAL NOT NULL ,
    weather_station character varying(512) COLLATE pg_catalog."default" NOT NULL,
    number_temp_readings integer NOT NULL DEFAULT 0,
    created_at timestamp without time zone NOT NULL DEFAULT clock_timestamp(),
    CONSTRAINT req1_pkey PRIMARY KEY ("ID")
) TABLESPACE pg_default;
-- ...