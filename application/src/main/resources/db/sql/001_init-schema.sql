/*create database webcrawler_db;

\connect webcrawler_db;
*/
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

create table statistic
(
    id         serial,
    total_hits integer default 0,
    constraint STATISTIC_PK PRIMARY KEY (id)
);


create table crawl
(
    id             serial,
    url_id         bigint  not null,
    keyword_id    bigint  not null,
    number_of_hits integer not null,
    statistic_id bigint not null,
    constraint STATISTIC_ID_FK foreign key (statistic_id) references statistic,
    constraint CRAWL_PK PRIMARY KEY (id),
    constraint KEYWORD_ID_FK foreign key (keyword_id) references keyword,
    constraint URL_ID_FK foreign key (url_id) references url
);


/*create table crawl_statistic
(
    crawl_id     bigint not null,

    constraint CRAWL_ID_FK foreign key (crawl_id) references crawl
)*/