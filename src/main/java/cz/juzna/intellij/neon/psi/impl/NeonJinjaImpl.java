package cz.juzna.intellij.neon.psi.impl;

import com.intellij.lang.ASTNode;
import cz.juzna.intellij.neon.psi.NeonJinja;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by Pavels.Veretennikovs on 2015.05.20..
 */
public class NeonJinjaImpl extends NeonPsiElementImpl implements NeonJinja {
    public NeonJinjaImpl(@NotNull ASTNode astNode) {
        super(astNode);
    }
    public String toString() {
        return "Neon Jinja2";
    }

}
