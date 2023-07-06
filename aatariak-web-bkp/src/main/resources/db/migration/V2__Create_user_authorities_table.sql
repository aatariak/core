
create table user_authorities(
    ID uuid PRIMARY KEY default UUID_GENERATE_V4()::UUID,
    USER_ID uuid,
    AUTHORITY VARCHAR(100) not null,
    CONSTRAINT user_authorities_user_id_foreign
        foreign key (USER_ID)
        references users(ID) ON DELETE CASCADE
);

create unique index user_authorities_user_id_authority_index on user_authorities(USER_ID, AUTHORITY)