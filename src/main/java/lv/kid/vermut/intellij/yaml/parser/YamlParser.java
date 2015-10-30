package lv.kid.vermut.intellij.yaml.parser;

import com.intellij.lang.ASTNode;
import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiParser;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.parser.ComposerEx;
import org.yaml.snakeyaml.parser.ParserImplEx;
import org.yaml.snakeyaml.resolver.Resolver;

/**
 * Created by Pavels.Veretennikovs on 2015.06.27..
 */
public class YamlParser implements PsiParser {
    @NotNull
    @Override
    public ASTNode parse(IElementType root, final PsiBuilder builder) {
        ParserImplEx parser = new ParserImplEx(new PsiBuilderToScannerAdapter(builder));

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
