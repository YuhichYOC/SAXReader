/*
 *
 * NodeEntity.java
 *
 * Copyright 2019 Yuichi Yoshii
 *     吉井雄一 @ 吉井産業  you.65535.kir@gmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.yoclabo.reader.xml;

import java.util.ArrayList;
import java.util.List;

import static com.yoclabo.reader.xml.infrastructure.Utilities.cut;
import static com.yoclabo.reader.xml.infrastructure.Utilities.skip;

public class NodeEntity {

    private TYPE myType;
    private String nodeName;
    private String nodeValue;
    private List<AttributeEntity> attributes;

    public NodeEntity(String arg) {
        parseComment(arg);
        parseEndElement(arg);
        parseEmptyElement(arg);
        parseElement(arg);
        parseText(arg);
    }

    public String getNodeName() {
        return nodeName;
    }

    public String getNodeValue() {
        return nodeValue;
    }

    public List<AttributeEntity> getAttributes() {
        return attributes;
    }

    public TYPE getType() {
        return myType;
    }

    private void parseComment(String arg) {
        if (!arg.startsWith("<!--")) {
            return;
        }
        if (null != myType) {
            return;
        }
        myType = TYPE.COMMENT;
        nodeValue = arg.substring(4, arg.length() - 3);
    }

    private void parseEndElement(String arg) {
        if (!arg.startsWith("</")) {
            return;
        }
        if (null != myType) {
            return;
        }
        myType = TYPE.END_ELEMENT;
        nodeName = arg.substring(1, arg.length() - 1);
    }

    private void parseEmptyElement(String arg) {
        if (!arg.startsWith("<")) {
            return;
        }
        if (!arg.endsWith("/>")) {
            return;
        }
        if (null != myType) {
            return;
        }
        myType = TYPE.EMPTY_ELEMENT;
        arg = arg.substring(1, arg.length() - 2);
        nodeName = cut(arg, " ");
        arg = skip(arg, " ");
        attributes = parseAttributes(arg);
    }

    private void parseElement(String arg) {
        if (!arg.startsWith("<")) {
            return;
        }
        if (null != myType) {
            return;
        }
        myType = TYPE.ELEMENT;
        arg = arg.substring(1, arg.length() - 1);
        nodeName = cut(arg, " ");
        arg = skip(arg, " ");
        attributes = parseAttributes(arg);
    }

    private void parseText(String arg) {
        if (null != myType) {
            return;
        }
        myType = TYPE.TEXT;
        nodeValue = arg.trim();
    }

    private List<AttributeEntity> parseAttributes(String arg) {
        List<AttributeEntity> ret = new ArrayList<>();
        if (0 == arg.length()) {
            return ret;
        }
        do {
            String n = cut(arg, "=\"");
            arg = skip(arg, "=\"");
            String v = cut(arg, "\"");
            arg = skip(arg, "\"");
            AttributeEntity add = new AttributeEntity(n, v);
            ret.add(add);
        } while (0 < arg.length());
        return ret;
    }

    public enum TYPE {
        ELEMENT,
        EMPTY_ELEMENT,
        END_ELEMENT,
        TEXT,
        COMMENT,
    }
}
