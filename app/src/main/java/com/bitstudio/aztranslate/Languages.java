package com.bitstudio.aztranslate;

public class Languages {


    private String name;
    private String filename;
    private String fileurl;
    private String key;
    private String symbolName;

    public Languages() {

    }

    public Languages(String name, String filename, String fileurl, String symbolName) {

        this.name = name;
        this.filename = filename;
        this.fileurl = fileurl;
        this.symbolName = symbolName;

    }

    public String getSymbolName() {
        return symbolName;
    }

    public void setSymbolName(String symbolName) {
        this.symbolName = symbolName;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFileurl() {
        return fileurl;
    }

    public void setFileurl(String fileurl) {
        this.fileurl = fileurl;
    }

    @Override
    public String toString() {
        return this.name;
    }
}

