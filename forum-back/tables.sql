drop table if exists Apply;
drop table if exists Approve;
drop table if exists Comment;
drop table if exists MessageBoard;
drop table if exists MyFile;
drop table if exists Plate;
drop table if exists Post;
drop table if exists ReplyComment;
drop table if exists User;

create table if not exists User
(
    u_id       int primary key auto_increment,
    u_name     varchar(20) not null unique,
    u_pass     varchar(16) not null,
    u_grade    tinyint,
    u_gender   tinyint,
    u_birthday datetime,
    avatar     varchar(100),
    available  bit
);

create table if not exists Plate
(
    p_id   int primary key auto_increment,
    p_name varchar(20),
    u_id   int
);

create table if not exists Post
(
    post_id         int primary key auto_increment,
    u_id            int,
    plate_id        int,
    post_name       varchar(100),
    create_datetime datetime,
    recycle         bit,
    content         text,
    update_datetime datetime
);

create table if not exists Approve
(
    u_id    int,
    post_id int
);

create table if not exists Comment
(
    c_id             int primary key auto_increment,
    text             text,
    post_id          int,
    u_id             int,
    publish_datetime datetime
);

create table if not exists ReplyComment
(
    rc_id            int primary key auto_increment,
    text             text,
    u_id             int,
    c_id             int,
    publish_datetime datetime
);

create table if not exists MyFile
(
    f_id int primary key auto_increment,
    name varchar(100),
    path varchar(100),
    u_id int
);

create table if not exists MessageBoard
(
    m_id      int primary key auto_increment,
    u_id      int,
    sender_id int,
    message   varchar(150)
);

create table if not exists Apply
(
    a_id    int primary key auto_increment,
    u_id    int,
    text    text,
    dispose bit
);