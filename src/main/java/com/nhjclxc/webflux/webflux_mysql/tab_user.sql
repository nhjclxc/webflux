CREATE TABLE `tab_user`
(
    `id`   int NOT NULL auto_increment COMMENT '编号',
    `code` varchar(10) DEFAULT NULL COMMENT '用户代码',
    `name` varchar(20) DEFAULT NULL COMMENT '用户姓名',
    `age`  int NOT NULL COMMENT '用户年龄',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='人员表';

INSERT INTO test.tab_user (id, code, name, age) VALUES (1, '1', '孙东', 27);
INSERT INTO test.tab_user (id, code, name, age) VALUES (2, '2', '胡佳鹏', 28);
INSERT INTO test.tab_user (id, code, name, age) VALUES (3, '3', '徐晨杰', 30);
INSERT INTO test.tab_user (id, code, name, age) VALUES (4, '4', '郭晋航', 31);
INSERT INTO test.tab_user (id, code, name, age) VALUES (5, '5', '陈永杰', 32);
INSERT INTO test.tab_user (id, code, name, age) VALUES (6, '6', '史敬源', 33);
INSERT INTO test.tab_user (id, code, name, age) VALUES (7, '7', '邬咪霞', 35);
INSERT INTO test.tab_user (id, code, name, age) VALUES (8, '8', '蒋浩文', 36);
INSERT INTO test.tab_user (id, code, name, age) VALUES (9, '9', '徐旭日', 38);
INSERT INTO test.tab_user (id, code, name, age) VALUES (10, '10', '顾家楷', 40);
INSERT INTO test.tab_user (id, code, name, age) VALUES (11, '11', '陈坚涌', 41);
INSERT INTO test.tab_user (id, code, name, age) VALUES (12, '12', '杜普源', 31);
INSERT INTO test.tab_user (id, code, name, age) VALUES (13, '13', '贾金', 32);
INSERT INTO test.tab_user (id, code, name, age) VALUES (14, '14', '魏敏', 33);
INSERT INTO test.tab_user (id, code, name, age) VALUES (15, '15', '陆文杰', 35);
