CREATE table plateau_details (
    id NUMBER GENERATED AS IDENTITY,
    max_x_coord NUMBER,
    max_y_coord NUMBER,
    rover_id VARCHAR2(50),
    max_rover_num NUMBER,
    primary key (id)
);
CREATE table rover_details (
    id NUMBER GENERATED AS IDENTITY,
    plateau_ref VARCHAR2(10),
    x_coord NUMBER,
    y_coord NUMBER,
    facing VARCHAR2(1),
    motion_command VARCHAR2(100),
    move_type VARCHAR2(4),
    primary key (id)
);