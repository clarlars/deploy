package org.opendatakit.webservice.utilities;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileAttribute;

public class ZipUtil {
  public static void extractZip(Path pathToZip, Path outputPath) throws IOException {

    try (FileSystem zipFs = FileSystems.newFileSystem(pathToZip, ZipUtil.class.getClassLoader())) {
      Files.walkFileTree(zipFs.getPath("/"), new SimpleFileVisitor<Path>() {
        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
          super.preVisitDirectory(dir, attrs);

          // concat string because dir has a leading slash
          Files.createDirectories(Paths.get(outputPath.toString() + dir.toString()));
          return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
          super.visitFile(file, attrs);

          // concat string because dir has a leading slash
          Files.copy(file, Paths.get(outputPath.toString() + file.toString()), StandardCopyOption.REPLACE_EXISTING);
          return FileVisitResult.CONTINUE;
        }
      });
    }
  }

  public static void extractZipFromResource(String name, Path outputPath) throws IOException {
    FileAttribute<?>[] attrs = {};
    Path temp = Files.createTempFile(name, ".zip", attrs);
    try {
      Files.copy(ZipUtil.class.getClassLoader().getResourceAsStream(name), temp, StandardCopyOption.REPLACE_EXISTING);

      extractZip(temp, outputPath);
    } finally {
    	Files.deleteIfExists(temp);
    }
  }
}
