/**
 * 
 */
package org.zollty.mlf4j.monitor.service.mlf4j.bean;

/**
 * 
 * @author yinranchao
 * @since 2014年10月20日
 */
public class FileContentQueryConditionBean {
    
    private String logFilePath;
    private int lineBegin;
    private int lineEnd;
    private String levelStr;
    private String keyWord;
    private String code;
    
    
    
    /**
     * @return the logFilePath
     */
    public String getLogFilePath() {
        return logFilePath;
    }
    /**
     * @param logFilePath the logFilePath to set
     */
    public void setLogFilePath(String logFilePath) {
        this.logFilePath = logFilePath;
    }
    /**
     * @return the lineBegin
     */
    public int getLineBegin() {
        return lineBegin;
    }
    /**
     * @param lineBegin the lineBegin to set
     */
    public void setLineBegin(int lineBegin) {
        this.lineBegin = lineBegin;
    }
    /**
     * @return the lineEnd
     */
    public int getLineEnd() {
        return lineEnd;
    }
    /**
     * @param lineEnd the lineEnd to set
     */
    public void setLineEnd(int lineEnd) {
        this.lineEnd = lineEnd;
    }
    /**
     * @return the levelStr
     */
    public String getLevelStr() {
        return levelStr;
    }
    /**
     * @param levelStr the levelStr to set
     */
    public void setLevelStr(String levelStr) {
        this.levelStr = levelStr;
    }
    /**
     * @return the keyWord
     */
    public String getKeyWord() {
        return keyWord;
    }
    /**
     * @param keyWord the keyWord to set
     */
    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }
    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }
    /**
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }
    

}
