package uc.mei.is.client;

import java.io.OutputStream;

public class Printer {
    private Printer(){}

    public static <T> void print(T obj, OutputStream os){
        try {
            os.write(obj.toString().getBytes());
        } catch (Exception e) {
            System.err.println(e.getStackTrace());
        }
    }
}
