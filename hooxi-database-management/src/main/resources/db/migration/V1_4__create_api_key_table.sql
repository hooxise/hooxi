create table hooxi.api_key
(
    api_key_id serial
        constraint hooxi_api_key_pk
            primary key,
    api_key_hash varchar UNIQUE,
    api_key_permissions varchar,
    api_key_name varchar UNIQUE,
    description varchar,
    create_ts numeric,
    expiration_ts numeric,
    created_by varchar,
    updated_by varchar,
    update_ts numeric,
    is_active boolean
);

alter table hooxi.api_key
    owner to hooxi;

