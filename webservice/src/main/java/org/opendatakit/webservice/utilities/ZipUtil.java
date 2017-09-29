package org.opendatakit.webservice.utilities;

import java.io.IOException;
import java.net.URI;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Collections;

public class ZipUtil {
  public static void extractZip(Path pathToZip, Path outputPath) throws IOException {
    // the uri scheme has to be jar
    URI zipUri = URI.create("jar:file:" + pathToZip.toAbsolutePath().toString());

    try (FileSystem zipFs = FileSystems.newFileSystem(zipUri, Collections.emptyMap())) {
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
    Path temp = Files.createTempFile(name, null);
    Files.copy(ZipUtil.class.getClassLoader().getResourceAsStream(name), temp, StandardCopyOption.REPLACE_EXISTING);

    extractZip(temp, outputPath);
  }
}
