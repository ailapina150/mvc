CREATE SCHEMA IF NOT EXISTS schema_conferences;

-------------------------------------------------УЧАСТНИКИ--------------------------------------------------------------
--Таблица визитка участника
-- не у всех юзеров в tg есть User_name
CREATE TABLE IF NOT EXISTS schema_conferences.users(
    id BIGINT PRIMARY KEY,--идентификатор участника в системе
    tg_id BIGINT NOT NULL,--идентификатор участника в телеграм
    user_name VARCHAR(30) , -- user_name участника в телеграм (может отсутствовать)
    tg_name VARCHAR(30) NOT NULL, -- имя участника в телеграм FIRST_NAME
    name VARCHAR(30) NOT NULL, -- имя участника
    entry_date DATE NOT NULL, --дата вступления
    field_of_activity VARCHAR(255) --сфера деятельности(может отсутствовать)
);
INSERT INTO schema_conferences.users (id, tg_id, user_name,tg_name, name, entry_date,field_of_activity)
VALUES (1,12123,'@Vasya','Vasya2' , 'Vasya','2023-12-12','management'),
       (2,12123,'@Masha','Masha134', 'Masha','2024-12-23','coking'),
       (3,12123,'@Vera','Vera1345', 'Vera','2023-12-12','painting'),
       (4,12123,'@Dima','Dimon', 'Dima','2023-12-12','drawing');

--------------------------------------;-----------СОГЛАСОВАНИЕ-----------------------------------------------------------
--Таблица согласования времени конференции
--id conference не прописываем, т. к. конференция еще не создана
--Таблица "согласование графика" будет очищатся после создания конференции?
CREATE TABLE IF NOT EXISTS schema_conferences.dates_and_times(
    id  BIGINT PRIMARY KEY, --индентификатор
    user_id BIGINT NOT NULL,-- пользователь
    date_time TIMESTAMP NOT NULL, -- дата и время когда пользователь свободен
    FOREIGN KEY (user_id) REFERENCES schema_conferences.users (id)
);
INSERT INTO schema_conferences.dates_and_times(id, user_id,date_time)
VALUES (1,1, '2025-04-11T12:00:00'),
       (2,1, '2025-04-11T13:00:00'),
       (3,1,'2025-04-11T14:00:00'),
       (4,1,'2025-04-11T15:00:00'),
       (5,2, '2025-04-11T10:00:00'),
       (6,2, '2025-04-11T11:00:00'),
       (7,2,'2025-04-11T12:00:00'),
       (8,2,'2025-04-11T13:00:00'),
       (9,3, '2025-04-11T12:00:00'),
       (10,3, '2025-04-11T13:00:00'),
       (11,4,'2025-04-11T12:00:00'),
       (12,4,'2025-04-11T16:00:00');
--Таблица согласования темы конференции
--id conference не прописываем, т. к. конференция еще не создана
--Таблица "прелагаемые темы" будет очищена после создания конференции?
CREATE TABLE IF NOT EXISTS schema_conferences.topics
(
    id  BIGINT PRIMARY KEY, --индентификатор
    user_id BIGINT NOT NULL,-- пользователь, предложивший тему
    title VARCHAR(255) NOT NULL ,--тема конференции
    count INT NOT NULL DEFAULT 0 --количество участников готовых выступить
);
INSERT INTO schema_conferences.topics (id, user_id,title,count)
VALUES (1,1, 'Cats',4),
       (2,1, 'Dogs',3),
       (3,3,'Fish',1),
       (4,4,'Birds',1);
--------------------------------------------------КОНФЕРЕНЦИЯ-----------------------------------------------------------
--статус конференции
CREATE TABLE IF NOT EXISTS schema_conferences.conference_status(
    id BIGINT PRIMARY KEY, --идентификатор статуса
    name VARCHAR(30) NOT NULL -- название статуса
);

INSERT INTO schema_conferences.conference_status(id, name)
VALUES (1,'Предложена'),--согласованы графики
       (2,'Заплонирована'),--утверждена тема ЗАМЕНИТЬ НА СОЗДАНА?
       (3,'В процессе'),
       (4,'Состоялась'),
       (5,'Не состоялась');
