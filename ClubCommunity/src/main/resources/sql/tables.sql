create table users(
     id 		    bigint 		not null auto_increment unique primary key,
     account_id     varchar(16)	not null unique,
     password 	    varchar(16)	not null,
     nick_name 	    varchar(12)	not null unique,
     created_at     timestamp 	default now(),
     updated_at     timestamp 	default now()
);
create table clubs(
     id			    bigint		not null auto_increment unique primary key,
     club_name	    varchar(12)	not null unique,
     member_num	    int			default	0,
     created_at	    timestamp	default now(),
     updated_at	    timestamp	default now()
);
create table user_club(
     id     	    bigint		 not null auto_increment unique primary key,
     user_id 	    bigint		 not null,
     club_id 	    bigint		 not null,
     created_at     timestamp	 default now(),
     updated_at     timestamp	 default now()
);
create table posts(
     id			    bigint		 not null auto_increment unique primary key,
     club_id	    bigint		 not null,
     title		    varchar(100) not null,
     content	    text,
     writer_id	    bigint		 not null,
     comment_num    int			 default 0,
     hit			int			 default 0,
     created_at	    timestamp	 default now(),
     updated_at	    timestamp	 default now()
);
create table comments(
     id			    bigint		 not null auto_increment unique primary key,
     post_id 	    bigint		 not null,
     content 	    text		 not null,
     writer_id	    bigint		 not null,
     created_at 	timestamp	 default now(),
     updated_at 	timestamp	 default now()
);