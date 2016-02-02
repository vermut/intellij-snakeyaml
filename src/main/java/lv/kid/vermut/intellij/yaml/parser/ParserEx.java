/**
 * Copyright (c) 2008, http://www.snakeyaml.org
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package lv.kid.vermut.intellij.yaml.parser;

import com.intellij.lang.PsiBuilder;
import lv.kid.vermut.intellij.yaml.lexer.ScannerEx;
import org.yaml.snakeyaml.events.Event;
import org.yaml.snakeyaml.parser.ParserImpl;

class ParserEx extends ParserImpl {

    ParserEx(ScannerEx scanner) {
        super(scanner);
    }

    PsiBuilder.Marker getMarker() {
        return ((ScannerEx) scanner).getMarker();
    }

    /**
     * Get the next event.
     */
    public Event peekEvent() {
        try {
            ((ScannerEx) scanner).setPeekMode(true);
            return super.peekEvent();
        } finally {
            ((ScannerEx) scanner).setPeekMode(false);
        }
    }

    /**
     * Get the next event and proceed further.
     */
    public Event getEvent() {
        peekEvent();
        ((ScannerEx) scanner).catchUpWithScanner();
        return super.getEvent();
    }
}
