/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/7.0.47
 * Generated at: 2020-04-13 11:37:14 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.WEB_002dINF.pages.client;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class paper_002dmanagement_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
  }

  public void _jspDestroy() {
  }

  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
        throws java.io.IOException, javax.servlet.ServletException {

    final javax.servlet.jsp.PageContext pageContext;
    javax.servlet.http.HttpSession session = null;
    final javax.servlet.ServletContext application;
    final javax.servlet.ServletConfig config;
    javax.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    javax.servlet.jsp.JspWriter _jspx_out = null;
    javax.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html; charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\n");
      out.write("\n");
      out.write("<div class=\"page-header\">\n");
      out.write("    <h1>组卷管理\n");
      out.write("        <small>");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${PROJECT.name}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("</small>\n");
      out.write("    </h1>\n");
      out.write("</div>\n");
      out.write("\n");
      out.write("<div class=\"container-fluid\">\n");
      out.write("    <div class=\"row\">\n");
      out.write("        <nav class=\"toolbar navbar navbar-default\">\n");
      out.write("            <div class=\"collapse navbar-collapse\">\n");
      out.write("                <ul class=\"nav navbar-nav\">\n");
      out.write("                    <li>\n");
      out.write("                        <a id=\"show-edit-paper-form-btn\" class=\"btn btn-primary navbar-btn-tm\" data-toggle=\"modal\"\n");
      out.write("                           role=\"button\"><i class=\"glyphicon glyphicon-plus-sign\"></i>新建组卷</a>\n");
      out.write("                    </li>\n");
      out.write("\n");
      out.write("                    <li>\n");
      out.write("                        <a id=\"reload-paper-btn\" href=\"#\" class=\"btn btn-primary navbar-btn-tm\" role=\"button\"><i\n");
      out.write("                                class=\"glyphicon glyphicon-refresh\"></i>刷新</a>\n");
      out.write("                    </li>\n");
      out.write("                    <li>\n");
      out.write("                        <form class=\"navbar-form\" role=\"search\" onsubmit=\"return false;\">\n");
      out.write("                            <div class=\"form-group\">\n");
      out.write("                                <input id=\"paper-keyword\" type=\"text\" class=\"form-control\" placeholder=\"试卷关键字\">\n");
      out.write("                                <a id=\"search-syllabus-btn\" class=\"btn btn-danger navbar-btn-tm\"><i\n");
      out.write("                                        class=\"glyphicon glyphicon-search\"></i></a>\n");
      out.write("                            </div>\n");
      out.write("                        </form>\n");
      out.write("                    </li>\n");
      out.write("                </ul>\n");
      out.write("            </div>\n");
      out.write("        </nav>\n");
      out.write("    </div>\n");
      out.write("    <div class=\"row\">\n");
      out.write("        ");
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/WEB-INF/partials/client/paper-management/new-paper-form.jsp", out, false);
      out.write("\n");
      out.write("    </div>\n");
      out.write("\n");
      out.write("    ");
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/WEB-INF/partials/pagination-snippet.jsp", out, false);
      out.write("\n");
      out.write("\n");
      out.write("    <div class=\"msg-area form-group\">\n");
      out.write("    </div>\n");
      out.write("    <div class=\"row\">\n");
      out.write("        <table id=\"paper-mgmt-table\" class=\"table table-striped table-bordered table-responsive table-condensed\">\n");
      out.write("        </table>\n");
      out.write("    </div>\n");
      out.write("</div>\n");
      out.write("\n");
      out.write("<script id=\"initial-status-transition\" type=\"text/x-handlebars-template\">\n");
      out.write("    <ul class=\"list-inline\">\n");
      out.write("        {{#each transitions}}\n");
      out.write("        <li>\n");
      out.write("            <button class=\"transition-initial btn btn-primary\" data-id=\"{{id}}\">{{name}}</button>\n");
      out.write("        </li>\n");
      out.write("        {{/each}}\n");
      out.write("    </ul>\n");
      out.write("</script>\n");
      out.write("\n");
      out.write("\n");
      out.write("<script src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${ctx}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("/assets/js/client/paper-management/manage-papers.js\"></script>\n");
      out.write("\n");
    } catch (java.lang.Throwable t) {
      if (!(t instanceof javax.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try { out.clearBuffer(); } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
