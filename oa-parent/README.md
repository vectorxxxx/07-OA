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
