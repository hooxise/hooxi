## Hooxi Config Service

Hooxi Config Service is for storing and retrieving event delivery destination configurations.

## Run in local

#### run postgres

`docker run -d --name basic-postgres --rm -e POSTGRES_USER=hooxi -e POSTGRES_PASSWORD=hooxi#123 -e PGDATA=/var/lib/postgresql/data/pgdata -v ~/work/hooxi/postgres/data:/var/lib/postgresql/data -p 5432:5432 -it postgres:14.1-alpine`

login to postgres with these credentials and create database objects by executing script postgres/hooxi.sql

#### run redis

`docker run --name hooxi-redis -d -v ~/work/hooxi/redis/data:/data -p 6379:6379 redis redis-server --save 60`