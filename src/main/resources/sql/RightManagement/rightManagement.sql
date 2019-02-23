/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2019/2/21 15:51:58                           */
/*==============================================================*/


drop table if exists management_action;

drop table if exists management_role;

drop table if exists management_user;

drop table if exists role_action_association;

drop table if exists user_role_association;

/*==============================================================*/
/* Table: management_action                                     */
/*==============================================================*/
create table management_action
(
   name                 varchar(20) not null,
   sort                 int not null default 999,
   remark               varchar(100),
   gmt_create           datetime not null,
   gmt_modified         datetime not null,
   parent_id            int not null default 0,
   url                  varchar(255) not null UNIQUE,
   area_name            varchar(32),
   method_name          varchar(32),
   controller_name      varchar(32),
   js_function_name     varchar(32),
   type_enum            int not null,
   menu_icon            varchar(32) not null,
   icon_width           int not null,
   icon_height          int not null,
   icon_cls             varchar(32),
   icon_class_name      varchar(32),
   is_show              bool not null default true,
   method_type_enum     int not null,
   id                   int not null AUTO_INCREMENT,
   primary key (id)
);

/*==============================================================*/
/* Table: management_role                                       */
/*==============================================================*/
create table management_role
(
   name                 varchar(20) not null,
   remark               varchar(100),
   sort                 int not null default 999,
   gmt_create           datetime not null,
   gmt_modified         datetime not null,
   id                   int not null AUTO_INCREMENT,
   primary key (id)
);

/*==============================================================*/
/* Table: management_user                                       */
/*==============================================================*/
create table management_user
(
   account              varchar(20) not null UNIQUE ,
   password             varchar(20) not null,
   is_delete            bool not null,
   sort                 int not null default 999,
   remark               varchar(100),
   gmt_create           datetime not null,
   gmt_modified         datetime not null,
   name                 varchar(20) not null,
   id                   int not null AUTO_INCREMENT,
   primary key (id)
);

/*==============================================================*/
/* Table: role_action_association                               */
/*==============================================================*/
create table role_action_association
(
   role_id              int not null,
   action_id            int not null,
   primary key (role_id, action_id)
);

/*==============================================================*/
/* Table: user_role_association                                 */
/*==============================================================*/
create table user_role_association
(
   use_id               int not null,
   rol_id               int not null,
   primary key (use_id, rol_id)
);

alter table role_action_association add constraint FK_role_action_association foreign key (role_id)
      references management_role (id) on delete restrict on update restrict;

alter table role_action_association add constraint FK_role_action_association2 foreign key (action_id)
      references management_action (id) on delete restrict on update restrict;

alter table user_role_association add constraint FK_user_role_association foreign key (use_id)
      references management_user (id) on delete restrict on update restrict;

alter table user_role_association add constraint FK_user_role_association2 foreign key (rol_id)
      references management_role (id) on delete restrict on update restrict;

