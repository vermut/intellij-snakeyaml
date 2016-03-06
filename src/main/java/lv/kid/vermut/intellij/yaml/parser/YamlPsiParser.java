package lv.kid.vermut.intellij.yaml.parser;

import com.intellij.lang.ASTNode;
import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiParser;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;
import lv.kid.vermut.intellij.yaml.lexer.ScannerAndBuilderSynchronizer;
import org.jetbrains.annotations.NotNull;
import org.yaml.snakeyaml.composer.ComposerException;
import org.yaml.snakeyaml.error.YAMLException;
import org.yaml.snakeyaml.parser.ParserException;
import org.yaml.snakeyaml.resolver.Resolver;
import org.yaml.snakeyaml.scanner.ScannerException;

/**
 * Created by Pavels.Veretennikovs on 2015.06.27..
 */
public class YamlPsiParser implements PsiParser {
    private static final Logger log = Logger.getInstance(YamlPsiParser.class);

    @NotNull
    @Override
    public ASTNode parse(@NotNull IElementType root, @NotNull final PsiBuilder builder) {
        ScannerAndBuilderSynchronizer scannerEx = new ScannerAndBuilderSynchronizer(builder);
        ParserEx parser = new ParserEx(scannerEx);

        builder.setDebugMode(true);
        ComposerEx composer = new ComposerEx(parser, new Resolver());
        PsiBuilder.Marker mark = builder.mark();
        try {
            while (composer.checkNode()) {
                composer.getNode();
            }
            // Drop the STREAM-END event.
            parser.getEvent();

        } catch (ScannerException e) {
            failAsError(builder, scannerEx, e);
        } catch (ParserException e) {
            failAsError(builder, scannerEx, e);
        } catch (ComposerException e) {
            failAsError(builder, scannerEx, e);
        } catch (YAMLException e) {
            failAsError(builder, scannerEx, e);
        }

        mark.done(root);
        return builder.getTreeBuilt();
    }

    private void failAsError(@NotNull PsiBuilder builder, ScannerAndBuilderSynchronizer scannerEx, Exception e) {
        log.debug(e);
        scannerEx.setPeekMode(false);

        // Eat up everything as error
        scannerEx.markError(e.getMessage());
        PsiBuilder.Marker errorMarker = scannerEx.getMarker();
        while (builder.getTokenType() != null) {
            builder.advanceLexer();
        }
        errorMarker.done(TokenType.ERROR_ELEMENT);
    }
}