--Коференция создается после того как определено время и тема?
--Конференция может включать в себя несколько групп или логичнее просто создаются несколько конференций с одинаковой темой и временем?
--Конференция имеет время модерации одинаковое для всех ее учащихся?
--Кто такой модератор? Каждый выступающий имеет своего модератора, а так же имеется модератор для каждой конференции?
--Что такое ссылка на конференцию? Это ссылка на zoom например? Надо ли ее хранить в базе?
--Правила модерации одинаковые для всех конференций?
--Что представляет из себя ссылка на правила конференции?  Надо ли ее хранить в базе?
CREATE TABLE IF NOT EXISTS schema_conferences.conferences(
    id BIGINT PRIMARY KEY,-- идентификатор
    topic VARCHAR(255) NOT NULL,--тема конференции
    start_data_time TIMESTAMP NOT NULL, --дата и время конференции?
    time_of_moderation INT NOT NULL,-- время модерации для каждого участника конференции в минутах
    reference VARCHAR(255) NOT NULL, -- ссылка на конференцию
    status_id BIGINT NOT NULL, --статус конференции
    FOREIGN KEY (status_id) REFERENCES schema_conferences.conference_status (id)
);
INSERT INTO schema_conferences.conferences (id,topic,start_data_time,time_of_moderation, reference,status_id)
VALUES (1,'Cats','2025-10-19T12:00:00',10,'cat-link',4),
       (2,'Dogs','2025-10-19T13:00:00',15,'dog-link',4);
----------------------------------------------УЧАСТНИК-НА-КОНФЕРЕНЦИИ---------------------------------------------------
CREATE TABLE IF NOT EXISTS schema_conferences.conference_results(
    id BIGINT PRIMARY KEY, --идентификатор
    reason VARCHAR(255) NOT NULL,  -- основание для начисления балов или штрафов
    point INT NOT NULL -- количество балов (заполняется после результатов голосования)
);
INSERT INTO schema_conferences.conference_results(id,reason,point)
VALUES (1,'лучший модератор',1),
       (2,'лучший спикер',3),
       (3,'худший модератор',-1),
       (4,'худший спикер',-3),
       (5,'не присудствовал',-5),
       (6,'луший спикер и модератор',4),
       (7,'луший спикер но худший модератор',2),
       (8,'луший модератор но худший спикер',-2);

    CREATE TABLE IF NOT EXISTS schema_conferences.conferences_users(
    id BIGINT PRIMARY KEY, --идентификатор
    user_id BIGINT NOT NULL, -- идентфикатор участника конференции
    conference_id BIGINT NOT NULL, --идентификатор конфиренции
    attendance BOOLEAN NOT NULL DEFAULT FALSE, --присутствие участника на конферении
    --group_number INT NOT NULL DEFAULT 1, --группа в конференции, если нужна?????
    speaker_number INT NOT NULL, -- номер в списке под которым участник выступает как спикер
    moderator_number INT NOT NULL, -- номер в списке под которым участник выступает как модератор
    result_id BIGINT,
    FOREIGN KEY (conference_id) REFERENCES schema_conferences.conferences (id),
    FOREIGN KEY (user_id) REFERENCES schema_conferences.users (id),
    FOREIGN KEY (result_id) REFERENCES schema_conferences.conference_results (id)
);
INSERT INTO schema_conferences.conferences_users (id,user_id,conference_id,attendance,speaker_number,moderator_number,result_id)
VALUES (1,1,1,true,1,4,2),
       (2,2,1,true,2,1,1),
       (3,3,1,true,3,2,4),
       (4,4,1,false,4,3,5),
       (5,1,2,true,1,3,2),
       (6,2,2,true,2,1,3),
       (7,3,2,false,3,2,5);
