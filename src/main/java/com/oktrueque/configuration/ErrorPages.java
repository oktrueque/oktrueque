package com.oktrueque.configuration;

import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class ErrorPages implements ErrorController {
    /**
     * Error Attributes in the Application
     */
    private final ErrorAttributes errorAttributes;

    private final static String ERROR_PATH = "/error";

    public ErrorPages(ErrorAttributes errorAttributes) {
        this.errorAttributes = errorAttributes;
    }

    @RequestMapping(value = ERROR_PATH)
    public String error(HttpServletRequest request, Model model) {
        Map<String, Object> errorAttr = getErrorAttributes(request, false);
        String path = errorAttr.get("path").toString();
        model.addAttribute("error", errorAttr);
        return "error";
    }

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }

    private Map<String, Object> getErrorAttributes(HttpServletRequest request, boolean includeStackTrace) {
        RequestAttributes requestAttributes = new ServletRequestAttributes(request);
        return this.errorAttributes.getErrorAttributes(requestAttributes, includeStackTrace);
    }
}
