# app-service

Open this is an Eclipse workspace. 
Then import the webservice project into this workspace.

webservice is an Eclipse project defining a Servlet 3.0 application.

The general structure is:

webservice/androidlibrary_src  -- identical copy of files from Android project
webservice/androidlibrary_gen  -- simplification of (generated) R object etc. from Android project
webservice/androidlibrary_mod  -- classes modified specifically for webservice

webservice/androidcommon_src  -- identical copy of files from Android project
webservice/androidcommon_gen  -- simplification of (generated) R object etc. from Android project
webservice/androidcommon_mod  -- classes modified specifically for webservice

webservice/services_src  -- identical copy of files from Android project
webservice/services_gen  -- simplification of (generated) R object etc. from Android project
webservice/services_mod  -- classes modified specifically for webservice

webservice/survey_src  -- identical copy of files from Android project

webservice/tables_src  -- identical copy of files from Android project
webservice/tables_mod  -- classes modified specifically for webservice

webservice/src -- code specific to webservice that is not in Android

webservice/android_sdk_android_MNC_src -- files from Android open source project
	This is source code from the android_sdk for MNC. Under $ANDROID_SDK/sources/android-MNC/

webservice/android_sdk_android_15_src -- files from Android open source project
     This is source code from the android_sdk for API 15. Under $ANDROID_SDK/sources/android-15/
	 Newer versions use reflection. Kept at 15 to avoid that.

webservice/android_sdk_23_mod -- modified files from Android open source project
     This is source code from the android_sdk for API 23. Under $ANDROID_SDK/sources/android-23/
	 Provides color constant definitions. Transform methods removed to reduce dependencies.
	 
webservice/platform_tools_base_annotations_src -- files from Android open source project
     From this repo: 
		git clone https://android.googlesource.com/platform/tools/base
		git checkout master -- as of September 25, 2017
		located under annotations/src/main/java/com/android/annotations
	 
webservice/platform_frameworks_base_core_src -- files from Android open source project
	 From this repo:
		git clone https://android.googlesource.com/platform/frameworks/base/
		git checkout master -- as of September 25, 2017
		located under core/java

webservice/platform_frameworks_base_core_mod -- modified files from Android open source project
	 These generally have sections of the file stripped out.
	 From this repo:
		git clone https://android.googlesource.com/platform/frameworks/base/
		git checkout master -- as of September 25, 2017
		located under core/java

webservice/platform_frameworks_base_core_gen -- 
	 Java for AIDL interfaces. Not sure where I found the generated source files.
	 AIDL is from this repo:
		git clone https://android.googlesource.com/platform/frameworks/base/
		git checkout master -- as of September 25, 2017
		located under core/java

webservice/platform_libcore_luni_mod -- files from Android open source project
	 These are slightly modified to reduce dependencies.
        git clone https://android.googlesource.com/platform/libcore
		git checkout master -- as of September 25, 2017
		located under luni/src/main/java
		
webservice/libs -- libraries webservice needs

webservice/WebContent -- content from app-designer, modified for operation within webservice context
               this is not split out into identical and modified code. The goal is for 
			   this version of these files to replace the content in nodejs-based app-designer.


sqlite-jdbc-3.8.7jar -- JAR for sqlite database 
               using JDBC -- https://bitbucket.org/xerial/sqlite-jdbc

siteScratch -- this is the scratch website and JDBC implementation of sqlite

To run:

1. Configure Tomcat or your web container so that it can find sqlite-jdbc-3.8.7.jar
via JNI. This generally means copying it into the libs directory of the Tomcat install.

2. Delete and re-create the Server so that the changes, above, are brought into the project.

3. Choose Run As... / Run On Server
   And choose the server you just created. 
   
4. Stop the server.

5. Right-click the webservice project and open "Run Configurations..."
   Choose the server you just configured 
   Choose the (x)= Arguments tab
   Add to the VM args:
    -Ddata.dir="${workspace_loc}/scratch"
   And apply the change.
   
Verify that this is also propagated to the debug configuration.

data.dir will be the directory under which the /opendatakit/default/data directory will be found.
I.e., where all user-specific and app-specific content will be initialized.
When the database is purged, this content will be deleted and regenerated.
All other (static) content is under the webservice/WebContent directory.

NOTE: for website implementation, the /opendatakit... path should be prefixed by the logged-in 
userid, so that each logged-in user has a different data directory and cannot trash other 
user directories.

NOTE: this is used as the path returned by the Environment class 
(under webservice/android_frameworks_aidl_gen)




