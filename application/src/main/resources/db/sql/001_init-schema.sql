create table url
(
    id  serial,
    name text not null,
    constraint URLS_PK PRIMARY KEY (id)
);

create table keyword
(
    id   serial,
    name text not null,
    constraint KEYWORDS_PK PRIMARY KEY (id)
);

create table statistic
(
    id serial,
    constraint STATISTIC_PK PRIMARY KEY (id)
);


create table crawl
(
    id             serial,
    url_id         bigint  not null,
    keyword_id     bigint  not null,
    number_of_hits integer not null,
    statistic_id   bigint  not null,
    constraint STATISTIC_ID_FK foreign key (statistic_id) references statistic,
    constraint CRAWL_PK PRIMARY KEY (id),
    constraint KEYWORD_ID_FK foreign key (keyword_id) references keyword,
    constraint URL_ID_FK foreign key (url_id) references url
);