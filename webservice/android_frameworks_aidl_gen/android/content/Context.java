package android.content;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.opendatakit.consts.IntentConsts;
import org.opendatakit.utilities.ODKFileUtils;

import android.content.res.Resources;

public class Context {
	
	private static Map<Integer, String> translations = new HashMap<Integer, String>();
	
	public static void addTranslations(Map<Integer, String> trans ) {
		translations.putAll(trans);
	}
	
	public String getPackageName() {
		return IntentConsts.AppProperties.APPLICATION_NAME;
	}
	
	public Resources getResources() {
		return null;
	}
	
	public File getDir(String subDir, int accessMode) {
		return new File(ODKFileUtils.getDataFolder(subDir));
	}
	
	public String getString(int in, Object... objects) {
		String value = translations.get(in);
		if ( value == null ) {
			throw new IllegalStateException("Unrecognized translation! " + in);
		}
		if ( objects != null && objects.length != 0 ) {
			return String.format(value,  objects);
		} else {
			return value;
		}
	}
}