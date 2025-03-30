DROP TABLE IF EXISTS public.post;

DROP TABLE IF EXISTS public.author;

CREATE TABLE IF NOT EXISTS public.author
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    name character varying COLLATE pg_catalog."default" NOT NULL,
    surname character varying COLLATE pg_catalog."default" NOT NULL,
    nickname character varying COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT author_pkey PRIMARY KEY (id),
    CONSTRAINT unique_author_nickname UNIQUE (nickname)
);

CREATE TABLE IF NOT EXISTS public.post
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    title character varying COLLATE pg_catalog."default" NOT NULL,
    hash_url character varying(8) COLLATE pg_catalog."default" NOT NULL,
    author character varying COLLATE pg_catalog."default" NOT NULL,
    version integer NOT NULL,
    deleted_after timestamp with time zone NOT NULL,
    created_at timestamp with time zone NOT NULL,
    CONSTRAINT post_pkey PRIMARY KEY (id),
    CONSTRAINT key_for_author FOREIGN KEY (author)
        REFERENCES public.author (nickname) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
);

INSERT INTO public.author (name, surname, nickname) VALUES
('John', 'Doe', 'johndoe'),
('Jane', 'Doe', 'janedoe'),
('Peter', 'Jones', 'peterjones');


INSERT INTO public.post (title, hash_url, author, version, deleted_after, created_at) VALUES
('Post 1', '12345678', 'johndoe', 1, '15/12/2024', '08/12/2024'),
('Post 2', '87654321', 'janedoe', 1, '28/12/2024', '09/12/2024'),
('Post 3', '13579246', 'peterjones', 1, '31/12/2024', '10/12/2024');

delete from author where id = 1;

delete from post where id = 2;
