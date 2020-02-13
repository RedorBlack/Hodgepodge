package com.red.webflux.poi.controller;


import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.TemplateExportParams;
import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;
import com.red.webflux.poi.model.Red;
import com.red.webflux.poi.util.FileUtil;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

/**
 * @Author: Red
 * @Descpription:
 * @Date: 10:26 2019/8/12
 */
@RestController
public class PoiController {


    /**
     * 注解导出
     *
     * @param response
     * @return
     */
    @GetMapping("/export")
    public void export(HttpServletResponse response) {
        Red red = null;
        List<Red> reds = new ArrayList<Red>();
        for (; ; ) {
            red = new Red();
            red.setAddress("广州海珠");
            red.setAge(100);
            red.setName("Red");
            red.setTime(System.currentTimeMillis());
            reds.add(red);
            if (reds.size() >= 200) {
                break;
            }
        }
        FileUtil.exportExcel(reds, "花名册", "哦哦哦", Red.class, "red.xls", response);
    }

    /**
     * map 导出
     *
     * @param response
     */
    @GetMapping("/exportMap")
    public void exportMap(HttpServletResponse response) {
        //标题
        List<ExcelExportEntity> entityList = new ArrayList<>();
        //内容
        List<Map<String, Object>> dataResult = new ArrayList<>();
        entityList.add(new ExcelExportEntity("表头苹果", "apple", 15));
        entityList.add(new ExcelExportEntity("表头香蕉", "banana", 25));
        entityList.add(new ExcelExportEntity("时间", "date", 25));
        entityList.add(new ExcelExportEntity("地址", "address", 25));
        entityList.add(new ExcelExportEntity("姓名", "name", 25));
        entityList.add(new ExcelExportEntity("年龄", "age", 25));
        Map<String, Object> map = null;
        for (; ; ) {
            map = new Hashtable<>();
            map.put("apple", "测试");
            map.put("banana", "测试");
            map.put("date", System.currentTimeMillis());
            map.put("address", "广州海珠");
            map.put("name", "Red");
            map.put("age", 100);
            dataResult.add(map);
            if (dataResult.size() >= 1000) {
                break;
            }
        }
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("测试", "测试"), entityList,
                dataResult);
        FileUtil.downLoadExcel("shuiguo.xls", response, workbook);
    }

    /**
     * 导出模板
     *
     * @param response
     */
    @GetMapping("/exportTemplateExport")
    public void exportTemplateExport(HttpServletResponse response) {


        TemplateExportParams params = new TemplateExportParams("C:/Users/sw/Desktop/chengji.xls");
        Map<String, Object> map = new HashMap<>();
        map.put("putinTime", "2014-12-25 14:30:00");
        map.put("paperName", "java");
        map.put("userId", "java");
        map.put("score", 55);
        map.put("note", "xioaxing");
        List<Map<String, String>> listMap = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            Map<String, String> lm = new HashMap<>();
            lm.put("index", i + 1 + "");
            lm.put("questionName", "fasfafaffafafafa");
            lm.put("answerA", "A001");
            lm.put("answerB", "A001");
            lm.put("answerC", "A001");
            lm.put("answerD", "A001");
            lm.put("myAnswer", "A");
            lm.put("sign", "V");
            listMap.add(lm);
        }
        map.put("maplist", listMap);
        Workbook workbook = ExcelExportUtil.exportExcel(params, map);
        FileUtil.downLoadExcel("成绩单.xls", response, workbook);
    }

    @GetMapping("/bigDataExport")
    public void bigDataExport(HttpServletResponse response) throws IOException {
        Workbook workbook = null;
        FileOutputStream fos = null;
        try {
            List<Red> list = new ArrayList<Red>();

            Red red = null;
            Date start = new Date();
            ExportParams params = new ExportParams("大数据测试", "测试");
            for (int i = 0; i < 1000000; i++) {
                red = new Red();
                red.setTime(System.currentTimeMillis());
                red.setName("ceshi");
                red.setAge(1000);
                red.setAddress("haizhu1");
                list.add(red);
                if (list.size() == 10000) {
                    workbook = ExcelExportUtil.exportBigExcel(params, Red.class, list);
                    list.clear();
                }
            }
            ExcelExportUtil.closeExportBigExcel();
            File savefile = new File("E:/excel/");
            if (!savefile.exists()) {
                savefile.mkdirs();
            }
            fos = new FileOutputStream("E:/excel/ExcelExportBigData.bigDataExport.xlsx");
            workbook.write(fos);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (fos != null) {
                fos.close();
            }
        }
    }
}
