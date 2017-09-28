package org.opendatakit.services;

import java.util.HashMap;

import android.content.Context;

public final class R {
	
	private R() {};
	
	public static final class string {
		private string() {};

		// from androidlibrary
	public static final int credential_type_none ;
	public static final int credential_type_username_password ;
	public static final int credential_type_google_account ;
	
	 	// from services
    public static final int app_name ;

    public static final int services_notice ;
    public static final int action_admin_settings ;
    public static final int action_settings ;
    public static final int action_about ;
    public static final int action_sync ;
    public static final int action_change_user ;
    public static final int action_resolve_conflict ;
    public static final int action_verify_server_settings ;

	/** Checkpoint and Conflict Resolution Stuff */
    public static final int all_conflicts_notice ;
    public static final int all_tables_scanned_for_conflicts ;
	public static final int cancel ;
	public static final int yes ;
	public static final int take_local ;
	public static final int take_server ;
	public static final int mark_resolved ;
	public static final int resolve_cannot_complete_message ;
	public static final int conflict_local_was_deleted_explanation ;
	public static final int conflict_server_was_deleted_explanation ;
    public static final int conflict_resolve_or_choose ;
	public static final int radio_button_message_restore_local_deleted ;
	public static final int radio_button_message_delete_local_deleted ;
	public static final int radio_button_message_restore_server_deleted ;
	public static final int radio_button_message_delete_server_deleted ;

    public static final int conflict_take_local_warning ;
    public static final int conflict_take_server_warning ;
    public static final int conflict_take_local_with_deltas_warning ;
	public static final int conflict_delete_local_confirmation_warning ;
	public static final int conflict_delete_on_server_confirmation_warning ;

	public static final int checkpoint_restore_complete_or_take_newest ;
	public static final int checkpoint_restore_incomplete_or_take_newest ;
	public static final int checkpoint_remove_or_take_newest ;

    public static final int select_checkpoint_to_resolve ;
    public static final int no_items_display_checkpoints ;
    public static final int checkpoint_take_all_oldest_or_remove ;
    public static final int checkpoint_take_all_newest ;
    public static final int silently_resolved_single_checkpoint ;
    public static final int silently_resolved_checkpoints ;

    public static final int checkpoint_radio_local ;
    public static final int checkpoint_radio_server ;
    public static final int checkpoint_take_oldest ;
	public static final int checkpoint_take_oldest_remove ;
	public static final int checkpoint_take_oldest_finalized ;
	public static final int checkpoint_take_oldest_incomplete ;
	public static final int checkpoint_take_newest ;
    public static final int checkpoint_take_newest_with_deltas ;

	public static final int checkpoint_delete_warning ;
	public static final int checkpoint_take_oldest_warning ;
	public static final int checkpoint_take_newest_warning ;
    public static final int checkpoint_take_newest_with_deltas_warning ;

    public static final int checkpoint_auto_apply ;
    public static final int checkpoint_auto_apply_all ;

    public static final int select_conflict_to_resolve ;
    public static final int no_items_display_conflicts ;
    public static final int conflict_take_all_local_updates ;
    public static final int conflict_take_all_server_updates ;
    public static final int silently_resolved_single_conflict ;
    public static final int silently_resolved_conflicts ;

    public static final int conflict_resolving_all ;
    public static final int resolving_row_n_of_m ;
    public static final int resolver_already_active ;
    public static final int done_resolving_rows ;

    public static final int conflict_radio_local ;
    public static final int conflict_radio_server ;
	public static final int conflict_take_local_updates ;
	public static final int conflict_take_server_updates ;
	public static final int conflict_take_local_with_deltas ;

	public static final int conflict_restore_with_server_changes ;
	public static final int conflict_restore_with_local_changes ;
	public static final int conflict_enforce_local_delete ;
	public static final int conflict_apply_delete_from_server ;

    public static final int conflict_auto_apply ;
    public static final int conflict_auto_apply_all ;

    public static final int conflict_database_consistency_error ;

    public static final int resolve_row_display_name ;
    /** Sync Service and Notifications stuff */

    /** progress dialog titles */
    public static final int sync_in_progress ;
    public static final int sync_app_level_files ;
    public static final int sync_table_level_files ;
    public static final int sync_row_data ;

    /** outcome dialog titles */
    public static final int sync_communications_error ;
    public static final int sync_device_internal_error ;
    public static final int sync_resync_because_config_reset_error ;
    public static final int sync_user_authorization_failure ;
    public static final int sync_server_configuration_failure ;
    public static final int sync_device_configuration_failure ;
    public static final int sync_conflicts_need_resolving ;
    public static final int sync_conflicts_text ;
    public static final int sync_successful ;
    public static final int sync_successful_text ;
    public static final int sync_complete_pending_attachments ;
    public static final int sync_complete_pending_attachments_text ;

    /** intermediate progress messages */
    public static final int sync_starting ;
    public static final int sync_getting_app_level_manifest ;

    public static final int sync_uploading_local_file ;
    public static final int sync_deleting_file_on_server ;
    public static final int sync_verifying_local_file ;
    public static final int sync_deleting_local_file ;

    public static final int sync_getting_table_manifest ;

    public static final int sync_table_data_sync_complete ;
    public static final int sync_table_data_sync_skipped ;
    public static final int sync_table_data_sync_pending_attachments ;
    public static final int sync_table_data_sync_with_conflicts ;
    public static final int sync_table_contains_checkpoints ;
    public static final int sync_table_erroneous_filter ;
    public static final int sync_table_erroneous_conflict_type ;

