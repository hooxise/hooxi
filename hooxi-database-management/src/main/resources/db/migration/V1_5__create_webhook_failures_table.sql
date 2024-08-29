create table hooxi.webhook_failure_log
(
    id serial constraint webhook_failures_pk primary key,
    internal_event_id varchar(50),
    external_event_id varchar(50),
    timestamp numeric,
    http_status numeric,
    response_payload varchar,
    response_headers varchar
);

alter table hooxi.webhook_failure_log
    owner to hooxi;

