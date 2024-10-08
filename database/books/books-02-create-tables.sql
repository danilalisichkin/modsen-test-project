\c books;

CREATE TABLE public.books (
                              id bigint NOT NULL,
                              author character varying(100) NOT NULL,
                              description character varying(400) NOT NULL,
                              genre character varying(100) NOT NULL,
                              isbn character varying(255) NOT NULL,
                              title character varying(100) NOT NULL
);

ALTER TABLE public.books OWNER TO api;

ALTER TABLE public.books ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.books_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);

ALTER TABLE ONLY public.books
    ADD CONSTRAINT books_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.books
    ADD CONSTRAINT ukkibbepcitr0a3cpk3rfr7nihn UNIQUE (isbn);
