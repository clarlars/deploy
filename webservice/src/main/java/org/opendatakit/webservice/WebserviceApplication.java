package org.opendatakit.webservice;

import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class WebserviceApplication {
  private static final String JAR_COLON = "jar:";

  public static void main(String[] args) {
    // TODO: Launch  UI here before to ask for scratch directory
    boolean hasDataDir = (System.getProperty("data.dir") != null);
    if ( hasDataDir ) {
      // user has invoked the jar and supplied a data.dir value -- use it.
      System.out.println("Scratch directory is " + System.getProperty("data.dir"));
    } else {
      // attempt to discover the filepath of the self-executable jar (or, when 
      // running under Eclipse, this class file). If we are able to find it, 
      // set the scratch directory to be a sibling of this file.
      try {
        URL url = WebserviceApplication.class.getProtectionDomain().getCodeSource().getLocation();
        Path self;
        // in an executable jar, the location would be filepath!jarpath
        // split on ! to get filepath portion.
        final String[] filepath_jarpath = url.toURI().toString().split("!");
        // and we are interested in the plain file, not the jar content, 
        // so strip off any jar: prefix to the filepath portion leaving file:...
        String plainFile;
        if ( filepath_jarpath[0].startsWith(JAR_COLON) ) {
          plainFile = filepath_jarpath[0].substring(JAR_COLON.length());
        } else {
          plainFile = filepath_jarpath[0];
        }
        // now reconstruct the path from the plainFile
        self = Paths.get(new URI(plainFile));
        if ( Files.exists(self) ) {
          // and make the data.dir a sibling
          String scratchDir = self.getParent().resolve("scratch").toString();
          System.setProperty("data.dir", scratchDir);
          System.out.println("Scratch directory is " + scratchDir);
        }
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        if ( System.getProperty("data.dir") == null ) {
          System.err.println("Unable to automatically set scratch (data.dir) location");
          System.err.println("Autoconfig error. Please set -Ddata.dir when starting java");
          System.exit(1);
        }
      }
    }
    SpringApplication.run(WebserviceApplication.class, args);
  }
}
