package game.ground.jumpableground;

import edu.monash.fit2099.engine.positions.Ground;

import java.util.ArrayList;
import java.util.List;

public class JumpableGroundManager {
    private final List<JumpableGround> allJumpableGround;

    private static JumpableGroundManager instance;

    private JumpableGroundManager() {
        this.allJumpableGround = new ArrayList<>();
    }

    public static JumpableGroundManager getInstance() {
        if (instance == null) {
            instance = new JumpableGroundManager();
        }
        return instance;
    }

    public List<JumpableGround> getAllModifiableHighGround() {
        return allJumpableGround;
    }

    public void addJumpableGround(JumpableGround jumpableGround) {
        this.allJumpableGround.add(jumpableGround);
    }

    //TODO: add exception
    public void removeHighGround(JumpableGround jumpableGround) {
        this.allJumpableGround.remove(jumpableGround);
    }

    public JumpableGround getJumpableGround(Ground ground) {
        for(JumpableGround jumpableGround : this.allJumpableGround) {
            if (jumpableGround.equals(ground)) {
                return jumpableGround;
            }
        }
        return null;
    }

    public boolean isJumpableGround(Ground ground) {
        JumpableGround corrJumpableGround = this.getJumpableGround(ground);
        return corrJumpableGround != null;
    }

}
