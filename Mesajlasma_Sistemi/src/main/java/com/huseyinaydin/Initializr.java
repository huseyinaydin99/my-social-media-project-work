package com.huseyinaydin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.DriverManager;

import com.mysql.jdbc.Driver;

public class Initializr {
	static {
		try {
			try {
				OutputStream outputStream = new FileOutputStream(new File("C:\\Users\\Hüseyin\\Desktop\\abc.txt"));
				outputStream.write("class bulunmadan önce! ".getBytes());
				outputStream.flush();
				outputStream.close();
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			Class.forName("oracle.jdbc.driver.OracleDriver");
			try {
				OutputStream outputStream = new FileOutputStream(new File("C:\\Users\\Hüseyin\\Desktop\\abc.txt"));
				outputStream.write("class bulundu! ".getBytes());
				outputStream.flush();
				outputStream.close();
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			try {
				OutputStream outputStream = new FileOutputStream(new File("C:\\Users\\Hüseyin\\Desktop\\abc.txt"));
				outputStream.write("class bulunamadı! ".getBytes());
				outputStream.flush();
				outputStream.close();
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}

	void dataSourceInit() {
		try {
			try {
				OutputStream outputStream = new FileOutputStream(new File("C:\\Users\\Hüseyin\\Desktop\\abc.txt"));
				outputStream.write("class bulunmadan önce! ".getBytes());
				outputStream.flush();
				outputStream.close();
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			Class.forName("oracle.jdbc.driver.OracleDriver");
			try {
				OutputStream outputStream = new FileOutputStream(new File("C:\\Users\\Hüseyin\\Desktop\\abc.txt"));
				outputStream.write("class bulundu! ".getBytes());
				outputStream.flush();
				outputStream.close();
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			try {
				OutputStream outputStream = new FileOutputStream(new File("C:\\Users\\Hüseyin\\Desktop\\abc.txt"));
				outputStream.write("class bulunamadı! ".getBytes());
				outputStream.flush();
				outputStream.close();
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		System.out.println("data source başlıyor!");
	}

	void dataSourceDestroy() {
		System.out.println("data source bitiyor!!");
	}
}
