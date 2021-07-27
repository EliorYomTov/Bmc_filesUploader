package com.elior.s3app;

import java.io.IOException;

public class App {

	public static void main(String[] args) throws IOException {		
		Uploader uploader = new Uploader("MTMY1LDIQNXIW4A8YKAD", "6Nl0s4gYDUg7fyIfrSt9kNxOzKAUkCUU3dXuglMm",
				"https://8826d347-e86d-11eb-80de-92d5926c9c30.sandbox.zenko.io", "C:\\bmc");
		uploader.uploadFiles();
	}
}
