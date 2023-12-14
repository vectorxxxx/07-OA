## 1、建表

```mysql
CREATE DATABASE `oa` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;
use `oa`;

CREATE TABLE `sys_role`
(
   `id`          bigint(20)  NOT NULL AUTO_INCREMENT COMMENT '角色id',
   `role_name`   varchar(20) NOT NULL DEFAULT '' COMMENT '角色名称',
   `role_code`   varchar(20)          DEFAULT NULL COMMENT '角色编码',
   `description` varchar(255)         DEFAULT NULL COMMENT '描述',
   `create_time` timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
   `update_time` timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
   `is_deleted`  tinyint(3)  NOT NULL DEFAULT '0' COMMENT '删除标记（0:不可用 1:可用）',
   PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 9
  DEFAULT CHARSET = utf8 COMMENT ='角色';


CREATE TABLE `sys_user`
(
   `id`          bigint(20)  NOT NULL AUTO_INCREMENT COMMENT '会员id',
   `username`    varchar(20) NOT NULL DEFAULT '' COMMENT '用户名',
   `password`    varchar(32) NOT NULL DEFAULT '' COMMENT '密码',
   `name`        varchar(50)          DEFAULT NULL COMMENT '姓名',
   `phone`       varchar(11)          DEFAULT NULL COMMENT '手机',
   `head_url`    varchar(200)         DEFAULT NULL COMMENT '头像地址',
   `dept_id`     bigint(20)           DEFAULT NULL COMMENT '部门id',
   `post_id`     bigint(20)           DEFAULT NULL COMMENT '岗位id',
   `open_id`     varchar(255)         DEFAULT NULL COMMENT '微信openId',
   `description` varchar(255)         DEFAULT NULL COMMENT '描述',
   `status`      tinyint(3)           DEFAULT NULL COMMENT '状态（1：正常 0：停用）',
   `create_time` timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
   `update_time` timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
   `is_deleted`  tinyint(3)  NOT NULL DEFAULT '0' COMMENT '删除标记（0:不可用 1:可用）',
   PRIMARY KEY (`id`),
   UNIQUE KEY `idx_username` (`username`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 13
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户表';


CREATE TABLE `sys_user_role`
(
   `id`          bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
   `role_id`     bigint(20) NOT NULL DEFAULT '0' COMMENT '角色id',
   `user_id`     bigint(20) NOT NULL DEFAULT '0' COMMENT '用户id',
   `create_time` timestamp  NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
   `update_time` timestamp  NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
   `is_deleted`  tinyint(3) NOT NULL DEFAULT '0' COMMENT '删除标记（0:不可用 1:可用）',
   PRIMARY KEY (`id`),
   KEY `idx_role_id` (`role_id`),
   KEY `idx_admin_id` (`user_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 11
  DEFAULT CHARSET = utf8 COMMENT ='用户角色';


