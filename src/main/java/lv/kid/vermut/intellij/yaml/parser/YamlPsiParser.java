package lv.kid.vermut.intellij.yaml.parser;

import com.intellij.lang.ASTNode;
import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiParser;
import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;
import lv.kid.vermut.intellij.yaml.lexer.PsiBuilderScannerParallelizator;
import org.jetbrains.annotations.NotNull;
import org.yaml.snakeyaml.parser.ComposerEx;
import org.yaml.snakeyaml.parser.ParserImplEx;
import org.yaml.snakeyaml.resolver.Resolver;
import org.yaml.snakeyaml.scanner.ScannerException;

/**
 * Created by Pavels.Veretennikovs on 2015.06.27..
 */
public class YamlPsiParser implements PsiParser {
    @NotNull
    @Override
    public ASTNode parse(@NotNull IElementType root, @NotNull final PsiBuilder builder) {
        PsiBuilderScannerParallelizator scannerEx = new PsiBuilderScannerParallelizator(builder);
        ParserImplEx parser = new ParserImplEx(scannerEx);

        builder.setDebugMode(true);
        ComposerEx composer = new ComposerEx(parser, new Resolver());
        PsiBuilder.Marker mark = builder.mark();
        try {
            while (composer.checkNode()) {
                composer.composeDocument();
            }
            // Drop the STREAM-END event.
            parser.getEvent();


        } catch (ScannerException e) {
            // e.printStackTrace();
            scannerEx.setPeekMode(false);
            // Eat up all as error
            scannerEx.markError(e.getMessage());
            PsiBuilder.Marker errorMarker = scannerEx.getMarker();
            while (builder.getTokenType() != null) {
                builder.advanceLexer();
            }
            ;
            // errorMarker.error(e.getMessage());
            errorMarker.done(TokenType.ERROR_ELEMENT);
        }

        mark.done(root);
        return builder.getTreeBuilt();
    }
}