    public static final int sync_verifying_app_name_on_server ;
    public static final int sync_obtaining_user_permissions_from_server ;
    public static final int sync_retrieving_tables_list_from_server ;
    public static final int sync_verifying_table_schema_on_server ;
    public static final int sync_creating_local_table ;
    public static final int sync_dropping_local_table ;
    public static final int sync_table_level_file_sync_complete ;
    public static final int sync_table_level_sync_failed ;

    public static final int sync_getting_changed_rows_on_server ;
    public static final int sync_anaylzing_local_row_changes ;
    public static final int sync_applying_batch_server_row_changes ;
    public static final int sync_fetching_local_rows_in_batch_server_row_changes ;
    public static final int sync_succeeded_pulling_rows_from_server ;
    public static final int sync_failed_pulling_rows_from_server ;

    public static final int sync_calculating_rows_to_push_to_server ;
    public static final int sync_pushing_local_row_changes_to_server ;
    public static final int sync_server_row_updated ;
    public static final int sync_server_row_update_failed ;
    public static final int sync_server_row_update_denied ;
    public static final int sync_updating_data_etag_after_push_to_server ;
    public static final int sync_completed_push_to_server ;

    public static final int sync_upserting_server_row ;
    public static final int sync_altering_server_row ;
    public static final int sync_deleting_server_row ;
    public static final int sync_skipping_attachments_server_row ;
    public static final int sync_uploading_attachments_server_row ;
    public static final int sync_downloading_attachments_server_row ;
    public static final int sync_syncing_attachments_server_row ;
    public static final int sync_attachment_no_changes ;
    public static final int sync_count_attachment_changes ;
    public static final int sync_fetch_batch_attachment_changes ;
    public static final int sync_attachment_completed ;

    public static final int sync_deleting_local_row ;
    public static final int sync_deferring_delete_local_row ;
    public static final int sync_updating_local_row ;
    public static final int sync_inserting_local_row ;
    public static final int sync_marking_conflicting_local_row ;

    /** Sync User Interface stuff */
    public static final int sync_details ;
    public static final int sync_server_url ;
    public static final int sync_default_server_url ;
    public static final int sync_account ;
    public static final int sync_progress_state_label ;
    public static final int sync_progress_message_label ;
    public static final int sync_save_settings ;
    public static final int sync_auth_account ;
    public static final int sync_instance_files ;
    public static final int sync_defer_instance_files ;
    public static final int sync_start ;
    public static final int sync_reset_server ;

    public static final int sync_confirm_change_settings ;
    public static final int sync_change_settings_warning ;
    public static final int sync_save ;

    public static final int sync_choose_account ;
    public static final int sync_waiting_auth ;

    public static final int sync_confirm_reset_app_server ;
    public static final int sync_reset_app_server_warning ;
    public static final int sync_reset ;

    public static final int sync_other_auth_fail ;
    public static final int sync_auth_unsuccessful ;

    public static final int sync_configure_username_password ;
    public static final int sync_configure_google_account ;
    public static final int sync_configure_credentials ;

    public static final int verify_server_settings_start ;
    public static final int verify_server_settings_starting ;
    public static final int verifying_server_settings ;
    public static final int verify_server_setttings_successful ;
    public static final int verify_server_setttings_successful_text ;
    public static final int verify_server_settings_header ;
    public static final int click_to_verify_server_settings ;
    public static final int warning_no_user_roles ;

    /* sync service layer messages */
    public static final int sync_notification_syncing ;
    public static final int sync_notification_failure ;
    public static final int sync_notification_conflicts ;
    public static final int sync_notification_conflicts_text ;
    public static final int sync_notification_success_verify_complete ;
    public static final int sync_notification_success_verify_complete_text ;
    public static final int sync_notification_success_complete ;
    public static final int sync_notification_success_complete_text ;
    public static final int sync_notification_success_pending_attachments ;
    public static final int sync_notification_success_pending_attachments_text ;
    public static final int sync_status_network_transport_error ;
    public static final int sync_status_device_internal_error ;
    public static final int sync_status_authentication_error ;
    public static final int sync_status_internal_server_error ;
    public static final int sync_status_bad_gateway_or_client_config ;
    public static final int sync_status_request_or_protocol_error ;
    public static final int sync_status_appname_not_supported_by_server ;
    public static final int sync_status_server_missing_config_files ;
    public static final int sync_status_server_reset_failed_device_has_no_config_files ;
    public static final int sync_status_resync_because_config_has_been_reset_error ;
    /** settings screen stuff */
    public static final int action_bar_admin_settings ;
    public static final int admin_settings ;
    public static final int admin_access_settings ;
    public static final int user_access_preferences ;

    public static final int admin_password_settings_summary ;
    public static final int admin_password_disabled_click_to_set ;
    public static final int admin_password_settings ;
    public static final int admin_password ;
    public static final int change_admin_password ;
    public static final int enable_admin_password ;

    public static final int enter_admin_password ;

    public static final int click_to_change_password ;

    public static final int current_user ;

    public static final int change_user ;
    public static final int new_user ;
    public static final int logout ;
    public static final int authenticate_credentials ;

    public static final int sync_pending_changes ;
    public static final int resolve_pending_changes ;
    public static final int resolve_with_sync ;
    public static final int ignore_changes ;

    public static final int resolve_checkpoints_and_conflicts ;
    public static final int resolve_pending_checkpoints_and_conflicts ;

    public static final int anonymous_warning ;
    public static final int authentication_error ;

    public static final int enter_new_user ;
    public static final int enter_new_password ;
    public static final int verify_new_password ;
    public static final int password_changed ;
    public static final int admin_password_disabled ;
    public static final int admin_password_enabled ;
    public static final int password_mismatch ;
    public static final int show_password_text ;

    public static final int action_bar_general_settings ;
    public static final int action_bar_general_settings_admin_mode ;
    public static final int general_settings ;
    public static final int admin_general_settings ;
    public static final int opendatakit_website ;
    public static final int click_to_web ;

