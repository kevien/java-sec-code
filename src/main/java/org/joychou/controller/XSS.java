package org.joychou.controller;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: JoyChou (joychou@joychou.org)
 * @date:   2018.01.02
 * @desc:   xss vuls code
 */

@Controller
@RequestMapping("/xss")
public class XSS {
    @RequestMapping("/print")
    @ResponseBody
    public static String ssrf_URLConnection(HttpServletRequest request)
    {
        // vul code
          String con = request.getParameter("con");
//        return con;

        // fix code 1
        // return encode(con);

        // fix code 2
         return StringEscapeUtils.escapeHtml(con);

    }

    public static String encode(String origin) {
        origin = StringUtils.replace(origin, "&", "&amp;");
        origin = StringUtils.replace(origin, "<", "&lt;");
        origin = StringUtils.replace(origin, ">", "&gt;");
        origin = StringUtils.replace(origin, "\"", "&quot;");
        origin = StringUtils.replace(origin, "'", "&#x27;");
        origin = StringUtils.replace(origin, "/", "&#x2F;");
        return origin;
    }
}
