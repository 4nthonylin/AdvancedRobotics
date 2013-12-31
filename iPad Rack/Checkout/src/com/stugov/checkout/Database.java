package com.stugov.checkout;

import java.io.IOException;
import java.net.MalformedURLException;

import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;

public class Database {
	public Database() throws AuthenticationException, MalformedURLException,
			IOException, ServiceException {

		String USERNAME = "checkout@tas.tw";
		String PASSWORD = "ipadcheckout";

		SpreadsheetService service = new SpreadsheetService("iPadDatabase");
		//service.setProtocolVersion(SpreadsheetService.Versions.V3);
		service.setUserCredentials(USERNAME, PASSWORD);

		// TODO: See other portions of this guide for code to put here...

	}
}