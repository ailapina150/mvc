------------------------------------------------------REDIS-------------------------------------------------------------

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

------------------------------------------------------KEYCLOAK----------------------------------------------------------
браузер: http://localhost:8081
Administration console
login password (admin 12345)
1.Создать реалм
master->Create Realm (левый верхний угол) -> ввести иля реалма (resume)
2.Создать клиента
Clients -> Create Clients -> ввести ClientId (resumeClient)
-> ввести valid redirect Urls (3 страница) ввидим порт на котором запущено приложение (http://localhost:8000/*)
3.Создать роли
Realm Role -> Create Role -> ввидим name (admin)
4.Создаем пользователя
Users -> Create New User
-> Username -> вводим login(admin) -> save
-> RoleMapping -> Assign role -> выбираем роль (admin)->assign
-> credentials-> Set password -> устанавливаем пароль (12345) -> save
5. Выбираем скоупы
Client Scope -> microprofile-jwt
6. Выполняем запрос для проверки
POST http://localhost:8081/realms/resume/protocol/openid-connect/token
Content-Type: application/x-www-form-urlencoded

client_id = resumeClient &
username = admin &
password = 12345  &
grant_type = password

Полученый jwt-token можно декодировать на сайте https://www.jwt.io/

-------------------------------------------------------SWAGGER----------------------------------------------------------
Откройте: http://localhost:8000/swagger-ui.html

Нажмите "Authorize" (замок)

Введите: Bearer ваш_токен_здесь

Убедитесь, что токен появился в верхней панели

Выполните запрос - теперь токен должен автоматически подставляться

_____________________________________________________ELASTICSEARCH______________________________________________________

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

--------------------------------------------------------KIBANA----------------------------------------------------------

    Проверьте Kibana:
браузер: http://localhost:5601



