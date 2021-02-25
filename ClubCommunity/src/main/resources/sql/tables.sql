# 모든 테이블이 생성 시간, 수정 시간 그리고 soft delete를 위한 삭제 여부를 가지고 있다.
create database if not exists club_community;
drop table if exists club_community.users;
# 사용자 테이블 : 계정, 비밀번호, 닉네임을 입력받는다.
# 계정과 별명은 중복이 불가능하다.
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
# 소모임 테이블 : 소모임 이름과 소개를 입력받는다.
# 소모임을 만든 사용자의 id와 가입된 사용자 수를 유지한다.
create table club_community.clubs(
    id              bigint      not null auto_increment unique primary key,
    owner_id        bigint      not null,
    club_name       varchar(12)	not null unique,
    introduce       varchar(100),
    member_num      int         default	0,
    created_at      timestamp   default now(),
    updated_at      timestamp   default now(),
    deleted         bool        default false
);
drop table if exists club_community.user_club;
# 사용자-소모임 N:M 매핑 테이블
# 사용자가 가입한 소모임을 의미한다.
# 소모임이 삭제되도 정보는 유지된다.
create table club_community.user_club(
    id              bigint      not null auto_increment unique primary key,
    user_id         bigint      not null,
    club_id         bigint      not null,
    created_at      timestamp   default now(),
    updated_at      timestamp   default now(),
    deleted         bool        default false,
    unique(user_id, club_id)
);
drop table if exists club_community.boards;
# 게시판 테이블
# 기본적으로 자유 게시판이 존재하고 소모임이 추가될 때 마다 그 소모임에 해당하는 게시판이 추가된다.
# club_id가 null 이면 모든 사용자가 열람 가능하다.
# 소모임이 추가될때 추가되고 삭제될 때 삭제된다.
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
# 게시글 테이블 : 제목, 내용을 입력받는다.
# 게시판 id, 작성자 id, 댓글 수, 조회수를 유지한다.
# 조회수는 별도의 검사 없이 게시글을 열람할 때 마다 올라간다.
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
# 댓글 테이블 : 내용을 입력받는다.
# 게시글 id, 작성자 id를 유지한다.
# 대댓글은 허용하지 않는다.
create table club_community.comments(
    id              bigint      not null auto_increment unique primary key,
    post_id         bigint      not null,
    content         text        not null,
    writer_id       bigint      not null,
    created_at      timestamp   default now(),
    updated_at      timestamp   default now(),
    deleted         bool        default false
);