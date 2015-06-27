package lv.kid.vermut.intellij.ansible.parser;

import com.intellij.lang.ASTNode;
import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiParser;
import com.intellij.psi.tree.IElementType;
import com.intellij.util.ReflectionUtil;
import org.jetbrains.annotations.NotNull;
import org.yaml.snakeyaml.composer.Composer;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.NodeId;
import org.yaml.snakeyaml.nodes.Tag;
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
    public ASTNode parse(IElementType root, PsiBuilder builder) {
        ParserImpl parser = new ParserImpl(new StreamReader(""));
        assert ReflectionUtil.setField(ParserImpl.class, parser, Scanner.class, "scanner", new PsiBuilderAdapter(builder));

        Composer composer = new Composer(parser, new Resolver() {
            @Override
            public Tag resolve(NodeId kind, String value, boolean implicit) {
                return super.resolve(kind, value, implicit);
            }
        });

        if (composer.checkNode()) {
            Node singleNode = composer.getNode();
            int o = 2;
        }
        return null;
    }
}
