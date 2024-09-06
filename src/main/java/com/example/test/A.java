package com.example.test;

/**
 * @author lx
 * @data 2023/5/15 10:29
 */
public enum  A  {
    B(1,"BB");
    private int num;
    private String str;

    A(int num, String str) {
        this.num = num;
        this.str = str;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }
}
