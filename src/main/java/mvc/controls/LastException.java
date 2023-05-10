package mvc.controls;

import java.io.PrintWriter;
import java.io.StringWriter;

/** 最新の例外格納クラス
 * @author none **/
public class LastException {
    protected static String _LastExcepTitle;
    protected static String _LastExcepPlace;
    protected static String _LastExcepParam;
    protected static String _LastExcepMessage;
    protected static String _LastExcepTrace;

    /** 最新の例外情報をセットします
     * @param method 例外が発生したメソッド名
     * @param param 例外の発生したメソッドが引き受けた引数、参考値など
     * @param ex Exceptionまたは派生クラス **/
    public static void SetLastException(String method, String param, Exception ex) {
        _LastExcepTitle = ex.getClass().getName();
        _LastExcepPlace = method;
        _LastExcepParam = param;
        _LastExcepMessage = ex.getMessage();
        var sw = new StringWriter();
        try(var pw = new PrintWriter(sw);) {
            ex.printStackTrace(pw);
            pw.flush();
            _LastExcepTrace = sw.toString();
        }
    }

    public static void LogWrite() {
		/*Logger logger = Logger.getLogger(OriginalUncaughtException.class.getName());
		try {
			Handler handler = new FileHandler("Exception.txt", true);
			handler.setFormatter(new SimpleFormatter()); //デフォルトはxml形式なのでフォーマットを変更
			logger.addHandler(handler);
		} catch (SecurityException | IOException e) {
			e.printStackTrace();
		}
		logger.log(Level.WARNING, _LastExcepTrace);*/
    }

    /** 最新の例外のクラス名が格納されます
     * @return String **/
    public static String getLastExcepTitle() {
        return _LastExcepTitle;
    }
    /** 最新の例外のクラス名が格納されます
     * @param value **/
    public static void setLastExcepTitle(String value) {
        _LastExcepTitle = value;
    }

    /** 最新の例外のメソッド名が格納されます
     * @return String **/
    public static String getLastExcepPlace() {
        return _LastExcepPlace;
    }
    /** 最新の例外のメソッド名が格納されます
     * @param value **/
    public static void setLastExcepPlace(String value) {
        _LastExcepPlace = value;
    }

    /** 最新の例外のパラメータが格納されます<br />
     * (メソッドに与えた引数や参考になる情報など)
     * @return String **/
    public static String getLastExcepParam() {
        return _LastExcepParam;
    }
    /** 最新の例外のパラメータが格納されます<br />
     * (メソッドに与えた引数や参考になる情報など)
     * @param value **/
    public static void setLastExcepParam(String value) {
        _LastExcepParam = value;
    }

    /** 最新の例外のメッセージが格納されます
     * @return String **/
    public static String getLastExcepMessage() {
        return _LastExcepMessage;
    }
    /** 最新の例外のメッセージが格納されます
     * @param value **/
    public static void setLastExcepMessage(String value) {
        _LastExcepMessage = value;
    }

    /** 最新の例外のスタックトレースが格納されます
     * @return String **/
    public static String getLastExcepTrace() {
        return _LastExcepTrace;
    }
    /** 最新の例外のメッセージが格納されます
     * @param value **/
    public static void setLastExcepTrace(String value) {
        _LastExcepTrace = value;
    }
}