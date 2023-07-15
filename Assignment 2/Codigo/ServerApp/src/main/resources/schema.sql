
--DROP TABLE IF EXISTS public.professor_student;

--DROP TABLE IF EXISTS public.professor;

--DROP TABLE IF EXISTS public.student;


-- Table: public.professor
CREATE TABLE IF NOT EXISTS public.professor
(
    id SERIAL PRIMARY KEY,
    name character varying(256) COLLATE pg_catalog."default" NOT NULL DEFAULT 'John Doe'::character varying
)
TABLESPACE pg_default;
ALTER TABLE IF EXISTS public.professor
    OWNER to postgres;


-- Table: public.student
CREATE TABLE IF NOT EXISTS public.student
(
    id SERIAL PRIMARY KEY,
    name character varying(256) COLLATE pg_catalog."default" NOT NULL DEFAULT 'John Doe'::character varying,
    birthdate date NOT NULL DEFAULT CURRENT_DATE,
    completed_credits integer NOT NULL DEFAULT 0,
    gpa double precision NOT NULL DEFAULT 0
)
TABLESPACE pg_default;
ALTER TABLE IF EXISTS public.student
    OWNER to postgres;


-- Table: public.professor_student
CREATE TABLE IF NOT EXISTS public.professor_student
(
    professor_id bigint NOT NULL,
    student_id bigint NOT NULL,
    CONSTRAINT professor_id FOREIGN KEY (professor_id)
        REFERENCES public.professor (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT student_id FOREIGN KEY (student_id)
        REFERENCES public.student (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
)
TABLESPACE pg_default;
ALTER TABLE IF EXISTS public.professor_student
    OWNER to postgres;


-- DROP INDEX IF EXISTS public.fki_professor_id;

-- Index: fki_professor_id
CREATE INDEX IF NOT EXISTS fki_professor_id
    ON public.professor_student USING btree
    (professor_id ASC NULLS LAST)
    TABLESPACE pg_default;


-- DROP INDEX IF EXISTS public.fki_student_id;

-- Index: fki_student_id
CREATE INDEX IF NOT EXISTS fki_student_id
    ON public.professor_student USING btree
    (student_id ASC NULLS LAST)
    TABLESPACE pg_default;
