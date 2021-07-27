package com.elior.s3app.utils;

public class PrintUtils {
	private static PrintUtils Instance;
	private StringBuilder sb = new StringBuilder();

	private PrintUtils() {}

	public static PrintUtils getInstance() {
		if (Instance == null) {
			synchronized (PrintUtils.class) {
				if (Instance == null) {
					Instance = new PrintUtils();
				}
			}
		}
		return Instance;
	}

	public void title(int filesSize, String bucketName) {
		sb.append(filesSize).append(" files are about to be transferred to bucket ").append(bucketName).append("\r\n");
		System.out.println(sb);
		sb.setLength(0);
	}

	public void progress(long transferredBytes, int percentToPrint) {
		sb.append("Transferred: ").append(transferredBytes).append(" bytes Percent: ").append(percentToPrint);
		System.out.println(sb);
		sb.setLength(0);
	}

	public void fileDetails(String fileName, long fileSize) {
		sb.append("Transferring: ").append(fileName).append(System.lineSeparator()).append("Size: ").append(fileSize)
				.append(" bytes");
		System.out.println(sb);
		sb.setLength(0);
	}

	public void emptyLine() {
		System.out.println();
	}
}
