create table hooxi.webhook_event_mapping
(
    internal_event_id      varchar not null,
    webhook_destination_id integer not null,
    event_status           varchar,
    version                integer,
    constraint webhook_event_mapping_pk
        primary key (internal_event_id, webhook_destination_id)
);

alter table hooxi.webhook_event_mapping
    owner to hooxi;