CREATE TABLE `sys_menu`
(
   `id`          bigint(20)  NOT NULL AUTO_INCREMENT COMMENT '编号',
   `parent_id`   bigint(20)  NOT NULL DEFAULT '0' COMMENT '所属上级',
   `name`        varchar(20) NOT NULL DEFAULT '' COMMENT '名称',
   `type`        tinyint(3)  NOT NULL DEFAULT '0' COMMENT '类型(0:目录,1:菜单,2:按钮)',
   `path`        varchar(100)         DEFAULT NULL COMMENT '路由地址',
   `component`   varchar(100)         DEFAULT NULL COMMENT '组件路径',
   `perms`       varchar(100)         DEFAULT NULL COMMENT '权限标识',
   `icon`        varchar(100)         DEFAULT NULL COMMENT '图标',
   `sort_value`  int(11)              DEFAULT NULL COMMENT '排序',
   `status`      tinyint(4)           DEFAULT NULL COMMENT '状态(0:禁止,1:正常)',
   `create_time` timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
   `update_time` timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
   `is_deleted`  tinyint(3)  NOT NULL DEFAULT '0' COMMENT '删除标记（0:不可用 1:可用）',
   PRIMARY KEY (`id`),
   KEY `idx_parent_id` (`parent_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 58
  DEFAULT CHARSET = utf8mb4 COMMENT ='菜单表';


CREATE TABLE `sys_role_menu`
(
   `id`          bigint(20) NOT NULL AUTO_INCREMENT,
   `role_id`     bigint(20) NOT NULL DEFAULT '0',
   `menu_id`     bigint(11) NOT NULL DEFAULT '0',
   `create_time` timestamp  NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
   `update_time` timestamp  NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
   `is_deleted`  tinyint(3) NOT NULL DEFAULT '0' COMMENT '删除标记（0:不可用 1:可用）',
   PRIMARY KEY (`id`),
   KEY `idx_role_id` (`role_id`),
   KEY `idx_menu_id` (`menu_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 33
  DEFAULT CHARSET = utf8 COMMENT ='角色菜单';


CREATE TABLE `oa_process_type`
(
   `id`          bigint(20)  NOT NULL AUTO_INCREMENT COMMENT 'id',
   `name`        varchar(20) NOT NULL DEFAULT '' COMMENT '类型名称',
   `description` varchar(255)         DEFAULT NULL COMMENT '描述',
   `create_time` timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
   `update_time` timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
   `is_deleted`  tinyint(3)  NOT NULL DEFAULT '0' COMMENT '删除标记（0:不可用 1:可用）',
   PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 4
  DEFAULT CHARSET = utf8 COMMENT ='审批类型';

CREATE TABLE `oa_process_template`
(
   `id`                      bigint(20)  NOT NULL AUTO_INCREMENT COMMENT '角色id',
   `name`                    varchar(20) NOT NULL DEFAULT '' COMMENT '模板名称',
   `icon_url`                varchar(100)         DEFAULT NULL COMMENT '图标路径',
   `process_type_id`         varchar(255)         DEFAULT NULL,
   `form_props`              text COMMENT '表单属性',
   `form_options`            text COMMENT '表单选项',
   `process_definition_key`  varchar(20)          DEFAULT NULL COMMENT '流程定义key',
   `process_definition_path` varchar(255)         DEFAULT NULL COMMENT '流程定义上传路径',
   `process_model_id`        varchar(255)         DEFAULT NULL COMMENT '流程定义模型id',
   `description`             varchar(255)         DEFAULT NULL COMMENT '描述',
   `status`                  tinyint(3)  NOT NULL DEFAULT '0',
   `create_time`             timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
   `update_time`             timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
   `is_deleted`              tinyint(3)  NOT NULL DEFAULT '0' COMMENT '删除标记（0:不可用 1:可用）',
   PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 4
  DEFAULT CHARSET = utf8 COMMENT ='审批模板';
```

## 术语

### 1、`ECMA`

`European Computer Manufacturers Association`，欧洲计算机制造商协会

### 2、`var` 和 `let` 的区别

`var` 和 `let` 都是用来声明变量的关键字，它们之间的主要区别在于作用域和变量提升。

1. 作用域
   - 使用 `var` 声明的变量存在变量提升，即在声明之前就可以访问变量，而 `let` 声明的变量只能在声明后才能访问
   - 使用 `var` 声明的变量在全局作用域和函数作用域中都有效，而 `let` 声明的变量只在块作用域内有效。

2. 变量提升
   - 使用 `var` 声明的变量会存在变量提升，即在代码执行前就会被提升到当前作用域的顶部，而 `let` 声明的变量不会存在变量提升。

因此，使用 `var` 声明的变量可能会导致意外的行为，而 `let` 声明的变量更符合预期的作用域规则。在 ES6 中，推荐使用 `let` 来声明变量，以避免变量提升和作用域问题。

### 3、csrf跨站请求伪造和cors跨域有什么区别和关系？

CSRF（跨站请求伪造）和CORS（跨域资源共享）是两个不同的概念，但它们之间存在一些相似之处。

- CSRF（跨站请求伪造）是一种攻击，攻击者通过伪造用户请求来欺骗用户，从而实现恶意目的。CSRF通常发生在用户登录或敏感操作的页面上。攻击者可以通过在请求中添加特定的参数或数据来伪造请求。CSRF攻击的主要目的是获取用户的敏感信息，如用户身份、密码等。

- CORS（跨域资源共享）是一种HTTP响应头，允许服务器在响应中声明允许的源（Origin）和请求方法（Methods）。CORS允许来自不同源的请求，但需要服务器进行验证。CORS的主要目的是允许跨域请求，以支持第三方API、第三方登录等场景。

CSRF和CORS之间存在一些相似之处，因为它们都需要对请求进行验证和授权。但是，CSRF主要关注的是请求的来源和身份，而CORS主要关注的是请求的来源。CSRF通常发生在用户登录或敏感操作的页面上，而CORS通常发生在API接口上。

总结起来，CSRF和CORS都是用来确保请求的安全性和身份验证的，但它们的目标和应用场景不同。CSRF主要关注请求的来源和身份，而CORS主要关注请求的来源。
