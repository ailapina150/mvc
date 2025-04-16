-- CREATE VIEW IF NOT EXISTS schema_conferences.users_view
--     AS
    SELECT
    u.id                as id,
    u.user_name         as user_name,
    u.name              as name,
    u.entry_date        as entry_date ,
    u.field_of_activity as field_of_activity,
    count( cs.conference_id) as asconference_count,
    sum(cs.point)       as rating,
    (SELECT c.name FROM  schema_conferences.chats c WHERE u.chat_id = c.id) as chat_name
    --сылки на общие таблицы конференций
    FROM schema_conferences.users u
    LEFT JOIN schema_conferences.conference_speaker cs ON  cs.user_id = u.id
    GROUP BY u.id, u.user_name, u.name, u.entry_date , u.field_of_activity, chat_name;

SELECT
    u.id                as id,
    u.user_name         as user_name,
    u.name              as name,
    u.entry_date        as entry_date ,
    u.field_of_activity as field_of_activity,
    count( cs.conference_id) as asconference_count,
    sum(cs.point)       as rating,
    (SELECT c.name FROM  schema_conferences.chats c WHERE u.chat_id = c.id) as chat_name
    --сылки на общие таблицы конференций
FROM schema_conferences.users u
         LEFT JOIN schema_conferences.conference_speaker cs ON  cs.user_id = u.id
GROUP BY u.id, u.user_name, u.name, u.entry_date , u.field_of_activity, chat_name


SELECT
(SELECT u.name FROM schema_conferences.users u WHERE u.id = ut.user_id) AS name,
ut.title,
count(cs.user_id)
FROM schema_conferences.user_topic ut
LEFT JOIN schema_conferences.conference_speaker cs
    ON ut.id = cs.user_topic_id
WHERE ut.approved is null
GROUP BY ut.user_id, ut.title;

-- SELECT
-- cs.user_id
-- cs.
-- FROM schema_conferences.conference_speaker cs;

INSERT INTO schema_conferences.users(id,user_name,name,entry_date, field_of_activity, chat_id)
VALUES (1,'@Vasya', 'Вася', '2021-02-12', 'www', 1 ),
       (2,'@Masha', 'Maша', '2022-03-15', 'www', 2 ),
       (3,'@Kolya', 'Коля', '2021-02-12', 'www', 1 );

