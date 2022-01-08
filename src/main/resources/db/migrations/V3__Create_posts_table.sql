CREATE SEQUENCE post_id_seq;

CREATE TABLE IF NOT EXISTS post_comments (
     id              uuid primary key,
     message         text not null,
     upvotes         bigint default 0,
     downvotes       bigint default 0,
     owner_name      varchar not null,
     owner_key       uuid references users_info(id),
     created_date    TIMESTAMPTZ NOT NULL DEFAULT now(),
     update_date     TIMESTAMPTZ
);

CREATE TABLE IF NOT EXISTS posts (
    id                  bigint primary key default nextval('post_id_seq'),
    text                text not null,
    encodedImg          text,
    tags                text[],
    city                varchar,
    neighborhood        varchar,
    upvotes             bigint default 0,
    downvotes           bigint default 0,
    user_id             uuid references users_info(id),
    comments_reference  uuid references post_comments(id),
    created_date        TIMESTAMPTZ NOT NULL DEFAULT now(),
    update_date         TIMESTAMPTZ
);

