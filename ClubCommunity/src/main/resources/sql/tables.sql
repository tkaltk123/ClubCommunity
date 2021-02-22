create database if not exists club_community;
drop table if exists club_community.users;
create table club_community.users(
    id              bigint      not null auto_increment unique primary key,
    account_id      varchar(16)	not null unique,
    password        varchar(100)not null,
    nick_name       varchar(12)	not null unique,
    created_at      timestamp   default now(),
    updated_at      timestamp   default now(),
    deleted         bool        default false
);
drop table if exists club_community.clubs;
create table club_community.clubs(
    id              bigint      not null auto_increment unique primary key,
    club_name       varchar(12)	not null unique,
    introduce       varchar(100),
    member_num      int         default	0,
    created_at      timestamp   default now(),
    updated_at      timestamp   default now(),
    deleted         bool        default false
);
drop table if exists club_community.user_club;
create table club_community.user_club(
    id              bigint      not null auto_increment unique primary key,
    user_id         bigint      not null,
    club_id         bigint      not null,
    created_at      timestamp   default now(),
    updated_at      timestamp   default now(),
    deleted         bool        default false
);
drop table if exists club_community.boards;
create table club_community.boards(
    id              bigint      not null auto_increment unique primary key,
    club_id         bigint      default null,
    board_name      varchar(16) not null unique,
    created_at      timestamp   default now(),
    updated_at      timestamp   default now(),
    deleted         bool        default false
);
insert into club_community.boards(board_name) values('자유 게시판');
drop table if exists club_community.posts;
create table club_community.posts(
    id              bigint      not null auto_increment unique primary key,
    board_id        bigint      not null,
    title           varchar(100)not null,
    content         text,
    writer_id       bigint      not null,
    comment_num     int         default 0,
    hit             int         default 0,
    created_at      timestamp   default now(),
    updated_at      timestamp   default now(),
    deleted         bool        default false
);
drop table if exists club_community.comments;
create table club_community.comments(
    id              bigint      not null auto_increment unique primary key,
    post_id         bigint      not null,
    content         text        not null,
    writer_id       bigint      not null,
    created_at      timestamp   default now(),
    updated_at      timestamp   default now(),
    deleted         bool        default false
);