package lv.kid.vermut.intellij.yaml.parser;

import com.intellij.psi.tree.IElementType;

enum YamlNodeTypes {
    scalar, sequence, mapping, anchor,
    tuple, key, value
}

/**
 * Created by VermutMac on 10/31/2015.
 */
public class YamlNodes {
    public static IElementType YAML_AnchorNode = new YamlNodeFacade(YamlNodeTypes.anchor);
    public static IElementType YAML_ScalarNode = new YamlNodeFacade(YamlNodeTypes.scalar);
    public static IElementType YAML_SequenceNode = new YamlNodeFacade(YamlNodeTypes.sequence);
    public static IElementType YAML_MappingNode = new YamlNodeFacade(YamlNodeTypes.mapping);

    public static IElementType YAML_NodeTuple = new YamlNodeFacade(YamlNodeTypes.tuple);
    public static IElementType YAML_KeyNode = new YamlNodeFacade(YamlNodeTypes.key);
    public static IElementType YAML_ValueNode = new YamlNodeFacade(YamlNodeTypes.value);
}
