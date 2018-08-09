/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.overflow;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import javax.media.jai.JAI;
import javax.media.jai.RenderedOp;
import java.util.Date;
import java.text.SimpleDateFormat;

import com.sun.media.jai.codec.BMPEncodeParam;
import com.sun.media.jai.codec.ImageCodec;
import com.sun.media.jai.codec.ImageEncoder;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Administrator
 */
public class SearchBRNot24 {
    public static String file_name = "";
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        //获取TXT文件时间部分
        file_name = getCurrentTimeForFile();
        String path = XMLTool.getConfigXML().get("ADDR");
        if(path == null || path.length() <=0){
            System.out.println("配置文件config.xml未配置图片文件夹得路径ADDR，请进行配置！");
            return;
        }
        List<String> list = getAllFile(path,false);
        List<String> _24b_list = new ArrayList<String>();
        List<String> _32b_list = new ArrayList<String>();
        List<String> _not_jpg_list = new ArrayList<String>();
        String picName = "";
        writeInTxt("\r\n");
        writeInTxt("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
        writeInTxt(path+"共有图片 " + list.size() + " 张！");
        System.out.println(path+"共有图片 " + list.size() + " 张！\r\n");
        for(int i=0;i<list.size();i++) {
            picName = list.get(i);
            if(isJpgPic(picName)){//判断是否是jpg格式图片
                int ps = getBitDeepth(picName);
                System.out.println(picName+"的位深度为: " + ps +" 位");
                if(ps == 32) {
                        _32b_list.add(picName);
                }
                if(ps == 24){
                    _24b_list.add(picName);
                }
            }else{
                System.out.println(picName+"是非jpg格式的的图片");
                _not_jpg_list.add(picName);
            }
                
        }
        System.out.println("\r\n$$$$$$$$$$$$$ 分割线 $$$$$$$$$$$$$");
        writeInTxt("\r\n");
        writeInTxt("符合要求的图片有 " + _24b_list.size() + " 张！");
        System.out.println("\r\n符合要求的图片有 " + _24b_list.size() + " 张！");
        for(int i=0;i<_24b_list.size();i++) {
            picName = _24b_list.get(i);
            System.out.println(picName);
            writeInTxt(picName);
        }
        
        System.out.println("\r\n位深度为32的图片有 " + _32b_list.size() + " 张！");
        writeInTxt("\r\n");
        writeInTxt("位深度为32的图片有 " + _32b_list.size() + " 张！");
        for(int i=0;i<_32b_list.size();i++) {
            picName = _32b_list.get(i);
            System.out.println(picName);
            writeInTxt(picName);
        }
        System.out.println("\r\n非jpg格式的的图片有 " + _not_jpg_list.size() + " 张！");
        writeInTxt("\r\n");
        writeInTxt("非jpg格式的的图片有 " + _not_jpg_list.size() + " 张！");
        for(int i=0;i<_not_jpg_list.size();i++) {
            picName = _not_jpg_list.get(i);
            System.out.println(picName);
            writeInTxt(picName);
        }
        writeInTxt("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
        System.out.println("\r\n执行完毕，结果记录在D:/pictureResult"+file_name+".txt中，请查看！");
    }
    /**
     * 判断图片格式是否为jpg
     * @param path
     * @return 
     */
    public static boolean isJpgPic(String path){
        String[] strList = path.split("\\.");
        String formatName = strList[1];
        boolean flag = false;
        if("jpg".equals(formatName)){
            flag = true;
        }
        return flag;
    }
    /**
     * 获取图片位深度
     * @param bytes
     * @return 
     */
    /**
    * 获取一个图片的位深度
    * @param path
    * @return
    */
    public static int getBitDeepth(String path) {
           File picture = new File(path);
           BufferedImage sourceImg = null;
           try {
                   sourceImg = ImageIO.read(new FileInputStream(picture));
           } catch (FileNotFoundException e) {
                   // TODO Auto-generated catch block
                   e.printStackTrace();
           } catch (IOException e) {
                   // TODO Auto-generated catch block
                   e.printStackTrace();
           }
           ColorModel color = sourceImg.getColorModel();
           int ps = color.getPixelSize(); //该函数能取到位深度
           return ps;
    }
    /**
     * 获取路径下的所有文件/文件夹
     * @param directoryPath 需要遍历的文件夹路径
     * @param isAddDirectory 是否将子文件夹的路径也添加到list集合中
     * @return
     */
    public static List<String> getAllFile(String directoryPath,boolean isAddDirectory) {
        List<String> list = new ArrayList<String>();
        File baseFile = new File(directoryPath);
        if (baseFile.isFile() || !baseFile.exists()) {
            return list;
        }
        File[] files = baseFile.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                if(isAddDirectory){
                    list.add(file.getAbsolutePath());
                }
                list.addAll(getAllFile(file.getAbsolutePath(),isAddDirectory));
            } else {
                list.add(file.getAbsolutePath());
            }
        }
        return list;
    }
    
    public static void JPGToBmp() throws IOException{
        /*tif转换到bmp格式*/
        String inputFile = "d:/imagetest/0113.jpg";
        String outputFile = "d:/imagetest/0113.bmp";
        RenderedOp src = JAI.create("fileload", inputFile);
        OutputStream os = null;
        try {
            os = new FileOutputStream(outputFile);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SearchBRNot24.class.getName()).log(Level.SEVERE, null, ex);
        }
        BMPEncodeParam param = new BMPEncodeParam();
        ImageEncoder enc = ImageCodec.createImageEncoder("BMP", os,param);
        enc.encode(src);
        os.close();//关闭流
    }
    /**
     * 将信息写入TXT文档
     * @param info 
     */
    public static void writeInTxt(String info) {
        FileWriter fw = null;
        try {
            //如果文件存在，则追加内容；如果文件不存在，则创建文件
            File f=new File("D:\\pictureResult"+file_name+".txt");
            fw = new FileWriter(f, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        PrintWriter pw = new PrintWriter(fw);
        if("\r\n".equals(info)){
            pw.println(info);
        }else{
            pw.println(getCurrentTime() + "  " + info);
        }
        
        pw.flush();
        try {
            fw.flush();
            pw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 获取当前系统时间
     * @return 
     */
    public static String getCurrentTime(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String time = df.format(new Date());
        return time;
    }
    /**
     * 获取当前系统时间 yyyyMMddHHmm
     * @return 
     */
    public static String getCurrentTimeForFile(){
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmm");//设置日期格式
        String time = df.format(new Date());
        return time;
    }
}
