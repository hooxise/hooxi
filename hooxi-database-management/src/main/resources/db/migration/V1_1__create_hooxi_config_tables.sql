create table hooxi.hooxi_destination_config
(
    id             serial
        constraint hooxi_destination_config_pk
            primary key,
    tenant_id      varchar,
    domain_id      varchar,
    subdomain_id   varchar,
    event_type     varchar,
    destination_id integer,
    status         varchar
);

alter table hooxi.hooxi_destination_config
    owner to hooxi;

create table hooxi.hooxi_destination
(
    id          serial
        constraint hooxi_destination_pk
            primary key,
    destination jsonb,
    auth_config jsonb,
    tls_config  jsonb,
    tenant_id   varchar
);

alter table hooxi.hooxi_destination
    owner to hooxi;