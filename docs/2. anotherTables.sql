-- AUTHOR: 0tak2.code@gmail.com
-- Should be executed after '1. bookBase.sql' executed.

USE liverary;

CREATE TABLE `accountsTBL` ( -- 계정 정보 테이블
	`ano`	int	NOT NULL AUTO_INCREMENT PRIMARY KEY, -- 계정 등록번호
	`aname`	varchar(8)	NOT NULL, -- 이름
	`adepartment`	varchar(16)	NULL, -- 부서(직원 계정인 경우)
	`abirth`	date	NOT NULL, -- 생년월일
	`acreatedAt`	date	NOT NULL, -- 입사일/가입일
	`aphone`	char(16)	NOT NULL, -- 연락처
	`aemail`	varchar(36)	NOT NULL, -- 이메일
	`aaddr`	varchar(256)	NOT NULL, -- 거주지
	`apoint`	int	NOT NULL, -- 포인트
	`alevel`	tinyint	NOT NULL, -- 시스템 권한 (0: 이용자 1~2: 직원)
	`ausername`	varchar(16)	NOT NULL UNIQUE, -- 접속 아이디
	`apassword`	varchar(24)	NOT NULL -- 접속 비밀번호
);

CREATE TABLE `loanRecordTBL` ( -- 대출 이력 테이블
	`lno`	int	NOT NULL AUTO_INCREMENT PRIMARY KEY, -- 대출이력 등록번호
	`bisbn`	char(50)	NOT NULL, -- 책 ISBN (FK)
	`ano`	int	NOT NULL, -- 계정 등록번호 (FK)
	`lcreatedAt`	date	NOT NULL, -- 대출 일자
	`ldueDate`	date	NOT NULL, -- 반납 기일 (예정반납일)
	`lreturnedAt`	date	NULL, -- 실제 반납일자. null이면 반납 안됨
	FOREIGN KEY(bisbn) REFERENCES booksTBL(bisbn),
	FOREIGN KEY(ano) REFERENCES accountsTBL(ano)
);

-- accountsTBL INSERT
INSERT INTO `accountsTBL` VALUES ('1', '관리자', '시스템팀',
							'1998-04-08', '2023-01-02', '010-1234-1234',
							'admin@admin.com', '서울 마포구', 0,
							2, 'admin', 'admin');
INSERT INTO `accountsTBL` VALUES ('2', '임영택', null,
							'1998-04-08', '2023-01-02', '010-1234-1234',
							'admin@admin.com', '서울 마포구', 0,
							0, 'limo', '980408');
