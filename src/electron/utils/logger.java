package electron.utils;


public class logger {
	public static void error(String msg) {
		System.err.println(msg);
	}
	public static void log(String msg) {
		System.out.println(msg);
	}
	public static void debug(String msg) {
		System.out.println("[DEBUG]"+msg);
	}
}
