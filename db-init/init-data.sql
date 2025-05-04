create table if not exists author
(
    id       bigint generated always as identity
    primary key,
    name     varchar not null,
    surname  varchar not null,
    nickname varchar not null
    constraint unique_author_nickname
    unique
);

create table if not exists post
(
    id            bigint generated always as identity
    primary key,
    title         varchar                  not null,
    hash_url      varchar(8)               not null,
    author        varchar                  not null
    constraint key_for_author
    references author (nickname)
    on delete cascade,
    version       integer                  not null,
    deleted_after timestamp with time zone not null,
    created_at    timestamp with time zone not null
        );

create table if not exists roles
(
    id        integer generated always as identity
    primary key,
    authority varchar not null
);

create table if not exists user_roles
(
    id      integer generated always as identity
    primary key,
    user_id integer,
    role_id integer
);

create table if not exists "user"
(
    id             integer generated always as identity
    primary key,
    email          varchar not null,
    password       varchar not null,
    roles          varchar,
    token_jwt      text,
    code_challenge text,
    access_code    varchar
);

insert into public.author (name, surname, nickname)
values  ('John', 'Doe', 'johndoe'),
        ('Jane', 'Doe', 'janedoe'),
        ('Peter', 'Jones', 'peterjones'),
        ('string', 'string', 'string'),
        ('Зоров', 'Максим', 'max');

insert into public.post (title, hash_url, author, version, deleted_after, created_at)
values  ('Post 1', '12345678', 'johndoe', 1, '2024-12-15 00:00:00.000000 +00:00', '2024-12-08 00:00:00.000000 +00:00'),
        ('Post 2', '87654321', 'janedoe', 1, '2024-12-28 00:00:00.000000 +00:00', '2024-12-09 00:00:00.000000 +00:00'),
        ('Post 3', '13579246', 'peterjones', 1, '2024-12-31 00:00:00.000000 +00:00', '2024-12-10 00:00:00.000000 +00:00'),
        ('verere', 'c673gVro', 'string', 0, '2024-12-23 14:00:33.533785 +00:00', '2024-12-18 14:00:33.533785 +00:00'),
        ('Test Title', 'cQQDNBRQ', 'johndoe', 0, '2024-12-21 12:31:52.585344 +00:00', '2024-12-20 12:32:18.720032 +00:00'),
        ('Тест', 'K3dmjIAq', 'max', 0, '2024-12-24 09:40:44.986214 +00:00', '2024-12-23 09:40:44.986214 +00:00'),
        ('Тест', 'phxhQN3r', 'max', 0, '2024-12-24 09:56:20.562917 +00:00', '2024-12-23 09:56:20.562917 +00:00'),
        ('Тест', 'I/40GBDd', 'max', 0, '2024-12-24 09:59:36.399806 +00:00', '2024-12-23 09:59:36.399806 +00:00'),
        ('Тест', '5kTsj/Z7', 'max', 0, '2024-12-24 10:06:34.901936 +00:00', '2024-12-23 10:06:34.901936 +00:00'),
        ( 'Тест', 'bGUK2AZq', 'max', 0, '2024-12-24 10:13:42.748823 +00:00', '2024-12-23 10:13:42.748823 +00:00'),
        ( 'Тест', 'Q2TO39ji', 'max', 0, '2024-12-24 10:15:08.522742 +00:00', '2024-12-23 10:15:08.522742 +00:00'),
        ( 'Тест', 'r+YcW6sx', 'max', 0, '2024-12-24 10:16:39.769194 +00:00', '2024-12-23 10:16:39.769194 +00:00'),
        ( 'Тест', 'ZWlGRFe/', 'max', 0, '2024-12-24 10:19:05.833641 +00:00', '2024-12-23 10:19:05.833641 +00:00'),
        ( 'Тест', 'u2Uwj8vM', 'max', 0, '2024-12-24 10:21:38.314836 +00:00', '2024-12-23 10:21:38.314836 +00:00'),
        ( 'Тест', '3h1zDByZ', 'max', 0, '2024-12-24 10:22:34.674702 +00:00', '2024-12-23 10:22:34.674702 +00:00'),
        ( 'Тест', 'repF25XU', 'max', 0, '2024-12-24 10:28:05.351531 +00:00', '2024-12-23 10:28:05.351531 +00:00'),
        ( 'Тест', 'YgP7bHIX', 'max', 0, '2024-12-24 11:02:44.709481 +00:00', '2024-12-23 11:02:44.709481 +00:00'),
        ('Сибинтек', '0ozIFcua', 'max', 0, '2024-12-24 16:40:18.071102 +00:00', '2024-12-23 16:40:18.071102 +00:00'),
        ('Сибинтек', 'awen41zI', 'max', 0, '2024-12-24 16:45:31.082372 +00:00', '2024-12-23 16:45:31.082372 +00:00'),
        ('Сибинтек', '7PM4PEeS', 'max', 0, '2024-12-24 16:55:38.869965 +00:00', '2024-12-23 16:55:38.869965 +00:00'),
        ('Сибинтек', 'cE1gHAcL', 'max', 0, '2024-12-24 17:00:01.821138 +00:00', '2024-12-23 17:00:01.821138 +00:00'),
        ('Сибинтек', 'gNTJBDLt', 'max', 0, '2024-12-24 17:01:19.747085 +00:00', '2024-12-23 17:01:19.747085 +00:00'),
        ('Сибинтек', 'LnYA5jpI', 'max', 0, '2024-12-24 17:03:28.075093 +00:00', '2024-12-23 17:03:28.076101 +00:00'),
        ('Сибинтек', 'zYpuRjkW', 'max', 0, '2024-12-25 08:16:43.853019 +00:00', '2024-12-24 08:16:43.853019 +00:00'),
        ('Сибинтек', 'A7zwQQ7f', 'max', 0, '2024-12-25 08:17:30.363482 +00:00', '2024-12-24 08:17:30.363482 +00:00'),
        ('МИРЭА', '5xPtVMw8', 'max', 0, '2024-12-25 08:28:37.228559 +00:00', '2024-12-24 08:28:37.228559 +00:00'),
        ('hgg', 'zeN8VVSG', 'max', 0, '2025-03-16 08:52:23.405040 +00:00', '2025-03-15 08:52:23.405040 +00:00'),
        ('Test Title', 'tIPqw2h/', 'johndoe', 0, '2025-05-02 11:02:08.346055 +00:00', '2025-05-01 11:02:08.355058 +00:00'),
        ('Test Title', 'cIQCvFtV', 'johndoe', 0, '2025-05-02 11:03:43.316028 +00:00', '2025-05-01 11:03:43.325034 +00:00'),
        ('Test Title', 'Ao0QQEp+', 'johndoe', 0, '2025-05-02 11:04:51.011632 +00:00', '2025-05-01 11:04:51.021629 +00:00');

