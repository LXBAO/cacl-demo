package com.example.crawler.vo;

/**
 * @author lx
 * @data 2022/11/11 9:11
 */
public class Page<T> {
    private int startPage;
    private int endPage;
    private T  t;
    public int getStartPage() {
        return startPage == 0 ? 1 : startPage;
    }

    public void setStartPage(int startPage) {
        this.startPage = startPage;
    }

    public int getEndPage() {
        return endPage;
    }

    public void setEndPage(int endPage) {
        this.endPage = endPage;
    }

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }
}
