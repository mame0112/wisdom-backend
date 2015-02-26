package util;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.mame.wisdom.WisdomControllerServlet;
import com.mame.wisdom.constant.WConstant;

public class DbgUtil {

	public final static Logger log = Logger
			.getLogger(WisdomControllerServlet.class.getName());

	public static void showLog(String tag, String message) {
		if (WConstant.IS_DEBUG) {
			if (message != null) {
				log.log(Level.WARNING, tag + ": " + message);
			}
		}
	}

}
