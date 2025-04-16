CREATE SCHEMA IF NOT EXISTS schema_conferences;

-- CREATE TABLE IF NOT EXISTS schema_conferences.chats(
--     id BIGINT PRIMARY KEY,
--     name VARCHAR(30) NOT NULL
-- );
--
-- INSERT INTO schema_conferences.chats(id, name)--добавить чаты?
-- VALUES (1,'Активные участники'),
--        (2,'Полезные участни'),
--        (3,'Удаленные участники');--?

CREATE TABLE IF NOT EXISTS schema_conferences.users(
    id BIGINT PRIMARY KEY,--используем телеграм ID, если пользователи только из телеграм
    tgId BIGINT,
    user_name VARCHAR(30) ,
    name VARCHAR(30) NOT NULL,
    entry_date DATE NOT NULL,
    field_of_activity VARCHAR(100)
    --chat_id BIGINT NOT NULL, -- нужна ли ссылка на реальный телеграм чат?
    --FOREIGN KEY (chat_id) REFERENCES schema_conferences.chats (id)
);

CREATE TABLE IF NOT EXISTS schema_conferences.conference_status(
    id BIGINT PRIMARY KEY,
    name VARCHAR(30) NOT NULL
);

INSERT INTO schema_conferences.conference_status(id, name)--добавить статусы?
VALUES (1,'Предложена'),--согласованы графики
       (2,'Заплонирована'),--утверждены темы
       (3,'В процессе'),
       (4,'Состоялась'),
       (5,'Не состоялась');

CREATE TABLE IF NOT EXISTS schema_conferences.rules(
    id  BIGINT PRIMARY KEY,
    name TEXT NOT NULL,
    actual BOOLEAN  NOT NULL,
    user_id  BIGINT,
    old_id BIGINT,
    argument TEXT,
   FOREIGN KEY (user_id) REFERENCES schema_conferences.users (id)
) ;

INSERT INTO schema_conferences.rules(id, name, actual)
VALUES (1,'Каждый новый участник должен заполнить «Визитку участника» после вступления в чат участников.', true),
       (2,'Все новые участники должны ознакомиться с «Правилами модерации конференций»
Участники сами являются модераторами, поэтому важно следовать правилам, чтобы избежать проблем с проведением конференции. Нарушение правил может привести к штрафам, если за это проголосует большинство участников.
', true),
       (3,'Каждую неделю публикуется «График согласования даты и времени конференций».
Каждый участник должен заполнить график, в соответствии с дедлайном, указав доступные диапазоны времени для участия в конференции. Минимальная продолжительность диапазона — 2 часа. Участники, не заполнившие график, получают штраф. Это обусловлено идеей активного участия в жизни сообщества.
', true),
       (4,'После заполнения графиков будет определена дата и время конференции на основе совпадений в графиках участников.
Если количество совпадений, между диапазонами участников, меньше минимально необходимого для проведения конференции, она не состоится.
Если есть несколько подходящих диапазонов – количество совпадений больше минимально необходимого для проведения конференции, для каждого будет назначена своя дата и время.
', true),
       (5,'Если график участника не соответствует ни одной из дат конференции, определенных по итогу сопоставления графиков, он не участвует в конференциях на текущей неделе.', true);

CREATE TABLE IF NOT EXISTS schema_conferences.conferences(
    id BIGINT PRIMARY KEY,
    conferences_topic VARCHAR(100) NOT NULL, --нужно ли добавить field_of_activity
    data DATE,
    start_time TIME, --- надо ли хранить дату и время предложенную для голосования
    time_of_moderation INT,
    reference VARCHAR(265),
    status_id BIGINT NOT NULL,
    FOREIGN KEY (status_id) REFERENCES schema_conferences.conference_status (id)
    ---? нада ли ссылка на правила или правила везде одни
);

CREATE TABLE IF NOT EXISTS schema_conferences.time(
    id  BIGINT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    data DATE NOT NULL,
    time TIME NOT NULL,
    conference_id BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES schema_conferences.users (id),
    FOREIGN KEY (conference_id) REFERENCES schema_conferences.conferences (id)
);

CREATE TABLE IF NOT EXISTS schema_conferences.user_topic(
    id BIGINT PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    approved BOOLEAN,-- или хранить число голосов
    user_id BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES schema_conferences.users (id)
);

CREATE TABLE IF NOT EXISTS schema_conferences.conference_speaker(
    id BIGINT PRIMARY KEY,
    group_number INT,
    speaker_number INT,
    moderator_number INT,
    attendance BOOLEAN,
    point INT,-- результаты голосования нужно ли хранить кто за кого голосовал?
   -- user_topic_id BIGINT NOT NULL ,
    conference_id BIGINT NOT NULL,
    user_id  BIGINT NOT NULL ,
    FOREIGN KEY (conference_id) REFERENCES schema_conferences.conferences (id),
    FOREIGN KEY (user_id) REFERENCES schema_conferences.users (id)--,
   -- FOREIGN KEY (user_topic_id) REFERENCES schema_conferences.user_topic (id)
);



