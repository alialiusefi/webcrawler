create database webcrawler_db;

\connect webcrawler_db;

create table url
(
    id  serial,
    url text not null,
    constraint URLS_PK PRIMARY KEY (id)
);

create table keyword
(
    id      serial,
    keyword text not null,
    constraint KEYWORDS_PK PRIMARY KEY (id)
);

create table crawl
(
    id             serial,
    url_id         bigint  not null,
    keywords_id    bigint  not null,
    number_of_hits integer not null,
    constraint CRAWL_PK PRIMARY KEY (id),
    constraint KEYWORD_ID_FK foreign key (keywords_id) references keyword,
    constraint URL_ID_FK foreign key (url_id) references url
);

create table statistic
(
    id         serial,
    total_hits timestamp not null,
    constraint STATISTIC_PK PRIMARY KEY (id)
);

create table crawl_statistic
(
    crawl_id     bigint not null,
    statistic_id bigint not null
)