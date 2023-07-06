
create table roles(
    ID uuid PRIMARY KEY default UUID_GENERATE_V4()::UUID,
    USER_ID uuid,
    PERMISSION VARCHAR(100) not null,
    CONSTRAINT roles_user_id_foreign
        foreign key (USER_ID)
        references users(ID) ON DELETE CASCADE
);

create unique index roles_user_id_role_index on roles(USER_ID, PERMISSION)