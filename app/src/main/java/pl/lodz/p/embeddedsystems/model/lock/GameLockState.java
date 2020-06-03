package pl.lodz.p.embeddedsystems.model.lock;

public enum GameLockState {
    LOCKED("HEAD DEVICE NORTH TO START."), UNLOCKED;

    private String info;

    GameLockState(){

    }
    GameLockState(String info){
        this.info = info;
    }

    public String getInfo() {
        return info;
    }
}
