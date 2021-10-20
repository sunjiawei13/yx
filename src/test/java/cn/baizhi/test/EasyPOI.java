package cn.baizhi.test;


import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest
public class EasyPOI {

    //导出
    @Test
    public void test1() throws IOException {
        List<Student> list = new ArrayList<>();
        list.add(new Student("2", "小明1", 20, new Date()));
        list.add(new Student("3", "小明2", 21, new Date()));
        list.add(new Student("4", "小明3", 22, new Date()));
        list.add(new Student("5", "小明4", 23, new Date()));

        //参数：标题，表名，实体类类对象，导出的集合
        Workbook workbook =ExcelExportUtil.exportExcel(new ExportParams
                ("计算机一班学生","学生表"),Student.class, list);

        workbook.write(new FileOutputStream("E:\\easypoi.xls"));
    }
    //导出
    @Test
    public void test2() throws IOException {

        List<Student> list = new ArrayList<>();
        list.add(new Student("2", "小明1", 20, new Date()));
        list.add(new Student("3", "小明2", 21, new Date()));
        list.add(new Student("4", "小明3", 22, new Date()));
        list.add(new Student("5", "小明4", 23, new Date()));

        List<Teacher> teachers=new ArrayList<>();
        teachers.add(new Teacher("1", "小高1", 1000.0,list ));
        teachers.add(new Teacher("2", "小高2", 2000.0,list ));
        teachers.add(new Teacher("3", "小高3", 3000.0,list ));

        //参数：标题，表名，实体类类对象，导出的集合
        Workbook workbook =ExcelExportUtil.exportExcel(new ExportParams
                ("学生信息","学生表"),Teacher.class, teachers);

        workbook.write(new FileOutputStream("E:\\teachereasypoi.xls"));
    }
    //导入
    @Test
    public void test3() throws IOException {

        //导入参数对象
        ImportParams params = new ImportParams();
        params.setTitleRows(1);//表格标题行数,默认0
        params.setHeadRows(2);//表头行数,默认1
        //                                                                                   导入位置     导入类型     导入参数对象
        List<Teacher> teachers = ExcelImportUtil.importExcel(new File("E:\\teachereasypoi.xls"), Teacher.class, params);

        for (Teacher teacher : teachers) {
            System.out.println(teacher);
        }
    }
    //user导出D:\IDEACodes\yx_sjw\src\1.jpg   网络路径不行
    @Test
    public void test4() throws IOException {
        List<User> list=new ArrayList<>();
        list.add(new User("1", "src/1.jpg", "小黑1"));
        list.add(new User("2", "src/2.gif", "小黑2"));
        list.add(new User("3", "http://20210816class.oss-cn-beijing.aliyuncs.com/16292604886871.jpg", "小黑3"));
        list.add(new User("4", "E:\\2.jpg", "小黑4"));

        //参数：标题，表名，实体类类对象，导出的集合
        Workbook workbook =ExcelExportUtil.exportExcel(new ExportParams
                ("用户信息","用户信息表"),User.class, list);

        workbook.write(new FileOutputStream("E:\\usereasypoi.xls"));

    }
    //图片导c入    D盘/excel/upload/img\User\
    @Test
    public void test5() throws IOException {
        //导入参数对象
        ImportParams params = new ImportParams();
        params.setTitleRows(1);//表格标题行数,默认0
        params.setHeadRows(1);//表头行数,默认1
        //                                 导入位置     导入类型     导入参数对象
        List<User> list = ExcelImportUtil.importExcel(new File("E:\\usereasypoi.xls"), User.class, params);

        for (User user : list) {
            System.out.println(user);
        }
    }
}
