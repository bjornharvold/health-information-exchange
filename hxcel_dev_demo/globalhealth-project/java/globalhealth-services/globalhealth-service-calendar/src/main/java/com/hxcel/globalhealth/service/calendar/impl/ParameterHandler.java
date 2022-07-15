/*
 * Copyright (c) 2006, Health XCEL Inc.. All Rights Reserved.
 */

package com.hxcel.globalhealth.service.calendar.impl;

import net.fortuna.ical4j.model.Parameter;
import net.fortuna.ical4j.model.ParameterList;
import net.fortuna.ical4j.model.parameter.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.dom4j.Element;

import java.util.Iterator;

/**
 * User: bharvold
 * Date: Nov 2, 2006
 * Time: 6:45:37 PM
 * Description:
 */
public class ParameterHandler {
    private static final Logger log = LoggerFactory.getLogger(ParameterHandler.class);

    public void printParameterList(ParameterList parameterList) {
        for (Iterator iter = parameterList.iterator(); iter.hasNext();) {
            Parameter parameter = (Parameter) iter.next();
            printParameter(parameter);
        }
    }

    public Element toXCalParameterList(Element e, ParameterList parameterList) {
        for (Iterator iter = parameterList.iterator(); iter.hasNext();) {
            Parameter parameter = (Parameter) iter.next();
            toXCalParameter(e, parameter);
        }

        return e;
    }

    public void printParameter(Parameter parameter) {
        log.info("============================================== BEGIN");
        log.info("Parameter: " + parameter.getClass());
        log.info(parameter.toString());
        log.info("============================================== END");
    }

    public Element toXCalParameter(Element e, Parameter parameter) {
        if (parameter instanceof AltRep) {
            toXCalAltRep(e, (AltRep)parameter);
        } else if (parameter instanceof Cn) {
            toXCalCn(e, (Cn)parameter);
        } else if (parameter instanceof CuType) {
            toXCalCuType(e, (CuType)parameter);
        } else if (parameter instanceof DelegatedFrom) {
            toXCalDelegatedFrom(e, (DelegatedFrom)parameter);
        } else if (parameter instanceof DelegatedTo) {
            toXCalDelegatedTo(e, (DelegatedTo)parameter);
        } else if (parameter instanceof Dir) {
            toXCalDir(e, (Dir)parameter);
        } else if (parameter instanceof Encoding) {
            toXCalEncoding(e, (Encoding)parameter);
        } else if (parameter instanceof FbType) {
            toXCalFbType(e, (FbType)parameter);
        } else if (parameter instanceof FmtType) {
            toXCalFmtType(e, (FmtType)parameter);
        } else if (parameter instanceof Language) {
            toXCalLanguage(e, (Language)parameter);
        } else if (parameter instanceof Member) {
            toXCalMember(e, (Member)parameter);
        } else if (parameter instanceof PartStat) {
            toXCalPartStat(e, (PartStat)parameter);
        } else if (parameter instanceof Range) {
            toXCalRange(e, (Range)parameter);
        } else if (parameter instanceof Related) {
            toXCalRelated(e, (Related)parameter);
        } else if (parameter instanceof RelType) {
            toXCalRelType(e, (RelType)parameter);
        } else if (parameter instanceof Role) {
            toXCalRole(e, (Role)parameter);
        } else if (parameter instanceof Rsvp) {
            toXCalRsvp(e, (Rsvp)parameter);
        } else if (parameter instanceof SentBy) {
            toXCalSentBy(e, (SentBy)parameter);
        } else if (parameter instanceof TzId) {
            toXCalTzId(e, (TzId)parameter);
        } else if (parameter instanceof Value) {
            toXCalValue(e, (Value)parameter);
        } else if (parameter instanceof XParameter) {
            toXCalXParameter(e, (XParameter)parameter);
        }

        return e;
    }
    
    private Element toXCalAltRep(Element e, AltRep v) {
        e.addAttribute("altrep", v.getValue().replaceAll("\"", ""));
        return e;
    }

    private Element toXCalCn(Element e, Cn v) {
        e.addAttribute("cn", v.getValue().replaceAll("\"", ""));
        return e;
    }

    private Element toXCalCuType(Element e, CuType v) {
        e.addAttribute("cutype", v.getValue().replaceAll("\"", ""));
        return e;
    }

    private Element toXCalDelegatedFrom(Element e, DelegatedFrom v) {
        e.addAttribute("delegated-from", v.getValue().replaceAll("\"", ""));
        return e;
    }

    private Element toXCalDelegatedTo(Element e, DelegatedTo v) {
        e.addAttribute("delegated-to", v.getValue().replaceAll("\"", ""));
        return e;
    }

    private Element toXCalDir(Element e, Dir v) {
        e.addAttribute("dir", v.getValue().replaceAll("\"", ""));
        return e;
    }

    private Element toXCalEncoding(Element e, Encoding v) {
        e.addAttribute("encoding", v.getValue().replaceAll("\"", ""));
        return e;
    }

    private Element toXCalFbType(Element e, FbType v) {
        e.addAttribute("fbtype", v.getValue().replaceAll("\"", ""));
        return e;
    }

    private Element toXCalFmtType(Element e, FmtType v) {
        e.addAttribute("fmttype", v.getValue().replaceAll("\"", ""));
        return e;
    }

    private Element toXCalLanguage(Element e, Language v) {
        e.addAttribute("xml:lang", v.getValue().replaceAll("\"", ""));
        return e;
    }

    private Element toXCalMember(Element e, Member v) {
        e.addAttribute("member", v.getValue().replaceAll("\"", ""));
        return e;
    }

    private Element toXCalPartStat(Element e, PartStat v) {
        e.addAttribute("partstat", v.getValue().replaceAll("\"", ""));
        return e;
    }

    private Element toXCalRange(Element e, Range v) {
        e.addAttribute("range", v.getValue().replaceAll("\"", ""));
        return e;
    }

    private Element toXCalRelated(Element e, Related v) {
        e.addAttribute("related", v.getValue().replaceAll("\"", ""));
        return e;
    }

    private Element toXCalRelType(Element e, RelType v) {
        e.addAttribute("reltype", v.getValue().replaceAll("\"", ""));
        return e;
    }

    private Element toXCalRole(Element e, Role v) {
        e.addAttribute("role", v.getValue().replaceAll("\"", ""));
        return e;
    }

    private Element toXCalRsvp(Element e, Rsvp v) {
        e.addAttribute("rsvp", v.getValue().replaceAll("\"", ""));
        return e;
    }

    private Element toXCalSentBy(Element e, SentBy v) {
        e.addAttribute("sent-by", v.getValue().replaceAll("\"", ""));
        return e;
    }

    private Element toXCalTzId(Element e, TzId v) {
        e.addAttribute("tzid", v.getValue().replaceAll("\"", ""));
        return e;
    }

    private Element toXCalValue(Element e, Value v) {
        e.addAttribute("value", v.getValue().replaceAll("\"", ""));
        return e;
    }

    private Element toXCalXParameter(Element e, XParameter v) {
        e.addAttribute("x-"+v.getName(), v.getValue().replaceAll("\"", ""));
        return e;
    }
}
