package lv.kid.vermut.intellij.yaml.parser;

import com.intellij.lang.ASTNode;
import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiParser;
import com.intellij.psi.tree.IElementType;
import com.intellij.util.ReflectionUtil;
import org.jetbrains.annotations.NotNull;
import org.yaml.snakeyaml.events.Event;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.parser.ParserImpl;
import org.yaml.snakeyaml.reader.StreamReader;
import org.yaml.snakeyaml.resolver.Resolver;
import org.yaml.snakeyaml.scanner.Scanner;

/**
 * Created by Pavels.Veretennikovs on 2015.06.27..
 */
public class YamlParser implements PsiParser {
    @NotNull
    @Override
    public ASTNode parse(IElementType root, final PsiBuilder builder) {
        ParserImpl parser = new ParserImpl(new StreamReader("")) {
            @Override
            public Event peekEvent() {
                return super.peekEvent();
            }

            @Override
            public Event getEvent() {
                currentEvent = super.getEvent();
                return currentEvent;
            }
        };
        assert ReflectionUtil.setField(ParserImpl.class, parser, Scanner.class, "scanner", new PsiBuilderAdapter(builder));

        ComposerEx composer = new ComposerEx(parser, new Resolver(), builder);
        PsiBuilder.Marker mark = builder.mark();
        while (composer.checkNode()) {
            Node singleNode = composer.composeDocument();
            int o = 2;
        }
        // Drop the STREAM-END event.
        parser.getEvent();

        mark.done(root);
        return builder.getTreeBuilt();
    }
}
