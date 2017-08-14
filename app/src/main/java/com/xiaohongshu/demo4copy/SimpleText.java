package com.xiaohongshu.demo4copy;

/**
 * Created by wupengjian on 17/8/14.
 */

public class SimpleText {
    String text;
    int height;

    public SimpleText(String text) {
        this.text = text;
        this.height = randomHeight();
    }

    public int randomHeight() {
        return (int) (100 + Math.random() * 800);
    }
}
