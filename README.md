# ODK Deploy

webservice is a Maven Spring Boot project defining a Servlet 3.1 application.

The general structure is:

```
webservice/src -- code specific to webservice that is not in Android

webservice/src/main/resources/static -- content from app-designer, modified for operation within webservice context
                                        this is not split out into identical and modified code. The goal is for 
                                        this version of these files to replace the content in nodejs-based app-designer.

library/androidlibrary_src  -- identical copy of files from Android project
library/androidlibrary_gen  -- simplification of (generated) R object etc. from Android project
library/androidlibrary_mod  -- classes modified specifically for webservice

library/androidcommon_src  -- identical copy of files from Android project
library/androidcommon_gen  -- simplification of (generated) R object etc. from Android project
library/androidcommon_mod  -- classes modified specifically for webservice

library/services_src  -- identical copy of files from Android project
library/services_gen  -- simplification of (generated) R object etc. from Android project
library/services_mod  -- classes modified specifically for webservice

library/survey_src  -- identical copy of files from Android project

library/tables_src  -- identical copy of files from Android project
library/tables_mod  -- classes modified specifically for webservice

library/android_sdk_android_MNC_src -- files from Android open source project
	This is source code from the android_sdk for MNC. Under $ANDROID_SDK/sources/android-MNC/

library/android_sdk_android_15_src -- files from Android open source project
     This is source code from the android_sdk for API 15. Under $ANDROID_SDK/sources/android-15/
	 Newer versions use reflection. Kept at 15 to avoid that.

library/android_sdk_23_mod -- modified files from Android open source project
     This is source code from the android_sdk for API 23. Under $ANDROID_SDK/sources/android-23/
	 Provides color constant definitions. Transform methods removed to reduce dependencies.
	 
library/platform_tools_base_annotations_src -- files from Android open source project
     From this repo: 
		git clone https://android.googlesource.com/platform/tools/base
		git checkout master -- as of September 25, 2017
		located under annotations/src/main/java/com/android/annotations
	 
library/platform_frameworks_base_core_src -- files from Android open source project
	 From this repo:
		git clone https://android.googlesource.com/platform/frameworks/base/
		git checkout master -- as of September 25, 2017
		located under core/java

library/platform_frameworks_base_core_mod -- modified files from Android open source project
	 These generally have sections of the file stripped out.
	 From this repo:
		git clone https://android.googlesource.com/platform/frameworks/base/
		git checkout master -- as of September 25, 2017
		located under core/java

library/platform_frameworks_base_core_gen -- 
	 Java for AIDL interfaces. Not sure where I found the generated source files.
	 AIDL is from this repo:
		git clone https://android.googlesource.com/platform/frameworks/base/
		git checkout master -- as of September 25, 2017
		located under core/java

library/platform_libcore_luni_mod -- files from Android open source project
	 These are slightly modified to reduce dependencies.
        git clone https://android.googlesource.com/platform/libcore
		git checkout master -- as of September 25, 2017
		located under luni/src/main/java

siteScratch -- this is the scratch website and JDBC implementation of sqlite
```

## To build

1. `ant` in dependencies directory
2. `mvn clean package`

This builds a Spring Boot uber jar

## To run

`mvn clean spring-boot:run` or `java -jar PATH_TO_JAR.jar`

If `data.dir` Java property is specified, 
data.dir will be the directory under which the `opendatakit/default/data` directory will be found.
I.e., where all user-specific and app-specific content will be initialized.
When the database is purged, this content will be deleted and regenerated.
All other (static) content is under the `webservice/src/main/resources/static` directory.

If `data.dir` is not specified, the directory in which the java / mvn command was ran is used. 

**NOTE:** for website implementation, the /opendatakit... path should be prefixed by the logged-in 
userid, so that each logged-in user has a different data directory and cannot trash other 
user directories.

**NOTE:** this is used as the path returned by the `Environment` class 
(under `library/platform_frameworks_base_core_mod/android/os`)
