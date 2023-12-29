-- script to add event ids to queue and
-- webhook id to pending list

local pending_webhooks_set_key = "pending_webhook_ids"
local webhook_events_key_prefix = "webhook_events"

local internalEventId = ARGV[1]
local tenantId = ARGV[2]
local i = 3
local webhookId = ARGV[i]

while(webhookId)
do
    redis.call("RPUSH", webhook_events_key_prefix..":{"..tenantId.."}:"..webhookId, internalEventId)
    redis.call('SADD', pending_webhooks_set_key, "{"..tenantId.."}:"..webhookId)
    i =i + 1
    webhookId = ARGV[i]
end
return

