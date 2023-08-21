-- find destinations
-- tenant:domain:subdmain:eventType

redis.log(redis.LOG_WARNING, ARGV[1])
redis.log(redis.LOG_WARNING, ARGV[2])
local key = "DEST:"..ARGV[1]..":"..ARGV[2]..":"..ARGV[3]..":"..ARGV[4]
redis.log(redis.LOG_WARNING, key)
local destinations = redis.call('LRANGE', key, 0, -1)
if destinations ~= nil then
    return destinations
end
key = "DEST:"..ARGV[1]..":"..ARGV[2]..":"..ARGV[3]
destinations = redis.call('LRANGE', key, 0, -1)
if destinations ~= nil then
    return destinations
end
key = "DEST:"..ARGV[1]..":"..ARGV[2]
destinations = redis.call('LRANGE', key, 0, -1)
if destinations ~= nil then
    return destinations
end
key = "DEST:"..ARGV[1]
destinations = redis.call('LRANGE', key, 0, -1)
if destinations ~= nil then
    return destinations
end
return nil
