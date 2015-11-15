package lv.kid.vermut.intellij.yaml.parser;

import com.intellij.lang.ASTNode;
import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiParser;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;
import org.yaml.snakeyaml.parser.ComposerEx;
import org.yaml.snakeyaml.parser.ParserImplEx;
import org.yaml.snakeyaml.resolver.Resolver;

/**
 * Created by Pavels.Veretennikovs on 2015.06.27..
 */
public class YamlPsiParser implements PsiParser {
    @NotNull
    @Override
    public ASTNode parse(@NotNull IElementType root, @NotNull final PsiBuilder builder) {
        ParserImplEx parser = new ParserImplEx(new PsiBuilderScannerParallelizator(builder));

        ComposerEx composer = new ComposerEx(parser, new Resolver());
        PsiBuilder.Marker mark = builder.mark();
//        try {
        while (composer.checkNode()) {
            composer.composeDocument();
        }
        // Drop the STREAM-END event.
        parser.getEvent();
/*
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
*/

        mark.done(root);
        return builder.getTreeBuilt();
    }
}