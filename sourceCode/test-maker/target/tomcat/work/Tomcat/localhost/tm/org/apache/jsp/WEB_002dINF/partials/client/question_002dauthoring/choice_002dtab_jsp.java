/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/7.0.47
 * Generated at: 2020-04-10 00:42:55 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.WEB_002dINF.partials.client.question_002dauthoring;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class choice_002dtab_jsp extends org.apache.jasper.runtime.HttpJspBase
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

      out.write("\r\n");
      out.write("\r\n");
      out.write("<div class=\"panel panel-primary\">\r\n");
      out.write("    <div class=\"panel-heading\" role=\"tab\" id=\"headingOne\">\r\n");
      out.write("        <h4 class=\"panel-title\">\r\n");
      out.write("            <a class=\"btn\" role=\"button\" data-toggle=\"collapse\" href=\"#collapseOne\" aria-expanded=\"true\" aria-controls=\"collapseOne\">\r\n");
      out.write("                题目选项详情\r\n");
      out.write("            </a>\r\n");
      out.write("        </h4>\r\n");
      out.write("    </div>\r\n");
      out.write("    <div id=\"collapseOne\" class=\"panel-collapse collapse in\" role=\"tabpanel\" aria-labelledby=\"headingOne\">\r\n");
      out.write("        <div class=\"panel-body\">\r\n");
      out.write("            <table id=\"q-choice-table\" class=\"table table-bordered table-striped table-condensed table-responsive table-hover\">\r\n");
      out.write("            </table>\r\n");
      out.write("        </div>\r\n");
      out.write("    </div>\r\n");
      out.write("</div>\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<div class=\"panel panel-info\">\r\n");
      out.write("    <div class=\"panel-heading\" role=\"tab\" id=\"headingTwo\">\r\n");
      out.write("        <h4 class=\"panel-title\">\r\n");
      out.write("            <a class=\"btn\" data-toggle=\"collapse\" href=\"#collapseTwo\" aria-expanded=\"false\" aria-controls=\"collapseTwo\">\r\n");
      out.write("                题目选项编辑框\r\n");
      out.write("            </a>\r\n");
      out.write("        </h4>\r\n");
      out.write("    </div>\r\n");
      out.write("    <div id=\"collapseTwo\" class=\"panel-collapse collapse in\" role=\"tabpanel\" aria-labelledby=\"headingTwo\">\r\n");
      out.write("        <div class=\"panel-body\">\r\n");
      out.write("            <div id=\"choice-image-container\" class=\"form-group\">\r\n");
      out.write("            </div>\r\n");
      out.write("            <div class=\"form-group\">\r\n");
      out.write("                <div class=\"col-md-5\">\r\n");
      out.write("                    <div  class=\"alert alert-warning alert-dismissible\" role=\"alert\">\r\n");
      out.write("                        <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-label=\"Close\"><span aria-hidden=\"true\">&times;</span></button>\r\n");
      out.write("                        <strong>注意！</strong> 要新增或者编辑题目选项，请先选择题目序号！\r\n");
      out.write("                    </div>\r\n");
      out.write("                </div>\r\n");
      out.write("            </div>\r\n");
      out.write("            <div class=\"form-group\">\r\n");
      out.write("                <label for=\"q-choice-id\" class=\"col-md-1 control-label\">ID</label>\r\n");
      out.write("                <div class=\"col-md-1\">\r\n");
      out.write("                    <input id=\"q-choice-id\" type=\"text\" class=\"form-control\" disabled>\r\n");
      out.write("                </div>\r\n");
      out.write("                <label for=\"q-choice-label-select\" class=\"col-md-2 col-lg-1 control-label\">序号</label>\r\n");
      out.write("                <div class=\"choice-label-list col-md-3\" style=\"width: auto;\">\r\n");
      out.write("                    <select id=\"q-choice-label-select\" name=\"choiceLabel\" class=\"form-control\" required>\r\n");
      out.write("                    </select>\r\n");
      out.write("                </div>\r\n");
      out.write("\r\n");
      out.write("                <label for=\"q-choice-answer\" class=\"col-md-2 control-label\">正确答案</label>\r\n");
      out.write("                <div class=\"col-md-2\">\r\n");
      out.write("                    <input type=\"checkbox\" id=\"q-choice-answer\" value=\"option1\"> <strong></strong>\r\n");
      out.write("                </div>\r\n");
      out.write("            </div>\r\n");
      out.write("            <div class=\"form-group\">\r\n");
      out.write("                <div class=\"col-md-12\">\r\n");
      out.write("                    <textarea id=\"item-choice-content\" name=\"choice-content\" class=\"rich-editable\" rows=\"5\">\r\n");
      out.write("                    </textarea>\r\n");
      out.write("                </div>\r\n");
      out.write("            </div>\r\n");
      out.write("            <div class=\"form-group\">\r\n");
      out.write("                <div class=\"col-md-offset-1 col-md-2\">\r\n");
      out.write("                    <button id=\"save-choice-btn\" type=\"button\" class=\"btn btn-success\">保存题目选项</button>\r\n");
      out.write("                </div>\r\n");
      out.write("            </div>\r\n");
      out.write("            <div class=\"msg-area form-group\">\r\n");
      out.write("            </div>\r\n");
      out.write("        </div>\r\n");
      out.write("    </div>\r\n");
      out.write("</div>\r\n");
      out.write("<script id=\"choice-image-list\" type=\"text/x-handlebars-template\">\r\n");
      out.write("    {{#each images}}\r\n");
      out.write("        <div class=\"col-md-3\">\r\n");
      out.write("            <div class=\"thumbnail\" data-index=\"{{@index}}\" data-id=\"{{id}}\">\r\n");
      out.write("                <a href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${ctx}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("{{path}}\" target=\"_blank\">\r\n");
      out.write("                    <img src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${ctx}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("{{path}}\" class=\"img-responsive img-thumbnail\" title=\"{{caption}}\">\r\n");
      out.write("                </a>\r\n");
      out.write("                <div class=\"caption\">\r\n");
      out.write("                    <h3 id=\"image-caption\" contenteditable=\"true\">{{caption}}</h3>\r\n");
      out.write("                    <p>\r\n");
      out.write("                        <button id=\"save-choice-image-btn\" class=\"btn-sm btn-primary\">保存</button>\r\n");
      out.write("                        <button id=\"delete-choice-image-btn\" class=\"btn-sm btn-danger\">删除</button>\r\n");
      out.write("                    </p>\r\n");
      out.write("                    <p><em>");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${ctx}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("{{path}}</em></p>\r\n");
      out.write("                </div>\r\n");
      out.write("            </div>\r\n");
      out.write("        </div>\r\n");
      out.write("    {{/each}}\r\n");
      out.write("</script>\r\n");
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
