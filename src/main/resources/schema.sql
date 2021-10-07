create table horse
(
    id       serial
        constraint horse_pkey
            primary key,
    bet      boolean,
    horse_id integer,
    place    integer,
    time     integer,
    race_id  integer
        constraint fkkxaxopadajphicfpjterk0xa5
            references race
);

alter table horse
    owner to keezil;

create table race
(
    id   serial
        constraint race_pkey
            primary key,
    date varchar(255)
);

alter table race
    owner to keezil;

create table race_horses
(
    race_id  integer not null
        constraint fk44njbbxy8phlkig40w2838n74
            references race,
    horse_id integer not null
        constraint uk_btw51nx4fksl2bdcbjanfce0x
            unique
        constraint fki8s32qvyegn9hgs78wenn03up
            references horse
);

alter table race_horses
    owner to keezil;

