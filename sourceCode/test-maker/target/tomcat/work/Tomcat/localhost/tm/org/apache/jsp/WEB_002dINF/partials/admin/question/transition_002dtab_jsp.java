/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/7.0.47
 * Generated at: 2020-04-09 16:26:27 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.WEB_002dINF.partials.admin.question;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class transition_002dtab_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      out.write("\r\n");
      out.write("<div class=\"container-fluid\">\r\n");
      out.write("    <nav class=\"toolbar navbar navbar-default\">\r\n");
      out.write("        <div class=\"collapse navbar-collapse\">\r\n");
      out.write("            <ul class=\"nav navbar-nav\">\r\n");
      out.write('\r');
      out.write('\n');
      out.write("\r\n");
      out.write("            </ul>\r\n");
      out.write("        </div>\r\n");
      out.write("    </nav>\r\n");
      out.write("\r\n");
      out.write("    <div id=\"form-section\" class=\"collapse form-group\">\r\n");
      out.write("        <form id=\"save-transition-form\" class=\"form-horizontal\" role=\"form\">\r\n");
      out.write("            <div class=\"form-group\">\r\n");
      out.write("                <div class=\"col-md-4 col-lg-3\">\r\n");
      out.write("                    <div class=\"form-group\">\r\n");
      out.write("                        <div class=\"col-md-4 col-lg-4\">\r\n");
      out.write("                            <label for=\"status-name\" class=\"control-label\">ID</label>\r\n");
      out.write("                        </div>\r\n");
      out.write("                        <div class=\"col-md-8 col-lg-6\">\r\n");
      out.write("                            <input type=\"text\" id=\"status-id\" name=\"id\" minlength=\"2\" class=\"form-control\" readonly disabled required>\r\n");
      out.write("                        </div>\r\n");
      out.write("                    </div>\r\n");
      out.write("                    <div class=\"form-group\">\r\n");
      out.write("                        <div class=\"col-md-4 col-lg-4\">\r\n");
      out.write("                            <label for=\"status-name\" class=\"control-label\">状态名</label>\r\n");
      out.write("                        </div>\r\n");
      out.write("                        <div class=\"col-md-8 col-lg-6\">\r\n");
      out.write("                            <input type=\"text\" id=\"status-name\" name=\"name\" minlength=\"2\" class=\"form-control\" readonly required>\r\n");
      out.write("                        </div>\r\n");
      out.write("                    </div>\r\n");
      out.write("                    <div class=\"form-group\">\r\n");
      out.write("                        <div class=\"col-md-offset-4 col-lg-offset-4 col-md-8 col-lg-6\">\r\n");
      out.write("                            <label id=\"question-start-status\" class=\"label-lg\">初始状态</label>\r\n");
      out.write("                        </div>\r\n");
      out.write("                    </div>\r\n");
      out.write("                </div>\r\n");
      out.write("\r\n");
      out.write("                <div class=\"col-md-7\">\r\n");
      out.write("                    <select id=\"transitional-states\" class=\"form-control\" size=\"9\" multiple>\r\n");
      out.write("                    </select>\r\n");
      out.write("\r\n");
      out.write("                </div>\r\n");
      out.write("            </div>\r\n");
      out.write("\r\n");
      out.write("            <div class=\"form-group\">\r\n");
      out.write("                <div class=\"col-md-2 col-md-offset-4 col-lg-1 col-lg-offset-3\">\r\n");
      out.write("                    <button id=\"save-status-btn\" type=\"submit\" class=\"btn btn-block btn-primary\">保存</button>\r\n");
      out.write("                </div>\r\n");
      out.write("                <div class=\"col-md-2 col-lg-1\">\r\n");
      out.write("                    <button id=\"reset-status-btn\" type=\"reset\" class=\"btn btn-block btn-warning\">重置</button>\r\n");
      out.write("                </div>\r\n");
      out.write("            </div>\r\n");
      out.write("        </form>\r\n");
      out.write("    </div>\r\n");
      out.write("    <table id=\"transition-table\" class=\"table table-condensed table-bordered table-striped table-responsive table-hover\">\r\n");
      out.write("\r\n");
      out.write("    </table>\r\n");
      out.write("</div>\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<script id=\"transition-data-template\" type=\"text/x-handlebars-template\">\r\n");
      out.write("    <thead>\r\n");
      out.write("    <tr class=\"warning\">\r\n");
      out.write("        <th  class=\"action-col-2\"></th>\r\n");
      out.write("        <th class=\"col-md-2 col-lg-1 column-separator\">状态</th>\r\n");
      out.write("        <th colspan=\"{{maxTransitions}}\"><!--{{@index}}--></th>\r\n");
      out.write("    </tr>\r\n");
      out.write("    </thead>\r\n");
      out.write("    <tbody>\r\n");
      out.write("    {{#each statuses}}\r\n");
      out.write("        <tr class=\"item-row\" data-id=\"{{id}}\" data-index=\"{{@index}}\">\r\n");
      out.write("            <td class=\"warning\">\r\n");
      out.write("                {{#unless finish}}\r\n");
      out.write("                    <a href=\"#\" class=\"edit-item\"><i class=\"glyphicon glyphicon-edit\"></i></a>\r\n");
      out.write("                {{/unless}}\r\n");
      out.write("                    <a href=\"#\" class=\"delete-item\"><i class=\"glyphicon glyphicon-remove\"></i></a>\r\n");
      out.write("            </td>\r\n");
      out.write("            <td class=\"warning column-separator\" title=\"ID: {{id}}\">\r\n");
      out.write("                {{#if start}}\r\n");
      out.write("                <span class=\"label-lg label-success\">{{name}}</span>\r\n");
      out.write("                {{else if finish}}\r\n");
      out.write("                <span class=\"label-lg label-danger\">{{name}}</span>\r\n");
      out.write("                {{else}}\r\n");
      out.write("                <span>{{name}}</span>\r\n");
      out.write("                {{/if}}\r\n");
      out.write("            </td>\r\n");
      out.write("            {{#getMapArrayValue ../map @index ../maxTransitions}}\r\n");
      out.write("            {{#is name \"!==\" ../name}}\r\n");
      out.write("                <td title=\"ID: {{id}}\">\r\n");
      out.write("                    {{#if start}}\r\n");
      out.write("                        <span class=\"label-lg label-success\">{{name}}</span>\r\n");
      out.write("                    {{else if finish}}\r\n");
      out.write("                        <span class=\"label-lg label-danger\">{{name}}</span>\r\n");
      out.write("                    {{else}}\r\n");
      out.write("                        <span>{{name}}</span>\r\n");
      out.write("                    {{/if}}\r\n");
      out.write("                </td>\r\n");
      out.write("            {{else}}\r\n");
      out.write("                <td></td>\r\n");
      out.write("            {{/is}}\r\n");
      out.write("            {{/getMapArrayValue}}\r\n");
      out.write("        </tr>\r\n");
      out.write("    {{else}}\r\n");
      out.write("        <tr class=\"item-row\" data-id=\"{{id}}\" data-index=\"{{@index}}\">\r\n");
      out.write("            <td class=\"warning\">\r\n");
      out.write("                <a href=\"#\" class=\"edit-item\"><i class=\"glyphicon glyphicon-edit\"></i></a>\r\n");
      out.write("            </td>\r\n");
      out.write("        </tr>\r\n");
      out.write("    {{/each}}\r\n");
      out.write("    </tbody>\r\n");
      out.write("</script>\r\n");
      out.write("<script id=\"save-transitions-msg\" type=\"text/x-handlebars-template\">\r\n");
      out.write("    <div class=\"row\">\r\n");
      out.write("        <div class=\"col-md-offset-2 col-md-8\">\r\n");
      out.write("            <p class=\" alert alert-warning\" role=\"alert\">确定要修改 <b>{{status.name}}</b> 的状态转换吗？</p>\r\n");
      out.write("            <p>状态过度细节如下：</p>\r\n");
      out.write("        </div>\r\n");
      out.write("    </div>\r\n");
      out.write("    <div class=\"row\">\r\n");
      out.write("        <div class=\"col-md-offset-1 col-md-10\">\r\n");
      out.write("            <table class=\"table table-striped table-bordered\">\r\n");
      out.write("                <thead>\r\n");
      out.write("                    <th>ID</th>\r\n");
      out.write("                    <th>名称</th>\r\n");
      out.write("                    <th>开始状态</th>\r\n");
      out.write("                    <th>结束状态</th>\r\n");
      out.write("                </thead>\r\n");
      out.write("                <tbody>\r\n");
      out.write("                {{#each statuses}}\r\n");
      out.write("                <tr>\r\n");
      out.write("                    <td>{{id}}</td>\r\n");
      out.write("                    <td>{{name}}</td>\r\n");
      out.write("                    <td>\r\n");
      out.write("                        {{#if start}}\r\n");
      out.write("                        <span class=\"label-lg label-danger\">初始状态</span>\r\n");
      out.write("                        {{/if}}\r\n");
      out.write("                    </td>\r\n");
      out.write("                    <td>\r\n");
      out.write("                        {{#if finish}}\r\n");
      out.write("                        <span class=\"label-lg label-danger\">结束状态</span>\r\n");
      out.write("                        {{/if}}\r\n");
      out.write("                    </td>\r\n");
      out.write("                </tr>\r\n");
      out.write("                {{/each}}\r\n");
      out.write("                </tbody>\r\n");
      out.write("            </table>\r\n");
      out.write("        </div>\r\n");
      out.write("    </div>\r\n");
      out.write("</script>\r\n");
      out.write("<script id=\"delete-transitions-msg\" type=\"text/x-handlebars-template\">\r\n");
      out.write("    <div class=\"row\">\r\n");
      out.write("        <div class=\"col-md-offset-2 col-md-8\">\r\n");
      out.write("            <p class=\" alert alert-danger\" role=\"alert\">确定要删除 <b>{{status.name}}</b> 的全部状态转换吗？</p>\r\n");
      out.write("            <p>状态过度细节如下：</p>\r\n");
      out.write("        </div>\r\n");
      out.write("    </div>\r\n");
      out.write("    <div class=\"row\">\r\n");
      out.write("        <div class=\"col-md-offset-1 col-md-10\">\r\n");
      out.write("            <table class=\"table table-striped table-bordered\">\r\n");
      out.write("                <thead>\r\n");
      out.write("                <th>ID</th>\r\n");
      out.write("                <th>名称</th>\r\n");
      out.write("                <th>开始状态</th>\r\n");
      out.write("                <th>结束状态</th>\r\n");
      out.write("                </thead>\r\n");
      out.write("                <tbody>\r\n");
      out.write("                {{#each statuses}}\r\n");
      out.write("                <tr>\r\n");
      out.write("                    <td>{{id}}</td>\r\n");
      out.write("                    <td>{{name}}</td>\r\n");
      out.write("                    <td>\r\n");
      out.write("                        {{#if start}}\r\n");
      out.write("                        <span class=\"label-lg label-danger\">初始状态</span>\r\n");
      out.write("                        {{/if}}\r\n");
      out.write("                    </td>\r\n");
      out.write("                    <td>\r\n");
      out.write("                        {{#if finish}}\r\n");
      out.write("                        <span class=\"label-lg label-danger\">结束状态</span>\r\n");
      out.write("                        {{/if}}\r\n");
      out.write("                    </td>\r\n");
      out.write("                </tr>\r\n");
      out.write("                {{/each}}\r\n");
      out.write("                </tbody>\r\n");
      out.write("            </table>\r\n");
      out.write("        </div>\r\n");
      out.write("    </div>\r\n");
      out.write("</script>\r\n");
      out.write("<script id=\"transition-list-items\" type=\"text/x-handlebars-template\">\r\n");
      out.write("    {{#each statuses}}\r\n");
      out.write("            <option value=\"{{id}}\" title=\"{{name}}\">{{name}}</option>\r\n");
      out.write("    {{/each}}\r\n");
      out.write("</script>\r\n");
      out.write("<script src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${ctx}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("/assets/js/admin/question/question-status-transition.js\"></script>\r\n");
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