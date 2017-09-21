package org.opendatakit.androidlibrary;

import java.util.HashMap;

import android.content.Context;

public final class R {
	public static final class string {
		
		private string() {};
		
		public static final int default_sync_server_url ;
		
		public static final int credential_type_none ;
		public static final int credential_type_username_password ;
		public static final int credential_type_google_account ;

		public static final int expansion_unzipping_begins ;
		public static final int expansion_unzipping_without_detail ;
		public static final int expansion_create_dir_detail ;
		public static final int expansion_unzipping_detail ;
		public static final int expansion_unzipping_complete ;

		public static final int searching_for_deleted_forms ;
		public static final int examining_form ;

		public static final int scanning_for_table_definitions ;
		public static final int processing_file ;
		public static final int defining_tableid_error ;

		public static final int poorly_formatted_init_file ;
		public static final int csv_file_not_found ;
		public static final int importing_file_without_detail ;
		public static final int import_csv_success ;
		public static final int import_csv_failure ;
		public static final int import_success ;
		public static final int import_in_progress ;

		public static final int searching_for_form_defs ;
		public static final int updating_table_form_information ;
		public static final int table_forms_register_success ;
		public static final int table_forms_register_failure ;

		public static final int abort_error_accessing_database ;
		
		static {
			HashMap<Integer, String> trans = new HashMap<Integer, String>();
			
			int idx = 1000;
			default_sync_server_url  = ++idx; trans.put(idx,  "https://open-data-kit.appspot.com");
			
			credential_type_none  = ++idx; trans.put(idx,  "none");
			credential_type_username_password  = ++idx; trans.put(idx,  "username_password");
			credential_type_google_account  = ++idx; trans.put(idx,  "google_account");

			expansion_unzipping_begins  = ++idx; trans.put(idx,  "Unzipping resources. Scanning zip directory.");
			expansion_unzipping_without_detail  = ++idx; trans.put(idx,  "Unzipping resources. Extracting %1$s (%2$d of %3$d)");
			expansion_create_dir_detail  = ++idx; trans.put(idx,  "Creating directory");
			expansion_unzipping_detail  = ++idx; trans.put(idx,  "at byte %1$d, file %2$d");
			expansion_unzipping_complete  = ++idx; trans.put(idx,  "Unzipping completed. %1$d resources unzipped.");

			searching_for_deleted_forms  = ++idx; trans.put(idx,  "Searching for deleted forms &#8230;");
			examining_form  = ++idx; trans.put(idx,  "Verifying form %2$s for table %1$s exists &#8230;");

			scanning_for_table_definitions  = ++idx; trans.put(idx,  "Scanning for table definitions. Creating %1$s (%2$d of %3$d).");
			processing_file  = ++idx; trans.put(idx,  "processing file&#8230;");
			defining_tableid_error  = ++idx; trans.put(idx,  "Error processing table definition for tableId %1$s");

			poorly_formatted_init_file  = ++idx; trans.put(idx,  "Invalid tables.init file");
			csv_file_not_found  = ++idx; trans.put(idx,  "Warning: CSV File not found: %1$s - skipped");
			importing_file_without_detail  = ++idx; trans.put(idx,  "Importing file (%1$d of %2$d): %3$s");
			import_csv_success  = ++idx; trans.put(idx,  "Success: CSV intialization for tableId %1$s");
			import_csv_failure  = ++idx; trans.put(idx,  "Failure: CSV intialization for tableId %1$s");
			import_success  = ++idx; trans.put(idx,  "CSV File import was successful.");
			import_in_progress  = ++idx; trans.put(idx,  "Importing row %1$d of about %2$d");

			searching_for_form_defs  = ++idx; trans.put(idx,  "Searching for new form definitions &#8230;");
			updating_table_form_information  = ++idx; trans.put(idx,  "Updating form information for tableId %1$s (%2$d of %3$d)");
			table_forms_register_success  = ++idx; trans.put(idx,  "Success: Processed forms for tableId %1$s");
			table_forms_register_failure  = ++idx; trans.put(idx,  "Failure: Processing forms for tableId %1$s");

			abort_error_accessing_database  = ++idx; trans.put(idx,  "Aborted: Error accessing database");
			
			Context.addTranslations(trans);
		}
	}
}