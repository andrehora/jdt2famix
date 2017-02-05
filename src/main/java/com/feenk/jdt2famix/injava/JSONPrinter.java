//Copyright (c) 2007-2008 Adrian Kuhn <akuhn(a)iam.unibe.ch>
//
//This file is part of 'Fame (for Java)'.
//
//'Fame (for Java)' is free software: you can redistribute it and/or modify
//it under the terms of the GNU Lesser General Public License as published by
//the Free Software Foundation, either version 3 of the License, or (at your
//option) any later version.
//
//'Fame (for Java)' is distributed in the hope that it will be useful, but
//WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
//or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
//License for more details.
//
//You should have received a copy of the GNU Lesser General Public License
//along with 'Fame (for Java)'. If not, see <http://www.gnu.org/licenses/>.
//
package com.feenk.jdt2famix.injava;

import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

import ch.akuhn.fame.internal.AbstractPrintClient;
import ch.akuhn.util.Strings;

public class JSONPrinter extends AbstractPrintClient {
	
private String FAMIX_LIST = "modifiers";
private String current_attribute;

private static final Format dateFormat = new SimpleDateFormat(
        "yyyy-MM-DD,hh:mm:ss");

public static final Object UNLIMITED = new Object();

public JSONPrinter(Appendable stream) {
    super(stream);
}

@Override
public void beginAttribute(String name) {
	current_attribute = name;
    indentation++;
    lntabs();
    append(',');
    append('"');
    append(name);
    append('"');
    append(':');
    append(' ');
    if (name.equals(FAMIX_LIST)) {
    	append('[');
    }
}

@Override
public void beginDocument() {
    //indentation++;
    append('[');
}

@Override
public void beginElement(String name) {
    indentation++;
    lntabs();
    append('{');
    //indentation++;
    //lntabs();
    //append(',');
    append("\"type\": ");
    append('"');
    append(name);
    append('"');
    append(',');
    append(' ');
    //append('{');
}

@Override
public void endAttribute(String name) {
    //append(',');
    if (name.equals(FAMIX_LIST)) {
    	append("\"\"]");
    }
    indentation--;
}

@Override
public void endDocument() {
    append("{}]");
    close();
}

@Override
public void endElement(String name) {
    append('}');
    append(',');
    indentation--;
}

@Override
public void primitive(Object value) {
    append(' ');
    if (value == UNLIMITED) {
        append('*');
    } else if (value instanceof String) {
        String string = (String) value;
        string = string.replace("\n", " ").replace("\r", " ");
        append('"');
        for (char ch: Strings.chars(string)) {
            if (ch == '\"' || ch == '\\') append('\\');
            append(ch);
            //if (ch == '\n') append("");
        }
        append('"');
        if (current_attribute.equals(FAMIX_LIST)) {
        	append(',');
        }
    } else if (value instanceof Boolean || value instanceof Number) {
        append(value.toString());
    } else if (value instanceof Date) {
        append(dateFormat.format(value));
    } else if (value instanceof Character) {
        append("'" + value + "'");
    } else if (value instanceof char[]) {
        primitive(new String((char[])value));
    } else {
        assert false : "Unknown primitive: " + value + " of type: " + value.getClass().getCanonicalName();
    }
}

@Override
public void reference(int index) {
    try {
        //stream.append(" (ref: "); // must prepend space!
        stream.append(Integer.toString(index));
        //stream.append(')');
    } catch (IOException ex) {
        throw new RuntimeException(ex);
    }
}

@Override
public void reference(String name) {
    try {
        //stream.append(" (ref: "); // must prepend space!
        stream.append(name);
        //stream.append(')');
    } catch (IOException ex) {
        throw new RuntimeException(ex);
    }
}

@Override
public void serial(int index) {
    try {
    	//indentation++;
    	//lntabs();
        stream.append("\"id\": "); // must prepend space!
        stream.append(Integer.toString(index));
        //indentation--;
    	//lntabs();
        //stream.append('{');
    } catch (IOException ex) {
        throw new RuntimeException(ex);
    }
}

}
