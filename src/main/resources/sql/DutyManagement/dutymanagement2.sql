/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2019/6/27 17:49:30                           */
/*==============================================================*/


drop table if exists duty_bill_state;

drop table if exists duty_incident_bill;

drop table if exists duty_incident_category;

drop table if exists incident_association;

/*==============================================================*/
/* Table: duty_bill_state                                       */
/*==============================================================*/
create table duty_bill_state
(
   id                   int not null,
   gmt_create           datetime not null,
   gmt_modified         datetime not null,
   gmt_isdelete         boolean not null default false,
   name                 varchar(64) not null,
   primary key (id)
);

/*==============================================================*/
/* Table: duty_incident_bill                                    */
/*==============================================================*/
create table duty_incident_bill
(
   id                   int not null,
   gmt_create           datetime not null,
   gmt_modified         datetime not null,
   is_delete            boolean not null default false,
   initiator            int not null,
   category             int not null,
   level                tinyint,
   asset_id             bigint not null,
   start_time           datetime not null,
   cause                varchar(1024) not null,
   impact               varchar(1024),
   processes            varchar(2048),
   finish_time          datetime,
   bill_state           int not null,
   primary key (id)
);

/*==============================================================*/
/* Table: duty_incident_category                                */
/*==============================================================*/
create table duty_incident_category
(
   id                   int not null,
   gmt_create           datetime not null,
   gmt_modified         datetime not null,
   gmt_isdelete         boolean not null default false,
   name                 varchar(64) not null,
   primary key (id)
);

/*==============================================================*/
/* Table: incident_association                                  */
/*==============================================================*/
create table incident_association
(
   id1                  int,
   id2                  int
);

alter table duty_incident_bill add constraint FK_Reference_10 foreign key (category)
      references duty_incident_category (id) on delete restrict on update restrict;

alter table duty_incident_bill add constraint FK_Reference_11 foreign key (initiator)
      references management_user (id) on delete restrict on update restrict;

alter table duty_incident_bill add constraint FK_Reference_12 foreign key (bill_state)
      references duty_bill_state (id) on delete restrict on update restrict;

alter table incident_association add constraint FK_Reference_13 foreign key (id1)
      references duty_incident_bill (id) on delete restrict on update restrict;

alter table incident_association add constraint FK_Reference_14 foreign key (id2)
      references duty_incident_bill (id) on delete restrict on update restrict;

