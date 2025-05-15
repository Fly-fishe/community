package com.nowcoder.community;

import java.io.IOException;

public class WkTests {

    public static void main(String[] args) {
        String cmd ="E:/javaworks/workspaces/wkhtmltopdf/bin/wkhtmltoimage --quality 75  https://www.nowcoder.com E:/javaworks/data/wk-images/3.png";
        try {
            Runtime.getRuntime().exec(cmd);
            System.out.println("ok.");
        } catch (IOException e) {
           e.printStackTrace();
        }
    }
}
