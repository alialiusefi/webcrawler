create or replace function getTopCrawls(IN statisticId bigint, IN limitParam int)
    returns table
            (
                urls             text,
                keywords         text,
                hits_per_keyword text,
                total_hits             bigint
            )
as

$$
    declare
begin
    return QUERY (select distinct (select url.name from url where url.id = outercrawls.url_id)
                                                                                                                     as urls,
                                  (select string_agg(distinct keyword.name, ',')
                                   from keyword
                                            inner join crawl on keyword.id = crawl.keyword_id)                       as keywords,
                                  (select string_agg(cast(s.number_of_hits as text), ',')
                                   from (select number_of_hits from crawl where url_id = outercrawls.url_id) as s)   as hits_per_keyword,

                                  (select sum(sub.number_of_hits) hits
                                   from (select number_of_hits from crawl where url_id = outercrawls.url_id) as sub) as total_hits
                  from crawl as outercrawls
                           inner join statistic s on outercrawls.statistic_id = s.id
                  where statistic_id = statisticId
                  order by total_hits desc
                  limit limitParam);
end;
$$
    LANGUAGE 'plpgsql';

create or replace function getCrawls(IN statisticId bigint)
    returns table
            (
                urls             text,
                keywords         text,
                hits_per_keyword text,
                total_hits             bigint
            )
as
$$
begin
    return QUERY (select distinct (select url.name from url where url.id = outercrawls.url_id)
                                                                                                                     as urls,
                                  (select string_agg(distinct keyword.name, ',')
                                   from keyword
                                            inner join crawl on keyword.id = crawl.keyword_id)                       as keywords,
                                  (select string_agg(cast(s.number_of_hits as text), ',')
                                   from (select number_of_hits from crawl where url_id = outercrawls.url_id) as s)   as hits_per_keyword,

                                  (select sum(sub.number_of_hits) hits
                                   from (select number_of_hits from crawl where url_id = outercrawls.url_id) as sub) as total_hits
                  from crawl as outercrawls
                           inner join statistic s on outercrawls.statistic_id = s.id
                  where statistic_id = statisticId);
end;
$$
    LANGUAGE 'plpgsql';