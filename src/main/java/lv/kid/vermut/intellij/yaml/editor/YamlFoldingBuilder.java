package lv.kid.vermut.intellij.yaml.editor;

import com.intellij.lang.ASTNode;
import com.intellij.lang.folding.FoldingBuilder;
import com.intellij.lang.folding.FoldingDescriptor;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import lv.kid.vermut.intellij.yaml.lexer.YamlTokenTypes;
import lv.kid.vermut.intellij.yaml.parser.YamlNodes;
import lv.kid.vermut.intellij.yaml.psi.YamlMapping;
import lv.kid.vermut.intellij.yaml.psi.YamlTuple;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Fold sections in Yaml
 */
public class YamlFoldingBuilder implements FoldingBuilder {
    @NotNull
    @Override
    public FoldingDescriptor[] buildFoldRegions(@NotNull ASTNode node, @NotNull Document document) {
        List<FoldingDescriptor> descriptors = new ArrayList<FoldingDescriptor>();

        for (final YamlMapping mapping : PsiTreeUtil.findChildrenOfType(node.getPsi(), YamlMapping.class)) {

            if (mapping.getFirstChild().getNode().getElementType().equals(YamlTokenTypes.YAML_BlockEntry))
                descriptors.add(new FoldingDescriptor(mapping.getNode(),
                        new TextRange(mapping.getTextRange().getStartOffset() + 2,
                                mapping.getTextRange().getEndOffset() - 1)) {
                    @Nullable
                    @Override
                    public String getPlaceholderText() {
                        return mapping.getText().substring(2, 10);
                    }
                });
        }

        for (final YamlTuple tuple : PsiTreeUtil.findChildrenOfType(node.getPsi(), YamlTuple.class)) {

            final PsiElement value = tuple.getValue();
            if (value != null && value.getNode().getElementType().equals(YamlNodes.YAML_MappingNode))
                descriptors.add(new FoldingDescriptor(value.getLastChild().getNode(),
                        new TextRange(value.getTextRange().getStartOffset(),
                                value.getTextRange().getEndOffset() - 1)));

            else if (value != null && value.getNode().getElementType().equals(YamlNodes.YAML_SequenceNode))
                descriptors.add(new FoldingDescriptor(value.getNode(),
                        new TextRange(value.getTextRange().getStartOffset(),
                                value.getTextRange().getEndOffset() - 1)));

        }

        return descriptors.toArray(new FoldingDescriptor[descriptors.size()]);
    }

    @Nullable
    @Override
    public String getPlaceholderText(@NotNull ASTNode node) {
        return ": ...";
    }

    public boolean isCollapsedByDefault(@NotNull ASTNode node) {
        return false;
    }
}