    public static final int server_settings_summary ;
    public static final int server ;
    public static final int server_restrictions_apply ;
    public static final int server_url ;
    public static final int change_server_url ;
    public static final int url_error ;
    public static final int url_error_whitespace ;

    public static final int restrict_server_settings_summary ;
    public static final int restrict_server ;
    public static final int credential ;
    public static final int change_credential ;

    public static final int username_password ;
    public static final int username ;
    public static final int change_username ;
    public static final int password ;
    public static final int change_server_password ;
    public static final int google_account ;
    public static final int selected_google_account_text ;
    public static final int no_account ;
    public static final int anonymous ;
    public static final int credential_type_prefix ;

    public static final int testing_support ;
    public static final int non_secure_authentication ;
    public static final int non_secure_summary ;

    public static final int restrict_device_settings_summary ;
    public static final int restrict_device ;

    public static final int device_settings_summary ;
    public static final int device ;
    public static final int device_restrictions_apply ;

    public static final int default_locale ;
    public static final int change_default_locale ;
    public static final int system_locale ;
    public static final int font_size ;
    public static final int change_font_size ;

    public static final int show_splash ;
    public static final int show_splash_summary ;
    public static final int splash_path ;
    public static final int default_splash_path ;
    public static final int select_another_image ;
    public static final int use_odk_default ;
    public static final int change_splash_path ;
    public static final int admin_change_splash_settings ;
    public static final int splash_media_save_failed ;

    public static final int tool_tables_settings ;
    public static final int tool_tables_restrictions_apply ;
    public static final int tool_tables_settings_summary ;
    public static final int custom_home_screen ;
    public static final int admin_tool_tables_settings ;
    public static final int admin_tool_tables_settings_summary ;
    public static final int change_custom_home_screen ;

    public static final int clear_configuration_settings ;
    public static final int click_to_clear_settings ;

    public static final int reset_settings ;
    public static final int confirm_reset_settings ;

