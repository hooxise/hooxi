-- script to get pending events for webhook invocation
-- returns webhook_id and list of pending event_ids

local pending_webhooks_set_key = "pending_webhook_ids"
local webhook_status_hash_key = "pending_webhook_ids"
local webhook_events_key_prefix = "webhook_events"
local pending_webhook = redis.call('SPOP', pending_webhooks_set_key, 1)
local returnValue = {}
local eventIds = {}

if(pending_webhook[1])
then
    --redis.log(redis.LOG_WARNING, "pending_webhook "..webhook_events_key_prefix..":"..pending_webhook[1])
    redis.call('HSET', webhook_status_hash_key..":"..pending_webhook[1], "status", "IN_PROGRESS")
    eventIds = redis.call('LRANGE', webhook_events_key_prefix..":"..pending_webhook[1], 0, 5)
    --redis.log(redis.LOG_WARNING, "event ids "..eventIds[1] )
    returnValue = {pending_webhook[1]}
    for i = 1, #eventIds
    do
      table.insert(returnValue, eventIds[i])
    end

else
    --redis.log(redis.LOG_WARNING, "not pending webhooks")
end
return returnValue

