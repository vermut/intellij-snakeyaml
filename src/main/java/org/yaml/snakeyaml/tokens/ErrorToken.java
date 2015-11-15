package org.yaml.snakeyaml.tokens;

import org.yaml.snakeyaml.error.Mark;

/**
 * Created by VermutMac on 11/16/2015.
 */
public class ErrorToken extends Token {
    public ErrorToken(Mark startMark, Mark endMark) {
        super(startMark, endMark);
    }

    @Override
    public ID getTokenId() {
        return ID.Error;
    }
}
