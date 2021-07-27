package com.elior.s3app;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import com.amazonaws.event.ProgressEvent;
import com.amazonaws.event.ProgressListener;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.elior.s3app.utils.AwsFactory;
import com.elior.s3app.utils.FilesUtils;
import com.elior.s3app.utils.PrintUtils;

public class Uploader {
	private AmazonS3 s3client;
	private String dirPath;
	private PrintUtils print = PrintUtils.getInstance();

	public Uploader(String accessKey, String secretKey, String endPoint, String dirPath) {
		this.s3client = AwsFactory.createS3ClientbyCredentials(accessKey, secretKey, endPoint);
		this.dirPath = dirPath;
	}

	public void uploadFiles() throws IOException {
		Set<String> files = FilesUtils.getFilesNames(dirPath);
		String bucketName = s3client.listBuckets().get(0).getName();
		upload(files, bucketName);
	}

	private void upload(Set<String> files, String bucketName) {
		print.title(files.size(), bucketName);
		for (String currFile : files) {
			uploadFile(currFile, bucketName);
		}
	}

	private void uploadFile(String currFile, String bucketName) {
		File file = new File(currFile);
		final long fileSize = file.length();
		PutObjectRequest request = setRequest(bucketName, currFile, file, fileSize);
		setProgressListener(request, fileSize);
		s3client.putObject(request);
		print.emptyLine();
	}

	private void setProgressListener(PutObjectRequest request, long fileSize) {
		request.setGeneralProgressListener(new ProgressListener() {
			long transferredBytes = 0;
			int lastPrintedPercent = 0;
			int percent = 0;
			int approximateBottomValue = 10;

			@Override
			public void progressChanged(ProgressEvent progressEvent) {
				transferredBytes += progressEvent.getBytesTransferred();
				percent = (int) ((transferredBytes / (float) fileSize) * 100);
				if (((percent % 10 == 0 && lastPrintedPercent != percent) || approximateBottomValue < percent)
						&& lastPrintedPercent != 100) {
					int percentToPrint = approximateBottomValue < percent && percent != 100 ? approximateBottomValue
							: percent;
					print.progress(transferredBytes, percentToPrint);
					lastPrintedPercent = percent;
					approximateBottomValue += 10;
				}
			}
		});

	}

	private PutObjectRequest setRequest(String bucketName, String currFile, File file, long fileSize) {
		PutObjectRequest request = new PutObjectRequest(bucketName, currFile, file);
		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentType("plain/text");
		metadata.addUserMetadata("title", "someTitle");
		request.setMetadata(metadata);
		print.fileDetails(currFile.substring(7), fileSize);
		return request;
	}

}
