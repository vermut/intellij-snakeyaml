package lv.kid.vermut.intellij.yaml.editor;

import com.intellij.lang.ASTNode;
import com.intellij.lang.folding.FoldingBuilder;
import com.intellij.lang.folding.FoldingDescriptor;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;
import com.intellij.psi.util.PsiTreeUtil;
import lv.kid.vermut.intellij.yaml.parser.NeonElementTypes;
import lv.kid.vermut.intellij.yaml.psi.YamlMapping;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Fold sections in Yaml
 */
public class NeonFoldingBuilder implements FoldingBuilder {
    private static final TokenSet COMPOUND_VALUE = TokenSet.create(
            NeonElementTypes.COMPOUND_VALUE,
            NeonElementTypes.HASH
    );

    private static void collectDescriptors(@NotNull ASTNode node, @NotNull List<FoldingDescriptor> descriptors) {
        IElementType type = node.getElementType();
        TextRange nodeTextRange = node.getTextRange();
        if ((!StringUtil.isEmptyOrSpaces(node.getText())) && (nodeTextRange.getLength() >= 2)) {
            if (type == NeonElementTypes.KEY_VALUE_PAIR) {
                ASTNode[] children = node.getChildren(COMPOUND_VALUE);

                if ((children.length > 0) && (!StringUtil.isEmpty(children[0].getText().trim()))) {
                    descriptors.add(new FoldingDescriptor(node, nodeTextRange));
                }
            }
            if (type == NeonElementTypes.SCALAR_VALUE) {
                descriptors.add(new FoldingDescriptor(node, nodeTextRange));
            }
        }
        for (ASTNode child : node.getChildren(null)) {
            collectDescriptors(child, descriptors);
        }
    }

    @NotNull
    @Override
    public FoldingDescriptor[] buildFoldRegions(@NotNull ASTNode node, @NotNull Document document) {
        //FoldingGroup group = FoldingGroup.newGroup("YAML_MappingNode");

        List<FoldingDescriptor> descriptors = new ArrayList<FoldingDescriptor>();

        Collection<YamlMapping> mappings = PsiTreeUtil.findChildrenOfType(node.getPsi(), YamlMapping.class);
        for (final YamlMapping mapping : mappings) {

            descriptors.add(new FoldingDescriptor(mapping.getNode(),
                    new TextRange(mapping.getTextRange().getStartOffset() + 1,
                            mapping.getTextRange().getEndOffset() - 1)) {
                @Nullable
                @Override
                public String getPlaceholderText() {
                    return "huj";
                }
            });
        }
        return descriptors.toArray(new FoldingDescriptor[descriptors.size()]);
    }


    @NotNull
    public FoldingDescriptor[] buildFgoldRegions(@NotNull ASTNode astNode, @NotNull Document document) {
        List descriptors = new LinkedList();
        collectDescriptors(astNode, descriptors);
        return (FoldingDescriptor[]) descriptors.toArray(new FoldingDescriptor[descriptors.size()]);
    }

    @Nullable
    public String getPlaceholderText(@NotNull ASTNode node) {
        IElementType type = node.getElementType();
        if (type == NeonElementTypes.KEY_VALUE_PAIR) {
            return node.getFirstChildNode().getText();
        }
        if (type == NeonElementTypes.SCALAR_VALUE) {
            return node.getText().substring(0, 1);
        }
        return "...";
    }

    public boolean isCollapsedByDefault(@NotNull ASTNode node) {
        return false;
    }
}
