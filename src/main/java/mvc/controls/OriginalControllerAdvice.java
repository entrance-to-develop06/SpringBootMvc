package mvc.controls;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/** Controllerクラスでの例外発生を一括で集約するクラス
 * @author none**/
@ControllerAdvice
public class OriginalControllerAdvice extends ResponseEntityExceptionHandler {
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView handleException(RuntimeException ex, WebRequest request) {
        LastException._LastExcepTitle = ex.getClass().getName();
        LastException._LastExcepPlace = "";
        LastException._LastExcepParam = HttpStatus.INTERNAL_SERVER_ERROR.toString();
        LastException._LastExcepMessage = ex.getMessage();
        var sw = new StringWriter();
        try(var pw = new PrintWriter(sw);) {
            ex.printStackTrace(pw);
            pw.flush();
            LastException._LastExcepTrace = sw.toString();
        }
        //LogWrite();
        ModelAndView mandv = new ModelAndView();
        mandv.addObject("btitle", "アプリケーションで例外が発生しました。");
        mandv.addObject("title", LastException._LastExcepTitle);
        mandv.addObject("place", LastException._LastExcepPlace);
        mandv.addObject("code", LastException._LastExcepParam);
        mandv.addObject("msg", LastException._LastExcepMessage);
        mandv.addObject("trace", LastException._LastExcepTrace);
        mandv.setViewName("/error/exception");

        return mandv;
    }
}