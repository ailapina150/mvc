--------------------------------------------Таблица визитка участника---------------------------------------------------
--Уточнить считаются ли конфиренции, на котрые участник не пришол?
CREATE OR REPLACE VIEW schema_conferences.user_card
AS
SELECT u.name,
       u.user_name,
       u.entry_date,
       u.field_of_activity,
       sum(cr.point)   AS sum_point,
       count(u.id)     AS count_conf,   -- число конференций на которые пользователь был зарегистрирован
       sum(CAST(cs.attendance AS INT)), -- число конференций на которых пользователь выступал
       array_agg(u.id) AS array_сonf
From schema_conferences.users u
         JOIN schema_conferences.conferences_users cs ON u.id = cs.user_id
         JOIN schema_conferences.conference_results cr on cs.result_id = cr.id
GROUP BY u.name, u.user_name, u.entry_date, u.field_of_activity
ORDER BY name;
COMMENT ON VIEW  schema_conferences.user_card IS 'Визитка участника';
----------------------------------------Графики участия в конфиренции---------------------------------------------------
CREATE OR REPLACE VIEW schema_conferences.schedule_of_conference
AS
SELECT u.name,
       u.user_name,
       array_agg(date_time) AS times
FROM schema_conferences.users u
         JOIN schema_conferences.dates_and_times t
              ON u.id = t.user_id
GROUP BY u.name, u.user_name
ORDER BY u.name;
COMMENT ON VIEW  schema_conferences.schedule_of_conference IS 'График участия в конференции';

CREATE OR REPLACE VIEW schema_conferences.agreed_data_and_time
AS
SELECT t.date_time,
count(u.id)       AS count,
array_agg(u.name) AS names
FROM schema_conferences.users u
         JOIN schema_conferences. dates_and_times t
              ON u.id = t.user_id
GROUP BY t.date_time
HAVING count(u.id)  >2
ORDER BY t.date_time;
COMMENT ON VIEW  schema_conferences.agreed_data_and_time IS 'Согласованные дата и время конференции';
----------------------------------------Темы предлагаемые на конференции------------------------------------------------
CREATE OR REPLACE VIEW schema_conferences.list_of_topics
AS
SELECT u.user_name,
       u.name,
       t.title,
       t.count
FROM schema_conferences.topics t
         JOIN schema_conferences.users u ON t.user_id = u.id;
COMMENT ON VIEW  schema_conferences.list_of_topics IS 'Перечень тем';

CREATE OR REPLACE VIEW schema_conferences.results_by_topic
AS
SELECT
cf.topic,
cf.start_data_time,
cf.reference,
u.name,
u.user_name
FROM schema_conferences.conferences_users cs
JOIN schema_conferences.users u ON cs.user_id = u.id
JOIN schema_conferences.conferences cf ON cf.id= cs.conference_id
where start_data_time > CURRENT_TIMESTAMP
ORDER BY cf.start_data_time ,cf.reference,u.name;
COMMENT ON VIEW  schema_conferences.results_by_topic IS 'Итоги голосования по темам и разбивка на группы';
----------------------------------------Общие таблицы коференции--------------------------------------------------------
CREATE OR REPLACE VIEW schema_conferences.conference_general
AS
SELECT conf.topic,
       conf.time_of_moderation,
       conf.start_data_time,
       conf.reference,
       (SELECT name FROM schema_conferences.conference_status cs WHERE cs.id = conf.status_id) AS status,
       count(cs.user_id)                                                                       AS count_spicer,       -- все учащиеся
       sum(CAST(cs.attendance AS INT))                                                         AS count_attend_spicer -- число учащихся, которые выступали
FROM schema_conferences.conferences conf
         JOIN schema_conferences.conferences_users cs
              ON conf.id = cs.conference_id
GROUP BY conf.topic, conf.time_of_moderation, conf.reference, conf.start_data_time, status;
COMMENT ON VIEW schema_conferences.conference_general IS 'Общая информация о конференции';

CREATE OR REPLACE VIEW schema_conferences.queues_for_speeches_and_moderation
AS
SELECT
conference_id,
row_number() over (partition by conference_id )                                  AS number,
(SELECT u.name FROM schema_conferences.users u WHERE u.id = cs.speaker_number)   AS speaker,
(SELECT u.name FROM schema_conferences.users u WHERE u.id = cs.moderator_number) AS moderator,
(SELECT c.time_of_moderation FROM schema_conferences.conferences c WHERE c.id = cs.conference_id) AS time_of_moderation
FROM schema_conferences.conferences_users cs;
COMMENT ON VIEW  schema_conferences.conference_general IS 'Очереди выступлений и модерации';

