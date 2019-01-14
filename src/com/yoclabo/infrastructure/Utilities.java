/*
 *
 * Utilities.java
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

package com.yoclabo.infrastructure;

public class Utilities {

    public static String cut(String arg, String target, int offset) {
        int i = arg.indexOf(target);
        if (-1 == i) {
            return arg;
        } else {
            return arg.substring(0, i + offset).trim();
        }
    }

    public static String skip(String arg, String target, int offset) {
        int i = arg.indexOf(target);
        if (-1 == i) {
            return "";
        } else {
            return arg.substring(i + target.length() + offset).trim();
        }
    }

}
