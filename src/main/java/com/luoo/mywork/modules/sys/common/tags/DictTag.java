package com.luoo.mywork.modules.sys.common.tags;

import com.luoo.mywork.modules.sys.utils.DictUtils;
import org.apache.taglibs.standard.tag.common.core.Util;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

/**
 * @author Luoo2
 * @create 2016-09-17 17:29
 */

public class DictTag extends TagSupport {


    protected String value;
    protected String defaultValue;
    protected String type;
    private String var;
    private int scope;

    public DictTag() {
        this.init();
    }

    private void init() {
        this.type  = null;
        this.value = null;
        this.defaultValue = null;
        this.scope = 1;
    }

    public void setValue(String value) throws JspTagException {
        this.value = value;
    }

    public void setDefaultValue(String defaultValue) throws JspTagException {
        this.defaultValue = defaultValue;
    }

    public void setType(String type) throws JspTagException {
        this.type = type;
    }


    public void setVar(String var) {
        this.var = var;
    }

    public void setScope(String scope) {
        this.scope = Util.getScope(scope);
    }

    public int doEndTag() throws JspException {
        String dictLabel = null;
        if(this.value == null) {
            if(this.var != null) {
                this.pageContext.removeAttribute(this.var, this.scope);
            }

            return 6;
        } else {

            dictLabel = DictUtils.getDictLabel(this.value, this.type, this.defaultValue);

            if(this.var != null) {
                this.pageContext.setAttribute(this.var, dictLabel, this.scope);
            } else {
                try {
                    this.pageContext.getOut().print(dictLabel);
                } catch (IOException var5) {
                    throw new JspTagException(var5.toString(), var5);
                }
            }

            return 6;
        }
    }

    public void release() {
        this.init();
    }


}
