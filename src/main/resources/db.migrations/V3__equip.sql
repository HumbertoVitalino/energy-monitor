CREATE SEQUENCE EQUIPMENT_SEQ START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE EQUIPMENT_LOG_SEQ START WITH 1 INCREMENT BY 1;

CREATE TABLE tbl_equipments (
    id BIGINT PRIMARY KEY DEFAULT NEXTVAL('EQUIPMENT_SEQ'),
    name VARCHAR(100) NOT NULL,
    active BOOLEAN DEFAULT false,
    consumption_per_hour DOUBLE PRECISION NOT NULL,
    max_active_hours INT NOT NULL,
    last_activated_at TIMESTAMP,
    sector_id BIGINT NOT NULL REFERENCES tbl_sector(id),
    user_id BIGINT REFERENCES tbl_user(id)
);

CREATE TABLE tbl_equipment_usage_log (
    id BIGINT PRIMARY KEY DEFAULT NEXTVAL('EQUIPMENT_LOG_SEQ'),
    equipment_id BIGINT NOT NULL REFERENCES tbl_equipments(id),
    started_at TIMESTAMP NOT NULL,
    ended_at TIMESTAMP NOT NULL,
    estimated_consumption DOUBLE PRECISION
);
