package lv.kid.vermut.intellij.yaml.editor;

import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.openapi.options.colors.AttributesDescriptor;
import com.intellij.openapi.options.colors.ColorDescriptor;
import com.intellij.openapi.options.colors.ColorSettingsPage;
import lv.kid.vermut.intellij.yaml.YamlLanguage;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.Map;


/**
 * Settings dialog for colors
 */
public class YamlColorsPage implements ColorSettingsPage {
    public static final AttributesDescriptor[] ATTRS = {
            new AttributesDescriptor("Bad character", YamlSyntaxHighlighter.UNKNOWN),
            new AttributesDescriptor("Comment", YamlSyntaxHighlighter.COMMENTS),
            new AttributesDescriptor("Document Separators", YamlSyntaxHighlighter.DOC_SEPARATORS),
            new AttributesDescriptor("Identifier", YamlSyntaxHighlighter.IDENTIFIER),
            new AttributesDescriptor("Sign: brace, comma, etc", YamlSyntaxHighlighter.INTERPUNCTION),
            new AttributesDescriptor("Number", YamlSyntaxHighlighter.NUMBER),
            new AttributesDescriptor("Keyword", YamlSyntaxHighlighter.KEYWORD),
            new AttributesDescriptor("String", YamlSyntaxHighlighter.STRING),
            new AttributesDescriptor("Specials", YamlSyntaxHighlighter.SPECIALS),
    };

    @NotNull
    @Override
    public String getDisplayName() {
        return YamlLanguage.INSTANCE.getDisplayName();
    }

    @Override
    public Icon getIcon() {
        return null;
    }

    @NotNull
    @Override
    public AttributesDescriptor[] getAttributeDescriptors() {
        return ATTRS;
    }

    @NotNull
    @Override
    public ColorDescriptor[] getColorDescriptors() {
        return new ColorDescriptor[0];
    }

    @NotNull
    @Override
    public SyntaxHighlighter getHighlighter() {
        return new YamlSyntaxHighlighter();
    }

    @NotNull
    @Override
    public String getDemoText() {
        return "%YAML 1.1\n" +
                "---\n" +
                "receipt:     Oz-Ware Purchase Invoice\n" +
                "date:        2012-08-06\n" +
                "shipped:     False\n" +
                "customer:\n" +
                "    given:   Dorothy\n" +
                "    family:  Gale\n" +
                "    dob:     {mon: 3, day: 2, year: 1900}\n" +
                "    picture: !!binary |\n" +
                "       R0lGODlhDAAMAIQAAP//9/X\n" +
                "       17unp5WZmZgAAAOfn515eXv\n" +
                "       Pz7Y6OjuDg4J+fn5OTk6enp\n" +
                "       56enmleECcgggoBADs=mZmE" +
                "\n" +
                "--- # Items list\n" +
                "items:\n" +
                "    - part_no:   A4786\n" +
                "      descrip:   Water Bucket (Filled)\n" +
                "      price:     1.47\n" +
                "      quantity:  4\n" +
                "\n" +
                "    - part_no:   E1628\n" +
                "      descrip:   High Heeled \"Ruby\" Slippers\n" +
                "      size:      8\n" +
                "      price:     133.7\n" +
                "      quantity:  1\n" +
                "\n" +
                "bill-to:  &id001\n" +
                "    street: |\n" +
                "            123 Tornado Alley\n" +
                "            Suite 16\n" +
                "    city:   East Centerville\n" +
                "    state:  KS\n" +
                "\n" +
                "ship-to:  *id001\n" +
                "\n" +
                "specialDelivery:  >\n" +
                "    Follow the Yellow Brick\n" +
                "    Road to the Emerald City.\n" +
                "    Pay no attention to the\n" +
                "    man behind the curtain.\n" +
                "...";
    }

    @Override
    public Map<String, TextAttributesKey> getAdditionalHighlightingTagToDescriptorMap() {
        return null;
    }
}
