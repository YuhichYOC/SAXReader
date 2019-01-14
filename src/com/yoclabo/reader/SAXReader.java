/*
 *
 * SAXReader.java
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

import static com.yoclabo.infrastructure.Utilities.cut;
import static com.yoclabo.infrastructure.Utilities.skip;

public class SAXReader {

    private String document;

    public SAXReader(String arg) {
        document = arg;
    }

    public boolean isEOF() {
        return 0 == document.length();
    }

    public NodeEntity next() {
        NodeEntity ret;
        if (document.startsWith("<")) {
            ret = new NodeEntity(cut(document, ">", 1));
            document = skip(document, ">", 0);
        } else {
            ret = new NodeEntity(cut(document, "<", 0));
            document = skip(document, "<", -1);
        }
        return ret;
    }

}
