package lv.kid.vermut.intellij.yaml.parser;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.IFileElementType;
import lv.kid.vermut.intellij.yaml.YamlLanguage;

/**
 * Created by VermutMac on 10/31/2015.
 */
public class YamlNodes {
    public static final IFileElementType FILE = new IFileElementType(YamlLanguage.INSTANCE);

    public static IElementType YAML_AnchorNode = new YamlNodeFacade(YamlNodeTypes.anchor);
    public static IElementType YAML_ScalarNode = new YamlNodeFacade(YamlNodeTypes.scalar);
    public static IElementType YAML_SequenceNode = new YamlNodeFacade(YamlNodeTypes.sequence);
    public static IElementType YAML_MappingNode = new YamlNodeFacade(YamlNodeTypes.mapping);

    public static IElementType YAML_NodeTuple = new YamlNodeFacade(YamlNodeTypes.tuple);
    public static IElementType YAML_KeyNode = new YamlNodeFacade(YamlNodeTypes.key);
    public static IElementType YAML_ValueNode = new YamlNodeFacade(YamlNodeTypes.value);
}
