create table hooxi.webhook_failures
(
    internal_event_id varchar(50)
        constraint webhook_failures_pk
            primary key,
    external_event_id varchar(50),
    timestamp numeric,
    http_status numeric,
    response_payload varchar,
    response_headers varchar
);

alter table hooxi.webhook_failures
    owner to hooxi;

