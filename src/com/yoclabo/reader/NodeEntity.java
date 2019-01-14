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

package com.yoclabo.reader;

import java.util.ArrayList;
import java.util.List;

import static com.yoclabo.infrastructure.Utilities.cut;
import static com.yoclabo.infrastructure.Utilities.skip;

public class NodeEntity {

    private final TYPE myType;
    private String nodeName;
    private String nodeValue;
    private List<AttributeEntity> attributes;

    public NodeEntity(String arg) {
        if (arg.startsWith("<!--")) {
            myType = TYPE.COMMENT;
            parseComment(arg);
        } else if (arg.startsWith("</")) {
            myType = TYPE.END_ELEMENT;
            parseNode(arg);
        } else if (arg.startsWith("<")) {
            if (arg.endsWith("/>")) {
                myType = TYPE.EMPTY_ELEMENT;
                parseEmpty(arg);
            } else {
                myType = TYPE.ELEMENT;
                parseNode(arg);
            }
        } else {
            myType = TYPE.TEXT;
            parseValue(arg);
        }
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
        nodeValue = arg.replaceAll("(<!--)(.*)(-->)", "$2").trim();
    }

    private void parseNode(String arg) {
        arg = arg.replaceAll("(<)(.+)(>)", "$2");
        nodeName = cut(arg, " ", 0);
        arg = skip(arg, " ", 0);
        attributes = parseAttributes(arg);
    }

    private void parseEmpty(String arg) {
        arg = arg.replaceAll("(<)(.+)(/>)", "$2");
        nodeName = cut(arg, " ", 0);
        arg = skip(arg, " ", 0);
        attributes = parseAttributes(arg);
    }

    private void parseValue(String arg) {
        nodeValue = arg.trim();
    }

    private List<AttributeEntity> parseAttributes(String arg) {
        List<AttributeEntity> ret = new ArrayList<>();
        if (0 == arg.length()) {
            return ret;
        }
        do {
            String n = cut(arg, "=\"", 0);
            arg = skip(arg, "=\"", 0);
            String v = cut(arg, "\"", 0);
            arg = skip(arg, "\"", 0);
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
