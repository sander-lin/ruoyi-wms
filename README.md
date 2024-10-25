## 平台简介

[![License](https://img.shields.io/badge/License-MIT-blue.svg)](https://gitee.com/zccbbg/ruoyi-fast-service/blob/master/LICENSE)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.1-blue.svg)]()
[![JDK-17](https://img.shields.io/badge/JDK-17-green.svg)]()

> 本项目基于ruoyi-vue-fast实现

> 项目代码、文档 均开源免费可商用 遵循开源协议在项目中保留开源协议文件即可<br>
活到老写到老 为兴趣而开源 为学习而开源 为让大家真正可以学到技术而开源

若依wms是一套基于若依的wms仓库管理系统，支持lodop和网页打印入库单、出库单。毫无保留给个人及企业免费使用。
* 前端采用Vue、Element UI。
* 后端采用Spring Boot、Spring Security、Redis & Jwt。
* 权限认证使用Jwt，支持多终端认证系统。
* 支持加载动态权限菜单，多方式轻松权限控制。
* 高效率开发，使用代码生成器可以一键生成前后端代码。


## 若依wms功能
1. 首页：库存预警与到期提醒、基础数据报表展示
2. 仓库/库区：管理维护仓库基础数据
3. 物料：管理维护物料基础数据
4. 客户/供应商：管理维护联系人基础数据
5. 入库：创建入库单后包括如下几个状态：暂存、作废、完成入库，入库类型包括：采购入库、外协入库、退货入库，入库单支持网页打印
6. 出库：创建出库单后包括如下几个状态：暂存、作废、完成出库，入库类型包括：销售出库、外协出库、调拨出库，出库单支持网页打印
7. 库存看板：查看当前物料库存数量，分仓库、库区、商品三个维度
8. 库存记录：查看当前物料库存操作记录
9. 库存明细：存放明细，分：仓库库区、商品两个维度

## 演示图
![入库单](docs/入库单.jpg)
![编辑入库单](docs/编辑入库单.jpg)
![打印](docs/打印.jpg)
![库存盘点](docs/库存盘点.jpg)
![库存统计](docs/库存统计.jpg)
![库存明细](docs/库存明细.jpg)
![库存记录](docs/库存记录.jpg)
