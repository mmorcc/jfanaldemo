package com.demo;

import com.demo.bean.AddressList;
import com.jfinal.config.*;
import com.jfinal.core.JFinal;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.template.Engine;

import java.util.List;

/**
 * Created by xupf-c on 2018/4/2.
 */

public class DemoConfig extends JFinalConfig {
    /**
     * 运行此 main 方法可以启动项目，此main方法可以放置在任意的Class类定义中，不一定要放于此
     *
     * 使用本方法启动过第一次以后，会在开发工具的 debug、run config 中自动生成
     * 一条启动配置，可对该自动生成的配置再添加额外的配置项，例如 VM argument 可配置为：
     * -XX:PermSize=64M -XX:MaxPermSize=256M
     */
    public static void main(String[] args) {
        /**
         * 特别注意：Eclipse 之下建议的启动方式
         */
        JFinal.start("src/main/webapp", 8081, "/");

        /**
         * 特别注意：IDEA 之下建议的启动方式，仅比 eclipse 之下少了最后一个参数
         */
        // JFinal.start("src/main/webapp", 80, "/");

    }
    @Override
    public void configConstant(Constants me) {
        me.setDevMode(true);
        me.setBaseDownloadPath("D:\\test.txt");
//        me.setBaseUploadPath("D:\\test.txt");
//        me.a
//        me.setViewType(ViewType.JSP);
        PropKit.use("config.properties");

        me.setDevMode(PropKit.getBoolean("devMode", false));
    }

    @Override
    public void configRoute(Routes me) {
        me.add("/", HelloController.class);
        me.add("/file",FileController.class);

    }

    @Override
    public void configEngine(Engine me) {
        me.add(new ContextPathHandler());
    }

    @Override
    public void configPlugin(Plugins me) {
        // 配置 druid 数据库连接池插件
        DruidPlugin druidPlugin = new DruidPlugin(PropKit.get("jdbcUrl"), PropKit.get("user"), PropKit.get("password").trim());
        me.add(druidPlugin);

        // 配置ActiveRecord插件
        ActiveRecordPlugin arp = new ActiveRecordPlugin(druidPlugin);
        // 所有映射在 MappingKit 中自动化搞定
        arp.addMapping("addressList", "id", AddressList.class);
        me.add(arp);

        // 配置 druid 数据库连接池插件
//        DruidPlugin druidPlugin = new DruidPlugin(PropKit.get("jdbcUrl"), PropKit.get("user"), PropKit.get("password").trim());
//        me.add(druidPlugin);

        // 配置ActiveRecord插件
//        ActiveRecordPlugin arp = new ActiveRecordPlugin(druidPlugin);
//        // 所有映射在 MappingKit 中自动化搞定
//        _MappingKit.mapping(arp);
//        me.add(arp);

//        C3p0Plugin c3p0Plugin = new C3p0Plugin(PropKit.get("jdbcUrl"),PropKit.get("username"),PropKit.get("password"),PropKit.get("driver"));
//        ActiveRecordPlugin arp = new ActiveRecordPlugin(c3p0Plugin);
//        arp.setShowSql(true);
//        arp.addMapping("addresslist","id", AddressList.class);
//        me.add(c3p0Plugin);
//        me.add(arp);
//        arp.setDialect(new PostgreSqlDialect());
    }

    @Override
    public void configInterceptor(Interceptors me) {

    }

    @Override
    public void configHandler(Handlers me) {

    }
    public void save() {
        String likeStr = "select * from addresslist";
        List<AddressList> addressList=AddressList.dao.find(likeStr);
        System.out.println("addressList.l="+addressList.size());
        // 用户删除
//        User.dao.deleteById(4);

        // 用户添加
//        new User().set("title", "宝华"+new Random().nextInt(100))
//                .set("content", "123")
////                .set("user_class_id", 1)
//                .save();
//
////        renderText("用户添加成功...");用户添加成功
//        // 用户查询
//        User user = User.dao.findById(2);
////        renderText(user.getStr("user_name"));
//        // 用户修改
//        User.dao.findById(1).set("title","张无忌").update();
//        // 查询语句
//        String likeStr = "select * from blog where title like '%宝%'";
//        List<User> userList = User.dao.find(likeStr);
//        String renderStr = "";
//        for (User user2 : userList) {
//            renderStr += user2.getStr("title");
//            renderStr += "---";
//        }
////        renderText(renderStr);
//        // 分页查询语句 参数列表（起始页，每页条数，查询字段，表和条件《使用占位符》，"设置占位符"）
//        Page<User> userPage = User.dao.paginate(1, 8,
//                "select *", "from blog where title like ?", "%华%");
//
//        renderStr = "";
//        for (User user2 : userPage.getList()) {
//            renderStr += "-->";
//            renderStr += user2.getStr("user_name")+"\n";
//        }
//        renderText("总页数："+userPage.getTotalPage()+"\t共有："+userPage.getTotalRow()+"条数据"
//                +"\t共有："+userPage.getTotalPage()+"页"+"\t当前页："+userPage.getPageNumber()
//                +"\n"+renderStr);

    }
}
