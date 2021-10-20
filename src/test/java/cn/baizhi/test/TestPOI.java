package cn.baizhi.test;


import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest
public class TestPOI {
    //导出
    @Test
    public void test1() throws IOException {
        //创建excel
        HSSFWorkbook workbook = new HSSFWorkbook();
        //创建工作表
        Sheet sheet1 = workbook.createSheet("学生信息表");
        Sheet sheet2 = workbook.createSheet("学生成绩表");
        //学生信息表创建1行    参数：行的下标
        Row row = sheet1.createRow(1);//代表第二行
        //创建列   单元格
        Cell cell = row.createCell(2);//代表单元格对象
        cell.setCellValue("hello");//为当前单元格设置内容
        //创建文件出来
        workbook.write(new FileOutputStream("E:\\poi.xls"));
    }

    //导出
    @Test
    public void test2() throws FileNotFoundException {
        //模拟程序的数据
        List<Student> list = new ArrayList<>();
        list.add(new Student("2", "张三1", 21, new Date()));
        list.add(new Student("3", "张三2", 22, new Date()));
        list.add(new Student("4", "张三3", 23, new Date()));
        list.add(new Student("5", "张三4", 24, new Date()));
        //模拟数据库查出的数据
        HSSFWorkbook workbook = new HSSFWorkbook();
        //构建字体
        HSSFFont font = workbook.createFont();
        font.setBold(true);    //加粗
        font.setColor(Font.COLOR_RED); //颜色
        //font.setColor(IndexedColors.GREEN.getIndex()); //颜色
        font.setFontHeightInPoints((short)20);  //字号
        font.setFontName("微软雅黑");  //字体
        font.setItalic(true);    //斜体
        font.setUnderline(FontFormatting.U_SINGLE);  //下划线

        //创建样式对象
        CellStyle cellStyle1 = workbook.createCellStyle();
        cellStyle1.setFont(font);     //将字体样式引入
        cellStyle1.setAlignment(HorizontalAlignment.CENTER);  //文字居中


        //创建样式对象
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        //日期样式对象
        HSSFDataFormat dataFormat = workbook.createDataFormat();
        //这是样式对象设置  日期格式
        cellStyle.setDataFormat(dataFormat.getFormat("yyyy年MM月dd日"));//short
        ////创建工作表   参数：表名（相当于Excel的sheet1,sheet2...)
        HSSFSheet sheet = workbook.createSheet("学生信息表");//当前表空间
        //创建第一行   第一个单元格
        HSSFRow row2 = sheet.createRow(0);//创建行下标
        HSSFCell cell3 = row2.createCell(0);//该行第一个单元格
        cell3.setCellStyle(cellStyle1);//当前单元格设置文字样式 居中 斜体  加粗........
        cell3.setCellValue("学生信息");
        //合并对象     参数：      行开始      行结束         列开始       列结束
        CellRangeAddress region=new CellRangeAddress(0, 0, 0, 3);
        sheet.addMergedRegion(region);

        //设置列宽
        sheet.setColumnWidth(3, 20*256);

        HSSFRow row = sheet.createRow(1);//创建行下标

        String[] content = {"id","姓名","年龄","生日"};
        // 循环创建单元格 i=0   1    2
        for(int i = 0; i<content.length; i++){
            HSSFCell cell = row.createCell(i);//该行第一个单元格
            cell.setCellValue(content[i]);
        }
        // 处理数据行数据 i=0   1
        for(int i = 0; i<list.size(); i++){
            HSSFRow row1 = sheet.createRow(i + 2);// 2  3   4  5 6  .....

            HSSFCell cell = row1.createCell(0);//当前行创建 第一个单元格
            cell.setCellValue(list.get(i).getId());

            HSSFCell cell1 = row1.createCell(1);
            cell1.setCellValue(list.get(i).getName());

            row1.createCell(2).setCellValue(list.get(i).getAge());

            HSSFCell cell2 = row1.createCell(3); //当前行创建第四个单元格
            cell2.setCellStyle(cellStyle);//当前单元格设置样式
            cell2.setCellValue(list.get(i).getBir());
        }
        try {
            workbook.write(new FileOutputStream("E:\\poi.xls"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //导入
    @Test
    //获取要导入的文件
    public void test3() throws IOException {
        HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(new File("E:\\poi.xls")));
        //获取工作薄
        HSSFSheet sheet = workbook.getSheet("学生信息表");
        //i从2开始   到空座空间的最后一行-1    2到5，加个i<=就是2到6行
        List<Student> list = new ArrayList<>();
        for (int i = 2; i < sheet.getLastRowNum(); i++) {
            //获取行
            HSSFRow row = sheet.getRow(i);
            //得到第一个单元格
            HSSFCell cell = row.getCell(0);
            String id = cell.getStringCellValue();
            //第二个单元格
            HSSFCell cell1 = row.getCell(1);
            String name = cell1.getStringCellValue();
            //第三个单元格
            HSSFCell cell2 = row.getCell(2);
            double age = cell2.getNumericCellValue();
            //第四个单元格
            HSSFCell cell3 = row.getCell(3);
            Date bir = cell3.getDateCellValue();

            Student student = new Student(id, name, (int)age, bir);
            list.add(student);
        }
        for (Student student : list) {
            System.out.println(student);
        }
    }

}
