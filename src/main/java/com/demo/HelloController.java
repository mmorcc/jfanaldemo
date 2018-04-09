package com.demo;

import com.demo.bean.AddressList;
import com.jfinal.core.Controller;
import com.jfinal.kit.PropKit;
import com.jfinal.upload.UploadFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import static com.sun.org.apache.xalan.internal.utils.SecuritySupport.getResourceAsStream;

/**
 * Created by zhangzh on 2017/6/19.
 */
public class HelloController extends Controller {

    public void index(){
//        this.renderHtml("success,<a href=\"./\">back</a>");
        this.renderHtml("success,<a href=\"./\">back</a>");
//    this.render("index.html");
        String name=getPara("name");
        System.out.println("name1="+name);
//        renderFile("/test.txt");
//        downfile();
//        uploadFile();
//       getResponse().geWriter().print("");
    }
    //增加，post和get方法
public void add(){
    List<AddressList> list = AddressList.dao.find("select * from addresslist where id=(select max(id) from addresslist)");
    int maxId=0;
    if(list!=null&&list.size()>0){
        Object id = list.get(list.size() - 1).get("id");
        Integer idInt=Integer.parseInt(String.valueOf(id));
        maxId=idInt+1;
    }
    String id= getPara(""+maxId);
//    String name= getPara("name");
//    String phone= getPara("phone");
    String name2 = getRequest().getHeader("name");
    String phone2 = getRequest().getHeader("phone");
    System.out.println(" name2="+name2+" maxid="+maxId);
    AddressList map=new AddressList();
    map.set("id",id);
    map.set("name",name2);
    map.set("phone",phone2);
    AddressList.dao._setAttrs(map);
    boolean isAdd = AddressList.dao.save();
    HttpServletResponse response = getResponse();
    try {
        Properties properties = new Properties();
        InputStream is = new FileInputStream("D://test.txt");
        properties.load(is);
        String ss = properties.getProperty("ss");
        System.out.println(" user="+ss);

        response.getWriter().write(isAdd+ss);
    } catch (IOException e) {
        e.printStackTrace();
    }
}

    public void save() {

        AddressList map=new AddressList();
        map.set("id","1");
        map.set("name","oplki");
        map.set("phone","ss12");
        AddressList.dao._setAttrs(map);
        AddressList.dao.update();
        AddressList.dao.deleteById("1");
        System.out.println("added");
        String likeStr = "select * from addresslist";
        List<AddressList> addressList= AddressList.dao.find(likeStr);
        System.out.println("addressList.l="+addressList.toString());
        HttpServletResponse response = getResponse();
        try {
            response.getWriter().write(addressList.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //下载文件
    public void downfile() {
        String name=getPara("name");
        System.out.println("name1="+name);
        File file = new File("D:\\file\\test.jpg");

        if (file.exists()) {
            renderFile(file);
        } else {
            renderJson();
        }
    }
    //上传文件
    public void uploadFile() {

        UploadFile uploadFile = this.getFile();

        String fileName = uploadFile.getOriginalFileName();

        File file = uploadFile.getFile();
        FileService fs = new FileService();
        File t = new File("D:\\file2\\" + UUID.randomUUID().toString()
                + file.getName().substring(file.getName().lastIndexOf(".")));
        try {
            t.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        fs.fileChannelCopy(file, t);
        file.delete();
        this.renderHtml("success,<a href=\"./\">back</a>");
    }
}