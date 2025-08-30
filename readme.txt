REDIS

# Подключиться к Redis внутри контейнера
docker exec -it redis redis-cli -a 12345

# Или подключиться с локальной машины
redis-cli -h localhost -p 6379 -a 12345

# Команды для проверки:
127.0.0.1:6379> PING
# Должен ответить: PONG

127.0.0.1:6379> INFO server
# Покажет информацию о сервере

127.0.0.1:6379> SET test "Hello Redis"
# Должен ответить: OK

127.0.0.1:6379> GET test
# Должен вернуть: "Hello Redis"

#очиста кеша
127.0.0.1:6379> FLUSHALL

#Список всех ключей
127.0.0.1:6379> KEYS *

127.0.0.1:6379> exit

ELASTICSEARCH

    Проверьте ElasticSearch (должен вернуть JSON с информацией):
bash:curl http://localhost:9200/
браузер: http://localhost:9200/

    Проверьте здоровье кластера:
bash: curl http://localhost:9200/_cluster/health?pretty
браузер: http://localhost:9200/_cluster/health?pretty

  Проверте индексы в Elasticsearch:
powershell: Invoke-WebRequest -Uri "http://localhost:9200/_cat/indices?v" -Method Get
браузер: http://localhost:9200/_cat/indices?v

  Поиск в Elasticsearch:
браузер: http://localhost:9200/app-logs-*/_search?pretty

 KIBANA

    Проверьте Kibana:
браузер: http://localhost:5601