CREATE OR REPLACE VIEW schema_conferences.missing_and_excluded
AS
SELECT conference_id,
       row_number() over (partition by conference_id )                         AS number,
       (SELECT u.name FROM schema_conferences.users u WHERE u.id = cs.user_id) AS user,
       CASE WHEN cs.attendance THEN 'ПРИСУТСВУЕТ' ELSE 'ОТСУТСВУЕТ' END        AS status,
       CASE WHEN cr.point < 0 AND cr.reason notnull THEN cr.reason ELSE '' END AS reason,
       CASE WHEN cr.point < 0 THEN -1 * cr.point ELSE 0 END                    AS fine
FROM schema_conferences.conferences_users cs
JOIN schema_conferences.conference_results cr on cs.result_id = cr.id;
COMMENT ON VIEW  schema_conferences.missing_and_excluded IS 'Отсутствующие и исключенные участники';

CREATE OR REPLACE VIEW schema_conferences.voting
AS
SELECT
row_number() over () AS number,
(SELECT u.name FROM schema_conferences.users u WHERE u.id = cs.id)                               AS user,
(COALESCE((SELECT u.name FROM schema_conferences.users u WHERE u.id = v.best_speaker), '') )     AS best_speaker,
(COALESCE((SELECT u.name FROM schema_conferences.users u WHERE u.id = v.worse_speaker), '') )    AS worse_speaker,
(COALESCE((SELECT u.name FROM schema_conferences.users u WHERE u.id = v.best_moderator), '') )   AS best_moderator,
(COALESCE((SELECT u.name FROM schema_conferences.users u WHERE u.id = v.worse_moderator), '') )  AS worse_moderator
FROM schema_conferences.voice v
JOIN schema_conferences.conferences_users cs on cs.id = v.id;
COMMENT ON VIEW  schema_conferences.voting IS 'Голосование';

--Лист5 Итоги конференции
CREATE OR REPLACE VIEW schema_conferences.conference_result
AS
SELECT conference_id,
       row_number() over (partition by conference_id )                         AS number,
       (SELECT u.name FROM schema_conferences.users u WHERE u.id = cs.user_id) AS user,
       cr.reason                                                               AS reason,
       cr.point                                                                AS FINE
FROM schema_conferences.conferences_users cs
JOIN schema_conferences.conference_results cr on cs.result_id = cr.id;
COMMENT ON VIEW  schema_conferences.conference_result IS 'Итоги конфернции';
-----------------------------------------------------Рейтинг------------------------------------------------------------
CREATE OR REPLACE VIEW schema_conferences.rating
AS
SELECT  u.user_name,
        u.name,
        sum(cr.point) AS rating,
        count(u.id)   AS count_conf
From schema_conferences.users u
         JOIN schema_conferences.conferences_users cs on u.id = cs.user_id
         JOIN schema_conferences.conference_results cr on cs.result_id = cr.id
GROUP BY u.name, u.user_name, u.entry_date, u.field_of_activity
ORDER BY name;
COMMENT ON VIEW schema_conferences.rating IS 'Рейтинг участников';
----------------------------------------Предложения по изменению правил-------------------------------------------------
CREATE OR REPLACE VIEW schema_conferences.change_rules
AS
SELECT
rp.id,
rp.proposal_date,
(SELECT u.name FROM schema_conferences.users u WHERE u.id = rp.user_id)           AS user,
(SELECT t.name FROM schema_conferences.type_rules t WHERE t.id = rp.type_id)      AS type,
(SELECT r.clause FROM schema_conferences.rules r WHERE r.id = rp.old_id)          AS clause,
(SELECT r.wording FROM schema_conferences.rules r WHERE r.id = rp.old_id)         AS old_wording,
rp.wording ,
rp.argument,
rp.start_time,
rp.end_time,
rp.votes_for,
rp.votes_against,
CASE WHEN rp.votes_for> rp.votes_against THEN 'ПРИНЯТО' ELSE 'НЕПРИНЯТО' END    AS status
FROM schema_conferences.rule_proposals rp;
COMMENT ON VIEW schema_conferences.change_rules IS 'Предложения по изменению праавил';
