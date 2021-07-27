package com.elior.s3app.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FilesUtils {

	@SuppressWarnings("unchecked")
	public static Set<String> getFilesNames(String dir) throws IOException {
		try (@SuppressWarnings("rawtypes")
		Stream stream = Files.list(Paths.get(dir))) {
			return (Set<String>) stream.filter(file -> !Files.isDirectory(Path.of(file.toString())))
					.map(file -> file.toString()).collect(Collectors.toSet());
		}
	}
}
