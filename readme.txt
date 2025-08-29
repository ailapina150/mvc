

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
    Точный поиск
$body = @{
    query = @{
        match_phrase = @{
            message = "---------APPLICATION STARTED------------"
        }
    }
} | ConvertTo-Json -Depth 5

Invoke-WebRequest -Uri "http://localhost:9200/app-logs-*/_search?pretty" -Method Post -Body $body -ContentType "application/json"

    Проверьте Kibana:
браузер: http://localhost:5601



