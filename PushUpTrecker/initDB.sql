CREATE TABLE IF NOT EXISTS push_ups
(
    id_user   BIGSERIAL,
    id_training  BIGSERIAL,
    PRIMARY KEY (id_user, id_training),
    calibration real,
    quantity_per_day int,
    quantity_per_month int,
    quantity_per_year int,
    quantity_per_all_time int
);