-- таблица для голосоания
-- Таблица голосование очищается каждый раз после заполнения графы point в таблице conference_speaker?
-- Пользователю обязательно надо заполнить все колонки в таблицы голосования или он может не голосовать?
CREATE TABLE IF NOT EXISTS schema_conferences.voice
(
    id BIGINT PRIMARY KEY, --идентификатор
    best_speaker BIGINT DEFAULT NULL,
    worse_speaker BIGINT DEFAULT NULL,
    best_moderator BIGINT DEFAULT NULL,
    worse_moderator BIGINT DEFAULT NULL,
    FOREIGN KEY (id) REFERENCES schema_conferences.conferences_users (id),
    FOREIGN KEY (best_speaker) REFERENCES schema_conferences.users (id),
    FOREIGN KEY (worse_speaker) REFERENCES schema_conferences.users (id),
    FOREIGN KEY (best_moderator) REFERENCES schema_conferences.users (id),
    FOREIGN KEY (worse_moderator) REFERENCES schema_conferences.users (id)
);
INSERT INTO schema_conferences.voice(id, best_speaker, worse_speaker,best_moderator,worse_moderator)
VALUES (1,2,3,null,null),
       (2,2,1,3,null);

-------------------------------------------------ПРАВИЛА----------------------------------------------------------------
CREATE TABLE IF NOT EXISTS schema_conferences.type_rules
(
    id BIGINT PRIMARY KEY,--идентификатор
    name VARCHAR(30) NOT NULL -- тип правил
);

INSERT INTO schema_conferences.type_rules(id, name)
VALUES (1,'community'),-- правила сообщества,
       (2,'moderation'),-- moderation - правила модерации,
       (3,'rule change'); -- rule change - правила изменения правил

--пункт правила всегда целое число?
CREATE TABLE IF NOT EXISTS schema_conferences.rules(
    id  BIGINT PRIMARY KEY,--идентификатор правила
    type_id BIGINT NOT NULL,--идентификатор типа правил
    clause INT NOT NULL, -- пункт правила
    wording TEXT NOT NULL,-- формулировка правила
    actual BOOLEAN NOT NULL, -- актуальность првавила (действует или изменено/удалено)
    FOREIGN KEY (type_id ) REFERENCES schema_conferences.type_rules (id)
) ;

INSERT INTO schema_conferences.rules(id,type_id,clause,wording,actual)
VALUES (1,1,1,'Сообщество предпологает активное участие',true),
       (2,1,2,'Каждый участник должен менять правила',false),
       (3,1,2,'Каждый участник может менять правила',true),
       (4,2,1,'Время модерации расчитывается в зависимости от количества участников',true);

--предложения по изменению правил
--после утверждения вносится в талицу schema_conferences.rules
CREATE TABLE IF NOT EXISTS schema_conferences.rule_proposals
(
    id  BIGINT PRIMARY KEY,--идентификатор правила
    proposal_date DATE NOT NULL, --дата предложения
    user_id  BIGINT NOT NULL, --инициатор изменения/введения правила
    type_id BIGINT NOT NULL,--тип правил
    old_id BIGINT, --идентификатор правила, для которого предлогаются изменения
    wording TEXT NOT NULL,-- предлагаемая формулировка
    argument TEXT NOT NULL , --аргумент, почему изменение неоходимо
    start_time TIMESTAMP NOT NULL , --дата и время начала голосования
    end_time TIMESTAMP NOT NULL , --дата и время окончания голосования
    votes_for INT NOT NULL  DEFAULT 0, -- голоса "ЗА"
    votes_against INT NOT NULL  DEFAULT 0, --голаса против
    FOREIGN KEY (user_id) REFERENCES schema_conferences.users (id),
    FOREIGN KEY (old_id) REFERENCES schema_conferences.rules (id),
    FOREIGN KEY (type_id ) REFERENCES schema_conferences.type_rules (id)
);

INSERT INTO schema_conferences.rule_proposals
    (id,proposal_date,user_id,type_id,old_id, wording,argument,start_time,end_time,votes_for,votes_against)
VALUES (1,'2024-10-23',1,1,2,'Каждый участник может менять правила','так лучше','2024-10-23T12:00:00', '2024-10-24T12:00:00', 2,1);


