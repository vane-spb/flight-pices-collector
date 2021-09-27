CREATE TABLE IF NOT EXISTS destinations_for_skyscanner
(
    id int primary key,
    origin_place varchar(5),
    destination_place varchar(5),
    date date,
    country varchar(5),
    currency varchar(5)
);

CREATE TABLE IF NOT EXISTS skyscanner_log
(
    id serial primary key,
    flight_price real,
    is_direct bool,
    departure_date timestamp,
    carrier_id int,
    carrier_name varchar(100),
    departure_place_id int,
    departure_place_iata_code varchar(5),
    departure_place_name varchar(100),
    departure_city_name varchar(100),
    destination_place_id int,
    destination_place_iata_code varchar(5),
    destination_place_name varchar(100),
    destination_city_name varchar(100),
    quote_date_time timestamp,
    request_time timestamp
);