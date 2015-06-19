package org.zollty.mlf4j.monitor.service.mlf4j.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.zollty.framework.util.MvcUtils;
import org.zollty.mlf4j.base.exception.MlfI18nException;
import org.zollty.mlf4j.monitor.service.mlf4j.IMlf4jLogFileService;
import org.zollty.mlf4j.monitor.service.mlf4j.bean.FileContentQueryConditionBean;
import org.zollty.util.NestedCheckedException;

/**
 * Mlf4jLogFileServiceImpl 单元测试
 * 
 * @author zollty
 * @since 2014年11月6日
 */
public class Mlf4jLogFileServiceImplTest {
    
    
    @Test
    public void testGetFileContent01(){
        String lineBegin = "1";
        String code = "";
        String lineEnd = "5";
        String levelStr = "";
        String keyWord = "";
        testGetFileContent("testcase01.txt", "testcase01-result.txt", lineBegin,
                code,
                lineEnd,
                levelStr,
                keyWord);
    }
    
    @Test
    public void testGetFileContent02(){
        String lineBegin = "1";
        String code = "UTF-8";
        String lineEnd = "8";
        String levelStr = "ERROR";
        String keyWord = "";
        testGetFileContent("testcase02.txt", "testcase02-result.txt", lineBegin,
                code,
                lineEnd,
                levelStr,
                keyWord);
    }
    
    @Test
    public void testGetFileContent03(){
        String lineBegin = "1";
        String code = "UTF-8";
        String lineEnd = "47";
        String levelStr = "";
        String keyWord = "价格";
        testGetFileContent("testcase03.txt", "testcase03-result.txt", lineBegin,
                code,
                lineEnd,
                levelStr,
                keyWord);
    }
    
    @Test
    public void testGetFileContent04(){
        String lineBegin = "1";
        String code = "UTF-8";
        String lineEnd = "47";
        String levelStr = "ERR";
        String keyWord = "价格";
        testGetFileContent("testcase04.txt", "testcase04-result.txt", lineBegin,
                code,
                lineEnd,
                levelStr,
                keyWord);
    }
    
    @Test
    public void testGetFileContent05(){
        String lineBegin = "1";
        String code = "UTF-8";
        String lineEnd = "47";
        String levelStr = "ERROR";
        String keyWord = "价格";
        testGetFileContent("testcase05.txt", "testcase05-result.txt", lineBegin,
                code,
                lineEnd,
                levelStr,
                keyWord);
    }
    
    @Test
    public void testcode(){
        String lineBegin = "1";
        String code = "UTF-8";
        String lineEnd = "50";
        String levelStr = "";
        String keyWord = "";
        testGetFileContent("testcode.txt", "testcode.txt", lineBegin,
                code,
                lineEnd,
                levelStr,
                keyWord);
    }
    
    private void testGetFileContent(String dataFileName, String resultFileName, String lineBegin,
    String code,
    String lineEnd,
    String levelStr,
    String keyWord){
        
        IMlf4jLogFileService mlf4jLogFileService = new Mlf4jLogFileServiceImpl();
        String encode = "GBK";
        if(MvcUtils.StringUtil.isNotBlank(code)){
            encode = code;
        }
        String filePath = null;
        
        String expectedContent = null;
        try {
            filePath = getFilePathFromClassPath(Mlf4jLogFileServiceImplTest.class.getClassLoader(), "org/zollty/mlf4j/monitor/service/mlf4j/impl/"+dataFileName);
            expectedContent = makeTempListToContent( getTextFileContent(MvcUtils.ResourceUtil.getInputStreamFromClassPath(Mlf4jLogFileServiceImplTest.class.getClassLoader(), "org/zollty/mlf4j/monitor/service/mlf4j/impl/"+resultFileName), encode) );
        }
        catch (NestedCheckedException e) {
            e.printStackTrace();
        }
        
        String logFilePath = filePath;
        
        FileContentQueryConditionBean queryCondition = new FileContentQueryConditionBean();
        
        if(MvcUtils.StringUtil.isNotBlank(logFilePath)){
            queryCondition.setLogFilePath(logFilePath);
        }
        if(MvcUtils.StringUtil.isNotBlank(lineBegin)){
            queryCondition.setLineBegin(Integer.parseInt(lineBegin));
        }
        if(MvcUtils.StringUtil.isNotBlank(lineEnd)){
            queryCondition.setLineEnd(Integer.parseInt(lineEnd));
        }
        if(MvcUtils.StringUtil.isNotBlank(levelStr)){
            queryCondition.setLevelStr(levelStr);
        }
        if(MvcUtils.StringUtil.isNotBlank(keyWord)){
            queryCondition.setKeyWord(keyWord);
        }
        if(MvcUtils.StringUtil.isNotBlank(code)){
            queryCondition.setCode(code);
        }
        
        
        
        try {
            String queryResult = 
            mlf4jLogFileService.getFileContent(queryCondition);
            Assert.assertEquals(expectedContent, queryResult);
        }
        catch (MlfI18nException e) {
            e.printStackTrace();
        }
        
    }
    
    
    
    
    ////////Bellow are helper methods for this class//////////////////////////////////
    
    /**
     * 取得clazz.getClassLoader()所在 ClassPath下的资源（非url.openStream()模式，支持动态更新）
     * 
     * @param classLoader 
     * @param resourcePath 相对路径
     */
    private static String getFilePathFromClassPath(ClassLoader classLoader, String resourcePath) throws NestedCheckedException {
        String resourcePathNew = resourcePath;
        if (resourcePathNew.startsWith("/") || resourcePathNew.startsWith(File.separator)) {
            resourcePathNew = resourcePathNew.substring(1);
        }
        URL url = classLoader.getResource(resourcePathNew); // 必须是ClassLoader
        String filePath = url.getPath();//.substring(1);
        return filePath;
    }
    
    /**
     * 按行解析文本文件
     */
    private static List<String> getTextFileContent(InputStream in, String charSet) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(in, charSet));
            String buf = null;
            List<String> ret = new ArrayList<String>();
            while (null != (buf = br.readLine())) {
                ret.add(buf);
            }
            return ret;
        }
        catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<String>();
        }
        finally {
            MvcUtils.IOUtil.closeIO(br);
        }
    }
    
    private static String makeTempListToContent(List<String> tempList){
        String content = "";
        for(int i = 0;i < tempList.size();i++){
            content = content + tempList.get(i) + "\n";
        }
        
        content = content.substring(0, content.length()-1);
        return content;
    }

}
