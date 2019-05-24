/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2019/5/24 14:40:37                           */
/*==============================================================*/


drop table if exists duty_record;

drop table if exists modified_user_association;

/*==============================================================*/
/* Table: duty_record                                           */
/*==============================================================*/
create table duty_record
(
   id                   int not null auto_increment,
   uid                  int,
   gmt_create           datetime not null,
   gmt_modified         datetime not null,
   content              varchar(2000),
   primary key (id)
);

/*==============================================================*/
/* Table: modified_user_association                             */
/*==============================================================*/
create table modified_user_association
(
   record_id            int not null,
   user_id              int not null,
   primary key (record_id, user_id)
);

alter table duty_record add constraint FK_author_user_association foreign key (uid)
      references management_user (id) on delete restrict on update restrict;

alter table modified_user_association add constraint FK_modified_user_association foreign key (record_id)
      references duty_record (id) on delete restrict on update restrict;

alter table modified_user_association add constraint FK_modified_user_association2 foreign key (user_id)
      references management_user (id) on delete restrict on update restrict;