    public static final int write_external_storage_rationale ;
    public static final int write_external_perm_denied ;
    public static final int success ;
		static {
			
			HashMap<Integer, String> trans = new HashMap<Integer, String>();
			int idx = 2000;
					
				// from androidlibrary
			credential_type_none  = ++idx; trans.put(idx,  "none");
			credential_type_username_password  = ++idx; trans.put(idx,  "username_password");
			credential_type_google_account  = ++idx; trans.put(idx,  "google_account");
			
				// from services
			app_name  = ++idx; trans.put(idx,  "ODK Services");

			services_notice  = ++idx; trans.put(idx,  "ODK Services provides database, file access, and data synchronization services to all ODK 2.0 applications.");
			action_admin_settings  = ++idx; trans.put(idx,  "Admin Settings");
			action_settings  = ++idx; trans.put(idx,  "Settings");
			action_about  = ++idx; trans.put(idx,  "About");
			action_sync  = ++idx; trans.put(idx,  "Sync");
			action_change_user  = ++idx; trans.put(idx,  "Change User/Logout");
			action_resolve_conflict  = ++idx; trans.put(idx,  "Resolve Conflicts");
			action_verify_server_settings  = ++idx; trans.put(idx,  "Update User Permissions");

			/** Checkpoint and Conflict Resolution Stuff */
			all_conflicts_notice  = ++idx; trans.put(idx,  "Please wait - scanning for conflicts&#8230;");
			all_tables_scanned_for_conflicts  = ++idx; trans.put(idx,  "All tables have been scanned for conflicts");
			cancel  = ++idx; trans.put(idx,  "Cancel");
			yes  = ++idx; trans.put(idx,  "Yes");
			take_local  = ++idx; trans.put(idx,  "Take Local Version");
			take_server  = ++idx; trans.put(idx,  "Take Server Version");
			mark_resolved  = ++idx; trans.put(idx,  "Mark Resolved");
			resolve_cannot_complete_message  = ++idx; trans.put(idx,  "A value has not been chosen for every conflicting column. The row cannot be resolved.");
			conflict_local_was_deleted_explanation  = ++idx; trans.put(idx,  "You have deleted a row someone else has edited.");
			conflict_server_was_deleted_explanation  = ++idx; trans.put(idx,  "Another user has deleted a row you have edited.");
			conflict_resolve_or_choose  = ++idx; trans.put(idx,  "Another user has edited the same row you have edited.");
			radio_button_message_restore_local_deleted  = ++idx; trans.put(idx,  "Restore and resolve.");
			radio_button_message_delete_local_deleted  = ++idx; trans.put(idx,  "Ignore changes, delete on server.");
			radio_button_message_restore_server_deleted  = ++idx; trans.put(idx,  "Restore and resolve.");
			radio_button_message_delete_server_deleted  = ++idx; trans.put(idx,  "Delete and lose local changes.");

			conflict_take_local_warning  = ++idx; trans.put(idx,  "Overwrite all server changes with the local changes?");
			conflict_take_server_warning  = ++idx; trans.put(idx,  "Overwrite all local changes with the server changes?");
			conflict_take_local_with_deltas_warning  = ++idx; trans.put(idx,  "Overwrite local and server changes with the selected values?");
			conflict_delete_local_confirmation_warning  = ++idx; trans.put(idx,  "Delete the row and lose your local changes?");
			conflict_delete_on_server_confirmation_warning  = ++idx; trans.put(idx,  "Delete row on server?");

			checkpoint_restore_complete_or_take_newest  = ++idx; trans.put(idx,  "Restore to earlier Finalized state or Save checkpoint as Incomplete");
			checkpoint_restore_incomplete_or_take_newest  = ++idx; trans.put(idx,  "Restore to previous Incomplete Save or Save checkpoint as Incomplete");
			checkpoint_remove_or_take_newest  = ++idx; trans.put(idx,  "Discard or Save checkpoint as Incomplete");

			select_checkpoint_to_resolve  = ++idx; trans.put(idx,  "Select the row with checkpoints to resolve");
			no_items_display_checkpoints  = ++idx; trans.put(idx,  "Please wait&#8230;\nSearching for checkpoint rows to resolve.");
			checkpoint_take_all_oldest_or_remove  = ++idx; trans.put(idx,  "Discard All Checkpoints");
			checkpoint_take_all_newest  = ++idx; trans.put(idx,  "Update All Checkpoints to Incomplete State");
			silently_resolved_single_checkpoint  = ++idx; trans.put(idx,  "A checkpoint on an unmodifiable row was silently rolled back.");
			silently_resolved_checkpoints  = ++idx; trans.put(idx,  "%1$d checkpoints on unmodifiable rows were silently rolled back.");

			checkpoint_radio_local  = ++idx; trans.put(idx,  "Apply:");
			checkpoint_radio_server  = ++idx; trans.put(idx,  "Restore:");
			checkpoint_take_oldest  = ++idx; trans.put(idx,  "Discard Checkpoint");
			checkpoint_take_oldest_remove  = ++idx; trans.put(idx,  "Discard");
			checkpoint_take_oldest_finalized  = ++idx; trans.put(idx,  "Restore to prior Finalized State");
			checkpoint_take_oldest_incomplete  = ++idx; trans.put(idx,  "Restore to prior Incomplete State");
			checkpoint_take_newest  = ++idx; trans.put(idx,  "Apply all changes and Save as Incomplete");
			checkpoint_take_newest_with_deltas  = ++idx; trans.put(idx,  "Apply Indicated Changes and Save as Incomplete");

			checkpoint_delete_warning  = ++idx; trans.put(idx,  "Delete entirely?");
			checkpoint_take_oldest_warning  = ++idx; trans.put(idx,  "Discard the Checkpoint and Proceed?");
			checkpoint_take_newest_warning  = ++idx; trans.put(idx,  "Save all changes as Incomplete and Proceed?");
			checkpoint_take_newest_with_deltas_warning  = ++idx; trans.put(idx,  "Apply Indicated Changes, Save as Incomplete and Proceed?");

			checkpoint_auto_apply  = ++idx; trans.put(idx,  "No user-defined field differences &#8212; automatically discarding checkpoint.");
			checkpoint_auto_apply_all  = ++idx; trans.put(idx,  "No user-defined field differences in any rows\nAutomatically discarded checkpoints.");

			select_conflict_to_resolve  = ++idx; trans.put(idx,  "Select the row with conflicts to resolve");
			no_items_display_conflicts  = ++idx; trans.put(idx,  "Please Wait&#8230;\nSearching for conflicting rows to resolve.");
			conflict_take_all_local_updates  = ++idx; trans.put(idx,  "Take Local Version - Reject All Server Changes");
			conflict_take_all_server_updates  = ++idx; trans.put(idx,  "Take Server Version - Discard Local Changes");
			silently_resolved_single_conflict  = ++idx; trans.put(idx,  "A conflict on an unmodifiable row was silently resolved using the server\'s changes.");
			silently_resolved_conflicts  = ++idx; trans.put(idx,  "%1$d conflicts on unmodifiable rows were silently resolved using the server\'s changes.");

			conflict_resolving_all  = ++idx; trans.put(idx,  "Resolving All Conflicts");
			resolving_row_n_of_m  = ++idx; trans.put(idx,  "Resolving row %1$d of %2$d");
			resolver_already_active  = ++idx; trans.put(idx,  "Action already in progress");
			done_resolving_rows  = ++idx; trans.put(idx,  "Completed");

			conflict_radio_local  = ++idx; trans.put(idx,  "Local:");
			conflict_radio_server  = ++idx; trans.put(idx,  "Server:");
			conflict_take_local_updates  = ++idx; trans.put(idx,  "Take Local Version");
			conflict_take_server_updates  = ++idx; trans.put(idx,  "Take Server Version");
			conflict_take_local_with_deltas  = ++idx; trans.put(idx,  "Merge Changes as Indicated Below");

			conflict_restore_with_server_changes  = ++idx; trans.put(idx,  "Restore local record with server updates");
			conflict_restore_with_local_changes  = ++idx; trans.put(idx,  "Restore server record with local updates");
			conflict_enforce_local_delete  = ++idx; trans.put(idx,  "Delete record on server");
			conflict_apply_delete_from_server  = ++idx; trans.put(idx,  "Delete local record");

			conflict_auto_apply  = ++idx; trans.put(idx,  "No user-defined field differences &#8212; automatically taking server metadata revisions.");
			conflict_auto_apply_all  = ++idx; trans.put(idx,  "No user-defined field differences in any rows.\nAutomatically took server revisions.");

			conflict_database_consistency_error  = ++idx; trans.put(idx,  "Database consistency issue when resolving conflicts &#8212; contact the ODK Team");

			resolve_row_display_name  = ++idx; trans.put(idx,  "Form: %1$s Instance: %2$s");
			/** Sync Service and Notifications stuff */

			/** progress dialog titles */
			sync_in_progress  = ++idx; trans.put(idx,  "Syncing in progress&#8230;");
			sync_app_level_files  = ++idx; trans.put(idx,  "Syncing application configuration&#8230;");
			sync_table_level_files  = ++idx; trans.put(idx,  "Syncing table and form configuration&#8230;");
			sync_row_data  = ++idx; trans.put(idx,  "Syncing data&#8230;");

			/** outcome dialog titles */
			sync_communications_error  = ++idx; trans.put(idx,  "A communications error occurred");
			sync_device_internal_error  = ++idx; trans.put(idx,  "An internal error occurred");
			sync_resync_because_config_reset_error  = ++idx; trans.put(idx,  "The server config was reset during this sync. Please restart.");
			sync_user_authorization_failure  = ++idx; trans.put(idx,  "User authorization failure");
			sync_server_configuration_failure  = ++idx; trans.put(idx,  "Server configuration failure");
			sync_device_configuration_failure  = ++idx; trans.put(idx,  "Device configuration failure");
			sync_conflicts_need_resolving  = ++idx; trans.put(idx,  "Conflicts detected");
			sync_conflicts_text  = ++idx; trans.put(idx,  "Other users have modified the same entries that you have.\r\nYou now will be directed to merge your changes with theirs.");
			sync_successful  = ++idx; trans.put(idx,  "Sync successful");
			sync_successful_text  = ++idx; trans.put(idx,  "Success! Your application content (data and configuration) matches that on the server.");
			sync_complete_pending_attachments  = ++idx; trans.put(idx,  "Sync (without attachments) successful");
			sync_complete_pending_attachments_text  = ++idx; trans.put(idx,  "Configuration and data have been sync\'d, but not all attachments have been sync\'d. Most common reasons include:\n\t-You or another user elected not to fully sync attachments\n\t-There was a problem transferring the files");

			/** intermediate progress messages */
			sync_starting  = ++idx; trans.put(idx,  "Starting sync&#8230;");
			sync_getting_app_level_manifest  = ++idx; trans.put(idx,  "Retrieving list of application files from server.");

			sync_uploading_local_file  = ++idx; trans.put(idx,  "Uploading %1$s to server.");
			sync_deleting_file_on_server  = ++idx; trans.put(idx,  "Deleting %1$s on server.");
			sync_verifying_local_file  = ++idx; trans.put(idx,  "Verifying %1$s matches version on server.");
			sync_deleting_local_file  = ++idx; trans.put(idx,  "Deleting %1$s on device.");

			sync_getting_table_manifest  = ++idx; trans.put(idx,  "Retrieving list of %1$s files from server.");

			sync_table_data_sync_complete  = ++idx; trans.put(idx,  "Completed sync of %1$s dataset with server.");
			sync_table_data_sync_skipped  = ++idx; trans.put(idx,  "Skipped sync of %1$s dataset (syncing config failed).");
			sync_table_data_sync_pending_attachments  = ++idx; trans.put(idx,  "Partially completed sync of %1$s dataset with server. Not all attachments synced.");
			sync_table_data_sync_with_conflicts  = ++idx; trans.put(idx,  "Partially completed sync of %1$s dataset with server. Resolve conflicts and resync.");
			sync_table_contains_checkpoints  = ++idx; trans.put(idx,  "Checkpoints must be resolved before sync can proceed.");
			sync_table_erroneous_filter  = ++idx; trans.put(idx,  "Internal error when querying database.");
			sync_table_erroneous_conflict_type  = ++idx; trans.put(idx,  "Internal error when resolving server data against locally conflicting row.");

			sync_verifying_app_name_on_server  = ++idx; trans.put(idx,  "Verifying server supports this user application name.");
			sync_obtaining_user_permissions_from_server  = ++idx; trans.put(idx,  "Retrieving user permissions from server.");
			sync_retrieving_tables_list_from_server  = ++idx; trans.put(idx,  "Retrieving list of tables from server.");
			sync_verifying_table_schema_on_server  = ++idx; trans.put(idx,  "Verifying %1$s schema matches that on server.");
			sync_creating_local_table  = ++idx; trans.put(idx,  "Creating %1$s&#8230;");
			sync_dropping_local_table  = ++idx; trans.put(idx,  "Deleting %1$s from device&#8230;");
			sync_table_level_file_sync_complete  = ++idx; trans.put(idx,  "Completed sync of all %1$s configuration files.");
			sync_table_level_sync_failed  = ++idx; trans.put(idx,  "Failed table %1$s configuration sync.");

			sync_getting_changed_rows_on_server  = ++idx; trans.put(idx,  "Fetching %1$s dataset changes from server.");
			sync_anaylzing_local_row_changes  = ++idx; trans.put(idx,  "Collecting %1$s local row changes to send to server.");
			sync_applying_batch_server_row_changes  = ++idx; trans.put(idx,  "Applying a batch of %1$s row updates from server.");
			sync_fetching_local_rows_in_batch_server_row_changes  = ++idx; trans.put(idx,  "Fetching %1$s rows matching row updates from server.");
			sync_succeeded_pulling_rows_from_server  = ++idx; trans.put(idx,  "Successfully pulled all %1$s row updates from server.");
			sync_failed_pulling_rows_from_server  = ++idx; trans.put(idx,  "Failed while pulling %1$s row updates from server.");

			sync_calculating_rows_to_push_to_server  = ++idx; trans.put(idx,  "Counting row changes in %1$s to send to server.");
			sync_pushing_local_row_changes_to_server  = ++idx; trans.put(idx,  "Pushing row changes in %1$s to server.");
			sync_server_row_updated  = ++idx; trans.put(idx,  "Updating %1$s dataset row %2$d of %3$d on server succeeded.");
			sync_server_row_update_failed  = ++idx; trans.put(idx,  "Updating %1$s dataset row %2$d of %3$d on server failed.");
			sync_server_row_update_denied  = ++idx; trans.put(idx,  "Updating %1$s dataset row %2$d of %3$d on server denied.");
			sync_updating_data_etag_after_push_to_server  = ++idx; trans.put(idx,  "Completed a bulk-update of %1$s on server.");
			sync_completed_push_to_server  = ++idx; trans.put(idx,  "Successfully updated %1$s on server.");

			sync_upserting_server_row  = ++idx; trans.put(idx,  "Sending %1$s dataset row %2$d of %3$d to server.");
			sync_altering_server_row  = ++idx; trans.put(idx,  "Sending %1$s dataset row %2$d of %3$d to server.");
			sync_deleting_server_row  = ++idx; trans.put(idx,  "Deleting %1$s dataset row %2$d of %3$d on server.");
			sync_skipping_attachments_server_row  = ++idx; trans.put(idx,  "Skipping %1$s attachments for row %2$d of %3$d on server.");
			sync_uploading_attachments_server_row  = ++idx; trans.put(idx,  "Uploading %1$s attachments for row %2$d of %3$d to server.");
			sync_downloading_attachments_server_row  = ++idx; trans.put(idx,  "Downloading %1$s attachments for row %2$d of %3$d from server.");
			sync_syncing_attachments_server_row  = ++idx; trans.put(idx,  "Syncing %1$s attachments for row %2$d of %3$d with server.");
			sync_attachment_no_changes  = ++idx; trans.put(idx,  "No attachments to sync for %1$s.");
			sync_count_attachment_changes  = ++idx; trans.put(idx,  "Counting rows with attachments to sync for %1$s");
			sync_fetch_batch_attachment_changes  = ++idx; trans.put(idx,  "Fetching first batch of rows with attachments to sync on %1$s");
			sync_attachment_completed  = ++idx; trans.put(idx,  "Completed syncing attachments for %1$s.");

			sync_deleting_local_row  = ++idx; trans.put(idx,  "Deleting %1$s local dataset row %2$d of %3$d.");
			sync_deferring_delete_local_row  = ++idx; trans.put(idx,  "Deferring full delete %1$s local dataset row %2$d of %3$d.");
			sync_updating_local_row  = ++idx; trans.put(idx,  "Updating %1$s local dataset row %2$d of %3$d.");
			sync_inserting_local_row  = ++idx; trans.put(idx,  "Inserting %1$s local dataset row %2$d of %3$d.");
			sync_marking_conflicting_local_row  = ++idx; trans.put(idx,  "Mark conflicting %1$s local dataset row %2$d of %3$d.");

			/** Sync User Interface stuff */
			sync_details  = ++idx; trans.put(idx,  "Sync Configuration:");
			sync_server_url  = ++idx; trans.put(idx,  "Server URL");
			sync_default_server_url  = ++idx; trans.put(idx,  "http://opendatakit-2.appspot.com");
			sync_account  = ++idx; trans.put(idx,  "Account:");
			sync_progress_state_label  = ++idx; trans.put(idx,  "Sync Progress State");
			sync_progress_message_label  = ++idx; trans.put(idx,  "Sync Progress Message");
			sync_save_settings  = ++idx; trans.put(idx,  "Save settings");
			sync_auth_account  = ++idx; trans.put(idx,  "Authorize account");
			sync_instance_files  = ++idx; trans.put(idx,  "Sync Instance Attachments");
			sync_defer_instance_files  = ++idx; trans.put(idx,  "Defer Instance Attachments");
			sync_start  = ++idx; trans.put(idx,  "Sync now");
			sync_reset_server  = ++idx; trans.put(idx,  "Reset App Server");

			sync_confirm_change_settings  = ++idx; trans.put(idx,  "Confirm Change Settings");
			sync_change_settings_warning  = ++idx; trans.put(idx,  "If you change your settings, tables you have sync\'d now may no longer be able to be sync\'d.");
			sync_save  = ++idx; trans.put(idx,  "Save");

			sync_choose_account  = ++idx; trans.put(idx,  "Please choose an account");
			sync_waiting_auth  = ++idx; trans.put(idx,  "Waiting on authentication");

			sync_confirm_reset_app_server  = ++idx; trans.put(idx,  "Confirm Reset App Server");
			sync_reset_app_server_warning  = ++idx; trans.put(idx,  "Resetting the App Server will clear all data currently on the server.");
			sync_reset  = ++idx; trans.put(idx,  "Reset");

			sync_other_auth_fail  = ++idx; trans.put(idx,  "Authorization request failed %1$s");
			sync_auth_unsuccessful  = ++idx; trans.put(idx,  "Please resolve the reported authorization issues and try again.");

			sync_configure_username_password  = ++idx; trans.put(idx,  "Username authentication was selected but a username and password are not configured. Please update your settings.");
			sync_configure_google_account  = ++idx; trans.put(idx,  "Google account authentication was selected but a Google account has not been configured. Please update your settings.");
			sync_configure_credentials  = ++idx; trans.put(idx,  "No authentication type has been selected. Please update your settings.");

			verify_server_settings_start  = ++idx; trans.put(idx,  "Verify User Permissions");
			verify_server_settings_starting  = ++idx; trans.put(idx,  "Starting fetch of user permissions&#8230;");
			verifying_server_settings  = ++idx; trans.put(idx,  "Verifying User Permissions&#8230;");
			verify_server_setttings_successful  = ++idx; trans.put(idx,  "Verification successful");
			verify_server_setttings_successful_text  = ++idx; trans.put(idx,  "Success! User permissions have been updated.");
			verify_server_settings_header  = ++idx; trans.put(idx,  "Verify User Permissions");
			click_to_verify_server_settings  = ++idx; trans.put(idx,  "Click to Verify User Permissions");
			warning_no_user_roles  = ++idx; trans.put(idx,  "Warning: User permissions have not been verified!\r\nRunning with reduced privileges.");

			/* sync service layer messages */
			sync_notification_syncing  = ++idx; trans.put(idx,  "ODK Sync In Progress %1$s");
			sync_notification_failure  = ++idx; trans.put(idx,  "ODK Sync %1$s Error");
			sync_notification_conflicts  = ++idx; trans.put(idx,  "ODK Sync %1$s Conflicts Exist");
			sync_notification_conflicts_text  = ++idx; trans.put(idx,  "%1$d tables have data conflicts or checkpoints and were not completely synced.");
			sync_notification_success_verify_complete  = ++idx; trans.put(idx,  "ODK Sync %1$s Server Settings Verified");
			sync_notification_success_verify_complete_text  = ++idx; trans.put(idx,  "ODK User Credentials and Permissions Updated");
			sync_notification_success_complete  = ++idx; trans.put(idx,  "ODK Sync %1$s Completed Successfully");
			sync_notification_success_complete_text  = ++idx; trans.put(idx,  "Sync Completed Successfully");
			sync_notification_success_pending_attachments  = ++idx; trans.put(idx,  "ODK Sync %1$s Completed Pending Attachments");
			sync_notification_success_pending_attachments_text  = ++idx; trans.put(idx,  "Sync Completed with %1$d tables having out-of-date row attachments.");
			sync_status_network_transport_error  = ++idx; trans.put(idx,  "Network Error.\r\nPlease verify your device browser can access your server.");
			sync_status_device_internal_error  = ++idx; trans.put(idx,  "Internal Error.\r\nPlease submit a bug report");
			sync_status_authentication_error  = ++idx; trans.put(idx,  "Authentication Error.\r\nPlease verify your username/password or your account access.");
			sync_status_internal_server_error  = ++idx; trans.put(idx,  "Internal Server Error (out of quota?).\r\nPlease notify your server administrator.");
			sync_status_bad_gateway_or_client_config  = ++idx; trans.put(idx,  "WiFi Gateway or Configuration Error.\r\nPlease verify the server URL and that your device browser can access your server.");
			sync_status_request_or_protocol_error  = ++idx; trans.put(idx,  "Protocol Error.\r\nPlease verify that the server and device are compatible.");
			sync_status_appname_not_supported_by_server  = ++idx; trans.put(idx,  "Server is not configured for this appName.\r\nPlease verify server configuration.");
			sync_status_server_missing_config_files  = ++idx; trans.put(idx,  "Server does not have all app-level or table-level configuration for this APK version.\r\nDid you intend to reset the server?");
			sync_status_server_reset_failed_device_has_no_config_files  = ++idx; trans.put(idx,  "Device does not have any configuration.\r\nDid you intend to sync to server?");
			sync_status_resync_because_config_has_been_reset_error  = ++idx; trans.put(idx,  "The app server has been reset since the last time you synced, and the schema has changed. The sync has been aborted.");
			/** settings screen stuff */
			action_bar_admin_settings  = ++idx; trans.put(idx,  "%1$s &gt; Admin Settings");
			admin_settings  = ++idx; trans.put(idx,  "Admin Settings");
			admin_access_settings  = ++idx; trans.put(idx,  "Tap for admin access to settings");
			user_access_preferences  = ++idx; trans.put(idx,  "User can change General Settings items:");

			admin_password_settings_summary  = ++idx; trans.put(idx,  "Admin password Enabled. Click to change admin password");
			admin_password_disabled_click_to_set  = ++idx; trans.put(idx,  "No Admin password. Click to enable.");
			admin_password_settings  = ++idx; trans.put(idx,  "Admin Password");
			admin_password  = ++idx; trans.put(idx,  "Admin Password");
			change_admin_password  = ++idx; trans.put(idx,  "Change admin password");
			enable_admin_password  = ++idx; trans.put(idx,  "Enable user restrictions");

			enter_admin_password  = ++idx; trans.put(idx,  "Enter Admin password:");

			click_to_change_password  = ++idx; trans.put(idx,  "Click to change password");

			current_user  = ++idx; trans.put(idx,  "Currently logged in as:");

			change_user  = ++idx; trans.put(idx,  "Change User");
			new_user  = ++idx; trans.put(idx,  "Authenticate New User");
			logout  = ++idx; trans.put(idx,  "Log Out");
			authenticate_credentials  = ++idx; trans.put(idx,  "Authenticate Credentials");

			sync_pending_changes  = ++idx; trans.put(idx,  "Sync Pending Changes");
			resolve_pending_changes  = ++idx; trans.put(idx,  "You have changes that have not been synced to" +
				" the server. If you change users or logout before syncing these changes, you may lose them.");
			resolve_with_sync  = ++idx; trans.put(idx,  "Sync Changes");
			ignore_changes  = ++idx; trans.put(idx,  "Ignore Unsynced Changes");

			resolve_checkpoints_and_conflicts  = ++idx; trans.put(idx,  "Resolve Checkpoints and Conflicts");
			resolve_pending_checkpoints_and_conflicts  = ++idx; trans.put(idx,  "You have checkpoint rows and/or conflicts with the server that must be resolved before you can change your authenticated user.");

			anonymous_warning  = ++idx; trans.put(idx,  "Authenticate new credentials?\r\n\r\nWarning: If you choose not verify your credentials you will be logged out.");
			authentication_error  = ++idx; trans.put(idx,  "Authentication Error");

			enter_new_user  = ++idx; trans.put(idx,  "Enter new username:");
			enter_new_password  = ++idx; trans.put(idx,  "Enter new password:");
			verify_new_password  = ++idx; trans.put(idx,  "Re-enter new password");
			password_changed  = ++idx; trans.put(idx,  "Password successfully changed");
			admin_password_disabled  = ++idx; trans.put(idx,  "Admin password disabled");
			admin_password_enabled  = ++idx; trans.put(idx,  "Admin password enabled");
			password_mismatch  = ++idx; trans.put(idx,  "Sorry, passwords do not match!");
			show_password_text  = ++idx; trans.put(idx,  "Show password");

			action_bar_general_settings  = ++idx; trans.put(idx,  "%1$s &gt; General Settings");
			action_bar_general_settings_admin_mode  = ++idx; trans.put(idx,  "%1$s &gt; General Settings (Admim)");
			general_settings  = ++idx; trans.put(idx,  "General Settings");
			admin_general_settings  = ++idx; trans.put(idx,  "Admin Access to Settings");
			opendatakit_website  = ++idx; trans.put(idx,  "Open Data Kit user documentation");
			click_to_web  = ++idx; trans.put(idx,  "Tap to visit http://opendatakit.org");

			server_settings_summary  = ++idx; trans.put(idx,  "User Identity, Authentication and Server Configuration");
			server  = ++idx; trans.put(idx,  "Server Settings");
			server_restrictions_apply  = ++idx; trans.put(idx,  "Server Settings (non-Admin restrictions apply)");
			server_url  = ++idx; trans.put(idx,  "Server URL");
			change_server_url  = ++idx; trans.put(idx,  "Server URL");
			url_error  = ++idx; trans.put(idx,  "Sorry, invalid URL!");
			url_error_whitespace  = ++idx; trans.put(idx,  "whitespace is not allowed in urls");

			restrict_server_settings_summary  = ++idx; trans.put(idx,  "Limit non-Admin ability to change Server Settings");
			restrict_server  = ++idx; trans.put(idx,  "Manage ability to change Server Settings");
			credential  = ++idx; trans.put(idx,  "Server Sign-on Credential");
			change_credential  = ++idx; trans.put(idx,  "Change Sign-on Credential");

			username_password  = ++idx; trans.put(idx,  "Username and/or Password");
			username  = ++idx; trans.put(idx,  "Username");
			change_username  = ++idx; trans.put(idx,  "ODK Username");
			password  = ++idx; trans.put(idx,  "Server Password");
			change_server_password  = ++idx; trans.put(idx,  "Server Password");
			google_account  = ++idx; trans.put(idx,  "Select Google Account");
			selected_google_account_text  = ++idx; trans.put(idx,  "Google Account");
			no_account  = ++idx; trans.put(idx,  "No account");
			anonymous  = ++idx; trans.put(idx,  "None (anonymous access)");
			credential_type_prefix  = ++idx; trans.put(idx,  "Credentials: ");

			testing_support  = ++idx; trans.put(idx,  "Non-Production (Test) Settings:");
			non_secure_authentication  = ++idx; trans.put(idx,  "Allow unsafe/unsecure Authentication");
			non_secure_summary  = ++idx; trans.put(idx,  "Authenticate over http: (testing support)");

			restrict_device_settings_summary  = ++idx; trans.put(idx,  "Limit non-Admin ability to change Device Settings");
			restrict_device  = ++idx; trans.put(idx,  "Manage ability to change Device Settings");

			device_settings_summary  = ++idx; trans.put(idx,  "Device-specific Configuration");
			device  = ++idx; trans.put(idx,  "Device Settings");
			device_restrictions_apply  = ++idx; trans.put(idx,  "Device Settings (non-Admin restrictions apply)");

			default_locale  = ++idx; trans.put(idx,  "Default Locale");
			change_default_locale  = ++idx; trans.put(idx,  "Change Default Locale");
			system_locale  = ++idx; trans.put(idx,  "Device Locale (%1$s)");
			font_size  = ++idx; trans.put(idx,  "Text Font Size");
			change_font_size  = ++idx; trans.put(idx,  "Text Font Size");

			show_splash  = ++idx; trans.put(idx,  "Show Splash Screen");
			show_splash_summary  = ++idx; trans.put(idx,  "Shows when application starts");
			splash_path  = ++idx; trans.put(idx,  "Selected Splash Image");
			default_splash_path  = ++idx; trans.put(idx,  "ODK Default");
			select_another_image  = ++idx; trans.put(idx,  "Select Another Image");
			use_odk_default  = ++idx; trans.put(idx,  "Use Default Image");
			change_splash_path  = ++idx; trans.put(idx,  "Change Image");
			admin_change_splash_settings  = ++idx; trans.put(idx,  "Change Splash Screen settings");
			splash_media_save_failed  = ++idx; trans.put(idx,  "Unable to copy selected splash screen image");

			tool_tables_settings  = ++idx; trans.put(idx,  "Tables-specific Settings");
			tool_tables_restrictions_apply  = ++idx; trans.put(idx,  "Tables-specific settings (non-Admin restrictions apply)");
			tool_tables_settings_summary  = ++idx; trans.put(idx,  "ODK Tables-specific settings");
			custom_home_screen  = ++idx; trans.put(idx,  "Use custom home screen");
			admin_tool_tables_settings  = ++idx; trans.put(idx,  "Manage ability to change Tables-specific Settings");
			admin_tool_tables_settings_summary  = ++idx; trans.put(idx,  "Limit non-Admin ability to change Tables-specific Settings");
			change_custom_home_screen  = ++idx; trans.put(idx,  "Change use of custom home screen");

			clear_configuration_settings  = ++idx; trans.put(idx,  "Reset configuration");
			click_to_clear_settings  = ++idx; trans.put(idx,  "Click to clear settings");

			reset_settings  = ++idx; trans.put(idx,  "Configuration Reset");
			confirm_reset_settings  = ++idx; trans.put(idx,  "Device-specific and user identity settings will be cleared "
					+ "and all tools will re-run their initialization logic.\n\nDo you wish to proceed?");

			write_external_storage_rationale  = ++idx; trans.put(idx,  "ODK Services needs to access external storage to store resources and interact with other ODK apps");
			write_external_perm_denied  = ++idx; trans.put(idx,  "ODK Services cannot function without write access to external storage");
			success  = ++idx; trans.put(idx,  "Success");

			Context.addTranslations(trans);
		}
	};

}
