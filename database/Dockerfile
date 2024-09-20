FROM postgres:14-alpine

WORKDIR /docker-entrypoint-initdb.d

COPY library/library-01-create-database.sql /docker-entrypoint-initdb.d/
COPY library/library-02-create-tables.sql /docker-entrypoint-initdb.d/
COPY library/library-03-insert-data.sql /docker-entrypoint-initdb.d/
COPY books/books-01-create-database.sql /docker-entrypoint-initdb.d/
COPY books/books-02-create-tables.sql /docker-entrypoint-initdb.d/
COPY books/books-03-insert-data.sql /docker-entrypoint-initdb.d/

ENV POSTGRES_USER=api
ENV POSTGRES_PASSWORD=api
