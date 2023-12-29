-- script to get pending events for webhook invocation
-- returns webhook_id and list of pending event_ids

local pending_webhooks_set_key = "pending_webhook_ids"
local webhook_status_hash_key = "pending_webhook_ids"
local webhook_events_key_prefix = "webhook_events"
local pending_webhook = ARGV[1]

redis.log(redis.LOG_WARNING, " from update webhook status pending_webhook "..pending_webhook )
local remaining_events = redis.call('LLEN', webhook_events_key_prefix..":"..pending_webhook)
redis.log(redis.LOG_WARNING, "remaining_events "..remaining_events )
if remaining_events > 0 then
    redis.call('HSET', webhook_status_hash_key..":"..pending_webhook, "status", "PENDING")
    redis.call('SADD', pending_webhooks_set_key, pending_webhook)
else
    redis.call('HSET', webhook_status_hash_key..":"..pending_webhook, "status", "DONE")
end
return