insert into public.roles (authority)
values  ('USER'),
        ('ADMIN');

insert into public.user (email, password, roles, token_jwt, code_challenge, access_code)
values  ('test1234@mail.ru', '$2a$10$E61/m79dJos01ZVbR.pBXecq9dhi7XWN8/9xTUmvWueBL/Ge.3f0O', 'ROLE_USER', 'eyJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJzZWxmIiwic3ViIjoidGVzdDEyMzRAbWFpbC5ydSIsImV4cCI6MTc0MDY0MTYwNiwicm9sZXMiOiJVU0VSIn0.fIs8OK2phC9DU5wk3HG5YRbL0wbdD4p7-it8xyLmF2HHlNruLSwMJ-YvBQbgpvmUKGQ8MW6hUB12CkB9vcgKKZcVn_A0Okeht6gN3Ln5dC3_ZOEqCzr8NLQm3WXJSDj0UeIzh9RsGSxMmxqthNKOr-tx40OA65uWXI4IWFgkBog0q4akem8MPp2qTgNHF_U5Na81roPocRLln9cF2O5GDa39CceONP3998ewC0lNbm3CySxeZnJZH9ySEMU_-gI2gs5cVIJ4WpDrF19fpBGCTiC1LvCN9--ycdMaJ640RLr_-mOjBXe4VUPbPr_zaNQPm0Q8dy1BBchYqXNFrse5tg', 'a/cWAWAW+so6MnjO789XqKEVSF9M2wOcwppbRWAlxQ8', 'uJu4IBgg0r4AoxBs-HXcj16aFnwMGCq4ysT1JK2k_2s'),
        ('helpy', '$2a$10$NfTtutbhLN3OktLzeMXRVeO4x3L7/dTnXopWZCgvtpN.Qk.xegdbm', 'ROLE_ADMIN', 'eyJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJzZWxmIiwic3ViIjoiaGVscHkiLCJleHAiOjE3NDMzNTY5MjAsInJvbGVzIjoiQURNSU4ifQ.DxVOOW5x4HPLS9PlBaKwg2qo7cFFYko1dHVl-qu7LYZL6dGenIDAMy7gdKtvYJcxQUemuE70g26U2uobxhLtdDmx2qUcFngoFdv3uIOZRd01N89U_uUdlR5d8RMFJnprAC69UwxrQLd88h7Ur5F0ULVC64vTxq4_u6psr-jKOMs8cACb3hGf8x_jybX44NJb8Lf4rxG8_HDTcmc3nE3NFYjV722Mqa_l-obkOrOBi1bfMHktLjHHw8T6M53aRlCX5gmy0VQ-SrNJcaTlbAQeaxzkTL9ciQ4KOtNc1vTxIMRlwc9elwJs0Qa_dYStMoVzvGMCWbZlTfteSr56iaf3FA', '590B/OZ0LU6eK5DyqXjbuJodyo+FFzHBmHfN76uXvao', 'XrXBXFNjJHMNaMKN-qGuxZ_XgjYgTyPyOOreAFBmDMA');

insert into public.user_roles (user_id, role_id)
values  (1, 1),
        (2, 1);