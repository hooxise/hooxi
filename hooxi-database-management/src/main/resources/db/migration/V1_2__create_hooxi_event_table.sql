create table hooxi.hooxi_event
(
    internal_event_id varchar(50) not null
        constraint hooxi_event_pk
            primary key,
    external_event_id varchar(50),
    event_type        varchar(50),
    event_source      varchar(50),
    event_timestamp   numeric,
    payload           varchar,
    headers           jsonb,
    event_status      varchar(25),
    tenant_id         varchar,
    domain_id         varchar,
    subdomain_id      varchar,
    version           integer,
    constraint hooxi_event_unique_external_event_id
        unique (external_event_id, tenant_id)
);

alter table hooxi.hooxi_event
    owner to hooxi;

