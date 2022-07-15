package org.apache.jsp.WEB_002dINF.jsp.service;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class service_005fupload_005fform_jspx extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fspring_002dform_005fform_0026_005fmethod_005fid_005fenctype_005fcommandName_005faction;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fspring_002dform_005ferrors_0026_005fpath_005fcssClass_005fnobody;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fspring_005fmessage_0026_005ftext_005fcode_005fnobody;

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.AnnotationProcessor _jsp_annotationprocessor;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _005fjspx_005ftagPool_005fspring_002dform_005fform_0026_005fmethod_005fid_005fenctype_005fcommandName_005faction = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fspring_002dform_005ferrors_0026_005fpath_005fcssClass_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fspring_005fmessage_0026_005ftext_005fcode_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_annotationprocessor = (org.apache.AnnotationProcessor) getServletConfig().getServletContext().getAttribute(org.apache.AnnotationProcessor.class.getName());
  }

  public void _jspDestroy() {
    _005fjspx_005ftagPool_005fspring_002dform_005fform_0026_005fmethod_005fid_005fenctype_005fcommandName_005faction.release();
    _005fjspx_005ftagPool_005fspring_002dform_005ferrors_0026_005fpath_005fcssClass_005fnobody.release();
    _005fjspx_005ftagPool_005fspring_005fmessage_0026_005ftext_005fcode_005fnobody.release();
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;


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

      //  spring-form:form
      org.springframework.web.servlet.tags.form.FormTag _jspx_th_spring_002dform_005fform_005f0 = (org.springframework.web.servlet.tags.form.FormTag) _005fjspx_005ftagPool_005fspring_002dform_005fform_0026_005fmethod_005fid_005fenctype_005fcommandName_005faction.get(org.springframework.web.servlet.tags.form.FormTag.class);
      _jspx_th_spring_002dform_005fform_005f0.setPageContext(_jspx_page_context);
      _jspx_th_spring_002dform_005fform_005f0.setParent(null);
      // /WEB-INF/jsp/service/service_upload_form.jspx(15,67) name = enctype type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_spring_002dform_005fform_005f0.setEnctype("multipart/form-data");
      // /WEB-INF/jsp/service/service_upload_form.jspx(15,67) name = method type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_spring_002dform_005fform_005f0.setMethod("POST");
      // /WEB-INF/jsp/service/service_upload_form.jspx(15,67) name = action type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_spring_002dform_005fform_005f0.setAction("/secure/service/upload.admin");
      // /WEB-INF/jsp/service/service_upload_form.jspx(15,67) name = commandName type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_spring_002dform_005fform_005f0.setCommandName("bundleUploadForm");
      // /WEB-INF/jsp/service/service_upload_form.jspx(15,67) name = id type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_spring_002dform_005fform_005f0.setId("form");
      int[] _jspx_push_body_count_spring_002dform_005fform_005f0 = new int[] { 0 };
      try {
        int _jspx_eval_spring_002dform_005fform_005f0 = _jspx_th_spring_002dform_005fform_005f0.doStartTag();
        if (_jspx_eval_spring_002dform_005fform_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
          do {
            out.write("<table width=\"100%\" border=\"0\" cellspacing=\"2\" cellpadding=\"0\">");
            out.write("<tr>");
            out.write("<td>");
            out.write(' ');
            out.write("</td>");
            out.write("<td>");
            //  spring-form:errors
            org.springframework.web.servlet.tags.form.ErrorsTag _jspx_th_spring_002dform_005ferrors_005f0 = (org.springframework.web.servlet.tags.form.ErrorsTag) _005fjspx_005ftagPool_005fspring_002dform_005ferrors_0026_005fpath_005fcssClass_005fnobody.get(org.springframework.web.servlet.tags.form.ErrorsTag.class);
            _jspx_th_spring_002dform_005ferrors_005f0.setPageContext(_jspx_page_context);
            _jspx_th_spring_002dform_005ferrors_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_spring_002dform_005fform_005f0);
            // /WEB-INF/jsp/service/service_upload_form.jspx(20,72) name = cssClass type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
            _jspx_th_spring_002dform_005ferrors_005f0.setCssClass("errors");
            // /WEB-INF/jsp/service/service_upload_form.jspx(20,72) name = path type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
            _jspx_th_spring_002dform_005ferrors_005f0.setPath("file");
            int[] _jspx_push_body_count_spring_002dform_005ferrors_005f0 = new int[] { 0 };
            try {
              int _jspx_eval_spring_002dform_005ferrors_005f0 = _jspx_th_spring_002dform_005ferrors_005f0.doStartTag();
              if (_jspx_th_spring_002dform_005ferrors_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                return;
              }
            } catch (Throwable _jspx_exception) {
              while (_jspx_push_body_count_spring_002dform_005ferrors_005f0[0]-- > 0)
                out = _jspx_page_context.popBody();
              _jspx_th_spring_002dform_005ferrors_005f0.doCatch(_jspx_exception);
            } finally {
              _jspx_th_spring_002dform_005ferrors_005f0.doFinally();
              _005fjspx_005ftagPool_005fspring_002dform_005ferrors_0026_005fpath_005fcssClass_005fnobody.reuse(_jspx_th_spring_002dform_005ferrors_005f0);
            }
            out.write("</td>");
            out.write("</tr>");
            out.write("<tr>");
            out.write("<td class=\"form_text\" width=\"125\">");
            if (_jspx_meth_spring_005fmessage_005f0(_jspx_th_spring_002dform_005fform_005f0, _jspx_page_context, _jspx_push_body_count_spring_002dform_005fform_005f0))
              return;
            out.write(':');
            out.write(' ');
            out.write("</td>");
            out.write("<td width=\"225\">");
            out.write("<input name=\"file\" type=\"file\"/>");
            out.write("<span class=\"required\">");
            out.write('*');
            out.write("</span>");
            out.write("</td>");
            out.write("</tr>");
            out.write("</table>");
            out.write("<input value=\"" + (java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${id}", java.lang.String.class, (PageContext)_jspx_page_context, null, false) + "\" name=\"id\" type=\"hidden\"/>");
            int evalDoAfterBody = _jspx_th_spring_002dform_005fform_005f0.doAfterBody();
            if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
              break;
          } while (true);
        }
        if (_jspx_th_spring_002dform_005fform_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
          return;
        }
      } catch (Throwable _jspx_exception) {
        while (_jspx_push_body_count_spring_002dform_005fform_005f0[0]-- > 0)
          out = _jspx_page_context.popBody();
        _jspx_th_spring_002dform_005fform_005f0.doCatch(_jspx_exception);
      } finally {
        _jspx_th_spring_002dform_005fform_005f0.doFinally();
        _005fjspx_005ftagPool_005fspring_002dform_005fform_0026_005fmethod_005fid_005fenctype_005fcommandName_005faction.reuse(_jspx_th_spring_002dform_005fform_005f0);
      }
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try { out.clearBuffer(); } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }

  private boolean _jspx_meth_spring_005fmessage_005f0(javax.servlet.jsp.tagext.JspTag _jspx_th_spring_002dform_005fform_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_spring_002dform_005fform_005f0)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  spring:message
    org.springframework.web.servlet.tags.MessageTag _jspx_th_spring_005fmessage_005f0 = (org.springframework.web.servlet.tags.MessageTag) _005fjspx_005ftagPool_005fspring_005fmessage_0026_005ftext_005fcode_005fnobody.get(org.springframework.web.servlet.tags.MessageTag.class);
    _jspx_th_spring_005fmessage_005f0.setPageContext(_jspx_page_context);
    _jspx_th_spring_005fmessage_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_spring_002dform_005fform_005f0);
    // /WEB-INF/jsp/service/service_upload_form.jspx(23,104) name = text type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_spring_005fmessage_005f0.setText("Bundle");
    // /WEB-INF/jsp/service/service_upload_form.jspx(23,104) name = code type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_spring_005fmessage_005f0.setCode("service.bundle");
    int[] _jspx_push_body_count_spring_005fmessage_005f0 = new int[] { 0 };
    try {
      int _jspx_eval_spring_005fmessage_005f0 = _jspx_th_spring_005fmessage_005f0.doStartTag();
      if (_jspx_th_spring_005fmessage_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
    } catch (Throwable _jspx_exception) {
      while (_jspx_push_body_count_spring_005fmessage_005f0[0]-- > 0)
        out = _jspx_page_context.popBody();
      _jspx_th_spring_005fmessage_005f0.doCatch(_jspx_exception);
    } finally {
      _jspx_th_spring_005fmessage_005f0.doFinally();
      _005fjspx_005ftagPool_005fspring_005fmessage_0026_005ftext_005fcode_005fnobody.reuse(_jspx_th_spring_005fmessage_005f0);
    }
    return false;
  }
}
