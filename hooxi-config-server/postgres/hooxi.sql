/* create
database hooxi
    with owner hooxi;
*/

create schema hooxi_config;

alter
schema hooxi_config owner to hooxi;

set
search_path to hooxi_config;

create table hooxi_destination_config
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

alter table hooxi_destination_config
    owner to hooxi;

create table hooxi_destination
(
    id          serial
        constraint hooxi_destination_pk
            primary key,
    destination jsonb,
    auth_config jsonb,
    tls_config jsonb,
    tenant_id   varchar
);

alter table hooxi_destination
    owner to hooxi;

