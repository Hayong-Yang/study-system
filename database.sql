create database study_system;
use study_system;

CREATE TABLE member (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    name VARCHAR(50) NOT NULL,
    role VARCHAR(20) NOT NULL DEFAULT 'ROLE_USER'
);

create table study(
	id int auto_increment primary key,
    title varchar(500) not null,
    content text not null,
    max_people int not null,
    max_date timestamp not null,
	writer_id int not null,
	created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp on update current_timestamp,
    foreign key (writer_id) references member(id) on delete cascade
);

CREATE TABLE study_application (
    id INT AUTO_INCREMENT PRIMARY KEY,
    member_id INT NOT NULL,
    study_id INT NOT NULL,
    applied_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    UNIQUE KEY unique_apply (member_id, study_id), 

    FOREIGN KEY (member_id) REFERENCES member(id) ON DELETE CASCADE,
    FOREIGN KEY (study_id) REFERENCES study(id) ON DELETE CASCADE
);

select * from member;
select * from study;
select * from study_application;

drop table study;
drop table study_application;
