# Android_Studio_Campus_office_supplies
安卓Android校园办公用品管理系统可导入Studio毕业源码案例设计
## 开发环境: Myclipse/Eclipse/Idea都可以(服务器端) + Eclipse(手机客户端) + mysql数据库
## 系统客户端和服务器端架构技术: 界面层，业务逻辑层，数据层3层分离技术，MVC设计思想！
## 服务器和客户端数据通信格式： XML格式(用于传输查询的记录集)和json格式(用于传输单个的对象信息) 
对系统界面描述：
Android端：
1.只允许普通用户登录及注册。
2.普通用户在系统中仅有创建，查看，修改申请表，查看办公系统总表，查看领用表的权限。
3.在系统中可以很明确地查看到自己可以进行的操作。
应用服务器端：
1.只允许系统管理员登录及注册
2.系统管理员拥有增删改查办公系统总表，修改查看申请表，增改查领用表，增改查购置表，增删改查部门信息表的权限。
3.在系统中可以很明确地查看到自己可以进行的操作。
## 实体ER属性：
部门: 部门编号,部门名称,部门类别,备注

人员: 人员编号,登录密码,所在部门,姓名,性别,出生日期,联系电话,家庭地址

物品类别: 物品类别id,物品类别名称

办公用品: 物品编号,商品类别,物品名称,物品图片,规格型号,计量单位,库存数量,单价,金额,仓库,备注

物品申请: 申请id,审请的用品,申请数量,申请时间,申请人,经办人,备注

物品购置: 购置id,购置物品,购置价格,购置数量,购置金额,入库时间,经办人,保管人,仓库

物品领用: 领用id,领用物品,领用数量,单价,金额,领用时间,领用人,经办人,仓库
