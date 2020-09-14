package com.overtime.stie.connectionhelper;

import java.sql.Connection;

public class Test {

	public static void main(String[] args) {
		ConnectionHelper helper = new ConnectionHelper();
		try {
			Connection conn = helper.initConnection();
			System.out.println(conn);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
